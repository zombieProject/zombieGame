package ai.DecisionTree;

import java.util.function.Supplier;

/**
 * Created by shubhimittal on 4/12/17.
 */

public class DecisionTree {

    private Node node;
    private DecisionTree left;
    private DecisionTree right;
    private Action actionExecuted;


    DecisionTree(Node node , DecisionTree left, DecisionTree right ) {

        this.node = node;
        this.left = left;
        this.right = right;
    }

    public Action getActionExecuted() {
        return actionExecuted;
    }

    public void executeDecisionTree() {

        Node startNode = node;

        while(startNode.isCondition()) {
              startNode.execute();

              Condition cond = startNode.asCondition();

            if(cond.getIsTestSuccessful()) {
                startNode = cond.getTrueValue();
             } else {
                startNode = cond.getFalseValue();
             }
        }

        actionExecuted = startNode.asAction();
        actionExecuted.execute();
    }
}