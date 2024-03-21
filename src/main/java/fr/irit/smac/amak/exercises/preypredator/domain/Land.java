package fr.irit.smac.amak.exercises.preypredator.domain;

import fr.irit.smac.amak.Environment;
import fr.irit.smac.amak.exercises.preypredator.amas.GenericAgent;
import fr.irit.smac.amak.exercises.preypredator.amas.SheepAgent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Land extends Environment {
	public final static int ROOM_WIDTH = 80;
	public final static int ROOM_HEIGHT = 50;
	public static final int MAX_GRASS = 100;
	private final GenericAgent[][] genericAgentsGrid = new GenericAgent[ROOM_WIDTH][ROOM_HEIGHT];
	private final int[][] grass = new int[ROOM_WIDTH][ROOM_HEIGHT];

	public Land() {
		for (int i = 0; i < ROOM_WIDTH; i++) {
			for (int j = 0; j < ROOM_HEIGHT; j++) {
				grass[i][j] = MAX_GRASS;
			}
		}
	}

	public boolean move(GenericAgent genericAgent, int x, int y) {
		if (x < 0 || x >= ROOM_WIDTH || y < 0 || y >= ROOM_HEIGHT)
			return false;
		if (genericAgentsGrid[x][y] != null && genericAgentsGrid[x][y] != genericAgent)
			return false;
		if (genericAgentsGrid[genericAgent.getX()][genericAgent.getY()] == genericAgent)
			genericAgentsGrid[genericAgent.getX()][genericAgent.getY()] = null;
		genericAgentsGrid[x][y] = genericAgent;
		return true;
	}

	public boolean isPositionValid(int x, int y) {
		if (x < 0 || x > ROOM_WIDTH - 1 || y < 0 || y > ROOM_HEIGHT - 1)
			return false;
		return true;
	}

	public Optional<GenericAgent> getAgent(int x, int y) {
		if (!isPositionValid(x, y))
			return Optional.empty();
		return Optional.ofNullable(genericAgentsGrid[x][y]);
	}

	@Override
	public void onCycle() {
		for (int i = 0; i < ROOM_WIDTH; i++) {
			for (int j = 0; j < ROOM_HEIGHT; j++) {
				grass[i][j] = Math.min(grass[i][j] + 1, MAX_GRASS);
			}
		}
	}

	public int getGrass(int x, int y) {
		if (!isPositionValid(x, y))
			return 0;
		return grass[x][y];
	}

	public void eatGrass(int x, int y) {
		if (!isPositionValid(x, y))
			return;
		if (canEatGrass(x, y))
			grass[x][y] = 0;
	}

	public boolean canEatGrass(int x, int y) {
		return grass[x][y] == MAX_GRASS;
	}

	public List<SheepAgent> getSheepAgentsAroundPosition(int x, int y) {
		List<SheepAgent> sheepAgents = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (isPositionValid(x + i, y + j)) {
					getAgent(x + i, y + j).ifPresent(agent -> {
						if (agent instanceof SheepAgent) {
							sheepAgents.add((SheepAgent) agent);
						}
					});
				}
			}
		}
		return sheepAgents;
	}
}
