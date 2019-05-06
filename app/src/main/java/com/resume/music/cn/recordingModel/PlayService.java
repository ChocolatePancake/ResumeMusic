package com.resume.music.cn.recordingModel;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.resume.music.cn.App;

import java.io.IOException;

import cn.control.c.com.ccontrol.MTimeTask;

public class PlayService {
    private static PlayService playService;

    private String url = "";

    private MediaPlayer mediaPlayer;

    private PlayListener playListener;

    private int currentProgress = 0;

    private int maxTime = 0;

    public static PlayService getInstance() {
        synchronized (PlayService.class) {
            if (playService == null)
                playService = new PlayService();
            return playService;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPlayListener(PlayListener playListener) {
        this.playListener = playListener;
    }

    public void setSeetTo(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    public void switchMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playMusic() {
        if (mediaPlayer == null) {//如果没有歌
            mediaPlayer = new MediaPlayer();
            //设置 mediaPlayer音频流的类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(App.getInstance(), Uri.parse(url));

                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //设置准备的监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    //获得音乐的播放时间
                    int time = mediaPlayer.getDuration();
                    maxTime = time;
                    //设置最大值为音乐播放的时间
                    if (playListener != null) {
                        playListener.maxTime(time);
                        playListener.onStatus(true);
                    }
                }
            });
        } else if (mediaPlayer.isPlaying()) {//如果正在播放
            mediaPlayer.pause();
            if (playListener != null) {
                playListener.onStatus(true);
            }
        } else {
            mediaPlayer.start();
            if (playListener != null) {
                playListener.onStatus(false);
            }
        }
    }

    public static abstract class PlayListener {
        abstract void maxTime(int time);

        abstract void onProgress(int progress);

        abstract void onStatus(boolean play);
    }
}
