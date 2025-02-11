package battleship.board;

import battleship.ship.Fleet;
import battleship.ship.Ship;
import battleship.ship.ShipType;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    public static final int BOARD_ROWS = 10;
    public static final int BOARD_COLS = 10;

    private final BoardSpace[][] gameBoard;
    private final Fleet fleet;

    public GameBoard() {
        this.fleet = new Fleet();
        this.gameBoard = new BoardSpace[GameBoard.BOARD_ROWS + 1][GameBoard.BOARD_COLS + 1];

        for (int row = 0; row <= GameBoard.BOARD_ROWS; row++) {
            for (int col = 0; col <= GameBoard.BOARD_COLS; col++) {
                this.gameBoard[row][col] = new BoardSpace();

                // Build the labels
                if (row == 0 && col == 0){
                    this.gameBoard[row][col].setAsLabel(" ");
                } else if (row == 0) {
                    this.gameBoard[row][col].setAsLabel(String.format("%2d", col));
                } else if (col == 0) {
                    this.gameBoard[row][col].setAsLabel(Character.toString('A' + row - 1));
                }
            }
        }
    }

    public void placeShip(ShipType shipType, String start, String end) {
        Coordinates startCoordinates = Coordinates.fromString(start);
        Coordinates endCoordinates = Coordinates.fromString(end);

        // Ensure coordinates are in bounds
        if (startCoordinates.isOutOfBounds(GameBoard.BOARD_ROWS, GameBoard.BOARD_COLS) || endCoordinates.isOutOfBounds(GameBoard.BOARD_ROWS, GameBoard.BOARD_COLS)) {
            throw new IllegalArgumentException("Error: Coordinates are out-of-bounds!");
        }

        // Ensure placement is not diagonal
        if ((startCoordinates.row() != endCoordinates.row()) && (startCoordinates.col() != endCoordinates.col())) {
            throw new IllegalArgumentException("Error: Diagonal ship placement is not allowed!");
        }

        // Order coordinates to simplify ship placement
        if (startCoordinates.compareTo(endCoordinates) > 0) {
            Coordinates temp = startCoordinates;
            startCoordinates = endCoordinates;
            endCoordinates = temp;
        }

        // Ensure coordinate distance matches ship size
        int shipLength = Math.max(Math.abs(startCoordinates.row() - endCoordinates.row()), Math.abs(startCoordinates.col() - endCoordinates.col())) + 1;
        if (shipType.getSize() != shipLength) {
            throw new IllegalArgumentException("Error: Coordinate distance does not match ship size!");
        }

        // Place the ship
        Ship activeShip = this.fleet.getShip(Fleet.SHIP_INDEX.get(shipType));

        // Used for rollback
        List<Coordinates> placedCoordinates = new ArrayList<>();

        for (int i = 0; i < activeShip.getSize(); i++) {
            int row = startCoordinates.row();
            int col = startCoordinates.col();

            if (startCoordinates.isRelationshipHorizontal(endCoordinates)) {
                col += i;
            } else {
                row += i;
            }

            // Identify overlapping ships
            if (this.gameBoard[row][col].isOccupied()) {
                for (Coordinates coordinate : placedCoordinates) {
                    this.gameBoard[coordinate.row()][coordinate.col()].setShip(null);
                }
                throw new IllegalArgumentException("Error: One of the selected spaces is already occupied!");
            }

            // Identify ships that are too close
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] direction : directions) {
                Coordinates adjacentCoordinate = new Coordinates(row + direction[0], col + direction[1]);

                if (adjacentCoordinate.isOutOfBounds(GameBoard.BOARD_ROWS, GameBoard.BOARD_COLS)) {
                    continue;
                }

                BoardSpace adjacentSpace = this.gameBoard[adjacentCoordinate.row()][adjacentCoordinate.col()];

                if (adjacentSpace.isOccupied() && adjacentSpace.getShip().getShipType() != shipType) {
                    for (Coordinates coordinate : placedCoordinates) {
                        this.gameBoard[coordinate.row()][coordinate.col()].setShip(null);
                    }
                    throw new IllegalArgumentException("Error: Ships are too close together!");
                }
            }

            this.gameBoard[row][col].setShip(activeShip);
            placedCoordinates.add(new Coordinates(row, col));
        }
    }

    public boolean attackSpace(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Error: Invalid coordinates!");
        }
        return this.gameBoard[coordinates.row()][coordinates.col()].attackSpace();
    }

    public void hideAllSpaces() {
        for (int row = 0; row <= GameBoard.BOARD_ROWS; row++) {
            for (int col = 0; col <= GameBoard.BOARD_COLS; col++) {
                this.gameBoard[row][col].hideSpace();
            }
        }
    }

    public boolean isGameOver() {
        return this.fleet.isDestroyed();
    }

    public String showBoard(boolean showSelf) {
        StringBuilder gameBoardStringBuilder = new StringBuilder();

        for (BoardSpace[] row : this.gameBoard) {
            for (BoardSpace space : row) {
                String outSpace = showSelf ? space.showSelf() : space.showOpponent();
                
                gameBoardStringBuilder.append(outSpace).append(" ");
            }
            gameBoardStringBuilder.append(System.lineSeparator());
        }

        return gameBoardStringBuilder.toString();
    }
}
