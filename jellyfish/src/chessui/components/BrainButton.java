/*******************************************************************************
 * Copyright (c) 2014, Thomas.H Warner.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors 
 * may be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 *******************************************************************************/

package chessui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;
import jellyfish.uci.UCIProtocolDriver;

/**
 * @author Thomas.H Warner 2014
 */
public class BrainButton extends JButton {

    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * Rate millisecondes at which the bran button changes state.
     */
    private static final int TWINKLE_CHANGE_LVL_RATE_MS = 75;
    
    /**
     * Range for rgb values fluctuations.
     */
    private static final int TWINKLE_RANGE = 100;
    
    /**
     * The default background of the button.
     */
    private Color buttonDefaultBackgroundColor;
    
    /**
     * If button event has activated infinite search.
     */
    private boolean thinking = false;

    /**
     * Timer.
     */
    private Timer timer;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Constructor.
     */
    public BrainButton() {
        this.setDoubleBuffered(true);
        //this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.setDoubleBuffered(true);
        this.setFocusPainted(false);
        this.setMargin(new java.awt.Insets(0, 0, 0, 0));
        this.setMaximumSize(new java.awt.Dimension(30, 30));
        this.setMinimumSize(new java.awt.Dimension(30, 30));
        this.setOpaque(true);
        this.setPreferredSize(new java.awt.Dimension(30, 30));
        this.setBackground(new Color(100,120,120));
        // Init Brain button :
        init();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Activate twinkling on component.
     * @param reloadingPreviousGame
     */
    public void startThink(final boolean reloadingPreviousGame) {
        if (!reloadingPreviousGame) {
            UCIProtocolDriver.getInstance().getIoExternalEngine().executeStaticInfiniteSearch();
            thinking = true;
            timer.start();
        }
    }
    
    /**
     * Disactivate twinkling on component.
     * @param reloadingPreviousGame
     */
    public void stopThink(final boolean reloadingPreviousGame) {
        if (!reloadingPreviousGame) {
            UCIProtocolDriver.getInstance().getIoExternalEngine().stopStaticInfiniteSearch();
            thinking = false;
            timer.stop();
            this.setBackground(buttonDefaultBackgroundColor);
        }
    }
    
    /**
     * Stop brain button activity and twinkling on component.
     */
    public void stopThink() {
        if (thinking) {
            UCIProtocolDriver.getInstance().getIoExternalEngine().stopStaticInfiniteSearch();
            thinking = false;
            timer.stop();
            this.setBackground(buttonDefaultBackgroundColor);
        }
    }

    /**
     * Init the class.
     */
    private void init() { 
        this.buttonDefaultBackgroundColor = this.getBackground();
        timer = new Timer(TWINKLE_CHANGE_LVL_RATE_MS , new TimerListener(this));
        timer.setInitialDelay(0);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public static int getTWINKLE_CHANGE_LVL_RATE_MS() {
        return TWINKLE_CHANGE_LVL_RATE_MS;
    }

    public static int getTWINKLE_RANGE() {
        return TWINKLE_RANGE;
    }
    
    public boolean isThinking() {
        return thinking;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inner class">
    /**
     * Timer inner class.
     */
    private class TimerListener implements ActionListener {
    
        /**
         * Rate of rgb incrementation or decrementation.
         */
        private static final int incrementationRate = 4; 
        
        /**
         * Button instance.
         */
        private final BrainButton brainButton;
        
        /**
         * RGB values of the buttons backgroud color.
         */
        private Integer r, g, b;
        
        /**
         * Lowest & highest Red values for color fluctuation.
         */
        private int lowestRValue, highestRValue;
        
        /**
         * If the button is being enlighted or not. 
         */
        private boolean enlighting = true;
        
        /**
         * Constructor.
         * @param brainButton 
         */
        public TimerListener(final BrainButton brainButton) {
            this.brainButton = brainButton;
            initRGB();
        }
 
        @Override
        public void actionPerformed(ActionEvent e) {
            // Here goes all the display code for making the button 'twinkle'.
            // Change button's background color :
            if (brainButton.getBackground().getRed() < highestRValue && enlighting) {
                r += incrementationRate; g += incrementationRate; b += incrementationRate;
                brainButton.setBackground(new Color(r,g,b));
            } else if (brainButton.getBackground().getRed() > lowestRValue && !enlighting) {
                r -= incrementationRate; g -= incrementationRate; b -= incrementationRate;
                brainButton.setBackground(new Color(r,g,b));
            } else {
                enlighting = !enlighting;
            }
        }

        /**
         * Init rgb values.
         */
        private void initRGB() {
            this.r = brainButton.getBackground().getRed();
            this.g = brainButton.getBackground().getGreen();
            this.b = brainButton.getBackground().getBlue();
            this.lowestRValue = brainButton.getBackground().getRed();
            this.highestRValue = brainButton.getBackground().getRed() + BrainButton.getTWINKLE_RANGE();
        }
    }
    //</editor-fold>
    
}


