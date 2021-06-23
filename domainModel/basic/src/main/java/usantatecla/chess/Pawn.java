package usantatecla.chess;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color, 'P');
    }

    @Override
    public boolean _isMoveLegal(Path path) {

        int sign = color.getForward();

        int distance = sign;
        if (this.isAttacking(path, distance)) {
            return true;
        }

        if (!path.getTargetSquare().isEmpty()) {
            return false;
        }

        if (!path.hasOriginPawnMoved()) {
            distance *= 2;
            if (!path.isMiddleEmpty()) {
                return false;
            }
        }

        if (path.getOriginCoordinate().horizontalDistance(path.getTargetCoordinate()) != 0) {
            return false;
        }

        if (this.color == Color.Black) {
            return  path.getOriginCoordinate().verticalDistance(path.getTargetCoordinate()) >= distance
                    && path.getOriginCoordinate().verticalDistance(path.getTargetCoordinate()) < 0;
        }
        return  path.getOriginCoordinate().absoluteVerticalDistance(path.getTargetCoordinate()) <= distance
                && path.getOriginCoordinate().verticalDistance(path.getTargetCoordinate()) > 0;
    }

    private boolean isAttacking(Path path, int distance) {
        return !path.isTargetEmpty()
                && !path.isTargetColor(this.color)
                && path.getOriginCoordinate().horizontalDistance(path.getTargetCoordinate()) == 1
                && path.getOriginCoordinate().verticalDistance(path.getTargetCoordinate()) == distance;
    }
}
