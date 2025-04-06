package org.example.horses;

import org.example.Board;
import org.example.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Horse {
    public BufferedImage image;
    public int team_color;
    public int x, y;
    public int row, col;
    public int oldRow, oldCol;
    public int cageRow, cageCol;
    public int cageDoorRow, cageDoorCol;
    public int startRow, startCol;
    public int[][] path;
    public int[][] cagePath;
    public int currentPathIndex = 0;
    public int currentCagePathIndex = 0;

    public Horse(int row, int col) {
        this.row = row;
        this.col = col;
        x = getX(row);
        y = getY(col);
        this.oldRow = row;
        this.oldCol = col;
        this.cageRow = row;
        this.cageCol = col;
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
    public boolean isInStart() {
        return row == startRow && col == startCol;
    }
    public boolean isWithInTheBoard(int row, int col) {
        int[][] validPositions = {
                {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {5, 6}, {4, 6}, {3, 6}, {2, 6}, {1, 6}, {0, 6},
                {0, 7}, {0, 8}, {1, 8}, {2, 8}, {3, 8}, {4, 8}, {5, 8}, {6, 8}, {6, 9}, {6, 10}, {6, 11}, {6, 12}, {6, 13},
                {6, 14}, {7, 14}, {8, 14}, {8, 13}, {8, 12}, {8, 11}, {8, 10}, {8, 9}, {8, 8}, {9, 8}, {10, 8}, {11, 8},
                {12, 8}, {13, 8}, {14, 8}, {14, 7}, {14, 6}, {13, 6}, {12, 6}, {11, 6}, {10, 6}, {9, 6}, {8, 6}, {8, 5},
                {8, 4}, {8, 3}, {8, 2}, {8, 1}, {8, 0}, {7, 0},
                {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}, {7, 6},
                {8, 7}, {9, 7}, {10, 7}, {11, 7}, {12, 7}, {13, 7},
                {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7},
                {7, 8}, {7, 9}, {7, 10}, {7, 11}, {7, 12}, {7, 13}
        };

        for (int[] pos : validPositions) {
            if (pos[0] == row && pos[1] == col) {
                return true;
            }
        }
        return false;
    }
    public void moveToCage() {
        this.x = getX(cageRow);
        this.y = getY(cageCol);
        this.row = cageRow;
        this.col = cageCol;
        this.currentPathIndex = 0;
        this.currentCagePathIndex = 0;
        this.oldRow = cageRow;
        this.oldCol = cageCol;
    }
    public boolean isInCage(int row, int col) {
        return row == cageRow && col == cageCol;
    }
    public boolean isInCageDoor(int row, int col) {
        return row == cageDoorRow && col == cageDoorCol;
    }
    public boolean isInCagePath(int row, int col) {
        for (int[] pos : cagePath) {
            if (pos[0] == row && pos[1] == col) {
                return true;
            }
        }
        return false;
    }
    public void updatePosition() {
        for (Horse horse : GamePanel.horses) {
            if (horse.row == this.row && horse.col == this.col && horse.team_color != this.team_color) {
                horse.moveToCage();
                break;
            }
        }
        x = getX(row);
        y = getY(col);
    }
    public int getRow(int x){
        return (x + Board.HALF_SQUARE_SIZE - Board.PADDING) / Board.SQUARE_SIZE;
    }
    public int getCol(int y){
        return (y + Board.HALF_SQUARE_SIZE - Board.PADDING) / Board.SQUARE_SIZE;
    }
    public int getY(int col) {
        return col * Board.SQUARE_SIZE + Board.PADDING;
    }

    public int getX(int row) {
        return row * Board.SQUARE_SIZE + Board.PADDING;
    }
}
