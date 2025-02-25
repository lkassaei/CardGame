import javax.swing.*;
import java.awt.*;

public class GameViewer extends JFrame{
    private Game game;

    // Images
    private Image startBackgroundImage;
    private Image fishy;
    private Image fish;
    private Image computerIcon;
    private Image playerIcon;

    // Window properties
    private final int X_ORIGIN = 0;
    private final int Y_ORIGIN = 0;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 1000;
    private final int HEIGHT_MARGIN = 700;
    private final int WIDTH_MARGIN = 50;

    private final int PANEL_X = 150;
    private final int PANEL_Y = 120;
    private final int PANEL_MARGIN = 300;

    // Card properties
    private final int CARD_WIDTH = 50;
    private final int CARD_HEIGHT = 100;

    // Fishy/Shark properties
    private final int SHARK_DIMENSION = 210;

    // Fish properties
    private final int FISH_DIMENSION = 200;

    // Instructions properties
    private final int INSTRUCTION_X = 220;
    private final int STARTING_INSTRUCTION_Y = 320;
    private final int INSTRUCTION_MARGIN = 40;

    // Quad properties
    private final int PLAYER_QUAD_X = 700;
    private final int PLAYER_QUAD_Y = 810;
    private final int COMPUTER_QUAD_X = 170;
    private final int COMPUTER_QUAD_Y = 290;
    private final int QUAD_MARGIN = 20;

    // Colors & Fonts
    private static final Color HEADER_COLOR = new Color(204, 140, 77); // Muted Orange
    private static final Color BACKGROUND_COLOR = new Color(100, 140, 200); // Muted Blue-Grey
    private static final Color TEXT_COLOR = new Color(171, 191, 227); // Lighter, cool-toned blue-grey
    private static final Color DARK_TEXT_COLOR = new Color(70, 100, 160); // Darker Muted Blue-Grey
    private static final Font HEADER_FONT = new Font("Arial Black", Font.BOLD, 45);
    private static final Font TEXT_FONT = new Font("Arial Black", Font.BOLD, 28);

    private static final Color WINNING_BACKGROUND_COLOR = new Color(80, 130, 80);
    private static final Color WINNING_TEXT_COLOR = new Color(40, 80, 40);
    private static final Color LOSING_BACKGROUND_COLOR = new Color(170, 80, 80);
    private static final Color LOSING_TEXT_COLOR = new Color(120, 40, 40);
    private static final Font GAME_OVER_FONT = new Font("Arial Black", Font.BOLD, 90);
    private static final int GAME_OVER_X = 250;
    private static final int GAME_OVER_Y = 450;


    public GameViewer(Game game) {
        this.game = game;

        // Create all images
        startBackgroundImage = new ImageIcon("Resources/water.png").getImage();
        fishy = new ImageIcon("Resources/fishy.png").getImage();
        fish = new ImageIcon("Resources/fish.png").getImage();
        computerIcon = new ImageIcon("Resources/ComputerIcon.jpg").getImage();
        playerIcon = new ImageIcon("Resources/PlayerIcon.jpg").getImage();

        // Get window running
        setupWindow();
    }

    public void setupWindow() {
        // Make sure window exits when you close it, has the title "Card Game",has a set size, and can be seen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Card Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Calls paint method
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        int state = game.getState();
        if (state == Game.INSTRUCTION_STATE) {
            paintInstructions(g);
        }
        else if (state == Game.MAIN_STATE) {
            paintMain(g);
        }
        else if (state == Game.GAME_OVER_STATE) {
            paintGameOver(g, game.getWinningPlayer());
        }
    }

    public void paintInstructions(Graphics g) {
        drawBackground(g);

        drawPanel(g, PANEL_X, PANEL_Y, WINDOW_WIDTH - PANEL_MARGIN, WINDOW_HEIGHT - PANEL_MARGIN, Color.WHITE, true);

        drawText(g, "GO FISH INSTRUCTIONS", 190, 220, HEADER_FONT, HEADER_COLOR);

        g.drawImage(fishy, 520, 580, SHARK_DIMENSION, SHARK_DIMENSION, this);
        g.drawImage(fish, 290, 690, FISH_DIMENSION, FISH_DIMENSION/2, this);
        g.drawImage(fish, 240, 600, FISH_DIMENSION, FISH_DIMENSION/2, this);

        drawPanel(g, 190, 260, WINDOW_WIDTH - 380, WINDOW_HEIGHT - 700, BACKGROUND_COLOR, true);

        drawText(g, "1. Take turns asking for a card rank.", INSTRUCTION_X, STARTING_INSTRUCTION_Y, TEXT_FONT, TEXT_COLOR);
        drawText(g, "2. If the player has it, you take all.", INSTRUCTION_X, STARTING_INSTRUCTION_Y + INSTRUCTION_MARGIN, TEXT_FONT, TEXT_COLOR);
        drawText(g, "3. Else, 'Go Fish!' (Draw a card)", INSTRUCTION_X, STARTING_INSTRUCTION_Y + 2 * INSTRUCTION_MARGIN, TEXT_FONT, TEXT_COLOR);
        drawText(g, "4. Collect 4-of-a-kinds or 'quads'.", INSTRUCTION_X, STARTING_INSTRUCTION_Y + 3 * INSTRUCTION_MARGIN, TEXT_FONT, TEXT_COLOR);
        drawText(g, "The Player with most quads Wins!", INSTRUCTION_X, STARTING_INSTRUCTION_Y + 4 * INSTRUCTION_MARGIN, TEXT_FONT, DARK_TEXT_COLOR);

    }

