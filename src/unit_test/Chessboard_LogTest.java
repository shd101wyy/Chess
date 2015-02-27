package unit_test;

import chessboard.Player;
import chessboard.ChessBoard;
import chessboard.Chessboard_Log;
import junit.framework.TestCase;
import piece.*;

import java.util.Stack;

public class Chessboard_LogTest extends TestCase {

    /**
     * Test Chessboard Log
     * The chessboard log should have the same content as Chessboard
     * @throws Exception
     */
    public void testToChessboard() throws Exception {

        // create classic chessboard
        ChessBoard board = new ChessBoard(8, 8);
        board.generateStandardBoard();

        Chessboard_Log log = new Chessboard_Log(board);

        assertEquals(board.getTurns(), log.getTurns());
        assertEquals(board.getHeight(), log.getHeight());
        assertEquals(board.getWidth(), log.getWidth());

        for(int i = 0; i < log.getHeight(); i++){
            for(int j = 0; j < log.getWidth(); j++){
                int x = j;
                int y = i;
                String s = log.getTileAtCoordinate(x, y);
                if (s == null){
                    assertEquals(s, board.getPieceAtCoordinate(x, y)); // should also be empty
                }
                else{
                    String color = s.substring(0, 5); // get color for that piece
                    String piece_name = s.substring(6);      // get piece name
                    Player player = (color.equals("white") ? Player.WHITE : Player.BLACK); // get player color
                    Piece p = board.getPieceAtCoordinate(x, y);

                    // check pawn
                    if (piece_name.equals("pawn_first") || piece_name.equals("pawn_not_first")){
                        assertEquals(p.getPiece_name(), "pawn");
                        assertEquals(((Pawn)p).isFirstTimeMove(), (piece_name.equals("pawn_first") ? true : false)); // check first move flag
                    }
                    else {
                        // the piece should have same piece_name, player
                        assertEquals(p.getPiece_name(), piece_name);
                        assertEquals(p.getPlayer(), player);
                    }
                }
            }
        }
    }

    /**
     * Test undo
     * @throws Exception
     */
    public void testUndo() throws Exception {
        // create classic chessboard
        ChessBoard board = new ChessBoard(8, 8);
        board.generateStandardBoard();

        // create stack of log
        Stack<Chessboard_Log> logs = new Stack<Chessboard_Log>();

        // store current board status to log
        logs.push(new Chessboard_Log(board));

        // move a pawn
        Piece p = board.getPieceAtCoordinate(0, 1);
        p.setCoordinate(0, 2);
        logs.push(new Chessboard_Log(board));

        // move a pawn
        p = board.getPieceAtCoordinate(1, 1);
        p.setCoordinate(1, 2);
        logs.push(new Chessboard_Log(board));

        // move a rook
        p = board.getPieceAtCoordinate(0, 0);
        p.setCoordinate(4, 4);
        // logs.push(new Chessboard_Log(board));

        // try undo once
        Chessboard_Log log = logs.pop();
        board = log.toChessboard();
        assertEquals("rook", board.getPieceAtCoordinate(0, 0).getPiece_name()); // rook should be restored
        assertEquals("pawn", board.getPieceAtCoordinate(1, 2).getPiece_name());
        assertEquals("pawn", board.getPieceAtCoordinate(0, 2).getPiece_name());
        assertEquals(null, board.getPieceAtCoordinate(0, 1));
        assertEquals(null, board.getPieceAtCoordinate(1, 1));

        // try undo twice
        log = logs.pop();
        board = log.toChessboard();
        assertEquals("rook", board.getPieceAtCoordinate(0, 0).getPiece_name()); // rook should be restored
        assertEquals(null, board.getPieceAtCoordinate(1, 2)); // this pawn should be put back, so nothing should be here
        assertEquals("pawn", board.getPieceAtCoordinate(0, 2).getPiece_name());
        assertEquals(null, board.getPieceAtCoordinate(0, 1));
        assertEquals("pawn", board.getPieceAtCoordinate(1, 1).getPiece_name()); // the pawn should be back

        // try undo three times
        log = logs.pop();
        board = log.toChessboard();
        assertEquals("rook", board.getPieceAtCoordinate(0, 0).getPiece_name()); // rook should be restored
        assertEquals(null, board.getPieceAtCoordinate(1, 2));
        assertEquals(null, board.getPieceAtCoordinate(0, 2)); // this pawn should be put back, so nothing should be here
        assertEquals("pawn", board.getPieceAtCoordinate(0, 1).getPiece_name());
        assertEquals("pawn", board.getPieceAtCoordinate(1, 1).getPiece_name()); // the pawn should be back

        // logs should be empty now
        assertEquals(true, logs.empty());

    }

}