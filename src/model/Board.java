package model;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Board {
    private final int[][] board = new int[8][8];
    private boolean isWhiteTurn = true;
    private boolean isMultiJump = false;
    private int multiJumpRow = -1;
    private int multiJumpCol = -1;
    public static final int DRAW = 0;
    public static final int WHITE_WIN = 1;
    public static final int BLACK_WIN = 2;
    public static final int CONTINUE = 3;

    public Board() {
        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board.length; col++){
                if ((row < 3) && ((row + col) % 2 != 0) ) {
                    board[row][col] = 2;
                }
                else if ((row >= 5) && ((row + col) % 2 != 0) ){
                    board[row][col] = 1;
                }
            }
        }
    }

    public boolean isWhiteTurn() {return isWhiteTurn;}

    public void setElement(int row, int col, int elem) throws ArrayIndexOutOfBoundsException{
        if(row < 0 || row >= board.length || col < 0 || col >= board.length)
            throw new ArrayIndexOutOfBoundsException("Выход за пределы массива");
        board[row][col] = elem;
    }

    public void setTurn(boolean whiteTurn) {
        this.isWhiteTurn = whiteTurn;
    }

    public boolean move(int row, int col, int toRow, int toCol) {
        if (row < 0 || row >= board.length || col < 0 || col >= board.length) return false;
        if (toRow < 0 || toRow >= board.length || toCol < 0 || toCol >= board.length) return false;
        if ((toRow + toCol) % 2 == 0) return false;
        if (board[row][col] == 0) return false;

        if (isMultiJump) {
            if (row != multiJumpRow || col != multiJumpCol) {
                return false;
            }
        } else {
            if (isWhiteTurn && (board[row][col] != 1 && board[row][col] != 3)) return false;
            if (!isWhiteTurn && (board[row][col] != 2 && board[row][col] != 4)) return false;
        }

        if (defoltMove(row, col, toRow, toCol)) {
            if (isMultiJump) return false;
            board[toRow][toCol] = board[row][col];
            board[row][col] = 0;
            checkQueen(toRow, toCol);
            isWhiteTurn = !isWhiteTurn;
            return true;
        }

        if (TakeMove(row, col, toRow, toCol)) {
            if (board[row][col] == 1 || board[row][col] == 2) {
                int middleRow = (row + toRow) / 2;
                int middleCol = (col + toCol) / 2;
                board[middleRow][middleCol] = 0;
            } else {
                int dRow = (toRow - row) > 0 ? 1 : -1;
                int dCol = (toCol - col) > 0 ? 1 : -1;
                int r = row + dRow;
                int c = col + dCol;

                while (r != toRow && c != toCol) {
                    if (board[r][c] != 0) {
                        board[r][c] = 0;
                        break;
                    }
                    r += dRow;
                    c += dCol;
                }
            }

            board[toRow][toCol] = board[row][col];
            board[row][col] = 0;
            checkQueen(toRow, toCol);

            if (canTake(toRow, toCol)) {
                isMultiJump = true;
                multiJumpRow = toRow;
                multiJumpCol = toCol;
                return true;
            }

            isMultiJump = false;
            multiJumpRow = -1;
            multiJumpCol = -1;
            isWhiteTurn = !isWhiteTurn;
            return true;
        }

        return false;
    }

    public boolean defoltMove(int row, int col, int toRow, int toCol) {
        if (canTake(row, col) || canAnyTake()) return false;

        if (board[row][col] == 1 || board[row][col] == 2) {
            if (isWhiteTurn) {
                if (toRow >= row) return false;
            } else {
                if (toRow <= row) return false;
            }
            if (abs(toRow - row) != 1 || abs(toCol - col) != 1) return false;
        }
        else if (board[row][col] == 3 || board[row][col] == 4) {
            if (abs(toRow - row) != abs(toCol - col)) return false;

            int dRow = (toRow - row) > 0 ? 1 : -1;
            int dCol = (toCol - col) > 0 ? 1 : -1;

            int r = row + dRow;
            int c = col + dCol;

            while (r != toRow && c != toCol) {
                if (board[r][c] != 0) return false;
                r += dRow;
                c += dCol;
            }
        }
        if (board[toRow][toCol] != 0) return false;

        return true;
    }

    public boolean canTake(int row, int col) {
        if ((isWhiteTurn && board[row][col] != 1 && board[row][col] != 3) ||
                (!isWhiteTurn && board[row][col] != 2 && board[row][col] != 4))
            return false;

        if (board[row][col] == 1 || board[row][col] == 2) {
            int[][] directions = {{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};
            for (int[] dir : directions) {
                int toRow = row + dir[0];
                int toCol = col + dir[1];

                if (toRow >= 0 && toRow < board.length && toCol >= 0 && toCol < board.length) {
                    if (TakeMove(row, col, toRow, toCol)) {
                        return true;
                    }
                }
            }
        }
        else if (board[row][col] == 3 || board[row][col] == 4){
            int[][] queenDirs = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            for (int[] dir : queenDirs) {
                int r = row + dir[0];
                int c = col + dir[1];
                boolean enemyFound = false;

                while (r >= 0 && r < board.length && c >= 0 && c < board.length) {
                    if (board[r][c] == 0) {
                        if (enemyFound) return true;
                    } else {
                        if (enemyFound) break;

                        if (isWhiteTurn && (board[r][c] == 2 || board[r][c] == 4)) {
                            enemyFound = true;
                        } else if (!isWhiteTurn && (board[r][c] == 1 || board[r][c] == 3)) {
                            enemyFound = true;
                        } else {
                            break;
                        }
                    }
                    r += dir[0];
                    c += dir[1];
                }
            }
        }
        return false;
    }

    public boolean canAnyTake() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (isWhiteTurn && (board[row][col] == 1 || board[row][col] == 3)) {
                    if (canTake(row, col)) return true;
                }
                if (!isWhiteTurn && (board[row][col] == 2 || board[row][col] == 4)) {
                    if (canTake(row, col)) return true;
                }
            }
        }
        return false;
    }

    public boolean TakeMove(int row, int col, int toRow, int toCol) {
        if (board[row][col] == 1 || board[row][col] == 2) {
            if (abs(toRow - row) != 2 || abs(toCol - col) != 2) return false;
            if (board[toRow][toCol] != 0) return false;

            int middleRow = (row + toRow) / 2;
            int middleCol = (col + toCol) / 2;

            if (middleRow < 0 || middleRow >= board.length || middleCol < 0 || middleCol >= board.length) {
                return false;
            }

            int cur = board[row][col];
            int middle = board[middleRow][middleCol];

            if ((cur == 1 && middle == 2) || (cur == 2 && middle == 1)) return true;
            if ((cur == 1 && middle == 4) || (cur == 2 && middle == 3)) return true;
            return false;
        }
        else if (board[row][col] == 3 || board[row][col] == 4){
            if (abs(toRow - row) != abs(toCol - col)) return false;
            if (board[toRow][toCol] != 0) return false;

            int dRow = (toRow - row) > 0 ? 1 : -1;
            int dCol = (toCol - col) > 0 ? 1 : -1;

            int r = row + dRow;
            int c = col + dCol;

            boolean enemyFound = false;
            while (r != toRow && c != toCol) {
                if (board[r][c] != 0) {
                    if (enemyFound) return false;
                    if ((board[row][col] == 3 && (board[r][c] == 2 || board[r][c] == 4)) ||
                            (board[row][col] == 4 && (board[r][c] == 1 || board[r][c] == 3))) {
                        enemyFound = true;
                    } else {
                        return false;
                    }
                }
                r += dRow;
                c += dCol;
            }
            return enemyFound;
        }
        return false;
    }

    public void checkQueen(int row, int col){
        if (board[row][col] == 1 && row == 0) board[row][col] = 3;
        else if (board[row][col] == 2 && row == board.length - 1) board[row][col] = 4;
    }

    public int IsWin() {
        if (countWhite() == 0) return BLACK_WIN;
        if (countBlack() == 0) return WHITE_WIN;
        if (Draw()) return DRAW;
        return CONTINUE;
    }

    public boolean Draw() {
        ArrayList<Integer> queensW = new ArrayList<>();
        ArrayList<Integer> queensB = new ArrayList<>();
        ArrayList<Integer> figuresW = new ArrayList<>();
        ArrayList<Integer> figuresB = new ArrayList<>();

        for (int i = 0 ; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if ((i + j) % 2 == 0) continue;
                if (board[i][j] == 3) queensW.add(board[i][j]);
                else if (board[i][j] == 4) queensB.add(board[i][j]);
                else if (board[i][j] == 1) figuresW.add(board[i][j]);
                else if (board[i][j] == 2) figuresB.add(board[i][j]);
            }
        }

        if (figuresW.isEmpty() && figuresB.isEmpty()){
            return ((queensW.size() <= 2 && queensB.size() <= 1) || (queensW.size() <= 1 && queensB.size() <= 2));
        }

        if (queensW.isEmpty() && queensB.isEmpty()){
            return (figuresW.size() == 1 && figuresB.size() == 1);
        }
        return false;
    }

    public int countWhite(){
        int counter = 0;
        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board.length; col++){
                if ((row + col) % 2 == 0) continue;
                if (board[row][col] == 1 || board[row][col] == 3) counter++;
            }
        }
        return counter;
    }

    public int countBlack() {
        int counter = 0;
        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board.length; col++){
                if ((row + col) % 2 == 0) continue;
                if (board[row][col] == 2 || board[row][col] == 4) counter++;
            }
        }
        return counter;
    }

    public int getElement(int i, int j) {
        if ((i < 0 || i >= board.length) || (j < 0 || j >= board.length))
            throw new ArrayIndexOutOfBoundsException("Выход за пределы");
        return board[i][j];
    }
}