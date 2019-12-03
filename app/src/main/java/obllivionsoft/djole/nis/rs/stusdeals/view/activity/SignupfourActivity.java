package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.SnackbarUtil;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Validations;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditTextBold;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupfourActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.et_pwd)
    MyEditTextBold etPwd;
    @BindView(R.id.radioNope)
    RadioButton radioNope;
    @BindView(R.id.radioYeah)
    RadioButton radioYeah;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.tv_next)
    MyTextViewBold tvNext;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    AppCompatActivity mContext;
    @BindView(R.id.switch_button)
    SwitchCompat switchButton;
    String isCheck="",mFreebies="",mFreebiesOffers="",university = "", mCourses = "", mGraduationDate = "",f_name="",l_name="",mDob="",eduEmail="",mPersnalMail="",mPhoneNumber="",mPassword="";
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signupfour);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext = SignupfourActivity.this;
//        signup.putExtra("course", mCourses);
//        signup.putExtra("university", university);
//        signup.putExtra("grdDate", mGraduationDate);
        Intent getData=getIntent();
        university=getData.getStringExtra("university");
        mCourses=getData.getStringExtra("course");
        mGraduationDate=getData.getStringExtra("grdDate");
        f_name=getData.getStringExtra("first_name");
        l_name=getData.getStringExtra("last_name");
        mDob=getData.getStringExtra("dob");
        eduEmail=getData.getStringExtra("educational_email");
        mPersnalMail=getData.getStringExtra("personal_email");
        mPhoneNumber=getData.getStringExtra("phn_number");
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);

        RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(checkedId);
                mFreebies = checkedRadioButton.getText().toString();
                if (mFreebies.equalsIgnoreCase("Nope")) {
                    mFreebiesOffers = "2";
                } else {
                    mFreebiesOffers = "1";
                }

            }
        });


            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    isCheck="1";
                }
                else{
                    isCheck="0";
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.ll_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_next:
                mPassword=etPwd.getText().toString().trim();
                Log.e("check",isCheck+"");
                Log.e("free",mFreebiesOffers+"");
                if (Validations.isValidateSignupFour(mContext, etPwd)) {
                    if (isCheck.equalsIgnoreCase("1"))
                    {
                        if(!mFreebiesOffers.isEmpty())
                        {
                            nextActivity();
                        }
                        else
                        {
                            SnackbarUtil.showWarningShortSnackbar(this, getString(R.string.err_msg_freebies));
                        }

                    }
                    else
                    {
                        SnackbarUtil.showWarningShortSnackbar(this, getString(R.string.err_msg_terms));
                    }


                }
                break;
            case R.id.ll_login:
                Intent login = new Intent(mContext, LoginActivity.class);
                startActivity(login);
                break;
        }
    }

    private void nextActivity() {
        Intent signup = new Intent(mContext, SignupfiveActivity.class);
        signup.putExtra("course", mCourses);
        signup.putExtra("university", university);
        signup.putExtra("grdDate", mGraduationDate);
        signup.putExtra("first_name", f_name);
        signup.putExtra("last_name", l_name);
        signup.putExtra("dob", mDob);
        signup.putExtra("educational_email", eduEmail);
        signup.putExtra("personal_email", mPersnalMail);
        signup.putExtra("phn_number",mPhoneNumber);
        signup.putExtra("pwd",mPassword);
        signup.putExtra("freebies",mFreebiesOffers);
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
