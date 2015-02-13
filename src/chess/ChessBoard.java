package chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class ChessBoard {
    private int width;
    private int height;
    private Piece pieces[][];
    private Piece chosen_piece; // the piece that is chosen by player
    private int turns; // count number of moves

    /**
     *
     * Construct chess board given width and height
     * +
     *  |
     *  |
     *  |
     *  |
     * (0, 0) -------->  +
     *
     * @param width
     * @param height
     */
    public ChessBoard(int width, int height){
        this.width = width;
        this.height = height;
        this.pieces = new Piece[height][];
        for(int i = 0; i < height; i++) {
            this.pieces[i] = new Piece[width];
        }
        this.chosen_piece = null;
        this.turns = 0;   // if it is even number, then it's White turn, otherwise Black turn
    }

    /**
     *
     * Return the piece at given coordinate
     * if there is no piece at that coordinate, or that coordinate is invalid, return null
     * @param x
     * @param y
     * @return
     */
    public Piece getPieceAtCoordinate(int x, int y){
        if(x >= this.width || x < 0 || y >= this.height || y < 0) // outside the boundary
            return null;
        return this.pieces[y][x];
    }

    public void setPieceAtCoordinate(Piece p, int x, int y){
        this.pieces[y][x] = p;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getPlayForThisTurn(){
        if (this.turns % 2 == 0){
            return 1; // White
        }
        return 2; // Black
    }

    /**
     * Draw square on board.
     *
     * (0, 0) -----------------------> x
     * |
     * |
     * |
     * |      canvas coordinate system.
     * |
     * |
     * y
     *
     * @param g2d
     * @param x
     * @param y
     * @param color
     * @param square_size
     */
    public void drawSquareForBoard(Graphics2D g2d, int x, int y, Color color, int square_size){
        g2d.setColor(color);       // set color
        g2d.fillRect(x, y, square_size, square_size); // draw square
    }

    /**
     *
     * @param g2d
     * @param p
     * @param square_size
     */
    public void drawPiece(Graphics2D g2d, Piece p, int square_size){
        int piece_x_coord = p.getX_coordinate(); // get piece x coordinate (left-bottom coordinate system)
        int piece_y_coord = p.getY_coordinate(); // vet piece y coordinate
        int x = piece_x_coord * square_size;                     // convert to canvas coordinate system
        int y = (this.height - piece_y_coord - 1) * square_size;
        try {
            BufferedImage image = ImageIO.read(new File(p.getPiece_image_path()));
            g2d.drawImage(image, x, y, square_size, square_size, null);
        } catch (Exception e) {
            System.out.println("ERROR: Cannot load image file\n"); // this exception should never happen
        }
    }

    /**
     * Draw current chess board
     * @param g2d
     */
    public void drawBoard(JPanel panel, Graphics2D g2d, double clicked_x_coord, double clicked_y_coord){
        // draw chess board
        // ==============================
        // draw squares on chess board
        int square_size = 64;       // size of each square
        int count = 0;              // this number is used to judge color for board square
        int i, j;
        Piece p;
        int x, y;                   // x and y coordinate of board square. this coordinate is using top-left system
        Color color;
        int player;                 // used to check whose turn right now
        for(i = 0; i < this.height; i++){
            for(j = 0; j < this.width; j++){
                x = j * square_size; // calculate canvas coordinate
                y = i * square_size;

                // decide the color for square
                if(count % 2 == 0){
                    color = new Color(255, 206, 158);
                }
                else{
                    color = new Color(209, 139, 71);
                }
                if(clicked_x_coord >= x  && clicked_x_coord < x + square_size && clicked_y_coord >= y && clicked_y_coord < y + square_size){
                    color = new Color(140, 91, 49);
                }
                drawSquareForBoard(g2d, x, y, color, square_size); // draw square
                count++;
            }
            count++;
        }

        // draw pieces
        for(i = 0; i < this.height; i++){
            for(j = 0; j < this.width; j++){
                // draw piece
                p = getPieceAtCoordinate(j, i);    // get piece at current canvas coordinate (left-top coordinate system)
                if(p != null) {
                    System.out.println("Draw Piece " + p.getX_coordinate() + " " + p.getY_coordinate());
                    drawPiece(g2d, p, square_size);
                }
            }
        }

        // check mouse click.
        if(clicked_x_coord >= 0 && clicked_y_coord >= 0) {     // valid click scope
            x = (int) (clicked_x_coord / 64);                  // convert to left-bottom chess board coordinate system
            y = this.height - 1 - (int) (clicked_y_coord / 64);
            p = getPieceAtCoordinate(x, y);

            /*
             *  Now we clicked a piece
             *
             */
            if (p != null) { // click a piece, show its possible moves
                if(p.getPlayer() == getPlayForThisTurn()) { // player chooses his/her own piece
                    this.chosen_piece = p;       // save as chosen_piece
                    // System.out.println("Enter Here\n");
                    // System.out.println(p.getX_coordinate() + " " + p.getY_coordinate());
                    ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate();
                    if (coords == null) {  // this should not happend
                        System.out.println("It is null\n");
                    } else {
                        for (Coordinate coord : coords) {
                            // System.out.println("p: X: " + coord.getX() + " Y: " + coord.getY());

                            // high light possible moves.
                            if(getPieceAtCoordinate(coord.getX(), coord.getY()) == null) { // that spot is empty
                                color = new Color(195, 98, 108);
                            }
                            else{  // opponent's piece is there
                                color = new Color(195, 77, 34);
                            }
                            x = coord.getX() * square_size;  // convert to canvas coordinate
                            y = (this.height - 1 - coord.getY()) * square_size;
                            drawSquareForBoard(g2d, x, y, color, square_size);

                            // draw piece at that coordinate
                            p = getPieceAtCoordinate(coord.getX(), coord.getY());
                            if (p != null)
                                drawPiece(g2d, p, square_size);
                        }
                    }
                }
                else{ // player choose opponent's piece
                    if(this.chosen_piece == null) // do nothing
                        return;
                    // check whether opponent's piece is under capture scope
                    ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
                    if(coords != null){
                        for(Coordinate coord : coords){
                            if (coord.getX() == p.getX_coordinate() && coord.getY() == p.getY_coordinate()){ // opponent's piece is captured
                                System.out.println("You captured a piece");
                                // remove that opponent's piece
                                this.pieces[p.getY_coordinate()][p.getX_coordinate()] = null;

                                // move player's piece to that coordinate
                                p.removeSelf(); // remove opponent's piece
                                this.chosen_piece.removeSelf();
                                this.chosen_piece.setCoordinate(coord.getX(), coord.getY());

                                // update turns and redraw the canvas
                                this.chosen_piece = null;
                                turns++;
                                panel.repaint();
                                return;
                            }
                        }
                    }
                    else{
                        // nothing happend then as you can choose opponent's piece
                    }
                }
            }
            else if (this.chosen_piece != null) { // that means  p == null, and player clicked a place
                ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
                if(coords != null){
                    for(Coordinate coord : coords){
                        if (coord.getX() == x && coord.getY() == y){ // opponent's piece is captured
                            System.out.println("You moved a piece");

                            // move player's piece to that coordinate
                            this.chosen_piece.removeSelf();
                            this.chosen_piece.setCoordinate(x, y);

                            // update turns and redraw the canvas
                            this.chosen_piece = null;
                            turns++;
                            panel.repaint();
                            return;
                        }
                    }
                }
                else{
                    // nothing happend then as you can choose opponent's piece
                }
            }
        }
    }

    /**
     * Remove a piece from the chess board
     * @param p
     */
    public void removePiece(Piece p){
        int x = p.getX_coordinate();
        int y = p.getY_coordinate();
        this.pieces[y][x] = null;
    }

    public void generateStandardBoard(){
        Piece p;  // a piece object

        /*
        // player1 white
        new Piece("rook", 0, 0, this, 1);   // a1 white rook
        new Piece("knight", 1, 0, this, 1); // b1 white knight
        new Piece("bishop", 2, 0, this, 1); // c1 white bishop
        new Piece("queen", 3, 0, this, 1);  // d1 white queen
        new Piece("king", 4, 0, this, 1);   // e1 white king
        new Piece("bishop", 5, 0, this, 1); // f1 white bishop
        new Piece("knight", 6, 0, this, 1); // g1 white knight
        new Piece("rook", 7, 0, this, 1);   // h1 white rook
        new Piece("pawn", 0, 1, this, 1);    // a2 white pawn
        new Piece("pawn", 1, 1, this, 1);    // b2 white pawn
        new Piece("pawn", 2, 1, this, 1);    // c2 white pawn
        new Piece("pawn", 3, 1, this, 1);    // d2 white pawn
        new Piece("pawn", 4, 1, this, 1);    // e2 white pawn
        new Piece("pawn", 5, 1, this, 1);    // f2 white pawn
        new Piece("pawn", 6, 1, this, 1);    // g2 white pawn
        new Piece("pawn", 7, 1, this, 1);    // h2 white pawn


        // player2 black
        new Piece("rook", 0, 7, this, 2);   // a1 white rook
        new Piece("knight", 1, 7, this, 2); // b1 white knight
        new Piece("bishop", 2, 7, this, 2); // c1 white bishop
        new Piece("queen", 3, 7, this, 2);  // d1 white queen
        new Piece("king", 4, 7, this, 2);   // e1 white king
        new Piece("bishop", 5, 7, this, 2); // f1 white bishop
        new Piece("knight", 6, 7, this, 2); // g1 white knight
        new Piece("rook", 7, 7, this, 2);   // h1 white rook
        new Piece("pawn", 0, 6, this, 2);    // a2 white pawn
        new Piece("pawn", 1, 6, this, 2);    // b2 white pawn
        new Piece("pawn", 2, 6, this, 2);    // c2 white pawn
        new Piece("pawn", 3, 6, this, 2);    // d2 white pawn
        new Piece("pawn", 4, 6, this, 2);    // e2 white pawn
        new Piece("pawn", 5, 6, this, 2);    // f2 white pawn
        new Piece("pawn", 6, 6, this, 2);    // g2 white pawn
        new Piece("pawn", 7, 6, this, 2);    // h2 white pawn
        */
    }
}
