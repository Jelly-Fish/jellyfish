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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.interfaces.OPENGL3DPaintable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author thw
 */
abstract class AbstractOPENGL3DObject implements OPENGL3DPaintable {
    
    //<editor-fold defaultstate="collapsed" desc="private vars">
    /**
     * Vertexes.
     */
    final Vector3f[] vertexs;
    
    /**
     * Color.
     */
    float[] color;
    
    /**
     * First color to be set on this object.
     */
    float[] finalColor;
    
    /**
     * Normals.
     */
    final float[] normals;
        
    /**
     * Origin color. final field.
     */
    final float[] originColor;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="contructors">
    /**
     * 
     * @param quads
     * @param color
     * @param normals 
     */
    public AbstractOPENGL3DObject(final Vector3f[] quads, final float[] color, final float[] normals) {
        this.vertexs = quads;
        this.color = color;
        this.finalColor = color;
        this.normals = normals;
        this.originColor= color;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    @Override
    public void appendNormals() {
        GL11.glNormal3f(normals[0], normals[1], normals[2]); 
    }
    
    @Override
    public void appendColor() {
        GL11.glColor3f(color[0], color[1], color[2]);
    }
    
    @Override
    public void paintVertexes() {
        for (int i = 0; i < vertexs.length; i++) {
            GL11.glVertex3f(vertexs[i].x, vertexs[i].y, vertexs[i].z);
        }
    }
    
    @Override
    public float getR() {
        return this.color[0];
    }

    @Override
    public float getG() {
        return this.color[1];
    }

    @Override
    public float getB() {
        return this.color[2];
    }
    
    @Override
    public float getAlpha() {
        
        try {
            return this.color[3];
        } catch (final IndexOutOfBoundsException ioobex) {
            return 0.0f;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="accessors">
    public float[] getFinalColor() {
        return finalColor;
    }
    
    public Vector3f[] getVertexes() {
        return vertexs;
    }

    public float[] getColor() {
        return color;
    }

    public float[] getNormals() {
        return normals;
    }
    
    public void setColor(final float[] color) {
        this.color = color;
    }
    
    public void setFinalColor(float[] finalColor) {
        this.finalColor = finalColor;
    }
    
    public float[] getOriginColor() {
        return originColor;
    }

    //</editor-fold>
    
}
