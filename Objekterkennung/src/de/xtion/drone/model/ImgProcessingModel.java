package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;

/**
 * Created with IntelliJ IDEA.
 * User: Noshaba
 * Date: 23.11.13
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class ImgProcessingModel extends Model<ImgProcessingModel.ImgProcModelEvent> {
    private final int minContrast;
    private final int maxContrast;
    private final int minBrightness;
    private final int maxBrightness;
    private final int minHue;
    private final int maxHue;
    private final int minSaturation;
    private final int maxSaturation;
    private final int minValue;
    private final int maxValue;
    private float contrast;
    private int brightness;
    private double hue;
    private double saturation;
    private double value;
    private boolean grayWorld;
    private boolean quadraticMapping;
    private boolean retinex;

    public ImgProcessingModel(){
        super(ImgProcessingModel.ImgProcModelEvent.values());
        this.minContrast = -10;
        this.maxContrast = 100;
        this.minBrightness = -10000;
        this.maxBrightness = 1000;
        this.minHue = 0;
        this.maxHue = 180;
        this.minSaturation = -255;
        this.maxSaturation = 255;
        this.minValue = -255;
        this.maxValue = 255;
        this.contrast = 1.0f;
        this.brightness = 0;
        this.hue = 0;
        this.saturation = 0;
        this.value = 0;
        this.grayWorld = false;
        this.quadraticMapping = false;
        this.retinex = false;
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

    public int getMinHue(){
        return this.minHue;
    }

    public int getMaxHue(){
        return this.maxHue;
    }

    public int getMinSaturation(){
        return this.minSaturation;
    }

    public int getMaxSaturation(){
        return this.maxSaturation;
    }

    public int getMinValue(){
        return this.minValue;
    }

    public int getMaxValue(){
        return this.maxValue;
    }

    public float getContrast(){
        return this.contrast;
    }

    public int getBrightness(){
        return this.brightness;
    }

    public double getHue(){
        return this.hue;
    }

    public double getSaturation(){
        return this.saturation;
    }

    public double getValue(){
        return this.value;
    }

    public boolean getGrayWorld(){
        return this.grayWorld;
    }

    public boolean getQuadraticMapping(){
        return this.quadraticMapping;
    }

    public boolean getRetinex(){
        return this.retinex;
    }

    public void setContrast(float contrast){
        this.contrast = contrast;
        fireModelEvent(ImgProcModelEvent.CONTRAST);
    }

    public void setBrightness(int brightness){
        this.brightness = brightness;
        fireModelEvent(ImgProcModelEvent.BRIGHTNESS);
    }

    public void setHue(double hue){
        this.hue = hue;
        fireModelEvent(ImgProcModelEvent.HUE);
    }

    public void setSaturation(double saturation){
        this.saturation = saturation;
        fireModelEvent(ImgProcModelEvent.SATURATION);
    }

    public void setValue(double value){
        this.value = value;
        fireModelEvent(ImgProcModelEvent.VALUE);
    }

    public void setGrayWorld(boolean grayWorld){
        this.grayWorld = grayWorld;
        fireModelEvent(ImgProcModelEvent.GRAY);
    }

    public void setQuadraticMapping(boolean quadraticMapping){
        this.quadraticMapping = quadraticMapping;
        fireModelEvent(ImgProcModelEvent.QMAPPING);
    }

    public void setRetinex(boolean retinex){
        this.retinex = retinex;
        fireModelEvent(ImgProcModelEvent.RETINEX);
    }

    /**
     * Enum list used for the events of the EdgeModel
     */
    public enum ImgProcModelEvent {
        CONTRAST, BRIGHTNESS, HUE, SATURATION, VALUE, GRAY, QMAPPING, RETINEX
    }
}
