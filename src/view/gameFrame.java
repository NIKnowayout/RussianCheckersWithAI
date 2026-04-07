package view;

import io.LoadAndSaveAction;
import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class gameFrame {
    private Board board;
    private JFrame frame;

    public void createGame(Board board) {
        this.board = board;

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        frame = new JFrame();
        frame.setTitle("Игра: Русские Шашки");
        frame.setSize(800, 850);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        BoardView boardView = new BoardView(board);
        frame.add(boardView);

        JMenuBar mainMenu = new JMenuBar();
        JMenu file = new JMenu("Файлы");
        JMenuItem saveGame = new JMenuItem("Сохранить");
        JMenuItem loadGame = new JMenuItem("Загрузить");
        JMenuItem exitGame = new JMenuItem("Выход");

        saveGame.addActionListener(e -> {
            LoadAndSaveAction saveAction = new LoadAndSaveAction();
            saveAction.save(board);
        });

        loadGame.addActionListener(e -> {
            LoadAndSaveAction loadAction = new LoadAndSaveAction();
            loadAction.load(board);
            frame.dispose();
        });

        exitGame.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(frame, "Выйти из игры?", "Выход", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
        });

        file.add(saveGame);
        file.add(loadGame);
        file.addSeparator();
        file.add(exitGame);
        mainMenu.add(file);

        frame.setJMenuBar(mainMenu);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int closeOpt = JOptionPane.showConfirmDialog(frame, "Закрыть окно?", "Сообщение", JOptionPane.YES_NO_OPTION);
                if (closeOpt == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }
}