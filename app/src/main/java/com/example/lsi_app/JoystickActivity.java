package com.example.lsi_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.ros.android.AppCompatRosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class JoystickActivity extends AppCompatRosActivity {

    private float MAX_DEFAULT_SPEED = 200;

    private Talker talker;
    private Listener listener;
    private TextView steeringTextView;
    private TextView speedTextView;
    private JoystickView throtitleBrakeJoystickView;
    private JoystickView steeringJoystickView;
    private ImageView continueImageView;
    private ImageView pauseImageView;
    private ImageView greenStillAliveImageView;
    private ImageView orangeStillAliveImageView;
    private ImageView redStillAliveImageView;
    private ImageView emergencyImageView;
    private ImageView exitImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick_layout);

        findViews();
        setItemsDesign();
        onClickListenerForButtons();
        callToJoysticks();
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname(), getMasterUri());
        listener = new Listener(this);
        nodeConfiguration.setNodeName("listener");
        nodeMainExecutor.execute(listener, nodeConfiguration);

        NodeConfiguration nodeConfiguration2 = NodeConfiguration.newPublic(getRosHostname(), getMasterUri());
        talker = new Talker(this);
        nodeConfiguration2.setNodeName("talker");
        nodeMainExecutor.execute(talker, nodeConfiguration2);
    }

    public JoystickActivity () {
        super("Joystick Activity", "Joystick Activity");
    }

    private void findViews() {
        steeringTextView = findViewById(R.id.steering_number_text_view);
        speedTextView = findViewById(R.id.speed_number_textView);
        throtitleBrakeJoystickView = findViewById(R.id.virtual_joystick_vertical_view);
        steeringJoystickView = findViewById(R.id.virtual_joystick_horizontal_view);
        continueImageView = findViewById(R.id.continue_button);
        pauseImageView = findViewById(R.id.pause_button);
        greenStillAliveImageView = findViewById(R.id.stillAlive_green_imageview);
        orangeStillAliveImageView = findViewById(R.id.stillAlive_orange_imageview);
        redStillAliveImageView = findViewById(R.id.stillAlive_red_imageview);
        emergencyImageView = findViewById(R.id.emergency_button);
        exitImageView = findViewById(R.id.exit_button);
    }

    private void setItemsDesign() {
        continueImageView.setColorFilter(Color.BLUE);
        pauseImageView.setColorFilter(Color.WHITE);
        pauseImageView.setBackgroundColor(Color.BLUE);
        exitImageView.setColorFilter(Color.BLUE);
    }

    private void onClickListenerForButtons() {
        continueImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickContinueButton();
            }
        });

        pauseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickPauseButton();
            }
        });

        emergencyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 userClickEmergencyButton();
            }
        });

        exitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickExitButton();
            }
        });
    }

    private void userClickContinueButton() {
        continueImageView.setBackgroundColor(Color.BLUE);
        continueImageView.setColorFilter(Color.WHITE);
        pauseImageView.setBackgroundColor(Color.WHITE);
        pauseImageView.setColorFilter(Color.BLUE);

        talker.publisherForPlayButton();
        userSentsVehicleMode(0);
    }

    private void userClickPauseButton() {
        pauseImageView.setBackgroundColor(Color.BLUE);
        pauseImageView.setColorFilter(Color.WHITE);
        continueImageView.setBackgroundColor(Color.WHITE);
        continueImageView.setColorFilter(Color.BLUE);
        talker.publisherForPauseButton();
    }

    private void userClickEmergencyButton() {
        userSentsVehicleMode(2);
    }

    private void userClickExitButton() {
        Intent intent = new Intent (JoystickActivity.this, DemosSelectorActivity.class);
        startActivity(intent);
        finish();
        //TODO add shutdowns
    }

    public void userSentsVehicleMode(int mode) {
        talker.publisherForVehicleMode(mode);
    }

    public void hideGreenStillAlive() {
        greenStillAliveImageView.setVisibility(View.GONE);
    }

    public void hideOrangeStillAlive() {
        orangeStillAliveImageView.setVisibility(View.GONE);
    }

    public void hideRedStillAlive() {
        redStillAliveImageView.setVisibility(View.GONE);
    }

    public void showGreenStillAlive() {
        greenStillAliveImageView.setVisibility(View.VISIBLE);
    }

    public void showOrangeStillAlive() {
        orangeStillAliveImageView.setVisibility(View.VISIBLE);
    }

    public void showRedStillAlive() {
        redStillAliveImageView.setVisibility(View.VISIBLE);
    }

    public void setGeneralSpeedText(double data) {
        speedTextView.setText(String.valueOf(data));
    }

    public void setGeneralSteeringText(double data) {
        steeringTextView.setText(String.valueOf(data));
    }

    private void callToJoysticks() {
        steeringJoystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                if(angle < 180) {
                    talker.publisherForSteering(angle);
                }
            }
        });

        throtitleBrakeJoystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                float realVelocity = (strength * 100) / MAX_DEFAULT_SPEED;
                if(angle < 270) {
                    talker.publisherForBrakeAndThrotitle(realVelocity);
                }

                if(angle > 270) {
                    realVelocity = Math.abs(((strength * 100) / MAX_DEFAULT_SPEED) - 100);
                    talker.publisherForBrakeAndThrotitle(realVelocity);
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
