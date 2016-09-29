package com.tec.datos1.tron.Menu;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.tec.datos1.tron.R;

public class MusicPlayer extends AsyncTask<Void, Void, Void> {
    Context mContext;

    public MusicPlayer(Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        MediaPlayer player = MediaPlayer.create(mContext, R.raw.pista1);
        player.setLooping(true);
        player.setVolume(1.0f,1.0f);
        player.start();
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }
}
