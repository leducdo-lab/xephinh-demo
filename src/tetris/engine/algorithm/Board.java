package tetris.engine.algorithm;

import java.awt.Graphics;

public class Board {
	
	private InGame inGame;
	
	private final int width = 10, heigth = 20;
	private int[][] matrix;
	
	public Board(InGame inGame) {
		this.inGame = inGame;
		matrix = new int[heigth][width];
	}	
	
	public void update() {
		//TODO
	}
	
	public void paint(Graphics g) {
		//TODO
		for(int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				if(matrix[row][col]!=0 ) {
					g.drawImage(inGame.getBlocks().getSubimage((matrix[row][col]- 1)*inGame.getBlockSize() , 
							0, inGame.getBlockSize(), inGame.getBlockSize()), 
							col*inGame.getBlockSize(), row*inGame.getBlockSize(), null);
				}
			}
		}
	}
	
	public void setShapeToBoard(Shape shape) {
		//TODO
		for(int row = 0; row < shape.getMatrix().length; row++) {
			for(int col = 0; col < shape.getMatrix()[0].length; col++) {
				if(shape.getMatrix()[row][col]!=0 ) {
					matrix[shape.getY() + row][shape.getX() + col] = shape.getColor();
				}
			}
		}
	}
	
	public int checkLine() {
		//TODO
		int score = 0;
		int currentLine = matrix.length - 1;
		for(int row = matrix.length - 1; row>0; row--) {
			int count = 0 ;
			for( int col = 0; col< matrix[0].length; col ++) {
				if(matrix[row][col] != 0) count++;
				matrix[currentLine][col]= matrix[row][col];
			
			}
			if (count < matrix[0].length) {
				currentLine --;
			}
			else {
				score +=1;
			}
		}
		return score;
	}

	public int[][] getMatrix() {
		return matrix;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeigth() {
		return heigth;
	}

}
