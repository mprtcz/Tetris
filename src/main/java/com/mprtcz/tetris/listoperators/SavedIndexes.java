package com.mprtcz.tetris.listoperators;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.logger.TetrisGameLogger;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Azet on 2016-05-08.
 */
public class SavedIndexes {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    Map<Integer, Color> savedIndexes;
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

    public Map<Integer, Color> getIndexesToDrawWith(Shape shape) {

        Map<Integer, Color> toDrawList  = savedIndexes
                .entrySet()
                .stream()
                .collect(ConcurrentHashMap::new,
                (integerColorMap, integerColorEntry) ->
                        integerColorMap.put(integerColorEntry.getKey(), integerColorEntry.getValue()),
                (icMap, icMap2) -> {});

        if (shape == null) {
            return toDrawList;
        }
        Arrays.stream(shape.getShapeCoordinates())
                .forEach(value -> toDrawList.put(value, shape.getColor()));
        return toDrawList;
    }

    public Map<Integer, Color> drawShape(Shape shape) {
        Map<Integer, Color> toDrawList = new HashMap<>();
        if(shape == null) {
            return toDrawList;
        }
        Arrays.stream(shape.getCoordinatesForIndex(3))
                .forEach(value -> toDrawList.put(value, shape.getColor()));
        return toDrawList;
    }

    public void saveFallenShape(Shape shape) {
        Arrays.stream(shape.getShapeCoordinates())
                .filter(value -> !savedIndexes.containsKey(value))
                .forEach(value -> savedIndexes.put(value, shape.getColor()));
    }

    List<Integer> getIndexesFromRowsToRemove(List<Integer> rows) {
        return IntStream.range(0, rows.size()).collect(
                ArrayList::new,
                (integers, value) -> integers
                        .addAll(IntStream.range(0, numberOfColumns)
                                .map(operand -> rows.get(value) * numberOfColumns + operand)
                                .boxed()
                                .collect(Collectors.toList())),
                (integers, integers2) -> {}
        );
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
        return !Arrays.stream(shape.getShapeCoordinates())
                .filter(value -> savedIndexes.containsKey(value))
                .findFirst()
                .isPresent();
    }

    public List<Integer> getAndMarkRowsToBeRemoved() {
        ConditionsChecker conditionsChecker = new ConditionsChecker(savedIndexes, maxIndex);
        List<Integer> rowsToRemove = conditionsChecker.getIndexesOfFullRows(numberOfColumns);
        List<Integer> indexesToRemove = getIndexesFromRowsToRemove(rowsToRemove);
        indexesToRemove.forEach(this::colorIndexAsRed);
        return rowsToRemove;
    }

    public int removeListedRowsAndAddScore(List<Integer> rowsToRemove, int score) {
        if (rowsToRemove.isEmpty()) {
            return score;
        }
        score += rowsToRemove.size() * 10;
        getIndexesFromRowsToRemove(rowsToRemove).forEach(integer -> savedIndexes.remove(integer));
        pullRemainingBricksDown(rowsToRemove);
        return score;
    }

    private void colorIndexAsRed(Integer i) {
        savedIndexes.put(i, Color.RED);
    }
}