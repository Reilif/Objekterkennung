package de.xtion.drone.gui;

import test.edge_detection.gui.EdgeModel;
import test.edge_detection.objects.gui.ModelEvent;
import test.edge_detection.objects.gui.ModelEventListener;

import javax.swing.*;
import java.awt.*;

/**
 * The class draws a BufferedImage, obtained from the EdgeModel as often as there is a new one
 */
public class ImagePanel extends JPanel {
	EdgeModel model;

	/**
	 * @param edgeModel The parameter represents the EdgeModel where the object gets the BufferedImage from
	 */
	public ImagePanel(EdgeModel edgeModel) {
		super(new GridLayout());
		this.model = edgeModel;
		setVisible(true);

		ModelEventListener sizeListener = new ModelEventListener() { //resize the frame when video images are available
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override public void run() {
						setPreferredSize(new Dimension(model.getVideoWidth(), model.getVideoHeight()));
					}
				});
			}
		};
		model.addModelEventListener(EdgeModel.EdgeModelEvent.V_WIDTH, sizeListener);
		model.addModelEventListener(EdgeModel.EdgeModelEvent.V_HEIGHT, sizeListener);

		model.addModelEventListener(EdgeModel.EdgeModelEvent.EDGE_IMG, new ModelEventListener() {  //repaint the frame when a new video image is available
			@Override public void actionPerformed(ModelEvent event) {
				SwingUtilities.invokeLater(new Runnable() {   //FIXME reusable - create only once?
					@Override public void run() {
						repaint();
					}
				});
			}
		});
	}

	/**
	 * Paints the BufferedImage from the EdgeModel
	 * @param g The passed down graphics object
	 */
	@Override protected void paintComponent(Graphics g) {
		if(model.getEdgeImage() != null) {
			g.drawImage(model.getEdgeImage(), 0, 0, model.getVideoWidth(), model.getVideoHeight(), this);
		}
	}
}
