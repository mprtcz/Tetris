package com.mprtcz.tetris;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.drawers.CanvasDrawer;
import com.mprtcz.tetris.drawers.NextShapeCanvasDrawer;
import com.mprtcz.tetris.listoperators.ListOperator;
import com.mprtcz.tetris.logger.TetrisGameLogger;
import com.mprtcz.tetris.music.Player;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-20.
 */
public class GameAgent {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private CanvasDrawer canvasDrawer;
    private ListOperator listOperator;
    private Shape shape;
    private int sleepingTime;
    private Canvas canvas;
    private final int SLEEPING_TIME = 300;
    private Canvas nextBrickCanvas;
    private TextField pointsTextField;
    private int points;
    private NextShapeCanvasDrawer nextShapeCanvasDrawer;
    private Player player;
    private boolean playMusic = false;
    private Shape nextShapeToDraw;
    private ListOperator nextShapeListOperator;
    private Shape.ShapeType nextShapeType;

    private boolean gameRunning = false;

    GameAgent(Canvas gameCanvas, Canvas nextBrickCanvas, TextField pointsTextField) {
        logger.log(level, "gameCanvas = [" + gameCanvas + "], nextBrickCanvas = [" + nextBrickCanvas + "]," +
                " pointsTextField = [" + pointsTextField + "]");
        this.canvas = gameCanvas;
        this.nextBrickCanvas = nextBrickCanvas;
        this.pointsTextField = pointsTextField;
        this.points = 0;
        this.player = new Player();
    }

    void startGame() {
        if (playMusic) {
            player.playMusic();
        }

        canvasDrawer = new CanvasDrawer(canvas);
        listOperator = new ListOperator(canvasDrawer.getNumberOfBasicSquares(), canvasDrawer.getNumberOfColumns());

        initializeNextShape();

        canvasDrawer.drawListOfIndexes(listOperator.getSavedIndexes());

        gameRunning = true;

        while (gameRunning) {

            shape = Shape.getInstance(nextShapeType, canvasDrawer.getNumberOfColumns(),
                    canvasDrawer.getNumberOfBasicSquares(), listOperator.getSavedIndexes());

            if (nextShapeToDraw != null) {
                shape.setColor(nextShapeToDraw.getColor());
            }

            pickAdnDrawNextShape();

            gameRunning = listOperator.canShapeBeAddedToGame(shape);


            sleepingTime = SLEEPING_TIME;
            while (shape.pullShapeIndexesDown() && gameRunning) {

                drawOnCanvasDrawer();

                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            listOperator.addIndexesToList(shape);
            shape = null;
            points = listOperator.removeFullRowsFromSavedIndexes(points);

            Platform.runLater(() -> pointsTextField.setText(String.valueOf(points)));

            drawOnCanvasDrawer();

            if (!gameRunning) {
                player.stopMusic();
                displayEndScreen();
            }
        }
    }

    private void drawOnCanvasDrawer() {
        Platform.runLater(() -> canvasDrawer.drawListOfIndexes(listOperator.getIndexesToDraw(shape)));
    }

    private void initializeNextShape() {
        nextShapeType = Shape.ShapeType.randomShapeType();
        nextShapeCanvasDrawer = new NextShapeCanvasDrawer(nextBrickCanvas);
        nextShapeListOperator = new ListOperator(17, 6);
    }

    private void pickAdnDrawNextShape() {
        nextShapeType = Shape.ShapeType.randomShapeType();
        nextShapeToDraw = Shape.getInstance(nextShapeType, 6, 17, new HashMap<>());

        Platform.runLater(() -> nextShapeCanvasDrawer.drawListOfIndexes(nextShapeListOperator.drawShape(nextShapeToDraw)));
    }

    void handleKeyReleasedEvents(KeyEvent event) {
        logger.log(level, "event = [" + event + "]");
        if (shape != null) {
            if (event.getCode() == KeyCode.UP) {
                shape.rotateShape();
            } else if (event.getCode() == KeyCode.LEFT) {
                shape.moveLeft();
            } else if (event.getCode() == KeyCode.RIGHT) {
                shape.moveRight();
            } else if (event.getCode() == KeyCode.DOWN) {
                sleepingTime = SLEEPING_TIME;
            }
            canvasDrawer.drawListOfIndexes(listOperator.getIndexesToDraw(shape));
        }
    }

    void handleKeyPressedEvents(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
            sleepingTime = SLEEPING_TIME / 10;
        }
    }

    void terminateGame() {
        logger.log(level, "Game terminated");
        gameRunning = false;
        player.stopMusic();
        playMusic = false;
        Platform.runLater(() -> pointsTextField.setText("0"));
    }

    void setMusic(boolean value) {
        logger.log(level, "value = [" + value + "]");
        if (!value) {
            player.stopMusic();
        } else {
            player.playMusic();
        }
    }

    private void displayEndScreen() {
        System.out.println("end screen");
        final double canvasMiddleWidth = Math.round(canvas.getWidth() / 2);
        final double canvasMiddleHeight = Math.round(canvas.getHeight() / 2);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);

        String endMessageString = "Game over! Your score: " +
                points +
                "\n Hit START to try again";

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(endMessageString, canvasMiddleWidth, canvasMiddleHeight);
    }

}
