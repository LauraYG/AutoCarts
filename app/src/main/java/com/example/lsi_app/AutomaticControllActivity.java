package com.example.lsi_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ros.android.AppCompatRosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class AutomaticControllActivity extends AppCompatRosActivity {

    private float MAX_DEFAULT_SPEED = 33.33f;

    private Button automaticButton;
    private Button throttleAutoButton;
    private Button throttleManualButton;
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
    private LinearLayout automaticSwitchRelativeLayout;
    private RelativeLayout autoModesRelativeLayout;
    private JoystickView virtualJoystickHorizontalView;
    private JoystickView virtualJoystickVerticalView;
    private boolean automaticButtonState;
    private boolean brakeDisplayed;
    private boolean throttleDisplayed;
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
        throttleAutoButton = findViewById(R.id.auto_throttle_button);
        throttleManualButton = findViewById(R.id.manual_throttle_button);
        steeringAutoButton = findViewById(R.id.auto_steering_button);
        steeringManualButton = findViewById(R.id.manual_steering_button);
        brakeAutoButton = findViewById(R.id.auto_brake_button);
        brakeManualButton = findViewById(R.id.manual_brake_button);
        continueImageView = findViewById(R.id.continue_button);
        pauseImageView = findViewById(R.id.pause_button);
        exitButton = findViewById(R.id.exit_button);
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
    }

    private void onClickListenersOfButtons(){
        automaticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickAutoButton();
            }
        });

        throttleAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickThrottleAutoButton();
            }
        });

        throttleManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickThrottleManualButton();
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
            userClickThrottleAutoButton();
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
            if(talker != null) {
                talker.setThrottleEnablePublisher(true);
                talker.setSteeringEnablePublisher(true);
                talker.setBrakeEnablePublisher(true);
            } else {
                returnForBeWithoutMaster();
            }
            automaticButtonState = false;
        }
    }

    private void userClickThrottleAutoButton() {
        throttleAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        throttleManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        throttleDisplayed = false;

        if(!brakeDisplayed) {
           virtualJoystickVerticalView.setVisibility(View.GONE);
        }
        if(talker != null) {
            talker.setThrottleEnablePublisher(true);
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickThrottleManualButton() {
        throttleAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        throttleManualButton.setBackgroundColor(getResources().getColor(R.color.green));

        throttleDisplayed = true;
        virtualJoystickVerticalView.setVisibility(View.VISIBLE);
        if(talker != null) {
            talker.setThrottleEnablePublisher(false);
            talker.createPublisherForJoystick();
            joystickVerticalFuncionality();
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickSteeringAutoButton() {
        steeringAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        steeringManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));

        virtualJoystickHorizontalView.setVisibility(View.GONE);
        if(talker != null) {
            talker.setSteeringEnablePublisher(true);
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickSteeringManualButton() {
        steeringAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        steeringManualButton.setBackgroundColor(getResources().getColor(R.color.green));

        virtualJoystickHorizontalView.setVisibility(View.VISIBLE);
        if(talker != null) {
            talker.setSteeringEnablePublisher(false);
            talker.createPublisherForJoystick();
            joystickHorizontalFuncionality();
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickBrakeAutoButton() {
        brakeAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        brakeManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));

        brakeDisplayed = false;
        if(!throttleDisplayed) {
            virtualJoystickVerticalView.setVisibility(View.GONE);
        }
        if(talker != null) {
            talker.setBrakeEnablePublisher(true);
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickBrakeManualButton() {
        brakeAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        brakeManualButton.setBackgroundColor(getResources().getColor(R.color.green));

        brakeDisplayed = true;
        virtualJoystickVerticalView.setVisibility(View.VISIBLE);
        if(talker != null) {
            talker.setBrakeEnablePublisher(false);
            talker.createPublisherForJoystick();
            joystickVerticalFuncionality();
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickContinueButton() {
        continueImageView.setBackgroundColor(Color.BLUE);
        continueImageView.setColorFilter(Color.WHITE);
        pauseImageView.setBackgroundColor(Color.WHITE);
        pauseImageView.setColorFilter(Color.BLUE);

        if(talker != null) {
            talker.publisherForPlayButton();
        } else {
            returnForBeWithoutMaster();
        }
        if(automaticButtonState) {
            userSentsVehicleMode(0);
        } else {
            userSentsVehicleMode(1);
            talker.setThrottleEnablePublisher(true);
            talker.setSteeringEnablePublisher(true);
            talker.setBrakeEnablePublisher(true);
        }
    }

    private void joystickHorizontalFuncionality() {
        virtualJoystickHorizontalView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                if(angle < 180) {
                    if(talker != null) {
                        float angleFinal = (float) (angle * Math.PI / 180);
                        talker.publisherForSteering(angleFinal);
                    } else {
                        returnForBeWithoutMaster();
                    }
                }
            }
        });
    }

    private void joystickVerticalFuncionality(){
        virtualJoystickVerticalView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                float realVelocity = (strength * 100) / MAX_DEFAULT_SPEED;
                if(throttleDisplayed && angle < 270) {
                    if(talker != null) {
                        talker.publisherForBrakeAndThrottle(realVelocity);
                    } else {
                        returnForBeWithoutMaster();
                    }
                }

                if(brakeDisplayed && angle > 270) {
                    realVelocity = Math.abs(((strength * 100) / MAX_DEFAULT_SPEED) - 100);
                    if(talker != null) {
                        talker.publisherForBrakeAndThrottle(realVelocity);
                    } else {
                        returnForBeWithoutMaster();
                    }
                }
            }
        });
    }

    private void userClickStanleyModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.green));

        if (talker != null) {
            talker.publisherForStanleyModeButton();
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickHInfiniteModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.green));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));

        if(talker != null) {
            talker.publisherForHInfiniteModeButton();
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickVerdeModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.green));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));

        if(talker != null) {
            talker.publisherForVerdeModeButton();
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void userClickPauseButton() {
        pauseImageView.setBackgroundColor(Color.BLUE);
        pauseImageView.setColorFilter(Color.WHITE);
        continueImageView.setBackgroundColor(Color.WHITE);
        continueImageView.setColorFilter(Color.BLUE);
        if(talker != null) {
            talker.publisherForPauseButton();
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void setInitialDesignForButtons(){
        throttleAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        throttleManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
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
        if(listener != null && talker != null) {
            listener.closeActivity();
            talker.closeActivity();
        }
        finish();
        Intent intent = new Intent (AutomaticControllActivity.this, MainActivity.class);
        startActivity(intent);
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
        float64 = ((double) Math.round(float64 * 1000d)/ 1000d);
        generalSpeedTextView.setText(String.valueOf(float64));
    }

    public void setGeneralSteeringText(double float64) {
        float64 = ((double) Math.round(float64 * 1000d)/ 1000d);
        generalSteeringTextView.setText(String.valueOf(float64));
    }

    public void userSentsVehicleMode(int mode) {
        if(talker != null) {
            talker.publisherForVehicleMode(mode);
        } else {
            returnForBeWithoutMaster();
        }
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

    public void returnForBeWithoutMaster() {
        finish();
        Intent intent = new Intent (AutomaticControllActivity.this, MainActivity.class);
        startActivity(intent);
    }
}