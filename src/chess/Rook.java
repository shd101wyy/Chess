package chess;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class Rook extends Piece {
    /**
     * Constructor: initialize a Rook Object
     * @param board
     * @param player
     */
    public Rook(ChessBoard board, int player){
        super("rook", board, player);
        if(player == 1){  // White player
            this.setPiece_image_path("assets/white_rook.png");
        }
        else{ // Black player
            this.setPiece_image_path("assets/black_rook.png");
        }
    }

    /**
     *Get all possible move coordinates for this rook piece at current coordinate
     *
     *        @
     *        @          P: this piece
     *        @          @: Possible coordinates to move
     *  @ @ @ P @ @ @
     *        @
     *        @
     *        @
     *
     * @return ArrayList<Coordinate> Object
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.getX_coordinate();       // get current x coord of pawn
        int current_y_coord = this.getY_coordinate();       // get current y coord of pawn
        ChessBoard board = this.getChessBoard();            // get chess board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList
        int i;
        // check left
        for(i = current_x_coord - 1; i >= 0; i--){
            if(board.getPieceAtCoordinate(i, current_y_coord) == null){  // the square is not occupied by any piece
                coords.add(new Coordinate(i, current_y_coord));
            }
            else if (board.getPieceAtCoordinate(i, current_y_coord).getPlayer() != this.getPlayer()){  // meet opponent's piece
                coords.add(new Coordinate(i, current_y_coord));
                break;
            }
            else{ // meet player's own piece
                break;
            }
        }
        // check right
        for(i = current_x_coord + 1; i < board.getWidth(); i++){
            if(board.getPieceAtCoordinate(i, current_y_coord) == null){  // the square is not occupied by any piece
                coords.add(new Coordinate(i, current_y_coord));
            }
            else if (board.getPieceAtCoordinate(i, current_y_coord).getPlayer() != this.getPlayer()){  // meet opponent's piece
                coords.add(new Coordinate(i, current_y_coord));
                break;
            }
            else{ // meet player's own piece
                break;
            }
        }
        // check above
        for(i = current_y_coord + 1 ; i < board.getHeight(); i++){
            if(board.getPieceAtCoordinate(current_x_coord, i) == null){  // the square is not occupied by any piece
                coords.add(new Coordinate(current_x_coord, i));
            }
            else if (board.getPieceAtCoordinate(current_x_coord, i).getPlayer() != this.getPlayer()){  // meet opponent's piece
                coords.add(new Coordinate(current_x_coord, i));
                break;
            }
            else{ // meet player's own piece
                break;
            }
        }
        // check below
        for(i = current_y_coord - 1; i >= 0; i--){
            if(board.getPieceAtCoordinate(current_x_coord, i) == null){  // the square is not occupied by any piece
                coords.add(new Coordinate(current_x_coord, i));
            }
            else if (board.getPieceAtCoordinate(current_x_coord, i).getPlayer() != this.getPlayer()){  // meet opponent's piece
                coords.add(new Coordinate(current_x_coord, i));
                break;
            }
            else{ // meet player's own piece
                break;
            }
        }
        return coords;
    }
}
