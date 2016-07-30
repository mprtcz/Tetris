# Tetris

A Java application implementing popular [Tetris](https://en.wikipedia.org/wiki/Tetris) game.
Optionally, at the top of the window there is a checkbox which allows the application to play music during gameplay.

Use left and right arrow keys to navigate falling brick sideways,
Use spacebar or up key to rotate the brick counterclockwise.
Use dow key to speed up the brick's downward movement.

The application's sample output:

![sampleOutput](https://sc-cdn.scaleengine.net/i/05ab5439de7daf6c28643c7b7d698e805.png)


To generate a jar file of this project using maven:
  1. after cloning the project, open terminal in the project's main directory and type:

  `mvn package`
  
  2. Next go to generated `target` directory:
  
  `cd target`
  
  3. To run generated jar file enter:
  
  `java -jar Tetris-jar-with-dependencies.jar`