package view;

import model.Board;

import javax.swing.*;

public class GameEnd {

    public void showEndGame(Board board) {
        String message;
        String title;

        if (board.IsWin() == Board.WHITE_WIN) {
            message = "Белые победили!";
            title = "Победа белых";
        } else if (board.IsWin() == Board.BLACK_WIN) {
            message = "Чёрные победили!";
            title = "Победа чёрных";
        } else {
            message = "Ничья!";
            title = "Конец игры";
        }

        int option = JOptionPane.showConfirmDialog(null,
                message + "\n\nСыграть ещё раз?",
                title,
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                Board newBoard = new Board();
                gameFrame frame = new gameFrame();
                frame.createGame(newBoard);
            });
        } else {
            System.exit(0);
        }
    }
}