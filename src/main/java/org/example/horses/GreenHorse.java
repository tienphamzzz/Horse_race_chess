package org.example.horses;

public class GreenHorse extends Horse {
    public GreenHorse(int row, int col) {
        super(row, col);
        this.team_color = 2;
        this.cageDoorRow = 0;
        this.cageDoorCol = 7;
        this.startRow = 0;
        this.startCol = 8;
        this.image = getImage("/horse/green_horse");
        this.path = new int[][]{
                {0, 8}, {1, 8}, {2, 8}, {3, 8}, {4, 8}, {5, 8}, {6, 8}, {6, 9}, {6, 10}, {6, 11}, {6, 12}, {6, 13},
                {6, 14}, {7, 14}, {8, 14}, {8, 13}, {8, 12}, {8, 11}, {8, 10}, {8, 9}, {8, 8}, {9, 8}, {10, 8}, {11, 8},
                {12, 8}, {13, 8}, {14, 8}, {14, 7}, {14, 6}, {13, 6}, {12, 6}, {11, 6}, {10, 6}, {9, 6}, {8, 6}, {8, 5},
                {8, 4}, {8, 3}, {8, 2}, {8, 1}, {8, 0}, {7, 0}, {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6},
                {5, 6}, {4, 6}, {3, 6}, {2, 6}, {1, 6}, {0, 6}, {0, 7}
        };
        this.cagePath = new int[][]{
                {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}, {6, 7}
        };
    }
}
