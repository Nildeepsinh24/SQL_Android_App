package com.example.sql_masterclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Buttons
        Button btnLearn = findViewById(R.id.btnLearn);
        Button btnPractice = findViewById(R.id.btnPractice);
        Button btnPlayground = findViewById(R.id.btnPlayground);
        Button btnAbout = findViewById(R.id.btnAbout);

        // 2. Set Click Listeners

        // Navigate to Learn Activity
        btnLearn.setOnClickListener(v ->
                startActivity(new Intent(this, LearnActivity.class)));

        // Navigate to Practice List
        btnPractice.setOnClickListener(v ->
                startActivity(new Intent(this, PracticeListActivity.class)));

        // Navigate to Playground
        btnPlayground.setOnClickListener(v ->
                startActivity(new Intent(this, PlaygroundActivity.class)));

        // Navigate to the new About Us Page
        btnAbout.setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));

        // 3. START SLOW ANIMATIONS
        // Buttons slide up one by one
        animateButton(btnLearn, 200);
        animateButton(btnPractice, 500);
        animateButton(btnPlayground, 800);
        animateButton(btnAbout, 1000);
    }

    /**
     * Helper method to animate buttons (Fade In + Slide Up)
     */
    private void animateButton(View view, long delay) {
        view.setAlpha(0f); // Start invisible
        view.setTranslationY(100f); // Start slightly lower

        view.animate()
                .alpha(1f) // Fade in
                .translationY(0f) // Slide up to original position
                .setStartDelay(delay)
                .setDuration(1200) // Slow, smooth speed
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}