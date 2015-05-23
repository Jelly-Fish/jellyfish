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
 * ****************************************************************************
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.ModelLoaderUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPieces;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.ErroneousChessPositionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author thw
 */
public class ChessBoard extends AbstractOPENGL3DObject {

    //<editor-fold defaultstate="collapsed" desc="private static vars">
    /**
     * Board vertexes.
     */
    private final static Vector3f[] boardVertexes = new Vector3f[]{
        new Vector3f(4.0f, 0.50f, 4.0f),
        new Vector3f(-4.0f, 0.50f, 4.0f),
        new Vector3f(-4.0f, 0.40f, 4.0f),
        new Vector3f(4.0f, 0.40f, 4.0f),
        new Vector3f(4.0f, 0.40f, -4.0f),
        new Vector3f(-4.0f, 0.40f, -4.0f),
        new Vector3f(-4.0f, 0.50f, -4.0f),
        new Vector3f(4.0f, 0.50f, -4.0f),
        new Vector3f(-4.0f, 0.50f, 4.0f),
        new Vector3f(-4.0f, 0.50f, -4.0f),
        new Vector3f(-4.0f, 0.40f, -4.0f),
        new Vector3f(-4.0f, 0.40f, 4.0f),
        new Vector3f(4.0f, 0.50f, -4.0f),
        new Vector3f(4.0f, 0.50f, 4.0f),
        new Vector3f(4.0f, 0.40f, 4.0f),
        new Vector3f(4.0f, 0.40f, -4.0f),
        new Vector3f(4.0f, 0.40f, 4.0f),
        new Vector3f(-4.0f, 0.40f, 4.0f),
        new Vector3f(-4.0f, 0.40f, -4.0f),
        new Vector3f(4.0f, 0.40f, -4.0f)
    };

    /**
     * Color applied to quad in a glBegin context.
     */
    private final static float[] quadColor = new float[]{0.2f, 0.2f, 0.2f};

    /**
     * Normals applied to quad in a glBegin context.
     */
    private final static float[] quadNormal = new float[]{0.0f, 1.0f, 0.0f};
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="private vars">
    /**
     * Hashmap of Chess positions mapped with chess squares, positions having a
     * String and Integer[2] values : A1/1,1 - A2/1,2 - H8/8,8 and so on.
     */
    private Map<ChessPositions, ChessSquare> squareMap = new HashMap<>();

