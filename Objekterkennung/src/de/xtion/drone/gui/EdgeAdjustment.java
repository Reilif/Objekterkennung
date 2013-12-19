package de.xtion.drone.gui;

import de.xtion.drone.model.EdgeModel;
import de.xtion.drone.model.util.ModelEvent;
import de.xtion.drone.model.util.ModelEventListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * This Panel provides the options to change the values in the EdgeModel at
 * runtime.
 */
public class EdgeAdjustment extends JPanel {
	private final int     scaleMinorSpacing = 300;
	private final int     scaleMajorSpacing = 3000;
	private final boolean scalePaintTicks   = true;
	JSlider cannyRadiusSlider;
	JSlider cannyThresholdOneSlider;
	JSlider cannyThresholdTwoSlider;
	JLabel  cannyRadiusLabel;
	JLabel  cannyThresholdOneLabel;
	JLabel  cannyThresholdTwoLabel;
	String  cannyRadiusString;
	String  cannyThresholdOneString;
	String  cannyThresholdTwoString;
	private EdgeModel model;

	/**
	 * @param edgeModel The parameter represents the EdgeModel the object is
	 *                  getting and setting information from/to
	 */
	public EdgeAdjustment(EdgeModel edgeModel) {
		super(new GridLayout(3, 2));
		model = edgeModel;
		setVisible(true);

		//Strings
		cannyRadiusString = "Blur radius: ";
		cannyThresholdOneString = "Threshold 1: ";
		cannyThresholdTwoString = "Threshold 2: ";

		//Slider
		cannyRadiusSlider = new JSlider(JSlider.HORIZONTAL,
		                                (int) model.getCannyRadiusMin(true),
		                                (int) model.getCannyRadiusMax(true),
		                                (int) model.getCannyRadius(true));
		cannyThresholdOneSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) model
				.getCannyThresholdOneMax(true), (int) model
				.getCannyThresholdOne(true));
		cannyThresholdTwoSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) model
				.getCannyThresholdTwoMax(true), (int) model
				.getCannyThresholdTwo(true));
		//Labels
		cannyRadiusLabel =
				new JLabel(cannyRadiusString + model.getCannyRadius());
		cannyThresholdOneLabel = new JLabel(
				cannyThresholdOneString + model.getCannyThresholdOne());
		cannyThresholdTwoLabel = new JLabel(
				cannyThresholdTwoString + model.getCannyThresholdTwo());

		//Scale
		cannyRadiusSlider.setMinorTickSpacing(scaleMinorSpacing / 3);
		cannyRadiusSlider.setMajorTickSpacing(scaleMajorSpacing / 3);
		cannyRadiusSlider.setPaintTicks(scalePaintTicks);

		cannyThresholdOneSlider.setMinorTickSpacing(scaleMinorSpacing);
		cannyThresholdOneSlider.setMajorTickSpacing(scaleMajorSpacing);
		cannyThresholdOneSlider.setPaintTicks(scalePaintTicks);

		cannyThresholdTwoSlider.setMinorTickSpacing(scaleMinorSpacing);
		cannyThresholdTwoSlider.setMajorTickSpacing(scaleMajorSpacing);
		cannyThresholdTwoSlider.setPaintTicks(scalePaintTicks);

		//adding
		add(cannyRadiusLabel);
		add(cannyRadiusSlider);
		add(cannyThresholdOneLabel);
		add(cannyThresholdOneSlider);
		add(cannyThresholdTwoLabel);
		add(cannyThresholdTwoSlider);

		//Listeners on Slider
		cannyRadiusSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCannyRadius(
								((JSlider) event.getSource()).getValue(), true);
						cannyRadiusLabel.setText(
								cannyRadiusString + model.getCannyRadius());
					}
				});
			}
		});

		cannyThresholdOneSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCannyThresholdOne(
								((JSlider) event.getSource()).getValue(), true);
						cannyThresholdOneLabel.setText(cannyThresholdOneString +
						                               model.getCannyThresholdOne());
					}
				});
			}
		});

		cannyThresholdTwoSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCannyThresholdTwo(
								((JSlider) event.getSource()).getValue(), true);
						cannyThresholdTwoLabel.setText(cannyThresholdTwoString +
						                               model.getCannyThresholdTwo());
					}
				});
			}
		});

		ModelEventListener ctom = new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						cannyThresholdTwoSlider.setMaximum(
								(int) model.getCannyThresholdTwoMax(true));
					}
				});
			}
		};

		//Listener on Model
		model.addModelEventListener(EdgeModel.EdgeModelEvent.CAN_THR_ONE_MAX,
		                            ctom);
	}
}
