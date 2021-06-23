package usantatecla.chess;

import usantatecla.utils.Coordinate;

public enum Color {
    Black(0, 1),
    White(Coordinate.DIMENSION - 1, Coordinate.DIMENSION - 2);

    private final int rowPieces;
    private final int rowPawns;

    private Color(int rowPieces, int rowPawns) {
        this.rowPieces = rowPieces;
        this.rowPawns = rowPawns;
    }

    public int getForward() {
        if (this == Color.Black) {
            return -1;
        }
        return +1;
    }

    static Color get(int ordinal){
        return Color.values()[ordinal];
    }

    public int getRowPieces() {
        return rowPieces;
    }

    public int getRowPawns() {
        return rowPawns;
    }

    public Color getOpposite() {
        if (this == Color.Black) {
            return Color.White;
        }
        return Color.Black;
    }
}
