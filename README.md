# Zombie Killer Game

This is a simulation of the online version of the Game which can be found at below mentioned link.
https://www.codingame.com/multiplayer/optimization/code-vs-zombies

## Game Overview

There is only one agent named **Ash**, who has a powerful weapon in this game. He needs to protect humans from being killed by Zombies and at the same time also kill all zombies to finish the game. **Ash, Zombies and Humans** are all placed in a closed grid/map. As part of the problem statement, the internal codes for the movement of zombies are hidden. The goal of Ash is to maximize game score by killing all zombies and saving maximum number of Humans.

Please refer to the docs for details about system architecture for Game Replica and UI.

## Game Rules

Game setup and rules influence scoring of Ash and adds to the complexity of the Game.

The game is played in a zone 16000 units wide by 9000 units high. You control a man named Ash, wielding a gun that lets him kill any zombie within a certain range around him.

**Ash works as follows**
1. Ash can be told to move to any point within the game zone by outputting a coordinate X Y. The top-left point is 0 0.
2. Each turn, Ash will move exactly 1000 units towards the target coordinate, or onto the target coordinates if he is less than 1000 units away.
3. If at the end of a turn, a zombie is within 2000 units of Ash, he will shoot that zombie and destroy it. More details on combat further down.
4. Ash could only see the surroundings (zombies, other people) within 3000 units

Other humans will be present in the game zone, but will not move. If zombies kill all of them, you lose the game and score 0 points for the current test case. Zombies are placed around the game zone at the start of the game, they must be destroyed to earn points.

**Zombies work as follows**
1. Each turn, every zombie will target the closest human, including Ash, and step 400 units towards them. If the zombie is less than 400 units away, the human is killed and the zombie moves onto their coordinate.
2. Two zombies may occupy the same coordinate.

**The order in which actions happens in between two rounds is**
1. Zombies move towards their targets.
2. Ash moves towards his target.
3. Any zombie within a 2000 unit range around Ash is destroyed.
4. Zombies eat any human they share coordinates with.

Killing zombies earns points. The number of points you get per zombie is subject to a few factors.

**Scoring works as follows**
1. A zombie is worth the number of humans still alive squared x10, not including Ash.
2. If several zombies are destroyed during on the same round, the nth zombie killed worth is multiplied by the (n+2) th number of the Fibonacci sequence (1, 2, 3, 5, 8, and so on). As a consequence, you should kill the maximum amount of zombies during a same turn.


## Ash
Ash's AI has been writen using three strategies
1. Simple strategy: Rule based (conditional statements)
2. Decision Tree
3. Monte Carlo Algorithm
4. MaxMin Algorithm

With MaxMin giving the best results consistently over other approaches. Please refer to the docs for implementation details and results.

## Files of Interest
```
src/game/Game.java - for code execution
src/ai - for Ash's AI
```
## Executing the Game 
```
Run with arguments 1 (default option), 2 or 3 to switch between different stages of AI for Ash
- src/game/Game.java
```

