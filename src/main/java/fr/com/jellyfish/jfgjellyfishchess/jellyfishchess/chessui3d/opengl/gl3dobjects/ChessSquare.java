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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.PlaneCollision3DUtils;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author thw
 */
public class ChessSquare extends AbstractOPENGL3DObject {

    //<editor-fold defaultstate="collapsed" desc="vars">
    /**
     * true if chess square has collided with mouse input on click.
     */
    private boolean colliding = false;

    /**
     * Chess position value.
     *
     * @see ChessPositions enum in enums package.
     */
    public final ChessPositions CHESS_POSITION;

    /**
     * Chess piece as model on this square. Null if chess square is empty.
     */
    private Model model = null;

    /**
     * Model's display list for rendering methodse.
     */
    private int modelDisplayList;

    /**
     * .obj file path.
     */
    private String modelObjPath = null;

    /**
     * Is this check square a check situation square ?
     */
    private boolean checkSquare = false;

    /**
     * Is this check square a checkmate situation square ?
     */
    private boolean checkmateSquare = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * @param quads
     * @param color
     * @param normals
     * @param chessPosition
     */
    public ChessSquare(final Vector3f[] quads, final float[] color, final float[] normals,
            final ChessPositions chessPosition) {
        super(quads, color, normals);
        this.CHESS_POSITION = chessPosition;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Return true if vertor collides with this vertexes.
     *
     * @param vector
     * @return in or out of collision with mouse click coordinates.
     */
    public boolean collidesWith(final Vector3f vector) {
        colliding = PlaneCollision3DUtils.inCollision(vector, vertexs);
        return colliding;
    }

    /**
     * Is this Model null ?
     *
     * @return boolean
     */
    public boolean hasModel() {
        return this.model != null;
    }

    @Override
    public void appendColor() {

        if (this.checkSquare && !this.checkmateSquare) {
            GL11.glColor3f(
                    UI3DConst.CHECK_SQUARE_COLOR[0],
                    UI3DConst.CHECK_SQUARE_COLOR[1],
                    UI3DConst.CHECK_SQUARE_COLOR[2]
            );
            return;
        }

        GL11.glColor3f(color[0], color[1], color[2]);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter & setters">
    public boolean isCheckmateSquare() {
        return checkmateSquare;
    }

    public void setCheckmateSquare(final boolean checkmateSquare) {
        this.checkmateSquare = checkmateSquare;
    }

    public boolean isCheckSquare() {
        return checkSquare;
    }

    public void setCheckSquare(final boolean checkSquare) {
        this.checkSquare = checkSquare;
    }

    public void setModelDisplayList(final int modelDisplayList) {
        this.modelDisplayList = modelDisplayList;
    }

    public void updateColor(final float[] color) {
        this.color = color;
    }

    public void updateColor(final Color color) {
        this.color = ColorUtils.color(color);
    }

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(final boolean colliding) {
        this.colliding = colliding;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(final Model model) {
        this.model = model;
    }

    public int getModelDisplayList() {
        return modelDisplayList;
    }

    public boolean isOccupied() {
        return this.model != null;
    }

    public String getModelObjPath() {
        return modelObjPath;
    }

    public void setModelObjPath(final String modelObjPath) {
        this.modelObjPath = modelObjPath;
    }
    //</editor-fold>

}
