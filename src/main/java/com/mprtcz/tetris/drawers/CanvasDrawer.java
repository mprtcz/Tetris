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

    public void drawListOfIndexes(Map<Integer, Color> list){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(0, 0, (numberOfColumns*basicSquareSize), (basicSquareSize*numberOfBasicSquares/numberOfColumns));
        for(Map.Entry<Integer, Color> entry : list.entrySet()){
            graphicsContext.setFill(entry.getValue());
            graphicsContext.fillRoundRect(getXCoordinate(entry.getKey()), getYCoordinate(entry.getKey()), basicSquareSize, basicSquareSize, 10, 10);
        }
    }

    public void drawEndScreen(String points){
        logger.log(level, "End screen");

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

    int getXCoordinate(int index){
        int xCoordinate = (index % numberOfColumns) * basicSquareSize;
        if(index > numberOfBasicSquares -1){
            return -1;
        } else {
            return xCoordinate;
        }
    }

    int getYCoordinate(int index){
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
