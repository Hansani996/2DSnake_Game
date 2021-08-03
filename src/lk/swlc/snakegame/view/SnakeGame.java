package lk.swlc.snakegame.view;
import lk.swlc.snakegame.model.Direction;
import lk.swlc.snakegame.model.TileType;
import lk.swlc.snakegame.panel.BoardPanel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame  extends JFrame {

    private static final long serialVID = 6678292058307426314L;
    private static final long frameTime = 1000L /50L;

    private static final int minLength =3;

    private static final int maxLength=3;

    private static final int maxDirection =3;

    private BoardPanel board;

    private SidePanel sidePanel;

    private Random random;

    private Clock timer;

    private boolean isNewGame;

    private boolean isGameOver;

    private boolean isPaused;

    private LinkedList<Point> snake;

    private LinkedList<Direction> directions;

    private int score;

    private int fritsEaten;

    private int nextFruitscore;

    public SnakeGame(){
        super("SNAKE GAME");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        this.board = new BoardPanel(this);
        this.sidePanel = new SidePanel(this);

        add(board,BorderLayout.CENTER);
        add(sidePanel,BorderLayout.EAST);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:

                        if (!isPaused && !isGameOver){
                            if (directions.size()<maxDirection){
                                Direction last = directions.peekLast();
                                if (last!=Direction.S && last!=Direction.N){
                                    directions.addLast(Direction.N);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        if(!isPaused && !isGameOver) {
                            if(directions.size() < maxDirection) {
                                Direction last = directions.peekLast();
                                if(last != Direction.N && last != Direction.S) {
                                    directions.addLast(Direction.S);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        if(!isPaused && !isGameOver) {
                            if(directions.size() < maxDirection) {
                                Direction last = directions.peekLast();
                                if(last != Direction.E && last != Direction.W) {
                                    directions.addLast(Direction.W);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        if(!isPaused && !isGameOver) {
                            if(directions.size() < maxDirection) {
                                Direction last = directions.peekLast();
                                if(last != Direction.W && last != Direction.E) {
                                    directions.addLast(Direction.E);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_P:
                        if (!isGameOver){
                            isPaused = !isPaused;
                            timer.setPaused(isPaused);
                        }
                        break;

                    case KeyEvent.VK_ENTER:
                        if (isNewGame || isGameOver){
                            restGame();
                        }
                        break;


                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }


    public void startGame(){
        this.random = new Random();
        this.snake = new LinkedList<>();
        this.directions = new LinkedList<>();
        this.timer = new Clock(9.0f);
        this.isNewGame = true;


        timer.setPaused(true);

        while (true){
            long start = System.nanoTime();

            timer.update();

            if (timer.hasElapsedCycle)){
                updateGame();
            }

            board.repaint();
            sidePanel.repaint();

            long delta = (System.nanoTime() - start) / 1000000L;
            if(delta < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - delta);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateGame(){
        TileType collision = updateSnake();
        if(collision==TileType.Fruit){
            fritsEaten++;
            score+=nextFruitscore;
            spawnFruit();

        }else if(collision==TileType.snakeBody){
            isGameOver=true;
            timer.setPaused(true);

        }else if(nextFruitscore>10){
            nextFruitscore--;
        }
    }

    private TileType updateSnake(){
        Direction direction = directions.peekFirst();
        Point head = new Point(snake.peekFirst());
        switch (direction){
            case N:
                head.y--;
                break;

            case S:
                head.y++;
                break;
            case W:
                head.x++;
                break;
            case E:
                head.x++;
                break;
        }

        if (head.x<0 || head.x >= BoardPanel.colCount || head.y<0 ||head.y>=BoardPanel.rowCount){
            return TileType.SnakeBody;
        }

        TileType old = board.getTile(head.x, head.y);
        if (old != TileType.Fruit && snake.size()>minLength){
            Point tail = snake.removeLast();
            board.setTile(tail, null);
            old=board.getTile(head.x,head.y);
        }

        if(old != TileType.SnakeBody) {
            board.setTile(snake.peekFirst(), TileType.SnakeBody);
            snake.push(head);
            board.setTile(head, TileType.SnakeHead);
            if(directions.size() > 1) {
                directions.poll();
            }
        }

        return old;

    }

    private void restGame(){
        this.score = 0;
        this.fritsEaten =0;

        this.isNewGame =false;
        this.isGameOver = false;

        Point head = new Point(BoardPanel.colCount /2,BoardPanel.rowCount /2);

        snake.clear();
        snake.add(head);

        board.clearBoard();
        board.setTile(head, TileType.SnakeHead);

        directions.clear();
        directions.add(Direction.N);

        timer.reset();
        spawnFruit();
    }




}

