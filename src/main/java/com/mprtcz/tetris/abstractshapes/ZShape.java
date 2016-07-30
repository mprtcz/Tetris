package com.mprtcz.tetris.abstractshapes;

import com.mprtcz.tetris.logger.TetrisGameLogger;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-25.
 */
public class ZShape extends Shape {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private int numberOfColumns;

    public ZShape(int numberOfColumns, int maxArrayIndex, Set<Integer> savedIndexes){
        super(maxArrayIndex, numberOfColumns, savedIndexes);
        this.numberOfColumns = numberOfColumns;
    }

    @Override
    public int[] getBasicCoordinates(int destinationIndex) {
        int[] basicCoords = new int[4];
        basicCoords[0] = destinationIndex;
        basicCoords[1] = destinationIndex + numberOfColumns;
        basicCoords[2] = destinationIndex + numberOfColumns + 1;
        basicCoords[3] = destinationIndex + 2*numberOfColumns + 1;
        return basicCoords;
    }
    @Override
    public ShapeType getShapeType(){
        return ShapeType.Z_shape;
    }
}
