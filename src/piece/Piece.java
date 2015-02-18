package piece;

import chess.ChessBoard;
import chess.Player;
import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 *
 * Piece Class
 */
public abstract class Piece {
    protected String piece_name; // name of the piece: king, rook, bishop, queen, knight, pawn.

    // Assume left bottom corner is (0, 0)
    protected int x_coordinate;  // piece x coordinate
    protected int y_coordinate;  // piece y coordinate

    protected Player player;     // white and black
    protected ChessBoard board;  // the current chessboard object

    protected String piece_image_path; // piece image path

    /**
     * Initialize a piece object
     * @param piece_name set the piece name
     * @param board      the chess board where we put this piece
     * @param player     the player id
     */
    public Piece(String piece_name, ChessBoard board, Player player){
        this.piece_name = piece_name;             // set the piece name
        this.x_coordinate = -1;                   // init coordinate to -1, which means the piece is not put to chess board yet
        this.y_coordinate = -1;
        this.board = board;                       // set the board
        this.player = player;                     // set player id
        this.board.addPieceToList(this);          // add piece to piece list
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
        if(this.x_coordinate != -1 && this.y_coordinate != -1) {  // piece is not just initialized.
            this.removeSelf(); // remove self from current coordinate
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
        this.x_coordinate = -1; // clear coordinate
        this.y_coordinate = -1;
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
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Getter, return player
     * @return
     */
    public Player getPlayer(){
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
     * Given coordinate (x, y), check whether the piece can move there.
     * If the piece can move to that coordinate, save (x, y) to coords.
     * If the piece cannot move anywhere further after reach that coordinate, like there is an another piece there, which blocks the way => return true; otherwise return false
     * @param coords
     * @param x
     * @param y
     * @return
     */
    public boolean addToCoordinatesIfValid(ArrayList<Coordinate> coords, int x, int y){
        if(this.board.getPieceAtCoordinate(x, y) == null){     // the square is not occupied by any piece
            coords.add(new Coordinate(x, y));
            return false;
        }
        else if(this.board.getPieceAtCoordinate(x, y).player != this.player) {  // meet opponent's piece
            coords.add(new Coordinate(x, y));
            return true;
        }
        else  // meet player's own piece
            return true;
    }
    /**
     * Get possible move coordinates for this piece
     * As this function is implemented in each subclass, it will return null.
     * @return
     */
    public abstract ArrayList<Coordinate> getPossibleMoveCoordinate();
}
