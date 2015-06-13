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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers.texturing.Sprite;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers.texturing.TextureLoader;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.SpriteUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPiece;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thw
 */
public class AlphaEventSprite {
    
    //<editor-fold defaultstate="collapsed" desc="vars">
    /**
     * 
     */
    private static final float MAX_POLYGON_HEIGHT = 3.0f;
    
    /**
     * Y values depending on ChessPieces type.
     */
    private static final Map<ChessPiece, Float> polygon_height_values =
            new HashMap<>();
    static 
    {
        AlphaEventSprite.polygon_height_values.put(ChessPiece.p, 1.00f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.P, 1.00f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.r, 0.80f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.R, 0.80f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.n, 0.85f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.N, 0.85f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.b, 0.70f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.B, 0.70f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.q, 0.40f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.Q, 0.40f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.k, 0.40f);
        AlphaEventSprite.polygon_height_values.put(ChessPiece.K, 0.40f);
    }
    
    /**
     * ChessPosition ref for displaying.
     */
    private ChessPositions chessPosition = null;
    
    /**
     * Chess piece type.
     */
    private final ChessPiece type;
    
    /**
     * 
     */
    private Sprite sprite = null;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * constructor.
     * @param textureLoader
     * @param chessPosition 
     * @param type 
     */
    public AlphaEventSprite(final TextureLoader textureLoader, final ChessPositions chessPosition, 
            final ChessPiece type) {
        this.chessPosition = chessPosition;
        this.type = type;
        this.sprite = SpriteUtils.getSprite(textureLoader, 
                UIConst.JELLYFISH_ICON_16.replaceFirst("/", ""));
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * 
     */
    public void draw() {
        this.sprite.paint(4.5f, 1.0f, 5.0f);
    }
    //</editor-fold>
    
}
