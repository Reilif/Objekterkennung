package test.test_gg;
import org.opencv.core.Scalar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;



class TrackbarPanel extends JPanel  {
    private int hMin = 58;
    private int sMin = 179;
    private int vMin = 180;

    private int hMax = 58;
    private int sMax = 64;
    private int vMax = 82;

    JSlider hMinSlider;
    JSlider sMinSlider;
    JSlider vMinSlider;

    JSlider hMaxSlider;
    JSlider sMaxSlider;
    JSlider vMaxSlider;

    JLabel hMinLabel;
    JLabel sMinLabel;
    JLabel vMinLabel;

    JLabel hMaxLabel;
    JLabel sMaxLabel;
    JLabel vMaxLabel;

    Scalar upper;
    Scalar low;



    public TrackbarPanel() {
        super();

        // Anlegen der JSlider
        hMinSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, hMin);
        sMinSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, sMin);
        vMinSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, vMin);

        hMaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 180, hMax);
        sMaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, sMax);
        vMaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, vMax);

        // Anlegen der JLabels
        hMinLabel = new JLabel("hMin: " + hMin);
        sMinLabel = new JLabel("sMin: " + sMin);
        vMinLabel = new JLabel("vMin: " + vMin);

        hMaxLabel = new JLabel("hMax: " + hMax);
        sMaxLabel = new JLabel("sMax: " + sMax);
        vMaxLabel = new JLabel("vMax: " + vMax);

        //Anzeige der Skala
        hMinSlider.setMajorTickSpacing(10);
        hMinSlider.setMinorTickSpacing(1);
        hMinSlider.setPaintTicks(true);

        sMinSlider.setMajorTickSpacing(10);
        sMinSlider.setMinorTickSpacing(1);
        sMinSlider.setPaintTicks(true);

        vMinSlider.setMajorTickSpacing(10);
        vMinSlider.setMinorTickSpacing(1);
        vMinSlider.setPaintTicks(true);

        hMaxSlider.setMajorTickSpacing(10);
        hMaxSlider.setMinorTickSpacing(1);
        hMaxSlider.setPaintTicks(true);

        sMaxSlider.setMajorTickSpacing(10);
        sMaxSlider.setMinorTickSpacing(1);
        sMaxSlider.setPaintTicks(true);

        vMaxSlider.setMajorTickSpacing(10);
        vMaxSlider.setMinorTickSpacing(1);
        vMaxSlider.setPaintTicks(true);

        upper = new Scalar(hMin, sMin, vMin);
        low = new Scalar(hMax, sMax, vMax);


        // Layout Ebene 0
        GridLayout layoutEbene0 = new GridLayout(14, 2);
        this.setLayout(layoutEbene0);	// 14 Zeilen, 2 Spalte



        // Hinzufügen der Elemente
        this.add(hMinLabel);
        this.add(hMinSlider);
        this.add(sMinLabel);
        this.add(sMinSlider);
        this.add(vMinLabel);
        this.add(vMinSlider);
        this.add(new JPanel());
        this.add(new JPanel());
        this.add(hMaxLabel);
        this.add(hMaxSlider);
        this.add(sMaxLabel);
        this.add(sMaxSlider);
        this.add(vMaxLabel);
        this.add(vMaxSlider);

        //Fuer den hMinSlider
        hMinSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    hMin = (int) source.getValue();
                    upper = new Scalar(hMin, sMin, vMin);
                    hMinLabel.setText("hMin: "+ hMin);

                }
            }
        });

        //Fuer den sMinSlider
        sMinSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    sMin = (int) source.getValue();
                    upper = new Scalar(hMin, sMin, vMin);
                    sMinLabel.setText("sMin: "+ sMin);
                }
            }
        });

        //Fuer den vMinSlider
        vMinSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    vMin = (int) source.getValue();
                    upper = new Scalar(hMin, sMin, vMin);
                    vMinLabel.setText("vMin: "+ vMin);
                }
            }
        });

        //Fuer den hMinSlider
        hMaxSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    hMax = (int) source.getValue();
                    low = new Scalar(hMax, sMax, vMax);
                    hMaxLabel.setText("hMax: "+ hMax);

                }
            }
        });

        //Fuer den sMinSlider
        sMaxSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    sMax = (int) source.getValue();
                    low = new Scalar(hMax, sMax, vMax);
                    sMaxLabel.setText("sMax: "+ sMax);
                }
            }
        });

        //Fuer den vMinSlider
        vMaxSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    vMax = (int) source.getValue();
                    low = new Scalar(hMax, sMax, vMax);
                    vMaxLabel.setText("vMax: "+ vMax);
                }
            }
        });

    }




    /**
     * Getter fuer die Slider
     * @return
     */
    public JSlider getHMinSlider() {
        return hMinSlider;
    }
    public JSlider getSMinSlider() {
        return sMinSlider;
    }
    public JSlider getVMinSlider() {
        return vMinSlider;
    }

    public JSlider getHMaxSlider() {
        return hMaxSlider;
    }
    public JSlider getSMaxSlider() {
        return sMaxSlider;
    }
    public JSlider getVMaxSlider() {
        return vMaxSlider;
    }


    /**
     * Getter fuer die Werte
     * @return
     */
    public Scalar getUpper() {
        return upper;
    }
    public Scalar getLow() {
        return low;
    }



}
