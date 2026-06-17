package com.example.bmicalculator;

/**
 * Utility class for BMI calculations and categorization.
 */
public class BmiCalculator {

    private static final double UNDERWEIGHT_MAX = 18.5;
    private static final double NORMAL_MAX = 25.0;
    private static final double OVERWEIGHT_MAX = 30.0;

    public static double calculateBmi(double weightKg, double heightMeters) {
        if (heightMeters <= 0 || weightKg <= 0) {
            return 0;
        }
        return weightKg / (heightMeters * heightMeters);
    }

    public static String getCategory(double bmi) {
        if (bmi < UNDERWEIGHT_MAX) return "Underweight";
        if (bmi < NORMAL_MAX) return "Normal Weight";
        if (bmi < OVERWEIGHT_MAX) return "Overweight";
        return "Obese";
    }

    public static int getCategoryColor(double bmi) {
        if (bmi < UNDERWEIGHT_MAX) return android.graphics.Color.parseColor("#5B9BD5");
        if (bmi < NORMAL_MAX) return android.graphics.Color.parseColor("#4CAF50");
        if (bmi < OVERWEIGHT_MAX) return android.graphics.Color.parseColor("#FF9800");
        return android.graphics.Color.parseColor("#F44336");
    }

    public static String getCategoryDescription(double bmi) {
        if (bmi < UNDERWEIGHT_MAX)
            return "Your BMI suggests you may be underweight. Consider consulting a healthcare provider for personalized dietary advice.";
        if (bmi < NORMAL_MAX)
            return "Great! You are within the healthy weight range. Maintain a balanced diet and regular physical activity.";
        if (bmi < OVERWEIGHT_MAX)
            return "You are slightly above the healthy range. Incorporating more activity and balanced meals can help.";
        return "Your BMI indicates obesity. It's recommended to speak with a healthcare professional for guidance.";
    }

    public static String getHealthyWeightRange(double heightMeters) {
        if (heightMeters <= 0) return "N/A";
        double minWeight = UNDERWEIGHT_MAX * heightMeters * heightMeters;
        double maxWeight = NORMAL_MAX * heightMeters * heightMeters;
        return String.format("%.1f - %.1f kg", minWeight, maxWeight);
    }

    public static double cmToMeters(double cm) {
        return cm / 100.0;
    }

    public static double feetInchesToMeters(double feet, double inches) {
        double totalInches = (feet * 12) + inches;
        return totalInches * 0.0254;
    }

    public static double lbsToKg(double lbs) {
        return lbs * 0.453592;
    }

    public static int bmiToProgress(double bmi) {
        return (int) Math.min((bmi / 40.0) * 100, 100);
    }
}
