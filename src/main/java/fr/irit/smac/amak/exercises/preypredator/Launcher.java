package fr.irit.smac.amak.exercises.preypredator;

import fr.irit.smac.amak.exercises.preypredator.amas.PreyPredatorAmas;
import fr.irit.smac.amak.exercises.preypredator.domain.Land;
import fr.irit.smac.amak.exercises.preypredator.domain.SimulationParameters;
import fr.irit.smac.amak.exercises.preypredator.ui.PreyPredatorMainWindow;
import fr.irit.smac.amak.scheduling.Scheduler;
import fr.irit.smac.amak.ui.SchedulerToolbar;

public class Launcher {
	public static void main(String[] args) {
		var simulationParameters = new SimulationParameters();
		var land = new Land(simulationParameters);
		var amas = new PreyPredatorAmas(land);
		var mainWindow = new PreyPredatorMainWindow(amas);
		var scheduler = new Scheduler(amas, land, mainWindow);
		mainWindow.addToolbar(new SchedulerToolbar("Scheduler", scheduler));
	}
}
