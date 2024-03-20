package fr.irit.smac.amak.exercises.transporterrobots.amas;

import fr.irit.smac.amak.Agent;
import fr.irit.smac.amak.exercises.transporterrobots.domain.Box;
import fr.irit.smac.amak.exercises.transporterrobots.domain.Room;
import lombok.Getter;

import java.util.Optional;

public class RobotAgent extends Agent<TransporterRobotsAmas, Room> {
	@Getter
	private int x, y;
	private Box heldBox;

	protected RobotAgent(TransporterRobotsAmas amas) {
		super(amas);
		x = 1;
		y = 1;
	}

	@Override
	protected void onDecideAndAct() {
		if (!isHoldingABox()) {
			tryToPickUpABox();
		} else if (getAmas().getEnvironment().isDropZone(x, y)) {
			tryToDropABox();
		}
		var newX = x + getAmas().getEnvironment().getRandom().nextInt(3) - 1;
		var newY = y + getAmas().getEnvironment().getRandom().nextInt(3) - 1;
		move(newX, newY);
	}

	private void tryToDropABox() {
		if (!isHoldingABox())
			return;
		getAmas().getEnvironment().dropBox(heldBox, x, y);
		heldBox = null;
	}

	private void move(int newX, int newY) {
		if (amas.getEnvironment().move(this, newX, newY)) {
			this.x = newX;
			this.y = newY;
		}
	}

	private void tryToPickUpABox() {
		var visionSnapshot = amas.getEnvironment().observe(x, y);
		var anyBoxSeen = visionSnapshot.getAnyBox();
		if (anyBoxSeen.isPresent()) {
			var box = anyBoxSeen.get();
			Optional<Box> boxPickResult = amas.getEnvironment().pickBox(box.getX(), box.getY());
			if (boxPickResult.isPresent())
				heldBox = boxPickResult.get();
		}
	}

	public boolean isHoldingABox() {
		return heldBox != null;
	}
}
