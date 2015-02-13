package chess;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class King extends Piece {
    public King(ChessBoard board, int player){
        super("king", board, player);
        if(player == 1){  // White player
            this.setPiece_image_path("assets/white_king.png");
        }
        else{ // Black player
            this.setPiece_image_path("assets/black_king.png");
        }
    }

    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.getX_coordinate();       // get current x coord of pawn
        int current_y_coord = this.getY_coordinate();       // get current y coord of pawn
        ChessBoard board = this.getChessBoard();            // get chess board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList

        // check left top
        if(current_x_coord - 1 >= 0 &&
                current_y_coord + 1 < board.getHeight() &&
                (board.getPieceAtCoordinate(current_x_coord -1, current_y_coord + 1) == null ||
                board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord + 1).getPlayer() != this.getPlayer())){
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord + 1));
        }

        // check top
        if(current_y_coord + 1 < board.getHeight() &&
                (board.getPieceAtCoordinate(current_x_coord , current_y_coord + 1) == null ||
                board.getPieceAtCoordinate(current_x_coord , current_y_coord + 1).getPlayer() != this.getPlayer())){
            coords.add(new Coordinate(current_x_coord, current_y_coord + 1));
        }

        // check right top
        if(current_x_coord + 1 < board.getWidth() &&
                current_y_coord + 1 < board.getHeight() &&
                (board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord + 1) == null ||
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord + 1).getPlayer() != this.getPlayer())){
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord + 1));
        }

        // check left
        if(current_x_coord - 1 >= 0 &&
                (board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord) == null ||
                board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord).getPlayer() != this.getPlayer())){
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord));
        }

        // check right
        if(current_x_coord + 1 < board.getWidth() &&
                (board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord) == null ||
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord).getPlayer() != this.getPlayer())){
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord ));
        }

        // check right bottom
        if(current_x_coord - 1 >= 0 &&
                current_y_coord - 1 >= 0 &&
                (board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord - 1) == null ||
                board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord - 1).getPlayer() != this.getPlayer())){
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord - 1));
        }

        // check bottom
        if(current_y_coord - 1 >= 0 &&
                (board.getPieceAtCoordinate(current_x_coord, current_y_coord - 1) == null ||
                board.getPieceAtCoordinate(current_x_coord, current_y_coord - 1).getPlayer() != this.getPlayer())){
            coords.add(new Coordinate(current_x_coord, current_y_coord - 1));
        }

        // check right bottom
        if(current_x_coord + 1 < board.getWidth() &&
                current_y_coord - 1 >= 0 &&
                (board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord - 1) == null ||
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord - 1).getPlayer() != this.getPlayer())){
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord - 1));
        }

        return coords;
    }
}