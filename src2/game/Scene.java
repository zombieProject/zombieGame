package game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import agent.Agent;
import agent.Agents;
import agent.Ash;
import agent.Human;
import agent.Zombie;

public class Scene {
	public static final int LEFT = 0;
	public static final int TOP = 0;
	public static final int RIGHT = 16000;
	public static final int DOWN = 9000;
	public static final int ZOMBIE_LIMIT = 400;
	public static final int ASH_LIMIT = 1000;
	public static final int SHOOTING_RANGE = 2000;
	
	private String status;
	Ash ash;
	Map<Integer,Zombie> zombielist;
	Map<Integer,Zombie> zombienextlist;
	Map<Integer, Human> humanlist;
	int score;
	
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
	
	public Scene(){
		
	}
	
	public Scene(Scene s){
		score = s.score;
		ash = new Ash(s.getAsh());
		
		zombielist = new HashMap<Integer,Zombie> ();
		zombienextlist = new HashMap<Integer,Zombie> ();
		humanlist = new HashMap<Integer,Human> ();
		
		for (Map.Entry<Integer, Zombie> entry : s.getZombieNextlist().entrySet()){
			zombielist.put(entry.getKey(), new Zombie(entry.getValue()));
		}
		
		for (Map.Entry<Integer, Zombie> entry : s.getZombieNextlist().entrySet()){
			zombienextlist.put(entry.getKey(), new Zombie(entry.getValue()));
		}
		
		for (Map.Entry<Integer, Human> entry : s.getHumanlist().entrySet()){
			humanlist.put(entry.getKey(), new Human(entry.getValue()));
		}
		zombieMove();
		
		status = s.getStatus();
		
		
	}

