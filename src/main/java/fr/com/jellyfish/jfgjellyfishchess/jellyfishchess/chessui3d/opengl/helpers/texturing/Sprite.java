/*******************************************************************************
 * Copyright (c) 2014, Thomas.H Warner.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors 
 * may be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 *******************************************************************************/

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers.texturing;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

/**
 * Implementation of sprite that uses an OpenGL quad and a texture to render a
 * given image to the screen.
 *
 * @author Kevin Glass
 * @author Brian Matzon
 */
public class Sprite {

    //<editor-fold defaultstate="collapsed" desc="vars">
    /**
     * The texture that stores the image for this sprite
     */
    protected Texture texture;

    /**
     * The width in pixels of this sprite
     */
    protected int width;

    /**
     * The height in pixels of this sprite
     */
    protected int height;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructors">
    /**
     * Create a new sprite from a specified image.
     *
     * @param loader the texture loader to use
     * @param ref A reference to the image on which this sprite should be based
     */
    public Sprite(final TextureLoader loader, final String ref) {
        try {
            texture = loader.getTexture(ref);
            width = texture.getImageWidth();
            height = texture.getImageHeight();
        } catch (final IOException ioe) {
            Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ioe);
            System.exit(-1);
        }
    }
    
    /**
     * Create a new sprite from a specified image.
     *
     * @param loader the texture loader to use
     * @param ref A reference to the image on which this sprite should be based
     */
    public Sprite(final TextureLoader loader, final File ref) {
        this(loader, ref.getPath());
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Get the width of this sprite in pixels
     *
     * @return The width of this sprite in pixels
     */
    public int getWidth() {
        return texture.getImageWidth();
    }

    /**
     * Get the height of this sprite in pixels
     *
     * @return The height of this sprite in pixels
     */
    public int getHeight() {
        return texture.getImageHeight();
    }

    /**
     * Draw the sprite at the specified location
     *
     * @param x The x location at which to draw this sprite
     * @param y The y location at which to draw this sprite
     */
    public void draw(int x, int y) {
        // store the current model matrix
        GL11.glPushMatrix();

        // bind to the appropriate texture for this sprite
        texture.bind();

        // translate to the right location and prepare to draw
        GL11.glTranslatef(x, y, 0.0f);

        // draw a quad textured to match the sprite
        GL11.glBegin(GL11.GL_QUADS); {
            GL11.glTexCoord2f(0.0f, 0.0f);
            GL11.glVertex2f(0.0f, 0.0f);

            GL11.glTexCoord2f(0.0f, texture.getHeight());
            GL11.glVertex2f(0.0f, height);

            GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
            GL11.glVertex2f(width, height);

            GL11.glTexCoord2f(texture.getWidth(), 0.0f);
            GL11.glVertex2f(width, 0.0f);
            
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        } GL11.glEnd();

        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }
    
    /**
     * Draw the sprite at the specified location
     *
     * @param x The x location
     * @param y The y location
     * @param z The z location
     */
    public void draw(float x, float y, float z) {
        // store the current model matrix
        GL11.glPushMatrix();

        // bind to the appropriate texture for this sprite
        texture.bind();

        // translate to the right location and prepare to draw
        GL11.glTranslatef(x, y, z);

        // draw a quad textured to match the sprite
        GL11.glBegin(GL11.GL_QUADS); {
        
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(x, y, z);

            GL11.glTexCoord2f(0, texture.getHeight());
            GL11.glVertex3f(x, height, z);

            GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
            GL11.glVertex3f(width, height, z);

            GL11.glTexCoord2f(texture.getWidth(), 0);
            GL11.glVertex3f(width, y, z);
            
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        } GL11.glEnd();

        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }
    
    /**
     * Test for drawing sprites in a glBegin context.
     * 
     * @param x
     * @param y
     * @param z 
     */
    public void paint(float x, float y, float z) {
        
        /***** GL11 config : **************************************************/
        /*GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glClearDepth(1);
        GL11.glDisable(GL11.GL_COLOR_MATERIAL);*/
        /**********************************************************************/
        
        texture.bind();
        
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(x, y, z);

        GL11.glTexCoord2f(0, 0.25f);
        GL11.glVertex3f(x, y + 0.25f, z);

        GL11.glTexCoord2f(0.25f, 0.25f);
        GL11.glVertex3f(x + 0.25f, y + 0.25f, z);

        GL11.glTexCoord2f(0.25f, 0);
        GL11.glVertex3f(x + 0.25f, y, z);
        
        /***** GL11 config : **************************************************/
        /*GL11.glAlphaFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);*/
        /**********************************************************************/
        
    }
    //</editor-fold>
    
}
