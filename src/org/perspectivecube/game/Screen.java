package org.perspectivecube.game;

import java.awt.Color;
import java.util.Arrays;

import org.perspectivecube.Main;

public class Screen {

	private int WIDTH = 0, HEIGHT = 0;

	public int[] pixels;

	public Screen(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;

		pixels = new int[WIDTH * HEIGHT];
	}
	
	public void render() {
		Arrays.fill(pixels, Color.black.getRGB());

		Main.getInstance().cube.render();
	}

	public int getWIDTH() {
		return WIDTH;
	}
	
	public int getHEIGHT() {
		return HEIGHT;
	}

}
