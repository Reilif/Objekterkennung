package test.test_gg;
import org.opencv.core.Scalar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;



class TrackbarPanel extends JPanel implements ChangeListener {
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
                        JSlider source = (JSlider)e.getSource();
                        if (!source.getValueIsAdjusting()) {
                            int hMin = (int)source.getValue();
                        }
                    }
                });

        sMinSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int sMin = (int)source.getValue();
                }
            }
        });


    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            if (source.getName() == "hMinSlider" ){
                int hMin = (int)source.getValue();

            }else if (source.getName() == "sMinSlider" ) {
                int sMin = (int)source.getValue();

            }else if (source.getName() == "vMinSlider" ) {
                int vMin = (int)source.getValue();

            }else if (source.getName() == "hMaxSlider" ) {
                int hMax = (int)source.getValue();
            }
        }
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
        return upper = new Scalar(hMin, sMin, vMin);
    }
    public Scalar getLow() {
        return low = new Scalar(hMax, sMax, vMax);
    }



}
