package com.example.yulpuma.magic_8_ball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //Not using
    public final static String EXTRA_MESSAGE = "com.example.magic_8_ball.MESSAGE";
    //String hello;
    Button mybtn;
    TextView txt;
    String[] arr = new String [20];
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 850;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arr[0] = "It is certain";
        arr[1] = "It is decidedly so";
        arr[2] = "Without a doubt";
        arr[3] = "Yes definitely";
        arr[4] = "You may rely on it";
        arr[5] = "As I see it yes";
        arr[6] = "Most likely";
        arr[7] = "Outlook good";
        arr[8] = "Yes";
        arr[9] = "Signs point to yes";
        arr[10] = "Reply hazy try again";
        arr[11] = "Ask again later";
        arr[12] ="Better not tell you now";
        arr[13] = "Cannot predict now";
        arr[14] = "Concentrate and ask again";
        arr[15] = "Don't count on it";
        arr[16] = "My reply is no";
        arr[17] = "My sources say no";
        arr[18] = "Outlook not so good";
        arr[19] = "Very doubtful";
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);


        //mybtn = (Button) findViewById(R.id.button_8);
        txt = (TextView) findViewById(R.id.question);



    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if((curTime - lastUpdate) > 100){
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(z - last_z)/ diffTime * 10000;
                if (speed > SHAKE_THRESHOLD){
                    txt = (TextView) findViewById(R.id.question);
                    txt.setText(arr[(int) (Math.random()*20)]);
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not using
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
