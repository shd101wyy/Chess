package unit_test;

import chess.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class PawnTest {
    /**
     *  test valid possible moves
     *
     */
    @Test
    public void testGetPossibleMoveCoordinate() throws Exception{
        ChessBoard board = new ChessBoard(8, 8);
        Piece p;

        p = new Pawn(board, 1);
        p.setCoordinate(0, 0);
        assertEquals(2, p.getPossibleMoveCoordinate().size()); // first time move, therefore it should be able to advance two squares.


        p.setCoordinate(0, 1);
        assertEquals(1, p.getPossibleMoveCoordinate().size()); // already moved, therefore it could move two squares.


        /*
         * create a pawn in same group
         * put it ahead p, then p couldn't move
         */
        Piece friend = new Pawn(board, 1);
        friend.setCoordinate(0, 2);
        assertEquals(0, p.getPossibleMoveCoordinate().size());

        /*
         * create an opponent piece at top right
         * therefore, p can move top right
         */
        Piece opponent_piece = new Pawn(board, 2);
        opponent_piece.setCoordinate(1, 2);
        assertEquals(1, p.getPossibleMoveCoordinate().size());

        /*
         * p reaches top boundary
         */
        p.setCoordinate(0, 7);
        assertEquals(0, p.getPossibleMoveCoordinate().size());
    }
}