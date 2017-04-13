package game;

import agent.Human;
import agent.Zombie;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by shubhimittal on 4/12/17.
 */


public class SceneAPI extends Scene {

    private List<Human> humansToBeKilled;
    private List<Zombie> zombiesToBeKilled;

    SceneAPI(String filepath) {
        super(filepath);
        this.humansToBeKilled = getHumansToBeKilled();
        this.zombiesToBeKilled = getZombiesToKill();
    }



    private Human closestHumanToAsh() {


    }

    private List<Human> orderByDistance() {

        return new LinkedList<>();

    }

    // RETURNS: A list of humans that will be killed by surrounding zombies
    // with a 100% probability.
    private List<Human> getHumansToBeKilled() {
        List<Human> humansToBeKilled = new LinkedList<>();

        // Iterate over all the humans in the game
        for(Map.Entry<Integer, Human> humanEntry: getHumanlist().entrySet()) {
            Human human = humanEntry.getValue();

            // Iterate over all the zombies in the game with their predicted location
            // in the next step in the game.
            for(Map.Entry<Integer, Zombie> zombieEntry : getZombieNextlist().entrySet()) {

                Zombie zombie = zombieEntry.getValue();

                // calculates the distance between human and this zombie in the next step
                // in the game.
                double humanZombieDistance = distance(
                        human.getX(), human.getY(), zombie.getX(), zombie.getY());

                // if the distance between human and zombie is within 400 units
                // then this human will be killed by this zombie in the next step
                if(humanZombieDistance < 400) {
                    humansToBeKilled.add(human);
                }
            }
        }
        return humansToBeKilled;

    }


    // RETURNS: true if there exists human that can be killed.
    public boolean canHumanBeKilled() {
        return humansToBeKilled.size() > 0;
    }

    // RETURNS: true if there exists a human that can be saved by Ash in the next step
    public boolean canAshSaveHuman() {

       int ashX = ash.getX();
       int ashY = ash.getY();

       boolean ashCanSave = false;

       for(Human human: humansToBeKilled) {

           // calculate distance between human and the ash
           // A zombie is going to replace this human in next step

           double humanAshDistance = distance(human.getX(), human.getY(), ashX, ashY);

           // ash will also travel 1000 unite towards target
           // assuming that target is at this human's place then
           // total distance between ash and the human should be less than 3000
           ashCanSave = humanAshDistance < 3000;

           if(ashCanSave) {
               break;
           }

       }
        return ashCanSave;
    }



    public Human closestHuman() {
        int ashX = ash.getX();
        int ashY = ash.getY();

        double minDistance = Double.MAX_VALUE;
        Human minHuman = null;

        for(Human h: humansToBeKilled) {
            double humanAshDistance = distance(h.getX(), h.getY(), ashX, ashY);

            if(humanAshDistance < minDistance) {

                minDistance = humanAshDistance;
                minHuman = h;
            }
        }

        return minHuman;
    }


    private List<Zombie> getZombiesToKill() {


        List<Zombie> zombies = new LinkedList<>();

        for (Map.Entry<Integer, Zombie> zombieEntry : getZombieNextlist().entrySet()) {

            Zombie zombie = zombieEntry.getValue();

            double ashZombieDistance = distance(ash.getX(), ash.getY(), zombie.getX(), zombie.getY());

            if (ashZombieDistance < 3000) {
                zombies.add(zombie);
            }
        }

        return zombies;
    }


    public boolean canAshKillZombies() {
        return zombiesToBeKilled.size() > 0;
    }


    public Zombie closestZombie() {
        int ashX = ash.getX();
        int ashY = ash.getY();

        double minDistance = Double.MAX_VALUE;
        Zombie minZombie = null;

        for(Zombie z : zombiesToBeKilled) {
            double zombieAshDistance = distance(z.getX(), z.getY(), ashX, ashY);

            if(zombieAshDistance < minDistance) {

                minDistance = zombieAshDistance;
                minZombie = z;
            }
        }
        return minZombie;
    }


}
