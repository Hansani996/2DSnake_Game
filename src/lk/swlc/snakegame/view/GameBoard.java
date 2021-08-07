package lk.swlc.snakegame.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener,Runnable {

    private Thread thread;
    private final int border_width = 300;
    private final int border_height = 300;
    private final int dot_size = 10;
    private final int all_dots = 900;
    private final int rand_pos = 29;
    private final int delay = 140;

    private final int x[] = new int[all_dots];
    private final int y[] = new int[all_dots];

    private int dots;
    private int orange_x;
    private int orange_y;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image orange;
    private Image head;

    public GameBoard(){
        initbord();
    }

    public void start(){
        try {
            thread = new Thread(this);
            thread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initbord(){
        addKeyListener(new TAdapter());
        setBackground(Color.blue);
        setFocusable(true);
        setPreferredSize(new Dimension(border_width,border_height));
        loadImages();
        initGame();
    }

    private void loadImages(){
        ImageIcon icon = new ImageIcon();
        ball =icon.getImage();

        ImageIcon icn = new ImageIcon();
        ball = icn.getImage();

        ImageIcon ic = new ImageIcon();
        head = ic.getImage();
    }

    private void initGame(){
        dots = 3;
        for (int i=0;i<dots;i++){
            x[i]=50 -i*10;
            y[i]=50;
        }

        timer = new Timer(delay,this);
        timer.start();
        start();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g){
        if (inGame){
            g.drawImage(orange,orange_x,orange_y,this);
            for (int i=0;i<dots;i++){
                if (i==0){
                    g.drawImage(head,x[i],y[i],this);
                    
                }else {
                    g.drawImage(ball,x[i],y[i],this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else {
            gameOver(g);
        }
    }
    
    private void gameOver(Graphics g){
        thread.stop();
        String msg = "Game Over";
        Font small = new Font("SANS_SERIF",Font.BOLD,14);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (border_width - metr.stringWidth(msg))/2,border_height /2);
        
    }
    
    private void checkOrange(){
        if ((x[0]==orange_x)&&(y[0]==orange_y)){
            dots++;
            locateOrange();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (left) {
            x[0] -= dot_size;
        }

        if (right) {
            x[0] += dot_size;
        }

        if (up) {
            y[0] -= dot_size;
        }

        if (down) {
            y[0] += dot_size;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= border_height) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= border_width) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }
    
    private void locateOrange(){
        int o =(int) (Math.random() * rand_pos);
        orange_x = ((o*dot_size));
        
        o =(int) (Math.random()*rand_pos);
        orange_y = ((o*dot_size));
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            thread.stop();
//            checkApple();
            checkCollision();
            start();
            move();

        }

        repaint();
    }
    

    @Override
    public void run() {
        try {
            for(int i = 6; i > 0; i--) {
//                locateApple();
                Thread.sleep(6000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
