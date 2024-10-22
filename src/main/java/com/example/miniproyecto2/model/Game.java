package com.example.miniproyecto2.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class Game implements IGame {
    private ArrayList<ArrayList<Integer>> currentBoard;
  //  private ArrayList<ArrayList<Integer>> board;

    public Game() {
        currentSudoku6x6();
        // Inicializa 'board' con valores predeterminados
    }

    // Implementación de la interfaz
    @Override

    public ArrayList<ArrayList<Integer>> generateSudoku6x6() {
       //  board = new ArrayList<>();

        // Inicializamos el tablero vacío (6x6)
       /* for (int i = 0; i < 6; i++) {
            board.add(new ArrayList<>(Collections.nCopies(6, 0)));
        }*/

        // Llenamos el tablero aplicando las reglas del Sudoku
        if (fillBoard(currentBoard)) {
            return currentBoard;
        }
        return currentBoard;
    }

  /*  public ArrayList<ArrayList<Integer>> getGenerateSudoku6x6() {
        return currentBoard;
    }*/

    @Override
    public ArrayList<ArrayList<Integer>> currentSudoku6x6() {
        currentBoard = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            currentBoard.add(new ArrayList<>(Collections.nCopies(6, 0))); // Inicializa con ceros
        }

        return currentBoard;
    }

    public ArrayList<ArrayList<Integer>> getcurrentSudoku6x6() {
    return currentBoard;
    }
    @Override
    public boolean updateCurrentBoard(int row, int col, int value) {
        if (row >= 0 && row < 6 && col >= 0 && col < 6) {
            // Usamos isValidPlacement para comprobar la validez del número
            if (isValidPlacement(currentBoard, row, col, value)) {
                currentBoard.get(row).set(col, value); // Actualiza el valor en currentBoard
              //  System.out.println("Número " + value + " agregado en la posición (" + row + ", " + col + ")");
                return true;
            }
        }
        return false; // Retorna false si los índices están fuera de límites
    }

    @Override
    public boolean fillBoard(ArrayList<ArrayList<Integer>> board) {
        Random random = new Random();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                // Si la celda está vacía, intentamos llenarla
                if (board.get(row).get(col) == 0) {
                    // Crear y mezclar los números válidos (1-6) sin usar un arreglo predefinido
                    ArrayList<Integer> shuffledNumbers = new ArrayList<>();
                    for (int num = 1; num <= 6; num++) {
                        shuffledNumbers.add(num);  // Añadir números del 1 al 6
                    }
                    Collections.shuffle(shuffledNumbers, random);  // Mezclar aleatoriamente

                    // Probar los números aleatorios
                    for (int number : shuffledNumbers) {
                        if (isValidPlacement(board, row, col, number)) {
                            board.get(row).set(col, number);  // Colocar número
                            if (fillBoard(board)) {  // Recursión para llenar el resto del tablero
                                return true;
                            }
                            board.get(row).set(col, 0);  // Backtrack si no se puede llenar el tablero
                        }
                    }
                    return false;  // No se pudo llenar correctamente
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

    public boolean isValidPlacementWithFutureCheck(ArrayList<ArrayList<Integer>> board, int row, int col, int number) {
        // Primero, llama a isValidPlacement para las validaciones básicas
        if (!isValidPlacement(board, row, col, number)) {
            return false; // Si no es válido, retorna falso
        }

        // Coloca temporalmente el número en la posición
        board.get(row).set(col, number);

        // Verifica si hay espacios vacíos y si pueden ser llenados
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                if (board.get(r).get(c) == 0) { // Encuentra un espacio vacío
                    boolean canPlaceAnyNumber = false;
                    for (int num = 1; num <= 6; num++) {
                        // Verifica si se puede colocar algún número en el espacio vacío
                        if (isValidPlacement(board, r, c, num)) {
                            canPlaceAnyNumber = true;
                            break; // Se puede colocar al menos un número
                        }
                    }
                    // Si no se puede colocar ningún número, revertimos y retornamos falso
                    if (!canPlaceAnyNumber) {
                        board.get(row).set(col, 0); // Revertir el cambio
                        return false;
                    }
                }
            }
        }

        // Revertimos el cambio antes de salir
        board.get(row).set(col, 0); // Revertir el cambio
        return true; // Todos los espacios vacíos pueden ser llenados
    }
}