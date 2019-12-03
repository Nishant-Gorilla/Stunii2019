package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.SnackbarUtil;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Validations;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditText;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignuoneActivity extends AppCompatActivity   {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.tv_next)
    MyTextViewBold tvNext;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    AppCompatActivity mContext;
    @BindView(R.id.tv_first_name)
    MyEditText tvFirstName;
    @BindView(R.id.tv_last_name)
    MyEditText tvLastName;
    @BindView(R.id.tv_dob)
    MyTextView tvDob;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signuone);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext = SignuoneActivity.this;
    }

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.ll_login,R.id.tv_dob})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_dob:
                openDate();
                break;
            case R.id.tv_next:
                if (Validations.isValidateSignupOne(mContext, tvFirstName, tvLastName)) {
                    if(!tvDob.getText().toString().isEmpty())
                    {
                        nextActivity();
                    }
                    else
                    {
                        SnackbarUtil.showWarningShortSnackbar(this, getString(R.string.err_msg_dob));
                    }

                }

                break;
            case R.id.ll_login:
                Intent login = new Intent(mContext, LoginActivity.class);
                startActivity(login);
                break;
        }
    }

    private void openDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        picker = new DatePickerDialog(mContext,AlertDialog.THEME_HOLO_DARK,

        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvDob.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }


    private void nextActivity() {
        Intent signup = new Intent(mContext, SignuptwoActivity.class);
        signup.putExtra("first_name", tvFirstName.getText().toString());
        signup.putExtra("last_name", tvLastName.getText().toString());
        signup.putExtra("dob", tvDob.getText().toString());
        startActivity(signup);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>keyboard hiding>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(ev);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + w.getLeft() - scrcoords[0];
            float y = ev.getRawY() + w.getTop() - scrcoords[1];

            if (ev.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                hideSoftKeyboard(mContext);
            }
        }
        return ret;

    }
}
