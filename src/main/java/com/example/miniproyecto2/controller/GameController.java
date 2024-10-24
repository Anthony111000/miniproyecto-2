package com.example.miniproyecto2.controller;

import com.example.miniproyecto2.view.GameStage;
import com.example.miniproyecto2.view.WinnerStage;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import com.example.miniproyecto2.model.Game;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

/**
 * Class representing the controller for the Sudoku game. This class handles the
 * game logic, user interactions, and updating the game state in the user interface.
 * It manages the Sudoku board, user inputs, and helps the player with hints.
 *
 * @author Jerson Alexis Ortiz Velasco
 * @author Jhon Antony Murillo Olave
 * @version 1.0
 * @since 1.0
 */
public class GameController {
    /**
     * The game instance that contains the game logic.
     *
     * @serial
     * @since 1.0
     */
    private Game game;

    /**
     * Counter to keep track of help requests.
     * @serial
     * @since 1.0
     */
    private int counterHelp;

    /**
     * GridPane that represents the game board in the user interface.
     * @serialField
     * @since 1.0
     */
    @FXML
    private GridPane board;

    /**
     * Button in the user interface that allows the user to request help.
     * @serialField
     * @since 1.0
     */
    @FXML
    private Button buttonHelp;

    /**
     * Label displaying the current game status in the user interface.
     * @serialField
     * @since 1.0
     */
    @FXML
    private Label statusLabel;

    /**
     * Initializes the game by setting up the Sudoku board and other components.
     *
     * @since 1.0
     */
    public void initialize() {
        counterHelp = 0;
        game = new Game();
        if (board == null) {
            return;
        }
        createAndAssignTextFieldsToBoard(game.generateSudoku6x6());

       // printSudokuBoard(game.getcurrentSudoku6x6()); //option to print a solution of the generated sudoku by consul.

        game.currentSudoku6x6();
        setTextFieldInputHandler();
        Tooltip tooltip = new Tooltip("Oprimir para obtener una sugerencia");
        buttonHelp.setTooltip(tooltip);
    }

    /**
     * Called when the help button is pressed. Changes the button's style to indicate it is pressed.
     *
     * @since 1.0
     */
    @FXML
    private void pressed() {
        buttonHelp.setStyle("-fx-background-color: #87CEEB;");
    }

    /**
     * Called when the help button is released. Changes the button's style back to the default.
     *
     * @since 1.0
     */
    @FXML
    private void released() {
        buttonHelp.setStyle("-fx-background-color: #000080;");
    }

    /**
     * Handles the action when the help button is clicked. Provides hints to the player
     * up to a maximum number of attempts.
     *
     * @since 1.0
     */
    @FXML
    private void handleButtonClick() {
        if (counterHelp++ < 6) {
            help();
            winner();
        } else {
            statusLabel.setText("No quedan más intentos");
        }
    }

