/*******************************************************************************
 * Copyright (c) 2014, Thomas.H Warner. All rights reserved.
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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.ChessMenCollection;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.BoardConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.CommonConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.ExceptionConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Board;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.entities.Position;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.FenConvertionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.InvalidMoveException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.CastlingObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.CheckObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.ExternalEngineObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.FenNotationObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.PawnEnPassantObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.interfaces.UiObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.timer.GameTimer;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIMessage;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.UCIProtocolDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.EngineCMDUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.FENConverter;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.utils.FENUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Chess game.
 *
 * @author Thomas.H Warner 2014
 */
public class ChessGame implements ExternalEngineObserver, CastlingObserver,
        PawnEnPassantObserver, CheckObserver, UiObserver, Serializable {

    //<editor-fold defaultstate="collapsed" desc="Private vars">    
    /**
     * Color Engine is playing with.
     */
    protected final char engineColor;

    /**
     * Engine depth exploring level.
     */
    protected Integer depth;

    /**
     * Color engine is playing against.
     */
    protected final char engineOponentColor;

    /**
     * Game move count.
     */
    private int moveCount = 0;

    /**
     * Move index when moving back or forwards.
     */
    private int moveIndex = 0;

    /**
     * If gui if resetting move forward or backwards.
     */
    private boolean resettingMove = false;

    /**
     * Map for game moves (moves move1...).
     */
    protected LinkedHashMap<Integer, String> gameMoves;

    /**
     * Map for fen string moves of the game.
     */
    protected LinkedHashMap<Integer, String> fenMoves;

    /**
     * GUI ref.
     */
    protected AbstractChessGameDriver driver;

    /**
     * When GUI forces engine to play his turn.
     */
    private boolean engineForcedToPlayedMove;

    /**
     * If next turn if for engine to play depending on colors chosen at game
     * start.
     */
    protected String colorToPLay;

    /**
     *
     */
    private List<FenNotationObserver> fenObservers = new ArrayList<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Constructor.
     *
     * @param driver
     * @param engineColor
     * @param engineOponentColor
     * @param depth
     * @param loadingPreviousGame
     * @param seconds
     */
    public ChessGame(final AbstractChessGameDriver driver, final char engineColor,
            final char engineOponentColor, final int depth, final boolean loadingPreviousGame,
            final int seconds) {
        
        // Init ChessMenCollection singleton class.
        ChessMenCollection.getInstance();
        this.engineColor = engineColor;
        this.engineOponentColor = engineOponentColor;
        this.depth = depth;
        this.driver = driver;
        this.engineForcedToPlayedMove = false;
        this.colorToPLay = BoardConst.WHITE; // Whites play first.
        // init class instance, param bool <loadingPreviousGame> = if a new game
        // is being initialized or a previous game beign reloaded :
        init(loadingPreviousGame);
        // Finnaly init, start timer & set this driver as engine observer :
        GameTimer.getInstance();
        GameTimer.getInstance().init(seconds, true);
        GameTimer.getInstance().setTimerObserver(this.driver);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public methods">
    /**
     * Gui - Engine Communication. A GUI can send params and react following
     * return statement.
     *
     * @param posFrom
     * @param posTo
     * @param guiMove
     * @param pawnPromotion
     * @param promotion
     * @return boolean : if the move has been executed and therefor validated by
     * engine.
     * @throws
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.PawnPromotionException
     */
    public boolean executeMove(final String posFrom, final String posTo,
            final boolean guiMove, final boolean pawnPromotion, final char promotion)
            throws PawnPromotionException {

        // Set next color to play. If colorToPLay == white then = black ect.
        setColorToPlay();

        // Execute a chess move that is sent in a String. Example : for a 
        // Pawn move posFrom will be D2 and posTo D4.
        try {

            // Validate for moves coming from GUI or Engine.
            if (Board.getInstance().getCoordinates().get(posFrom).getOnPositionChessMan().move(
                    Board.getInstance().getCoordinates().get(posFrom),
                    Board.getInstance().getCoordinates().get(posTo), guiMove)) {

                // Reset boolean resettingMove = false to prevent deserializing 
                // moves that are out of game context : gui can take a different
                // move than those serialized in snapshots. Also update fen and
                // game moves LinkedHashMaps and clear obsolete snapshots from
                // data/snapshot directory :
                if (this.resettingMove) {
                    updateGameSnapshots();
                    resettingMove = false;
                }
                // Increment the global game move counter and move index.
                ++moveCount;
                ++moveIndex;

                // Add move to map.
                if (!pawnPromotion) {
                    gameMoves.put(moveIndex, posFrom.toLowerCase() + posTo.toLowerCase());
                } else {
                    // If the move is a Pawn promotion move, and promotion value at 
                    // end of the move command.
                    gameMoves.put(moveIndex, posFrom.toLowerCase() + posTo.toLowerCase()
                            + String.valueOf(promotion));
                }

                String moves;
                String fenMove;

                // See http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
                // for FEN notation. This is a classic chess notation style. 
                // Displaying it or exporting a game to file in FEN style notation 
                // may be possible using the fenMoves LinkedHashMap. 
                // Build FEN string :
                fenMove = FENUtils.buildFENString();

                // Add to Map of FEN Strings:
                fenMoves.put(moveIndex, fenMove);
                notifyFENObservers();

                // Build "moves" string for send to engine. Uses UCI protocol.
                // See http://support.stockfishchess.org/kb/advanced-topics/uci-protocol
                moves = EngineCMDUtils.buildMovesString(gameMoves);

                // If move comes from GUI, call external engine and send move 
                // string following with 'go depth <value of depth param>'.
                // See UCI protocol for more information.
                if (guiMove) {
                    // Prevent GUI from executing moves will searching :
                    driver.setEngineSearching(true);

                    UCIProtocolDriver.getInstance().getIoExternalEngine().writeToEngine(
                            moves, MessageTypeConst.INPUT_2);
                    UCIProtocolDriver.getInstance().getIoExternalEngine().writeToEngine(
                            UCIConst.GO_DEPTH + UCIConst.SPACE + depth.toString(), MessageTypeConst.INPUT_1);
                }

                // Save snapshot of chess board :
                BoardSnapshot snapshot = new BoardSnapshot(Board.getInstance().getCoordinates(), moveIndex, fenMove);
                snapshot.serialize();
                // The move has been validated by ChessMan.move() method :
                return true;
            }
        } catch (final InvalidMoveException ime) {
            Logger.getLogger(ChessGame.class.getName()).log(Level.WARNING, null, ime);
            // Reset next color to play.
            setColorToPlay();
            return false;
        }

        return false;
    }

    /**
     * Take move back.
     *
     * @throws
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.FenConvertionException
     * @throws
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.exceptions.MoveIndexOutOfBoundsException
     */
    public void executeMoveBack() throws FenConvertionException, MoveIndexOutOfBoundsException {

        if (moveIndex <= 0) {
            throw new MoveIndexOutOfBoundsException(String.format(ExceptionConst.EX_MOVE_OUT_OF_BOUNDS,
                    String.valueOf(moveCount), String.valueOf(moveIndex)));
        }

        this.resettingMove = true;
        --moveIndex;

        BoardSnapshot snapshot = new BoardSnapshot(moveIndex);
        snapshot.deserialize();
        Board.getInstance().resetCoordinates(snapshot.getSnapshot(), this);

        // Get <position, fenValue> from fenMoves Map's last entry :
        Map<String, Character> positions
                = FENConverter.givePositionsFromFen(fenMoves.get(moveIndex));
        // Convert fen String and call GUI.
        driver.applyMoveBack(positions, fenMoves.get(moveIndex).replace(
                UCIConst.POS + CommonConst.SPACE_STR, CommonConst.EMPTY_STR), moveIndex,
                depth);
        notifyFENObservers();
        setColorToPlay();
    }

    /**
     * Move foward in move collection.
     *
     * @throws FenConvertionException
     * @throws MoveIndexOutOfBoundsException
     */
    public void executeMoveForward() throws FenConvertionException, MoveIndexOutOfBoundsException {

        // Move forward can only be used if executeMoveBack() has previously been
        // called and no call to executeMove(...) has occured.
        if (this.resettingMove) {

            if (moveIndex >= moveCount) {
                throw new MoveIndexOutOfBoundsException(String.format(ExceptionConst.EX_MOVE_OUT_OF_BOUNDS,
                        String.valueOf(moveCount), String.valueOf(moveIndex)));
            }

            this.resettingMove = true;
            ++moveIndex;

            BoardSnapshot snapshot = new BoardSnapshot(moveIndex);
            snapshot.deserialize();
            Board.getInstance().resetCoordinates(snapshot.getSnapshot(), this);

            // Get <position, fenValue> from fenMoves Map's last entry :
            Map<String, Character> positions
                    = FENConverter.givePositionsFromFen(fenMoves.get(moveIndex));
            // Convert fen String and call GUI.
            driver.applyMoveBack(positions, fenMoves.get(moveIndex).replace(
                    UCIConst.POS + CommonConst.SPACE_STR, CommonConst.EMPTY_STR), moveIndex,
                    depth);

            setColorToPlay();
        }
    }

    /**
     * Update fen and game moves linked hash maps; delete all entries that are
     * greater or equal too move index.
     */
    public void updateGameSnapshots() {

        // Remove Linked has maps entry indexes until last move index is reached.
        while (moveCount > moveIndex) {
            fenMoves.remove(moveCount);
            gameMoves.remove(moveCount);
            --moveCount; // decrement move count.
        }

        moveCount = moveIndex;
        // Finally delete obsolete snapshots :
        BoardSnapshot.deleteSnapshots(this.moveIndex);
    }

    /**
     * @param observer
     */
    public void addFenObserver(final FenNotationObserver observer) {
        this.fenObservers.add(observer);
        notifyFENObservers();
    }
    
    /**
     * Notify that Game and game driver are ready for engine com.
     */
    public void notifyReadyStateToEngine() {
        UCIProtocolDriver.getInstance().getIoExternalEngine().notifyObserversReady();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private methods">
    /**
     * Initialize this new Game : add this as an Observer for engine moves.
     *
     * @param loadingPreviousGame
     */
    private void init(final boolean loadingPreviousGame) {

        // Initialize maps for both FEN & UCI protocol.
        fenMoves = new LinkedHashMap<>();
        gameMoves = new LinkedHashMap<>();
        final UCIProtocolDriver uci = UCIProtocolDriver.getInstance();
        // Clean up engine and add driver as engine observer:
        uci.getIoExternalEngine().clearObservers();
        uci.getIoExternalEngine().addExternalEngineObserver(this.driver);
        uci.getIoExternalEngine().setGameInstance(this);
        // See ExternalEngineObserver interface.

        // If GUI has decided to play blacks and GUI is not in the process of
        // reloading the previous game, then first move must be forced. 
        // Engine is playing whites and must be told to take the first move :
        // simply write to class UCIProtocolDriver's instance of IOExternalEngine
        // calling it's writeToEngine() method.
        if (Character.valueOf(this.engineColor).equals(BoardConst.WHITE_CHAR)
                && !loadingPreviousGame) {
            UCIProtocolDriver.getInstance().getIoExternalEngine().writeToEngine(
                    UCIConst.POS + UCIConst.SPACE + UCIConst.START_POS
                    + UCIConst.SPACE + UCIConst.MOVES, MessageTypeConst.INPUT_1);
            UCIProtocolDriver.getInstance().getIoExternalEngine().writeToEngine(
                    UCIConst.GO_DEPTH + UCIConst.SPACE + this.depth.toString(), MessageTypeConst.INPUT_1);
        }

        // Build FEN string :
        String fenMove = FENUtils.buildFENString();

        // Save snapshot of chess board :
        BoardSnapshot snapshot = new BoardSnapshot(Board.getInstance().getCoordinates(), moveIndex, fenMove);
        snapshot.serialize();

        // Add to Map of FEN Strings:
        fenMoves.put(moveIndex, fenMove);
    }

    /**
     * Set next move color.
     */
    private void setColorToPlay() {
        this.colorToPLay = this.colorToPLay.equals(BoardConst.WHITE) ? BoardConst.BLACK : BoardConst.WHITE;
    }

    /**
     *
     * @return String value of engine's oponent color.
     */
    public String getEngineOponentColorStringValue() {
        return this.engineColor == UCIConst.WHITE_CHAR_LOWER ? CommonConst.BLACK_STR : CommonConst.WHITE_STR;
    }

    /**
     *
     * @return String value of engine's color.
     */
    public String getEngineColorStringValue() {
        return this.engineColor == UCIConst.WHITE_CHAR_LOWER ? CommonConst.WHITE_STR : CommonConst.BLACK_STR;
    }

    /**
     * Notify FEN notation observers.
     */
    private void notifyFENObservers() {

        StringBuilder s = new StringBuilder();

        for (FenNotationObserver fenObserver : fenObservers) {

            for (int i = 0; i <= this.moveIndex; i++) {
                s.append(i).append(".").append(
                        this.fenMoves.get(i).replaceAll("position fen ", "")).append(" ");
            }
            fenObserver.observeFEN(s.toString());
            s = new StringBuilder();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overriden interface methods">
    /**
     * @param color black or white.
     * @return true if king defined by color is check else false.
     */
    @Override
    public boolean inCheckSituation(final String color) {

        for (Position p : Board.getInstance().getCoordinates().values()) {
            if (p.getOnPositionChessMan().getColor().equals(color) && !p.getOnPositionChessMan().isNullChessMan()) {
                final boolean check = p.getOnPositionChessMan().getChessManKing().isKingInCheckSituation(
                    BoardConst.coordinatesIntegerMap.get(
                            p.getOnPositionChessMan().getChessManKing().getBoardPosition().toString()));

                if (check) {
                    this.driver.applyCheckSituation(
                            p.getOnPositionChessMan().getChessManKing().getBoardPosition(), check);
                    return check;
                }
            }
        }

        return false;
    }

    /**
     * @param ticks
     */
    @Override
    public void resetTimer(final int ticks) {
        GameTimer.setTicks(ticks);
    }

    @Override
    public void engineMoved(final UCIMessage message) throws InvalidMoveException {
        this.engineForcedToPlayedMove = false;
    }
    
    @Override
    public boolean applyEngineMove(final String posFrom, final String posTo, final String bestMove, final boolean pawnPromotion, final char promotion) {
        return false;
    }

    @Override
    public int getSearchDepth() {
        return this.depth;
    }

    @Override
    public void setSearchDepth(final int depth) {
        this.depth = depth;
    }

    @Override
    public boolean isObserverReady() {
        return true;
    }

    //<editor-fold defaultstate="collapsed" desc="Overriden interface methods - unused">
    @Override
    public void engineResponse(final String response, final int msgLevel) {
        // Unused here. 
    }
    
    @Override
    public void engineInfiniteSearchResponse(final UCIMessage message) {
        // Unused here.
    }

    @Override
    public void applyCastling(final String posFrom, final String posTo) {
        // Unused here.
    }

    @Override
    public void applyPawnEnPassant(final String takenPawnPosition) {
        // Unused here.
    }

    @Override
    public void applyCheckSituation(final Position king, final boolean inCheck) {
        // Unused here.
    }
    //</editor-fold>
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public boolean isResettingMove() {
        return resettingMove;
    }

    public void setResettingMove(final boolean resettingMove) {
        this.resettingMove = resettingMove;
    }

    public String getColorToPLay() {
        return colorToPLay;
    }

    public boolean isEngineForcedToPlayedMove() {
        return engineForcedToPlayedMove;
    }

    public LinkedHashMap<Integer, String> getGameMoves() {
        return gameMoves;
    }

    public char getEngineColor() {
        return engineColor;
    }

    public Integer getDepth() {
        return depth;
    }

    public char getEngineOponentColor() {
        return engineOponentColor;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public LinkedHashMap<Integer, String> getFenMoves() {
        return fenMoves;
    }

    public void setDepth(final Integer depth) {
        this.depth = depth;
    }

    public AbstractChessGameDriver getDriver() {
        return driver;
    }

    public int getMoveIndex() {
        return moveIndex;
    }

    public void setMoveCount(final int moveCount) {
        this.moveCount = moveCount;
    }

    public void setMoveIndex(final int moveIndex) {
        this.moveIndex = moveIndex;
    }

    public void setGameMoves(final LinkedHashMap<Integer, String> gameMoves) {
        this.gameMoves = gameMoves;
    }

    public void setFenMoves(final LinkedHashMap<Integer, String> fenMoves) {
        this.fenMoves = fenMoves;
    }

    public void setColorToPLay(final String colorToPLay) {
        this.colorToPLay = colorToPLay;
    }
    // </editor-fold> 

}
