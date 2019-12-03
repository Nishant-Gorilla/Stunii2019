package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Validations;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditText;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.DemandRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DemandResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemandActivity extends AppCompatActivity {

    @BindView(R.id.tv_city)
    MyEditText tvCity;
    @BindView(R.id.tv_organization)
    MyEditText tvOrganization;
    @BindView(R.id.tv_deal_type)
    MyEditText tvDealType;
    @BindView(R.id.tv_demand)
    LinearLayout tvDemand;
    @BindView(R.id.mBack)
    AppCompatImageView mBack;
    String mNAme="";
    AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_demand);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext=DemandActivity.this;
        mNAme = AppPreferences.init(mContext).getString(AppConstant.USER_FIRST_NAME);
    }


    @OnClick({R.id.mBack, R.id.tv_demand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBack:
                onBackPressed();
                break;
            case R.id.tv_demand:
                if (Validations.isValidateDemand(mContext, tvCity, tvOrganization,tvDealType)) {
                    demandDeals();

                }
                break;
        }
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.hit demand deal >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void demandDeals() {
        ProgressBar.getInstanse().showDialog(mContext);
        DemandRequestModel demandRequestModel=new DemandRequestModel();
        demandRequestModel.setCity(tvCity.getText().toString());
        demandRequestModel.setOrganisation(tvCity.getText().toString());
        demandRequestModel.setDealType(tvDealType.getText().toString());
        demandRequestModel.setStudent_name(mNAme);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DemandResponseModel> call = apiService.demandDeal("application/json", demandRequestModel);
        call.enqueue(new Callback<DemandResponseModel>() {
            @Override
            public void onResponse(Call<DemandResponseModel> call, Response<DemandResponseModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if(response.body().status==200)
                    {
                        opeDialog();
                    }
                    else
                    {
                        Toast.makeText(mContext, "There is some error please try again after some time", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();

                }

            }

            @Override
            public void onFailure(Call<DemandResponseModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
                Log.e("exception",t.getMessage());

            }


        });


    }

    private void opeDialog() {

        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.app_name))
                .setMessage("Thank you! Your Deal Demand has successfully been submitted. Be sure to keep an eye out for this offer!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        tvCity.setText("");
                        tvOrganization.setText("");
                        tvDealType.setText("");

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
}
