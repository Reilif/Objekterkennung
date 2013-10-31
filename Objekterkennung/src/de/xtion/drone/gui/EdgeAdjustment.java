package de.xtion.drone.gui;

import test.edge_detection.gui.EdgeModel;
import test.edge_detection.objects.gui.ModelEvent;
import test.edge_detection.objects.gui.ModelEventListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * This Panel provides the options to change the values in the EdgeModel at runtime.
 */
public class EdgeAdjustment extends JPanel {
	private final int     scaleMinorSpacing = 300;
	private final int     scaleMajorSpacing = 3000;
	private final boolean scalePaintTicks   = true;
	JSlider cannnyRadiusSlider;
	JSlider cannnyThresholdOneSlider;
	JSlider cannnyThresholdTwoSlider;
	JLabel  cannnyRadiusLabel;
	JLabel  cannnyThresholdOneLabel;
	JLabel  cannnyThresholdTwoLabel;
	String  cannyRadiusString;
	String  cannyThresholdOneString;
	String  cannyThresholdTwoString;
	private EdgeModel model;

	/**
	 * @param edgeModel  The parameter represents the EdgeModel the object is getting and setting information from/to
	 */
	public EdgeAdjustment(EdgeModel edgeModel) {
		super(new GridLayout(3, 2));
		model = edgeModel;
		setVisible(true);

		//Strings
		cannyRadiusString = "Blur radius:";
		cannyThresholdOneString = "Threshold 1: ";
		cannyThresholdTwoString = "Threshold 2: ";

		//Slider
		System.out.println(model.getCannyRadiusMin(true));
		System.out.println(model.getCannyRadius(true));
		System.out.println(model.getCannyRadiusMax(true));
		cannnyRadiusSlider = new JSlider(JSlider.HORIZONTAL, (int) model.getCannyRadiusMin(true),
		                                 (int) model.getCannyRadiusMax(true), (int) model.getCannyRadius(true));
		cannnyThresholdOneSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) model.getCannyThresholdOneMax(true),
		                                       (int) model.getCannyThresholdOne(true));
		cannnyThresholdTwoSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) model.getCannyThresholdTwoMax(true),
		                                       (int) model.getCannyThresholdTwo(true));
		//Labels
		cannnyRadiusLabel = new JLabel(cannyRadiusString);
		cannnyThresholdOneLabel = new JLabel(cannyThresholdOneString);
		cannnyThresholdTwoLabel = new JLabel(cannyThresholdTwoString);

		//Scale
		cannnyRadiusSlider.setMinorTickSpacing(scaleMinorSpacing/3);
		cannnyRadiusSlider.setMajorTickSpacing(scaleMajorSpacing/3);
		cannnyRadiusSlider.setPaintTicks(scalePaintTicks);

		cannnyThresholdOneSlider.setMinorTickSpacing(scaleMinorSpacing);
		cannnyThresholdOneSlider.setMajorTickSpacing(scaleMajorSpacing);
		cannnyThresholdOneSlider.setPaintTicks(scalePaintTicks);

		cannnyThresholdTwoSlider.setMinorTickSpacing(scaleMinorSpacing);
		cannnyThresholdTwoSlider.setMajorTickSpacing(scaleMajorSpacing);
		cannnyThresholdTwoSlider.setPaintTicks(scalePaintTicks);

		//adding
		add(cannnyRadiusLabel);
		add(cannnyRadiusSlider);
		add(cannnyThresholdOneLabel);
		add(cannnyThresholdOneSlider);
		add(cannnyThresholdTwoLabel);
		add(cannnyThresholdTwoSlider);

		//Listeners on Slider
		cannnyRadiusSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCannyRadius(((JSlider) event.getSource()).getValue(), true);
						cannnyRadiusLabel.setText(cannyRadiusString + model.getCannyRadius());
					}
				});
			}
		});

		cannnyThresholdOneSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCannyThresholdOne(((JSlider) event.getSource()).getValue(), true);
						cannnyThresholdOneLabel.setText(cannyThresholdOneString + model.getCannyThresholdOne());
					}
				});
			}
		});

		cannnyThresholdTwoSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCannyThresholdTwo(((JSlider) event.getSource()).getValue(), true);
						cannnyThresholdTwoLabel.setText(cannyThresholdTwoString + model.getCannyThresholdTwo());
					}
				});
			}
		});

		//Listener on Model
		model.addModelEventListener(EdgeModel.EdgeModelEvent.CAN_THR_ONE_MAX, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						cannnyThresholdTwoSlider.setMaximum((int) model.getCannyThresholdTwoMax(true));
					}
				});
			}
		});
	}
}
