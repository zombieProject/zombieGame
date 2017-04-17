package game;

import agent.Agent;
import agent.Human;
import agent.Zombie;

import java.util.*;

/**
 * Created by shubhimittal on 4/12/17.
 */


// All The conditions and actions are developed keeping in mind
// Zombies current location and possible location in the their
// next turn.

public class SceneAPI extends Scene {


    // human and zombie in at the same index in these
    // lists share the same coordinates

    private List<Human>  humanToBeKilledInNextTurn;
    private List<Zombie> zombiesKillingHumanInNextTurn;

    // list has all the humans that can be saved
    // by Ash
    private List<Human> humanToBeSavedInThisTurn;


    // list of all the zombies that can be killed by ash in this turn
    private List<Zombie> zombiesToKillInThisTurn;


    // list of all the zombies that can be killed by ash after in second chance.
    private List<Zombie> zombiesToKillInNextTurn;


    public SceneAPI(Scene s) {
        super(s);

        humanToBeKilledInNextTurn = new ArrayList<>();
        zombiesKillingHumanInNextTurn = new ArrayList<>();
        humanToBeSavedInThisTurn = new ArrayList<>();
        zombiesToKillInThisTurn = new ArrayList<>();
        zombiesToKillInNextTurn = new ArrayList<>();

        setHumanZombieKillingsInNextTurn();
        setHumansToSaveInThisTurn();
        setZombiesToKillInThisTurn();
        setZombiesToKillInNextTurn();
    }


    /************************************************************************
     *
     * Ash's next one move prediction based on the prediction of zombies
     * current location and next action
     */


    // Sets humanToBeKilledInNextTurn
    // Sets zombiesKillingHumanInNextTurn
    private void setHumanZombieKillingsInNextTurn() {

        for(Map.Entry<Integer, Human> humanEntry : getHumanlist().entrySet()) {
            Human human = humanEntry.getValue();

            // check if this human shares coordinates with any of the zombies in the
            // currently.
            // if yes, then this human will be killed in next step by the zombie
            for(Map.Entry<Integer, Zombie> zombieEntry: getZombielist().entrySet()) {

                Zombie zombie = zombieEntry.getValue();
                if(human.getX() == zombie.getX() && human.getY() == zombie.getY()) {
                    // same x, y coordinates for human and this zombie

                    // humans that will be killed by zombies when their turn comes
                   humanToBeKilledInNextTurn.add(human);

                   // zombies that will be killing these humans in their next turn
                   zombiesKillingHumanInNextTurn.add(zombie);
                }
            }


        }

    }

    // Sets humanToBeSavedInThisTurn, humans that can be saved in this step of Ash
    // These are the humans that can be killed in the next turn by the zombies
    private void setHumansToSaveInThisTurn() {

        for(Human human : humanToBeKilledInNextTurn) {

            double humanAshDistance = distance(ash.getX(), ash.getY(), human.getX(), human.getY());

            if(humanAshDistance < 3000) {
                humanToBeSavedInThisTurn.add(human);

            }
        }
    }


    private void setZombiesToKillInThisTurn() {

        for(Map.Entry<Integer, Zombie> zombieEntry : getZombielist().entrySet()) {
            Zombie zombie = zombieEntry.getValue();

            double ashZombieDistance = distance(ash.getX(), ash.getY(), zombie.getX(), zombie.getY());

            if(ashZombieDistance < 3000) {
                zombiesToKillInThisTurn.add(zombie);

            }
        }

    }


    private void setZombiesToKillInNextTurn() {
        for(Map.Entry<Integer, Zombie> zombieEntry: getZombieNextlist().entrySet()) {
            // we check zombies after one move

            Zombie zombie = zombieEntry.getValue();

            double ashZombieDistance = distance(ash.getX(), ash.getY(), zombie.getX(), zombie.getY());

            // Ash will also move by 1000 units in the next turn
            // Ash can again move 1000 units towards the zombies
            // And have an added shooting range of 2000
            if(ashZombieDistance < 4000) {
                zombiesToKillInNextTurn.add(zombie);
            }
        }
    }

