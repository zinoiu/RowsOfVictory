# Rows of Victory

A Tic-Tac-Toe-inspired game made to build critical thinking, logic, and decision-making through play. Players look ahead, recognize patterns, and plan strategies. Built in Java with Swing.


## How to play

- Play with two players on one computer, or play against the computer. Enter your names when the game starts.
- Take turns placing marks on a 3x3 board. Three in a row, column, or diagonal wins, and the winning line is highlighted.
- The twist: each player keeps only their three most recent marks. Placing a fourth removes your oldest one, so you have to keep planning ahead.
- Wins are counted below the board. After each game you can play again or go back to the mode menu.

## Computer difficulty

- **Easy:** plays random moves.
- **Medium:** takes a winning move or blocks yours when it can, otherwise plays randomly.
- **Hard:** uses the minimax algorithm.

## Run it

1. Install a JDK (Java Development Kit).
2. Clone the repo and open the folder:

```
git clone https://github.com/zinoiu/RowsOfVictory.git
cd RowsOfVictory
```

3. Compile and start the game:

```
javac *.java
java GameLauncher
```

Keep `ROV.jpg` (the start screen background) in the same folder as the code.

## Files

- `GameLauncher.java`: start screen with a PLAY button.
- `RowsOfVictory.java`: the game window, rules, computer opponent, and scoring.

## My part

Group project for ITE 260 (Group 6). I worked on the documentation.

