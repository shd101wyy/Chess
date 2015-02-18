package piece;

import chess.ChessBoard;
import chess.Player;
import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class Knight extends Piece {
    /**
     * Constructor: initialize a Knight Object
     * @param board
     * @param player
     */
    public Knight(ChessBoard board, Player player) {
        super("knight", board, player);
        if (player == Player.WHITE) {  // White player
            this.setPiece_image_path("assets/white_knight.png");
        } else { // Black player
            this.setPiece_image_path("assets/black_knight.png");
        }
    }

    /**
     *  Get all possible move coordinates for this knight piece at current coordinate
     *
     *         @      @
     *   @                   @       P: this piece
     *             P                 @: Possible coordinates to move
     *   @                   @
     *         @       @

     * @return ArrayList<Coordinate> Object
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.x_coordinate;       // get current x coord of pawn
        int current_y_coord = this.y_coordinate;       // get current y coord of pawn
        ChessBoard board = this.getChessBoard();            // get chess board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList
        int x, y;
        /*
         several cases
                     2      3
               1                   4

               5                   8
                    6       7

         */
        // case1
        x = this.x_coordinate - 2;
        y = this.y_coordinate + 1;
        if(x >= 0 && y < board.getHeight() &&
                (board.getPieceAtCoordinate(x, y ) == null ||      // that square is not occupied by any piece
                board.getPieceAtCoordinate(x, y).player != this.player)){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(x, y));
        }
        // case2
        x = this.x_coordinate - 1;
        y = this.y_coordinate + 2;
        if(x >= 0 && y < board.getHeight() &&
                (board.getPieceAtCoordinate(x, y ) == null ||     // that square is not occupied by any piece
                        board.getPieceAtCoordinate(x, y).player != this.player)){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(x, y));
        }
        // case3
        x = this.x_coordinate + 1;
        y = this.y_coordinate + 2;
        if(x < board.getWidth() && y < board.getHeight() &&
                (board.getPieceAtCoordinate(x, y ) == null ||     // that square is not occupied by any piece
                        board.getPieceAtCoordinate(x, y).player != this.player)){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(x, y));
        }
        // case4
        x = this.x_coordinate + 2;
        y = this.y_coordinate + 1;
        if(x < board.getWidth() && y < board.getHeight() &&
                (board.getPieceAtCoordinate(x, y ) == null ||     // that square is not occupied by any piece
                        board.getPieceAtCoordinate(x, y).player != this.player)){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(x, y));
        }
        // case5
        x = this.x_coordinate - 2;
        y = this.y_coordinate - 1;
        if(x >= 0 && y >= 0 &&
                (board.getPieceAtCoordinate(x, y ) == null ||     // that square is not occupied by any piece
                        board.getPieceAtCoordinate(x, y).player != this.player)){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(x, y));
        }
        // case6
        x = this.x_coordinate - 1;
        y = this.y_coordinate - 2;
        if(x >= 0 && y >= 0 &&
                (board.getPieceAtCoordinate(x, y ) == null ||    // that square is not occupied by any piece
                        board.getPieceAtCoordinate(x, y).player != this.player)){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(x, y));
        }
        // case7
        x = this.x_coordinate + 1;
        y = this.y_coordinate - 2;
        if(x < board.getWidth() && y >= 0 &&
                (board.getPieceAtCoordinate(x, y ) == null ||   // that square is not occupied by any piece
                        board.getPieceAtCoordinate(x, y).player != this.player)){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(x, y));
        }
        // case1
        x = this.x_coordinate + 2;
        y = this.y_coordinate - 1;
        if(x < board.getWidth() && y >= 0 &&
                (board.getPieceAtCoordinate(x, y ) == null ||  // that square is not occupied by any piece
                        board.getPieceAtCoordinate(x, y).player != this.player)){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(x, y));
        }


        return coords;
    }
}