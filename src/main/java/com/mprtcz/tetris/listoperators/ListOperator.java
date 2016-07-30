package com.mprtcz.tetris.listoperators;

import com.mprtcz.tetris.logger.TetrisGameLogger;
import com.mprtcz.tetris.abstractshapes.Shape;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-08.
 */
public class ListOperator {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private HashSet<Integer> savedIndexes;
    private int maxIndex;
    private int numberOfColumns;

    public ListOperator(int maxIndex, int numberOfColumns) {
        this.savedIndexes = new HashSet<>();
        this.maxIndex = maxIndex;
        this.numberOfColumns = numberOfColumns;
    }

    public HashSet<Integer> getSavedIndexes() {
        return savedIndexes;
    }

    public void setSavedIndexes(HashSet<Integer> savedIndexes) {
        this.savedIndexes = savedIndexes;
    }

    public HashSet<Integer> getIndexesToDraw(Shape shape) {
        HashSet<Integer> toDrawList = new HashSet<>();
        for (Integer i : savedIndexes) {
            toDrawList.add(i);
        }
        if (shape != null) {
            for (Integer i : shape.getShapeCoordinates()) {
                toDrawList.add(i);
            }
        }
        return toDrawList;
    }

    public HashSet<Integer> drawShape(Shape shape){
        HashSet<Integer> toDrawList = new HashSet<>();
        if (shape != null) {
            for (Integer i : shape.getCoordinatesForIndex(3)) {
                toDrawList.add(i);
            }
        }
        return toDrawList;
    }

    public void addIndexesToList(Shape shape) {
        for (Integer i : shape.getShapeCoordinates()) {
            if (!savedIndexes.contains(i)) {
                savedIndexes.add(i);
            }
        }
    }

    public int removeFullRowsFromSavedIndexes(int points) {
        ConditionsChecker conditionsChecker = new ConditionsChecker(savedIndexes, maxIndex);
        List<Integer> rowsToRemove = conditionsChecker.getIndexesOfFullRows(numberOfColumns);
        if (rowsToRemove.size() > 0) {
            points += rowsToRemove.size() * 10;
            savedIndexes.removeAll(getIndexesFromRowsToRemove(rowsToRemove));
            pullRemainingBricksDown(rowsToRemove);
        }
        return points;
    }

    private List<Integer> getIndexesFromRowsToRemove(List<Integer> rows) {
        List<Integer> indexesToRemove = new ArrayList<>();
        for (Integer row : rows) {
            for (int j = 0; j < this.numberOfColumns; j++) {
                indexesToRemove.add(row * this.numberOfColumns + j);
            }
        }
        return indexesToRemove;
    }

    private void pullRemainingBricksDown(List<Integer> rowsToRemove) {
        for(int i : rowsToRemove){
            HashSet<Integer> movedIndexes = new HashSet<>();
            for(Integer index : savedIndexes){
                if(index < (i * numberOfColumns)){
                    movedIndexes.add(index+numberOfColumns);
                } else {
                    movedIndexes.add(index);
                }
            }
            savedIndexes = movedIndexes;
        }
    }

    public boolean canShapeBeAddedToGame(Shape shape){
        for(int i : shape.getShapeCoordinates()){
            if(savedIndexes.contains(i)){
                return false;
            }
        }
        return true;
    }

}
