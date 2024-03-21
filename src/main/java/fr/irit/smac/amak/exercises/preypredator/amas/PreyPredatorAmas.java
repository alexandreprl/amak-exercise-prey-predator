package fr.irit.smac.amak.exercises.preypredator.amas;

import fr.irit.smac.amak.Amas;
import fr.irit.smac.amak.exercises.preypredator.domain.Land;

public class PreyPredatorAmas extends Amas<Land> {
	public PreyPredatorAmas(Land environment) {
		super(environment, 1, ExecutionPolicy.ONE_PHASE);
	}

	@Override
	protected void onInitialAgentsCreation() {
		for (int i = 0; i < 5; i++) {
			new SheepAgent(this, 10, this.getEnvironment().getRandom().nextInt(Land.ROOM_WIDTH), this.getEnvironment().getRandom().nextInt(Land.ROOM_HEIGHT));
		}
		for (int i = 0; i < 5; i++) {
			new WolfAgent(this, 10, this.getEnvironment().getRandom().nextInt(Land.ROOM_WIDTH), this.getEnvironment().getRandom().nextInt(Land.ROOM_HEIGHT));
		}
	}
}
