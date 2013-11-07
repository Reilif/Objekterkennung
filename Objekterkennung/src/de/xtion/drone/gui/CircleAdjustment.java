package de.xtion.drone.gui;

import de.xtion.drone.model.CircleModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class CircleAdjustment extends JPanel {
	private final int     scaleMinorSpacing = 10;
	private final int     scaleMajorSpacing = 100;
	private final boolean scalePaintTicks   = true;
	private CircleModel model;
	private JSlider     circleDensitySlider;
	private JSlider     circleMinSizeSlider;
	private JSlider     circleMaxSizeSlider;
	private JLabel      circleDensityLabel;
	private JLabel      circleMinSizeLabel;
	private JLabel      circleMaxSizeLabel;
	private String      circleDensityString;
	private String      circleMinSizeString;
	private String      circleMaxSizeString;

	public CircleAdjustment(final CircleModel colorModel) {
		super(new GridLayout(3, 2));
		this.model = colorModel;
		setVisible(true);

		//Strings
		circleDensityString = "Density: ";
		circleMinSizeString = "Min Circle Size: ";
		circleMaxSizeString = "Max Circle Size: ";

		//Slider
		circleDensitySlider = new JSlider(JSlider.HORIZONTAL, (int) colorModel.getDensityMin(true),
		                                  (int) colorModel.getDensityMax(true), (int) colorModel.getDensity(true));
		circleMinSizeSlider =
				new JSlider(JSlider.HORIZONTAL, colorModel.getCircleMinSizeMin(), colorModel.getCircleMinSizeMax(),
				            colorModel.getCircleMinSize());
		circleMaxSizeSlider =
				new JSlider(JSlider.HORIZONTAL, colorModel.getCircleMaxSizeMin(), colorModel.getCircleMaxSizeMax(),
				            colorModel.getCircleMaxSize());

		//Labels
		circleDensityLabel = new JLabel(circleDensityString + model.getDensity());
		circleMinSizeLabel = new JLabel(circleMinSizeString + model.getCircleMinSize());
		circleMaxSizeLabel = new JLabel(circleMaxSizeString + model.getCircleMaxSize());

		//Scale
		circleDensitySlider.setMinorTickSpacing(scaleMinorSpacing);
		circleDensitySlider.setMajorTickSpacing(scaleMajorSpacing);
		circleDensitySlider.setPaintTicks(scalePaintTicks);

		circleMinSizeSlider.setMinorTickSpacing(scaleMinorSpacing);
		circleMinSizeSlider.setMajorTickSpacing(scaleMajorSpacing);
		circleMinSizeSlider.setPaintTicks(scalePaintTicks);

		circleMaxSizeSlider.setMinorTickSpacing(scaleMinorSpacing);
		circleMaxSizeSlider.setMajorTickSpacing(scaleMajorSpacing);
		circleMaxSizeSlider.setPaintTicks(scalePaintTicks);

		//adding
		add(circleDensityLabel);
		add(circleDensitySlider);
		add(circleMinSizeLabel);
		add(circleMinSizeSlider);
		add(circleMaxSizeLabel);
		add(circleMaxSizeSlider);

		//Listener to Slider
		circleDensitySlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setDensity(((JSlider) event.getSource()).getValue(), true);
						circleDensityLabel.setText(circleDensityString + model.getDensity());
					}
				});
			}
		});
		circleMinSizeSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCircleMinSize(((JSlider) event.getSource()).getValue());
						circleMinSizeLabel.setText(circleMinSizeString + model.getCircleMinSize());
					}
				});
			}
		});
		circleMaxSizeSlider.addChangeListener(new ChangeListener() {
			@Override public void stateChanged(final ChangeEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						model.setCircleMaxSize(((JSlider) event.getSource()).getValue());
						circleMaxSizeLabel.setText(circleMaxSizeString + model.getCircleMaxSize());
					}
				});
			}
		});
	}
}
