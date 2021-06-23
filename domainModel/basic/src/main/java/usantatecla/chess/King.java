package usantatecla.chess;

public class King extends Piece{
    public King(Color color) {
        super(color, 'K');
    }

    @Override
    public boolean _isMoveLegal(Path path) {
        return path.getDistance() == 1;
    }
}
