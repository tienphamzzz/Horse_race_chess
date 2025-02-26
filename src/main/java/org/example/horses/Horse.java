package org.example.horses;

import org.example.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Horse {
    public BufferedImage image;
    public int team_color;
    public int x, y;
    public int row, col;

    public Horse(int row, int col) {
        this.row = row;
        this.col = col;
        x = getX(col);
        y = getY(row);
    }

    public BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    public void draw(Graphics2D g2) {
            g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        this.row = getRow(newX);
        this.col = getCol(newY);
    }
    public void updatePosition() {
        x = getX(col);
        y = getY(row);
    }
    public int getRow(int x){
        return (x + Board.HALF_SQUARE_SIZE - Board.PADDING) / Board.SQUARE_SIZE;
    }
    public int getCol(int y){
        return (y + Board.HALF_SQUARE_SIZE - Board.PADDING) / Board.SQUARE_SIZE;
    }
    private int getY(int row) {
        return col * Board.SQUARE_SIZE + Board.PADDING;
    }

    private int getX(int col) {
        return row * Board.SQUARE_SIZE + Board.PADDING;
    }
}
