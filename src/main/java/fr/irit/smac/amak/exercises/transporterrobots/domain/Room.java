package fr.irit.smac.amak.exercises.transporterrobots.domain;

import fr.irit.smac.amak.Environment;
import fr.irit.smac.amak.exercises.transporterrobots.amas.RobotAgent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Room extends Environment {
	public final static int ROOM_WIDTH = 100;
	public final static int ROOM_HEIGHT = 50;
	private final RobotAgent[][] robotAgentsGrid = new RobotAgent[ROOM_WIDTH][ROOM_HEIGHT];
	@Getter
	private final List<Box> boxesOnGrid = new ArrayList<>();

	@Override
	public void onReady() {
		for (var x = 0; x < Room.ROOM_WIDTH; x++) {
			for (var y = 0; y < Room.ROOM_HEIGHT; y++) {
				if (isPickUpZone(x, y))
					boxesOnGrid.add(new Box(x, y));
			}
		}
	}

	public boolean move(RobotAgent robotAgent, int x, int y) {
		if (isObstacleForRobotAgent(robotAgent, x, y))
			return false;
		if (robotAgentsGrid[x][y] != null && robotAgentsGrid[x][y] != robotAgent)
			return false;
		if (robotAgentsGrid[robotAgent.getX()][robotAgent.getY()] == robotAgent)
			robotAgentsGrid[robotAgent.getX()][robotAgent.getY()] = null;
		robotAgentsGrid[x][y] = robotAgent;
		return true;
	}

	public boolean isObstacleForRobotAgent(RobotAgent robotAgent, int x, int y) {
		if (isWall(x, y))
			return true;
		if (boxOnGrid(x, y).isPresent())
			return true;
		return robotAgentsGrid[x][y] != null && robotAgentsGrid[x][y] != robotAgent;
	}

	private Optional<Box> boxOnGrid(int x, int y) {
		return boxesOnGrid.stream().filter(box -> box.getX() == x && box.getY() == y).findFirst();
	}

	public Optional<Box> pickBox(int x, int y) {
		if (isDropZone(x,y))
			return Optional.empty();
		var box = boxOnGrid(x, y);
		box.ifPresent(boxesOnGrid::remove);
		return box;
	}

	public void dropBox(Box box, int x, int y) {
		box.setX(x);
		box.setY(y);

		boxesOnGrid.add(box);
	}

	public boolean isWall(int x, int y) {
		if (x <= 0 || x >= ROOM_WIDTH - 1 || y <= 0 || y >= ROOM_HEIGHT - 1)
			return true;

		// Central block
		if (x > 40 && x < 60 && y > 1 && y < ROOM_HEIGHT - 2)
			return true;

		return false;
	}

	public boolean isPickUpZone(int x, int y) {
		if (x > 0 && x < 10 && y > 10 && y < 40)
			return true;
		return false;
	}

	public boolean isDropZone(int x, int y) {
		if (x > ROOM_WIDTH - 12 && x < ROOM_WIDTH - 1 && y > 10 && y < 40)
			return true;
		return false;
	}

	public VisionSnapshot observe(int x, int y) {
		var visionSnapshot = new VisionSnapshot(boxOnGrid(x, y + 1),
		                                        boxOnGrid(x + 1, y),
		                                        boxOnGrid(x, y - 1),
		                                        boxOnGrid(x - 1, y));

		return visionSnapshot;
	}
}
