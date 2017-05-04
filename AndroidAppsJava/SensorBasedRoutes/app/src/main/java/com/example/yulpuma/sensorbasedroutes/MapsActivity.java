package com.example.yulpuma.sensorbasedroutes;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaRecorder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.log10;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener, SensorEventListener{

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private MediaRecorder mRecorder = null;
    private Polyline line;
    private ArrayList<LatLng> points;
    private ArrayList<PolylineOptions> option;
    private ArrayList<Double> decArr;
    private ArrayList<Polyline> polylines;
    private SensorManager sensManager;
    private Sensor sSensor;
    TextView decibelVal;
    TextView ac, lac, gyros, grav, press, decibel;
    Button recording;
    boolean acc = false, gyro = false, pressure = false, gravity = false, linear = false;
    double acx, acy, acz, lacx, lacy, lacz, gyrox, gyroy, gyroz, gravx, gravy, gravz, pressVal;
    DecimalFormat facx = new DecimalFormat("0.0"), facy = new DecimalFormat("0.0"), facz = new DecimalFormat("0.0"),
            flacx = new DecimalFormat("0.0"), flacy = new DecimalFormat("0.0"), flacz = new DecimalFormat("0.0"),
            fgyrox = new DecimalFormat("0.0"), fgyroy = new DecimalFormat("0.0"), fgyroz = new DecimalFormat("0.0"),
            fgravx = new DecimalFormat("0.0"), fgravy = new DecimalFormat("0.0"), fgravz = new DecimalFormat("0.0"),
            fpressVal = new DecimalFormat("0.0");
    int first = 0;
    float currPress, lastPress;

    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final String LOG_TAG = "AudioRecordTest";
    private boolean isRecording = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        recording = (Button) findViewById(R.id.recording);
        points = new ArrayList<LatLng>();
        option = new ArrayList<PolylineOptions>();
        decArr = new ArrayList<Double>();
        polylines = new ArrayList<Polyline>();
        decibelVal = (TextView) findViewById(R.id.decibel);
        ac = (TextView) findViewById(R.id.ac);
        lac = (TextView) findViewById(R.id.lac);
        gyros = (TextView) findViewById(R.id.gyros);
        grav = (TextView) findViewById(R.id.grav);
        press = (TextView) findViewById(R.id.press);
        sensManager = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sSensor != null)
            sensManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if(sSensor != null)
            sensManager.registerListener(this,sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if(sSensor != null)
            sensManager.registerListener(this,sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(sSensor != null)
            sensManager.registerListener(this,sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sSensor = sensManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(sSensor != null)
            sensManager.registerListener(this,sSensor, SensorManager.SENSOR_DELAY_NORMAL);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location){
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        PolylineOptions options = null;
        //Place current location marker
        double dec = -28;
        int color = Color.BLUE;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if(mRecorder != null){
            dec = 20 * log10(mRecorder.getMaxAmplitude()/ 32767.0);
            double deci = (26 + dec) * 3;
            decibelVal.setText("Decibel: " + deci);
            if (dec < -21.0) {
                options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            } else if (dec > -21.0 && dec < -15.0) {
                options = new PolylineOptions().width(5).color(Color.GREEN).geodesic(true);
                color = Color.GREEN;
            } else if (dec > -15.0 && dec < -9.0) {
                options = new PolylineOptions().width(5).color(Color.YELLOW).geodesic(true);
            } else {
                options = new PolylineOptions().width(5).color(Color.RED).geodesic(true);
            }
        }
        points.add(latLng);
        ac.setText("AC: " + facx.format(acx) + " " + facy.format(acy) + " " + facz.format(acz));
        lac.setText("LAC: " + flacx.format(lacx) + " " + flacy.format(lacy) + " " + flacz.format(lacz));
        grav.setText("grav: " + fgravx.format(gravx) + " " + fgravy.format(gravy) + " " + fgravz.format(gravz));
        gyros.setText("gyro: " + fgyrox.format(gyrox) + " " + fgyroy.format(gyroy) + " " + fgyroz.format(gyroz));
        press.setText("pressure: " + fpressVal.format(pressVal) + currPress + " " + lastPress);
        if(options == null)
            options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        option.add(options);
        if(acc && linear && gyro && gravity && pressure)
            decibel.setText("True");
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
    }
    public void record(View view){
        if(isRecording){
            recording.setText("Stop Recording");
            startRecording();
        }
        else{
            recording.setText("Start Recording");
            stopRecording();
        }
        isRecording = !isRecording;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile("/dev/null");
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording(){
        if(mRecorder != null){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float[] values = event.values;
            acx = Math.abs(values[0]);
            acy = Math.abs(values[1]);
            acz = Math.abs(values[2]);
            if(acx < 0.2 && facy.format(acy) == "9.8" && acz < 0.2)
                acc = true;
            else
                acc = false;
        }
        else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            float[] values = event.values;
            lacx = Math.abs(values[0]);
            lacy = Math.abs(values[1]);
            lacz = Math.abs(values[2]);
            if(lacx < 0.2 && lacy < 0.2 && lacz < 0.2)
                linear = true;
            else
                linear = false;
        }
        else if (sensor.getType() == Sensor.TYPE_GRAVITY){
            float[] values = event.values;
            gravx = Math.abs(values[0]);
            gravy = Math.abs(values[1]);
            gravz = Math.abs(values[2]);
            if(gravx < 0.2 && fgravy.format(gravy) == "9.8" && gravz < 0.2)
                gravity = true;
            else
                gravity = false;
        }
        else if (sensor.getType() == Sensor.TYPE_GYROSCOPE){
            float[] values = event.values;
            gyrox = Math.abs(values[0]);
            gyroy = Math.abs(values[1]);
            gyroz = Math.abs(values[2]);
            if(gyrox < 0.2 && gyroy < 0.2 && gyroz < 0.2)
                gyro = true;
            else
                gyro = false;
        }
        else{
            float[] values = event.values;
            if(first == 0){
                currPress = values[0];
                lastPress = values[0];
                first++;
            }
            else{
                lastPress = currPress;
                currPress = values[0];
            }
            pressVal = Math.abs(currPress - lastPress);
            if(pressVal < 0.2)
                pressure = true;
            else
                pressure = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
