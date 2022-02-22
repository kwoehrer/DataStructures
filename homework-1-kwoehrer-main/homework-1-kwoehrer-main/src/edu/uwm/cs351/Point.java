package edu.uwm.cs351;
//Krischan Woehrer, Homework #1, CS 351
/**
 * Immutable location in two dimensional space.
 * 
 * @author Krischan Woehrer
 */
public class Point {
	private final double x;
	private final double y;

	/**
	 * Create a point at the given (x,y) coordinates.
	 * 
	 * @param xCoord x coordinate
	 * @param yCoord y coordinate
	 */
	public Point(double xCoord, double yCoord) {
		this.x = xCoord;
		this.y = yCoord;
	}

	/**
	 * Return the x coordinate of this point.
	 * 
	 * @return x coordinate of this point
	 */
	public double x() {
		return this.x;
	}

	/**
	 * Return the y coordinate of this point.
	 * 
	 * @return y coordinate of this point
	 */
	public double y() {
		return this.y;
	}

	/**
	 * Round this point to the closest AWT point (using integer coordinates).
	 * 
	 * @return rounded point
	 */
	public java.awt.Point asAWT() {
		int xRound = (int) Math.round(this.x());
		int yRound = (int) Math.round(this.y());
		return new java.awt.Point(xRound, yRound);
	}

	/**
	 * Compute the distance (never negative) between two points.
	 * 
	 * @param other another point, must not be null
	 * @return distance between points.
	 */
	public double distance(Point other) {
		// Construct a vector between the points than calculate the magnitude of the
		// vector (AKA distance)
		return new Vector(this, other).magnitude();
	}

	/**
	 * Return string representation of this point in the form "(x,y)".
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override // implementation
	public String toString() {
		return "(" + this.x() + "," + this.y() + ")";
	}
}
