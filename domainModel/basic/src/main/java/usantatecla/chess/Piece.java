package usantatecla.chess;

import usantatecla.utils.Console;

import java.util.List;

public abstract class Piece {
    protected final Color color;
    protected final char letter;

    protected Piece(Color color, char letter) {
        this.color = color;
        this.letter = letter;
    }

    public Color getColor() {
        return color;
    }

    public void write() {
        if (this.color == Color.Black) {
            Message.COLOR_BLACK.write();
        }
        Console.getInstance().write(letter);
        Message.COLOR_RESET.write();
    }

    public boolean isMoveLegal(Path path) {
        return !this.isEatingFriend(path) && this._isMoveLegal(path);
    }

    public boolean hasLegalMove(List<Path> paths) {
        for (Path path: paths) {
            if (isMoveLegal(path)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEatingFriend(Path path) {
        return path.getTargetSquare().isColor(this.color);
    }

    protected abstract boolean _isMoveLegal(Path path);
}
