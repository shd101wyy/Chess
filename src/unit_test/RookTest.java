package unit_test;

import org.junit.Test;

import static org.junit.Assert.*;

import chess.*;
import piece.Pawn;
import piece.Piece;
import piece.Rook;

public class RookTest {
    /**
     *  test valid possible moves
     * @throws Exception
     */
    @Test
    public void testGetPossibleMoveCoordinate() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);

        // create rook
        Rook rook = new Rook(board, Piece.Player.WHITE);
        rook.setCoordinate(0, 0);
        assertEquals(14, rook.getPossibleMoveCoordinate().size());

        // put rook at center of chess board
        rook.setCoordinate(3, 3);
        assertEquals(14, rook.getPossibleMoveCoordinate().size());

        // put an enemy on top
        Piece enemy = new Pawn(board, Piece.Player.BLACK);
        enemy.setCoordinate(3, 5);
        assertEquals(12, rook.getPossibleMoveCoordinate().size());

        // put an friend on left
        Piece friend = new Pawn(board, Piece.Player.WHITE);
        friend.setCoordinate(1, 3);
        assertEquals(10, rook.getPossibleMoveCoordinate().size());
    }
}