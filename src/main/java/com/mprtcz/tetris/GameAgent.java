package com.mprtcz.tetris;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.drawers.CanvasDrawer;
import com.mprtcz.tetris.drawers.NextShapeCanvasDrawer;
import com.mprtcz.tetris.listoperators.SavedIndexes;
import com.mprtcz.tetris.logger.TetrisGameLogger;
import com.mprtcz.tetris.music.Player;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-20.
 */
class GameAgent {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private CanvasDrawer canvasDrawer;
    private SavedIndexes savedIndexes;
    private Canvas canvas;

    private NextShapeCanvasDrawer nextShapeCanvasDrawer;
    private SavedIndexes nextShapeSavedIndexes;

    private Canvas nextBrickCanvas;
    private Shape shape;

    private Shape nextShapeToDraw;
    private int sleepingTime;

    private final int SLEEPING_TIME = 300;
    private TextField pointsTextField;
    private int score;

    private Player player;
    private boolean playMusic = false;

    private Shape.ShapeType nextShapeType;

    private boolean gameRunning = false;

    GameAgent(Canvas gameCanvas, Canvas nextBrickCanvas, TextField pointsTextField) {
        logger.log(level, "gameCanvas = [" + gameCanvas + "], nextBrickCanvas = [" + nextBrickCanvas + "]," +
                " pointsTextField = [" + pointsTextField + "]");
        this.canvas = gameCanvas;
        this.nextBrickCanvas = nextBrickCanvas;
        this.pointsTextField = pointsTextField;
        this.score = 0;
        this.player = new Player();
    }

    void startGame() {
        if (playMusic) {
            player.playMusic();
        }

        canvasDrawer = new CanvasDrawer(canvas);
        savedIndexes = new SavedIndexes(canvasDrawer.getNumberOfColumns(), canvasDrawer.getNumberOfBasicSquares());
        initializeNextShape();
        do {
            getNewShapeAndApplyColor();
            pickAndDrawNextShape();
            gameRunning = savedIndexes.canShapeBeAddedToGame(shape);
            sleepingTime = SLEEPING_TIME;
            firePullShapeDownLoop();
            calculateAndDisplayScore();
            drawOnCanvasDrawer();
        } while (gameRunning);

        player.stopMusic();
        Platform.runLater(() -> canvasDrawer.drawEndScreen(String.valueOf(score)));
    }

    private void getNewShapeAndApplyColor() {
        shape = Shape.shapeFactory(nextShapeType, canvasDrawer.getNumberOfColumns(),
                canvasDrawer.getNumberOfBasicSquares(), savedIndexes.getSavedIndexes());
        if (nextShapeToDraw != null) {
            shape.setColor(nextShapeToDraw.getColor());
        }
    }

    private void firePullShapeDownLoop() {
        while (shape.pullShapeIndexesDown() && gameRunning) {
            drawOnCanvasDrawer();
            putThreadToSleep();
        }
        addShapeToListAndNullify();
    }

    private void calculateAndDisplayScore() {
        score = savedIndexes.removeFullRowsFromSavedIndexes(score);
        Platform.runLater(() -> pointsTextField.setText(String.valueOf(score)));
    }

    private void addShapeToListAndNullify() {
        savedIndexes.saveFallenShape(shape);
        shape = null;
    }

    private void putThreadToSleep() {
        try {
            Thread.sleep(sleepingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawOnCanvasDrawer() {
        Platform.runLater(() -> canvasDrawer.drawIndexesOnGraphicContext(savedIndexes.getIndexesToDraw(shape)));
    }

    private void initializeNextShape() {
        nextShapeType = Shape.ShapeType.randomShapeType();
        nextShapeCanvasDrawer = new NextShapeCanvasDrawer(nextBrickCanvas);
        nextShapeSavedIndexes = new SavedIndexes(6, 17);
    }

    private void pickAndDrawNextShape() {
        nextShapeType = Shape.ShapeType.randomShapeType();
        nextShapeToDraw = Shape.shapeFactory(nextShapeType, 6, 17, new HashMap<>());

        Platform.runLater(() -> nextShapeCanvasDrawer.drawIndexesOnGraphicContext(nextShapeSavedIndexes.drawShape(nextShapeToDraw)));
    }

    void handleKeyReleasedEvents(KeyEvent event) {
        logger.log(level, "event = [" + event + "]");
        isMoveLoopRunning = false;
        if (shape != null) {
            if (event.getCode() == KeyCode.DOWN) {
                sleepingTime = SLEEPING_TIME;
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.SPACE) {
                shape.rotateShape();
                event.consume();
            }
            drawOnCanvasDrawer();
        }
    }

    void handleKeyPressedEvents(KeyEvent event) {
        if (shape != null) {
            moveLoop(event);
        }
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

    void playMusic(boolean value) {
        logger.log(level, "value = [" + value + "]");
        if (!value) {
            player.stopMusic();
        } else {
            player.playMusic();
        }
    }

    private boolean isMoveLoopRunning = false;

    private void moveLoop(KeyEvent event) {
        boolean runLoop = true;
        while (runLoop) {
            if (event.getCode() == KeyCode.LEFT) {
                shape.moveLeft();
            } else if (event.getCode() == KeyCode.RIGHT) {
                shape.moveRight();
            }
            drawOnCanvasDrawer();
            runLoop = this.isMoveLoopRunning;
        }
    }
}
