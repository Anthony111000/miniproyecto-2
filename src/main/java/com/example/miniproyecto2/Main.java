package com.example.miniproyecto2;

import com.example.miniproyecto2.view.GameStage;
import com.example.miniproyecto2.view.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the "elSolEclipsado" application. This class extends {@link Application}
 * and serves as the entry point for the JavaFX application.
 *
 * @author Jerson Alexis Ortiz Velasco
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {

    /**
     * The main method of the for the "elSolEclipsado" application. This method is the entry point
     * when running the application from the command line or an IDE.
     *
     * @param args command-line arguments passed to the application
     * @since 1.0
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by setting up the primary stage.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if an error occurs during application startup
     * @since 1.0
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        GameStage.getInstance();

    }
}
