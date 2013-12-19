package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;

/**
 * Model of the GUI for the image processing options.
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

    /**
     * Initialization of all values
     */

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

    /**
     * @return the minimum possible contrast value to add
     */

    public int getMinContrast(){
        return this.minContrast;
    }

    /**
     * @return the maximum possible contrast value to add
     */

    public int getMaxContrast(){
        return this.maxContrast;
    }

    /**
     * @return the minimum possible brightness value to add
     */

    public int getMinBrightness(){
        return this.minBrightness;
    }

    /**
     * @return the maximum possible brightness value to add
     */

    public int getMaxBrightness(){
        return this.maxBrightness;
    }

    /**
     * @return the minimum possible hue value to add
     */

    public int getMinHue(){
        return this.minHue;
    }

    /**
     * @return the maximum possible hue value to add
     */

    public int getMaxHue(){
        return this.maxHue;
    }

    /**
     * @return the minimum possible saturation value to add
     */

    public int getMinSaturation(){
        return this.minSaturation;
    }

    /**
     * @return the maximum possible saturation value to add
     */

    public int getMaxSaturation(){
        return this.maxSaturation;
    }

    /**
     * @return the minimum possible pixel value to add
     */

    public int getMinValue(){
        return this.minValue;
    }

    /**
     * @return the maximum possible pixel value to add
     */

    public int getMaxValue(){
        return this.maxValue;
    }

    /**
     * @return the set contrast
     */

    public float getContrast(){
        return this.contrast;
    }

    /**
     * @return the set brightness
     */

    public int getBrightness(){
        return this.brightness;
    }

    /**
     * @return the set hue
     */

    public double getHue(){
        return this.hue;
    }

    /**
     * @return the set saturation
     */

    public double getSaturation(){
        return this.saturation;
    }

    /**
     * @return the set pixel value
     */

    public double getValue(){
        return this.value;
    }

    /**
     * @return gray world flag
     */

    public boolean getGrayWorld(){
        return this.grayWorld;
    }

    /**
     * @return quadratic mapping flag
     */

    public boolean getQuadraticMapping(){
        return this.quadraticMapping;
    }

    /**
     * @return retinex flag
     */

    public boolean getRetinex(){
        return this.retinex;
    }

    /**
     * The method 'setContrast' sets the contrast of an image and fires an event that the contrast has been changed
     * @param contrast - new contrast value
     */

    public void setContrast(float contrast){
        this.contrast = contrast;
        fireModelEvent(ImgProcModelEvent.CONTRAST);
    }

    /**
     * The method 'setBrightness' sets the brightness of an image and fires an event that the brightness has been changed
     * @param brightness - new brightness value
     */

    public void setBrightness(int brightness){
        this.brightness = brightness;
        fireModelEvent(ImgProcModelEvent.BRIGHTNESS);
    }

    /**
     * The method 'setHue' sets the hue of an image and fires an event that the hue has been changed
     * @param hue - new hue value
     */

    public void setHue(double hue){
        this.hue = hue;
        fireModelEvent(ImgProcModelEvent.HUE);
    }

    /**
     * The method 'setSaturation' sets the saturation of an image and fires an event that the saturation has been changed
     * @param saturation - new saturation value
     */

    public void setSaturation(double saturation){
        this.saturation = saturation;
        fireModelEvent(ImgProcModelEvent.SATURATION);
    }

    /**
     * The method 'setValue' sets the pixel value of an image and fires an event that the pixel value has been changed
     * @param value - new pixel value
     */

    public void setValue(double value){
        this.value = value;
        fireModelEvent(ImgProcModelEvent.VALUE);
    }

    /**
     * The method 'setGrayWorld' sets the gray world flag when the gray world white balance has been applied to the image
     * and fires an event that the white balance has been added
     * @param grayWorld - gray world flag
     */

    public void setGrayWorld(boolean grayWorld){
        this.grayWorld = grayWorld;
        fireModelEvent(ImgProcModelEvent.GRAY);
    }

    /**
     * The method 'setQuadraticMapping' sets the quadratic mapping flag when the quadratic mapping white balance has been
     * applied to the image and fires an event that the white balance has been added
     * @param quadraticMapping - quadratic mapping flag
     */

    public void setQuadraticMapping(boolean quadraticMapping){
        this.quadraticMapping = quadraticMapping;
        fireModelEvent(ImgProcModelEvent.QMAPPING);
    }

    /**
     * The method 'setRetinex' sets the retinex flag when the Retinex white balance has been
     * applied to the image and fires an event that the white balance has been added
     * @param retinex - retinex flag
     */

    public void setRetinex(boolean retinex){
        this.retinex = retinex;
        fireModelEvent(ImgProcModelEvent.RETINEX);
    }

    /**
     * Enum list used for the events of the ImgProcessingModel
     */
    public enum ImgProcModelEvent {
        CONTRAST, BRIGHTNESS, HUE, SATURATION, VALUE, GRAY, QMAPPING, RETINEX
    }
}
