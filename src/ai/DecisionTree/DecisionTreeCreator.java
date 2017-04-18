package ai.DecisionTree;

import game.Scene;
import game.SceneAPI;

/**
 * Created by shubhimittal on 4/12/17.
 */


public class DecisionTreeCreator {

    public DecisionTree createDecisionTree(Scene s) {

        SceneAPI api = new SceneAPI(s);

        // Action nodes
        // four kinds of actions
        
        Action saveHumanInThisTurn = new Action(()-> api.saveHumanInThisTurn());
        Action killZombieInNextTurn = new Action(()-> api.killzombieInNextTurn());
        Action killZombieInThisTurn = new Action(()-> api.killZombieInThisTurn());
        Action moveToClosestHuman = new Action(()-> api.moveToClosestHuman());

        // Condition nodes
        // four kinds of conditions
        
        Condition canAshKillZombiesInNextTurn = new Condition(
                ()-> api.canAshKillZombiesInNextTurn(), killZombieInNextTurn, moveToClosestHuman);

        Condition canAshKillZombiesInThisTurn = new Condition(
                ()->api.canAshKillZombiesInThisTurn(), killZombieInThisTurn, canAshKillZombiesInNextTurn);

        Condition canAshSaveHumanInThisTurn = new Condition(
                ()-> api.canAshSaveHumanInThisTurn(),saveHumanInThisTurn, canAshKillZombiesInThisTurn);

        Condition canHumanBekilledInNextTurn = new Condition(
                ()-> api.canHumanBekilledInNextTurn(),
                canAshSaveHumanInThisTurn,
                canAshKillZombiesInThisTurn);


        // Action decision trees
        
        DecisionTree saveHumanInThisTurnA = new DecisionTree(saveHumanInThisTurn, null, null);
        DecisionTree killZombieInNextTurnA= new DecisionTree(killZombieInNextTurn, null, null);
        DecisionTree killZombieInThisTurnA = new DecisionTree(killZombieInThisTurn, null, null);
        DecisionTree moveToClosestHumanA = new DecisionTree(moveToClosestHuman, null, null);


        // condition decision trees
        
        DecisionTree canAshKillZombiesInNextTurnD = new DecisionTree(
                canAshKillZombiesInNextTurn, killZombieInNextTurnA, moveToClosestHumanA);


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
