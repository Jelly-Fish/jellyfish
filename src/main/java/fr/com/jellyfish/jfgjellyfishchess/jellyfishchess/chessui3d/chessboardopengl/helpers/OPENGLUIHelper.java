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

package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.helpers;

import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.ChessBoard;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.ChessSquare;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.gl3dobjects.OPENGLModel;
import fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d.chessboardopengl.utils.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author thw
 */
public class OPENGLUIHelper {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars">
    final private MouseEventHelper mouseHelper = new MouseEventHelper();
    private ChessBoard board;
    
    private final int width = 800;
    private final int height = 600;
    private DisplayMode displayMode;
    private boolean running = true;
    
    // right-left roll.
    private float r = -360.0f;
    // up-down roll.
    private float g = -1099.5f;
    private float speed = 1.50f;
    private float zoom = -16.0f;
    
    // LIGTH :
    private final float ligthdistance = 5.0f;
    private float lightAmbient[] = { 0.5f, 0.5f, 0.5f, 0.5f };
    private float lightDiffuse[] = { 0.80f, 0.80f, 0.80f, 0.80f };
    private float[] lightPosition1 = { -ligthdistance, 4.0f, 0.0f, 0.0f };
    private float[] lightPosition2 = { ligthdistance, 4.0f, 0.0f, 0.0f };
    private float spotDirection[] = { 0.0f, 0.0f, 0.0f, 0.0f };
    
    // SHADERS :
    private int shaderProgram; 
    private int vertexShader;
    private int fragmentShader;
    
    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Starter.
     */
    public void start() {

        try {
            createWindow();
            initOPENGL();
            board = new ChessBoard(null, null, null);
            run();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Initialize GL & GLU.
     */
    private void initOPENGL() {

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.10f, 0.06f, 0.12f, 0.0f); // bg color.
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        
        /***********************************************************************
         * Transparency for end of glBegin{] and fordrawing semi transparent
         * quads after opaque ones. */
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glAlphaFunc(GL11.GL_ALWAYS, GL11.GL_ONE);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        /***********************************************************************
         * GL11.glEnable(GL11.GL_BLEND) will result in a transparency effect but
         * all quads will be affected. Solution : switch from enabled/disabled in
         * glBegin method > draw normal quads 1st then alpha ones... TODO */
        
        GL11.glLoadIdentity();
        
        GLU.gluPerspective(
                45.0f,
                (float) displayMode.getWidth() / (float) displayMode.getHeight(),
                0.1f,
                100.0f);
        
        /***********************************************************************
         * Light. */
        // AMBIENT Disabled : GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, allocFloats(lightAmbient));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, BufferUtils.allocFloats(lightDiffuse));        
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, BufferUtils.allocFloats(lightPosition1));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPOT_DIRECTION, BufferUtils.allocFloats(spotDirection));
        // AMBIENT Disabled : GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, BufferUtils.allocFloats(lightAmbient));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, BufferUtils.allocFloats(lightDiffuse));        
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, BufferUtils.allocFloats(lightPosition2));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPOT_DIRECTION, BufferUtils.allocFloats(spotDirection));
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glEnable(GL11.GL_LIGHT1);
        GL11.glCullFace(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BACK);
        GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_TRUE);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
        /**********************************************************************/

        /*****************************************************************
         * GL_NORMALIZE will add smooth shading. Else shading is harsh. */
        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
    }
    
    /**
     * Initialize main frame Window.
     * @throws Exception 
     */
    private void createWindow() throws Exception {

        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (DisplayMode d1 : d) {
            if (d1.getWidth() == width && d1.getHeight() == height && d1.getBitsPerPixel() == 32) {
                displayMode = d1;
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("LWJGL engine");
        /**
         * Display.create(); // previously
         * Below for solving anialiasing anti-aliasing. 
         * (8,8,8,8) also works... TODO : needs lookup for detail.
         */
        Display.create(new PixelFormat(8,8,0,8));
    }

    /**
     * Main run loop.
     */
    private void run() {

        while (running && !Display.isCloseRequested()) {
            
            try {
                getKeyInput();
                render();
                mouseHelper.selectedSquareEvent(board.getSquareMap().values());
                Display.update();
                Display.sync(60);
            } catch (final Exception ex) {
                running = false;
            }
        }
        
        GL20.glDeleteProgram(shaderProgram);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        Display.destroy();
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

        // Shader disabled. GL20.glUseProgram(shaderProgram);
        
        GL11.glBegin(GL11.GL_QUADS); {
            board.paintVertexes();
        } GL11.glEnd(); 
        
        GL11.glPopMatrix();
        
        /***********************************************************************
         * Chess set models display.
         * @see ModelLoaderUtils.createDisplayList method */
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

        if (esc) { running = false; }
        
        if (space) {

            if (keyDown) {
                zoom -= speed / 10.0f;
            } else if (keyUp) {
                zoom += speed / 10.0f;
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
    //</editor-fold>
    
}
