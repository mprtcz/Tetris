package com.mprtcz.tetris2.rotationtests;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Azet on 2016-07-28.
 */
public class Utils {

    static void compareSameLengthIntArrays(int[] array1, int[] array2){
        for (int i = 0; i < array1.length; i++) {
            assertEquals(array1[i], array2[i]);
        }
    }
}
