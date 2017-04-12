package ai.DecisionTree;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by shubhimittal on 4/12/17.
 */
public class DecisionTreeTest {


    private void action1() {

        System.out.println("action1");
    }

    private void action2() {
        System.out.println("action2");
    }


    private void action3() {
        System.out.println("action 3");
    }

    private void action4() {
        System.out.println("action 4");
    }


    // Tree1
    //               3 > 2
    //         2 > 1       Action3
    // Action1    Action2

    // Action1 should get executed

    @Test
    public void testTree1() {

        Node act1 = new Action(()-> action1());

        Node act2 = new Action(()-> action2());

        Node act3 = new Action(()-> action3());

        Node act4 = new Action(()-> action4());

        DecisionTree actTree1 = new DecisionTree(act1, null , null);
        DecisionTree actTree2 = new DecisionTree(act2, null , null);
        DecisionTree actTree3 = new DecisionTree(act3, null , null);
        DecisionTree actTree4 = new DecisionTree(act4, null , null);

        Node truePart = new Condition(
                ()-> 2 > 1, act1, act2);

        DecisionTree left = new DecisionTree(truePart, actTree1, actTree2);


        Node root = new Condition(
                ()-> 3 > 2, truePart, act3);

        DecisionTree sampleTree = new DecisionTree(root, left, actTree4);

        // output should be action 1
        sampleTree.executeDecisionTree();


        // Action 1 should get executed
        assertEquals(act1.asAction().getExecuted(), true);

        // No other actions should get executed
        assertEquals(act2.asAction().getExecuted(), false);
        assertEquals(act3.asAction().getExecuted(), false);
        assertEquals(act4.asAction().getExecuted(), false);

    }



    // Root of the tree is an action.
    // This is the tree with only one node.
    // Tree:
    // Action1

    @Test
    public void testTree2() {

        Node root = new Action(()-> action1());
        DecisionTree sampleTree = new DecisionTree(root, null, null);

        // Action 1 should get executed.
        sampleTree.executeDecisionTree();

        assertEquals(root.asAction().getExecuted(), true);

    }


}
