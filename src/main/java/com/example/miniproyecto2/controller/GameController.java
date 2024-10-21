package com.example.miniproyecto2.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import com.example.miniproyecto2.model.Game;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

import java.util.Random;
import java.util.ArrayList;

public class GameController {

    private Game game;

    @FXML
    private GridPane board;
    @FXML
    private GridPane grid00;
    @FXML
    private GridPane grid01;
    @FXML
    private GridPane grid20;
    @FXML
    private GridPane grid10;
    @FXML
    private GridPane grid11;
    @FXML
    private GridPane grid21;
    @FXML
    private Button buttonHelp;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleButtonClick() {
        System.out.println("El botón Ayuda fue presionado");
    }

    public void initialize() {
        game = new Game();
        Random rand = new Random();

        // Limpiar el mensaje del statusLabel al inicio
        statusLabel.setText(""); // Asegúrate de que no haya mensaje al inicio

        ArrayList<ArrayList<Integer>> sudokuBoard = game.generateSudoku6x6();
        printSudokuBoard(sudokuBoard);
        createAndAssignTextFieldsToBoard(sudokuBoard);
    }

    // Método para crear los TextFields y asignarles valores en el GridPane 'board'
    private void createAndAssignTextFieldsToBoard(ArrayList<ArrayList<Integer>> sudokuBoard) {
        // Limpiar el GridPane 'board' antes de agregar nuevos TextFields
        board.getChildren().clear();

        // Recorrer el tablero de 6x6 para crear los TextFields
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                // Crear un nuevo TextField
                TextField textField = new TextField();

                // Asignar propiedades al TextField
                textField.setPrefWidth(80);
                textField.setPrefHeight(80);
                textField.setAlignment(Pos.CENTER);
                textField.setText("");
                textField.setEditable(true);

                // Configurar el TextFormatter para aceptar solo números del 1 al 6
                textField.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-6]") || newText.isEmpty()) {
                        return change;
                    }
                    return null;
                }));

                // Mantener las variables row y col como efectivamente finales
                final int currentRow = row;
                final int currentCol = col;

                // Añadir un listener para validar la entrada cuando cambie el valor
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.isEmpty()) {
                        int number = Integer.parseInt(newValue);
                        sudokuBoard.get(currentRow).set(currentCol, number);

                        // Validar la celda actual
                        if (!game.isValidPlacement(sudokuBoard, currentRow, currentCol, number)) {
                            // Resaltar la celda en rojo si la colocación es inválida
                            textField.setStyle("-fx-border-color: red;");
                            statusLabel.setText("NÚMERO EQUIVOCADO");
                            statusLabel.setStyle("-fx-text-fill: red;");
                        } else {
                            // Restablecer el estilo si la colocación es válida
                            textField.setStyle("-fx-border-color: black;");
                            statusLabel.setText("");
                        }
                    } else {
                        // Si el campo está vacío, restablecer el valor del tablero a 0
                        sudokuBoard.get(currentRow).set(currentCol, 0);
                        textField.setStyle("-fx-border-color: black;");
                        statusLabel.setText("");
                    }

                    // Verificación del estado del tablero
                    if (isBoardComplete(sudokuBoard)) {
                        boolean allValid = true; // Para verificar si todos los números son válidos

                        for (int r = 0; r < 6; r++) {
                            for (int c = 0; c < 6; c++) {
                                int cellValue = sudokuBoard.get(r).get(c);
                                // Verificar si cada número es válido
                                if (cellValue != 0 && !game.isValidPlacement(sudokuBoard, r, c, cellValue)) {
                                    allValid = false;
                                    break; // Salir del bucle si se encuentra un número inválido
                                }
                            }
                        }

                        // Mostrar mensaje de victoria solo si todos los números son válidos
                        if (allValid) {
                            statusLabel.setText("GANASTE!!!");
                            statusLabel.setStyle("-fx-text-fill: green;");  // Cambiar color del texto a verde
                        }
                    }
                });

                // Agregar el TextField al GridPane en la posición correspondiente
                board.add(textField, col, row);
            }
        }

        // Asignar valores iniciales a algunos TextFields...
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

    // Método para verificar si el tablero está completo
    private boolean isBoardComplete(ArrayList<ArrayList<Integer>> sudokuBoard) {
        for (ArrayList<Integer> row : sudokuBoard) {
            for (Integer cell : row) {
                if (cell == 0) { // Si hay alguna celda vacía
                    return false;
                }
            }
        }
        return true;
    }

    // Método para obtener dos posiciones aleatorias dentro de un bloque de 3x2
    private int[] getRandomPositionsInBlock(int startRow, int startCol) {
        Random rand = new Random();
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                positions.add(i * 3 + j);
            }
        }

        // Seleccionar dos posiciones aleatorias diferentes
        int pos1 = positions.remove(rand.nextInt(positions.size()));
        int pos2 = positions.remove(rand.nextInt(positions.size()));

        return new int[] { pos1, pos2 };
    }

    // Método auxiliar para obtener el TextField de un GridPane en una posición específica
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public static void printSudokuBoard(ArrayList<ArrayList<Integer>> sudokuBoard) {
        for (ArrayList<Integer> row : sudokuBoard) {
            for (Integer value : row) {
                System.out.print(value + " "); // Imprime cada valor en la fila
            }
            System.out.println(); // Salta a la siguiente línea después de imprimir una fila
        }
    }

    @FXML
    private void pressed() {
        buttonHelp.setStyle("-fx-background-color: #87CEEB;");
    }

    @FXML
    private void released() {
        buttonHelp.setStyle("-fx-background-color: #000080;");
    }
}
