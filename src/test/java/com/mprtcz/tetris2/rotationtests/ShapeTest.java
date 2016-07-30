package com.mprtcz.tetris2.rotationtests;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.abstractshapes.TShape;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created by Azet on 2016-05-23.
 */
public class ShapeTest {
    int numberOfColumns = 20;
    int maxArrayIndex = 380;
    int initialCoordinate = 220;
    HashSet<Integer> savedIndexes = new HashSet<>();

    @Test
    public void rotateIndexByBasicIndexTest() {
        Shape shape = new TShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        System.out.println("Before rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape();
        System.out.println("After rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        int[] coords = shape.getShapeCoordinates();

        assertTrue(coords[0] == 220);
        assertTrue(coords[1] == 201);
        assertTrue(coords[2] == 221);
        assertTrue(coords[3] == 241);


        System.out.println("Before rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape();
        coords = shape.getShapeCoordinates();
        System.out.println("After rotation: " + Arrays.toString(shape.getShapeCoordinates()));

        assertTrue(coords[0] == 21);
        assertTrue(coords[1] == 12);
        assertTrue(coords[2] == 11);
        assertTrue(coords[3] == 10);

        System.out.println("Before rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape();
        coords = shape.getShapeCoordinates();
        System.out.println("After rotation: " + Arrays.toString(shape.getShapeCoordinates()));

        assertTrue(coords[0] == 21);
        assertTrue(coords[1] == 10);
        assertTrue(coords[2] == 20);
        assertTrue(coords[3] == 30);

        System.out.println("Before rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape();
        coords = shape.getShapeCoordinates();
        System.out.println("After rotation: " + Arrays.toString(shape.getShapeCoordinates()));

        assertTrue(coords[0] == 21);
        assertTrue(coords[1] == 30);
        assertTrue(coords[2] == 31);
        assertTrue(coords[3] == 32);
    }

    @Test
    public void moveRightTest() {
        Shape shape = new TShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        System.out.println(" Before rightshift: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape();
        shape.moveRight();
        System.out.println("After rightshift: " + Arrays.toString(shape.getShapeCoordinates()));
        int[] coords = shape.getShapeCoordinates();
        System.out.println("coords: " + Arrays.toString(coords));

        assertTrue(coords[0] == 22);
        assertTrue(coords[1] == 32);
        assertTrue(coords[2] == 42);
        assertTrue(coords[3] == 41);
    }
}
