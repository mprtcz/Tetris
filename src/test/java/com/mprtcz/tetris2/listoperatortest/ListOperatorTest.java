package com.mprtcz.tetris2.listoperatortest;

import com.mprtcz.tetris.listoperators.ListOperator;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Azet on 2016-06-01.
 */
public class ListOperatorTest {
    int maxIndex = 19;
    int numberOfColumns = 4;
    int points = 0;
    Integer[] array = new Integer[]{19,18,17,16,15, 1};
    HashSet<Integer> savedIndexes = new HashSet<>(Arrays.asList(array));


    @Test
    public void removeFullRowsFromSavedIndexesTest(){
        ListOperator listOperator = new ListOperator(maxIndex, numberOfColumns);
        listOperator.setSavedIndexes(savedIndexes);
        listOperator.removeFullRowsFromSavedIndexes(points);

        assertTrue(savedIndexes.contains(15));

        assertTrue(savedIndexes.contains(1));

        assertEquals(savedIndexes.size(), 2);
    }
}
