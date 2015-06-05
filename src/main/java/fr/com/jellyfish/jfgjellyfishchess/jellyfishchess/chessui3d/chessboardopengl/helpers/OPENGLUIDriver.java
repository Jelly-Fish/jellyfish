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
 * *****************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.MessageConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.interfaces.Writable;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.ui.UiDisplayWriterHelper;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.SoundUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Move;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.MoveQueue;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.RestartNewGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.EqualityException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.ErroneousChessPositionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.GameTypeConst;
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
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.ChessGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIMessage;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIProtocolDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.externalengine.IOExternalEngine;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.ChessGameBuilderUtils;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author thw
 */
public class OPENGLUIDriver extends AbstractChessGameDriver {

    //<editor-fold defaultstate="collapsed" desc="vars">
    /**
     * OPENGLUIHelper instance reference.
     */
    private OPENGLUIHelper uiHelper;

    /**
     * ChessGame instance.
     */
    public ChessGame game = null;

    /**
     *
     */
    private final UiDisplayWriterHelper writer;

    /**
     * Global move queue.
     */
    public MoveQueue moveQueue;

    /**
     * gl display list that are nolonger of any utility. in init method all
     * indexes are set to -1.
     */
    private final int[] obsoleteDisplayListQueue = new int[200];
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     *
     * @param console
     */
    public OPENGLUIDriver(final Writable console) {

        this.writer = new UiDisplayWriterHelper(console.getTextPaneOutput(), console);
        this.moveQueue = new MoveQueue();
        init();
        initDriverObservation();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="init">
    /**
     * Initialize Drier.
     *
     * @param restart
     * @param loadingPreviousGame
     */
    private void init() {
        
        UCIProtocolDriver.getInstance().getIoExternalEngine().clearObservers();
        UCIProtocolDriver.getInstance().getIoExternalEngine().addExternalEngineObserver(this);

        this.setEngineColor(Game3D.engine_color_str_value);
        
        try {
            this.game = ChessGameBuilderUtils.buildGame(this, GameTypeConst.CHESS_GAME,
                    Game3D.getCharValue(Game3D.engine_color_str_value),
                    Game3D.getCharValue(Game3D.engine_oponent_color_str_value),
                    Game3D.engine_search_depth,
                    false,
                    1);
        } catch (final ChessGameBuildException ex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Init obsolete display list queue.
         */
        for (int i = 0; i < this.obsoleteDisplayListQueue.length; i++) {
            this.obsoleteDisplayListQueue[i] = -1;
        }
    }

    /**
     * Add this to all necessary observer patterns.
     */
    private void initDriverObservation() {

        // Add this class to Rook class's CastlingObservers.
        Rook rookA1 = (Rook) Board.getInstance().getCoordinates().get(
                BoardConst.A1).getOnPositionChessMan();
        rookA1.addCastlingObserver(this);
        Rook rookA8 = (Rook) Board.getInstance().getCoordinates().get(
                BoardConst.A8).getOnPositionChessMan();
        rookA8.addCastlingObserver(this);
        Rook rookH1 = (Rook) Board.getInstance().getCoordinates().get(
                BoardConst.H1).getOnPositionChessMan();
        rookH1.addCastlingObserver(this);
        Rook rookH8 = (Rook) Board.getInstance().getCoordinates().get(
                BoardConst.H8).getOnPositionChessMan();
        rookH8.addCastlingObserver(this);

        // Add this as observer on all Pawn classes for "En passant" moving.
        // Black Pawns :
        Pawn pawnH7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.H7).getOnPositionChessMan();
        pawnH7.addPawnEnPassantObserver(this);
        Pawn pawnG7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.G7).getOnPositionChessMan();
        pawnG7.addPawnEnPassantObserver(this);
        Pawn pawnF7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.F7).getOnPositionChessMan();
        pawnF7.addPawnEnPassantObserver(this);
        Pawn pawnE7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.E7).getOnPositionChessMan();
        pawnE7.addPawnEnPassantObserver(this);
        Pawn pawnD7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.D7).getOnPositionChessMan();
        pawnD7.addPawnEnPassantObserver(this);
        Pawn pawnC7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.C7).getOnPositionChessMan();
        pawnC7.addPawnEnPassantObserver(this);
        Pawn pawnB7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.B7).getOnPositionChessMan();
        pawnB7.addPawnEnPassantObserver(this);
        Pawn pawnA7 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.A7).getOnPositionChessMan();
        pawnA7.addPawnEnPassantObserver(this);
        // White Pawns :
        Pawn pawnA2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.A2).getOnPositionChessMan();
        pawnA2.addPawnEnPassantObserver(this);
        Pawn pawnB2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.B2).getOnPositionChessMan();
        pawnB2.addPawnEnPassantObserver(this);
        Pawn pawnC2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.C2).getOnPositionChessMan();
        pawnC2.addPawnEnPassantObserver(this);
        Pawn pawnD2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.D2).getOnPositionChessMan();
        pawnD2.addPawnEnPassantObserver(this);
        Pawn pawnE2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.E2).getOnPositionChessMan();
        pawnE2.addPawnEnPassantObserver(this);
        Pawn pawnF2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.F2).getOnPositionChessMan();
        pawnF2.addPawnEnPassantObserver(this);
        Pawn pawnG2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.G2).getOnPositionChessMan();
        pawnG2.addPawnEnPassantObserver(this);
        Pawn pawnH2 = (Pawn) Board.getInstance().getCoordinates().get(
                BoardConst.H2).getOnPositionChessMan();
        pawnH2.addPawnEnPassantObserver(this);

        // Add this as check observer to all chessmen :
        for (String position : BoardConst.boardPositions) {
            Board.getInstance().getCoordinates().get(position).getOnPositionChessMan().setCheckObserver(this);
        }
    }
    
    /**
     * @param restartGameDto 
     */
    public final void restart(final RestartNewGame restartGameDto) {
        
        /**
         * RestartNewGame instance is not used here but could in the futur.
         */
        
        this.moveQueue = new MoveQueue();
        
        // If game is a restart then Engine must also be restarted using appropriate 
        // commands.
        IOExternalEngine.getInstance().writeToEngine(UCIConst.ENGINE_QUIT, MessageTypeConst.NOT_SO_TRIVIAL);
        IOExternalEngine.getInstance().init();
        
        // Re-initialize singleton classes ChessBoard & ChessMenCollection.
        Board.getInstance().init();
        ChessMenCollection.getInstance().init();
        init();
        initDriverObservation();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overriden Interface methods">
    @Override
    public void engineResponse() {
    }

    @Override
    public void engineResponse(final String response, final int msgLevel) {
        if (response != null) {
            this.writer.appendText(response, msgLevel, true);
        }
    }

    @Override
    public void engineMoved(final UCIMessage message) {
        
        Game3D.engine_moving = true;
        
        /**
         * TODO : sleeping here needs re-thinking...
         * A long term solution is needed.
         */
        try {
            if (Game3D.uiEnabled) {
                Thread.sleep(Game3D.inter_move_sleep_time_ms);
            } else if (Game3D.engine_color_str_value.equals(UI3DConst.COLOR_W_STR_VALUE)) {
                Thread.sleep(Game3D.inter_move_sleep_time_ms * 10);
                Game3D.uiEnabled = true;
            } else {
                Thread.sleep(Game3D.inter_move_sleep_time_ms);
                Game3D.uiEnabled = true;
            }
        } catch (final InterruptedException ex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (game.getColorToPLay().toLowerCase().toCharArray()[0] == game.getEngineColor()) {
            // TODO : notify move.
        } else {
            // TODO : wrong turn.
        }

        /**
         * Apply move to GUI.
         * Check legth of message : if == 4 then split in 2 halfs.
         * == 5 is a pawn promotion.
         */
        char promotion = ' ';
        char promotionColor = ' ';
        boolean pawnPromotion = false;
        final String posFrom;
        final String posTo;
        Move m;

        if (message.getBestMove().length() == 4 || message.getBestMove().length() == 5) {

            posFrom = (String.valueOf(message.getBestMove().toCharArray()[0])
                    + String.valueOf(message.getBestMove().toCharArray()[1]));
            posTo = (String.valueOf(message.getBestMove().toCharArray()[2])
                    + String.valueOf(message.getBestMove().toCharArray()[3]));

            if (message.getBestMove().length() == 5) {

                pawnPromotion = true;
                // Get promotion type. Ex : a7a8q 'q' meaning Queen.
                promotion = message.getBestMove().toCharArray()[4];
                // Also determinate promotion color.
                if (this.getEngineColor().equals(BoardConst.BLACK)) {
                    promotionColor = 'b';
                } else if (this.getEngineColor().equals(BoardConst.WHITE)) {
                    promotionColor = 'w';
                }
            }

            try {

                if (game.executeMove(posFrom, posTo, false, pawnPromotion, promotion)) {

                    if (pawnPromotion) {
                        // TODO : code pawn promotioning.
                    } else {

                        try {

                            if (uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posTo)).getModel() != null) {
                                // Then move is a take move : 
                                m = new Move(ChessPositions.get(posFrom), ChessPositions.get(posTo), true,
                                        uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posFrom)).getModel(),
                                        uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posTo)).getModel());
                            } else {
                                // Then simple move.
                                m = new Move(ChessPositions.get(posFrom), ChessPositions.get(posTo), true,
                                        uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posFrom)).getModel());
                            }

                            uiHelper.engineMovePositions.appendToEnd(m);
                            this.moveQueue.appendToEnd(m);
                        } catch (final ErroneousChessPositionException ex) {
                            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    // Free GUI so that it can move again.
                    Game3D.engine_moving = false;
                    this.setEngineSearching(false);
                } else {
                    Game3D.engine_moving = true;
                    throw new InvalidMoveException(message.getBestMove() + " is not a valid move.");
                }

            } catch (InvalidMoveException | PawnPromotionException ex) {
                Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.WARNING, null, ex);
                return;
            }

            // Finally, is checkmate from engine ? :
            if (message.getMessage().contains(UCIConst.NONE)
                    && game.getMoveCount() >= UCIConst.FOOLS_MATE) {
                writer.appendText(String.format(MessageConst.CHECK_MATE,
                        game.getEngineOponentColorStringValue(), game.getMoveCount()),
                        MessageTypeConst.CHECKMATE, true);
            }
        }
    }

    @Override
    public void engineInfiniteSearchResponse(final UCIMessage uciMessage) throws InvalidInfiniteSearchResult {
    }

    @Override
    public void applyCastling(final String posFrom, final String posTo) {
        try {
            final boolean engineMove
                    = Game3D.engine_color_str_value.equals(UI3DConst.COLOR_B_STR_VALUE)
                    && posFrom.toCharArray()[1] == '8';
            final Move m = new Move(ChessPositions.get(posFrom), ChessPositions.get(posTo), engineMove,
                    uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posFrom)).getModel(), true);
            uiHelper.engineMovePositions.appendToEnd(m);
            moveQueue.appendToEnd(m);
        } catch (final ErroneousChessPositionException ex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void applyPawnEnPassant(final String takenPawnPosition) {

        try {
            this.appendObsoleteDisplayList(
                this.uiHelper.getBoard().getSquareMap().get(ChessPositions.get(takenPawnPosition)).getModelDisplayList());
            this.uiHelper.getBoard().getSquareMap().get(ChessPositions.get(takenPawnPosition)).setModel(null);
        } catch (final ErroneousChessPositionException ecpex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ecpex);
        } catch (final QueueCapacityOverflowException qcofex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, qcofex);
        }
    }

    @Override
    public void applyCheckSituation(final Position king, final boolean inCheck) {
    }

    @Override
    public void tick(final String displayTime) {
        Game3D.current_game_time = displayTime;
        if (displayTime != null) {
            this.writer.overrideText(String.format("game time: %s\n", displayTime), MessageTypeConst.TIMER, true);
        }
    }

    @Override
    public void applyMoveBack(Map<String, Character> positions, String fen, int moveCount, int plyDepth) {

        if (this.game.getMoveCount() > 0) {  

            final int mIndex = moveQueue.getCounter();
            final String strIndex = String.valueOf(mIndex);
            final String decrementedStrIndex = String.valueOf(mIndex - 1);
            final Move m = moveQueue.getMoves().get(strIndex);

            if (!m.isTakeMove()) {

                uiHelper.getBoard().updateSquare(m.getPosFrom(), m.getPosTo(), m.getModel().getColor());
                
                /**
                 * Specific castling move back.
                 */
                if (mIndex - 1 > 0 && moveQueue.getMoves().get(decrementedStrIndex).isCastlingMove()) {
                    final Move kingMove = moveQueue.getMoves().get(decrementedStrIndex);
                    uiHelper.getBoard().updateSquare(kingMove.getPosFrom(), kingMove.getPosTo(),
                            kingMove.getModel().getColor());

                    try {
                        // If castling, then remove the rook Move entry :
                        moveQueue.removeFromQueue(decrementedStrIndex, kingMove);
                    } catch (final MoveIndexOutOfBoundsException mioobex) {
                        Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, mioobex);
                    } catch (final EqualityException eex) {
                        Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, eex);
                    }
                }
            } else if (m.isTakeMove()) {
                uiHelper.getBoard().updateSquare(m.getPosFrom(), m.getPosTo(), m.getModel(), m.getTakenModel());
            } else {
                // BIG TROUBLE ! TODO : throw exception.
            }

            try {
                // Finally :
                moveQueue.removeFromQueue(strIndex, m);
            } catch (final MoveIndexOutOfBoundsException mioobex) {
                Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, mioobex);
            } catch (final EqualityException eex) {
                Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, eex);
            }

            uiHelper.getBoard().resetAllChessSquareBackgroundColors();
            uiHelper.getSoundManager().playEffect(SoundUtils.StaticSoundVars.move);
            uiHelper.getBoard().setSelectedSquare(null);

        } else {
            // TODO : notify
            // No move to move back to.
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Remove all label opengl objects.
     */
    public void removeAllLabels() {

        for (ChessSquare s : uiHelper.getBoard().getSquareMap().values()) {
            // Set all labels to null.
            s.setLabel(null);
        }
    }
    
    /**
     * @param dl int display list OPEN GL reference.
     * @throws
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException
     */
    public void appendObsoleteDisplayList(final int dl) throws QueueCapacityOverflowException {
        
        for (int i = 0; i < this.obsoleteDisplayListQueue.length; i++) {
            
            if (this.obsoleteDisplayListQueue[i] == -1) {
                this.obsoleteDisplayListQueue[i] = dl;
                
                /**
                 * DEBUG :
                 */
                System.out.println("dl = " + dl + " obsoleteDisplayListQueue.length = " + 
                        this.obsoleteDisplayListQueue.length);
                
                return;
            }
        }

        throw new QueueCapacityOverflowException(String.format(QueueCapacityOverflowException.MESSAGE_1,
                this.obsoleteDisplayListQueue.length));
    }

    /**
     * Delete gl display lists in a gl context only.
     * @param startIndex
     */
    public void clearObsoleteDisplayLists(final int startIndex) {
        
        /**
         * Do NOT remove display lists if ui is undoing moves.
         * The risk is falsing model swapping. Also return if the 
         * start index if < 0.
         */
        if (Game3D.undoingMoves || startIndex < 0) { return; }
        
        for (int i = startIndex; i < this.obsoleteDisplayListQueue.length; i++) {
            
            if (this.obsoleteDisplayListQueue[i] == -1) {
                return; // clean up is finished.
            } else {
                
                /**
                 * Delete display list from allocated memory.
                 *
                 * @see
                 * https://www.opengl.org/discussion_boards/showthread.php/128966-How-delete-a-display-list-quick-and-clean
                 */
                GL11.glDeleteLists(this.obsoleteDisplayListQueue[i], 1);
                this.obsoleteDisplayListQueue[i] = -1;
            }
        }
    }
    
    /**
     * Clean up what can & must be.
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException
     */
    public void cleanUp() throws QueueCapacityOverflowException {
        
        for (Map.Entry<ChessPositions, ChessSquare> entry : this.uiHelper.getBoard().getSquareMap().entrySet()) {
            appendObsoleteDisplayList(entry.getValue().getModelDisplayList());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter & setters">   
    public OPENGLUIHelper getUiHelper() {
        return uiHelper;
    }
    
    public void setHelper(OPENGLUIHelper helper) {
        this.uiHelper = helper;
    }

    public int[] getObsoleteDisplayListQueue() {
        return obsoleteDisplayListQueue;
    }
    //</editor-fold>

}
