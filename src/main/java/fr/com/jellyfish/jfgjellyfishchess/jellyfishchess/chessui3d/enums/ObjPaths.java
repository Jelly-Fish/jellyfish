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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.constants.UI3DConst;

/**
 *
 * @author thw
 */
public enum ObjPaths {
    
    q("src/main/resources/models/queen.obj"),
    b("src/main/resources/models/bishop.obj"),
    nBlack("src/main/resources/models/knightb.obj"),
    nWhite("src/main/resources/models/knightw.obj"),
    r("src/main/resources/models/rook.obj");
    
    /**
     * .obj file path.
     */
    private final String path;

    private ObjPaths(final String path) {
        this.path = path;
    }
    
    /**
     * Return .obj path depending on fen char value and color.
     * @param t
     * @param color
     * @return 
     */
    public static String get(final char t, final String color) {
    
        switch(t) {
            case 'q':
                return q.getPath();
            case 'b':
                return b.getPath();
            case 'r':
                return r.getPath();
            case 'n':
                return color.equals(UI3DConst.COLOR_B_STR_VALUE) ? nBlack.getPath() : nWhite.getPath();
            default:
                return q.getPath();
        }
    }
    
    public String getPath() {
        return path;
    }
    
}
