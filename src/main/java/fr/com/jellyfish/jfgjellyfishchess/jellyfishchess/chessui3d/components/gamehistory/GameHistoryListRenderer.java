/**
 * *****************************************************************************
 * Copyright (c) 2015, Thomas.H Warner. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. 
 ******************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.components.gamehistory;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author thw
 */
public class GameHistoryListRenderer extends javax.swing.JLabel implements ListCellRenderer {

    /**
     * Zebra color A.
     */
    private final Color colorA = new Color(240,240,240);
    
    /**
     * Zebra color B.
     */
    private final Color colorB = new Color(220,220,230);
    
    /**
     * List elements default foreground.
     */
    private final Color defaultForeground = new Color(51,51,51);
    
    /**
     * List element selected foreground.
     */
    private final Color selectedForeground = Color.ORANGE;
    
    /**
     * List element selected background.
     */
    private final Color selectedBackground = Color.GRAY;
    
    /**
     * List elements font value.
     */
    private final Font elementFont = new java.awt.Font("Meiryo", Font.PLAIN, 14);
    
    public GameHistoryListRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, 
            boolean isSelected, boolean cellHasFocus) {
        
        final GameHistoryLabel gameHistoryData = (GameHistoryLabel) value;
        
        this.setFont(this.elementFont);
        this.setText(gameHistoryData.getDescription());
        
        if (!isSelected) {
            final Color c = index % 2 == 0 ? this.colorA : this.colorB;
            this.setBackground(c);
            this.setForeground(this.defaultForeground);
        } else {
            this.setBackground(this.selectedBackground);
            this.setForeground(this.selectedForeground);
        }
        
        return this;
    }
}
