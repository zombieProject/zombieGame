package game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Agent;
import agent.Agents;
import agent.Ash;
import agent.Human;
import agent.Zombie;

public class Scene {
	private static final int LEFT = 0;
	private static final int TOP = 0;
	private static final int RIGHT = 16000;
	private static final int DOWN = 9000;
	private static final int ZOMBIE_LIMIT = 400;
	private static final int ASH_LIMIT = 1000;
	private static final int SHOOTING_RANGE = 2000;
	
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
	
	Scene(Scene s){
		score = score;
		ash = new Ash(s.getAsh());
		zombielist = new HashMap<Integer,Zombie> (s.getZombieNextlist());
		zombienextlist = new HashMap<Integer,Zombie> (s.getZombieNextlist());
		humanlist = new HashMap<Integer,Human>(s.getHumanlist());
		status = s.getStatus();
		
		
	}

	
	Scene(String filepath){
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
	
	public void printScene(){
		System.out.println("Ash: " + ash.toString());
		System.out.println("zombies: " + zombielist.toString());
		System.out.println("human: " + humanlist.toString());
	}
	
	public String status(){
		return status;
	}
	
	public void nextScene(){
		zombieMove();
		ashMove();
		ashKillZombie();
		zombieKillHuman();
		if(zombienextlist.isEmpty()){
			status = "pass";
		}else if(humanlist.isEmpty()){
			status = "fail";
		}
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
		if(distance < distance(a.getX(),a.getY(),ash.getX(),ash.getY()))
			target = ash;
		return target;
	}
	
	
	private void ashMove(){

	}
	
	private void zombieMove(){
		for (Map.Entry<Integer, Zombie> entry : zombienextlist.entrySet()) {  
//			move(entry.getValue(),500,500);
			Agent target = findTarget(entry.getValue());
			move(entry.getValue(),target.getX(),target.getY());
			
		}
	}
	
	private void move(Agent a, int x, int y){
		if(a.isAsh()){
			if(distance(a.getX(),ash.getY(),x,y) <= ASH_LIMIT){
				a.setDestination(x, y);
			}
			else {
				double offsetx = offsetX(ASH_LIMIT,a.getX(),a.getY(),x,y);
				double offsety = offsetY(ASH_LIMIT,a.getX(),a.getY(),x,y);
				a.setDestination((int)offsetx+a.getX(), (int)offsety+a.getY());
				
			}
		}else{
			if(distance(a.getX(),ash.getY(),x,y) <= ZOMBIE_LIMIT){
				a.setDestination(x, y);	
		}else{
			double offsetx = offsetX(ZOMBIE_LIMIT,a.getX(),a.getY(),x,y);
			double offsety = offsetY(ZOMBIE_LIMIT,a.getX(),a.getY(),x,y);
			a.setDestination((int)offsetx+a.getX(), (int)offsety+a.getY());
			}
		}
	}
	
	
	private double offsetX(int bevel, int x1,int y1, int x2, int y2){
		return bevel*Math.sin(Math.atan((x1-x2)/(y1-y2)));
	}
	
	private double offsetY(int bevel, int x1,int y1, int x2, int y2){
		return bevel*Math.cos(Math.atan((x1-x2)/(y1-y2)));
	}
	
	private double distance(int x1,int y1, int x2, int y2){
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
	
	private void ashKillZombie(){
		int killcount = 0;
		Map<Integer, Zombie> copyzombienextlist = new HashMap<Integer,Zombie>(zombienextlist);
		
		for (Map.Entry<Integer, Zombie> entry : copyzombienextlist.entrySet()){
			if(distance(ash.getX(),ash.getY(),entry.getValue().getX(),entry.getValue().getY())<=SHOOTING_RANGE){
				zombienextlist.remove(entry.getKey());
				killcount+=1;
				score += killcount*fibonacci(killcount);
			}		
		}
	}
	
	private void zombieKillHuman(){
		for (Map.Entry<Integer, Zombie> entryz : zombienextlist.entrySet()){
			for (Map.Entry<Integer, Human> entryh : humanlist.entrySet()){
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
	
	
	
	public static void main(String args[]){
		Scene s1 = new Scene("D:/Workspace/zombie/testcase/testcase1.txt");
		s1.printScene();
//		double a = 8.8;
		System.out.println(s1.offsetY(400,0,0,0,500));
		System.out.println(s1.offsetY(400,0,0,500,500));
		System.out.println(s1.fibonacci(5));
		Map<Integer, String> mylist= new HashMap<Integer,String>();

	}


}
