package com.example.miniproyecto2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class representing a game of 6x6 Sudoku. This class extends the GameAdapter
 * and provides implementations for generating and managing the Sudoku board.
 *
 * @author Jerson Alexis Ortiz Velasco
 * @author Jhon Antony Murillo Olave
 * @version 1.0
 * @since 1.0
 */
public class Game extends GameAdapter {
    private ArrayList<ArrayList<Integer>> currentBoard;

    /**
     * Constructs a new Game instance and initializes the current Sudoku board.
     */
    public Game() {
        currentSudoku6x6();
    }

    /**
     * Generates a new 6x6 Sudoku board if possible.
     *
     * @return the current Sudoku board after generation.
     * @since 1.0
     */
    @Override
    public ArrayList<ArrayList<Integer>> generateSudoku6x6() {
        if (fillBoard(currentBoard)) {
            return currentBoard;
        }
        return currentBoard;
    }

    /**
     * Initializes the current 6x6 Sudoku board with zeros.
     *
     * @return the initialized Sudoku board.
     * @since 1.0
     */
    @Override
    public ArrayList<ArrayList<Integer>> currentSudoku6x6() {
        currentBoard = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            currentBoard.add(new ArrayList<>(Collections.nCopies(6, 0)));
        }
        return currentBoard;
    }

    /**
     * Retrieves the current state of the Sudoku board.
     *
     * @return the current Sudoku board.
     * @since 1.0
     */
    @Override
    public ArrayList<ArrayList<Integer>> getcurrentSudoku6x6() {
        return currentBoard;
    }

    /**
     * Updates the Sudoku board at the specified position with a new value.
     *
     * @param row   the row of the board to update.
     * @param col   the column of the board to update.
     * @param value the new value to place at the specified position.
     * @return true if the update was successful, false otherwise.
     * @since 1.0
     */
    @Override
    public boolean updateCurrentBoard(int row, int col, int value) {
        if (row >= 0 && row < 6 && col >= 0 && col < 6) {
            if (isValidPlacement(currentBoard, row, col, value)) {
                currentBoard.get(row).set(col, value);
                return true;
            }
        }
        return false;
    }

    /**
     * Fills the Sudoku board with valid numbers based on Sudoku rules.
     *
     * @param board the Sudoku board to be filled.
     * @return true if the board was successfully filled, false otherwise.
     * @since 1.0
     */
    @Override
    public boolean fillBoard(ArrayList<ArrayList<Integer>> board) {
        Random random = new Random();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (board.get(row).get(col) == 0) {
                    ArrayList<Integer> shuffledNumbers = new ArrayList<>();
                    for (int num = 1; num <= 6; num++) {
                        shuffledNumbers.add(num);
                    }
                    Collections.shuffle(shuffledNumbers, random);
                    for (int number : shuffledNumbers) {
                        if (isValidPlacement(board, row, col, number)) {
                            board.get(row).set(col, number);
                            if (fillBoard(board)) {
                                return true;
                            }
                            board.get(row).set(col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether a given number can be placed in a specified position on the board.
     *
     * @param board  the Sudoku board.
     * @param row    the row to check.
     * @param col    the column to check.
     * @param number the number to validate.
     * @return true if the number can be placed, false otherwise.
     * @since 1.0
     */
    @Override
    public boolean isValidPlacement(ArrayList<ArrayList<Integer>> board, int row, int col, int number) {
        for (int i = 0; i < 6; i++) {
            if (i != col && board.get(row).get(i) == number) {
                return false;
            }
        }

        for (int i = 0; i < 6; i++) {
            if (i != row && board.get(i).get(col) == number) {
                return false;
            }
        }

        int regionRowStart = (row / 2) * 2;
        int regionColStart = (col / 3) * 3;

        for (int i = regionRowStart; i < regionRowStart + 2; i++) {
            for (int j = regionColStart; j < regionColStart + 3; j++) {
                if ((i != row || j != col) && board.get(i).get(j) == number) {
                    return false;
                }
            }
        }
        return true;
    }
}
