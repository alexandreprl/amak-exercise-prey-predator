package fr.irit.smac.amak.exercises.preypredator.amas;

import fr.irit.smac.amak.Amas;
import fr.irit.smac.amak.exercises.preypredator.domain.Land;

public class PreyPredatorAmas extends Amas<Land> {
	public PreyPredatorAmas(Land environment) {
		super(environment, 1, ExecutionPolicy.ONE_PHASE);
	}

	@Override
	protected void onInitialAgentsCreation() {
		for (int i = 0; i < getEnvironment().getSimulationParameters().getInitialSheepCount(); i++) {
			new SheepAgent(this,
			               getEnvironment().getSimulationParameters().getInitialSheepLives(),
			               this.getEnvironment().getRandom().nextInt(getEnvironment().getSimulationParameters().getLandWidth()),
			               this.getEnvironment().getRandom().nextInt(getEnvironment().getSimulationParameters().getLandHeight()));
		}
		for (int i = 0; i < getEnvironment().getSimulationParameters().getInitialWolfCount(); i++) {
			new WolfAgent(this,
			              getEnvironment().getSimulationParameters().getInitialWolfLives(),
			              this.getEnvironment().getRandom().nextInt(getEnvironment().getSimulationParameters().getLandWidth()),
			              this.getEnvironment().getRandom().nextInt(getEnvironment().getSimulationParameters().getLandHeight()));
		}
	}
}
