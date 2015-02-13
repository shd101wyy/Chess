package chess;

import java.util.ArrayList;

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


    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.getX_coordinate();       // get current x coord of pawn
        int current_y_coord = this.getY_coordinate();       // get current y coord of pawn
        ChessBoard board = this.getChessBoard();            // get chess board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList

        int i, j;
        // go direction of left top
        for(i = current_x_coord - 1, j = current_y_coord + 1; i >= 0 && j < board.getHeight(); i--, j++){
            if(board.getPieceAtCoordinate(i, j) == null){
                coords.add(new Coordinate(i, j));
            }
            else if(board.getPieceAtCoordinate(i, j).getPlayer() != this.getPlayer()) {
                coords.add(new Coordinate(i, j));
                break;
            }
        }
        // go direction of right top
        for(i = current_x_coord + 1, j = current_y_coord + 1; i < board.getWidth() && j < board.getHeight(); i++, j++){
            if(board.getPieceAtCoordinate(i, j) == null){
                coords.add(new Coordinate(i, j));
            }
            else if(board.getPieceAtCoordinate(i, j).getPlayer() != this.getPlayer()){
                coords.add(new Coordinate(i, j));
                break;
            }
        }
        // go direction of left bottom
        for(i = current_x_coord - 1, j = current_y_coord - 1; i >= 0 && j >= 0; i--, j--){
            if(board.getPieceAtCoordinate(i, j) == null){
                coords.add(new Coordinate(i, j));
            }
            else if(board.getPieceAtCoordinate(i, j).getPlayer() != this.getPlayer()){
                coords.add(new Coordinate(i, j));
                break;
            }
        }
        // go direction of right bottom
        for(i = current_x_coord + 1, j = current_y_coord - 1; i < board.getWidth() && j >= 0; i++, j--){
            if(board.getPieceAtCoordinate(i, j) == null){
                coords.add(new Coordinate(i, j));
            }
            else if(board.getPieceAtCoordinate(i, j).getPlayer() != this.getPlayer()){
                coords.add(new Coordinate(i, j));
                break;
            }
        }
        return  coords;
    }
}