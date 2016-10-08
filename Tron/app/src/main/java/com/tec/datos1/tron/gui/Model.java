package com.tec.datos1.tron.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import android.widget.Switch;

import com.tec.datos1.tron.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * A two-dimensional square for use as a drawn object in OpenGL ES 2.0.
 * @author Andres Campos
 */
public class Model {

    public String id;
    public float x;
    public float y;
    public boolean isMe;
    public String color;
    public float orientation;
    public List<Trail> trail =  new ArrayList<>();
    public int trailNum = 3;
    public Shield shield;
    public boolean hasShield = false;

    public synchronized int getTrailNum(){
        return this.trailNum;
    }
    public synchronized void setTrailNum(int num){
        this.trailNum = num;
    }

    public static final String CUBE_MESH_VERTEX_SHADER = " \n" + "\n"
            + "attribute vec4 vertexPosition; \n"
            + "attribute vec4 vertexNormal; \n"
            + "attribute vec2 vertexTexCoord; \n" + "\n"
            + "varying vec2 texCoord; \n" + "varying vec4 normal; \n" + "\n"
            + "uniform mat4 modelViewProjectionMatrix; \n" + "\n"
            + "void main() \n" + "{ \n"
            + "   gl_Position = modelViewProjectionMatrix * vertexPosition; \n"
            + "   normal = vertexNormal; \n" + "   texCoord = vertexTexCoord; \n"
            + "} \n";

    public static final String CUBE_MESH_FRAGMENT_SHADER = " \n" + "\n"
            + "precision mediump float; \n" + " \n" + "varying vec2 texCoord; \n"
            + "varying vec4 normal; \n" + " \n"
            + "uniform sampler2D texSampler2D; \n" + " \n" + "void main() \n"
            + "{ \n" + "   gl_FragColor = texture2D(texSampler2D, texCoord); \n"
            + "} \n";

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final FloatBuffer texBuffer;
    public FloatBuffer colorBuffer;
    private int mPositionHandle;
    private int texSampler2DHandle;
    private int mvpMatrixHandle;
    private int mMVPMatrixHandle;
    private int textureCoordHandle;
    private int mProgram;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    private final float squareCoords[] = {
            -0.5f,  0.5f, -0.5f,   // top left
            -0.5f, -0.5f, -0.5f,   // bottom left
            0.5f, -0.5f, -0.5f,   // bottom right
            0.5f,  0.5f, -0.5f }; // top right
    private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    static float[] texCoords =
            {
                    0f,0f,
                    0f,1f,
                    1f,1f,
                    1f,0f
            };
    int[] textureIDs = new int[3]; // array for 1 texture-id


    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

