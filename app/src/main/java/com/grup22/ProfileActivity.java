package com.grup22;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    FloatingActionButton camButton,faceButton,objectButton;
    RatingBar ratingBar;
    TextView number;

    Animation fabOpen,fabClose,fabRClockwise,fabRanticlockwise;

    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        camButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        faceButton = (FloatingActionButton)findViewById(R.id.floatingFace);
        objectButton = (FloatingActionButton)findViewById(R.id.floatingObject);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        fabRanticlockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        number =(TextView)findViewById(R.id.phoneNumber);

        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(ProfileActivity.this, ObjectActivity.class);
                //startActivity(i);

                if(isOpen)
                {
                    objectButton.startAnimation(fabClose);
                    faceButton.startAnimation(fabClose);
                    camButton.startAnimation(fabRanticlockwise);
                    objectButton.setClickable(false);
                    faceButton.setClickable(false);
                    isOpen = false;
                }
                else
                {
                    objectButton.startAnimation(fabOpen);
                    faceButton.startAnimation(fabOpen);
                    camButton.startAnimation(fabRClockwise);
                    objectButton.setClickable(true);
                    faceButton.setClickable(true);
                    isOpen = true;
                }
            }
        });

    }
}
