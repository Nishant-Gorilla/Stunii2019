package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.QrClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ReferralResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ReferralSuccessModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralActivity extends AppCompatActivity {

    @BindView(R.id.image_cross)
    AppCompatImageView imageCross;
    @BindView(R.id.sp_referral)
    AppCompatSpinner spReferral;
    @BindView(R.id.tv_submit)
    MyTextViewBold tvSubmit;
    @BindView(R.id.tv_skip)
    MyTextViewBold tvSkip;
    AppCompatActivity mContext;
    List<ReferralResponseModel.From> mList;
    ArrayList<String> mFromlist;
    String mId = "", mEmail = "";
    String mNAme = "", mobileNumber = "", mLastname = "", mReferFrom = "", mUniversity = "";
    @BindView(R.id.tv_txt)
    MyTextViewBold tvTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_referral);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mContext = ReferralActivity.this;
        mFromlist = new ArrayList<>();

        mId = AppPreferences.init(this).getString(AppConstant.USER_ID);
        // mEmail = AppPreferences.init(this).getString(AppConstant.USER_EMAIL);
        mEmail = AppPreferences.init(this).getString(AppConstant.USER_EMAIL);
        mNAme = AppPreferences.init(this).getString(AppConstant.USER_FIRST_NAME);
        mLastname = AppPreferences.init(this).getString(AppConstant.USER_LAST_NAME);
        mobileNumber = AppPreferences.init(this).getString(AppConstant.USER_MOBILE);
        mUniversity = AppPreferences.init(this).getString(AppConstant.USER_UNIVERSITY);

        tvTxt.setText("Congratulations... Welcome to Stunii Premium, you are now part of the STUNii family and our VIP Crew! \n We are always here to help, if you have any issues contact us directly via social media @stuniiapp \n We would love to know how you found out about Stunii Premium, please select from one of the drop down options");

        getReferral();
    }

    private void getReferral() {

        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ReferralResponseModel> call = apiService.getReferral();
        call.enqueue(new Callback<ReferralResponseModel>() {
            @Override
            public void onResponse(Call<ReferralResponseModel> call, Response<ReferralResponseModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {

                        mList = response.body().from;
                        if (!mList.isEmpty()) {
                            for (int i = 0; i < mList.size(); i++) {
                                mFromlist.add(mList.get(i).referral_from);
                            }
                            setAdapter();
                        }


                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<ReferralResponseModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>adapter >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void setAdapter() {

        ArrayAdapter eduAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mFromlist);
        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spReferral.setAdapter(eduAdapter);
        spReferral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mReferFrom = mFromlist.get(i);
                Log.e("university", mReferFrom + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick({R.id.image_cross, R.id.tv_submit, R.id.tv_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_cross:
                passIntent();
                break;
            case R.id.tv_submit:
                hitReferalapi();
                break;
            case R.id.tv_skip:
                passIntent();
                break;
        }
    }

    private void hitReferalapi() {

        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = QrClient.getClientQr().create(ApiInterface.class);
        Call<ReferralSuccessModel> call = apiService.postReferral("api/reference?" + "userid=" + mId + "&fullname=" + mNAme + " " + mLastname + "&email=" + mEmail + "&referBy=" + mReferFrom + "&university=" + mUniversity);
        call.enqueue(new Callback<ReferralSuccessModel>() {
            @Override
            public void onResponse(Call<ReferralSuccessModel> call, Response<ReferralSuccessModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {

                        if (response.body().status.equalsIgnoreCase("1")) {
                            passIntent();
                        }
                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<ReferralSuccessModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>redirect user to home page>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void passIntent() {
        Intent i = new Intent(mContext, HomeActivity.class);
        startActivity(i);
        finishAffinity();

    }


}
