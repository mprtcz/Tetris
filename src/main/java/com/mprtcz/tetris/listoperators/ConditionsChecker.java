package com.mprtcz.tetris.listoperators;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.logger.TetrisGameLogger;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ConditionsChecker {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private Map<Integer, Color> savedIndexesList = new HashMap<>();
    private int maxIndex;
    private int numberOfColumns;

    public ConditionsChecker(Map<Integer, Color> savedIndexesList, int maxIndex) {
        this.savedIndexesList = savedIndexesList;
        this.maxIndex = maxIndex;
    }

    public ConditionsChecker(int maxIndex, int numberOfColumns) {
        this.maxIndex = maxIndex;
        this.numberOfColumns = numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public void setSavedIndexesList(Map<Integer, Color> savedIndexesList) {
        this.savedIndexesList = savedIndexesList;
    }

    private boolean checkIfIndexesAreWithinTheList(int[] indexes) {
        return !checkIfIndexFulfillsTheRequirement(
                indexes,
                integer -> integer >= maxIndex,
                integer -> logger.log(level, "Index not in range of the list: " + integer + " maxIndex: " + maxIndex));
    }

    private boolean checkIfIndexesExistInAList(int[] indexes) {
        return checkIfIndexFulfillsTheRequirement(
                indexes,
                integer -> savedIndexesList.containsKey(integer),
                integer -> logger.log(level, "Index " + integer + " exists in a list: " + savedIndexesList.toString()));
    }

    private boolean checkIfIndexesAreLessThanZero(int[] indexes) {
        return checkIfIndexFulfillsTheRequirement(
                indexes,
                integer -> integer < 0,
                integer -> logger.log(Level.WARNING, "Index " + integer + " less than zero!"));
    }

    private boolean checkIfIndexFulfillsTheRequirement(
            int[] indexes, Predicate<Integer> p, Consumer<Integer> messageLogger) {
        return Arrays.stream(indexes).filter(value -> {
            if(p.test(value)) {
                messageLogger.accept(value);
                return true;
            }
            return false;
        }).findAny().isPresent();
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
        boolean borderCondition;
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
        if (numberOfColumns == 0) {
            logger.log(level, "Right Border Condition false, number of columns is 0");
            return false;
        }

        int[] initialCoordinates = shape.getShapeCoordinates();
        int[] targetCoordinates = shape.getNextOrientationCoordinates();

        boolean rightBorderCondition = IntStream.range(0, initialCoordinates.length).filter(
                index -> (initialCoordinates[index] % numberOfColumns) > (targetCoordinates[index] % numberOfColumns))
                .findAny().isPresent();

        if(rightBorderCondition) {
            logger.log(level, "Right Border Condition false");
            return false;
        } else {
            logger.log(level, "Right Border Condition True");
            return true;
        }
    }

    public boolean checkAllMovingConditions(int[] indexes) {
        logger.log(level, "Checking all conditions for indexes: " + Arrays.toString(indexes));
        boolean checkDuplicates = checkIfIndexesExistInAList(indexes);
        logger.log(level, "checkIfIndexesExistInAList(indexes): " + checkDuplicates);
        boolean checkIfWithin = checkIfIndexesAreWithinTheList(indexes);
        logger.log(level, "checkIfIndexesAreWithinTheList(indexes): " + checkIfWithin);
        boolean lessThanZero = checkIfIndexesAreLessThanZero(indexes);
        logger.log(level, "checkIfIndexesAreLessThanZero(indexes): " + lessThanZero);
        boolean result = !checkDuplicates && checkIfWithin && !lessThanZero;
        logger.log(level, "Returning: " + result);
        return result;
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
        return !IntStream.range(0, numberOfColumns).filter(value -> {
            Integer index = row * numberOfColumns + value;
            return !savedIndexesList.containsKey(index);
        }).findFirst().isPresent();
    }
}
