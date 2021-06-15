package usantatecla;

import usantatecla.utils.Path;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color, 'N');
    }

    @Override
    public boolean _isMoveLegal(Path path) {
        return (path.getHorizontalDistance() == 1 && path.getVerticalDistance() == 2)
                || (path.getHorizontalDistance() == 2 && path.getVerticalDistance() == 1);
    }
}
