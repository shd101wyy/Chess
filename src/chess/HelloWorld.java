package chess;

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.*;

public class HelloWorld extends JPanel{
    static JFrame frame;
    double clicked_x_coord = -1;
    double clicked_y_coord = -1;
    public HelloWorld(){
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
        //
        //    draw chess board
        //
        int count = 0;
        int x, y;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                x = j * 64;
                y = i * 64;
                if(count % 2 == 0){
                    g2d.setColor(new Color(255, 206, 158));
                }
                else{
                    g2d.setColor(new Color(209, 139, 71));
                }
                if(clicked_x_coord >= x  && clicked_x_coord < x + 64 && clicked_y_coord >= y && clicked_y_coord < y + 64){
                    g2d.setColor(new Color(140, 91, 49));
                }
                g2d.fillRect(x, y, 64, 64);
                count++;
            }
            count++;
        }

        try {
            BufferedImage image = ImageIO.read(new File("assets/black_rook.png"));
            g2d.drawImage(image, 0, 0, 64, 64, null);
            //g2d.drawImage(image, 128, 128, 64, 64, null);
        }
        catch (Exception e){
            System.out.println("Error: Cannot find file\n");
        }
    }
    public static void main(String [] args){
        frame = new JFrame("Chess");
        frame.add(new HelloWorld());
        frame.getContentPane().setPreferredSize(new Dimension(8*64, 8*64));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}