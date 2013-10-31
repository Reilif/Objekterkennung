package test.edge_detection.core;

import test.edge_detection.gui.EdgeModel;
import test.edge_detection.gui.MainFrame;

public class Main {
	public static void main(String args[]) {
		System.loadLibrary("opencv_java246");
		EdgeModel edgeModel = new EdgeModel();

		EdgeDetection edge = new EdgeDetection(edgeModel);
		MainFrame frame = new MainFrame(edgeModel);

		Thread t = new Thread(edge); //TODO Threadpool or wait/notify for different image analysis?
		t.start();
	}
}
