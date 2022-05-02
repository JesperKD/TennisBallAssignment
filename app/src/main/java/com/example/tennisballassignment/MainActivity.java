package com.example.tennisballassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sm = null;
    TextView textView1 = null;
    ImageView ballView = null;
    List list;

    //Listening for all sensor events
    SensorEventListener sel = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        //Updates every time a censor value has changed
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            textView1.setText("x: " + values[0] + "\ny: " + values[1] + "\nz: " + values[2]);

            //Updates the X Y Z positioning of the ball depending on the censor values
            ballView.setX(-values[0] * 5);
            ballView.setY(-values[1] * 5);
            ballView.setZ(-values[2] * 5);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get a SensorManager instance */
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        textView1 = (TextView) findViewById(R.id.textView1);

        ballView = findViewById(R.id.ballImage);

        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (list.size() > 0) {
            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        if (list.size() > 0) {
            sm.unregisterListener(sel);
        }
        super.onStop();
    }

}