package chess;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */
//import chess.ChessBoard;
public class Piece {
    private String piece_name; // name of the piece: king, rook, bishop, queen, knight, pawn.

    // Assume left bottom corner is (0, 0)
    private int x_coordinate;  // piece x coordinate
    private int y_coordinate;  // piece y coordinate

    private int player;        // #player
    private ChessBoard board;

    private String piece_image_path; // piece image path

    /**
     * Initialize a piece object
     * @param piece_name set the piece name
     * @param board      the chess board where we put this piece
     */
    public Piece(String piece_name, ChessBoard board, int player){
        // String [] valid_piece_name = {"king", "rook", "bishop", "queen", "knight", "pawn"}; // list valid piece name
        this.piece_name = piece_name;
        this.x_coordinate = -1;
        this.y_coordinate = -1;
        this.board = board;
        this.player = player;
    }

    /**
     *
     * @param x         the x coordinate to put the piece
     * @param y         the y coordinate to put the piece
     * @return if can put the piece at that coordinate, return true; otherwise return true.
     */
    public boolean setCoordinate(int x, int y){
        if(x < 0 || x >= this.board.getWidth() || y < 0 || y >= this.board.getHeight() || this.board.getPieceAtCoordinate(x, y) != null){ // invalid coordinate
            return false;
        }
        // set coordinate
        this.x_coordinate = x;
        this.y_coordinate = y;
        // save this piece to board
        this.board.setPieceAtCoordinate(this, x, y);
        return true;
    }

    public void removeSelf(){
        this.board.removePiece(this);
    }

    public ChessBoard getChessBoard(){
        return this.board;
    }

    public void setPlayer(int player){
        this.player = player;
    }

    public int getPlayer(){
        return this.player;
    }

    public int getX_coordinate(){
        return this.x_coordinate;
    }

    public int getY_coordinate(){
        return  this.y_coordinate;
    }

    public void setXY(int x, int y){
        this.x_coordinate = x;
        this.y_coordinate = y;
    }

    public void setX_coordinate(int x){
        this.x_coordinate = x;
    }

    public void setY_coordinate(int y){
        this.y_coordinate = y;
    }

    public String getPiece_name(){
        return this.piece_name;
    }

    public void setPiece_image_path(String piece_image_path){
        this.piece_image_path = piece_image_path;
    }

    public String getPiece_image_path(){
        return this.piece_image_path;
    }
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        return null;
    }
}
