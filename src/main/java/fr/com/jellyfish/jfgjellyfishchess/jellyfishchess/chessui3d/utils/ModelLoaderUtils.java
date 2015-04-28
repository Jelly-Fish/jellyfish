/**
 * Copyright (c) 2013, Oskar Veerhoek All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of the FreeBSD Project.
 *
 *
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
package chessboardopengl.utils;

import chessboardopengl.openglentities.OpenGLMdl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glMaterialf;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author Oskar
 * @author thw
 */
public class ModelLoaderUtils {

    /**
     * @param m
     * @param margins
     * @param magins
     * @param rgb
     * @return
     */
    public static int createDisplayList(final OpenGLMdl m, final float[] margins,
            final float[] rgb) {

        int displayList = glGenLists(1);
        float mx =  + margins[0];
        float my =  + margins[1];
        float mz =  + margins[2];
        
        glNewList(displayList, GL_COMPILE);
        {
            glMaterialf(GL_FRONT, GL_SHININESS, 120);
            glColor3f(rgb[0], rgb[1], rgb[2]);
            glBegin(GL_TRIANGLES);
            for (OpenGLMdl.Face face : m.getFaces()) {
                
                if (face.hasNormals()) {
                    Vector3f n1 = m.getNormals().get(face.getNormalIndices()[0] - 1);
                    glNormal3f(n1.x + mx, n1.y + my, n1.z + mz);
                }
                
                Vector3f v1 = m.getVertices().get(face.getVertexIndices()[0] - 1);
                glVertex3f(v1.x + mx, v1.y + my, v1.z + mz);
                
                if (face.hasNormals()) {
                    Vector3f n2 = m.getNormals().get(face.getNormalIndices()[1] - 1);
                    glNormal3f(n2.x + mx, n2.y + my, n2.z + mz);
                }
                
                Vector3f v2 = m.getVertices().get(face.getVertexIndices()[1] - 1);
                glVertex3f(v2.x + mx, v2.y + my, v2.z + mz);
                
                if (face.hasNormals()) {
                    Vector3f n3 = m.getNormals().get(face.getNormalIndices()[2] - 1);
                    glNormal3f(n3.x + mx, n3.y + my, n3.z + mz);
                }
                
                Vector3f v3 = m.getVertices().get(face.getVertexIndices()[2] - 1);
                glVertex3f(v3.x + mx, v3.y + my, v3.z + mz);
            }
            glEnd();
        }
        glEndList();
        return displayList;
    }

    /**
     *
     * @param m
     * @return
     */
    public static int getModelDisplayList(final OpenGLMdl m) {

        final int displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        {
            glMaterialf(GL_FRONT, GL_SHININESS, 120);
            glColor3f(0.4f, 0.27f, 0.17f);
            glBegin(GL_TRIANGLES);
            for (OpenGLMdl.Face face : m.getFaces()) {
                if (face.hasNormals()) {
                    Vector3f n1 = m.getNormals().get(face.getNormalIndices()[0] - 1);
                    glNormal3f(n1.x, n1.y, n1.z);
                }
                Vector3f v1 = m.getVertices().get(face.getVertexIndices()[0] - 1);
                glVertex3f(v1.x, v1.y, v1.z);
                if (face.hasNormals()) {
                    Vector3f n2 = m.getNormals().get(face.getNormalIndices()[1] - 1);
                    glNormal3f(n2.x, n2.y, n2.z);
                }
                Vector3f v2 = m.getVertices().get(face.getVertexIndices()[1] - 1);
                glVertex3f(v2.x, v2.y, v2.z);
                if (face.hasNormals()) {
                    Vector3f n3 = m.getNormals().get(face.getNormalIndices()[2] - 1);
                    glNormal3f(n3.x, n3.y, n3.z);
                }
                Vector3f v3 = m.getVertices().get(face.getVertexIndices()[2] - 1);
                glVertex3f(v3.x, v3.y, v3.z);
            }
            glEnd();
        }
        glEndList();
        return displayList;
    }

    /**
     *
     * @param f
     * @param margins
     * @return OpenGLMdl
     * @throws IOException
     */
    public static OpenGLMdl loadModel(final File f) throws IOException {

        final BufferedReader reader = new BufferedReader(new FileReader(f));
        final OpenGLMdl m = new OpenGLMdl();
        String line;
        while ((line = reader.readLine()) != null) {
            String prefix = line.split(" ")[0];
            if (prefix.equals("v")) {
                m.getVertices().add(parseVertex(line));
            } else if (prefix.equals("vn")) {
                m.getNormals().add(parseNormal(line));
            } else if (prefix.equals("f")) {
                m.getFaces().add(parseFace(m.hasNormals(), line));
            } else {
                Logger.getLogger(ModelLoaderUtils.class.getName()).log(Level.SEVERE, null,
                        "model file .obj contains lines which cannot or will not be parsed:\n" + line);
            }
        }
        reader.close();
        return m;
    }

    /**
     * @param line
     * @return Vector3f
     */
    private static Vector3f parseVertex(final String line) {

        String[] xyz = line.split(" ");
        float x = Float.valueOf(xyz[1]);
        float y = Float.valueOf(xyz[2]);
        float z = Float.valueOf(xyz[3]);
        return new Vector3f(x, y, z);
    }

    /**
     * @param line
     * @return Vector3f
     */
    private static Vector3f parseNormal(final String line) {

        String[] xyz = line.split(" ");
        float x = Float.valueOf(xyz[1]);
        float y = Float.valueOf(xyz[2]);
        float z = Float.valueOf(xyz[3]);
        return new Vector3f(x, y, z);
    }

    /**
     * @param hasNormals
     * @param line
     * @return OpenGLMdl.Face
     */
    private static OpenGLMdl.Face parseFace(final boolean hasNormals, final String line) {

        String[] faceIndices = line.split(" ");
        int[] vertexIndicesArray = {Integer.parseInt(faceIndices[1].split("/")[0]),
            Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
        
        if (hasNormals) {
            int[] normalIndicesArray = new int[3];
            normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
            normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
            normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
            return new OpenGLMdl.Face(vertexIndicesArray, normalIndicesArray);
        } else {
            return new OpenGLMdl.Face((vertexIndicesArray));
        }
    }

}
