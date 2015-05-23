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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 * Orinig author :
 *
 * @author Mark Bernard date: 26-May-2004 Port of NeHe's Lesson 13 to LWJGL
 * Title: Bitmap fonts Uses version 0.9alpha of LWJGL http://www.lwjgl.org/
 * Ported directly from the C++ version
 *
 * @author thw Refactor and glPrint overloading for printing many lines
 * considering \n in param strings.
 */
public class OPENGLFontHelper {

    private static final Color OPAQUE_BLACK = Color.BLACK;
    private static final Color TRANSPARENT_WHITE = new Color(255, 255, 255, 255);
    private int texture;
    public static final float X_TRANSLATION1 = 0.05f;
    private Vector3f startPosition = new Vector3f();

    /**
     * List of messages to display.
     */
    private List<String> msgQueue = new LinkedList<>();

    /**
     * Base Display List For The Font Set.
     */
    private int base;

    /**
     * constructor.
     *
     * @param xStart
     * @param yStart
     * @param zStart
     */
    public OPENGLFontHelper(final float xStart, final float yStart, final float zStart) {

        startPosition.x = xStart;
        startPosition.y = yStart;
        startPosition.z = zStart;
    }

    /**
     * Append to text array.
     *
     * @param msg
     */
    public void append(final String msg) {

        if (msg.contains("\n")) {
            final String[] s = msg.split("\n");
            if (s.length > 1) {
                msgQueue.addAll(Arrays.asList(s));
            } else {
                msgQueue.add(msg.replaceAll("\n", ""));
            }
        }
    }

    /**
     * Print all queue messages.
     */
    public void glPrintAll() {

        if (msgQueue.size() > 0) {
            float xTranslate = glPrint(msgQueue.get(msgQueue.size() - 1), startPosition.x, startPosition.y, startPosition.z, OPENGLFontHelper.X_TRANSLATION1);
            for (int i = msgQueue.size(); i >= 0; i--) {
                xTranslate = glPrint(msgQueue.get(i), xTranslate, startPosition.y, startPosition.z, xTranslate);
            }
        }
    }

