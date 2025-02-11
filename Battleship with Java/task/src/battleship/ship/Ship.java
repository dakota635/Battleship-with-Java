package battleship.ship;

public abstract class Ship {
    protected ShipType shipType;
    protected int shipHealth;
    protected boolean destroyed;

    public Ship(ShipType shipType) {
        this.shipType = shipType;
        this.shipHealth = this.shipType.getSize();
        this.destroyed = false;
    }

    public ShipType getShipType() {
        return this.shipType;
    }

    public int getSize() {
        return this.shipType.getSize();
    }

    public void takeDamage() {
        this.shipHealth -= 1;

        if (this.shipHealth == 0) {
            this.destroyed = true;
            System.out.println("You sank a ship!");
        }
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }
}
