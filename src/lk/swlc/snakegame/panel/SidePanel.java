package lk.swlc.snakegame.panel;

import lk.swlc.snakegame.view.SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;



public class SidePanel extends JPanel {

    private static final long serialVersionUID = -40557434900946408L;

    private static final Font LARGE_FONT = new Font("cursive", Font.BOLD, 20);

    private static final Font MEDIUM_FONT = new Font("cursive", Font.BOLD, 16);

    private static final Font SMALL_FONT = new Font("cursive", Font.BOLD, 12);

//    Tahoma

    private SnakeGame game;

    public SidePanel(SnakeGame game) {
        this.game = game;
//change dimention with 300
        setPreferredSize(new Dimension(200, BoardPanel.rowCount * BoardPanel.tileSize));
        setBackground(Color.LIGHT_GRAY);
    }

    private static final int STATISTICS_OFFSET = 100;  //150

    private static final int CONTROLS_OFFSET = 300;   ///320

    private static final int MESSAGE_STRIDE = 15; //30

    private static final int SMALL_OFFSET = 15;  //30

    private static final int LARGE_OFFSET = 25; //50

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.WHITE);

        g.setFont(LARGE_FONT);
        g.drawString("Snake Game ", getWidth() / 2 - g.getFontMetrics().stringWidth("Snake Game") / 2, 40); //50

//        g.setFont(MEDIUM_FONT);
//        g.drawString("Statistics", SMALL_OFFSET, STATISTICS_OFFSET);
//        g.drawString("Controls", SMALL_OFFSET, CONTROLS_OFFSET);

        g.setFont(SMALL_FONT);

        //Draw the content for the statistics category.
        int drawY = STATISTICS_OFFSET;
//        g.drawString("Total Score: " + game.getScore(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("SCORE : " + game.getFritsEaten(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
//        g.drawString("Fruit Score: " + game.getNextFruitScore(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        //Draw the content for the controls category.
        drawY = CONTROLS_OFFSET;
//        g.drawString("Move Up: W / Up Arrowkey", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
//        g.drawString("Move Down: S / Down Arrowkey", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
//        g.drawString("Move Left: A / Left Arrowkey", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
//        g.drawString("Move Right: D / Right Arrowkey", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
//        g.drawString("Pause Game: P", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
    }
}
