package ai.DecisionTree;

import game.Scene;
import game.SceneAPI;

/**
 * Created by shubhimittal on 4/12/17.
 */
public class DecisionTreeCreator {


    public DecisionTree createDecisionTree(Scene s) {

        SceneAPI api = new SceneAPI(s.getFilePath());

        // Action nodes
        Action moveForBetterFuture = new Action(()-> {api.moveForBetterFuture();});
        Action saveHumanInThisTurn = new Action(()-> {api.saveHumanInThisTurn();});
        Action killzombieInNextTurn = new Action(()-> {api.killzombieInNextTurn();});
        Action killZombieInThisTurn = new Action(()-> {api.killZombieInThisTurn();});

        // Condition nodes
        Condition canAshKillZombiesInNextTurn = new Condition(
                ()->{ return api.canAshKillZombiesInNextTurn();}, killzombieInNextTurn, moveForBetterFuture);

        Condition canAshKillZombiesInThisTurn = new Condition(
                ()->{return api.canAshKillZombiesInThisTurn();}, killZombieInThisTurn, canAshKillZombiesInNextTurn);

        Condition canAshSaveHumanInThisTurn = new Condition(
                ()->{return api.canAshSaveHumanInThisTurn();},saveHumanInThisTurn, canAshKillZombiesInThisTurn);

        Condition canHumanBekilledInNextTurn = new Condition(
                ()->{return api.canHumanBekilledInNextTurn();},
                canAshSaveHumanInThisTurn,
                canAshKillZombiesInThisTurn);


        // Action decision trees
        DecisionTree moveForBetterFutureA = new DecisionTree(moveForBetterFuture, null, null);
        DecisionTree saveHumanInThisTurnA = new DecisionTree(saveHumanInThisTurn, null, null);
        DecisionTree killzombieInNextTurnA= new DecisionTree(killzombieInNextTurn, null, null);
        DecisionTree killZombieInThisTurnA = new DecisionTree(killZombieInThisTurn, null, null);


        // condition decision trees
        DecisionTree canAshKillZombiesInNextTurnD = new DecisionTree(
                canAshKillZombiesInNextTurn, killzombieInNextTurnA, moveForBetterFutureA);


        DecisionTree canAshKillZombiesInThisTurnD = new DecisionTree(
                canAshKillZombiesInThisTurn, killZombieInThisTurnA, canAshKillZombiesInNextTurnD);


        DecisionTree canAshSaveHumanInThisTurnD = new DecisionTree(
                canAshSaveHumanInThisTurn, saveHumanInThisTurnA, canAshKillZombiesInThisTurnD);


        // Main tree
        DecisionTree tree = new DecisionTree(
                canHumanBekilledInNextTurn, canAshSaveHumanInThisTurnD, canAshKillZombiesInThisTurnD);


        return tree;
    }


}
