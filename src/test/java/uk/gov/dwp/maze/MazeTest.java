package uk.gov.dwp.maze;

import org.junit.Test;
import uk.gov.dwp.maze.InvalidMazeException;
import uk.gov.dwp.maze.Maze;
import uk.gov.dwp.maze.Tile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.fail;
import static uk.gov.dwp.maze.Tile.*;

public class MazeTest {

    static InputStream toStream(String s) {
        return new ByteArrayInputStream(s.getBytes(Charset.forName("utf-8")));
    }

    private Maze loadSimplestMaze() {
        String s = "XSF ";
        return Maze.loadMaze(toStream(s));
    }

    @Test
    public void testEmptyMazeShouldFail() {
        try {
            Maze.loadMaze(toStream(""));
            fail();
        } catch (InvalidMazeException e) {
            assertThat(e.getMessage(), is("Maze must contain at least one row"));
        }
    }

    @Test
    public void testMazeWithInvalidCharactersShouldFail() {
        try {
            Maze.loadMaze(toStream("XXXXYYYYXXXX"));
            fail();
        } catch (InvalidMazeException e) {
            assertThat(e.getMessage(), is("Invalid character 'Y' in maze on line 1"));
        }
    }

    @Test
    public void testUnevenMazeShouldFail() {
        try {
            Maze.loadMaze(toStream("XXXXX\nSF "));
            fail();
        } catch (InvalidMazeException e) {
            assertThat(e.getMessage(), is("Maze line 2 is not the correct length 5"));
        }
    }

    @Test
    public void mazeWithNoStartShouldFail() {
        try {
            Maze.loadMaze(toStream("XXXXX\nF    "));
            fail();
        } catch (InvalidMazeException e) {
            assertThat(e.getMessage(), is("There should be only one start point, found 0"));
        }
    }

    @Test
    public void mazeWithNoFinishShouldFail() {
        try {
            Maze.loadMaze(toStream("XXXXX\nS    "));
            fail();
        } catch (InvalidMazeException e) {
            assertThat(e.getMessage(), is("There should be only one finish point, found 0"));
        }
    }

    @Test
    public void mazeWithMoreThanOneStartShouldFail() {
        try {
            Maze.loadMaze(toStream("XXXXX\nSSF  "));
            fail();
        } catch (InvalidMazeException e) {
            assertThat(e.getMessage(), is("There should be only one start point, found 2"));
        }
    }

    @Test
    public void mazeWithMoreThanOneFinishShouldFail() {
        try {
            Maze.loadMaze(toStream("XXXXX\nFFS  "));
            fail();
        } catch (InvalidMazeException e) {
            assertThat(e.getMessage(), is("There should be only one finish point, found 2"));
        }
    }


    @Test
    public void testTileCounts() throws Exception {
        Map<Tile, Integer> counts = loadSimplestMaze().getCountsForTile();

        assertThat(counts, hasEntry(is(Empty), is(1)));
        assertThat(counts, hasEntry(is(Wall), is(1)));
        assertThat(counts, hasEntry(is(Start), is(1)));
        assertThat(counts, hasEntry(is(Finish), is(1)));
    }


    @Test
    public void getTileReturnsWall() throws Exception{
        Maze m = loadSimplestMaze();
        assertThat(m.getTileAt(new Point(0,0)), equalTo(Wall));
    }

    @Test
    public void getTileReturnsEmpty() throws Exception{
        Maze m = loadSimplestMaze();
        assertThat(m.getTileAt(new Point(3,0)), equalTo(Empty));
    }

    @Test
    public void getTileReturnsStart() throws Exception{
        Maze m = loadSimplestMaze();
        assertThat(m.getTileAt(new Point(1,0)), equalTo(Start));
    }

    @Test
    public void getTileReturnsFinish() throws Exception{
        Maze m = loadSimplestMaze();
        assertThat(m.getTileAt(new Point(2,0)), equalTo(Finish));
    }

    @Test
    public void testGetStartPoint() {
        Maze m = loadSimplestMaze();
        assertThat(m.getStartPoint(), equalTo(new Point(1,0)));
    }

}
