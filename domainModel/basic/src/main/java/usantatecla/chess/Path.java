package usantatecla.chess;

import java.util.ArrayList;

import usantatecla.utils.Coordinate;

public class Path {
    private final ArrayList<Square> path;

    public Path(ArrayList<Square> path) {
        this.path = path;
    }

    public Coordinate getOriginCoordinate() {
        return this.path.get(0).getCoordinate();
    }

    public Coordinate getTargetCoordinate() {
        return this.path.get(this.path.size()-1).getCoordinate();
    }

    public Square getOriginSquare() {
        return this.path.get(0);
    }

    public Color getOriginColor() {
        return this.path.get(0).getPiece().getColor();
    }

    public Square getTargetSquare() {
        return this.path.get(this.path.size()-1);
    }

    public Piece getOriginPiece() {
        return this.path.get(0).getPiece();
    }

    public Piece getTargetPiece() {
        return this.path.get(this.path.size()-1).getPiece();
    }

    public int getHorizontalDistance() {
        return this.getOriginCoordinate().absoluteHorizontalDistance(this.getTargetCoordinate());
    }

    public int getVerticalDistance() {
        return this.getOriginCoordinate().absoluteVerticalDistance(this.getTargetCoordinate());
    }

    public int getDistance() {
        return this.getOriginCoordinate().getDistance(this.getTargetCoordinate());
    }

    public boolean isDiagonal() {
        return this.getOriginCoordinate().isInSameDiagonalAs(this.getTargetCoordinate());
    }

    public boolean isMiddleEmpty() {
        for (int i = 1; i < this.size() - 1; i++) {
            if (!this.get(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTargetColor(Color color) {
        return this.getTargetSquare().isColor(color);
    }

    public boolean isOriginColor(Color color) {
        return this.getOriginSquare().isColor(color);
    }

    public boolean hasOriginPawnMoved() {
        return this.getOriginCoordinate().getRow() != this.getOriginColor().getRowPawns();
    }

    public int size() {
        return this.path.size();
    }

    public Square get(int index) {
        return this.path.get(index);
    }

    public ArrayList<Square> getSquares() {
        return path;
    }

    public boolean isTargetEmpty() {
        return this.getTargetSquare().isEmpty();
    }
}