    public void paintMain(Graphics g) {
        drawBackground(g);

        drawPanel(g, PANEL_X, PANEL_Y, WINDOW_WIDTH - PANEL_MARGIN, WINDOW_HEIGHT - PANEL_MARGIN, Color.WHITE, true);

        g.drawImage(fishy, 520, 350, SHARK_DIMENSION, SHARK_DIMENSION, this);
        g.drawImage(fish, 290, 490, FISH_DIMENSION, FISH_DIMENSION/2, this);
        g.drawImage(fish, 240, 400, FISH_DIMENSION, FISH_DIMENSION/2, this);

        g.drawImage(this.computerIcon, 170, 180, 100, 100, this);
        drawText(g, "Computer", 170, 180, TEXT_FONT, HEADER_COLOR);

        g.drawImage(this.playerIcon, 700, 670, 150, 150, this);
        drawText(g, "Player 1", 700, 700, TEXT_FONT, HEADER_COLOR);

        drawPanel(g, 150, 300, WINDOW_WIDTH - 2 * 150, 40, HEADER_COLOR, false);
        drawPanel(g, 150, 620, WINDOW_WIDTH - 2 * 150, 40, HEADER_COLOR, false);

        drawCards(game.getPlayer1(), g);
        drawCards(game.getComputer(), g);
        drawQuads(game.getPlayer1(), g);
        drawQuads(game.getComputer(), g);
    }

    public void paintGameOver(Graphics g, Player winner) {list();
        drawBackground(g);

        Color panelColor;
        Color textColor;
        if (winner.getName().equals("Computer")) {
            panelColor = LOSING_BACKGROUND_COLOR;
            textColor = LOSING_TEXT_COLOR;
        } else {
            panelColor = WINNING_BACKGROUND_COLOR;
            textColor = WINNING_TEXT_COLOR;
        }

        drawPanel(g, PANEL_X, PANEL_Y, WINDOW_WIDTH - PANEL_MARGIN, WINDOW_HEIGHT - PANEL_MARGIN, panelColor, true);
        drawText(g, winner.getName().equals("Computer") ? "You Lost." : "You Won!", GAME_OVER_X, GAME_OVER_Y,
                GAME_OVER_FONT, textColor);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(startBackgroundImage, X_ORIGIN, Y_ORIGIN, WINDOW_WIDTH, WINDOW_HEIGHT, this);
    }

    private void drawPanel(Graphics g, int x, int y, int width, int height, Color color, boolean isFilled) {
        g.setColor(color);
        if (isFilled) {
            g.fillRect(x, y, width, height);
        }
        else {
            g.drawRect(x, y, width, height);
        }
    }

    private void drawText(Graphics g, String text, int x, int y, Font font, Color color) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public void drawCards(Player current, Graphics g) {
        for (int i = 0; i < current.getHand().size(); i++) {
            int row = i / 10; // Determines which row
            int col = i % 10; // Resets column index after 10 cards
            int x;
            int y;

            if (current.getName().equals("Computer")) {
                x = WINDOW_WIDTH - WIDTH_MARGIN - (col + 1) * CARD_WIDTH - 50;
                y = 80 + (row * (CARD_HEIGHT/2));
            }
            else {
                x = col * CARD_WIDTH + WIDTH_MARGIN;
                y = HEIGHT_MARGIN + (row * (CARD_HEIGHT/2));
            }
            current.getHand().get(i).draw(g, x, y, current.getName().equals("Computer"));
        }
    }

    public void drawQuads(Player current, Graphics g) {
        int x;
        int y;
        if (current.getName().equals("Computer")) {
            x = COMPUTER_QUAD_X;
            y = COMPUTER_QUAD_Y;
        }
        else {
            x = PLAYER_QUAD_X;
            y = PLAYER_QUAD_Y;
        }
        for (int i = 0; i < current.getQuads().size(); i++) {
            drawText(g, current.getQuads().get(i), x + QUAD_MARGIN * i, y, TEXT_FONT, TEXT_COLOR);
        }
    }
}
