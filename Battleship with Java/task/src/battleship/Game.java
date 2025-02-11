package battleship;

import java.util.Scanner;
import battleship.board.GameBoard;
import battleship.board.Coordinates;
import battleship.ship.ShipType;

public class Game {

    GameBoard[] gameBoards;
    Scanner sc;

    public Game() {
        this.sc = new Scanner(System.in);
        this.gameBoards = new GameBoard[2];
        this.gameBoards[0] = new GameBoard();
        this.gameBoards[1] = new GameBoard();
    }

    public void showBoard(int activePlayer) {
        System.out.println(this.gameBoards[activePlayer].showBoard(true));
    }

    public void showFullBoard(int activePlayer) {
        int inactivePlayer = (activePlayer == 0) ? 1 : 0;
        System.out.println(this.gameBoards[inactivePlayer].showBoard(false));
        System.out.println("---------------------");
        System.out.println(this.gameBoards[activePlayer].showBoard(true));
    }

    public void placeFleet(int activePlayer) {
        System.out.printf("Player %d, place your ships on the game field%n", (activePlayer + 1));
        this.showBoard(activePlayer);

        for (ShipType shipType : ShipType.values()){
            boolean isPlaced = false;

            while (!isPlaced) {
                // Get player input
                System.out.printf("Enter the coordinates of the %s (%d cells):%n", shipType, shipType.getSize());
                String playerCoordinates1 = this.sc.next();
                String playerCoordinates2 = this.sc.next();

                // Attempt to place the ship
                try {
                    this.gameBoards[activePlayer].placeShip(shipType, playerCoordinates1, playerCoordinates2);
                    this.showBoard(activePlayer);
                    isPlaced = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        sc.nextLine();
        sc.nextLine();
    }

    public void startGame() {
        // Prep the boards to start the game
        this.gameBoards[0].hideAllSpaces();
        this.gameBoards[1].hideAllSpaces();

        int inactivePlayer = 0;
        int activePlayer = 1;
        GameBoard inactivePlayerBoard = this.gameBoards[inactivePlayer];
        GameBoard activePlayerBoard = this.gameBoards[activePlayer];

        while (true) {
            // Swap players here to ensure endgame condition is checked BEFORE switching active players
            int tempPlayer = inactivePlayer;
            inactivePlayer = activePlayer;
            activePlayer = tempPlayer;

            GameBoard tempPlayerBoard = inactivePlayerBoard;
            inactivePlayerBoard = activePlayerBoard;
            activePlayerBoard = tempPlayerBoard;

            this.showFullBoard(activePlayer);

            // Attempt to parse player attack coordinates
            Coordinates attackCoordinates = null;
            boolean validAttack = false;
            while (!validAttack) {
                // Get player input
                System.out.printf("Player %d, it's your turn:%n", (activePlayer + 1));

                String attackCoordinatesString = this.sc.next();

                attackCoordinates = Coordinates.fromString(attackCoordinatesString);

                if (!attackCoordinates.isOutOfBounds(GameBoard.BOARD_ROWS, GameBoard.BOARD_COLS)) {
                    validAttack = true;
                } else {
                    System.out.println("Error: Attack coordinates are invalid. Try again!");
                }
            }

            // Register if attack hit or missed
            boolean attackSuccess = inactivePlayerBoard.attackSpace(attackCoordinates);
            this.showFullBoard(activePlayer);
            System.out.println(attackSuccess ? "You hit a ship!" : "You missed!");

            if (inactivePlayerBoard.isGameOver()) {
                break;
            }

            // Continue the game
            System.out.println("Press Enter and pass the move to another player");
            sc.nextLine();
            sc.nextLine();
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
    }
}
