package usantatecla.utils;

import java.util.ArrayList;
import java.util.List;

public class Coordinate {
    public static final int DIMENSION = 8;
    static final String ROW = "Row: ";
    static final String COLUMN = "Column: ";
    private int row, column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinate() {

    }

    public int horizontalDistance(Coordinate coordinate) {
        return this.column - coordinate.column;
    }

    public int verticalDistance(Coordinate coordinate) {
        return this.row - coordinate.row;
    }

    public int absoluteHorizontalDistance(Coordinate coordinate) {
        return Math.abs(this.horizontalDistance(coordinate));
    }

    public int absoluteVerticalDistance(Coordinate coordinate) {
        return Math.abs(this.verticalDistance(coordinate));
    }

    public int getDistance(Coordinate coordinate) {
        return (int) Math.sqrt(Math.pow(this.absoluteHorizontalDistance(coordinate), 2) + Math.pow(this.absoluteVerticalDistance(coordinate), 2));
    }

    public boolean isInSameDiagonalAs(Coordinate coordinate) {
        return this.absoluteVerticalDistance(coordinate) == this.absoluteHorizontalDistance(coordinate);
    }

    private Direction getDirectionTo(Coordinate coordinate) {

        if (this.getDistance(coordinate) == 0) {
            return Direction.NULL;
        }
        if (this.verticalDistance(coordinate) == 0) {
            return Direction.HORIZONTAL;
        }
        if (this.horizontalDistance(coordinate) == 0) {
            return Direction.VERTICAL;
        }
        if (this.isInSameDiagonalAs(coordinate)) {
            if ((this.row > coordinate.row && this.column > coordinate.column) || (this.row < coordinate.row && this.column < coordinate.column)) {
                return Direction.MAIN_DIAGONAL;
            }
            return  Direction.INVERSE_DIAGONAL;
        }
        return Direction.NULL;
    }

    private Coordinate getNextCoordinate(Coordinate target) {
        Direction direction = this.getDirectionTo(target);
        Coordinate nextCoordinate = direction.getNextCoordinate(this);
        Coordinate nextOppositeCoordinate = direction.getNextOppositeCoordinate(this);

        if (target.getDistance(nextCoordinate) > target.getDistance(nextOppositeCoordinate)) {
            return nextOppositeCoordinate;
        }
        return nextCoordinate;
    }

    public ArrayList<Coordinate> getCoordinatesTo(Coordinate coordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(this);


        if (this.getDirectionTo(coordinate) != Direction.NULL) {
            Coordinate nextCoordinate = this.getNextCoordinate(coordinate);

            while (!nextCoordinate.equals(coordinate)) {
                coordinates.add(nextCoordinate);
                nextCoordinate = nextCoordinate.getNextCoordinate(coordinate);
            }
        }
        coordinates.add(coordinate);
        return coordinates;
    }

    public void read(String title) {
        Console console = Console.getInstance();
        console.writeln(title);
        this.row = console.readInt(Coordinate.ROW) - 1;
        this.column = console.readInt(Coordinate.COLUMN) - 1;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    private boolean equals(Coordinate coordinate) {
        return this.row == coordinate.row && this.column == coordinate.column;
    }

    public List<Coordinate> getNeighbours() {
        List<Coordinate> neighbours = new ArrayList<>();

        for (Direction direction: Direction.values()) {
            if (direction.isNull()) continue;
            if (direction.getNextCoordinate(this).isValid()) {
                neighbours.add(direction.getNextCoordinate(this));
            }
            if (direction.getNextOppositeCoordinate(this).isValid()) {
                neighbours.add(direction.getNextOppositeCoordinate(this));
            }
        }
        return neighbours;
    }

    private boolean isValid() {
        return this.row >= 0 && this.row < DIMENSION
                && this.column >= 0 && this.column < DIMENSION;
    }
}
