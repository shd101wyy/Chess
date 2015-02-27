package unit_test;

import org.junit.Test;

import static org.junit.Assert.*;
import chessboard.ChessBoard;
import chess.Player;
import piece.Knight;
import piece.Pawn;
import piece.Piece;

public class KnightTest {
    /**
     *  test valid possible moves
     *
     */
    @Test
    public void testGetPossibleMoveCoordinate() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);

        Knight knight = new Knight(board, Player.WHITE);

        // put king at center of the board
        knight.setCoordinate(3, 3);
        assertEquals(8, knight.getPossibleMoveCoordinate().size());

        // put king at most left side
        knight.setCoordinate(0, 3);
        assertEquals(4, knight.getPossibleMoveCoordinate().size());

        // put a friend at one possible move coordinate
        Piece friend = new Pawn(board, Player.WHITE);
        friend.setCoordinate(1, 5);
        assertEquals(3, knight.getPossibleMoveCoordinate().size());
        friend.removeSelf();

        // put an enemy at one possible move coordinate
        Piece enemy = new Pawn(board, Player.BLACK);
        enemy.setCoordinate(1, 5);
        assertEquals(4, knight.getPossibleMoveCoordinate().size());
    }
}