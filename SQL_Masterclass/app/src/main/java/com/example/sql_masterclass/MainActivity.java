package com.example.sql_masterclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< Updated upstream
        // 1️⃣ INITIALIZE BUTTONS FIRST (ONLY MOVED UP)
=======
        // 1. Initialize Buttons
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======

        addPressEffect(btnLearn);
        addPressEffect(btnPractice);
        addPressEffect(btnPlayground);
        addPressEffect(btnAbout);


        // 3. START SLOW ANIMATIONS
        // Buttons slide up one by one
        animateButton(btnLearn, 200);
        animateButton(btnPractice, 500);
        animateButton(btnPlayground, 800);
        animateButton(btnAbout, 1000);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
}
=======

    private void addPressEffect(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(1.10f).scaleY(1.10f).setDuration(120).start();
                    v.setElevation(30f);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(120).start();
                    v.setElevation(6f);
                    break;

            }
            return false;
        });
    }

}
>>>>>>> Stashed changes
