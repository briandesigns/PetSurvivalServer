package Stats;

/**
 * Created by brian on 4/12/16.
 */
public enum PlayerStats {
    baseHealth(100),
    baseHealthPoint(100),
    inventoryCapacity(5),
    baseHitPoint(15),
    baseSpeed(2),
    baseSpeedDuration(0.03);

    private double value;

    PlayerStats(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
