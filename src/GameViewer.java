import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameViewer extends JFrame implements MouseListener {
    private Game game;
    private String cardSelected;

    // Images
    private Image startBackgroundImage;
    private Image fishy;
    private Image fish;
    private Image computerIcon;
    private Image playerIcon;

    // Window properties
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 1000;
    private final int HEIGHT_MARGIN = 700;
    private final int WIDTH_MARGIN = 50;

    // Card properties
    private final int CARD_WIDTH = 50;
    private final int CARD_HEIGHT = 100;

    // Colors & Fonts
    private static final Color HEADER_COLOR = new Color(204, 140, 77); // Muted Orange
    private static final Color BACKGROUND_COLOR = new Color(100, 140, 200); // Muted Blue-Grey
    private static final Color TEXT_COLOR = new Color(171, 191, 227); // Lighter, cool-toned blue-grey
    private static final Color DARK_TEXT_COLOR = new Color(70, 100, 160); // Darker Muted Blue-Grey
    private static final Font HEADER_FONT = new Font("Arial Black", Font.BOLD, 45);
    private static final Font TEXT_FONT = new Font("Arial Black", Font.BOLD, 28);


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

        drawPanel(g, 150, 120, WINDOW_WIDTH - 300, WINDOW_HEIGHT - 300, Color.WHITE, true);

        drawText(g, "GO FISH INSTRUCTIONS", 190, 220, HEADER_FONT, HEADER_COLOR);

        g.drawImage(fishy, 520, 580, 210, 210, this);
        g.drawImage(fish, 290, 690, 200, 100, this);
        g.drawImage(fish, 240, 600, 200, 100, this);

        drawPanel(g, 190, 260, WINDOW_WIDTH - 380, WINDOW_HEIGHT - 700, BACKGROUND_COLOR, true);

        drawText(g, "1. Take turns asking for a card rank.", 220, 320, TEXT_FONT, TEXT_COLOR);
        drawText(g, "2. If the player has it, you take all.", 220, 360, TEXT_FONT, TEXT_COLOR);
        drawText(g, "3. Else, 'Go Fish!' (Draw a card)", 220, 400, TEXT_FONT, TEXT_COLOR);
        drawText(g, "4. Collect 4-of-a-kinds or 'quads'.", 220, 440, TEXT_FONT, TEXT_COLOR);
        drawText(g, "The Player with most quads Wins!", 220, 490, TEXT_FONT, DARK_TEXT_COLOR);

    }

    public void paintMain(Graphics g) {
        drawBackground(g);

        drawPanel(g, 150, 120, WINDOW_WIDTH - 300, WINDOW_HEIGHT - 300, Color.WHITE, true);

        g.drawImage(fishy, 520, 350, 210, 210, this);
        g.drawImage(fish, 290, 490, 200, 100, this);
        g.drawImage(fish, 240, 400, 200, 100, this);

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
            panelColor = new Color(170, 80, 80);
            textColor = new Color(120, 40, 40);
        } else {
            panelColor = new Color(80, 130, 80);
            textColor = new Color(40, 80, 40);
        }

        drawPanel(g, 150, 120, WINDOW_WIDTH - 300, WINDOW_HEIGHT - 300, panelColor, true);
        drawText(g, winner.getName().equals("Computer") ? "You Lost." : "You Won!", 250, 450,
                new Font("Arial Black", Font.BOLD, 90), textColor);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(startBackgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
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
            x = 170;
            y = 290;
        }
        else {
            x = 700;
            y = 810;
        }
        for (String quad : current.getQuads()) {
            drawText(g, quad, x, y, TEXT_FONT, TEXT_COLOR);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < game.getPlayer1().getHand().size(); i++) {
            int row = i / 10; // Determines which row
            int col = i % 10; // Resets column index after 10 cards

            int x = col * CARD_WIDTH + WIDTH_MARGIN;
            int y = HEIGHT_MARGIN + (row * (CARD_HEIGHT/2));
            if (e.getX() >= x && e.getX() <= x + CARD_WIDTH/2 && e.getY() >= y && e.getY() <= y + CARD_HEIGHT) {
                System.out.println(game.getPlayer1().getHand().get(i).getRank());
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
