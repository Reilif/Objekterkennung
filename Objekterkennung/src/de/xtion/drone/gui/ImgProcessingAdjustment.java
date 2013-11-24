package de.xtion.drone.gui;

import de.xtion.drone.model.ImgProcessingModel;
import org.monte.media.image.WhiteBalance;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
    private JLabel contrastLabel;
    private JLabel brightnessLabel;

    public static StringBuffer algorithms;
    public static RescaleOp rescaleOp;

    public ImgProcessingAdjustment(ImgProcessingModel m){
        super(new GridLayout(0,1));

        // indicate image adjustments
        ImgProcessingAdjustment.algorithms = new StringBuffer("---");
        ImgProcessingAdjustment.rescaleOp = new RescaleOp(m.getContrast(), m.getBrightness(), null);

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

        this.contrastLabel = new JLabel("Contrast: " + m.getContrast());
        this.brightnessLabel = new JLabel("Brightness: " + m.getBrightness());

        this.contrastSlider.setMinorTickSpacing(m.getMaxContrast() / 5 / 10);
        this.contrastSlider.setMajorTickSpacing(m.getMaxContrast() / 5);
        this.contrastSlider.setPaintTicks(scalePaintTicks);

        this.brightnessSlider.setMinorTickSpacing(m.getMaxBrightness() / 5 / 10);
        this.brightnessSlider.setMajorTickSpacing(m.getMaxBrightness() / 5);
        this.brightnessSlider.setPaintTicks(scalePaintTicks);

        this.add(this.wbGrayWorld);
        this.add(this.wbQM);
        this.add(this.wbRetinex);
        this.add(new JPanel());
        this.add(new JPanel());
        this.add(new JPanel());
        this.add(new JPanel());
        this.add(this.contrastSlider);
        this.add(this.contrastLabel);
        this.add(new JPanel());
        this.add(new JPanel());
        this.add(this.brightnessSlider);
        this.add(this.brightnessLabel);

        this.contrastSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setContrast(((JSlider) e.getSource()).getValue());
                contrastLabel.setText("Contrast: " + model.getContrast());
                ImgProcessingAdjustment.rescaleOp = new RescaleOp(model.getContrast(), model.getBrightness(), null);
            }
        });

        this.brightnessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setBrightness(((JSlider) e.getSource()).getValue());
                brightnessLabel.setText("Brightness: " + model.getBrightness());
                ImgProcessingAdjustment.rescaleOp = new RescaleOp(model.getContrast(), model.getBrightness(), null);
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
        } else if(source == wbQM){
            index = 1;
            c = 'q';
        } else if(source == wbRetinex){
            index = 2;
            c = 'r';
        }

        if(e.getStateChange() == ItemEvent.DESELECTED){
            c = '-';
        }

        ImgProcessingAdjustment.algorithms.setCharAt(index,c);
    }

    private static BufferedImage setWhiteBalance(BufferedImage data){
        if(ImgProcessingAdjustment.algorithms.toString().equals("g--")){
             return WhiteBalance.whiteBalanceGreyworld(data);
        } else if(ImgProcessingAdjustment.algorithms.toString().equals("-q-")){
            return WhiteBalance.whiteBalanceQM(data);
        } else if(ImgProcessingAdjustment.algorithms.toString().equals("--r")){
            return WhiteBalance.whiteBalanceRetinex(data);
        } else if(ImgProcessingAdjustment.algorithms.toString().equals("gq-")){
            return WhiteBalance.whiteBalanceQM(WhiteBalance.whiteBalanceGreyworld(data));
        } else if(ImgProcessingAdjustment.algorithms.toString().equals("g-r")){
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceGreyworld(data));
        } else if(ImgProcessingAdjustment.algorithms.toString().equals("-qr")){
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceQM(data));
        } else if(ImgProcessingAdjustment.algorithms.toString().equals("gqr")) {
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceQM(WhiteBalance.whiteBalanceGreyworld(data)));
        } else {
            return data;
        }
    }

    private static BufferedImage setBrightnessAndContrast(BufferedImage data){
        return ImgProcessingAdjustment.rescaleOp.filter(data, data);
    }

    public static BufferedImage setImgAdjustments(BufferedImage data){
        return ImgProcessingAdjustment.setBrightnessAndContrast(ImgProcessingAdjustment.setWhiteBalance(data));
    }
}
