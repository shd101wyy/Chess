package unit_test;

import chess.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class KingTest {
    /**
     *  test valid possible moves
     * @throws Exception
     */
    @Test
    public void testGetPossibleMoveCoordinate() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);

        King king = new King(board, 1);
        king.setCoordinate(3, 0);
        assertEquals(5, king.getPossibleMoveCoordinate().size());

        // put an enemy ahead
        Pawn enemy = new Pawn(board, 2);
        enemy.setCoordinate(3, 1);
        assertEquals(5, king.getPossibleMoveCoordinate().size());

        // put an friend on the left;
        Pawn friend = new Pawn(board, 1);
        friend.setCoordinate(2, 0);
        assertEquals(4, king.getPossibleMoveCoordinate().size());

        // put king at the center of the board
        king.setCoordinate(3, 3);
        assertEquals(8, king.getPossibleMoveCoordinate().size());
    }

    /*
     *  test whether king is in check
     * @throws Exception
     */
    @Test
    public void testIsInCheck() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);

        King king = new King(board, 1);
        king.setCoordinate(3, 0);
        assertEquals(false, king.isInCheck());

        // add an enemy ahead
        Rook rook = new Rook(board, 2);
        rook.setCoordinate(3, 6);
        assertEquals(true, king.isInCheck());

    }
}