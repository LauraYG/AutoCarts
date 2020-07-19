package com.example.lsi_app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ros.android.AppCompatRosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class AutomaticControllActivity extends AppCompatRosActivity {

    private float MAX_DEFAULT_SPEED = 200;

    private Button automaticButton;
    private Button throtitleAutoButton;
    private Button throtitleManualButton;
    private Button steeringAutoButton;
    private Button steeringManualButton;
    private Button brakeAutoButton;
    private Button brakeManualButton;
    private Button stanleyModeButton;
    private Button hInfiniteModeButton;
    private Button verdeModeButton;
    private TextView generalSpeedTextView;
    private TextView generalSteeringTextView;
    private ImageView continueImageView;
    private ImageView pauseImageView;
    private ImageView exitButton;
    private ImageView emergencyButton;
    private ImageView stillAliveGreenImageView;
    private ImageView stillAliveOrangeImageView;
    private ImageView stillAliveRedImageView;
    private Drawable batteryProgressBarStyleDrawable;
    private Drawable upsProgressBarStyleDrawable;
    private ProgressBar batteryProgressBar;
    private ProgressBar upsProgressBar;
    private RelativeLayout automaticSwitchRelativeLayout;
    private RelativeLayout autoModesRelativeLayout;
    private JoystickView virtualJoystickHorizontalView;
    private JoystickView virtualJoystickVerticalView;
    private boolean automaticButtonState;
    private boolean brakeDisplayed;
    private boolean throtitleDisplayed;
    private Talker talker;
    private Listener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_controll_layout);

        automaticButtonState = false;

        findViews();
        setItemsDesign();
        onClickListenersOfButtons();
    }

    public AutomaticControllActivity () {
        super("AutomaticControll Activity", "AutomaticControll Activity");
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

    private void findViews(){
        automaticButton = findViewById(R.id.automatic_controll_button);
        automaticSwitchRelativeLayout = findViewById(R.id.automatic_switch_relativelayout);
        throtitleAutoButton = findViewById(R.id.auto_throtitle_button);
        throtitleManualButton = findViewById(R.id.manual_throtitle_button);
        steeringAutoButton = findViewById(R.id.auto_steering_button);
        steeringManualButton = findViewById(R.id.manual_steering_button);
        brakeAutoButton = findViewById(R.id.auto_brake_button);
        brakeManualButton = findViewById(R.id.manual_brake_button);
        continueImageView = findViewById(R.id.continue_button);
        pauseImageView = findViewById(R.id.pause_button);
        exitButton = findViewById(R.id.exit_button);
        //batteryProgressBar = findViewById(R.id.battery_progressbar);
        //upsProgressBar = findViewById(R.id.ups_progressbar);
        autoModesRelativeLayout = findViewById(R.id.auto_modes_relativelayout);
        stanleyModeButton = findViewById(R.id.stanley_mode_button);
        hInfiniteModeButton = findViewById(R.id.h_infinite_mode_button);
        verdeModeButton = findViewById(R.id.verde_mode_button);
        emergencyButton = findViewById(R.id.emergency_button);
        stillAliveGreenImageView = findViewById(R.id.stillAlive_green_imageview);
        stillAliveOrangeImageView = findViewById(R.id.stillAlive_orange_imageview);
        stillAliveRedImageView = findViewById(R.id.stillAlive_red_imageview);
        generalSpeedTextView = findViewById(R.id.speed_number_textView);
        generalSteeringTextView = findViewById(R.id.steering_number_text_view);
        virtualJoystickHorizontalView = findViewById(R.id.virtual_joystick_horizontal_view);
        virtualJoystickVerticalView = findViewById(R.id.virtual_joystick_vertical_view);
    }

    private void setItemsDesign(){
        automaticButton.setBackgroundColor(Color.BLUE);
        continueImageView.setColorFilter(Color.BLUE);
        pauseImageView.setColorFilter(Color.WHITE);
        pauseImageView.setBackgroundColor(Color.BLUE);
        exitButton.setColorFilter(Color.BLUE);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //batteryProgressBarStyleDrawable = getDrawable(R.drawable.customprogressbar);
            //upsProgressBarStyleDrawable = getDrawable(R.drawable.customprogressbar);
        }
        batteryProgressBar.setProgressDrawable(batteryProgressBarStyleDrawable);
        upsProgressBar.setProgressDrawable(upsProgressBarStyleDrawable);
        batteryProgressBar.setProgress(50);
        upsProgressBar.setProgress(20);*/
    }

    private void onClickListenersOfButtons(){
        automaticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickAutoButton();
            }
        });

        throtitleAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickThrotitleAutoButton();
            }
        });

        throtitleManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickThrotitleManualButton();
            }
        });

        steeringAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickSteeringAutoButton();
            }
        });

        steeringManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickSteeringManualButton();
            }
        });

        brakeAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickBrakeAutoButton();
            }
        });

        brakeManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickBrakeManualButton();
            }
        });

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

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSentsVehicleMode(2);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToHomeActivity();
            }
        });

        stanleyModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickStanleyModeButton();
            }
        });

        hInfiniteModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickHInfiniteModeButton();
            }
        });

        verdeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickVerdeModeButton();
            }
        });
    }

    private void userClickAutoButton(){
        if (!automaticButtonState) {
            automaticButton.setBackgroundColor(Color.RED);
            automaticSwitchRelativeLayout.setVisibility(View.VISIBLE);
            userClickBrakeAutoButton();
            userClickSteeringAutoButton();
            userClickThrotitleAutoButton();
            setInitialDesignForModeButtons();
            autoModesRelativeLayout.setVisibility(View.GONE);
            automaticButtonState = true;
            userSentsVehicleMode(0);
        } else {
            automaticButton.setBackgroundColor(Color.BLUE);
            automaticSwitchRelativeLayout.setVisibility(View.GONE);
            autoModesRelativeLayout.setVisibility(View.VISIBLE);
            virtualJoystickHorizontalView.setVisibility(View.GONE);
            virtualJoystickVerticalView.setVisibility(View.GONE);
            setInitialDesignForButtons();
            userSentsVehicleMode(1);
            talker.setThrotitleEnablePublisher(false);
            talker.setSteeringEnablePublisher(false);
            talker.setBrakeEnablePublisher(false);
            automaticButtonState = false;
        }
    }

    private void userClickThrotitleAutoButton() {
        throtitleAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        throtitleManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        throtitleDisplayed = false;

        if(!brakeDisplayed) {
           virtualJoystickVerticalView.setVisibility(View.GONE);
        }
        talker.setThrotitleEnablePublisher(false);
    }

    private void userClickThrotitleManualButton() {
        throtitleAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        throtitleManualButton.setBackgroundColor(getResources().getColor(R.color.green));

        throtitleDisplayed = true;
        virtualJoystickVerticalView.setVisibility(View.VISIBLE);
        talker.setThrotitleEnablePublisher(true);
        joystickVerticalFuncionality();
    }

    private void userClickSteeringAutoButton() {
        steeringAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        steeringManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));

        virtualJoystickHorizontalView.setVisibility(View.GONE);
        talker.setSteeringEnablePublisher(false);
    }

    private void userClickSteeringManualButton() {
        steeringAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        steeringManualButton.setBackgroundColor(getResources().getColor(R.color.green));

        virtualJoystickHorizontalView.setVisibility(View.VISIBLE);
        talker.setSteeringEnablePublisher(true);
        joystickHorizontalFuncionality();
    }

    private void userClickBrakeAutoButton() {
        brakeAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        brakeManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));

        brakeDisplayed = false;
        if(!throtitleDisplayed) {
            virtualJoystickVerticalView.setVisibility(View.GONE);
        }
        talker.setBrakeEnablePublisher(false);
    }

    private void userClickBrakeManualButton() {
        brakeAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        brakeManualButton.setBackgroundColor(getResources().getColor(R.color.green));

        brakeDisplayed = true;
        virtualJoystickVerticalView.setVisibility(View.VISIBLE);
        talker.setBrakeEnablePublisher(true);
        joystickVerticalFuncionality();
    }

    private void userClickContinueButton() {
        continueImageView.setBackgroundColor(Color.BLUE);
        continueImageView.setColorFilter(Color.WHITE);
        pauseImageView.setBackgroundColor(Color.WHITE);
        pauseImageView.setColorFilter(Color.BLUE);

        talker.publisherForPlayButton();
        if(automaticButtonState) {
            userSentsVehicleMode(0);
        } else {
            userSentsVehicleMode(1);
        }
    }

    private void joystickHorizontalFuncionality() {
        virtualJoystickHorizontalView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                if(angle < 180) {
                    talker.publisherForSteering(angle);
                }
            }
        });
    }

    private void joystickVerticalFuncionality(){
        virtualJoystickVerticalView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                float realVelocity = (strength * 100) / MAX_DEFAULT_SPEED;
                if(throtitleDisplayed && angle < 270) {
                    talker.publisherForBrakeAndThrotitle(realVelocity);
                }

                if(brakeDisplayed && angle > 270) {
                    realVelocity = Math.abs(strength - 100);
                    talker.publisherForBrakeAndThrotitle(realVelocity);
                }

            }
        });
    }

    private void userClickStanleyModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.green));

        //TODO call to publisher
    }

    private void userClickHInfiniteModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.green));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));

        //TODO call to publisher
    }

    private void userClickVerdeModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.green));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));

        //TODO call to publisher
    }

    private void userClickPauseButton() {
        pauseImageView.setBackgroundColor(Color.BLUE);
        pauseImageView.setColorFilter(Color.WHITE);
        continueImageView.setBackgroundColor(Color.WHITE);
        continueImageView.setColorFilter(Color.BLUE);
        talker.publisherForPauseButton();
    }

    private void setInitialDesignForButtons(){
        throtitleAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        throtitleManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        steeringAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        steeringManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        brakeAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        brakeManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        userClickPauseButton();
    }

    private void setInitialDesignForModeButtons(){
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
    }

    private void returnToHomeActivity() {
        Intent intent = new Intent (AutomaticControllActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showGreenStillAlive() {
        stillAliveGreenImageView.setVisibility(View.VISIBLE);
    }

    public void hideGreenStillAlive() {
        stillAliveGreenImageView.setVisibility(View.GONE);
    }

    public void showOrangeStillAlive() {
        stillAliveOrangeImageView.setVisibility(View.VISIBLE);
    }

    public void hideOrangeStillAlive() {
        stillAliveOrangeImageView.setVisibility(View.GONE);
    }

    public void showRedStillAlive() {
        stillAliveRedImageView.setVisibility(View.VISIBLE);
    }

    public void hideRedStillAlive() {
        stillAliveRedImageView.setVisibility(View.GONE);
    }

    public void setGeneralSpeedText(double float64) {
        generalSpeedTextView.setText(String.valueOf(float64));
    }

    public void setGeneralSteeringText(double float64) {
        generalSteeringTextView.setText(String.valueOf(float64));
    }

    public void userSentsVehicleMode(int mode) {
        talker.publisherForVehicleMode(mode);
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