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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 * A chess square UI component.
 * @author Thomas Warner 2014
 */
public class ChessSquare extends javax.swing.JButton {
    
    /**
     * Color of chess square.
     */
    private String squareColor;
    
    /**
     * Image icon associated with chess square button.
     */
    private ImageIcon icon;
    
    /**
     * Component has label component binded to it.
     */
    private boolean labeled;

    /**
     * Label of this component.
     */
    private javax.swing.JLabel label;
    
    /**
     * If label is visible for a labeled square.
     */
    private boolean labelVisible;
    
    /**
     * Constructor.
     * Set's double buffered = true.
     */
    public ChessSquare() {
        this.setDoubleBuffered(true);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.labeled = false;
        this.labelVisible = false;
    }
    
        
    /**
     * Add label Compnent to JButton, the labels text representation as param of
     * this method.
     * @param labelText text for new label.
     */
    public void addPositionLabel(final String labelText) {

        label = new javax.swing.JLabel(labelText);
        label.setFont(new java.awt.Font("arial", java.awt.Font.BOLD, 12));
        label.setAlignmentX(java.awt.Component.TOP_ALIGNMENT);
        label.setAlignmentY(java.awt.Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(0,0,0,24)));
        this.add(label);
        this.labeled = true;
        this.labelVisible = true;
    }
    
    /**
     * Dispose of this components label.
     */
    public void removeLabel() {
        if (this.label != null) {
            this.remove(0);
        }
        this.label = null;
    }
    
    @Override
    public ImageIcon getIcon() {
        return icon;
    }
    
    public void setIcon(final ImageIcon icon) {
        this.icon = icon;
    }

    public String getSquareColor() {
        return squareColor;
    }
    
    public void setSquareColor(final String squareColor) {
        this.squareColor = squareColor;
    }
    
    public boolean isLabeled() {
        return labeled;
    }
    
    public void setLabeled(boolean labeled) {
        this.labeled = labeled;
    }
    
    
    public boolean isLabelVisible() {
        return labelVisible;
    }

    public void setLabelVisible(boolean labelVisible) {
        this.labelVisible = labelVisible;
    }
    
}
