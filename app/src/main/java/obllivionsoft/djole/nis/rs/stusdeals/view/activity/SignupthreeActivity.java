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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.SnackbarUtil;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupthreeActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.sp_institute)
    AppCompatSpinner spInstitute;
    @BindView(R.id.sp_course)
    AppCompatSpinner spCourse;
    @BindView(R.id.tv_next)
    MyTextViewBold tvNext;
    AppCompatActivity mContext;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    String[] educational;
    String[] courses;
    String university = "", mCourses = "", mGraduationDate = "",f_name="",l_name="",mDob="",eduEmail="",mPersnalMail="",mPhoneNumber="";
    @BindView(R.id.tv_grd_date)
    MyTextView tvGrdDate;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signupthree);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {


        mContext = SignupthreeActivity.this;
        Intent getData=getIntent();
        f_name=getData.getStringExtra("first_name");
        l_name=getData.getStringExtra("last_name");
        mDob=getData.getStringExtra("dob");
        eduEmail=getData.getStringExtra("educational_email");
        mPersnalMail=getData.getStringExtra("personal_email");
        mPhoneNumber=getData.getStringExtra("phn_number");

        educational = getResources().getStringArray(R.array.educational);
        courses = getResources().getStringArray(R.array.Courses);


        ArrayAdapter eduAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, educational);
        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spInstitute.setAdapter(eduAdapter);
        spInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                } else {
                    university = educational[i];
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter courseAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spCourse.setAdapter(courseAdapter);
        spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                } else {
                    mCourses = courses[i];
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick({R.id.iv_back, R.id.tv_next, R.id.ll_login,R.id.tv_grd_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_next:
                mGraduationDate=tvGrdDate.getText().toString();
                if(!mCourses.isEmpty()&&!mGraduationDate.isEmpty()&&!university.isEmpty())
                {
                    Intent signup = new Intent(mContext, SignupfourActivity.class);
                    signup.putExtra("course", mCourses);
                    signup.putExtra("university", university);
                    signup.putExtra("grdDate", mGraduationDate);
                    signup.putExtra("first_name", f_name);
                    signup.putExtra("last_name", l_name);
                    signup.putExtra("dob", mDob);
                    signup.putExtra("educational_email", eduEmail);
                    signup.putExtra("personal_email", mPersnalMail);
                    signup.putExtra("phn_number",mPhoneNumber);
                    startActivity(signup);
                }
                else
                {
                    SnackbarUtil.showWarningShortSnackbar(this, getString(R.string.err_msg_all_required));
                }

                break;
            case R.id.ll_login:
                Intent login = new Intent(mContext, LoginActivity.class);
                startActivity(login);
                break;
            case R.id.tv_grd_date:
               openDatePicker();
                break;
        }
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>pic graduation date>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void openDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        picker = new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvGrdDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, day);
        picker.show();

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
