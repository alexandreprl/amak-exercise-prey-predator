package fr.irit.smac.amak.exercises.preypredator.amas;

public class WolfAgent extends GenericAgent {
	public WolfAgent(PreyPredatorAmas amas, int lives, int x, int y) {
		super(amas, lives, x, y);
	}

	@Override
	protected void onDecideAndAct() {
		moveRandomly();
	}
}
