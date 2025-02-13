import javax.swing.*;
import java.awt.*;

public class GameViewer extends JFrame{
    private Image backgroundImage;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 1000;
    private final int HEIGHT_MARGIN = 500;
    private final int WIDTH_MARGIN = 50;
    private final int CARD_WIDTH = 50;
    private final int CARD_HEIGHT = 100;
    private Game game;

    public GameViewer(Game game) {
        this.game = game;
        backgroundImage = new ImageIcon("Resources/GoFishStart.png").getImage();
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
            paintGameOver(g);
        }
    }

    public void paintInstructions(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_WIDTH, this);
        g.setColor(Color.black);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 27));
        g.drawString("1. You take a turn, then the computer.", 125, 450);
        g.drawString("2. Ask for a rank from the other player.", 125, 490);
        g.drawString("3. If they have it, they must give all copies.", 125, 530);
        g.drawString("4. If not, they say 'Go Fish!' and you draw.", 125, 570);
        g.drawString("5. Collect four of a kind (a quad) for points.", 125, 610);
        g.drawString("6. The game ends when all quads are collected.", 125, 650);
        g.drawString("7. The player with the most quads wins!", 125, 690);
    }

    public void paintMain(Graphics g) {
        g.setColor(new Color(50, 180, 255)); // Bright Ocean Blue
        g.fillRect(0, 0, WINDOW_WIDTH, 20);

        g.setColor(new Color(30, 144, 255)); // Deep Sky Blue
        g.fillRect(0, 200, WINDOW_WIDTH, 40);

        g.setColor(new Color(0, 119, 190)); // Rich Cerulean Blue
        g.fillRect(0, 400, WINDOW_WIDTH, 240);

        g.setColor(new Color(0, 77, 153)); // Deep Water Blue
        g.fillRect(0, 600, WINDOW_WIDTH, 300);

        g.setColor(new Color(0, 38, 77));  // Abyssal Dark Blue
        g.fillRect(0, 800, WINDOW_WIDTH, 400);
        for (int i = 0; i < game.getPlayer1().getHand().size(); i++) {
            game.getPlayer1().getHand().get(i).draw(g, i * CARD_WIDTH + WIDTH_MARGIN, HEIGHT_MARGIN, false);
        }
        for (int i = 0; i < game.getComputer().getHand().size(); i++) {
            game.getComputer().getHand().get(i).draw(g, WINDOW_WIDTH - WIDTH_MARGIN - (i + 1) * CARD_WIDTH, CARD_HEIGHT, true);
        }
    }

    public void paintGameOver(Graphics g) {

    }
}
