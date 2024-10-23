package com.example.miniproyecto2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class representing the winner stage of the "SUDOKU" application.
 * This class is responsible for initializing and displaying the winner view.
 *
 * @author Jerson Alexis Ortiz Velasco
 * @author Jhon Antony Murillo Olave
 * @version 1.0
 * @since 1.0
 */

public class WinnerStage extends Stage {

    /**
     * Constructs a new WelcomeStage and initializes its components.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public WinnerStage() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto2/winner-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);

        setTitle("SUDOKU");
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/miniproyecto2/favicon.png"))));
        setResizable(false);

        show();

    }
    /**
     * Returns the singleton instance of WinnerStage.
     *
     * @return the singleton instance of WinnerStage.
     * @throws IOException if the instance cannot be created.
     * @see WinnerStageHolder
     */
    public static WinnerStage getInstance() throws IOException {

        WinnerStageHolder.INSTANCE = WinnerStageHolder.INSTANCE != null ? WinnerStageHolder.INSTANCE : new WinnerStage();
        return WinnerStageHolder.INSTANCE;

    }

    /**
     * Deletes the singleton instance of WinnerStage.
     */
    public static void deletedInstance() {

        WinnerStageHolder.INSTANCE.close();

    }

    /**
     * Holds the singleton instance of WinnerStage.
     *
     * @since 1.0
     */
    private static class WinnerStageHolder {
        private static WinnerStage INSTANCE;

    }

}

