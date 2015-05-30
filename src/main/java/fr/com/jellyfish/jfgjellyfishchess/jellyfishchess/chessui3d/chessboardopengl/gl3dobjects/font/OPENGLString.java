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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.font;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.FactorZEROException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thw
 */
public class OPENGLString {
    
    /**
     * 
     */
    public final OPENGLCharacter[] value;
    
    /**
     * Length of this OPENGLString.
     */
    public final int length;
    
    /**
     * Left margin for char layout.
     */
    private final float left_margin;
    
    /**
     * Color to draw string in.
     */
    private float[] color = null;
    
    /**
     * Constructor 1.
     * @param leftMargin
     * @param value 
     */
    public OPENGLString(final float leftMargin, final OPENGLCharacter ... value) {
        this.value = value;
        this.length = this.value.length;
        this.left_margin = leftMargin;
    }
    
    /**
     * constructor 1.
     * @param leftMargin
     * @param color
     * @param value 
     */
    public OPENGLString(final float leftMargin, final float[] color, final OPENGLCharacter ... value) {
        this(leftMargin, value);
        this.color = color;
    }

    /**
     * Draw this opengl string by call draw method on each char inthe string.
     * @param factor
     * @param xM
     * @param yM
     * @param zM 
     */
    public void drawString(final float factor, final float xM, final float yM, final float zM) {
        
        float m = 0.0f;
        this.color = this.color == null ? new float[] { 0.0f, 0.0f, 0.0f } : color;
        
        for (int i = 0; i < value.length; i++) {
            
            try {
                value[i].drawChar(factor, xM + m, yM, zM, this.color);
                m += left_margin;
            } catch (final FactorZEROException ex) {
                Logger.getLogger(OPENGLString.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
