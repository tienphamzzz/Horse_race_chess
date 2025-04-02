package org.example.horses;

public class YellowHorse extends Horse {
    public YellowHorse(int row, int col) {
        super(row, col);
        this.team_color = 3;
        this.cageDoorRow = 7;
        this.cageDoorCol = 14;
        this.startRow = 8;
        this.startCol = 14;
        this.image = getImage("/horse/yellow_horse");
        this.path = new int[][]{
                {8, 14}, {8, 13}, {8, 12}, {8, 11}, {8, 10}, {8, 9}, {8, 8}, {9, 8}, {10, 8}, {11, 8},
                {12, 8}, {13, 8}, {14, 8}, {14, 7}, {14, 6}, {13, 6}, {12, 6}, {11, 6}, {10, 6}, {9, 6}, {8, 6}, {8, 5},
                {8, 4}, {8, 3}, {8, 2}, {8, 1}, {8, 0}, {7, 0}, {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {5, 6}, {4, 6}, {3, 6}, {2, 6}, {1, 6}, {0, 6},
                {0, 7}, {0, 8}, {1, 8}, {2, 8}, {3, 8}, {4, 8}, {5, 8}, {6, 8}, {6, 9}, {6, 10}, {6, 11}, {6, 12}, {6, 13},
                {6, 14}, {7, 14}
        };
        this.cagePath = new int[][]{
                {7, 13}, {7, 12}, {7, 11}, {7, 10}, {7, 9}, {7, 8}
        };
    }
}
