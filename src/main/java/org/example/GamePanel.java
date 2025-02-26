package org.example;

import org.example.horses.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    public static int current_turn = 1;
    public static ArrayList<Horse> horses = new ArrayList<>();
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();
    Dice dice = new Dice();
    Horse selectedHorse = null;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setHorses();

        this.setLayout(null);
        JButton button = new JButton("Roll dice");
        button.setBounds(17 * Board.SQUARE_SIZE + Board.HALF_SQUARE_SIZE + Board.PADDING, 8 * Board.SQUARE_SIZE + Board.PADDING, Board.SQUARE_SIZE * 2, Board.HALF_SQUARE_SIZE);
        button.addActionListener(e -> dice.rollWithAnimation(this));
        this.add(button);
    }
    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setHorses(){
        // Draw Blue Horses
        horses.add(new BlueHorse(1,1));
        horses.add(new BlueHorse(4,1));
        horses.add(new BlueHorse(1,4));
        horses.add(new BlueHorse(4,4));
        // Draw Green Horses
        horses.add(new GreenHorse(1,10));
        horses.add(new GreenHorse(4,10));
        horses.add(new GreenHorse(1,13));
        horses.add(new GreenHorse(4,13));
        // Draw Red Horses
        horses.add(new RedHorse(10,1));
        horses.add(new RedHorse(13,1));
        horses.add(new RedHorse(10,4));
        horses.add(new RedHorse(13,4));
        // Draw Yellow Horses
        horses.add(new YellowHorse(10,13));
        horses.add(new YellowHorse(13,13));
        horses.add(new YellowHorse(10,10));
        horses.add(new YellowHorse(13,10));
    }
//    public void setDice(){
//        dice.
//    }
    private void update() {
        if (mouse.pressed) {
            if (selectedHorse == null) {
                for (Horse horse : horses) {
                    if (horse.team_color == current_turn && horse.x < mouse.x && horse.x + Board.SQUARE_SIZE > mouse.x &&
                            horse.y < mouse.y && horse.y + Board.SQUARE_SIZE > mouse.y) {
                        selectedHorse = horse;
                        break;
                    }
                }
            } else {
                selectedHorse.moveTo(mouse.x, mouse.y);
            }
        }

        if (mouse.pressed == false){
            if (selectedHorse != null){
                selectedHorse.updatePosition();
                selectedHorse = null;
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        board.draw(g2);

        for (Horse horse : horses) {
            horse.draw(g2);
        }

        dice.draw(g2, 17 * Board.SQUARE_SIZE + Board.HALF_SQUARE_SIZE + Board.PADDING, 5 * Board.SQUARE_SIZE + Board.PADDING, 100);

        if (selectedHorse != null) {
            g2.setColor(Color.WHITE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.fillRect((selectedHorse.row * Board.SQUARE_SIZE) + Board.PADDING, (selectedHorse.col * Board.SQUARE_SIZE) + Board.PADDING, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            selectedHorse.draw(g2);
        }
    }

    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
}
