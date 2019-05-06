package com.resume.music.cn.recordingModel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.resume.music.cn.R;

import java.util.List;

import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.plog;

import static tech.com.commoncore.avdb.AVDbManager.MEDIA_FILE;
import static tech.com.commoncore.avdb.AVDbManager.MEDIA_FILE_NAME;
import static tech.com.commoncore.avdb.AVDbManager.TARGET_AVUSER;
import static tech.com.commoncore.avdb.AVDbManager.USER_HEAD_ICON;
import static tech.com.commoncore.avdb.AVDbManager.USER_NICK_NAME;

public class PlayView extends LinearLayout {

    private View mView;
    private ImageView headImg, switchImg, nextImg;
    private TextView musicNameTx, userNameTx;
    private SeekBar seekBar;

    private int oldPosition = 0;

    private List<AVObject> musicAVObject;

    public void setMusicAVObject(List<AVObject> musicAVObject) {
        this.musicAVObject = musicAVObject;
    }

    public void playAVObjectPosition(int position) {
        oldPosition = position;
        if (musicAVObject != null) {
            AVObject object = musicAVObject.get(position);
            updateView(object);
            PlayService.getInstance().setUrl(object.get(MEDIA_FILE).toString());
        }
    }

    public void switchMusic(int newPosition) {
        if (newPosition != oldPosition) {
            PlayService.getInstance().switchMusic();
        }
    }

    public void playMusic() {
        PlayService.getInstance().playMusic();
    }

    public PlayView(Context context) {
        super(context);
        init(context);
    }

    public PlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.layout_play_view, this);
        headImg = mView.findViewById(R.id.play_view_user_head);
        switchImg = mView.findViewById(R.id.play_view_switch);
        nextImg = mView.findViewById(R.id.play_view_next);
        musicNameTx = mView.findViewById(R.id.play_view_name);
        userNameTx = mView.findViewById(R.id.play_view_user_name);
        seekBar = mView.findViewById(R.id.play_view_seek_bar);

        updateView(null);

        switchImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                plog.paly("点击了播放");
                PlayService.getInstance().playMusic();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                PlayService.getInstance().setSeetTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        PlayService.getInstance().setPlayListener(new PlayService.PlayListener() {
            @Override
            void maxTime(int time) {
                seekBar.setMax(time);
            }

            @Override
            void onProgress(int progress) {
                seekBar.setProgress(progress);
            }

            @Override
            void onStatus(boolean play) {
                if (play) {
                    switchImg.setImageResource(R.drawable.icon_music_stop_play);
                } else {
                    switchImg.setImageResource(R.drawable.icon_music_play);
                }
            }
        });
    }

    private void updateView(AVObject musicObject) {
        String name = "";
        String headUrl = "";
        String musicName = "";

        if (musicObject != null) {
            AVUser avUser = musicObject.getAVUser(TARGET_AVUSER);
            name = avUser.get(USER_NICK_NAME).toString();
            headUrl = avUser.get(USER_HEAD_ICON).toString();
            musicName = musicObject.get(MEDIA_FILE_NAME).toString();
        }


        GlideManager.loadImg(headUrl, headImg, R.mipmap.ic_default_head);
        musicNameTx.setText(musicName);
        userNameTx.setText(name);
    }
}
