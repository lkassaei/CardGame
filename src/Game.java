import java.util.Scanner;

public class Game {
    private Player player1;
    private Deck deck;

    public Game(Player player1) {
       this.player1 = player1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name 1: ");
        String name1 = scanner.next();
        Player p1 = new Player(name1);

        Game g1 = new Game(p1);
    }
}



