package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAI {
    private final Random random = new Random();

    public static class Move {
        public int fromRow, fromCol, toRow, toCol;
        public Move(int fromRow, int fromCol, int toRow, int toCol) {
            this.fromRow = fromRow;
            this.fromCol = fromCol;
            this.toRow = toRow;
            this.toCol = toCol;
        }
    }

    public void makeRandomMove(Board board) {
        if (board.isWhiteTurn()) return;

        List<Move> takeMoves = getTakeMove(board);

        if (!takeMoves.isEmpty()) {
            Move move = takeMoves.get(random.nextInt(takeMoves.size()));
            board.move(move.fromRow, move.fromCol, move.toRow, move.toCol);

            boolean canContinue = true;
            while (canContinue) {
                takeMoves = getTakeMoveFromPosition(board, move.toRow, move.toCol);
                if (!takeMoves.isEmpty()) {
                    Move nextMove = takeMoves.get(random.nextInt(takeMoves.size()));
                    board.move(nextMove.fromRow, nextMove.fromCol, nextMove.toRow, nextMove.toCol);
                    move.toRow = nextMove.toRow;
                    move.toCol = nextMove.toCol;
                } else {
                    canContinue = false;
                }
            }
        } else {
            List<Move> defoltMoves = getDefoltMoves(board);
            if (!defoltMoves.isEmpty()) {
                Move chosenMove = defoltMoves.get(random.nextInt(defoltMoves.size()));
                board.move(chosenMove.fromRow, chosenMove.fromCol, chosenMove.toRow, chosenMove.toCol);
            }
        }
    }

    private List<Move> getDefoltMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getElement(i, j) == 2) {
                    int[][] directions = {{1, 1}, {1, -1}};
                    for (int[] dir : directions) {
                        int toRow = i + dir[0];
                        int toCol = j + dir[1];
                        if (toRow >= 0 && toRow < 8 && toCol >= 0 && toCol < 8) {
                            Board copy = CopyBoard(board);
                            if (copy.move(i, j, toRow, toCol))
                                moves.add(new Move(i, j, toRow, toCol));
                        }
                    }
                }
                else if (board.getElement(i, j) == 4) {
                    int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                    for (int[] dir : directions) {
                        int toRow = i + dir[0];
                        int toCol = j + dir[1];
                        while (toRow >= 0 && toRow < 8 && toCol >= 0 && toCol < 8) {
                            Board copy = CopyBoard(board);
                            if (copy.move(i, j, toRow, toCol))
                                moves.add(new Move(i, j, toRow, toCol));
                            else break;
                            toRow += dir[0];
                            toCol += dir[1];
                        }
                    }
                }
            }
        }
        return moves;
    }

    public List<Move> getTakeMove(Board board) {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getElement(i, j) == 2) {
                    int[][] realmoves = {{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};
                    for (int[] dir : realmoves) {
                        int toRow = i + dir[0];
                        int toCol = j + dir[1];
                        if (toRow >= 0 && toRow < 8 && toCol >= 0 && toCol < 8) {
                            Board copy = CopyBoard(board);
                            if (copy.TakeMove(i, j, toRow, toCol))
                                moves.add(new Move(i, j, toRow, toCol));
                        }
                    }
                }
                else if (board.getElement(i, j) == 4) {
                    int[][] realmoves = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                    for (int[] dir : realmoves) {
                        int r = i + dir[0];
                        int c = j + dir[1];
                        boolean enemyFound = false;
                        while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                            if (board.getElement(r, c) == 0) {
                                if (enemyFound) {
                                    Board copy = CopyBoard(board);
                                    if (copy.TakeMove(i, j, r, c))
                                        moves.add(new Move(i, j, r, c));
                                }
                            } else {
                                if (enemyFound) break;
                                if (!board.isWhiteTurn() && (board.getElement(r, c) == 1 || board.getElement(r, c) == 3)) {
                                    enemyFound = true;
                                } else break;
                            }
                            r += dir[0];
                            c += dir[1];
                        }
                    }
                }
            }
        }
        return moves;
    }

    public List<Move> getTakeMoveFromPosition(Board board, int row, int col) {
        List<Move> moves = new ArrayList<>();
        if (board.getElement(row, col) == 2) {
            int[][] realmoves = {{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};
            for (int[] dir : realmoves) {
                int toRow = row + dir[0];
                int toCol = col + dir[1];
                if (toRow >= 0 && toRow < 8 && toCol >= 0 && toCol < 8) {
                    Board copy = CopyBoard(board);
                    if (copy.TakeMove(row, col, toRow, toCol))
                        moves.add(new Move(row, col, toRow, toCol));
                }
            }
        } else if (board.getElement(row, col) == 4) {
            int[][] realmoves = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            for (int[] dir : realmoves) {
                int r = row + dir[0];
                int c = col + dir[1];
                boolean enemyFound = false;
                while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                    if (board.getElement(r, c) == 0) {
                        if (enemyFound) {
                            Board copy = CopyBoard(board);
                            if (copy.TakeMove(row, col, r, c))
                                moves.add(new Move(row, col, r, c));
                        }
                    } else {
                        if (enemyFound) break;
                        if (!board.isWhiteTurn() && (board.getElement(r, c) == 1 || board.getElement(r, c) == 3)) {
                            enemyFound = true;
                        } else break;
                    }
                    r += dir[0];
                    c += dir[1];
                }
            }
        }
        return moves;
    }

    private Board CopyBoard(Board original) {
        Board copy = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copy.setElement(i, j, original.getElement(i, j));
            }
        }
        copy.setTurn(original.isWhiteTurn());
        return copy;
    }
}