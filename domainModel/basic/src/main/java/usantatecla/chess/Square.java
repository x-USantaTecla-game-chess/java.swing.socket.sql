package usantatecla.chess;

import usantatecla.utils.Coordinate;

public class Square {
    private final Coordinate coordinate;
    private Piece piece;

    public Square(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void movePieceTo(Square target) {
        target.piece = this.piece;
        this.piece = null;
    }

    public boolean isMoveLegal(Path squaresInBetween) {
        return this.piece.isMoveLegal(squaresInBetween);
    }

    public void write() {
        if (this.piece != null) {
            this.piece.write();
        }
        else {
            Message.SPACE.write();
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isEmpty() {
        return this.piece == null;
    }

    public boolean isColor(Color color) {
        if (this.piece == null) {
            return false;
        }
        return this.piece.getColor() == color;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
}
