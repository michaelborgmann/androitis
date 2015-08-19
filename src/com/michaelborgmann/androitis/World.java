package com.michaelborgmann.androitis;

public class World {

	Board board;
	Pieces pieces;
	int screenHeight;
	
	int piece, rotation;
	int positionX, positionY;
	
	int nextPiece, nextRotation;
	int nextPositionX, nextPositionY;
	
	public World(Board board, Pieces pieces, int screenHeight) {
		this.board = board;
		this.pieces = pieces;
		this.screenHeight = screenHeight;
		initGame();
	}
	
	public int getRandom(int a, int b) {
		return (int)(Math.random() * ((b - a + 1) + a));
	}

	public void initGame() {
		
		// init first piece
		piece = getRandom(0, 6);
		rotation = getRandom(0, 3);
		positionX = (Board.BOARD_WIDTH / 2) + pieces.getInitialPositionX(piece, rotation);
		positionY = pieces.getInitialPositionY(piece, rotation);
		
		// next piece
		nextPiece = getRandom(0, 6);
		nextRotation = getRandom(0, 3);
		nextPositionX = Board.BOARD_WIDTH + 5;
		nextPositionY = 5;
	}
	
	public void createNewPiece() {
		piece = nextPiece;
		rotation = nextRotation;
		positionX = (Board.BOARD_WIDTH / 2) + pieces.getInitialPositionX(piece, rotation);
		positionY = pieces.getInitialPositionY(piece, rotation);
		
		nextPiece = getRandom(0, 6);
		nextRotation = getRandom(0, 3);
	}	
}
