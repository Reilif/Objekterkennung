package test.edge_detection.gui;

import test.edge_detection.objects.gui.ModelEvent;
import test.edge_detection.objects.gui.ModelEventListener;

import javax.swing.*;
import java.awt.*;

/**
 * The MainFrame is the frame in which all panels are placed to be displayed
 */
public class MainFrame extends JFrame {
	private EdgeModel       model;
	private ImagePanel      imagePanel;
	private AdjustmentPanel adjustmentPanel;

	/**
	 * @param edgeModel The parameter represents the Model the Frame is getting its Information from
	 */
	public MainFrame(EdgeModel edgeModel) {
		super("MainFrame - Edge Detection");
		this.model = edgeModel;

		//Frame
		getContentPane().setLayout(new GridLayout(1, 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerOnScreen(); //Important for Dual-Monitor!!
		setResizable(false);
		setVisible(true);

		//Adjustment Panel
		adjustmentPanel = new AdjustmentPanel(model);
		getContentPane().add(adjustmentPanel);

		//Image Panel
		imagePanel = new ImagePanel(this.model);
		getContentPane().add(imagePanel);

		//Listener
		ModelEventListener sizeListener = new ModelEventListener() { //repack the frame when video images are available
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						pack();
					}
				});
			}
		};
		edgeModel.addModelEventListener(EdgeModel.EdgeModelEvent.V_WIDTH, sizeListener);
		edgeModel.addModelEventListener(EdgeModel.EdgeModelEvent.V_HEIGHT, sizeListener);
	}

	/**
	 * This method sets the Frame in the center of the screen - needed for dual monitor set ups.
	 */
	void centerOnScreen() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}
}
