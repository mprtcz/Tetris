package com.mprtcz.tetris.abstractshapes;

import com.mprtcz.tetris.logger.TetrisGameLogger;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-08.
 */
public class IShape extends Shape {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private int numberOfColumns;
/*
    public IShape(int numberOfColumns, int maxArrayIndex) {
        int initialCoordinate = numberOfColumns / 2;
        super.setInitialCoordinate(initialCoordinate);
        logger.log(level, "initialCoordinate " + initialCoordinate);

        super.setMaxListIndex(maxArrayIndex);

        Orientation orientation = Orientation.BASIC;
        super.setOrientation(orientation);

        this.numberOfColumns = numberOfColumns;
        super.setNumberOfColumns(numberOfColumns);
    }//*/

    public IShape(int numberOfColumns, int maxArrayIndex, Set<Integer> savedIndexes) {
        super(maxArrayIndex, numberOfColumns, savedIndexes);
        this.numberOfColumns = numberOfColumns;
    }

    public int[] getBasicCoordinates(int destinationIndex) {
        int[] basicCoords = new int[4];
        basicCoords[0] = destinationIndex;
        basicCoords[1] = destinationIndex + numberOfColumns;
        basicCoords[2] = destinationIndex + (2 * numberOfColumns);
        basicCoords[3] = destinationIndex + (3 * numberOfColumns);
        return basicCoords;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.I_shape;
    }
}
