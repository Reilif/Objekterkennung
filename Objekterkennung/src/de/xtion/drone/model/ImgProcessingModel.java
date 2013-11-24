package de.xtion.drone.model;

/**
 * Created with IntelliJ IDEA.
 * User: Noshaba
 * Date: 23.11.13
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class ImgProcessingModel {
    private final int minContrast;
    private final int maxContrast;
    private final int minBrightness;
    private final int maxBrightness;
    private float contrast;
    private int brightness;

    public ImgProcessingModel(){
        this.minContrast = -10;
        this.maxContrast = 100;
        this.minBrightness = -10000;
        this.maxBrightness = 1000;
        this.contrast = 1.0f;
        this.brightness = 0;
    }

    public int getMinContrast(){
        return this.minContrast;
    }

    public int getMaxContrast(){
        return this.maxContrast;
    }

    public int getMinBrightness(){
        return this.minBrightness;
    }

    public int getMaxBrightness(){
        return this.maxBrightness;
    }

    public float getContrast(){
        return this.contrast;
    }

    public int getBrightness(){
        return this.brightness;
    }

    public void setContrast(float contrast){
        this.contrast = contrast;
    }

    public void setBrightness(int brightness){
        this.brightness = brightness;
    }
}
