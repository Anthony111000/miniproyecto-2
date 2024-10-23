package com.example.miniproyecto2.model;

import java.util.ArrayList;

/**
 * Abstract class that serves as an adapter for the IGame interface.
 * This class provides default implementations for the methods defined in the IGame interface.
 * Subclasses can extend this class and provide their own implementations.
 *
 * @author Jerson Alexis Ortiz Velasco
 * @author Jhon Antony Murillo Olave
 * @version 1.0
 * @since 1.0
 */
public abstract class GameAdapter implements IGame {

    /**
     * Generates a new 6x6 Sudoku board with an initial setup of numbers.
     *
     * @return an empty 2D ArrayList representing the generated Sudoku board.
     */
    @Override
    public ArrayList<ArrayList<Integer>> generateSudoku6x6() {
        return new ArrayList<>();
    }

    /**
     * Retrieves the current state of the 6x6 Sudoku board.
     *
     * @return an empty 2D ArrayList representing the current Sudoku board.
     */
    @Override
    public ArrayList<ArrayList<Integer>> currentSudoku6x6() {
        return new ArrayList<>();
    }

    /**
     * Updates the current Sudoku board with a new value at a specified position.
     *
     * @param row   the row of the board to update.
     * @param col   the column of the board to update.
     * @param value the new value to place at the specified position.
     * @return false indicating that the update operation is not implemented.
     */
    @Override
    public boolean updateCurrentBoard(int row, int col, int value) {
        return false;
    }

    /**
     * Fills the Sudoku board with valid numbers based on Sudoku rules.
     *
     * @param board the Sudoku board to be filled.
     * @return false indicating that the filling operation is not implemented.
     */
    @Override
    public boolean fillBoard(ArrayList<ArrayList<Integer>> board) {
        return false;
    }

    /**
     * Checks whether a given number can be placed in a specified position on the board.
     *
     * @param board  the Sudoku board.
     * @param row    the row to check.
     * @param col    the column to check.
     * @param number the number to validate.
     * @return false indicating that the placement validation is not implemented.
     */
    @Override
    public boolean isValidPlacement(ArrayList<ArrayList<Integer>> board, int row, int col, int number) {
        return false;
    }

    /**
     * Retrieves the current Sudoku board for further processing.
     *
     * @return an empty 2D ArrayList representing the current Sudoku board.
     */
    @Override
    public ArrayList<ArrayList<Integer>> getcurrentSudoku6x6() {
        return new ArrayList<>();
    }
}
