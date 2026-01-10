package com.example.sql_masterclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1️⃣ INITIALIZE BUTTONS FIRST (ONLY MOVED UP)
        View btnLearn = findViewById(R.id.btnLearn);
        View btnPractice = findViewById(R.id.btnPractice);
        View btnPlayground = findViewById(R.id.btnPlayground);
        View btnAbout = findViewById(R.id.btnAbout);

        // 2️⃣ SPLASH OVERLAY (UNCHANGED LOGIC)
        View splashOverlay = findViewById(R.id.splashOverlay);
        if (splashOverlay != null) {
            new android.os.Handler(android.os.Looper.getMainLooper())
                    .postDelayed(() -> {
                        splashOverlay.setVisibility(View.GONE);

                        // START ANIMATIONS AFTER SPLASH
                        animateButton(btnLearn, 0);
                        animateButton(btnPractice, 150);
                        animateButton(btnPlayground, 300);
                        animateButton(btnAbout, 450);

                    }, 400);
        }

        // 3️⃣ CLICK LISTENERS (UNCHANGED)
        btnLearn.setOnClickListener(v ->
                startActivity(new Intent(this, LearnActivity.class)));

        btnPractice.setOnClickListener(v ->
                startActivity(new Intent(this, PracticeListActivity.class)));

        btnPlayground.setOnClickListener(v ->
                startActivity(new Intent(this, PlaygroundActivity.class)));

        btnAbout.setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));
    }

    // ANIMATION METHOD (UNCHANGED)
    private void animateButton(View view, long delay) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.setTranslationY(100f);

        view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(delay)
                .setDuration(1200)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}
