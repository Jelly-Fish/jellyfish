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
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers.texturing.TextureLoader;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.BufferUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.ColorUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.SoundUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.components.Console3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.MoveQueue;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Move;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.RestartNewGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.BoardSnapshot;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.externalengine.IOExternalEngine;
import java.awt.Color;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
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
    private KeyboardEventHelper keyHelper;
    private SoundManager soundManager;
    private ChessBoard board;
    OPENGLUIDriver driver;
    public TextureLoader textureLoader;
    private RestartNewGame restartGameDto = null;

    public MoveQueue engineMovePositions;

    private final int width = 800;
    private final int height = 600;
    private DisplayMode displayMode;
    private boolean running = true;
    
    // right-left roll.
    public float r = UI3DConst.START_R_B;
    // up-down roll.
    public float g = UI3DConst.START_G_B;
    float speed = UI3DConst.TANSLATE_SPEED;
    float zoom = UI3DConst.START_ZOOM;

    /**
     * Lighting :
     */
    private float[] ambientLight = {0.5f, 0.5f, 0.5f, 0.5f};
    private float[] lightDiffuse = {0.5f, 0.5f, 0.5f, 0.5f};
    private float[] lightPosition4 = {0.0f, 5.0f, 0.0f, 1.0f};
    private float[] spotDirection = {0.0f, 0.0f, 0.0f, 0.0f};
    private float[] materialSpecular = {0.9686f, 0.9529f, 0.7450f, 0.5f};

    /**
     * SHADERS :
     */
    private int shaderProgram;
    private int vertexShader;
    private int fragmentShader;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Starter.
     *
     * @param console
     * @param color
     */
    public void start(final Console3D console, final String color) {

        try {
            
            Game3D.initGame3DSettings(this, color == null ? UI3DConst.COLOR_W_STR_VALUE : color);
            final float[] c = ColorUtils.color(new Color(92,122,119));
            Game3D.bg_color = new float[]{c[0],c[1],c[2],0.0f};
            this.engineMovePositions = new MoveQueue();
            this.driver = new OPENGLUIDriver(console);
            this.driver.getWriter().setDisplayAll(false);
            console.setDriver(this.driver);
            textureLoader = new TextureLoader();
            createWindow();
            initOPENGL();
            board = new ChessBoard(null, null, null, driver);
            this.driver.setHelper(this);
            initSoundData();
            mouseHelper = new MouseEventHelper(this, Game3D.engine_oponent_color_str_value);
            keyHelper = new KeyboardEventHelper(this);
            console.setKeyboardHelper(keyHelper);
            console.setMouseHelper(mouseHelper);
            displayGraphicsInfo();
            run();
            
        } catch (final Exception ex) {
            Logger.getLogger(OPENGLUIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * restarter.
     *
     * @param restartGameDto
     */
    public void restart(final RestartNewGame restartGameDto) { 
        Game3D.ui_checkmate = false;
        this.restartGameDto = restartGameDto;
    }

    /**
     * Initialize GL & GLU.
     */
    private void initOPENGL() {

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(
                UI3DConst.DEFAULT_BG_COLOR[0],
                UI3DConst.DEFAULT_BG_COLOR[1],
                UI3DConst.DEFAULT_BG_COLOR[2],
                UI3DConst.DEFAULT_BG_COLOR[3]
                ); // bg color.
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
         * in glBegin method > draw normal quads 1st then alpha ones.
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
         * Light. Unused settings examples below : // global ambient light :
         * //GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT,
         * BufferUtils.allocFloats(ambientLight)); // diffused ligth :
         * //GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE,
         * BufferUtils.allocFloats(lightDiffuse));
         */
        GL11.glShadeModel(GL11.GL_SMOOTH);
        // set specular material color : 
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, BufferUtils.allocFloats(materialSpecular));
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 50.0f);

        GL11.glLight(GL11.GL_LIGHT4, GL11.GL_POSITION, BufferUtils.allocFloats(lightPosition4));
        GL11.glLight(GL11.GL_LIGHT4, GL11.GL_SPOT_DIRECTION, BufferUtils.allocFloats(spotDirection));
        GL11.glLight(GL11.GL_LIGHT4, GL11.GL_DIFFUSE, BufferUtils.allocFloats(lightDiffuse));
        GL11.glLight(GL11.GL_LIGHT4, GL11.GL_AMBIENT, BufferUtils.allocFloats(ambientLight));

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT4);
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

        Display.setLocation(0, 0);
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
         * anti-aliasing. (8,8,8,8) also works.
         * detail.
         */
        Display.create(new PixelFormat(8, 8, 0, 8));
    }

    /**
     * Main run loop.
     */
    private void run() {
        
        while (running && !Display.isCloseRequested()) {
                
            //<editor-fold defaultstate="collapsed" desc="restart">
            if (this.restartGameDto != null && !this.restartGameDto.isRestarted()) {

                Game3D.initGame3DSettings(this, this.restartGameDto.color == null ?
                        UI3DConst.COLOR_W_STR_VALUE : this.restartGameDto.color);
                this.engineMovePositions.clearQueue();
                this.board = new ChessBoard(null, null, null, driver);
                this.board.resetAllChessSquareBackgroundColors();
                
                try {
                    this.driver.cleanUp();
                } catch (final QueueCapacityOverflowException qcofex) {
                    Logger.getLogger(OPENGLUIHelper.class.getName()).log(Level.SEVERE, null, qcofex);
                }
                
                this.driver.restart(restartGameDto);
                displayGraphicsInfo();
                this.mouseHelper.selectedSquareEvent(board.getSquareMap());
                // Finally always reset this dto class to null :
                this.restartGameDto.setRestarted(true);
                
            } else if (this.restartGameDto != null && this.restartGameDto.isRestarted()) {
                this.restartGameDto = null;
            }
            //</editor-fold>
            
            this.keyHelper.processKeyInput();
            render();
            this.mouseHelper.selectedSquareEvent(board.getSquareMap());
            updateEngineMoves();
            this.driver.clearObsoleteDisplayLists(OPENGLUIDriver.MAX_DISPLAY_LIST_DELETE_START_INDEX);
            Display.update();
            Display.sync(60);
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

        GL11.glClearColor(
                Game3D.bg_color[0],
                Game3D.bg_color[1],
                Game3D.bg_color[2],
                Game3D.bg_color[3]
                ); // bg color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        GL11.glTranslatef(0.0f, -0.0f, zoom);
        GL11.glRotatef(r, 0.0f, 1.0f, 0.0f);
        GL11.glRotated(g, g, 1.0f, 0.0f);

        /**
         * DEBUG : *************************************************************
         * System.out.println("r="+r+" g="+g+" zoom="+zoom);
         */
         
        GL11.glPushMatrix();
        
        GL11.glBegin(GL11.GL_QUADS);
        {
            board.paintVertexes();
        }
        GL11.glEnd();

        GL11.glPopMatrix();

        /**
         * *********************************************************************
         * models display lists.
         *
         * @see ModelLoaderUtils.createDisplayList method
         */
        for (ChessSquare s : board.getSquareMap().values()) {

            if (s.getModel() != null) {
                GL11.glCallList(s.getModelDisplayList());
            }
        }

        /**
         * OPEN GL debuging. Must be decommented only when neded.
         */
        //System.out.println("-- OPENG GL ERROR STATE = " + GL11.glGetError());
    }

    /**
     * Update engine moves appended to queue.
     */
    private void updateEngineMoves() {
        
        int counter = 1;
        float[] color;
        if (engineMovePositions.getMoves().size() > 0) {

            /**
             * Process engines moves built when calling executeEngineMoves().
             */
            for (Move m : engineMovePositions.getMoves().values()) {

                color = m.isEngineMove() ? Game3D.engine_color : Game3D.engine_oponent_color;
                
                if (m.isPawnPromotion()) {
                    board.updateSquare(m.getPosTo(), m.getPosFrom(), color, 
                            m.getPawnPromotionObjPath(), m.getPawnPromotionPieceType());
                } else {
                    board.updateSquare(m.getPosTo(), m.getPosFrom(), color);
                }
                
                soundManager.playEffect(SoundUtils.StaticSoundVars.move);

                if (counter == engineMovePositions.getMoves().size()) {
                    
                    if (board.getSelectedSquare() != null) {
                        board.resetAllChessSquareBackgroundColors(board.getSelectedSquare().CHESS_POSITION);
                    } else {
                        board.resetAllChessSquareBackgroundColors();
                    }
                    board.getSquareMap().get(m.getPosFrom()).updateColor(UI3DConst.ENGINE_MOVE_SQUARE_COLOR);
                    board.getSquareMap().get(m.getPosTo()).updateColor(UI3DConst.ENGINE_MOVE_SQUARE_COLOR);
                }

                ++counter;
            }
            engineMovePositions.clearQueue();
        }
    }

    /**
     * DEBUG OPENGL and g card informations.
     */
    private void displayGraphicsInfo() {
        System.out.println("\n-------- OPEN GL INFO ------------------------------");
        System.out.println("-- GL_RENDERER: " + GL11.glGetString(GL11.GL_RENDERER));
        System.out.println("-- GL_VENDOR: " + GL11.glGetString(GL11.GL_VENDOR));
        System.out.println("-- GL_VERSION: " + GL11.glGetString(GL11.GL_VERSION));
        final ContextCapabilities caps = GLContext.getCapabilities();
        System.out.println("-- OpenGL 3.0: " + caps.OpenGL30);
        System.out.println("-- OpenGL 3.1: " + caps.OpenGL31);
        System.out.println("-- OpenGL 3.2: " + caps.OpenGL32);
        System.out.println("-- OpenGL 3.3: " + caps.OpenGL33);
        System.out.println("-- OpenGL 4.0: " + caps.OpenGL40);
        System.out.println("-- ARB_compatibility: " + caps.GL_ARB_compatibility);
        System.out.println("----------------------------------------------------\n");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter & setters">    
    public void setRunning(boolean running) {
        this.running = running;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
    //</editor-fold>

}
