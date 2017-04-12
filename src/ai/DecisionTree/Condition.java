package ai.DecisionTree;

import java.util.function.Supplier;

/**
 * Created by shubhimittal on 4/12/17.
 */

class Condition extends NodeImplementer {

    private Supplier<Boolean> test;

    private Node trueValue;

    private Node falseValue;

    private boolean isTestSuccessful;

    Condition (Supplier s, Node t, Node f) {
        this.test = s;
        this.trueValue = t;
        this.falseValue = f;
    }


    public Node getTrueValue() {
        return this.trueValue;
    }

    public Node getFalseValue() {
        return this.falseValue;
    }

    public boolean getIsTestSuccessful() {
        return this.isTestSuccessful;
    }


    public void execute() {
        if (test.get()) {
            this.isTestSuccessful = true;
        } else {
            this.isTestSuccessful = false;
        }
    }

}