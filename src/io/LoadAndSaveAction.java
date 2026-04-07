package io;

import model.Board;
import view.gameFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class LoadAndSaveAction {

    public void load(Board board) {
        JFileChooser open = new JFileChooser();
        open.setDialogTitle("Загрузить игру");
        open.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int res = open.showOpenDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            Input io = new Input();
            try {
                io.InputGame(open.getSelectedFile(), board);
                gameFrame game = new gameFrame();
                game.createGame(board);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка открытия файла: " + ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void save(Board board) {
        JFileChooser save = new JFileChooser();
        save.setDialogTitle("Сохранить игру");
        save.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int res = save.showSaveDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            Output out = new Output();
            try {
                String path = save.getSelectedFile().getPath();
                if (!path.endsWith(".txt")) {
                    path += ".txt";
                }
                File file = new File(path);
                out.OutputGame(file, board);
                JOptionPane.showMessageDialog(null,
                        "Игра успешно сохранена!",
                        "Сохранение",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка сохранения: " + ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}