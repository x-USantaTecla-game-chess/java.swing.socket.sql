package usantatecla.chess;

import usantatecla.utils.Console;

enum Message {
    TITLE("--- CHESS ---"),
    HORIZONTAL_LINE("-----------------------------------"),
    VERTICAL_LINE(" | "),
    ENTER_COORDINATE_PIECE("Enter a coordinate to select a piece:"),
    ENTER_COORDINATE_SQUARE("Enter a coordinate to select a square:"),
    WHITE_CHECK("White is in check!"),
    BLACK_CHECK("Black is in check!"),
    WHITE_CHECKMATE("White has been checkmated - Black wins!"),
    BLACK_CHECKMATE("Black has been checkmated - White wins!"),
    RESUME("Do you want to continue"),
    SPACE(" "),
    COLOR_BLACK("\u001B[30m"),
    COLOR_RESET("\u001B[0m");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    void write() {
        Console.getInstance().write(this.message);
    }

    void writeln() {
        Console.getInstance().writeln(this.message);
    }

    @Override
    public String toString() {
        return message;
    }
}
