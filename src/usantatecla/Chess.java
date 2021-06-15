package usantatecla;

import usantatecla.utils.YesNoDialog;

public class Chess {
    private final Board board;
    private final Turn turn;

    public Chess() {
        this.board = new Board();
        this.turn = new Turn(this.board);
    }

    private void play() {
        do {
            this.playGame();
        } while (this.isResumedGame());
    }

    private void playGame() {
        Message.TITLE.writeln();
        this.board.print();
        do {
            this.turn.play();
            this.board.print();
        } while (!this.isCheckMate());
        this.turn.writeWinner();
    }

    private boolean isCheckMate() {
        return this.board.isCheckMate(this.turn.getActiveColor());
    }

    private boolean isResumedGame() {
        YesNoDialog yesNoDialog = new YesNoDialog();
        yesNoDialog.read(Message.RESUME.toString());
        if (yesNoDialog.isAffirmative()) {
            this.board.reset();
            this.turn.reset();
        }
        return yesNoDialog.isAffirmative();
    }

    public static void main(String[] args) {
        new Chess().play();
    }
}
