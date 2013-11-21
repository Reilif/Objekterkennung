package de.xtion.drone.launcher;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import de.xtion.drone.gui.*;
import org.opencv.core.Mat;

import de.xtion.drone.ARDroneController;
import de.xtion.drone.WebCamController;
import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.Navdata.Direction3D;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.manipulation.CircleDetection;
import de.xtion.drone.manipulation.ColorDetection;
import de.xtion.drone.manipulation.ColorEdgeDetection;
import de.xtion.drone.manipulation.NavController2D;
import de.xtion.drone.model.CircleModel;
import de.xtion.drone.model.CircleModel.CircleModelEvent;
import de.xtion.drone.model.ColorEdgeModel;
import de.xtion.drone.model.ColorModel;
import de.xtion.drone.model.ColorModel.ColorModelEvents;
import de.xtion.drone.model.MainModel;
import de.xtion.drone.model.util.ModelEvent;
import de.xtion.drone.model.util.ModelEventListener;
import de.xtion.drone.motioncontroller.MoveController;
import de.xtion.drone.utils.ImageUtils;
import org.monte.media.image.WhiteBalance;

public class Launcher {

	private final DrohnenSteuerung tastenStererung = new DrohnenSteuerung();

	static {
		System.loadLibrary("opencv_java246");
	}
	private final ActionShowEdgeCam actionEdge = new ActionShowEdgeCam();
	private final ActionShowColorTrack actionShowColorTrack = new ActionShowColorTrack();
	private final ActionShowCircleTrack actionShowCircleTrack = new ActionShowCircleTrack();
	private final ActionReset actionReset = new ActionReset();
	private final ActionLaunch actionLaunch = new ActionLaunch();
	private final ActionLand actionLand = new ActionLand();
	private final ActionFolgeKreis actionFolgekreis = new ActionFolgeKreis();
	private final ActionConnect actionConnect = new ActionConnect();
	private final ActionShowLiveCam actionShowLiveCam = new ActionShowLiveCam();
	private final ActionMoveController actionMoveController = new ActionMoveController();

	private boolean useWebcam = true;
	private MoveController mvController;

	private final class DrohnenSteuerung implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {

			
			
