package org.perspectivecube.game.character.controller;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import org.perspectivecube.Main;

public class MouseController implements MouseMotionListener, MouseListener {

	private boolean started = false;

	private int ignoreMouseMove = 0, update = 0;
	private boolean ignore = false;

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	private double lastX = 0;
	private double lastY = 0;
	private int lastdiffX = 0;
	private int lastdiffY = 0;

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!started)
			return;

		if (ignoreMouseMove <= 0) {
			double sen = Main.getCharacter().getSensibility();

			int diffX = (int) ((e.getX() - lastX) * sen);
			int diffY = (int) ((lastY - e.getY()) * sen);

			if (ignore) {
				ignore = false;
				diffX = lastdiffX;
				diffY = lastdiffY;
			}

			Main.getCharacter().pitch = normalizePitch(Main.getCharacter().pitch + diffY);
			Main.getCharacter().yaw = normalizeYaw(Main.getCharacter().yaw + diffX);
			
			System.out.println("YAW: " + Main.getCharacter().yaw + ", PITCH: " + Main.getCharacter().pitch);
			
			lastX = e.getX();
			lastY = e.getY();

			lastdiffX = diffX;
			lastdiffY = diffY;

			ignoreMouseMove = 5;
			return;
		}
		if (update <= 0) {
			try {
				new Robot().mouseMove(Main.getInstance().jframe.getX() + Main.WIDTH / 2,
						Main.getInstance().jframe.getY() + Main.HEIGHT / 2);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			
			ignore = true;
			update = 10;
			return;
		}

		update--;
		ignoreMouseMove--;

	}
	

    public double normalizePitch(double pitch) {
        return pitch > 90 ? 90 : (pitch < -90 ? -90 : pitch);
    }

    public double normalizeYaw(double yaw) {
        yaw = yaw  % 360;
        if (yaw < 0) yaw += 360.0;
        return yaw;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!started) {
			Main.getInstance().jframe.setCursor(Main.getInstance().jframe.getToolkit()
					.createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null));
		}
		started = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
