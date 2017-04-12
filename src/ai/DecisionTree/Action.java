package ai.DecisionTree;

import java.util.function.Supplier;

/**
 * Created by shubhimittal on 4/12/17.
 */

class Action extends NodeImplementer {

    private boolean executed;

    private Runnable action;

    Action(Runnable s) {
        this.action = s;
    }

    public boolean getExecuted() {
        return this.executed;
    }

    public void execute() {
        action.run();
        this.executed = true;
    }

}
