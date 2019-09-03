package org.perspectivecube.game.character;

import org.perspectivecube.maths.Vector3;

public class Character {

	public Vector3 force;
	public Vector3 location;
	
	public double pitch = 0;
	public double yaw = 0;
	
	public Character() {
		force = new Vector3(0, 0, 0);
		location = new Vector3(0, 0, 0);
	}
	
	public void run() {
		force = force.mul(0.96);
		
		location.add(force.getX(),force.getY(),force.getZ());
	}

	public double getSensibility() {
		return 0.2;
	}
	
}
