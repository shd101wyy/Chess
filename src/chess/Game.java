package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game extends JPanel{
    static JFrame frame;
    static ChessBoard board;    // chess board

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
                repaint();
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
        board.drawBoard(this, g2d, clicked_x_coord, clicked_y_coord);
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String [] args){
        // initialize Chessboard
        board = new ChessBoard(8, 8); // create standard 8 x 8 chess board.

        // initialize standard 8 x 8 chess board
        board.generateStandardBoard();;

        /*
        King king1 = new King(board, 1);
        king1.setCoordinate(0, 7);

        King king2 = new King(board, 2);
        king2.setCoordinate(0, 0);

        Rook rook2 = new Rook(board, 2);
        rook2.setCoordinate(2, 7);

        Rook rook3 = new Rook(board, 2);
        rook3.setCoordinate(0, 4);
        */

        /*
        // stalemate
        King king1 = new King(board, 1);
        king1.setCoordinate(7, 7);

        King king2 = new King(board, 2);
        king2.setCoordinate(5, 6);

        Rook rook2 = new Rook(board, 2);
        rook2.setCoordinate(6, 6);
        */

        /*
        // checkmate
        King king1 = new King(board, 2);
        king1.setCoordinate(5, 4);

        King king2 = new King(board, 1);
        king2.setCoordinate(7, 4);

        Rook rook1 = new Rook(board, 2);
        rook1.setCoordinate(7, 0);
        */



        /*
        King king1 = new King(board, 1);
        king1.setCoordinate(1, 7);
        Rook rook2 = new Rook(board, 2);
        rook2.setCoordinate(0, 5);
        King king2 = new King(board, 2);
        king2.setCoordinate(2, 5);
        Knight knight2 = new Knight(board, 2);
        knight2.setCoordinate(4, 6);
        */

        // Initialize JFrame
        frame = new JFrame("Chess");  // init jframe object
        frame.add(new Game());        // draw canvas
        frame.getContentPane().setPreferredSize(new Dimension(8 * 64, 8 * 64));  // set height and width
        frame.setResizable(false);    // disable resizable
        frame.pack();
        frame.setVisible(true);       // set as visible
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set close operation
    }
}