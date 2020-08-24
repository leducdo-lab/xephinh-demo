package tetris.engine.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import tetris.engine.Game;
import tetris.engine.gui.screen.GameScr;
import tetris.engine.gui.screen.MenuScr;

public class Window {
	
	public static enum Screen {
		Menu,
		Game
	}
	private Screen screen = Screen.Menu;
	
	private JFrame frame;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	//private Input input;
	
	private MenuScr menuScr;
	private GameScr gameScr;

	public Window(Game game) {
		canvas = new Canvas();
		Dimension s = new Dimension((int)(game.getWidth() * game.getScale()) , (int)(game.getHeight() * game.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		
		frame = new JFrame(game.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		//input = game.getInput();
		
		menuScr = new MenuScr(game);
		gameScr = new GameScr(game);
	}
	
	public void update() {
		//TODO
		gameScr.update();
	}
	
	public void paint() {
		gameScr.paint(g);
		bs.show();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

}
