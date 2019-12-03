package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.SnackbarUtil;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Validations;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditText;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;

import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.EmailCheckModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealsCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.EmailCheckResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SubCategoryResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignuptwoActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.tv_next)
    MyTextViewBold tvNext;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    AppCompatActivity mContext;
    String f_name = "", l_name = "", mDob = "",mMessage="";
    @BindView(R.id.et_edu_email)
    MyEditText etEduEmail;
    @BindView(R.id.et_personal_email)
    MyEditText etPersonalEmail;
    @BindView(R.id.et_phone_number)
    MyEditText etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signuptwo);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {


        mContext = SignuptwoActivity.this;
        Intent getData = getIntent();
        f_name = getData.getStringExtra("first_name");
        l_name = getData.getStringExtra("last_name");
        mDob = getData.getStringExtra("dob");

    }

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.ll_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_next:
                if (Validations.isValidateSignupTwo(mContext, etEduEmail, etPersonalEmail)) {
                    if(isValidMobile(etPhoneNumber.getText().toString()))
                    {
                        checkEmailVaild();

                    }
                    else
                    {
                        SnackbarUtil.showWarningShortSnackbar(this, getString(R.string.err_msg_phone_number));
                    }


                }

                break;
            case R.id.ll_login:
                Intent login = new Intent(mContext, LoginActivity.class);
                startActivity(login);
                break;
        }
    }

    private void checkEmailVaild() {
        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        EmailCheckModel emailCheckModel=new EmailCheckModel();
        emailCheckModel.setEmail(etEduEmail.getText().toString().trim());
        Call<EmailCheckResponse> call = apiService.checkEmail(emailCheckModel);
        call.enqueue(new Callback<EmailCheckResponse>() {
            @Override
            public void onResponse(Call<EmailCheckResponse> call, Response<EmailCheckResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    Log.e("respne", response.body().toString() + "");
                    if (response.body() != null) {

                           if(response.body().success==true)
                           {
                               nextActivity();
                           }
                           else
                           {
                               mMessage=response.body().message;
                               opeDialog();
                           }

                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();

                }

            }

            @Override
            public void onFailure(Call<EmailCheckResponse> call, Throwable t) {
                Log.e("exception", t.getMessage());
                ProgressBar.getInstanse().hideDialog();

            }


        });
    }


    private void opeDialog() {

        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.app_name))
                .setMessage(mMessage)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }


                })
                .setIcon(R.drawable.logosignup)
                .show();



    }

    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 9 && phone.length() <= 12;
        }
        return false;
    }

    private void nextActivity() {
        Log.e("email",etEduEmail.getText().toString().trim()+"check"+"");
        Intent signup = new Intent(mContext, SignupthreeActivity.class);
        signup.putExtra("first_name", f_name);
        signup.putExtra("last_name", l_name);
        signup.putExtra("dob",mDob);
        signup.putExtra("educational_email",etEduEmail.getText().toString().trim());
        signup.putExtra("personal_email",etPersonalEmail.getText().toString().trim());
        signup.putExtra("phn_number",etPhoneNumber.getText().toString().trim());
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
