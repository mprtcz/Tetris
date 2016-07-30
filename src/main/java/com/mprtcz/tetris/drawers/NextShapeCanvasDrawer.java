package com.mprtcz.tetris.drawers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Created by Azet on 2016-05-26.
 */
public class NextShapeCanvasDrawer extends CanvasDrawer {
    private Canvas canvas;

    public NextShapeCanvasDrawer(Canvas canvas) {
        super(canvas, 6);
        this.canvas = canvas;
    }

    @Override
    public void drawListOfIndexes(Map<Integer, Color> map){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        int width = super.getBasicSquareSize() * super.getNumberOfColumns();
        int height = super.getBasicSquareSize() * (super.getNumberOfBasicSquares()/super.getNumberOfColumns());
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(0, 0, width,
                height);
        for(Map.Entry<Integer, Color> entry : map.entrySet()){
            graphicsContext.setFill(entry.getValue());
            graphicsContext.fillRoundRect(super.getXCoordinate(entry.getKey()), super.getYCoordinate(entry.getKey()), super.getBasicSquareSize(), super.getBasicSquareSize(), 10, 10);
        }
    }
}
