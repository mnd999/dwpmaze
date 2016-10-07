Feature: As a world famous explorer of Mazes I would like to exist in a maze and be able to navigate it.

  Scenario: Given a maze the explorer should be able to drop in to the Start point facing north
    Given this maze
      |XXXXXXXXXXXXXXX|
      |X             X|
      |X XXXXXXXXXXX X|
      |X XS        X X|
      |X XXXXXXXXX X X|
      |X XXXXXXXXX X X|
      |X XXXX      X X|
      |X XXXX XXXX X X|
      |X XXXX XXXX X X|
      |X X    XXXXXX X|
      |X X XXXXXXXXX X|
      |X X XXXXXXXXX X|
      |X X         X X|
      |X XXXXXXXXX   X|
      |XFXXXXXXXXXXXXX|
    When the explorer starts exploring the maze
    Then the explorers position is 3,3

  Scenario: Given a maze the explorer should be able to move forward
    Given this maze
      |XXXXXXXXXXXXXXX|
      |X             X|
      |X XXXXXXXXXXX X|
      |X XS        X X|
      |X XXXXXXXXX X X|
      |X XXXXXXXXX X X|
      |X XXXX      X X|
      |X XXXX XXXX X X|
      |X XXXX XXXX X X|
      |X X    XXXXXX X|
      |X X XXXXXXXXX X|
      |X X XXXXXXXXX X|
      |X X         X X|
      |X XXXXXXXXX   X|
      |XFXXXXXXXXXXXXX|
    When the explorer starts exploring the maze
    And the explorer turns right
    And the explorer moves forward 1 times
    Then the explorers position is 4,3

  Scenario: An explorer on a maze can't walk through a wall
    Given this maze
      |XXX|
      |XSX|
      |XFX|
    When the explorer starts exploring the maze
    Then the explorer can't move forward
    And the explorers position is 1,1

  Scenario: An explorer on a maze must be able to declare what is in front of them
    Given this maze
      |XFXX|
      |XS X|
      |XXXX|
    When the explorer starts exploring the maze
    Then the explorer should declare they have a Finish in front of them
    When the explorer turns left
    Then the explorer should declare they have a Wall in front of them
    When the explorer turns right
    And the explorer turns right
    Then the explorer should declare they have an Empty in front of them
    When the explorer moves forward 1 times
    And the explorer turns right
    And the explorer turns right
    Then the explorer should declare they have a Start in front of them
    

    Scenario: An explorer on a maze must be able to declare all movement options from their given location
      Given this maze
        |XXFXX|
        |XX XX|
        |X S X|
        |XX XX|
        |XXXXX|
      When the explorer starts exploring the maze
      Then the explorer should declare these directions
        |North|
        |South|
        |East |
        |West |

  Scenario: An explorer on a maze must be able to declare all movement options from their given location
    Given this maze
      |XXFXX|
      |XXXXX|
      |XXS X|
      |XX XX|
      |XXXXX|
    When the explorer starts exploring the maze
    Then the explorer should declare these directions
      |South|
      |East |


    Scenario: An explorer on a maze must be able to report a record of where they have been in an understandable fashion
      Given this maze
        |XXFXX|
        |XX XX|
        |X S X|
        |XX XX|
        |XXXXX|
      When the explorer starts exploring the maze
      And the explorer turns left
      And the explorer moves forward 1 times
      And the explorer turns right
      And the explorer turns right
      And the explorer moves forward 1 times
      And the explorer turns left
      And the explorer moves forward 2 times
      Then the explorers record should contain
        |I entered the maze at the start point.|
        |Then I moved west.                    |
        |Then I moved east.                    |
        |Then I moved north.                   |
        |And then I moved north to the finish. |