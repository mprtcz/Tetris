# Tetris

A Java application implementing popular [Tetris](https://en.wikipedia.org/wiki/Tetris) game.
The Game starts after pressing `START` Button at the bottom of the screen. Next to it, there is a checkbox which allows the application to play music during gameplay.

The music's source: [Tetris Meets Metal - Youtube](https://www.youtube.com/watch?v=lGkyL_MF-lw)

Application's UI during gameplay:

![sampleOutput](https://sc-cdn.scaleengine.net/i/ea02b73c247710e9e221644415309730.png)


**Controls**: 
* :arrow_left: hit or hold for the brick to move left
* :arrow_right: hit or hold for the brick to move right
* :arrow_down: holding speeds up the brick's down movement
* :arrow_up: or `spacebar` rotates the brick counterclockwise.


Section `Next Brick` displays what kind of shape is going to spawn next:

![sampleOutput](https://sc-cdn.scaleengine.net/i/1a3b6a22d031ed250b168a3c769be00a.png)

In the right side of the window the application displays actual points a player has scored.

The application's sample game:

![tetris-gameplay](https://media.giphy.com/media/3o7bubkTFp3z80mbOE/giphy.gif)

---

To generate a jar file of this project using maven:
  1. after cloning the project, open terminal in the project's main directory and type:

  `mvn package`
  
  2. Next go to generated `target` directory:
  
  `cd target`
  
  3. To run generated jar file enter:
  
  `java -jar Tetris-jar-with-dependencies.jar`
