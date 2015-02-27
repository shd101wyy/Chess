package piece;

import chessboard.ChessBoard;
import chess.Player;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/18/15.
 */

public class Archer extends Piece{

    /**
     * Constructor: initialize an Archor Object
     * @param board  the board we are currently using
     * @param player the player holds this piece
     */
    public Archer(ChessBoard board, Player player){
        super("archer", board, player);
        // set piece image path
        if(player == Player.WHITE){  // White player
            this.piece_image_path = "assets/white_archer.png";
        }
        else{ // Black player
            this.piece_image_path = "assets/black_archer.png";
        }
    }

    /**
     * add (x, y) to coords if there is enemy
     * @param x  the x coordinate
     * @param y  the y coordinate
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
     *         # # #                #: is enemy
     *       #   @   #              @: is possible move area
     *       # @ P @ #              P: archer
     *       #   @   #
     *         # # #
     *
     * @return ArrayList<Coordinate> Object that contains all possible move coordinates.
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate(){
        int current_x_coord = this.x_coordinate;       // get current x coord of pawn
        int current_y_coord = this.y_coordinate;       // get current y coord of pawn
        ChessBoard board = this.board;            // get chess board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList

        // check possible move coordinates;
        // if there is an enemy, then archer cannot move there.
        // check left
        if (current_x_coord >= 1 && board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord) == null)
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord));
        // check top
        if (current_y_coord + 1 < board.getHeight() &&
                board.getPieceAtCoordinate(current_x_coord, current_y_coord + 1) == null)
            coords.add(new Coordinate(current_x_coord, current_y_coord + 1));
        // check right
        if (current_x_coord + 1 < board.getWidth() &&
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord) == null)
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord));
        // check bottom
        if (current_y_coord - 1 >= 0 &&
                board.getPieceAtCoordinate(current_x_coord, current_y_coord - 1) == null)
            coords.add(new Coordinate(current_x_coord, current_y_coord - 1));

        // check attack area
        // check top three
        addToCoordIfThereIsEnemy(coords, current_x_coord - 1, current_y_coord + 2 );
        addToCoordIfThereIsEnemy(coords, current_x_coord, current_y_coord + 2 );
        addToCoordIfThereIsEnemy(coords, current_x_coord + 1, current_y_coord + 2 );

        // check left three
        addToCoordIfThereIsEnemy(coords, current_x_coord - 2, current_y_coord + 1 );
        addToCoordIfThereIsEnemy(coords, current_x_coord - 2, current_y_coord);
        addToCoordIfThereIsEnemy(coords, current_x_coord - 2, current_y_coord - 1 );

        // check bottom three
        addToCoordIfThereIsEnemy(coords, current_x_coord - 1, current_y_coord - 2 );
        addToCoordIfThereIsEnemy(coords, current_x_coord, current_y_coord - 2 );
        addToCoordIfThereIsEnemy(coords, current_x_coord + 1, current_y_coord - 2 );

        // check right three
        addToCoordIfThereIsEnemy(coords, current_x_coord + 2, current_y_coord + 1 );
        addToCoordIfThereIsEnemy(coords, current_x_coord + 2, current_y_coord);
        addToCoordIfThereIsEnemy(coords, current_x_coord + 2, current_y_coord - 1 );

        return coords;
    }
}
