package unit_test;

import chessboard.ChessBoard;
import chessboard.Player;
import junit.framework.TestCase;
import piece.*;

public class LancerTest extends TestCase {
    /**
     *  test valid possible moves
     *
     */
    public void testGetPossibleMoveCoordinate() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);
        Piece p;

        // put p at center
        p = new Lancer(board, Player.WHITE);
        p.setCoordinate(4, 4);
        assertEquals(8, p.getPossibleMoveCoordinate().size());

        // put friend on left, which block the way
        Piece friend = new Pawn(board, Player.WHITE);
        friend.setCoordinate(3, 4);
        assertEquals(6, p.getPossibleMoveCoordinate().size());

        // put enemy on right, which also block the way
        Piece enemy = new Pawn(board, Player.BLACK);
        enemy.setCoordinate(5, 4);
        assertEquals(5, p.getPossibleMoveCoordinate().size());
    }
}