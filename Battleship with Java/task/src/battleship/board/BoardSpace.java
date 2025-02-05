package battleship.board;

import battleship.ship.Ship;

public class BoardSpace {
    public static final String FOG_OF_WAR = "~";
    public static final String OCCUPIED_SPACE = "O";
    public static final String HIT_SPACE = "X";
    public static final String MISS_SPACE = "M";

    private boolean isPlayable;
    private String spaceStatus;
    private Ship ship;

    public BoardSpace() {
        this.isPlayable = true;
        this.spaceStatus = BoardSpace.FOG_OF_WAR;
        this.ship = null;
    }

    public void setAsLabel(String label) {
        this.isPlayable = false;
        this.spaceStatus = label;
    }

    public boolean isPlayable() {
        return this.isPlayable;
    }

    public void setShip(Ship ship) {
        if (ship != null) {
            this.spaceStatus = BoardSpace.OCCUPIED_SPACE;
        } else {
            this.spaceStatus = BoardSpace.FOG_OF_WAR;
        }
        this.ship = ship;
    }

    public boolean isOccupied() {
        return this.ship != null;
    }

    public Ship getShip() {
        return this.ship;
    }

    public boolean attackSpace() {
        if (this.ship != null) {
            this.spaceStatus = BoardSpace.HIT_SPACE;
            return true;
        } else {
            this.spaceStatus = BoardSpace.MISS_SPACE;
            return false;
        }
    }

    @Override
    public String toString() {
        return this.spaceStatus;
    }
}
