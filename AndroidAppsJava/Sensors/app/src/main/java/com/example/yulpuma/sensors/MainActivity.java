package com.example.yulpuma.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensManager;
    private Sensor sSensor;
    //all the TextViews being used
    TextView acVals, maxAcVals, minAcVals;
    TextView lacVals, maxLacVals, minLacVals;
    TextView gVals, maxGVals, minGVals;
    TextView ambTVals, maxAmbTVals, minAmbTVals;
    TextView gyroVals, maxGyroVals, minGyroVals;
    TextView magFieldVals, maxMagFieldVals, minMagFieldVals;
    TextView lightSVals, maxLightSVals, minLightSVals;
    TextView proximityVals, maxProxVals, minProxVals;
    TextView pressureVals, maxPressVals, minPressVals;
    //all the max/min values to be recorded for each respective sensor type
    float maxX = 0, maxY = 0, maxZ = 0;
    float minX = 0, minY = 0, minZ = 0;
    float lmaxX = 0, lmaxY = 0, lmaxZ = 0;
    float lminX = 0, lminY = 0, lminZ = 0;
    float gmaxX = 0, gmaxY = 0, gmaxZ = 0;
    float gminX = 0, gminY = 0, gminZ = 0;
    float ambTmax = 0, ambTmin = 0;
    float gyromaxX = 0, gyromaxY = 0, gyromaxZ = 0;
    float gyrominX = 0, gyrominY = 0, gyrominZ = 0;
    float magFieldmaxX = 0, magFieldmaxY = 0, magFieldmaxZ = 0;
    float magFieldminX = 0, magFieldminY = 0, magFieldminZ = 0;
    float lightSMax = 0, lightSMin = 0;
    float proxMax = 0, proxMin = 0;
    float pressMax = 0, pressMin = 0;
    //Counter to record the first iteration of a sensor
    int first = 0, firstL = 0, firstLi = 0, firstPr = 0, firstMF = 0, firstAC = 0, firstLAC = 0,
    firstG = 0, firstAmb = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Getting the textview ids for all sensors
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
        magFieldVals = (TextView) findViewById(R.id.magField);
        maxMagFieldVals = (TextView) findViewById(R.id.magFieldMax);
        minMagFieldVals = (TextView) findViewById(R.id.magFieldMin);
        lightSVals = (TextView) findViewById(R.id.lightSensor);
        maxLightSVals = (TextView) findViewById(R.id.lightSensorMax);
        minLightSVals = (TextView) findViewById(R.id.lightSensorMin);
        proximityVals = (TextView) findViewById(R.id.proximity);
        maxProxVals = (TextView) findViewById(R.id.proxMax);
        minProxVals = (TextView) findViewById(R.id.proxMin);
        pressureVals = (TextView) findViewById(R.id.pressure);
        maxPressVals = (TextView) findViewById(R.id.pressMax);
        minPressVals = (TextView) findViewById(R.id.pressMin);

        sensManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        /*getting access to all available sensors and
        checking if the phone the app is running on has those sensors*/
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            acVals.setText("AC: N/A");
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            lacVals.setText("LAC: N/A");
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            gVals.setText("Gravity: N/A");
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            ambTVals.setText("Ambient Temp: N/A");
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            gyroVals.setText("Gyroscope: N/A");
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            magFieldVals.setText("Ambient Temp: N/A");
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            lightSVals.setText("Light: N/A");
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            proximityVals.setText("Proximity: N/A");
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            pressureVals.setText("Pressure: N/A");

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){/*Not in use*/}

    public void onSensorChanged(SensorEvent event){
        Sensor sensor = event.sensor;
        //When a sensor is detected it makes sure the correct values are being accessed
        //All the if statements are the same with respect to their sensors
        //the first values for each if statements keep track of the first iteration of the sensor being used
        //so that an initial max/min value is stored
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            firstAC++;
            float[] values = event.values;
            if(firstAC == 1){
                float firstX = values[0];
                float firstY = values[1];
                float firstZ = values[2];
                maxX = firstX;
                maxY = firstY;
                maxZ = firstZ;
                minX = firstX;
                minY = firstY;
                minZ = firstZ;
            }
            acVals.setText("AC: x: " + values[0] + " m/s^2 y: " + values[1] + " m/s^2 z: " + values[2] + " m/s^2");
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
            maxAcVals.setText("AC Max: x: " + maxX + " m/s^2 y: " + maxY + " ms/^2 z: " + maxZ + " m/s^2");
            minAcVals.setText("AC Min: x: " + minX + "m/s^2 y: " + minY + "m/s^2 z: " + minZ + " m/s^2");
        }
        else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            firstLAC++;
            float[] values = event.values;
            if(firstLAC == 1){
                float firstX = values[0];
                float firstY = values[1];
                float firstZ = values[2];
                lmaxX = firstX;
                lmaxY = firstY;
                lmaxZ = firstZ;
                lminX = firstX;
                lminY = firstY;
                lminZ = firstZ;
            }
            lacVals.setText("LAC: x: " + values[0] + " m/s^2 y: " + values[1] + " m/s^2 z: " + values[2] + " m/s^2");
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
            maxLacVals.setText("LAC Max: x: " + lmaxX + " m/s^2 y: " + lmaxY + " m/s^2 z: " + lmaxZ + " m/s^2");
            minLacVals.setText("LAC Min: x: " + lminX + " m/s^2 y: " + lminY + " m/s^2 z: " + lminZ + " m/s^2");
        }
        else if (sensor.getType() == Sensor.TYPE_GRAVITY){
            first++;
            float[] values = event.values;
            if(first == 1){
                float firstX = values[0];
                float firstY = values[1];
                float firstZ = values[2];
                gmaxX = firstX;
                gmaxY = firstY;
                gmaxZ = firstZ;
                gminX = firstX;
                gminY = firstY;
                gminZ = firstZ;
            }
            gVals.setText("Gravity: x: " + values[0] + " m/s^2 y: " + values[1] + " m/s^2 z: " + values[2] + " m/s^2");
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
            maxGVals.setText("GMax: x: " + gmaxX + " m/s^2 y: " + gmaxY + " m/s^2 z: " + gmaxZ + "m/s^2");
            minGVals.setText("GMin: x: " + gminX + " m/s^2 y: " + gminY + " m/s^2 z: " + gminZ + " m/s^2");
        }
        else if (sensor.getType() == Sensor.TYPE_GYROSCOPE){
            firstG++;
            float[] values = event.values;
            if(firstG == 1){
                float firstX = values[0];
                float firstY = values[1];
                float firstZ = values[2];
                gyromaxX = firstX;
                gyromaxY = firstY;
                gyromaxZ = firstZ;
                gyrominX = firstX;
                gyrominY = firstY;
                gyrominZ = firstZ;
            }
            gyroVals.setText("Gyroscope: x: " + values[0] + " m/s^2 y: " + values[1] + " m/s^2 z: " + values[2] + " m/s^2");
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
            maxGyroVals.setText("GyroMax: x: " + gyromaxX + " m/s^2 y: " + gyromaxY + " m/s^2 z: " + gyromaxZ + " m/s^2");
            minGyroVals.setText("GyroMin: x: " + gyrominX + " m/s^2 y: " + gyrominY + " m/s^2 z: " + gyrominZ + " m/s^2");
        }
        else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            firstMF++;
            float[] values = event.values;
            if(firstMF == 1){
                float firstX = values[0];
                float firstY = values[1];
                float firstZ = values[2];
                magFieldmaxX = firstX;
                magFieldmaxY = firstY;
                magFieldmaxZ = firstZ;
                magFieldminX = firstX;
                magFieldminY = firstY;
                magFieldminZ = firstZ;
            }
            magFieldVals.setText("Magnetic Field: x: " + values[0] + " m/s^2 y: " + values[1] + " m/s^2 z: " + values[2] + " m/s^2");
            if (values[0] > magFieldmaxX)
                magFieldmaxX = values[0];
            if (values[1] > magFieldmaxY)
                magFieldmaxY = values[1];
            if (values[2] > magFieldmaxZ)
                magFieldmaxZ = values[2];
            if (values[0] < magFieldminX)
                magFieldminX = values[0];
            if (values[1] < magFieldminY)
                magFieldminY = values[1];
            if (values[2] < magFieldminZ)
                gminZ = values[2];
            maxMagFieldVals.setText("MagFieldMax: x: " + magFieldmaxX + " m/s^2 y: " + magFieldmaxY + " m/s^2 z: " + magFieldmaxZ + " m/s^2");
            minMagFieldVals.setText("MagFieldMin: x: " + magFieldminX + " m/s^2 y: " + magFieldminY + " m/s^2 z: " + magFieldminZ + " m/s^2");
        }
        else if (sensor.getType() == Sensor.TYPE_LIGHT){
            float[] values = event.values;
            firstLi++;
            if(firstLi == 1){
                float firstX = values[0];
                lightSMax = firstX;
                lightSMin = firstX;
            }
            lightSVals.setText("Light: " + values[0] + " lux");
            if(values[0] > lightSMax)
                lightSMax = values[0];
            if (values[0] < lightSMin)
                lightSMin = values[0];
            maxLightSVals.setText("Light Max: " + lightSMax + " lux");
            minLightSVals.setText("Light Min: " + lightSMin + " lux");
        }
        else if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float[] values = event.values;
            firstL++;
            if(firstL == 1){
                float firstX = values[0];
                proxMax = firstX;
                proxMin = firstX;
            }
            proximityVals.setText("Proximity: " + values[0] + " cm");
            if (values[0] > proxMax)
                proxMax = values[0];
            if (values[0] < proxMin)
                proxMin = values[0];
            maxProxVals.setText("Proximity Max: " + proxMax + " cm");
            minProxVals.setText("Proximity Min: " + proxMin + " cm");
        }
        else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            float[] values = event.values;
            firstPr++;
            if(firstPr == 1){
                float firstX = values[0];
                pressMax = firstX;
                pressMin = firstX;
            }
            pressureVals.setText("Pressure: " + values[0] + " hPa");
            if (values[0] > pressMax)
                pressMax = values[0];
            if (values[0] < pressMin)
                pressMin = values[0];
            maxPressVals.setText("Pressure Max: " + pressMax + " hPa");
            minPressVals.setText("Pressure Min: " + pressMin + " hPa");
        }
        else{
            firstAmb++;
            float[] values = event.values;
            if(firstPr == 1){
                float firstX = values[0];
                ambTmax = firstX;
                ambTmin = firstX;
            }
            ambTVals.setText("Ambient Temperatures: " + event.values[0]);
            if (values[0] > ambTmax)
                ambTmax = values[0];
            if (values[0] < ambTmin)
                ambTmin = values[0];
            maxAmbTVals.setText("ambTMax: " + ambTmax);
            minAmbTVals.setText("ambTMin: " + ambTmin);

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

    public void nextPage(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
