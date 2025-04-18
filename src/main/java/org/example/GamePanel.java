package org.example;

import org.example.horses.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    public static int current_turn = 1;
    public static ArrayList<Horse> horses = new ArrayList<>();
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();
    Dice dice = new Dice();
    Horse selectedH = null;
    JButton button = new JButton("Roll dice");
    private boolean diceRolled = false;
    private int[][] cachedTargetPosition;
    private boolean gameOver;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setHorses();

        this.setLayout(null);

        button.setBounds(17 * Board.SQUARE_SIZE + Board.HALF_SQUARE_SIZE + Board.PADDING, 8 * Board.SQUARE_SIZE + Board.PADDING, Board.SQUARE_SIZE * 2, Board.HALF_SQUARE_SIZE);
        add(button);
        button.addActionListener(_ -> rollDice());
    }
    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setHorses(){
        // Draw Blue Horses
        horses.add(new BlueHorse(1,1));
        horses.add(new BlueHorse(4,1));
        horses.add(new BlueHorse(1,4));
        horses.add(new BlueHorse(4,4));
        // Draw Green Horses
        horses.add(new GreenHorse(1,10));
        horses.add(new GreenHorse(4,10));
        horses.add(new GreenHorse(1,13));
        horses.add(new GreenHorse(4,13));
        // Draw Red Horses
        horses.add(new RedHorse(10,1));
        horses.add(new RedHorse(13,1));
        horses.add(new RedHorse(10,4));
        horses.add(new RedHorse(13,4));
        // Draw Yellow Horses
        horses.add(new YellowHorse(10,13));
        horses.add(new YellowHorse(13,13));
        horses.add(new YellowHorse(10,10));
        horses.add(new YellowHorse(13,10));
        //Draw test horses
//        horses.add(new BlueHorse(7, 0, 1));
    }
    private void update() {
        if (mouse.pressed && diceRolled) {
            if (selectedH == null) {
                for (Horse horse : horses) {
                    if (horse.team_color == current_turn && horse.x < mouse.x && horse.x + Board.SQUARE_SIZE > mouse.x &&
                            horse.y < mouse.y && horse.y + Board.SQUARE_SIZE > mouse.y) {
                        selectedH = horse;
                        selectedH.oldRow = selectedH.row;
                        selectedH.oldCol = selectedH.col;
                        cachedTargetPosition = tagetPosition(selectedH, dice);
                        break;
                    }
                }
            } else {
                selectedH.moveTo(mouse.x, mouse.y);
            }
        }
        if (!mouse.pressed) {
            if (selectedH != null) {
                if ((selectedH.isWithInTheBoard(selectedH.row, selectedH.col) || selectedH.isInCagePath(selectedH.row, selectedH.col))
                         && isHorseCorrectPosition(selectedH, cachedTargetPosition)) {
                    selectedH.updatePosition();
                    selectedH.oldRow = selectedH.row;
                    selectedH.oldCol = selectedH.col;
                    if(!selectedH.isInStart() && !selectedH.isInCagePath(selectedH.row, selectedH.col)){
                        selectedH.currentPathIndex += dice.getValue();
                    }
                    if(selectedH.isInCageDoor(selectedH.row, selectedH.col)){
                        selectedH.currentCagePathIndex = 0;
                    }
                    if(selectedH.isInCagePath(selectedH.row, selectedH.col)){
                        selectedH.currentCagePathIndex = dice.getValue();
                    }
//                    System.out.println("Path length: " + selectedH.path.length);
//                    System.out.println("Current path index: " + selectedH.currentPathIndex);
//                    System.out.println("Cage path length: " + selectedH.cagePath.length);
                    System.out.println("Current cage path index: " + selectedH.currentCagePathIndex);
                    changePlayer();
                } else {
                    selectedH.moveTo(selectedH.getX(selectedH.oldRow), selectedH.getY(selectedH.oldCol));
                    selectedH.row = selectedH.oldRow;
                    selectedH.col = selectedH.oldCol;
//                    System.out.println("Path length: " + selectedH.path.length);
//                    System.out.println("Current path index: " + selectedH.currentPathIndex);
//                    System.out.println("Cage path length: " + selectedH.cagePath.length);
                    System.out.println("Current cage path index: " + selectedH.currentCagePathIndex);
                }
                selectedH = null;
            }
        }
        if (isGameOver()) {
            gameOver = true;
            System.out.println("Game Over!");
            // Additional logic to handle game over state can be added here
        }
    }
    private void rollDice() {
        dice.rollWithAnimation(this, button, () -> {
            diceRolled = true;

            if (!isAnyHorseCanMove()) {
                changePlayer();
            }
        });
    }
    private boolean isGameOver() {
        int[] teamCount = new int[5]; // Assuming team colors are 1 to 4

        for (Horse horse : horses) {
            if (horse.isInCagePath(horse.row, horse.col) && horse.currentCagePathIndex >= 3 && horse.currentCagePathIndex <= 6) {
                teamCount[horse.team_color]++;
            }
        }

        for (int i = 1; i <= 4; i++) {
            if (teamCount[i] == 4) {
                System.out.println("Team " + i + " wins!");
                return true;
            }
        }

        return false;
    }
    private boolean isHorseCorrectPosition(Horse selectedHorse,int[][] target){
        return canMove(selectedHorse, target) && selectedHorse.row == target[0][0] && selectedHorse.col == target[0][1];
    }
    private boolean canMove(Horse selectedHorse, int[][] target){
        // A horse is call moved if target position is not the same as current position
        if (selectedHorse.oldRow == target[0][0] && selectedHorse.oldCol == target[0][1]){
            System.out.println("Horse is not moved");
            return false;
        }
        // A horse can move if the target position is not occupied by another horse of the same color except the horse itself
        for (Horse horse : horses) {
            if (horse.row == target[0][0] && horse.col == target[0][1] && horse.team_color == selectedHorse.team_color && horse != selectedHorse) {
                System.out.println("There is a horse in the target position");
                return false;
            }
        }
        // A horse can move if there are no other horses in the way to the target position (if targetPathIndex is 3, it must be not any horse in position 1, 2)
        if(!selectedHorse.isInCage(selectedHorse.oldRow, selectedHorse.oldCol)){
            int targetPathIndex = selectedHorse.currentPathIndex + dice.getValue();
            if (targetPathIndex < selectedHorse.path.length) {
                for (int i = selectedHorse.currentPathIndex + 1; i < targetPathIndex; i++) {
                    for (Horse horse : horses) {
                        if (horse.row == selectedHorse.path[i][0] && horse.col == selectedHorse.path[i][1]) {
                            System.out.println("There is a horse in the way");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    private int[][] tagetPosition(Horse selectedHorse, Dice dice){
        int[][] target = new int[1][2];
        int currentRow = selectedHorse.oldRow;
        int currentCol = selectedHorse.oldCol;
        int targetRow, targetCol;
        int diceValue = dice.getValue();
        if(selectedHorse.isInCage(currentRow, currentCol)) {
            if (diceValue == 6 || diceValue == 1) {
                targetRow = selectedHorse.startRow;
                targetCol = selectedHorse.startCol;
                target[0][0] = targetRow;
                target[0][1] = targetCol;
            } else {
                target[0][0] = currentRow;
                target[0][1] = currentCol;
            }
        }
        if(selectedHorse.isWithInTheBoard(currentRow, currentCol)){
            int[][] path = selectedHorse.path;
            int pathLength = path.length;
            if(selectedHorse.currentPathIndex + diceValue < pathLength && !selectedHorse.isInCage(currentRow, currentCol)){
                targetRow = path[selectedHorse.currentPathIndex + diceValue][0];
                targetCol = path[selectedHorse.currentPathIndex + diceValue][1];
                target[0][0] = targetRow;
                target[0][1] = targetCol;

            }
            else{
                target[0][0] = currentRow;
                target[0][1] = currentCol;
            }
        }
        if(selectedHorse.isInCageDoor(currentRow, currentCol)){
            int[][] cagePath = selectedHorse.cagePath;
            int cagePathLength = cagePath.length;
            if(selectedHorse.currentCagePathIndex + diceValue - 1 < cagePathLength){
                targetRow = cagePath[selectedHorse.currentCagePathIndex + diceValue - 1][0];
                targetCol = cagePath[selectedHorse.currentCagePathIndex + diceValue - 1][1];
                target[0][0] = targetRow;
                target[0][1] = targetCol;
            }else {
                target[0][0] = currentRow;
                target[0][1] = currentCol;
            }
        }
        if (selectedHorse.isInCagePath(currentRow, currentCol)){
            int[][] cagePath = selectedHorse.cagePath;
            if(diceValue > selectedHorse.currentCagePathIndex + 1){
                targetRow = cagePath[diceValue - 1][0];
                targetCol = cagePath[diceValue - 1][1];
                target[0][0] = targetRow;
                target[0][1] = targetCol;

            }else {
                target[0][0] = currentRow;
                target[0][1] = currentCol;
            }
        }
        return target;
    }

    private boolean isAnyHorseCanMove() {
        for (Horse horse : horses) {
            if (horse.team_color == current_turn && canMove(horse, tagetPosition(horse, dice))) {
                return true;
            }
        }
        return false;
    }

    private void changePlayer(){
        if (current_turn == 1){
            current_turn = 2;
        }
        else if (current_turn == 2){
            current_turn = 3;
        }
        else if (current_turn == 3){
            current_turn = 4;
        }
        else if (current_turn == 4){
            current_turn = 1;
        }
        System.out.println("Current turn: " + current_turn);
        diceRolled = false;
        selectedH = null;
        button.setEnabled(true);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        board.draw(g2);

        for (Horse horse : horses) {
            horse.draw(g2);
        }

        dice.draw(g2, 17 * Board.SQUARE_SIZE + Board.HALF_SQUARE_SIZE + Board.PADDING, 5 * Board.SQUARE_SIZE + Board.PADDING, 100);

        if (selectedH != null) {
            g2.setColor(Color.WHITE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.fillRect((selectedH.row * Board.SQUARE_SIZE) + Board.PADDING, (selectedH.col * Board.SQUARE_SIZE) + Board.PADDING, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            selectedH.draw(g2);
        }

        // Draw post-move position
        if (selectedH != null) {
            g2.setColor(Color.RED);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.fillRect((cachedTargetPosition[0][0] * Board.SQUARE_SIZE) + Board.PADDING, (cachedTargetPosition[0][1] * Board.SQUARE_SIZE) + Board.PADDING, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }

        // Draw current turn
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Book Antiqua", Font.PLAIN, 30));
        g2.setColor(Color.BLACK);
        if(current_turn == 1){
            g2.drawString("Your turn", 85 + Board.PADDING, 155 + Board.PADDING);
        }
        else if(current_turn == 2){
            g2.drawString("Your turn", 85 + Board.PADDING, 605 + Board.PADDING);
        }
        else if(current_turn == 3){
            g2.drawString("Your turn", 540 + Board.PADDING, 605 + Board.PADDING);
        }
        else if(current_turn == 4){
            g2.drawString("Your turn", 540 + Board.PADDING, 155 + Board.PADDING);
        }
    }

    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
}
