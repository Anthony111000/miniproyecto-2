package com.example.miniproyecto2.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import com.example.miniproyecto2.model.Game;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;
import java.util.ArrayList;


public class GameController {

    private Game game;

    @FXML
    private GridPane board;

    @FXML
    private Button buttonHelp;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleButtonClick() {
        System.out.println("El botón Ayuda fue presionado");


        //   provideHint();
      /*  for (int row = 0; row <= 5; row++) {
            Line line = new Line(0, row * 50, 300, row * 50);  // Línea horizontal
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);  // Grosor de la línea
            board.getChildren().add(line);
        }

// Añadir líneas verticales
        for (int col = 0    ; col <= 5; col++) {
            Line line = new Line(col * 50, 0, col * 50, 300);  // Línea vertical
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);  // Grosor de la línea
            board.getChildren().add(line);
        }*/

    }
    public void initialize() {

        game = new Game();
        Random rand = new Random();

        ArrayList<ArrayList<Integer>> sudokuBoard = game.generateSudoku6x6();

        printSudokuBoard(sudokuBoard);
        createAndAssignTextFieldsToBoard(sudokuBoard);

    }

    private void printCurrentBoard() {
        System.out.println("Estado actual de currentBoard desde GameController:");
        for (ArrayList<Integer> row : game.currentSudoku6x6()) {
            for (Integer value : row) {
                System.out.print(value + " ");
            }
            System.out.println(); // Nueva línea después de cada fila
        }
        System.out.println(); // Línea en blanco para mayor claridad
    }

    // Método para crear los TextFields y asignarles valores en el GridPane 'board'
    private void createAndAssignTextFieldsToBoard(ArrayList<ArrayList<Integer>> sudokuBoard) {
        // Limpiar el GridPane 'board' antes de agregar nuevos TextFields
        board.getChildren().clear();

        Random rand = new Random();

        // Recorrer el tablero de 6x6 para crear los TextFields
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                // Crear un nuevo TextField
                TextField textField = new TextField();

                // Asignar propiedades al TextField
                textField.setPrefWidth(80);  // Definir el ancho preferido del TextField
                textField.setPrefHeight(80); // Definir la altura preferida del TextField
                textField.setAlignment(Pos.CENTER);  // Centrar el texto dentro del TextField

                // Dejar vacío el TextField por defecto
                textField.setText("");

                // Hacer que el TextField sea editable si está vacío
                textField.setEditable(true);


                textField.setTextFormatter(createTextFormatter(row, col));
                // Agregar el TextField al GridPane en la posición correspondiente
                board.add(textField, col, row);
            }
        }

        // Recorrer cada bloque de 3x2 (2 filas por 3 columnas de bloques)
        for (int blockRow = 0; blockRow < 3; blockRow++) { // 3 bloques horizontales (filas)
            for (int blockCol = 0; blockCol < 2; blockCol++) { // 2 bloques verticales (columnas)

                // Obtener las posiciones del bloque actual
                int startRow = blockRow * 2;  // Cada bloque tiene 2 filas
                int startCol = blockCol * 3;  // Cada bloque tiene 3 columnas

                // Generar dos posiciones aleatorias dentro del bloque
                int[] positions = getRandomPositionsInBlock(startRow, startCol);

                // Asignar valores a las posiciones aleatorias dentro del bloque
                for (int i = 0; i < 2; i++) {
                    int row = positions[i] / 3 + startRow;  // Calculamos la fila
                    int col = positions[i] % 3 + startCol;  // Calculamos la columna

                    // Obtener el valor correspondiente del sudokuBoard
                    int value = sudokuBoard.get(row).get(col);

                    // Obtener el TextField y asignar el valor
                    TextField textField = (TextField) getNodeFromGridPane(board, col, row);
                    textField.setText(String.valueOf(value));

                    // Hacer que algunos TextFields sean no editables si tienen valor
                    textField.setEditable(false);
                }
            }
        }
    }
    private TextFormatter<String> createTextFormatter(int row, int col) {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Verifica si el nuevo texto es un número entre 1 y 6 o está vacío
            if (newText.matches("[1-6]") || newText.isEmpty()) {
                int value = newText.isEmpty() ? 0 : Integer.parseInt(newText); // Convertir el texto a número

                // Llama al método en la clase Game para actualizar currentBoard
                boolean isUpdated = game.updateCurrentBoard(row, col, value);
                printCurrentBoard();

                // Imprime la fila, columna y valor ingresado
                System.out.println("Fila: " + row + ", Columna: " + col + ", Valor ingresado: " + value);

                for (Node node : board.getChildren()) {
                    if (node instanceof TextField) {
                        node.setStyle(""); // O estilo por defecto
                    }
                }

                // Cambia el borde del TextField si el número es inválido
                if (!isUpdated && value !=0) {
                    // Aquí cambias el borde a rojo para indicar un valor no válido
                    change.getControl().setStyle("-fx-border-color: red;  -fx-border-width: 2px;");
                    statusLabel.setText("¡¡Numero invalido!!");
                } else {
                    // Resetea el estilo si es válido
                    change.getControl().setStyle(""); // O puedes usar el estilo por defecto
                    statusLabel.setText("");
                }

                return change; // Acepta el cambio
            }

            return null; // Rechaza cualquier valor que no esté en el rango 1-6
        });
    }



    // Método para obtener dos posiciones aleatorias dentro de un bloque de 3x2
    private int[] getRandomPositionsInBlock(int startRow, int startCol) {
        Random rand = new Random();
        // Crear un arreglo con todas las posiciones posibles dentro de un bloque 3x2
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {  // Dos filas en un bloque
            for (int j = 0; j < 3; j++) {  // Tres columnas en un bloque
                positions.add(i * 3 + j);  // Agregar la posición (indexada linealmente dentro del bloque)
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