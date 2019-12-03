package obllivionsoft.djole.nis.rs.stusdeals.controller.Utills;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import java.io.File;

import obllivionsoft.djole.nis.rs.stusdeals.R;


public class Validations {

    /**
     * @param editText - EditText field which need to be validated
     * @return - Returns true if editText is Null or empty
     */
    public static boolean isNullOrEmpty(EditText editText) {
        return editText.getText() == null
                || editText.getText().toString().trim().length() == 0;
    }

    public static boolean isNullOrEmpty(TextView textView) {
        return textView.getText() == null
                || textView.getText().toString().trim().length() == 0;
    }


    public static boolean isContainSpecialCharacter(String string) {
        return string.matches("[a-zA-Z0-9.? ]*");
    }

    private boolean validateFullnameLength(Activity applicationContext, EditText mEtUsername, String errMessage) {
        if (isNullOrEmpty(mEtUsername) && mEtUsername.getText().toString().trim().length() < 2) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            requestFocus(applicationContext, mEtUsername);
            return false;
        }
        return true;
    }

    private void requestFocus(Context applicationContext, View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private static boolean validateUsername(Activity applicationContext, EditText mEtUsername, String errMessage) {
        if (isNullOrEmpty(mEtUsername)) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            mEtUsername.setFocusable(true);
            mEtUsername.requestFocus();
            return true;

        }
        return false;
    }

    private static boolean validateTextView(Activity applicationContext, TextView mTextView, String errMessage) {
        if (isNullOrEmpty(mTextView)) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            return true;

        }
        return false;
    }

    private static boolean validateRoleType(Activity applicationContext, TextView mEtUsername, String errMessage) {
        if (isNullOrEmpty(mEtUsername)) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            // mEtUsername.setFocusable(true);
            //  mEtUsername.requestFocus();
            return true;

        }
        return false;
    }

    private boolean validateBirthdayDate(Activity applicationContext, TextView mTvBirthday, String errMessage) {
        if (isNullOrEmpty(mTvBirthday)) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            mTvBirthday.setFocusable(true);
            mTvBirthday.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateImage(Activity applicationContext, ImageView mIvImage, File file, String errMessage) {
        if (file == null) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            mIvImage.setFocusable(true);
            mIvImage.requestFocus();
            return false;
        }
        return true;
    }

    private static boolean validatePasswordLength(Activity applicationContext, EditText mEtPassword, String errMessage) {
        Log.e("length",mEtPassword.getText().toString().trim().length()+"");
        if (mEtPassword.getText().toString().trim().length() <= 11) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            mEtPassword.setFocusable(true);
            mEtPassword.requestFocus();
            return false;
        }
        return true;
    }


    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    private static boolean validatePasswordContainSpecialCharacter(Activity applicationContext, EditText mEtPassword, String errMessage) {
        if (isContainSpecialCharacter(mEtPassword.getText().toString().trim())) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            mEtPassword.setFocusable(true);
            mEtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private static boolean validateEmailAddress(Activity applicationContext, View view, String errMessage) {
        String email = ((EditText) view).getText().toString().trim();


        if (email.contains("[a-zA-Z0-9._-]+") || email.contains("@")) {
            if (email.isEmpty() || !isValidEmail(email)) {
                SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
                // requestFocus(applicationContext, ((EditText) view));
                return true;
            }
        } else {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage);
            // requestFocus(applicationContext, ((EditText) view));
            return true;
        }

        return false;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isValidatePasswords(Activity applicationContext, View view, View view1, String errMessage) {
        String password = ((EditText) view).getText().toString().trim();
        String confrimpassword = ((EditText) view1).getText().toString().trim();
        return password.equalsIgnoreCase(confrimpassword);

    }

    public static boolean isValidateLogin(Activity applicationContext, EditText mEtUsername, EditText mEtPassword) {
        if (validateEmailAddress(applicationContext, mEtUsername,applicationContext.getResources().getString(R.string.err_msg_email))) {
            return false;
        }
        if (validateUsername(applicationContext, mEtPassword,applicationContext.getResources().getString(R.string.err_msg_password))) {
            return false;
        }
        return true;
    }
    public static boolean isValidateDemand(Activity applicationContext, EditText mEtCity, EditText mEtOrg,EditText  mEtdealtype) {
        if (validateUsername(applicationContext, mEtCity,applicationContext.getResources().getString(R.string.err_msg_email))) {
            return false;
        }
        if (validateUsername(applicationContext, mEtOrg,applicationContext.getResources().getString(R.string.err_msg_password))) {
            return false;
        }
        if (validateUsername(applicationContext, mEtdealtype,applicationContext.getResources().getString(R.string.err_msg_password))) {
            return false;
        }
        return true;
    }
    public static boolean isValidateSignupOne(Activity applicationContext, EditText mEtFirsname, EditText mEtLastname) {
        if (validateUsername(applicationContext, mEtFirsname,applicationContext.getResources().getString(R.string.err_msg_first_name))) {
            return false;
        }
        if (validateUsername(applicationContext, mEtLastname,applicationContext.getResources().getString(R.string.err_msg_last_name))) {
            return false;
        }
        return true;

    }
    public static boolean isValidateSignupTwo(Activity applicationContext, EditText mEtEduEmail, EditText mEtPersonalEmail) {
        if (validateEmailAddress(applicationContext, mEtEduEmail, applicationContext.getResources().getString(R.string.err_msg_edu_email))) {
            return false;
        }
        if (validateEmailAddress(applicationContext, mEtPersonalEmail, applicationContext.getResources().getString(R.string.err_msg_personal_email))) {
            return false;
        }
        return true;
    }

    public static boolean isValidateSignupFour(Activity applicationContext, EditText mEtPassword) {

        if (validateUsername(applicationContext, mEtPassword, applicationContext.getResources().getString(R.string.err_msg_password))) {
            return false;
        }
        return true;
    }
//
//    public static boolean isValidateCreateAccount(Activity applicationContext, EditText mEtName, EditText mEtEmailAddress, EditText mEtPassword, EditText mEtphonenumber) {
//
//        if (validateUsername(applicationContext, mEtName, applicationContext.getResources().getString(R.string.err_msg_name))) {
//            return false;
//        }
//        if (validateEmailAddress(applicationContext, mEtEmailAddress, applicationContext.getResources().getString(R.string.err_msg_email))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtPassword, applicationContext.getResources().getString(R.string.err_msg_password))) {
//            return false;
//        }
////        if (validateUsername(applicationContext, mEtAddress, applicationContext.getResources().getString(R.string.err_msg_address))) {
////            return false;
////        }
//        if (validateUsername(applicationContext, mEtphonenumber, applicationContext.getResources().getString(R.string.err_msg_phone_number))) {
//            return false;
//        }
//
//        return true;
//    }



//    public static boolean isValidateupdateProfile(Activity applicationContext, EditText mEtRestaurantname , EditText mEtphonenumber, EditText mEtAddress,EditText mEtDescription) {
//
//        if (validateUsername(applicationContext, mEtRestaurantname, applicationContext.getResources().getString(R.string.err_msg_Restaurant_name))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtphonenumber, applicationContext.getResources().getString(R.string.err_msg_phone_number))) {
//            return false;
//        }
//        if (validateEmailAddress(applicationContext, mEtAddress, applicationContext.getResources().getString(R.string.err_msg_address))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtDescription, applicationContext.getResources().getString(R.string.err_msg_password))) {
//            return false;
//        }
//
//
//        return true;
//    }
//
//
//    public static boolean isValidateCreateAccount(Activity applicationContext, EditText mEtRestaurantname, EditText mEtName, EditText mEtEmailAddress, EditText mEtPassword, EditText mEtConfirmPassword, EditText mEtphonenumber) {
//
//        if (validateUsername(applicationContext, mEtRestaurantname, applicationContext.getResources().getString(R.string.err_msg_Restaurant_name))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtName, applicationContext.getResources().getString(R.string.name))) {
//            return false;
//        }
//        if (validateEmailAddress(applicationContext, mEtEmailAddress, applicationContext.getResources().getString(R.string.err_msg_email))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtPassword, applicationContext.getResources().getString(R.string.err_msg_password))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtConfirmPassword, applicationContext.getResources().getString(R.string.err_msg_cnf_password))) {
//            return false;
//        }
////        if (validateUsername(applicationContext, mEtAddress, applicationContext.getResources().getString(R.string.err_msg_address))) {
////            return false;
////        }
//        if (validateUsername(applicationContext, mEtphonenumber, applicationContext.getResources().getString(R.string.err_msg_phone_number))) {
//            return false;
//        }
//
//        return true;
//    }
//
//
//
//
//
//
////        return true;
////
////    }
////    public static boolean isValidateEditProfile(Activity applicationContext, EditText mEtUsername, EditText mEtPassword) {
////        if (validateUsername(applicationContext, mEtUsername,applicationContext.getResources().getString(R.string.err_msg_full_name))) {
////            return false;
////        }
////        if (validateUsername(applicationContext, mEtPassword,applicationContext.getResources().getString(R.string.err_msg_password))) {
////            return false;
////        }
////        return true;
////    }
//
//
//    /**
//     * Validating form Login
//     *
//     * @param applicationContext
//     * @param mEtUsername
//     * @param mEtPassword
//     */
//    public static boolean isValidateAdditem(Activity applicationContext, EditText mEtUsername, EditText mEtPassword) {
//        if (validateUsername(applicationContext, mEtUsername,applicationContext.getResources().getString(R.string.err_msg_add_Item))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtPassword,applicationContext.getResources().getString(R.string.err_msg_add_Price))) {
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean isValidateLogin(Activity applicationContext, EditText mEtUsername, EditText mEtPassword) {
//        if (validateUsername(applicationContext, mEtUsername,applicationContext.getResources().getString(R.string.err_msg_email))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtPassword,applicationContext.getResources().getString(R.string.err_msg_password))) {
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean isValidateChangepwd(Activity applicationContext, EditText mEtUsername, EditText mEtPassword,EditText mEtCnfrmpwd) {
//        if (validateUsername(applicationContext, mEtUsername,applicationContext.getResources().getString(R.string.err_msg_old_password))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtPassword,applicationContext.getResources().getString(R.string.err_msg_password))) {
//            return false;
//        }
//        if (validateUsername(applicationContext, mEtCnfrmpwd,applicationContext.getResources().getString(R.string.err_msg_cnf_password))) {
//            return false;
//        }
//        return true;
//    }
//    public static boolean isValidateForgotpwd(Activity applicationContext, EditText mEtForgot) {
//        if (validateUsername(applicationContext, mEtForgot,applicationContext.getResources().getString(R.string.err_msg_email))) {
//            return false;
//        }
//
//        return true;
//    }





}
