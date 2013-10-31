package de.xtion.drone.gui;

import de.xtion.drone.model.ColorModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ColorAdjustment extends JPanel {
	private final int     scaleMinorSpacing = 300;
	private final int     scaleMajorSpacing = 3000;
	private final boolean scalePaintTicks   = true;
	ColorModel model;
	JSlider    hLowerSlider;
	JSlider    sLowerSlider;
	JSlider    vLowerSlider;
	JSlider    hUpperSlider;
	JSlider    sUpperSlider;
	JSlider    vUpperSlider;
	JLabel     hLowerLabel;
	JLabel     sLowerLabel;
	JLabel     vLowerLabel;
	JLabel     hUpperLabel;
	JLabel     sUpperLabel;
	JLabel     vUpperLabel;
	String     hLowerString;
	String     sLowerString;
	String     vLowerString;
	String     hUpperString;
	String     sUpperString;
	String     vUpperString;

	public ColorAdjustment(ColorModel m) {
		super();
		this.model = m;

		//Strings for the labels
		hLowerString = "H Lower:";
		sLowerString = "S Lower:";
		vLowerString = "V Lower:";
		hUpperString = "H Upper:";
		sUpperString = "S Upper:";
		vUpperString = "V Upper";

		// Anlegen der JSlider
		hLowerSlider = new JSlider(JSlider.HORIZONTAL, m.gethMin(), m.gethMax(), m.gethLower());
		sLowerSlider = new JSlider(JSlider.HORIZONTAL, m.getsMin(), m.getsMax(), m.getsLower());
		vLowerSlider = new JSlider(JSlider.HORIZONTAL, m.getvMin(), m.getvMax(), m.getvLower());

		hUpperSlider = new JSlider(JSlider.HORIZONTAL, m.gethMin(), m.gethMax(), m.gethUpper());
		sUpperSlider = new JSlider(JSlider.HORIZONTAL, m.getsMin(), m.getsMax(), m.getsUpper());
		vUpperSlider = new JSlider(JSlider.HORIZONTAL, m.getvMin(), m.getvMax(), m.getvUpper());

		// Anlegen der JLabels
		hLowerLabel = new JLabel(hLowerString + m.gethLower());
		sLowerLabel = new JLabel(sLowerString + m.getsLower());
		vLowerLabel = new JLabel(vLowerString + m.getvLower());

		hUpperLabel = new JLabel(hUpperString + m.gethUpper());
		sUpperLabel = new JLabel(sUpperString + m.getsUpper());
		vUpperLabel = new JLabel(vUpperString + m.getvUpper());

		//Anzeige der Skala
		hLowerSlider.setMinorTickSpacing(scaleMinorSpacing);
		hLowerSlider.setMajorTickSpacing(scaleMajorSpacing);
		hLowerSlider.setPaintTicks(scalePaintTicks);

		sLowerSlider.setMinorTickSpacing(scaleMinorSpacing);
		sLowerSlider.setMajorTickSpacing(scaleMajorSpacing);
		sLowerSlider.setPaintTicks(scalePaintTicks);

		vLowerSlider.setMinorTickSpacing(scaleMinorSpacing);
		vLowerSlider.setMajorTickSpacing(scaleMajorSpacing);
		vLowerSlider.setPaintTicks(scalePaintTicks);

		hUpperSlider.setMinorTickSpacing(scaleMinorSpacing);
		hUpperSlider.setMajorTickSpacing(scaleMajorSpacing);
		hUpperSlider.setPaintTicks(scalePaintTicks);

		sUpperSlider.setMinorTickSpacing(scaleMinorSpacing);
		sUpperSlider.setMajorTickSpacing(scaleMajorSpacing);
		sUpperSlider.setPaintTicks(scalePaintTicks);

		vUpperSlider.setMinorTickSpacing(scaleMinorSpacing);
		vUpperSlider.setMajorTickSpacing(scaleMajorSpacing);
		vUpperSlider.setPaintTicks(scalePaintTicks);

		this.setLayout(new GridLayout(14, 2));// 14 Zeilen, 2 Spalte

		// Hinzufügen der Elemente
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
	}
}
