import javax.swing.*;
import java.awt.*;

public class GameViewer extends JFrame{
    private Image backgroundImage;
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 600;
    private Game game;

    public GameViewer(Game game) {
        this.game = game;
        backgroundImage = new ImageIcon("Resources/gofish.jpg").getImage();
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
    }

    public void paintMain(Graphics g) {
        g.setColor(new Color(0,100,0));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void paintGameOver(Graphics g) {

    }
}
