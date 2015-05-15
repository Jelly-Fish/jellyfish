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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.ui;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.interfaces.Writable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Thomas.H Warner 2014
 */
class Console extends javax.swing.JFrame implements Writable {

    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * Margin right between console and main JFrame, used when visibility is changed.
     */
    private static final int NEW_VISIBILITY_MARGIN_RIGHT = 0;
    
    /**
     * GUI class ref.
     */
    private MainUi ui;
    
    /**
     * 
     */
    private boolean userReadingOutput = false; 
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor"> 
    /**
     * Constructor 1.
     */
    public Console(final MainUi ui) {
        
        this.ui = ui;
        
        initComponents();
        java.net.URL imgURL = getClass().getResource(UIConst.JELLYFISH_FRAME_ICON);
        javax.swing.ImageIcon img = new javax.swing.ImageIcon(imgURL);
        this.setIconImage(img.getImage());
        this.setLocationRelativeTo(null);
        
        this.ScrollPaneTextPane.getVerticalScrollBar().addMouseListener(
            new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!ui.isMainUiReady()) { return; }
                    userReadingOutput = true;
                }

                @Override
                public void mousePressed(MouseEvent e) { 
                    if (!ui.isMainUiReady()) { return; }
                    userReadingOutput = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) { 
                    userReadingOutput = false;
                }

                @Override
                public void mouseEntered(MouseEvent e) { 
                    if (!ui.isMainUiReady()) { return; }
                    userReadingOutput = true;
                }

                @Override
                public void mouseExited(MouseEvent e) { 
                    userReadingOutput = false;
                }
            } 
        );
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Package private methods">
    /**
     * Update consoles location relative to main frame.
     * @param mainFrameX
     * @param mainFrameY
     * @param mainFrameHeight
     * @param mainFrameWidth 
     */
    void updateConsoleLocationOnStartUp(int mainFrameX, int mainFrameY, int mainFrameHeight, int mainFrameWidth) {
        this.setSize((int)(mainFrameWidth / 1.5), mainFrameHeight);
        this.setLocation(mainFrameX - (int)(mainFrameWidth / 1.5) - NEW_VISIBILITY_MARGIN_RIGHT, mainFrameY);
    }

    /**
     * Update console display depending on main frame iconification.
     */
    void updateDispaly(final boolean mainFrameVisible) {
        this.setVisible(mainFrameVisible);
    }
    
    /**
     * Hide console.
     */
    void cancelVisibility() {
        this.setVisible(false);
    }
    
    /**
     * Set default centered location to console frame.
     */
    void updateConsoleLocationDefault() {
        this.setLocationRelativeTo(null);
    }
    //</editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScrollPaneTextPane = new javax.swing.JScrollPane();
        textPaneOutput = new javax.swing.JTextPane();
        //{
            //    @Override
            //    public boolean getScrollableTracksViewportWidth() {
                //        return getUI().getPreferredSize(this).width <= getParent().getSize().width;
                //    }
            //};

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Jellyfish console");
        setName("consoleframe"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                consoleWindowClosing(evt);
            }
        });

        ScrollPaneTextPane.setBorder(null);
        ScrollPaneTextPane.setDoubleBuffered(true);

        textPaneOutput.setBackground(new java.awt.Color(0, 0, 0));
        textPaneOutput.setBorder(null);
        textPaneOutput.setFont(new java.awt.Font("Lucida Console", 0, 13)); // NOI18N
        textPaneOutput.setForeground(new java.awt.Color(240, 240, 255));
        textPaneOutput.setToolTipText("");
        textPaneOutput.setCaretColor(new java.awt.Color(255, 255, 255));
        textPaneOutput.setDoubleBuffered(true);
        textPaneOutput.setMargin(new java.awt.Insets(6, 6, 6, 6));
        textPaneOutput.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        textPaneOutput.setSelectionColor(new java.awt.Color(255, 255, 255));
        textPaneOutput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textPaneOutputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textPaneOutputFocusLost(evt);
            }
        });
        ScrollPaneTextPane.setViewportView(textPaneOutput);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 285, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(ScrollPaneTextPane, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(ScrollPaneTextPane, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="events"> 
    private void consoleWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_consoleWindowClosing
        this.setVisible(false);
        this.ui.getDriver().getStatusIO().getUserSettings().setConsoleVisible(this.isVisible());
    }//GEN-LAST:event_consoleWindowClosing

    private void textPaneOutputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textPaneOutputFocusGained
        if (!ui.isMainUiReady()) { return; }
        userReadingOutput = true;
    }//GEN-LAST:event_textPaneOutputFocusGained

    private void textPaneOutputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textPaneOutputFocusLost
        userReadingOutput = false;
    }//GEN-LAST:event_textPaneOutputFocusLost
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Variables declaration - do not modify"> 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPaneTextPane;
    private javax.swing.JTextPane textPaneOutput;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setters">
    public javax.swing.JTextPane getTextPaneOutput() {
        return textPaneOutput;
    }
    
    public javax.swing.JScrollPane getScrollPane() {
        return ScrollPaneTextPane;
    }
    
    @Override
    public boolean isUserReadingOutput() {
        return userReadingOutput;
    }
    //</editor-fold>
    
}
