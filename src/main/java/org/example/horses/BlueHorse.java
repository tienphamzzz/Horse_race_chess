package org.example.horses;

public class BlueHorse extends Horse {
    public BlueHorse(int row, int col) {
        super(row, col);
        this.team_color = 1;
        this.cageDoorRow = 7;
        this.cageDoorCol = 0;
        this.startRow = 6;
        this.startCol = 0;
        this.image = getImage("/horse/blue_horse");
        this.path = new int[][]{
                {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {5, 6}, {4, 6}, {3, 6}, {2, 6}, {1, 6}, {0, 6},
                {0, 7}, {0, 8}, {1, 8}, {2, 8}, {3, 8}, {4, 8}, {5, 8}, {6, 8}, {6, 9}, {6, 10}, {6, 11}, {6, 12}, {6, 13},
                {6, 14}, {7, 14}, {8, 14}, {8, 13}, {8, 12}, {8, 11}, {8, 10}, {8, 9}, {8, 8}, {9, 8}, {10, 8}, {11, 8},
                {12, 8}, {13, 8}, {14, 8}, {14, 7}, {14, 6}, {13, 6}, {12, 6}, {11, 6}, {10, 6}, {9, 6}, {8, 6}, {8, 5},
                {8, 4}, {8, 3}, {8, 2}, {8, 1}, {8, 0}, {7, 0}
        };
        this.cagePath = new int[][]{
                {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}, {7, 6}
        };
    }
    // Test horse generate function
    public BlueHorse(int row, int col, int key) {
        super(row, col);
        this.team_color = 1;
        this.cageDoorRow = 7;
        this.cageDoorCol = 0;
        this.startRow = 6;
        this.startCol = 0;
        this.image = getImage("/horse/blue_horse");
        this.path = new int[][]{
                {6, 0}, {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {5, 6}, {4, 6}, {3, 6}, {2, 6}, {1, 6}, {0, 6},
                {0, 7}, {0, 8}, {1, 8}, {2, 8}, {3, 8}, {4, 8}, {5, 8}, {6, 8}, {6, 9}, {6, 10}, {6, 11}, {6, 12}, {6, 13},
                {6, 14}, {7, 14}, {8, 14}, {8, 13}, {8, 12}, {8, 11}, {8, 10}, {8, 9}, {8, 8}, {9, 8}, {10, 8}, {11, 8},
                {12, 8}, {13, 8}, {14, 8}, {14, 7}, {14, 6}, {13, 6}, {12, 6}, {11, 6}, {10, 6}, {9, 6},{8, 6}, {8, 5},
                {8, 4}, {8, 3}, {8, 2}, {8, 1}, {8, 0}, {7, 0}
        };
        this.cagePath = new int[][]{
                {7, 1}, {7, 2}, {7, 3}, {7, 4}, {7, 5}, {7, 6}
        };
        this.cageRow = 0;
        this.cageCol = 0;
        for (int i = 0; i < path.length; i++) {
            if (path[i][0] == row && path[i][1] == col) {
                this.currentPathIndex = i;
                System.out.println("currentTestPathIndex: " + currentPathIndex);
                System.out.println("currentTestCagePathIndex: " + currentCagePathIndex);
                break;
            }
        }
        for (int i = 0; i < cagePath.length; i++) {
            if (cagePath[i][0] == row && cagePath[i][1] == col) {
                this.currentCagePathIndex = i;
                break;
            }
        }
    }
}