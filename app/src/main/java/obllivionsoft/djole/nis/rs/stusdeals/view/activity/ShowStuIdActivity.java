package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.StuIDResponseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowStuIdActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    AppCompatImageView imgBack;
    @BindView(R.id.tv_done)
    MyTextView tvDone;
    @BindView(R.id.ll_verified)
    LinearLayout llVerified;
    @BindView(R.id.ll_back)
    RelativeLayout llBack;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.tv_name)
    MyTextViewBold tvName;
    @BindView(R.id.tv_university)
    MyTextView tvUniversity;
    @BindView(R.id.tv_verified_date)
    MyTextView tvVerifiedDate;
    @BindView(R.id.tv_course)
    MyTextView tvCourse;
    AppCompatActivity mContext;
    String mUserid="",mAccessToken="";
    String mName="",mUniversity="",mVerified="",mCourse="",mImage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stu_id);
        ButterKnife.bind(this);
        InitView();
    }

    private void InitView() {
        mContext=ShowStuIdActivity.this;
        mUserid = AppPreferences.init(mContext).getString(AppConstant.USER_ID);
        mAccessToken = AppPreferences.init(mContext).getString(AppConstant.ACCESS_TOKEN);
        Log.e("token",mAccessToken+"");

        getStuiddetails();
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.get stu id details>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getStuiddetails() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<StuIDResponseModel> call = apiService.stuIdInfo("students/" + mUserid,"application/json","YW5kcm9pZF9hcHA6MzA1MEI3V1QwVmoz",mAccessToken);
        call.enqueue(new Callback<StuIDResponseModel>() {
            @Override
            public void onResponse(Call<StuIDResponseModel> call, Response<StuIDResponseModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        mName=response.body().fname+" "+response.body().lname;
                        String verifiedUntil =response.body().created_at;
                        mVerified = response.body().graduationDate;
                        mUniversity=response.body().institution;
                        mCourse=response.body().course;
                        mImage=response.body().photo;
                        setData();


                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<StuIDResponseModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });

    }

    private void setData() {

        tvName.setText(mName);
        if(!mVerified.isEmpty())
        {
            tvVerifiedDate.setText("Verified until "+mVerified);
        }

        tvUniversity.setText(mUniversity);
        tvCourse.setText(mCourse);
        if(!mImage.isEmpty())
        {
            Glide.with(mContext)
                    .load(AppConstant.imgStudentUrl + mUserid + "/o/" + mImage)
                    .into(imgProfile);
        }
    }

    @OnClick({R.id.img_back, R.id.tv_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_done:
                onBackPressed();
                break;
        }
    }
    private String GetVerifiedUntilDate(String verifiedUntil) {


        SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.tsFromServer);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(verifiedUntil));
            cal.add(Calendar.YEAR, 5);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy");
        return "Verified until " + displayFormat.format(cal.getTime());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
