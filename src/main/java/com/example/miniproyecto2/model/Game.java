package com.example.miniproyecto2.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class Game implements IGame {
    private ArrayList<ArrayList<Integer>> currentBoard;

    public Game() {
        currentSudoku6x6();
        // Inicializa 'board' con valores predeterminados
    }

    // Implementación de la interfaz
    @Override
    public ArrayList<ArrayList<Integer>> generateSudoku6x6() {
        ArrayList<ArrayList<Integer>> board = new ArrayList<>();

        // Inicializamos el tablero vacío (6x6)
        for (int i = 0; i < 6; i++) {
            board.add(new ArrayList<>(Collections.nCopies(6, 0)));
        }

        // Llenamos el tablero aplicando las reglas del Sudoku
        if (fillBoard(board)) {
            return board;
        } else {
            System.out.println("No se pudo generar un Sudoku válido.");
            return null;
        }
    }

    @Override
    public ArrayList<ArrayList<Integer>> currentSudoku6x6() {
        if (currentBoard == null) {
            currentBoard = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                currentBoard.add(new ArrayList<>(Collections.nCopies(6, 0))); // Inicializa con ceros
            }
        }
        return currentBoard;
    }

    @Override
    public boolean updateCurrentBoard(int row, int col, int value) {
        if (row >= 0 && row < 6 && col >= 0 && col < 6) {
            // Usamos isValidPlacement para comprobar la validez del número
            if (isValidPlacement(currentBoard, row, col, value)) {
                currentBoard.get(row).set(col, value); // Actualiza el valor en currentBoard
                System.out.println("Número " + value + " agregado en la posición (" + row + ", " + col + ")");
                return true;
            } else {
                System.out.println("El número " + value + " no es válido en la posición (" + row + ", " + col + ")");
                return false;
            }
        }
        return false; // Retorna false si los índices están fuera de límites
    }

    @Override
    public boolean fillBoard(ArrayList<ArrayList<Integer>> board) {
        int[] validNumbers = {1, 2, 3, 4, 5, 6};  // Números válidos para el Sudoku 6x6
        Random random = new Random();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                // Si la celda está vacía, intentamos llenarla
                if (board.get(row).get(col) == 0) {
                    // Mezclamos los números para probar aleatoriamente
                    ArrayList<Integer> shuffledNumbers = new ArrayList<>();
                    for (int num : validNumbers) {
                        shuffledNumbers.add(num);
                    }
                    Collections.shuffle(shuffledNumbers, random);

                    for (int number : shuffledNumbers) {
                        if (isValidPlacement(board, row, col, number)) {
                            board.get(row).set(col, number);
                            if (fillBoard(board)) {
                                return true;
                            }
                            board.get(row).set(col, 0);  // Backtrack
                        }
                    }
                    return false;  // No se puede llenar correctamente
                }
            }
        }
        return true;  // Tablero lleno con éxito
    }

    @Override
    public boolean isValidPlacement(ArrayList<ArrayList<Integer>> board, int row, int col, int number) {
        // Verifica la fila, omitiendo la posición actual
        for (int i = 0; i < 6; i++) {
            if (i != col && board.get(row).get(i) == number) { // Excluye la columna actual
                return false;
            }
        }

        // Verifica la columna, omitiendo la posición actual
        for (int i = 0; i < 6; i++) {
            if (i != row && board.get(i).get(col) == number) { // Excluye la fila actual
                return false;
            }
        }

        // Verifica la subregión (bloque 2x3), omitiendo la posición actual
        int regionRowStart = (row / 2) * 2;
        int regionColStart = (col / 3) * 3;

        for (int i = regionRowStart; i < regionRowStart + 2; i++) {
            for (int j = regionColStart; j < regionColStart + 3; j++) {
                if ((i != row || j != col) && board.get(i).get(j) == number) { // Excluye la posición actual
                    return false;
                }
            }
        }

        return true;  // El número es válido en esa posición
    }

}