package test.model;

import model.Board;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInitialBoardSetup() {
        assertEquals(12, board.countWhite());
        assertEquals(12, board.countBlack());
        assertTrue(board.isWhiteTurn());
    }

    @Test
    public void testSimpleMoveWhite() {
        assertTrue(board.move(6, 1, 5, 0));
        assertEquals(0, board.getElement(6, 1));
        assertEquals(1, board.getElement(5, 0));
        assertFalse(board.isWhiteTurn());
    }

    @Test
    public void testSimpleMoveBlack() {
        board.move(6, 1, 5, 0);
        assertTrue(board.move(2, 1, 3, 0));
        assertEquals(0, board.getElement(2, 1));
        assertEquals(2, board.getElement(3, 0));
        assertTrue(board.isWhiteTurn());
    }

    @Test
    public void testInvalidMoveWrongDirection() {
        assertFalse(board.move(5, 0, 6, 1));
    }

    @Test
    public void testTakeMove() {
        board.setElement(5, 0, 1);
        board.setElement(4, 1, 2);
        board.setElement(3, 2, 0);

        assertTrue(board.move(5, 0, 3, 2));
        assertEquals(0, board.getElement(4, 1));
        assertEquals(1, board.getElement(3, 2));
    }

    @Test
    public void testCantMoveWhenTakeAvailable() {
        board.setElement(5, 0, 1);
        board.setElement(4, 1, 2);

        assertFalse(board.defoltMove(5, 2, 4, 1));
    }

    @Test
    public void testWhiteQueenPromotion() {
        board.setElement(1, 0, 1);
        board.move(1, 0, 0, 1);
        assertEquals(3, board.getElement(0, 1));
    }

    @Test
    public void testBlackQueenPromotion() {
        board.setTurn(false);
        board.setElement(6, 1, 2);
        board.move(6, 1, 7, 0);
        assertEquals(4, board.getElement(7, 0));
    }

    @Test
    public void testQueenMove() {
        board.setElement(5, 0, 3);
        assertTrue(board.move(5, 0, 2, 3));
        assertEquals(3, board.getElement(2, 3));
    }

    @Test
    public void testQueenTake() {
        board.setElement(5, 0, 3);
        board.setElement(3, 2, 2);
        board.setElement(1, 4, 0);

        assertTrue(board.move(5, 0, 1, 4));
        assertEquals(0, board.getElement(3, 2));
    }

    @Test
    public void testWhiteWin() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int val = board.getElement(i, j);
                if (val == 2 || val == 4) {
                    board.setElement(i, j, 0);
                }
            }
        }
        assertEquals(Board.WHITE_WIN, board.IsWin());
    }

    @Test
    public void testBlackWin() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int val = board.getElement(i, j);
                if (val == 1 || val == 3) {
                    board.setElement(i, j, 0);
                }
            }
        }
        assertEquals(Board.BLACK_WIN, board.IsWin());
    }

    @Test
    public void testDraw() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.setElement(i, j, 0);
            }
        }
        board.setElement(0, 1, 3);
        board.setElement(7, 0, 4);
        assertTrue(board.Draw());
    }

    @Test
    public void testCanTake() {
        board.setElement(5, 0, 1);
        board.setElement(4, 1, 2);
        assertTrue(board.canTake(5, 0));
    }

    @Test
    public void testCanAnyTake() {
        board.setElement(5, 0, 1);
        board.setElement(4, 1, 2);
        assertTrue(board.canAnyTake());
    }
}