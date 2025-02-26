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
    private static final int X_ORIGIN = 0;
    private static final int Y_ORIGIN = 0;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 1000;
    private static final int HEIGHT_MARGIN = 700;
    private static final int WIDTH_MARGIN = 50;

    // Panel properties
    private static final int PANEL_X = 150;
    private static final int PANEL_Y = 120;
    private static final int PANEL_MARGIN = 300;
    private static final int HEADER_PANEL_Y1 = 300;
    private static final int HEADER_PANEL_Y2 = 620;
    private static final int HEADER_PANEL_HEIGHT = 40;

    // Card properties
    private static final int CARD_WIDTH = 50;
    private static final int CARD_HEIGHT = 100;
    private static final int MAX_CARDS_PER_ROW = 10;
    private static final double CARD_SPACING = 0.5;

    // Quad properties
    private static final int PLAYER_QUAD_X = 700;
    private static final int PLAYER_QUAD_Y = 810;
    private static final int COMPUTER_QUAD_X = 170;
    private static final int COMPUTER_QUAD_Y = 290;
    private static final int QUAD_MARGIN = 20;

    // Fishy/Shark properties
    private static final int SHARK_DIMENSION = 210;
    private static final int SHARK_X = 520;
    private static final int SHARK_Y_INSTRUCTIONS = 580;
    private static final int SHARK_Y_MAIN = 380;

    // Fish properties
    private static final int FISH_DIMENSION = 200;
    private static final int FISH1_X = 290;
    private static final int FISH1_Y_INSTRUCTIONS = 690;
    private static final int FISH1_Y_MAIN = 490;
    private static final int FISH2_X = 240;
    private static final int FISH2_Y_INSTRUCTIONS = 600;
    private static final int FISH2_Y_MAIN = 400;

    // Icon properties
    private static final int COMPUTER_ICON_DIMENSION = 100;
    private static final int PLAYER_ICON_DIMENSION = 150;
    private static final int COMPUTER_ICON_X = 170;
    private static final int COMPUTER_ICON_Y = 180;
    private static final int COMPUTER_TEXT_Y = 160;
    private static final int PLAYER_ICON_X = 700;
    private static final int PLAYER_ICON_Y = 670;
    private static final int PLAYER_TEXT_Y = 700;
    private static final int ICON_TEXT_OFFSET = 20;

    // Instructions properties
    private static final int HEADER_X = 190;
    private static final int HEADER_Y = 220;
    private static final int INSTRUCTION_X = 220;
    private static final int STARTING_INSTRUCTION_Y = 320;
    private static final int INSTRUCTION_MARGIN = 40;
    private static final int INSTRUCTION_PANEL_X = 190;
    private static final int INSTRUCTION_PANEL_Y = 260;
    private static final int INSTRUCTION_PANEL_WIDTH = 380;
    private static final int INSTRUCTION_PANEL_HEIGHT = 700;

    // Game over properties
    private static final int GAME_OVER_X = 250;
    private static final int GAME_OVER_Y = 450;

    // Colors & Fonts
    private static final Color HEADER_COLOR = new Color(204, 140, 77); // Muted Orange
    private static final Color BACKGROUND_COLOR = new Color(100, 140, 200); // Muted Blue-Grey
    private static final Color TEXT_COLOR = new Color(171, 191, 227); // Light Blue-Grey
    private static final Color DARK_TEXT_COLOR = new Color(70, 100, 160); // Dark Blue-Grey
    private static final Color WINNING_BACKGROUND_COLOR = new Color(80, 130, 80);
    private static final Color WINNING_TEXT_COLOR = new Color(40, 80, 40);
    private static final Color LOSING_BACKGROUND_COLOR = new Color(170, 80, 80);
    private static final Color LOSING_TEXT_COLOR = new Color(120, 40, 40);

    private static final Font HEADER_FONT = new Font("Arial Black", Font.BOLD, 45);
    private static final Font TEXT_FONT = new Font("Arial Black", Font.BOLD, 28);
    private static final Font GAME_OVER_FONT = new Font("Arial Black", Font.BOLD, 90);

    // Computer card placement properties
    private static final int COMPUTER_Y_START = 80;
    private static final int COMPUTER_X_OFFSET = 50;

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
        // Make sure window exits when you close it, has the title "Card Game", has a set size, and can be seen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Card Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Calls paint method
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        // Find what state the game is in
        int state = game.getState();
        // Based on the state, draw the right things
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
        // Draw the background of the instruction screen
        drawBackground(g);

        // Draw the main instruction panel
        drawPanel(g, PANEL_X, PANEL_Y, WINDOW_WIDTH - PANEL_MARGIN, WINDOW_HEIGHT - PANEL_MARGIN, Color.WHITE, true);

        // Draw the header text for instructions
        drawText(g, "GO FISH INSTRUCTIONS", HEADER_X, HEADER_Y, HEADER_FONT, HEADER_COLOR);

        // Draw the decorative images (shark and fish)
        g.drawImage(fishy, SHARK_X, SHARK_Y_INSTRUCTIONS, SHARK_DIMENSION, SHARK_DIMENSION, this);
        g.drawImage(fish, FISH1_X, FISH1_Y_INSTRUCTIONS, FISH_DIMENSION, FISH_DIMENSION / 2, this);
        g.drawImage(fish, FISH2_X, FISH2_Y_INSTRUCTIONS, FISH_DIMENSION, FISH_DIMENSION / 2, this);

        // Draw the panel behind the instructions
        drawPanel(g, INSTRUCTION_PANEL_X, INSTRUCTION_PANEL_Y, WINDOW_WIDTH - INSTRUCTION_PANEL_WIDTH, WINDOW_HEIGHT - INSTRUCTION_PANEL_HEIGHT, BACKGROUND_COLOR, true);

        // Loop through and display the game instructions using drawText
        drawText(g, "1. Take turns asking for a card rank.", INSTRUCTION_X, STARTING_INSTRUCTION_Y, TEXT_FONT, TEXT_COLOR);
        drawText(g, "2. If the player has it, you take all.", INSTRUCTION_X, STARTING_INSTRUCTION_Y + INSTRUCTION_MARGIN, TEXT_FONT, TEXT_COLOR);
        drawText(g, "3. Else, 'Go Fish!' (Draw a card)", INSTRUCTION_X, STARTING_INSTRUCTION_Y + 2 * INSTRUCTION_MARGIN, TEXT_FONT, TEXT_COLOR);
        drawText(g, "4. Collect 4-of-a-kinds or 'quads'.", INSTRUCTION_X, STARTING_INSTRUCTION_Y + 3 * INSTRUCTION_MARGIN, TEXT_FONT, TEXT_COLOR);
        drawText(g, "The Player with most quads Wins!", INSTRUCTION_X, STARTING_INSTRUCTION_Y + 4 * INSTRUCTION_MARGIN, TEXT_FONT, DARK_TEXT_COLOR);
    }

    public void paintMain(Graphics g) {
        // Draw the background of the main game screen
        drawBackground(g);

        // Draw the main panel where the game elements will be displayed
        drawPanel(g, PANEL_X, PANEL_Y, WINDOW_WIDTH - PANEL_MARGIN, WINDOW_HEIGHT - PANEL_MARGIN, Color.WHITE, true);

        // Draw the decorative shark and fish images
        g.drawImage(fishy, SHARK_X, SHARK_Y_MAIN, SHARK_DIMENSION, SHARK_DIMENSION, this);
        g.drawImage(fish, FISH1_X, FISH1_Y_MAIN, FISH_DIMENSION, FISH_DIMENSION / 2, this);
        g.drawImage(fish, FISH2_X, FISH2_Y_MAIN, FISH_DIMENSION, FISH_DIMENSION / 2, this);

        // Draw the computer player's icon and label
        g.drawImage(this.computerIcon, COMPUTER_ICON_X, COMPUTER_ICON_Y, COMPUTER_ICON_DIMENSION, COMPUTER_ICON_DIMENSION, this);
        drawText(g, "Computer", COMPUTER_ICON_X, COMPUTER_TEXT_Y + ICON_TEXT_OFFSET, TEXT_FONT, HEADER_COLOR);

        // Draw the player’s icon and label
        g.drawImage(this.playerIcon, PLAYER_ICON_X, PLAYER_ICON_Y, PLAYER_ICON_DIMENSION, PLAYER_ICON_DIMENSION, this);
        drawText(g, "Player 1", PLAYER_ICON_X, PLAYER_TEXT_Y, TEXT_FONT, HEADER_COLOR);

        // Draw header panels to separate sections of the interface
        drawPanel(g, PANEL_X, HEADER_PANEL_Y1, WINDOW_WIDTH - PANEL_MARGIN, HEADER_PANEL_HEIGHT, HEADER_COLOR, false);
        drawPanel(g, PANEL_X, HEADER_PANEL_Y2, WINDOW_WIDTH - PANEL_MARGIN, HEADER_PANEL_HEIGHT, HEADER_COLOR, false);

        // Draw the cards currently in the hands of both the player and computer
        drawCards(game.getPlayer1(), g);
        drawCards(game.getComputer(), g);

        // Draw the collected 'quads' (sets of four matching cards) for both players
        drawQuads(game.getPlayer1(), g);
        drawQuads(game.getComputer(), g);
    }

    public void paintGameOver(Graphics g, Player winner) {
        // Draw the background for the game over screen
        drawBackground(g);

        // Determine the text and panel/text colors based on whether the player won or lost
        Color panelColor;
        Color textColor;
        String text = "";

        if (winner.getName().equals("Computer")) {
            panelColor = LOSING_BACKGROUND_COLOR;
            textColor = LOSING_TEXT_COLOR;
            text = "You Lost.";
        } else {
            panelColor = WINNING_BACKGROUND_COLOR;
            textColor = WINNING_TEXT_COLOR;
            text = "You Won!";
        }

        // Draw the game over panel with appropriate color
        drawPanel(g, PANEL_X, PANEL_Y, WINDOW_WIDTH - PANEL_MARGIN, WINDOW_HEIGHT - PANEL_MARGIN, panelColor, true);

        // Display the game result message
        drawText(g, text, GAME_OVER_X, GAME_OVER_Y, GAME_OVER_FONT, textColor);
    }

    private void drawBackground(Graphics g) {
        // Draws the game background image
        g.drawImage(startBackgroundImage, X_ORIGIN, Y_ORIGIN, WINDOW_WIDTH, WINDOW_HEIGHT, this);
    }

    private void drawPanel(Graphics g, int x, int y, int width, int height, Color color, boolean isFilled) {
        // Sets the panel color
        g.setColor(color);
        if (isFilled) {
            g.fillRect(x, y, width, height); // Filled rectangle for solid panels
        }
        else {
            g.drawRect(x, y, width, height); // Outline-only rectangle for borders
        }
    }

    private void drawText(Graphics g, String text, int x, int y, Font font, Color color) {
        // Sets the font and color, then draws the text
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public void drawCards(Player current, Graphics g) {
        // Loop through all cards in the player's hand and draw them on the screen
        for (int i = 0; i < current.getHand().size(); i++) {
            int row = i / MAX_CARDS_PER_ROW; // Determines which row the card should be placed in
            int col = i % MAX_CARDS_PER_ROW; // Determines the column position within a row
            int x;
            int y;

            if (current.getName().equals("Computer")) {
                // Align the computer's cards on the right side
                x = WINDOW_WIDTH - WIDTH_MARGIN - (col + 1) * CARD_WIDTH - COMPUTER_X_OFFSET;
                y = COMPUTER_Y_START + (row * (int)(CARD_HEIGHT * CARD_SPACING));
            }
            else {
                // Align the player's cards on the left side
                x = col * CARD_WIDTH + WIDTH_MARGIN;
                y = HEIGHT_MARGIN + (row * (int)(CARD_HEIGHT * CARD_SPACING));
            }

            // Draw the card, hiding it if it belongs to the computer
            current.getHand().get(i).draw(g, x, y, current.getName().equals("Computer"));
        }
    }

    public void drawQuads(Player current, Graphics g) {
        // Set the initial x and y positions for displaying quads
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

        // Display each collected quad at a calculated position
        for (int i = 0; i < current.getQuads().size(); i++) {
            drawText(g, current.getQuads().get(i), x + QUAD_MARGIN * i, y, TEXT_FONT, TEXT_COLOR);
        }
    }
}
