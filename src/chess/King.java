package chess;

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
    public boolean move(int d_x, int d_y) {
        return false;
    }
}