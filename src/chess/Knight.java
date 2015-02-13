package chess;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class Knight extends Piece {
    public Knight(ChessBoard board, int player) {
        super("knight", board, player);
        if (player == 1) {  // White player
            this.setPiece_image_path("assets/white_knight.png");
        } else { // Black player
            this.setPiece_image_path("assets/black_knight.png");
        }
    }

    public boolean move(int d_x, int d_y) {
        return false;
    }

    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        return null;
    }
}