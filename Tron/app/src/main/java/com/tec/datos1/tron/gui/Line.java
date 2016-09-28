package com.tec.datos1.tron.gui;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by josea on 9/27/2016.
 */
public class Line {
    private FloatBuffer VertexBuffer;

    private final String VertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main(){" +
                    "   gl_Position = uMVPMatrix * vPosition;" +
                    "}";
    private final String FragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main(){" +
                    "   gl_FragColor = vColor;" +
                    "}";

    protected int PositionHandle;
    protected int ColorHandle;
    protected int MVPMatrixHandle;
    private final int mProgram;


    static final int COORDS_PER_VERTEX = 3;
    static float LineCoords[] = {
            0.0f, 0.0f, 0.0f,
            -10.0f, 0.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            -10.0f, -1.0f, 0.0f,

            0.0f, 0.0f, 0.0f,
            0.0f, -10.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, -10.0f, 0.0f
    };

    private final int VertexCount = LineCoords.length / COORDS_PER_VERTEX;
    private final int VertexStride = COORDS_PER_VERTEX * 4;

    float color[] = {0.0f, 0.0f, 0.0f, 1.0f};

    public Line() {
        ByteBuffer bb = ByteBuffer.allocateDirect(LineCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        VertexBuffer = bb.asFloatBuffer();
        VertexBuffer.put(LineCoords);
        VertexBuffer.position(0);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, loadShader(GLES20.GL_VERTEX_SHADER, VertexShaderCode));
        GLES20.glAttachShader(
                mProgram, loadShader(GLES20.GL_FRAGMENT_SHADER, FragmentShaderCode));
        GLES20.glLinkProgram(mProgram);

    }

    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(mProgram);
        PositionHandle = GLES20.glGetAttribLocation(mProgram,"vPosition");
        GLES20.glEnableVertexAttribArray(PositionHandle);
        GLES20.glVertexAttribPointer(PositionHandle,COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,false,VertexStride,VertexBuffer);
        ColorHandle = GLES20.glGetUniformLocation(mProgram,"vColor");
        GLES20.glUniform4fv(ColorHandle,1,color,0);
        MVPMatrixHandle = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
        GLES20.glUniformMatrix4fv(MVPMatrixHandle,1,false,mvpMatrix,0);
        GLES20.glDrawArrays(GLES20.GL_LINES,0,VertexCount);
        GLES20.glDisableVertexAttribArray(PositionHandle);
    }

    /**
     * Loads the provided shader in the program.
     */
    private static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}