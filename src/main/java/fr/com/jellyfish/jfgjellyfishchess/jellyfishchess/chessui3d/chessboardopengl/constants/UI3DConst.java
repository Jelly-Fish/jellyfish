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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.constants;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thw
 */
public class UI3DConst {

    public static final float Y_MARGIN = 0.50f;
    public static final float X_MARGIN = 0.50f;
    public static final float Z_MARGIN = 0.50f;

    public static final float[] COLOR_W = new float[]{0.9f, 0.9f, 0.88f};
    public static final float[] COLOR_B = new float[]{0.11f, 0.1f, 0.088f};

    public static final float[] DEFAULT_BG_COLOR = new float[]{0.8901f, 0.8392f, 0.7568f, 0.0f};

    public static final float[] ENGINE_MOVE_SQUARE_COLOR = ColorUtils.color(new Color(51, 204, 204));
    public static final float[] UI_MOVE_SQUARE_COLOR = ColorUtils.color(new Color(255, 204, 0));

    public static final String COLOR_W_STR_VALUE = "white";
    public static final String COLOR_B_STR_VALUE = "black";

    /**
     *
     */
    public static final float[] CHECKMATE_SQUARE_COLOR = ColorUtils.color(new Color(255, 0, 0));
    
    /**
     *
     */
    public static final float[] CHECK_SQUARE_COLOR = ColorUtils.color(new Color(255, 102, 0));

    /**
     * Zoom in max value.
     */
    public static final float MAX_ZOOM_IN = -10.0f;

    /**
     * Zoom out max value.
     */
    public static final float MAX_ZOOM_OUT = -23.0f;

    /**
     * Start roll playing whites.
     */
    public static final float START_R_W = -360.0f;

    /**
     * Start roll playing blacks.
     */
    public static final float START_R_B = -540.0f;

    /**
     * Start g playing whites.
     */
    public static final float START_G_W = -1145f;

    /**
     * Start g playing blacks.
     */
    public static final float START_G_B = -1020f;

    /**
     * Start zoom value.
     */
    public static final float START_ZOOM = -12.0f;

    /**
     * Translation speed.
     */
    public static final float TANSLATE_SPEED = 1.50f;

    public static final List<ChessPositions> PAWN_LAYOUT_W = new ArrayList<>();

    static {
        PAWN_LAYOUT_W.add(ChessPositions.A2);
        PAWN_LAYOUT_W.add(ChessPositions.B2);
        PAWN_LAYOUT_W.add(ChessPositions.C2);
        PAWN_LAYOUT_W.add(ChessPositions.D2);
        PAWN_LAYOUT_W.add(ChessPositions.E2);
        PAWN_LAYOUT_W.add(ChessPositions.F2);
        PAWN_LAYOUT_W.add(ChessPositions.G2);
        PAWN_LAYOUT_W.add(ChessPositions.H2);
    }

    public static final List<ChessPositions> PAWN_LAYOUT_B = new ArrayList<>();

    static {
        PAWN_LAYOUT_B.add(ChessPositions.A7);
        PAWN_LAYOUT_B.add(ChessPositions.B7);
        PAWN_LAYOUT_B.add(ChessPositions.C7);
        PAWN_LAYOUT_B.add(ChessPositions.D7);
        PAWN_LAYOUT_B.add(ChessPositions.E7);
        PAWN_LAYOUT_B.add(ChessPositions.F7);
        PAWN_LAYOUT_B.add(ChessPositions.G7);
        PAWN_LAYOUT_B.add(ChessPositions.H7);
    }

    public static final List<ChessPositions> ROOK_LAYOUT_W = new ArrayList<>();

    static {
        ROOK_LAYOUT_W.add(ChessPositions.A1);
        ROOK_LAYOUT_W.add(ChessPositions.H1);
    }

    public static final List<ChessPositions> ROOK_LAYOUT_B = new ArrayList<>();

    static {
        ROOK_LAYOUT_B.add(ChessPositions.A8);
        ROOK_LAYOUT_B.add(ChessPositions.H8);
    }

    public static final List<ChessPositions> KNIGHT_LAYOUT_W = new ArrayList<>();

    static {
        KNIGHT_LAYOUT_W.add(ChessPositions.B1);
        KNIGHT_LAYOUT_W.add(ChessPositions.G1);
    }

    public static final List<ChessPositions> KNIGHT_LAYOUT_B = new ArrayList<>();

    static {
        KNIGHT_LAYOUT_B.add(ChessPositions.B8);
        KNIGHT_LAYOUT_B.add(ChessPositions.G8);
    }

    public static final List<ChessPositions> BISHOP_LAYOUT_W = new ArrayList<>();

    static {
        BISHOP_LAYOUT_W.add(ChessPositions.C1);
        BISHOP_LAYOUT_W.add(ChessPositions.F1);
    }

    public static final List<ChessPositions> BISHOP_LAYOUT_B = new ArrayList<>();

    static {
        BISHOP_LAYOUT_B.add(ChessPositions.C8);
        BISHOP_LAYOUT_B.add(ChessPositions.F8);
    }

    public static final ChessPositions QUEEN_LAYOUT_W = ChessPositions.D1;
    public static final ChessPositions QUEEN_LAYOUT_B = ChessPositions.D8;
    public static final ChessPositions KING_LAYOUT_W = ChessPositions.E1;
    public static final ChessPositions KING_LAYOUT_B = ChessPositions.E8;

}
