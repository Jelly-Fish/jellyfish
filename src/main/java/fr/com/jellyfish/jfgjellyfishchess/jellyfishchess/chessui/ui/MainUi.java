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

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.*;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.BrainButton;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.MessageConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.ui.gamedata.GameDataLoaderDialog;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.GameTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.dto.ChessGameDTO;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Pawn;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.FenConvertionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.BoardSnapshot;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.timer.GameTimer;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.externalengine.IOExternalEngine;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.DataUtils;
import java.awt.Component;
import javax.swing.JPanel;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;


/**
 * @author Thomas.H Warner 2014
 */
public class MainUi extends javax.swing.JFrame {

    //<editor-fold defaultstate="collapsed" desc="Private vars">
    /**
     * State listener.
     */
    private final WindowStateListener stateListener;
    
    /**
     * Main frame's state : iconified, set by window state listener.
     */
    private boolean iconified;
    
    /**
     * Main frame's state : maximized, set by window state listener.
     */
    private boolean maximized;
    
    /**
     * Instance of the GUI driver.
     */
    private MainUiDriver driver;
    
    /**
     * Console instance.
     */
    private final Console console;
    
    /**
     * 
     */
    private boolean mainUiReady = false;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Creates new form IHM
     * @param consoleVisibility
     */
    public MainUi(final boolean consoleVisibility) {
        
        this.console = new Console(this);
        this.console.setVisible(consoleVisibility);
        
        initComponents();
        boardContainer.setLocation(30, 30);
        // Set boardContainer opaque true to prevent green background display :
        boardContainer.setOpaque(false);
        java.net.URL imgURL = getClass().getResource(UIConst.JELLYFISH_FRAME_ICON);
        javax.swing.ImageIcon img = new javax.swing.ImageIcon(imgURL);
        this.setIconImage(img.getImage());
        
        // Window state listener declaration :
        this.stateListener = new MainUiAdapter(this);
        this.addWindowStateListener(stateListener);
        
        this.mainUiReady = true;
    }
    //</editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        globalPopupMenu = new javax.swing.JPopupMenu();
        clearOutputMenuItem = new javax.swing.JMenuItem();
        displayConsolePopupMenuItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        newGameWhitesPopupMenuItem = new javax.swing.JMenuItem();
        newGameBlacksPopupMenuItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        forceMovePopupMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        pawnPromoPopupMenu = new javax.swing.JMenu();
        queenPromoPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        bishopPromoPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        knightPromoPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        rookPromoPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        changePlyDepthPopupMenu = new javax.swing.JMenu();
        goDeeperPopupMenuItem = new javax.swing.JMenuItem();
        goShallowerPopupMenuItem = new javax.swing.JMenuItem();
        difficultySettingsMenu = new javax.swing.JMenu();
        dummyPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        easyPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        mediumPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        hardPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        harderPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        expertPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        masterPopupCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        globalContainer = new javax.swing.JPanel();
        boardContainer = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessBoard();
        bA8 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bA7 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bA6 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bA5 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bA4 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bA3 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bA2 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bA1 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bB8 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bB7 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bB6 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bB5 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bB4 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bB3 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bB2 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bB1 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bC8 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bC7 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bC6 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bC5 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bC4 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bC3 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bC2 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bC1 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bD8 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bD7 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bD6 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bD5 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bD4 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bD3 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bD2 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bD1 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bE8 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bE7 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bE6 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bE5 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bE4 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bE3 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bE2 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bE1 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bF8 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bF7 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bF6 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bF5 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bF4 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bF3 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bF2 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bF1 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bG8 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bG7 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bG6 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bG5 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bG4 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bG3 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bG2 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bG1 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bH8 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bH7 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bH6 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bH5 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bH4 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bH3 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bH2 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        bH1 = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare();
        controlPanel = new javax.swing.JPanel();
        takeMoveBackButton = new javax.swing.JButton();
        moveForwardButton = new javax.swing.JButton();
        brainButton = new fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.BrainButton();
        forceEngineToTakeMoveButton = new javax.swing.JButton();
        activateGUIEngineSupportButton = new javax.swing.JButton();
        newGameWhitesButton = new javax.swing.JButton();
        newGameBlackButton = new javax.swing.JButton();
        deeperPlyDepthButton = new javax.swing.JButton();
        shallowerPlyDepthButton = new javax.swing.JButton();
        displayConsoleButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        loadFromGameDataMenuItem = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        displayConsoleMenuItem = new javax.swing.JMenuItem();
        appendAllOutputCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        displayCoordinatesCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        alwaysLoadPreviousGameCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        saveGameDataOnCloseCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        gameMenu = new javax.swing.JMenu();
        newGameWhitesMenuItem = new javax.swing.JMenuItem();
        newGameBlacksMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        pawnPromotionjMenu = new javax.swing.JMenu();
        queenPromoCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        bishopPromoCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        knightPromoCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        rookPromoCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        forceMoveMenuItem = new javax.swing.JMenuItem();
        activateGuiEngineSearchCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        plyDepthjMenu = new javax.swing.JMenu();
        plydeeperMenuItem = new javax.swing.JMenuItem();
        plyShallowerMenuItem = new javax.swing.JMenuItem();
        difficultyMenu = new javax.swing.JMenu();
        dummyCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        easyCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        mediumCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        hardCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        harderCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        expertCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        masterCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        chessmenIconMenu = new javax.swing.JMenu();
        classicOneChessmenMenuItem = new javax.swing.JMenuItem();
        classicTwoChessmenMenuItem = new javax.swing.JMenuItem();
        twoDOneChessmenMenuItem = new javax.swing.JMenuItem();
        boardIconMenu = new javax.swing.JMenu();
        boardDigitalOneMenuItem = new javax.swing.JMenuItem();
        boardDigitalTwoMenuItem = new javax.swing.JMenuItem();
        boardDigitalThreeMenuItem = new javax.swing.JMenuItem();
        boardDigitalFourMenuItem = new javax.swing.JMenuItem();
        boardEbonyIvoryMenuItem = new javax.swing.JMenuItem();
        AboutMenu = new javax.swing.JMenu();
        aboutJellyFishMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();

        globalPopupMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        globalPopupMenu.setBorder(null);
        globalPopupMenu.setBorderPainted(false);
        globalPopupMenu.setComponentPopupMenu(globalPopupMenu);
        globalPopupMenu.setDoubleBuffered(true);

        clearOutputMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        clearOutputMenuItem.setText("Clear console output");
        clearOutputMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOutputMenuItemActionPerformed(evt);
            }
        });
        globalPopupMenu.add(clearOutputMenuItem);

        displayConsolePopupMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        displayConsolePopupMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        displayConsolePopupMenuItem.setText("Change console visibility");
        displayConsolePopupMenuItem.setToolTipText("");
        displayConsolePopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayConsoleMenuItemActionPerformed(evt);
            }
        });
        globalPopupMenu.add(displayConsolePopupMenuItem);
        globalPopupMenu.add(jSeparator6);

        newGameWhitesPopupMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        newGameWhitesPopupMenuItem.setText("New game : play whites");
        newGameWhitesPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameWhitesMenuItemActionPerformed(evt);
            }
        });
        globalPopupMenu.add(newGameWhitesPopupMenuItem);

        newGameBlacksPopupMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        newGameBlacksPopupMenuItem.setText("New game : play blacks");
        newGameBlacksPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameBlacksMenuItemActionPerformed(evt);
            }
        });
        globalPopupMenu.add(newGameBlacksPopupMenuItem);
        globalPopupMenu.add(jSeparator7);

        forceMovePopupMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        forceMovePopupMenuItem.setText("Force engine to play move");
        forceMovePopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forceMoveMenuItemActionPerformed(evt);
            }
        });
        globalPopupMenu.add(forceMovePopupMenuItem);
        globalPopupMenu.add(jSeparator5);

        pawnPromoPopupMenu.setText("Pawn promotion settings");
        pawnPromoPopupMenu.setToolTipText("");
        pawnPromoPopupMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        queenPromoPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        queenPromoPopupCheckBoxMenuItem.setSelected(true);
        queenPromoPopupCheckBoxMenuItem.setText("Promoto to queen");
        queenPromoPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queenPromoCheckBoxMenuItemActionPerformed(evt);
            }
        });
        pawnPromoPopupMenu.add(queenPromoPopupCheckBoxMenuItem);

        bishopPromoPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bishopPromoPopupCheckBoxMenuItem.setText("Promote to bishop");
        bishopPromoPopupCheckBoxMenuItem.setToolTipText("");
        bishopPromoPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bishopPromoCheckBoxMenuItemActionPerformed(evt);
            }
        });
        pawnPromoPopupMenu.add(bishopPromoPopupCheckBoxMenuItem);

        knightPromoPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        knightPromoPopupCheckBoxMenuItem.setText("Promote to knight");
        knightPromoPopupCheckBoxMenuItem.setToolTipText("");
        knightPromoPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                knightPromoCheckBoxMenuItemActionPerformed(evt);
            }
        });
        pawnPromoPopupMenu.add(knightPromoPopupCheckBoxMenuItem);

        rookPromoPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rookPromoPopupCheckBoxMenuItem.setText("Promote to rook");
        rookPromoPopupCheckBoxMenuItem.setToolTipText("");
        rookPromoPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rookPromoCheckBoxMenuItemActionPerformed(evt);
            }
        });
        pawnPromoPopupMenu.add(rookPromoPopupCheckBoxMenuItem);

        globalPopupMenu.add(pawnPromoPopupMenu);
        globalPopupMenu.add(jSeparator3);

        changePlyDepthPopupMenu.setText("Change ply depth");
        changePlyDepthPopupMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        goDeeperPopupMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        goDeeperPopupMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        goDeeperPopupMenuItem.setText("Go deeper");
        goDeeperPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plydeeperMenuItemActionPerformed(evt);
            }
        });
        changePlyDepthPopupMenu.add(goDeeperPopupMenuItem);

        goShallowerPopupMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        goShallowerPopupMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        goShallowerPopupMenuItem.setText("Go shallower");
        goShallowerPopupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plyShallowerMenuItemActionPerformed(evt);
            }
        });
        changePlyDepthPopupMenu.add(goShallowerPopupMenuItem);

        globalPopupMenu.add(changePlyDepthPopupMenu);

        difficultySettingsMenu.setText("Difficulty settings");
        difficultySettingsMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        dummyPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dummyPopupCheckBoxMenuItem.setText("Dummy");
        dummyPopupCheckBoxMenuItem.setToolTipText("");
        dummyPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dummyCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultySettingsMenu.add(dummyPopupCheckBoxMenuItem);

        easyPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        easyPopupCheckBoxMenuItem.setText("Easy");
        easyPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                easyCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultySettingsMenu.add(easyPopupCheckBoxMenuItem);

        mediumPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mediumPopupCheckBoxMenuItem.setText("Medium");
        mediumPopupCheckBoxMenuItem.setToolTipText("");
        mediumPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediumCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultySettingsMenu.add(mediumPopupCheckBoxMenuItem);

        hardPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        hardPopupCheckBoxMenuItem.setText("Hard");
        hardPopupCheckBoxMenuItem.setToolTipText("");
        hardPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultySettingsMenu.add(hardPopupCheckBoxMenuItem);

        harderPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        harderPopupCheckBoxMenuItem.setText("Harder");
        harderPopupCheckBoxMenuItem.setToolTipText("");
        harderPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harderCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultySettingsMenu.add(harderPopupCheckBoxMenuItem);

        expertPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        expertPopupCheckBoxMenuItem.setText("Expert");
        expertPopupCheckBoxMenuItem.setToolTipText("");
        expertPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expertCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultySettingsMenu.add(expertPopupCheckBoxMenuItem);

        masterPopupCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterPopupCheckBoxMenuItem.setText("Master");
        masterPopupCheckBoxMenuItem.setToolTipText("");
        masterPopupCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultySettingsMenu.add(masterPopupCheckBoxMenuItem);

        globalPopupMenu.add(difficultySettingsMenu);

        globalPopupMenu.getAccessibleContext().setAccessibleParent(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("jellyfish - play chess, have fun !");
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(250, 100));
        setName("mainframe"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeiconified(java.awt.event.WindowEvent evt) {
                formWindowDeiconified(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        globalContainer.setBackground(new java.awt.Color(80, 92, 92));
        globalContainer.setToolTipText("");
        globalContainer.setComponentPopupMenu(globalPopupMenu);
        globalContainer.setFocusable(false);
        globalContainer.setMinimumSize(new java.awt.Dimension(200, 70));
        globalContainer.setLayout(null);

        boardContainer.setBackground(new java.awt.Color(0, 255, 0));
        boardContainer.setFocusable(false);
        boardContainer.setMaximumSize(new java.awt.Dimension(440, 440));
        boardContainer.setMinimumSize(new java.awt.Dimension(440, 440));
        boardContainer.setName(""); // NOI18N
        boardContainer.setPreferredSize(new java.awt.Dimension(440, 440));

        bA8.setBackground(new java.awt.Color(255, 255, 255));
        bA8.setBorder(null);
        bA8.setToolTipText("");
        bA8.setFocusPainted(false);
        bA8.setHideActionText(true);

        bA7.setBackground(new java.awt.Color(153, 153, 153));
        bA7.setBorder(null);
        bA7.setFocusPainted(false);

        bA6.setBackground(new java.awt.Color(255, 255, 255));
        bA6.setBorder(null);
        bA6.setFocusPainted(false);

        bA5.setBackground(new java.awt.Color(153, 153, 153));
        bA5.setBorder(null);
        bA5.setFocusPainted(false);

        bA4.setBackground(new java.awt.Color(255, 255, 255));
        bA4.setBorder(null);
        bA4.setFocusPainted(false);

        bA3.setBackground(new java.awt.Color(153, 153, 153));
        bA3.setBorder(null);
        bA3.setFocusPainted(false);

        bA2.setBackground(new java.awt.Color(255, 255, 255));
        bA2.setBorder(null);
        bA2.setFocusPainted(false);

        bA1.setBackground(new java.awt.Color(153, 153, 153));
        bA1.setBorder(null);
        bA1.setFocusPainted(false);

        bB8.setBackground(new java.awt.Color(153, 153, 153));
        bB8.setBorder(null);
        bB8.setFocusPainted(false);

        bB7.setBackground(new java.awt.Color(255, 255, 255));
        bB7.setBorder(null);
        bB7.setFocusPainted(false);

        bB6.setBackground(new java.awt.Color(153, 153, 153));
        bB6.setBorder(null);
        bB6.setFocusPainted(false);

        bB5.setBackground(new java.awt.Color(255, 255, 255));
        bB5.setBorder(null);
        bB5.setFocusPainted(false);

        bB4.setBackground(new java.awt.Color(153, 153, 153));
        bB4.setBorder(null);
        bB4.setFocusPainted(false);

        bB3.setBackground(new java.awt.Color(255, 255, 255));
        bB3.setBorder(null);
        bB3.setFocusPainted(false);

        bB2.setBackground(new java.awt.Color(153, 153, 153));
        bB2.setBorder(null);
        bB2.setFocusPainted(false);

        bB1.setBackground(new java.awt.Color(255, 255, 255));
        bB1.setBorder(null);
        bB1.setFocusPainted(false);

        bC8.setBackground(new java.awt.Color(255, 255, 255));
        bC8.setBorder(null);
        bC8.setFocusPainted(false);

        bC7.setBackground(new java.awt.Color(153, 153, 153));
        bC7.setBorder(null);
        bC7.setFocusPainted(false);

        bC6.setBackground(new java.awt.Color(255, 255, 255));
        bC6.setBorder(null);
        bC6.setFocusPainted(false);

        bC5.setBackground(new java.awt.Color(153, 153, 153));
        bC5.setBorder(null);
        bC5.setFocusPainted(false);

        bC4.setBackground(new java.awt.Color(255, 255, 255));
        bC4.setBorder(null);
        bC4.setFocusPainted(false);

        bC3.setBackground(new java.awt.Color(153, 153, 153));
        bC3.setBorder(null);
        bC3.setFocusPainted(false);

        bC2.setBackground(new java.awt.Color(255, 255, 255));
        bC2.setBorder(null);
        bC2.setFocusPainted(false);

        bC1.setBackground(new java.awt.Color(153, 153, 153));
        bC1.setBorder(null);
        bC1.setFocusPainted(false);

        bD8.setBackground(new java.awt.Color(153, 153, 153));
        bD8.setBorder(null);
        bD8.setFocusPainted(false);

        bD7.setBackground(new java.awt.Color(255, 255, 255));
        bD7.setBorder(null);
        bD7.setFocusPainted(false);

        bD6.setBackground(new java.awt.Color(153, 153, 153));
        bD6.setBorder(null);
        bD6.setFocusPainted(false);

        bD5.setBackground(new java.awt.Color(255, 255, 255));
        bD5.setBorder(null);
        bD5.setFocusPainted(false);

        bD4.setBackground(new java.awt.Color(153, 153, 153));
        bD4.setBorder(null);
        bD4.setFocusPainted(false);

        bD3.setBackground(new java.awt.Color(255, 255, 255));
        bD3.setBorder(null);
        bD3.setFocusPainted(false);

        bD2.setBackground(new java.awt.Color(153, 153, 153));
        bD2.setBorder(null);
        bD2.setFocusPainted(false);

        bD1.setBackground(new java.awt.Color(255, 255, 255));
        bD1.setBorder(null);
        bD1.setFocusPainted(false);

        bE8.setBackground(new java.awt.Color(255, 255, 255));
        bE8.setBorder(null);
        bE8.setFocusPainted(false);

        bE7.setBackground(new java.awt.Color(153, 153, 153));
        bE7.setBorder(null);
        bE7.setFocusPainted(false);

        bE6.setBackground(new java.awt.Color(255, 255, 255));
        bE6.setBorder(null);
        bE6.setFocusPainted(false);

        bE5.setBackground(new java.awt.Color(153, 153, 153));
        bE5.setBorder(null);
        bE5.setFocusPainted(false);

        bE4.setBackground(new java.awt.Color(255, 255, 255));
        bE4.setBorder(null);
        bE4.setFocusPainted(false);

        bE3.setBackground(new java.awt.Color(153, 153, 153));
        bE3.setBorder(null);
        bE3.setFocusPainted(false);

        bE2.setBackground(new java.awt.Color(255, 255, 255));
        bE2.setBorder(null);
        bE2.setFocusPainted(false);

        bE1.setBackground(new java.awt.Color(153, 153, 153));
        bE1.setBorder(null);
        bE1.setFocusPainted(false);

        bF8.setBackground(new java.awt.Color(153, 153, 153));
        bF8.setBorder(null);
        bF8.setFocusPainted(false);

        bF7.setBackground(new java.awt.Color(255, 255, 255));
        bF7.setBorder(null);
        bF7.setFocusPainted(false);

        bF6.setBackground(new java.awt.Color(153, 153, 153));
        bF6.setBorder(null);
        bF6.setFocusPainted(false);

        bF5.setBackground(new java.awt.Color(255, 255, 255));
        bF5.setBorder(null);
        bF5.setFocusPainted(false);

        bF4.setBackground(new java.awt.Color(153, 153, 153));
        bF4.setBorder(null);
        bF4.setFocusPainted(false);

        bF3.setBackground(new java.awt.Color(255, 255, 255));
        bF3.setBorder(null);
        bF3.setFocusPainted(false);

        bF2.setBackground(new java.awt.Color(153, 153, 153));
        bF2.setBorder(null);
        bF2.setFocusPainted(false);

        bF1.setBackground(new java.awt.Color(255, 255, 255));
        bF1.setBorder(null);
        bF1.setFocusPainted(false);

        bG8.setBackground(new java.awt.Color(255, 255, 255));
        bG8.setBorder(null);
        bG8.setFocusPainted(false);

        bG7.setBackground(new java.awt.Color(153, 153, 153));
        bG7.setBorder(null);
        bG7.setFocusPainted(false);

        bG6.setBackground(new java.awt.Color(255, 255, 255));
        bG6.setBorder(null);
        bG6.setFocusPainted(false);

        bG5.setBackground(new java.awt.Color(153, 153, 153));
        bG5.setBorder(null);
        bG5.setFocusPainted(false);

        bG4.setBackground(new java.awt.Color(255, 255, 255));
        bG4.setBorder(null);
        bG4.setFocusPainted(false);

        bG3.setBackground(new java.awt.Color(153, 153, 153));
        bG3.setBorder(null);
        bG3.setFocusPainted(false);

        bG2.setBackground(new java.awt.Color(255, 255, 255));
        bG2.setBorder(null);
        bG2.setFocusPainted(false);

        bG1.setBackground(new java.awt.Color(153, 153, 153));
        bG1.setBorder(null);
        bG1.setFocusPainted(false);

        bH8.setBackground(new java.awt.Color(153, 153, 153));
        bH8.setBorder(null);
        bH8.setFocusPainted(false);

        bH7.setBackground(new java.awt.Color(255, 255, 255));
        bH7.setBorder(null);
        bH7.setFocusPainted(false);

        bH6.setBackground(new java.awt.Color(153, 153, 153));
        bH6.setBorder(null);
        bH6.setFocusPainted(false);

        bH5.setBackground(new java.awt.Color(255, 255, 255));
        bH5.setBorder(null);
        bH5.setFocusPainted(false);

        bH4.setBackground(new java.awt.Color(153, 153, 153));
        bH4.setBorder(null);
        bH4.setFocusPainted(false);

        bH3.setBackground(new java.awt.Color(255, 255, 255));
        bH3.setBorder(null);
        bH3.setFocusPainted(false);

        bH2.setBackground(new java.awt.Color(153, 153, 153));
        bH2.setBorder(null);
        bH2.setFocusPainted(false);

        bH1.setBackground(new java.awt.Color(255, 255, 255));
        bH1.setBorder(null);
        bH1.setFocusPainted(false);

        javax.swing.GroupLayout boardContainerLayout = new javax.swing.GroupLayout(boardContainer);
        boardContainer.setLayout(boardContainerLayout);
        boardContainerLayout.setHorizontalGroup(
            boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardContainerLayout.createSequentialGroup()
                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(boardContainerLayout.createSequentialGroup()
                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(boardContainerLayout.createSequentialGroup()
                                .addComponent(bA5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bB5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(boardContainerLayout.createSequentialGroup()
                                    .addComponent(bA6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(bB6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(boardContainerLayout.createSequentialGroup()
                                        .addComponent(bA7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bB7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, boardContainerLayout.createSequentialGroup()
                                        .addComponent(bA8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bB8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, boardContainerLayout.createSequentialGroup()
                                .addComponent(bC5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bD5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bE5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bF5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bG5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bH5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(boardContainerLayout.createSequentialGroup()
                                    .addComponent(bC6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(bD6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(bE6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(bF6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(bG6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(bH6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(boardContainerLayout.createSequentialGroup()
                                    .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(boardContainerLayout.createSequentialGroup()
                                            .addComponent(bC7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, 0)
                                            .addComponent(bD7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(boardContainerLayout.createSequentialGroup()
                                            .addComponent(bC8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, 0)
                                            .addComponent(bD8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(bE8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bE7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 0, 0)
                                    .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(boardContainerLayout.createSequentialGroup()
                                            .addComponent(bF8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, 0)
                                            .addComponent(bG8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(boardContainerLayout.createSequentialGroup()
                                            .addComponent(bF7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(bG7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(bH8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bH7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(boardContainerLayout.createSequentialGroup()
                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardContainerLayout.createSequentialGroup()
                                .addComponent(bA4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bB4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardContainerLayout.createSequentialGroup()
                                .addComponent(bA1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bB1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardContainerLayout.createSequentialGroup()
                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bA3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bA2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bB2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bB3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardContainerLayout.createSequentialGroup()
                                .addComponent(bC1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bD1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bE1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bF1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bG1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardContainerLayout.createSequentialGroup()
                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(boardContainerLayout.createSequentialGroup()
                                        .addComponent(bC3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bD3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bE3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bF3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(boardContainerLayout.createSequentialGroup()
                                        .addComponent(bC2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bD2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bE2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bF2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, 0)
                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(boardContainerLayout.createSequentialGroup()
                                        .addComponent(bG3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(bH3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(boardContainerLayout.createSequentialGroup()
                                        .addComponent(bG2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(bH1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bH2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(boardContainerLayout.createSequentialGroup()
                                .addComponent(bC4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bD4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bE4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bF4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bG4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(bH4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, 0))
        );
        boardContainerLayout.setVerticalGroup(
            boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardContainerLayout.createSequentialGroup()
                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(boardContainerLayout.createSequentialGroup()
                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(boardContainerLayout.createSequentialGroup()
                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bB4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(boardContainerLayout.createSequentialGroup()
                                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(boardContainerLayout.createSequentialGroup()
                                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(boardContainerLayout.createSequentialGroup()
                                                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(bA8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(bB8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(bC8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(bD8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(bE8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(bF8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(bG8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(bH8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(0, 0, 0)
                                                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(bA7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(boardContainerLayout.createSequentialGroup()
                                                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(bC7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bD7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bE7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bF7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bG7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bH7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(bC6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bD6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bE6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bF6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bG6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(bH6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                    .addGroup(boardContainerLayout.createSequentialGroup()
                                                        .addComponent(bB7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(0, 0, 0)
                                                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(bB6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(bA6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(bB5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(bA5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(bG5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(bE5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(bD5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(bF5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(bH5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(bC5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(bA4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bE4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bC4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bD4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bF4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bG4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bH4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bA3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bE3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bC3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bD3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bF3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bG3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bH3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(bB3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bA2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bE2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bC2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bD2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bF2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bG2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bH2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(bB2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(boardContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bF1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bD1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bA1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bB1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bC1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bE1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bG1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bH1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        globalContainer.add(boardContainer);
        boardContainer.setBounds(30, 30, 440, 440);
        boardContainer.getAccessibleContext().setAccessibleName("");
        boardContainer.getAccessibleContext().setAccessibleDescription("");

        controlPanel.setFocusable(false);
        controlPanel.setOpaque(false);

        takeMoveBackButton.setBackground(new java.awt.Color(100, 120, 120));
        takeMoveBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/previous_move.png"))); // NOI18N
        takeMoveBackButton.setToolTipText("take move back");
        takeMoveBackButton.setBorder(null);
        takeMoveBackButton.setContentAreaFilled(false);
        takeMoveBackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        takeMoveBackButton.setDoubleBuffered(true);
        takeMoveBackButton.setFocusPainted(false);
        takeMoveBackButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        takeMoveBackButton.setMaximumSize(new java.awt.Dimension(34, 34));
        takeMoveBackButton.setMinimumSize(new java.awt.Dimension(34, 34));
        takeMoveBackButton.setOpaque(true);
        takeMoveBackButton.setPreferredSize(new java.awt.Dimension(34, 34));
        takeMoveBackButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        takeMoveBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                takeMoveBackButtonActionPerformed(evt);
            }
        });

        moveForwardButton.setBackground(new java.awt.Color(100, 120, 120));
        moveForwardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next_move.png"))); // NOI18N
        moveForwardButton.setToolTipText("Move forward");
        moveForwardButton.setBorder(null);
        moveForwardButton.setContentAreaFilled(false);
        moveForwardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        moveForwardButton.setDoubleBuffered(true);
        moveForwardButton.setFocusPainted(false);
        moveForwardButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        moveForwardButton.setMaximumSize(new java.awt.Dimension(34, 34));
        moveForwardButton.setMinimumSize(new java.awt.Dimension(34, 34));
        moveForwardButton.setOpaque(true);
        moveForwardButton.setPreferredSize(new java.awt.Dimension(34, 34));
        moveForwardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        moveForwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveForwardButtonActionPerformed(evt);
            }
        });

        brainButton.setBorder(null);
        brainButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/brain.png"))); // NOI18N
        brainButton.setToolTipText("Brain button : stops engine move evaluation support is activated");
        brainButton.setMaximumSize(new java.awt.Dimension(34, 34));
        brainButton.setMinimumSize(new java.awt.Dimension(34, 34));
        brainButton.setPreferredSize(new java.awt.Dimension(34, 34));
        brainButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                brainButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                brainButtonMouseExited(evt);
            }
        });
        brainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brainButtonActionPerformed(evt);
            }
        });

        forceEngineToTakeMoveButton.setBackground(new java.awt.Color(100, 120, 120));
        forceEngineToTakeMoveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/jelly_fish_button.png"))); // NOI18N
        forceEngineToTakeMoveButton.setToolTipText("Force engine to take move");
        forceEngineToTakeMoveButton.setBorder(null);
        forceEngineToTakeMoveButton.setContentAreaFilled(false);
        forceEngineToTakeMoveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forceEngineToTakeMoveButton.setDoubleBuffered(true);
        forceEngineToTakeMoveButton.setFocusPainted(false);
        forceEngineToTakeMoveButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        forceEngineToTakeMoveButton.setMaximumSize(new java.awt.Dimension(34, 34));
        forceEngineToTakeMoveButton.setMinimumSize(new java.awt.Dimension(34, 34));
        forceEngineToTakeMoveButton.setOpaque(true);
        forceEngineToTakeMoveButton.setPreferredSize(new java.awt.Dimension(34, 34));
        forceEngineToTakeMoveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        forceEngineToTakeMoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forceMoveMenuItemActionPerformed(evt);
            }
        });

        activateGUIEngineSupportButton.setBackground(new java.awt.Color(100, 120, 120));
        activateGUIEngineSupportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/activate_search.png"))); // NOI18N
        activateGUIEngineSupportButton.setToolTipText("Activate engine move evaluation support");
        activateGUIEngineSupportButton.setBorder(null);
        activateGUIEngineSupportButton.setContentAreaFilled(false);
        activateGUIEngineSupportButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        activateGUIEngineSupportButton.setDoubleBuffered(true);
        activateGUIEngineSupportButton.setFocusPainted(false);
        activateGUIEngineSupportButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        activateGUIEngineSupportButton.setMaximumSize(new java.awt.Dimension(34, 34));
        activateGUIEngineSupportButton.setMinimumSize(new java.awt.Dimension(34, 34));
        activateGUIEngineSupportButton.setOpaque(true);
        activateGUIEngineSupportButton.setPreferredSize(new java.awt.Dimension(34, 34));
        activateGUIEngineSupportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        activateGUIEngineSupportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activateGUIEngineSupportButtonActionPerformed(evt);
            }
        });

        newGameWhitesButton.setBackground(new java.awt.Color(100, 120, 120));
        newGameWhitesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_game_white.png"))); // NOI18N
        newGameWhitesButton.setToolTipText("New game, play whites");
        newGameWhitesButton.setBorder(null);
        newGameWhitesButton.setContentAreaFilled(false);
        newGameWhitesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newGameWhitesButton.setDoubleBuffered(true);
        newGameWhitesButton.setFocusPainted(false);
        newGameWhitesButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        newGameWhitesButton.setOpaque(true);
        newGameWhitesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        newGameWhitesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameWhitesMenuItemActionPerformed(evt);
            }
        });

        newGameBlackButton.setBackground(new java.awt.Color(100, 120, 120));
        newGameBlackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new_game_black.png"))); // NOI18N
        newGameBlackButton.setToolTipText("New game, play blacks");
        newGameBlackButton.setBorder(null);
        newGameBlackButton.setContentAreaFilled(false);
        newGameBlackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newGameBlackButton.setDoubleBuffered(true);
        newGameBlackButton.setFocusPainted(false);
        newGameBlackButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        newGameBlackButton.setMaximumSize(new java.awt.Dimension(34, 34));
        newGameBlackButton.setMinimumSize(new java.awt.Dimension(34, 34));
        newGameBlackButton.setOpaque(true);
        newGameBlackButton.setPreferredSize(new java.awt.Dimension(34, 34));
        newGameBlackButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        newGameBlackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameBlacksMenuItemActionPerformed(evt);
            }
        });

        deeperPlyDepthButton.setBackground(new java.awt.Color(100, 120, 120));
        deeperPlyDepthButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ply_deeper.png"))); // NOI18N
        deeperPlyDepthButton.setToolTipText("Change ply depth : go deeper");
        deeperPlyDepthButton.setBorder(null);
        deeperPlyDepthButton.setContentAreaFilled(false);
        deeperPlyDepthButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deeperPlyDepthButton.setDoubleBuffered(true);
        deeperPlyDepthButton.setFocusPainted(false);
        deeperPlyDepthButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        deeperPlyDepthButton.setOpaque(true);
        deeperPlyDepthButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        deeperPlyDepthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plydeeperMenuItemActionPerformed(evt);
            }
        });

        shallowerPlyDepthButton.setBackground(new java.awt.Color(100, 120, 120));
        shallowerPlyDepthButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ply_shalower.png"))); // NOI18N
        shallowerPlyDepthButton.setToolTipText("Change ply depth : go shallower");
        shallowerPlyDepthButton.setBorder(null);
        shallowerPlyDepthButton.setContentAreaFilled(false);
        shallowerPlyDepthButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        shallowerPlyDepthButton.setDoubleBuffered(true);
        shallowerPlyDepthButton.setFocusPainted(false);
        shallowerPlyDepthButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        shallowerPlyDepthButton.setOpaque(true);
        shallowerPlyDepthButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        shallowerPlyDepthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plyShallowerMenuItemActionPerformed(evt);
            }
        });

        displayConsoleButton.setBackground(new java.awt.Color(100, 120, 120));
        displayConsoleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/console.png"))); // NOI18N
        displayConsoleButton.setToolTipText("Change console visibility");
        displayConsoleButton.setBorder(null);
        displayConsoleButton.setContentAreaFilled(false);
        displayConsoleButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        displayConsoleButton.setDoubleBuffered(true);
        displayConsoleButton.setFocusPainted(false);
        displayConsoleButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        displayConsoleButton.setOpaque(true);
        displayConsoleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                controlButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                controlButtonMouseExited(evt);
            }
        });
        displayConsoleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayConsoleMenuItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addComponent(brainButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(activateGUIEngineSupportButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(takeMoveBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(forceEngineToTakeMoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(moveForwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(newGameWhitesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(newGameBlackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(deeperPlyDepthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(shallowerPlyDepthButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(displayConsoleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(moveForwardButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newGameBlackButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(activateGUIEngineSupportButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newGameWhitesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(brainButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(forceEngineToTakeMoveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(takeMoveBackButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deeperPlyDepthButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(shallowerPlyDepthButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayConsoleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        globalContainer.add(controlPanel);
        controlPanel.setBounds(30, 480, 440, 40);

        menuBar.setBorder(null);
        menuBar.setDoubleBuffered(true);
        menuBar.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        menuBar.setName(""); // NOI18N

        menuFile.setText("File");
        menuFile.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        loadFromGameDataMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        loadFromGameDataMenuItem.setText("Load from game history");
        loadFromGameDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFromGameDataMenuItemActionPerformed(evt);
            }
        });
        menuFile.add(loadFromGameDataMenuItem);

        menuBar.add(menuFile);

        menuEdit.setText("Edit");
        menuEdit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        displayConsoleMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        displayConsoleMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        displayConsoleMenuItem.setText("Change console visibility");
        displayConsoleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayConsoleMenuItemActionPerformed(evt);
            }
        });
        menuEdit.add(displayConsoleMenuItem);

        appendAllOutputCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        appendAllOutputCheckBoxMenuItem.setText("Display all engine output to console");
        appendAllOutputCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendAllOutputCheckBoxMenuItemActionPerformed(evt);
            }
        });
        menuEdit.add(appendAllOutputCheckBoxMenuItem);

        displayCoordinatesCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        displayCoordinatesCheckBoxMenuItem.setSelected(true);
        displayCoordinatesCheckBoxMenuItem.setText("Display board coordinates");
        displayCoordinatesCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayCoordinatesCheckBoxMenuItemActionPerformed(evt);
            }
        });
        menuEdit.add(displayCoordinatesCheckBoxMenuItem);
        menuEdit.add(jSeparator4);

        alwaysLoadPreviousGameCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        alwaysLoadPreviousGameCheckBoxMenuItem.setSelected(true);
        alwaysLoadPreviousGameCheckBoxMenuItem.setText("Always reload previous game");
        alwaysLoadPreviousGameCheckBoxMenuItem.setDoubleBuffered(true);
        alwaysLoadPreviousGameCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alwaysLoadPreviousGameCheckBoxMenuItemActionPerformed(evt);
            }
        });
        menuEdit.add(alwaysLoadPreviousGameCheckBoxMenuItem);

        saveGameDataOnCloseCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saveGameDataOnCloseCheckBoxMenuItem.setSelected(true);
        saveGameDataOnCloseCheckBoxMenuItem.setText("Always save game data on close");
        saveGameDataOnCloseCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGameDataOnCloseCheckBoxMenuItemActionPerformed(evt);
            }
        });
        menuEdit.add(saveGameDataOnCloseCheckBoxMenuItem);

        menuBar.add(menuEdit);

        gameMenu.setText("Game");
        gameMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        newGameWhitesMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        newGameWhitesMenuItem.setText("New game : play whites");
        newGameWhitesMenuItem.setDoubleBuffered(true);
        newGameWhitesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameWhitesMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(newGameWhitesMenuItem);

        newGameBlacksMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        newGameBlacksMenuItem.setText("New game : play blacks");
        newGameBlacksMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameBlacksMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(newGameBlacksMenuItem);
        gameMenu.add(jSeparator1);

        pawnPromotionjMenu.setText("Pawn promotion settings");
        pawnPromotionjMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        queenPromoCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        queenPromoCheckBoxMenuItem.setSelected(true);
        queenPromoCheckBoxMenuItem.setText("Promote to queen");
        queenPromoCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queenPromoCheckBoxMenuItemActionPerformed(evt);
            }
        });
        pawnPromotionjMenu.add(queenPromoCheckBoxMenuItem);

        bishopPromoCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bishopPromoCheckBoxMenuItem.setText("Promote to bishop");
        bishopPromoCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bishopPromoCheckBoxMenuItemActionPerformed(evt);
            }
        });
        pawnPromotionjMenu.add(bishopPromoCheckBoxMenuItem);

        knightPromoCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        knightPromoCheckBoxMenuItem.setText("Promote to knight");
        knightPromoCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                knightPromoCheckBoxMenuItemActionPerformed(evt);
            }
        });
        pawnPromotionjMenu.add(knightPromoCheckBoxMenuItem);

        rookPromoCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rookPromoCheckBoxMenuItem.setText("Promote to rook");
        rookPromoCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rookPromoCheckBoxMenuItemActionPerformed(evt);
            }
        });
        pawnPromotionjMenu.add(rookPromoCheckBoxMenuItem);

        gameMenu.add(pawnPromotionjMenu);

        forceMoveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        forceMoveMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        forceMoveMenuItem.setText("Force engine to play move  ");
        forceMoveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forceMoveMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(forceMoveMenuItem);

        activateGuiEngineSearchCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        activateGuiEngineSearchCheckBoxMenuItem.setSelected(true);
        activateGuiEngineSearchCheckBoxMenuItem.setText("Activate engine support");
        activateGuiEngineSearchCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activateGuiEngineSearchCheckBoxMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(activateGuiEngineSearchCheckBoxMenuItem);
        gameMenu.add(jSeparator2);

        plyDepthjMenu.setText("Change ply depth");
        plyDepthjMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        plydeeperMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        plydeeperMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        plydeeperMenuItem.setText("Go deeper");
        plydeeperMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plydeeperMenuItemActionPerformed(evt);
            }
        });
        plyDepthjMenu.add(plydeeperMenuItem);

        plyShallowerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        plyShallowerMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        plyShallowerMenuItem.setText("Go shallower");
        plyShallowerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plyShallowerMenuItemActionPerformed(evt);
            }
        });
        plyDepthjMenu.add(plyShallowerMenuItem);

        gameMenu.add(plyDepthjMenu);

        difficultyMenu.setText("Difficulty settings");
        difficultyMenu.setToolTipText("");
        difficultyMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        dummyCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dummyCheckBoxMenuItem.setText("Dummy");
        dummyCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dummyCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultyMenu.add(dummyCheckBoxMenuItem);

        easyCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        easyCheckBoxMenuItem.setText("Easy");
        easyCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                easyCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultyMenu.add(easyCheckBoxMenuItem);

        mediumCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mediumCheckBoxMenuItem.setText("Medium");
        mediumCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediumCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultyMenu.add(mediumCheckBoxMenuItem);

        hardCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        hardCheckBoxMenuItem.setText("Hard");
        hardCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultyMenu.add(hardCheckBoxMenuItem);

        harderCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        harderCheckBoxMenuItem.setText("Harder");
        harderCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                harderCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultyMenu.add(harderCheckBoxMenuItem);

        expertCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        expertCheckBoxMenuItem.setText("Expert");
        expertCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expertCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultyMenu.add(expertCheckBoxMenuItem);

        masterCheckBoxMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masterCheckBoxMenuItem.setText("Master");
        masterCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterCheckBoxMenuItemActionPerformed(evt);
            }
        });
        difficultyMenu.add(masterCheckBoxMenuItem);

        gameMenu.add(difficultyMenu);
        gameMenu.add(jSeparator8);

        chessmenIconMenu.setText("Chess men display settings");
        chessmenIconMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        classicOneChessmenMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        classicOneChessmenMenuItem.setText("Classical n°1");
        classicOneChessmenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classicOneChessmenMenuItemActionPerformed(evt);
            }
        });
        chessmenIconMenu.add(classicOneChessmenMenuItem);

        classicTwoChessmenMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        classicTwoChessmenMenuItem.setText("Classical n°2");
        classicTwoChessmenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classicTwoChessmenMenuItemActionPerformed(evt);
            }
        });
        chessmenIconMenu.add(classicTwoChessmenMenuItem);

        twoDOneChessmenMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        twoDOneChessmenMenuItem.setText("2d modern n°1");
        twoDOneChessmenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoDOneChessmenMenuItemActionPerformed(evt);
            }
        });
        chessmenIconMenu.add(twoDOneChessmenMenuItem);

        gameMenu.add(chessmenIconMenu);

        boardIconMenu.setText("Chess board display settings");
        boardIconMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        boardDigitalOneMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        boardDigitalOneMenuItem.setText("Digital 1");
        boardDigitalOneMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardDigitalOneMenuItemActionPerformed(evt);
            }
        });
        boardIconMenu.add(boardDigitalOneMenuItem);

        boardDigitalTwoMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        boardDigitalTwoMenuItem.setText("Digital 2");
        boardDigitalTwoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardDigitalTwoMenuItemActionPerformed(evt);
            }
        });
        boardIconMenu.add(boardDigitalTwoMenuItem);

        boardDigitalThreeMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        boardDigitalThreeMenuItem.setText("Digital 3");
        boardDigitalThreeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardDigitalThreeMenuItemActionPerformed(evt);
            }
        });
        boardIconMenu.add(boardDigitalThreeMenuItem);

        boardDigitalFourMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        boardDigitalFourMenuItem.setText("Digital 4");
        boardDigitalFourMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardDigitalFourMenuItemActionPerformed(evt);
            }
        });
        boardIconMenu.add(boardDigitalFourMenuItem);

        boardEbonyIvoryMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        boardEbonyIvoryMenuItem.setText("Ebony & ivory");
        boardEbonyIvoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardEbonyIvoryMenuItemActionPerformed(evt);
            }
        });
        boardIconMenu.add(boardEbonyIvoryMenuItem);

        gameMenu.add(boardIconMenu);

        menuBar.add(gameMenu);

        AboutMenu.setText("About");
        AboutMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        aboutJellyFishMenuItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aboutJellyFishMenuItem.setText("About jellyfish");
        aboutJellyFishMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJellyFishMenuItemActionPerformed(evt);
            }
        });
        AboutMenu.add(aboutJellyFishMenuItem);

        helpMenu.setText("Help");
        helpMenu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AboutMenu.add(helpMenu);

        menuBar.add(AboutMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(globalContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(globalContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Menu items events">
    private void forceMoveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forceMoveMenuItemActionPerformed
        // Force engine to play what ever side.
        if (!driver.isPerforming()) {
            
            if (this.brainButton.isThinking()) {
                brainButtonStopThinking();
            }
            this.driver.getGame().updateGameSnapshotting();
            this.driver.getGame().executeMove();
        }
    }//GEN-LAST:event_forceMoveMenuItemActionPerformed

    private void newGameWhitesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameWhitesMenuItemActionPerformed
        
        if (!driver.isPerforming()) {
            
            Object[] options = MessageConst.OK_CANCEL_OPTIONS;
            int result = JOptionPane.showOptionDialog(this,
                String.format(MessageConst.NEW_GAME_CONFIRMATION_DIALOG_TEXT, UIConst.WHITE_STR),
                MessageConst.NEW_GAME_CONFIRMATION_DIALOG_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource(UIConst.NEW_GAME_WHITE_BUTTON_ICON)),
                options,
                options[0]);
            
            if (result == 0) {
                
                // Delete content of snapshots directory before game restart, otherwise
                // initial board snapshot will be deleted on new Game(...) call.
                BoardSnapshot.deleteSnapshots(new File(BoardSnapshot.getSNAPSHOT_PATH()));
                clearChessSquares();
                // Param 2, boolean is true = whites, false for blacks.
                this.driver.getStatusIO().getUserSettings().setWhite(true);
                this.driver.getStatusIO().getUserSettings().setColor(BoardConst.WHITE);
                this.driver.getStatusIO().getGameStatus().setSeconds(0);
                this.driver.restart(true, false, true, GameTypeConst.CHESS_GAME);
                brainButtonStopThinking();
                this.driver.getHelper().repaintAllChessSquares();
            }
        }
    }//GEN-LAST:event_newGameWhitesMenuItemActionPerformed

    private void newGameBlacksMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameBlacksMenuItemActionPerformed
        
        if (!driver.isPerforming()) {
            
            Object[] options = MessageConst.OK_CANCEL_OPTIONS;
            int result = JOptionPane.showOptionDialog(this,
                String.format(MessageConst.NEW_GAME_CONFIRMATION_DIALOG_TEXT, UIConst.BLACK_STR),
                MessageConst.NEW_GAME_CONFIRMATION_DIALOG_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource(UIConst.NEW_GAME_BLACK_BUTTON_ICON)),
                options,
                options[0]);
            
            if (result == 0) {
                
                // Delete content of snapshots directory before game restart, otherwise
                // initial board snapshot will be deleted on new Game(...) call.
                BoardSnapshot.deleteSnapshots(new File(BoardSnapshot.getSNAPSHOT_PATH()));
                clearChessSquares();
                // Param 2, boolean is true = whites, false for blacks.
                this.driver.getStatusIO().getUserSettings().setWhite(false);
                this.driver.getStatusIO().getUserSettings().setColor(BoardConst.BLACK);
                this.driver.getStatusIO().getGameStatus().setSeconds(0);
                this.driver.restart(true, false, true, GameTypeConst.CHESS_GAME);
                brainButtonStopThinking();
                this.driver.getHelper().repaintAllChessSquares();
            }
        }
    }//GEN-LAST:event_newGameBlacksMenuItemActionPerformed

    private void plydeeperMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plydeeperMenuItemActionPerformed
        
        if (!driver.isPerforming()) {
            int tempDepth = this.driver.getGame().getDepth();
            if (tempDepth < 50) {
                this.driver.getGame().setDepth(++tempDepth);
                this.driver.getStatusIO().getUserSettings().setDepth(this.driver.getGame().getDepth());
            }
            driver.getHelper().updatePlyDepthDisplay();
            setDifficultySettingsCheckBoxs(this.driver.getGame().getDepth());
        }
    }//GEN-LAST:event_plydeeperMenuItemActionPerformed

    private void plyShallowerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plyShallowerMenuItemActionPerformed
        
        if (!driver.isPerforming()) {
            int tempDepth = this.driver.getGame().getDepth();
            if (tempDepth > 0) {
                this.driver.getGame().setDepth(--tempDepth);
                this.driver.getStatusIO().getUserSettings().setDepth(this.driver.getGame().getDepth());
            }
            driver.getHelper().updatePlyDepthDisplay();
            setDifficultySettingsCheckBoxs(this.driver.getGame().getDepth());
        }
    }//GEN-LAST:event_plyShallowerMenuItemActionPerformed

    private void aboutJellyFishMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJellyFishMenuItemActionPerformed
        
        if (!driver.isPerforming()) {
            
            try {
                Desktop.getDesktop().browse(new URI(UIConst.JELLYFISH_HOME_PAGE));
            } catch (URISyntaxException ex) {
                JOptionPane.showMessageDialog(this, MessageConst.OBSOLETE_VER_JELLYFISH,
                    MessageConst.OBSOLETE_VER_JELLYFISH_TITLE,
                    JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, MessageConst.IO_EXCEPTION_JELLYFISH_HOME_PAGE,
                    MessageConst.IO_EXCEPTION_JELLYFISH_HOME_PAGE_TITLE,
                    JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_aboutJellyFishMenuItemActionPerformed
    
    /**
     * Form is closing down, all processes must be shutdown.
     * @param evt 
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        // Close engine process.
        IOExternalEngine.getInstance().writeToEngine(UCIConst.ENGINE_QUIT, MessageTypeConst.NOT_SO_TRIVIAL);
        // Shut down Tiny Sound.
        driver.getSoundPlayer().shutDown();
        // Set game moves first. Before that clear the TreeMap of game moves :
        driver.getStatusIO().getGameStatus().getGameMoves().clear();
        driver.getStatusIO().getGameStatus().getFenMoves().clear();
        driver.getStatusIO().getGameStatus().getGameMoves().putAll(driver.getGame().getGameMoves());
        driver.getStatusIO().getGameStatus().getFenMoves().putAll(this.driver.getGame().getFenMoves());
        driver.getStatusIO().getGameStatus().setMoveCount(this.driver.getGame().getMoveCount());
        driver.getStatusIO().getGameStatus().setSeconds(GameTimer.getInstance().getTicks());
        // Perform serialization in driver class :
        driver.getStatusIO().serializeStatus();
        // Stop Timer :
        GameTimer.getInstance().getSwingTimer().stop();
        // Delete content of temp snapshots directory.
        BoardSnapshot.deleteSnapshots(new File(BoardSnapshot.getSNAPSHOT_PATH()));
        
        
        if (!driver.isCurrentlyReloadingPreviousGame() &&
            this.driver.getStatusIO().getUserSettings().isBackupGameData() &&
            this.driver.getGame().getMoveCount() > 0) {
            try {
                DataUtils.saveGameData(this.driver.getEngineOponentColor(),
                    this.driver.getGame().getFenMoves(), this.driver.getGame().getGameMoves(),
                    this.driver.getGame().getMoveCount(), GameTimer.getInstance().getTicks());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_formWindowClosing

    private void classicOneChessmenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classicOneChessmenMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getHelper().swapChessMenIcons(UIConst.CLASSIC_ONE);
            this.driver.getStatusIO().getUserSettings().setChessmenStyle(UIConst.CLASSIC_ONE);
        }
    }//GEN-LAST:event_classicOneChessmenMenuItemActionPerformed

    private void classicTwoChessmenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classicTwoChessmenMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getHelper().swapChessMenIcons(UIConst.CLASSIC_TWO);
            this.driver.getStatusIO().getUserSettings().setChessmenStyle(UIConst.CLASSIC_TWO);
        }
    }//GEN-LAST:event_classicTwoChessmenMenuItemActionPerformed

    private void twoDOneChessmenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoDOneChessmenMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getHelper().swapChessMenIcons(UIConst.TWO_D_MODERN_ONE);
            this.driver.getStatusIO().getUserSettings().setChessmenStyle(UIConst.TWO_D_MODERN_ONE);
        }
    }//GEN-LAST:event_twoDOneChessmenMenuItemActionPerformed

    private void alwaysLoadPreviousGameCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alwaysLoadPreviousGameCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            // Set ON or OFF the 'reload previous game feature'. By default is set to true.
            this.driver.getStatusIO().getUserSettings().setLoadPreviousGame(
                alwaysLoadPreviousGameCheckBoxMenuItem.isSelected());
        }
    }//GEN-LAST:event_alwaysLoadPreviousGameCheckBoxMenuItemActionPerformed

    /**
     * Init gui params and controls.
     * @param evt 
     */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        // Menu bar visibility :
        this.menuBar.setVisible(true); // this.driver.getStatusIO().getUserSettings().isMenuBarVisible());
        // Set alwaysLoadPreviousGameCheckBoxMenuItem checkBox menuItem state 
        // according to UserSettings deserialization :
        alwaysLoadPreviousGameCheckBoxMenuItem.setSelected(
            this.driver.getStatusIO().getUserSettings().isLoadPreviousGame());
        appendAllOutputCheckBoxMenuItem.setSelected(
            this.driver.getStatusIO().getUserSettings().isDisplayAll());
        this.driver.getWriter().setDisplayAll(
            this.driver.getStatusIO().getUserSettings().isDisplayAll());
        activateGuiEngineSearchCheckBoxMenuItem.setSelected(
            this.driver.getStatusIO().getUserSettings().isActivateInfiniteSearchAfterEngineMove());
        saveGameDataOnCloseCheckBoxMenuItem.setSelected(
            this.driver.getStatusIO().getUserSettings().isBackupGameData());
        
        // Ifinite search support activation ? :
        if (this.driver.getStatusIO().getUserSettings().isActivateInfiniteSearchAfterEngineMove()) {
            activateGUIEngineSupportButton.setIcon(
                new javax.swing.ImageIcon(getClass().getResource(
                UIConst.SEARCH_SUPPORT_ACTIVATED_BUTTON_ICON)));
        } else {
            activateGUIEngineSupportButton.setIcon(
                new javax.swing.ImageIcon(getClass().getResource(
                UIConst.SEARCH_SUPPORT_DISACTIVATED_BUTTON_ICON)));
        }
        
        // Reset difficulty setings :
        setDifficultySettingsCheckBoxs(this.driver.getStatusIO().getUserSettings().getDepth());
        this.driver.getHelper().updatePlyDepthDisplay();

        // Console visibility :
        if (this.driver.getStatusIO().getUserSettings().isConsoleVisible()) {
            // Update console location :
            this.console.updateConsoleLocationOnStartUp(this.getX(), this.getY(), this.getHeight(), this.getWidth());
        } else {
            this.console.cancelVisibility();
        }
    }//GEN-LAST:event_formWindowOpened

    private void appendAllOutputCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appendAllOutputCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            boolean selected = appendAllOutputCheckBoxMenuItem.isSelected();
            this.driver.getWriter().setDisplayAll(selected);
            this.driver.getStatusIO().getUserSettings().setDisplayAll(selected);
        }
    }//GEN-LAST:event_appendAllOutputCheckBoxMenuItemActionPerformed

    private void queenPromoCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queenPromoCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            setPawnPromotionType(UCIConst.QUEEN_PROMOTION);
            clearAllPromotionCheckBoxs(UCIConst.QUEEN_PROMOTION);
        }
    }//GEN-LAST:event_queenPromoCheckBoxMenuItemActionPerformed

    private void bishopPromoCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bishopPromoCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            setPawnPromotionType(UCIConst.BISHOP_PROMOTION);
            clearAllPromotionCheckBoxs(UCIConst.BISHOP_PROMOTION);
        }
    }//GEN-LAST:event_bishopPromoCheckBoxMenuItemActionPerformed

    private void knightPromoCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_knightPromoCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            setPawnPromotionType(UCIConst.KNIGHT_PROMOTION);
            clearAllPromotionCheckBoxs(UCIConst.KNIGHT_PROMOTION);
        }
    }//GEN-LAST:event_knightPromoCheckBoxMenuItemActionPerformed

    private void rookPromoCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rookPromoCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            setPawnPromotionType(UCIConst.ROOK_PROMOTION);
            clearAllPromotionCheckBoxs(UCIConst.ROOK_PROMOTION);
        }
    }//GEN-LAST:event_rookPromoCheckBoxMenuItemActionPerformed

    private void dummyCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dummyCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getGame().setDepth(UCIConst.DEPTH_DUMMY);
            this.driver.getStatusIO().getUserSettings().setDepth(UCIConst.DEPTH_DUMMY);
            clearAllDifficultySettingsCheckBoxs(dummyCheckBoxMenuItem, dummyPopupCheckBoxMenuItem);
            driver.getHelper().updatePlyDepthDisplay();
        }
    }//GEN-LAST:event_dummyCheckBoxMenuItemActionPerformed

    private void easyCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_easyCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getGame().setDepth(UCIConst.DEPTH_EASY);
            this.driver.getStatusIO().getUserSettings().setDepth(UCIConst.DEPTH_EASY);
            clearAllDifficultySettingsCheckBoxs(easyCheckBoxMenuItem, easyPopupCheckBoxMenuItem);
            driver.getHelper().updatePlyDepthDisplay();
        }
    }//GEN-LAST:event_easyCheckBoxMenuItemActionPerformed

    private void mediumCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mediumCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getGame().setDepth(UCIConst.DEPTH_MEDIUM);
            this.driver.getStatusIO().getUserSettings().setDepth(UCIConst.DEPTH_MEDIUM);
            clearAllDifficultySettingsCheckBoxs(mediumCheckBoxMenuItem, mediumPopupCheckBoxMenuItem);
            driver.getHelper().updatePlyDepthDisplay();
        }
    }//GEN-LAST:event_mediumCheckBoxMenuItemActionPerformed

    private void hardCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getGame().setDepth(UCIConst.DEPTH_HARD);
            this.driver.getStatusIO().getUserSettings().setDepth(UCIConst.DEPTH_HARD);
            clearAllDifficultySettingsCheckBoxs(hardCheckBoxMenuItem, hardPopupCheckBoxMenuItem);
            driver.getHelper().updatePlyDepthDisplay();
        }
    }//GEN-LAST:event_hardCheckBoxMenuItemActionPerformed

    private void harderCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_harderCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getGame().setDepth(UCIConst.DEPTH_HARDER);
            this.driver.getStatusIO().getUserSettings().setDepth(UCIConst.DEPTH_HARDER);
            clearAllDifficultySettingsCheckBoxs(harderCheckBoxMenuItem, harderPopupCheckBoxMenuItem);
            driver.getHelper().updatePlyDepthDisplay();
        }
    }//GEN-LAST:event_harderCheckBoxMenuItemActionPerformed

    private void expertCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expertCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getGame().setDepth(UCIConst.DEPTH_EXPERT);
            this.driver.getStatusIO().getUserSettings().setDepth(UCIConst.DEPTH_EXPERT);
            clearAllDifficultySettingsCheckBoxs(expertCheckBoxMenuItem, expertPopupCheckBoxMenuItem);
            driver.getHelper().updatePlyDepthDisplay();
        }
    }//GEN-LAST:event_expertCheckBoxMenuItemActionPerformed

    private void masterCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterCheckBoxMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.driver.getGame().setDepth(UCIConst.DEPTH_MASTER);
            this.driver.getStatusIO().getUserSettings().setDepth(UCIConst.DEPTH_MASTER);
            clearAllDifficultySettingsCheckBoxs(masterCheckBoxMenuItem, masterPopupCheckBoxMenuItem);
            driver.getHelper().updatePlyDepthDisplay();
        }
    }//GEN-LAST:event_masterCheckBoxMenuItemActionPerformed

    private void clearOutputMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearOutputMenuItemActionPerformed
        if (!driver.isPerforming()) {
            // Clear text area output :
            this.console.getTextPaneOutput().setText(UIConst.STR_EMPTY);
        }
    }//GEN-LAST:event_clearOutputMenuItemActionPerformed

    private void controlButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_controlButtonMouseEntered
        if (!driver.isPerforming()) {
            javax.swing.JButton button = (javax.swing.JButton)evt.getComponent();
            button.setBackground(UIConst.CONTROL_HOVER_BACKGROUND);
            controlPanelRepaint();
        }
    }//GEN-LAST:event_controlButtonMouseEntered

    private void controlButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_controlButtonMouseExited
        if (!driver.isPerforming()) {
            javax.swing.JButton button = (javax.swing.JButton)evt.getComponent();
            button.setBackground(UIConst.CONTROL_MOUSE_EXITED_COLOR);
            controlPanelRepaint();
        }
    }//GEN-LAST:event_controlButtonMouseExited

    private void takeMoveBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_takeMoveBackButtonActionPerformed
        if (!driver.isPerforming()) {
            
            if (this.brainButton.isThinking()) {
                brainButtonStopThinking();
            }
            
            try {
                this.driver.getGame().executeMoveBack();
            } catch (FenConvertionException ex) {
                Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MoveIndexOutOfBoundsException ex) {
                Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }//GEN-LAST:event_takeMoveBackButtonActionPerformed

    private void moveForwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveForwardButtonActionPerformed
        if (!driver.isPerforming()) {
            
            if (this.brainButton.isThinking()) {
                brainButtonStopThinking();
            }
            
            try {
                this.driver.getGame().executeMoveForward();
            } catch (FenConvertionException ex) {
                Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MoveIndexOutOfBoundsException ex) {
                Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }//GEN-LAST:event_moveForwardButtonActionPerformed

    private void activateGuiEngineSearchCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activateGuiEngineSearchCheckBoxMenuItemActionPerformed
        
        if (!driver.isPerforming()) {
            
            // Invert checkbox state :
            activateGuiEngineSearchCheckBoxMenuItem.setSelected(!activateGuiEngineSearchCheckBoxMenuItem.isSelected());
            // Set status for on window on closing serialization.
            this.driver.getStatusIO().getUserSettings().setActivateInfiniteSearchAfterEngineMove(
                activateGuiEngineSearchCheckBoxMenuItem.isSelected());
            
            if (this.brainButton.isThinking()) {
                // Stop infinite engine search on game move string for gui side
                // and display results :
                // In any case fire clear button method :
                brainButtonStopThinking();
                driver.clearAllSquareBorders();
            }
        }
    }//GEN-LAST:event_activateGuiEngineSearchCheckBoxMenuItemActionPerformed

    private void brainButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brainButtonMouseEntered
        if (!driver.isPerforming() &&
                !this.brainButton.isThinking()) {
            javax.swing.JButton button = (javax.swing.JButton)evt.getComponent();
            button.setBackground(UIConst.CONTROL_HOVER_BACKGROUND);
            controlPanelRepaint();
        }
    }//GEN-LAST:event_brainButtonMouseEntered

    private void brainButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brainButtonMouseExited
        if (!driver.isPerforming() &&
                !this.brainButton.isThinking()) {
            javax.swing.JButton button = (javax.swing.JButton)evt.getComponent();
            button.setBackground(UIConst.CONTROL_MOUSE_EXITED_COLOR);
            controlPanelRepaint();
        }
    }//GEN-LAST:event_brainButtonMouseExited

    private void brainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brainButtonActionPerformed
        
        if (!driver.isPerforming()) {
            
            if (this.brainButton.isThinking()) {
                // Stop infinite engine search on game move string for gui side
                // and display results :
                // In any case fire clear button method :
                brainButtonStopThinking();
            }
        }
        
        // If Feature is not activated then activate it, then give notive modally. 
        if (!this.driver.getStatusIO().getUserSettings().isActivateInfiniteSearchAfterEngineMove()) {
            activateGUIEngineSupportButtonActionPerformed(null);
            JOptionPane.showMessageDialog(this,
                "Search support is now activated");
        }
    }//GEN-LAST:event_brainButtonActionPerformed

    private void activateGUIEngineSupportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activateGUIEngineSupportButtonActionPerformed
        
        if (!driver.isPerforming()) {
            // On event, if the status for this param is set to true then inverse booleans :
            // set associated checkbox in the menu to selected true/false, change the icon of the 
            // button and set status for serialization on window closing event.
            if (this.driver.getStatusIO().getUserSettings().isActivateInfiniteSearchAfterEngineMove()) {
                activateGUIEngineSupportButton.setIcon(
                    new javax.swing.ImageIcon(getClass().getResource(
                    UIConst.SEARCH_SUPPORT_DISACTIVATED_BUTTON_ICON)));
                this.driver.getStatusIO().getUserSettings().setActivateInfiniteSearchAfterEngineMove(false);
                this.activateGuiEngineSearchCheckBoxMenuItem.setSelected(false);
            } else {
                activateGUIEngineSupportButton.setIcon(
                    new javax.swing.ImageIcon(getClass().getResource(
                    UIConst.SEARCH_SUPPORT_ACTIVATED_BUTTON_ICON)));
                this.driver.getStatusIO().getUserSettings().setActivateInfiniteSearchAfterEngineMove(true);
                this.activateGuiEngineSearchCheckBoxMenuItem.setSelected(true);
            }

            if (this.brainButton.isThinking()) {
                brainButtonStopThinking();
            }
        }
    }//GEN-LAST:event_activateGUIEngineSupportButtonActionPerformed

    private void displayConsoleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayConsoleMenuItemActionPerformed
        
        this.console.setVisible(!this.console.isVisible());
        this.driver.getStatusIO().getUserSettings().setConsoleVisible(this.console.isVisible());
        
        // If console is being displayed then update it's location :
        if (this.console.isVisible()) {
            if (!this.isIconified() && !this.isMaximized()) {
                this.console.updateConsoleLocationOnStartUp(this.getX(), this.getY(), this.getHeight(), 
                    this.getWidth());
            } else {
                this.console.updateConsoleLocationDefault();
            }
        }
        this.focus();
    }//GEN-LAST:event_displayConsoleMenuItemActionPerformed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        if (this.driver.getStatusIO().getUserSettings().isConsoleVisible()) {
            this.console.updateDispaly(false);
        }
    }//GEN-LAST:event_formWindowIconified

    private void formWindowDeiconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeiconified
        if (this.driver.getStatusIO().getUserSettings().isConsoleVisible()) {
            this.console.updateDispaly(true);
            this.focus();
        }
    }//GEN-LAST:event_formWindowDeiconified

    private void boardDigitalOneMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardDigitalOneMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.getBoardContainer().setChessBoardBackgroundImage(
                this.driver.getHelper().createImageIcon(UIConst.BOARD_RES +
                UIConst.DIGITAL_LIGHT1));
            this.driver.getStatusIO().getUserSettings().setChessBoardStyle(UIConst.DIGITAL_LIGHT1);
        }
    }//GEN-LAST:event_boardDigitalOneMenuItemActionPerformed

    private void boardDigitalFourMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardDigitalFourMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.getBoardContainer().setChessBoardBackgroundImage(
                this.driver.getHelper().createImageIcon(UIConst.BOARD_RES +
                UIConst.DIGITAL_LIGHT4));
            this.driver.getStatusIO().getUserSettings().setChessBoardStyle(UIConst.DIGITAL_LIGHT4);
        }
    }//GEN-LAST:event_boardDigitalFourMenuItemActionPerformed

    private void boardDigitalTwoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardDigitalTwoMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.getBoardContainer().setChessBoardBackgroundImage(
                this.driver.getHelper().createImageIcon(UIConst.BOARD_RES +
                UIConst.DIGITAL_LIGHT2));
            this.driver.getStatusIO().getUserSettings().setChessBoardStyle(UIConst.DIGITAL_LIGHT2);
        }
    }//GEN-LAST:event_boardDigitalTwoMenuItemActionPerformed

    private void boardDigitalThreeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardDigitalThreeMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.getBoardContainer().setChessBoardBackgroundImage(
                this.driver.getHelper().createImageIcon(UIConst.BOARD_RES +
                UIConst.DIGITAL_LIGHT3));
            this.driver.getStatusIO().getUserSettings().setChessBoardStyle(UIConst.DIGITAL_LIGHT3);
        }
    }//GEN-LAST:event_boardDigitalThreeMenuItemActionPerformed

    private void boardEbonyIvoryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardEbonyIvoryMenuItemActionPerformed
        if (!driver.isPerforming()) {
            this.getBoardContainer().setChessBoardBackgroundImage(
                this.driver.getHelper().createImageIcon(UIConst.BOARD_RES +
                UIConst.EBONY_IVORY));
            this.driver.getStatusIO().getUserSettings().setChessBoardStyle(UIConst.EBONY_IVORY);
        }
    }//GEN-LAST:event_boardEbonyIvoryMenuItemActionPerformed

    private void saveGameDataOnCloseCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGameDataOnCloseCheckBoxMenuItemActionPerformed
        this.driver.getStatusIO().getUserSettings().setBackUpGameData(
            saveGameDataOnCloseCheckBoxMenuItem.isSelected());
    }//GEN-LAST:event_saveGameDataOnCloseCheckBoxMenuItemActionPerformed

    private void loadFromGameDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadFromGameDataMenuItemActionPerformed
        
        if (!driver.isPerforming()) {
            
            final String data[] = new GameDataLoaderDialog(this, 
                Dialog.ModalityType.APPLICATION_MODAL).showDialog();
            
            if (data[0] != null && data[1] != null) {
                
                if (!driver.isPerforming()) {
                    
                    Object[] options = MessageConst.OK_CANCEL_OPTIONS;
                        int result = JOptionPane.showOptionDialog(this,
                            String.format(MessageConst.LOAD_DATA_FILES_TEXT, data[1]),
                                MessageConst.LOAD_DATA_FILES_TITLE,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);

                    if (result == 0) {

                        try {
                            // Build DTO class. Then generate game.
                            final ChessGameDTO dto = DataUtils.getGameData(data[0]);

                            clearChessSquares();
                            brainButton.stopThink();
                            BoardSnapshot.deleteSnapshots(new File(BoardSnapshot.getSNAPSHOT_PATH()));
                            driver.getStatusIO().getGameStatus().setGameMoves(dto.getMoves());
                            driver.getStatusIO().getGameStatus().setFenMoves(dto.getFenMoves());
                            driver.getStatusIO().getGameStatus().setSeconds(dto.getSeconds());
                            driver.getStatusIO().getGameStatus().setMoveCount(dto.getMoveCount());
                            driver.getStatusIO().getUserSettings().setWhite(dto.getColor().equals(BoardConst.WHITE));
                            driver.getStatusIO().getUserSettings().setColor(dto.getColor());
                            driver.getStatusIO().serializeStatus();
                            driver.restart(true, true, false, GameTypeConst.CHESS_GAME);
                            driver.loadGame(true);

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainUi.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(this,
                                MessageConst.LOAD_DATA_FILES_ERR_TEXT,
                                MessageConst.LOAD_DATA_FILES_TITLE,
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_loadFromGameDataMenuItemActionPerformed

    private void displayCoordinatesCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayCoordinatesCheckBoxMenuItemActionPerformed
        
        if (!driver.isPerforming()) {
            this.driver.getStatusIO().getUserSettings().setBoardCoordinatesVisible(
                this.displayCoordinatesCheckBoxMenuItem.isSelected());
            this.driver.getHelper().resetChessSquareLabelling(
                this.displayCoordinatesCheckBoxMenuItem.isSelected());
            this.driver.getHelper().repaintAllChessSquares();
        }
    }//GEN-LAST:event_displayCoordinatesCheckBoxMenuItemActionPerformed
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Variables declaration - do not modify">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AboutMenu;
    private javax.swing.JMenuItem aboutJellyFishMenuItem;
    private javax.swing.JButton activateGUIEngineSupportButton;
    private javax.swing.JCheckBoxMenuItem activateGuiEngineSearchCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem alwaysLoadPreviousGameCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem appendAllOutputCheckBoxMenuItem;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bA1;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bA2;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bA3;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bA4;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bA5;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bA6;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bA7;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bA8;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bB1;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bB2;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bB3;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bB4;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bB5;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bB6;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bB7;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bB8;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bC1;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bC2;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bC3;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bC4;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bC5;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bC6;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bC7;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bC8;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bD1;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bD2;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bD3;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bD4;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bD5;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bD6;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bD7;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bD8;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bE1;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bE2;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bE3;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bE4;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bE5;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bE6;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bE7;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bE8;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bF1;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bF2;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bF3;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bF4;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bF5;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bF6;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bF7;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bF8;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bG1;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bG2;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bG3;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bG4;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bG5;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bG6;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bG7;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bG8;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bH1;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bH2;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bH3;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bH4;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bH5;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bH6;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bH7;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare bH8;
    private javax.swing.JCheckBoxMenuItem bishopPromoCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem bishopPromoPopupCheckBoxMenuItem;
    private javax.swing.JPanel boardContainer;
    private javax.swing.JMenuItem boardDigitalFourMenuItem;
    private javax.swing.JMenuItem boardDigitalOneMenuItem;
    private javax.swing.JMenuItem boardDigitalThreeMenuItem;
    private javax.swing.JMenuItem boardDigitalTwoMenuItem;
    private javax.swing.JMenuItem boardEbonyIvoryMenuItem;
    private javax.swing.JMenu boardIconMenu;
    private fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.BrainButton brainButton;
    private javax.swing.JMenu changePlyDepthPopupMenu;
    private javax.swing.JMenu chessmenIconMenu;
    private javax.swing.JMenuItem classicOneChessmenMenuItem;
    private javax.swing.JMenuItem classicTwoChessmenMenuItem;
    private javax.swing.JMenuItem clearOutputMenuItem;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton deeperPlyDepthButton;
    private javax.swing.JMenu difficultyMenu;
    private javax.swing.JMenu difficultySettingsMenu;
    private javax.swing.JButton displayConsoleButton;
    private javax.swing.JMenuItem displayConsoleMenuItem;
    private javax.swing.JMenuItem displayConsolePopupMenuItem;
    private javax.swing.JCheckBoxMenuItem displayCoordinatesCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem dummyCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem dummyPopupCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem easyCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem easyPopupCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem expertCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem expertPopupCheckBoxMenuItem;
    private javax.swing.JButton forceEngineToTakeMoveButton;
    private javax.swing.JMenuItem forceMoveMenuItem;
    private javax.swing.JMenuItem forceMovePopupMenuItem;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JPanel globalContainer;
    private javax.swing.JPopupMenu globalPopupMenu;
    private javax.swing.JMenuItem goDeeperPopupMenuItem;
    private javax.swing.JMenuItem goShallowerPopupMenuItem;
    private javax.swing.JCheckBoxMenuItem hardCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem hardPopupCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem harderCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem harderPopupCheckBoxMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JCheckBoxMenuItem knightPromoCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem knightPromoPopupCheckBoxMenuItem;
    private javax.swing.JMenuItem loadFromGameDataMenuItem;
    private javax.swing.JCheckBoxMenuItem masterCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem masterPopupCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem mediumCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem mediumPopupCheckBoxMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JButton moveForwardButton;
    private javax.swing.JButton newGameBlackButton;
    private javax.swing.JMenuItem newGameBlacksMenuItem;
    private javax.swing.JMenuItem newGameBlacksPopupMenuItem;
    private javax.swing.JButton newGameWhitesButton;
    private javax.swing.JMenuItem newGameWhitesMenuItem;
    private javax.swing.JMenuItem newGameWhitesPopupMenuItem;
    private javax.swing.JMenu pawnPromoPopupMenu;
    private javax.swing.JMenu pawnPromotionjMenu;
    private javax.swing.JMenu plyDepthjMenu;
    private javax.swing.JMenuItem plyShallowerMenuItem;
    private javax.swing.JMenuItem plydeeperMenuItem;
    private javax.swing.JCheckBoxMenuItem queenPromoCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem queenPromoPopupCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem rookPromoCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem rookPromoPopupCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem saveGameDataOnCloseCheckBoxMenuItem;
    private javax.swing.JButton shallowerPlyDepthButton;
    private javax.swing.JButton takeMoveBackButton;
    private javax.swing.JMenuItem twoDOneChessmenMenuItem;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Stop Brain button and associated tasks.
     */
    public void brainButtonStopThinking() {
        // Stop engine search.
        driver.clearAllSquareBorders();
        brainButton.stopThink(this.driver.isCurrentlyReloadingPreviousGame());
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Private & package private methods">
    /**
     * Clear all ChessSquares for a new Game.
     */
    void clearChessSquares() {
        ChessSquare square;
        for (Component component : this.getBoardContainer().getComponents()) {
            square = (ChessSquare)component;
            square.setIcon(null);
        }
    }
    
    /**
     * Focus on this JFrame.
     */
    void focus() {
        // Focus on main frame :
        this.toFront();
        this.setState(Frame.NORMAL);
    }
    
    /**
     * Set appropriate pawn promotion check box.
     * @param checkBoxMenuItem 
     */
    private void clearAllPromotionCheckBoxs(final char promotionType) {
        
        // Main menu :
        queenPromoCheckBoxMenuItem.setSelected(false);
        bishopPromoCheckBoxMenuItem.setSelected(false);
        knightPromoCheckBoxMenuItem.setSelected(false);
        rookPromoCheckBoxMenuItem.setSelected(false);
        // Popup menu :
        queenPromoPopupCheckBoxMenuItem.setSelected(false);
        bishopPromoPopupCheckBoxMenuItem.setSelected(false);
        knightPromoPopupCheckBoxMenuItem.setSelected(false);
        rookPromoPopupCheckBoxMenuItem.setSelected(false);
        
        switch (promotionType) {
            case UCIConst.QUEEN_PROMOTION:
                queenPromoCheckBoxMenuItem.setSelected(true);
                queenPromoPopupCheckBoxMenuItem.setSelected(true);
                break;
            case UCIConst.BISHOP_PROMOTION:
                bishopPromoCheckBoxMenuItem.setSelected(true);
                bishopPromoPopupCheckBoxMenuItem.setSelected(true);
                break;
            case UCIConst.KNIGHT_PROMOTION:
                knightPromoCheckBoxMenuItem.setSelected(true);
                knightPromoPopupCheckBoxMenuItem.setSelected(true);
                break;
            case UCIConst.ROOK_PROMOTION:
                rookPromoCheckBoxMenuItem.setSelected(true);
                rookPromoPopupCheckBoxMenuItem.setSelected(true);
                break;
            default:
                break;
        }
        
    }
    
    /**
     * Clear all difficulty settings checkboxs.
     */
    private void clearAllDifficultySettingsCheckBoxs(javax.swing.JCheckBoxMenuItem ... checkBoxs) {
        
        dummyCheckBoxMenuItem.setSelected(false);
        easyCheckBoxMenuItem.setSelected(false);
        mediumCheckBoxMenuItem.setSelected(false);
        hardCheckBoxMenuItem.setSelected(false);
        harderCheckBoxMenuItem.setSelected(false);
        expertCheckBoxMenuItem.setSelected(false);
        masterCheckBoxMenuItem.setSelected(false);
        
        dummyPopupCheckBoxMenuItem.setSelected(false);
        easyPopupCheckBoxMenuItem.setSelected(false);
        mediumPopupCheckBoxMenuItem.setSelected(false);
        hardPopupCheckBoxMenuItem.setSelected(false);
        harderPopupCheckBoxMenuItem.setSelected(false);
        expertPopupCheckBoxMenuItem.setSelected(false);
        masterPopupCheckBoxMenuItem.setSelected(false);
        
        for (javax.swing.JCheckBoxMenuItem checkBox : checkBoxs) {
            checkBox.setSelected(true);
        }
    }
    
    /**
     * Set all pawns promotion type as selected.
     * @param QUEEN_PROMOTION 
     */
    private void setPawnPromotionType(final char promotionType) {
        for (Position pos : Board.getInstance().getCoordinates().values()) {
            
            // If color = GUI color and position's chessman is pawn then set 
            // selected pawn promotion type.
            if (pos.getOnPositionChessMan() instanceof Pawn &&
                !pos.getOnPositionChessMan().getCOLOR().equals(driver.getEngineColor())) {
                ((Pawn)pos.getOnPositionChessMan()
                    ).setPromotionType(promotionType);
            }
        }
    }
    
    /**
     * Adjust difficulty settings checkboxs.
     * @param depth 
     */
    private void setDifficultySettingsCheckBoxs(final int depth) {

        switch (depth) {
            case UCIConst.DEPTH_DUMMY :
                clearAllDifficultySettingsCheckBoxs(dummyCheckBoxMenuItem, dummyPopupCheckBoxMenuItem);
                break;
            case UCIConst.DEPTH_EASY :
                clearAllDifficultySettingsCheckBoxs(easyCheckBoxMenuItem, easyPopupCheckBoxMenuItem);
                break;
            case UCIConst.DEPTH_MEDIUM :
                clearAllDifficultySettingsCheckBoxs(mediumPopupCheckBoxMenuItem, mediumCheckBoxMenuItem);
                break;
            case UCIConst.DEPTH_HARD :
                clearAllDifficultySettingsCheckBoxs(hardPopupCheckBoxMenuItem, hardCheckBoxMenuItem);
                break;
            case UCIConst.DEPTH_HARDER :
                clearAllDifficultySettingsCheckBoxs(harderCheckBoxMenuItem, harderPopupCheckBoxMenuItem);
                break;
            case UCIConst.DEPTH_EXPERT :
                clearAllDifficultySettingsCheckBoxs(expertCheckBoxMenuItem, expertPopupCheckBoxMenuItem);
                break;
            case UCIConst.DEPTH_MASTER :
                clearAllDifficultySettingsCheckBoxs(masterPopupCheckBoxMenuItem, masterCheckBoxMenuItem);
                break;
            default :
                break;
        }
    }
    
    /**
     * Repaint control panel and its components.
     */
    private void controlPanelRepaint() {
        this.controlPanel.repaint();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public JCheckBoxMenuItem getDisplayCoordinatesCheckBoxMenuItem() {
        return displayCoordinatesCheckBoxMenuItem;
    }
    
    public boolean isMainUiReady() {
        return mainUiReady;
    }
    
    public MainUiDriver getDriver() {
        return driver;
    }
    
    public boolean isIconified() {
        return iconified;
    }

    public void setIconified(boolean iconified) {
        this.iconified = iconified;
    }

    public boolean isMaximized() {
        return maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }
    
    public Console getConsole() {
        return console;
    }
        
    public BrainButton getBrainButton() {
        return brainButton;
    }
    
    public JPanel getControlPanel() {
        return controlPanel;
    }
    
    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbA1() {
        return bA1;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbA2() {
        return bA2;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbA3() {
        return bA3;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbA4() {
        return bA4;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbA5() {
        return bA5;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbA6() {
        return bA6;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbA7() {
        return bA7;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbA8() {
        return bA8;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbB1() {
        return bB1;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbB2() {
        return bB2;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbB3() {
        return bB3;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbB4() {
        return bB4;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbB5() {
        return bB5;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbB6() {
        return bB6;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbB7() {
        return bB7;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbB8() {
        return bB8;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbC1() {
        return bC1;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbC2() {
        return bC2;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbC3() {
        return bC3;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbC4() {
        return bC4;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbC5() {
        return bC5;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbC6() {
        return bC6;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbC7() {
        return bC7;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbC8() {
        return bC8;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbD1() {
        return bD1;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbD2() {
        return bD2;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbD3() {
        return bD3;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbD4() {
        return bD4;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbD5() {
        return bD5;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbD6() {
        return bD6;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbD7() {
        return bD7;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbD8() {
        return bD8;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbE1() {
        return bE1;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbE2() {
        return bE2;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbE3() {
        return bE3;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbE4() {
        return bE4;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbE5() {
        return bE5;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbE6() {
        return bE6;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbE7() {
        return bE7;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbE8() {
        return bE8;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbF1() {
        return bF1;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbF2() {
        return bF2;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbF3() {
        return bF3;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbF4() {
        return bF4;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbF5() {
        return bF5;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbF6() {
        return bF6;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbF7() {
        return bF7;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbF8() {
        return bF8;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbG1() {
        return bG1;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbG2() {
        return bG2;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbG3() {
        return bG3;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbG4() {
        return bG4;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbG5() {
        return bG5;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbG6() {
        return bG6;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbG7() {
        return bG7;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbG8() {
        return bG8;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbH1() {
        return bH1;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbH2() {
        return bH2;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbH3() {
        return bH3;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbH4() {
        return bH4;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbH5() {
        return bH5;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbH6() {
        return bH6;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbH7() {
        return bH7;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare getbH8() {
        return bH8;
    }

    public fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessBoard getBoardContainer() {
        return (fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessBoard) boardContainer;
    }
    
    public JPanel getGlobalContainer() {
        return globalContainer;
    }

    public void setUiDriver(MainUiDriver driver) {
        this.driver = driver;
    }
    // </editor-fold>
    
}