			char keyCode = e.getKeyChar();
			switch (keyCode) {
			case 'w':
				getArDroneController().setNavdata(Direction3D.FORWARD);
				break;
			case 's':
				getArDroneController().setNavdata(Direction3D.BACKWARD);
				break;
			case 'd':
				getArDroneController().setNavdata(Direction3D.RIGHT);
				break;
			case 'a':
				getArDroneController().setNavdata(Direction3D.LEFT);
				break;
			case 'W':
				getArDroneController().setNavdata(Direction3D.UP);
				break;
			case 'S':
				getArDroneController().setNavdata(Direction3D.DOWN);
				break;
			case 'D':
				getArDroneController().setNavdata(Direction3D.TURN_RIGHT);
				break;
			case 'A':
				getArDroneController().setNavdata(Direction3D.TURN_LEFT);
				break;
			default:
				break;
			}
		
		}
	}

	private final class Monitor extends JLabel {

		private int nr;

		public Monitor(int nr) {
			this.nr = nr;
			setIcon(null);
		}

		public void setImage(BufferedImage bim) {
			if (bim != null && isVisible()) {
				int height = bim.getHeight();
				int width = bim.getWidth();

				double div = (double) height / (double) width;

				Image scaled;
				if (getWidth() * div < getHeight()) {
					scaled = bim.getScaledInstance(getWidth(),
							(int) (getWidth() * div), BufferedImage.SCALE_FAST);
				} else {
					scaled = bim.getScaledInstance((int) (getHeight() / div),
							getHeight(), BufferedImage.SCALE_FAST);
				}

				ImageIcon icon = new ImageIcon(scaled);
				setIcon(icon);
			}
		}

		@Override
		public void setIcon(Icon icon) {
			if (icon == null) {
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
			webcamController.addOBJController(colorDetection);
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

	private CircleDetection circleDetection;

	private final class ActionShowCircleTrack extends AbstractAction {

		public ActionShowCircleTrack() {
			putValue(NAME, "Zeige Kreisbild");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final CircleModel circleModel = mainModel.getCircleModel();
			circleDetection = new CircleDetection(circleModel,
					mainModel.getColorModel(), mainModel.getEdgeModel());
			if (useWebcam) {
				webcamController.addOBJController(circleDetection);
			} else {
				getArDroneController().addOBJController(circleDetection);
			}

			// starteObjektsteuerung();

			circleModel.addModelEventListener(CircleModelEvent.CIRCLE_IMG,
					new ModelEventListener() {
						@Override
						public void actionPerformed(ModelEvent event) {
							BufferedImage colorImage = circleModel
									.getCircleImage();
							getMonitor3().setImage(colorImage);
						}
					});
		}
	}

	private final class ActionShowEdgeCam extends AbstractAction {

		public ActionShowEdgeCam() {
			putValue(NAME, "Zeige ColorEdgebild");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final ColorEdgeModel edgeModel = mainModel.getColorEdgeModel();
			final ColorEdgeDetection edgeDetection = new ColorEdgeDetection(
					edgeModel);
			webcamController.addOBJController(edgeDetection);
			edgeModel.addModelEventListener(
                    ColorEdgeModel.ColorEdgeModelEvent.COLOR_EDGE_IMAGE,
                    new ModelEventListener() {

                        @Override
                        public void actionPerformed(ModelEvent event) {
                            BufferedImage edgeImage = edgeModel
                                    .getColorEdgeImage();
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

	private final class ActionLaunch extends AbstractAction {
		public ActionLaunch() {
			putValue(NAME, "Startet Drohne");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			getArDroneController().launch();
		}
	}

	private final class ActionFolgeKreis extends AbstractAction {

		public ActionFolgeKreis() {
			putValue(NAME, "Startet Kreistracking");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			starteObjektsteuerung();
		}
	}

	private final class ActionLand extends AbstractAction {
		public ActionLand() {
			putValue(NAME, "Landet Drohne");
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			getArDroneController().land();
		}
	}

	private final class ActionMoveController extends AbstractAction {
		public ActionMoveController() {
			putValue(NAME, "Aktiviert MoveController");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			getMoveController();
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

			OBJController contr = new OBJController() {

				@Override
				public void processImage(BufferedImage data) {
					getMonitor0().setImage(data);
				}

				@Override
				public void addNavController(NavController contr) {
				}
			};

			if (useWebcam) {
				webcamController.addOBJController(contr);
			} else {
				getArDroneController().addOBJController(contr);
			}
		}
	}

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.start();
	}

	public MoveController getMoveController() {
		if (mvController == null) {
			mvController = new MoveController(getArDroneController(), this);
		}

		return mvController;
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
	private WebCamController webcamController;

	private ColorEdgeModel.EdgePosition prevPos;

	public Launcher() {

		jFrame = new JFrame();
		jFrame.setTitle("Launcher XTion Drone");

		jFrame.setJMenuBar(getMenu());
		mainModel = new MainModel();

		jFrame.setContentPane(getContent());
		webcamController = new WebCamController();
		arDroneController = new ARDroneController();

		jFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				jFrame.setVisible(false);
				getArDroneController().stop();
				jFrame.dispose();

				System.exit(0);
			}
		});

		enableActions(true);
		jFrame.pack();

		prevPos = null;

		mainModel.getColorEdgeModel().addModelEventListener(
				ColorEdgeModel.ColorEdgeModelEvent.COLOR_EDGE_POS,
				new ModelEventListener() {
					@Override
					public void actionPerformed(ModelEvent event) {
						if (mainModel.getColorEdgeModel().getEdgePosition() != prevPos) {
							prevPos = mainModel.getColorEdgeModel()
									.getEdgePosition();
							System.out.println(prevPos.name());
						}
					}
				});
		
		jFrame.addKeyListener(tastenStererung);
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
		jTabbedPane.addTab("Steuerung ColorEdgepanel", new JScrollPane(
				new ColorEdgeAdjustment(mainModel.getColorEdgeModel())));
		jTabbedPane.addTab("Steuerung Circlepanel", new JScrollPane(new CircleAdjustment(mainModel.getCircleModel())));
		jTabbedPane.addTab("Steuerung Edgepanel", new JScrollPane(
				new EdgeAdjustment(mainModel.getEdgeModel())));
        jTabbedPane.addTab("Weißabgleich", new WhiteBalances());

		Component jButton = new TextField();
		jButton.addKeyListener(tastenStererung);
		jTabbedPane.addTab("Tastatursteuerung", jButton);
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
		menuDrone.addSeparator();
		menuDrone.add(new JMenuItem(actionLaunch));
		menuDrone.add(new JMenuItem(actionLand));
		menuDrone.addSeparator();
		menuDrone.add(new JMenuItem(actionFolgekreis));

		JMenu menuAnsicht = new JMenu();
		menuAnsicht.setText("Ansicht");
		ret.add(menuAnsicht);

		menuAnsicht.add(new JMenuItem(actionShowLiveCam));
		menuAnsicht.add(new JMenuItem(actionEdge));
		menuAnsicht.add(new JMenuItem(actionShowColorTrack));
		menuAnsicht.add(new JMenuItem(actionShowCircleTrack));

		JMenu menuController = new JMenu();
		menuController.setText("Controller");
		ret.add(menuController);
		menuController.add(new JMenuItem(actionMoveController));
		return ret;
	}

	private void enableActions(boolean b) {
		actionShowLiveCam.setEnabled(b);
		actionReset.setEnabled(b);
		actionEdge.setEnabled(b);
		actionShowColorTrack.setEnabled(b);
		actionShowCircleTrack.setEnabled(b);

		actionFolgekreis.setEnabled(b);

		actionLaunch.setEnabled(b);
		actionLand.setEnabled(b);
	}

	private void start() {
		jFrame.setVisible(true);
		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public DrohnenController getArDroneController() {
		return arDroneController;
	}

	public void starteObjektsteuerung() {
		NavController2D navController2D = new NavController2D();
		circleDetection.addNavController(navController2D);
		navController2D.addDrohnenController(getArDroneController());
	}

}
