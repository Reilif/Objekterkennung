package de.xtion.drone.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import de.xtion.drone.ARDroneController;
import de.xtion.drone.ColorOBJController;
import de.xtion.drone.manipulation.EdgeDetection;
import de.xtion.drone.model.EdgeModel;
import de.xtion.drone.model.EdgeModel.EdgeModelEvent;
import de.xtion.drone.model.MainModel;
import de.xtion.drone.model.util.ModelEvent;
import de.xtion.drone.model.util.ModelEventListener;

public class Launcher {

	static {
		System.loadLibrary("opencv_java246");
	}
	
	private final  ActionShowEdgeCam actionEdge = new ActionShowEdgeCam();
	private static final Border MONITOR_BORDER = BorderFactory.createLineBorder(Color.BLUE);
	private final ActionReset actionReset = new ActionReset();
	private final ActionConnect actionConnect = new ActionConnect();
	private final ActionShowLiveCam actionShowLiveCam = new ActionShowLiveCam();


	private final class ActionShowEdgeCam extends AbstractAction {

		public ActionShowEdgeCam() {
			putValue(NAME, "Zeige Edgebild");
			setEnabled(false);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			final EdgeModel edgeModel = mainModel.getEdgeModel();
			EdgeDetection edgeDetection = new EdgeDetection(edgeModel);
			getArDroneController().addOBJController(edgeDetection);
			
			edgeModel.addModelEventListener(EdgeModelEvent.EDGE_IMG, new ModelEventListener() {
				
				@Override
				public void actionPerformed(ModelEvent event) {
					BufferedImage edgeImage = edgeModel.getEdgeImage();
					getMonitor2().setIcon(new ImageIcon(edgeImage));
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
			
			if(connectToDrone){
				enableActions(true);
			}else{
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
			getArDroneController().showLiveCam(getMonitor0());
		}
	}


	private static final int NUMBER_OF_COLS_MENUE = 1;


	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.start();
	}

	private JButton connectButton;
	private final ARDroneController arDroneController;
	private JButton vidButton;
	private JFrame jFrame;
	private JButton colorTrackButton;
	private JLabel monitor1;
	public JLabel getMonitor1() {
		return monitor1;
	}


	public JLabel getMonitor2() {
		return monitor2;
	}


	public JLabel getMonitor3() {
		return monitor3;
	}


	public JLabel getMonitor0() {
		return monitor0;
	}

	private JLabel monitor2;
	private JLabel monitor3;
	private JLabel monitor0;
	private final MainModel mainModel;
	
	public Launcher() {
		
		jFrame = new JFrame();
		jFrame.setTitle("Launcher XTion Drone");
		
		jFrame.setJMenuBar(getMenu());
		
		jFrame.setContentPane(getContent());
		mainModel = new MainModel();
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
	}
	

	private Container getContent() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		
		jPanel.add(getCenterPanel(), BorderLayout.CENTER);
		jPanel.add(getSettingsPanel(), BorderLayout.WEST);
		jPanel.add(getInformationPanel(), BorderLayout.SOUTH);
		return jPanel;
	}


	private Component getInformationPanel() {
		return new JPanel();
	}


	private Component getSettingsPanel() {
		return new JPanel();
	}


	private Component getCenterPanel() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(2, 2));
		
		monitor0 = new JLabel(){
			@Override
			public void setIcon(Icon icon) {
				super.setIcon(icon);
				if(icon == null){
					setText("Kein Bild auf Monitor 0");
				}else{
					setText("");
				}
			}
		};
		
		monitor0.setBorder(MONITOR_BORDER);
		monitor1 = new JLabel(){
			@Override
			public void setIcon(Icon icon) {
				super.setIcon(icon);
				if(icon == null){
					setText("Kein Bild auf Monitor 1");
				}else{
					setText("");
				}
			}
		};
		monitor2 = new JLabel(){
			@Override
			public void setIcon(Icon icon) {
				super.setIcon(icon);
				if(icon == null){
					setText("Kein Bild auf Monitor 2");
				}else{
					setText("");
				}
			}
		};
		monitor3 = new JLabel(){
			@Override
			public void setIcon(Icon icon) {
				super.setIcon(icon);
				if(icon == null){
					setText("Kein Bild auf Monitor 3");
				}else{
					setText("");
				}
			}
		};
		
		jPanel.add(monitor0);
		jPanel.add(monitor1);
		jPanel.add(monitor2);
		jPanel.add(monitor3);
		
		return jPanel;
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
		return ret;
	}



	private JButton getButtonColoredTrack() {
		if(colorTrackButton == null){
			colorTrackButton = new JButton();
			colorTrackButton.setText("Starte Farbtracking");
			colorTrackButton.setEnabled(false);
			colorTrackButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					getArDroneController().addOBJController(new ColorOBJController());
				}
			});
		}
		return colorTrackButton;
	}


	private void enableActions(boolean b) {
		actionShowLiveCam.setEnabled(b);
		actionReset.setEnabled(b);
		actionEdge.setEnabled(b);
		getButtonColoredTrack().setEnabled(b);
	}


	private void start() {
		jFrame.setVisible(true);
		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}


	public ARDroneController getArDroneController() {
		return arDroneController;
	}

}
