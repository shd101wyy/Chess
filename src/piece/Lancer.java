package piece;

import chess.ChessBoard;
import chess.Player;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/18/15.
 */
public class Lancer extends Piece{
    /**
     * Constructor: initialize an Lancer Object
     * @param board
     * @param player
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
     * add (x, y) to coords if there is enemy
     * @param x
     * @param y
     * @return
     */
    public void addToCoordIfThereIsEnemy(ArrayList<Coordinate> coords, int x, int y){
        if (x < 0 || x >= this.board.getWidth() || y < 0 || y >= this.board.getHeight()) // invalid coordinate
            return;
        Piece p = this.board.getPieceAtCoordinate(x, y); // get piece at that coord
        if (p == null) return; // empty
        if (p.player == this.player)
            return; // player's
        else // enemy
            coords.add(new Coordinate(x, y));
    }
    /**
     * Attack and Move coordinate:
     *
     *           #
     *         # # #                  #: is enemy
     *       # # p # #                 @: is possible move area
     *                                P: archer
     *  if there is enemy/friend on left/top/right that block the way, then archer cannot go further
     *
     * @return
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
