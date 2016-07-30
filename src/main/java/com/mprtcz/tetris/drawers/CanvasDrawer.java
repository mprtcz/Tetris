package com.mprtcz.tetris.drawers;

import com.mprtcz.tetris.logger.TetrisGameLogger;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashSet;
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

    public void drawListOfIndexes(HashSet<Integer> list){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(0, 0, (numberOfColumns*basicSquareSize), (basicSquareSize*numberOfBasicSquares/numberOfColumns));
        for(Integer i : list){
            graphicsContext.fillRoundRect(getXCoordinate(i), getYCoordinate(i), basicSquareSize, basicSquareSize, 10, 10);
        }
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
