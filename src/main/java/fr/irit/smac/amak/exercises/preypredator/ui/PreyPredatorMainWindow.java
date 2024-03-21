package fr.irit.smac.amak.exercises.preypredator.ui;

import fr.irit.smac.amak.exercises.preypredator.amas.GenericAgent;
import fr.irit.smac.amak.exercises.preypredator.amas.PreyPredatorAmas;
import fr.irit.smac.amak.exercises.preypredator.amas.SheepAgent;
import fr.irit.smac.amak.exercises.preypredator.amas.WolfAgent;
import fr.irit.smac.amak.exercises.preypredator.domain.Land;
import fr.irit.smac.amak.ui.MainWindow;
import fr.irit.smac.amak.ui.VectorialGraphicsPanel;
import fr.irit.smac.amak.ui.drawables.Drawable;
import fr.irit.smac.amak.ui.drawables.DrawableImage;
import fr.irit.smac.amak.ui.drawables.DrawableRectangle;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PreyPredatorMainWindow extends MainWindow {

	private static final int OBSTACLE_LAYER = 1;
	private static final int GRASS_LAYER = 2;
	private static final int AGENT_LAYER = 3;
	private final VectorialGraphicsPanel vectorialGraphicsPanel;
	private final PreyPredatorAmas amas;
	private final Map<GenericAgent, DrawableImage> agentDrawables = new HashMap<>();
	private final String sheepFilename;
	private final String wolfFilename;
	private final Drawable[][] grassDrawables = new DrawableRectangle[Land.ROOM_WIDTH][Land.ROOM_HEIGHT];

	public PreyPredatorMainWindow(PreyPredatorAmas amas) {
		super();
		this.amas = amas;

		sheepFilename = getClass().getResource("/sheep.png").getFile();
		wolfFilename = getClass().getResource("/wolf.png").getFile();

		vectorialGraphicsPanel = new VectorialGraphicsPanel("Map");
		vectorialGraphicsPanel.setDefaultView(80, -400, -250);
		setLeftPanel(vectorialGraphicsPanel);
		RenderWalls();
	}

	private void RenderWalls() {
		// Create drawable rectangle for each grass
		for (var x = 0; x < Land.ROOM_WIDTH; x++) {
			for (var y = 0; y < Land.ROOM_HEIGHT; y++) {
				grassDrawables[x][y] = new DrawableRectangle(vectorialGraphicsPanel, x * 10, y * 10, 10, 10).setColor(new Color(0, 255, 0)).setLayer(GRASS_LAYER);
			}
		}

		for (var x = -1; x < Land.ROOM_WIDTH + 1; x++) {
			for (var y = -1; y < Land.ROOM_HEIGHT + 1; y++) {
				if (!amas.getEnvironment().isPositionValid(x, y))
					new DrawableRectangle(vectorialGraphicsPanel, x * 10, y * 10, 10, 10).setColor(new Color(191, 191, 191)).setLayer(OBSTACLE_LAYER);
			}
		}
	}

	@Override
	public void cycle() {
		var agentsList = amas.getAgents(GenericAgent.class);
		for (var agent : agentsList) {
			var drawable = getOrCreateDrawable(agent);
			drawable.move(agent.getX() * 10, agent.getY() * 10);
		}
		for (Iterator<GenericAgent> iterator = agentDrawables.keySet().iterator(); iterator.hasNext(); ) {
			var ra = iterator.next();
			if (!agentsList.contains(ra)) {
				agentDrawables.get(ra).remove();
				iterator.remove();
			}
		}
		// Change the grass color based on the value
		for (var x = 0; x < Land.ROOM_WIDTH; x++) {
			for (var y = 0; y < Land.ROOM_HEIGHT; y++) {
				int green = (int) Math.floor((double) (amas.getEnvironment().getGrass(x, y)) / (double) Land.MAX_GRASS * 255);
				grassDrawables[x][y].setColor(new Color(0, green, 0));
			}
		}
	}

	private DrawableImage getOrCreateDrawable(GenericAgent agent) {
		return agentDrawables.computeIfAbsent(agent, (a) -> {
			var filename = "";
			if (a instanceof SheepAgent) {
				filename = sheepFilename;
			} else if (a instanceof WolfAgent) {
				filename = wolfFilename;
			}
			var drawableImage = new DrawableImage(vectorialGraphicsPanel, agent.getX() * 10, agent.getY() * 10, filename);
			drawableImage.setLayer(AGENT_LAYER);
			return drawableImage;
		});
	}
}
