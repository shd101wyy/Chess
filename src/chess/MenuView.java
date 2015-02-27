package chess;

/**
 * Created by wangyiyi on 2/27/15.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * This class is used to draw menu
 *
 */
public class MenuView extends JPanel {
    private String player1_name;  // name of player1
    private String player2_name;  // name of player2
    private int player1_score; // score of player1
    private int player2_score; // score of player2
    private String message;      // game message
    protected GameView game_view; // game view associated with the menu view
    /**
     * MenuView constructor
     * This constructor is used to put several components on panel
     * eg: start button, restart button, forfeit button, undo button.
     * @param width
     * @param height
     */
    public MenuView(int width, int height, final GameView game_view){
        this.game_view = game_view;
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(null);

         /*
          *
          * Setup several buttons for menu
          *
          */
        JButton start_btn = new JButton("Start");
        start_btn.setBounds(10, 10, 100, 50);
        this.add(start_btn);
        start_btn.addActionListener(new ActionListener() {
            /**
             * Clicked start button
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                game_view.game_controller.clickedStartButton(); // run click start button event
            }
        });


        JButton restart_btn = new JButton("Restart");
        restart_btn.setBounds(120, 10, 100, 50);
        this.add(restart_btn);
        restart_btn.addActionListener(new ActionListener() {
            /**
             * Clicked restart button
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                game_view.game_controller.clickedRestartButton(); // run click restart button event
            }
        });

        JButton forfeit_btn = new JButton("Forfeit");
        forfeit_btn.setBounds(230, 10, 100, 50);
        this.add(forfeit_btn);
        forfeit_btn.addActionListener(new ActionListener() {
            /**
             * Clicked forfeit button
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                game_view.game_controller.clickedForfeitButton();  // run click forfeit button event
            }
        });

        JButton undo_btn = new JButton("Undo");
        undo_btn.setBounds(340, 10, 100, 50);
        this.add(undo_btn);

        JButton player1_btn = new JButton("WHITE");
        player1_btn.setBounds(10, 100, 100, 50);
        this.add(player1_btn);


        JButton player2_btn = new JButton("BLACK");
        player2_btn.setBounds(340, 100, 100, 50);
        this.add(player2_btn);

        this.setBackground(new Color(100, 175, 89)); // draw menu background

        player1_name = "WHITE";
        player1_score = 0;
        player2_name = "BLACK";
        player2_score = 0;
        message = "Press Start button to start the game";
    }

    /**
     * Draw images on canvas
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(new Color(90, 132, 255));
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString("VS", 200, 200);


        // draw score for player1(WHITE)
        g.setColor(new Color(255, 255, 255));
        g.drawString(Integer.toString(this.player1_score), 70, 200);    // draw score for player1(WHITE)

        // draw score for player2(BLACK)
        String score2 = "Score2";
        g.setColor(new Color(0, 0, 0));
        g.drawString(Integer.toString(this.player2_score), 390, 200);   // draw score for player2(BLACK)

        // draw game message
        g.setColor(new Color(241, 255, 163));
        g.drawString(this.message, 20, 300);
    }

    /**
     *
     * @param player1_score
     * @param player2_score
     * @param message
     */
    public void drawMenu(int player1_score, int player2_score, String message){
        // update player1_score player2_score and message
        this.player1_score = player1_score;
        this.player2_score = player2_score;
        this.message = message;

        this.repaint(); // redraw the components
    }
}