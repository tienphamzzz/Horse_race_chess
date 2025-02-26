package org.example.horses;

public class YellowHorse extends Horse {
    public YellowHorse(int row, int col) {
        super(row, col);
        this.team_color = 3;
        this.image = getImage("/horse/yellow_horse");
    }
}
