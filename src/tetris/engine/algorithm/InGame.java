package tetris.engine.algorithm;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import tetris.engine.FileLoader;
import tetris.engine.gui.screen.GameScr;

public class InGame {
	
	private GameScr gameScr;
	
	private Board board;
	private Shape[] shapes = new Shape[7];
	private Shape currentShape, nextShape;
	
	private BufferedImage blocks;
	private final int blockSize = 30;
	
	private boolean gameOver = false;
	
	private int level;
	
	private int score;
	
	public InGame(GameScr gameScr, int level) {
		
		this.gameScr = gameScr;
		this.level = level;
		
		//TODO
		score =0;
		board = new Board(this);
		blocks = FileLoader.loadImage("/tiles.png");
		
		shapes[0] = new Shape(new int[][]{
			{1, 1},
			{1, 1}
		}, blocks.getSubimage(0, 0, blockSize, blockSize), this, 1);
		shapes[1] = new Shape(new int[][]{
			{0, 1, 0},
			{1, 1, 1},
			{0, 0, 0}
		}, blocks.getSubimage(blockSize, 0, blockSize, blockSize), this, 2);
		shapes[2] = new Shape(new int[][]{
			{1, 0, 0},
			{1, 1, 1},
			{0, 0, 0}
		}, blocks.getSubimage(blockSize*2, 0, blockSize, blockSize), this, 3);
		shapes[3] = new Shape(new int[][]{
			{0, 0, 1},
			{1, 1, 1},
			{0, 0, 0}
		}, blocks.getSubimage(blockSize*3, 0, blockSize, blockSize), this, 4);
		shapes[4] = new Shape(new int[][]{
			{1, 1, 0},
			{0, 1, 1},
			{0, 0, 0}
		}, blocks.getSubimage(blockSize*4, 0, blockSize, blockSize), this, 5);
		shapes[5] = new Shape(new int[][]{
			{0, 1, 1},
			{1, 1, 0},
			{0, 0, 0}
		}, blocks.getSubimage(blockSize*5, 0, blockSize, blockSize), this, 6);
		shapes[6] = new Shape(new int[][]{
			{0, 0, 0, 0},
			{1, 1, 1, 1},
			{0, 0, 0, 0},
			{0, 0, 0, 0}
		}, blocks.getSubimage(blockSize*6, 0, blockSize, blockSize), this, 7);
		
		setNextShape();
		setCurrentShape();
	}
	
	public void update() {
		//TODO
		currentShape.update();
		if(currentShape.isCollision()) {
			board.setShapeToBoard(currentShape);
			score += board.checkLine() * (8 + level * 2);		
			checkGameOver();
			if(!gameOver) setCurrentShape();
		}
		keyUpdate();
	}
	
	public void paint(Graphics g) {
		//TODO
		currentShape.paint(g);
		
		for(int row = 0; row < nextShape.getMatrix().length; row ++)
		{
			for(int col = 0; col < nextShape.getMatrix()[0].length; col ++)
			{
				if(nextShape.getMatrix()[row][col] != 0)
				{
					g.drawImage(nextShape.getBlock(), col*30 + 320 + (4 - nextShape.getMatrix()[0].length) * 10, row*30 + 50, null);	
				}
			}		
		}
		
		board.paint(g);
	}
	
	private void keyUpdate() {
		//TODO
		if(gameScr.getGame().getInput().isKeyDown(KeyEvent.VK_UP)) {
			currentShape.rotateShape();
			//System.out.println("roll");
		}
		if(gameScr.getGame().getInput().isKeyDown(KeyEvent.VK_DOWN)) {
			currentShape.speedUp();
		}
		if(gameScr.getGame().getInput().isKeyUp(KeyEvent.VK_DOWN)) {
			currentShape.speedDown();
		}
		if(gameScr.getGame().getInput().isKeyHold(KeyEvent.VK_RIGHT)) {
			currentShape.setDeltaX(1);
		}
		if(gameScr.getGame().getInput().isKeyHold(KeyEvent.VK_LEFT)) {
			currentShape.setDeltaX(-1);
		}
	}
	
	private void checkGameOver() {
		for(int col = 0; col < board.getMatrix()[0].length; col++) {
			if(board.getMatrix()[0][col] != 0) {
				gameOver = true;
			}
		}
	}
	
	private void setCurrentShape() {
		currentShape = nextShape;
		//TODO
		currentShape.setLastTime(System.currentTimeMillis());
		setNextShape();
	}
	private void setNextShape() {
		//TODO
		int index = (int)(Math.random()*shapes.length);
		nextShape = new Shape(shapes[index].getMatrix(), shapes[index].getBlock(), this, shapes[index].getColor());
	}

	public Board getBoard() {
		return board;
	}

	public BufferedImage getBlocks() {
		return blocks;
	}

	public int getBlockSize() {
		return blockSize;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

}
