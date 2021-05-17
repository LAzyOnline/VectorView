package com.test.testvector;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VectorView vector = findViewById(R.id.vector);
        vector.setFillColor(Color.parseColor("#ff0000"));
        vector.setViewportHeight(24f);
        vector.setViewportWidth(24f);
//        //手掌
        vector.setPathData("M22,23 q0,4,-4,4 h-7 q-2,0,-3,-1 T1,16 q-0.6,-0.8,0,-2 t5,3 " +
                "q1,1,2,0 T8,4 q0,-1,0.9,-1.1 t1.1,1 t1.5,9 q0.25,0.5,0.5,0.5 " +
                "t0.5,-0.5 t0,-11 q0.2,-1,1.1,-1.1 t1.1,1.1 t1,11 q0.25,0.5,0.5,0.5 " +
                "t0.5,-0.5 t0.5,-9 q0.2,-1,1,-1 t1,1 t0.5,9 q0.25,0.5,0.5,0.5 " +
                "t0.5,-0.5 t1.2,-6.5 q0.3,-1,1,-1 t0.8,1 t-0.8,6 T22,23");
//        "M22,23 q0,4,-4,4 h-7 q-2,0,-3,-1 T1,16 q-0.6,-0.8,0,-2 t5,3 "+

        //心
//        vector.setPathData("M8,4 h24 q4,0,4,4 v24 q0,4,-4,4 h-24 q-4,0,-4,-4 v-24 q0,-4,4,-4 M20,15 a5,6,-15,0,0,-9,2 c0,5,4,6,9,12 c5,-6,9,-7,9,-12 a5,6,15,0,0,-9,-2");

//        vector.setPathData("M8,10 a4,6,0,1,1,6,6");
    }
}