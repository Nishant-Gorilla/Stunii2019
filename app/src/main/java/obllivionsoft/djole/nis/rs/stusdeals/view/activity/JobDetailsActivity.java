package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.JobDetailRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.JobDetailsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivity extends AppCompatActivity {

    @BindView(R.id.yv_back)
    AppCompatImageView yvBack;
    @BindView(R.id.iv_share)
    AppCompatImageView ivShare;
    AppCompatActivity mContext;
    String mJobid = "";
    @BindView(R.id.img_logo)
    CircleImageView imgLogo;
    @BindView(R.id.tv_job_title)
    MyTextViewBold tvJobTitle;
    @BindView(R.id.tv_company_name)
    MyTextViewBold tvCompanyName;
    @BindView(R.id.tv_address)
    MyTextViewBold tvAddress;
    @BindView(R.id.tv_job_type)
    MyTextView tvJobType;
    @BindView(R.id.tv_hourly_rate)
    MyTextView tvHourlyRate;
    @BindView(R.id.tv_job_desc)
    MyTextView tvJobDesc;
    @BindView(R.id.tv_apply_jobs)
    MyTextViewBold tvApplyJobs;
    String mJobTitle="",mCompanyname="",mAddress="",mJobType="",mHourly="",mJobDec="",mImage="",mEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_job_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext = JobDetailsActivity.this;
        Intent getData = getIntent();
        mJobid = getData.getStringExtra("job_id");

        getJobDetails();


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>get job details apis>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getJobDetails() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        JobDetailRequest jobdetailrequest = new JobDetailRequest();
        jobdetailrequest.setJobId(mJobid);
        Call<JobDetailsResponse> call = apiService.getJobDetails("application/json", jobdetailrequest);
        call.enqueue(new Callback<JobDetailsResponse>() {
            @Override
            public void onResponse(Call<JobDetailsResponse> call, Response<JobDetailsResponse> response) {
                try {
                    if (response.body() != null) {
                        mJobTitle=response.body().data.jobName;
                        mJobType=response.body().data.jobType;
                        mJobDec=response.body().data.jobDescription;
                        mCompanyname=response.body().data.companyName;
                        mImage=response.body().data.companyImage;
                        mHourly=response.body().data.jobRate;
                        mAddress=response.body().data.jobAddress;
                        mEmail=response.body().data.companyEmail;

                        setJobData();


                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<JobDetailsResponse> call, Throwable t) {
                Log.e("exception", t.getMessage());

            }


        });


    }

    private void setJobData() {

        tvJobTitle.setText(mJobTitle);
        tvCompanyName.setText(mCompanyname);
        tvAddress.setText(mAddress);
        tvHourlyRate.setText(mHourly);
        tvJobDesc.setText(mJobDec);
        tvJobType.setText(mJobType);
        Glide.with(mContext)
                .load(AppConstant.imageURL+mImage)
                .into(imgLogo);
    }

    @OnClick({R.id.yv_back, R.id.iv_share,R.id.tv_apply_jobs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yv_back:
                onBackPressed();
                break;
            case R.id.iv_share:
                break;
            case R.id.tv_apply_jobs:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", mEmail,null));
                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"jobs@stunii.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Apply for "+mJobTitle);
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi please find attachment");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
