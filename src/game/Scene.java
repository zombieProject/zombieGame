package game;

import agent.*;
import game.debug.DebugGraphs;
import game.debug.EmptyOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Scene {
	private static final int LEFT = 0;
	private static final int TOP = 0;
	public static final int RIGHT = 16000;
	public static final int DOWN = 9000;
	public static final int ZOMBIE_LIMIT = 400;
	public static final int ASH_LIMIT = 1000;
	public static final int SHOOTING_RANGE = 2000;

	int score;
    private String filePath;
    private String status;
    
	Ash ash;
	Map<Integer,Zombie> zombielist;
	Map<Integer,Zombie> zombienextlist;
	Map<Integer, Human> humanlist;
	
	private boolean debugMode = false;

	// debug related information
	private static PrintStream debugTextDisable = createPrintStream(new EmptyOutputStream());
	private ByteArrayOutputStream byteArrayOutputStream = null;
	private PrintStream debugTextEnable = null;
	private DebugGraphs debugGraphs = new DebugGraphs(this);

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
		if(!debugMode) return;
		if(byteArrayOutputStream == null){
			byteArrayOutputStream = new ByteArrayOutputStream();
			debugTextEnable = createPrintStream(byteArrayOutputStream);
		}
	}

	public PrintStream debugInfo(){
		if(debugMode) return debugTextEnable;
		else return debugTextDisable;
	}

	public DebugGraphs getDebugGraphs() {
		return debugGraphs;
	}

	public String getDebugText(){
		if(byteArrayOutputStream == null) return null;
		try {
			return byteArrayOutputStream.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getFilePath() {
        return filePath;
    }

	public String getStatus(){
		return status;
	}
	
	public int getScore(){
		return score;
	}
	
	public Ash getAsh() {
		return ash;
	}


	public Map<Integer, Zombie> getZombielist() {
		return zombielist;
	}
	public Map<Integer, Zombie> getZombieNextlist() {
		return zombienextlist;
	}


	public Map<Integer, Human> getHumanlist() {
		return humanlist;
	}
	
	
	// Constructors for Scene
	
	// use Scene object as argument
	
	public Scene(Scene s){

	    filePath = s.filePath;
		score = s.score;
		ash = new Ash(s.getAsh());
		
		zombielist = new HashMap<Integer,Zombie> ();
		zombienextlist = new HashMap<Integer,Zombie> ();
		humanlist = new HashMap<Integer,Human> ();
		
		for (Map.Entry<Integer, Zombie> entry : s.getZombielist().entrySet()){
			zombielist.put(entry.getKey(), new Zombie(entry.getValue()));
		}
		
		for (Map.Entry<Integer, Zombie> entry : s.getZombieNextlist().entrySet()){
			zombienextlist.put(entry.getKey(), new Zombie(entry.getValue()));
		}
		
		for (Map.Entry<Integer, Human> entry : s.getHumanlist().entrySet()){
			humanlist.put(entry.getKey(), new Human(entry.getValue()));
		}
		status = s.getStatus();
		
		
	}

	// use filepath as argument
	
	public Scene(String filepath){
		
		// In each file, the first line is Ash's data,
		//    the second line is Zombie's data,
		//	  the third line is Human's data.
		
		score = 0;
		zombielist = new HashMap<Integer, Zombie>();
        zombienextlist = new HashMap<Integer, Zombie>();
		humanlist = new HashMap<Integer, Human>();
		status = "ongoing";
		this.filePath = filepath;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath)));
			// ash line
			String input1=br.readLine();
			input1 = input1.trim().replaceAll("\\s+", "\t" );

			String[] dataa = input1.split("\t");
			this.ash = Agents.makeAsh(Integer.parseInt(dataa[0]), Integer.parseInt(dataa[1]));
			// zombie line
			String input2 = br.readLine();
            input2 = input2.trim().replaceAll("\\s+", "\t" );
			String[] dataz = input2.split("\t");
			for (int i = 0; i < dataz.length; i+=2){
				zombielist.put(i/2, Agents.makeZombie(Integer.parseInt(dataz[i]), Integer.parseInt(dataz[i+1])));
			}
			// human line
			String input3 = br.readLine();
            input3 = input3.trim().replaceAll("\\s+", "\t" );
			String[] datah = input3.split("\t");
			for (int i = 0; i < datah.length; i+=2){
				humanlist.put(i/2, Agents.makeHuman(Integer.parseInt(datah[i]), Integer.parseInt(datah[i+1])));
			}

            // update the zombie next list with the next move for each zombies
            updateZombieNextMove();
            br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void printScene(){
		System.out.println("Ash: " + ash.toString());
		System.out.println("zombies: " + zombielist.toString());
		System.out.println("human: " + humanlist.toString());
	}
	
	public String status(){
		return status;
	}
	
	public void nextScene(String ashmove){

	    // Zombie moves to the next location
	    zombieMove();

	    // Zombies next step locations gets updated based on the current move
		updateZombieNextMove();
		ashMove(ashmove);
		ashKillZombie();
		zombieKillHuman();
		
		// determine if Ash win the game
		
		if(zombienextlist.isEmpty()){
			status = "pass";
		}else if(humanlist.isEmpty()){
			status = "fail";
		}
	}
	
	public List<Integer> outGameSequence(){
		
		List<Integer> sequence = new ArrayList<Integer>();
		//add ash
		sequence.add(ash.getX());
		sequence.add(ash.getY());
		//add human
		sequence.add(humanlist.size());
		for (Map.Entry<Integer, Human> entry : humanlist.entrySet()){
			sequence.add(entry.getKey());
			sequence.add(entry.getValue().getX());
			sequence.add(entry.getValue().getY());
		}		
		//add zombie
		sequence.add(zombielist.size());
		for (Map.Entry<Integer, Zombie> entry : zombielist.entrySet()){
			sequence.add(entry.getKey());
			sequence.add(entry.getValue().getX());
			sequence.add(entry.getValue().getY());
			sequence.add(zombienextlist.get(entry.getKey()).getX());
			sequence.add(zombienextlist.get(entry.getKey()).getY());
			}
		
		return sequence;
	}
	

	// This method is used to find agent who is in smallest distance
	
	private Agent findTarget(Agent a){
		
		double distance = Integer.MAX_VALUE;
		Agent target = new Agent();
		
		for (Map.Entry<Integer, Human> entry : humanlist.entrySet()){
			double d = distance(a.getX(),a.getY(),entry.getValue().getX(),entry.getValue().getY());
			if (d<distance){
				
				// find the smallest distance between Agent a and a human
				
				distance = d;
				target = entry.getValue();
			}			
		}
		
		// compute the distance between Agent a and Ash
		
		if(distance > distance(a.getX(),a.getY(),ash.getX(),ash.getY())){
			target = ash;
		}
		return target;
	}
	
	
	private void ashMove(String move){
		String[] m = move.split("\\s+");
		int targetx = Integer.parseInt(m[0]);
		int targety = Integer.parseInt(m[1]);
		move(ash,targetx,targety);

	}
	
	private void zombieMove(){
        for (Map.Entry<Integer, Zombie> entry : zombielist.entrySet()) {
        	
        	// find the target that the zombie will move to
        	
			Agent target = findTarget(entry.getValue());
			move(entry.getValue(),target.getX(),target.getY());
			
		}
	}

	public void updateZombieNextMove() {

	    // Next predicted move will always be based on the first move
		
	    HashMap<Integer, Zombie> zombiesNextList = new HashMap<>();

	    // All zombies move together in each turn
	    
        for (Map.Entry<Integer, Zombie> entry : zombielist.entrySet()) {
        	
            Zombie copyOfZombie = new Zombie(entry.getValue());
            Agent target = findTarget(copyOfZombie);
            move(copyOfZombie,target.getX(),target.getY());
            zombiesNextList.put(entry.getKey(), copyOfZombie);
        }
        this.zombienextlist = zombiesNextList;
    }

	private void move(Agent a, int x, int y){
		int limit;

		// Just Ash and Zombies can make movement
		
		if(a.isAsh()) {
			
			// Ash moves exact 1000 units
			limit = ASH_LIMIT;
			
		} else {
			
			// Zombie moves exact 400 units
			limit = ZOMBIE_LIMIT;
		}

		if(distance(a.getX(),a.getY(),x,y) < limit) {
			
			// if the distance is smaller than limit, then the agent moves onto target coordination
			a.setDestination(x, y);
			
		} else {
			
			// else change agent's coordination
			
			
			double offsetx = offsetX(limit,a.getX(),a.getY(),x,y);
			if(a.getX()>x){
				offsetx = -offsetx;
			}
			double offsety = offsetY(limit,a.getX(),a.getY(),x,y);
			if(a.getY()>y){
				offsety = -offsety;
			}
					
			a.setDestination((int)(Math.floor(offsetx+a.getX())), (int)(Math.floor(offsety+a.getY())));
		}
	}
	
	// Helper methods for calculation
	
	public double offsetX(int bevel, int x1,int y1, int x2, int y2){
		if(y1 == y2){
			return bevel;
			
		}else{
			return bevel*Math.sin(Math.atan((double)(Math.abs(((double)x1-(double)x2)/((double)y1-(double)y2)))));
		}
	}
	
	public double offsetY(int bevel, int x1,int y1, int x2, int y2){
		if(y1 == y2){
			return 0;
		}else{
			return bevel*Math.cos(Math.atan((double)(Math.abs(((double)x1-(double)x2)/((double)y1-(double)y2)))));
		}
	}
	
	public double distance(int x1,int y1, int x2, int y2){
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
	
	
	// This method calculates scores when Ash kills zombies
	
	private void ashKillZombie(){
		
		int killcount = 0;
		int zombieworth = humanlist.size()*10;
		Map<Integer, Zombie> copyzombienextlist = new HashMap<Integer,Zombie>(zombienextlist);
		
		for (Map.Entry<Integer, Zombie> entry : copyzombienextlist.entrySet()){
			
			// Ash can kill any zombies in the range of 2000 units
			
			if(distance(ash.getX(),ash.getY(),entry.getValue().getX(),entry.getValue().getY())<=SHOOTING_RANGE){
				zombienextlist.remove(entry.getKey());
				zombielist.remove(entry.getKey());
				killcount+=1;
				score += zombieworth*fibonacci(killcount);
			}		
		}
	}
	
	private void zombieKillHuman(){

		// The human are killed when human and zombies have the same coordination
		
	    Map<Integer, Human> humansKilled = new HashMap<>(humanlist);

		for (Map.Entry<Integer, Zombie> entryz : zombienextlist.entrySet()){
			
			for (Map.Entry<Integer, Human> entryh : humansKilled.entrySet()){
				
				if(entryz.getValue().getX()==entryh.getValue().getX()&&
						entryz.getValue().getY()==entryh.getValue().getY()){
					
                    humanlist.remove(entryh.getKey());
				}
			}		
		}
	}
	
	
	// n>=1,1, 2, 3, 5, 8
	
	private int fibonacci(int n){
		if (n==1){
			return 1;
		}
		else if(n==2){
			return 2;
		}
		else return fibonacci(n-1)+fibonacci(n-2);
	}
	
	
	// Test
	
	public static void main(String args[]){
		Scene s1 = new Scene("testcase/testcase1.txt");
		s1.printScene();
//		double a = 8.8;
		System.out.println(s1.offsetY(400,0,0,0,500));
		System.out.println(s1.offsetY(400,0,0,500,500));
		System.out.println(s1.fibonacci(5));
		Map<Integer, String> mylist= new HashMap<Integer,String>();
		
		System.out.println(s1.offsetX(400, 15999, 5500, 10999, 0));
		System.out.println(s1.offsetY(400, 15999, 5500, 10999, 0));

	}

	public static PrintStream createPrintStream(OutputStream outputStream){
		try {
			return new PrintStream(outputStream, true, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
