package Stats;

/**
 * Created by brian on 4/12/16.
 */
public enum Direction {
    up("up"), down("down"), left("left"), right("right");

    private String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return this.direction;
    }
}
