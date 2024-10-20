package com.example.miniproyecto2.view;

import com.example.miniproyecto2.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Represents the main stage of the game "elSolEclipsado".
 * This class is responsible for initializing and displaying the game window.
 *
 * @author Jerson Alexis Otiz Velasco
 * @version 1.0
 * @since 1.0
 */
public class GameStage extends Stage {
    /**
     * Controls the game logic and interactions.
     * @serial
     * @since 1.0
     */
    private GameController gameController;

    /**
     * Constructs a new GameStage and initializes the game window.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public GameStage() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto2/game-view.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root,605,676);
        setScene(scene);

        setTitle("Sol eclipsado");
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/miniproyecto2/favicon.png"))));
        setResizable(false);

        show();

    }

    /**
     * Returns the singleton instance of GameStage.
     *
     * @return the singleton instance of GameStage.
     * @throws IOException if the instance cannot be created.
     * @see GameStageHolder
     */
    public static GameStage getInstance() throws IOException {

        GameStageHolder.INSTANCE = GameStageHolder.INSTANCE != null ? GameStageHolder.INSTANCE : new GameStage();
        return GameStageHolder.INSTANCE;

    }

    /**
     * Deletes the singleton instance of GameStage.
     */
    public static void deletedInstance() {

        GameStageHolder.INSTANCE.close();

    }

    /**
     * Returns the GameController associated with this stage.
     *
     * @return the GameController associated with this stage.
     */
    public GameController getGameController() {

        return gameController;

    }

    /**
     * Holds the singleton instance of GameStage.
     *
     * @since 1.0
     */
    private static class GameStageHolder {

        private static GameStage INSTANCE;

    }

}