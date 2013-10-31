package de.xtion.drone.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class ImageUtils {

	/**
	 * Wandellt ein Java {@link BufferedImage} in eine openCV {@link Mat}, damit
	 * mit dieser Berechnungen durchgeführt werden können.
	 * 
	 * @param bufImg
	 * @return
	 */
	public static Mat bufferedImageToMat(BufferedImage bufImg) {
		Mat ret = new Mat(bufImg.getHeight(), bufImg.getWidth(), CvType.CV_8UC3);

		byte[] pixels = ((DataBufferByte) bufImg.getRaster().getDataBuffer())
				.getData();
		ret.put(0, 0, pixels);

		return ret;
	}

	/**
	 * Wandelt eine openCV {@link Mat} in ein Java {@link BufferedImage}, damit
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
		switch (matrix.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			// bgr to rgb
			byte b;
			for (int i = 0; i < data.length; i = i + 3) {
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
}
