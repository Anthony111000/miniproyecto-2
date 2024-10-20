package com.example.miniproyecto2.model;

import java.util.ArrayList;

public interface IGame {
    ArrayList<ArrayList<Integer>> generateSudoku6x6();
    boolean fillBoard(ArrayList<ArrayList<Integer>> board);
    boolean isValidPlacement(ArrayList<ArrayList<Integer>> board, int row, int col, int number);
}
