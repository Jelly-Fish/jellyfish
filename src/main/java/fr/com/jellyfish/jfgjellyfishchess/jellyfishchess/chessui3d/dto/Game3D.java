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

/**
 *
 * @author thw
 */
public class Game3D {

    //<editor-fold defaultstate="collapsed" desc="vars">
    /**
     * Displya all engine output ?
     */
    private static boolean display_all_output = false;
    
    /**
     * Enable hints lauches infinite search query to engine on game layout. Once
     * stopped, the search result is sent back to
     * fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.driver.AbstractChessGameDriver
     * class for UI notification.
     */
    private static boolean enable_hints = true;
    
    /**
     * Display returned hint ?
     */
    private static boolean display_hint = false;

    /**
     * Engine predefined color.
     */
    private static float[] engine_color = UI3DConst.COLOR_B;

    /**
     * Engine oponent predefined color.
     */
    private static float[] engine_oponent_color = UI3DConst.COLOR_W;

    /**
     * Engine oponent predefined color string value.
     */
    private static String engine_oponent_color_str_value = "white";

    /**
     * Engine predefined color string value.
     */
    private static String engine_color_str_value = "black";

    /**
     * Game timing.
     */
    private static String current_game_time = "";

    /**
     * Nextcolor to play.
     */
    private static float[] color_to_play = UI3DConst.COLOR_W;

    /**
     * Is ui side check mate ?
     */
    private static boolean ui_checkmate = false;

    /**
     * Is engine side check mate ?
     */
    private static boolean engine_checkmate = false;

    /**
     * Is ui side check ?
     */
    private static boolean ui_check = false;

    /**
     * Is engine side check ?
     */
    private static boolean engine_check = false;

    /**
     * FEN pawn promotion value.
     */
    private static char pawn_promotion = 'q';

    /**
     * bg color.
     */
    private static float[] bg_color = UI3DConst.DEFAULT_BG_COLOR;

    /**
     * Engine search depth currently sent.
     */
    private static int engine_search_depth = 3;

    /**
     * Is ui enabled and ready ?
     */
    private static boolean uiEnabled = false;

    /**
     * Is ui undoing moves ?
     */
    private static boolean undoingMoves = false;

    /**
     * Has engine finished moving ?
     */
    private static boolean engine_moving = false;

    /**
     * Has engine finished moving ?
     */
    private static boolean ui_moving = false;

    /**
     * Wait time in ms between ui & engine moves.
     */
    private static long inter_move_sleep_time_ms = 280;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public static methods">
    /**
     * initialize all static variables.
     *
     * @param uiHelper
     * @param color
     */
    public static void initGame3DSettings(final OPENGLUIHelper uiHelper, final String color) {

        if (color.equals(UI3DConst.COLOR_W_STR_VALUE)) {
            uiHelper.r = UI3DCoordinateConst.START_R_W;
            uiHelper.g = UI3DCoordinateConst.START_G_W;
            Game3D.engine_color_str_value = UI3DConst.COLOR_B_STR_VALUE;
            Game3D.engine_oponent_color_str_value = UI3DConst.COLOR_W_STR_VALUE;
            Game3D.engine_color = UI3DConst.COLOR_B;
            Game3D.engine_oponent_color = UI3DConst.COLOR_W;
        } else {
            uiHelper.r = UI3DCoordinateConst.START_R_B;
            uiHelper.g = UI3DCoordinateConst.START_G_B;
            Game3D.engine_color_str_value = UI3DConst.COLOR_W_STR_VALUE;
            Game3D.engine_oponent_color_str_value = UI3DConst.COLOR_B_STR_VALUE;
            Game3D.engine_color = UI3DConst.COLOR_W;
            Game3D.engine_oponent_color = UI3DConst.COLOR_B;
        }
    }

    /**
     * Get char value w or b depending on param1 value.
     *
     * @param value String value of color (white or black).
     * @return char w = white, b = black
     */
    public static char getCharValue(final String value) {
        return value.equals(UI3DConst.COLOR_B_STR_VALUE) ? 'b' : 'w';
    }

    /**
     * @return is there a checksituation ?
     */
    public static boolean noCheck() {
        return Game3D.ui_check == false && Game3D.engine_check == false;
    }

