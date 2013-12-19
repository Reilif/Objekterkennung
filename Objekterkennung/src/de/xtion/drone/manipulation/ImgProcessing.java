package de.xtion.drone.manipulation;

import de.xtion.drone.utils.ImageUtils;
import org.monte.media.image.WhiteBalance;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * The class processes a buffered image obtained form the web cam and edits its brightness, contrast, hue, saturation,
 * value and color values if wanted.
 */
public class ImgProcessing {

    private static Mat tempOne = new Mat();
    private static Mat tempTwo = new Mat();
    private static Mat tempThree = new Mat();
    public static StringBuffer algorithms;          // string buffer for white balance
    public static RescaleOp rescaleOp;              // to set the brightness and contrast of a buffered image
    public static double[] hsv;                     // HSV value that shall be added
    private static double[] hsv2 = new double[3];   // new HSV value of the pixel

    /**
     * The method 'setWhiteBalance' applies selected white balances on the image
     * @param data - buffered image to apply the white balances on
     * @return - buffered image with applied white balances or without if none were selected
     */

    private static BufferedImage setWhiteBalance(BufferedImage data){
        if(ImgProcessing.algorithms.toString().equals("g--")){
            return WhiteBalance.whiteBalanceGreyworld(data);
        } else if(ImgProcessing.algorithms.toString().equals("-q-")){
            return WhiteBalance.whiteBalanceQM(data);
        } else if(ImgProcessing.algorithms.toString().equals("--r")){
            return WhiteBalance.whiteBalanceRetinex(data);
        } else if(ImgProcessing.algorithms.toString().equals("gq-")){
            return WhiteBalance.whiteBalanceQM(WhiteBalance.whiteBalanceGreyworld(data));
        } else if(ImgProcessing.algorithms.toString().equals("g-r")){
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceGreyworld(data));
        } else if(ImgProcessing.algorithms.toString().equals("-qr")){
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceQM(data));
        } else if(ImgProcessing.algorithms.toString().equals("gqr")) {
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceQM(WhiteBalance.whiteBalanceGreyworld(data)));
        } else {
            return data;
        }
    }

    /**
     * The method 'setBrightnessAndContrast' sets the brightness and contrast of the image
     * @param data - buffered image of that the brightness and contrast shall be set
     * @return - buffered image with new brightness and contrast
     */

    private static BufferedImage setBrightnessAndContrast(BufferedImage data){
        // 'rescaleOp.filter' applies the set rescaleOp on the image (first value is contrast, second is brightness)
        return ImgProcessing.rescaleOp.filter(data, data);
    }

    /**
     * The method 'setHSV' sets the hue, saturation and value of the image
     * @param data - buffered image of that the HSV shall be set
     * @return - buffered image with new HSV
     */

    private static BufferedImage setHSV(BufferedImage data){
        ImgProcessing.tempOne = ImageUtils.bufferedImageToMat(data);
        // BGR to HSV
        Imgproc.cvtColor(ImgProcessing.tempOne, ImgProcessing.tempTwo, Imgproc.COLOR_BGR2HSV);
        for(int i = 0; i < ImgProcessing.tempTwo.rows(); i++){
            for(int j = 0; j < ImgProcessing.tempTwo.cols(); j++){
                ImgProcessing.hsv2[0] = ImgProcessing.hsv[0] + ImgProcessing.tempTwo.get(i,j)[0]; // hue
                ImgProcessing.hsv2[1] = ImgProcessing.hsv[1] + ImgProcessing.tempTwo.get(i,j)[1]; // saturation
                ImgProcessing.hsv2[2] = ImgProcessing.hsv[2] + ImgProcessing.tempTwo.get(i,j)[2]; // value
                ImgProcessing.tempTwo.put(i, j, ImgProcessing.hsv2); // pixel with new values
            }
        }
        // HSV to BGR
        Imgproc.cvtColor(ImgProcessing.tempTwo, ImgProcessing.tempThree, Imgproc.COLOR_HSV2BGR);
        return ImageUtils.matToBufferedImage(ImgProcessing.tempThree);
    }

    /**
     * The method 'setImgAdjustments' applies all ImgProcessing methods on the image
     * @param data - buffered image that shall be edited
     * @return - edited buffered image
     */

    public static BufferedImage setImgAdjustments(BufferedImage data){
        return ImgProcessing.setHSV(ImgProcessing.setBrightnessAndContrast(ImgProcessing.setWhiteBalance(data)));
    }
}
