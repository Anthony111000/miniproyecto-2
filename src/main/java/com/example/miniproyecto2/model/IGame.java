package com.example.miniproyecto2.model;

import java.util.ArrayList;

public  interface IGame {
    ArrayList<ArrayList<Integer>> generateSudoku6x6();
    ArrayList<ArrayList<Integer>> currentSudoku6x6();
    boolean updateCurrentBoard(int row, int col, int value);
    boolean fillBoard(ArrayList<ArrayList<Integer>> board);
    boolean isValidPlacement(ArrayList<ArrayList<Integer>> board, int row, int col, int number);
}
