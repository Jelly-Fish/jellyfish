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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers.texturing;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author thw
 */
public class ResizableSprite extends Sprite {
    /**
     * 
     */
    protected int deffinedHeight;
    
    /**
     * 
     */
    protected float deffinedWidth;
    
    /**
     * Constructor.
     * @param loader
     * @param ref 
     */
    public ResizableSprite(final TextureLoader loader, final String ref) {
        super(loader, ref);
        
        this.deffinedWidth = this.getWidth();
        this.deffinedHeight = this.getHeight();
    }
    
    /**
     * Draw the sprite at the specified location
     *
     * @param x The x location at which to draw this sprite
     * @param y The y location at which to draw this sprite
     */
    @Override
    public void draw(int x, int y) {
        // store the current model matrix
        GL11.glPushMatrix();

        // bind to the appropriate texture for this sprite
        texture.bind();

        // translate to the right location and prepare to draw
        GL11.glTranslatef(x, y, 0);

        // draw a quad textured to match the sprite
        GL11.glBegin(GL11.GL_QUADS); {
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, 0);

            GL11.glTexCoord2f(0, texture.getHeight());
            GL11.glVertex2f(0, height);

            GL11.glTexCoord2f(deffinedWidth, texture.getHeight());
            GL11.glVertex2f(deffinedWidth, height);

            GL11.glTexCoord2f(deffinedWidth, 0);
            GL11.glVertex2f(deffinedWidth, 0);
            
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        } GL11.glEnd();

        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }
    
    public float getDeffinedWidth() {
        return deffinedWidth;
    }

    public void setDeffinedWidth(final float deffinedWidth) {
        this.deffinedWidth = deffinedWidth;
    }
}
