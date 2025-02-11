package battleship.board;

import battleship.ship.Ship;

public class BoardSpace {
    public static final String FOG_OF_WAR = "~";
    public static final String OCCUPIED_SPACE = "O";
    public static final String HIT_SPACE = "X";
    public static final String MISS_SPACE = "M";

    private boolean isPlayable;
    private String spaceStatus;
    private String displayStatus;
    private Ship ship;

    public BoardSpace() {
        this.isPlayable = true;
        this.spaceStatus = BoardSpace.FOG_OF_WAR;
        this.revealSpace();
        this.ship = null;
    }

    public void setAsLabel(String label) {
        this.isPlayable = false;
        this.spaceStatus = label;
        this.revealSpace();
    }

    public void hideSpace() {
        if (this.isPlayable) {
            this.displayStatus = BoardSpace.FOG_OF_WAR;
        }
    }

    public void revealSpace() {
        this.displayStatus = this.spaceStatus;
    }

    public void setShip(Ship ship) {
        if (ship != null) {
            this.spaceStatus = BoardSpace.OCCUPIED_SPACE;
            this.revealSpace();
        } else {
            this.spaceStatus = BoardSpace.FOG_OF_WAR;
            this.revealSpace();
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
            if (!this.spaceStatus.equals(BoardSpace.HIT_SPACE)) {
                this.spaceStatus = BoardSpace.HIT_SPACE;
                this.ship.takeDamage();
            }
            this.revealSpace();
            return true;
        } else {
            this.spaceStatus = BoardSpace.MISS_SPACE;
            this.revealSpace();
            return false;
        }
    }

    public String showSelf() {
        return this.spaceStatus;
    }

    public String showOpponent() {
        return this.displayStatus;
    }
}
