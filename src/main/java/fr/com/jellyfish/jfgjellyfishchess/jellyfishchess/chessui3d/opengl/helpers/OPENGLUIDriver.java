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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.components.Console3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.Writable;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.SoundUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Hint;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Move;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.MoveQueue;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.NewGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.EqualityException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.ErroneousChessPositionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.ErroneousDTOMoveException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.FenValueException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.interfaces.MoveQueueObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.interfaces.ProgressObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.ChessUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.DataUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.time.StopWatch;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.GameTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.ChessMenCollection;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.ChessGameBuildException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidInfiniteSearchResult;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.ChessGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.FenNotationObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIMessage;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIProtocolDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.ChessGameBuilderUtils;
import java.io.IOException;
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
     * Global move queue.
     */
    public MoveQueue moveQueue;

    /**
     * gl display list that are nolonger of any utility. in init method all
     * indexes are set to -1.
     */
    private final int[] obsoleteDisplayListQueue = new int[600];

    /**
     * Start index for iterating on display lists & appending.
     */
    private static final int MAX_DISPLAY_LIST_APPEND_START_INDEX = 4;

    /**
     * Start index for iterating on display lists & deleting.
     */
    static final int MAX_DISPLAY_LIST_DELETE_START_INDEX = 2;

    /**
     * Writable instance.
     */
    private final Writable writable;
    
    /**
     * UiObserver instance.
     */
    private final Console3D console;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     *
     * @param console
     */
    public OPENGLUIDriver(final Writable console) {

        this.writable = console;
        this.console = (Console3D) console;
        this.writer = new UiDisplayWriterHelper((javax.swing.JTextPane) 
                console.getTextPaneOutput(), console);
        init(Game3D.getInstance().isReloadPreviousGame());
        initDriverObservation();

        this.writer.appendText(UI3DConst.JELLYFISH_VERSION,
                MessageTypeConst.INPUT_2, true);

        // Search will be launched prior to game reloading if hints are enabled.
        if (!Game3D.getInstance().isReloadPreviousGame()) {
            this.lauchHintSearch(Game3D.getInstance().isEnableHints());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="init">
    /**
     * Initialize Drier.
     *
     * @param restart
     * @param loadingPreviousGame
     */
    private void init(final boolean reloadingGame) {

        this.setEngineColor(Game3D.getInstance().getEngineColorStringValue());
        
        try {
            this.game = ChessGameBuilderUtils.buildGame(this, GameTypeConst.CHESS_GAME,
                    Game3D.getInstance().getCharValue(Game3D.getInstance().getEngineColorStringValue()),
                    Game3D.getInstance().getCharValue(Game3D.getInstance().getEngineOponentColorStringValue()),
                    Game3D.getInstance().getEngineSearchDepth(),
                    reloadingGame,
                    1);
            this.writable.clearOutput();
            this.moveQueue = new MoveQueue(Game3D.getInstance().getEngineOponentColorStringValue(),
                    Game3D.getInstance().getEngineColorStringValue(),(MoveQueueObserver) this.writable);
            this.game.addFenObserver((FenNotationObserver) writable);
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
     * @param restartGameDto
     */
    public final void restart(final NewGame restartGameDto) {

        // Clear all model display lists :
        this.clearObsoleteDisplayLists(0);
        
        // Re-initialize singleton classes ChessBoard & ChessMenCollection.
        Board.getInstance().init();
        ChessMenCollection.getInstance().init();
        init(false);
        super.initDriverObservation();

        // If the game restart is called for relaoding a previously played game from
        // the game history list, then set moveQueue & perform this.reload(true).
        if (restartGameDto != null && restartGameDto.isReloadingSavedGame() && restartGameDto.getQueue() != null) {
            Game3D.getInstance().setPreviousMoveQueue(restartGameDto.getQueue());
            final boolean reloaded = reload(true, restartGameDto.getProgressObserver());
            if (reloaded && restartGameDto.getProgressObserver() != null) {
                restartGameDto.getProgressObserver().notifyProcessEnd();
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overriden Interface methods">
    @Override
    public void engineResponse(final String response, final int msgLevel) {

        if (response != null) {

            this.writer.appendText(response, msgLevel, true);

            // Does engine move indicate checkmate ? :
            final boolean checkmateOutput = 
                    ChessUtils.isOuputUiCheckMateNotification(this.game.getMoveCount(), response);
            // Is king in check situation ? :
            final boolean checkPosition = 
                    this.game.inCheckSituation(Game3D.getInstance().getEngineOponentColorStringValue());
            
            if (ChessUtils.isKingStaleMate(checkmateOutput, checkPosition)) {
                Game3D.getInstance().setEngineCheckmate(true);
                this.writer.appendText(String.format("%s King is stalemate in %s moves.\nGame reults in a Draw...\n",
                    Game3D.getInstance().getEngineOponentColorStringValue(), String.valueOf(this.game.getMoveCount())),
                    MessageTypeConst.CHECKMATE, true);
                Game3D.getInstance().setEngineSearching(false);
                return;
            }
            
            // is checkmate from engine ?... : is UI checkmate ?
            if (checkmateOutput && checkPosition) {
                
                Game3D.getInstance().setUiCheckmate(true);
                this.uiHelper.getBoard().updateKingSquareCheckmate(
                        Game3D.getInstance().getEngineOponentColorStringValue());
                this.writer.appendText(
                        String.format("%s King is checkmate in %s moves.\n",
                                Game3D.getInstance().getEngineOponentColorStringValue(),
                                String.valueOf(this.game.getMoveCount())),
                        MessageTypeConst.CHECKMATE,
                        true);
            }
        }
    }

    @Override
    public void engineMoved(final UCIMessage message) throws InvalidMoveException {
        
        if (!this.game.getColorToPLay().equals(Game3D.getInstance().getEngineColorStringValue())) {
            return;
        }

        // Does engine move indicate checkmate ? :
        final boolean checkmateOutput = ChessUtils.isOuputEngineCheckMateNotification(this.game.getDepth(),
                this.game.getMoveCount(), message.getMessage());
        // Is king in check situation ? :
        final boolean checkPosition = this.game.inCheckSituation(Game3D.getInstance().getEngineColorStringValue());
        
        if (ChessUtils.isKingStaleMate(checkmateOutput, checkPosition)) {
            Game3D.getInstance().setEngineCheckmate(true);
            this.writer.appendText(String.format("%s King is stalemate in %s moves.\nGame reults in a Draw...\n",
                Game3D.getInstance().getEngineColorStringValue(), String.valueOf(this.game.getMoveCount())),
                MessageTypeConst.CHECKMATE, true);
            Game3D.getInstance().setEngineSearching(false);
            return;
        }
        
        if (checkmateOutput && checkPosition) {
            Game3D.getInstance().setEngineCheckmate(true);
            this.uiHelper.getBoard().updateKingSquareCheckmate(
                    Game3D.getInstance().getEngineColorStringValue());
            this.writer.appendText(String.format("%s King is checkmate in %s moves.\n", 
                Game3D.getInstance().getEngineColorStringValue(),
                String.valueOf(this.game.getMoveCount())), MessageTypeConst.CHECKMATE, true);
            Game3D.getInstance().setEngineSearching(false);
            return;
        }

        Game3D.getInstance().setEngineMoving(true);
        new StopWatch(Game3D.getInstance().getInterMoveSleepTimeMs()).delay(null);
        
        if (!message.isValidUCI()) {
            throw new InvalidMoveException("Chess move defined in UCIMessage is not a valid move.");
        }
        
        if (this.applyEngineMove(message.getPositionFrom(), message.getPositionTo(),
                message.getBestMove(),
                message.isPawnPromotion(), message.getPromotionFenValue())) {
            // Finally :
            // Set engine searching ended :
            Game3D.getInstance().setEngineSearching(false);
            // If hints are enabled, then lauch new seach.
            this.lauchHintSearch(Game3D.getInstance().isEnableHints());
        } else {
            Game3D.getInstance().setEngineMoving(true);
            throw new InvalidMoveException(message.getBestMove() + " is not a valid move.");
        }
    }
    
    @Override
    public boolean applyEngineMove(final String posFrom, final String posTo, final String bestMove,
            final boolean pawnPromotion, final char promotion) {
        
        Move m;

        try {

            if (game.executeMove(posFrom, posTo, false, pawnPromotion, promotion)) {

                m = new Move(this.game.getMoveCount(), ChessPositions.get(posFrom),
                            ChessPositions.get(posTo), true, false,
                            uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posFrom)).getModel(),
                            uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posTo)).getModel());

                if (pawnPromotion) {
                    m.addPawnPromotionData(promotion, Game3D.getInstance().getEngineColorStringValue());
                }

                uiHelper.engineMovePositions.appendToEnd(m);
                this.moveQueue.appendToEnd(m);
                // Free GUI so that it can move again.
                Game3D.getInstance().setEngineMoving(false);
                this.setEngineSearching(false);
                // If move is validated check & checkmate situation is impossible :
                Game3D.getInstance().setEngineCheck(false);
                Game3D.getInstance().setEngineCheckmate(false);
                if (pawnPromotion) {
                    Game3D.getInstance().setEngineCheck(this.uiHelper.driver.game.inCheckSituation(
                            Game3D.getInstance().getEngineOponentColorStringValue()));
                }

                return true;
            }
            
            return false;
        } catch (final ErroneousChessPositionException | FenValueException | 
                PawnPromotionException | ErroneousDTOMoveException ex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void engineInfiniteSearchResponse(final UCIMessage uciMessage) throws InvalidInfiniteSearchResult {

        if (!Game3D.getInstance().isEnableHints() || !Game3D.getInstance().isDisplayHint()) {
            return;
        }

        final String bestMove = uciMessage.getBestMove();
        if (bestMove.length() == 5 || bestMove.length() == 4) {

            this.writer.appendText(
                    String.format("Hint : %s%s-%s%s\n",
                            bestMove.substring(0, 1), bestMove.substring(1, 2),
                            bestMove.substring(2, 3), bestMove.substring(3, 4)),
                    MessageTypeConst.CHECK,
                    true);

            if (bestMove.length() == 4) {

                final char[] from = {bestMove.toCharArray()[0], bestMove.toCharArray()[1]};
                final char[] to = {bestMove.toCharArray()[2], bestMove.toCharArray()[3]};
                try {
                    this.uiHelper.getBoard().displayHint(
                            new Hint(
                                    ChessPositions.get(String.valueOf(from)),
                                    ChessPositions.get(String.valueOf(to))
                            ));
                } catch (final ErroneousChessPositionException ex) {
                    Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        Game3D.getInstance().setDisplayHint(false);
    }

    @Override
    public void applyCastling(final String posFrom, final String posTo) {

        try {

            final boolean engineMove = ColorUtils.floatArrayEqual(
                    uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posFrom)).getModel().getColor(),
                    Game3D.getInstance().getEngineColor());

            final Move m = new Move(this.game.getMoveCount(),
                    ChessPositions.get(posFrom), ChessPositions.get(posTo), engineMove, true,
                    uiHelper.getBoard().getSquareMap().get(ChessPositions.get(posFrom)).getModel(), null);
            uiHelper.engineMovePositions.appendToEnd(m);
            moveQueue.appendToEnd(m);
            
            if (Game3D.getInstance().isReloadingPreviousGame()) {
                uiHelper.updateEngineMovesOnReload();
            }
            
        } catch (final ErroneousChessPositionException | ErroneousDTOMoveException ex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void applyPawnEnPassant(final String takenPawnPosition) {

        try {
            this.appendObsoleteDisplayList(
                    this.uiHelper.getBoard().getSquareMap().get(ChessPositions.get(takenPawnPosition)).getModelDisplayList());
            this.uiHelper.getBoard().getSquareMap().get(ChessPositions.get(takenPawnPosition)).setModel(null);
            this.uiHelper.getBoard().getSelectedSquare().setColor(
                    this.uiHelper.getBoard().getSelectedSquare().getFinalColor());
            this.uiHelper.getBoard().setSelectedSquare(null);
        } catch (final ErroneousChessPositionException ecpex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ecpex);
        } catch (final QueueCapacityOverflowException qcofex) {
            Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, qcofex);
        }
    }

    @Override
    public void applyCheckSituation(final Position king, final boolean inCheck) {

        if (inCheck) {

            try {
                this.writer.appendText(
                        String.format("%s King is in check position.\n",
                                king.getOnPositionChessMan().getColor()), MessageTypeConst.CHECK, true);

                final boolean uiWhite
                        = Game3D.getInstance().getEngineOponentColorStringValue().equals(UI3DConst.COLOR_W_STR_VALUE);
                final boolean engineMove = this.uiHelper.getBoard().isEngineSideKing(uiWhite,
                        ChessPositions.get(king.toString()));

                this.uiHelper.getBoard().updateKingSquareCheck(ChessPositions.get(king.toString()));
                Game3D.getInstance().setEngineCheck(engineMove);
                Game3D.getInstance().setUiCheck(!engineMove);

            } catch (final ErroneousChessPositionException ecpex) {
                Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, ecpex);
            }
        } else {
            Game3D.getInstance().setEngineCheck(false);
            Game3D.getInstance().setUiCheck(false);
        }
    }

    @Override
    public void tick(final String displayTime, final int ticks) {
        
        Game3D.getInstance().setCurrentGameTime(displayTime);
        if (displayTime != null) {
            this.writer.overrideText(String.format("game time: %s\n", displayTime), MessageTypeConst.TIMER, true);
            this.moveQueue.setTicks(ticks);
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

                if (!m.isPawnPromotion()) {
                    uiHelper.getBoard().updateSquare(m.getPosFrom(), m.getPosTo(), m.getModel().getColor());
                } else if (m.isPawnPromotion()) {
                    uiHelper.getBoard().updateSquare(m.getPosFrom(), m.getPosTo(), m.getModel().getColor(), 
                        m.getPawnModelObjPath(), m.getPawnPromotionPieceType());
                }

                /**
                 * Specific castling move back ?
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
            this.writer.appendText("There is no move to undo.", MessageTypeConst.NOT_SO_TRIVIAL, true);
        }
    }
        
    @Override
    public boolean isObserverReady() {
        return this.isReady();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * @param dl int display list OPEN GL reference.
     * @throws
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException
     */
    public void appendObsoleteDisplayList(final int dl) throws QueueCapacityOverflowException {
        
        for (int i = OPENGLUIDriver.MAX_DISPLAY_LIST_APPEND_START_INDEX; i < this.obsoleteDisplayListQueue.length; i++) {

            if (this.obsoleteDisplayListQueue[i] == -1) {
                this.obsoleteDisplayListQueue[i] = dl;
                
                if (Game3D.getInstance().isDEBUGMODE()) {
                    System.out.println("dl = " + dl + " obsoleteDisplayListQueue.length = "
                        + this.obsoleteDisplayListQueue.length);
                }

                return;
            }
        }

        throw new QueueCapacityOverflowException(String.format(QueueCapacityOverflowException.MESSAGE_1,
                this.obsoleteDisplayListQueue.length));
    }

    /**
     * Delete gl display lists in a gl context only.
     *
     * @param startIndex
     */
    void clearObsoleteDisplayLists(final int startIndex) {

        /**
         * Do NOT remove display lists if ui is undoing moves. The risk is
         * falsing model swapping. Also return if the start index if < 0.
         */
        if (Game3D.getInstance().isUndoingMoves() || startIndex < 0) {
            return;
        }

        for (int i = startIndex; i < this.obsoleteDisplayListQueue.length; i++) {

            if (this.obsoleteDisplayListQueue[i] == -1) {
                return; // clean up is finished.
            } else {

                /**
                 * Delete display list from allocated memory.
                 * @see https://www.opengl.org/discussion_boards/showthread.php/128966-How-delete-a-display-list-quick-and-clean
                 */
                GL11.glDeleteLists(this.obsoleteDisplayListQueue[i], 1);
                this.obsoleteDisplayListQueue[i] = -1;
            }
        }
    }

    /**
     * Clean up what can & must be.
     *
     * @throws
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException
     */
    void cleanUp() throws QueueCapacityOverflowException {

        for (Map.Entry<ChessPositions, ChessSquare> entry : this.uiHelper.getBoard().getSquareMap().entrySet()) {
            this.appendObsoleteDisplayList(entry.getValue().getModelDisplayList());
        }
    }

    /**
     * @param enableHints
     */
    public final void lauchHintSearch(final boolean enableHints) {

        if (enableHints) {
            UCIProtocolDriver.getInstance().getIoExternalEngine().executeStaticInfiniteSearch();
            new StopWatch(180).delay(null);
        }
    }

    /**
     * @param enableHints
     */
    public void stopHintSearch(final boolean enableHints) {
        
        if (enableHints) {
            UCIProtocolDriver.getInstance().getIoExternalEngine().stopStaticInfiniteSearch();
        }
    }
    
    /**
     * @param performReload
     * @param progressObserver
     * @return status
     */
    public boolean reload(final boolean performReload, final ProgressObserver progressObserver) {

        if (!performReload) {
            return performReload;
        }

        int counter = 0;
        final int maxMoves = Game3D.getInstance().getPreviousMoveQueue().getMoves().size();
        Game3D.getInstance().setReloadingPreviousGame(true);
        this.writable.enableAllEvents(false);
        
        // Iterate on move queue of previous game. Call ChessGame.executeMove()
        // for each move in the queue :
        for (Move m : Game3D.getInstance().getPreviousMoveQueue().getMoves().values()) {
            
            if (m.isCastlingMove()) {
                continue;
            }
            
            try {
                ChessMoveHelper.getInstance().reloadMove(m);
                if (progressObserver != null) {
                    ++counter;
                    progressObserver.notifyProgress(counter, maxMoves);
                }
            } catch (final ErroneousDTOMoveException edmex) {
                
                Logger.getLogger(OPENGLUIDriver.class.getName()).log(Level.SEVERE, null, edmex);
                
                try {
                   // Here, if reload fails then delete serialized file and exit.
                   DataUtils.deleteDataFiles(DataUtils.DATA_BACKUP_PATH + DataUtils.FILE_NAME +
                           DataUtils.XML_FILE_EXTENTION);
                } catch (final IOException ioex) {
                   Logger.getLogger(ChessMoveHelper.class.getName()).log(Level.SEVERE, null, ioex);
                }

                javax.swing.JOptionPane.showMessageDialog(this.uiHelper.console,
                       "The previous game failed to reload...\nA new game will be loaded instead.\nSorry :S",
                       "Game failed to reload",
                       javax.swing.JOptionPane.ERROR_MESSAGE);

                this.uiHelper.console.callNewGame(Game3D.getInstance().getEngineOponentColorStringValue(),
                       1000, false);
                break;
            }
        }
        
        this.uiHelper.engineMovePositions.clearQueue();
        this.game.resetTimer(Game3D.getInstance().getPreviousMoveQueue().getTicks());
        Game3D.getInstance().setReloadingPreviousGame(false);
        Game3D.getInstance().setPreviousMoveQueue(null);
        this.writable.enableAllEvents(true);
        this.lauchHintSearch(Game3D.getInstance().isEnableHints());
        Game3D.getInstance().setUiEnabled(true);
        this.uiHelper.getBoard().resetAllChessSquareBackgroundColors();
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter & setters">   
    public OPENGLUIHelper getUiHelper() {
        return uiHelper;
    }

    public void setHelper(final OPENGLUIHelper helper) {
        this.uiHelper = helper;
    }

    public int[] getObsoleteDisplayListQueue() {
        return obsoleteDisplayListQueue;
    }

    public Writable getWritable() {
        return writable;
    }
    //</editor-fold>

}
