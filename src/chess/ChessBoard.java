package chess;

import piece.*;

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
    private int width;           // the width of chess board
    private int height;          // the height of chess board
    private Piece tiles[][];    // this 2d array is used to save pieces
    private Piece chosen_piece;  // the piece that is chosen by player
    private int turns;           // count number of moves
    private Piece king1;         // king for player1 White
    private Piece king2;         // king for player2 Black
    private boolean gameover;    // check whether game is over
    private ArrayList<Piece> white_pieces; // array list used to save all white pieces
    private ArrayList<Piece> black_pieces; // array list used to save all black pieces

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

        // initialize the 2d array to store pieces
        this.tiles = new Piece[height][];
        for(int i = 0; i < height; i++) {
            this.tiles[i] = new Piece[width];
        }

        // initialize white_pieces and black_pieces
        this.white_pieces = new ArrayList<Piece>();
        this.black_pieces = new ArrayList<Piece>();

        // initialize other variables
        this.chosen_piece = null; // no piece is chosen by player yet
        this.turns = 0;           // if it is even number, then it's White turn, otherwise Black turn
        this.king1 = null;        // no king is set yet
        this.king2 = null;
        this.gameover = false;    // game not over yet
    }

    /**
     * add piece to white_pieces or black_pieces
     * @param p
     */
    public void addPieceToList(Piece p) {
        if(p.getPlayer() == Player.WHITE){
            this.white_pieces.add(p);
        }
        else{
            this.black_pieces.add(p);
        }
    }

    /**
     * Getter: return this.white_pieces
     * @return
     */
    public ArrayList<Piece> getWhite_pieces(){
        return this.white_pieces;
    }

    /**
     * Getter: return this.black_pieces
     * @return
     */
    public ArrayList<Piece> getBlack_pieces(){
        return this.black_pieces;
    }


    /**
     * Getter: get king1
     * @return
     */
    public Piece getKing1(){
        return this.king1;
    }

    /**
     * Setter: set king1
     * @param p
     */
    public void setKing1(Piece p){
        this.king1 = p;
    }

    /**
     * Getter: get king2
     * @return
     */
    public Piece getKing2(){
        return this.king2;
    }

    /**
     * Setter: set king2
     * @param p
     */
    public void setKing2(Piece p){
        this.king2 = p;
    }

    /**
     * Setter: set turns
     * @param turns
     */
    public void setTurns(int turns){
        this.turns = turns;
    }

    /**
     * Getter: get turns
     * @return
     */
    public int getTurns(){
        return this.turns;
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
        return this.tiles[y][x];
    }

    /**
     * Store the piece at (x, y) coordinate
     * @param p
     * @param x
     * @param y
     */
    public void setPieceAtCoordinate(Piece p, int x, int y){
        this.tiles[y][x] = p;
    }

    /**
     * Getter: get the width of chess board
     * @return
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Getter: get the height of chess board
     * @return
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Get the player for this turn
     * @return
     */
    public Player getPlayerForThisTurn(){
        if (this.turns % 2 == 0){
            return Player.WHITE; // White
        }
        return Player.BLACK; // Black
    }

    /**
     * Remove a piece from the chess board
     * @param p
     */
    public void removePiece(Piece p){
        int x = p.getX_coordinate();
        int y = p.getY_coordinate();
        this.tiles[y][x] = null;
    }

    /**
     * Draw square on board.
     *
     * (0, 0) -----------------------> x
     * |
     * |
     * |
     * |      canvas coordinate system.
     * |     (x, y)
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
     * Draw Piece on Chess Board
     * @param g2d
     * @param p
     * @param square_size
     */
    public void drawPiece(Graphics2D g2d, Piece p, int square_size){
        int piece_x_coord = p.getX_coordinate();                // get piece x coordinate (left-bottom coordinate system)
        int piece_y_coord = p.getY_coordinate();                // vet piece y coordinate
        int x = piece_x_coord * square_size;                    // convert to canvas coordinate system
        int y = (this.height - piece_y_coord - 1) * square_size;
        try {
            BufferedImage image = ImageIO.read(new File(p.getPiece_image_path()));  // read image for this piece
            g2d.drawImage(image, x, y, square_size, square_size, null);             // draw the image
        } catch (Exception e) {
            System.out.println("ERROR: Cannot load image file\n"); // this exception should never happen
            System.exit(0);
        }
    }

    /**
     *
     * Check whether this is a suicide move
     * Suppose p moves to (move_to_x, move_to_y) coordinate, then check if the king is in check.
     * @param p
     * @param move_to_x
     * @param move_to_y
     * @return return true if this move will cause king being checked.
     */
    public boolean isSuicideMove(Piece p, int move_to_x, int move_to_y){
        Player current_player = p.getPlayer();       // get player
        int current_x_coord = p.getX_coordinate();
        int current_y_coord = p.getY_coordinate();
        boolean is_suicide = false;

        Piece king = (current_player == Player.WHITE ? this.king1 : this.king2);  // get king

        Piece remove_piece = getPieceAtCoordinate(move_to_x, move_to_y); // get piece that need to be removed

        ArrayList<Piece> opponent_pieces = (current_player == Player.WHITE ? this.getBlack_pieces() : this.getWhite_pieces()); // get opponent's pieces

        if(remove_piece != null) {
            remove_piece.removeSelf();  // remove self temporarily
        }
        if(p.getPiece_name().equals("pawn"))  // if it is pawn, we don't want to change its first_time_move flag
            ((Pawn)p).setCoordinateWithoutChangingFirstTimeMoveFlag(move_to_x, move_to_y);
        else
            p.setCoordinate(move_to_x, move_to_y); // move p to that coordinate;

        for(Piece opponent_piece : opponent_pieces){
            if(p.getX_coordinate() == -1 || p.getY_coordinate() == -1) // invalid coordinate
                continue;
            ArrayList<Coordinate> coords = opponent_piece.getPossibleMoveCoordinate();
            for(Coordinate coord : coords){
                if(coord.getX() == king.getX_coordinate() && coord.getY() == king.getY_coordinate()){ // will go to king's coord
                    is_suicide = true;
                    break;
                }
            }
        }
        // restore remove_piece and p
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
        // System.out.println("Is suicide: " + is_suicide);
        return is_suicide;
    }

    /**
     *
     * Check whether player can move a piece
     * If a player can not move any piece, then return true
     * @return true if player cannot move any piece; otherwise return false
     */
    public boolean playerCannotMove(Player player){
        int i, j;
        ArrayList<Piece> player_pieces = ((player == Player.WHITE) ? this.getWhite_pieces() : this.getBlack_pieces());
        for(Piece p : player_pieces){ // get player's piece
            if(p.getX_coordinate() == -1 || p.getY_coordinate() == -1)
                continue;;
            ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate(); // possible move coordinates.
            for(Coordinate coord : coords){
                if (isSuicideMove(p, coord.getX(), coord.getY()) == false){ // so piece can move there.
                    return false; // there is a legal move
                }
            }

        }
        return true;
    }


    /**
     * Check whether player's king is in stalemate
     * when not checked, check whether is there any legal move
     * if there is no legal move, return true; otherwise return false.
     * @param player
     * @return
     */
    public boolean isStalemate(Player player){
        King king = player == Player.WHITE ? (King)this.king1 : (King)this.king2;
        if(king.isInCheck() == false){ // king is not in check.
            // check whether is there any legal move
            // if there is no legal move, then return true
            // if there is a legal move, then return false
            if(playerCannotMove(player))
                return true;
            return false;
        }
        else{
            return false;
        }
    }

    /**
     * Game Over, popup a gameover window.
     * @param panel
     */
    public void game_over(JPanel panel){
        this.gameover = true;
        JOptionPane.showMessageDialog(panel, "Game Over!", "", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Draw current chess board
     * @param g2d
     */
    public void drawBoard(JPanel panel, Graphics2D g2d, double clicked_x_coord, double clicked_y_coord){
        // draw chess board
        /* #################################
         * ## draw squares on chess board ##
         * #################################
         */
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

                /*
                 * decide the color for square
                 */
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

        /*
         * draw pieces
         */
        for(i = 0; i < this.height; i++){
            for(j = 0; j < this.width; j++){
                /* draw piece */
                p = getPieceAtCoordinate(j, i);    // get piece at current canvas coordinate (left-top coordinate system)
                if(p != null) {
                    drawPiece(g2d, p, square_size);
                }
            }
        }

        /*
         * Check checkmate and stalemate
         */
        if (playerCannotMove(this.turns%2 == 0 ? Player.WHITE : Player.BLACK)){ // so right now that player cannot move any chess
            King king = (this.turns%2 == 0) ? (King)this.king1 : (King)this.king2;  // get current player's king
            this.gameover = true;
            if(king.isInCheck()){ // checkmate
                JOptionPane.showMessageDialog(panel, "Checkmate!", "", JOptionPane.INFORMATION_MESSAGE);
            }
            else{ // stalemate
                JOptionPane.showMessageDialog(panel, "Player"+(this.turns%2 == 0 ? 1 : 2)+" Stalemate!", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        /*
         * check mouse click.
         */
        if(clicked_x_coord >= 0 && clicked_y_coord >= 0) {     // valid click scope
            x = (int) (clicked_x_coord / 64);                  // convert to left-bottom chess board coordinate system
            y = this.height - 1 - (int) (clicked_y_coord / 64);
            p = getPieceAtCoordinate(x, y);

            /*
             *  Now we clicked a piece
             *
             */
            if (p != null) { // player clicked a piece; show its possible moves
                if(p.getPlayer() == getPlayerForThisTurn()) { // player clicked his/her own piece
                    this.chosen_piece = p;       // save as chosen_piece
                    ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate(); // get all possible move coordinates for this choesn piece

                    for (Coordinate coord : coords) {
                        if (isSuicideMove(this.chosen_piece, coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                            continue;
                        }
                        /*
                         *  high light possible moves.
                         */
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

                        /* draw piece at that coordinate */
                        p = getPieceAtCoordinate(coord.getX(), coord.getY());
                        if (p != null)
                            drawPiece(g2d, p, square_size);
                    }
                }
                else{ // player clicked opponent's piece
                    if(this.chosen_piece == null) // do nothing
                        return;
                    // check whether opponent's piece is under capture scope
                    ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
                    if(coords != null){
                        for(Coordinate coord : coords){
                            if (isSuicideMove(this.chosen_piece, coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                                continue;
                            }
                            if (coord.getX() == p.getX_coordinate() && coord.getY() == p.getY_coordinate()){ // opponent's piece is captured
                                // System.out.println("You captured a piece");

                                /*  remove that opponent's piece */
                                this.tiles[p.getY_coordinate()][p.getX_coordinate()] = null;

                                /* move player's piece to that coordinate */
                                p.removeSelf(); // remove opponent's piece
                                if(p.getPiece_name().equals("king")){ // check whether game over
                                    game_over(panel); // game over
                                }
                                this.chosen_piece.setCoordinate(coord.getX(), coord.getY());

                                /* update turns and redraw the canvas */
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
                for(Coordinate coord : coords){
                    if (isSuicideMove(this.chosen_piece, coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                        continue;
                    }
                    if (coord.getX() == x && coord.getY() == y){ // player can move the piece there
                        // System.out.println("You moved a piece");

                        // move player's piece to that coordinate
                        this.chosen_piece.setCoordinate(x, y);

                        // update turns and redraw the canvas
                        this.chosen_piece = null;
                        turns++;
                        panel.repaint();
                        return;
                    }
                }
            }
        }
    }


    /**
     * Generate standard 8 x 8 chess board
     */
    public void generateStandardBoard(){
        Piece p;  // a piece object

        // ===============
        //  player1 white
        // ===============
        p = new Rook(this, Player.WHITE); // a1 white rook
        p.setCoordinate(0, 0);

        p = new Knight(this, Player.WHITE); // b1 white knight
        p.setCoordinate(1, 0);

        p = new Bishop(this, Player.WHITE); // c1 white bishop
        p.setCoordinate(2, 0);

        p = new Queen(this, Player.WHITE); // d1 white queen
        p.setCoordinate(3, 0);

        p = new King(this, Player.WHITE);   // e1 white king
        p.setCoordinate(4, 0);

        p = new Bishop(this, Player.WHITE); // f1 white bishop
        p.setCoordinate(5, 0);

        p = new Knight(this, Player.WHITE); // g1 white knight
        p.setCoordinate(6, 0);

        p = new Rook(this, Player.WHITE);   // h2 white rook
        p.setCoordinate(7, 0);

        p = new Pawn(this, Player.WHITE);   // a2 white pawn
        p.setCoordinate(0, 1);

        p = new Pawn(this, Player.WHITE);   // b2 white pawn
        p.setCoordinate(1, 1);

        p = new Pawn(this, Player.WHITE);   // c2 white pawn
        p.setCoordinate(2, 1);

        p = new Pawn(this, Player.WHITE);   // d2 white pawn
        p.setCoordinate(3, 1);

        p = new Pawn(this, Player.WHITE);   // e2 white pawn
        p.setCoordinate(4, 1);

        p = new Pawn(this, Player.WHITE);   // f2 white pawn
        p.setCoordinate(5, 1);

        p = new Pawn(this, Player.WHITE);   // g2 white pawn
        p.setCoordinate(6, 1);

        p = new Pawn(this, Player.WHITE);   // h2 white pawn
        p.setCoordinate(7, 1);


        // ===============
        //  player2 black
        // ===============
        p = new Rook(this, Player.BLACK); // a8 black rook
        p.setCoordinate(0, 7);

        p = new Knight(this, Player.BLACK); // b8 black knight
        p.setCoordinate(1, 7);

        p = new Bishop(this, Player.BLACK); // c8 black bishop
        p.setCoordinate(2, 7);

        p = new Queen(this, Player.BLACK); // d8 black queen
        p.setCoordinate(3, 7);

        p = new King(this, Player.BLACK);   // e8 black king
        p.setCoordinate(4, 7);

        p = new Bishop(this, Player.BLACK); // f8 black bishop
        p.setCoordinate(5, 7);

        p = new Knight(this, Player.BLACK); // g8 black knight
        p.setCoordinate(6, 7);

        p = new Rook(this, Player.BLACK);   // h8 black rook
        p.setCoordinate(7, 7);

        p = new Pawn(this, Player.BLACK);   // a7 black pawn
        p.setCoordinate(0, 6);

        p = new Pawn(this, Player.BLACK);   // b7 black pawn
        p.setCoordinate(1, 6);

        p = new Pawn(this, Player.BLACK);   // c7 black pawn
        p.setCoordinate(2, 6);

        p = new Pawn(this, Player.BLACK);   // d7 black pawn
        p.setCoordinate(3, 6);

        p = new Pawn(this, Player.BLACK);   // e7 black pawn
        p.setCoordinate(4, 6);

        p = new Pawn(this, Player.BLACK);   // f7 black pawn
        p.setCoordinate(5, 6);

        p = new Pawn(this, Player.BLACK);   // g7 black pawn
        p.setCoordinate(6, 6);

        p = new Pawn(this, Player.BLACK);   // h7 black pawn
        p.setCoordinate(7, 6);
    }
}
