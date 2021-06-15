package usantatecla;

import usantatecla.utils.Path;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color, 'Q');
    }

    @Override
    public boolean _isMoveLegal(Path path) {
        return new Bishop(this.color).isMoveLegal(path)
                || new Rook(this.color).isMoveLegal(path);
    }
}
