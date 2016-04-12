package Stats;

/**
 * Created by brian on 4/12/16.
 */
public enum EnemyStats {
    baseHealth(100),
    baseHealthPoint(100),
    inventoryCapacity(1),
    baseHitPoint(2),
    baseSpeed(8),
    baseSpeedDuration(0.05);

    private double value;

    EnemyStats(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
