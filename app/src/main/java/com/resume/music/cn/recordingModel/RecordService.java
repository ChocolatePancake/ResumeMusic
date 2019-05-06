package com.resume.music.cn.recordingModel;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.resume.music.cn.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import tech.com.commoncore.plog;

public class RecordService {
    private static RecordService recordService;
    private static final String TAG = "PWDebug";

    private TranslationListener translationListener;

    public void setTranslationListener(TranslationListener translationListener) {
        this.translationListener = translationListener;
    }

    public static RecordService getInstance() {
        synchronized (RecordService.class) {
            if (recordService == null) {
                recordService = new RecordService();
            }
            return recordService;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (translationListener != null) {
                switch (msg.what) {
                    case 10085:
                        translationListener.start();
                        break;
                    case 10086:
                        translationListener.end(path, wavFileName);
                        break;
                    case 10087:
                        translationListener.error();
                        break;
                }
            }
        }
    };

    /**
     * 采样率，现在能够保证在所有设备上使用的采样率是44100Hz, 但是其他的采样率（22050, 16000, 11025）在一些设备上也可以使用。
     */
    public static final int SAMPLE_RATE_IN_HZ = 44100;
    /**
     * 声道数。CHANNEL_IN_MONO and CHANNEL_IN_STEREO. 其中CHANNEL_IN_MONO是可以保证在所有设备能够使用的。
     */
    public static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    /**
     * 返回的音频数据的格式。 ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, and ENCODING_PCM_FLOAT.
     */
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private boolean isRecording;
    private AudioRecord audioRecord;
    private int minBufferSize;
    private AudioTrack audioTrack;
    private byte[] audioData;
    private FileInputStream fileInputStream;
    private String fileName = "temporary_elephant.pcm";
    private String path;
    private String wavFileName = "";

    public boolean isRecording() {
        return isRecording;
    }

    public void startRecord() {
        minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize);
        audioData = new byte[minBufferSize];
        path = App.getInstance().getExternalFilesDir(null).getPath();
        final File file = new File(path, fileName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        if (file.exists()) {
            file.delete();
        }
        audioRecord.startRecording();
        isRecording = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (null != fos) {
                    while (isRecording) {
                        int read = audioRecord.read(audioData, 0, minBufferSize);
                        // 如果读取音频数据没有出现错误，就将数据写入到文件
                        if (AudioRecord.ERROR_INVALID_OPERATION != read && fos != null) {
                            try {
                                fos.write(audioData);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        fos.close();
                        handler.sendEmptyMessage(10085);
                        pcmToWav();
                        handler.sendEmptyMessage(10086);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void pcmToWav() {
        PcmToWavUtil pcmToWavUtil = new PcmToWavUtil(SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        wavFileName = "aa" + System.currentTimeMillis() + "elephant.wav";
        File pcmFile = new File(path, fileName);
        File wavFile = new File(path, wavFileName);
        if (!wavFile.mkdirs()) {
            plog.paly("没有这个文件");
        }
        if (wavFile.exists()) {
            plog.paly("有这个文件,删除掉");
            wavFile.delete();
        }
        pcmToWavUtil.pcmToWav(pcmFile.getAbsolutePath(), wavFile.getAbsolutePath());
    }

    public void stopRecord() {
        isRecording = false;
        if (null != audioRecord) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playInModeStream() {
        /*
         * SAMPLE_RATE_INHZ 对应pcm音频的采样率
         * channelConfig 对应pcm音频的声道
         * AUDIO_FORMAT 对应pcm音频的格式
         * */
        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        final int minBufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE_IN_HZ, channelConfig, AUDIO_FORMAT);
        audioTrack = new AudioTrack(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build(),
                new AudioFormat.Builder().setSampleRate(SAMPLE_RATE_IN_HZ)
                        .setEncoding(AUDIO_FORMAT)
                        .setChannelMask(channelConfig)
                        .build(),
                minBufferSize,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        audioTrack.play();

        File file = new File(path, wavFileName);
        try {
            fileInputStream = new FileInputStream(file);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] tempBuffer = new byte[minBufferSize];
                        while (fileInputStream.available() > 0) {
                            int readCount = fileInputStream.read(tempBuffer);
                            if (readCount == AudioTrack.ERROR_INVALID_OPERATION ||
                                    readCount == AudioTrack.ERROR_BAD_VALUE) {
                                continue;
                            }
                            if (readCount != 0 && readCount != -1) {
                                audioTrack.write(tempBuffer, 0, readCount);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static abstract class TranslationListener {

        public abstract void start();

        public abstract void end(String path, String fileName);

        public abstract void error();
    }

}
