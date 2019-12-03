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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.SnackbarUtil;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.SignupRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SignupResponseModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupeightActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.ll_gym)
    LinearLayout llGym;
    @BindView(R.id.ll_library)
    LinearLayout llLibrary;
    @BindView(R.id.ll_gaming)
    LinearLayout llGaming;
    @BindView(R.id.ll_outdoors)
    LinearLayout llOutdoors;
    @BindView(R.id.tv_next)
    MyTextViewBold tvNext;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    AppCompatActivity mContext;
    String mAccessToken="", mFindme="",mNightout="",mAreyou="", mGender="",mFreebiesOffers="",university = "", mCourses = "", mGraduationDate = "",f_name="",l_name="",mDob="",eduEmail="",mPersnalMail="",mPhoneNumber="",mPassword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signupeight);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext=SignupeightActivity.this;
        mAccessToken=AppPreferences.init(mContext).getString(AppConstant.ACCESS_TOKEN);
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
        mNightout=getData.getStringExtra("mNightout");
    }

    @OnClick({R.id.iv_back, R.id.ll_gym, R.id.ll_library, R.id.ll_gaming, R.id.ll_outdoors, R.id.tv_next, R.id.ll_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_gym:
                mFindme="1";
                llGym.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle_line));
                llLibrary.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llGaming.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llOutdoors.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                break;
            case R.id.ll_library:
                mFindme="2";
                llGym.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llLibrary.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle_line));
                llGaming.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llOutdoors.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                break;
            case R.id.ll_gaming:
                mFindme="3";
                llGym.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llLibrary.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llGaming.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle_line));
                llOutdoors.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                break;
            case R.id.ll_outdoors:
                mFindme="4";
                llGym.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llLibrary.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llGaming.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle));
                llOutdoors.setBackground(ContextCompat.getDrawable(mContext, R.drawable.boarder_circle_line));
                break;
            case R.id.tv_next:
                if(!mFindme.isEmpty())
                {
                    doSignup();
                }
                else
                {
                    SnackbarUtil.showWarningShortSnackbar(this, getString(R.string.err_msg_selcet));
                }

                break;
            case R.id.ll_login:
                Intent login=new Intent(mContext,LoginActivity.class);
                startActivity(login);
                break;
        }
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>signup api call>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void doSignup() {
        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        SignupRequestModel signupRequestModel=new SignupRequestModel();
        signupRequestModel.setFname(f_name);
        signupRequestModel.setLname(l_name);
        signupRequestModel.setDob(mDob);
        signupRequestModel.setEmail(eduEmail);
        signupRequestModel.setPersonal_email(mPersnalMail);
        signupRequestModel.setPhone_number(mPhoneNumber);
        signupRequestModel.setInstitution(university);
        signupRequestModel.setCourse(mCourses);
        signupRequestModel.setGraduationDate(mGraduationDate);
        signupRequestModel.setPassword(mPassword);
        signupRequestModel.setEmailNotification(mFreebiesOffers);
        signupRequestModel.setGender(Integer.parseInt(mGender));
        signupRequestModel.setAreYou(mAreyou);
        signupRequestModel.setPerfectNightOut(mNightout);
        signupRequestModel.setFindMe(mFindme);
        signupRequestModel.setDevice_token("");
        Call<SignupResponseModel> call = apiService.signUp("application/json","YW5kcm9pZF9hcHA6MzA1MEI3V1QwVmoz",mAccessToken, signupRequestModel);
        call.enqueue(new Callback<SignupResponseModel>() {
            @Override
            public void onResponse(Call<SignupResponseModel> call, Response<SignupResponseModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.code() == 400) {
                        Toast.makeText(mContext, "Email is already registered with us", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {

                        AppPreferences.init(mContext).putString(AppConstant.ACCESS_TOKEN, response.body().access_token);
                        AppPreferences.init(mContext).putString(AppConstant.USER_ID, response.body()._id);
                        AppPreferences.init(mContext).putString(AppConstant.USER_FIRST_NAME, response.body().fname);
                        AppPreferences.init(mContext).putString(AppConstant.USER_LAST_NAME, response.body().lname);
                        AppPreferences.init(mContext).putString(AppConstant.USER_EMAIL, response.body().email);
                        AppPreferences.init(mContext).putString(AppConstant.USER_MOBILE, response.body().phone_number);
                        AppPreferences.init(mContext).putString(AppConstant.USER_UNIVERSITY, response.body().institution);
                        fireIntent();


                    }

                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();

                }
            }
                @Override
                public void onFailure(Call<SignupResponseModel> call, Throwable t) {
                    ProgressBar.getInstanse().hideDialog();
                    Log.e("exception",t.getMessage());
                    Toast.makeText(mContext, "error occoured", Toast.LENGTH_SHORT).show();

                }



        });
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.home intent>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void fireIntent() {
        Intent login = new Intent(mContext, HomeActivity.class);
        startActivity(login);
        finishAffinity();

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
