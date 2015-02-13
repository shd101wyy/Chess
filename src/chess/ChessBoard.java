package chess;

import com.sun.codemodel.internal.JOp;

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
    private Piece king1; // king for player1 White
    private Piece king2; // king for player2 Black
    private boolean gameover; // check whether gameover

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
        this.king1 = null;
        this.king2 = null;
        this.gameover = false;
    }

    public Piece getKing1(){
        return this.king1;
    }

    public void setKing1(Piece p){
        this.king1 = p;
    }

    public Piece getKing2(){
        return this.king2;
    }

    public void setKing2(Piece p){
        this.king2 = p;
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

    public int getPlayerForThisTurn(){
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
     *
     * Check whether this is a suicide move
     * @param p
     * @param move_to_x
     * @param move_to_y
     * @return
     */
    public boolean isSuicideMove(Piece p, int move_to_x, int move_to_y){
        int current_player = p.getPlayer(); // get player
        int current_x_coord = p.getX_coordinate();
        int current_y_coord = p.getY_coordinate();
        int i, j;
        boolean is_suicide = false;
        boolean first_time_move_for_remove_piece = false;
        boolean first_time_move_for_p = false;

        Piece king = (current_player == 1 ? this.king1 : this.king2);  // get king

        Piece remove_piece = getPieceAtCoordinate(move_to_x, move_to_y); // get piece that need to be removed

        if(remove_piece != null) {
            remove_piece.removeSelf();  // remove self temporarily
        }
        p.removeSelf();
        if(p.getPiece_name().equals("pawn"))  // if it is pawn, we dont want to change its first_time_move flag
            ((Pawn)p).setCoordinateWithoutChangingFirstTimeMoveFlag(move_to_x, move_to_y);
        else
            p.setCoordinate(move_to_x, move_to_y); // move p to that coord;

        for(i = 0; i < this.width; i++){
            for(j = 0; j < this.height; j++){
                if (this.pieces[j][i] != null && this.pieces[j][i].getPlayer() != current_player){ // opponent's piece
                    ArrayList<Coordinate> coords = this.pieces[j][i].getPossibleMoveCoordinate();
                    for(Coordinate coord : coords){
                        if(coord.getX() == king.getX_coordinate() && coord.getY() == king.getY_coordinate()){ // will go to king's coord
                            is_suicide = true;
                            break;
                        }
                    }
                }
            }
        }

        // restore remove_piece and p
        p.removeSelf();
        if(p.getPiece_name().equals("pawn"))
            ((Pawn)p).setCoordinateWithoutChangingFirstTimeMoveFlag(current_x_coord, current_y_coord);
        else
            p.setCoordinate(current_x_coord, current_y_coord);
        if(remove_piece != null) {
            if (remove_piece.getPiece_name().equals("pawn"))
                ((Pawn)remove_piece).setCoordinateWithoutChangingFirstTimeMoveFlag(move_to_x, move_to_y);
            else
                remove_piece.setCoordinate(move_to_x, move_to_y);
        }
        System.out.println("Is suicide: " + is_suicide);
        return is_suicide;
    }

    /**
     *
     * Check whether opponent can move
     * If opponent can not move any piece, then game over
     * @return
     */
    public boolean opponentCannotMove(int player){
        int i, j;
        for (i = 0; i < this.width; i++){
            for(j = 0; j < this.width; j++){
                Piece p = getPieceAtCoordinate(i, j);
                if(p != null && p.getPlayer() == player){  // get opponent's piece
                    ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate(); // possible move coordinates.
                    for(Coordinate coord : coords){
                        if (isSuicideMove(p, coord.getX(), coord.getY()) == false){ // so opponent piece can move there.
                            return false; // not game over
                        }
                    }
                }
            }
        }
        return true;
    }

    /*
     * Check whether opponent can kill king next move
     */
    public boolean opponentCanKillKing(int player){
        Piece king = ((player == 1) ? king2 : king1);
        int i, j;
        for (i = 0; i < this.width; i++){
            for(j = 0; j < this.width; j++){
                Piece p = getPieceAtCoordinate(i, j);
                if(p != null && p.getPlayer() == player){  // get opponent's piece
                    ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate(); // possible move coordinates.
                    for(Coordinate coord : coords){
                        if (coord.getX() == king.getX_coordinate() && coord.getY() == king.getY_coordinate()){ // so opponent piece can move there.
                            return true; // game over
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check whether player's king is in stalemate
     * @param player
     * @return
     */
    public boolean isStalemate(int player){
        King king = player == 1 ? (King)this.king1 : (King)this.king2;
        if(king.isInCheck() == false){ // king is not in check.
            // check whether king has legal move
            // if king doesnt have legal move, then return true
            ArrayList<Coordinate> coords = king.getPossibleMoveCoordinate();
            if(coords.size() == 0)
                return false;
            for(Coordinate coord : coords){
                if (isSuicideMove(king, coord.getX(), coord.getY()) == false){ // there is a legal move
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }


    public void gameover(JPanel panel){
        this.gameover = true;
        JOptionPane.showMessageDialog(panel, "Game Over!", "", JOptionPane.INFORMATION_MESSAGE);
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
                    // System.out.println("Draw Piece " + p.getX_coordinate() + " " + p.getY_coordinate());
                    drawPiece(g2d, p, square_size);
                }
            }
        }

        // check stalemate
        if(isStalemate(this.turns%2 == 0 ? 1 : 2)){
            this.gameover = true;
            JOptionPane.showMessageDialog(panel, "Player"+(this.turns%2 == 0 ? 1 : 2)+" Stalemate!", "", JOptionPane.INFORMATION_MESSAGE);
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
                if(p.getPlayer() == getPlayerForThisTurn()) { // player chooses his/her own piece
                    this.chosen_piece = p;       // save as chosen_piece
                    // System.out.println("Enter Here\n");
                    // System.out.println(p.getX_coordinate() + " " + p.getY_coordinate());
                    ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate();
                    if (coords == null) {  // this should not happend
                        System.out.println("It is null\n");
                    } else {
                        for (Coordinate coord : coords) {
                            if (isSuicideMove(this.chosen_piece, coord.getX(), coord.getY())){ // it is a suicide move
                                continue;
                            }
                            // high light possible moves.
                            if(getPieceAtCoordinate(coord.getX(), coord.getY()) == null) { // that spot is empty
                                color = new Color(195, 98, 108);
                            }
                            else if (getPieceAtCoordinate(coord.getX(), coord.getY()).getPiece_name().equals("king")){ // check?
                                color = new Color(252, 236, 93);
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
                else{ // player chooses opponent's piece
                    if(this.chosen_piece == null) // do nothing
                        return;
                    // check whether opponent's piece is under capture scope
                    ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
                    if(coords != null){
                        for(Coordinate coord : coords){
                            if (isSuicideMove(this.chosen_piece, coord.getX(), coord.getY())){ // it is a suicide move
                                continue;
                            }
                            if (coord.getX() == p.getX_coordinate() && coord.getY() == p.getY_coordinate()){ // opponent's piece is captured
                                System.out.println("You captured a piece");
                                // remove that opponent's piece
                                this.pieces[p.getY_coordinate()][p.getX_coordinate()] = null;

                                // move player's piece to that coordinate
                                p.removeSelf(); // remove opponent's piece
                                if(p.getPiece_name().equals("king")){ // check whether game over
                                    gameover(panel); // game over
                                }
                                this.chosen_piece.removeSelf();
                                this.chosen_piece.setCoordinate(coord.getX(), coord.getY());

                                if(opponentCannotMove(this.chosen_piece.getPlayer() == 1 ? 2 : 1)){ // check whether opponent can move
                                    gameover(panel);
                                }
                                if(opponentCanKillKing(this.chosen_piece.getPlayer() == 1 ? 2 : 1)){ // check whether next round opponent will definitely kill the king
                                    gameover(panel);
                                }
                                // update turns and redraw the canvas
                                this.chosen_piece = null;
                                turns++;
                                panel.repaint();
                                return;
                            }
                        }
                    }
                    else{
                        // nothing happend here
                    }
                }
            }
            else if (this.chosen_piece != null) { // that means  p == null, and player clicked a square
                ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
                if(coords != null){
                    for(Coordinate coord : coords){
                        if (isSuicideMove(this.chosen_piece, coord.getX(), coord.getY())){ // it is a suicide move
                            continue;
                        }
                        if (coord.getX() == x && coord.getY() == y){ // opponent's piece is captured
                            System.out.println("You moved a piece");

                            // move player's piece to that coordinate
                            this.chosen_piece.removeSelf();
                            this.chosen_piece.setCoordinate(x, y);

                            if(opponentCannotMove(this.chosen_piece.getPlayer() == 1 ? 2 : 1)){ // check whether opponent can move
                                gameover(panel);
                            }

                            if(opponentCanKillKing(this.chosen_piece.getPlayer() == 1 ? 2 : 1)){ // check whether next round opponent will definitely kill the king
                                gameover(panel);
                            }

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


    /**
     * Generate standard 8 x 8 chess board
     */
    public void generateStandardBoard(){
        Piece p;  // a piece object

        // ===============
        //  player1 white
        // ===============
        p = new Rook(this, 1); // a1 white rook
        p.setCoordinate(0, 0);

        p = new Knight(this, 1); // b1 white knight
        p.setCoordinate(1, 0);

        p = new Bishop(this, 1); // c1 white bishop
        p.setCoordinate(2, 0);

        p = new Queen(this, 1); // d1 white queen
        p.setCoordinate(3, 0);

        p = new King(this, 1);   // e1 white king
        p.setCoordinate(4, 0);

        p = new Bishop(this, 1); // f1 white bishop
        p.setCoordinate(5, 0);

        p = new Knight(this, 1); // g1 white knight
        p.setCoordinate(6, 0);

        p = new Rook(this, 1);   // h2 white rook
        p.setCoordinate(7, 0);

        p = new Pawn(this, 1);   // a2 white pawn
        p.setCoordinate(0, 1);

        p = new Pawn(this, 1);   // b2 white pawn
        p.setCoordinate(1, 1);

        p = new Pawn(this, 1);   // c2 white pawn
        p.setCoordinate(2, 1);

        p = new Pawn(this, 1);   // d2 white pawn
        p.setCoordinate(3, 1);

        p = new Pawn(this, 1);   // e2 white pawn
        p.setCoordinate(4, 1);

        p = new Pawn(this, 1);   // f2 white pawn
        p.setCoordinate(5, 1);

        p = new Pawn(this, 1);   // g2 white pawn
        p.setCoordinate(6, 1);

        p = new Pawn(this, 1);   // h2 white pawn
        p.setCoordinate(7, 1);


        // ===============
        //  player2 black
        // ===============
        p = new Rook(this, 2); // a8 black rook
        p.setCoordinate(0, 7);

        p = new Knight(this, 2); // b8 black knight
        p.setCoordinate(1, 7);

        p = new Bishop(this, 2); // c8 black bishop
        p.setCoordinate(2, 7);

        p = new Queen(this, 2); // d8 black queen
        p.setCoordinate(3, 7);

        p = new King(this, 2);   // e8 black king
        p.setCoordinate(4, 7);

        p = new Bishop(this, 2); // f8 black bishop
        p.setCoordinate(5, 7);

        p = new Knight(this, 2); // g8 black knight
        p.setCoordinate(6, 7);

        p = new Rook(this, 2);   // h8 black rook
        p.setCoordinate(7, 7);

        p = new Pawn(this, 2);   // a7 black pawn
        p.setCoordinate(0, 6);

        p = new Pawn(this, 2);   // b7 black pawn
        p.setCoordinate(1, 6);

        p = new Pawn(this, 2);   // c7 black pawn
        p.setCoordinate(2, 6);

        p = new Pawn(this, 2);   // d7 black pawn
        p.setCoordinate(3, 6);

        p = new Pawn(this, 2);   // e7 black pawn
        p.setCoordinate(4, 6);

        p = new Pawn(this, 2);   // f7 black pawn
        p.setCoordinate(5, 6);

        p = new Pawn(this, 2);   // g7 black pawn
        p.setCoordinate(6, 6);

        p = new Pawn(this, 2);   // h7 black pawn
        p.setCoordinate(7, 6);
    }
}
