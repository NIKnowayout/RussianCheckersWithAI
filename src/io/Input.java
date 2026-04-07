package io;

import model.Board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    public void InputGame(File file, Board board) throws IOException {
        if (file == null) {
            throw new IOException("Файл не выбран");
        }

        if (!file.exists()) {
            throw new IOException("Файл не существует: " + file.getPath());
        }
        if (!file.isFile()) {
            throw new IOException("Это не файл: " + file.getPath());
        }
        if (!file.canRead()) {
            throw new IOException("Нет прав на чтение файла: " + file.getPath());
        }

        try (FileInputStream game = new FileInputStream(file);
             Scanner scanner = new Scanner(game)) {

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (scanner.hasNextInt()) {
                        board.setElement(i, j, scanner.nextInt());
                    } else {
                        throw new IOException("Недостаточно данных в файле");
                    }
                }
            }
        } catch (InputMismatchException e) {
            throw new IOException("Некорректные данные в файле (ожидались числа)");
        }
    }
}