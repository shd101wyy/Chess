package unit_test;

import chess.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {

    /**
     * Test player id
     * player id could only be 1 or 2; otherwise throw exception
     * @throws Exception
     */
    @Test
    public void testGetPlayer() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);  // create chess board

        Piece p1 = new King(board, 1);            // check player id valid
        assertEquals(p1.getPlayer(), 1);

        Piece p2 = new King(board, 2);            // check player id valid
        assertEquals(p2.getPlayer(), 2);
    }

    /**
     * Test whether piece name is correct
     * @throws Exception
     */
    @Test
    public void testGetPiece_name() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);  // create chess board

        Piece p;

        p = new King(board, 1);
        assertEquals(p.getPiece_name(), "king");

        p = new Bishop(board, 1);
        assertEquals(p.getPiece_name(), "bishop");

        p = new Knight(board, 1);
        assertEquals(p.getPiece_name(), "knight");

        p = new Pawn(board, 1);
        assertEquals(p.getPiece_name(), "pawn");

        p = new Queen(board, 1);
        assertEquals(p.getPiece_name(), "queen");

        p = new Rook(board, 1);
        assertEquals(p.getPiece_name(), "rook");
    }
}