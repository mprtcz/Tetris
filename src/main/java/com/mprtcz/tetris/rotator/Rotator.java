package com.mprtcz.tetris.rotator;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.logger.TetrisGameLogger;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-30.
 */
public class Rotator {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private int numberOfColumns;
    private Shape shape;

    public Rotator(Shape shape) {
        logger.log(level, "");
        this.numberOfColumns = shape.getNumberOfColumns();
        this.shape = shape;
    }

    private class Point {
        private int x;
        private int y;

        Point(int index) {
            x = index % numberOfColumns;
            y = index / numberOfColumns;
        }
    }

    private int rotateIndexAroundPivotPoint(int index, int degree, int pivotPointIndex) {
        logger.log(level, "index = [" + index + "], degree = [" + degree + "], pivotPointIndex = [" + pivotPointIndex + "]");
        boolean isZeroIndex = false;
        boolean isLastIndex = false;

        if (pivotPointIndex % numberOfColumns == 0) {
            isZeroIndex = true;
            index = index + 1;
            pivotPointIndex = pivotPointIndex + 1;
        } else if (pivotPointIndex % numberOfColumns == numberOfColumns - 1) {
            isLastIndex = true;
            index = index - 1;
            pivotPointIndex = pivotPointIndex - 1;
        }

        double radianDegree = Math.toRadians(degree);

        Point pivotPoint = new Point(pivotPointIndex);
        Point rotatedPoint = new Point(index);

        double xCoordinate = (rotatedPoint.x - pivotPoint.x) * Math.cos(radianDegree) + (rotatedPoint.y - pivotPoint.y)
                * Math.sin(radianDegree) + pivotPoint.x;
        double yCoordinate = -(rotatedPoint.x - pivotPoint.x) * Math.sin(radianDegree) + (rotatedPoint.y - pivotPoint.y)
                * Math.cos(radianDegree) + pivotPoint.y;


        int computedIndex = (int) Math.round(yCoordinate * numberOfColumns + xCoordinate);

        if (isZeroIndex) {
            return computedIndex - 1;
        } else if (isLastIndex) {
            return computedIndex + 1;
        } else {
            return computedIndex;
        }
    }

    public int[] rotateByDegree(int destinationIndex, int degree) {
        logger.log(level, "destinationIndex = " + destinationIndex + " degree: " + degree);

        int[] coordinates = new int[4];
        int[] shapeCoordinates = shape.getBasicCoordinates(destinationIndex);

        for (int i = 0; i < shapeCoordinates.length; i++) {
            coordinates[i] = rotateIndexAroundPivotPoint(shapeCoordinates[i], degree,
                    shapeCoordinates[0]);
        }

        logger.log(level, "Coordinates after rotation: " + Arrays.toString(coordinates));
        return coordinates;
    }
}