    /**
     * Sets an input handler for each text field to check for win conditions upon text change.
     *
     * @since 1.0
     */
    public void setTextFieldInputHandler() {
        for (Node node : board.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    winner();
                });
            }
        }
    }

    /**
     * Checks if the player has won the game by validating the current board state.
     *
     * @since 1.0
     */

    public void winner() {
        ArrayList<ArrayList<Integer>> currentBoard = game.getcurrentSudoku6x6();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                int number = currentBoard.get(row).get(col);

                if (number == 0) {
                    return; // Still empty cells
                }
                if (!game.isValidPlacement(currentBoard, row, col, number)) {
                    return; // Invalid placement found
                }
            }
        }

        windowChange(); // Trigger the winner stage
    }

    /**
     * Changes the current window to the winner stage.
     *
     * @since 1.0
     */
    public void windowChange() {
        GameStage.deletedInstance();
        try {
            WinnerStage.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Prints the current Sudoku board to the console for debugging purposes.
     *
     * @param sudokuBoard the Sudoku board to print.
     * @since 1.0
     */
    public static void printSudokuBoard(ArrayList<ArrayList<Integer>> sudokuBoard) {
        for (ArrayList<Integer> row : sudokuBoard) {
            for (Integer value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    /**
     * Provides a hint by filling an empty cell with a valid number.
     *
     * @since 1.0
     */
    private void help() {
        Random random = new Random();

        for (int i = 0; i < 36; i++) {
            int row = random.nextInt(6);
            int col = random.nextInt(6);

            TextField textField = (TextField) getNodeFromGridPane(board, col, row);

            if (textField != null && textField.getText().isEmpty()) {
                for (int number = 1; number <= 6; number++) {
                    if (game.isValidPlacement(game.getcurrentSudoku6x6(), row, col, number)) {
                        textField.setText(String.valueOf(number));
                        textField.setStyle("-fx-border-color: green; -fx-border-width: 2px;-fx-font-size: 30px; -fx-font-weight: bold;");
                        return;
                    }
                }
            }
        }
    }

    /**
     * Creates and assigns TextField components to the Sudoku board based on the generated Sudoku values.
     *
     * @param sudokuBoard the Sudoku board containing the initial values.
     * @since 1.0
     */
    private void createAndAssignTextFieldsToBoard(ArrayList<ArrayList<Integer>> sudokuBoard) {
        board.getChildren().clear();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField textField = new TextField();
                textField.setPrefWidth(80);
                textField.setPrefHeight(80);
                textField.setAlignment(Pos.CENTER);
                textField.setText("");
                textField.setEditable(true);
                textField.setTextFormatter(createTextFormatter(row, col));

                board.add(textField, col, row);
            }
        }

        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 2; blockCol++) {
                int startRow = blockRow * 2;
                int startCol = blockCol * 3;

                int[] positions = getRandomPositionsInBlock();

                for (int i = 0; i < 2; i++) {
                    int row = positions[i] / 3 + startRow;
                    int col = positions[i] % 3 + startCol;

                    int value = sudokuBoard.get(row).get(col);

                    TextField textField = (TextField) getNodeFromGridPane(board, col, row);
                    textField.setText(String.valueOf(value));
                    textField.setEditable(false);
                }
            }
        }
    }

    /**
     * Creates a text formatter for the input fields to restrict input to valid Sudoku numbers (1-6).
     *
     * @param row the row index of the text field.
     * @param col the column index of the text field.
     * @return a TextFormatter that controls input validation.
     * @since 1.0
     */
    private TextFormatter<String> createTextFormatter(int row, int col) {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            change.getControl().setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

            if (newText.matches("[1-6]") || newText.isEmpty()) {
                int value = newText.isEmpty() ? 0 : Integer.parseInt(newText);
                boolean isUpdated = game.updateCurrentBoard(row, col, value);



                if (!isUpdated && value != 0) {
                    change.getControl().setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-font-size: 30px; -fx-font-weight: bold;");
                    statusLabel.setText("¡¡¡Número inválido!!!");
                } else {
                    statusLabel.setText("");

                }
                return change;
            }
            return null;
        });
    }

    /**
     * Gets random positions within a 2x3 block for initial placement of numbers.
     *
     *
     * @return an array containing two random positions in the specified block.
     * @since 1.0
     */
    public int[] getRandomPositionsInBlock() {
        Random rand = new Random();

        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                positions.add(i * 3 + j);
            }
        }

        int pos1 = positions.remove(rand.nextInt(positions.size()));
        int pos2 = positions.remove(rand.nextInt(positions.size()));

        return new int[]{pos1, pos2};
    }


    /**
     * Retrieves a node from the GridPane at the specified column and row.
     *
     * @param gridPane the GridPane from which to retrieve the node.
     * @param col      the column index of the node.
     * @param row      the row index of the node.
     * @return the node at the specified position, or null if not found.
     * @since 1.0
     */

    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }


}
