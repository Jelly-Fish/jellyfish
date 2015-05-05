/*******************************************************************************
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
 * *****************************************************************************/

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

/**
 * @see http://stackoverflow.com/questions/22523189/lwjgl-get-mouse-coordinates-on-given-plane
 */
public class Location3DUtils {

    private final static IntBuffer viewport = BufferUtils.createIntBuffer(16);
    private final static FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
    private final static FloatBuffer projection = BufferUtils.createFloatBuffer(16);
    private final static FloatBuffer winZ = BufferUtils.createFloatBuffer(20);
    private final static FloatBuffer position = BufferUtils.createFloatBuffer(3);

    /**
     * @param mouseX
     * @param mouseY
     * @return Vector3f
     * @see http://stackoverflow.com/questions/22523189/lwjgl-get-mouse-coordinates-on-given-plane
     */
    static public Vector3f getMousePositionIn3dCoordinates(final int mouseX, final int mouseY) {

        viewport.clear();
        modelview.clear();
        projection.clear();
        winZ.clear();
        position.clear();
        float winX, winY;

        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        winX = (float) mouseX;
        winY = (float) mouseY;

        GL11.glReadPixels(mouseX, (int) winY, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);

        float zz = winZ.get();

        GLU.gluUnProject(winX, winY, zz, modelview, projection, viewport, position);

        Vector3f v = new Vector3f(position.get(0), position.get(1), position.get(2));

        return v;
    }

}
