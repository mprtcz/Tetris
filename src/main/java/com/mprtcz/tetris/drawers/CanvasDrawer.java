package com.mprtcz.tetris.drawers;

import com.mprtcz.tetris.logger.TetrisGameLogger;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-08.
 */
public class CanvasDrawer {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;
    private static final int ARC_WIDTH = 10;
    private static final int ARC_HEIGHT = 10;

    private Canvas canvas;
    private int numberOfColumns = 20;
    private int numberOfBasicSquares;
    private int basicSquareSize;


    public CanvasDrawer(Canvas canvas) {
        this.canvas = canvas;
        this.basicSquareSize = (int) canvas.getWidth()/numberOfColumns;
        determineNumberOfBasicSquares();
    }

    CanvasDrawer(Canvas canvas, int numberOfColumns){
        this.canvas = canvas;
        this.numberOfColumns = numberOfColumns;
        this.basicSquareSize = (int) canvas.getWidth()/numberOfColumns;
        determineNumberOfBasicSquares();
    }

    private void determineNumberOfBasicSquares(){
        this.numberOfBasicSquares = numberOfColumns * (int) (canvas.getHeight()/basicSquareSize);
    }

    public void drawIndexesOnGraphicContext(Map<Integer, Color> indexesToDraw){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        prepareGraphicsContext(graphicsContext);
        for(Map.Entry<Integer, Color> entry : indexesToDraw.entrySet()){
            drawRoundColoredRectangle(graphicsContext, entry);
        }
    }

    void drawRoundColoredRectangle(GraphicsContext graphicsContext, Map.Entry<Integer, Color> entry) {
        graphicsContext.setFill(entry.getValue());
        graphicsContext.fillRoundRect(getXCoordinate(entry.getKey()), getYCoordinate(entry.getKey()), basicSquareSize, basicSquareSize, ARC_WIDTH, ARC_HEIGHT);

    }

    private void prepareGraphicsContext(GraphicsContext graphicsContext) {
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(0, 0, (numberOfColumns*basicSquareSize), (basicSquareSize*numberOfBasicSquares/numberOfColumns));
    }

    public void drawEndScreen(String points){
        logger.log(level, "End screen");

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        initializeEndScreen(graphicsContext);
        displayEndMessage(graphicsContext, points);

    }

    private void initializeEndScreen(GraphicsContext graphicsContext) {
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.setFill(Color.BLACK);
    }

    private void displayEndMessage(GraphicsContext graphicsContext, String points) {
        final double canvasMiddleWidth = Math.round(canvas.getWidth() / 2);
        final double canvasMiddleHeight = Math.round(canvas.getHeight() / 2);
        String endMessageString = "Game over! Your score: " + points +
                "\n Hit START to try again";
        graphicsContext.fillText(endMessageString, canvasMiddleWidth, canvasMiddleHeight);
    }

    private int getXCoordinate(int index){
        int xCoordinate = (index % numberOfColumns) * basicSquareSize;
        if(index > numberOfBasicSquares -1){
            return -1;
        } else {
            return xCoordinate;
        }
    }

    private int getYCoordinate(int index){
        int yCoordinate = (index / numberOfColumns) * basicSquareSize;
        if(index > numberOfBasicSquares -1){
            return -1;
        } else {
            return yCoordinate;
        }
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfBasicSquares() {
        return numberOfBasicSquares;
    }

    int getBasicSquareSize() {
        return basicSquareSize;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }
}
