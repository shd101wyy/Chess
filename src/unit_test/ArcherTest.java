package unit_test;

import chessboard.ChessBoard;
import chessboard.Player;
import junit.framework.TestCase;
import piece.*;

import static org.junit.Assert.assertEquals;

public class ArcherTest extends TestCase {
    /**
     *  test valid possible moves
     *
     */
    public void testGetPossibleMoveCoordinate() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);
        Piece p;

        // put p at center
        p = new Archer(board, Player.WHITE);
        p.setCoordinate(4, 4);
        assertEquals(4, p.getPossibleMoveCoordinate().size());

        // put enemy on left
        Piece enemy = new Pawn(board, Player.BLACK);
        enemy.setCoordinate(3, 4);
        assertEquals(3, p.getPossibleMoveCoordinate().size());

        // put enemy at attack area
        Piece rook = new Rook(board, Player.BLACK);
        rook.setCoordinate(4, 6);
        assertEquals(4, p.getPossibleMoveCoordinate().size());

        rook.setCoordinate(3, 6);
        assertEquals(4, p.getPossibleMoveCoordinate().size());

        rook.setCoordinate(5, 6);
        assertEquals(4, p.getPossibleMoveCoordinate().size());

        rook.setCoordinate(4, 2);
        assertEquals(4, p.getPossibleMoveCoordinate().size());

        rook.setCoordinate(2, 6); // not in attack area
        assertEquals(3, p.getPossibleMoveCoordinate().size());


    }
}