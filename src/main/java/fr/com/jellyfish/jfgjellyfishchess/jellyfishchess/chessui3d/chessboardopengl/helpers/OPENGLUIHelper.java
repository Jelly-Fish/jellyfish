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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui.constants.UIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.ChessBoard;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.BufferUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.SoundUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.EngineMoveQueue;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Move;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.BoardSnapshot;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.externalengine.IOExternalEngine;
import java.awt.Color;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.ImageIOImageData;

/**
 *
 * @author thw
 */
public class OPENGLUIHelper {

    //<editor-fold defaultstate="collapsed" desc="Private vars">
    private MouseEventHelper mouseHelper;
    private SoundManager soundManager;
    private ChessBoard board;
    private OPENGLUIDriver driver;
    public final EngineMoveQueue engineMovePositions = new EngineMoveQueue();

    private final int width = 800;
    private final int height = 600;
    private DisplayMode displayMode;
    private boolean running = true;

    // right-left roll.
    private float r = UI3DConst.START_R;
    // up-down roll.
    private float g = UI3DConst.START_G;
    private float speed = UI3DConst.TANSLATE_SPEED;
    private float zoom = UI3DConst.START_ZOOM;

    // Lighting :
    private final float LIGHT_DISTANCE = 3.0f;
    private final float LIGHT_HEIGHT = 2.0f;
    private float[] ambientLight = {0.5f, 0.5f, 0.5f, 0.5f};
    private float[] lightDiffuse = {0.80f, 0.80f, 0.80f, 0.80f};
    private float[] lightPosition0 = {-LIGHT_DISTANCE, LIGHT_HEIGHT, 0.0f, 0.0f};
    private float[] lightPosition1 = {LIGHT_DISTANCE, LIGHT_HEIGHT, 0.0f, 0.0f};
    private float[] lightPosition3 = {0.0f, LIGHT_HEIGHT, LIGHT_DISTANCE, 0.0f};
    private float[] lightPosition4 = {0.0f, LIGHT_HEIGHT, -LIGHT_DISTANCE, 0.0f};
    private float[] spotDirection = {0.0f, 0.0f, 0.0f, 1.0f};
    private float[] materialSpecular = {0.9686f, 0.9529f, 0.7450f, 0.5f};

    // SHADERS :    
    private int shaderProgram;
    private int vertexShader;
    private int fragmentShader;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Starter.
     *
     * @param driver OPENGLUIDriver
     */
    public void start(final OPENGLUIDriver driver) {

        try {

            this.driver = driver;
            createWindow();
            initOPENGL();
            board = new ChessBoard(null, null, null);
            this.driver.setHelper(this);
            initSoundData();
            mouseHelper = new MouseEventHelper(this);
            run();
        } catch (final Exception e) {
            running = false;
            Logger.getLogger(OPENGLUIHelper.class.getName()).log(Level.WARNING, null, e);
        }
    }

    /**
     * Initialize GL & GLU.
     */
    private void initOPENGL() {

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.8901f, 0.8392f, 0.7568f, 0.0f); // bg color.
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        GL11.glMatrixMode(GL11.GL_PROJECTION);

        /**
         * *********************************************************************
         * Transparency for end of glBegin{] and fordrawing semi transparent
         * quads after opaque ones.
         */
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glAlphaFunc(GL11.GL_ALWAYS, GL11.GL_ONE);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        /**
         * *********************************************************************
         * GL11.glEnable(GL11.GL_BLEND) will result in a transparency effect but
         * all quads will be affected. Solution : switch from enabled/disabled
         * in glBegin method > draw normal quads 1st then alpha ones... TODO
         */

        GLU.gluPerspective(
                45.0f,
                (float) displayMode.getWidth() / (float) displayMode.getHeight(),
                0.1f,
                100.0f);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

