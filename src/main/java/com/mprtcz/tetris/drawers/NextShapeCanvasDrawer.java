package com.mprtcz.tetris.drawers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public class NextShapeCanvasDrawer extends CanvasDrawer {
    private Canvas canvas;

    public NextShapeCanvasDrawer(Canvas canvas) {
        super(canvas, 6);
        this.canvas = canvas;
    }

    @Override
    public void drawIndexesOnGraphicContext(Map<Integer, Color> coloredIndexesToDraw) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initializeGraphicsContext(graphicsContext);
        coloredIndexesToDraw.entrySet().forEach(
                integerColorEntry -> drawRoundColoredRectangle(graphicsContext, integerColorEntry));
    }

    private void initializeGraphicsContext(GraphicsContext graphicsContext) {
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        drawFrame(graphicsContext);
    }

    private void drawFrame(GraphicsContext graphicsContext) {
        int frameWidth = super.getBasicSquareSize() * super.getNumberOfColumns();
        int frameHeight = super.getBasicSquareSize() * (super.getNumberOfBasicSquares() / super.getNumberOfColumns());
        graphicsContext.setStroke(Color.RED);
        graphicsContext.strokeRect(0, 0, frameWidth, frameHeight);
    }
}
