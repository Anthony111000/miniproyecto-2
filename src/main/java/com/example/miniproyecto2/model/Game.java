package com.example.miniproyecto2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game extends GameAdapter {
    private ArrayList<ArrayList<Integer>> currentBoard;

    public Game() {
        currentSudoku6x6();
    }

    @Override
    public ArrayList<ArrayList<Integer>> generateSudoku6x6() {
        if (fillBoard(currentBoard)) {
            return currentBoard;
        }
        return currentBoard;
    }

    @Override
    public ArrayList<ArrayList<Integer>> currentSudoku6x6() {
        currentBoard = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            currentBoard.add(new ArrayList<>(Collections.nCopies(6, 0)));
        }
        return currentBoard;
    }

    // Sobrescribir correctamente el método para devolver el tablero actual
    @Override
    public ArrayList<ArrayList<Integer>> getcurrentSudoku6x6() {
        return currentBoard;
    }

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
