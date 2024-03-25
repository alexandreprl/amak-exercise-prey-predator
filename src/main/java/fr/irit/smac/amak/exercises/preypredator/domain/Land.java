package fr.irit.smac.amak.exercises.preypredator.domain;

import fr.irit.smac.amak.Environment;
import fr.irit.smac.amak.exercises.preypredator.amas.GenericAgent;
import fr.irit.smac.amak.exercises.preypredator.amas.SheepAgent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Land extends Environment {
	@Getter
	private final SimulationParameters simulationParameters;
	private final GenericAgent[][] genericAgentsGrid;
	private final int[][] grass;

	public Land(SimulationParameters simulationParameters) {
		this.simulationParameters = simulationParameters;
		this.genericAgentsGrid = new GenericAgent[simulationParameters.getLandWidth()][simulationParameters.getLandHeight()];
		this.grass = new int[simulationParameters.getLandWidth()][simulationParameters.getLandHeight()];
		for (int i = 0; i < simulationParameters.getLandWidth(); i++) {
			for (int j = 0; j < simulationParameters.getLandHeight(); j++) {
				grass[i][j] = simulationParameters.getMaxGrassLife();
			}
		}
	}

	public boolean move(GenericAgent genericAgent, int x, int y) {
		if (x < 0 || x >= simulationParameters.getLandWidth() || y < 0 || y >= simulationParameters.getLandHeight())
			return false;
		if (genericAgentsGrid[x][y] != null && genericAgentsGrid[x][y] != genericAgent)
			return false;
		if (genericAgentsGrid[genericAgent.getX()][genericAgent.getY()] == genericAgent)
			genericAgentsGrid[genericAgent.getX()][genericAgent.getY()] = null;
		genericAgentsGrid[x][y] = genericAgent;
		return true;
	}

	public boolean isPositionValid(int x, int y) {
		if (x < 0 || x > simulationParameters.getLandWidth() - 1 || y < 0 || y > simulationParameters.getLandHeight() - 1)
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
		for (int i = 0; i < simulationParameters.getLandWidth(); i++) {
			for (int j = 0; j < simulationParameters.getLandHeight(); j++) {
				grass[i][j] = Math.min(grass[i][j] + 1, simulationParameters.getMaxGrassLife());
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
		return grass[x][y] == simulationParameters.getMaxGrassLife();
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