    /**
     * Currently selected active square.
     */
    private ChessSquare selectedSquare = null;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     *
     * @param quads
     * @param color
     * @param normals
     */
    public ChessBoard(final Vector3f[] quads, final float[] color, final float[] normals) {
        super(ChessBoard.boardVertexes, ChessBoard.quadColor, ChessBoard.quadNormal);
        this.build();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Build board, squares, their positions and model layout for a new game.
     */
    private void build() {

        /**
         * Build squares:
         */
        float c = 0.85f;
        ChessSquare square = null;
        Vector3f[] vector;

        int x = 1, y = 8;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                vector = new Vector3f[4];
                vector[0] = new Vector3f(-4.0f + i, 0.50f, -4.0f + j);
                vector[1] = new Vector3f(-3.0f + i, 0.50f, -4.0f + j);
                vector[2] = new Vector3f(-3.0f + i, 0.50f, -3.0f + j);
                vector[3] = new Vector3f(-4.0f + i, 0.50f, -3.0f + j);

                try {
                    square = new ChessSquare(vector, new float[]{c, c, c}, new float[]{0.0f, -5.0f, 0.0f},
                            ChessPositions.get(x, y));
                    this.squareMap.put(square.CHESS_POSITION, square);
                } catch (final ErroneousChessPositionException ecpex) {
                    Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ecpex);
                    throw new RuntimeException();
                }

                if (j < 7) {
                    c = c == 0.85f ? 0.0f : 0.85f;
                }
                --y;
            }
            ++x;
            y = 8;
        }

        try {
            /**
             * Build models and affect to squares:
             */
            for (ChessPositions pos : UI3DConst.PAWN_LAYOUT_W) {
                this.squareMap.get(pos).setModel(
                        ModelLoaderUtils.loadModel(new File("src/main/resources/models/pawn.obj"), 
                                ChessPieces.P));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DConst.X_MARGIN,
                                    UI3DConst.Y_MARGIN,
                                    pos.zM() + UI3DConst.Z_MARGIN
                                }, UI3DConst.COLOR_W));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/pawn.obj");
            }

            for (ChessPositions pos : UI3DConst.PAWN_LAYOUT_B) {
                this.squareMap.get(pos).setModel(
                        ModelLoaderUtils.loadModel(new File("src/main/resources/models/pawn.obj"),
                                ChessPieces.p));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DConst.X_MARGIN,
                                    UI3DConst.Y_MARGIN,
                                    pos.zM() + UI3DConst.Z_MARGIN
                                }, UI3DConst.COLOR_B));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/pawn.obj");
            }

            for (ChessPositions pos : UI3DConst.ROOK_LAYOUT_W) {
                this.squareMap.get(pos).setModel(
                        ModelLoaderUtils.loadModel(new File("src/main/resources/models/rook.obj"),
                                ChessPieces.R));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DConst.X_MARGIN,
                                    UI3DConst.Y_MARGIN,
                                    pos.zM() + UI3DConst.Z_MARGIN
                                }, UI3DConst.COLOR_W));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/rook.obj");
            }

            for (ChessPositions pos : UI3DConst.ROOK_LAYOUT_B) {
                this.squareMap.get(pos).setModel(
                        ModelLoaderUtils.loadModel(new File("src/main/resources/models/rook.obj"),
                                ChessPieces.R));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DConst.X_MARGIN,
                                    UI3DConst.Y_MARGIN,
                                    pos.zM() + UI3DConst.Z_MARGIN
                                }, UI3DConst.COLOR_B));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/rook.obj");
            }

            for (ChessPositions pos : UI3DConst.KNIGHT_LAYOUT_W) {
                this.squareMap.get(pos).setModel(
                        ModelLoaderUtils.loadModel(new File("src/main/resources/models/knightw.obj"),
                                ChessPieces.N));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DConst.X_MARGIN,
                                    UI3DConst.Y_MARGIN,
                                    pos.zM() + UI3DConst.Z_MARGIN
                                }, UI3DConst.COLOR_W));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/knightw.obj");
            }

            for (ChessPositions pos : UI3DConst.KNIGHT_LAYOUT_B) {
                this.squareMap.get(pos).setModel(
                        ModelLoaderUtils.loadModel(new File("src/main/resources/models/knightb.obj"),
                                ChessPieces.n));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DConst.X_MARGIN,
                                    UI3DConst.Y_MARGIN,
                                    pos.zM() + UI3DConst.Z_MARGIN
                                }, UI3DConst.COLOR_B));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/knightb.obj");
            }

            for (ChessPositions pos : UI3DConst.BISHOP_LAYOUT_W) {
                this.squareMap.get(pos).setModel(
                        ModelLoaderUtils.loadModel(new File("src/main/resources/models/bishop.obj"),
                                ChessPieces.B));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DConst.X_MARGIN,
                                    UI3DConst.Y_MARGIN,
                                    pos.zM() + UI3DConst.Z_MARGIN
                                }, UI3DConst.COLOR_W));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/bishop.obj");
            }

            for (ChessPositions pos : UI3DConst.BISHOP_LAYOUT_B) {
                this.squareMap.get(pos).setModel(
                        ModelLoaderUtils.loadModel(new File("src/main/resources/models/bishop.obj"),
                                ChessPieces.b));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DConst.X_MARGIN,
                                    UI3DConst.Y_MARGIN,
                                    pos.zM() + UI3DConst.Z_MARGIN
                                }, UI3DConst.COLOR_B));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/bishop.obj");
            }

            this.squareMap.get(ChessPositions.D1).setModel(
                    ModelLoaderUtils.loadModel(new File("src/main/resources/models/queen.obj"),
                            ChessPieces.Q));
            this.squareMap.get(ChessPositions.D1).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(ChessPositions.D1).getModel(),
                            new float[]{
                                ChessPositions.D1.xM() + UI3DConst.X_MARGIN,
                                UI3DConst.Y_MARGIN,
                                ChessPositions.D1.zM() + UI3DConst.Z_MARGIN
                            }, UI3DConst.COLOR_W));
            this.squareMap.get(ChessPositions.D1).setModelObjPath("src/main/resources/models/queen.obj");
            this.squareMap.get(ChessPositions.D8).setModel(
                    ModelLoaderUtils.loadModel(new File("src/main/resources/models/queen.obj"),
                            ChessPieces.q));
            this.squareMap.get(ChessPositions.D8).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(ChessPositions.D8).getModel(),
                            new float[]{
                                ChessPositions.D8.xM() + UI3DConst.X_MARGIN,
                                UI3DConst.Y_MARGIN,
                                ChessPositions.D8.zM() + UI3DConst.Z_MARGIN
                            }, UI3DConst.COLOR_B));
            this.squareMap.get(ChessPositions.D8).setModelObjPath("src/main/resources/models/queen.obj");

            this.squareMap.get(ChessPositions.E1).setModel(
                    ModelLoaderUtils.loadModel(new File("src/main/resources/models/king.obj"),
                            ChessPieces.K));
            this.squareMap.get(ChessPositions.E1).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(ChessPositions.E1).getModel(),
                            new float[]{
                                ChessPositions.E1.xM() + UI3DConst.X_MARGIN,
                                UI3DConst.Y_MARGIN,
                                ChessPositions.E1.zM() + UI3DConst.Z_MARGIN
                            }, UI3DConst.COLOR_W));
            this.squareMap.get(ChessPositions.E1).setModelObjPath("src/main/resources/models/king.obj");
            this.squareMap.get(ChessPositions.E8).setModel(
                    ModelLoaderUtils.loadModel(new File("src/main/resources/models/king.obj"),
                            ChessPieces.k));
            this.squareMap.get(ChessPositions.E8).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(ChessPositions.E8).getModel(),
                            new float[]{
                                ChessPositions.E8.xM() + UI3DConst.X_MARGIN,
                                UI3DConst.Y_MARGIN,
                                ChessPositions.E8.zM() + UI3DConst.Z_MARGIN
                            }, UI3DConst.COLOR_B));
            this.squareMap.get(ChessPositions.E8).setModelObjPath("src/main/resources/models/king.obj");

        } catch (final IOException ex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param posFrom
     * @param posTo
     * @param elementColor
     */
    public void updateSquare(final ChessPositions posTo, final ChessPositions posFrom, final float[] elementColor) {

        try {
            final String path = this.squareMap.get(posFrom).getModelObjPath();
            this.squareMap.get(posTo).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(posFrom).getModel(),
                            new float[]{
                                posTo.xM() + UI3DConst.X_MARGIN,
                                UI3DConst.Y_MARGIN,
                                posTo.zM() + UI3DConst.Z_MARGIN
                            }, elementColor));

            this.squareMap.get(posTo).setModelObjPath(path);
            this.squareMap.get(posTo).setModel(this.squareMap.get(posFrom).getModel());
            this.squareMap.get(posFrom).setModel(null);

        } catch (final Exception ex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * @param pos
     * @param model
     */
    public void updateSquare(final ChessPositions pos, final OPENGLModel model) {

        final String path = this.squareMap.get(pos).getModelObjPath();
        this.squareMap.get(pos).setModelDisplayList(
                ModelLoaderUtils.createDisplayList(model,
                        new float[]{
                            pos.xM() + UI3DConst.X_MARGIN,
                            UI3DConst.Y_MARGIN,
                            pos.zM() + UI3DConst.Z_MARGIN
                        }, model.getColor()));

        this.squareMap.get(pos).setModelObjPath(path);
        this.squareMap.get(pos).setModel(model);
    }

    @Override
    public void paintVertexes() {

        for (ChessSquare s : this.squareMap.values()) {
            s.appendNormals();
            s.appendColor();
            s.paintVertexes();
            
            /* Alpha block : **************************************/
            if (s.hasAlpha()) {
                // FIXME : sprite is not working because of GL11.glEnable(GL11.GL_COLOR_MATERIAL)
                // in OPENGLUIHelper class initOPENGL() method. GL_COLOR_MATERIAL is used to 
                // avoid texturing. When drawing a sprite in glBegin context lighting is or seems
                // to be turned off... Turning off GL_COLOR_MATERIALS enables sprite draw but also
                // damages all rendering on non textured vertexes.
                //s.getAlphaEvent().draw();
            }
            /******************************************************/
            
        }

        this.appendColor();
        this.appendNormals();

        for (int i = 0; i < vertexs.length; i++) {
            GL11.glVertex3f(vertexs[i].x, vertexs[i].y, vertexs[i].z);
        }
    }

    /**
     * Reset all chess squares color to original color.
     *
     * @param excluded positions not to reset.
     */
    public void resetAllChessSquareBackgroundColors(final ChessPositions... excluded) {

        final List<String> values = new ArrayList<>();
        for (ChessPositions pos : excluded) {
            values.add(pos.getStrPositionValue());
        }

        for (Map.Entry<ChessPositions, ChessSquare> entry : squareMap.entrySet()) {
            if (!values.contains(entry.getKey().getStrPositionValue())) {
                entry.getValue().updateColor(entry.getValue().getOriginColor());
            }
        }
    }

    /**
     * Reset all chess squares color to original color.
     */
    public void resetAllChessSquareBackgroundColors() {

        for (Map.Entry<ChessPositions, ChessSquare> entry : squareMap.entrySet()) {
            entry.getValue().updateColor(entry.getValue().getOriginColor());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter & setters">
    public Map<ChessPositions, ChessSquare> getSquareMap() {
        return squareMap;
    }

    public ChessSquare getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelectedSquare(ChessSquare selectedSquare) {
        this.selectedSquare = selectedSquare;
    }

    //</editor-fold>
}
