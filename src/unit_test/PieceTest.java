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
    /**
     * Test whether the piece image path is correct
     */
    @Test
    public void testGetPiece_image_path() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);  // create chess board

        Piece p;

        // test player 1
        p = new King(board, 1);
        assertEquals(p.getPiece_image_path(), "assets/white_king.png");

        p = new Bishop(board, 1);
        assertEquals(p.getPiece_image_path(), "assets/white_bishop.png");

        p = new Knight(board, 1);
        assertEquals(p.getPiece_image_path(), "assets/white_knight.png");

        p = new Pawn(board, 1);
        assertEquals(p.getPiece_image_path(), "assets/white_pawn.png");

        p = new Queen(board, 1);
        assertEquals(p.getPiece_image_path(), "assets/white_queen.png");

        p = new Rook(board, 1);
        assertEquals(p.getPiece_image_path(), "assets/white_rook.png");


        // test player 2
        p = new King(board, 2);
        assertEquals(p.getPiece_image_path(), "assets/black_king.png");

        p = new Bishop(board, 2);
        assertEquals(p.getPiece_image_path(), "assets/black_bishop.png");

        p = new Knight(board, 2);
        assertEquals(p.getPiece_image_path(), "assets/black_knight.png");

        p = new Pawn(board, 2);
        assertEquals(p.getPiece_image_path(), "assets/black_pawn.png");

        p = new Queen(board, 2);
        assertEquals(p.getPiece_image_path(), "assets/black_queen.png");

        p = new Rook(board, 2);
        assertEquals(p.getPiece_image_path(), "assets/black_rook.png");
    }

    /**
     * Test when call setCoordinate on a invalid coordinate, will false be returned ?
     * @throws Exception
     */
    @Test
    public void testSetCoordinate() throws Exception{
        ChessBoard board = new ChessBoard(8, 8);

        Piece p;
        boolean result;

        p = new King(board, 1);

        /*      1 |   2          | 3
         *    -----------------------
         *        |              |
         *     4  | valid area   | 5
         *        |              |
         *    -----------------------
         *     6  |   7          | 8
         */

        // check case 1
        result = p.setCoordinate(-1, 9); // outside the boundary;
        assertEquals(result, false);

        // check case 2
        result = p.setCoordinate(1, 8); // outside the boundary;
        assertEquals(result, false);

        // check case 3
        result = p.setCoordinate(10, 10); // outside the boundary;
        assertEquals(result, false);

        // check case 4
        result = p.setCoordinate(-1, 0); // outside the boundary;
        assertEquals(result, false);

        // check case 5
        result = p.setCoordinate(9, 4); // outside the boundary;
        assertEquals(result, false);

        // check case 6
        result = p.setCoordinate(-1, -1); // outside the boundary;
        assertEquals(result, false);

        // check case 7
        result = p.setCoordinate(4, -1); // outside the boundary;
        assertEquals(result, false);

        // check case 8
        result = p.setCoordinate(9, -4); // outside the boundary;
        assertEquals(result, false);


        // check valid area
        result = p.setCoordinate(0, 0); // inside valid area
        assertEquals(result, true);

        result = p.setCoordinate(7, 7); // inside valid area
        assertEquals(result, true);

        result = p.setCoordinate(6, 3); // inside valid area
        assertEquals(result, true);

        result = p.setCoordinate(2, 2); // inside valid area
        assertEquals(result, true);


        // create new piece, test whether we can put it at p's location
        Piece p2 = new King(board, 2);
        result = p2.setCoordinate(2, 2);  // this should return false
        assertEquals(result, false);

        // put p2 in a valid place
        result = p2.setCoordinate(2, 3);
        assertEquals(result, true);

        result = p2.setCoordinate(0, 0);
        assertEquals(result, true);
    }

    /**
     * test whether a piece can remove itself correctly
     * @throws Exception
     */
    @Test
    public void testRemoveSelf() throws Exception {
        ChessBoard board = new ChessBoard(8, 8);

        Piece p = new King(board, 1);
        p.setCoordinate(0, 0);

        p.removeSelf();      // piece removes itself from the chess board
        assertEquals(p.getX_coordinate(), -1);  // coordinate should be reset to -1
        assertEquals(p.getY_coordinate(), -1);

        /* the piece should be found on board after we remove it */
        boolean find = false;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (board.getPieceAtCoordinate(i, j) == p){
                    find = true;
                    break;
                }
            }
        }
        assertEquals(find, false); // piece should not be found from the chess board
    }


}