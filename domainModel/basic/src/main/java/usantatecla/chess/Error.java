package usantatecla.chess;

import usantatecla.utils.Console;

enum Error {

	NOT_EMPTY("The square is empty"),
	IS_ILLEGAL("This move is illegal"),
	PIECE_NOT_OWN("That piece is not yours"),
	SQUARE_NOT_EMPTY("That square already has a piece"),
	NULL;

	private String message;

	Error(){
	}

	Error(String message){
		this.message = message;
	}

	void writeln() {
		if (!this.isNull()) {
			Console.getInstance().writeln(this.message);
		}
	}

	public boolean isNull() {
		return this == Error.NULL;
	}

}
