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

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.components.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.MessageConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.uistatus.StatusIO;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.utils.ImageIconPool;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.utils.SoundPlayer;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.ChessMenCollection;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Pawn;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.chessmen.Rook;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.ChessGameBuildException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidInfiniteSearchResult;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.ChessGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.UiObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.timer.GameTimer;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIMessage;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIProtocolDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.externalengine.IOExternalEngine;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.ChessGameBuilderUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;



/**
 * @author Thomas.H Warner 2014
 */
public class MainUiDriver extends AbstractChessGameDriver {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars"> 
    /**
     * Sound player for the game's sounds.
     */
    private final SoundPlayer soundPlayer;
    
    /**
     * ui instance.
     */
    private final MainUi ui;
    
    /**
     * Gui events class instance.
     */
    private final MainUiEvents events;
    
    /**
     * Map components with chess position value : B5, A1 ect.
     */
    private LinkedHashMap<String, ChessSquare> squareHashMap;
    
    /**
     * Game writter instance.
     */
    private final UiDisplayWriterHelper writer;

    /**
     * Previously selected square.
     */
    private ChessSquare lastSelectedChessSquare = null;

    /**
     * Entry point for chess engine.
     */
    private ChessGame game;
    
    /**
     * Serializer instance.
     */
    private final StatusIO statusIO;
    
    /**
     * Ui observers.
     */
    private UiObserver uiObserver;
    
    /**
     * If the process of reloading a game is under go or not.
     */
    private boolean currentlyReloadingPreviousGame = false;
    
    /**
     * Driver's helper utils class.
     */
    private final MainUiDriverHelper helper;
    
