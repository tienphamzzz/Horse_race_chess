package org.example.horses;

public class BlueHorse extends Horse {
    public BlueHorse(int row, int col) {
        super(row, col);
        this.team_color = 1;
        this.image = getImage("/horse/blue_horse");
    }
}
