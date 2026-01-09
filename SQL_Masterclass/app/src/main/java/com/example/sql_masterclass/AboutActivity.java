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

        // 1. Assign Views
        CardView cardDesc = findViewById(R.id.cardDescription);
        CardView cardTeam = findViewById(R.id.cardTeam);
        CardView cardContact = findViewById(R.id.cardContact);
        TextView tvVersion = findViewById(R.id.tvVersion);

        // 2. Entry Animations (Slide up effect)
        animateView(findViewById(R.id.imgAppLogo), 0);
        animateView(findViewById(R.id.tvAppName), 100);
        animateView(cardDesc, 200);
        animateView(cardTeam, 300);
        animateView(cardContact, 400);

        // 3. Fluid Color Animations (Shifting backgrounds)
        startColorAnimation(cardDesc, "#1976D2", "#7B1FA2", "#311B92");
        startColorAnimation(cardContact, "#00695C", "#2E7D32", "#00838F");

        // 4. Version Setup
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            tvVersion.setText("Version " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tvVersion.setText("Version 1.0");
        }

        // 5. Contact Click Listener (Opens Email)
        cardContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void animateView(View view, long delay) {
        if (view == null) return;
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

    private void startColorAnimation(final CardView card, String hex1, String hex2, String hex3) {
        int c1 = Color.parseColor(hex1);
        int c2 = Color.parseColor(hex2);
        int c3 = Color.parseColor(hex3);

        ValueAnimator anim = ValueAnimator.ofObject(new ArgbEvaluator(), c1, c2, c3);
        anim.setDuration(5000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.addUpdateListener(animation -> card.setCardBackgroundColor((int) animation.getAnimatedValue()));
        anim.start();
    }

    private void sendEmail() {
        String mailto = "mailto:sqlmasterclass05@gmail.com" +
                "?subject=" + Uri.encode("Support: SQL Masterclass App") +
                "&body=" + Uri.encode("Hello SQL Masterclass Team,\n\n");

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            // This allows user to pick their email app
            startActivity(Intent.createChooser(emailIntent, "Send Email..."));
        } catch (Exception e) {
            Toast.makeText(this, "No email app found!", Toast.LENGTH_SHORT).show();
        }
    }
}