// SnakeAndLaddersApp.java
package com.example.oop;

import javafx.application.Application; // Inheritance: Application class is inherited
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SnakeAndLaddersApp extends Application { // Inheritance: SnakeAndLaddersApp extends Application

    // Constants for board size and tile size
    static final int BOARD_SIZE = 10;
    static final int TILE_SIZE = 60;

    private int player1Position = 1;  // Player 1's starting position
    private int player2Position = 1;  // Player 2's starting position
    private Circle player1Token = new Circle(TILE_SIZE / 6, Color.RED);  // Player 1's token
    private Circle player2Token = new Circle(TILE_SIZE / 6, Color.BLUE); // Player 2's token
    private boolean isPlayer1Turn = true; // Flag to track whose turn it is

    private Map<Integer, Integer> snakesAndLadders = new HashMap<>(); // Map for snakes and ladders
    private Text message = new Text("Player 1, roll the dice to start!"); // Message display
    private Text rolledNumberText = new Text("Rolled Number: 0"); // Display rolled number

    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) { // Overriding: start method from Application
        VBox board = createBoard(); // Create the game board

        Scene scene = new Scene(board, BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE + 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake and Ladders Game");
        primaryStage.show();
    }

    public SnakeAndLaddersApp() {
        // Set up the snakes and ladders map
        snakesAndLadders.put(16, 6);  // Snake from 16 to 6
        snakesAndLadders.put(46, 25); // Snake from 46 to 25
        snakesAndLadders.put(49, 11); // Snake from 49 to 11
        snakesAndLadders.put(62, 19); // Snake from 62 to 19
        snakesAndLadders.put(64, 60); // Snake from 64 to 60
        snakesAndLadders.put(74, 53); // Snake from 74 to 53
        snakesAndLadders.put(89, 68); // Snake from 89 to 68
        snakesAndLadders.put(92, 88); // Snake from 92 to 88
        snakesAndLadders.put(95, 75); // Snake from 95 to 75
        snakesAndLadders.put(99, 80); // Snake from 99 to 80
        snakesAndLadders.put(2, 38);  // Ladder from 2 to 38
        snakesAndLadders.put(7, 14);  // Ladder from 7 to 14
        snakesAndLadders.put(8, 31);  // Ladder from 8 to 31
        snakesAndLadders.put(15, 26); // Ladder from 15 to 26
        snakesAndLadders.put(21, 42); // Ladder from 21 to 42
        snakesAndLadders.put(28, 84); // Ladder from 28 to 84
        snakesAndLadders.put(36, 44); // Ladder from 36 to 44
        snakesAndLadders.put(51, 67); // Ladder from 51 to 67
        snakesAndLadders.put(71, 91); // Ladder from 71 to 91
        snakesAndLadders.put(78, 98); // Ladder from 78 to 98
        snakesAndLadders.put(87, 94); // Ladder from 87 to 94
    }

    public VBox createBoard() {
        VBox root = new VBox();
        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();

        // Load the background image
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/board.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
        );

        // Set the background of the stack pane
        stackPane.setBackground(new Background(bgImage));

        boolean leftToRight = true;

        // Create the grid of tiles without numbers
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(Color.TRANSPARENT);  // Make tiles transparent to show the background
                tile.setStroke(Color.ORANGERED);
                gridPane.add(tile, leftToRight ? col : (BOARD_SIZE - 1 - col), row);
            }
            leftToRight = !leftToRight;
        }

        gridPane.add(player1Token, 0, BOARD_SIZE - 1);
        gridPane.add(player2Token, 0, BOARD_SIZE - 1);

        // Create roll dice button
        Button rollDiceButton = new Button("Roll Dice");
        rollDiceButton.setOnAction(e -> rollDice());

        // Create reset button
        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(e -> resetGame());

        rolledNumberText.setFont(new Font(20));
        rolledNumberText.setFill(Color.BLUE);

        // Add the grid pane to the stack pane
        stackPane.getChildren().add(gridPane); // Add grid pane on top of the background

        // Add all elements to the root layout
        root.getChildren().addAll(stackPane, rollDiceButton, resetButton, rolledNumberText, message);
        return root;
    }

    private void rollDice() {
        Random random = new Random();
        int diceRoll = random.nextInt(6) + 1; // Roll a dice
        rolledNumberText.setText("Rolled Number: " + diceRoll);
        if (isPlayer1Turn) {
            message.setText("Player 1 rolled a " + diceRoll);
            movePlayer1(diceRoll);
        } else {
            message.setText("Player 2 rolled a " + diceRoll);
            movePlayer2(diceRoll);
        }
        isPlayer1Turn = !isPlayer1Turn; // Switch turns
    }

    private void movePlayer1(int diceRoll) {
        player1Position += diceRoll; // Move Player 1
        if (player1Position >= 100) {
            player1Position = 100; // Ensure Player 1 does not go over 100
            message.setText("Player 1 wins!");
        }
        if (snakesAndLadders.containsKey(player1Position)) {
            int newPos = snakesAndLadders.get(player1Position);
            message.setText(newPos < player1Position ? "Player 1 hit a snake!" : "Player 1 hit a ladder!");
            player1Position = newPos; // Move Player 1 based on snakes/ladders
        }
        updatePlayerPosition(player1Token, player1Position);
    }

    private void movePlayer2(int diceRoll) {
        player2Position += diceRoll; // Move Player 2
        if (player2Position >= 100) {
            player2Position = 100; // Ensure Player 2 does not go over 100
            message.setText("Player 2 wins!");
        }
        if (snakesAndLadders.containsKey(player2Position)) {
            int newPos = snakesAndLadders.get(player2Position);
            message.setText(newPos < player2Position ? "Player 2 hit a snake!" : "Player 2 hit a ladder!");
            player2Position = newPos; // Move Player 2 based on snakes/ladders
        }
        updatePlayerPosition(player2Token, player2Position);
    }

    private void updatePlayerPosition(Circle playerToken, int playerPosition) {
        int row = (playerPosition - 1) / BOARD_SIZE;
        int col = (playerPosition - 1) % BOARD_SIZE;
        if (row % 2 == 1) {
            col = BOARD_SIZE - 1 - col; // Adjust column for snake and ladder movement
        }
        GridPane.setRowIndex(playerToken, BOARD_SIZE - 1 - row);
        GridPane.setColumnIndex(playerToken, col);
    }

    private void resetGame() {
        player1Position = 1; // Reset Player 1's position
        player2Position = 1; // Reset Player 2's position
        updatePlayerPosition(player1Token, player1Position); // Update UI for Player 1
        updatePlayerPosition(player2Token, player2Position); // Update UI for Player 2
        isPlayer1Turn = true; // Reset turn
        message.setText("Player 1, roll the dice to start!"); // Reset message
        rolledNumberText.setText("Rolled Number: 0"); // Reset rolled number display
    }
}
