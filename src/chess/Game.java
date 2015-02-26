package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game extends JPanel{
    static JFrame frame;
    static ChessBoard board;    // chess board
    static GameController game_controller; // game controller
    static GameView game_view;  // game view
    static Game game_panel;         // game
    // used to store the coordinate of mouse click
    double clicked_x_coord = -1;
    double clicked_y_coord = -1;
    public Game(){
        /**
         * Mouse press event
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                // save mouse click (x, y) coordinate
                clicked_x_coord = e.getPoint().getX();
                clicked_y_coord = e.getPoint().getY();
                // System.out.println("X: " + clicked_x_coord + " Y: " + clicked_y_coord);
                repaint(); // this will call this.paint function
            }
        });
    }

    /**
     * Draw images on canvas
     * @param g
     */
    @Override
    public void paint(Graphics g){
        // System.out.println("paint");
        Graphics2D g2d = (Graphics2D) g;
        game_view.drawBoard(g2d, clicked_x_coord, clicked_y_coord); // draw empty board
        String checkmate_or_slatemate = game_controller.isCheckmateOrStalemate();
        if(checkmate_or_slatemate == null) { // neither checkmate nor stalemate
            game_controller.checkUserClick(g2d, clicked_x_coord, clicked_y_coord); // check user mouse click
        }
        else if (checkmate_or_slatemate.equals("checkmate")){ // checkmate
            JOptionPane.showMessageDialog(this, "Checkmate!", "", JOptionPane.INFORMATION_MESSAGE);
        }
        else { // stalemate
            JOptionPane.showMessageDialog(this, "Player"+(this.board.turns%2 == 0 ? 1 : 2)+" Stalemate!", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String [] args){
        // init game
        game_panel = new Game();
        // initialize Chessboard(model), game view, and game controller
        board = new ChessBoard(8, 8); // create standard 8 x 8 chess board.
        game_view = new GameView(board, game_panel, 64); // initialize game view
        game_controller = new GameController(board, game_view); // initialize game constroller

        // initialize standard 8 x 8 chess board
        // board.generateStandardBoard();;

        // initialize fantasy 8 x 8 chess board
        board.generateFantasyBoard();

        // Initialize JFrame
        frame = new JFrame("Chess");  // init jframe object
        frame.add(game_panel);        // draw canvas
        frame.getContentPane().setPreferredSize(new Dimension(8 * 64, 8 * 64));  // set height and width
        frame.setResizable(false);    // disable resizable
        frame.pack();
        frame.setVisible(true);       // set as visible
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set close operation
    }
}