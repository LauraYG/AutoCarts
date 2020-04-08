package com.example.lsi_app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DemosSelectorActivity extends AppCompatActivity {

    private ImageView joystickModeImageView;
    private ImageView visualizationModeImageView;
    private ImageView exitImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_selector_layout);

        joystickModeImageView = findViewById(R.id.manual_button_imageview);
        visualizationModeImageView = findViewById(R.id.visualization_button_imageview);
        exitImageView = findViewById(R.id.exit_button);
        joystickModeImageView.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        exitImageView.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        joystickModeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickJoyStickMode();
            }
        });

        visualizationModeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickVisualizationMode();
            }
        });

        exitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickExitButton();
            }
        });
    }

    private void userClickExitButton() {
        Intent intent = new Intent (DemosSelectorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void userClickVisualizationMode() {
        Intent intent = new Intent (DemosSelectorActivity.this, JoystickActivity.class);
        startActivity(intent);
        finish();
    }

    private void userClickJoyStickMode() {
        Intent intent = new Intent (DemosSelectorActivity.this, VisualizationActivity.class);
        startActivity(intent);
        finish();
    }
}
