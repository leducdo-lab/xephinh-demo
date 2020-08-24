package tetris.engine.algorithm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Shape {
	
	private InGame inGame;
	private int[][] matrix;
	private BufferedImage block;
	private int color;
	private int x, y;
	//private int width, height;
	private int minX, minY, maxX, maxY;
	
	private int deltaX;
	
	private int normal = 600, fast = 50;
	private int delay;
	
	private long now, lastTime;
	
	private boolean collision = false;
	
	public Shape(int[][] matrix, BufferedImage block, InGame inGame, int color) {
		this.matrix = matrix;
		this.block = block;
		this.color = color;
		this.inGame = inGame;
		deltaX = 0;
		x = 3; y = -1;
		normal = 600 - (int)(600 * 0.1 * (inGame.getLevel() - 1));
		//System.out.println("level: " + inGame.getLevel() + "normal: " + normal);
		delay = normal;
		now = System.currentTimeMillis();
		lastTime = now;
		//width = 0; height = 0;
		minX = 0; minY = 0; maxX = 0; maxY = 0;
		//TODO
		setSizeShape(matrix);
	}
	
	public void update() {
		//TODO
		now = System.currentTimeMillis();
		boolean flag = true;
		
		if((x + maxX + deltaX < inGame.getBoard().getWidth()) && (x + minX + deltaX >= 0)) {
			for(int row = minY; row < maxY + 1; row++)
			{
				for(int col = minX; col < maxX + 1; col ++)
				{
					if(matrix[row][col] != 0) 
					{
						
						if(inGame.getBoard().getMatrix()[y + row][x + deltaX + col] != 0)
						{
							flag = false;
						}
					}
				}
			}
			if(flag) x += deltaX;
		}
		if(!(y + 1 + maxY > inGame.getBoard().getHeigth() - 1) && !collision)
		{			
			for(int row = minY; row < maxY + 1; row++)
			{
				for(int col = minX; col < maxX + 1; col ++)
				{
					if(matrix[row][col] != 0)
					{
						
						if(inGame.getBoard().getMatrix()[y + 1 + row][x + col] != 0)
						{
							if(now - lastTime > delay / 2) {
								collision = true;
							}
						}
					}
				}
			}
			if(now - lastTime > delay) {
				lastTime = System.currentTimeMillis();
				if(!collision) y++;
			}
		}else{
			if(now - lastTime > delay / 2) {
				collision = true;
			}
		}		
		deltaX = 0;
	}
	
	public void paint(Graphics g) {
		//TODO
		for(int row = 0; row < matrix.length; row++) {
			for(int col = 0; col < matrix[0].length; col++) {
				if(matrix[row][col] != 0) {
					g.drawImage(block, col*inGame.getBlockSize() + x*inGame.getBlockSize(), row*inGame.getBlockSize() + y*inGame.getBlockSize(), null);
				}
			}
		}
	}
	
	private void setSizeShape(int[][] tmp) {
		boolean flag = false;
		for(int col = 0; col < tmp[0].length; col++) {
			for(int row = 0; row < tmp.length; row++) {
				if(tmp[row][col] != 0) {
					if (!flag) {
						minX = col;
						maxX = col;
						flag = true;
						break;
					} else {
						maxX = col;
					}					
				}
			}
		}
		flag = false;
		for(int row = 0; row < tmp.length; row++) {
			for(int col = 0; col < tmp[0].length; col++) {
				if(tmp[row][col] != 0) {
					if (!flag) {
						minY = row;
						maxY = row;
						flag = true;
						break;
					} else {
						maxY = row;
					}
				}
			}
		}
		if(x + maxX > inGame.getBoard().getWidth() - 1) x = inGame.getBoard().getWidth() - 1 - maxX;
		if(x + minX < 0) x = 0;
		if(y + maxY > inGame.getBoard().getHeigth() - 1) y = inGame.getBoard().getHeigth() - 1 - maxY;
		if(y + minY < 0) y = 0;
		//System.out.println(x+" "+y+" "+minX+" "+maxX+" "+minY+" "+maxY);
	}
	
	public void rotateShape() {
		int[][] rotatedShape = transposeMatrix(matrix);
		rotatedShape = reverseRows(rotatedShape);
		//TODO
		int Sx = x, Sy = y;
		setSizeShape(rotatedShape);
		for(int row = maxY; row < maxY + 1; row++)
		{
			for(int col = minX; col < maxX + 1; col++)
			{
				if(rotatedShape[row][col] != 0)
				{
					if((inGame.getBoard().getMatrix()[y + row][x + col] != 0))
					{
						x = Sx; y = Sy;
						setSizeShape(matrix);
						return;
					}
				}
			}
		}
		matrix = rotatedShape;
		setSizeShape(matrix);
	}
	private int[][] transposeMatrix(int[][] tmp) {
		//TODO
		int[][] temp = new int[tmp[0].length][tmp.length];
        for (int i = 0; i < tmp.length; i++)
            for (int j = 0; j < tmp[0].length; j++)
                temp[j][i] = tmp[i][j];
        return temp;
	}
	private int[][] reverseRows(int[][] tmp){
		//TODO
		int middle = tmp.length/2;		
		for(int i = 0; i < middle; i++)
		{
			int[] temp = tmp[i];			
			tmp[i] = tmp[tmp.length - i - 1];
			tmp[tmp.length - i - 1] = temp;
		}		
		return tmp;
	}
	
	public void speedUp() {
		delay = fast;
	}
	
	public void speedDown() {
		delay = normal;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public BufferedImage getBlock() {
		return block;
	}

	public int getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

}