    private Agent closestToAsh(List<Agent> agents) {

        // we choose the human that is closest to Ash and that can be saved
        int ashX = ash.getX();
        int ashY = ash.getY();

        double minDistance = Double.MAX_VALUE;
        Agent minAgent = null;

        for(Agent a: agents) {
            double agentAshDistance = distance(a.getX(), a.getY(), ashX, ashY);

            if(agentAshDistance < minDistance) {

                minDistance = agentAshDistance;
                minAgent = a;
            }
        }

        return minAgent;

    }

    private Zombie killZombie(int turn) {

        List<Agent> a = new ArrayList<>();
        Zombie zombieToKill;

        if(turn == 1) {
            a.addAll(zombiesToKillInThisTurn);
        } else {
            a.addAll(zombiesToKillInNextTurn);
        }
        if(a.size() > 0 && a.size() == 1) {

            zombieToKill = (Zombie) a.get(0);

        } else {

            //TODO: change the strategy to kill more than one zombies
            zombieToKill =  (Zombie) closestToAsh(a);
        }

        return zombieToKill;
    }

    // CONDITIONS:

    // RETURNS: true if there exists a human that can be killed in by zombies in their next turn

    public boolean canHumanBekilledInNextTurn() {
        return humanToBeKilledInNextTurn.size() > 0;
    }

    // RETURNS: true iff there exists a human that can be saved by Ash in this turn,
    // humans here include the humans that can be killed by the zombie
    public boolean canAshSaveHumanInThisTurn() {
        return humanToBeSavedInThisTurn.size() > 0;
    }


    // RETURNS: true iff there exists a zombie that can be killed by Ash in this turn
    public boolean canAshKillZombiesInThisTurn() {
        return zombiesToKillInThisTurn.size() > 0;
    }




    // RETURNS: true iff there is a 100% possibility of Ash killing one of the humans
   public boolean canAshKillZombiesInNextTurn() {



       return zombiesToKillInNextTurn.size() > 0;
   }

    // ACTIONS

    // RETURNS: the human that is closest to ash and can be saved by ash.
    //
    public Map<String, Integer> saveHumanInThisTurn() {
        List<Agent> a = new ArrayList<>();
        Human humanToSave;
        Map<String, Integer> coord = new HashMap<>();
        a.addAll(humanToBeSavedInThisTurn);

        if(a.size() > 0 && a.size() == 1) {
            // there is only one human to save
            humanToSave = (Human) a.get(0);

        } else {
            // TODO: change strategy to save more than one human.
            humanToSave = (Human) closestToAsh(a);
        }

        coord.put("x", humanToSave.getX());
        coord.put("y", humanToSave.getY());

        return coord;
    }

    // RETURNS: the zombie that is closest to ash and can be killed by ash
    // in this turn
    public Map<String, Integer> killZombieInThisTurn() {
        Map<String, Integer> coord = new HashMap<>();
        Zombie zombieToKill = killZombie(1);
        coord.put("x", zombieToKill.getX());
        coord.put("y", zombieToKill.getY());
        return coord;
    }

    public Map<String, Integer> killzombieInNextTurn() {
        Map<String, Integer> coord = new HashMap<>();
        Zombie zombieToKill = killZombie(2);
        coord.put("x", zombieToKill.getX());
        coord.put("y", zombieToKill.getY());
        return coord;
    }


    public Map<String, Integer> moveToClosestHuman() {

        HashMap<String, Integer> target = new HashMap<>();
       List<Agent> agents = new ArrayList<>();
       agents.addAll(getHumanlist().values());
       Human human = (Human) closestToAsh(agents);
       target.put("x", human.getX());
       target.put("y", human.getY());

       return target;
    }

}
