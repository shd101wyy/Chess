package chess;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class Rook extends Piece {
    public Rook(ChessBoard board, int player){
        super("rook", board, player);
        if(player == 1){  // White player
            this.setPiece_image_path("assets/white_rook.png");
        }
        else{ // Black player
            this.setPiece_image_path("assets/black_rook.png");
        }
    }

    public boolean move(int d_x, int d_y) {
        return false;
    }
}
