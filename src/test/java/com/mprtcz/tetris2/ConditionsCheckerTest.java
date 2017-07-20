package com.mprtcz.tetris2;

import com.mprtcz.tetris.listoperators.ConditionsChecker;
import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Azet on 2016-05-17.
 */
public class ConditionsCheckerTest {
    private Integer[] array = new Integer[]{};
    private Integer[] rowsResultArray = new Integer[]{0};
    Integer[] indexesToRemoveArray = new Integer[]{0,1,2,3,10,11,13,14};
    private List<Integer> rowsResultList = new ArrayList<>(Arrays.asList(rowsResultArray));
    private Map<Integer, Color> savedIndexes = new HashMap<>();
    private int maxIndex = 15;
    private int numberOfColumns = 4;

    @Test
    public void getIndexesOfFullRowsTest(){
        ConditionsChecker conditionsChecker = new ConditionsChecker(savedIndexes, maxIndex);
        List<Integer> rowsToDelete = conditionsChecker.getIndexesOfFullRows(numberOfColumns);

        for(int i = 0; i < rowsToDelete.size(); i++){
           assertTrue(Objects.equals(rowsToDelete.get(i), rowsResultList.get(i)));
        }
    }
}
