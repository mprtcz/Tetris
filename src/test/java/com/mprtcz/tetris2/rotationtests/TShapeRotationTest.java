package com.mprtcz.tetris2.rotationtests;

import com.mprtcz.tetris.abstractshapes.Shape;
import com.mprtcz.tetris.abstractshapes.TShape;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created by Azet on 2016-07-27.
 */
public class TShapeRotationTest {
    private int numberOfColumns = 20;
    private int maxArrayIndex = 380;
    private int initialCoordinate = 230;
    private HashSet<Integer> savedIndexes = new HashSet<>();

    @Test
    public void rotateShape90Deg() {
        Shape shape = new TShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        System.out.println("Before rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape();
        System.out.println("After rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        int[] coordinates = shape.getShapeCoordinates();
        int[] expectedCoordinates = new int[]{230, 251, 231, 211};

        for (int i = 0; i < coordinates.length; i++) {
            assertTrue(coordinates[i] == expectedCoordinates[i]);
        }
    }

    @Test
    public void rotateShape180Deg() {
        Shape shape = new TShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        System.out.println("Before rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape(); //90deg
        shape.rotateShape();//180deg
        System.out.println("After rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        int[] coordinates = shape.getShapeCoordinates();
        int[] expectedCoordinates = new int[]{230, 211, 210, 209};

        for (int i = 0; i < coordinates.length; i++) {
            assertTrue(coordinates[i] == expectedCoordinates[i]);
        }
    }

    @Test
    public void rotateShape270Deg() {
        Shape shape = new TShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        System.out.println("Before rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape(); //90deg
        shape.rotateShape();//180deg
        shape.rotateShape();//270deg

        System.out.println("After rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        int[] coordinates = shape.getShapeCoordinates();
        int[] expectedCoordinates = new int[]{230, 209, 229, 249};

        for (int i = 0; i < coordinates.length; i++) {
            assertTrue(coordinates[i] == expectedCoordinates[i]);
        }
    }

    @Test
    public void rotateShape360Deg() {
        Shape shape = new TShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        System.out.println("Before rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.rotateShape(); //90deg
        shape.rotateShape();//180deg
        shape.rotateShape();//270deg
        shape.rotateShape();//360deg
        System.out.println("After rotation: " + Arrays.toString(shape.getShapeCoordinates()));
        int[] coordinates = shape.getShapeCoordinates();
        int[] expectedCoordinates = new int[]{230, 249, 250, 251};

        for (int i = 0; i < coordinates.length; i++) {
            assertTrue(coordinates[i] == expectedCoordinates[i]);
        }
    }

    @Test
    public void moveRightTest() {
        Shape shape = new TShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        System.out.println(" Before right shift: " + Arrays.toString(shape.getShapeCoordinates()));
        shape.moveRight();
        System.out.println("After right shift: " + Arrays.toString(shape.getShapeCoordinates()));
        int[] coordinates = shape.getShapeCoordinates();
        System.out.println("coordinates: " + Arrays.toString(coordinates));
        int[] expectedCoordinates = new int[]{231, 250, 251, 252};

        for (int i = 0; i < coordinates.length; i++) {
            assertTrue(coordinates[i] == expectedCoordinates[i]);
        }
    }

    @Test
    public void moveLeftToLeftBorderWhile90DegRotated() {
        Shape shape = new TShape(numberOfColumns, maxArrayIndex, savedIndexes);
        final int[] expectedAfterRotationCoordinates = new int[]{221, 242, 222, 202};
        final int[] expectedCoordinatesAfterShift = new int[]{220, 241, 221, 201};
        final int localInitialCoordinate = 221;

        shape.setInitialCoordinate(localInitialCoordinate);
        shape.rotateShape();

        int[] coordinates = shape.getShapeCoordinates();

        Assert.assertArrayEquals(expectedAfterRotationCoordinates ,coordinates);

        shape.moveLeft();

        int[] coordinatesAfterShift = shape.getShapeCoordinates();
        System.out.println("coordinatesAfterShift = " + Arrays.toString(coordinatesAfterShift));

        Assert.assertArrayEquals(coordinatesAfterShift, expectedCoordinatesAfterShift);
    }
}
