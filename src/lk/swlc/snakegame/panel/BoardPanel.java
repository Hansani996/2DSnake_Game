package lk.swlc.snakegame.panel;

import lk.swlc.snakegame.model.TileType;
import lk.swlc.snakegame.view.SnakeGame;
import lk.swlc.snakegame.model.Direction;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private static final long serialVersionUID = -1102632585936750607L;

    public static final int colCount = 25;
    public static final int rowCount = 25;
    public static final int tileSize= 20;
    private static final int eyeLarge = tileSize / 3;
    private static final int eyeSmall = tileSize /6;
    private static final int eyeLength = tileSize /5;
    private static final Font font = new Font("Tahoma" , Font.BOLD,25);


    private SnakeGame snakeGame;

    private TileType[] tiles;

    public BoardPanel(SnakeGame game){
        this.snakeGame= game;
        this.tiles = new TileType[rowCount*colCount];

        setPreferredSize(new Dimension(colCount*tileSize,rowCount*tileSize));
        setBackground(Color.BLACK);

    }

    public void clearBoard(){
        for (int i=0;i<tiles.length;i++){
            tiles[i]=null;
        }
    }

    public void setTile(Point point,TileType type){setTile(point.x,point.y,type);}

    public void setTile(int x, int y, TileType type) {
        tiles[y * rowCount + x] = type;
    }

    public TileType getTile(int x, int y) {
        return tiles[y * rowCount + x];
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for (int x=0;x<colCount;x++){
            for (int y=0;y<rowCount;y++){
                TileType type = getTile(x,y);
                if (type != null){
                    drawTile(x* tileSize, y*tileSize,type, g);
                }
            }
        }

        g.setColor(Color.DARK_GRAY);
        g.drawRect(0,0,getWidth()-1,getHeight()-1);
        for(int x = 0; x < rowCount; x++) {
            for(int y = 0; y < rowCount; y++) {
                g.drawLine(x * tileSize, 0, x * tileSize, getHeight());
                g.drawLine(0, y * tileSize, getWidth(), y * tileSize);
            }
        }

        if (snakeGame.isGameOver() || snakeGame.isNewGame() || snakeGame.isPaused()){
            g.setColor(Color.white);

            int cenX = getWidth()/2;
            int cenY = getHeight()/2;

            String lMsg = null;
            String mMsg = null;
            if (snakeGame.isNewGame()){
                lMsg = "Snake Game !";
                mMsg = "Press Enter to Start";
            } else if(snakeGame.isGameOver()) {
                lMsg = "Game Over!";
                 mMsg = "Press Enter to Restart";
            } else if(snakeGame.isPaused()) {
                lMsg = "Paused";
                mMsg = "Press P to Resume";
            }

            g.setFont(font);
            g.drawString(lMsg, cenX - g.getFontMetrics().stringWidth(lMsg) / 2, cenY - 50);
            g.drawString(mMsg, cenX - g.getFontMetrics().stringWidth(mMsg) / 2, cenY + 50);




        }
    }

    private void drawTile(int x, int y, TileType type, Graphics g){
        switch (type){
            case Fruit:
                g.setColor(Color.RED);
                g.fillOval(x + 2, y + 2, tileSize - 4, tileSize - 4);
                break;

            case SnakeBody:
                g.setColor(Color.GREEN);
                g.fillRect(x, y, tileSize, tileSize);
                break;

            case SnakeHead:
                //Fill the tile in with green.
                g.setColor(Color.GREEN);
                g.fillRect(x, y, tileSize, tileSize);

                //Set the color to black so that we can start drawing the eyes.
                g.setColor(Color.BLACK);
                switch(snakeGame.getDirection()) {
                    case N: {
                        int baseY = y + eyeSmall;
                        g.drawLine(x + eyeLarge, baseY, x + eyeLarge, baseY + eyeLength);
                        g.drawLine(x + tileSize - eyeLarge, baseY, x + tileSize - eyeLarge, baseY + eyeLength);
                        break;
                    }

                    case S: {
                        int baseY = y + tileSize - eyeSmall;
                        g.drawLine(x + eyeLarge, baseY, x + eyeLarge, baseY - eyeLength);
                        g.drawLine(x + tileSize - eyeLarge, baseY, x + tileSize - eyeLarge, baseY - eyeLength);
                        break;
                    }

                    case W: {
                        int baseX = x + eyeSmall;
                        g.drawLine(baseX, y + eyeLarge, baseX + eyeLength, y + eyeLarge);
                        g.drawLine(baseX, y + tileSize - eyeLarge, baseX + eyeLength, y + tileSize - eyeLarge);
                        break;
                    }

                    case E: {
                        int baseX = x + tileSize - eyeSmall;
                        g.drawLine(baseX, y + eyeLarge, baseX - eyeLength, y + eyeLarge);
                        g.drawLine(baseX, y + tileSize - eyeLarge, baseX - eyeLength, y + tileSize - eyeLarge);
                        break;
                    }

                }
                break;
        }
    }

}
