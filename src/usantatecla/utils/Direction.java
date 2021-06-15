package usantatecla.utils;

public enum Direction {

    HORIZONTAL(0, 1),
    VERTICAL(1, 0),
    MAIN_DIAGONAL(1, 1),
    INVERSE_DIAGONAL(1, -1),
    NULL;

    private int row;
    private int column;

    private Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    private Direction() {

    }

    public Coordinate getNextCoordinate(Coordinate coordinate) {
        return new Coordinate(coordinate.getRow() + this.row, coordinate.getColumn() + this.column);
    }

    public Coordinate getNextOppositeCoordinate(Coordinate coordinate) {
        return new Coordinate(coordinate.getRow() - this.row, coordinate.getColumn() - this.column);
    }

    public boolean isNull() {
        return this == Direction.NULL;
    }

}