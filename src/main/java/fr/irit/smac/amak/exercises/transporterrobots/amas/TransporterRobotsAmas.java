package fr.irit.smac.amak.exercises.transporterrobots.amas;

import fr.irit.smac.amak.Amas;
import fr.irit.smac.amak.exercises.transporterrobots.domain.Room;

public class TransporterRobotsAmas extends Amas<Room> {
	public TransporterRobotsAmas(Room environment) {
		super(environment, 1, ExecutionPolicy.ONE_PHASE);
		for (int i = 0; i < 5; i++) {
			new RobotAgent(this);
		}
	}
}