     float[] blue = {  0.0f, 0.0f, 1.0f, 1.0f};
    float[] green = { 0.5f, 1.0f, 0.5f, 1.0f };
    float[] yellow = { 1.0f, 1.0f, 0.0f , 1.0f };
    float[] red = { 1.0f, 0.0f, 0.0f ,1.0f};
    float[] purple = { 1.0f, 0.0f, 1.0f, 1.0f };


    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Model(float addX, float addY,int amount,String color) {

        float[] thisColor = blue;
        if(color.startsWith("blue")){
            thisColor = blue;
        }else if(color.startsWith("green")){
            thisColor = green;
        }else if(color.startsWith("red")){
            thisColor = red;
        }else if(color.startsWith("yellow")){
            thisColor = yellow;
        }else if (color.startsWith("gray")){
            thisColor = purple;
        }

        shield = new Shield(thisColor);

        int i = amount;
        while(i>=0){
            Trail t = new Trail(thisColor);
            trail.add(t);
            i--;
        }

        //Change x and y values for the vertices.
        Log.d("Grid", String.valueOf(squareCoords[0]));
        squareCoords[0] += addX;
        squareCoords[3] += addX;
        squareCoords[6] += addX;
        squareCoords[9] += addX;
        squareCoords[1] -= addY;
        squareCoords[4] -= addY;
        squareCoords[7] -= addY;
        squareCoords[10] -= addY;

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // Setup texture-coords-array buffer, in float. An float has 4 bytes (NEW)
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.LITTLE_ENDIAN);
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, loadShader(GLES20.GL_VERTEX_SHADER, CUBE_MESH_VERTEX_SHADER));
        GLES20.glAttachShader(
                mProgram, loadShader(GLES20.GL_FRAGMENT_SHADER, CUBE_MESH_FRAGMENT_SHADER));
        GLES20.glLinkProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vertexPosition");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "modelViewProjectionMatrix");
        textureCoordHandle = GLES20.glGetAttribLocation(mProgram, "vertexTexCoord");
        textureCoordHandle = GLES20.glGetAttribLocation(mProgram, "vertexTexCoord");
        texSampler2DHandle = GLES20.glGetUniformLocation(mProgram,
                "texSampler2D");
        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram,
                "modelViewProjectionMatrix");
        //mColorHandle = GLES20.glGetAttribLocation(mProgram,"a_Color");

    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix The Model View Project matrix in which to draw this shape
     */


    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment.
        GLES20.glUseProgram(mProgram);

        // Prepare the cube coordinate data.
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT,
                false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(textureCoordHandle, 2,
                GLES20.GL_FLOAT, false, 0, texBuffer);
        //GLES20.glVertexAttribPointer(mColorHandle, 4,
              //  GLES20.GL_FLOAT, false, 0, colorBuffer);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(textureCoordHandle);
        //GLES20.glEnableVertexAttribArray(mColorHandle);

        // activate texture 0, bind it, and pass to shader
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GL10.GL_TEXTURE_2D,textureIDs[0]);
        GLES20.glUniform1i(texSampler2DHandle, 0);

        // Apply the projection and view transformation.
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the cube.
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // disable the enabled arrays
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(textureCoordHandle);
       // GLES20.glDisableVertexAttribArray(mColorHandle);
    }

    /** Loads the provided shader in the program. */
    private static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void loadTexture(GL10 gl, Context context,String color) {
        InputStream istream = context.getResources().openRawResource(R.raw.graybike);

        if(color.startsWith("blue")){
            istream = context.getResources().openRawResource(R.raw.bluebike);
        }else if(color.startsWith("red")){
            istream = context.getResources().openRawResource(R.raw.redbike);
        } else if (color.startsWith("green")) {
            istream = context.getResources().openRawResource(R.raw.greenbike);
        }else if(color.startsWith("yellow")){
            istream = context.getResources().openRawResource(R.raw.yellowbike);
        }else if (color.startsWith("gray")){
            istream = context.getResources().openRawResource(R.raw.graybike);
        }else if(color.startsWith("bomb")){
            istream = context.getResources().openRawResource(R.raw.bomb);
        }
        else if(color.startsWith("fuel")){
            istream = context.getResources().openRawResource(R.raw.fuel);
        }
        else if(color.startsWith("turbo")){
            istream = context.getResources().openRawResource(R.raw.turbo);
        }
        else if(color.startsWith("trail")){
            istream = context.getResources().openRawResource(R.raw.trail);
        }else if(color.startsWith("shield")){
            istream =  context.getResources().openRawResource(R.raw.shield);
        }

        Bitmap bitmap;
        try {
            // Read and decode input as bitmap
            bitmap = BitmapFactory.decodeStream(istream);
        } finally {
            try {
                istream.close();
            } catch(IOException e) { }
        }

        gl.glGenTextures(3, textureIDs, 0);  // Generate texture-ID array for 3 textures (NEW)

        // Create Nearest Filtered Texture and bind it to texture 0 (NEW)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        // Create Linear Filtered Texture and bind it to texture 1 (NEW)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[1]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        // Create mipmapped textures and bind it to texture 2 (NEW)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[2]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
        if(gl instanceof GL11) {
            gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();
    }

    public void updateTrail(float lastx, float lasty, float lastOrientation){
        Log.d("trail", "updateTrail");
        boolean flag = false;
        for(int i = 0; i < this.trailNum ; i++){
            if(i < this.trail.size()){
                if (trail.get(i).id == null) {
                    trail.get(i).x = lastx;
                    trail.get(i).y = lasty;
                    trail.get(i).id = "trail";
                    trail.get(i).orientation = lastOrientation;
                    flag = true;
                    break;
                }
            }
        }
        if(!flag){
            for(int i = 0; i < this.trailNum; i++){
                if(i < this.trail.size()-1) {
                    if (trail.get(i + 1).id != null) {
                        trail.get(i).x = trail.get(i + 1).x;
                        trail.get(i).y = trail.get(i + 1).y;
                        trail.get(i).orientation = trail.get(i + 1).orientation;
                    } else {
                        trail.get(i).x = lastx;
                        trail.get(i).y = lasty;
                        trail.get(i).orientation = orientation;
                    }
                }
            }
        }
    }
}