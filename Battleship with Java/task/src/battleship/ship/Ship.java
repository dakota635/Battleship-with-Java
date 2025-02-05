package battleship.ship;

public abstract class Ship {
    protected ShipType shipType;
    protected boolean[] damageReport;
    protected boolean destroyed;

    public Ship(ShipType shipType) {
        this.shipType = shipType;
        this.damageReport = new boolean[this.shipType.getSize()];
        this.destroyed = false;
    }

    public ShipType getShipType() {
        return this.shipType;
    }

    public int getSize() {
        return this.shipType.getSize();
    }

    public boolean[] getDamageReport() {
        return this.damageReport;
    }

    public boolean isDestroyed() {
        this.destroyed = true;

        for (boolean segmentStatus : this.damageReport) {
            if (!segmentStatus) {
                this.destroyed = false;
                break;
            }
        }
        return this.destroyed;
    }
}
