
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 100;
    final int[] playerOneX = new int[GAME_UNITS];
    final int[] playerOneY = new int[GAME_UNITS];
    final int[] playerTwoX = new int[GAME_UNITS];
    final int[] playerTwoY = new int[GAME_UNITS];
    int playerOneBodyParts = 6;
    int playerTwoBodyParts = 6;
    int playerOneApplesEaten;
    int playerTwoApplesEaten;
    int appleX;
    int appleY;
    char playerOneDirection = 'R';
    char playerTwoDirection = 'L';
    boolean playerOneRunning = false;
    boolean playerTwoRunning = false;
    boolean playerOneWins = false;
    boolean playerTwoWins = false;
    boolean tie = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame() {
        playerOneRunning = true;
        playerTwoRunning = true;
        playerOneBodyParts = 6;
        playerTwoBodyParts = 6;
        playerOneApplesEaten = 6;
        playerTwoApplesEaten = 6;
        playerOneDirection = 'R';
        playerTwoDirection = 'L';
        playerOneX[0] = UNIT_SIZE;
        playerOneY[0] = UNIT_SIZE;
        playerTwoX[0] = SCREEN_WIDTH - UNIT_SIZE * 2;
        playerTwoY[0] = SCREEN_HEIGHT - UNIT_SIZE * 2;
        newApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (playerOneRunning && playerTwoRunning) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < playerOneBodyParts; i++) {
                g.setColor(Color.green);
                g.fillRect(playerOneX[i], playerOneY[i], UNIT_SIZE, UNIT_SIZE);
            }

            for (int i = 0; i < playerTwoBodyParts; i++) {
                g.setColor(Color.blue);
                g.fillRect(playerTwoX[i], playerTwoY[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Player 1 Size: " + playerOneApplesEaten, (SCREEN_WIDTH - metrics.stringWidth("Player 1 Score: " + playerOneApplesEaten)) / 2, g.getFont().getSize());
            g.drawString("Player 2 Size: " + playerTwoApplesEaten, (SCREEN_WIDTH - metrics.stringWidth("Player 2 Score: " + playerTwoApplesEaten)) / 2, SCREEN_HEIGHT - g.getFont().getSize());
        } else {
            gameOver(g);
        }

        if (playerOneWins) {
            g.setColor(Color.green);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Player 1 Wins!", ((SCREEN_WIDTH - metrics.stringWidth("Player 1 Wins!")) / 2), (SCREEN_HEIGHT / 2) - 75);
        }

        if (playerTwoWins) {
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Player 2 Wins!", ((SCREEN_WIDTH - metrics.stringWidth("Player 2 Wins!")) / 2), (SCREEN_HEIGHT / 2) - 75);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = playerOneBodyParts; i > 0; i--) {
            playerOneX[i] = playerOneX[i - 1];
            playerOneY[i] = playerOneY[i - 1];
        }

        for (int i = playerTwoBodyParts; i > 0; i--) {
            playerTwoX[i] = playerTwoX[i - 1];
            playerTwoY[i] = playerTwoY[i - 1];
        }

        switch (playerOneDirection) {
            case 'U':
                playerOneY[0] = playerOneY[0] - UNIT_SIZE;
                break;
            case 'D':
                playerOneY[0] = playerOneY[0] + UNIT_SIZE;
                break;
            case 'L':
                playerOneX[0] = playerOneX[0] - UNIT_SIZE;
                break;
            case 'R':
                playerOneX[0] = playerOneX[0] + UNIT_SIZE;
                break;
        }

        switch (playerTwoDirection) {
            case 'U':
                playerTwoY[0] = playerTwoY[0] - UNIT_SIZE;
                break;
            case 'D':
                playerTwoY[0] = playerTwoY[0] + UNIT_SIZE;
                break;
            case 'L':
                playerTwoX[0] = playerTwoX[0] - UNIT_SIZE;
                break;
            case 'R':
                playerTwoX[0] = playerTwoX[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((playerOneX[0] == appleX) && (playerOneY[0] == appleY)) {
            playerOneBodyParts++;
            playerOneApplesEaten++;
            newApple();
        }

        if ((playerTwoX[0] == appleX) && (playerTwoY[0] == appleY)) {
            playerTwoBodyParts++;
            playerTwoApplesEaten++;
            newApple();
        }
    }

    public void checkCollision() {
        // Player 1 collision detection
        for (int i = playerOneBodyParts; i > 0; i--) {
            if ((playerOneX[0] == playerOneX[i]) && (playerOneY[0] == playerOneY[i])) {
                playerOneRunning = false;
            }
        }

        if (playerOneX[0] < 0 || playerOneX[0] >= SCREEN_WIDTH || playerOneY[0] < 0 || playerOneY[0] >= SCREEN_HEIGHT) {
            playerOneRunning = false;
        }

        // Player 2 collision detection
        for (int i = playerTwoBodyParts; i > 0; i--) {
            if ((playerTwoX[0] == playerTwoX[i]) && (playerTwoY[0] == playerTwoY[i])) {
                playerTwoRunning = false;
            }
        }

        if (playerTwoX[0] < 0 || playerTwoX[0] >= SCREEN_WIDTH || playerTwoY[0] < 0 || playerTwoY[0] >= SCREEN_HEIGHT) {
            playerTwoRunning = false;
        }

        // Check if player 1 hits player 2's body
        for (int i = playerTwoBodyParts; i > 0; i--) {
            if ((playerOneX[0] == playerTwoX[i]) && (playerOneY[0] == playerTwoY[i])) {
                playerOneRunning = false;
                break;
            }
        }

        // Check if player 2 hits player 1's body
        for (int i = playerOneBodyParts; i > 0; i--) {
            if ((playerTwoX[0] == playerOneX[i]) && (playerTwoY[0] == playerOneY[i])) {
                playerTwoRunning = false;
                break;
            }
        }

        if (!playerOneRunning || !playerTwoRunning) {
            timer.stop();
        }

        if (!playerOneRunning && !playerTwoRunning) {
            tie = true;
            timer.stop();
        }

        if (!playerTwoRunning) {
            playerOneWins = true;
            timer.stop();
        }

        if (!playerOneRunning) {
            playerTwoWins = true;
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.green);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Player 1 Size: " + playerOneApplesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Player 1 Size: " + playerOneApplesEaten)) / 2, SCREEN_HEIGHT / 2 + g.getFont().getSize() + 6);

        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Player 2 Size: " + playerTwoApplesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Player 2 Size: " + playerTwoApplesEaten)) / 2, SCREEN_HEIGHT / 2 + 2 * g.getFont().getSize() + 6);

        if (tie) {
            g.setColor(Color.yellow);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));

            // Calculate the width of the "Tie!" string
            FontMetrics fontMetrics = g.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth("Tie!");

            // Draw the "Tie!" string at the center of the screen
            int x = (SCREEN_WIDTH - stringWidth) / 2;
            int y = SCREEN_HEIGHT / 2;
            g.drawString("Tie!", x, y);
        } else if (playerOneWins) {
            g.setColor(Color.green);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));

            // Calculate the width of the "Player 1 Wins!" string
            FontMetrics fontMetrics = g.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth("Player 1 Wins!");

            // Draw the "Player 1 Wins!" string at the center of the screen
            int x = (SCREEN_WIDTH - stringWidth) / 2;
            int y = (SCREEN_HEIGHT / 2) - 75;
            g.drawString("Player 1 Wins!", x, y);
        } else if (playerTwoWins) {
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));

            // Calculate the width of the "Player 2 Wins!" string
            FontMetrics fontMetrics = g.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth("Player 2 Wins!");

            // Draw the "Player 2 Wins!" string at the center of the screen
            int x = (SCREEN_WIDTH - stringWidth) / 2;
            int y = (SCREEN_HEIGHT / 2) - 75;
            g.drawString("Player 2 Wins!", x, y);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (playerOneRunning && playerTwoRunning) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                // Player 1 controls
                case KeyEvent.VK_A:
                    if (playerOneDirection != 'R') {
                        playerOneDirection = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (playerOneDirection != 'L') {
                        playerOneDirection = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (playerOneDirection != 'D') {
                        playerOneDirection = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (playerOneDirection != 'U') {
                        playerOneDirection = 'D';
                    }
                    break;

                // Player 2 controls
                case KeyEvent.VK_LEFT:
                    if (playerTwoDirection != 'R') {
                        playerTwoDirection = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (playerTwoDirection != 'L') {
                        playerTwoDirection = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (playerTwoDirection != 'D') {
                        playerTwoDirection = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (playerTwoDirection != 'U') {
                        playerTwoDirection = 'D';
                    }
                    break;
            }
        }
    }
}


