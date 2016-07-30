package com.mprtcz.tetris2;

import com.mprtcz.tetris.listoperators.ConditionsChecker;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Azet on 2016-05-17.
 */
public class ConditionsCheckerTest {
    Integer[] array = new Integer[]{0,1,2,3,6,8,9,10,11,13,14};
    Integer[] rowsResultArray = new Integer[]{0,2};
    Integer[] indexesToRemoveArray = new Integer[]{0,1,2,3,10,11,13,14};
    private List<Integer> rowsResultList = new ArrayList<>(Arrays.asList(rowsResultArray));
    private HashSet<Integer> savedIndexes = new HashSet<>(Arrays.asList(array));
    private int maxIndex = 15;
    private int numberOfColumns = 4;

    @Test
    public void getIndexesOfFullRowsTest(){
        ConditionsChecker conditionsChecker = new ConditionsChecker(savedIndexes, maxIndex);
        List<Integer> rowsToDelete = conditionsChecker.getIndexesOfFullRows(numberOfColumns);

        System.out.println("savedIndexes: " +savedIndexes.toString());
        System.out.println("resultindexes: " +rowsToDelete.toString());

        for(int i = 0; i < rowsToDelete.size(); i++){
           assertTrue(Objects.equals(rowsToDelete.get(i), rowsResultList.get(i)));
        }
    }
}
