package battleship.ship;

public enum ShipType {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    private final int size;
    private final String readableName;

    ShipType(int size, String readableName) {
        this.size = size;
        this.readableName = readableName;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return this.readableName;
    }
}
