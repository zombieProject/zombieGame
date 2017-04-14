package ai.DecisionTree;

import com.sun.org.apache.regexp.internal.RE;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by shubhimittal on 4/12/17.
 */

public class Action extends NodeImplementer {

    private boolean executed;
    private Object result;

    private Supplier<Object> action;

    Action(Supplier s) {
        this.action = s;
    }

    public boolean getExecuted() {
        return this.executed;
    }

    public Object getResult() {
        return this.result;
    }

    public void execute() {
        this.result = action.get();
        this.executed = true;
    }

}