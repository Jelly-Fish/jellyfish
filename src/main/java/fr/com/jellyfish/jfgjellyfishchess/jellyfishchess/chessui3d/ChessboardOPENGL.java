/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.chessui3d;

import chessboardopengl.openglentities.OpenGLMdl;
import chessboardopengl.utils.ModelLoaderUtils;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ChessboardOPENGL {
    
    //<editor-fold defaultstate="collapsed" desc="Private vars">
    public static final int width = 800;
    public static final int height = 600;
    static DisplayMode displayMode;
    static boolean running = true;
    
    // right-left roll.
    static float r = -360.0f;
    // up-down roll.
    static float g = -1099.5f;
    static float speed = 1.50f;
    static float zoom = -16.0f;
    
    // LIGTH :
    static float lightAmbient[] = { 0.5f, 0.5f, 0.5f, 0.5f };
    static float lightDiffuse[] = { 0.80f, 0.80f, 0.80f, 1.0f };
    static float lightPosition[] = { 0.0f, 6.0f, 0.0f, 0.0f };
    static float spotDirection[] = { 0.0f, 0.0f, 0.0f, 0.0f };
    
    // SHADERS :
    static int shaderProgram; 
    static int vertexShader;
    static int fragmentShader;
    
    // PAWN model test :
    static OpenGLMdl pawn;
    private static int pawnDisplayList;
    static float[] margins = { 0.50f, 0.50f, 2.50f };
    static float[] rgbW = { 0.4f, 0.27f, 0.17f }; 
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * 
     */
    public static void start() {

        try {
            createWindow();
            initOPENGL();
            initModels();
            run();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 
     */
    private static void initOPENGL() {

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.03f, 0.01f, 0.15f, 0.05f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        
        GLU.gluPerspective(
                45.0f,
                (float) displayMode.getWidth() / (float) displayMode.getHeight(),
                0.1f,
                100.0f);
        
        /**********************************************************************
         * Light
         */
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, allocFloats(lightAmbient));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, allocFloats(lightDiffuse));        
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, allocFloats(lightPosition));
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT1);
        GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_TRUE);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
        /**********************************************************************/

        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
    }
    
    /**
     * 
     */
    private static void initModels() {
        
        try {
            pawn = ModelLoaderUtils.loadModel(new File("src/main/resources/models/pawn.obj"));
            pawnDisplayList = ModelLoaderUtils.createDisplayList(pawn, margins, rgbW);
            //new Chess3DObject(0.50f, 0.25f, 2.50f, );
        } catch (final IOException ex) {
            Logger.getLogger(ChessboardOPENGL.class.getName()).log(Level.SEVERE, null, 
                    "IOException: " + ex);
        }
    }
    
    /**
     * 
     * @param floatarray
     * @return 
     */
    public static FloatBuffer allocFloats(float[] floatarray) {
        FloatBuffer fb = ByteBuffer.allocateDirect(
               floatarray.length * 255).order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(floatarray).flip();
        return fb;
    }

    /**
     * 
     * @throws Exception 
     */
    private static void createWindow() throws Exception {

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
     * 
     */
    private static void run() {

        while (running && !Display.isCloseRequested()) {
            
            try {
                getKeyInput();
                render();
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
     * 
     */
    private static void render() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, allocFloats(lightPosition));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPOT_DIRECTION, allocFloats(spotDirection));
        
        GL11.glTranslatef(0.0f, -0.0f, zoom);
        GL11.glRotatef(r, 0.0f, 1.0f, 0.0f);
        GL11.glRotated(g, g, 1.0f, 0.0f);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);
        
        GL20.glUseProgram(shaderProgram);
        
        
        GL11.glBegin(GL11.GL_QUADS);
        {

            GL11.glColor3f(0.3f, 0.28f, 0.28f);
            // TOP BORDERS :
            GL11.glNormal3f(0.0f, 1.0f, 0.0f); 
            GL11.glVertex3f(5.0f, 0.40f, -5.0f);
            GL11.glVertex3f(-5.0f, 0.40f, -5.0f);
            GL11.glVertex3f(-5.0f, 0.40f, 5.0f);
            GL11.glVertex3f(5.0f, 0.40f, 5.0f);

            // BOARD :
            float c = 0.0f;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {

                    GL11.glColor3f(c, c, c);
                    GL11.glNormal3f(0.0f, -5.0f, 0.0f); 
                    GL11.glVertex3f(-4.0f + i, 0.50f, -4.0f + j);
                    GL11.glVertex3f(-3.0f + i, 0.50f, -4.0f + j);
                    GL11.glVertex3f(-3.0f + i, 0.50f, -3.0f + j);
                    GL11.glVertex3f(-4.0f + i, 0.50f, -3.0f + j);

                    if (j != 7) { c = c == 0.0f ? 1.0f : 0.0f; }
                }
            }
            
            GL11.glColor3f(0.2f, 0.2f, 0.2f);
            // BOTTOM :
            GL11.glVertex3f(5.0f, 0.0f, 5.0f);
            GL11.glVertex3f(-5.0f, 0.0f, 5.0f);
            GL11.glVertex3f(-5.0f, 0.0f, -5.0f);
            GL11.glVertex3f(5.0f, 0.0f, -5.0f);

            GL11.glColor3f(0.3f, 0.3f, 0.3f);
            // BORDERS :
            GL11.glVertex3f(5.0f, 0.40f, 5.0f);
            GL11.glVertex3f(-5.0f, 0.40f, 5.0f);
            GL11.glVertex3f(-5.0f, 0.0f, 5.0f);
            GL11.glVertex3f(5.0f, 0.0f, 5.0f);

            GL11.glVertex3f(5.0f, 0.0f, -5.0f);
            GL11.glVertex3f(-5.0f, 0.0f, -5.0f);
            GL11.glVertex3f(-5.0f, 0.40f, -5.0f);
            GL11.glVertex3f(5.0f, 0.40f, -5.0f);

            GL11.glVertex3f(-5.0f, 0.40f, 5.0f);
            GL11.glVertex3f(-5.0f, 0.40f, -5.0f);
            GL11.glVertex3f(-5.0f, 0.0f, -5.0f);
            GL11.glVertex3f(-5.0f, 0.0f, 5.0f);

            GL11.glVertex3f(5.0f, 0.40f, -5.0f);
            GL11.glVertex3f(5.0f, 0.40f, 5.0f);
            GL11.glVertex3f(5.0f, 0.0f, 5.0f);
            GL11.glVertex3f(5.0f, 0.0f, -5.0f);

            // INNER GRID BORDER
            GL11.glVertex3f(4.0f, 0.50f, 4.0f);
            GL11.glVertex3f(-4.0f, 0.50f, 4.0f);
            GL11.glVertex3f(-4.0f, 0.40f, 4.0f);
            GL11.glVertex3f(4.0f, 0.40f, 4.0f);

            GL11.glVertex3f(4.0f, 0.40f, -4.0f);
            GL11.glVertex3f(-4.0f, 0.40f, -4.0f);
            GL11.glVertex3f(-4.0f, 0.50f, -4.0f);
            GL11.glVertex3f(4.0f, 0.50f, -4.0f);

            GL11.glVertex3f(-4.0f, 0.50f, 4.0f);
            GL11.glVertex3f(-4.0f, 0.50f, -4.0f);
            GL11.glVertex3f(-4.0f, 0.40f, -4.0f);
            GL11.glVertex3f(-4.0f, 0.40f, 4.0f);

            GL11.glVertex3f(4.0f, 0.50f, -4.0f);
            GL11.glVertex3f(4.0f, 0.50f, 4.0f);
            GL11.glVertex3f(4.0f, 0.40f, 4.0f);
            GL11.glVertex3f(4.0f, 0.40f, -4.0f);
            
            // test quad : 
            /*GL11.glColor3f(0.0f, 0.0f, 1.0f);
            GL11.glVertex3f(-3.0f, 0.50f, -2.0f);
            GL11.glVertex3f(-3.0f, 0.50f, -3.0f);
            GL11.glVertex3f(-3.0f, 1.50f, -3.0f);
            GL11.glVertex3f(-3.0f, 1.50f, -2.0f);*/

        } GL11.glEnd();
        
        /**
         * Chess set models display.
         * @see ModelLoaderUtils.createDisplayList method
         * Pawns
         */
        GL11.glCallList(pawnDisplayList);

        GL20.glUseProgram(0);
    }

    /**
     * 
     */
    static void getKeyInput() {

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
