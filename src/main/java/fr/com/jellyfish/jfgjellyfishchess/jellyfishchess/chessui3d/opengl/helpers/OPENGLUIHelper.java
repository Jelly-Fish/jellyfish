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
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.constants.UI3DCoordinateConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects.ChessBoard;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.helpers.texturing.TextureLoader;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.BufferUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.SoundUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.components.Console3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.MoveQueue;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Game3D;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.Move;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.dto.NewGame;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.exceptions.QueueCapacityOverflowException;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.interfaces.ProgressObserver;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.DataUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.opengl.utils.OPENGLDisplayUtils;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.MessageTypeConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants.UCIConst;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.game.BoardSnapshot;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.uci.externalengine.IOExternalEngine;
import java.awt.Canvas;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
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
    /**
     * Console3D instance reference.
     */
    public Console3D console;
    
    /**
     * Mouse helper.
     */
    private MouseEventHelper mouseHelper;
    
    /**
     * Keyboard helper.
     */
    private KeyboardEventHelper keyHelper;
    
    /**
     * Sound helper.
     */
    private SoundManager soundManager;
    
    /**
     * Chess board class.
     */
    private ChessBoard board;
    
    /**
     * Game & UI driver instance.
     */
    OPENGLUIDriver driver;
    
    /**
     * OPENGL texture helper.
     */
    public TextureLoader textureLoader;
    
    /**
     * DTO class for restarting nes games.
     */
    private NewGame restartGameDto = null;
    
    /**
     * OPENGL errors for logging.
     */
    private Integer glError = null;

    /**
     * Engine & jellyfish API move queue for rendering.
     */
    public MoveQueue engineMovePositions;

    /**
     * GL Diaplayable mode.
     */
    private DisplayMode displayMode;
    
    /**
     * Prog is running ?
     */
    private boolean running = true;
    
    /**
     * right-left roll.
     */
    public float r = UI3DCoordinateConst.START_R_B;
    
    /**
     * Up-down rool.
     */
    public float g = UI3DCoordinateConst.START_G_B;
    
    /**
     * Translate speed.
     */
    float speed = UI3DCoordinateConst.TANSLATE_SPEED;
    
    /**
     * Zoom in-out value.
     */
    float zoom = UI3DCoordinateConst.START_ZOOM;

    /**
     * Lighting :
     */
    private float[] ambientLight = {0.385f, 0.385f, 0.385f, 0.385f};
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
     * @param console
     * @param reload
     * @param progressObserver
     */
    public void start(final Console3D console, final boolean reload, 
            final ProgressObserver progressObserver) {

        try {
            
            this.console = console;
            Game3D.getInstance().initGame3DSettings(this, null);
            this.engineMovePositions = new MoveQueue(Game3D.getInstance().getEngineOponentColorStringValue(),
                    Game3D.getInstance().getEngineColorStringValue());
            this.driver = new OPENGLUIDriver(this.console);
            this.console.setDriver(this.driver);
            this.driver.getWriter().setDisplayAll(Game3D.getInstance().isDisplayAllOutput());
            this.textureLoader = new TextureLoader();
            createWindow(reload);
            initOPENGL();
            this.board = new ChessBoard(null, null, null, driver, Game3D.getInstance().getWhiteSquareColor(),
                Game3D.getInstance().getBlackSquareColor());
            this.driver.setHelper(this);
            initSoundData();
            this.mouseHelper = new MouseEventHelper(this, 
                    Game3D.getInstance().getEngineOponentColorStringValue());
            this.keyHelper = new KeyboardEventHelper(this);
            this.console.setKeyboardHelper(keyHelper);
            this.console.setMouseHelper(mouseHelper);
            displayGraphicsInfo();
            final boolean relaoded = this.driver.reload(Game3D.getInstance().isReloadPreviousGame() &&
                    Game3D.getInstance().getPreviousMoveQueue() != null, progressObserver);
            
            if (reload && relaoded) {
                progressObserver.notifyProcessEnd();
                OPENGLDisplayUtils.showDisplay(UI3DCoordinateConst.START_WINDOW_X, 
                    UI3DCoordinateConst.START_WINDOW_Y);
                this.console.setVisible(true);
            }
            
            this.driver.setReady(true);
            this.driver.game.notifyReadyStateToEngine();
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
    public void restart(final NewGame restartGameDto) { 
        
        this.driver.setReady(false);
        final String uiColor = restartGameDto.fetchUiColor();
        final String engineColor = restartGameDto.fetchEngineColor();
        
        this.driver.stopHintSearch(true);
        Game3D.getInstance().setEngineOponentColorStringValue(uiColor);
        Game3D.getInstance().setEngineColorStringValue(engineColor);
        Game3D.getInstance().setEngineMoving(false);
        Game3D.getInstance().setEngineSearching(false);
        Game3D.getInstance().setUiCheckmate(false);
        Game3D.getInstance().setUiCheck(false);
        Game3D.getInstance().setEngineCheck(false);
        Game3D.getInstance().setEngineCheckmate(false);
        this.restartGameDto = restartGameDto;
    }

    /**
     * Initialize GL & GLU.
     */
    private void initOPENGL() {

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(
                Game3D.getInstance().getBgColor()[0],
                Game3D.getInstance().getBgColor()[1],
                Game3D.getInstance().getBgColor()[2],
                Game3D.getInstance().getBgColor()[3]
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
     * @param visible
     * @throws Exception 
     */
    private Canvas createWindow(final boolean reload) throws Exception {

        Display.setFullscreen(false);
        Display.setResizable(false);
        Display.setVSyncEnabled(true);

        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (DisplayMode d1 : d) {
            if (d1.getWidth() == UI3DCoordinateConst.WINDOW_WIDTH && 
                    d1.getHeight() == UI3DCoordinateConst.WINDOW_HEIGHT && 
                    d1.getBitsPerPixel() == 32) {
                displayMode = d1;
                break;
            }
        }

        if (reload) {
            OPENGLDisplayUtils.hideDisplay(UI3DCoordinateConst.WINDOW_WIDTH, 
                UI3DCoordinateConst.WINDOW_HEIGHT);
        } else {
            Display.setLocation(UI3DCoordinateConst.START_WINDOW_X, 
            UI3DCoordinateConst.START_WINDOW_Y);
        }
        
        Display.setDisplayMode(displayMode);
        Display.setTitle("jellyfish - play chess, have fun !");

        Display.setIcon(new java.nio.ByteBuffer[]{
            new ImageIOImageData().imageToByteBuffer(ImageIO.read(
            new File(getClass().getResource(UI3DConst.JELLYFISH_ICON_32).getPath())), false, false, null),
            new ImageIOImageData().imageToByteBuffer(ImageIO.read(
            new File(getClass().getResource(UI3DConst.JELLYFISH_ICON_16).getPath())), false, false, null)
        });

        /**
         * anti-aliasing. (8,8,8,8) also works.
         */
        Display.create(new PixelFormat(8, 8, 0, 8));
        return Display.getParent();
    }

    /**
     * Main run loop.
     */
    private void run() {
        
        while (running) {
                
            //<editor-fold defaultstate="collapsed" desc="restart">
            if (this.restartGameDto != null && !this.restartGameDto.isRestarted()) {
                
                OPENGLDisplayUtils.hideDisplay(UI3DCoordinateConst.WINDOW_WIDTH, 
                    UI3DCoordinateConst.WINDOW_HEIGHT);
                
                Game3D.getInstance().initGame3DSettings(this, restartGameDto);
                this.engineMovePositions.clearQueue();
                
                this.board = new ChessBoard(null, null, null, driver, Game3D.getInstance().getWhiteSquareColor(),
                    Game3D.getInstance().getBlackSquareColor());
                this.board.resetAllChessSquareBackgroundColors();
                
                try {
                    this.driver.cleanUp();
                } catch (final QueueCapacityOverflowException qcofex) {
                    Logger.getLogger(OPENGLUIHelper.class.getName()).log(Level.SEVERE, null, qcofex);
                }
                
                this.driver.restart(restartGameDto);
                
                displayGraphicsInfo();
 
                this.restartGameDto.setRestarted(true);
                
                OPENGLDisplayUtils.showDisplay(UI3DCoordinateConst.START_WINDOW_X, 
                    UI3DCoordinateConst.START_WINDOW_Y);
                
            } else if (this.restartGameDto != null && this.restartGameDto.isRestarted()) {
                // Finally reset this dto class to null, set driver as ready &
                // notify it's state to engine.
                this.restartGameDto = null;
                this.driver.setReady(true);
                this.driver.game.notifyReadyStateToEngine();
            }
            //</editor-fold>
            
            OPENGLDisplayUtils.checkDisplayLocation(Display.getX(), Display.getY(),
                    UI3DCoordinateConst.START_WINDOW_X, 
                    UI3DCoordinateConst.START_WINDOW_Y);
            
            this.keyHelper.processKeyInput();
            this.render();
            this.mouseHelper.selectedSquareEvent(board.getSquareMap());
            this.updateEngineMoves();
            this.driver.clearObsoleteDisplayLists(OPENGLUIDriver.MAX_DISPLAY_LIST_DELETE_START_INDEX);
            Display.update();
            Display.sync(60);
        }
        
        this.driver.clearObsoleteDisplayLists(0);
        soundManager.destroy();
        GL20.glDeleteProgram(shaderProgram);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        Display.destroy();

        
        // Close engine process & delete snapshots.
        IOExternalEngine.getInstance().writeToEngine(UCIConst.ENGINE_QUIT, MessageTypeConst.NOT_SO_TRIVIAL);
        BoardSnapshot.deleteSnapshots(new File(BoardSnapshot.getSNAPSHOT_PATH()));

        // Serialize Game3D & MoveQueue :
        Game3D.getInstance().serialize();
        DataUtils.xmlSerializeMoveQueue(this.driver.moveQueue);
    }

    /**
     * OPENGL Redering content.
     */
    private void render() {

        GL11.glClearColor(
                Game3D.getInstance().getBgColor()[0],
                Game3D.getInstance().getBgColor()[1],
                Game3D.getInstance().getBgColor()[2],
                Game3D.getInstance().getBgColor()[3]
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
         * OPEN GL debuging.
         */
        if (this.glError == null || this.glError != GL11.glGetError()) {
            this.glError = GL11.glGetError();
            System.err.println("-- OPENG GL ERROR STATE = " + this.glError);
        }
    }

    /**
     * Update engine moves appended to queue.
     */
    private void updateEngineMoves() {
        
        int counter = 1;
        float[] color;
        //if (engineMovePositions.getMoves().size() > 0) {

            /**
             * Process engines moves built when calling executeEngineMoves().
             */
            for (Move m : engineMovePositions.getMoves().values()) {

                color = m.isEngineMove() ? 
                        Game3D.getInstance().getEngineColor() : 
                        Game3D.getInstance().getEngineOponentColor();
                
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
                    
                    // Only set square color if the move is comming from engine.
                    // This will prevent ui side rook castling move to be colored
                    // with engine side square color :
                    if (m.isEngineMove()) {
                        board.getSquareMap().get(m.getPosFrom()).updateColor(UI3DConst.ENGINE_MOVE_SQUARE_COLOR);
                        board.getSquareMap().get(m.getPosTo()).updateColor(UI3DConst.ENGINE_MOVE_SQUARE_COLOR);
                    }
                }
                ++counter;
            }
            engineMovePositions.clearQueue();
        //}
    }
    
    /**
     * Update engine moves queue only when reloading a game previously played or 
     * loaded from game history.
     */
    public void updateEngineMovesOnReload() {

        if (engineMovePositions.getMoves().size() > 0) {

            for (Move m : engineMovePositions.getMoves().values()) {
                board.updateSquare(m.getPosTo(), m.getPosFrom(), 
                    m.isEngineMove() ? Game3D.getInstance().getEngineColor() : 
                        Game3D.getInstance().getEngineOponentColor());
            }
            engineMovePositions.clearQueue();
        }
    }

    /**
     * DEBUG OPENGL and g card informations.
     */
    private void displayGraphicsInfo() {
        
        if (!Game3D.getInstance().isDEBUGMODE()) {
            return;
        }
        
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
