package unit_test;

import org.junit.Test;

import static org.junit.Assert.*;
import chess.*;
import piece.Pawn;
import piece.Piece;
import piece.Queen;

public class QueenTest {
    /**
     *  test valid possible moves
     * @throws Exception
     */
    @Test
    public void testGetPossibleMoveCoordinate() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);

        // create a queen
        Queen queen = new Queen(board, 1);
        queen.setCoordinate(3, 0);
        assertEquals(21, queen.getPossibleMoveCoordinate().size());

        // put queen at center
        queen.setCoordinate(3, 3);
        assertEquals(27, queen.getPossibleMoveCoordinate().size());

        // put an enemy on top
        Piece enemy = new Pawn(board, 2);
        enemy.setCoordinate(3, 5);
        assertEquals(25, queen.getPossibleMoveCoordinate().size());

        // put an friend on left
        Piece friend = new Pawn(board, 1);
        friend.setCoordinate(1, 3);
        assertEquals(23, queen.getPossibleMoveCoordinate().size());

    }
}