	public Scene(String filepath){
		score = 0;
		zombielist = new HashMap<Integer, Zombie>();
		humanlist = new HashMap<Integer, Human>();
		status = "ongoing";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
			// ash line
			String input=br.readLine();
			String[] dataa = input.split("\t");
			this.ash = Agents.makeAsh(Integer.parseInt(dataa[0]), Integer.parseInt(dataa[1]));
			// zombie line
			input = br.readLine();
			String[] dataz = input.split("\t");
			for (int i = 0; i < dataz.length; i+=2){
				zombielist.put(i/2, Agents.makeZombie(Integer.parseInt(dataz[i]), Integer.parseInt(dataz[i+1])));
			}
			// human line
			input = br.readLine();
			String[] datah = input.split("\t");
			for (int i = 0; i < datah.length; i+=2){
				humanlist.put(i/2, Agents.makeHuman(Integer.parseInt(datah[i]), Integer.parseInt(datah[i+1])));
			}
			
			zombienextlist = new HashMap<Integer,Zombie>(zombielist);
			//close
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	public Scene sceneCopy(){
		Scene copyscene = new Scene();
		copyscene.score = this.score;
		copyscene.ash = new Ash(this.getAsh());
		
		copyscene.zombielist = new HashMap<Integer,Zombie> ();
		copyscene.zombienextlist = new HashMap<Integer,Zombie> ();
		copyscene.humanlist = new HashMap<Integer,Human> ();
		
		for (Map.Entry<Integer, Zombie> entry : this.getZombieNextlist().entrySet()){
			copyscene.zombielist.put(entry.getKey(), new Zombie(entry.getValue()));
		}
		
		for (Map.Entry<Integer, Zombie> entry : this.getZombieNextlist().entrySet()){
			copyscene.zombienextlist.put(entry.getKey(), new Zombie(entry.getValue()));
		}
		
		for (Map.Entry<Integer, Human> entry : this.getHumanlist().entrySet()){
			copyscene.humanlist.put(entry.getKey(), new Human(entry.getValue()));
		}
		
		copyscene.status = this.getStatus();
		return  copyscene;
	}
	
	public void printScene(){
		System.out.println("Ash: " + ash.toString());
		System.out.println("zombies: " + zombielist.toString());
		System.out.println("human: " + humanlist.toString());
	}
	
	public String status(){
		return status;
	}
	
	public Scene nextScene(String ashmove){
//		zombieMove();
		ashMove(ashmove);
		ashKillZombie();
		zombieKillHuman();
		if(zombienextlist.isEmpty()){
			status = "pass";
		}else if(humanlist.isEmpty()){
			status = "fail";
		}
		return this;
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
	

	
	private Agent findTarget(Agent a){
		double distance = Integer.MAX_VALUE;
		Agent target = new Agent();
		for (Map.Entry<Integer, Human> entry : humanlist.entrySet()){
			double d = distance(a.getX(),a.getY(),entry.getValue().getX(),entry.getValue().getY());
			if (d<distance){
				distance = d;
				target = entry.getValue();
			}			
		}
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
		for (Map.Entry<Integer, Zombie> entry : zombienextlist.entrySet()) {  
//			move(entry.getValue(),500,500);
			Agent target = findTarget(entry.getValue());
			move(entry.getValue(),target.getX(),target.getY());
			
		}
	}
	
	private void move(Agent a, int x, int y){
//		if(a.isAsh()){
//			if(distance(a.getX(),a.getY(),x,y) <= ASH_LIMIT){
//				a.setDestination(x, y);
//			}
//			else {
//				double offsetx = offsetX(ASH_LIMIT,a.getX(),a.getY(),x,y);
//				double offsety = offsetY(ASH_LIMIT,a.getX(),a.getY(),x,y);
//				a.setDestination((int)offsetx+a.getX(), (int)offsety+a.getY());
//				
//			}
//		}else{
//			if(distance(a.getX(),a.getY(),x,y) <= ZOMBIE_LIMIT){
//				a.setDestination(x, y);	
//		}else{
//			double offsetx = offsetX(ZOMBIE_LIMIT,a.getX(),a.getY(),x,y);
//			double offsety = offsetY(ZOMBIE_LIMIT,a.getX(),a.getY(),x,y);
//			a.setDestination((int)offsetx+a.getX(), (int)offsety+a.getY());
//			}
//		}
		
		int limit;

		if(a.isAsh()) {
			limit = ASH_LIMIT;
		} else {
			limit = ZOMBIE_LIMIT;
		}

		if(distance(a.getX(),a.getY(),x,y) < limit) {
			a.setDestination(x, y);
		} else {
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
	
	private void ashKillZombie(){
		int killcount = 0;
		int zombieworth = humanlist.size()*10;
		Map<Integer, Zombie> copyzombienextlist = new HashMap<Integer,Zombie>(zombienextlist);
		for (Map.Entry<Integer, Zombie> entry : copyzombienextlist.entrySet()){
			if(distance(ash.getX(),ash.getY(),entry.getValue().getX(),entry.getValue().getY())<=SHOOTING_RANGE){
				zombienextlist.remove(entry.getKey());
				killcount+=1;
				score += zombieworth*fibonacci(killcount);
			}		
		}
	}
	
	private void zombieKillHuman(){
		Set<Integer> killid= new HashSet<Integer>();
		for (Map.Entry<Integer, Zombie> entryz : zombienextlist.entrySet()){
			for (Map.Entry<Integer, Human> entryh : humanlist.entrySet()){
				if(entryz.getValue().getX()==entryh.getValue().getX()&&
						entryz.getValue().getY()==entryh.getValue().getY()){
					killid.add(entryh.getKey());
				}
			}		
		}		
		for (Integer id : killid) {  
		      if(humanlist.containsKey(id)){
		    	  humanlist.remove(id);
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
	
	
	
	
	public static void main(String args[]){
		Scene s1 = new Scene("testcase/testcase3.txt");
		s1.printScene();
//		double a = 8.8;
		System.out.println(s1.offsetY(400,0,0,0,500));
		System.out.println(s1.offsetY(400,0,0,500,500));
		System.out.println(s1.fibonacci(5));
		Map<Integer, String> mylist= new HashMap<Integer,String>();
		
		System.out.println(s1.offsetX(400, 15999, 5500, 10999, 0));
		System.out.println(s1.offsetY(400, 15999, 5500, 10999, 0));

	}


}
