package tetris.engine.gui.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import tetris.engine.FileLoader;
import tetris.engine.Game;
import tetris.engine.gui.MyButton;

public class MenuScr extends Screen{

	private MyButton newGameButton;
	private MyButton pauseButton;
	private MyButton tutorialButton;
	private MyButton soundButton;
	private BufferedImage playNewGame;
	private BufferedImage pause;
	private BufferedImage sound;
	private BufferedImage tutorial;
	private BufferedImage backGround;
	private Clip music;
	private ArrayList<MyButton> buttons = new ArrayList<MyButton>();

	private boolean isPause = false;
	private int isMute = 0;
	
	public MenuScr(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		backGround = FileLoader.loadImage("/bgr1.png");
		playNewGame = FileLoader.loadImage("");
		pause = FileLoader.loadImage("/pause.png");
		sound = FileLoader.loadImage("");
		tutorial = FileLoader.loadImage("");
		music = FileLoader.LoadSound("/music.wav");
		music.loop(Clip.LOOP_CONTINUOUSLY);
		
		newGameButton = new MyButton(game, playNewGame, 350, 520, 50, 50);
		pauseButton = new MyButton(game,pause, 350, 360, 50, 50);
		soundButton = new MyButton(game, sound, 350, 200, 50, 50);
//		soundButton = new MyButton(game, sound.getSubimage(isMute * 512, 0, 512, 512), 350, 200, 50, 50);
		tutorialButton = new MyButton(game, tutorial, 350, 40 , 50, 50);
		
		buttons.add(newGameButton);
		buttons.add(pauseButton);
		buttons.add(tutorialButton);
		buttons.add(soundButton);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(buttons!=null) {
			for(MyButton button : buttons) {
				button.update();
			}
		}
		if(newGameButton.isMouseDown()) {
			game.start();
		}
		if(pauseButton.isMouseDown()) {
			isPause = !isPause;
		}
		if(tutorialButton.isMouseDown()) {
			
		}
		if(soundButton.isMouseDown()){
			isMute = 1 - isMute;
			soundButton.setImage(sound.getSubimage(isMute * 512, 0,  512, 512));
			if(isMute == 1) {
				music.stop();
			}
			else {
				music.start();
				music.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}
		if(pauseButton.isMouseDown()) {
			isPause = !isPause;
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(backGround, 0, 0, game.getWindow().getCanvas().getWidth(), game.getWindow().getCanvas().getHeight(), null);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(new Color(0, 0, 0, 100));
		
		g.setFont(new Font("Georgia", Font.BOLD, 25));
		g.setColor(Color.WHITE);
		
		g.drawString("Score",  game.getWidth() - 125, 250);
			
		if(buttons != null) {
			for(MyButton button: buttons) {
				button.paint(g);
			}
		}
		if(newGameButton.isMouseDown()) {
				game.start();
		}		
	}

}
