package fr.irit.smac.amak.exercises.preypredator.amas;

public class SheepAgent extends GenericAgent {
	public SheepAgent(PreyPredatorAmas amas, int lives, int x, int y) {
		super(amas, lives, x, y);
	}

	@Override
	protected void onPerceive() {

	}

	@Override
	protected void onDecideAndAct() {
		moveRandomly();
	}
}
