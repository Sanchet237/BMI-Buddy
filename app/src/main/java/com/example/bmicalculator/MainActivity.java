package com.example.bmicalculator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Main activity for the BMI Calculator app.
 * Handles UI, user interaction, and coordination between business logic classes.
 */
public class MainActivity extends AppCompatActivity {

    private TextView    btnMetric, btnImperial;
    private EditText    etHeightCm, etHeightFt, etHeightIn, etWeight;
    private LinearLayout layoutHeightImperial;
    private TextView    tvHeightUnit, tvWeightUnit;
    private Button      btnCalculate, btnClear, btnInfo, btnShare;
    private CardView    cardResult;
    private TextView    tvBmiValue, tvCategory, tvCategoryDesc, tvStatusBadge;
    private ProgressBar progressBmiScale;
    private ImageView   btnInstagram, btnLinkedIn, btnGithub;

    private boolean isMetric = true;
    private double  lastBmi  = 0.0;
    private String  lastCategory = "";
    private List<Double> bmiHistory;

    private static final DecelerateInterpolator DECELERATE = new DecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT = new OvershootInterpolator(Constants.OVERSHOOT_TENSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupUnitToggle();
        setupClickListeners();
        loadPreferences();
        restoreLastResult();
    }

    private void bindViews() {
        btnMetric            = findViewById(R.id.btnMetric);
        btnImperial          = findViewById(R.id.btnImperial);
        etHeightCm           = findViewById(R.id.etHeightCm);
        layoutHeightImperial = findViewById(R.id.layoutHeightImperial);
        etHeightFt           = findViewById(R.id.etHeightFt);
        etHeightIn           = findViewById(R.id.etHeightIn);
        etWeight             = findViewById(R.id.etWeight);
        tvHeightUnit         = findViewById(R.id.tvHeightUnit);
        tvWeightUnit         = findViewById(R.id.tvWeightUnit);
        btnCalculate         = findViewById(R.id.btnCalculate);
        btnClear             = findViewById(R.id.btnClear);
        btnInfo              = findViewById(R.id.btnInfo);
        btnShare             = findViewById(R.id.btnShare);
        cardResult           = findViewById(R.id.cardResult);
        tvBmiValue           = findViewById(R.id.tvBmiValue);
        tvCategory           = findViewById(R.id.tvCategory);
        tvCategoryDesc       = findViewById(R.id.tvCategoryDesc);
        tvStatusBadge        = findViewById(R.id.tvStatusBadge);
        progressBmiScale     = findViewById(R.id.progressBmiScale);
        btnInstagram         = findViewById(R.id.btnInstagram);
        btnLinkedIn          = findViewById(R.id.btnLinkedIn);
        btnGithub            = findViewById(R.id.btnGithub);

        bmiHistory = new ArrayList<>();
    }

    private void setupUnitToggle() {
        btnMetric.setOnClickListener(v -> switchToMetric());
        btnImperial.setOnClickListener(v -> switchToImperial());
    }

    private void setupClickListeners() {
        btnCalculate.setOnClickListener(v -> onCalculateClicked());
        btnClear.setOnClickListener(v -> onClearClicked());
        btnInfo.setOnClickListener(v -> showInfoDialog());
        btnShare.setOnClickListener(v -> shareResult());
        btnInstagram.setOnClickListener(v -> openUrl("https://www.instagram.com/sanchetkolekar/"));
        btnLinkedIn.setOnClickListener(v -> openUrl("https://www.linkedin.com/in/sanchet-kolekar-613916331"));
        btnGithub.setOnClickListener(v -> openUrl("https://github.com/Sanchet237"));
    }

    private void switchToMetric() {
        if (isMetric) return;
        isMetric = true;
        updateUnitToggleUI();
        savePreferences();
    }

    private void switchToImperial() {
        if (!isMetric) return;
        isMetric = false;
        updateUnitToggleUI();
        savePreferences();
    }

    private void updateUnitToggleUI() {
        if (isMetric) {
            btnMetric.setBackgroundResource(R.drawable.bg_toggle_selected);
            btnMetric.setTextColor(Color.WHITE);
            btnImperial.setBackgroundResource(android.R.color.transparent);
            btnImperial.setTextColor(Color.parseColor("#9CA3AF"));

            etHeightCm.setVisibility(View.VISIBLE);
            layoutHeightImperial.setVisibility(View.GONE);

            tvHeightUnit.setText(R.string.centimeters);
            tvWeightUnit.setText(R.string.kilograms);
        } else {
            btnImperial.setBackgroundResource(R.drawable.bg_toggle_selected);
            btnImperial.setTextColor(Color.WHITE);
            btnMetric.setBackgroundResource(android.R.color.transparent);
            btnMetric.setTextColor(Color.parseColor("#9CA3AF"));

            etHeightCm.setVisibility(View.GONE);
            layoutHeightImperial.setVisibility(View.VISIBLE);

            tvHeightUnit.setText(R.string.feet_inches);
            tvWeightUnit.setText(R.string.pounds);
        }

        clearInputs();
        cardResult.setVisibility(View.GONE);
    }

