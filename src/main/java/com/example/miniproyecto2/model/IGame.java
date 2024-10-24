package com.example.miniproyecto2.model;

import java.util.ArrayList;

/**
 * Interface representing the core functionalities of a Sudoku game.
 * This interface defines methods for generating, updating, and validating a 6x6 Sudoku board.
 *
 * @author Jerson Alexis Ortiz Velasco
 * @author Jhon Antony Murillo Olave
 * @version 1.0
 * @since 1.0
 */
public interface IGame {

    /**
     * Generates a new 6x6 Sudoku board with an initial setup of numbers.
     *
     * @return a 2D ArrayList representing the generated Sudoku board.
     *  @since 1.0
     */
    ArrayList<ArrayList<Integer>> generateSudoku6x6();

    /**
     * Retrieves the current state of the 6x6 Sudoku board.
     *
     * @return a 2D ArrayList representing the current Sudoku board.
     *  @since 1.0
     */
    ArrayList<ArrayList<Integer>> currentSudoku6x6();

    /**
     * Updates the current Sudoku board with a new value at a specified position.
     *
     * @param row   the row of the board to update.
     * @param col   the column of the board to update.
     * @param value the new value to place at the specified position.
     * @return true if the update is valid, false otherwise.
     *  @since 1.0
     */
    boolean updateCurrentBoard(int row, int col, int value);

    /**
     * Fills the Sudoku board with valid numbers based on Sudoku rules.
     *
     * @param board the Sudoku board to be filled.
     * @return true if the board is successfully filled, false otherwise.
     *  @since 1.0
     */
    boolean fillBoard(ArrayList<ArrayList<Integer>> board);

    /**
     * Checks whether a given number can be placed in a specified position on the board.
     *
     * @param board  the Sudoku board.
     * @param row    the row to check.
     * @param col    the column to check.
     * @param number the number to validate.
     * @return true if the placement is valid, false otherwise.
     *  @since 1.0
     */
    boolean isValidPlacement(ArrayList<ArrayList<Integer>> board, int row, int col, int number);

    /**
     * Retrieves the current Sudoku board for further processing.
     *
     * @return a 2D ArrayList representing the current Sudoku board.
     *  @since 1.0
     */
    ArrayList<ArrayList<Integer>> getcurrentSudoku6x6();
}
