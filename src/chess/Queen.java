package chess;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class Queen extends Piece {
    public Queen(ChessBoard board, int player){
        super("queen", board, player);
        if(player == 1){  // White player
            this.setPiece_image_path("assets/white_queen.png");
        }
        else{ // Black player
            this.setPiece_image_path("assets/black_queen.png");
        }
    }
    public boolean move(int d_x, int d_y) {
        return false;
    }
}
