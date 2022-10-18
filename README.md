## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

---

# About the Project

**This is a project that simulated John Conway's [Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life), invented in 1970. This game demonstrated “finite automaton” where simple rules are used to create complex behavior.**

## `Game Details`

The game simulation is played on a board consisting of a square grid of “cells”. Each cell can be “alive” or “dead”. Small black circles represent living cells, and blank cells represent dead cells.

## `Game Rules`

A new generation of cells is created from the current generation as follows: 

The eight cells surrounding a given cell (vertically, horizontally, or diagonally) are its “neighbours”. 

The fate of any cell on the board is controlled by how many living neighbours it has, using two rules:
1. **Survival rule:** If a cell is alive, then it stays alive in the next generation if it has either 2 or 3 living neighbours. *(It dies of loneliness if it has 0 or 1, and dies of overcrowding if it has 4 or more.)*

2. **Birth rule:** If a dead (empty) cell has exactly 3 living neighbours, then it becomes a living cell in the next generation.

## `Playing the Game`

The game is played by rinning the LifeWindow.java file. It is a resizable window that provides an interface with self-explanatory instructions.

Each cell can be clicked to toggle it from dead to alive or vice versa.

Other actions can be performed using the keyboard. Pressing 1 to 5 gives one of five different sample configurations which should act as follows:

1.	This one should die out in exactly 8 generations. It is a very small one, but it will test all of the rules.

2.	This is a “glider gun”. Small “gliders” should be generated that move toward the top right, one every 30 generations.

3.	This is a set of 8 gliders that collide to form a glider gun.

4.	This is a “harvester”. The block of living cells will crawl along the line of cells, turning them into little 2x2 blocks (bales).

5.	This is the “r pentomino”. It is a simple group of 5 cells, but it generates very complex behaviour. On an infinitely large board, it will change for 1,103 generations before it settles into a stable or repeating pattern.

---
