package com.example.miniproyecto2.model;

import java.util.ArrayList;

public abstract class GameAdapter implements IGame {

    @Override
    public ArrayList<ArrayList<Integer>> generateSudoku6x6() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<ArrayList<Integer>> currentSudoku6x6() {
        return new ArrayList<>();
    }

    @Override
    public boolean updateCurrentBoard(int row, int col, int value) {
        return false;
    }

    @Override
    public boolean fillBoard(ArrayList<ArrayList<Integer>> board) {
        return false;
    }

    @Override
    public boolean isValidPlacement(ArrayList<ArrayList<Integer>> board, int row, int col, int number) {
        return false;
    }

    // Añadir implementación predeterminada del método
    @Override
    public ArrayList<ArrayList<Integer>> getcurrentSudoku6x6() {
        return new ArrayList<>();
    }
}
