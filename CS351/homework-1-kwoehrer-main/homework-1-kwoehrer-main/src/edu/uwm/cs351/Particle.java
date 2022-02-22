package edu.uwm.cs351;

import java.awt.Color;
import java.awt.Graphics;
// Krischan Woehrer, Homework #1, CS 351
/**
 * Representation of a point mass moving in two dimensional space. A particle
 * has a location (a point), velocity (a vector), a mass (double) and a color.
 * The mass and color never change.
 * 
 * @author Krischan Woehrer
 */
public class Particle {
	private volatile Point position; // must be "volatile" because of animation (not for other fields)
	private Vector velocity;
	private final double mass;
	private final Color color;

	/**
	 * Create a new particle with the given position, velocity, mass and color.
	 * 
	 * @param p position (location) of particle initially, must not be null
	 * @param v velocity of particle initially, must not be null
	 * @param m mass of particle
	 * @param c color of particle, must not be null
	 * @throws NullPointerException when an argument (position, velocity or color) is null
	 */
	public Particle(Point p, Vector v, double m, Color c) {
		if (p == null || v == null || c == null) {
			throw new NullPointerException("A null value was passed as an argument to the Particle constructor.");
		}
		this.position = p;
		this.velocity = v;
		this.mass = m;
		this.color = c;
	}

	/**
	 * Return the position of this particle
	 * 
	 * @return position, never null
	 */
	public Point getPosition() {
		return this.position;
	}

	/**
	 * Return the velocity of this particle.
	 * 
	 * @return velocity, never null
	 */
	public Vector getVelocity() {
		return this.velocity;
	}

	/**
	 * Estimate the location of the particle after one unit of time by letting it
	 * move at a constant velocity for that time from its starting position.
	 * Essentially that simply means we apply the velocity as a vector to the
	 * particles current position.
	 */
	public void move() {
		this.position = this.getVelocity().move(this.getPosition());
	}

	/**
	 * Draw the particle at its current position as a small circle. The area of the
	 * circle is proportional to the mass: the radius of the circle is the square
	 * root of the mass.
	 * 
	 * @param g graphics context (must not be null)
	 * @throws NullPointerException when the Graphics argument is null
	 */
	public void draw(Graphics g) {

		if (g == null) {
			throw new NullPointerException("Cannot draw particle with null Graphics instance.");
		}

		double radius = Math.sqrt(this.mass);
		double upperX = this.getPosition().asAWT().getX() - radius;
		double upperY = this.getPosition().asAWT().getY() - radius;

		g.setColor(this.color);
		g.fillOval((int) upperX, (int) upperY, (int) radius * 2, (int) radius * 2);

	}

	private static final double G = 1;

	/**
	 * Compute the Newtonian gravitational force that this particle exerts on the
	 * other. This force is proportional to the product of the masses and inversely
	 * proportional to the distance between them. The constant of proportionality is
	 * given by {@link G} that is fixed at 1.0 for CS 351 purposes. The direction of
	 * the force is toward this particle.
	 * 
	 * @param other particle to operate gravitation on, must not be null
	 * @return force of gravitation toward this particle
	 * @throws NullPointerException when the other particle argument is null
	 * @throws ArithmeticException  when the particles have the same exact position
	 */
	public Vector gravForceOn(Particle other) {
		if (other == null) {
			throw new NullPointerException("Cannot determine gravity force with a null particle.");
		}

		Vector v21 = new Vector(other.getPosition(), this.getPosition());
		double distance = v21.magnitude();
		Vector u21 = v21.normalize();
		Vector gravForce;

		if (distance != 0) {
			// Scale is equivalent to multiplication
			gravForce = u21.scale((G * this.mass * other.mass) / (distance * distance));
		} else {
			throw new IllegalArgumentException(
					"Particles share the same position values and the distance between them is 0. Distance cannot be 0 when determining gravitational force.");
			// Was unsure what type of Exception to use. IllegalStateException typically
			// applies to the object used to call the method so I choose
			// IllegalArgumentException instead.
		}

		return gravForce;
	}

	/**
	 * Apply a force as an acceleration on the velocity (after dividing by the
	 * mass). This velocity is affected as though one applied the constant
	 * acceleration for one time unit.
	 * 
	 * @param force force applied, must not be null
	 * @throws NullPointerException when the force argument is null
	 */
	public void applyForce(Vector force) {
		if (force == null) {
			throw new NullPointerException("The Vector force cannot be null.");
		}

		this.velocity = this.getVelocity().add(force.scale(1 / this.mass));
	}
}
