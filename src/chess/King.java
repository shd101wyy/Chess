package chess;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class King extends Piece {
    /**
     * Constructor: Initialize a King Object
     * @param board
     * @param player
     */
    public King(ChessBoard board, int player){
        super("king", board, player);
        if(player == 1){  // White player
            this.setPiece_image_path("assets/white_king.png");
            if(board.getKing1() == null){
                board.setKing1(this);
            }
            else{
                System.out.println("ERROR: There are more than one king");
            }
        }
        else{ // Black player
            this.setPiece_image_path("assets/black_king.png");
            if(board.getKing2() == null){
                board.setKing2(this);
            }
            else{
                System.out.println("ERROR: There are more than one king");
            }
        }
    }

    /**
     * Get all possible move coordinates for this king piece at current coordinate
     *
     *                   P: this piece
     *                   @: Possible coordinates to move
     *     @ @ @
     *     @ P @
     *     @ @ @
     *
     * @return ArrayList<Coordinate> Object
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.getX_coordinate();       // get current x coord of pawn
        int current_y_coord = this.getY_coordinate();       // get current y coord of pawn
        ChessBoard board = this.getChessBoard();            // get chess board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList

        // check left top
        if(current_x_coord - 1 >= 0 &&
                current_y_coord + 1 < board.getHeight() &&
                (board.getPieceAtCoordinate(current_x_coord -1, current_y_coord + 1) == null ||                         // that square is not occupied by any piece
                board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord + 1).getPlayer() != this.getPlayer())){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord + 1));
        }

        // check top
        if(current_y_coord + 1 < board.getHeight() &&
                (board.getPieceAtCoordinate(current_x_coord , current_y_coord + 1) == null ||                           // that square is not occupied by any piece
                board.getPieceAtCoordinate(current_x_coord , current_y_coord + 1).getPlayer() != this.getPlayer())){    // or the square is occupied by opponent's piece
            coords.add(new Coordinate(current_x_coord, current_y_coord + 1));
        }

        // check right top
        if(current_x_coord + 1 < board.getWidth() &&
                current_y_coord + 1 < board.getHeight() &&
                (board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord + 1) == null ||                        // that square is not occupied by any piece
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord + 1).getPlayer() != this.getPlayer())){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord + 1));
        }

        // check left
        if(current_x_coord - 1 >= 0 &&
                (board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord) == null ||                           // that square is not occupied by any piece
                board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord).getPlayer() != this.getPlayer())){    // or the square is occupied by opponent's piece
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord));
        }

        // check right
        if(current_x_coord + 1 < board.getWidth() &&
                (board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord) == null ||                          // that square is not occupied by any piece
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord).getPlayer() != this.getPlayer())){  // or the square is occupied by opponent's piece
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord ));
        }

        // check right bottom
        if(current_x_coord - 1 >= 0 &&
                current_y_coord - 1 >= 0 &&
                (board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord - 1) == null ||                        // that square is not occupied by any piece
                board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord - 1).getPlayer() != this.getPlayer())){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord - 1));
        }

        // check bottom
        if(current_y_coord - 1 >= 0 &&
                (board.getPieceAtCoordinate(current_x_coord, current_y_coord - 1) == null ||                          // that square is not occupied by any piece
                board.getPieceAtCoordinate(current_x_coord, current_y_coord - 1).getPlayer() != this.getPlayer())){   // or the square is occupied by opponent's piece
            coords.add(new Coordinate(current_x_coord, current_y_coord - 1));
        }

        // check right bottom
        if(current_x_coord + 1 < board.getWidth() &&
                current_y_coord - 1 >= 0 &&
                (board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord - 1) == null ||                        // that square is not occupied by any piece
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord - 1).getPlayer() != this.getPlayer())){ // or the square is occupied by opponent's piece
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord - 1));
        }

        return coords;
    }

    /**
     * Check whether the king is in check
     * @return
     */
    public boolean isInCheck(){
        ChessBoard board = this.getChessBoard();   // get current chess board
        int i, j;
        for(i = 0; i < board.getWidth(); i++){
            for(j = 0; j < board.getHeight(); j++){
                Piece p = board.getPieceAtCoordinate(i, j);   // get piece at that coordinate
                if(p != null && p.getPlayer() != this.getPlayer()){ // it is opponent
                    ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate();
                    for(Coordinate coord : coords){
                        if (coord.getX() == this.getX_coordinate() && coord.getY() == this.getY_coordinate()){  // opponent's next move could reach the king
                            return true; // is in check
                        }
                    }
                }
            }
        }
        return false;
    }


}