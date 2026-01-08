package com.example.sql_masterclass;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // --- 1. ENTRY ANIMATIONS (Slide Up) ---
        animateView(findViewById(R.id.imgAppLogo), 0);
        animateView(findViewById(R.id.tvAppName), 100);

        CardView cardDesc = findViewById(R.id.cardDescription);
        animateView(cardDesc, 200);

        animateView(findViewById(R.id.cardTeam), 300);

        CardView cardContact = findViewById(R.id.cardContact);
        animateView(cardContact, 400);


        // --- 2. START FLUID COLOR ANIMATIONS ---
        // Description Card: Blue -> Purple -> Indigo
        startColorAnimation(cardDesc, "#1976D2", "#7B1FA2", "#311B92");

        // Contact Card: Teal -> Emerald -> Cyan (Deep Ocean Theme)
        startColorAnimation(cardContact, "#00695C", "#2E7D32", "#00838F");


        // --- 3. VERSION SETUP ---
        TextView tvVersion = findViewById(R.id.tvVersion);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            tvVersion.setText("Version " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tvVersion.setText("Version 1.0");
        }


        // --- 4. EMAIL BUTTON SETUP ---
        TextView btnEmail = findViewById(R.id.btnContactEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    // Helper: Slide Up + Fade In
    private void animateView(View view, long delay) {
        view.setAlpha(0f);
        view.setTranslationY(100f);
        view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(delay)
                .setDuration(600)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    // Helper: Fluid Color Pulse (Now accepts 3 custom colors)
    private void startColorAnimation(final CardView card, String hex1, String hex2, String hex3) {
        int color1 = Color.parseColor(hex1);
        int color2 = Color.parseColor(hex2);
        int color3 = Color.parseColor(hex3);

        // Animate: Color 1 -> Color 2 -> Color 3 -> Back to Color 1
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), color1, color2, color3);
        colorAnimation.setDuration(5000); // 5 seconds for full loop
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                card.setCardBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    // --- EMAIL LOGIC ---
    private void sendEmail() {
        String recipient = "sqlmasterclass05@gmail.com";
        String subject = "SQL Masterclass Support";

        // Try Gmail app
        Intent gmailIntent = new Intent(Intent.ACTION_SENDTO);
        gmailIntent.setData(Uri.parse("mailto:" + recipient));
        gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        gmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        gmailIntent.setPackage("com.google.android.gm");

        if (gmailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(gmailIntent);
        } else {
            // Fallback: Open browser Gmail compose
            try {
                String url = "https://mail.google.com/mail/?view=cm&fs=1&to="
                        + recipient + "&su=" + Uri.encode(subject);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(webIntent);
            } catch (Exception e) {
                Toast.makeText(this, "No email app or browser found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}