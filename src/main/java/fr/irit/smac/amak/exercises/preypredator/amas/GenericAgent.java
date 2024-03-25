package fr.irit.smac.amak.exercises.preypredator.amas;

import fr.irit.smac.amak.Agent;
import fr.irit.smac.amak.exercises.preypredator.domain.Land;
import lombok.Getter;

public abstract class GenericAgent extends Agent<PreyPredatorAmas, Land> {

	@Getter
	protected int x, y;
	@Getter
	private int lives;

	protected GenericAgent(PreyPredatorAmas amas, int lives, int x, int y) {
		super(amas);
		this.lives = lives;
		this.x = x;
		this.y = y;
	}

	protected void move(int newX, int newY) {
		if (amas.getEnvironment().move(this, newX, newY)) {
			this.x = newX;
			this.y = newY;
		}
	}

	@Override
	protected final void onAgentCycleEnd() {
		lives--;
		if (lives <= 0) {
			destroy();
		}
	}

	protected void addLives(int livesToAdd) {
		lives+=livesToAdd;
	}

	// Create a new agent with the same type at the same position
	protected void reproduce() {
		try {
			getClass().getConstructor(PreyPredatorAmas.class, int.class, int.class, int.class)
			          .newInstance(amas, lives, x, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void moveRandomly() {
		// Move randomly in one direction
		int newX = x + (int) (Math.random() * 3) - 1;
		int newY = y + (int) (Math.random() * 3) - 1;
		move(newX, newY);
	}
}
