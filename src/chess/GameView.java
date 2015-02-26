package chess;

/**
 * Created by wangyiyi on 2/25/15.
 */

import piece.Coordinate;
import piece.King;
import piece.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * Draw GUI for chess game
 */
public class GameView {
    private ChessBoard board; // chessboard that we are using
    protected JPanel panel; // jpanel where we draw GUI
    protected int tile_size; // size of tile
    /**
     * Constructor: init game view
     * @param board
     * @param panel
     * @param tile_size
     */
    public GameView(ChessBoard board, JPanel panel, int tile_size){
        this.board = board;
        this.panel = panel;
        this.tile_size = tile_size;
    }

    /**
     * Draw tile on board.
     *
     *      Coordinate System
     *      (0, 0) -----------------------> x
     *      |
     *      |
     *      |
     *      |      canvas coordinate system.
     *      |     (x, y)
     *      |
     *      y
     *
     * @param g2d
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param color  the color to draw
     */
    public void drawTileForBoard(Graphics2D g2d, int x, int y, Color color){
        g2d.setColor(color);       // set color
        g2d.fillRect(x, y, this.tile_size, this.tile_size); // draw tile
    }

    /**
     * Draw Piece on Chessboard
     * @param g2d
     * @param p           the piece we want to draw
     */
    public void drawPiece(Graphics2D g2d, Piece p){
        int piece_x_coord = p.getX_coordinate();                // get piece x coordinate (left-bottom coordinate system)
        int piece_y_coord = p.getY_coordinate();                // vet piece y coordinate
        int x = piece_x_coord * this.tile_size;                    // convert to canvas coordinate system
        int y = (this.board.height - piece_y_coord - 1) * this.tile_size;
        try {
            BufferedImage image = ImageIO.read(new File(p.getPiece_image_path()));  // read image for this piece
            g2d.drawImage(image, x, y, this.tile_size, this.tile_size, null);             // draw the image
        } catch (Exception e) { // this should never happen
            System.out.println("ERROR: Cannot load image file\n"); // this exception should never happen
            System.exit(0);
        }
    }

    /**
     * Game Over, popup a gameover window.
     */
    public void game_over(){
        JOptionPane.showMessageDialog(this.panel, "Game Over!", "", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * draw tiles that the piece can move to
     * @param g2d
     */
    public void drawPossibleMovesForPiece(Graphics2D g2d, ArrayList<Coordinate> coords){
        Piece p;
        Color color;
        int x, y;
        for (Coordinate coord : coords) {
            /*
             *  high light possible moves.
             */
            if(this.board.getPieceAtCoordinate(coord.getX(), coord.getY()) == null) { // that spot is empty
                color = new Color(195, 98, 108);
            }
            else if (this.board.getPieceAtCoordinate(coord.getX(), coord.getY()).getPiece_name().equals("king")){ // check?
                color = new Color(252, 236, 93);
            }
            else{  // opponent's piece is there
                color = new Color(195, 77, 34);
            }
            x = coord.getX() * this.tile_size;  // convert to canvas coordinate
            y = (this.board.height - 1 - coord.getY()) * this.tile_size;
            drawTileForBoard(g2d, x, y, color);

                        /* draw piece at that coordinate */
            p = this.board.getPieceAtCoordinate(coord.getX(), coord.getY());
            if (p != null)
                drawPiece(g2d, p);
        }
    }
    /**
     * Draw current chessboard
     *
     * @param g2d
     * @param clicked_x_coord:  the x coordinate where we clicked
     * @param clicked_y_coord:  the y coordinate where we clicked
     */
    public void drawBoard(Graphics2D g2d, double clicked_x_coord, double clicked_y_coord){
        // draw chess board
        /* #################################
         * ## draw squares on chess board ##
         * #################################
         */
        int count = 0;              // this number is used to judge color for board square
        int i, j;
        Piece p;
        int x, y;                   // x and y coordinate of board square. this coordinate is using top-left system
        Color color;
        int player;                 // used to check whose turn right now
        for(i = 0; i < this.board.height; i++){
            for(j = 0; j < this.board.width; j++){
                x = j * this.tile_size; // calculate canvas coordinate
                y = i * this.tile_size;

                /*
                 * decide the color for square
                 */
                if(count % 2 == 0){
                    color = new Color(255, 206, 158);
                }
                else{
                    color = new Color(209, 139, 71);
                }
                if(clicked_x_coord >= x  && clicked_x_coord < x + this.tile_size && clicked_y_coord >= y && clicked_y_coord < y + this.tile_size){
                    color = new Color(140, 91, 49);
                }
                drawTileForBoard(g2d, x, y, color); // draw square
                count++;
            }
            count++;
        }
        /*
         * draw pieces
         */
        for(i = 0; i < this.board.height; i++){
            for(j = 0; j < this.board.width; j++){
                /* draw piece */
                p = this.board.getPieceAtCoordinate(j, i);    // get piece at current canvas coordinate (left-top coordinate system)
                if(p != null) {
                    drawPiece(g2d, p);
                }
            }
        }
    }
}
