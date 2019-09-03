package org.perspectivecube.maths;

public class Vector3 {
	
	public double x;
	public double y;
	public double z;
	
	public Vector3() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3 set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		return this;
	}
	
	public double length() {
		return (double) Math.sqrt(x * x + y * y + z * z);
	}
	
	public double dot(Vector3 vector) {
		return x * vector.getX() + y * vector.getY() + z * vector.getZ();
	}
	
	public Vector3 normalize() {
		double length = length();
		x /= length;
		y /= length;
		z /= length;
		return this;
	}
	
	public Vector3 cross(Vector3 vector) {
		double newX = y * vector.getZ() - z * vector.getY();
		double newY = z * vector.getX() - x * vector.getZ();
		double newZ = x * vector.getY() - y * vector.getX();
		
		return new Vector3(newX, newY, newZ);
	}
	
	public Vector3 add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;

		return this;
	}

	public Vector3 sub(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;

		return this;
	}

	public Vector3 add(Vector3 vector) {
		this.x+=vector.getX();
		this.y+=vector.getY();
		this.z+=vector.getZ();
		
		return this;
	}

	public Vector3 sub(Vector3 vector) {
		this.x-=vector.getX();
		this.y-=vector.getY();
		this.z-=vector.getZ();
		
		return this;
	}

	public Vector3 mul(double value) {
		this.x*=value;
		this.y*=value;
		this.z*=value;
		
		return this;
	}

	public Vector3 div(double value) {
		this.x/=value;
		this.y/=value;
		this.z/=value;
		
		return this;
	}
	
	public String toString() {
		return x + ", " + y + ", " + z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public Vector3 clone() {
		return new Vector3(x, y, z);
	}
	
	public Vector2 toVector2() {
		double fov = 8;
		
		double x = (this.x) / (fov-this.z+1)*(fov*0.8);
		double y = (this.y) / (fov-this.z+1)*(fov*0.8);

		return new Vector2(x, y);
	}
}