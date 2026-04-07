package io;

import model.Board;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {

    public void OutputGame(File file, Board board) throws IOException {
        if (file == null) {
            throw new IOException("Файл не выбран");
        }

        try (PrintWriter gameWriter = new PrintWriter(file)) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    gameWriter.print(board.getElement(i, j));
                    if (j < 7) {
                        gameWriter.print(" ");
                    }
                }
                if (i < 7) {
                    gameWriter.print("\n");
                }
            }
            gameWriter.flush();
        }
    }
}