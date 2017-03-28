package com.example.yul.noddingdetection;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Yul on 3/27/2017.
 */

public class MyFaceDetectionListener extends Activity implements Camera.FaceDetectionListener {

    public MyFaceDetectionListener(Context context){
        this.context=context;
        //exp = (TextView) findViewById(R.id.exp);
    }
    public Context context;
    public int x,y, count = 0;
    private TextView exp;

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
                Toast toast = Toast.makeText(this.context, "No", Toast.LENGTH_SHORT);
                toast.show();
            }
            else if(Math.abs(x - faces[0].rect.centerX()) >= 20){
                Log.d("FaceDetection", "Nodded YEESSS");
                x = faces[0].rect.centerX();
                y = faces[0].rect.centerY();
                Toast toast = Toast.makeText(this.context, "Yes", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                x = faces[0].rect.centerX();
                y = faces[0].rect.centerY();
                //exp.setText("ITS WORKSRS");
            }
        }
    }
}
