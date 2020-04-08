package com.example.lsi_app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView manualButtonImageview;
    private ImageView automaticButtonImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manualButtonImageview = findViewById(R.id.manual_button_imageview);
        automaticButtonImageView = findViewById(R.id.automatic_button_imageview);
        manualButtonImageview.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        automaticButtonImageView.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        manualButtonImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToManualActivity();
            }
        });

        automaticButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAutomaticActivity();
            }
        });
    }

    private void navigateToManualActivity(){
        Intent intent = new Intent (MainActivity.this, DemosSelectorActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToAutomaticActivity() {
        Intent intent = new Intent (MainActivity.this, AutomaticControllActivity.class);
        startActivity(intent);
        finish();
    }
}
