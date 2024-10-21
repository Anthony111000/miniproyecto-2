package com.example.miniproyecto2.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class Game implements IGame {

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
        // Verificar si el número ya está en la fila
        for (int i = 0; i < 6; i++) {
            if (i != col && board.get(row).get(i) == number) {
                return false; // Número ya está en la fila
            }
        }

        // Verificar si el número ya está en la columna
        for (int i = 0; i < 6; i++) {
            if (i != row && board.get(i).get(col) == number) {
                return false; // Número ya está en la columna
            }
        }

        // Verificar si el número ya está en el bloque de 2x3
        int regionRowStart = (row / 2) * 2;
        int regionColStart = (col / 3) * 3;

        for (int i = regionRowStart; i < regionRowStart + 2; i++) {
            for (int j = regionColStart; j < regionColStart + 3; j++) {
                if ((i != row || j != col) && board.get(i).get(j) == number) {
                    return false; // Número ya está en el bloque 2x3
                }
            }
        }

        return true; // Número válido
    }

}
