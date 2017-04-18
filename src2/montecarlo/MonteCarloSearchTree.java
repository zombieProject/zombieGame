package montecarlo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Agent;
import agent.Human;
import game.Game;
import game.Scene;

public class MonteCarloSearchTree {
	int firstMoveIndex;
	Scene scene;
	private MonteCarloSearchTree parent;
	private int depth;
	private int targetx;
	private int targety;
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
		targetx = fromx;
		targety = fromy;		
		childrenlist = new ArrayList<MonteCarloSearchTree>();
		firstMoveIndex = Integer.MAX_VALUE;
	}
	
	public Scene getScene(){
		return scene;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public int getTargetX(){
		return targetx;
	}
	
	public int getTargetY(){
		return targety;
	}
	
	public MonteCarloSearchTree getParent(){
		return parent;
	}
	
	private void expand(int searchdepth, int samplesize){
		if(depth < searchdepth&&scene.getStatus()=="ongoing"){
			for(int i = 0; i< samplesize; i++){
				Location location = MonteCarlo.randomLocation(scene.getAsh().getX(), scene.getAsh().getY());
				int x =location.getX();
				int y = location.getY();
				String ashmove = String.valueOf(x);
				ashmove += " ";
				ashmove += String.valueOf(y);
				Scene scenecopy = scene.sceneCopy();
				scenecopy.nextScene(ashmove);
				if(scenecopy.getStatus() != "fail"){
					MonteCarloSearchTree child = new MonteCarloSearchTree(scenecopy, this, depth+1, x, y);
					if(depth == 0)
					{
					child.setFirstMoveIndex(childrenlist.size());}
					else
					{
						child.setFirstMoveIndex(firstMoveIndex);
					}
					childrenlist.add(child);
				}	
			}
		}
		
		for(MonteCarloSearchTree tree : childrenlist){
//			System.out.println("expand depth: " + this.getDepth());
//			System.out.println("node count: " + childrenlist.size());
			tree.expand(searchdepth, samplesize);
		}
	}
	
	public int getFirtsMoveIndex() {
		return firstMoveIndex;
	}

	public void setFirstMoveIndex(int firsMoveIndex) {
		this.firstMoveIndex = firsMoveIndex;
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
		List<MonteCarloSearchTree> leafs = new ArrayList<MonteCarloSearchTree>();
		leafs = getAllLeaf(leafs);
		
		MonteCarloSearchTree bestresult = this;
		int bestscore = Integer.MIN_VALUE;
		for(MonteCarloSearchTree tree : leafs){
			if(tree.getScene().getStatus() != "fail" && tree.getDepth()!=0){
				if(tree.getScene().getScore() > bestscore)
				{
					bestresult = tree;
					bestscore = tree.getScene().getScore();
				}else if(tree.getScene().getScore() == bestscore){
					if(tree.getDepth() < bestresult.getDepth()){
						bestresult = tree;
						bestscore = tree.getScene().getScore();
					}
				}
			}
		}
		System.out.println("predict best score:  "+ bestscore);
//		if(this.getScene().getScore() == bestresult.getScene().getScore()){
//			return moveToHuman();
//		}
//		
//		while(bestresult.getDepth() != 1&&bestresult.getDepth()!=0){
//			bestresult = bestresult.getParent();
//		}
		
		if(bestscore ==this.scene.getScore()||this.childrenlist.isEmpty()){
			return moveToHuman();
		}
		else{
		move = String.valueOf(this.childrenlist.get(bestresult.getFirtsMoveIndex()).getTargetX());
		move += " ";
		move += String.valueOf(this.childrenlist.get(bestresult.getFirtsMoveIndex()).getTargetY());
		System.out.println("ash`s target: " + move.toString());
		return move;
		}

	}
	
	public String getMoveByProbability(int searchdepth, int samplesize){
		String move = null;
		expand(searchdepth,samplesize);
		List<MonteCarloSearchTree> leafs = new ArrayList<MonteCarloSearchTree>();
		leafs = getAllLeaf(leafs);
		
		MonteCarloSearchTree bestresult = this;
		Map<Integer, Integer> scores = new HashMap<Integer,Integer>();
		for(MonteCarloSearchTree tree : leafs){
			if(tree.getScene().getStatus() != "fail" && tree.getDepth()!=0){
				int index = tree.getFirtsMoveIndex();
				if(scores.containsKey(index)){
					int value = scores.get(index);
					scores.put(index, value+tree.scene.getScore());
				}else{
					scores.put(index, tree.scene.getScore());
				}

			}		
		}
		int maxscore = 0;
		int bestindexofmove = 0;
		for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
			if(entry.getValue()>=maxscore){
				maxscore = entry.getValue();
				bestindexofmove = entry.getKey();
			}
		}
		if(maxscore ==this.scene.getScore()){
			return moveToHuman();
		}
		else{
		move = String.valueOf(this.childrenlist.get(bestindexofmove).getTargetX());
		move += " ";
		move += String.valueOf(this.childrenlist.get(bestindexofmove).getTargetX());
		System.out.println("ash`s target: " + move.toString());
		return move;
		}
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
