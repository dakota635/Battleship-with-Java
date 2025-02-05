package battleship.ship;

import java.util.Map;

public class Fleet {
    public static final Map<ShipType, Integer> SHIP_INDEX = Map.ofEntries(
            Map.entry(ShipType.AIRCRAFT_CARRIER, 0),
            Map.entry(ShipType.BATTLESHIP, 1),
            Map.entry(ShipType.SUBMARINE, 2),
            Map.entry(ShipType.CRUISER, 3),
            Map.entry(ShipType.DESTROYER, 4)
    );

    private final Ship[] fleet;
    private boolean destroyed;

    public Fleet() {
        // A fleet consists of one of each class of ship
        this.fleet = new Ship[5];

        this.fleet[Fleet.SHIP_INDEX.get(ShipType.AIRCRAFT_CARRIER)] = new AircraftCarrier();
        this.fleet[Fleet.SHIP_INDEX.get(ShipType.BATTLESHIP)] = new Battleship();
        this.fleet[Fleet.SHIP_INDEX.get(ShipType.SUBMARINE)] = new Submarine();
        this.fleet[Fleet.SHIP_INDEX.get(ShipType.CRUISER)] = new Cruiser();
        this.fleet[Fleet.SHIP_INDEX.get(ShipType.DESTROYER)] = new Destroyer();

        this.destroyed = false;
    }

    public Ship getShip(int shipIndex) {
        return fleet[shipIndex];
    }

    public boolean isDestroyed() {
        this.destroyed = true;

        for (Ship ship : this.fleet) {
            if (!ship.isDestroyed()) {
                this.destroyed = false;
                break;
            }
        }
        return this.destroyed;
    }
}