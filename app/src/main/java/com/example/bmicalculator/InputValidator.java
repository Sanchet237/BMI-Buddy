package com.example.bmicalculator;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Utility class for input validation.
 */
public class InputValidator {

    public static boolean validateField(EditText field, String value, String errorMsg) {
        if (TextUtils.isEmpty(value)) {
            field.setError(errorMsg);
            field.requestFocus();
            return false;
        }
        field.setError(null);
        return true;
    }

    public static boolean validatePositive(EditText field, double value, String errorMsg) {
        if (value <= 0) {
            field.setError(errorMsg);
            field.requestFocus();
            return false;
        }
        field.setError(null);
        return true;
    }

    public static double parseDouble(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean validateHeightCm(EditText field, String heightStr) {
        if (!validateField(field, heightStr, "Please enter your height")) return false;
        
        double height = parseDouble(heightStr);
        if (!validatePositive(field, height, "Height must be greater than 0")) return false;
        
        if (height > Constants.MAX_HEIGHT_CM) {
            field.setError("Please enter a realistic height (max " + Constants.MAX_HEIGHT_CM + " cm)");
            return false;
        }
        return true;
    }

    public static boolean validateWeightKg(EditText field, String weightStr) {
        if (!validateField(field, weightStr, "Please enter your weight")) return false;
        
        double weight = parseDouble(weightStr);
        if (!validatePositive(field, weight, "Weight must be greater than 0")) return false;
        
        if (weight > Constants.MAX_WEIGHT_KG) {
            field.setError("Please enter a realistic weight (max " + Constants.MAX_WEIGHT_KG + " kg)");
            return false;
        }
        return true;
    }

    public static boolean validateHeightFt(EditText field, String ftStr) {
        if (!validateField(field, ftStr, "Please enter feet")) return false;
        
        double feet = parseDouble(ftStr);
        if (!validatePositive(field, feet, "Height must be greater than 0")) return false;
        
        if (feet > Constants.MAX_HEIGHT_FT) {
            field.setError("Height too large");
            return false;
        }
        return true;
    }

    public static boolean validateHeightIn(EditText field, String inStr) {
        double inches = TextUtils.isEmpty(inStr) ? 0 : parseDouble(inStr);
        
        if (inches < 0 || inches > Constants.MAX_INCHES) {
            field.setError("Inches must be 0–" + Constants.MAX_INCHES);
            return false;
        }
        field.setError(null);
        return true;
    }

    public static boolean validateWeightLbs(EditText field, String weightStr) {
        if (!validateField(field, weightStr, "Please enter your weight")) return false;
        
        double weight = parseDouble(weightStr);
        if (!validatePositive(field, weight, "Weight must be greater than 0")) return false;
        
        if (weight > Constants.MAX_WEIGHT_LBS) {
            field.setError("Please enter a realistic weight");
            return false;
        }
        return true;
    }

    public static double getSafeValue(String value, double defaultValue) {
        double result = parseDouble(value);
        return result >= 0 ? result : defaultValue;
    }
}