    private void onCalculateClicked() {
        hideKeyboard();

        double heightMeters;
        double weightKg;

        try {
            if (isMetric) {
                String hStr = etHeightCm.getText().toString().trim();
                String wStr = etWeight.getText().toString().trim();

                if (!InputValidator.validateHeightCm(etHeightCm, hStr)) return;
                if (!InputValidator.validateWeightKg(etWeight, wStr)) return;

                heightMeters = BmiCalculator.cmToMeters(InputValidator.parseDouble(hStr));
                weightKg = InputValidator.parseDouble(wStr);

            } else {
                String ftStr = etHeightFt.getText().toString().trim();
                String inStr = etHeightIn.getText().toString().trim();
                String wStr = etWeight.getText().toString().trim();

                if (!InputValidator.validateHeightFt(etHeightFt, ftStr)) return;
                if (!InputValidator.validateHeightIn(etHeightIn, inStr)) return;
                if (!InputValidator.validateWeightLbs(etWeight, wStr)) return;

                double feet = InputValidator.parseDouble(ftStr);
                double inches = TextUtils.isEmpty(inStr) ? 0 : InputValidator.parseDouble(inStr);
                double lbs = InputValidator.parseDouble(wStr);

                heightMeters = BmiCalculator.feetInchesToMeters(feet, inches);
                weightKg = BmiCalculator.lbsToKg(lbs);
            }

            double bmi = BmiCalculator.calculateBmi(weightKg, heightMeters);
            displayResult(bmi);
            saveResult(bmi, BmiCalculator.getCategory(bmi));
            addToHistory(bmi);

        } catch (NumberFormatException e) {
            showToast(getString(R.string.error_height_positive));
        }
    }

    private void onClearClicked() {
        clearInputs();
        cardResult.setVisibility(View.GONE);
        lastBmi = 0.0;
        lastCategory = "";
    }

    private void displayResult(double bmi) {
        if (bmi <= 0) return;

        lastBmi = bmi;
        lastCategory = BmiCalculator.getCategory(bmi);

        int accentColor = BmiCalculator.getCategoryColor(bmi);
        String description = BmiCalculator.getCategoryDescription(bmi);
        String bmiText = String.format("%.1f", bmi);

        tvBmiValue.setText(bmiText);
        tvBmiValue.setTextColor(accentColor);
        tvCategory.setText(lastCategory);
        tvCategoryDesc.setText(description);
        tvStatusBadge.setText(lastCategory);
        tvStatusBadge.setTextColor(accentColor);

        if (cardResult.getVisibility() != View.VISIBLE) {
            cardResult.setVisibility(View.VISIBLE);
            cardResult.setAlpha(0f);
            cardResult.setTranslationY(60f);

            AnimatorSet anim = new AnimatorSet();
            anim.playTogether(
                    ObjectAnimator.ofFloat(cardResult, "alpha", 0f, 1f),
                    ObjectAnimator.ofFloat(cardResult, "translationY", 60f, 0f));
            anim.setDuration(Constants.ANIMATION_DURATION_CARD);
            anim.setInterpolator(OVERSHOOT);
            anim.start();
        }

        animateBmiValue(bmi);
        animateProgress(BmiCalculator.bmiToProgress(bmi));
    }

    private void animateBmiValue(double targetBmi) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, (float) targetBmi);
        animator.setDuration(Constants.ANIMATION_DURATION_VALUE);
        animator.setInterpolator(DECELERATE);
        animator.addUpdateListener(anim -> {
            float val = (float) anim.getAnimatedValue();
            tvBmiValue.setText(String.format("%.1f", val));
        });
        animator.start();
    }

    private void animateProgress(int targetProgress) {
        ValueAnimator animator = ValueAnimator.ofInt(progressBmiScale.getProgress(), targetProgress);
        animator.setDuration(Constants.ANIMATION_DURATION_PROGRESS);
        animator.setInterpolator(DECELERATE);
        animator.addUpdateListener(anim ->
                progressBmiScale.setProgress((int) anim.getAnimatedValue()));
        animator.start();
    }

    private void showInfoDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.info_dialog_title)
                .setMessage(R.string.info_dialog_message)
                .setPositiveButton(R.string.info_dialog_button, null)
                .show();
    }

    private void shareResult() {
        if (lastBmi == 0.0) {
            showToast(getString(R.string.toast_calculate_first));
            return;
        }

        String shareText = String.format(
                getString(R.string.share_message),
                String.format("%.1f", lastBmi),
                lastCategory);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        startActivity(Intent.createChooser(intent, getString(R.string.share_chooser)));
    }

    private void saveResult(double bmi, String category) {
        getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putFloat(Constants.KEY_BMI, (float) bmi)
                .putString(Constants.KEY_CATEGORY, category)
                .putString(Constants.KEY_UNIT, isMetric ? Constants.UNIT_METRIC : Constants.UNIT_IMPERIAL)
                .apply();
    }

    private void savePreferences() {
        getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(Constants.KEY_PREFERRED_UNIT, isMetric ? Constants.UNIT_METRIC : Constants.UNIT_IMPERIAL)
                .apply();
    }

    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        String preferredUnit = prefs.getString(Constants.KEY_PREFERRED_UNIT, Constants.UNIT_METRIC);
        if (Constants.UNIT_IMPERIAL.equals(preferredUnit)) {
            switchToImperial();
        }
    }

    private void restoreLastResult() {
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        float savedBmi = prefs.getFloat(Constants.KEY_BMI, 0f);
        if (savedBmi > 0f) {
            displayResult(savedBmi);
        }
    }

    private void addToHistory(double bmi) {
        bmiHistory.add(0, bmi);
        if (bmiHistory.size() > Constants.HISTORY_SIZE) {
            bmiHistory.remove(bmiHistory.size() - 1);
        }
    }

    private void clearInputs() {
        etHeightCm.setText("");
        etHeightFt.setText("");
        etHeightIn.setText("");
        etWeight.setText("");
        clearErrors();
    }

    private void clearErrors() {
        etHeightCm.setError(null);
        etHeightFt.setError(null);
        etHeightIn.setError(null);
        etWeight.setError(null);
    }

    private void hideKeyboard() {
        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            showToast("Unable to open URL");
        }
    }
}
