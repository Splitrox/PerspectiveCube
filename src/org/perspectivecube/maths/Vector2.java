package org.perspectivecube.maths;

import java.awt.Point;

public class Vector2 {
	
	private double x, y;
	
	public Vector2() {
		x = 0;
		y = 0;
	}
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double length() {
		return (double) Math.sqrt(x * x + y * y);
	}
	
	public double dot(Vector2 vector) {
		return x * vector.getX() + y * vector.getY();
	}
	
	public Vector2 normalize() {
		double length = length();
		x /= length;
		y /= length;
		
		return this;
	}	
	
	public Vector2 rotate(double angle) {
		double radians = (double) Math.toRadians(angle);
		
		return new Vector2((double) (x * Math.cos(radians)) - (double) (y * Math.sin(radians)), (double) (x * Math.sin(radians) + (double) y * Math.cos(radians)));
	}
	
	public Vector2 add(Vector2 vector) {
		return new Vector2(x + vector.getX(), y + vector.getY());
	}
	
	public Vector2 sub(Vector2 vector) {
		return new Vector2(x - vector.getX(), y - vector.getY());
	}
	
	public Vector2 mul(Vector2 vector) {
		return new Vector2(x * vector.getX(), y * vector.getY());
	}
	
	public Vector2 div(Vector2 vector) {
		return new Vector2(x / vector.getX(), y / vector.getY());
	}
	
	public Vector2 add(double value) {
		return new Vector2(x + value, y + value);
	}
	
	public Vector2 sub(double value) {
		return new Vector2(x - value, y - value);
	}
	
	public Vector2 mul(double value) {
		return new Vector2(x * value, y * value);
	}
	
	public Vector2 div(double value) {
		return new Vector2(x / value, y / value);
	}
	
	public String toString() {
		return x + ", " + y;
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

	public Point toPoint() {
		return new Point((int)(x*80),(int)(y*80));
	}
}