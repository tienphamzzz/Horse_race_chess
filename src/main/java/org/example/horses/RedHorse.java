package org.example.horses;

public class RedHorse extends Horse {
    public RedHorse(int row, int col) {
        super(row, col);
        this.team_color = 4;
        this.image = getImage("/horse/red_horse");
    }
}
