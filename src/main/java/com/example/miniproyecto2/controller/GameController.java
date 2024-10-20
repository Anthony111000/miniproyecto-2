package com.example.miniproyecto2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.miniproyecto2.model.Game;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.awt.event.MouseEvent;
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
    private GridPane grid02;

    @FXML
    private GridPane grid10;

    @FXML
    private GridPane grid11;

    @FXML
    private GridPane grid12;
    @FXML
    private Button buttonHelp;

    @FXML
    private void handleButtonClick() {

    }
    public void initialize() {
        // Inicializar el juego y la generación de Sudoku

        Game game = new Game();
        // Generar el tablero de Sudoku
        ArrayList<ArrayList<Integer>> sudokuBoard = game.generateSudoku6x6();

        // Llamar al método para llenar los sub-GridPanes con los valores generados
        fillGridWithSudokuValues(sudokuBoard);
    }

    private void fillGridWithSudokuValues(ArrayList<ArrayList<Integer>> sudokuBoard) {
        // Llenar los sub-GridPanes con los valores del Sudoku
        fillSubGridWithValues(grid00, sudokuBoard, 0, 0);
        fillSubGridWithValues(grid01, sudokuBoard, 0, 2);
        fillSubGridWithValues(grid02, sudokuBoard, 0, 4);
        fillSubGridWithValues(grid10, sudokuBoard, 3, 0);
        fillSubGridWithValues(grid11, sudokuBoard, 3, 2);
        fillSubGridWithValues(grid12, sudokuBoard, 3, 4);
    }

    // Método para llenar cada sub-GridPane con los valores del Sudoku (3x2)
    private void fillSubGridWithValues(GridPane subGrid, ArrayList<ArrayList<Integer>> sudokuBoard, int startRow, int startCol) {
        // Llenar los TextFields dentro del subGrid (3x2)
        for (int row = 0; row < 2; row++) {  // Solo 2 filas en cada sub-grid
            for (int col = 0; col < 3; col++) {  // Solo 3 columnas en cada sub-grid
                // Asegúrate de que no te salga del límite de la matriz (6x6)
                if (startRow + row < 6 && startCol + col < 6) {
                    // Crear un nuevo TextField
                    TextField textField = new TextField();
                    textField.setMaxWidth(50);  // Ajustar el tamaño
                    textField.setMaxHeight(50);  // Ajustar el tamaño

                    // Asignar el valor del Sudoku al TextField
                    int value = sudokuBoard.get(startRow + row).get(startCol + col);
                    textField.setText(String.valueOf(value));  // Establecer el valor en el TextField

                    // Agregar el TextField al subGrid en la posición correspondiente
                    subGrid.add(textField, col, row);  // Agregar el TextField a la posición adecuada (columna, fila)
                }
            }
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
