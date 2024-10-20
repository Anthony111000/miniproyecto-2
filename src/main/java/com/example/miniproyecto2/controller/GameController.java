package com.example.miniproyecto2.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import com.example.miniproyecto2.model.Game;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    private void handleButtonClick() {
        System.out.println("El botón Ayuda fue presionado");
        provideHint();
    }
    public void initialize() {


        game = new Game();
        Random rand = new Random();

        ArrayList<ArrayList<Integer>> sudokuBoard = game.generateSudoku6x6();
        printSudokuBoard(sudokuBoard);

        createTextFieldsInGrids();
        assignTwoValuesPerGrid(sudokuBoard);


    }



    public static void printSudokuBoard(ArrayList<ArrayList<Integer>> sudokuBoard) {
        for (ArrayList<Integer> row : sudokuBoard) {
            for (Integer value : row) {
                System.out.print(value + " "); // Imprime cada valor en la fila
            }
            System.out.println(); // Salta a la siguiente línea después de imprimir una fila
        }
    }

    private void createTextFieldsInGrids() {
        // Crear los TextFields en los diferentes GridPanes
        createTextFieldsForGrid(grid00, 0, 0);
        createTextFieldsForGrid(grid01, 0, 1);
        createTextFieldsForGrid(grid10, 2, 0);
        createTextFieldsForGrid(grid11, 2, 1);
        createTextFieldsForGrid(grid20, 4, 0);
        createTextFieldsForGrid(grid21, 4, 1);
    }

    // Método auxiliar para crear los TextFields en un GridPane
    private void createTextFieldsForGrid(GridPane grid, int startRow, int startCol) {
        for (int row = startRow; row < startRow + 2; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                TextField textField = new TextField();
                textField.setPrefWidth(80);  // Establecer el tamaño de los TextField
                textField.setPrefHeight(80); // Establecer el tamaño de los TextField
                textField.setAlignment(Pos.CENTER);
                textField.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

                // Filtrar las teclas para permitir solo 1-6 y un solo carácter
                textField.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
                    String character = event.getCharacter();
                    // Permitir solo los dígitos 1-6 y asegurar que no haya más de un carácter
                    if (!character.matches("[1-6]") || textField.getText().length() >= 1) {
                        event.consume(); // Si no es un número del 1 al 6 o ya hay un dígito, consumir el evento
                    }
                });

                grid.add(textField, col - startCol, row - startRow);  // Añadir TextField al GridPane
            }
        }
    }


    // Método para asignar solo dos valores a cada GridPane
    private void assignTwoValuesPerGrid(ArrayList<ArrayList<Integer>> sudokuBoard) {

        assignTwoValuesToGrid(grid00, sudokuBoard, 0, 0);
        assignTwoValuesToGrid(grid01, sudokuBoard, 0, 3);
        assignTwoValuesToGrid(grid10, sudokuBoard, 2, 0);
        assignTwoValuesToGrid(grid11, sudokuBoard, 2, 3);
        assignTwoValuesToGrid(grid20, sudokuBoard, 4, 0);
        assignTwoValuesToGrid(grid21, sudokuBoard, 4, 3);
    }

    // Método auxiliar para asignar dos valores a un GridPane
    private void assignTwoValuesToGrid(GridPane grid, ArrayList<ArrayList<Integer>> sudokuBoard, int startRow, int startCol) {
        Random rand = new Random();

        // Elegir dos posiciones aleatorias para asignar los valores
        int row1 = rand.nextInt(2) + startRow;  // Elige una fila aleatoria entre startRow y startRow + 1
        int col1 = rand.nextInt(3) + startCol;  // Elige una columna aleatoria entre startCol y startCol + 2

        int row2, col2;
        // Asegurarse de que la segunda posición sea diferente de la primera
        do {
            row2 = rand.nextInt(2) + startRow;
            col2 = rand.nextInt(3) + startCol;
        } while (row1 == row2 && col1 == col2);  // Si es la misma posición, se elige otra

        // Asignar los valores de sudokuBoard a las posiciones aleatorias
        int value1 = sudokuBoard.get(row1).get(col1);
        int value2 = sudokuBoard.get(row2).get(col2);

        // Obtener los TextField correspondientes a las posiciones aleatorias
        TextField textField1 = (TextField) getNodeFromGridPane(grid, col1 - startCol, row1 - startRow);
        TextField textField2 = (TextField) getNodeFromGridPane(grid, col2 - startCol, row2 - startRow);

        // Asignar los valores a los TextField
        textField1.setText(String.valueOf(value1));
        textField2.setText(String.valueOf(value2));

        // Bloquear los TextFields con valores asignados
        textField1.setEditable(false);
        textField2.setEditable(false);

        // Dejar los demás TextFields vacíos y editables
        for (int row = startRow; row < startRow + 2; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if ((row != row1 || col != col1) && (row != row2 || col != col2)) {
                    // Obtener el TextField y vaciarlo
                    TextField textField = (TextField) getNodeFromGridPane(grid, col - startCol, row - startRow);
                    textField.setText("");  // Dejar vacío el TextField
                    textField.setEditable(true);  // Dejar el TextField editable
                }
            }
        }
    }
    // Método auxiliar para obtener un nodo de un GridPane (TextField en este caso)
    private Node getNodeFromGridPane(GridPane grid, int col, int row) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;  // Si no se encuentra el nodo
    }

    @FXML
    private void pressed() {
        buttonHelp.setStyle("-fx-background-color: #87CEEB;");
    }

    @FXML
    private void released() {
        buttonHelp.setStyle("-fx-background-color: #000080;");
    }

    private void provideHint() {
        System.out.println("Buscando una celda vacía...");

        // Crear un arreglo con los GridPanes
        GridPane[] grids = {grid00, grid01, grid10, grid11, grid20, grid21};
        Random rand = new Random();

        // Barajar los GridPanes de manera aleatoria
        for (int i = 0; i < grids.length; i++) {
            int randomIndex = rand.nextInt(grids.length);
            // Intercambiar los GridPanes aleatoriamente
            GridPane temp = grids[i];
            grids[i] = grids[randomIndex];
            grids[randomIndex] = temp;
        }

        // Ahora recorrer cada GridPane con el orden aleatorio
        for (GridPane grid : grids) {
            ArrayList<Node> nodes = new ArrayList<>(grid.getChildren());

            // Barajar las celdas dentro de cada GridPane de manera aleatoria
            for (int i = 0; i < nodes.size(); i++) {
                int randomIndex = rand.nextInt(nodes.size());
                Node temp = nodes.get(i);
                nodes.set(i, nodes.get(randomIndex));
                nodes.set(randomIndex, temp);
            }

            // Recorrer las celdas barajadas dentro del GridPane
            for (Node node : nodes) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;

                    // Verificar si la celda está vacía
                    if (textField.getText().isEmpty()) {
                        // Proporcionamos una sugerencia para la celda vacía
                        textField.setText("0"); // Solo un ejemplo
                        textField.setEditable(false);  // Bloquear la celda
                        System.out.println("Sugerencia proporcionada en una celda vacía.");
                        return;  // Salir después de encontrar la primera celda vacía
                    }
                }
            }
        }

        System.out.println("No se encontraron celdas vacías.");
    }


}
