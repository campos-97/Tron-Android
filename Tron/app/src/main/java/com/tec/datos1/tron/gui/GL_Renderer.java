package com.tec.datos1.tron.gui;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.concurrent.TimeUnit;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *  OpenGL Custom renderer used with GLSurfaceView
 */
public class GL_Renderer implements GLSurfaceView.Renderer {
    private Context context;   // Application's context
    private Node node;
    private Line line;

    /** Rotation increment per frame. */
    private static final float CUBE_ROTATION_INCREMENT = 0.6f;

    /** The refresh rate, in frames per second. */
    private static final int REFRESH_RATE_FPS = 60;

    /** The duration, in milliseconds, of one frame. */
    private static final float FRAME_TIME_MILLIS = TimeUnit.SECONDS.toMillis(1) / REFRESH_RATE_FPS;

    private  float[] mMVPMatrix;
    private  float[] mProjectionMatrix;
    private  float[] mViewMatrix;
    private  float[] mRotationMatrix;
    private  float[] mFinalMVPMatrix;

    private  float[] gMVPMatrix;
    private  float[] gRotationMatrix;
    private  float[] gFinalMVPMatrix;

    private float mCubeRotation;
    private long mLastUpdateMillis;

    public float zoom = 1;
    private float ratio;
    public float movex = 0;
    public float movey = 0;
    public float movez = 0;

    // Constructor with global application context
    public GL_Renderer(Context context) {
        this.context = context;

        mMVPMatrix = new float[16];
        mProjectionMatrix = new float[16];
        mViewMatrix = new float[16];
        mRotationMatrix = new float[16];
        mFinalMVPMatrix = new float[16];

        gMVPMatrix = new float[16];
        gRotationMatrix = new float[16];
        gFinalMVPMatrix = new float[16];

        // Set the fixed camera position (View matrix).
        Matrix.setLookAtM(mViewMatrix, 0, 0.0f, 0.0f, -4.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

    }

    @Override
    public void onSurfaceCreated(GL10 unused,EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(255.0f, 255.0f, 255.0f, 255.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        node = new Node(0,0);
        line= new Line();
        node.loadTexture(unused, context);    // Load image into Texture (NEW)
        unused.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)

    }

    @Override
    public void onSurfaceChanged(GL10 unused,int width, int height) {
        ratio = (float) width / height;

        GLES20.glViewport(0, 0, width, height);
        // This projection matrix is applied to object coordinates in the onDrawFrame() method.
        Matrix.frustumM(mProjectionMatrix, 0, -ratio*zoom, ratio*zoom, -1.0f*zoom, 1.0f*zoom, 1.0f, 7.0f);
        //Grid view
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.multiplyMM(gMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        //Grid Scale
        Matrix.scaleM(gMVPMatrix, 0, 1.0f,1.0f, 1.0f);
        Matrix.scaleM(mMVPMatrix, 0, 8f,8f, 8f);
        //Move Model
        Matrix.translateM(gMVPMatrix, 0, 0.0f, 0f, 0f);
        //Grid rotation.
        Matrix.rotateM(gMVPMatrix, 0, 0.0f, 0, 0, 1.0f);
        Matrix.setRotateM(gRotationMatrix, 0, mCubeRotation, 1.0f, 1.0f, 1.0f);


    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setLookAtM(mViewMatrix, 0, movex, movey, -4.0f, movex, movey, 0.0f, 0.0f, 1.0f, 0.0f);

        Matrix.frustumM(mProjectionMatrix, 0, -ratio*zoom, ratio*zoom, -1.0f*zoom, 1.0f*zoom, 1.0f, 7.0f);
        Matrix.multiplyMM(gMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        //Grid rotation.
        Matrix.rotateM(gMVPMatrix, 0, 0.0f, 0, 0, 1.0f);

        Matrix.translateM(gMVPMatrix,0,movex,movey,movez);
        // Apply the rotation.
        Matrix.setRotateM(gRotationMatrix, 0, mCubeRotation, 1.0f, 1.0f, 1.0f);

        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(gFinalMVPMatrix, 0, gMVPMatrix, 0, gRotationMatrix, 0);
        Matrix.multiplyMM(mFinalMVPMatrix, 0, mMVPMatrix, 0, gRotationMatrix, 0);

        line.draw(mFinalMVPMatrix);
        node.draw(gFinalMVPMatrix);
    }
}