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

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.FactorZEROException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author thw
 */
public class OPENGLCharacter {
    
    /**
     * Color of character.
     */
    private float[] color;
    
    /**
     * Char value.
     */
    private final GLCharValue value;
    
    /**
     * @param color
     * @param value 
     */
    public OPENGLCharacter(final float[] color, final char value) {
        this.color = color;
        this.value = GLCharValue.get(value);
    }
    
    /**
     * @param value 
     */
    public OPENGLCharacter(final char value) {
        this(new float[] { 0.0f, 0.0f, 0.0f }, value);
    }
    
    /**
     * @param factor
     * @param x
     * @param y
     * @param z 
     * @param color 
     * @throws fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.FactorZEROException 
     */
    public void drawChar(final float factor, final float x, final float y, final float z, final float[] color) 
            throws FactorZEROException {
        
        this.color = this.color == null ? new float[] { 0.0f, 0.0f, 0.0f } : color;
                
        if (factor == 0.0f) {
            throw new FactorZEROException(String.format(FactorZEROException.MESSAGE_1, factor));
        }
        
        GL11.glColor3f(this.color[0], this.color[1], this.color[2]);
        GL11.glBegin(GL11.GL_LINES); {
            final Vector3f[] v = value.getVertexes();
            for (int i = 0; i < v.length; i++) {
                GL11.glVertex3f((v[i].x / factor) + x, (v[i].y / factor) + y, z);
            }
        } GL11.glEnd();
    }
    
    /**
     * 
     */
    private enum GLCharValue {
    
        a('a', new Vector3f[] { 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.85f, 0.0f, 0.0f),
        }),
        
        b('b', new Vector3f[] { 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.90f, 0.0f), 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
        }),
        
        c('c', new Vector3f[] { 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
        }),
        
        d('d', new Vector3f[] { 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f), 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
        }),
        
        e('e', new Vector3f[] { 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.25f, 0.0f),
            new Vector3f(0.75f, 0.25f, 0.0f),
            new Vector3f(0.25f, 0.25f, 0.0f),
        }),
        
        f('f', new Vector3f[] { 
            new Vector3f(0.35f, 0.0f, 0.0f),
            new Vector3f(0.35f, 0.90f, 0.0f),
            new Vector3f(0.35f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),  
            new Vector3f(0.25f, 0.60f, 0.0f),
            new Vector3f(0.75f, 0.60f, 0.0f),
        }),
        
        g('g', new Vector3f[] { 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, -0.30f, 0.0f),
            new Vector3f(0.75f, -0.30f, 0.0f),
            new Vector3f(0.25f, -0.30f, 0.0f),
        }),
        
        h('h', new Vector3f[] { 
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.90f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
        }),
        
        one('1', new Vector3f[] { 
            new Vector3f(0.25f, 0.90f, 0.0f),
            new Vector3f(0.60f, 0.90f, 0.0f),
            new Vector3f(0.60f, 0.90f, 0.0f),
            new Vector3f(0.60f, 0.0f, 0.0f),
        }),
        
        two('2', new Vector3f[] { 
            new Vector3f(0.25f, 0.90f, 0.0f), 
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.45f, 0.0f),
            new Vector3f(0.75f, 0.45f, 0.0f),
            new Vector3f(0.25f, 0.45f, 0.0f),
            new Vector3f(0.25f, 0.45f, 0.0f),
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
        }),
        
        three('3', new Vector3f[] { 
            new Vector3f(0.25f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.45f, 0.0f),
            new Vector3f(0.25f, 0.45f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.0f, 0.0f),
            
        }),
        
        four('4', new Vector3f[] { 
            new Vector3f(0.25f, 0.45f, 0.0f),
            new Vector3f(0.60f, 0.90f, 0.0f),
            new Vector3f(0.60f, 0.90f, 0.0f),
            new Vector3f(0.60f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.45f, 0.0f),
            new Vector3f(0.75f, 0.45f, 0.0f),
        }),
        
        five('5', new Vector3f[] { 
            new Vector3f(0.25f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.25f, 0.90f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.25f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.50f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.75f, 0.0f, 0.0f),
            new Vector3f(0.25f, 0.0f, 0.0f),
        }),
        
        six('6', new Vector3f[] { 
            new Vector3f(0.25f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f), 
            new Vector3f(0.25f, 0.90f, 0.0f), 
            new Vector3f(0.25f, 0.0f, 0.0f), 
            new Vector3f(0.25f, 0.0f, 0.0f), 
            new Vector3f(0.75f, 0.0f, 0.0f), 
            new Vector3f(0.75f, 0.0f, 0.0f), 
            new Vector3f(0.75f, 0.45f, 0.0f), 
            new Vector3f(0.75f, 0.45f, 0.0f), 
            new Vector3f(0.25f, 0.45f, 0.0f), 
        }),
        
        seven('7', new Vector3f[] { 
            new Vector3f(0.25f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.35f, 0.0f, 0.0f),
        }),
        
        eight('8', new Vector3f[] { 
            new Vector3f(0.25f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f), 
            new Vector3f(0.75f, 0.90f, 0.0f), 
            new Vector3f(0.25f, 0.0f, 0.0f), 
            new Vector3f(0.25f, 0.0f, 0.0f), 
            new Vector3f(0.75f, 0.0f, 0.0f), 
            new Vector3f(0.75f, 0.0f, 0.0f), 
            new Vector3f(0.75f, 0.45f, 0.0f), 
            new Vector3f(0.75f, 0.45f, 0.0f), 
            new Vector3f(0.72f, 0.45f, 0.0f),
            new Vector3f(0.75f, 0.90f, 0.0f),
            new Vector3f(0.75f, 0.45f, 0.0f), 
        });
        
        /**
         * true char value.
         */
        private final char value;
        
        /**
         * array of vertexes.
         */
        private final Vector3f[] vertexes;
        
        /**
         * @param value 
         */
        private GLCharValue(final char value, final Vector3f[] vertexes) {
            this.value = value;
            this.vertexes = vertexes;
        }
        
        /**
         * @return char
         */
        public char getChar() {
            return this.value;
        }
        
        /**
         * @return Vector3f[]
         */
        public Vector3f[] getVertexes() {
            return this.vertexes;
        }
        
        /**
         * 
         * @param c
         * @return 
         */
        public static GLCharValue get(final char c) {
            
            for (GLCharValue val : GLCharValue.values()) {
                if (val.getChar() == c) {
                    return val;
                }
            }
            
            return null;
        }
        
    }
    
}
