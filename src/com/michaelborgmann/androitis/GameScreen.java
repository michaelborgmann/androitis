package com.michaelborgmann.androitis;

import java.util.List;

import android.graphics.Color;

import com.michaelborgmann.gameworks.Game;
import com.michaelborgmann.gameworks.Graphics;
import com.michaelborgmann.gameworks.Input.TouchEvent;
import com.michaelborgmann.gameworks.Screen;

public class GameScreen extends Screen {
	
	static final int OFFSET = 100;
	
	static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;
    float tickTime = 0;
    static float tick = TICK_INITIAL;
	
	Pieces pieces = new Pieces();
	Board board = new Board(pieces, game.getGraphics().getWidth(), game.getGraphics().getHeight());
	World world = new World(board, pieces, game.getGraphics().getHeight());

	public GameScreen(Game game) { super(game); }
	public void update(float deltaTime) {
		
		tickTime += deltaTime;
		while (tickTime > tick) {
			tickTime -= tick;
			if (board.isPossibleMove(world.positionX, world.positionY + 1, world.piece, world.rotation))
				world.positionY++;
			else {
				board.storePiece(world.positionX, world.positionY, world.piece, world.rotation);
				board.deletePosibleLines();
				if (board.isGameOver()) break;
				world.createNewPiece();
			}
		}
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int length = touchEvents.size();
		for(int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP) {
				if(event.x > 256 && event.y > 416 ) {
					game.setScreen(new MainMenuScreen(game));
					if(Settings.soundsystem) Assets.click.play(1);
					return;
				}
			}
		}
	}
	
	public void render(float deltaTime) {
		Graphics graphics = game.getGraphics();      
		graphics.drawPixmap(Assets.background, 0, 0);
		graphics.drawPixmap(Assets.buttons, 256, 416, 0, 128, 64, 64);
		drawScene();
	}
	
	public void pause() {}
	public void resume() {}
	public void remove() {}
	
	private void drawPiece(int x, int y, int piece, int rotation) {
		
		Graphics graphics = game.getGraphics();
		
		int xpos = board.getPixelPositionX(x);
		int ypos = board.getPixelPositionY(y);
		
		for (int i = 0; i < Board.PIECE_BLOCKS; i++) 
			for (int j = 0; j < Board.PIECE_BLOCKS; j++) {
				if (pieces.getBlockType(piece, rotation, j, i) != 0)
					graphics.drawRect(
							xpos + i * Board.BLOCK_SIZE,
							ypos + j * Board.BLOCK_SIZE - OFFSET,
							Board.BLOCK_SIZE - 1,
							Board.BLOCK_SIZE - 1,
							Color.BLUE);
			}
	}
	
	private void drawBoard() {
		
		Graphics graphics = game.getGraphics();
		
		int boardLeft = (graphics.getWidth() / 2) - (Board.BLOCK_SIZE * (Board.BOARD_WIDTH / 2));
		int boardRight = (graphics.getWidth() / 2) + (Board.BLOCK_SIZE * (Board.BOARD_WIDTH / 2));
		int boardBottom = (Board.BLOCK_SIZE * Board.BOARD_HEIGHT);
		int boardTop = graphics.getHeight() - boardBottom;
		
		// playfield
/*		graphics.drawRect(
				boardLeft, boardTop,
				boardRight - boardLeft, boardBottom,
				Color.WHITE);
*/		
		graphics.drawRect(
				boardLeft - Board.BOARD_LINE_WIDTH, boardTop - OFFSET,
				Board.BOARD_LINE_WIDTH, boardBottom,
				Color.RED);
		
		graphics.drawRect(
				boardRight, boardTop - OFFSET,
				Board.BOARD_LINE_WIDTH, boardBottom,
				Color.RED);
		
		boardLeft++;
		for (int i = 0; i < Board.BOARD_WIDTH; i++) {
			for (int j = 0; j < Board.BOARD_HEIGHT; j++)
				if (!board.isFreeBlock(i, j))
					graphics.drawRect(
							boardLeft + i * Board.BLOCK_SIZE,
							boardTop + j * Board.BLOCK_SIZE - OFFSET,
							Board.BLOCK_SIZE - 1,
							Board.BLOCK_SIZE - 1,
							Color.GREEN);
		}
	}
	
	public void drawScene() {
		drawBoard();
		drawPiece(world.positionX, world.positionY, world.piece, world.rotation);
		drawPiece(world.nextPositionX, world.nextPositionY, world.nextPiece, world.nextRotation);
	}
	
	
}