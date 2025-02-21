import javax.swing.*;
import java.awt.*;

public class GameViewer extends JFrame{
    private Image startBackgroundImage;
    private Image fishy;
    private Image fish;
    private Image welcome;
    private Image sharky;

    private Image computerIcon;
    private Image playerIcon;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 1000;
    private final int HEIGHT_MARGIN = 700;
    private final int WIDTH_MARGIN = 50;
    private final int CARD_WIDTH = 50;
    private final int CARD_HEIGHT = 100;
    private Game game;

    public GameViewer(Game game) {
        this.game = game;
        startBackgroundImage = new ImageIcon("Resources/water.png").getImage();
        fishy = new ImageIcon("Resources/fishy.png").getImage();
        fish = new ImageIcon("Resources/fish.png").getImage();
        sharky = new ImageIcon("Resources/sharky.png").getImage();
        welcome = new ImageIcon("Resources/welcome.png").getImage();
        computerIcon = new ImageIcon("Resources/ComputerIcon.jpg").getImage();
        playerIcon = new ImageIcon("Resources/PlayerIcon.jpg").getImage();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Card Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        if (game.getState() == Game.INSTRUCTION_STATE) {
            paintInstructions(g);
        }
        else if (game.getState() == Game.MAIN_STATE) {
            paintMain(g);
        }
        else if (game.getState() == Game.GAME_OVER_STATE) {
            paintGameOver(g, game.getWinningPlayer());
        }
    }

    public void paintInstructions(Graphics g) {
        g.drawImage(this.startBackgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);

        g.setColor(Color.WHITE);
        g.fillRect(150, 120, WINDOW_WIDTH - 2 *150, WINDOW_HEIGHT - 300);

        g.setColor(new Color(204, 140, 77)); // Muted Orange
        g.setFont(new Font("Arial Black", Font.BOLD, 45));
        g.drawString("GO FISH INSTRUCTIONS", 190, 220);

        g.drawImage(fishy, 520, 580, 210, 210, this);
        g.drawImage(fish, 290, 690, 200, 100, this);
        g.drawImage(fish, 240, 600, 200, 100, this);

        g.setColor(new Color(100, 140, 200)); // Muted Blue-Grey with More Blue
        g.fillRect(190, 260, WINDOW_WIDTH - 2*190, WINDOW_HEIGHT - 700);


        g.setColor(new Color(171, 191, 227)); // Lighter, cool-toned blue-grey
        g.setFont(new Font("Arial Black", Font.BOLD, 29));
        g.drawString("1. Take turns asking for a card rank.", 220, 320);
        g.drawString("2. If the player has it, you take all.", 220, 360);
        g.drawString("3. Else, 'Go Fish!' (Draw a card)", 220, 400);
        g.drawString("3. Collect 4-of-a-kinds or 'quads'. " , 220, 440);
        g.setColor(new Color(70, 100, 160)); // Darker Muted Blue-Grey
        g.setFont(new Font("Arial Black", Font.BOLD, 30));
        g.drawString("The Player with most quads Wins!", 220, 490);
    }

    public void paintMain(Graphics g) {
        g.drawImage(this.startBackgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);

        g.setColor(Color.WHITE);
        g.fillRect(150, 120, WINDOW_WIDTH - 2 *150, WINDOW_HEIGHT - 300);

        g.drawImage(fishy, 520, 350, 210, 210, this);
        g.drawImage(fish, 290, 490, 200, 100, this);
        g.drawImage(fish, 240, 400, 200, 100, this);

        g.setColor(new Color(204, 140, 77)); // Muted Orange
        //g.setColor(new Color(100, 140, 200)); // Muted Blue-Grey with More Blue
        g.setFont(new Font("Arial Black", Font.BOLD, 28));

        g.drawImage(this.computerIcon, 170, 180, 100, 100, this);
        g.drawString("Computer", 170, 180);

        g.drawImage(this.playerIcon, 700, 670, 150, 150, this);
        g.drawString("Player 1", 700, 700);

        g.drawRect(150, 300, WINDOW_WIDTH - 2 *150, 40);
        g.drawRect(150, 620, WINDOW_WIDTH - 2 *150, 40);

        drawCards(game.getPlayer1(), g);
        drawCards(game.getComputer(), g);

        drawQuads(game.getPlayer1(), g);
        drawQuads(game.getComputer(), g);
    }

    public void paintGameOver(Graphics g, Player winner) {list();
        g.drawImage(this.startBackgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);

        g.setColor(Color.WHITE);
        g.fillRect(150, 120, WINDOW_WIDTH - 2 *150, WINDOW_HEIGHT - 300);

        g.drawImage(this.sharky, 350, 380, 300, 300, this);

        g.setColor(new Color(204, 140, 77)); // Muted Orange


        g.drawRect(150, 300, WINDOW_WIDTH - 2 *150, 40);
        g.drawRect(150, 620, WINDOW_WIDTH - 2 *150, 40);

        g.setColor(new Color(100, 140, 200)); // Muted Blue-Grey with More Blue
        g.setFont(new Font("Arial Black", Font.BOLD, 45));
        g.drawString(winner.getName() + "Wins!", 400, 300);

    }

    public void drawCards(Player current, Graphics g) {
        for (int i = 0; i < current.getHand().size(); i++) {
            int row = i / 10; // Determines if it's the first row (0) or second row (1)
            int col = i % 10; // Resets column index after 10 cards

            if (current.getName().equals("Computer")) {
                int x = WINDOW_WIDTH - WIDTH_MARGIN - (col + 1) * CARD_WIDTH - 50; // Moves cards slightly left
                int y = 80 + (row * (CARD_HEIGHT/2));
                current.getHand().get(i).draw(g, x, y, true);
            }
            else {
                int x = col * CARD_WIDTH + WIDTH_MARGIN;
                int y = HEIGHT_MARGIN + (row * (CARD_HEIGHT/2));
                current.getHand().get(i).draw(g, x, y, false);
            }
        }
    }

    public void drawQuads(Player current, Graphics g) {
        for (int i = 0; i< current.getQuads().size(); i++) {
            if (current.getName().equals("Computer")) {
                g.drawString(current.getQuads().get(i), 170, 290);
            }
            else {
                g.drawString(current.getQuads().get(i), 700, 810);
            }
        }
    }
}
