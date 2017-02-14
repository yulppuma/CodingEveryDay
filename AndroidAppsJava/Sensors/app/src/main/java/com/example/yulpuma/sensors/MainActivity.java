package com.example.yulpuma.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensManager;
    private Sensor accelerometer;
    TextView acVals, maxAcVals, minAcVals;
    float maxX = 0, maxY = 0, maxZ = 0;
    float minX = 0, minY = 0, minZ = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        acVals = (TextView)findViewById(R.id.ac);
        maxAcVals = (TextView) findViewById(R.id.acMax);
        minAcVals = (TextView) findViewById(R.id.acMin);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){/*Not in use*/}

    public void onSensorChanged(SensorEvent event){
        float[] values = event.values;
        acVals.setText("x: " + values[0] + " y: " + values[1] + " z: " + values[2]);
        if(values[0] > maxX)
            maxX = values[0];
        if(values[1] > maxY)
            maxY = values[1];
        if (values[2] > maxZ)
            maxZ = values[2];
        if (values[0] < minX)
            minX = values[0];
        if (values[1] < minY)
            minY = values[1];
        if (values[2] < minZ)
            minZ = values[2];
        maxAcVals.setText("AC Max: x: " + maxX + " y: " + maxY + " z: " + maxZ);
        minAcVals.setText("AC Min: x: " + minX + " y: " + minY + " z: " + minZ);
    }

    protected void onPause() {
        super.onPause();
        sensManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
