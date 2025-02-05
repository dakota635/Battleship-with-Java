package battleship;

import java.util.Scanner;
import battleship.board.GameBoard;
import battleship.board.Coordinates;
import battleship.ship.ShipType;

public class Game {

    GameBoard gameBoard;
    Scanner sc;

    public Game() {
        this.sc = new Scanner(System.in);
        this.gameBoard = new GameBoard();
    }

    public void showBoard() {
        System.out.println(this.gameBoard);
    }

    public void placeFleet() {
        this.showBoard();

        for (ShipType shipType : ShipType.values()){
            boolean isPlaced = false;

            while (!isPlaced) {
                // Get player input
                System.out.printf("Enter the coordinates of the %s (%d cells):%n", shipType, shipType.getSize());
                String playerCoordinates1 = this.sc.next();
                String playerCoordinates2 = this.sc.next();

                // Attempt to place the ship
                try {
                    gameBoard.placeShip(shipType, playerCoordinates1, playerCoordinates2);
                    this.showBoard();
                    isPlaced = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void startGame() {
        System.out.println("The game starts!");

        this.showBoard();

        // Attempt to parse player attack coordinates
        Coordinates attackCoordinates = null;

        boolean validAttack = false;

        while (!validAttack) {
            // Get player input
            System.out.println("Take a shot!");

            String attackCoordinatesString = this.sc.next();

            attackCoordinates = Coordinates.fromString(attackCoordinatesString);

            if (!attackCoordinates.isOutOfBounds(GameBoard.BOARD_ROWS, GameBoard.BOARD_COLS)) {
                validAttack = true;
            } else {
                System.out.println("Error: Attack coordinates are invalid. Try again!");
            }
        }

        // Register if attack hit or missed
        boolean attackSuccess = this.gameBoard.attackSpace(attackCoordinates);
        this.showBoard();
        System.out.println(attackSuccess ? "You hit a ship!" : "You missed!");
    }
}
