package uk.gov.dwp.maze;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.gov.dwp.maze.Tile.*;

public class Maze {

    private Maze(Map<Point, Tile> maze, int width, int height) {
        this.maze = maze;
        this.width = width;
        this.height = height;
        validateMaze();
    }

    private Map<Point, Tile> maze;
    private int width;
    private int height;

    public static Maze loadMaze(InputStream filename) throws InvalidMazeException {
        Map<Point, Tile> maze = new HashMap<>();
        try {
            List<String> mazeLines = IOUtils.readLines(filename, Charset.forName("utf-8"));
            if (mazeLines.size() < 1) throw new InvalidMazeException("Maze must contain at least one row");

            int height = mazeLines.size();
            int width = mazeLines.get(0).length();

            for (int y = 0; y < mazeLines.size(); y++) {
                String line = mazeLines.get(y);
                if (line.length() != width)
                    throw new InvalidMazeException("Maze line " + (y + 1) + " is not the correct length " + width);
                for (int x = 0; x < line.length(); x++) {
                    switch (line.charAt(x)) {
                        case 'X':
                            // Wall is the most commmon tile type so don't store it for efficiency
                            break;
                        case ' ':
                            maze.put(new Point(x, y), Empty);
                            break;
                        case 'S':
                            maze.put(new Point(x, y), Start);
                            break;
                        case 'F':
                            maze.put(new Point(x, y), Finish);
                            break;
                        default:
                            throw new InvalidMazeException("Invalid character '"+ line.charAt(x) + "' in maze on line " + (y+1));
                    }
                }

            }
            return new Maze(maze, width, height);
        } catch (IOException e) {
            throw new InvalidMazeException("Error loading maze", e);
        }
    }

    public Point getStartPoint() {
        return maze.entrySet().stream()
                .filter(entry -> entry.getValue() == Start)
                .findFirst().get().getKey();
    }

    public Map<Tile, Integer> getCountsForTile() {
        Map<Tile, Integer> counts = new HashMap<>();
        counts.put(Empty, 0);
        counts.put(Start, 0);
        counts.put(Finish, 0);

        for (Tile t : maze.values()) {
            counts.put(t, counts.get(t) + 1);
        }
        counts.put(Wall, width * height - maze.size());
        return counts;
    }

    public Tile getTileAt(Point p) {
        Tile t = maze.get(p);
        return t == null ? Wall : t;
    }

    private void validateMaze() throws InvalidMazeException {
        Map<Tile, Integer> counts = getCountsForTile();
        if (counts.get(Start) != 1)
            throw new InvalidMazeException("There should be only one start point, found " + counts.get(Start));
        if (counts.get(Finish) != 1)
            throw new InvalidMazeException("There should be only one finish point, found " + counts.get(Finish));
    }

}
