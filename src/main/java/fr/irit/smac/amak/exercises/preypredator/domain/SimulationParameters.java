package fr.irit.smac.amak.exercises.preypredator.domain;

import lombok.Getter;

public class SimulationParameters {
	@Getter
	private int landWidth = 80;
	@Getter
	private int landHeight = 50;
	@Getter
	private int maxGrassLife = 100;
	@Getter
	private int initialSheepCount = 5;
	@Getter
	private int initialWolfCount = 5;
	@Getter
	private int initialSheepLives = 10;
	@Getter
	private int initialWolfLives = 10;
}
