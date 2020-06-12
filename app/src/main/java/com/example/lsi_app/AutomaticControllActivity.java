package com.example.lsi_app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.ros.android.AppCompatRosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

public class AutomaticControllActivity extends AppCompatRosActivity {

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
    private ImageView continueImageView;
    private ImageView pauseImageView;
    private ImageView exitButton;
    private ImageView emergencyButton;
    private Drawable batteryProgressBarStyleDrawable;
    private Drawable upsProgressBarStyleDrawable;
    private ProgressBar batteryProgressBar;
    private ProgressBar upsProgressBar;
    private RelativeLayout automaticSwitchRelativeLayout;
    private RelativeLayout autoModesRelativeLayout;
    private boolean automaticButtonState;
    private String xx, yy;
    private NodeMainExecutor nodeMainExecutor;

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

        Talker talker = new Talker(this);
        nodeConfiguration.setNodeName("talker");
        nodeMainExecutor.execute(talker, nodeConfiguration);
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
        batteryProgressBar = findViewById(R.id.battery_progressbar);
        upsProgressBar = findViewById(R.id.ups_progressbar);
        autoModesRelativeLayout = findViewById(R.id.auto_modes_relativelayout);
        stanleyModeButton = findViewById(R.id.stanley_mode_button);
        hInfiniteModeButton = findViewById(R.id.h_infinite_mode_button);
        verdeModeButton = findViewById(R.id.verde_mode_button);
        emergencyButton = findViewById(R.id.emergency_button);
    }

    private void setItemsDesign(){
        automaticButton.setBackgroundColor(Color.BLUE);
        continueImageView.setColorFilter(Color.BLUE);
        pauseImageView.setColorFilter(Color.WHITE);
        pauseImageView.setBackgroundColor(Color.BLUE);
        exitButton.setColorFilter(Color.BLUE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            batteryProgressBarStyleDrawable = getDrawable(R.drawable.customprogressbar);
            upsProgressBarStyleDrawable = getDrawable(R.drawable.customprogressbar);
        }
        batteryProgressBar.setProgressDrawable(batteryProgressBarStyleDrawable);
        upsProgressBar.setProgressDrawable(upsProgressBarStyleDrawable);
        batteryProgressBar.setProgress(50);
        upsProgressBar.setProgress(20);
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
        } else {
            automaticButton.setBackgroundColor(Color.BLUE);
            automaticSwitchRelativeLayout.setVisibility(View.GONE);
            autoModesRelativeLayout.setVisibility(View.VISIBLE);
            setInitialDesignForButtons();
            automaticButtonState = false;
        }
    }

    private void userClickThrotitleAutoButton() {
        throtitleAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        throtitleManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
    }

    private void userClickThrotitleManualButton() {
        throtitleAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        throtitleManualButton.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void userClickSteeringAutoButton() {
        steeringAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        steeringManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
    }

    private void userClickSteeringManualButton() {
        steeringAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        steeringManualButton.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void userClickBrakeAutoButton() {
        brakeAutoButton.setBackgroundColor(getResources().getColor(R.color.green));
        brakeManualButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
    }

    private void userClickBrakeManualButton() {
        brakeAutoButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        brakeManualButton.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void userClickContinueButton() {
        continueImageView.setBackgroundColor(Color.BLUE);
        continueImageView.setColorFilter(Color.WHITE);
        pauseImageView.setBackgroundColor(Color.WHITE);
        pauseImageView.setColorFilter(Color.BLUE);
    }

    private void userClickStanleyModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void userClickHInfiniteModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.green));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
    }

    private void userClickVerdeModeButton() {
        verdeModeButton.setBackgroundColor(getResources().getColor(R.color.green));
        hInfiniteModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
        stanleyModeButton.setBackgroundColor(getResources().getColor(R.color.grey_light));
    }

    private void userClickPauseButton() {
        pauseImageView.setBackgroundColor(Color.BLUE);
        pauseImageView.setColorFilter(Color.WHITE);
        continueImageView.setBackgroundColor(Color.WHITE);
        continueImageView.setColorFilter(Color.BLUE);
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

    public void updateGPSCoordinates(final double x, final double y) {
        /*this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*xx=String.format("%.2f", x);
                yy=String.format("%.2f", y);
            }
        });*/
    }

    public void shutDownThisActivity() {
        //Todo call ListenerTalker
    }
}