package uk.gov.dwp.maze;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Explorer {

    public Explorer(Maze m) {
        this.maze = m;
        position = m.getStartPoint();
        explorersRecord.add("I entered the maze at the start point.");
    }

    public enum Direction { North, South, East, West }
    public enum MoveResult { Moved, Ouch, Finish }

    private Maze maze;
    private Point position;
    private Direction facing = Direction.North;
    private List<String> explorersRecord = new ArrayList<>();

    public MoveResult moveForward() {
        Tile t = getWhatIsInFront();
        switch (t) {
            case Empty:
            case Start:
                position = getPointInDirection(facing);
                explorersRecord.add("Then I moved " + facing.toString().toLowerCase()+".");
                return MoveResult.Moved;
            case Wall:
                return MoveResult.Ouch;
            case Finish:
                position = getPointInDirection(facing);
                explorersRecord.add("And then I moved " + facing.toString().toLowerCase() + " to the finish.");
                return MoveResult.Finish;
            default:
                throw new IllegalStateException("There is no tile in front of the explorer.");
        }
    }

    public Tile getWhatIsInFront() {
        Point inFront = getPointInDirection(facing);
        return maze.getTileAt(inFront);
    }

    public void turnLeft() {
        switch (facing) {
            case North:
                facing = Direction.West;
                break;
            case South:
                facing = Direction.East;
                break;
            case East:
                facing = Direction.North;
                break;
            case West:
                facing = Direction.South;
                break;
        }
    }

    public void turnRight() {
        switch (facing) {
            case North:
                facing = Direction.East;
                break;
            case South:
                facing = Direction.West;
                break;
            case East:
                facing = Direction.South;
                break;
            case West:
                facing = Direction.North;
                break;
        }
    }

    public List<Direction> movementOptions() {
        return Arrays.stream(Direction.values())
                .filter(this::canMoveInDirection)
                .collect(Collectors.toList());
    }

    public Point getPosition() {
        return position;
    }

    public String getExplorersRecord() {
        return StringUtils.join(explorersRecord, "\n");
    }

    private boolean canMoveInDirection(Direction d) {
        return maze.getTileAt(getPointInDirection(d)) != Tile.Wall;
    }

    private Point getPointInDirection(Direction d) {
        switch (d) {
            case North:
                return new Point(position.getX(), position.getY() - 1);
            case South:
                return new Point(position.getX(), position.getY() + 1);
            case East:
                return new Point(position.getX() + 1, position.getY() );
            case West:
                return new Point(position.getX() - 1, position.getY() );
            default:
                throw new IllegalArgumentException("Direction cannot be null");
        }

    }

}
