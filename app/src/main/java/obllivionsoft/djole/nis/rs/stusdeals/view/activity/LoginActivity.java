package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Validations;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditText;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditTextBold;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.ForgotRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.LoginRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ForgorRequestResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.LoginResponseModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AppCompatActivity mContext;
    @BindView(R.id.iv_gif)
    AppCompatImageView ivGif;
    @BindView(R.id.et_email)
    MyEditText etEmail;
    @BindView(R.id.et_pwd)
    MyEditTextBold etPwd;
    @BindView(R.id.tv_forgot_pwd)
    MyTextViewBold tvForgotPwd;
    @BindView(R.id.tv_login)
    MyTextViewBold tvLogin;
    @BindView(R.id.tv_signup)
    MyTextView tvSignup;
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    String mForgetEmail="",mAccessToken="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mContext = LoginActivity.this;
        mAccessToken=AppPreferences.init(mContext).getString(AppConstant.ACCESS_TOKEN);
////>>>>>>>>>>>>>>>>>>play the gif>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Glide.with(mContext).asGif().load(R.raw.owlgif).into(ivGif);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.tv_forgot_pwd, R.id.tv_login, R.id.tv_signup,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_pwd:
                openForgotDialog();
                break;
            case R.id.tv_login:

                if (Validations.isValidateLogin(mContext, etEmail, etPwd)) {
                    dologin();

                }

                break;
            case R.id.tv_signup:
                Intent signup = new Intent(mContext, SignuoneActivity.class);
                startActivity(signup);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>open forgot dialog>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void openForgotDialog() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_forgot_password);
        Button reset = (Button) dialog.findViewById(R.id.btn_reset);
        Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        MyEditText email=(MyEditText) dialog.findViewById(R.id.et_email);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Log.e("email",email.getText().toString()+"");
                mForgetEmail=email.getText().toString();
                if(!mForgetEmail.isEmpty())
                {
                    ForgotPassword();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(mContext, "Email is Required", Toast.LENGTH_SHORT).show();
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void ForgotPassword() {
        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        ForgotRequestModel forgotRequestModel=new ForgotRequestModel();
        forgotRequestModel.setEmail(mForgetEmail);
        Call<ForgorRequestResponse> call = apiService.forgetPwd("application/json","YW5kcm9pZF9hcHA6MzA1MEI3V1QwVmoz", forgotRequestModel);
        call.enqueue(new Callback<ForgorRequestResponse>() {
            @Override
            public void onResponse(Call<ForgorRequestResponse> call, Response<ForgorRequestResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {

                            Toast.makeText(mContext, "Instructions to reset your password have been sent to your email if your email is registered with us", Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();

                }

            }

            @Override
            public void onFailure(Call<ForgorRequestResponse> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
                Log.e("exception",t.getMessage());
                Toast.makeText(LoginActivity.this, "error occoured", Toast.LENGTH_SHORT).show();

            }


        });




    }

    private void dologin() {
        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setEmail(etEmail.getText().toString().trim());
        loginRequestModel.setPassword(etPwd.getText().toString().trim());
        Call<LoginResponseModel> call = apiService.loginUser("application/json","YW5kcm9pZF9hcHA6MzA1MEI3V1QwVmoz",mAccessToken, loginRequestModel);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.code() == 400)
                    {
                        openDialog();
                      //  Toast.makeText(LoginActivity.this, "Invaild Login Crential please check again", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                        Intent login = new Intent(mContext, HomeActivity.class);
                        startActivity(login);
                        finishAffinity();


                    }

                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();

                }

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
                Log.e("exception",t.getMessage());
                Toast.makeText(LoginActivity.this, "error occoured", Toast.LENGTH_SHORT).show();

            }


        });




    }
//>>>>>>>>>>>>>>>>>>>>>>>>.incorrect credential error>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void openDialog() {

        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.app_name))
                .setMessage("You’re so close but that looks like the wrong login details. Please make sure you’re using an Educational Email Address. You can also use the “Forgot Password” section if needs be. If you're still having issues singing in, please don’t hesitate to contact us on: help@stunii.com")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }


                })
                .setIcon(R.drawable.logosignup)
                .show();
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
