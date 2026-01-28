package com.project_vn.sql_masterclass;

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

        // 3. Background Color Animations
        startColorAnimation(cardDesc, "#1976D2", "#7B1FA2", "#311B92");
        startColorAnimation(cardContact, "#00695C", "#2E7D32", "#00838F");

        // 4. App Version
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            tvVersion.setText("Version " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tvVersion.setText("Version 1.0");
        }

        // 5. Contact Us → Direct Email Compose
        cardContact.setOnClickListener(v -> openEmailComposer());
    }

    // ---------- Animations ----------
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

    private void startColorAnimation(CardView card, String c1, String c2, String c3) {
        int color1 = Color.parseColor(c1);
        int color2 = Color.parseColor(c2);
        int color3 = Color.parseColor(c3);

        ValueAnimator animator = ValueAnimator.ofObject(
                new ArgbEvaluator(),
                color1,
                color2,
                color3
        );

        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(a ->
                card.setCardBackgroundColor((int) a.getAnimatedValue())
        );
        animator.start();
    }

    // ---------- Email Composer ----------
    private void openEmailComposer() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:sqlmasterclass05@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Support: SQL Masterclass App");
        intent.putExtra(Intent.EXTRA_TEXT, "Hello SQL Masterclass Team,\n\n");

        try {
            startActivity(Intent.createChooser(intent, "Send Email"));
        } catch (Exception e) {
            Toast.makeText(this, "No email app found!", Toast.LENGTH_SHORT).show();
        }
    }
}
