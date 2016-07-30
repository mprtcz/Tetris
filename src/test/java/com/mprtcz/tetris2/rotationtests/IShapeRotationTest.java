package com.mprtcz.tetris2.rotationtests;

import com.mprtcz.tetris.abstractshapes.IShape;
import com.mprtcz.tetris.abstractshapes.Shape;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created by Azet on 2016-07-27.
 */
public class IShapeRotationTest {
    private int numberOfColumns = 20;
    private int maxArrayIndex = 380;
    private int initialCoordinate = 230;
    private HashSet<Integer> savedIndexes = new HashSet<>();

    @Test
    public void rotateShape90Deg(){
        Shape shape = new IShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        shape.rotateShape();
        int[] coordinates = shape.getShapeCoordinates();

        assertTrue(coordinates[0] == 230);
        assertTrue(coordinates[1] == 231);
        assertTrue(coordinates[2] == 232);
        assertTrue(coordinates[3] == 233);
    }

    @Test
    public void rotateShape180Deg(){
        Shape shape = new IShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        shape.rotateShape(); //90deg
        shape.rotateShape();//180deg
        int[] coordinates = shape.getShapeCoordinates();

        assertTrue(coordinates[0] == 230);
        assertTrue(coordinates[1] == 210);
        assertTrue(coordinates[2] == 190);
        assertTrue(coordinates[3] == 170);
    }

    @Test
    public void rotateShape270Deg(){
        Shape shape = new IShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        shape.rotateShape(); //90deg
        shape.rotateShape();//180deg
        shape.rotateShape();//270deg

        int[] coordinates = shape.getShapeCoordinates();

        assertTrue(coordinates[0] == 230);
        assertTrue(coordinates[1] == 229);
        assertTrue(coordinates[2] == 228);
        assertTrue(coordinates[3] == 227);
    }

    @Test
    public void rotateShape360Deg(){
        Shape shape = new IShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        shape.rotateShape(); //90deg
        shape.rotateShape();//180deg
        shape.rotateShape();//270deg
        shape.rotateShape();//360deg
        int[] coordinates = shape.getShapeCoordinates();

        assertTrue(coordinates[0] == 230);
        assertTrue(coordinates[1] == 250);
        assertTrue(coordinates[2] == 270);
        assertTrue(coordinates[3] == 290);
    }

    @Test
    public void moveRightTest() {
        Shape shape = new IShape(numberOfColumns, maxArrayIndex, savedIndexes);
        shape.setInitialCoordinate(initialCoordinate);
        shape.moveRight();
        int[] coordinates = shape.getShapeCoordinates();

        assertTrue(coordinates[0] == 231);
        assertTrue(coordinates[1] == 251);
        assertTrue(coordinates[2] == 271);
        assertTrue(coordinates[3] == 291);
    }
}
