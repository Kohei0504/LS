package com.example.admin.ls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static SensorManager manager;
    private static SensorManager managerLight;
    private Sensor mLight;
    private static TextView xT;
    private static TextView yT;
    private static TextView zT;
    float x = 0;
    float px = 0;
    private MediaPlayer mediaPlayer;
    int a = 0;
    int b = 1;
    int power=0;
    float z=0;
    float pz=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        xT = (TextView) findViewById(R.id.xText);
        yT = (TextView) findViewById(R.id.yText);
        zT = (TextView) findViewById(R.id.zText);
        managerLight = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //光センサーである[Sensor.TYPE_LIGHT]を指定
        mLight = managerLight.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if ((double) sensorEvent.values[0] > 20 && b == 0) {
                b = 1;
            }
            if ((double) sensorEvent.values[0] == 0  && a == 0 && b == 1) {
                audioPlay0();
                a = 1;
                b = 0;
                power=1;
            }
            if ((double) sensorEvent.values[0] == 0 && a == 1 && b == 1) {
                audioPlay2();
                a = 0;
                b = 0;
                power=0;
            }
        }
        if (x - px > 10&&power==1) {//音楽の再生の判定
            audioPlay1();
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            px = x;
            pz=z;
            xT.setText("x:" + sensorEvent.values[0]);
            yT.setText("y:" + sensorEvent.values[1]);
            zT.setText("z:" + sensorEvent.values[2]);
            x = sensorEvent.values[0];
            z=sensorEvent.values[2];
        }/*if (Math.abs(z - pz) > 1&&power==1) {//音楽の再生の判定
            audioPlay3();
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        manager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            Sensor s = sensors.get(0);
            manager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
        }
        managerLight.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void audioPlay0() {

        // インタンスを生成
        mediaPlayer = new MediaPlayer();
        String filePath = "PowerUp1.wav";

        try {
            // assetsから mp3 ファイルを読み込み
            AssetFileDescriptor afdescripter = getAssets().openFd(filePath);
            //MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 再生する
        mediaPlayer.start();
    }

    private void audioPlay1() {

        // インタンスを生成
        mediaPlayer = new MediaPlayer();

        //音楽ファイル名, あるいはパス
        Random r = new Random();
        int n = r.nextInt(9);
        String filePath;
        if (0 <= n && n < 3) {
            filePath = "Crash1.wav";
        } else if (3 <= n && n < 6) {
            filePath = "Crash2.wav";
        } else {
            filePath = "Crash4.wav";
        }
        //String filePath = "sound.mp3";

        try {
            // assetsから mp3 ファイルを読み込み
            AssetFileDescriptor afdescripter = getAssets().openFd(filePath);
            //MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 再生する
        mediaPlayer.start();
    }

    private void audioPlay2() {

        // インタンスを生成
        mediaPlayer = new MediaPlayer();
        String filePath = "PowerDown1.wav";

        try {
            // assetsから mp3 ファイルを読み込み
            AssetFileDescriptor afdescripter = getAssets().openFd(filePath);
            //MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 再生する
        mediaPlayer.start();
    }
    private void audioPlay3() {

        // インタンスを生成
        mediaPlayer = new MediaPlayer();

        //音楽ファイル名, あるいはパス
        Random r = new Random();
        int n = r.nextInt(16);
        String filePath;
        if (0 <= n && n < 4) {
            filePath = "Swing3.wav";
        } else if (4 <= n && n < 8) {
            filePath = "Swing4.wav";
        }else if (8 <= n && n < 12) {
            filePath = "Swing5.wav";
        }else {
            filePath = "Swing6.wav";
        }
        //String filePath = "sound.mp3";

        try {
            // assetsから mp3 ファイルを読み込み
            AssetFileDescriptor afdescripter = getAssets().openFd(filePath);
            //MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 再生する
        mediaPlayer.start();
    }
}
