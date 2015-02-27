package chessboard;

/**
 * Created by wangyiyi on 2/27/15.
 */

import chess.Player;
import chessboard.ChessBoard;
import piece.*;

/**
 * This class is used to save chessboard piece move history
 */
public class Chessboard_Log {
    private int width;  // width of chessboard
    private int height; // height of chessboard
    private String tiles[][]; // 2d array used to store piece information
    private int turns; // player turn
    /**
     * Generate chessboard log according to board
     * @param board
     */
    public Chessboard_Log(ChessBoard board){
        this.width = board.width; // set width
        this.height = board.height; // set height
        this.turns = board.turns; // save turns

        // initialize this.tiles.
        // initialize the 2d array to store pieces
        this.tiles = new String[height][];
        for(int i = 0; i < height; i++) {
            this.tiles[i] = new String[width];
        }

        // copy piece status
        // I will store piece information in format of color+name
        // eg: "white_rook", "black_queen"
        // pawn is the special case, I also need to store the first_time_move flag for it.
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Piece p = board.getPieceAtCoordinate(j, i);
                if (p == null) // not a piece
                    continue;
                String piece_name = p.getPiece_name(); // get piece name
                if(piece_name.equals("pawn")){ // check pawn first time move
                    if(((Pawn)p).isFirstTimeMove() == true){
                        this.tiles[i][j] = (p.getPlayer() == Player.WHITE ? "white_" : "black_") + "pawn_first";
                    }
                    else{
                        this.tiles[i][j] = (p.getPlayer() == Player.WHITE ? "white_" : "black_") + "pawn_not_first";
                    }
                }
                else{
                    this.tiles[i][j] = (p.getPlayer() == Player.WHITE ? "white_" : "black_") + piece_name; // save piece name
                }
            }
        }
    }

    /**
     * Generate chessboard according to log
     * @return a chessboard object
     */
    public ChessBoard toChessboard() {
        ChessBoard board = new ChessBoard(this.width, this.height); // create new chessboard object
        board.setTurns(this.turns); // set turns
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.height; j++) {
                int x = j; // get x coord
                int y = i; // get y coord
                String piece_name = this.tiles[i][j]; // get piece name
                if (piece_name == null){ // not a piece
                    continue;
                }
                Piece p;
                String color = piece_name.substring(0, 5); // get color for that piece
                piece_name = piece_name.substring(6);      // get piece name
                Player player = (color.equals("white") ? Player.WHITE : Player.BLACK); // get player color

                // I tried to use switch here, but it seems that my java doesn't support it
                if (piece_name.equals("pawn_first")) {
                    p = new Pawn(board, player);
                    p.setCoordinate(x, y);
                    ((Pawn) p).setFirst_time_move(true);
                } else if (piece_name.equals("pawn_not_first")) {
                    p = new Pawn(board, player);
                    p.setCoordinate(x, y);
                    ((Pawn) p).setFirst_time_move(false);
                } else if (piece_name.equals("archer")) {
                    p = new Archer(board, player);
                    p.setCoordinate(x, y);
                } else if (piece_name.equals("bishop")) {
                    p = new Bishop(board, player);
                    p.setCoordinate(x, y);
                } else if (piece_name.equals("king")) {
                    p = new King(board, player);
                    p.setCoordinate(x, y);
                } else if (piece_name.equals("knight")) {
                    p = new Knight(board, player);
                    p.setCoordinate(x, y);
                } else if (piece_name.equals("lancer")) {
                    p = new Lancer(board, player);
                    p.setCoordinate(x, y);
                } else if (piece_name.equals("queen")) {
                    p = new Queen(board, player);
                    p.setCoordinate(x, y);
                } else {
                    p = new Rook(board, player);
                    p.setCoordinate(x, y);
                }
            }
        }

        return board;
    }
}
