package chess;

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.*;

public class Game extends JPanel{
    static JFrame frame;
    static ChessBoard board;    // chess board
    double clicked_x_coord = -1;
    double clicked_y_coord = -1;
    public Game(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                clicked_x_coord = e.getPoint().getX();
                clicked_y_coord = e.getPoint().getY();
                System.out.println("X: " + clicked_x_coord + " Y: " + clicked_y_coord);
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g){
        System.out.println("paint");
        Graphics2D g2d = (Graphics2D) g;
        board.drawBoard(this, g2d, clicked_x_coord, clicked_y_coord);
    }

    public static void main(String [] args){
        // initialize Chessboard
        board = new ChessBoard(8, 8); // create standard 8 x 8 chess board.

        // set piece on board
        Pawn pawn1 = new Pawn(board, 1);  // white
        pawn1.setCoordinate(0, 1);
        Pawn pawn2 = new Pawn(board, 2);  // black
        pawn2.setCoordinate(0, 7);

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