package chess;
import piece.Coordinate;
import piece.King;
import piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/25/15.
 */
public class GameController {
    protected ChessBoard board;  // chess board
    protected GameView game_view; // game view
    protected Piece chosen_piece;  // the piece that is chosen by player

    /**
     * Constructor: initialize game controller
     * @param board
     * @param game_view
     */
    public GameController(ChessBoard board, GameView game_view){
        this.board = board;
        this.game_view = game_view;
        this.chosen_piece = null; // no piece is chosen by player yet
    }

    /**
     * check whether is checkmate or stalemate
     * @return null if neither checkmate nor stalemate; return "checkmate" if checkmate; return "stalemate" if stalemate
     */
    public String isCheckmateOrStalemate(){
        /*
         * Check checkmate and stalemate
         */
        if (this.board.playerCannotMove(this.board.turns % 2 == 0 ? Player.WHITE : Player.BLACK)){ // so right now that player cannot move any chess
            King king = (this.board.turns % 2 == 0) ? (King)this.board.king1 : (King)this.board.king2;  // get current player's king
            // this.gameover = true;
            if(king.isInCheck()){ // checkmate
                return "checkmate";
            }
            else{ // stalemate
                return "stalemate";
            }
        }
        return null;
    }

    /**
     * Return possible move coordinates for piece; eliminate suicide move
     * @param p the piece we want to move.
     * @return coordinate lists
     */
    ArrayList<Coordinate> showPossibelMovesForPiece(Piece p){
        ArrayList<Coordinate> return_coords = new ArrayList<Coordinate>();
        ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate(); // get all possible move coordinates for this choesn piece
        Color color;
        int x, y;
        for (Coordinate coord : coords) {
            if (this.chosen_piece.isSuicideMove(coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                continue;
            }

            // add this coord to return_coords
            return_coords.add(coord);
        }
        return return_coords;
    }

    /**
     * Player's piece captures opponent piece
     *
     * If it can be done, redraw the chessboard; otherwise do nothing
     * @param panel
     * @param opponent_piece             opponent piece
     */
    public void movePieceToOpponentPieceLocationIfValid(JPanel panel, Piece opponent_piece){
        // check whether opponent's piece is under capture scope
        ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
        if(coords != null){
            for(Coordinate coord : coords){
                if (this.chosen_piece.isSuicideMove(coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                    continue;
                }
                if (coord.getX() == opponent_piece.getX_coordinate() && coord.getY() == opponent_piece.getY_coordinate()){ // opponent's piece is captured
                    // System.out.println("You captured a piece");

                    /*  remove that opponent's piece */
                    this.board.removePiece(opponent_piece);

                    /* move player's piece to that coordinate */
                    opponent_piece.removeSelf(); // remove opponent's piece
                    //if(opponent_piece.getPiece_name().equals("king")){ // check whether game over
                    //    game_over(panel); // game over
                    //}
                    this.chosen_piece.setCoordinate(coord.getX(), coord.getY());

                                /* update turns and redraw the canvas */
                    this.chosen_piece = null;
                    this.board.turns++;
                    panel.repaint();
                    return;
                }
            }
        }
        else{
            // nothing happend here
        }
    }

    /**
     * Move player's piece to unoccupied tile if valid, which means the move is not a suicide move
     * @param panel
     * @param x     the x coord to move to
     * @param y     the y coord to move to
     */
    public void movePlayerPieceToEmptyTileIfValid(JPanel panel, int x, int y){
        ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
        for(Coordinate coord : coords){
            if (this.chosen_piece.isSuicideMove(coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                continue;
            }
            if (coord.getX() == x && coord.getY() == y){ // player can move the piece there
                // System.out.println("You moved a piece");

                // move player's piece to that coordinate
                this.chosen_piece.setCoordinate(x, y);

                // update turns and redraw the canvas
                this.chosen_piece = null;
                this.board.turns++;
                panel.repaint();
                return;
            }
        }
    }
    /**
     * Check user mouse click, and update gui.
     * @param g2d
     * @param clicked_x_coord
     * @param clicked_y_coord
     */
    public void checkUserClick(Graphics2D g2d, double clicked_x_coord, double clicked_y_coord){
        int x, y;
        Piece p;
        /*
         * check mouse click.
         */
        if(clicked_x_coord >= 0 && clicked_y_coord >= 0) {     // valid click scope
            x = (int) (clicked_x_coord / 64);                  // convert to left-bottom chess board coordinate system
            y = this.board.height - 1 - (int) (clicked_y_coord / 64);
            p = this.board.getPieceAtCoordinate(x, y);

            /*
             *  Now we clicked a piece
             *
             */
            if (p != null) { // player clicked a piece; show its possible moves
                if(p.getPlayer() == this.board.getPlayerForThisTurn()) { // player clicked his/her own piece
                    this.chosen_piece = p;       // save as chosen_piece
                    this.game_view.drawPossibleMovesForPiece(g2d, this.showPossibelMovesForPiece(p)); // draw possible moves
                }
                else{ // player clicked opponent's piece
                    if(this.chosen_piece == null) // do nothing
                        return;
                    this.movePieceToOpponentPieceLocationIfValid(this.game_view.panel, p);
                }
            }
            else if (this.chosen_piece != null) { // that means  p == null, and player clicked a tile that is not occupied
                this.movePlayerPieceToEmptyTileIfValid(this.game_view.panel, x, y);
            }
        }
    }
}
