package org.example;

import java.awt.*;

public class Board {
    final int MAX_COL = 15;
    final int MAX_ROW = 15;
    public static final int SQUARE_SIZE = 50;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;
    public static final int PADDING = 20; // Thêm lề

    Color blue = new Color(0, 7, 110);
    Color red = new Color(223, 25, 14);
    Color green = new Color(47, 121, 19);
    Color yellow = new Color(255, 230, 18);

    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(4));

        // Tô màu nền
        g2.setColor(new Color(255, 193, 140));
        g2.fillRect(0, 0, MAX_COL * SQUARE_SIZE + 2 * PADDING, MAX_ROW * SQUARE_SIZE + 2 * PADDING);
        // Vẽ bàn cờ với lề
        for (int row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COL; col++) {
                int x = col * SQUARE_SIZE + PADDING;
                int y = row * SQUARE_SIZE + PADDING;

                if ((6 <= row && row <= 8) || (6 <= col && col <= 8)) {
//                    g2.setColor(Color.WHITE);
//                    g2.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

                    Color circleColor = getCircleColor(row, col);
                    if (circleColor != null) {
                        g2.setColor(circleColor);
                        g2.drawOval(x + 7, y + 7, SQUARE_SIZE - 14, SQUARE_SIZE - 14);
                    }
                }
            }
        }

        // Vẽ số trong ô trung tâm
        for (int i = 1; i < 7; i++) {
            drawColoredSquare(g2, blue, 7, i, i);
            drawColoredSquare(g2, green, i, 7, i);
            drawColoredSquare(g2, red, 14 - i, 7, i);
            drawColoredSquare(g2, yellow, 7, 14 - i, i);
        }

        // Vẽ lồng ngựa với lề
        drawHorseCage(g2, blue, 0, 0);
        drawHorseCage(g2, green, 0, 9);
        drawHorseCage(g2, red, 9, 0);
        drawHorseCage(g2, yellow, 9, 9);

        // Vẽ điểm xuất chuồng
        g2.setColor(blue);
        g2.fillOval(6 * SQUARE_SIZE + PADDING + 13, 0 * SQUARE_SIZE + PADDING + 13, SQUARE_SIZE - 26, SQUARE_SIZE - 26);
        g2.setColor(green);
        g2.fillOval(0 * SQUARE_SIZE + PADDING + 13, 8 * SQUARE_SIZE + PADDING + 13, SQUARE_SIZE - 26, SQUARE_SIZE - 26);
        g2.setColor(red);
        g2.fillOval(14 * SQUARE_SIZE + PADDING + 13, 6 * SQUARE_SIZE + PADDING + 13, SQUARE_SIZE - 26, SQUARE_SIZE - 26);
        g2.setColor(yellow);
        g2.fillOval(8 * SQUARE_SIZE + PADDING + 13, 14 * SQUARE_SIZE + PADDING + 13, SQUARE_SIZE - 26, SQUARE_SIZE - 26);

        // Vẽ điểm nhập chuồng (điểm cuối)
        g2.setColor(blue);
        drawArrow(g2, 7 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE, 0 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE - 10, 7 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE, 0 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE + 10, 10);
        g2.setColor(green);
        drawArrow(g2, 0 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE - 10, 7 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE, 0 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE + 10, 7 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE, 10);
        g2.setColor(red);
        drawArrow(g2, 14 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE + 10, 7 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE, 14 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE - 10, 7 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE, 10);
        g2.setColor(yellow);
        drawArrow(g2, 7 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE, 14 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE + 10, 7 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE, 14 * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE - 10, 10);

        // Vẽ viền bàn cờ tổng thể
        g2.setColor(Color.BLACK);
        g2.drawRect(PADDING - 10, PADDING - 10, MAX_COL * SQUARE_SIZE + 20, MAX_ROW * SQUARE_SIZE + 20);
    }

    private Color getCircleColor(int row, int col) {
        if (0 <= row && row <= 6 && 0 <= col && col <= 7) return blue;
        if (0 <= row && row <= 7 && 8 <= col && col <= 14) return red;
        if (7 <= row && row <= 14 && 0 <= col && col <= 6) return green;
        if (8 <= row && row <= 14 && 7 <= col && col <= 14) return yellow;
        return null;
    }

    private void drawColoredSquare(Graphics2D g2, Color color, int col, int row, int number) {
        int x = col * SQUARE_SIZE + PADDING + 1;
        int y = row * SQUARE_SIZE + PADDING + 1;

        g2.setColor(color);
        g2.fillRect(x, y, SQUARE_SIZE - 2, SQUARE_SIZE - 2);

        drawCenteredNumber(g2, String.valueOf(number), col, row, 30, Color.WHITE);
    }

    private void drawHorseCage(Graphics2D g2, Color color, int col, int row) {
        int x = col * SQUARE_SIZE + PADDING;
        int y = row * SQUARE_SIZE + PADDING;

        g2.setColor(color);
        g2.drawRoundRect(x, y, 6 * SQUARE_SIZE, 6 * SQUARE_SIZE, 50, 50);
        g2.drawOval( (col + 1) * SQUARE_SIZE + PADDING, (row + 1) * SQUARE_SIZE + PADDING, 4 * SQUARE_SIZE, 4 * SQUARE_SIZE);
    }

    private static void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2, int arrowSize) {
        // Vẽ đường chính của mũi tên
        g2.drawLine(x1, y1, x2, y2);

        // Tính toán vector hướng
        double angle = Math.atan2(y2 - y1, x2 - x1);

        // Tạo 2 điểm đầu mũi tên
        int arrowX1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int arrowY1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
        int arrowX2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int arrowY2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

        // Vẽ đầu mũi tên
        g2.drawLine(x2, y2, arrowX1, arrowY1);
        g2.drawLine(x2, y2, arrowX2, arrowY2);
    }

    private void drawCenteredNumber(Graphics2D g2, String number, int col, int row, int fontSize, Color color) {
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2.setFont(font);
        g2.setColor(color);

        FontMetrics metrics = g2.getFontMetrics();
        int x = col * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE - metrics.stringWidth(number) / 2;
        int y = row * SQUARE_SIZE + PADDING + HALF_SQUARE_SIZE + metrics.getAscent() / 2 - 2;

        g2.drawString(number, x, y);
    }
}
