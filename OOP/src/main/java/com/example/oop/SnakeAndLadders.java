package com.example.oop;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;  // Import Font for setting text font size

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SnakeAndLadders {

    private static final int BOARD_SIZE = 10;
    private static final int TILE_SIZE = 60;
    private int playerPosition = 1;
    private Circle playerToken = new Circle(TILE_SIZE / 4, Color.RED);
    private Map<Integer, Integer> snakesAndLadders = new HashMap<>();
    private Text message = new Text("Roll the dice to start!");
    private Text rolledNumberText = new Text("Rolled Number: 0"); // Text to display rolled number

    public SnakeAndLadders() {
        // Define positions of snakes and ladders (from, to)
        snakesAndLadders.put(16, 6);  // Snake from 16 to 6
        snakesAndLadders.put(47, 26); // Snake from 47 to 26
        snakesAndLadders.put(49, 11); // Snake from 49 to 11
        snakesAndLadders.put(56, 53); // Snake from 56 to 53
        snakesAndLadders.put(62, 19); // Snake from 62 to 19
        snakesAndLadders.put(64, 60); // Ladder from 64 to 60
        snakesAndLadders.put(87, 24); // Snake from 87 to 24
        snakesAndLadders.put(93, 73); // Snake from 93 to 73
        snakesAndLadders.put(95, 75); // Snake from 95 to 75
        snakesAndLadders.put(98, 78); // Snake from 98 to 78
        snakesAndLadders.put(1, 38);  // Ladder from 1 to 38
        snakesAndLadders.put(4, 14);  // Ladder from 4 to 14
        snakesAndLadders.put(9, 31);  // Ladder from 9 to 31
        snakesAndLadders.put(21, 42); // Ladder from 21 to 42
        snakesAndLadders.put(28, 84); // Ladder from 28 to 84
        snakesAndLadders.put(36, 44); // Ladder from 36 to 44
        snakesAndLadders.put(51, 67); // Ladder from 51 to 67
        snakesAndLadders.put(71, 91); // Ladder from 71 to 91
        snakesAndLadders.put(80, 100); // Ladder from 80 to 100
    }

    public VBox createBoard() {
        VBox root = new VBox();
        GridPane gridPane = new GridPane();

        // Create the board (tiles)
        int number = 100;
        boolean leftToRight = true;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK);

                // Add tile numbers
                Text numberText = new Text(String.valueOf(number));
                gridPane.add(tile, leftToRight ? col : (BOARD_SIZE - 1 - col), row);
                gridPane.add(numberText, leftToRight ? col : (BOARD_SIZE - 1 - col), row);

                number--;
            }
            leftToRight = !leftToRight; // Switch row direction
        }

        // Add player token
        gridPane.add(playerToken, 0, BOARD_SIZE - 1);

        // Roll Dice Button
        Button rollDiceButton = new Button("Roll Dice");
        rollDiceButton.setOnAction(e -> rollDice());

        // Set text properties for visibility
        rolledNumberText.setFont(new Font(20)); // Increase font size
        rolledNumberText.setFill(Color.BLUE);    // Change text color to blue

        // Add components to the root
        root.getChildren().addAll(gridPane, rollDiceButton, rolledNumberText, message);
        return root;
    }

    private void rollDice() {
        Random random = new Random();
        int diceRoll = random.nextInt(6) + 1; // Simulate dice roll (1-6)
        rolledNumberText.setText("Rolled Number: " + diceRoll); // Update rolled number display
        message.setText("You rolled a " + diceRoll);
        movePlayer(diceRoll);
    }

    private void movePlayer(int diceRoll) {
        playerPosition += diceRoll;

        // Check if the player reaches or exceeds 100
        if (playerPosition >= 100) {
            playerPosition = 100;
            message.setText("You win!");
        }

        // Check for snakes or ladders
        if (snakesAndLadders.containsKey(playerPosition)) {
            playerPosition = snakesAndLadders.get(playerPosition);
            message.setText("You hit a " + (playerPosition > 100 ? "ladder!" : "snake!"));
        }

        updatePlayerPosition();
    }

    private void updatePlayerPosition() {
        int row = (playerPosition - 1) / BOARD_SIZE;
        int col = (playerPosition - 1) % BOARD_SIZE;

        // For odd rows, reverse the direction
        if (row % 2 == 1) {
            col = BOARD_SIZE - 1 - col;
        }

        GridPane.setRowIndex(playerToken, BOARD_SIZE - 1 - row);
        GridPane.setColumnIndex(playerToken, col);
    }
}
