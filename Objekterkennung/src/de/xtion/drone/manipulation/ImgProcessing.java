package de.xtion.drone.manipulation;

import de.xtion.drone.utils.ImageUtils;
import org.monte.media.image.WhiteBalance;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * Created with IntelliJ IDEA.
 * User: Noshaba
 * Date: 24.11.13
 * Time: 03:15
 * To change this template use File | Settings | File Templates.
 */
public class ImgProcessing {

    private static Mat tempOne = new Mat();
    private static Mat tempTwo = new Mat();
    private static Mat tempThree = new Mat();
    public static StringBuffer algorithms;
    public static RescaleOp rescaleOp;
    public static double[] hsv;

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

    private static BufferedImage setBrightnessAndContrast(BufferedImage data){
        return ImgProcessing.rescaleOp.filter(data, data);
    }

    private static BufferedImage setHSV(BufferedImage data){
        tempOne = ImageUtils.bufferedImageToMat(data);
        Imgproc.cvtColor(tempOne, tempTwo, Imgproc.COLOR_BGR2HSV);
        double[] hsv2 = new double[3];

        for(int i = 0; i < tempTwo.rows(); i++){
            for(int j = 0; j < tempTwo.cols(); j++){
                hsv2[0] = hsv[0] + tempTwo.get(i,j)[0];
                hsv2[1] = hsv[1] + tempTwo.get(i,j)[1];
                hsv2[2] = hsv[2] + tempTwo.get(i,j)[2];
                tempTwo.put(i, j, hsv2);
            }
        }

        Imgproc.cvtColor(tempTwo, tempThree, Imgproc.COLOR_HSV2BGR);

        return ImageUtils.matToBufferedImage(tempThree);
    }

    private static BufferedImage setSaturation(BufferedImage data){
        return data;
    }

    private static BufferedImage setValue(BufferedImage data){
        return data;
    }

    public static BufferedImage setImgAdjustments(BufferedImage data){
        return ImgProcessing.setHSV(ImgProcessing.setBrightnessAndContrast(ImgProcessing.setWhiteBalance(data)));
    }
}
