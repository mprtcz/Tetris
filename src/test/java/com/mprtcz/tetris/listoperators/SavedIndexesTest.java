package com.mprtcz.tetris.listoperators;

import com.mprtcz.tetris.abstractshapes.IShape;
import com.mprtcz.tetris.abstractshapes.Shape;
import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Azet on 2016-06-01.
 */
public class SavedIndexesTest {

    @Test
    public void getIndexesFromRowsToRemoveTest_oneRow() {
        SavedIndexes savedIndexes = new SavedIndexes(5, 15);
        List<Integer> testData = new ArrayList<>();
        testData.add(1);
        List<Integer> result = savedIndexes.getIndexesFromRowsToRemove(testData);

        List<Integer> expected = Arrays.asList(5, 6, 7, 8, 9);
        assertEquals(expected, result);
    }

    @Test
    public void getIndexesFromRowsToRemoveTest_twoRows() {
        SavedIndexes savedIndexes = new SavedIndexes(5, 15);
        List<Integer> testData = new ArrayList<>();
        testData.add(1);
        testData.add(3);
        List<Integer> result = savedIndexes.getIndexesFromRowsToRemove(testData);

        List<Integer> expected = Arrays.asList(5, 6, 7, 8, 9, 15, 16, 17, 18, 19);
        assertEquals(expected, result);
    }

    @Test
    public void getIndexesFromRowsToRemoveTest_noRow() {
        SavedIndexes savedIndexes = new SavedIndexes(5, 15);
        List<Integer> testData = new ArrayList<>();
        List<Integer> result = savedIndexes.getIndexesFromRowsToRemove(testData);

        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, result);
    }

    @Test
    public void getIndexesToDrawTest() {
        SavedIndexes savedIndexes = new SavedIndexes(5, 15);
        Map<Integer, Color> savedIndexesMap = new HashMap<>();
        savedIndexesMap.put(1, Color.valueOf("#000000"));
        savedIndexesMap.put(2, Color.valueOf("#000000"));
        savedIndexesMap.put(3, Color.valueOf("#000000"));
        savedIndexesMap.put(4, Color.valueOf("#000000"));
        savedIndexesMap.put(5, Color.valueOf("#000000"));
        savedIndexes.setSavedIndexes(savedIndexesMap);
        Shape testShape = new IShape(5, 15, savedIndexesMap);

        Map<Integer, Color> result = savedIndexes.getIndexesToDrawWith(testShape);

        Set<Integer> expectedKeys = new HashSet<>(Arrays.asList(1, 17, 2, 3, 4, 5, 7, 12));
        assertEquals(result.keySet(), expectedKeys);
    }

}
