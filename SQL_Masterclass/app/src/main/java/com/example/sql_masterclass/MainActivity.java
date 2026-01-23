package com.project_vn.sql_masterclass;

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

        View btnLearn = findViewById(R.id.btnLearn);
        View btnPractice = findViewById(R.id.btnPractice);
        View btnPlayground = findViewById(R.id.btnPlayground);
        View btnAbout = findViewById(R.id.btnAbout);

        // CLICK LISTENERS
        btnLearn.setOnClickListener(v ->
                startActivity(new Intent(this, LearnActivity.class)));

        btnPractice.setOnClickListener(v ->
                startActivity(new Intent(this, PracticeListActivity.class)));

        btnPlayground.setOnClickListener(v ->
                startActivity(new Intent(this, PlaygroundActivity.class)));

        btnAbout.setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));

        // Press Effects
        addPressEffect(btnLearn);
        addPressEffect(btnPractice);
        addPressEffect(btnPlayground);
        addPressEffect(btnAbout);

        // START SLOW ANIMATIONS
        animateButton(btnLearn, 200);
        animateButton(btnPractice, 500);
        animateButton(btnPlayground, 800);
        animateButton(btnAbout, 1000);
    }

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
