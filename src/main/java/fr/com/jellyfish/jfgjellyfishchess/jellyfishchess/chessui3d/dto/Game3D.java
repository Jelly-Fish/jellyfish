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
    
    public static float[] engine_color = UI3DConst.COLOR_B;
    public static float[] engine_oponent_color = UI3DConst.COLOR_W;
    public static String engine_oponent_color_str_value = "white";
    public static String engine_color_str_value = "black";
    public static String current_game_time = "";
    public static float[] color_to_play = UI3DConst.COLOR_W;
    
    
    public static float[] bg_color = UI3DConst.DEFAULT_BG_COLOR;
    
    /**
     * Engine search depth currently sent.
     */
    public static int engine_search_depth = 2;
    
    /**
     * Is ui enabled and ready ?
     */
    public static boolean uiEnabled = false;
    
    /**
     * Is ui undoing moves ?
     */
    public static boolean undoingMoves = false;
    
    /**
     * Has engine finished moving ?
     */
    public static boolean engine_moving = false;
    
    /**
     * Has engine finished moving ?
     */
    public static boolean ui_moving = false;
    
    /**
     * Wait time in ms between ui & engine moves.
     */
    public static long inter_move_sleep_time_ms = 280;
    
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
    
}
