package usantatecla;

import usantatecla.utils.Console;
import usantatecla.utils.Coordinate;
import usantatecla.utils.Path;

import java.util.ArrayList;

public class Board {
    private final Square[][] squares = new Square[Coordinate.DIMENSION][Coordinate.DIMENSION];
    private Square[] kingsSquares;
    private Path checkPath;

    public Board() {
        this.reset();
    }

    public void reset() {
        this.createSquares();

        for (Color color : Color.values()) {
            Piece[] pieces = Board.getInitialPieces(color);
            Pawn[] pawns = Board.getInitialPawns(color);

            for (int i = 0; i < Coordinate.DIMENSION; i++) {
                this.squares[color.getRowPieces()][i].setPiece(pieces[i]);
                this.squares[color.getRowPawns()][i].setPiece(pawns[i]);
            }
        }

        kingsSquares = new Square[]{
                squares[0][4],
                squares[Coordinate.DIMENSION - 1][4]
        };
    }

    private static Pawn[] getInitialPawns(Color color) {
        Pawn[] pawns = new Pawn[Coordinate.DIMENSION];
        for (int i = 0; i < Coordinate.DIMENSION; i++) {
            pawns[i] = new Pawn(color);
        }
        return pawns;
    }

    private static Piece[] getInitialPieces(Color color) {
        return new Piece[]{
                new Rook(color),
                new Knight(color),
                new Bishop(color),
                new Queen(color),
                new King(color),
                new Bishop(color),
                new Knight(color),
                new Rook(color)
        };
    }

    private void createSquares() {
        for (int j = 0; j < Coordinate.DIMENSION; j++) {
            for (int i = 0; i < Coordinate.DIMENSION; i++) {
                this.squares[i][j] = new Square(new Coordinate(i, j));
            }
        }
    }

    public void movePiece(Coordinate origin, Coordinate target) {
        assert origin != null;
        assert target != null;
        assert this.isMoveLegal(origin, target);

        Square originSquare = this.getSquare(origin);
        Square targetSquare = this.getSquare(target);

        updateKings(originSquare, targetSquare);
        originSquare.movePieceTo(targetSquare);

        if (isCheck(targetSquare.getPiece().getColor().getOpposite())) {
            checkPath = new Path(getSquares(target.getCoordinatesTo(kingsSquares[targetSquare.getPiece().getColor().getOpposite().ordinal()].getCoordinate())));
        }
    }

    private void updateKings(Square originSquare, Square targetSquare) {
        assert originSquare != null;
        assert targetSquare != null;

        if (originSquare == kingsSquares[0] || originSquare == kingsSquares[1]) {
            this.kingsSquares[originSquare.getPiece().getColor().ordinal()] = targetSquare;
        }
    }

    public boolean isMoveLegal(Coordinate origin, Coordinate target) {
        assert origin != null;
        assert target != null;

        Path path = new Path(this.getSquares(origin.getCoordinatesTo(target)));
        return this.getSquare(origin).isMoveLegal(path)
                && this.isNotSelfCheck(path);
    }

    private boolean isNotSelfCheck(Path path) {
        assert path != null;

        Color color = path.getOriginColor();
        Piece pieceToBeTaken = path.getTargetPiece();
        this.movePiece(path.getOriginCoordinate(), path.getTargetCoordinate());
        for (int i = 0; i < Coordinate.DIMENSION; i++) {
            for (int j = 0; j < Coordinate.DIMENSION; j++) {
                Coordinate originOfPath = this.squares[i][j].getCoordinate();
                Coordinate targetOfPath = this.kingsSquares[color.ordinal()].getCoordinate();
                Path pathToKing = new Path(this.getSquares(originOfPath.getCoordinatesTo(targetOfPath)));

                if (!this.squares[i][j].isEmpty() && !this.squares[i][j].isColor(color) && this.squares[i][j].isMoveLegal(pathToKing)) {
                    this.movePiece(path.getTargetCoordinate(), path.getOriginCoordinate());
                    path.getTargetSquare().setPiece(pieceToBeTaken);
                    return false;
                }
            }
        }
        this.movePiece(path.getTargetCoordinate(), path.getOriginCoordinate());
        path.getTargetSquare().setPiece(pieceToBeTaken);
        return true;
    }

    private boolean isCheck(Color color) {
        assert color != null;

        for (int i = 0; i < Coordinate.DIMENSION; i++) {
            for (int j = 0; j < Coordinate.DIMENSION; j++) {
                if (!this.squares[i][j].isEmpty() && !this.squares[i][j].isColor(color)) {
                    Coordinate originOfPath = this.squares[i][j].getCoordinate();
                    Coordinate targetOfPath = this.kingsSquares[color.ordinal()].getCoordinate();
                    Path pathToKing = new Path(this.getSquares(originOfPath.getCoordinatesTo(targetOfPath)));

                    if (this.squares[i][j].isMoveLegal(pathToKing)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckMate(Color color) {
        assert color != null;

        if (!this.isCheck(color)) return false;

        Square kingSquare = this.kingsSquares[color.ordinal()];

        for (Coordinate neighbour : kingSquare.getCoordinate().getNeighbours()) {
            ArrayList<Square> squares = new ArrayList<>();
            squares.add(kingSquare);
            squares.add(this.getSquare(neighbour));
            if (kingSquare.isMoveLegal(new Path(squares)) && this.isNotSelfCheck(new Path(squares))) {
                return false;
            }
        }

        for (int i = 0; i < Coordinate.DIMENSION; i++) {
            for (int j = 0; j < Coordinate.DIMENSION; j++) {
                if (!this.squares[i][j].isEmpty() && this.squares[i][j].isColor(color)) {
                    Coordinate originOfPath = this.squares[i][j].getCoordinate();
                    for (int k = 0; k < checkPath.size() - 1; k++) {
                        Coordinate targetOfPath = checkPath.get(k).getCoordinate();
                        Path path = new Path(this.getSquares(originOfPath.getCoordinatesTo(targetOfPath)));

                        if (this.squares[i][j].isMoveLegal(path) && this.isNotSelfCheck(path)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    boolean isEmpty(Coordinate coordinate) {
        assert coordinate != null;

        return this.getSquare(coordinate).isEmpty();
    }

    public boolean isOpposite(Coordinate coordinate, Color color) {
        assert coordinate != null;
        assert color != null;

        if (this.getSquare(coordinate).isEmpty()) {
            return false;
        }
        return !this.getSquare(coordinate).isColor(color);
    }

    private Square getSquare(Coordinate coordinate) {
        assert coordinate != null;

        return this.squares[coordinate.getRow()][coordinate.getColumn()];
    }

    private ArrayList<Square> getSquares(ArrayList<Coordinate> coordinates) {
        assert coordinates != null && coordinates.size() > 1;

        ArrayList<Square> squares = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            squares.add(getSquare(coordinate));
        }
        return squares;
    }

    public void print() {
        Message.HORIZONTAL_LINE.writeln();
        for (int i = 0; i < Coordinate.DIMENSION; i++) {
            Message.VERTICAL_LINE.write();
            for (int j = 0; j < Coordinate.DIMENSION; j++) {
                this.squares[i][j].write();
                Message.VERTICAL_LINE.write();
            }
            Console.getInstance().writeln();
        }
        Message.HORIZONTAL_LINE.writeln();
        if (this.isCheck(Color.Black)) {
            Message.BLACK_CHECK.writeln();
        } else if (this.isCheck(Color.White)) {
            Message.WHITE_CHECK.writeln();
        }
    }

}
