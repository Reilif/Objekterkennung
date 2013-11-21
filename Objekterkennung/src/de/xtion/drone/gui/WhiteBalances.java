package de.xtion.drone.gui;

import org.monte.media.image.WhiteBalance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Noshaba
 * Date: 21.11.13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class WhiteBalances extends JPanel implements ItemListener {

    private JCheckBox wbGrayWorld;
    private JCheckBox wbQM;
    private JCheckBox wbRetinex;
    public static StringBuffer algorithms;
   // public static BufferedImage image;

    public WhiteBalances(){
        super(new GridLayout(0,1));

        this.wbGrayWorld = new JCheckBox("Grayworld");
        this.wbGrayWorld.setMnemonic(KeyEvent.VK_G);

        this.wbQM = new JCheckBox("Quadratic Mapping");
        this.wbQM.setMnemonic(KeyEvent.VK_Q);

        this.wbRetinex = new JCheckBox("Retinex");
        this.wbRetinex.setMnemonic(KeyEvent.VK_R);

        //Register listener for the check boxes
        this.wbGrayWorld.addItemListener(this);
        this.wbQM.addItemListener(this);
        this.wbRetinex.addItemListener(this);

        //Indicates which algorithms are selected
        this.algorithms = new StringBuffer("---");

        this.add(this.wbGrayWorld);
        this.add(this.wbQM);
        this.add(this.wbRetinex);
    }

    @ Override
    public void itemStateChanged(ItemEvent e){
        int index = 0;
        char c = '-';
        Object source = e.getItemSelectable();

        if(source == wbGrayWorld){
            index = 0;
            c = 'g';
        } else if(source == wbQM){
            index = 1;
            c = 'q';
        } else if(source == wbRetinex){
            index = 2;
            c = 'r';
        }

        if(e.getStateChange() == ItemEvent.DESELECTED){
            c = '-';
        }

        WhiteBalances.algorithms.setCharAt(index,c);
    }

    public static BufferedImage setWhiteBalance(BufferedImage data){
        if(WhiteBalances.algorithms.toString().equals("g--")){
             return WhiteBalance.whiteBalanceGreyworld(data);
        } else if(WhiteBalances.algorithms.toString().equals("-q-")){
            return WhiteBalance.whiteBalanceQM(data);
        } else if(WhiteBalances.algorithms.toString().equals("--r")){
            return WhiteBalance.whiteBalanceRetinex(data);
        } else if(WhiteBalances.algorithms.toString().equals("gq-")){
            return WhiteBalance.whiteBalanceQM(WhiteBalance.whiteBalanceGreyworld(data));
        } else if(WhiteBalances.algorithms.toString().equals("g-r")){
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceGreyworld(data));
        } else if(WhiteBalances.algorithms.toString().equals("-qr")){
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceQM(data));
        } else if(WhiteBalances.algorithms.toString().equals("gqr")) {
            return WhiteBalance.whiteBalanceRetinex(WhiteBalance.whiteBalanceQM(WhiteBalance.whiteBalanceGreyworld(data)));
        } else {
            return data;
        }
    }
}
