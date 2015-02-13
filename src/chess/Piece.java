package chess;

import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 *
 * Piece Class
 */
public class Piece {
    private String piece_name; // name of the piece: king, rook, bishop, queen, knight, pawn.

    // Assume left bottom corner is (0, 0)
    private int x_coordinate;  // piece x coordinate
    private int y_coordinate;  // piece y coordinate

    private int player;        // #player, 1 => White, 2 => Black
    private ChessBoard board;  // the current chessboard object

    private String piece_image_path; // piece image path

    /**
     * Initialize a piece object
     * @param piece_name set the piece name
     * @param board      the chess board where we put this piece
     * @param player     the player id
     */
    public Piece(String piece_name, ChessBoard board, int player){
        this.piece_name = piece_name;             // set the piece name
        this.x_coordinate = -1;                   // init coordinate to -1, which means the piece is not put to chess board yet
        this.y_coordinate = -1;
        this.board = board;                       // set the board
        if(player != 1 && player != 2){           // invalid player id
            System.out.println("ERROR: Invalid player #"+player);
            System.exit(0);
        }
        this.player = player;                     // set player id
    }

    /**
     * Set the coordinate of piece on chess board
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

    /**
     * Piece removes itself from chess board
     */
    public void removeSelf(){
        this.board.removePiece(this);
    }

    /**
     * ChessBoarder getter
     * @return board
     */
    public ChessBoard getChessBoard(){
        return this.board;
    }

    /**
     * Setter, set player
     * @param player
     */
    public void setPlayer(int player){
        this.player = player;
    }

    /**
     * Getter, return player
     * @return
     */
    public int getPlayer(){
        return this.player;
    }

    /**
     * Getter, return the x coordinate of this piece
     * @return
     */
    public int getX_coordinate(){
        return this.x_coordinate;
    }

    /**
     * Getter, return the y coordinate of this piece
     * @return
     */
    public int getY_coordinate(){
        return  this.y_coordinate;
    }

    /**
     * Getter, return the piece name of this piece
     * @return
     */
    public String getPiece_name(){
        return this.piece_name;
    }

    /**
     * Setter, set the piece image path of this piece
     * @param piece_image_path
     */
    public void setPiece_image_path(String piece_image_path){
        this.piece_image_path = piece_image_path;
    }

    /**
     * Getter, return piece image path of this piece
     * @return
     */
    public String getPiece_image_path(){
        return this.piece_image_path;
    }

    /**
     * Get possible move coordinates for this piece
     * As this function is implemented in each subclass, it will return null.
     * @return
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        return null;
    }
}
