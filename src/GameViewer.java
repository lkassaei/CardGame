import javax.swing.*;
import java.awt.*;

public class GameViewer extends JFrame{
    private Image startBackgroundImage;
    private Image fishy;
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
        startBackgroundImage = new ImageIcon("Resources/GoFishStart.png").getImage();
        fishy = new ImageIcon("Resources/fishy.png").getImage();
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
            paintGameOver(g);
        }
    }

    public void paintInstructions(Graphics g) {
        g.drawImage(this.startBackgroundImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        g.setColor(Color.blue);
        g.fillRect(0, 250, WINDOW_WIDTH, 500);

        g.setColor(Color.WHITE);
        g.fillRect(0, 400, WINDOW_WIDTH, 200);
        g.drawImage(fishy, 400, 400, 200, 200, this);


        g.setColor(Color.darkGray);
        g.setFont(new Font("Courier New", Font.BOLD, 27));

        g.drawImage(this.computerIcon, 150, 80, 100, 100, this);
        g.drawString("Computer", 150, 80);

        g.drawImage(this.playerIcon, 700, 750, 150, 150, this);
        g.drawString("Player 1", 700, 780);

        for (int i = 0; i < game.getPlayer1().getHand().size(); i++) {
            game.getPlayer1().getHand().get(i).draw(g, i * CARD_WIDTH + WIDTH_MARGIN, HEIGHT_MARGIN,false);
        }
        for (int i = 0; i < game.getComputer().getHand().size(); i++) {
            game.getComputer().getHand().get(i).draw(g, WINDOW_WIDTH - WIDTH_MARGIN - (i + 1) * CARD_WIDTH, CARD_HEIGHT - 30, true);
        }

        for (int i = 0; i< game.getComputer().getQuads().size(); i++) {
            g.drawString(game.getComputer().getQuads().get(i), 150 + (40*i), 185);
        }

        for (int i = 0; i< game.getPlayer1().getQuads().size(); i++) {
            g.drawString(game.getPlayer1().getQuads().get(i) + ", ", WINDOW_WIDTH - (game.getComputer().getQuads().size()*40) + (40*i), 890);
        }
    }

    public void paintGameOver(Graphics g) {list();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        g.setColor(Color.blue);
        g.fillRect(0, 250, WINDOW_WIDTH, 500);

        g.setColor(Color.WHITE);
        g.fillRect(0, 400, WINDOW_WIDTH, 200);
        g.drawImage(fishy, 400, 400, 200, 200, this);
    }
}
