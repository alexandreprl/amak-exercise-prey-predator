package fr.irit.smac.amak.exercises.transporterrobots.domain;

import java.util.Optional;

public record VisionSnapshot(
		Optional<Box> north, Optional<Box> east, Optional<Box> south, Optional<Box> west) {
	public Optional<Box> getAnyBox() {
		if (north.isPresent())
			return north;
		if (east.isPresent())
			return east;
		if (south.isPresent())
			return south;
		if (west.isPresent())
			return west;
		return Optional.empty();
	}
}