        /**
         * *********************************************************************
         * Light.
         */
        GL11.glShadeModel(GL11.GL_SMOOTH);
        // set specular material color : 
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, BufferUtils.allocFloats(materialSpecular));
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 30.0f);
        // global ambient light 
        //GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, BufferUtils.allocFloats(ambientLight));

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, BufferUtils.allocFloats(lightDiffuse));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, BufferUtils.allocFloats(lightPosition0));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPOT_DIRECTION, BufferUtils.allocFloats(spotDirection));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, BufferUtils.allocFloats(lightDiffuse));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, BufferUtils.allocFloats(lightPosition1));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPOT_DIRECTION, BufferUtils.allocFloats(spotDirection));
        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, BufferUtils.allocFloats(lightDiffuse));
        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, BufferUtils.allocFloats(lightPosition3));
        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_SPOT_DIRECTION, BufferUtils.allocFloats(spotDirection));
        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, BufferUtils.allocFloats(lightDiffuse));
        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, BufferUtils.allocFloats(lightPosition4));
        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_SPOT_DIRECTION, BufferUtils.allocFloats(spotDirection));

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glEnable(GL11.GL_LIGHT1);
        GL11.glEnable(GL11.GL_LIGHT2);
        GL11.glEnable(GL11.GL_LIGHT3);
        GL11.glCullFace(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BACK);
        GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_TRUE);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);

        /**
         * ***************************************************************
         * GL_NORMALIZE will add smooth shading. Else shading is harsh.
         */
        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);

    }

    /**
     * Init all AL sounds & sound effects.
     */
    private void initSoundData() {
        soundManager = new SoundManager();
        soundManager.initialize(16);
        SoundUtils.StaticSoundVars.bip = soundManager.addSound(SoundUtils.StaticSoundVars.BIP);
        SoundUtils.StaticSoundVars.move = soundManager.addSound(SoundUtils.StaticSoundVars.MOVE);
    }

    /**
     * Initialize main frame Window.
     *
     * @throws Exception
     */
    private void createWindow() throws Exception {

        Display.setFullscreen(false);
        Display.setResizable(true);

        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (DisplayMode d1 : d) {
            if (d1.getWidth() == width && d1.getHeight() == height && d1.getBitsPerPixel() == 32) {
                displayMode = d1;
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("jellyfish 3D - play chess, have fun !");

        Display.setIcon(new java.nio.ByteBuffer[]{
            new ImageIOImageData().imageToByteBuffer(ImageIO.read(
            new File(getClass().getResource(UIConst.JELLYFISH_ICON_32).getPath())), false, false, null),
            new ImageIOImageData().imageToByteBuffer(ImageIO.read(
            new File(getClass().getResource(UIConst.JELLYFISH_ICON_16).getPath())), false, false, null)
        });

        /**
         * Display.create(); // previously Below for solving anialiasing
         * anti-aliasing. (8,8,8,8) also works... TODO : needs lookup for
         * detail.
         */
        Display.create(new PixelFormat(8, 8, 0, 8));
    }

    /**
     * Main run loop.
     */
    private void run() {

        while (running && !Display.isCloseRequested()) {

            try {
                getKeyInput();
                render();
                this.mouseHelper.selectedSquareEvent(board.getSquareMap());
                updateEngineMoves();
                Display.update();
                Display.sync(60);
            } catch (final Exception ex) {
                running = false;
                Logger.getLogger(OPENGLUIHelper.class.getName()).log(Level.SEVERE, ex.getMessage());
            }
        }

        soundManager.destroy();
        GL20.glDeleteProgram(shaderProgram);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        Display.destroy();

        // finnaly close engine process & delete snapshots.
        IOExternalEngine.getInstance().writeToEngine(UCIConst.ENGINE_QUIT, MessageTypeConst.NOT_SO_TRIVIAL);
        BoardSnapshot.deleteSnapshots(new File(BoardSnapshot.getSNAPSHOT_PATH()));
    }

    /**
     * OPENGL Redering content.
     */
    private void render() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        GL11.glTranslatef(0.0f, -0.0f, zoom);
        GL11.glRotatef(r, 0.0f, 1.0f, 0.0f);
        GL11.glRotated(g, g, 1.0f, 0.0f);

        /**
         * DEBUG : *************************************************************
         * System.out.println("z="+r+" g="+g+" zoom="+zoom);
         */
        
        // Shader disabled. GL20.glUseProgram(shaderProgram);
        GL11.glBegin(GL11.GL_QUADS);
        {
            board.paintVertexes();
        }
        GL11.glEnd();

        GL11.glPopMatrix();

        /**
         * *********************************************************************
         * Chess set models display.
         *
         * @see ModelLoaderUtils.createDisplayList method
         */
        for (ChessSquare s : board.getSquareMap().values()) {
            if (s.getModel() != null) {
                GL11.glCallList(s.getModelDisplayList());
            }
        }

        // Shader disabled. GL20.glUseProgram(0);
    }

    /**
     *
     */
    private void getKeyInput() {

        boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP);
        boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
        boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
        boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
        boolean space = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean esc = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);

        if (esc) {
            running = false;
        }

        if (space) {

            if (keyDown) {
                zoom = zoom > UI3DConst.MAX_ZOOM_OUT ? zoom - (speed / 10.0f) : zoom;
            } else if (keyUp) {
                zoom = zoom < UI3DConst.MAX_ZOOM_IN ? zoom + (speed / 10.0f) : zoom;
            }

            return;
        }

        if (keyDown) {
            g -= speed;
        } else if (keyUp) {
            g += speed;
        }

        if (keyLeft) {
            r -= speed;
        } else if (keyRight) {
            r += speed;
        }
    }

    /**
     * Update engine moves appended to queue.
     */
    private void updateEngineMoves() {

        int counter = 1;
        float[] color;
        if (engineMovePositions.getEngineMoves().size() > 0) {
            for (Move m : engineMovePositions.getEngineMoves()) {
                color = m.isEngineMove() ? Game3D.engine_color : Game3D.engine_oponent_color;
                board.updateSquare(m.getPosTo(), m.getPosFrom(), color);
                soundManager.playEffect(SoundUtils.StaticSoundVars.move);
                
                if (counter == engineMovePositions.getEngineMoves().size()) {
                    board.resetAllChessSquareBackgroundColors(board.getSelectedSquare().CHESS_POSITION);
                    board.getSquareMap().get(m.getPosFrom()).updateColor(UI3DConst.ENGINE_MOVE_COLOR);
                    board.getSquareMap().get(m.getPosTo()).updateColor(UI3DConst.ENGINE_MOVE_COLOR);
                }
                
                ++counter;
            }
            engineMovePositions.clearQueue();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter & setters">
    public ChessBoard getBoard() {
        return board;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public OPENGLUIDriver getDriver() {
        return driver;
    }
    //</editor-fold>

}
