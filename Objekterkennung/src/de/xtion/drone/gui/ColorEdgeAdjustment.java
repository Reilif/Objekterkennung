package de.xtion.drone.gui;

import de.xtion.drone.model.ColorEdgeModel;
import de.xtion.drone.model.util.ModelEvent;
import de.xtion.drone.model.util.ModelEventListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * This Panel provides the options to change the values in the
 * ColorEdgeDetection at runtime.
 */
public class ColorEdgeAdjustment extends JPanel {
	private final boolean scalePaintTicks = true;
	ColorEdgeModel model;
	JSlider        hLowerSlider;
	JSlider        sLowerSlider;
	JSlider        vLowerSlider;
	JSlider        hUpperSlider;
	JSlider        sUpperSlider;
	JSlider        vUpperSlider;
	JSlider        topBorderSlider;
	JSlider        rightBorderSlider;
	JSlider        leftBorderSlider;
	JSlider        bottomBorderSlider;
	JSlider        colorThresholdSlider;
	JLabel         hLowerLabel;
	JLabel         sLowerLabel;
	JLabel         vLowerLabel;
	JLabel         hUpperLabel;
	JLabel         sUpperLabel;
	JLabel         vUpperLabel;
	JLabel         topBorderLabel;
	JLabel         rightBorderLabel;
	JLabel         bottomBorderLabel;
	JLabel         leftBorderLabel;
	JLabel         colorThresholdLabel;
	String         hLowerString;
	String         sLowerString;
	String         vLowerString;
	String         hUpperString;
	String         sUpperString;
	String         vUpperString;
	String         topBorderString;
	String         rightBorderString;
	String         bottomBorderString;
	String         leftBorderString;
	String         colorThresholdString;

	public ColorEdgeAdjustment(ColorEdgeModel m) {
		super();
		this.model = m;

		makeStrings();
		makeLabels();
		makeSliders();
		makeLayout();
		registerEventListener();
	}

	void makeStrings() {
		//Strings for the labels
		hLowerString = "H Lower: ";
		sLowerString = "S Lower: ";
		vLowerString = "V Lower: ";
		hUpperString = "H Upper: ";
		sUpperString = "S Upper: ";
		vUpperString = "V Upper: ";
		topBorderString = "Top Border: ";
		rightBorderString = "Right Border: ";
		bottomBorderString = "Bottom Border: ";
		leftBorderString = "Left Border: ";
		colorThresholdString = "Color Mass: ";
	}

	private void makeLabels() {
		hLowerLabel = new JLabel(hLowerString + model.gethLower());
		sLowerLabel = new JLabel(sLowerString + model.getsLower());
		vLowerLabel = new JLabel(vLowerString + model.getvLower());

		hUpperLabel = new JLabel(hUpperString + model.gethUpper());
		sUpperLabel = new JLabel(sUpperString + model.getsUpper());
		vUpperLabel = new JLabel(vUpperString + model.getvUpper());

		topBorderLabel = new JLabel(topBorderString + model.getTopBorder());
		rightBorderLabel =
				new JLabel(rightBorderString + model.getRightBorder());
		bottomBorderLabel =
				new JLabel(bottomBorderString + model.getBottomBorder());
		leftBorderLabel = new JLabel(leftBorderString + model.getLeftBorder());

		colorThresholdLabel =
				new JLabel(colorThresholdString + model.getColorThreshold());
	}

