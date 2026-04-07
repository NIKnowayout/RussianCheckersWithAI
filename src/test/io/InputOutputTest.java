package test.io;

import io.Input;
import io.Output;
import model.Board;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;

public class InputOutputTest {
    private Board board;
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        board = new Board();
        tempFile = File.createTempFile("test_board", ".txt");
        tempFile.deleteOnExit();
    }

    @After
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testSaveBoard() throws IOException {
        Output output = new Output();
        output.OutputGame(tempFile, board);
        assertTrue(tempFile.exists() && tempFile.length() > 0);
    }

    @Test
    public void testLoadBoard() throws IOException {
        board.setElement(0, 1, 5);
        new Output().OutputGame(tempFile, board);

        Board loadedBoard = new Board();
        new Input().InputGame(tempFile, loadedBoard);

        assertEquals(board.getElement(0, 1), loadedBoard.getElement(0, 1));
    }

    @Test
    public void testSaveLoadFullCycle() throws IOException {
        board.setElement(0, 1, 3);
        board.setElement(7, 0, 4);
        board.setTurn(false);

        new Output().OutputGame(tempFile, board);

        Board loadedBoard = new Board();
        new Input().InputGame(tempFile, loadedBoard);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(board.getElement(i, j), loadedBoard.getElement(i, j));
            }
        }
        assertEquals(board.isWhiteTurn(), loadedBoard.isWhiteTurn());
    }

    @Test(expected = IOException.class)
    public void testSaveThrowsExceptionWhenFileNull() throws IOException {
        Output output = new Output();
        output.OutputGame(null, board);
    }

    @Test(expected = IOException.class)
    public void testLoadThrowsExceptionWhenFileNotExists() throws IOException {
        Input input = new Input();
        input.InputGame(new File("fake.txt"), board);
    }
}