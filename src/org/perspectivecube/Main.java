package org.perspectivecube;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import org.perspectivecube.game.Screen;
import org.perspectivecube.game.character.Character;
import org.perspectivecube.game.character.controller.*;
import org.perspectivecube.game.models.*;
import org.perspectivecube.maths.Vector3;

public class Main extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -4718475435565604864L;

	public static double SCALE = 1;
	public static int WIDTH = (int) Math.floor(1200 / SCALE);
	public static int HEIGHT = WIDTH * 3 / 4;

	private static final BufferedImage screen_image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private static final int[] pixels = ((DataBufferInt) screen_image.getRaster().getDataBuffer()).getData();

	public static double FRAME_RATES = 60;

	private static Main instance;

	public Graphics g;
	public JFrame jframe;

	public int currentFPS = 0;

	private boolean isRunning;

	public Screen screen;

	public Cube cube;

	private double lastFpsCheck = 0, lastTickCheck = 0;
	private int totalFrames = 0;

	public Character character;

	public static Character getCharacter() {
		return instance.character;
	}

	public Main() {
		instance = this;

		screen = new Screen(WIDTH, HEIGHT);

		cube = new Cube(new Vector3(),3);

		FRAME_RATES = getRefreshRate();
		FRAME_RATES = FRAME_RATES > 120 ? 120 : FRAME_RATES;

		character = new Character();

		jframe = new JFrame("Perspective 3D - Calculando FPS");

		jframe.setVisible(true);
		jframe.setResizable(false);
		jframe.setSize((int) Math.floor(WIDTH * SCALE), (int) Math.floor(HEIGHT * SCALE));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);

		addKeyListener(new KeyboardController());

		MouseController mc = new MouseController();

		addMouseListener(mc);
		addMouseMotionListener(mc);

		jframe.add(this);

		g = jframe.getGraphics();

		start();

	}

	public void start() {
		if (isRunning)
			return;

		isRunning = true;

		new Thread(this).start();
	}

	private void tick() {
		character.force.add(KeyboardController.keyBoard.clone()
				.mul(0.2 * (FRAME_RATES / 120) + ((FRAME_RATES / 120) - 1) * 0.6));
		character.run();

		render();
	}

	public void stop() {
		isRunning = false;
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		screen.render();

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		g.drawImage(screen_image, 0, 0, (int) Math.floor(screen_image.getWidth() * SCALE),
				(int) Math.floor(screen_image.getHeight() * SCALE), null);

		g.dispose();
		bs.show();

	}

	public void run() {
		while (isRunning) {

			totalFrames++;

			if (System.nanoTime() > lastTickCheck + 1000000000 / FRAME_RATES) {
				lastTickCheck = System.nanoTime();
				
				tick();
			}

			if (System.nanoTime() > lastFpsCheck + 1000000000) {
				lastFpsCheck = System.nanoTime();
				currentFPS = totalFrames;
				totalFrames = 0;
				
				jframe.setTitle("Perspective 3D - " + currentFPS + " FPS");
			}
			totalFrames++;

			/*try {
				Thread.sleep(1);
			} catch (Exception e) {}*/
			
		}
		dispose();
	}

	public void dispose() {
		System.exit(0);
	}

	public static Main getInstance() {
		return instance;
	}

	private int getRefreshRate() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode()
				.getRefreshRate();
	}

	public static void main(String args[]) {
		new Main();
	}
}
