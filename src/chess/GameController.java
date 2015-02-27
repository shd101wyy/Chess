package chess;
import chessboard.ChessBoard;
import chessboard.Chessboard_Log;
import piece.Coordinate;
import piece.King;
import piece.Piece;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/25/15.
 */

/**
 * Game Controller
 */
public class GameController {
    protected ChessBoard board;  // chess board
    protected GameView game_view; // game view
    protected Piece chosen_piece;  // the piece that is chosen by player
    protected boolean game_start; // game already starts?
    protected String player1_name; // player1 name
    protected String player2_name; // player2 name
    protected int player1_score; // player1 score
    protected int player2_score; // player2 score
    protected String message;    // game message
    Stack<Chessboard_Log> chessboard_history_log; // used to save move history

    /**
     * Constructor: initialize game controller
     * @param board
     * @param game_view
     */
    public GameController(ChessBoard board, GameView game_view){
        this.board = board;      // bind chessboard to current game controller
        this.game_view = game_view; // bind game view to current game controller
        this.chosen_piece = null; // no piece is chosen by player yet
        this.game_start = false;  // game is not started yet, need to click start button.
        this.player1_name = "WHITE"; // player1 name
        this.player2_name = "BLACK"; // player2 name
        this.player1_score = 0; // player1 score
        this.player2_score = 0; // player2 score
        this.message = "Press Start button to start the game"; // game message
        this.chessboard_history_log = null; // no piece move history yet
    }

    /**
     * Check whether player's king is in check
     * @param player check this player's king
     * @return true if king is in check.
     */
    public boolean playersKingIsInCheck(Player player){
        Piece king;
        if (player == Player.WHITE){
            king = this.board.getKing1();
        }
        else{
            king = this.board.getKing2();
        }
        return ((King)king).isInCheck();
    }

