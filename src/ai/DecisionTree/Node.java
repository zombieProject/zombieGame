package ai.DecisionTree;

/**
 * Created by shubhimittal on 4/12/17.
 */
interface  Node {

    boolean isCondition();

    boolean isAction();

    Condition asCondition();

    Action asAction();

    void execute();

}
