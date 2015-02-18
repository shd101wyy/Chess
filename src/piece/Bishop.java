package piece;

import chess.ChessBoard;
import chess.Player;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */

public class Bishop extends Piece {
    /**
     * Constructor: initialize a Bishop Object
     * @param board
     * @param player
     */
    public Bishop(ChessBoard board, Player player){
        super("bishop", board, player);

        // set piece image path
        if(player == Player.WHITE){  // White player
            this.piece_image_path = "assets/white_bishop.png";
        }
        else{ // Black player
            this.piece_image_path = "assets/black_bishop.png";
        }
    }


    /**
     * Get all possible move coordinates for this bishop piece at current coordinate
     * @         @
     *  @       @          P: this piece
     *   @     @           @: Possible coordinates to move
     *    @   @
     *     @ @
     *      P
     *     @ @
     *    @   @
     *   @     @
     *  @       @
     *
     *
     * @return ArrayList<Coordinate> Object
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.x_coordinate;       // get current x coord of pawn
        int current_y_coord = this.y_coordinate;       // get current y coord of pawn
        ChessBoard board = this.board;            // get chess board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList

        int i, j;
        // go direction of left top
        for(i = current_x_coord - 1, j = current_y_coord + 1; i >= 0 && j < board.getHeight(); i--, j++){
            if(addToCoordinatesIfValid(coords, i, j)) // add to coords if valid; if this return true, then it meets other pieces.
                break;
        }
        // go direction of right top
        for(i = current_x_coord + 1, j = current_y_coord + 1; i < board.getWidth() && j < board.getHeight(); i++, j++){
            if(addToCoordinatesIfValid(coords, i, j)) // add to coords if valid; if this return true, then it meets other pieces.
                break;
        }
        // go direction of left bottom
        for(i = current_x_coord - 1, j = current_y_coord - 1; i >= 0 && j >= 0; i--, j--){
            if(addToCoordinatesIfValid(coords, i, j)) // add to coords if valid; if this return true, then it meets other pieces.
                break;
        }
        // go direction of right bottom
        for(i = current_x_coord + 1, j = current_y_coord - 1; i < board.getWidth() && j >= 0; i++, j--){
            if(addToCoordinatesIfValid(coords, i, j)) // add to coords if valid; if this return true, then it meets other pieces.
                break;
        }
        return  coords;
    }
}