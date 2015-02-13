package chess;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class Pawn extends Piece {
    private boolean first_time_move;

    public Pawn(ChessBoard board, int player) {
        super("pawn", board, player);
        this.first_time_move = true;   // it is pawn's first move, so it can advance 2 squares now.
        if (player == 1) {  // White player
            this.setPiece_image_path("assets/white_pawn.png");
        } else { // Black player
            this.setPiece_image_path("assets/black_pawn.png");
        }
    }

    /**
     * Assume no promotion for pawn
     *
     * @return
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.getX_coordinate();       // get current x coord of pawn
        int current_y_coord = this.getY_coordinate();       // get current y coord of pawn
        ChessBoard board = this.getChessBoard();            // get chess board

        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList
        int possible_move = (this.getPlayer() == 1) ? 1 : -1;                // if White player, then move +1, otherwise move -1
        if (current_y_coord + possible_move >= board.getHeight() || current_y_coord + possible_move < 0) { // reach top/bottom
            return coords;
        }

        if (board.getPieceAtCoordinate(current_x_coord, current_y_coord + possible_move) != null) { // check piece in front
            // do nothing
        } else {
            coords.add(new Coordinate(current_x_coord, current_y_coord + possible_move));
        }
        if (this.first_time_move &&
                board.getPieceAtCoordinate(current_x_coord, current_y_coord + possible_move) == null &&
                board.getPieceAtCoordinate(current_x_coord, current_y_coord + possible_move * 2) == null) {   // first move, therefore it can advance two more steps
            coords.add(new Coordinate(current_x_coord, current_y_coord + possible_move * 2));
        }

        if (board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord + possible_move) != null &&
                board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord + possible_move).getPlayer() != this.getPlayer()) {  // there is an opponent piece on the left side
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord + possible_move));
        }

        if (board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord + possible_move) != null &&
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord + possible_move).getPlayer() != this.getPlayer()) {  // there is an opponent piece on the right side
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord + possible_move));
        }
        return coords;
    }

    public boolean setCoordinate(int x, int y) {
        if (this.getX_coordinate() == -1 || this.getY_coordinate() == -1) { // first time init
            this.first_time_move = true;
        } else {
            this.first_time_move = false;
        }
        return super.setCoordinate(x, y);
    }
}