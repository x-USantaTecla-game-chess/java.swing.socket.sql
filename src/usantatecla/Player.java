package usantatecla;

import usantatecla.utils.Coordinate;

public class Player {
    private final Color color;
    private final Board board;

    Player(Color color, Board board) {
        this.color = color;
        this.board = board;
    }

    public void play() {
        Coordinate origin, target;
        Error error;
        do {
            origin = this.getCoordinate(Message.ENTER_COORDINATE_PIECE);
            error = this.getMoveError(origin);
        } while (!error.isNull());
        do {
            target = this.getCoordinate(Message.ENTER_COORDINATE_SQUARE);
            error = this.getMoveError(origin, target);
        } while (!error.isNull());

        this.board.movePiece(origin, target);
    }

    Coordinate getCoordinate(Message message) {
        assert message != null;

        Coordinate coordinate = new Coordinate();
        coordinate.read(message.toString());
        return coordinate;
    }

    private Error getMoveError(Coordinate origin) {
        assert origin != null;

        Error error = Error.NULL;
        if (this.board.isEmpty(origin)) {
            error = Error.NOT_EMPTY;
        } else if (this.board.isOpposite(origin, this.color)) {
            error = Error.PIECE_NOT_OWN;
        }
        error.writeln();
        return error;
    }

    private Error getMoveError(Coordinate origin, Coordinate target) {
        assert origin != null;
        assert target != null;

        Error error = Error.NULL;
        if (!this.board.isMoveLegal(origin, target)) {
            error = Error.IS_ILLEGAL;
        }
        error.writeln();
        return error;
    }

    public Color getColor() {
        return this.color;
    }
}
