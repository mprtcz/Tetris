package com.mprtcz.tetris.listoperators;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

}
