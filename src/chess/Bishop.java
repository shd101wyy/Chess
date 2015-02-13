package chess;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class Bishop extends Piece {
    private String image_path;
    public Bishop(ChessBoard board, int player){
        super("bishop", board, player);

        if(player == 1){  // White player
            this.setPiece_image_path("assets/white_bishop.png");
        }
        else{ // Black player
            this.setPiece_image_path("assets/black_bishop.png");
        }
    }

    public boolean move(int d_x, int d_y) {
        return false;
    }
}