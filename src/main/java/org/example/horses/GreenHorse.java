package org.example.horses;

public class GreenHorse extends Horse {
    public GreenHorse(int row, int col) {
        super(row, col);
        this.team_color = 2;
        this.image = getImage("/horse/green_horse");
    }
}
