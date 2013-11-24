package de.xtion.drone.gui;

import de.xtion.drone.manipulation.ImgProcessing;
import de.xtion.drone.model.ImgProcessingModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.RescaleOp;

/**
 * Created with IntelliJ IDEA.
 * User: Noshaba
 * Date: 21.11.13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class ImgProcessingAdjustment extends JPanel implements ItemListener {

    private ImgProcessingModel model;

    private JCheckBox wbGrayWorld;
    private JCheckBox wbQM;
    private JCheckBox wbRetinex;

    private final boolean scalePaintTicks = true;
    private JSlider contrastSlider;
    private JSlider brightnessSlider;
    private JSlider hueSlider;
    private JSlider saturationSlider;
    private JSlider valueSlider;
    private JLabel whiteBalancesLabel;
    private JLabel contrastLabel;
    private JLabel brightnessLabel;
    private JLabel hueLabel;
    private JLabel saturationLabel;
    private JLabel valueLabel;

    public ImgProcessingAdjustment(ImgProcessingModel m){
        super(new GridLayout(0,1));

        // init image adjustments
        ImgProcessing.algorithms = new StringBuffer("---");
        ImgProcessing.rescaleOp = new RescaleOp(m.getContrast(), m.getBrightness(), null);
        ImgProcessing.hsv = new double[3];

        this.model = m;

        // init checkboxes for white balance
        this.wbGrayWorld = new JCheckBox("Grayworld");
        this.wbQM = new JCheckBox("Quadratic Mapping");
        this.wbRetinex = new JCheckBox("Retinex");

        // init key events
        this.wbGrayWorld.setMnemonic(KeyEvent.VK_G);
        this.wbQM.setMnemonic(KeyEvent.VK_Q);
        this.wbRetinex.setMnemonic(KeyEvent.VK_R);

        //Register listener for the check boxes
        this.wbGrayWorld.addItemListener(this);
        this.wbQM.addItemListener(this);
        this.wbRetinex.addItemListener(this);

        // init slider for brightness and contrast
        this.contrastSlider = new JSlider(JSlider.HORIZONTAL, m.getMinContrast(), m.getMaxContrast(), (int) m.getContrast());
        this.brightnessSlider = new JSlider(JSlider.HORIZONTAL, m.getMinBrightness(), m.getMaxBrightness(), m.getBrightness());

        this.whiteBalancesLabel = new JLabel("White Balances");
        this.contrastLabel = new JLabel("Contrast: " + m.getContrast());
        this.brightnessLabel = new JLabel("Brightness: " + m.getBrightness());

        this.contrastSlider.setMinorTickSpacing(m.getMaxContrast() / 5 / 10);
        this.contrastSlider.setMajorTickSpacing(m.getMaxContrast() / 5);
        this.contrastSlider.setPaintTicks(scalePaintTicks);

        this.brightnessSlider.setMinorTickSpacing(m.getMaxBrightness() / 5 / 10);
        this.brightnessSlider.setMajorTickSpacing(m.getMaxBrightness() / 5);
        this.brightnessSlider.setPaintTicks(scalePaintTicks);

        // init slider for HSV
        this.hueSlider = new JSlider(JSlider.HORIZONTAL, m.getMinHue(), m.getMaxHue(), (int) m.getHue());
        this.saturationSlider = new JSlider(JSlider.HORIZONTAL, m.getMinSaturation(), m.getMaxSaturation(), (int) m.getSaturation());
        this.valueSlider = new JSlider(JSlider.HORIZONTAL, m.getMinValue(), m.getMaxValue(), (int) m.getValue());

        this.hueLabel = new JLabel("Hue: " + m.getHue());
        this.saturationLabel = new JLabel("Saturation: " + m.getSaturation());
        this.valueLabel = new JLabel("Value: " + m.getValue());

        this.hueSlider.setMinorTickSpacing(m.getMaxHue() / 5 / 10);
        this.hueSlider.setMajorTickSpacing(m.getMaxHue() / 5);
        this.hueSlider.setPaintTicks(scalePaintTicks);

        this.saturationSlider.setMinorTickSpacing(m.getMaxSaturation() / 5 / 10);
        this.saturationSlider.setMajorTickSpacing(m.getMaxSaturation() / 5);
        this.saturationSlider.setPaintTicks(scalePaintTicks);

        this.valueSlider.setMinorTickSpacing(m.getMaxValue() / 5 / 10);
        this.valueSlider.setMajorTickSpacing(m.getMaxValue() / 5);
        this.valueSlider.setPaintTicks(scalePaintTicks);

        this.add(this.whiteBalancesLabel);
        this.add(this.wbGrayWorld);
        this.add(this.wbQM);
        this.add(this.wbRetinex);
        this.add(new JPanel());
        this.add(this.contrastLabel);
        this.add(this.contrastSlider);
        this.add(this.brightnessLabel);
        this.add(this.brightnessSlider);
        this.add(new JPanel());
        this.add(this.hueLabel);
        this.add(this.hueSlider);
        this.add(this.saturationLabel);
        this.add(this.saturationSlider);
        this.add(this.valueLabel);
        this.add(this.valueSlider);

        this.contrastSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setContrast(((JSlider) e.getSource()).getValue());
                contrastLabel.setText("Contrast: " + model.getContrast());
                ImgProcessing.rescaleOp = new RescaleOp(model.getContrast(), model.getBrightness(), null);
            }
        });

        this.brightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setBrightness(((JSlider) e.getSource()).getValue());
                brightnessLabel.setText("Brightness: " + model.getBrightness());
                ImgProcessing.rescaleOp = new RescaleOp(model.getContrast(), model.getBrightness(), null);
            }
        });

        this.hueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setHue(((JSlider) e.getSource()).getValue());
                hueLabel.setText("Hue: " + model.getHue());
                ImgProcessing.hsv[0] = model.getHue();
            }
        });

        this.saturationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setSaturation(((JSlider) e.getSource()).getValue());
                saturationLabel.setText("Saturation: " + model.getSaturation());
                ImgProcessing.hsv[1] = model.getSaturation();
            }
        });

        this.valueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setValue(((JSlider) e.getSource()).getValue());
                valueLabel.setText("Value: " + model.getValue());
                ImgProcessing.hsv[2] = model.getValue();
            }
        });

    }

    @ Override
    public void itemStateChanged(ItemEvent e){
        int index = 0;
        char c = '-';
        Object source = e.getItemSelectable();

        if(source == wbGrayWorld){
            index = 0;
            c = 'g';
            this.model.setGrayWorld(true);
        } else if(source == wbQM){
            index = 1;
            c = 'q';
            this.model.setQuadraticMapping(true);
        } else if(source == wbRetinex){
            index = 2;
            c = 'r';
            this.model.setRetinex(true);
        }

        if(e.getStateChange() == ItemEvent.DESELECTED){
            c = '-';
            if(source == wbGrayWorld){
                this.model.setGrayWorld(false);
            } else if(source == wbQM){
                this.model.setQuadraticMapping(false);
            } else if(source == wbRetinex){
                this.model.setRetinex(false);
            }
        }

        ImgProcessing.algorithms.setCharAt(index,c);
    }

}
