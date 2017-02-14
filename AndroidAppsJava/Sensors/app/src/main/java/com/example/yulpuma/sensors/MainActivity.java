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
    private Sensor sSensor;
    TextView acVals, maxAcVals, minAcVals;
    TextView lacVals, maxLacVals, minLacVals;
    TextView gVals, maxGVals, minGVals;
    TextView ambTVals, maxAmbTVals, minAmbTVals;
    TextView gyroVals, maxGyroVals, minGyroVals;
    float maxX = 0, maxY = 0, maxZ = 0;
    float minX = 0, minY = 0, minZ = 0;
    float lmaxX = 0, lmaxY = 0, lmaxZ = 0;
    float lminX = 0, lminY = 0, lminZ = 0;
    float gmaxX = 0, gmaxY = 0, gmaxZ = 0;
    float gminX = 0, gminY = 0, gminZ = 0;
    float ambTmax = 0, ambTmin = 0;
    float gyromaxX = 0, gyromaxY = 0, gyromaxZ = 0;
    float gyrominX = 0, gyrominY = 0, gyrominZ = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Values
        acVals = (TextView)findViewById(R.id.ac);
        maxAcVals = (TextView) findViewById(R.id.acMax);
        minAcVals = (TextView) findViewById(R.id.acMin);
        lacVals = (TextView) findViewById(R.id.lac);
        maxLacVals = (TextView) findViewById(R.id.lacMax);
        minLacVals = (TextView) findViewById(R.id.lacMin);
        gVals = (TextView) findViewById(R.id.grav);
        maxGVals = (TextView) findViewById(R.id.gMax);
        minGVals = (TextView) findViewById(R.id.gMin);
        ambTVals = (TextView) findViewById(R.id.ambT);
        maxAmbTVals =(TextView) findViewById(R.id.ambTMax);
        minAmbTVals =(TextView) findViewById(R.id.ambTMin);
        gyroVals = (TextView) findViewById(R.id.gyro);
        maxGyroVals = (TextView) findViewById(R.id.gyroMax);
        minGyroVals = (TextView) findViewById(R.id.gyroMin);

        sensManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){/*Not in use*/}

    public void onSensorChanged(SensorEvent event){
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null)
                acVals.setText("Accelerometer is unavailable on this phone");
            else {
                float[] values = event.values;
                acVals.setText("AC: x: " + values[0] + " y: " + values[1] + " z: " + values[2]);
                if (values[0] > maxX)
                    maxX = values[0];
                if (values[1] > maxY)
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
        }
        else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            if (sensManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) == null){
                lacVals.setText("Linear Accelerometer is unavailable on this phone");
            }
            else{
                float[] values = event.values;
                lacVals.setText("LAC: x: " + values[0] + " y: " + values[1] + " z: " + values[2]);
                if (values[0] > lmaxX)
                    lmaxX = values[0];
                if (values[1] > lmaxY)
                    lmaxY = values[1];
                if (values[2] > lmaxZ)
                    lmaxZ = values[2];
                if (values[0] < lminX)
                    lminX = values[0];
                if (values[1] < lminY)
                    lminY = values[1];
                if (values[2] < lminZ)
                    lminZ = values[2];
                maxLacVals.setText("LAC Max: x: " + lmaxX + " y: " + lmaxY + " z: " + lmaxZ);
                minLacVals.setText("LAC Min: x: " + lminX + " y: " + lminY + " z: " + lminZ);
            }
        }
        else if (sensor.getType() == Sensor.TYPE_GRAVITY){
            if (sensManager.getDefaultSensor(Sensor.TYPE_GRAVITY) == null){
                gVals.setText("Gravity sensor is unavailable on this phone");
            }
            else{
                float[] values = event.values;
                gVals.setText("Gravity: x: " + values[0] + " y: " + values[1] + " z: " + values[2]);
                if (values[0] > gmaxX)
                    gmaxX = values[0];
                if (values[1] > gmaxY)
                    gmaxY = values[1];
                if (values[2] > gmaxZ)
                    gmaxZ = values[2];
                if (values[0] < gminX)
                    gminX = values[0];
                if (values[1] < gminY)
                    gminY = values[1];
                if (values[2] < gminZ)
                    gminZ = values[2];
                maxGVals.setText("GMax: x: " + gmaxX + " y: " + gmaxY + " z: " + gmaxZ);
                minGVals.setText("GMin: x: " + gminX + " y: " + gminY + " z: " + gminZ);
            }
        }
        else if (sensor.getType() == Sensor.TYPE_GYROSCOPE){
            if (sensManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null){
                gyroVals.setText("N/A");
            }
            else{
                float[] values = event.values;
                gyroVals.setText("Gyroscope: x: " + values[0] + " y: " + values[1] + " z: " + values[2]);
                if (values[0] > gyromaxX)
                    gyromaxX = values[0];
                if (values[1] > gmaxY)
                    gyromaxY = values[1];
                if (values[2] > gyromaxZ)
                    gyromaxZ = values[2];
                if (values[0] < gyrominX)
                    gyrominX = values[0];
                if (values[1] < gyrominY)
                    gyrominY = values[1];
                if (values[2] < gyrominZ)
                    gyrominZ = values[2];
                maxGyroVals.setText("GyroMax: x: " + gyromaxX + " y: " + gyromaxY + " z: " + gyromaxZ);
                minGyroVals.setText("GyroMin: x: " + gyrominX + " y: " + gyrominY + " z: " + gyrominZ);
            }
        }
        else{
            if (sensManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null){
                ambTVals.setText("N/dA");
            }
            else{
                float[] values = event.values;
                ambTVals.setText("Ambient Temperatures: " + event.values[0]);
                if (values[0] > ambTmax)
                    ambTmax = values[0];
                if (values[0] < ambTmin)
                    ambTmin = values[0];
                maxAmbTVals.setText("ambTMax: " + ambTmax);
                minAmbTVals.setText("ambTMin: " + ambTmin);
            }
        }
    }

    protected void onPause() {
        super.onPause();
        sensManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
