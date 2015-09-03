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
    private boolean display_all_output = false;

    /**
     * Enable hints lauches infinite search query to engine on game layout. Once
     * stopped, the search result is sent back to
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver
     * class for UI notification.
     */
    private boolean enable_hints = false;

    /**
     * Display returned hint ?
     */
    private boolean display_hint = false;

    /**
     * Engine predefined color.
     */
    private float[] engine_color = UI3DConst.COLOR_B;

    /**
     * Engine oponent predefined color.
     */
    private float[] engine_oponent_color = UI3DConst.COLOR_W;

    /**
     * Engine oponent predefined color string value.
     */
    private String engine_oponent_color_str_value = UI3DConst.COLOR_W_STR_VALUE;

    /**
     * Engine predefined color string value.
     */
    private String engine_color_str_value = UI3DConst.COLOR_B_STR_VALUE;

    /**
     * FEN pawn promotion value.
     */
    private char pawn_promotion = 'q';

    /**
     * bg color.
     */
    private float[] bg_color = UI3DConst.DEFAULT_BG_COLOR;

    /**
     * Engine search depth currently sent.
     */
    private int engine_search_depth = 2;

    /**
     * Wait time in ms between ui & engine moves.
     */
    private long inter_move_sleep_time_ms = 280;

    /**
     * Always reload previous game ?
     */
    private boolean reload_previous_game = false;
    
    /**
     * Is game being reloaded ?
     */
    private boolean reloading_previous_game = false;
    
    /**
     * Black square color as float array.
     */
    private float[] black_square_color = UI3DConst.BLACK_SQUARE_COLOR;
    
    /**
     * White square color as float array.
     */
    private float[] white_square_color = UI3DConst.WHITE_SQUARE_COLOR;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="transient vars">
    /**
     * Move queue for reloading previously played games.
     */
    private transient MoveQueue previous_move_queue = null;

    /**
     * Is ui enabled and ready ?
     */
    private transient boolean uiEnabled = false;

    /**
     * Nextcolor to play.
     */
    private transient float[] color_to_play = UI3DConst.COLOR_W;

    /**
     * Game timing.
     */
    private transient String current_game_time = "";

    /**
     * Is ui side check mate ?
     */
    private transient boolean ui_checkmate = false;

    /**
     * Is engine side check mate ?
     */
    private transient boolean engine_checkmate = false;

    /**
     * Is ui undoing moves ?
     */
    private transient boolean undoingMoves = false;

    /**
     * Has engine finished moving ?
     */
    private transient boolean engine_moving = false;

    /**
     * Has engine finished moving ?
     */
    private transient boolean ui_moving = false;

    /**
     * Is ui side check ?
     */
    private transient boolean ui_check = false;

    /**
     * Is engine side check ?
     */
    private transient boolean engine_check = false;

    /**
     * Is engine searching ?
     */
    private transient boolean engine_searching = false;
    
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
            this.engine_oponent_color_str_value = restartGameDto.getUiColor();
            this.engine_color_str_value = restartGameDto.getUiColor().equals(UI3DConst.COLOR_W_STR_VALUE)
                    ? UI3DConst.COLOR_B_STR_VALUE : UI3DConst.COLOR_W_STR_VALUE;
        }

        final boolean uiPlayingWhites = this.engine_oponent_color_str_value.equals(UI3DConst.COLOR_W_STR_VALUE);
        uiHelper.r = uiPlayingWhites ? UI3DCoordinateConst.START_R_W : UI3DCoordinateConst.START_R_B;
        uiHelper.g = uiPlayingWhites ? UI3DCoordinateConst.START_G_W : UI3DCoordinateConst.START_G_B;
        this.engine_color = uiPlayingWhites ? UI3DConst.COLOR_B : UI3DConst.COLOR_W;
        this.engine_oponent_color = uiPlayingWhites ? UI3DConst.COLOR_W : UI3DConst.COLOR_B;
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
        return this.ui_check == false && this.engine_check == false;
    }

    /**
     * @return is there a checmate situation ?
     */
    public boolean noCheckmate() {
        return this.ui_checkmate == false && this.engine_checkmate == false;
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
            fileOutputStream = new FileOutputStream("data/game3d");
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
            fileInputStreamm = new FileInputStream("data/game3d");
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
        return black_square_color;
    }

    public void setBlackSquareColor(final float[] black_square_color) {
        this.black_square_color = black_square_color;
    }

    public float[] getWhiteSquareColor() {
        return white_square_color;
    }

    public void setWhiteSquareColor(final float[] white_square_color) {
        this.white_square_color = white_square_color;
    }
    
    public boolean isReloadingPreviousGame() {
        return reloading_previous_game;
    }

    public void setReloadingPreviousGame(final boolean reloading_previous_game) {
        this.reloading_previous_game = reloading_previous_game;
    }
    
    public MoveQueue getPreviousMoveQueue() {
        return this.previous_move_queue;
    }

    public void setPreviousMoveQueue(final MoveQueue previous_move_queue) {
        this.previous_move_queue = previous_move_queue;
    }

    public boolean isReloadPreviousGame() {
        return this.reload_previous_game;
    }

    public void setReloadPreviousGame(final boolean reload_previous_game) {
        this.reload_previous_game = reload_previous_game;
    }
    
    public boolean isDisplayHint() {
        return this.display_hint;
    }

    public void setDisplayHint(final boolean display_hint) {
        this.display_hint = display_hint;
    }

    public boolean isDisplayAllOutput() {
        return this.display_all_output;
    }

    public void setDisplayAllOutput(final boolean display_all_output) {
        this.display_all_output = display_all_output;
    }

    public boolean isEnableHints() {
        return this.enable_hints;
    }

    public void setEnableHints(final boolean enable_hints) {
        this.enable_hints = enable_hints;
    }

    public boolean isEngineCheckmate() {
        return this.engine_checkmate;
    }

    public void setEngineCheckmate(final boolean engine_checkmate) {
        this.engine_checkmate = engine_checkmate;
    }

    public boolean isUiCheck() {
        return this.ui_check;
    }

    public void setUiCheck(final boolean ui_check) {
        this.ui_check = ui_check;
    }

    public boolean isEngineCheck() {
        return this.engine_check;
    }

    public void setEngineCheck(final boolean engine_check) {
        this.engine_check = engine_check;
    }

    public float[] getEngineColor() {
        return this.engine_color;
    }

    public void setEngineColor(final float[] engine_color) {
        this.engine_color = engine_color;
    }

    public float[] getEngineOponentColor() {
        return this.engine_oponent_color;
    }

    public void setEngineOponentColor(final float[] engine_oponent_color) {
        this.engine_oponent_color = engine_oponent_color;
    }

    public String getEngineOponentColorStringValue() {
        return this.engine_oponent_color_str_value;
    }

    public void setEngineOponentColorStringValue(final String engine_oponent_color_str_value) {
        this.engine_oponent_color_str_value = engine_oponent_color_str_value;
    }

    public String getEngineColorStringValue() {
        return this.engine_color_str_value;
    }

    public void setEngineColorStringValue(final String engine_color_str_value) {
        this.engine_color_str_value = engine_color_str_value;
    }

    public String getCurrentGameTime() {
        return this.current_game_time;
    }

    public void setCurrentGameTime(final String current_game_time) {
        this.current_game_time = current_game_time;
    }

    public float[] getColorToPlay() {
        return this.color_to_play;
    }

    public void setColorToPlay(final float[] color_to_play) {
        this.color_to_play = color_to_play;
    }

    public boolean isUiCheckmate() {
        return this.ui_checkmate;
    }

    public void setUiCheckmate(final boolean ui_checkmate) {
        this.ui_checkmate = ui_checkmate;
    }

    public char getPawnPromotion() {
        return this.pawn_promotion;
    }

    public void setPawnPromotion(final char pawn_promotion) {
        this.pawn_promotion = pawn_promotion;
    }

    public float[] getBgColor() {
        return this.bg_color;
    }

    public void setBgColor(final float[] bg_color) {
        this.bg_color = bg_color;
    }

    public int getEngineSearchDepth() {
        return this.engine_search_depth;
    }

    public void setEngineSearchDepth(final int engine_search_depth) {
        this.engine_search_depth = engine_search_depth;
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
        return this.engine_moving;
    }

    public void setEngineMoving(final boolean engine_moving) {
        this.engine_moving = engine_moving;
    }

    public boolean isUiMoving() {
        return this.ui_moving;
    }

    public void setUiMoving(final boolean ui_moving) {
        this.ui_moving = ui_moving;
    }

    public long getInterMoveSleepTimeMs() {
        return this.inter_move_sleep_time_ms;
    }

    public void setInterMoveSleepTimeMs(final long inter_move_sleep_time_ms) {
        this.inter_move_sleep_time_ms = inter_move_sleep_time_ms;
    }

    public boolean isEngineSearching() {
        return engine_searching;
    }

    public void setEngineSearching(final boolean engine_searching) {
        this.engine_searching = engine_searching;
    }
    //</editor-fold>

}