	private void makeSliders() {
		hLowerSlider = new JSlider(JSlider.HORIZONTAL, model.gethMin(),
		                           model.gethMax(), model.gethLower());
		sLowerSlider = new JSlider(JSlider.HORIZONTAL, model.getsMin(),
		                           model.getsMax(), model.getsLower());
		vLowerSlider = new JSlider(JSlider.HORIZONTAL, model.getvMin(),
		                           model.getvMax(), model.getvLower());

		hUpperSlider = new JSlider(JSlider.HORIZONTAL, model.gethMin(),
		                           model.gethMax(), model.gethUpper());
		sUpperSlider = new JSlider(JSlider.HORIZONTAL, model.getsMin(),
		                           model.getsMax(), model.getsUpper());
		vUpperSlider = new JSlider(JSlider.HORIZONTAL, model.getvMin(),
		                           model.getvMax(), model.getvUpper());

		topBorderSlider = new JSlider(JSlider.HORIZONTAL, 1, model.getMaxY(),
		                              model.getTopBorder());
		rightBorderSlider = new JSlider(JSlider.HORIZONTAL, 1, model.getMaxX(),
		                                model.getRightBorder());
		bottomBorderSlider = new JSlider(JSlider.HORIZONTAL, 1, model.getMaxY(),
		                                 model.getBottomBorder());
		leftBorderSlider = new JSlider(JSlider.HORIZONTAL, 1, model.getMaxX(),
		                               model.getLeftBorder());

		colorThresholdSlider = new JSlider(JSlider.HORIZONTAL,
		                                   model.getColorThresholdMin(true),
		                                   model.getColorThresholdMax(true),
		                                   (int) model.getColorThreshold(true));

		//Anzeige der Skala
		hLowerSlider.setMinorTickSpacing(model.gethMax() / 5 / 10);
		hLowerSlider.setMajorTickSpacing(model.gethMax() / 5);
		hLowerSlider.setPaintTicks(scalePaintTicks);

		sLowerSlider.setMinorTickSpacing(model.getsMax() / 5 / 10);
		sLowerSlider.setMajorTickSpacing(model.getsMax() / 5);
		sLowerSlider.setPaintTicks(scalePaintTicks);

		vLowerSlider.setMinorTickSpacing(model.getvMax() / 5 / 10);
		vLowerSlider.setMajorTickSpacing(model.getvMax() / 5);
		vLowerSlider.setPaintTicks(scalePaintTicks);

		hUpperSlider.setMinorTickSpacing(model.gethMax() / 5 / 10);
		hUpperSlider.setMajorTickSpacing(model.gethMax() / 5);
		hUpperSlider.setPaintTicks(scalePaintTicks);

		sUpperSlider.setMinorTickSpacing(model.getsMax() / 5 / 10);
		sUpperSlider.setMajorTickSpacing(model.getsMax() / 5);
		sUpperSlider.setPaintTicks(scalePaintTicks);

		vUpperSlider.setMinorTickSpacing(model.getvMax() / 5 / 10);
		vUpperSlider.setMajorTickSpacing(model.getvMax() / 5);
		vUpperSlider.setPaintTicks(scalePaintTicks);

		topBorderSlider.setMinorTickSpacing(model.getMaxX() / 5 / 10);
		topBorderSlider.setMajorTickSpacing(model.getMaxX() / 5);
		topBorderSlider.setPaintTicks(scalePaintTicks);

		rightBorderSlider.setMinorTickSpacing(model.getMaxX() / 5 / 10);
		rightBorderSlider.setMajorTickSpacing(model.getMaxX() / 5);
		rightBorderSlider.setPaintTicks(scalePaintTicks);

		bottomBorderSlider.setMinorTickSpacing(model.getMaxX() / 5 / 10);
		bottomBorderSlider.setMajorTickSpacing(model.getMaxX() / 5);
		bottomBorderSlider.setPaintTicks(scalePaintTicks);

		leftBorderSlider.setMinorTickSpacing(model.getMaxX() / 5 / 10);
		leftBorderSlider.setMajorTickSpacing(model.getMaxX() / 5);
		leftBorderSlider.setPaintTicks(scalePaintTicks);

		colorThresholdSlider
				.setMinorTickSpacing(model.getColorThresholdMax(true) / 5 / 10);
		colorThresholdSlider
				.setMajorTickSpacing(model.getColorThresholdMax(true) / 5);
		colorThresholdSlider.setPaintTicks(scalePaintTicks);
	}

	private void makeLayout() {
		this.setLayout(new GridLayout(0, 2));

		this.add(hLowerLabel);
		this.add(hLowerSlider);
		this.add(sLowerLabel);
		this.add(sLowerSlider);
		this.add(vLowerLabel);
		this.add(vLowerSlider);
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(hUpperLabel);
		this.add(hUpperSlider);
		this.add(sUpperLabel);
		this.add(sUpperSlider);
		this.add(vUpperLabel);
		this.add(vUpperSlider);
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(topBorderLabel);
		this.add(topBorderSlider);
		this.add(rightBorderLabel);
		this.add(rightBorderSlider);
		this.add(bottomBorderLabel);
		this.add(bottomBorderSlider);
		this.add(leftBorderLabel);
		this.add(leftBorderSlider);
		this.add(new JPanel());
		this.add(new JPanel());
		this.add(colorThresholdLabel);
		this.add(colorThresholdSlider);
	}

