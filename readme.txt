Programming test for a job application some years ago 
=====================================================

Maze Test Solution
==================

Assumptions made

* You are not looking for an api, a GUI or anything to run this in, it is sufficient to drive the code with tests.
* It is not possible to move outside of the maze, the implementation will treat any co-ordinates outside the maze as walls.
* Mazes are rectangular. Odd shapes are treated as invalid and will not load.
* 'an understandable fashion' for the explorers record means a human readable log

Classes:

* Tile - An enum to represent the state of a tile in the maze.
* Maze - The implementation of the maze, I used a map because I'm only interested in storing the state of tiles that are significant.
Walls, the most common type are not stored. This is fairly self contained, so I started out testing it using junit, and that seemed
sufficient so I left it at that.
* Point - An immutable x,y coordinant. I did start off with java.awt.Point, but that is mutable which makes it risky to make part
of the public API, and it also felt a bit wierd using java.awt when not building a GUI.
* Explorer - The implementation of the explorer, which takes a maze to explore. It seems more natural to test this with the Maze
instead of introducing mocks, so that is what I have done. I went with a Cucumber test here as the Gherkin format makes it quite
readable to express the test maze and the outcomes inline.
* InvalidMazeException - Thrown when something about the maze is invalid.
