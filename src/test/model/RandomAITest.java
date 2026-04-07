package test.model;

import model.Board;
import model.RandomAI;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RandomAITest {
    private Board board;
    private RandomAI ai;

    @Before
    public void setUp() {
        board = new Board();
        ai = new RandomAI();
    }

    @Test
    public void testAIDoesNotMoveOnWhiteTurn() {
        int whiteCount = board.countWhite();
        ai.makeRandomMove(board);
        assertEquals(whiteCount, board.countWhite());
    }

    @Test
    public void testAIMakesMoveOnBlackTurn() {
        board.move(6, 1, 5, 0);
        int blackCount = board.countBlack();
        ai.makeRandomMove(board);
        assertNotEquals(blackCount, board.countBlack());
    }

    @Test
    public void testAIPrefersTakeMove() {
        board.move(6, 1, 5, 0);
        board.setElement(4, 1, 2);
        board.setElement(3, 2, 0);

        int blackCount = board.countBlack();
        ai.makeRandomMove(board);
        assertTrue(blackCount > board.countBlack());
    }

    @Test
    public void testGetTakeMoveReturnsMoves() {
        board.move(6, 1, 5, 0);
        board.setElement(4, 1, 2);
        board.setElement(3, 2, 0);

        var moves = ai.getTakeMove(board);
        assertFalse(moves.isEmpty());
    }
}