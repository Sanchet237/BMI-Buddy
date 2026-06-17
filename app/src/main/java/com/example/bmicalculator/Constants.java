package com.example.bmicalculator;

/**
 * Application-wide constants.
 */
public class Constants {

    // SharedPreferences
    public static final String PREFS_NAME = "bmi_prefs";
    public static final String KEY_BMI = "last_bmi";
    public static final String KEY_CATEGORY = "last_category";
    public static final String KEY_UNIT = "last_unit";
    public static final String KEY_HISTORY = "bmi_history";
    public static final String KEY_PREFERRED_UNIT = "preferred_unit";

    // Animations
    public static final int ANIMATION_DURATION_CARD = 500;
    public static final int ANIMATION_DURATION_VALUE = 800;
    public static final int ANIMATION_DURATION_PROGRESS = 900;
    public static final float OVERSHOOT_TENSION = 0.7f;

    // Unit conversions
    public static final double CM_TO_M_FACTOR = 0.01;
    public static final double INCHES_TO_M = 0.0254;
    public static final double LBS_TO_KG = 0.453592;
    public static final double FEET_TO_INCHES = 12.0;

    // BMI scale
    public static final double BMI_SCALE_MAX = 40.0;
    public static final int PROGRESS_MAX = 100;

    // Validation limits
    public static final double MAX_HEIGHT_CM = 300;
    public static final double MAX_WEIGHT_KG = 500;
    public static final double MAX_HEIGHT_FT = 10;
    public static final double MAX_WEIGHT_LBS = 1100;
    public static final int MAX_INCHES = 11;

    // History
    public static final int HISTORY_SIZE = 10;

    // Units
    public static final String UNIT_METRIC = "metric";
    public static final String UNIT_IMPERIAL = "imperial";
}
