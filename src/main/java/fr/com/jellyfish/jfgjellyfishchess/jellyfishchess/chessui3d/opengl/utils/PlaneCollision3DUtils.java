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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils;

import java.awt.Rectangle;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author thw
 */
public class PlaneCollision3DUtils {

    public static final float ONE_UNIT = 1.0f;
    public static final float HALF_UNIT = 0.5f;
    public static final float QUARTER_UNIT = 0.25f;
    public static final float THREE_QUARTER_UNIT = 0.75f;
    
    public static boolean inCollision(final Vector3f point, final Vector3f[] vertexes,
            final float maxY) {

        if (point.y > maxY) {
            return false;
        }

        final Rectangle r = new Rectangle((int) vertexes[0].x, (int) vertexes[0].z, 1, 1);
        return r.intersects(point.x, point.z, 0.1, 0.1);
    }
    
    public static boolean inCollision(final Vector3f point, final Vector3f[] vertexes) {

        final Rectangle r = new Rectangle((int) vertexes[0].x, (int) vertexes[0].z, 1, 1);
        return r.intersects(point.x, point.z, 0.1, 0.1);
    }

}
