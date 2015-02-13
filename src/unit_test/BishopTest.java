package unit_test;

import chess.Bishop;
import chess.ChessBoard;
import chess.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

public class BishopTest {
    /**
     *  test valid possible moves
     *
     */
    @Test
    public void testGetPossibleMoveCoordinate() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);
        Piece p;

        // put p at left bottom corner
        p = new Bishop(board, 1);
        p.setCoordinate(0, 0);
        assertEquals(p.getPossibleMoveCoordinate().size(), 7);

        // put p at center
        p.setCoordinate(3, 3);
        assertEquals(p.getPossibleMoveCoordinate().size(), 13);

        // put opponent piece at one possible move coordinate
        Piece opponent_piece = new Bishop(board, 1);
        opponent_piece.setCoordinate(5, 5);
        assertEquals(p.getPossibleMoveCoordinate().size(), 10);
    }
}