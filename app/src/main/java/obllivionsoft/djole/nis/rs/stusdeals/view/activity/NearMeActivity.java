package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Gps;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.NearMeResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.TestingDealsModels;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.NearMeAdapter;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.SubCategoryDealAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearMeActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.tv_subdeal_title)
    MyTextViewBold tvSubdealTitle;
    @BindView(R.id.rv_near_me)
    RecyclerView rvNearMe;
    AppCompatActivity mContext;
    Gps gps;
    double current_lat=0.0, current_long=0.0;
    List<NearMeResponse.Data> listNearDeals;
    NearMeAdapter nearMeAdapter;
    String mVip="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_near_me);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {
        mContext=NearMeActivity.this;
        getLoc();
        getNearDeals();

    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>.get near by deals>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getNearDeals() {
        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NearMeResponse> call = apiService.NearMeDeals("deals/?isActive=true&lat="+current_lat+"&lon="+current_long);
        call.enqueue(new Callback<NearMeResponse>() {

            @Override
            public void onResponse(Call<NearMeResponse> call, Response<NearMeResponse> response) {
                try
                {
                    ProgressBar.getInstanse().hideDialog();
                    if(response.body()!=null)
                    {
                        listNearDeals=response.body().data;

                        //>>>>>>>>>>>>>>shorting a list using distance?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                        Collections.sort(listNearDeals, new Comparator<NearMeResponse.Data>() {
                            @Override
                            public int compare(NearMeResponse.Data dataa, NearMeResponse.Data t1) {
                                return Double.compare(dataa.distance, t1.distance);
                            }
                        });

                        initRecyclerview();
                    }
                }
                catch (Exception e)
                {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<NearMeResponse> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
                Log.e("failure",t.getMessage());

            }
        });


    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>set adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void initRecyclerview() {


        RecyclerView.LayoutManager mLayoutdeals = new LinearLayoutManager(mContext, rvNearMe.VERTICAL, false);
        rvNearMe.setLayoutManager(mLayoutdeals);

        nearMeAdapter = new NearMeAdapter(mContext,listNearDeals, new NearMeAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {
                mVip = AppPreferences.init(mContext).getString(AppConstant.VIPUSER);
                String isVip = String.valueOf(listNearDeals.get(position).isVIP);
                if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {

                    String deal_id = listNearDeals.get(position)._id;
                    String distance = String.valueOf(listNearDeals.get(position).distance);
                    Intent dealdetails = new Intent(mContext, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance",distance);
                    startActivity(dealdetails);


                } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
                    Intent premiumOffer = new Intent(mContext, PremiumOfferActivity.class);
                    startActivity(premiumOffer);
                } else if (isVip.equalsIgnoreCase("false")) {
                    String deal_id = listNearDeals.get(position)._id;
                    String distance = String.valueOf(listNearDeals.get(position).distance);
                    Intent dealdetails = new Intent(mContext, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance",distance);
                    startActivity(dealdetails);
                }


            }
        });
        rvNearMe.setAdapter(nearMeAdapter);


    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>get locations near by>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getLoc() {
        gps = new Gps(mContext);

        if (gps.canGetLocation()) {
            current_lat = gps.getLatitude();
            current_long = gps.getLongitude();
            Log.e("current_lat", current_lat + "");
            Log.e("current_long", current_long + "");


        } else {
            gps.showSettingsAlert();
        }
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
