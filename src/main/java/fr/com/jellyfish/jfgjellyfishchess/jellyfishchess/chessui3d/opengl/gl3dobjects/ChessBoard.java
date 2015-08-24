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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DCoordinateConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers.OPENGLUIDriver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.ModelLoaderUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Hint;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPiece;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.enums.ChessPositions;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.ErroneousChessPositionException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException;
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
    private final Map<ChessPositions, ChessSquare> squareMap;

    /**
     * Currently selected active square.
     */
    private ChessSquare selectedSquare = null;

    /**
     * Currently selected active square.
     */
    private ChessSquare checkSquare = null;

    /**
     * Driver instance.
     */
    private final OPENGLUIDriver driver;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor">
    /**
     * Constructor.
     *
     * @param quads
     * @param color
     * @param normals
     * @param driver
     * @param whiteSquareColor
     * @param blackSquareColor
     */
    public ChessBoard(final Vector3f[] quads, final float[] color, final float[] normals, final OPENGLUIDriver driver,
            final float[] whiteSquareColor, final float[] blackSquareColor) {
        
        super(ChessBoard.boardVertexes, ChessBoard.quadColor, ChessBoard.quadNormal);
        this.driver = driver;
        squareMap = new HashMap<>();
        
        this.build(whiteSquareColor, blackSquareColor);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="overriden methods">
    @Override
    public void paintVertexes() {

        for (ChessSquare s : this.squareMap.values()) {
            s.appendNormals();
            s.appendColor();
            s.paintVertexes();
        }

        this.appendColor();
        this.appendNormals();

        for (int i = 0; i < vertexs.length; i++) {
            GL11.glVertex3f(vertexs[i].x, vertexs[i].y, vertexs[i].z);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="methods">
    /**
     * Build board, squares & their positions.
     * 
     * @param whiteSquareColor
     * @param blackSquareColor 
     */
    public final void resetSquareColors(final float[] whiteSquareColor, final float[] blackSquareColor) {
        
        float[] c = whiteSquareColor;
        
        int x = 1, y = 8;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                
                try {
                    this.squareMap.get(ChessPositions.get(x, y)).resetColor(c);
                } catch (final ErroneousChessPositionException ecpex) {
                    Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ecpex);
                }
                
                if (j < 7) {
                    c = c == whiteSquareColor ? blackSquareColor : whiteSquareColor;
                }
                --y;
            }
            ++x;
            y = 8;
        }
    }
    
    /**
     * Build board, squares & their positions. Build model layout for a new game.
     * 
     * @param whiteSquareColor
     * @param blackSquareColor 
     */
    private void build(final float[] whiteSquareColor, final float[] blackSquareColor) {

        /**
         * Build squares:
         */
        float[] c = whiteSquareColor;
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
                    square = new ChessSquare(vector, c, new float[]{0.0f, -5.0f, 0.0f},
                            ChessPositions.get(x, y));
                    this.squareMap.put(square.CHESS_POSITION, square);
                } catch (final ErroneousChessPositionException ecpex) {
                    Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ecpex);
                    throw new RuntimeException();
                }

                if (j < 7) {
                    c = c == whiteSquareColor ? blackSquareColor : whiteSquareColor;
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
                this.squareMap.get(pos).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/pawn.obj"),
                        ChessPiece.P));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DCoordinateConst.X_MARGIN,
                                    UI3DCoordinateConst.Y_MARGIN,
                                    pos.zM() + UI3DCoordinateConst.Z_MARGIN
                                }, UI3DConst.COLOR_W));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/pawn.obj");
            }

            for (ChessPositions pos : UI3DConst.PAWN_LAYOUT_B) {
                this.squareMap.get(pos).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/pawn.obj"),
                        ChessPiece.p));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DCoordinateConst.X_MARGIN,
                                    UI3DCoordinateConst.Y_MARGIN,
                                    pos.zM() + UI3DCoordinateConst.Z_MARGIN
                                }, UI3DConst.COLOR_B));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/pawn.obj");
            }

            for (ChessPositions pos : UI3DConst.ROOK_LAYOUT_W) {
                this.squareMap.get(pos).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/rook.obj"),
                        ChessPiece.R));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DCoordinateConst.X_MARGIN,
                                    UI3DCoordinateConst.Y_MARGIN,
                                    pos.zM() + UI3DCoordinateConst.Z_MARGIN
                                }, UI3DConst.COLOR_W));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/rook.obj");
            }

            for (ChessPositions pos : UI3DConst.ROOK_LAYOUT_B) {
                this.squareMap.get(pos).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/rook.obj"),
                        ChessPiece.R));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DCoordinateConst.X_MARGIN,
                                    UI3DCoordinateConst.Y_MARGIN,
                                    pos.zM() + UI3DCoordinateConst.Z_MARGIN
                                }, UI3DConst.COLOR_B));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/rook.obj");
            }

            for (ChessPositions pos : UI3DConst.KNIGHT_LAYOUT_W) {
                this.squareMap.get(pos).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/knightw.obj"),
                        ChessPiece.N));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DCoordinateConst.X_MARGIN,
                                    UI3DCoordinateConst.Y_MARGIN,
                                    pos.zM() + UI3DCoordinateConst.Z_MARGIN
                                }, UI3DConst.COLOR_W));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/knightw.obj");
            }

            for (ChessPositions pos : UI3DConst.KNIGHT_LAYOUT_B) {
                this.squareMap.get(pos).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/knightb.obj"),
                        ChessPiece.n));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DCoordinateConst.X_MARGIN,
                                    UI3DCoordinateConst.Y_MARGIN,
                                    pos.zM() + UI3DCoordinateConst.Z_MARGIN
                                }, UI3DConst.COLOR_B));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/knightb.obj");
            }

            for (ChessPositions pos : UI3DConst.BISHOP_LAYOUT_W) {
                this.squareMap.get(pos).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/bishop.obj"),
                        ChessPiece.B));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DCoordinateConst.X_MARGIN,
                                    UI3DCoordinateConst.Y_MARGIN,
                                    pos.zM() + UI3DCoordinateConst.Z_MARGIN
                                }, UI3DConst.COLOR_W));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/bishop.obj");
            }

            for (ChessPositions pos : UI3DConst.BISHOP_LAYOUT_B) {
                this.squareMap.get(pos).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/bishop.obj"),
                        ChessPiece.b));
                this.squareMap.get(pos).setModelDisplayList(
                        ModelLoaderUtils.createDisplayList(this.squareMap.get(pos).getModel(),
                                new float[]{
                                    pos.xM() + UI3DCoordinateConst.X_MARGIN,
                                    UI3DCoordinateConst.Y_MARGIN,
                                    pos.zM() + UI3DCoordinateConst.Z_MARGIN
                                }, UI3DConst.COLOR_B));
                this.squareMap.get(pos).setModelObjPath("src/main/resources/models/bishop.obj");
            }

            this.squareMap.get(ChessPositions.D1).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/queen.obj"),
                    ChessPiece.Q));
            this.squareMap.get(ChessPositions.D1).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(ChessPositions.D1).getModel(),
                            new float[]{
                                ChessPositions.D1.xM() + UI3DCoordinateConst.X_MARGIN,
                                UI3DCoordinateConst.Y_MARGIN,
                                ChessPositions.D1.zM() + UI3DCoordinateConst.Z_MARGIN
                            }, UI3DConst.COLOR_W));
            this.squareMap.get(ChessPositions.D1).setModelObjPath("src/main/resources/models/queen.obj");
            this.squareMap.get(ChessPositions.D8).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/queen.obj"),
                    ChessPiece.q));
            this.squareMap.get(ChessPositions.D8).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(ChessPositions.D8).getModel(),
                            new float[]{
                                ChessPositions.D8.xM() + UI3DCoordinateConst.X_MARGIN,
                                UI3DCoordinateConst.Y_MARGIN,
                                ChessPositions.D8.zM() + UI3DCoordinateConst.Z_MARGIN
                            }, UI3DConst.COLOR_B));
            this.squareMap.get(ChessPositions.D8).setModelObjPath("src/main/resources/models/queen.obj");

            this.squareMap.get(ChessPositions.E1).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/king.obj"),
                    ChessPiece.K));
            this.squareMap.get(ChessPositions.E1).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(ChessPositions.E1).getModel(),
                            new float[]{
                                ChessPositions.E1.xM() + UI3DCoordinateConst.X_MARGIN,
                                UI3DCoordinateConst.Y_MARGIN,
                                ChessPositions.E1.zM() + UI3DCoordinateConst.Z_MARGIN
                            }, UI3DConst.COLOR_W));
            this.squareMap.get(ChessPositions.E1).setModelObjPath("src/main/resources/models/king.obj");
            this.squareMap.get(ChessPositions.E8).setModel(ModelLoaderUtils.loadModel(new File("src/main/resources/models/king.obj"),
                    ChessPiece.k));
            this.squareMap.get(ChessPositions.E8).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(ChessPositions.E8).getModel(),
                            new float[]{
                                ChessPositions.E8.xM() + UI3DCoordinateConst.X_MARGIN,
                                UI3DCoordinateConst.Y_MARGIN,
                                ChessPositions.E8.zM() + UI3DCoordinateConst.Z_MARGIN
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
    public void updateSquare(final ChessPositions posTo,
            final ChessPositions posFrom, final float[] elementColor) {

        try {

            /**
             * Prepare old display list for deletion.
             */
            this.driver.appendObsoleteDisplayList(this.squareMap.get(posFrom).getModelDisplayList());

            final String path = this.squareMap.get(posFrom).getModelObjPath();
            this.squareMap.get(posTo).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(posFrom).getModel(),
                            new float[]{
                                posTo.xM() + UI3DCoordinateConst.X_MARGIN,
                                UI3DCoordinateConst.Y_MARGIN,
                                posTo.zM() + UI3DCoordinateConst.Z_MARGIN
                            }, elementColor));

            this.squareMap.get(posTo).setModelObjPath(path);
            this.squareMap.get(posTo).setModel(this.squareMap.get(posFrom).getModel());
            this.squareMap.get(posFrom).setModel(null);

        } catch (final QueueCapacityOverflowException qcofex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, qcofex);
        }

    }

    /**
     * @param posFrom
     * @param posTo
     * @param elementColor
     * @param objPath
     * @param type
     */
    public void updateSquare(final ChessPositions posTo,
            final ChessPositions posFrom, final float[] elementColor,
            final String objPath, final ChessPiece type) {

        try {

            /**
             * Prepare old display list for deletion.
             */
            this.driver.appendObsoleteDisplayList(this.squareMap.get(posFrom).getModelDisplayList());

            this.squareMap.get(posTo).setModel(ModelLoaderUtils.loadModel(new File(objPath), type));

            this.squareMap.get(posTo).setModelDisplayList(
                    ModelLoaderUtils.createDisplayList(this.squareMap.get(posTo).getModel(),
                            new float[]{
                                posTo.xM() + UI3DCoordinateConst.X_MARGIN,
                                UI3DCoordinateConst.Y_MARGIN,
                                posTo.zM() + UI3DCoordinateConst.Z_MARGIN
                            }, elementColor));

            this.squareMap.get(posTo).setModelObjPath(objPath);
            this.squareMap.get(posFrom).setModel(null);

        } catch (final QueueCapacityOverflowException qcofex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, qcofex);
        } catch (final IOException ex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param posTo
     * @param posFrom
     * @param model
     * @param takenModel
     */
    public void updateSquare(final ChessPositions posTo, final ChessPositions posFrom,
            final Model model, final Model takenModel) {

        try {

            // Swap models:
            /**
             * Prepare old display list for deletion.
             */
            this.driver.appendObsoleteDisplayList(this.squareMap.get(posTo).getModelDisplayList());

            final String path1 = this.squareMap.get(posTo).getModelObjPath();
            this.squareMap.get(posTo).setModelDisplayList(ModelLoaderUtils.createDisplayList(model,
                    new float[]{
                        posTo.xM() + UI3DCoordinateConst.X_MARGIN,
                        UI3DCoordinateConst.Y_MARGIN,
                        posTo.zM() + UI3DCoordinateConst.Z_MARGIN
                    }, model.getColor()));

            this.squareMap.get(posTo).setModelObjPath(path1);
            this.squareMap.get(posTo).setModel(model);

            /**
             * Prepare old display list for deletion.
             */
            this.driver.appendObsoleteDisplayList(this.squareMap.get(posFrom).getModelDisplayList());
            final String path2 = this.squareMap.get(posFrom).getModelObjPath();
            this.squareMap.get(posFrom).setModelDisplayList(ModelLoaderUtils.createDisplayList(takenModel,
                    new float[]{
                        posFrom.xM() + UI3DCoordinateConst.X_MARGIN,
                        UI3DCoordinateConst.Y_MARGIN,
                        posFrom.zM() + UI3DCoordinateConst.Z_MARGIN
                    }, takenModel.getColor()));

            this.squareMap.get(posFrom).setModelObjPath(path2);
            this.squareMap.get(posFrom).setModel(takenModel);

        } catch (final QueueCapacityOverflowException qcofex) {
            Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, qcofex);
        }
    }

    /**
     * Reset all chess squares color to original color.
     *
     * @param excluded positions not to reset.
     */
    public void resetAllChessSquareBackgroundColors(final ChessPositions... excluded) {

        final List<String> values = new ArrayList<>();

        if (excluded != null) {
            for (ChessPositions pos : excluded) {
                values.add(pos.getStrPositionValue());
            }
        }

        for (Map.Entry<ChessPositions, ChessSquare> entry : squareMap.entrySet()) {
            if (!values.contains(entry.getKey().getStrPositionValue())) {
                entry.getValue().updateColor(entry.getValue().getFinalColor());
            }
        }
    }

    /**
     * Reset all chess squares color to original color.
     */
    public void resetAllChessSquareBackgroundColors() {

        for (Map.Entry<ChessPositions, ChessSquare> entry : squareMap.entrySet()) {
            entry.getValue().setCheckSquare(false);
            entry.getValue().setCheckmateSquare(false);
            entry.getValue().updateColor(entry.getValue().getOriginColor());
            entry.getValue().setFinalColor(entry.getValue().getOriginColor());
        }
    }

    /**
     * @param uiWhite
     * @param cp
     * @return
     */
    public boolean isEngineSideKing(final boolean uiWhite, final ChessPositions cp) {

        for (ChessSquare s : this.squareMap.values()) {
            if (s.CHESS_POSITION.isEqualTo(cp) && ColorUtils.floatArrayEqual(s.getModel().getColor(),
                    uiWhite ? UI3DConst.COLOR_B : UI3DConst.COLOR_W)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param cp
     * @return
     */
    public ChessSquare getSquare(final ChessPositions cp) {
        return this.squareMap.get(cp);
    }

    /**
     * Update king square color depending on check situations.
     *
     * @param king ChessPositions
     */
    public void updateKingSquareCheck(final ChessPositions king) {

        if (!Game3D.getInstance().noCheckmate()) {
            return;
        }

        if (Game3D.getInstance().noCheck() && this.checkSquare != null) {
            this.checkSquare.setCheckSquare(false);
            this.checkSquare = null;
            return;
        }

        if (king != null) {
            this.getSquare(king).setCheckSquare(true);
            this.checkSquare = this.getSquare(king);
        }
    }

    /**
     * Update white or black side king square for checkmate.
     * @param color
     */
    public void updateKingSquareCheckmate(final String color) {
        
        final boolean whiteSide = color.equals(UI3DConst.COLOR_W_STR_VALUE);
        
        for (Map.Entry<ChessPositions, ChessSquare> entry : this.squareMap.entrySet()) {
            
            if (entry.getValue().hasModel()
                    && ColorUtils.floatArrayEqual(entry.getValue().getModel().getColor(),
                            (whiteSide ? UI3DConst.COLOR_W : UI3DConst.COLOR_B))
                    && entry.getValue().getModel().getType().equals(
                            whiteSide ? ChessPiece.getWhiteKing() : ChessPiece.getBlackKing())) {
                entry.getValue().setCheckSquare(false);
                entry.getValue().setCheckmateSquare(true);
                entry.getValue().setColor(UI3DConst.CHECKMATE_SQUARE_COLOR);
                entry.getValue().setFinalColor(UI3DConst.CHECKMATE_SQUARE_COLOR);
                break;
            }
        }
    }

    /**
     * Set correct colors to selected, non-selected & in-check squares.
     */
    public void resetSquareColors() {

        for (Map.Entry<ChessPositions, ChessSquare> s : this.squareMap.entrySet()) {

            if (s.getKey().getStrPositionValue().equals(
                    this.getSelectedSquare().CHESS_POSITION.getStrPositionValue())) {

                s.getValue().setColor(UI3DConst.UI_MOVE_SQUARE_COLOR);
            } else {
                s.getValue().setColor(s.getValue().getFinalColor());
            }
        }

        this.updateKingSquareCheck(null);
    }
    
    /**
     * @param hint 
     */
    public void displayHint(final Hint hint) {
        this.squareMap.get(hint.getPosFrom()).setColor(UI3DConst.HINT_COLOR);
        this.squareMap.get(hint.getPosTo()).setColor(UI3DConst.HINT_COLOR);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter & setters">
    public Map<ChessPositions, ChessSquare> getSquareMap() {
        return squareMap;
    }

    public ChessSquare getSelectedSquare() {
        return selectedSquare;
    }

    public final void setSelectedSquare(final ChessSquare selectedSquare) {
        this.selectedSquare = selectedSquare;
    }
    //</editor-fold>

}
