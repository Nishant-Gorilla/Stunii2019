package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.SnackbarUtil;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupSevenActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.ll_night)
    LinearLayout llNight;
    @BindView(R.id.ll_pub)
    LinearLayout llPub;
    @BindView(R.id.ll_meal)
    LinearLayout llMeal;
    @BindView(R.id.ll_cozy)
    LinearLayout llCozy;
    @BindView(R.id.tv_next)
    MyTextViewBold tvNext;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    AppCompatActivity mContext;
    String mNightout="",mAreyou="", mGender="",mFreebiesOffers="",university = "", mCourses = "", mGraduationDate = "",f_name="",l_name="",mDob="",eduEmail="",mPersnalMail="",mPhoneNumber="",mPassword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_seven);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        mContext=SignupSevenActivity.this;

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
        mFreebiesOffers=getData.getStringExtra("freebies");
        mPassword=getData.getStringExtra("pwd");
        mGender=getData.getStringExtra("gender");
        mAreyou=getData.getStringExtra("areYou");
    }


    @OnClick({R.id.iv_back, R.id.ll_night, R.id.ll_pub, R.id.ll_meal, R.id.ll_cozy, R.id.tv_next, R.id.ll_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_night:
                mNightout="1";
                llNight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle_line));
                llPub.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llMeal.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llCozy.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                break;
            case R.id.ll_pub:
                mNightout="2";
                llNight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llPub.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle_line));
                llMeal.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llCozy.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                break;
            case R.id.ll_meal:
                mNightout="3";
                llNight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llPub.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llMeal.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle_line));
                llCozy.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                break;
            case R.id.ll_cozy:
                mNightout="4";
                llNight.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llPub.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llMeal.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llCozy.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle_line));
                break;
            case R.id.tv_next:
                if(!mNightout.isEmpty())
                {
                    nextActivity();
                }
                else{
                    SnackbarUtil.showWarningShortSnackbar(this, getString(R.string.err_msg_selcet));
                }
                break;
            case R.id.ll_login:
                Intent login=new Intent(mContext,LoginActivity.class);
                startActivity(login);
                break;
        }
    }

    private void nextActivity() {
        Intent signup = new Intent(mContext, SignupeightActivity.class);
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
        signup.putExtra("gender",mGender);
        signup.putExtra("areYou",mAreyou);
        signup.putExtra("mNightout",mNightout);
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
