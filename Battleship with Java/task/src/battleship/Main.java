package battleship;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        for (int i = 0; i < 2; i++) {
            // Creates boards for 2 players
            game.placeFleet(i);
        }

        game.startGame();
    }
}
