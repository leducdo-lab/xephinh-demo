package tetris.engine.gui.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import tetris.engine.FileLoader;
import tetris.engine.Game;
import tetris.engine.algorithm.InGame;
import tetris.engine.gui.MyButton;

public class GameScr extends Screen{
	
	private BufferedImage backGround;
	private BufferedImage gameOver;
	private BufferedImage backToMenu;
	private BufferedImage pause;
	private BufferedImage speaker;
	private InGame inGame;
	
	private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
	private MyButton btnBackToMenuButton;
	private MyButton btnSpeakButton;
	private MyButton btnPauseButton;
	
	private boolean isPause = false;
	private int isMute = 0;
	
	private int level = 1;

	public GameScr(Game game) {
		super(game);
		inGame = new InGame(this, level);
		backGround = FileLoader.loadImage("/backGround.png");
		gameOver = FileLoader.loadImage("/gameover.png");
		backToMenu = FileLoader.loadImage("/backToMenu.png");
		pause = FileLoader.loadImage("/pause.png");
		speaker = FileLoader.loadImage("/speaker.png");
		
		btnBackToMenuButton = new MyButton(game, backToMenu, 350, 520, 50, 50);
		btnPauseButton = new MyButton(game,pause, 350, 360, 50, 50);
		btnSpeakButton = new MyButton(game, speaker.getSubimage(isMute * 512, 0, 512, 512), 350, 440, 50, 50);
		
		buttons.add(btnBackToMenuButton);
		buttons.add(btnPauseButton);
		buttons.add(btnSpeakButton);
	}

	@Override
	public void update() {
		if(!inGame.isGameOver() && !isPause) {
			inGame.update();
		}
		if(buttons!=null) {
			for(MyButton button : buttons) {
				button.update();
			}
		}
		if(btnSpeakButton.isMouseDown()){
			isMute = 1 - isMute;
			btnSpeakButton.setImage(speaker.getSubimage(isMute * 512, 0,  512, 512));
		}
		if(btnPauseButton.isMouseDown()) {
			isPause = !isPause;
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(backGround, 0, 0, game.getWindow().getCanvas().getWidth(), game.getWindow().getCanvas().getHeight(), null);		
		
		//TODO anything paint
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(new Color(0, 0, 0, 100));
		for(int i =0; i<=inGame.getBoard().getHeigth(); i++) {
			g2d.drawLine(0,  i*inGame.getBlockSize(), inGame.getBoard().getWidth()*inGame.getBlockSize(), i*inGame.getBlockSize());
		}
		for(int j = 0; j <= inGame.getBoard().getWidth(); j++) {
			g2d.drawLine(j*inGame.getBlockSize(), 0, j*inGame.getBlockSize(), inGame.getBoard().getHeigth()*inGame.getBlockSize());
		}
		
		g.setFont(new Font("Georgia", Font.BOLD, 25));
		g.setColor(Color.WHITE);
		g.drawString("Level  " + level, game.getWidth() - 125, 200);
		
		g.drawString("Score",  game.getWidth() - 125, 250);
		g.drawString(inGame.getScore() + "", game.getWidth() - 125, 280);
		
		inGame.paint(g);
		
		if(isPause) {
			
		}
		
		if(buttons != null) {
			for(MyButton button: buttons) {
				button.paint(g);
			}
		}
		if(inGame.isGameOver()) {
			g.drawImage(gameOver,  20,  150,  267,  200,  null);
		}
		
	
	}
	public void setNewGame(int level) {
		this.level = level;
		inGame = new InGame(this, level);
	}

}