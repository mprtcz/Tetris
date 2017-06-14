package com.mprtcz.tetris.listoperators;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.logger.TetrisGameLogger;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-08.
 */
public class SavedIndexes {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    private Map<Integer, Color> savedIndexes;
    private int maxIndex;
    private int numberOfColumns;

    public SavedIndexes(int numberOfColumns, int maxIndex) {
        this.savedIndexes = new HashMap<>();
        this.maxIndex = maxIndex;
        this.numberOfColumns = numberOfColumns;
    }

    public Map<Integer, Color> getSavedIndexes() {
        return savedIndexes;
    }

    public void setSavedIndexes(Map<Integer, Color> savedIndexes) {
        this.savedIndexes = savedIndexes;
    }

    public Map<Integer, Color> getIndexesToDraw(Shape shape) {
        Map<Integer, Color> toDrawList = new ConcurrentHashMap<>();
        for (Map.Entry<Integer, Color> i : savedIndexes.entrySet()) {
            toDrawList.put(i.getKey(), i.getValue());
        }
        if (shape != null) {
            for (Integer i : shape.getShapeCoordinates()) {
                toDrawList.put(i, shape.getColor());
            }
        }
        return toDrawList;
    }

    public Map<Integer, Color> drawShape(Shape shape) {
        Map<Integer, Color> toDrawList = new HashMap<>();
        if (shape != null) {
            for (Integer i : shape.getCoordinatesForIndex(3)) {
                toDrawList.put(i, shape.getColor());
            }
        }
        return toDrawList;
    }

    public void saveFallenShape(Shape shape) {
        for (Integer i : shape.getShapeCoordinates()) {
            if (!savedIndexes.containsKey(i)) {
                savedIndexes.put(i, shape.getColor());
            }
        }
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
        for (int i : rowsToRemove) {
            Map<Integer, Color> movedIndexes = new HashMap<>();
            for (Map.Entry<Integer, Color> index : savedIndexes.entrySet()) {
                if (index.getKey() < (i * numberOfColumns)) {
                    int newIndex = index.getKey() + numberOfColumns;
                    movedIndexes.put(newIndex, index.getValue());
                } else {
                    movedIndexes.put(index.getKey(), index.getValue());
                }
            }
            savedIndexes = movedIndexes;
        }
    }

    public boolean canShapeBeAddedToGame(Shape shape) {
        for (int i : shape.getShapeCoordinates()) {
            if (savedIndexes.containsKey(i)) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getAndMarkRowsToBeRemoved() {
        ConditionsChecker conditionsChecker = new ConditionsChecker(savedIndexes, maxIndex);
        List<Integer> rowsToRemove = conditionsChecker.getIndexesOfFullRows(numberOfColumns);
        List<Integer> indexesToRemove = getIndexesFromRowsToRemove(rowsToRemove);
        if (rowsToRemove.size() > 0) {
            indexesToRemove.forEach(this::colorIndexAsRed);
        }
        return rowsToRemove;
    }

    public int removeListedRowsAndAddScore(List<Integer> rowsToRemove, int score) {
        List<Integer> indexesToRemove = getIndexesFromRowsToRemove(rowsToRemove);
        if (rowsToRemove.size() > 0) {
            score += rowsToRemove.size() * 10;
            for (Integer i : indexesToRemove) {
                savedIndexes.remove(i);
            }
            pullRemainingBricksDown(rowsToRemove);
        }
        return score;
    }

    private void colorIndexAsRed(Integer i) {
        savedIndexes.put(i, Color.RED);
    }
}