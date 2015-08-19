package com.michaelborgmann.androitis;

public class Board {
	
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 20;
	public static final int PIECE_BLOCKS = 5;
	public static final int BLOCK_SIZE = 15;
	public static final int BOARD_LINE_WIDTH = 6;
	
	int boardPosition;
	
	boolean isFilled = false;
	boolean board[][] = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
	
	Pieces pieces;
	
	int screenWidth, screenHeight;

	public Board(Pieces pieces, int screenWidth, int screenHeight) {
		this.pieces = pieces;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		boardPosition = screenWidth / 2;
		
		initBoard();
	}
	
	private void initBoard() {
		for (int i = 0; i < BOARD_WIDTH; i++)
			for (int j = 0; j < BOARD_HEIGHT; j++)
				board[i][j] = !isFilled;
	}
	
	public void storePiece(int x, int y, int piece, int rotation) {
		for (int i1 = x, i2 = 0; i1 < x + PIECE_BLOCKS; i1++, i2++)
			for (int j1 = y, j2 = 0; j1 < y + PIECE_BLOCKS; j1++, j2++)
				if (pieces.getBlockType(piece, rotation, j2, i2) != 0)
					board[i1][j1] = isFilled;
	}
	
	public boolean isGameOver() {
		for (int i = 0; i < BOARD_WIDTH; i++)
			if (board[i][0] == isFilled) return true;
		return false;
	}
	
	private void deleteLine(int y) {
		for (int j = y; j > 0; j--)
			for (int i = 0; i < BOARD_WIDTH; i++)
				board[i][j] = board[i][j - 1];
	}
	
	public void deletePosibleLines() {
		for (int j = 0; j < BOARD_HEIGHT; j++) {
			int i = 0;
			while (i < BOARD_WIDTH) {
				if (board[i][j] != isFilled) break;
				i++;
			}
			if (i == BOARD_WIDTH) deleteLine(j);
		}
	}
	
	public boolean isFreeBlock(int x, int y) {
		if (board[x][y] == !isFilled) return true;
		else return false;
	}
	
	public int getPixelPositionX(int position) {
		return ((boardPosition - (BLOCK_SIZE * (BOARD_WIDTH / 2))) + (position * BLOCK_SIZE));
	}
	
	public int getPixelPositionY(int position) {
		return ((screenHeight - (BLOCK_SIZE * BOARD_HEIGHT)) + (position * BLOCK_SIZE));
	}
	
	public boolean isPossibleMove(int x, int y, int piece, int rotation) {
		for (int i1 = x, i2 = 0; i1 < x + PIECE_BLOCKS; i1++, i2++)
			for (int j1 = y, j2 = 0; j1 < y + PIECE_BLOCKS; j1++, j2++) {
				
				if (i1 < 0 || i1 > BOARD_WIDTH -1 || j2 > BOARD_HEIGHT -1)
					if (pieces.getBlockType(piece, rotation, j2, i2) != 0)
						return false;
				
				if (j1 >= 0)
					if ((pieces.getBlockType(piece, rotation, j2, i2) !=0) && (!isFreeBlock(i1, j1)))
						return false;
			}
		// no collision
		return true;
	}
	
	public int getScreenHeight() { return screenHeight; }
}
