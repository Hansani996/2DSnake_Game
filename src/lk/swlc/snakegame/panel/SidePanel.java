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
        setPreferredSize(new Dimension(200, BoardPanel.rowCount * BoardPanel.tileSize)); //200
        setBackground(Color.LIGHT_GRAY);
    }

    private static final int STATISTICS_OFFSET = 100;

    private static final int CONTROLS_OFFSET = 50;

    private static final int MESSAGE_STRIDE = 15;

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


        int drawY = STATISTICS_OFFSET;
        g.drawString("SCORE : " + game.getFritsEaten(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);

        drawY = CONTROLS_OFFSET;

    }
}
