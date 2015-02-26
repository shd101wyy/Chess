package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game{
    static JFrame frame;
    private ChessBoard board;    // chess board
    private GameController game_controller; // game controller
    private GameView game_view;  // game view
    private JFrame game_frame;  // game frame
    /**
     * Constructor: init game, set necessary properties.
     * @param board
     * @param game_controller
     * @param game_view
     * @param game_frame  game window(frame)
     */
    public Game(ChessBoard board, GameController game_controller, GameView game_view, JFrame game_frame){
        this.board = board;
        this.game_controller = game_controller;
        this.game_view = game_view;
        this.game_frame = game_frame;

        this.game_view.bindGameController(game_controller); // bind game controller to game view
    }

    /**
     *
     */
    public void setMode(String mode){
        if (mode.equals("classic")){
            this.board.generateStandardBoard();
        }
        else{
            this.board.generateFantasyBoard();
        }
    }

    public void startGame(){
        this.game_frame.add(this.game_view);        // draw canvas
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String [] args){
        // initialize Chessboard(model), game view, and game controller
        ChessBoard board;
        GameView game_view;
        GameController game_controller;
        board = new ChessBoard(8, 8); // create standard 8 x 8 chess board.
        game_view = new GameView(board, 64); // initialize game view
        game_controller = new GameController(board, game_view); // initialize game constroller

        // Initialize JFrame
        frame = new JFrame("Chess");  // init jframe object
        frame.getContentPane().setPreferredSize(new Dimension(8 * 64, 8 * 64));  // set height and width
        frame.setResizable(false);    // disable resizable
        frame.pack();
        frame.setVisible(true);       // set as visible
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set close operation

        // init game
        Game game = new Game(board, game_controller, game_view, frame);

        // set board mode
        game.setMode("classic");

        // start game
        game.startGame();




        // initialize standard 8 x 8 chess board
        // board.generateStandardBoard();;

        // initialize fantasy 8 x 8 chess board
        board.generateFantasyBoard();




    }
}