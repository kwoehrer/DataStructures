package edu.uwm.cs351;
//Krischan Woehrer, Homework #1, CS 351
/**
 * An immutable class representing a two dimensional vector in Cartesian space.
 * 
 * @author Krischan Woehrer
 */
public class Vector {
	private final double dx;
	private final double dy;

	/**
	 * Construct an empty vector.
	 */
	public Vector() {
		this(0.0,0.0);
	}

	/**
	 * Construct a vector with given delta (change of position)
	 * 
	 * @param deltaX change in x coordinate
	 * @param deltaY change in y coordinate
	 */
	public Vector(double deltaX, double deltaY) {
		this.dx = deltaX;
		this.dy = deltaY;
	}

	/**
	 * Create a vector from p1 to p2.
	 * 
	 * @param p1
	 * @param p2
	 */
	public Vector(Point p1, Point p2) {
		this.dx = p2.x() - p1.x();
		this.dy = p2.y() - p1.y();
	}

	/**
	 * Return the directed movement of the vector in the x direction.
	 * 
	 * @return The vectors movement in the x direction.
	 */
	public double dx() {
		return this.dx;
	}

	/**
	 * Return the directed movement of the vector in the y direction.
	 * 
	 * @return The vectors movement in the y direction.
	 */
	public double dy() {
		return this.dy;
	}

	/**
	 * Return the new point after applying this vector to the argument.
	 * 
	 * @param p old position
	 * @return new position
	 */
	public Point move(Point p) {
		double newX = p.x() + this.dx();
		double newY = p.y() + this.dy();
		return new Point(newX, newY);
	}

	/**
	 * Compute the magnitude of this vector. How far does it take a point from its
	 * origin?
	 * 
	 * @return magnitude
	 */
	public double magnitude() {
		double magn = Math.sqrt((this.dx() * this.dx()) + (this.dy() * this.dy()));
		return magn;
	}

	/**
	 * Return a new vector that is the sum of this vector and the parameter.
	 * 
	 * @param other another vector (must not be null)
	 * @return new vector that is sum of this and other vectors.
	 */
	public Vector add(Vector other) {
		// I did not throw a NullPointerException here as I figured it made more sense
		// to simply return our current vector instead.
		if (other == null) {
			return this;
		}
		double newDx = this.dx() + other.dx();
		double newDy = this.dy() + other.dy();

		return new Vector(newDx, newDy);
	}

	/**
	 * Compute a new vector that scales this vector by the given amount. The new
	 * vector points in the same direction (unless scale is zero) but has magnitude
	 * scaled by the given amount. If the scale is negative, the new vector points
	 * in the <em>opposite</em> direction.
	 * 
	 * @param s scale amount
	 * @return new vector that scales this vector
	 */
	public Vector scale(double s) {
		double newDx = this.dx() * s;
		double newDy = this.dy() * s;
		return new Vector(newDx, newDy);
	}

	/**
	 * Return a unit vector (one with magnitude 1.0) in the same direction as this
	 * vector. This operation is not defined on the zero vector.
	 * 
	 * @return new vector with unit magnitude in the same direction as this.
	 */
	public Vector normalize() {
		// Catch zero vectors and return null(undefined)
		if (this.dx() == 0 && this.dy() == 0) {
			return null;
		}

		double magn = this.magnitude();
		double normDx = this.dx() / magn;
		double normDy = this.dy() / magn;

		return new Vector(normDx, normDy);
	}

	/**
	 * Return string representation of this vector in the form "<x,y>".
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<" + this.dx() + "," + this.dy() + ">";
	}
}
