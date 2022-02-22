package edu.uwm.cs351;

import java.awt.Graphics;

import javax.swing.JPanel;

//Krischan Woehrer, Homework #2, CS 351
/**
 * A simulation of three particles in two dimensional Cartesian space acting on
 * each other using Newtonian gravitation.
 */
public class ParticleSimulation extends JPanel {
	/**
	 * Put this in to keep Eclipse happy ("KEH").
	 */
	private static final long serialVersionUID = 1L;

	private final ParticleSeq particles = new ParticleSeq();

	/**
	 * Create a particle simulation with three particles
	 * 
	 * @param p1 first particle, must not be null
	 * @param p2 second particle, must not be null
	 * @param p3 third particle, must not be null
	 */
	public ParticleSimulation(Particle p1, Particle p2, Particle p3) {
		if (p1 == null || p2 == null || p3 == null) {
			throw new NullPointerException("Cannot simulate with null particles");
		}
		particles.append(p1);
		particles.append(p2);
		particles.append(p3);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		particles.start();
		while (particles.isCurrent()) {
			particles.getCurrent().draw(g);
			particles.advance();
		}
	}

	/**
	 * Accelerate each particle by the gravitational force of each other particle.
	 * Then compute the next position of all particles.
	 */
	public void move() {
		ParticleSeq seq2 = particles.clone();
		ParticleSeq seq3 = particles.clone();

		for (seq2.start(); seq2.isCurrent(); seq2.advance()) {
			Vector force = new Vector();

			for (seq3.start(); seq3.isCurrent(); seq3.advance()) {
				if (seq2.getCurrent() == seq3.getCurrent()) {
					continue;
				}
				force = force.add(seq3.getCurrent().gravForceOn(seq2.getCurrent()));
			}

			seq2.getCurrent().applyForce(force);
		}

		seq2.start();
		while (seq2.isCurrent()) {
			seq2.getCurrent().move();
			seq2.advance();
		}

		repaint();
	}
}
