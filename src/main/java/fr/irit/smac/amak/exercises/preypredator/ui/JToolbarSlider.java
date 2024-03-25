package fr.irit.smac.amak.exercises.preypredator.ui;

import javax.swing.*;
import java.util.function.Consumer;

public class JToolbarSlider extends JToolBar {
	private JSlider slider;

	public JToolbarSlider(String name, int min, int max, int value, Consumer<Integer> onChange) {
		super(name);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		var label = new JLabel(name);
		add(label);
		slider = new JSlider(min, max, value);
		label.setText(name + ": " + slider.getValue());
		slider.addChangeListener(e -> {
			onChange.accept(slider.getValue());
			label.setText(name + ": " + slider.getValue());
		});
		add(slider);
	}
}
