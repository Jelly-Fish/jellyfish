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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DCoordinateConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers.OPENGLUIHelper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thw
 */
public class Game3D implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="vars">
    /**
     * Displya all engine output ?
     */
    private boolean displayAllOutput = false;

    /**
     * Enable hints lauches infinite search query to engine on game layout. Once
     * stopped, the search result is sent back to
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver
     * class for UI notification.
     */
    private boolean enableHints = false;

    /**
     * Display returned hint ?
     */
    private boolean displayHint = false;

    /**
     * Engine predefined color.
     */
    private float[] engineColor = UI3DConst.COLOR_B;

    /**
     * Engine oponent predefined color.
     */
    private float[] engineOponentColor = UI3DConst.COLOR_W;

    /**
     * Engine oponent predefined color string value.
     */
    private String engineOponentColorStrValue = UI3DConst.COLOR_W_STR_VALUE;

    /**
     * Engine predefined color string value.
     */
    private String engineColorStrValue = UI3DConst.COLOR_B_STR_VALUE;

    /**
     * FEN pawn promotion value.
     */
    private char pawnPromotion = 'q';

    /**
     * bg color.
     */
    private float[] backgroundColor = UI3DConst.DEFAULT_BG_COLOR;

    /**
     * Engine search depth currently sent.
     */
    private int engineSearchDepth = 2;

    /**
     * Wait time in ms between ui & engine moves.
     */
    private long interMoveSleepTimeMs = 500;

    /**
     * Always reload previous game ?
     */
    private boolean reloadPreviousGame = false;
    
    /**
     * Is game being reloaded ?
     */
    private boolean reloadingPreviousGame = false;
    
    /**
     * Black square color as float array.
     */
    private float[] blackSquareColor = UI3DConst.BLACK_SQUARE_COLOR;
    
    /**
     * White square color as float array.
     */
    private float[] whiteSquareColor = UI3DConst.WHITE_SQUARE_COLOR;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="transient vars">
    /**
     * Move queue for reloading previously played games.
     */
    private transient MoveQueue previousMoveQueue = null;

    /**
     * Is ui enabled and ready ?
     */
    private transient boolean uiEnabled = false;

    /**
     * Nextcolor to play.
     */
    private transient float[] colorToPlay = UI3DConst.COLOR_W;

    /**
     * Game timing.
     */
    private transient String currentGameTime = "";

    /**
     * Is ui side check mate ?
     */
    private transient boolean uiCheckmate = false;

    /**
     * Is engine side check mate ?
     */
    private transient boolean engineCheckmate = false;

    /**
     * Is ui undoing moves ?
     */
    private transient boolean undoingMoves = false;

    /**
     * Has engine finished moving ?
     */
    private transient boolean engineMoving = false;

    /**
     * Has engine finished moving ?
     */
    private transient boolean uiMoving = false;

    /**
     * Is ui side check ?
     */
    private transient boolean uiCheck = false;

    /**
     * Is engine side check ?
     */
    private transient boolean engineCheck = false;

    /**
     * Is engine searching ?
     */
    private transient boolean engineSearching = false;
    
    /**
     * Is this program in a developement / debug mode ?
     * If Release mode, then set to true.
     */
    private transient final boolean DEBUG_MODE = true;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="static vars">
    /**
     * Singleton instance.
     */
    private static Game3D instance = null;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="private constructor">
    /**
     *
     */
    private Game3D() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public methods">
    /**
     * Singleton accessor.
     *
     * @return Game3D only instance.
     */
    public static Game3D getInstance() {

        if (Game3D.instance == null) {
            Game3D.instance = new Game3D();
        }

        return Game3D.instance;
    }

    /**
     * initialize all variables.
     *
     * @param uiHelper
     * @param restartGameDto
     */
    public void initGame3DSettings(final OPENGLUIHelper uiHelper, final NewGame restartGameDto) {

        if (restartGameDto != null) {
            this.engineOponentColorStrValue = restartGameDto.fetchUiColor();
            this.engineColorStrValue = restartGameDto.fetchEngineColor();
        }

        final boolean uiPlayingWhites = this.engineOponentColorStrValue.equals(UI3DConst.COLOR_W_STR_VALUE);
        uiHelper.r = uiPlayingWhites ? UI3DCoordinateConst.START_R_W : UI3DCoordinateConst.START_R_B;
        uiHelper.g = uiPlayingWhites ? UI3DCoordinateConst.START_G_W : UI3DCoordinateConst.START_G_B;
        this.engineColor = uiPlayingWhites ? UI3DConst.COLOR_B : UI3DConst.COLOR_W;
        this.engineOponentColor = uiPlayingWhites ? UI3DConst.COLOR_W : UI3DConst.COLOR_B;
        this.pawnPromotion = uiPlayingWhites ? 'Q' : 'q';
    }

    /**
     * Get char value w or b depending on param1 value.
     *
     * @param value String value of color (white or black).
     * @return char w = white, b = black
     */
    public char getCharValue(final String value) {
        return value.equals(UI3DConst.COLOR_B_STR_VALUE) ? 'b' : 'w';
    }

    /**
     * @return is there a checksituation ?
     */
    public boolean noCheck() {
        return this.uiCheck == false && this.engineCheck == false;
    }

    /**
     * @return is there a checmate situation ?
     */
    public boolean noCheckmate() {
        return this.uiCheckmate == false && this.engineCheckmate == false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public serialization methods">
    /**
     * Save, serialize user's setup.
     */
    public void serialize() {

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream("data/srlz/game3d");
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(Game3D.class.getName()).log(Level.SEVERE, null, fnfex);
        }

        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (final IOException ioex) {
            Logger.getLogger(Game3D.class.getName()).log(Level.SEVERE, null, ioex);
        }

    }

    /**
     *
     */
    public void deserialize() {

        FileInputStream fileInputStreamm = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStreamm = new FileInputStream("data/srlz/game3d");
        } catch (final FileNotFoundException fnfex) {
            Logger.getLogger(Game3D.class.getName()).log(Level.SEVERE, null, fnfex);
        }
        try {
            objectInputStream = new ObjectInputStream(fileInputStreamm);
            Object obj = objectInputStream.readObject();
            if (obj instanceof Game3D) {
                Game3D.instance = ((Game3D) obj);
                objectInputStream.close();
                fileInputStreamm.close();
            }
        } catch (final IOException ioex) {
            Logger.getLogger(Game3D.class.getName()).log(Level.SEVERE, null, ioex);
        } catch (final ClassNotFoundException cnfex) {
            Logger.getLogger(Game3D.class.getName()).log(Level.SEVERE, null, cnfex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Accessors">
    public boolean isDEBUGMODE() {
        return DEBUG_MODE;
    }
    
    public float[] getBlackSquareColor() {
        return blackSquareColor;
    }

    public void setBlackSquareColor(final float[] black_square_color) {
        this.blackSquareColor = black_square_color;
    }

    public float[] getWhiteSquareColor() {
        return whiteSquareColor;
    }

    public void setWhiteSquareColor(final float[] white_square_color) {
        this.whiteSquareColor = white_square_color;
    }
    
    public boolean isReloadingPreviousGame() {
        return reloadingPreviousGame;
    }

    public void setReloadingPreviousGame(final boolean reloading_previous_game) {
        this.reloadingPreviousGame = reloading_previous_game;
    }
    
    public MoveQueue getPreviousMoveQueue() {
        return this.previousMoveQueue;
    }

    public void setPreviousMoveQueue(final MoveQueue previous_move_queue) {
        this.previousMoveQueue = previous_move_queue;
    }

    public boolean isReloadPreviousGame() {
        return this.reloadPreviousGame;
    }

    public void setReloadPreviousGame(final boolean reload_previous_game) {
        this.reloadPreviousGame = reload_previous_game;
    }
    
    public boolean isDisplayHint() {
        return this.displayHint;
    }

    public void setDisplayHint(final boolean display_hint) {
        this.displayHint = display_hint;
    }

    public boolean isDisplayAllOutput() {
        return this.displayAllOutput;
    }

    public void setDisplayAllOutput(final boolean display_all_output) {
        this.displayAllOutput = display_all_output;
    }

    public boolean isEnableHints() {
        return this.enableHints;
    }

    public void setEnableHints(final boolean enable_hints) {
        this.enableHints = enable_hints;
    }

    public boolean isEngineCheckmate() {
        return this.engineCheckmate;
    }

    public void setEngineCheckmate(final boolean engine_checkmate) {
        this.engineCheckmate = engine_checkmate;
    }

    public boolean isUiCheck() {
        return this.uiCheck;
    }

    public void setUiCheck(final boolean ui_check) {
        this.uiCheck = ui_check;
    }

    public boolean isEngineCheck() {
        return this.engineCheck;
    }

    public void setEngineCheck(final boolean engine_check) {
        this.engineCheck = engine_check;
    }

    public float[] getEngineColor() {
        return this.engineColor;
    }

    public void setEngineColor(final float[] engine_color) {
        this.engineColor = engine_color;
    }

    public float[] getEngineOponentColor() {
        return this.engineOponentColor;
    }

    public void setEngineOponentColor(final float[] engine_oponent_color) {
        this.engineOponentColor = engine_oponent_color;
    }

    public String getEngineOponentColorStringValue() {
        return this.engineOponentColorStrValue;
    }

    public void setEngineOponentColorStringValue(final String engine_oponent_color_str_value) {
        this.engineOponentColorStrValue = engine_oponent_color_str_value;
    }

    public String getEngineColorStringValue() {
        return this.engineColorStrValue;
    }

    public void setEngineColorStringValue(final String engine_color_str_value) {
        this.engineColorStrValue = engine_color_str_value;
    }

    public String getCurrentGameTime() {
        return this.currentGameTime;
    }

    public void setCurrentGameTime(final String current_game_time) {
        this.currentGameTime = current_game_time;
    }

    public float[] getColorToPlay() {
        return this.colorToPlay;
    }

    public void setColorToPlay(final float[] color_to_play) {
        this.colorToPlay = color_to_play;
    }

    public boolean isUiCheckmate() {
        return this.uiCheckmate;
    }

    public void setUiCheckmate(final boolean ui_checkmate) {
        this.uiCheckmate = ui_checkmate;
    }

    public char getPawnPromotion() {
        return this.pawnPromotion;
    }

    public void setPawnPromotion(final char pawn_promotion) {
        this.pawnPromotion = pawn_promotion;
    }

    public float[] getBgColor() {
        return this.backgroundColor;
    }

    public void setBgColor(final float[] bg_color) {
        this.backgroundColor = bg_color;
    }

    public int getEngineSearchDepth() {
        return this.engineSearchDepth;
    }

    public void setEngineSearchDepth(final int engine_search_depth) {
        this.engineSearchDepth = engine_search_depth;
    }

    public boolean isUiEnabled() {
        return this.uiEnabled;
    }

    public void setUiEnabled(final boolean uiEnabled) {
        this.uiEnabled = uiEnabled;
    }

    public boolean isUndoingMoves() {
        return this.undoingMoves;
    }

    public void setUndoingMoves(final boolean undoingMoves) {
        this.undoingMoves = undoingMoves;
    }

    public boolean isEngineMoving() {
        return this.engineMoving;
    }

    public void setEngineMoving(final boolean engine_moving) {
        this.engineMoving = engine_moving;
    }

    public boolean isUiMoving() {
        return this.uiMoving;
    }

    public void setUiMoving(final boolean ui_moving) {
        this.uiMoving = ui_moving;
    }

    public long getInterMoveSleepTimeMs() {
        return this.interMoveSleepTimeMs;
    }

    public void setInterMoveSleepTimeMs(final long inter_move_sleep_time_ms) {
        this.interMoveSleepTimeMs = inter_move_sleep_time_ms;
    }

    public boolean isEngineSearching() {
        return engineSearching;
    }

    public void setEngineSearching(final boolean engine_searching) {
        this.engineSearching = engine_searching;
    }
    //</editor-fold>

}
