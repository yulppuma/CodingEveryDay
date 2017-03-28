package com.example.yul.noddingdetection;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Yul on 3/27/2017.
 */

public class MyFaceDetectionListener extends AppCompatActivity implements Camera.FaceDetectionListener {

    public int x,y, count = 0;

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if(faces.length > 0){
            if(count == 0){
                x = faces[0].rect.centerX();
                y = faces[0].rect.centerY();
                count++;
            }
            Log.d("FaceDetection", "face detected: "+ faces.length +
                    " Face 1 Location X: " + faces[0].rect.centerX() +
                    " Y: " + faces[0].rect.centerY() + " X: " + x + " Y: " + y);

            if(Math.abs(y - faces[0].rect.centerY()) >= 50){
                Log.d("FaceDetection", "Nodded NOOOO");
                x = faces[0].rect.centerX();
                y = faces[0].rect.centerY();
            }
            else if(Math.abs(x - faces[0].rect.centerX()) >= 20){
                Log.d("FaceDetection", "Nodded YEESSS");
                x = faces[0].rect.centerX();
                y = faces[0].rect.centerY();
            }
            else{
                x = faces[0].rect.centerX();
                y = faces[0].rect.centerY();
            }
        }
    }
}