    /**
     * Driver's initializer.
     */
    private MainUiDriverInitializer initializer;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Constructor.
     * @param ui
     * @param statusIO 
     * @param loadingPreviousGame 
     * @param gameType 
     */
    public MainUiDriver(final MainUi ui, final StatusIO statusIO, 
            final boolean loadingPreviousGame, final String gameType) {
        
        this.soundPlayer = new SoundPlayer();
        this.statusIO = statusIO;
        this.ui = ui;
        this.writer = new UiDisplayWriterHelper(ui.getConsole().getTextPaneOutput(), ui.getConsole());
        this.writer.appendText(UIConst.JELLYFISH_V, 4, true);
        this.setUiPlayingWhites(this.statusIO.getUserSettings().isWhite());
        this.events = new MainUiEvents(this);
        this.setFenLastSelectedChessMan(UIConst.STR_EMPTY);   //fenLastSelectedChessMan = UIConst.STR_EMPTY;
        this.setGameType(gameType);
        this.helper = new MainUiDriverHelper(this);
        this.ui.getGlobalContainer().setBackground(statusIO.getUserSettings().getGloabalContainerBackgroundColor());
        initDriver(false, loadingPreviousGame);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Init">
    /**
     * Initialize Drier. If restart do not initialize GUI component events again.
     * Also do not initialize the boards background.
     * @param restart
     * @param loadingPreviousGame
     */
    private void initDriver(final boolean restart, final boolean loadingPreviousGame) {
        
        // Set engine and gui colors (black or white) :
        this.setEngineColor(this.isUiPlayingWhites() ? BoardConst.BLACK : BoardConst.WHITE);
        this.setEngineOponentColor(this.isUiPlayingWhites() ? BoardConst.WHITE : BoardConst.BLACK);
        
        // Reset the icon pool :
        ImageIconPool.reset();
        
        UCIProtocolDriver.getInstance().getIoExternalEngine().clearObservers();
        UCIProtocolDriver.getInstance().getIoExternalEngine().addExternalEngineObserver(this);
        
        this.initializer = new MainUiDriverInitializer(this);
        this.initializer.init(this.isUiPlayingWhites());
        
        if (!restart) {
            // Only init with these methods if driver is launched for first time
            // and not restarted as when GUI starts a new game.
            this.events.initGUIComponentEvents();
        }
        

        try {
            // In the case of restart = true, new ChessGame will execute a white
            // move if GUI has chosen black : Driver process must be started before
            // new ChessGame instance is created to be able to perform whites first
            // game move.
            // Here new game connot be a BlitzChessGameInstance.
            final int seconds = this.statusIO.getUserSettings().isLoadPreviousGame() ?
                this.statusIO.getGameStatus().getSeconds() : 0;
            this.game = ChessGameBuilderUtils.buildGame(this, this.getGameType(), 
                this.getEngineColor().toCharArray()[0],
                this.getEngineOponentColor().toCharArray()[0],
                this.statusIO.getUserSettings().getDepth(), 
                loadingPreviousGame, seconds);

            // Add ChessGames instance to ui observers :
            this.uiObserver = this.game;
        } catch (ChessGameBuildException ex) {
            // TODO : notify and deal with crash...
            Logger.getLogger(MainUiDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Initialize observers.
        initDriverObservation();
        
        // Finish init : clear all chess borders for safety, repaint all
        // sqaures.
        clearAllSquareBorders();
        // Set this to StatusIO instance :
        this.statusIO.setDriver(this);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Methods">   
    //<editor-fold defaultstate="collapsed" desc="Public methods">   
    /**
     * Restart driver and build a new game :
     * This method is called by GUI when a new game is started and it's content 
     * is analog to the constructor method except for details like call to
     * initDriver(bool color, bool restart = true). initDriver(...) method reacts
     * in function of this param.
     * @param restart
     * @param playStartSound
     * @param reloadingPreviousGame
     * @param gameType
     * @return 
     */
    public boolean restart(final boolean restart, final boolean reloadingPreviousGame, 
            final boolean playStartSound, final String gameType) {
        
        this.setGameType(gameType);
        
        // If game is a "restart" (a new game started from GUI using "new Game
        // menu item") then Engine must also be restarted using appropriate 
        // commands.
        IOExternalEngine.getInstance().writeToEngine(UCIConst.ENGINE_QUIT, MessageTypeConst.NOT_SO_TRIVIAL);
        IOExternalEngine.getInstance().init();
        
        // Re-initialize singleton classes ChessBoard & ChessMenCollection.
        Board.getInstance().init();
        ChessMenCollection.getInstance().init();

        this.setUiPlayingWhites(this.statusIO.getUserSettings().isWhite());
        ImageIconPool.reset();
        
        // Set engine and gui colors (black or white) :
        this.setEngineColor(this.isUiPlayingWhites() ? BoardConst.BLACK : BoardConst.WHITE);
        this.setEngineOponentColor(this.isUiPlayingWhites() ? BoardConst.WHITE : BoardConst.BLACK);
        
        // Param 1 is bool restart = true, param 2 = loading previous game = false.
        initDriver(restart, reloadingPreviousGame);
        
        if (playStartSound) {
            this.soundPlayer.playSound(SoundPlayer.RESTART);
        }
        
        // If all has gone well :
        return true;
    }
       
    /**
     * Load previous game that was serialized just before Form closing event.
     * @param reloadingFromData
     */
    public void loadGame(final boolean reloadingFromData) {
        
        // If user has not set to Off this feature, then proceed with game 
        // deserialization.
        if (this.statusIO.getUserSettings().isLoadPreviousGame() || reloadingFromData) {

            // Here inany case, prenvent user interaction :
            setCurrentlyReloadingPreviousGame(true);
            ui.getBoardContainer().setVisible(false);

            this.writer.appendText(String.format(UIConst.DISPLAY_LOADING_PREVIOUS_GAME, 
                    this.statusIO.getGameStatus().getGameMoves().size()) + CommonConst.BACKSLASH_N, 
                    MessageTypeConst.INPUT_2, true);

            // If there is something to load, then build moves.
            if (this.statusIO.getGameStatus().getGameMoves().size() > 0) {
                for (String move : this.statusIO.getGameStatus().getGameMoves().values()) {

                    // Here the best idea was to simply use the existing engineMoved(...)
                    // method implemented in GUIDriver class via ExternalEngineObserver
                    // interface. Basicly we want to simulate engine responses for all previous 
                    // moves saved in chessgui.guistatus.GameStatus class.
                    // We need to go through executeMove(...) in ChessGame class to reload
                    // the game move collection but above all to reset JellyFish in it's 
                    // game supervising :
                    engineMoved(new UCIMessage(UIConst.DISPLAY_LOADING_PREVIOUS_GAME, move));
                }
            }
        }
        
        // Update UI with user settings.
        this.helper.updateUiSettings();
        
        // In any case, enable user interaction :
        setCurrentlyReloadingPreviousGame(false);
        ui.getBoardContainer().setVisible(true);
        clearAllSquareBorders();
    }
    
    /**
    * Clear all colored borders.
    */
    @Override
    public void clearAllSquareBorders() {
        
        for (ChessSquare square : squareHashMap.values()) {
            if (square.isEffectDisplayed()) {
                square.removeEventEffects();
            }
        }
        // Reset border colors & selected assets.
        /*for (ChessSquare square : squareHashMap.values()) {
            if (square.getBorder() != null) {
                square.setBorder(null);
            }
        }*/
    }
        
    /**
     * Rebuild previous GUI move display (back 1 gui move & 1 engine).
     * @param positions 
     * @param fen 
     * @param moveCount 
     * @param plyDepth 
     */
    @Override
    public void applyMoveBack(final Map<String, Character> positions, final String fen,
            final int moveCount, final int plyDepth) {
        
        ImageIconPool.reset();
        
        ImageIcon icon;
        String ref = UIConst.STR_EMPTY;
        String color = UIConst.STR_EMPTY;
        final String style = statusIO.getUserSettings().getChessmenStyle();
        
        for (Map.Entry<String, Character> entry : positions.entrySet()) {
            
            ref = UIConst.FEN_TO_CHESSMAN_REF.get(entry.getValue());
            
            if (ref == null) {
                squareHashMap.get(entry.getKey()).setIcon(null);
            } else {
                icon =  this.helper.createImageIcon(UIConst.CHESSMEN + style + 
                    UIConst.SLASH + ref + UIConst.PNG_EXT);
                icon.setDescription(ref);
                squareHashMap.get(entry.getKey()).setIcon(icon);
                color = Character.isLowerCase(entry.getValue()) ? BoardConst.BLACK : BoardConst.WHITE;
                ImageIconPool.getPool().put(icon, color);
            }
        }
        
        // Play move sound :
        soundPlayer.playSound(SoundPlayer.MOVE);
        this.helper.repaintAllChessSquares();
        clearAllSquareBorders();
    }
    
    /**
     * Is driver performing a task ?
     * @return boolean
     */
    @Override
    public boolean isPerforming() {
        return this.isCurrentlyReloadingPreviousGame() || this.isEngineSearching();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Package private methods">  
    /**
     * Notify all observers that UI has moved.
     */
    void notifyMoveToUiObserver() {
        this.uiObserver.uiMoved();  
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Private methods">
    /**
     * Add this to all necessary observer patterns.
     */
    private void initDriverObservation() {
        
        // Add this class to Rook class's CastlingObservers.
        Rook rookA1 = (Rook)Board.getInstance().getCoordinates().get(
                BoardConst.A1).getOnPositionChessMan();
        rookA1.addCastlingObserver(this);
        Rook rookA8 = (Rook)Board.getInstance().getCoordinates().get(
                BoardConst.A8).getOnPositionChessMan();
        rookA8.addCastlingObserver(this);
        Rook rookH1 = (Rook)Board.getInstance().getCoordinates().get(
                BoardConst.H1).getOnPositionChessMan();
        rookH1.addCastlingObserver(this);
        Rook rookH8 = (Rook)Board.getInstance().getCoordinates().get(
                BoardConst.H8).getOnPositionChessMan();
        rookH8.addCastlingObserver(this);
        
        // Add this as observer on all Pawn classes for "En passant" moving.
        // Black Pawns :
        Pawn pawnH7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.H7).getOnPositionChessMan();
        pawnH7.addPawnEnPassantObserver(this);
        Pawn pawnG7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.G7).getOnPositionChessMan();
        pawnG7.addPawnEnPassantObserver(this);
        Pawn pawnF7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.F7).getOnPositionChessMan();
        pawnF7.addPawnEnPassantObserver(this);
        Pawn pawnE7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.E7).getOnPositionChessMan();
        pawnE7.addPawnEnPassantObserver(this);
        Pawn pawnD7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.D7).getOnPositionChessMan();
        pawnD7.addPawnEnPassantObserver(this);
        Pawn pawnC7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.C7).getOnPositionChessMan();
        pawnC7.addPawnEnPassantObserver(this);
        Pawn pawnB7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.B7).getOnPositionChessMan();
        pawnB7.addPawnEnPassantObserver(this);
        Pawn pawnA7 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.A7).getOnPositionChessMan();
        pawnA7.addPawnEnPassantObserver(this);
        // White Pawns :
        Pawn pawnA2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.A2).getOnPositionChessMan();
        pawnA2.addPawnEnPassantObserver(this);
        Pawn pawnB2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.B2).getOnPositionChessMan();
        pawnB2.addPawnEnPassantObserver(this);
        Pawn pawnC2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.C2).getOnPositionChessMan();
        pawnC2.addPawnEnPassantObserver(this);
        Pawn pawnD2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.D2).getOnPositionChessMan();
        pawnD2.addPawnEnPassantObserver(this);
        Pawn pawnE2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.E2).getOnPositionChessMan();
        pawnE2.addPawnEnPassantObserver(this);
        Pawn pawnF2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.F2).getOnPositionChessMan();
        pawnF2.addPawnEnPassantObserver(this);
        Pawn pawnG2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.G2).getOnPositionChessMan();
        pawnG2.addPawnEnPassantObserver(this);
        Pawn pawnH2 = (Pawn)Board.getInstance().getCoordinates().get(
                BoardConst.H2).getOnPositionChessMan();
        pawnH2.addPawnEnPassantObserver(this);

        // Add this as check observer to all chessmen :
        for (String position : BoardConst.boardPositions) {
            Board.getInstance().getCoordinates().get(position).getOnPositionChessMan(
                ).setCheckObserver(this);
        }
    }
    //</editor-fold>
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Overriden Interface methods">   
    @Override
    public void engineResponse() { }
    
    @Override
    public void engineResponse(final String response, final int msgLevel) {
        // Append simple text message to text display area.
        writer.appendText(response, msgLevel, true);
    }
    
    @Override
    public void engineInfiniteSearchResponse(final UCIMessage uciMessage) throws InvalidInfiniteSearchResult {
        
        // Append simple text message to text display area.
        writer.appendText(uciMessage.getMessage(), uciMessage.getMessageLvl(), true);

        if (uciMessage.getBestMove().length() == 4 || uciMessage.getBestMove().length() == 5) {

            final String posFrom = (String.valueOf(uciMessage.getBestMove().toCharArray()[0]) +
                    String.valueOf(uciMessage.getBestMove().toCharArray()[1]));
            final String posTo = (String.valueOf(uciMessage.getBestMove().toCharArray()[2]) +
                    String.valueOf(uciMessage.getBestMove().toCharArray()[3]));

            squareHashMap.get(posFrom).addEventEffect(UIConst.HINT_COLOR, UIConst.BORDER_WIDTH);
            squareHashMap.get(posTo).addEventEffect(UIConst.HINT_COLOR, UIConst.BORDER_WIDTH);
            
            if (uciMessage.getBestMove().length() == 5) {
                // TODO
            } else {

            }

        } else {
            // Engine response on infinite search is an illegal move.
            throw new InvalidInfiniteSearchResult(uciMessage + MessageConst.IS_NOT_VALID_MOVE); 
        }
        
    }

    @Override
    public void engineMoved(final UCIMessage uciMessage) {
        
        if (!this.currentlyReloadingPreviousGame) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(IOExternalEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Apply move to GUI.
        // Check legth of message : if == 4 then split in 2 halfs.
        // == 5 is a pawn promotion.
        char promotion = CommonConst.DUMMY;
        char promotionColor = CommonConst.DUMMY;
        boolean pawnPromotion = false;
        boolean moveIsEffective = false;
        final String posFrom;
        final String posTo;

        if (uciMessage.getBestMove().length() == 4 || uciMessage.getBestMove().length() == 5) {

            posFrom = (String.valueOf(uciMessage.getBestMove().toCharArray()[0]) +
                    String.valueOf(uciMessage.getBestMove().toCharArray()[1]));
            posTo = (String.valueOf(uciMessage.getBestMove().toCharArray()[2]) +
                    String.valueOf(uciMessage.getBestMove().toCharArray()[3]));

            if (uciMessage.getBestMove().length() == 5) { // Pawn promotion.

                pawnPromotion = true;
                // Get promotion type. Ex : a7a8q 'q' meaning Queen.
                promotion = uciMessage.getBestMove().toCharArray()[4];
                // Also determinate promotion color.
                if (this.getEngineColor().equals(BoardConst.BLACK)) {
                    promotionColor = UCIConst.BLACK_CHAR_LOWER;
                } else if (this.getEngineColor().equals(BoardConst.WHITE)) {
                    promotionColor = UCIConst.WHITE_CHAR_LOWER;
                }
            } else { // Reset local vars.
                promotion = UCIConst.DUMMY;
                promotionColor = UCIConst.DUMMY;
                pawnPromotion = false;
            }

            try {
                // Execute engines move via ChessGame class executeMove(...).
                // Even if engine's move is supposed to be a correct/valid Chess
                // move, check it anyway. This will also keep track of the game
                // moves and details.
                if (game.executeMove(posFrom, posTo, false, pawnPromotion, promotion)) {

                    if (pawnPromotion) { // Pawn promotion.                

                        ImageIcon promotedIcon = this.helper.createImageIcon(UIConst.CHESSMEN +
                                statusIO.getUserSettings().getChessmenStyle() +
                                UIConst.SLASH + (this.getEngineColor().toLowerCase()).toCharArray()[0] +
                                String.valueOf(promotion) + UIConst.PNG_EXT);
                        ImageIconPool.getPool().put(promotedIcon, this.getEngineColor());
                        this.helper.applyGUIPawnPromotion(posFrom, posTo, 
                                this.helper.createImageIcon(UIConst.CHESSMEN +
                                statusIO.getUserSettings().getChessmenStyle() +
                                UIConst.SLASH + String.valueOf(promotionColor) +
                                String.valueOf(promotion) + UIConst.PNG_EXT));
                        
                        moveIsEffective = true;
                    } else {
                        // Move icon.
                        this.helper.moveChessSquareIcon(squareHashMap.get(posFrom).getIcon(),
                                squareHashMap.get(posFrom), squareHashMap.get(posTo));
                        moveIsEffective = true;
                    }

                    // Free GUI so that it can move again.
                    this.setEngineSearching(false);
                    
                    // If GUI has set true to activate infinite search after engine move,
                    // launch process, until brain button is pressed.
                    if (statusIO.getUserSettings().isActivateInfiniteSearchAfterEngineMove() &&
                            this.getEngineOponentColor().equals(this.game.getColorToPLay()) &&
                            !this.currentlyReloadingPreviousGame) {
                        // Launch infinite engine search on game positions for gui side support :
                        // Activate brain button display :
                        this.getUi().getBrainButton().startThink(this.currentlyReloadingPreviousGame);
                    }
                    
                    // Finally, if move is vaidated :
                    if (moveIsEffective) {
                        // Apply square coloration for engine last move user
                        // notification &/OR game lecture.
                        this.squareHashMap.get(posFrom
                            ).addEventEffect(UIConst.ENGINE_PREVIOUS_MOVE, UIConst.BORDER_WIDTH);
                        this.squareHashMap.get(posTo
                            ).addEventEffect(UIConst.ENGINE_PREVIOUS_MOVE, UIConst.BORDER_WIDTH);
                    }

                } else {
                    // Engine has attempted an illegal move.
                    throw new InvalidMoveException(uciMessage.getBestMove() +
                            MessageConst.IS_NOT_VALID_MOVE);
                }

            } catch (InvalidMoveException | PawnPromotionException ex) {
                Logger.getLogger(MainUiDriver.class.getName()).log(Level.WARNING, null, ex);
                if (ex instanceof InvalidMoveException) {
                    writer.appendText(uciMessage.getBestMove() +
                        MessageConst.IS_NOT_VALID_MOVE, MessageTypeConst.ERROR, true);
                }
            }
        }
               
    }
    
    @Override
    public void applyCastling(final String posFrom, final String posTo) {
        
        // Chess engine throws back a castling exception to enable GUI
        // to update visual components with icons.
        // Update rook :
        this.helper.moveChessSquareIcon(squareHashMap.get(posFrom).getIcon(),
                    squareHashMap.get(posFrom), squareHashMap.get(posTo));
        // Repaint all moved squares.
        this.helper.repaintChessSquares(squareHashMap.get(posFrom), squareHashMap.get(posTo),
                squareHashMap.get(posFrom), squareHashMap.get(posTo));
    
    }
    
    @Override
    public void applyPawnEnPassant(final String virtualPawnPosition) {
       squareHashMap.get(virtualPawnPosition).setIcon(null);
       squareHashMap.get(virtualPawnPosition).removeEventEffects();
       this.helper.repaintChessSquares(squareHashMap.get(virtualPawnPosition));
    }
    
    @Override
    public void applyCheckSituation(final Position king, final boolean inCheck) {
        
        if (!this.currentlyReloadingPreviousGame && !this.game.isEngineForcedToPlayedMove() &&
                king.getOnPositionChessMan().getChessManKing().getCOLOR().equals(this.getEngineOponentColor())) {
            
            if (inCheck) {
                writer.appendText(king.getOnPositionChessMan().getCOLOR() + " king @" + 
                    king.getOnPositionChessMan().getBoardPosition().getCoordinates() + " is in check." + 
                    CommonConst.BACKSLASH_N, MessageTypeConst.CHECK, !this.currentlyReloadingPreviousGame);       

                squareHashMap.get(king.toString().toLowerCase()
                    ).addEventEffect(UIConst.CHECK_COLOR, UIConst.BORDER_WIDTH);
            } else {
                boolean kingMove = this.getFenLastSelectedChessMan().equals(String.valueOf(king.getOnPositionChessMan().getFenValue())); 
                if (kingMove) {
                    squareHashMap.get(king.toString().toLowerCase()
                        ).addEventEffect(UIConst.LAST_MOVE_COLOR, UIConst.BORDER_WIDTH);
                } else {
                    squareHashMap.get(king.toString().toLowerCase()).removeEventEffects();
                }
            }
        }
    }
    
    @Override
    public void tick(final String displayTime) {
        writer.overrideText(String.format(GameTimer.TIME_DISPLAY, displayTime), MessageTypeConst.TIMER, true);
    } 
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public MainUiDriverInitializer getInitializer() {
        return initializer;
    }
    
    public MainUiDriverHelper getHelper() {
        return helper;
    }
    
    public void setLastSelectedChessSquare(final ChessSquare lastSelectedChessSquare) {
        this.lastSelectedChessSquare = lastSelectedChessSquare;
    }
    
    public ChessSquare getLastSelectedChessSquare() {
        return lastSelectedChessSquare;
    }
    
    public MainUi getUi() {
        return ui;
    }
    
    public Map<String, ChessSquare> getSquareHashMap() {
        return squareHashMap;
    }
    
    public void setSquareHashMap(final LinkedHashMap<String, ChessSquare> hashMap) {
        this.squareHashMap = hashMap;
    }
    
    public StatusIO getStatusIO() {
        return statusIO;
    }
    
    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }   
    
    public ChessGame getGame() {
        return game;
    }
    
    public UiDisplayWriterHelper getWriter() {
        return writer;
    }
    
    public boolean isCurrentlyReloadingPreviousGame() {
        return currentlyReloadingPreviousGame;
    }

    public void setCurrentlyReloadingPreviousGame(final boolean currentlyReloadingPreviousGame) {
        this.currentlyReloadingPreviousGame = currentlyReloadingPreviousGame;
    }
    //</editor-fold>

}
