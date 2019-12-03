package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Gps;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.ProviderDealRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealsCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ProviderDealsModel;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.ProviderDealAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderProfileActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.iv_share)
    AppCompatImageView ivShare;
    @BindView(R.id.rv_provider_deal)
    RecyclerView rvProviderDeal;
    AppCompatActivity context;
    ProviderDealAdapter providerDealAdapter;
    String provider_id = "", mProvider_name = "", mProvider_image = "", mVip = "";
    List<ProviderDealsModel.Data> mProDeals;
    @BindView(R.id.tv_title)
    MyTextViewBold tvTitle;
    @BindView(R.id.tv_provider_title)
    MyTextViewBold tvProviderTitle;
    @BindView(R.id.img_provider)
    CircleImageView imgProvider;
    List<DealsCustomModel> listDeals;
    Gps gps;
    double current_lat = 0.0, current_long = 0.0;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_provider_profile);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        listDeals = new ArrayList<>();
        context = ProviderProfileActivity.this;
        Intent getId = getIntent();
        provider_id = getId.getStringExtra("provider_id");
        mProvider_name = getId.getStringExtra("provider_name");
        mProvider_image = getId.getStringExtra("provider_image");
        if (!mProvider_name.isEmpty()) {
            tvTitle.setText(mProvider_name);
            tvProviderTitle.setText("Offer from " + mProvider_name);

        }

        if (!mProvider_image.isEmpty()) {
            Glide.with(context)
                    .load(AppConstant.imgProvidersUrl + provider_id + "/o/" + mProvider_image)
                    .into(imgProvider);
        }
        Log.e("providerid", provider_id + "");
        getLoc();
        getProviderDeals();



        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                simpleSwipeRefreshLayout.setRefreshing(false);
                initView();
            }


        });


    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>get current location>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void getLoc() {
        gps = new Gps(context);

        if (gps.canGetLocation()) {
            current_lat = gps.getLatitude();
            current_long = gps.getLongitude();
            Log.e("current_lat", current_lat + "");
            Log.e("current_long", current_long + "");


        } else {
            gps.showSettingsAlert();
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>get provider deals>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getProviderDeals() {

        ProgressBar.getInstanse().showDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        ProviderDealRequest providerDealRequest = new ProviderDealRequest();
        providerDealRequest.setProviderId(provider_id);
        Call<ProviderDealsModel> call = apiService.getProviderDeals("application/json", providerDealRequest);
        call.enqueue(new Callback<ProviderDealsModel>() {
            @Override
            public void onResponse(Call<ProviderDealsModel> call, Response<ProviderDealsModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        mProDeals = response.body().data;
                        if (!mProDeals.isEmpty()) {
                            for (int i = 0; i < mProDeals.size(); i++) {
                                double lat = mProDeals.get(i).location.lat;
                                double lng = mProDeals.get(i).location.lng;
                                double distance = distance(lat, lng, current_lat, current_long);
                                Log.e("distance", distance + "");
                                DealsCustomModel dealsCustomModel = new DealsCustomModel();
                                dealsCustomModel.setId(mProDeals.get(i)._id);
                                dealsCustomModel.setDistance(distance);
                                dealsCustomModel.setTitle(mProDeals.get(i).title);
                                dealsCustomModel.setCoverImage(mProDeals.get(i).coverPhoto);
                                dealsCustomModel.setProviderImage(mProDeals.get(i).photo);
                                dealsCustomModel.setVip(mProDeals.get(i).isVIP);
                                listDeals.add(dealsCustomModel);
                            }
                        }

                        //shorting the list according to location>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                        Collections.sort(listDeals, new Comparator<DealsCustomModel>() {
                            @Override
                            public int compare(DealsCustomModel d1, DealsCustomModel d2) {
                                return Double.compare(d1.getDistance(), d2.getDistance());
                            }
                        });
                        CallProviderDealAdapter();

                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<ProviderDealsModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>call adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void CallProviderDealAdapter() {
        //>>>>>>>>>>>>>>>>>>>>>>>>>>ALL deal recycler view list>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        RecyclerView.LayoutManager mLayoutdeals = new LinearLayoutManager(context, rvProviderDeal.VERTICAL, false);
        rvProviderDeal.setLayoutManager(mLayoutdeals);
        rvProviderDeal.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        providerDealAdapter = new ProviderDealAdapter(context, listDeals, mProvider_name, new ProviderDealAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {

                mVip = AppPreferences.init(context).getString(AppConstant.VIPUSER);
                String isVip = String.valueOf(listDeals.get(position).isVip());
                if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {

                    String deal_id = listDeals.get(position).getId();
                    String distance = String.valueOf(listDeals.get(position).getDistance());
                    Intent dealdetails = new Intent(context, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance", distance);
                    context.startActivity(dealdetails);


                } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
                    Intent premiumOffer = new Intent(context, PremiumOfferActivity.class);
                    context.startActivity(premiumOffer);
                } else if (isVip.equalsIgnoreCase("false")) {
                    String deal_id = listDeals.get(position).getId();
                    String distance = String.valueOf(listDeals.get(position).getDistance());
                    Intent dealdetails = new Intent(context, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance", distance);
                    context.startActivity(dealdetails);
                }


            }
        });
        rvProviderDeal.setAdapter(providerDealAdapter);

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

    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_share:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
