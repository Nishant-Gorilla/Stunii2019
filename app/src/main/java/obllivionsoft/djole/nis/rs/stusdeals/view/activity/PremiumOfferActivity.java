package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Gps;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealsCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.VipDealsResponse;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.VipDealAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PremiumOfferActivity extends AppCompatActivity {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.rv_vip_offers)
    RecyclerView rvVipOffers;
    @BindView(R.id.ll_upgrade)
    LinearLayout llUpgrade;
    AppCompatActivity mContext;
    List<VipDealsResponse.Premium> mList;
    VipDealAdapter vipDealAdapter;
    @BindView(R.id.tv_price)
    MyTextViewBold tvPrice;
    String mPrice="";
    List<DealsCustomModel> listDeals;
    Gps gps;
    double current_lat=0.0, current_long=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_premium_offer);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext = PremiumOfferActivity.this;
        listDeals=new ArrayList<>();
        getLoc();
        getPremiumDeals();


    }

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

    private void getPremiumDeals() {
        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<VipDealsResponse> call = apiService.getVipDeals();
        call.enqueue(new Callback<VipDealsResponse>() {
            @Override
            public void onResponse(Call<VipDealsResponse> call, Response<VipDealsResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        if (response.body().status == 200) {
                            mList = response.body().premium;
                            if(!mList.isEmpty())
                            {
                                for(int i=0;i<mList.size();i++)
                                {
                                    double lat=mList.get(i).location.lat;
                                    double lng=mList.get(i).location.lng;
                                    double distance=distance(lat,lng,current_lat,current_long);
                                    Log.e("distance",distance+"");
                                    DealsCustomModel dealsCustomModel=new DealsCustomModel();
                                    dealsCustomModel.setId(mList.get(i)._id);
                                    dealsCustomModel.setDistance(distance);
                                    dealsCustomModel.setProvider_name(mList.get(i)._provider.name);
                                    dealsCustomModel.setTitle(mList.get(i).meta_title);
                                    dealsCustomModel.setCoverImage(mList.get(i).coverPhoto);
                                    dealsCustomModel.setProviderImage(mList.get(i).photo);
                                    dealsCustomModel.setVip(mList.get(i).isVIP);
                                    listDeals.add(dealsCustomModel);
                                }
                            }

                            //>>>>>>>>>>>>>>shorting list according to distnace>>>>>>>>>>>>>>>>>>>>>>>>>
                            Collections.sort(listDeals, new Comparator<DealsCustomModel>() {
                                @Override
                                public int compare(DealsCustomModel d1, DealsCustomModel d2) {
                                    return Double.compare(d1.getDistance(), d2.getDistance());
                                }
                            });
                            mPrice=response.body().premiumPrice;

                            setVipAdapter();
                            if(!mPrice.isEmpty())
                            {
                                tvPrice.setText(mPrice);
                            }
                        }

                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<VipDealsResponse> call, Throwable t) {
                Log.e("failure", t.getMessage());
                ProgressBar.getInstanse().hideDialog();

            }


        });

    }



    private void setVipAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, rvVipOffers.HORIZONTAL, false);
        rvVipOffers.setLayoutManager(layoutManager);
        vipDealAdapter = new VipDealAdapter(mContext, listDeals, new VipDealAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {

                Toast.makeText(mContext, "Upgarde to premium to get the access to these deals", Toast.LENGTH_SHORT).show();
            }
        });
        rvVipOffers.setAdapter(vipDealAdapter);
    }

    @OnClick({R.id.ll_back, R.id.ll_upgrade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_upgrade:
                Intent stripe = new Intent(this, StripeActivity.class);
                stripe.putExtra("price",mPrice);
                startActivity(stripe);
                break;
        }
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>distance find between two lat long>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
