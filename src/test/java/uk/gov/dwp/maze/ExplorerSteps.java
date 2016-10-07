package uk.gov.dwp.maze;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static uk.gov.dwp.maze.Explorer.MoveResult.Ouch;

public class ExplorerSteps {

    private Maze maze;
    private Explorer explorer;

    @Given("^this maze$")
    public void thisMaze(List<String> maze) throws Throwable {
        this.maze = Maze.loadMaze(MazeTest.toStream(StringUtils.join(maze, "\n")));

    }

    @When("^the explorer starts exploring the maze$")
    public void theExplorerStartsExploringTheMaze() throws Throwable {
        explorer = new Explorer(maze);
    }

    @Then("^the explorers position is (\\d+),(\\d+)$")
    public void theExplorersPositionIs(int x, int y) throws Throwable {
        Assert.assertThat(explorer.getPosition(), is(new Point(x,y)));
    }

    @And("^the explorer turns right$")
    public void theExplorerTurnsRight() throws Throwable {
        explorer.turnRight();
    }

    @And("^the explorer moves forward (\\d+) times$")
    public void theExplorerMovesForward(int times) throws Throwable {
        for (int i = 0; i< times; i++) {
            Assert.assertThat(explorer.moveForward(), is(not(Ouch)));
        }
    }

    @Then("^the explorer should declare these directions$")
    public void theExplorerShouldBeAbleToMoveInTheseDirections(List<Explorer.Direction> directions) throws Throwable {
        Assert.assertThat(explorer.movementOptions(), is(directions));
    }

    @Then("^the explorer should declare they have an? (\\w+) in front of them$")
    public void theExplorerShouldDeclareTheHaveAWallInFrontOfThem(Tile t) throws Throwable {
        Assert.assertThat(explorer.getWhatIsInFront(), is(t));
    }

    @When("^the explorer turns left$")
    public void theExplorerTurnsLeft() throws Throwable {
        explorer.turnLeft();
    }

    @Then("^the explorer can't move forward$")
    public void theExplorerCanTMoveForward() throws Throwable {
        Assert.assertThat(explorer.moveForward(), is(Ouch));
    }

    @Then("^the explorers record should contain$")
    public void theExplorersRecordShouldContain(List<String> logEntries) throws Throwable {
        List<String> actualRecord = Arrays.asList(explorer.getExplorersRecord().split("\n"));
        Assert.assertThat(actualRecord, is(logEntries));
    }
}
