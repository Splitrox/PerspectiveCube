package org.perspectivecube.game.character.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.perspectivecube.maths.Vector3;

public class KeyboardController implements KeyListener {

	public static Vector3 keyBoard = new Vector3(0, 0, 0);

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 16:
			keyBoard.setY(0.03);
			break;
		case 32:
			keyBoard.setY(-0.03);
			break;
		case 65:
			keyBoard.setX(-0.03);
			break;
		case 68:
			keyBoard.setX(0.03);
			break;
		case 87:
			keyBoard.setZ(-0.03);
			break;
		case 83:
			keyBoard.setZ(0.03);
			break;
		case 27:

			System.exit(0);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 16:
			keyBoard.setY(0);
			break;
		case 32:
			keyBoard.setY(0);
			break;
		case 65:
			keyBoard.setX(0);
			break;
		case 68:
			keyBoard.setX(0);
			break;
		case 87:
			keyBoard.setZ(0);
			break;
		case 83:
			keyBoard.setZ(0);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
