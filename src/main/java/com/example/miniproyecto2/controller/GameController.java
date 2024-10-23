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


public class GameController {

    private Game game;
    private int counterHelp;


    @FXML
    private GridPane board;

    @FXML
    private Button buttonHelp;

    @FXML
    private Label statusLabel;

    @FXML
    private void pressed() {
        buttonHelp.setStyle("-fx-background-color: #87CEEB;");
    }

    @FXML
    private void released() {
        buttonHelp.setStyle("-fx-background-color: #000080;");
    }

    @FXML
    private void handleButtonClick() {

    if(counterHelp++<6) {
        help();

    } else {
        statusLabel.setText("No quedan más intentos");
    }

    }
    public void initialize() {
        counterHelp=0;
        game = new Game();
        Random rand = new Random();

        if (board == null) {
            return;
        }

        createAndAssignTextFieldsToBoard(game.generateSudoku6x6());

        //printSudokuBoard(game.getcurrentSudoku6x6());

        game.currentSudoku6x6();
        setTextFieldInputHandler();
        Tooltip tooltip = new Tooltip("Oprimir para obtener sugerencia");;
        buttonHelp.setTooltip(tooltip);



    }

    public static void printSudokuBoard(ArrayList<ArrayList<Integer>> sudokuBoard) {
        for (ArrayList<Integer> row : sudokuBoard) {
            for (Integer value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

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


    private void createAndAssignTextFieldsToBoard(ArrayList<ArrayList<Integer>> sudokuBoard) {

        board.getChildren().clear();

        Random rand = new Random();


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


                int[] positions = getRandomPositionsInBlock(startRow, startCol);

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

    private TextFormatter<String> createTextFormatter(int row, int col) {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            change.getControl().setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

            if (newText.matches("[1-6]") || newText.isEmpty()) {
                int value = newText.isEmpty() ? 0 : Integer.parseInt(newText);
                boolean isUpdated = game.updateCurrentBoard(row, col, value);

                for (Node node : board.getChildren()) {
                    if (node instanceof TextField) {
                        node.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;"); // Asegúrate de que todos mantengan el estilo
                    }
                }

                if (!isUpdated && value != 0) {
                    change.getControl().setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-font-size: 30px; -fx-font-weight: bold;");
                    statusLabel.setText("¡¡Numero invalido!!");
                } else {

                    statusLabel.setText("");
                }
                return change;
            }
            return null;
        });
    }

    public void winner() {
        ArrayList<ArrayList<Integer>> currentBoard = game.getcurrentSudoku6x6();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                int number = currentBoard.get(row).get(col);

                if (number == 0) {
                    return;
                }
                if (!game.isValidPlacement(currentBoard, row, col, number)) {
                    return;
                }
            }
        }

        windowChange();

    }
    public void windowChange() {
        GameStage.deletedInstance();
        try {
            WinnerStage.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] getRandomPositionsInBlock(int startRow, int startCol) {
        Random rand = new Random();

        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                positions.add(i * 3 + j);
            }
        }

        int pos1 = positions.remove(rand.nextInt(positions.size()));
        int pos2 = positions.remove(rand.nextInt(positions.size()));

        return new int[] { pos1, pos2 };
    }


    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void setTextFieldInputHandler() {
        for (Node node : board.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    winner();
                });
            }
        }
    }


}