package com.example.oop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        SnakeAndLaddersApp game = new SnakeAndLaddersApp();
        Scene scene = new Scene(game.createBoard(), 600, 600);
        stage.setTitle("Snake and Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