    /**
     * @return is there a checmate situation ?
     */
    public static boolean noCheckmate() {
        return Game3D.ui_checkmate == false && Game3D.engine_checkmate == false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Accessors">
    public static boolean isDisplayHint() {
        return display_hint;
    }

    public static void setDisplayHint(boolean display_hint) {
        Game3D.display_hint = display_hint;
    }
    
    public static boolean isDisplayAllOutput() {
        return display_all_output;
    }

    public static void setDisplayAllOutput(boolean display_all_output) {
        Game3D.display_all_output = display_all_output;
    }
    
    public static boolean isEnableHints() {
        return enable_hints;
    }

    public static void setEnableHints(final boolean enable_hints) {
        Game3D.enable_hints = enable_hints;
    }

    public static boolean isEngineCheckmate() {
        return engine_checkmate;
    }

    public static void setEngineCheckmate(final boolean engine_checkmate) {
        Game3D.engine_checkmate = engine_checkmate;
    }

    public static boolean isUiCheck() {
        return ui_check;
    }

    public static void setUiCheck(final boolean ui_check) {
        Game3D.ui_check = ui_check;
    }

    public static boolean isEngineCheck() {
        return engine_check;
    }

    public static void setEngineCheck(final boolean engine_check) {
        Game3D.engine_check = engine_check;
    }

    public static float[] getEngineColor() {
        return engine_color;
    }

    public static void setEngineColor(final float[] engine_color) {
        Game3D.engine_color = engine_color;
    }

    public static float[] getEngineOponentColor() {
        return engine_oponent_color;
    }

    public static void setEngineOponentColor(final float[] engine_oponent_color) {
        Game3D.engine_oponent_color = engine_oponent_color;
    }

    public static String getEngineOponentColorStringValue() {
        return engine_oponent_color_str_value;
    }

    public static void setEngineOponentColorStringValue(final String engine_oponent_color_str_value) {
        Game3D.engine_oponent_color_str_value = engine_oponent_color_str_value;
    }

    public static String getEngineColorStringValue() {
        return engine_color_str_value;
    }

    public static void setEngineColorStringValue(final String engine_color_str_value) {
        Game3D.engine_color_str_value = engine_color_str_value;
    }

    public static String getCurrentGameTime() {
        return current_game_time;
    }

    public static void setCurrentGameTime(final String current_game_time) {
        Game3D.current_game_time = current_game_time;
    }

    public static float[] getColorToPlay() {
        return color_to_play;
    }

    public static void setColorToPlay(final float[] color_to_play) {
        Game3D.color_to_play = color_to_play;
    }

    public static boolean isUiCheckmate() {
        return ui_checkmate;
    }

    public static void setUiCheckmate(final boolean ui_checkmate) {
        Game3D.ui_checkmate = ui_checkmate;
    }

    public static char getPawnPromotion() {
        return pawn_promotion;
    }

    public static void setPawnPromotion(final char pawn_promotion) {
        Game3D.pawn_promotion = pawn_promotion;
    }

    public static float[] getBgColor() {
        return bg_color;
    }

    public static void setBgColor(final float[] bg_color) {
        Game3D.bg_color = bg_color;
    }

    public static int getEngineSearchDepth() {
        return engine_search_depth;
    }

    public static void setEngineSearchDepth(final int engine_search_depth) {
        Game3D.engine_search_depth = engine_search_depth;
    }

    public static boolean isUiEnabled() {
        return uiEnabled;
    }

    public static void setUiEnabled(final boolean uiEnabled) {
        Game3D.uiEnabled = uiEnabled;
    }

    public static boolean isUndoingMoves() {
        return undoingMoves;
    }

    public static void setUndoingMoves(final boolean undoingMoves) {
        Game3D.undoingMoves = undoingMoves;
    }

    public static boolean isEngineMoving() {
        return engine_moving;
    }

    public static void setEngineMoving(final boolean engine_moving) {
        Game3D.engine_moving = engine_moving;
    }

    public static boolean isUiMoving() {
        return ui_moving;
    }

    public static void setUiMoving(final boolean ui_moving) {
        Game3D.ui_moving = ui_moving;
    }

    public static long getInterMoveSleepTimeMs() {
        return inter_move_sleep_time_ms;
    }

    public static void setInterMoveSleepTimeMs(final long inter_move_sleep_time_ms) {
        Game3D.inter_move_sleep_time_ms = inter_move_sleep_time_ms;
    }
    //</editor-fold>

}
