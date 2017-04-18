package ai.montecarlo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import agent.Agent;
import agent.Human;
import game.Game;
import game.Scene;

public class MonteCarloSearchTree {
	Scene scene;
	private MonteCarloSearchTree parent;
	private int depth;
	private int x;
	private int y;
	List<MonteCarloSearchTree> childrenlist;
	
	public MonteCarloSearchTree(Scene s){
		depth = 0;
		scene = s;
		childrenlist = new ArrayList<MonteCarloSearchTree>();
	}
	
	public MonteCarloSearchTree(Scene s,MonteCarloSearchTree p, int d, int fromx, int fromy){
		scene = s;
		parent = p;
		depth = d;
		x = fromx;
		y = fromy;		
		childrenlist = new ArrayList<MonteCarloSearchTree>();
	}
	
	public Scene getScene(){
		return scene;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public MonteCarloSearchTree getParent(){
		return parent;
	}
	
	private void expand(int searchdepth, int samplesize){
		if(depth < searchdepth&&scene.getStatus()=="ongoing"){
			for(int i = 0; i< samplesize; i++){
				int x = MonteCarlo.randomX()+scene.getAsh().getX();
				int y = MonteCarlo.randomY()+scene.getAsh().getY();
				String ashmove = String.valueOf(x);
				ashmove += " ";
				ashmove += String.valueOf(y);
				Scene scenecopy = new Scene(scene);
				scenecopy.nextScene(ashmove);
				if(scenecopy.getStatus() != "fail"){
					MonteCarloSearchTree child = new MonteCarloSearchTree(scenecopy, this, depth+1, x, y);
					childrenlist.add(child);
				}	
			}
		}
		
		for(MonteCarloSearchTree tree : childrenlist){
//			System.out.println("expand depth: " + this.getDepth());
//			System.out.println("node count: " + childrenlist.size());
			tree.expand(searchdepth, samplesize/4);
		}
	}
	
	private List<MonteCarloSearchTree> getAllLeaf(List<MonteCarloSearchTree> leaflist){
		if(childrenlist.isEmpty()){
			leaflist.add(this);
		}else{
			for(MonteCarloSearchTree tree:childrenlist){
				tree.getAllLeaf(leaflist);
			}
		}
		return leaflist;	
	}
	
	public String getMove(int searchdepth, int samplesize){
		String move = null;
		expand(searchdepth,samplesize);
		List<MonteCarloSearchTree> leafs = new ArrayList();
		leafs = getAllLeaf(leafs);
		
		MonteCarloSearchTree bestresult = this;
		int bestscore = Integer.MIN_VALUE;
		for(MonteCarloSearchTree tree : leafs){
			if(tree.getScene().getStatus() != "fail" && tree.getDepth()!=0){
				if(tree.getScene().getScore() >= bestresult.getScene().getScore())
				{
					bestresult = tree;
				}else if(tree.getScene().getScore() == bestresult.getScene().getScore()){
					if(tree.getDepth() < bestresult.getDepth()){
						bestresult = tree;
					}
				}
			}
		}
		if(this.getScene().getScore() == bestresult.getScene().getScore()){
			return moveToHuman();
		}
		
		while(bestresult.getDepth() != 1&&bestresult.getDepth()!=0){
			bestresult = bestresult.getParent();
		}
		
		move = String.valueOf(bestresult.getX());
		move += " ";
		move += String.valueOf(bestresult.getY());
		
		return move;
	}
	
	private String moveToHuman(){
		Map<Integer,Human> humanlist =  this.scene.getHumanlist();
		int closestHumanX = 0;
		int closestHumanY = 0;
		double mindistance = Integer.MAX_VALUE;
		for (Map.Entry<Integer,Human > entry : humanlist.entrySet()){
			double distance = this.getScene().distance(entry.getValue().getX(), entry.getValue().getY(), 
					this.getScene().getAsh().getX(), this.getScene().getAsh().getY());
			if(distance < mindistance){
				mindistance = distance;
				closestHumanX = entry.getValue().getX();
				closestHumanY = entry.getValue().getY();
			}
		}
		String move = String.valueOf(closestHumanX);
		move += " ";
		move += String.valueOf(closestHumanY);
		return move;
	}
	
	

}
