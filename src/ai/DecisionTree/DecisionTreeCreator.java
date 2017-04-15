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
        Action moveToHumanCentroid = new Action(()-> api.moveToHumanCentroid());
        Action saveHumanInThisTurn = new Action(()-> api.saveHumanInThisTurn());
        Action killZombieInNextTurn = new Action(()-> api.killzombieInNextTurn());
        Action killZombieInThisTurn = new Action(()-> api.killZombieInThisTurn());

        // Condition nodes
        Condition canAshKillZombiesInNextTurn = new Condition(
                ()-> api.canAshKillZombiesInNextTurn(), killZombieInNextTurn, moveToHumanCentroid);

        Condition canAshKillZombiesInThisTurn = new Condition(
                ()->api.canAshKillZombiesInThisTurn(), killZombieInThisTurn, canAshKillZombiesInNextTurn);

        Condition canAshSaveHumanInThisTurn = new Condition(
                ()-> api.canAshSaveHumanInThisTurn(),saveHumanInThisTurn, canAshKillZombiesInThisTurn);

        Condition canHumanBekilledInNextTurn = new Condition(
                ()-> api.canHumanBekilledInNextTurn(),
                canAshSaveHumanInThisTurn,
                canAshKillZombiesInThisTurn);


        // Action decision trees
        DecisionTree moveToHumanCentroidA = new DecisionTree(moveToHumanCentroid, null, null);
        DecisionTree saveHumanInThisTurnA = new DecisionTree(saveHumanInThisTurn, null, null);
        DecisionTree killZombieInNextTurnA= new DecisionTree(killZombieInNextTurn, null, null);
        DecisionTree killZombieInThisTurnA = new DecisionTree(killZombieInThisTurn, null, null);


        // condition decision trees
        DecisionTree canAshKillZombiesInNextTurnD = new DecisionTree(
                canAshKillZombiesInNextTurn, killZombieInNextTurnA, moveToHumanCentroidA);


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
