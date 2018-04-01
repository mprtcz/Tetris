package com.mprtcz.tetris.abstractshapes;

import com.mprtcz.tetris.listoperators.ConditionsChecker;
import com.mprtcz.tetris.logger.TetrisGameLogger;
import com.mprtcz.tetris.rotator.Rotator;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-22.
 */
public abstract class Shape {

    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    public enum ShapeType {
        I_shape("I"),
        O_shape("O"),
        T_shape("T"),
        L_shape("L"),
        J_shape("J"),
        Z_shape("Z"),
        S_shape("S");

        private String value;

        ShapeType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        private static final List<ShapeType> VALUES = Arrays.asList(values());
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static ShapeType randomShapeType() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    public static Shape shapeFactory(ShapeType shapeType, int numberOfColumns, int maxListIndex, Map<Integer, Color> savedIndexes) {
        switch (shapeType) {
            case I_shape:
                return new IShape(numberOfColumns, maxListIndex, savedIndexes);
            case O_shape:
                return new OShape(numberOfColumns, maxListIndex, savedIndexes);
            case T_shape:
                return new TShape(numberOfColumns, maxListIndex, savedIndexes);
            case L_shape:
                return new LShape(numberOfColumns, maxListIndex, savedIndexes);
            case J_shape:
                return new JShape(numberOfColumns, maxListIndex, savedIndexes);
            case Z_shape:
                return new ZShape(numberOfColumns, maxListIndex, savedIndexes);
            case S_shape:
                return new SShape(numberOfColumns, maxListIndex, savedIndexes);
            default:
                return new IShape(numberOfColumns, maxListIndex, savedIndexes);
        }
    }

    public enum Orientation {
        BASIC("BASIC", 0),
        DEG90("90_DEGREES", 90),
        DEG180("180_DEGREES", 180),
        DEG270("270_DEGREES", 270);

        private String stringValue;
        private int degree;

        Orientation(String value, int degree) {
            this.stringValue = value;
            this.degree = degree;
        }

        public int getDegree() {
            return degree;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    private static Orientation getNextOrientation(Orientation orientation) {
        if (orientation == Orientation.BASIC) {
            return Orientation.DEG90;
        } else if (orientation == Orientation.DEG90) {
            return Orientation.DEG180;
        } else if (orientation == Orientation.DEG180) {
            return Orientation.DEG270;
        } else if (orientation == Orientation.DEG270) {
            return Orientation.BASIC;
        } else {
            return Orientation.BASIC;
        }
    }

    public Shape(int maxArrayIndex, int numberOfColumns, Map<Integer, Color> savedIndexes) {
        logger.log(level, "New Shape: " + this.getShapeType());
        this.initialCoordinate = numberOfColumns / 2;
        this.numberOfColumns = numberOfColumns;
        this.orientation = Orientation.BASIC;
        this.rotator = new Rotator(this);
        this.conditionsChecker = new ConditionsChecker(maxArrayIndex, numberOfColumns);
        this.conditionsChecker.setSavedIndexesList(savedIndexes);
        this.color = getRandomColor();
    }

    private int initialCoordinate;
    private int numberOfColumns;
    private Orientation orientation;
    private Rotator rotator;
    private ConditionsChecker conditionsChecker;
    private Color color;


    public abstract int[] getBasicCoordinates(int destinationIndex);

    public abstract ShapeType getShapeType();

    public int[] getCoordinatesForIndex(int destinationIndex) {
        return rotator.rotateByDegree(destinationIndex, orientation.getDegree());
    }

    private int[] getCoordinates() {
        return  getCoordinatesForIndex(initialCoordinate);
    }

    public int[] getNextOrientationCoordinates() {
        return rotator.rotateByDegree(initialCoordinate, getNextOrientation(orientation).getDegree());
    }

    private int[] getRightShiftedCoordinates() {
        logger.log(level, "");
        int rightShiftedInitialCoordinate = initialCoordinate + 1;
        return getCoordinatesForIndex(rightShiftedInitialCoordinate);
    }

    private int[] getLeftShiftedCoordinates() {
        logger.log(level, "");
        int leftShiftedInitialCoordinate = initialCoordinate - 1;
        return getCoordinatesForIndex(leftShiftedInitialCoordinate);
    }

    public void moveRight() {
        logger.log(level, "");
        int[] coordinates = getRightShiftedCoordinates();
        if (conditionsChecker.checkAllMovingConditions(coordinates) &&
                conditionsChecker.checkBorderCondition(getCoordinates(), coordinates)) {
            this.initialCoordinate = coordinates[0];
        }
    }

    public void moveLeft() {
        logger.log(level, "");
        int[] coordinates = getLeftShiftedCoordinates();
        if (conditionsChecker.checkAllMovingConditions(coordinates) &&
                conditionsChecker.checkBorderCondition(getCoordinates(), coordinates)) {
            this.initialCoordinate = coordinates[0];
        }
    }

    public void rotateShape() {
        logger.log(level, "");
        if (conditionsChecker.checkAllRotatingConditions(this)) {
            this.orientation = Shape.getNextOrientation(orientation);
        }
    }

    public boolean pullShapeIndexesDown() {
        if (conditionsChecker.checkAllMovingConditions(getCoordinatesForIndex(initialCoordinate + numberOfColumns))) {
            logger.log(level, "initial coordinate " + initialCoordinate + "number of columns: " + numberOfColumns);
            this.initialCoordinate += numberOfColumns;
            logger.log(level, "initial coordinate " + initialCoordinate);
            return true;
        } else {
            return false;
        }
    }

    public int[] getShapeCoordinates() {
        logger.log(level, " " + Arrays.toString(getCoordinates()));
        return getCoordinates();
    }

    public void setInitialCoordinate(int initialCoordinate) {
        this.initialCoordinate = initialCoordinate;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color getRandomColor() {
        String[] materialColors = {
                "#39add1", // light blue
                "#3079ab", // dark blue
                "#c25975", // mauve
                "#e15258", // red
                "#f9845b", // orange
                "#838cc7", // lavender
                "#7d669e", // purple
                "#53bbb4", // aqua
                "#51b46d", // green
                "#e0ab18", // mustard
                "#637a91", // dark gray
                "#f092b0", // pink
                "#b7c0c7"  // light gray
        };
        int randomNumber = new Random().nextInt(materialColors.length);
        String color = materialColors[randomNumber];
        return Color.web(color);
    }
}