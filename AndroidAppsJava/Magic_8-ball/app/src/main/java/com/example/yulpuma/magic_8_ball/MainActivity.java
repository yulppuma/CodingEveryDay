package com.example.yulpuma.magic_8_ball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.magic_8_ball.MESSAGE";
    String hello;
    Button mybtn;
    TextView txt;
    String[] arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mybtn = (Button) findViewById(R.id.button_8);
        txt = (TextView) findViewById(R.id.question);
        arr = new String[20];
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

        mybtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                txt.setText(arr[(int) (Math.random()*20)]);
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
