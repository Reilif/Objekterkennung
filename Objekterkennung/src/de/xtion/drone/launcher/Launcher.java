package de.xtion.drone.launcher;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.xtion.drone.ARDroneController;
import de.xtion.drone.ColorOBJController;

public class Launcher {

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
	
	public Launcher() {
		
		jFrame = new JFrame();
		jFrame.setTitle("Launcher XTion Drone");
		
		arDroneController = new ARDroneController();
		
		jFrame.setLayout(new GridLayout(0, NUMBER_OF_COLS_MENUE));
		
		jFrame.add(getButtonConnectDrone());
		jFrame.add(getButtonShowVideo());
		jFrame.add(getButtonColoredTrack());
		
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
	

	private JButton getButtonShowVideo() {
		if(vidButton == null){
			vidButton = new JButton();
			vidButton.setText("Zeige Videoaufzeichnung");
			vidButton.setEnabled(false);
			vidButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					getArDroneController().showLiveCam();
				}
			});
		}
		return vidButton;
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


	private JButton getButtonConnectDrone() {
		if(connectButton == null){
			connectButton = new JButton();
			connectButton.setText("Verbinde mit der Drohne");
			connectButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean connectToDrone = getArDroneController().connectToDrone();
					
					if(connectToDrone){
						enableButtons(true);
					}else{
						enableButtons(false);
					}
				}
			});
		}
		return connectButton;
	}

	private void enableButtons(boolean b) {
		getButtonShowVideo().setEnabled(b);
		getButtonColoredTrack().setEnabled(b);
	}


	private void start() {
		jFrame.setSize(400, 600);
		jFrame.setVisible(true);
	}


	public ARDroneController getArDroneController() {
		return arDroneController;
	}

}
