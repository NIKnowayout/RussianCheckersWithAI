package view;

import model.RandomAI;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardView extends JPanel {
    public static final int CELL_SIZE = 80;
    private Board board;
    private boolean isSelected;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public BoardView(Board board) {
        this.board = board;
        this.isSelected = false;
        setPreferredSize(new Dimension(640, 640));
        MyMouseListener m1 = new MyMouseListener();
        this.addMouseListener(m1);
    }

    class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int col = e.getX() / CELL_SIZE;
            int row = e.getY() / CELL_SIZE;

            if (row < 0 || row >= 8 || col < 0 || col >= 8) return;

            if (!isSelected) {
                if (board.getElement(row, col) > 0) {
                    if (board.isWhiteTurn() && (board.getElement(row, col) == 1 || board.getElement(row, col) == 3)) {
                        selectedRow = row;
                        selectedCol = col;
                        isSelected = true;
                    } else if (!board.isWhiteTurn() && (board.getElement(row, col) == 2 || board.getElement(row, col) == 4)) {
                        selectedRow = row;
                        selectedCol = col;
                        isSelected = true;
                    }
                }
            } else {
                boolean r = board.move(selectedRow, selectedCol, row, col);
                if (r) {
                    isSelected = false;
                    repaint();

                    if (board.IsWin() != Board.CONTINUE) {
                        GameEnd gameEnd = new GameEnd();
                        gameEnd.showEndGame(board);
                        return;
                    }

                    if (!board.isWhiteTurn()) {
                        RandomAI ai = new RandomAI();
                        ai.makeRandomMove(board);
                        repaint();

                        if (board.IsWin() != Board.CONTINUE) {
                            GameEnd gameEnd = new GameEnd();
                            gameEnd.showEndGame(board);
                        }
                    }
                } else {
                    isSelected = false;
                }
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                g.setColor((row + col) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99));
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                int piece = board.getElement(row, col);
                if (piece == 2) {
                    drawPiece(g, col * CELL_SIZE, row * CELL_SIZE, Color.BLACK, false);
                } else if (piece == 1) {
                    drawPiece(g, col * CELL_SIZE, row * CELL_SIZE, Color.WHITE, false);
                } else if (piece == 3) {
                    drawPiece(g, col * CELL_SIZE, row * CELL_SIZE, Color.WHITE, true);
                } else if (piece == 4) {
                    drawPiece(g, col * CELL_SIZE, row * CELL_SIZE, Color.BLACK, true);
                }
            }
        }

        if (isSelected && selectedRow >= 0 && selectedCol >= 0) {
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(selectedCol * CELL_SIZE + 5, selectedRow * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
        }
    }

    private void drawPiece(Graphics g, int x, int y, Color color, boolean isQueen) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);

        g2d.setColor(color);
        g2d.fillOval(x + 3, y + 3, CELL_SIZE - 6, CELL_SIZE - 6);

        g2d.setColor(Color.BLACK);
        g2d.drawOval(x + 3, y + 3, CELL_SIZE - 6, CELL_SIZE - 6);

        if (isQueen) {
            g2d.setColor(color == Color.WHITE ? Color.BLACK : Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, CELL_SIZE / 2));
            String q = "Q";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(q);
            int textHeight = fm.getAscent();
            g2d.drawString(q, x + (CELL_SIZE - textWidth) / 2, y + (CELL_SIZE + textHeight) / 2 - 5);
        }
    }
}