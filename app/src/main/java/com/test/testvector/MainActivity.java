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
        vector.setPathData("M22,23 q0,4,-4,4 h-7 q-2,0,-3,-1 T1,16 q-0.6,-0.8,0,-2 t5,3 " +
                "q1,1,2,0 T8,4 q0,-1,0.9,-1.1 t1.1,1 t1.5,9 q0.25,0.5,0.5,0.5 " +
                "t0.5,-0.5 t0,-11 q0.2,-1,1.1,-1.1 t1.1,1.1 t1,11 q0.25,0.5,0.5,0.5 " +
                "t0.5,-0.5 t0.5,-9 q0.2,-1,1,-1 t1,1 t0.5,9 q0.25,0.5,0.5,0.5 " +
                "t0.5,-0.5 t1.2,-6.5 q0.3,-1,1,-1 t0.8,1 t-0.8,6 T22,23");
    }
}