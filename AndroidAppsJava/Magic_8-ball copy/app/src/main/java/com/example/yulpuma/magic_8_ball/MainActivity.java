package com.example.yulpuma.magic_8_ball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.magic_8_ball.MESSAGE";
    String hello;
    Button mybtn;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mybtn = (Button) findViewById(R.id.button_8);
        txt = (TextView) findViewById(R.id.question);
        hello = " Does it work";

        mybtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                txt.setText(hello);
            }
        });
    }

    //Currently not using
    public void onClickB(View view){
        txt.setText(hello);
    }

    //Another function that copies input text
    public void sendMessage(View view) {
        //Does something on click
        Intent intent = new Intent (this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }
}
