package piece;

import chessboard.ChessBoard;
import chessboard.Player;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/18/15.
 */
public class Lancer extends Piece{
    /**
     * Constructor: initialize an Lancer Object
     * @param board the board that we are currently using
     * @param player the player that holds the piece
     */
    public Lancer(ChessBoard board, Player player){
        super("lancer", board, player);
        // set piece image path
        if(player == Player.WHITE){  // White player
            this.piece_image_path = "assets/white_lancer.png";
        }
        else{ // Black player
            this.piece_image_path = "assets/black_lancer.png";
        }
    }

    /**
     * Attack and Move coordinate:
     *
     *           #
     *         # # #                  #: possible moves
     *       # # p # #                P: lancer
     *
     *  if there is enemy/friend on left/top/right that block the way, then archer cannot go further
     *
     * @return ArrayList<Coordinate> Object that contains all possible move coordinates.
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate(){
        int current_x_coord = this.x_coordinate;       // get current x coord of pawn
        int current_y_coord = this.y_coordinate;       // get current y coord of pawn
        ChessBoard board = this.board;            // get chess board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList

        int i = (this.player == Player.WHITE) ? 1 : -1;
        // check area

        if(addToCoordinatesIfValid(coords, current_x_coord - 1, current_y_coord) == false) // check no block way
            addToCoordinatesIfValid(coords, current_x_coord - 2, current_y_coord);
        if(addToCoordinatesIfValid(coords, current_x_coord + 1, current_y_coord) == false) // check no block way
            addToCoordinatesIfValid(coords, current_x_coord + 2, current_y_coord);
        addToCoordinatesIfValid(coords, current_x_coord - 1, current_y_coord + 1*i);
        addToCoordinatesIfValid(coords, current_x_coord + 1, current_y_coord + 1*i);
        if(addToCoordinatesIfValid(coords, current_x_coord, current_y_coord + 1*i) == false) // check no block way
            addToCoordinatesIfValid(coords, current_x_coord, current_y_coord + 2*i);

        return coords;
    }
}
