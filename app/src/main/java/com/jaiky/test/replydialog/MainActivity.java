package com.jaiky.test.replydialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void button1(View view)
    {
        startActivity(new Intent(this,Rlv1Activity.class));
    }
    public void button2(View view)
    {
        startActivity(new Intent(this,Rlv2Activity.class));
    }
}
