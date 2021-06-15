package usantatecla;

import usantatecla.utils.Path;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color, 'R');
    }

    @Override
    public boolean _isMoveLegal(Path path) {
        return (path.getHorizontalDistance() == 0 ^ path.getVerticalDistance() == 0) // Telecom - XOR
                && path.isMiddleEmpty();
    }
}
