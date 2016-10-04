package com.tec.datos1.tron.gui;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *  OpenGL Custom renderer used with GLSurfaceView
 *  @author Andres Campos
 */
public class GL_Renderer implements GLSurfaceView.Renderer {
    private Context context;   // Application's context
    private Trail trail;
    private Line line;

    public GL10 gl;

    private List<Model> players = new ArrayList<Model>();
    private List<Model> items = new ArrayList<>();

    /** The refresh rate, in frames per second. */
    private static final int REFRESH_RATE_FPS = 60;

    private  float[] mMVPMatrix;
    private  float[] mProjectionMatrix;
    private  float[] mViewMatrix;
    private  float[] mRotationMatrix;
    private  float[] mFinalMVPMatrix;

    private  float[] gMVPMatrix;
    private  float[] gRotationMatrix;
    private  float[] gFinalMVPMatrix;

    public float zoom = 2;
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
        Matrix.setLookAtM(mViewMatrix, 0, 0.0f, 0.0f, -7.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

    }

    /**
     * Sets everything once a view gets created.
     * @param unused
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 unused,EGLConfig config) {

        gl = unused;
        // Set the background frame color
        GLES20.glClearColor(255.0f, 255.0f, 255.0f, 255.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        trail = new Trail();
        line = new Line();
        unused.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture (NEW)

        String[] mainColors = {"blue","green","red","yellow"};
        int i= 0;
        while(i<10){
            if(i<4){
                Model player = new Model(0,0,50);
                player.loadTexture(unused, context,mainColors[i]);
                player.color = mainColors[i];
                players.add(player);
                i++;
            }else{
                Model player = new Model(0,0,3);
                player.loadTexture(unused, context,"gray");
                players.add(player);
                i++;
            }
        }
        int itms = 0;
        while (itms<50){
            Model item = new Model(0,0,0);
            if(itms<10){
                item.loadTexture(unused,context,"bomb");
                item.color = "bomb";
            }else if(itms<20){
                item.loadTexture(unused,context,"fuel");
                item.color = "fuel";
            }else if(itms<30){
                item.loadTexture(unused,context,"shield");
                item.color = "shield";
            }else if(itms<40){
                item.loadTexture(unused,context,"turbo");
                item.color = "turbo";
            }else if(itms<50){
                item.loadTexture(unused,context,"trail");
                item.color = "trail";
            }
            this.items.add(item);
            itms++;
        }
    }

    /**
     * Updates everything if the surface changes.
     * @param unused
     * @param width
     * @param height
     */
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
        Matrix.scaleM(gMVPMatrix, 0, 2.0f,2.0f, 2.0f);
        //Matrix.scaleM(mMVPMatrix, 0, 8f,8f, 8f);
        //Move Model
        Matrix.translateM(gMVPMatrix, 0, 0.0f, 0f, 0f);
        //Grid rotation.
        Matrix.rotateM(gMVPMatrix, 0, 0.0f, 0, 0, 1.0f);
        Matrix.setRotateM(gRotationMatrix, 0, 0, 1.0f, 1.0f, 1.0f);

    }

    /**
     * Constantly updates everything.
     * @param unused
     */
    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //Matrix.setLookAtM(mViewMatrix, 0, movex, movey, -4.0f, movex, movey, 0.0f, 0.0f, 1.0f, 0.0f);

        Matrix.frustumM(mProjectionMatrix, 0, -ratio*zoom, ratio*zoom, -1.0f*zoom, 1.0f*zoom, 1.0f, 7.0f);
        Matrix.multiplyMM(gMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        //Grid rotation.
        Matrix.rotateM(gMVPMatrix, 0, 180.0f, 0.0f, 0, 1.0f);

        Matrix.translateM(gMVPMatrix,0,movex,movey,movez);
        // Apply the rotation.
        Matrix.setRotateM(gRotationMatrix, 0, 0, 1.0f, 1.0f, 1.0f);

        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(gFinalMVPMatrix, 0, gMVPMatrix, 0, gRotationMatrix, 0);
        Matrix.multiplyMM(mFinalMVPMatrix, 0, mMVPMatrix, 0, gRotationMatrix, 0);

        //Matrix.setLookAtM(mViewMatrix, 0, 0.0f, 0.0f, -6.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        line.draw(mFinalMVPMatrix);

        for (Model player:players){
            if(player.id != null){
                if(player.isMe == true){
                    Matrix.setLookAtM(mViewMatrix, 0, -player.x, -player.y, -7.0f, -player.x, -player.y, 0.0f, 0.0f, 1.0f, 0.0f);
                }
                Matrix.multiplyMM(gMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                Matrix.translateM(gMVPMatrix,0,-player.x,-player.y,movez);
                Matrix.rotateM(gMVPMatrix, 0, player.orientation, 0.0f, 0, 1.0f);
                Matrix.multiplyMM(gFinalMVPMatrix, 0, gMVPMatrix, 0, gRotationMatrix, 0);
                player.draw(gFinalMVPMatrix);
                for(int i = 0; i < player.trailNum ; i++){
                    if(player.trail.get(i).id != null){
                        Log.d("trail", "drawTrail: "+player.trail.get(i).id);
                        Matrix.multiplyMM(gMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                        Matrix.translateM(gMVPMatrix,0,-player.trail.get(i).x,-player.trail.get(i).y,movez);
                        Matrix.rotateM(gMVPMatrix, 0, player.trail.get(i).orientation, 0.0f, 0, 1.0f);
                        Matrix.multiplyMM(gFinalMVPMatrix, 0, gMVPMatrix, 0, gRotationMatrix, 0);
                        player.trail.get(i).draw(gFinalMVPMatrix);
                    }
                }
            }
        }
        for(Model bomb: items){
            if(bomb.id != null){
                Matrix.multiplyMM(gMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                Matrix.translateM(gMVPMatrix,0,-bomb.x,-bomb.y,movez);
                Matrix.rotateM(gMVPMatrix, 0, 0.0f, 0.0f, 0, 1.0f);

                Matrix.multiplyMM(gFinalMVPMatrix, 0, gMVPMatrix, 0, gRotationMatrix, 0);
                bomb.draw(gFinalMVPMatrix);
            }
        }
        //trail.draw(gFinalMVPMatrix);
    }

    /**
     * Used to update the players positions.
     * @param id
     * @param x
     * @param y
     */
    public void updatePlayers(String id, float x, float y){
        Log.d("game", "updatePlayers: "+id);
        boolean found = false;
        for(Model player:players){
            if(player.id!=null) {
                player.updateTrail(player.x, player.y, player.orientation);
                if (player.id.equals(id)) {
                    Log.d("game", "Found: " + player.id);
                    found = true;
                    if(player.x < x){
                        player.orientation = 180;
                    }else if(player.x > x){
                        player.orientation = 0;
                    }else if(player.y < y){
                        player.orientation = -90;
                    }else if(player.y > y){
                        player.orientation = 90;
                    }
                    player.x = x;
                    player.y = y;
                    movex = x;
                    movey = y;
                    break;
                }
            }else{
                break;
            }
        }
        if(found == false){
            addPlayer(id,x,y);
        }
    }

    /**
     * Adds a new player if needed.
     * @param id
     * @param x
     * @param y
     */
    public void addPlayer(String id, float x, float y){
        Log.d("game", "addPlayer: "+id);
        for(Model player : players){
            if(player.id == null && player.color != null) {
                if (player.color.startsWith(id)) {
                    player.id = id;
                    player.x = x;
                    player.y = y;
                    break;
                } else {
                    player.id = id;
                    player.x = x;
                    player.y = y;
                    break;
                }
            }
        }
    }

    /**
     * Adds the user player.
     * @param id
     * @param isMe
     */
    public void addPlayer(String id,boolean isMe){
        for(Model player : players){
            if(player.id == null){
                if(player.color.startsWith(id)){
                    players.get(0).id = id;
                    players.get(0).isMe = isMe;
                    break;
                }
            }
        }
    }

    public void addItem(String type,int x, int y){
        for (Model item: items){
            if(item.id == null){
                if(item.color.startsWith(type)){
                    if(item.x == x && item.y==y){
                        break;
                    }else if(item.id == null){
                        item.x = x;
                        item.y = y;
                        item.id = type;
                        break;
                    }
                }
            }
        }
    }

    public void destroyPlayer(String id){
        Log.d("kill", "destroyPlayer: "+id);
        for(Model player: players){
            if(player.id != null) {
                if (player.id.startsWith(id)) {
                    player.id = null;
                }
            }
        }
    }

    public void removeItem(String type, int x, int y){
        Log.d("kill", "removeItem: "+type);
        for (Model item: items){
            if(item.id != null) {
                if (item.color.startsWith(type)) {
                    if(item.x == x && item.y == y) {
                        item.id = null;
                        break;
                    }
                }
            }
        }
    }

    public void addTrail(String id){
        for(Model player: players){
            if(player.id != null) {
                if (player.id.startsWith(id)) {
                    player.trailNum += 1;
                }
            }
        }
    }

}