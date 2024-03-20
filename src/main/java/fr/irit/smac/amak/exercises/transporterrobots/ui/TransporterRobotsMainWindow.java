package fr.irit.smac.amak.exercises.transporterrobots.ui;

import fr.irit.smac.amak.exercises.transporterrobots.amas.RobotAgent;
import fr.irit.smac.amak.exercises.transporterrobots.amas.TransporterRobotsAmas;
import fr.irit.smac.amak.exercises.transporterrobots.domain.Box;
import fr.irit.smac.amak.exercises.transporterrobots.domain.Room;
import fr.irit.smac.amak.ui.MainWindow;
import fr.irit.smac.amak.ui.VectorialGraphicsPanel;
import fr.irit.smac.amak.ui.drawables.Drawable;
import fr.irit.smac.amak.ui.drawables.DrawableImage;
import fr.irit.smac.amak.ui.drawables.DrawableRectangle;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TransporterRobotsMainWindow extends MainWindow {

	private static final int OBSTACLE_LAYER = 1;
	private static final int ROBOT_LAYER = 2;
	private static final int BOX_LAYER = 3;
	private final VectorialGraphicsPanel vectorialGraphicsPanel;
	private final TransporterRobotsAmas amas;
	private final Map<RobotAgent, DrawableImage> robotDrawables = new HashMap<>();
	private final Map<Box, Drawable> boxDrawables = new HashMap<>();
	private final String robotFullFilename;
	private final String robotEmptyFilename;

	public TransporterRobotsMainWindow(TransporterRobotsAmas amas) {
		super();
		this.amas = amas;

		robotFullFilename = getClass().getResource("/RobotFull.png").getFile();
		robotEmptyFilename = getClass().getResource("/RobotEmpty.png").getFile();

		vectorialGraphicsPanel = new VectorialGraphicsPanel("Map");
		vectorialGraphicsPanel.setDefaultView(80, -495, -250);
		setLeftPanel(vectorialGraphicsPanel);
		RenderWalls();
	}

	private void RenderWalls() {
		for (var x = 0; x < Room.ROOM_WIDTH; x++) {
			for (var y = 0; y < Room.ROOM_HEIGHT; y++) {
				if (amas.getEnvironment().isWall(x, y))
					new DrawableRectangle(vectorialGraphicsPanel, x * 10, y * 10, 10, 10).setColor(new Color(191, 191, 191)).setLayer(OBSTACLE_LAYER);
				if (amas.getEnvironment().isPickUpZone(x, y))
					new DrawableRectangle(vectorialGraphicsPanel, x * 10, y * 10, 10, 10).setColor(Color.green).setLayer(OBSTACLE_LAYER);
				if (amas.getEnvironment().isDropZone(x, y))
					new DrawableRectangle(vectorialGraphicsPanel, x * 10, y * 10, 10, 10).setColor(Color.red).setLayer(OBSTACLE_LAYER);
			}
		}
	}

	@Override
	public void cycle() {
		var robotAgentsList = amas.getAgents(RobotAgent.class);
		for (var agent :
				robotAgentsList) {
			var drawable = getOrCreateDrawable(agent);
			drawable.setFilename(agent.isHoldingABox() ? robotFullFilename : robotEmptyFilename);
			drawable.move(agent.getX() * 10, agent.getY() * 10);
		}
		for (Iterator<RobotAgent> iterator = robotDrawables.keySet().iterator(); iterator.hasNext(); ) {
			var ra = iterator.next();
			if (!robotAgentsList.contains(ra)) {
				robotDrawables.get(ra).remove();
				iterator.remove();
			}
		}

		for (var b : amas.getEnvironment().getBoxesOnGrid()) {
			getOrCreateDrawable(b).move(b.getX() * 10, b.getY() * 10);
		}
		for (Iterator<Box> iterator = boxDrawables.keySet().iterator(); iterator.hasNext(); ) {
			var b = iterator.next();
			if (!amas.getEnvironment().getBoxesOnGrid().contains(b)) {
				boxDrawables.get(b).remove();
				iterator.remove();
			}
		}
	}

	private DrawableImage getOrCreateDrawable(RobotAgent agent) {
		return robotDrawables.computeIfAbsent(agent, (a) -> {
			var drawableImage = new DrawableImage(vectorialGraphicsPanel, agent.getX() * 10, agent.getY() * 10, getClass().getResource("/RobotEmpty.png").getFile());
			drawableImage.setLayer(ROBOT_LAYER);
			return drawableImage;
		});
	}

	private Drawable getOrCreateDrawable(Box box) {
		return boxDrawables.computeIfAbsent(box, (b) ->
		{
			return new DrawableImage(vectorialGraphicsPanel, b.getX() * 10, b.getY() * 10, getClass().getResource("/box.png").getFile()).setColor(Color.white).setLayer(BOX_LAYER);
		});
	}
}
