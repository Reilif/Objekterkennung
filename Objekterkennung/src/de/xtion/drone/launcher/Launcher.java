package de.xtion.drone.launcher;

import de.xtion.drone.WebCamController;
import de.xtion.drone.gui.CircleAdjustment;
import de.xtion.drone.gui.ColorAdjustment;
import de.xtion.drone.gui.EdgeAdjustment;
import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.manipulation.CircleDetection;
import de.xtion.drone.manipulation.ColorDetection;
import de.xtion.drone.manipulation.EdgeDetection;
import de.xtion.drone.model.CircleModel;
import de.xtion.drone.model.CircleModel.CircleModelEvent;
import de.xtion.drone.model.ColorModel;
import de.xtion.drone.model.ColorModel.ColorModelEvents;
import de.xtion.drone.model.EdgeModel;
import de.xtion.drone.model.EdgeModel.EdgeModelEvent;
import de.xtion.drone.model.MainModel;
import de.xtion.drone.model.util.ModelEvent;
import de.xtion.drone.model.util.ModelEventListener;
import de.xtion.drone.utils.ImageUtils;
import org.opencv.core.Mat;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Launcher {

	static {
		System.loadLibrary("opencv_java246");
	}

	private static final Border MONITOR_BORDER = BorderFactory.createLineBorder(Color.BLUE);

	private final ActionShowEdgeCam     actionEdge            = new ActionShowEdgeCam();
	private final ActionShowColorTrack  actionShowColorTrack  = new ActionShowColorTrack();
	private final ActionShowCircleTrack actionShowCircleTrack = new ActionShowCircleTrack();
	private final ActionReset           actionReset           = new ActionReset();
	private final ActionConnect         actionConnect         = new ActionConnect();
	private final ActionShowLiveCam     actionShowLiveCam     = new ActionShowLiveCam();

	private final class Monitor extends JLabel {

		private int nr;

		public Monitor(int nr) {
			this.nr = nr;
			setIcon(null);
		}

		public void setImage(BufferedImage bim) {
			if(bim != null) {
				int height = bim.getHeight();
				int width = bim.getWidth();

				double div = (double) height / (double) width;

				Image scaled;
				if(getWidth() * div > getHeight()) {
					scaled = bim.getScaledInstance(getWidth(), (int) (getWidth() * div), BufferedImage.SCALE_FAST);
				} else {
					scaled = bim.getScaledInstance((int) (getHeight() / div), getHeight(), BufferedImage.SCALE_FAST);
				}

				ImageIcon icon = new ImageIcon(scaled);
				setIcon(icon);
			}
		}

		@Override
		public void setIcon(Icon icon) {
			if(icon == null) {
				setText("Kein Bild auf Monitor " + nr);
			} else {
				setText("");
			}
			super.setIcon(icon);
		}
	}

	private final class ActionShowColorTrack extends AbstractAction {
		public ActionShowColorTrack() {
			putValue(NAME, "Zeige Farbtracking");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final ColorModel colorModel = mainModel.getColorModel();
			ColorDetection colorDetection = new ColorDetection(colorModel);
			getArDroneController().addOBJController(colorDetection);
			colorModel.addModelEventListener(ColorModelEvents.COLOR_IMAGE,
					new ModelEventListener() {

						@Override
						public void actionPerformed(ModelEvent event) {
							Mat colorImage = colorModel.getColorImage();
							BufferedImage matToBufferedImage = ImageUtils
									.matToBufferedImage(colorImage);

							getMonitor1().setImage(matToBufferedImage);
						}
					});
		}
	}

	private final class ActionShowCircleTrack extends AbstractAction {
		public ActionShowCircleTrack() {
			putValue(NAME, "Zeige Kreisbild");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final CircleModel circleModel = mainModel.getCircleModel();
			final CircleDetection circleDetection = new CircleDetection(
					circleModel, mainModel.getColorModel(),
					mainModel.getEdgeModel());
			getArDroneController().addOBJController(circleDetection);

			circleModel.addModelEventListener(CircleModelEvent.CIRCLE_IMG, new ModelEventListener() {

				@Override
				public void actionPerformed(ModelEvent event) {
					BufferedImage colorImage = circleModel.getCircleImage();
					getMonitor3().setImage(colorImage);
				}
			});
		}
	}

	private final class ActionShowEdgeCam extends AbstractAction {

		public ActionShowEdgeCam() {
			putValue(NAME, "Zeige Edgebild");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final EdgeModel edgeModel = mainModel.getEdgeModel();
			final EdgeDetection edgeDetection = new EdgeDetection(edgeModel);
			getArDroneController().addOBJController(edgeDetection);
			edgeModel.addModelEventListener(EdgeModelEvent.EDGE_IMG,
					new ModelEventListener() {

						@Override
						public void actionPerformed(ModelEvent event) {
							BufferedImage edgeImage = edgeModel.getEdgeImage();
							getMonitor2().setImage(edgeImage);
						}
					});
		}
	}

	private final class ActionReset extends AbstractAction {
		public ActionReset() {
			putValue(NAME, "Reset Drohne");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			getArDroneController().resetDrone();
		}
	}

	private final class ActionConnect extends AbstractAction {

		public ActionConnect() {
			putValue(NAME, "Verbinde mit der Drohne");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean connectToDrone = getArDroneController().connectToDrone();

			if (connectToDrone) {
				enableActions(true);
			} else {
				enableActions(false);
			}
		}
	}

	private final class ActionShowLiveCam extends AbstractAction {

		public ActionShowLiveCam() {
			putValue(NAME, "Zeige Livebild");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			getArDroneController().addOBJController(new OBJController() {
				
				@Override
				public void processImage(BufferedImage data) {
					getMonitor0().setImage(data);
				}
				
				@Override
				public void addNavController(NavController contr) {
				}
			});
		}
	}

	private static final int NUMBER_OF_COLS_MENUE = 1;

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.start();
	}

	private final DrohnenController arDroneController;
	private JFrame jFrame;
	private Monitor monitor1;

	public Monitor getMonitor1() {
		return monitor1;
	}

	public Monitor getMonitor2() {
		return monitor2;
	}

	public Monitor getMonitor3() {
		return monitor3;
	}

	public Monitor getMonitor0() {
		return monitor0;
	}

	private Monitor monitor2;
	private Monitor monitor3;
	private Monitor monitor0;
	private final MainModel mainModel;

	private JPanel content;

	private JPanel centerPanel;

	public Launcher() {

		jFrame = new JFrame();
		jFrame.setTitle("Launcher XTion Drone");

		jFrame.setJMenuBar(getMenu());
		mainModel = new MainModel();

		jFrame.setContentPane(getContent());
		arDroneController = new WebCamController();

		// arDroneController = new ARDroneController();

		jFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				jFrame.setVisible(false);
				getArDroneController().stop();
				jFrame.dispose();

				
				Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
				for (Thread thread : allStackTraces.keySet()) {
					if(thread.isAlive()){
					}
				}
				System.exit(0);
			}
		});
	}

	private JPanel getContent() {
		if (content == null) {
			content = new JPanel();
			content.setLayout(new BorderLayout());
			content.setBorder(BorderFactory.createLoweredBevelBorder());

			content.add(getCenterPanel(), BorderLayout.CENTER);
			content.add(getSettingsPanel(), BorderLayout.WEST);
			content.add(getInformationPanel(), BorderLayout.SOUTH);
		}
		return content;
	}

	private Component getInformationPanel() {
		return new JPanel();
	}

	private Component getSettingsPanel() {
		JPanel jPanel = new JPanel();

		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.addTab("Steuerung Farbpanel", new JScrollPane(new ColorAdjustment(mainModel.getColorModel())));
		jTabbedPane.addTab("Steuerung Edgepanel", new JScrollPane(
				new EdgeAdjustment(mainModel.getEdgeModel())));
		jTabbedPane.addTab("Steuerung Circlepanel", new JScrollPane(new CircleAdjustment(mainModel.getCircleModel())));

		jPanel.add(jTabbedPane);
		return jPanel;
	}

	private Component getCenterPanel() {
		if (centerPanel == null) {

			centerPanel = new JPanel();
			centerPanel.setLayout(new GridLayout(2, 2));

			monitor0 = new Monitor(0);
			monitor1 = new Monitor(1);
			monitor2 = new Monitor(2);
			monitor3 = new Monitor(3);

			centerPanel.add(monitor0);
			centerPanel.add(monitor1);
			centerPanel.add(monitor2);
			centerPanel.add(monitor3);
		}

		return centerPanel;
	}

	private JMenuBar getMenu() {
		JMenuBar ret = new JMenuBar();

		JMenu menuDrone = new JMenu();
		menuDrone.setText("Drohne");
		ret.add(menuDrone);
		menuDrone.add(new JMenuItem(actionConnect));
		menuDrone.add(new JMenuItem(actionReset));

		JMenu menuAnsicht = new JMenu();
		menuAnsicht.setText("Ansicht");
		ret.add(menuAnsicht);

		menuAnsicht.add(new JMenuItem(actionShowLiveCam));
		menuAnsicht.add(new JMenuItem(actionEdge));
		menuAnsicht.add(new JMenuItem(actionShowColorTrack));
		menuAnsicht.add(new JMenuItem(actionShowCircleTrack));
		return ret;
	}

	private void enableActions(boolean b) {
		actionShowLiveCam.setEnabled(b);
		actionReset.setEnabled(b);
		actionEdge.setEnabled(b);
		actionShowColorTrack.setEnabled(b);
		actionShowCircleTrack.setEnabled(b);
	}

	private void start() {
		jFrame.setVisible(true);
		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public DrohnenController getArDroneController() {
		return arDroneController;
	}

}