    /**
     * check whether is checkmate or stalemate
     * @return null if neither checkmate nor stalemate; return "checkmate" if checkmate; return "stalemate" if stalemate
     */
    public String isCheckmateOrStalemate(){
        /*
         * Check checkmate and stalemate
         */
        if (this.board.playerCannotMove(this.board.getTurns() % 2 == 0 ? Player.WHITE : Player.BLACK)){ // so right now that player cannot move any chess
            King king = (this.board.getTurns() % 2 == 0) ? (King)this.board.getKing1() : (King)this.board.getKing2();  // get current player's king
            if(king == null) // chessboard not initialized yet.
                return null;
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
     * Game is over
     * @param status
     */
    public void gameIsOver(String status){
        if(this.game_start == false) // game already over
            return;
        Player current_player = this.getPlayerForThisTurn(); // get current player
        this.game_start = false; // game not started now.
        if (status.equals("checkmate")){ // checkmate
            this.message = "Checkmate! "  + (current_player == Player.WHITE ? this.player2_name : player1_name) + " Win!!"; // reset message
            // update player score
            if (current_player == Player.WHITE){
                this.player2_score++;
            }
            else{
                this.player1_score++;
            }
        }
        else{ // stalemate
            this.message = "Stalemate!"; // reset message
        }

        // redraw menu
        this.game_view.menu_view.drawMenu(this.player1_score, this.player2_score, this.message);
    }

    /**
     * Return possible move coordinates for piece; eliminate suicide move
     *
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

                    // save current history log
                    Chessboard_Log log = new Chessboard_Log(this.board);
                    chessboard_history_log.push(log);

                    //  remove that opponent's piece
                    this.board.removePiece(opponent_piece);

                    // move player's piece to that coordinate
                    opponent_piece.removeSelf(); // remove opponent's piece

                    // move the chosen piece to coordinate of opponent's piece
                    this.chosen_piece.setCoordinate(coord.getX(), coord.getY());

                    // update turns and redraw the canvas
                    this.chosen_piece = null;
                    this.board.incrementTurns();
                    this.message = (this.playersKingIsInCheck(this.getPlayerForThisTurn()) ? "Check! " : "") +     // show king in check
                                   (this.getPlayerForThisTurn() == Player.WHITE ? this.player1_name : this.player2_name) + "'s turn"; // show which player's turn
                    this.updateMessage(this.message);
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

                // save current history log
                Chessboard_Log log = new Chessboard_Log(this.board);
                chessboard_history_log.push(log);


                // move player's piece to that coordinate
                this.chosen_piece.setCoordinate(x, y);

                // update turns and redraw the canvas
                this.chosen_piece = null;
                this.board.incrementTurns();
                this.message = (this.playersKingIsInCheck(this.getPlayerForThisTurn()) ? "Check! " : "") +     // show king in check
                        (this.getPlayerForThisTurn() == Player.WHITE ? this.player1_name : this.player2_name) + "'s turn"; // show which player's turn
                this.updateMessage(this.message);
                panel.repaint();
                return;
            }
        }
    }

    /**
     *
     * @return Player for this turn.
     */
    public Player getPlayerForThisTurn(){
        return this.board.getPlayerForThisTurn();
    }

    /**
     * Check user mouse click, and update gui.
     * @param g2d
     * @param clicked_x_coord
     * @param clicked_y_coord
     */
    public void checkUserClick(Graphics2D g2d, double clicked_x_coord, double clicked_y_coord){
        if(this.game_start == false){ // game is not started yet. so we don't need to check user mouse click.
            return;
        }
        int x, y;
        Piece p;
        /*
         * check mouse click.
         */
        if(clicked_x_coord >= 0 && clicked_y_coord >= 0) {     // valid click scope
            x = (int) (clicked_x_coord / 64);                  // convert to left-bottom chess board coordinate system
            y = this.board.getHeight() - 1 - (int) (clicked_y_coord / 64);
            p = this.board.getPieceAtCoordinate(x, y);

            /*
             *  Now we clicked a spot/piece
             *
             */
            if (p != null) { // player clicked a piece; show its possible moves
                if(p.getPlayer() == this.board.getPlayerForThisTurn()) { // player clicked his/her own piece
                    this.chosen_piece = p;       // save as chosen_piece
                    this.game_view.chessboard_view.drawPossibleMovesForPiece(g2d, this.showPossibelMovesForPiece(p)); // draw possible moves
                }
                else{ // player clicked opponent's piece
                    if(this.chosen_piece == null) // do nothing
                        return;
                    this.movePieceToOpponentPieceLocationIfValid(this.game_view, p);
                }
            }
            else if (this.chosen_piece != null) { // that means  p == null, and player clicked a tile that is not occupied
                this.movePlayerPieceToEmptyTileIfValid(this.game_view, x, y);
            }
        }
    }

    /**
     * Update game message
     * @param message
     */
    public void updateMessage(String message){
        // set message
        this.message = message;

        // redraw menu
        this.game_view.menu_view.drawMenu(this.player1_score, this.player2_score, this.message);
    }

    /**
     *
     * Start a new game
     * if game_mode equals 1 => fantasy mode; otherwise classic mode
     * @param game_mode
     */
    public void startNewGame(int game_mode){
        ChessBoard new_board = new ChessBoard(8, 8); // create new board;
        this.game_view.chessboard_view.clicked_x_coord = -1;  // reset click x coord
        this.game_view.chessboard_view.clicked_y_coord = -1;  // reset click y coord

        // rebind the chessboard to GameView, GameConstroller
        this.board = new_board;
        this.game_view.board = new_board;
        this.game_view.chessboard_view.board = new_board;

        if(game_mode == 2){ // classic mode
            this.board.generateStandardBoard();
            this.game_view.redraw();
        }
        else { // fantasy mode
            this.board.generateFantasyBoard();
            this.game_view.redraw();
        }


        this.chessboard_history_log = new Stack<Chessboard_Log>();   // init chessboard history log
        this.game_start = true; // start game
        this.message = "Have fun in game!!\n" + (this.player1_name) + "'s turn";
        this.game_view.menu_view.drawMenu(this.player1_score, this.player2_score, this.message); // redraw menu for game
    }

    /**
     * Player clicked start button
     */
    public void clickedStartButton(){
        if(this.game_start){ // game already started, so this func should do nothing
            JOptionPane.showMessageDialog(null, "Game already started");
            return;
        }
        // show game mode selection dialog
        Object[] options = {"Let me think..", "Fantasy", "Classic"};
        int chosen_option = JOptionPane.showOptionDialog(null,
                "Which game mode do you want to play",
                "Game Mode",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        if(chosen_option == 2 || chosen_option == 1){
            this.startNewGame(chosen_option);
        }
        else{
            return;
        }
    }

    /**
     * Player clicked restart button
     */
    public void clickedRestartButton(){
        if(this.game_start == false){ // game not started yet, cannot restart.
            JOptionPane.showMessageDialog(null, "Game not started");
            return;
        }
        int entry = JOptionPane.showConfirmDialog(null, this.player1_name + "! Do you want to restart the game?", "Please select", JOptionPane.YES_NO_OPTION);
        if(entry == JOptionPane.NO_OPTION) { // player1 doesn't agree to restart the game
            return;
        }
        entry = JOptionPane.showConfirmDialog(null, this.player2_name + "! Do you want to restart the game?", "Please select", JOptionPane.YES_NO_OPTION);
        if(entry == JOptionPane.NO_OPTION){ // player2 doesn't agree to restart the game
            return;
        }
        // show game mode selection dialog
        Object[] options = {"Let me think..", "Fantasy", "Classic"};
        int chosen_option = JOptionPane.showOptionDialog(null,
                "Which game mode do you want to play",
                "Game Mode",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
        if(chosen_option == 2 || chosen_option == 1){ // player chose classic mode or fantasy mode
            this.startNewGame(chosen_option);
        }
    }

    /**
     * Player clicked forfeit button
     */
    public void clickedForfeitButton(){
        if(this.game_start == false){ // game not started yet, cannot forfeit.
            JOptionPane.showMessageDialog(null, "Game not started");
            return;
        }
        Player current_player = this.getPlayerForThisTurn(); // get player for the turn
        int entry = JOptionPane.showConfirmDialog(null, (current_player == Player.WHITE ? this.player1_name : this.player2_name) + "! Do you want to give up the game?", "Please select", JOptionPane.YES_NO_OPTION);
        if (entry == JOptionPane.YES_OPTION){ // player wants to forfeit
            this.message = (current_player == Player.WHITE ? this.player2_name : player1_name) + " Win!!"; // reset message
            this.game_start = false; // game not started now.

            // update player score
            if (current_player == Player.WHITE){
                this.player2_score++;
            }
            else{
                this.player1_score++;
            }

            // redraw menu
            this.game_view.menu_view.drawMenu(this.player1_score, this.player2_score, this.message);
        }
    }

    /**
     * Player clicked undo button
     */
    public void clickedUndoButton(){
        if (chessboard_history_log.empty() || this.game_start == false){ // cannot undo
            JOptionPane.showMessageDialog(null, "Cannot undo");
            return;
        }
        Chessboard_Log log = chessboard_history_log.pop(); // get most recent log
        ChessBoard new_board = log.toChessboard(); // create new chessboard from log.

        // rebind the chessboard to GameView, GameConstroller
        this.board = new_board;
        this.game_view.board = new_board;
        this.game_view.chessboard_view.board = new_board;

        this.game_start = true; // start game
        this.message = (this.getPlayerForThisTurn() == Player.WHITE ? this.player1_name : this.player2_name) + "'s turn";
        this.game_view.menu_view.drawMenu(this.player1_score, this.player2_score, this.message); // redraw menu for game

        // redraw everything
        this.game_view.redraw();

    }

    /**
     * Update Player Name
     * @param btn
     */
    public void updatePlayerName(JButton btn){
        if(this.game_start == true){
            JOptionPane.showMessageDialog(null, "You cannot change your name during the game");
            return;
        }
        String s = (String)JOptionPane.showInputDialog(null, "Please new name for " + btn.getText());
        if (s != null && s.length() > 0){
            if(btn.getText().equals(this.player1_name)){ // update player1 name
                if(this.player2_name.equals(s)){ // invalid name, cuz player1 and player2 will have the same name
                    JOptionPane.showMessageDialog(null, "Invalid name: " + s);
                    return;
                }
                this.player1_name = s;
            }
            else{ // update player2 name
                if(this.player1_name.equals(s)){ // invalid name, cuz player1 and player2 will have the same name
                    JOptionPane.showMessageDialog(null, "Invalid name: " + s);
                    return;
                }
                this.player2_name = s;
            }
            btn.setText(s); // update player name
        }
    }
}
