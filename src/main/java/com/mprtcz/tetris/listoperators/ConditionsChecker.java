package com.mprtcz.tetris.listoperators;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.logger.TetrisGameLogger;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-10.
 */
public class ConditionsChecker {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private Map<Integer, Color> savedIndexesList = new HashMap<>();
    private int maxIndex;

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    private int numberOfColumns;

    public ConditionsChecker(Map<Integer, Color> savedIndexesList, int maxIndex) {
        this.savedIndexesList = savedIndexesList;
        this.maxIndex = maxIndex;
    }

    public ConditionsChecker(int maxIndex, int numberOfColumns) {
        this.maxIndex = maxIndex;
        this.numberOfColumns = numberOfColumns;
    }

    public void setSavedIndexesList(Map<Integer, Color> savedIndexesList) {
        this.savedIndexesList = savedIndexesList;
    }

    public ConditionsChecker(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    private boolean checkIfIndexesAreWithinTheList(int[] indexes) {
        for (int i : indexes) {
            if (i >= maxIndex) {
                logger.log(level, "Index not in range of the list: " + i + " maxIndex: " + maxIndex);
                return false;
            }
        }
        return true;
    }

    private boolean checkIfIndexesExistInAList(int[] indexes) {
        for (int i : indexes) {
            if (savedIndexesList.containsKey(i)) {
                logger.log(level, "Index " + i + " exists in a list: " + savedIndexesList.toString());
                return true;
            }
        }
        return false;
    }

    private boolean checkIfIndexesAreLessThanZero(int[] indexes) {
        for (int i : indexes) {
            if (i < 0) {
                logger.log(Level.WARNING, "Index " + i + " less than zero!");
                return true;
            }
        }
        return false;
    }

    public boolean checkBorderCondition(int[] initialCoordinates, int[] targetCoordinates) {
        if (initialCoordinates.length == targetCoordinates.length) {
            if (numberOfColumns != 0) {
                for (int i = 0; i < initialCoordinates.length; i++) {
                    if (initialCoordinates[i] / numberOfColumns != targetCoordinates[i] / numberOfColumns) {
                        logger.log(level, "Border Condition False");
                        return false;
                    }
                }
                logger.log(level, "Border Condition True");
                return true;
            } else {
                logger.log(level, "Border Condition False");
                return false;
            }
        } else {
            logger.log(level, "Border Condition False");
            return false;
        }
    }

    public boolean checkAllRotatingConditions(Shape shape) {
        logger.log(level, "Checking rotating conditions for shape: " + shape.toString());
        boolean borderCondition = false;
        Shape.Orientation orientation = shape.getOrientation();
        int[] targetCoordinates = shape.getNextOrientationCoordinates();

        if (orientation == Shape.Orientation.BASIC || orientation == Shape.Orientation.DEG270) {
            borderCondition = checkRightBorderConditions(shape);
        } else if (orientation == Shape.Orientation.DEG90 || orientation == Shape.Orientation.DEG180) {
            borderCondition = checkLeftBorderConditions(shape);
        } else {
            logger.log(level, "Strange orientation! " + orientation.toString());
            borderCondition = true;
        }

        boolean movingConditions = checkAllMovingConditions(targetCoordinates);
        return movingConditions && borderCondition;
    }

    private boolean checkLeftBorderConditions(Shape shape) {
        logger.log(level, "Checking rotating conditions for shape: " + shape.toString());
        int[] initialCoordinates = shape.getShapeCoordinates();
        int[] targetCoordinates = shape.getNextOrientationCoordinates();

        if (numberOfColumns != 0) {
            for (int i = 0; i < initialCoordinates.length; i++) {
                if ((initialCoordinates[i] % numberOfColumns) < (targetCoordinates[i] % numberOfColumns)) {
                    logger.log(level, "Left Border Condition false");
                    return false;
                }
            }
            logger.log(level, "Left Border Condition true");
            return true;
        }
        logger.log(level, "Left Border Condition false");
        return false;
    }

    private boolean checkRightBorderConditions(Shape shape) {
        logger.log(level, "Checking rotating conditions for shape: " + shape.toString());
        int[] initialCoordinates = shape.getShapeCoordinates();
        int[] targetCoordinates = shape.getNextOrientationCoordinates();

        if (numberOfColumns != 0) {
            for (int i = 0; i < initialCoordinates.length; i++) {
                if ((initialCoordinates[i] % numberOfColumns) > (targetCoordinates[i] % numberOfColumns)) {
                    logger.log(level, "Right Border Condition false");
                    return false;
                }
            }
            logger.log(level, "Right Border Condition True");
            return true;
        }
        logger.log(level, "Right Border Condition false");
        return false;
    }

    public boolean checkAllMovingConditions(int[] indexes) {
        logger.log(level, "Checking all conditions for indexes: " + Arrays.toString(indexes));
        boolean checkDuplicates = checkIfIndexesExistInAList(indexes);
        logger.log(level, "checkIfIndexesExistInAList(indexes): " + checkIfIndexesExistInAList(indexes));
        boolean checkIfWithin = checkIfIndexesAreWithinTheList(indexes);
        logger.log(level, "checkIfIndexesAreWithinTheList(indexes): " + checkIfIndexesAreWithinTheList(indexes));
        boolean lessThanZero = checkIfIndexesAreLessThanZero(indexes);
        logger.log(level, "checkIfIndexesAreLessThanZero(indexes): " + checkIfIndexesAreLessThanZero(indexes));
        logger.log(level, "Returning: " + (!checkDuplicates && checkIfWithin && !lessThanZero));
        return !checkDuplicates && checkIfWithin && !lessThanZero;
    }

    public List<Integer> getIndexesOfFullRows(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        List<Integer> indexesOfFullRows = new ArrayList<>();
        int numberOfRows = (maxIndex + 1) / this.numberOfColumns;

        for (int row = 0; row < numberOfRows; row++) {
            if (checkIfRowIsFull(row)) {
                indexesOfFullRows.add(row);
            }
        }
        logger.log(level, "Full row indexes: " + indexesOfFullRows.toString());
        return indexesOfFullRows;
    }

    private boolean checkIfRowIsFull(int row) {
        for (int i = 0; i < numberOfColumns; i++) {
            Integer index = row * numberOfColumns + i;
            if (!savedIndexesList.containsKey(index)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfRowIsEmpty(int row) {
        for (int i = 0; i < numberOfColumns; i++) {
            Integer index = row * numberOfColumns + i;
            if (savedIndexesList.containsKey(index)) {
                return false;
            }
        }
        return true;
    }
}
