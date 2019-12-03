package obllivionsoft.djole.nis.rs.stusdeals.controller.Utills;

import android.app.Activity;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import obllivionsoft.djole.nis.rs.stusdeals.R;






/*
 * Created by Neha Kalia on 7-11-2017.
 */

public class SnackbarUtil {
    public static void showSuccessLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary));
        snackbar.show();
    }

    public static void showSuccessShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary));
        snackbar.show();
    }

    public static void showErrorLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary));
        snackbar.show();
    }

    public static void showErrorShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary));
        snackbar.show();
    }

    public static void showWarningLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary));
        snackbar.show();
    }

    public static void showWarningShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, 4500);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary));
        snackbar.show();
    }
}
