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

    Scalar upper;
    Scalar low;



    public TrackbarPanel() {
        super();

        // Anlegen der JSlider
        hMinSlider = new JSlider(JSlider.HORIZONTAL, 0, 240, hMin);
        sMinSlider = new JSlider(JSlider.HORIZONTAL, 0, 240, sMin);
        vMinSlider = new JSlider(JSlider.HORIZONTAL, 0, 240, vMin);

        hMaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 240, hMax);
        sMaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 240, sMax);
        vMaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 240, vMax);

        //Turn on major tick marks.
        hMinSlider.setMajorTickSpacing(10);
        hMinSlider.setMinorTickSpacing(1);
        hMinSlider.setPaintTicks(true);

        upper = new Scalar(hMin, sMin, vMin);
        low = new Scalar(hMax, sMax, vMax);


        // Layout Ebene 0
        GridLayout layoutEbene0 = new GridLayout(7, 1);
        this.setLayout(layoutEbene0);	// 7 Zeilen, 2 Spalte

        // Hinzufügen der Elemente
        this.add(hMinSlider);
        this.add(sMinSlider);
        this.add(vMinSlider);
        this.add(new JPanel());
        this.add(hMaxSlider);
        this.add(sMaxSlider);
        this.add(vMaxSlider);

        //Fuer den hMinSlider
        hMinSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    hMin = (int) source.getValue();
                    upper = new Scalar(hMin, sMin, vMin);

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
