package org.example;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Dice {
    private static final int SIDES = 6;
    private int value;
    private Random random;
    private boolean rolling;

    public Dice() {
        random = new Random();
        value = 1;
        rolling = false;
    }

    // Hiệu ứng tung xúc xắc
    public void rollWithAnimation(JPanel panel) {
        rolling = true;
        Timer timer = new Timer(100, e -> {
            value = random.nextInt(SIDES) + 1;
            panel.repaint();

            ((Timer) e.getSource()).setDelay(((Timer) e.getSource()).getDelay() + 50);
            if (((Timer) e.getSource()).getDelay() >= 600) {
                ((Timer) e.getSource()).stop();
                rolling = false;
            }
        });
        timer.start();
    }

    // Phương thức vẽ xúc xắc
    public void draw(Graphics2D g2, int x, int y, int size) {
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, size, size, 20, 20);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, size, size, 20, 20);

        drawDots(g2, x, y, size);
    }

    private void drawDots(Graphics2D g2, int x, int y, int size) {
        g2.setColor(Color.BLACK);
        int dotSize = size / 6;
        int center = size / 2;
        int offset = size / 4;

        switch (value) {
            case 1 -> drawDot(g2, x + center, y + center, dotSize);
            case 2 -> {
                drawDot(g2, x + offset, y + offset, dotSize);
                drawDot(g2, x + size - offset, y + size - offset, dotSize);
            }
            case 3 -> {
                drawDot(g2, x + offset, y + offset, dotSize);
                drawDot(g2, x + center, y + center, dotSize);
                drawDot(g2, x + size - offset, y + size - offset, dotSize);
            }
            case 4 -> {
                drawDot(g2, x + offset, y + offset, dotSize);
                drawDot(g2, x + offset, y + size - offset, dotSize);
                drawDot(g2, x + size - offset, y + offset, dotSize);
                drawDot(g2, x + size - offset, y + size - offset, dotSize);
            }
            case 5 -> {
                drawDot(g2, x + offset, y + offset, dotSize);
                drawDot(g2, x + offset, y + size - offset, dotSize);
                drawDot(g2, x + center, y + center, dotSize);
                drawDot(g2, x + size - offset, y + offset, dotSize);
                drawDot(g2, x + size - offset, y + size - offset, dotSize);
            }
            case 6 -> {
                drawDot(g2, x + offset, y + offset, dotSize);
                drawDot(g2, x + offset, y + size / 2, dotSize);
                drawDot(g2, x + offset, y + size - offset, dotSize);
                drawDot(g2, x + size - offset, y + offset, dotSize);
                drawDot(g2, x + size - offset, y + size / 2, dotSize);
                drawDot(g2, x + size - offset, y + size - offset, dotSize);
            }
        }
    }

    private void drawDot(Graphics2D g2, int x, int y, int dotSize) {
        g2.fillOval(x - dotSize / 2, y - dotSize / 2, dotSize, dotSize);
    }
}
