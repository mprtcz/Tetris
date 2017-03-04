package com.mprtcz.tetris2.listoperatortest;

import com.mprtcz.tetris.listoperators.SavedIndexes;
import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Azet on 2016-06-01.
 */
public class SavedIndexesTest {
    int maxIndex = 19;
    int numberOfColumns = 4;
    int points = 0;
    Integer[] array = new Integer[]{19,18,17,16,15, 1};
    Map<Integer, Color> savedIndexes = new HashMap<>();
    {
        for(Integer i : array){
            savedIndexes.put(i, Color.ALICEBLUE);
        }
    }


    @Test
    public void removeFullRowsFromSavedIndexesTest(){
        SavedIndexes savedIndexes = new SavedIndexes(numberOfColumns, maxIndex);
        savedIndexes.setSavedIndexes(this.savedIndexes);
        savedIndexes.removeFullRowsFromSavedIndexes(points);

        assertTrue(this.savedIndexes.containsKey(15));

        assertTrue(this.savedIndexes.containsKey(1));

        assertEquals(this.savedIndexes.size(), 2);
    }
}
