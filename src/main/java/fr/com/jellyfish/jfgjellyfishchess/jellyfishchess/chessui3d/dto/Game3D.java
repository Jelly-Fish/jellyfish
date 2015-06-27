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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers.OPENGLUIHelper;

/**
 *
 * @author thw
 */
public class Game3D {
    
    //<editor-fold defaultstate="collapsed" desc="vars">
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
    private static int engine_search_depth = 2;
    
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
     * @param uiHelper
     * @param color 
     */
    public static void initGame3DSettings(final OPENGLUIHelper uiHelper, final String color) {
        
        if (color.equals(UI3DConst.COLOR_W_STR_VALUE)) {
            uiHelper.r = UI3DConst.START_R_W;
            uiHelper.g = UI3DConst.START_G_W;
            Game3D.engine_color_str_value = UI3DConst.COLOR_B_STR_VALUE;
            Game3D.engine_oponent_color_str_value = UI3DConst.COLOR_W_STR_VALUE;
            Game3D.engine_color = UI3DConst.COLOR_B;
            Game3D.engine_oponent_color = UI3DConst.COLOR_W;
        } else {
            uiHelper.r = UI3DConst.START_R_B;
            uiHelper.g = UI3DConst.START_G_B;
            Game3D.engine_color_str_value = UI3DConst.COLOR_W_STR_VALUE;
            Game3D.engine_oponent_color_str_value = UI3DConst.COLOR_B_STR_VALUE;
            Game3D.engine_color = UI3DConst.COLOR_W;
            Game3D.engine_oponent_color = UI3DConst.COLOR_B;
        }
    }
    
    /**
     * Get char value w or b depending on param1 value.
     * @param value String value of color (white or black).
     * @return char w = white, b = black
     */
    public static char getCharValue(final String value) {
        return value.equals(UI3DConst.COLOR_B_STR_VALUE) ? 'b' : 'w';
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Accessors">
    public static float[] getEngine_color() {
        return engine_color;
    }

    public static void setEngine_color(float[] engine_color) {
        Game3D.engine_color = engine_color;
    }

    public static float[] getEngine_oponent_color() {
        return engine_oponent_color;
    }

    public static void setEngine_oponent_color(float[] engine_oponent_color) {
        Game3D.engine_oponent_color = engine_oponent_color;
    }

    public static String getEngine_oponent_color_str_value() {
        return engine_oponent_color_str_value;
    }

    public static void setEngine_oponent_color_str_value(String engine_oponent_color_str_value) {
        Game3D.engine_oponent_color_str_value = engine_oponent_color_str_value;
    }

    public static String getEngine_color_str_value() {
        return engine_color_str_value;
    }

    public static void setEngine_color_str_value(String engine_color_str_value) {
        Game3D.engine_color_str_value = engine_color_str_value;
    }

    public static String getCurrent_game_time() {
        return current_game_time;
    }

    public static void setCurrent_game_time(String current_game_time) {
        Game3D.current_game_time = current_game_time;
    }

    public static float[] getColor_to_play() {
        return color_to_play;
    }

    public static void setColor_to_play(float[] color_to_play) {
        Game3D.color_to_play = color_to_play;
    }

    public static boolean isUi_checkmate() {
        return ui_checkmate;
    }

    public static void setUi_checkmate(boolean ui_checkmate) {
        Game3D.ui_checkmate = ui_checkmate;
    }

    public static char getPawn_promotion() {
        return pawn_promotion;
    }

    public static void setPawn_promotion(char pawn_promotion) {
        Game3D.pawn_promotion = pawn_promotion;
    }

    public static float[] getBg_color() {
        return bg_color;
    }

    public static void setBg_color(float[] bg_color) {
        Game3D.bg_color = bg_color;
    }

    public static int getEngine_search_depth() {
        return engine_search_depth;
    }

    public static void setEngine_search_depth(int engine_search_depth) {
        Game3D.engine_search_depth = engine_search_depth;
    }

    public static boolean isUiEnabled() {
        return uiEnabled;
    }

    public static void setUiEnabled(boolean uiEnabled) {
        Game3D.uiEnabled = uiEnabled;
    }

    public static boolean isUndoingMoves() {
        return undoingMoves;
    }

    public static void setUndoingMoves(boolean undoingMoves) {
        Game3D.undoingMoves = undoingMoves;
    }

    public static boolean isEngine_moving() {
        return engine_moving;
    }

    public static void setEngine_moving(boolean engine_moving) {
        Game3D.engine_moving = engine_moving;
    }

    public static boolean isUi_moving() {
        return ui_moving;
    }

    public static void setUi_moving(boolean ui_moving) {
        Game3D.ui_moving = ui_moving;
    }

    public static long getInter_move_sleep_time_ms() {
        return inter_move_sleep_time_ms;
    }

    public static void setInter_move_sleep_time_ms(long inter_move_sleep_time_ms) {
        Game3D.inter_move_sleep_time_ms = inter_move_sleep_time_ms;
    }
    //</editor-fold>
    
}
