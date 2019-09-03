package org.perspectivecube.game.models;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map.Entry;

import org.perspectivecube.Main;
import org.perspectivecube.game.Screen;
import org.perspectivecube.maths.Quaternion;
import org.perspectivecube.maths.Vector3;
import org.perspectivecube.game.character.Character;

public class Cube {

	private HashMap<Vector3, Color> renderPoints = new HashMap<>();

	private Vector3 location;

	private double directionAngle = 0;

	private Vector3 rotation = new Vector3();
	private Vector3 rotationAxis = new Vector3();

	private Quaternion quaternion = new Quaternion();
	private Quaternion localQuaternion;

	private String direction = "NONE";

	private double size = 0;
	
	public Cube(Vector3 location, double size) {
		this.location = location;
		this.size = size;
		
		changeDirection(Math.toRadians(0), 1);

		localQuaternion = new Quaternion(rotation);


		for (double z = -size / 2 + 0.01; z <= size / 2 - 0.01; z += 0.05) {
			for (double x = -size / 2 + 0.01; x <= size / 2 - 0.01; x += 0.05) {
				renderPoints.put(location.clone().add(x, -size / 2, z), Color.lightGray);
			}
		}

		for (double z = -size / 2 + 0.01; z <= size / 2 - 0.01; z += 0.05) {
			for (double x = -size / 2 + 0.01; x <= size / 2 - 0.01; x += 0.05) {
				renderPoints.put(location.clone().add(x, size / 2, z), Color.blue);
			}
		}

		for (double z = -size / 2 + 0.01; z <= size / 2 - 0.01; z += 0.05) {
			for (double y = -size / 2 + 0.01; y <= size / 2 - 0.01; y += 0.05) {
				renderPoints.put(location.clone().add(-size / 2, y, z), Color.green);
			}
		}

		for (double z = -size / 2 + 0.01; z <= size / 2 - 0.01; z += 0.05) {
			for (double y = -size / 2 + 0.01; y <= size / 2 - 0.01; y += 0.05) {
				renderPoints.put(location.clone().add(size / 2, y, z), Color.CYAN);
			}
		}

		for (double x = -size / 2 + 0.01; x <= size / 2 - 0.01; x += 0.05) {
			for (double y = -size / 2 + 0.01; y <= size / 2 - 0.01; y += 0.05) {
				renderPoints.put(location.clone().add(x, y, size / 2), Color.magenta);
			}
		}

		for (double x = -size / 2 + 0.01; x <= size / 2 - 0.01; x += 0.05) {
			for (double y = -size / 2 + 0.01; y <= size / 2 - 0.01; y += 0.05) {
				renderPoints.put(location.clone().add(x, y, -size / 2), Color.yellow);
			}
		}
	}

	public void render() {
		double W = Main.WIDTH;
		double H = Main.HEIGHT;

		double HALF_W = W / 2;
		double HALF_H = H / 2;

		Screen screen = Main.getInstance().screen;

		Character character = Main.getCharacter();

		double xCam = character.location.getX();
		double yCam = character.location.getY();
		double zCam = character.location.getZ();
		
		rotateY();
		
		for (Entry<Vector3, Color> e : renderPoints.entrySet()) {
			Vector3 l3d = e.getKey();
			
			Vector3 ll = localQuaternion.rotateVector(l3d);
			ll.add(location);
			
			ll.add(xCam, yCam, zCam);
			
			if (ll.getZ() >= 8)
				continue;

			Point l = ll.toVector2().toPoint();

			double x = l.getX();
			double y = l.getY();

			y += HALF_H;
			x += HALF_W;

			int newPos = (int) (x + y * W);

			if (x < 0 || x >= Main.WIDTH || y < 0 || y >= Main.HEIGHT)
				continue;

			if (newPos < 0)
				continue;
			if (newPos >= screen.pixels.length)
				continue;

			Main.getInstance().screen.pixels[newPos] = e.getValue().getRGB();
		}
	}
	
	public void rotateY() {
		if (!direction.equalsIgnoreCase("Y")) {
			changeDirection(Math.toRadians(90), 1);
			direction = "Y";
		}
		localQuaternion = quaternion.multiply(localQuaternion);
	}

	public void rotateX() {
		if (!direction.equalsIgnoreCase("X")) {
			changeDirection(Math.toRadians(0), 1);
			direction = "X";
		}
		localQuaternion = quaternion.multiply(localQuaternion);
	}

	public void changeDirection(double d, double direction) {
		directionAngle += d;

		double x = Math.cos(directionAngle);
		double y = Math.sin(directionAngle);

		rotationAxis.set(direction * x, direction * y, 0);

		quaternion.set(rotationAxis, Math.toRadians(2));
	}
	
	public double getSize() {
		return size;
	}

}