    /**
     * Custom GL "Print" Routine.
     *
     * @param msg
     */
    public void glPrint(final String msg) {

        if (msg != null) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
            for (int i = 0; i < msg.length(); i++) {
                GL11.glCallList(base + msg.charAt(i));
                GL11.glTranslatef(0.05f, 0.0f, 0.0f);
            }
        }
    }

    /**
     * Custom GL "Print" Routine overload 1.
     *
     * @param msg
     * @param y
     * @param x
     * @param z
     * @return
     */
    private float glPrint(final String msg, final float x, final float y, final float z, final float translationX) {

        float xT = 0.0f;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        for (int i = 0; i < msg.length(); i++) {
            if (i == 0) {
                GL11.glTranslatef(x + translationX, y, z);
            } else {
                GL11.glTranslatef(translationX, 0.0f, 0.0f);
            }
            GL11.glCallList(base + msg.charAt(i));
            xT -= translationX;
        }

        return xT;
    }

    /**
     * The original tutorial number 13 used a windows specific extension to
     * generate a bitmap for the font. Custom bitmap generation that you see
     * below.
     *
     * @param fontName
     */
    public void buildFont(final String fontName) {

        Font font;

        /**
         * Note that this will work well with monospace fonts, but does not look
         * as good with proportional fonts.
         */
        BufferedImage fontImage; // image for creating the bitmap
        int bitmapSize = 512; // set the size for the bitmap texture
        boolean sizeFound = false;
        boolean directionSet = false;
        int delta = 0;
        int fontSize = 24;

        /**
         * To find out how much space a Font takes, you need to use a the
         * FontMetrics class. To get the FontMetrics, you need to get it from a
         * Graphics context. A Graphics context is only available from a
         * displayable surface, ie any class that subclasses Component or any
         * Image. First the font is set on a Graphics object. Then get the
         * FontMetrics and find out the width and height of the widest character
         * (W). Then take the largest of the 2 values and find the maximum size
         * font that will fit in the size allocated.
         */
        while (!sizeFound) {
            font = new Font(fontName, Font.PLAIN, fontSize);
            // use BufferedImage.TYPE_4BYTE_ABGR to allow alpha blending
            fontImage = new BufferedImage(bitmapSize, bitmapSize, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = (Graphics2D) fontImage.getGraphics();
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int width = fm.stringWidth("W");
            int height = fm.getHeight();
            int lineWidth = (width > height) ? width * 16 : height * 16;
            if (!directionSet) {
                if (lineWidth > bitmapSize) {
                    delta = -2;
                } else {
                    delta = 2;
                }
                directionSet = true;
            }
            if (delta > 0) {
                if (lineWidth < bitmapSize) {
                    fontSize += delta;
                } else {
                    sizeFound = true;
                    fontSize -= delta;
                }
            } else if (delta < 0) {
                if (lineWidth > bitmapSize) {
                    fontSize += delta;
                } else {
                    sizeFound = true;
                    fontSize -= delta;
                }
            }
        }

        /**
         * Now that a font size has been determined, create the final image, set
         * the font and draw the standard/extended ASCII character set for that
         * font.
         */
        font = new Font(fontName, Font.PLAIN, fontSize); // Font Name
        // use BufferedImage.TYPE_4BYTE_ABGR to allow alpha blending
        fontImage = new BufferedImage(bitmapSize, bitmapSize, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) fontImage.getGraphics();
        g.setFont(font);
        g.setColor(OPAQUE_BLACK);
        g.setBackground(TRANSPARENT_WHITE);
        FontMetrics fm = g.getFontMetrics();
        for (int i = 0; i < 256; i++) {
            int x = i % 16;
            int y = i / 16;
            char ch[] = {(char) i};
            String temp = new String(ch);
            g.drawString(temp, (x * 32) + 1, (y * 32) + fm.getAscent());
        }

        /**
         * The following code is taken directly for the LWJGL example code. It
         * takes a Java Image and converts it into an OpenGL texture. This is a
         * very powerful feature as you can use this to generate textures on the
         * fly out of anything.
         *
         * Flip image :
         */
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -fontImage.getHeight(null));
        AffineTransformOp op
                = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        fontImage = op.filter(fontImage, null);

        // Put Image In Memory :
        ByteBuffer scratch
                = ByteBuffer.allocateDirect(
                        4 * fontImage.getWidth() * fontImage.getHeight());

        byte data[]
                = (byte[]) fontImage.getRaster().getDataElements(
                        0,
                        0,
                        fontImage.getWidth(),
                        fontImage.getHeight(),
                        null);
        scratch.clear();
        scratch.put(data);
        scratch.rewind();

        // Create A IntBuffer For Image Address In Memory :
        IntBuffer buf
                = ByteBuffer
                .allocateDirect(4)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        GL11.glGenTextures(buf); // Create Texture In OpenGL.

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0)); // Typical Texture Generation Using Data From The Image.

        // Linear Filtering
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        // Linear Filtering
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        // Generate The Texture
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA,
                fontImage.getWidth(), fontImage.getHeight(), 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, scratch);

        texture = buf.get(0); // Return Image Address In Memory.

        base = GL11.glGenLists(256); // Storage For 256 Characters.

        /**
         * Generate the display lists. One for each character in the
         * standard/extended ASCII chart.
         */
        float textureDelta = 1.0f / 16.0f;
        for (int i = 0; i < 256; i++) {
            float u = ((float) (i % 16)) / 16.0f;
            float v = 1.f - (((float) (i / 16)) / 16.0f);
            GL11.glNewList(base + i, GL11.GL_COMPILE);
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
                GL11.glBegin(GL11.GL_QUADS);
                {
                    GL11.glTexCoord2f(u, v);
                    GL11.glVertex3f(-0.0450f, 0.0450f, 0.0f);
                    GL11.glTexCoord2f((u + textureDelta), v);
                    GL11.glVertex3f(0.0450f, 0.0450f, 0.0f);
                    GL11.glTexCoord2f((u + textureDelta), v - textureDelta);
                    GL11.glVertex3f(0.0450f, -0.0450f, 0.0f);
                    GL11.glTexCoord2f(u, v - textureDelta);
                    GL11.glVertex3f(-0.0450f, -0.0450f, 0.0f);
                }
                GL11.glEnd();
            }
            GL11.glEndList();
        }
    }

}
