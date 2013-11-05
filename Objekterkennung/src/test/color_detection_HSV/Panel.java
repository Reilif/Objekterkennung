package test.color_detection_HSV;
// Import the basic graphics classes.  
// The problem here is that we read the image with OpenCV into a Mat object.  
// But OpenCV for java doesn't have the method "imshow", so, we got to use  
// java for that (drawImage) that uses Image or BufferedImage.  
// So, how to go from one the other... Here is the way...  

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private static double x = 0;
	private static double y = 0;
	private static  Mat circles;

	private BufferedImage getImage() {
		return image;
	}

	private void setImage(BufferedImage newimage) {
		image = newimage;
	}

	/**
	 * Wandelt eine openCV {@link org.opencv.core.Mat} in ein Java {@link java.awt.image.BufferedImage}, damit
	 * dieses dargestellt werden kann.
	 *
	 * @param matrix
	 * @return
	 */
	public static BufferedImage matToBufferedImage(Mat matrix) {
		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int) matrix.elemSize();
		byte[] data = new byte[cols * rows * elemSize];
		int type;
		matrix.get(0, 0, data);
		switch(matrix.channels()) {
			case 1:
				type = BufferedImage.TYPE_BYTE_GRAY;
				break;
			case 3:
				type = BufferedImage.TYPE_3BYTE_BGR;
				// bgr to rgb
				byte b;
				for(int i = 0; i < data.length; i = i + 3) {
					b = data[i];
					data[i] = data[i + 2];
					data[i + 2] = b;
				}
				break;
			default:
				return null;
		}
		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0, 0, cols, rows, data);
		return image2;
	}

	public void paintComponent(Graphics g) {
		BufferedImage temp = getImage();
		if(temp != null) {
			g.drawImage(temp, 10, 10, temp.getWidth(), temp.getHeight(), this);

			for (int i = 0; i < circles.cols(); i++) {
				 double vCircle[]=circles.get(0,i);

	             Point center=new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
	             int radius = (int)Math.round(vCircle[2]);
	             
	             g.drawOval((int)center.x -radius, (int)center.y -radius, 2*radius, 2*radius);
			}
		}
	}

	public static void main(String arg[]) {
		// Load the native library.
		System.loadLibrary("opencv_java246");

		JFrame frame = new JFrame("BasicPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);

		Panel panel = new Panel();
		frame.setContentPane(panel);
		frame.setVisible(true);

		// erzeugt das Trackframe  & Panel
		JFrame trackframe = new JFrame("TrackPanel");
		trackframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		trackframe.setSize(400, 600);
		trackframe.setLocationRelativeTo(null);

		TrackbarPanel trackPanel = new TrackbarPanel();
		trackframe.setContentPane(trackPanel);
		trackframe.setVisible(true);

		Mat webcam_image = new Mat();
		BufferedImage temp;
		VideoCapture capture = new VideoCapture(0);
		if(capture.isOpened()) {
			while(true) {
				capture.read(webcam_image);
				if(!webcam_image.empty()) {
					frame.setSize(webcam_image.width() + 40, webcam_image.height() + 60);

					Mat therehold = getTherehold(webcam_image, trackPanel.getLower(), trackPanel.getUpper());

					temp = matToBufferedImage(therehold);
					panel.setImage(temp);
					panel.repaint();
				} else {
					System.out.println(" --(!) No captured frame -- Break!");
					break;
				}
			}
		}
	}

	private static Mat getTherehold(Mat webcam_image, Scalar low, Scalar upper) {
		Mat mat = new Mat();
		webcam_image.copyTo(mat);
		Imgproc.cvtColor(webcam_image, mat, Imgproc.COLOR_BGR2HSV);

		Mat ret = new Mat();
		mat.copyTo(ret);

		Core.inRange(mat, low, upper, ret);

		Moments moments2 = Imgproc.moments(ret, true);
		double m10 = moments2.get_m10();
		double m01 = moments2.get_m01();
		double area = moments2.get_m00();

		x = m10 / area;
		y = m01 / area;
		
		Mat tempOne = new Mat();
		webcam_image.copyTo(tempOne);
		
		circles = new Mat();

		Imgproc.GaussianBlur(ret, tempOne, new Size(9, 9), 2, 2 );
		
		return ret;
	}
}