	private void registerEventListener() {
		//Fuer den hLowerSlider
		hLowerSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.sethLower(((JSlider) e.getSource()).getValue());
				hLowerLabel.setText(hLowerString + model.gethLower());
			}
		});

		//Fuer den sLowerSlider
		sLowerSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.setsLower(((JSlider) e.getSource()).getValue());
				sLowerLabel.setText(sLowerString + model.getsLower());
			}
		});

		//Fuer den vLowerSlider
		vLowerSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.setvLower(((JSlider) e.getSource()).getValue());
				vLowerLabel.setText(vLowerString + model.getvLower());
			}
		});

		//Fuer den hLowerSlider
		hUpperSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.sethUpper(((JSlider) e.getSource()).getValue());
				hUpperLabel.setText(hUpperString + model.gethUpper());
			}
		});

		//Fuer den sLowerSlider
		sUpperSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.setsUpper(((JSlider) e.getSource()).getValue());
				sUpperLabel.setText(sUpperString + model.getsUpper());
			}
		});

		//Fuer den vLowerSlider
		vUpperSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				model.setvUpper(((JSlider) e.getSource()).getValue());
				vUpperLabel.setText(vUpperString + model.getvUpper());
			}
		});

		topBorderSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				model.setTopBorder(((JSlider) e.getSource()).getValue());
				topBorderLabel.setText(topBorderString + model.getTopBorder());
			}
		});

		rightBorderSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				model.setRightBorder(((JSlider) e.getSource()).getValue());
				rightBorderLabel
						.setText(rightBorderString + model.getRightBorder());
			}
		});

		leftBorderSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				model.setLeftBorder(((JSlider) e.getSource()).getValue());
				leftBorderLabel
						.setText(leftBorderString + model.getLeftBorder());
			}
		});

		bottomBorderSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				model.setBottomBorder(((JSlider) e.getSource()).getValue());
				bottomBorderLabel
						.setText(bottomBorderString + model.getBottomBorder());
			}
		});

		colorThresholdSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				model.setColorThreshold(((JSlider) e.getSource()).getValue(),
				                        true);
				colorThresholdLabel.setText(
						colorThresholdString + model.getColorThreshold());
			}
		});

		ModelEventListener maxY = new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent e) {
				if(topBorderSlider.getValue() > model.getMaxY()) {
					topBorderSlider.setValue(model.getMaxY());
				}
				if(bottomBorderSlider.getValue() > model.getMaxY()) {
					bottomBorderSlider.setValue(model.getMaxY());
				}

				topBorderSlider.setMinorTickSpacing(model.getMaxX() / 5 / 10);
				topBorderSlider.setMajorTickSpacing(model.getMaxX() / 5);

				bottomBorderSlider.setMinorTickSpacing(model.getMaxX() /
				                                       5 / 10);
				bottomBorderSlider.setMajorTickSpacing(model.getMaxX() / 5);

				topBorderSlider.setMaximum(model.getMaxY());
				bottomBorderSlider.setMaximum(model.getMaxY());
			}
		};

		ModelEventListener maxX = new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent e) {
				if(rightBorderSlider.getValue() > model.getMaxX()) {
					rightBorderSlider.setValue(model.getMaxX());
				}
				if(leftBorderSlider.getValue() > model.getMaxX()) {
					leftBorderSlider.setValue(model.getMaxX());
				}

				rightBorderSlider.setMinorTickSpacing(model.getMaxX() /
				                                      5 / 10);
				rightBorderSlider.setMajorTickSpacing(model.getMaxX() / 5);

				leftBorderSlider.setMinorTickSpacing(model.getMaxX() /
				                                     5 / 10);
				leftBorderSlider.setMajorTickSpacing(model.getMaxX() / 5);

				rightBorderSlider.setMaximum(model.getMaxX());
				leftBorderSlider.setMaximum(model.getMaxX());
			}
		};

		model.addModelEventListener(ColorEdgeModel.ColorEdgeModelEvent.MAX_Y,
		                            maxY);

		model.addModelEventListener(ColorEdgeModel.ColorEdgeModelEvent.MAX_X,
		                            maxX);
	}
}
