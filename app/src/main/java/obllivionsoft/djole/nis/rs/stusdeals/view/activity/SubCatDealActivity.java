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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
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
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.SubCatDealsRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealsCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SubCatDealsResponse;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.SubCategoryDealAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCatDealActivity extends AppCompatActivity {

    @BindView(R.id.rv_saved_deal)
    RecyclerView rvSavedDeal;
    SubCategoryDealAdapter subCategoryDealAdapter;
    AppCompatActivity mContext;
    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    String subCatId = "", subCatName = "", mVip = "";
    @BindView(R.id.tv_subdeal_title)
    MyTextViewBold tvSubdealTitle;
    List<SubCatDealsResponse.Data> mSubDealsList;
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
        setContentView(R.layout.activity_sub_cat_deal);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext = SubCatDealActivity.this;
        listDeals = new ArrayList<>();

        Intent getData = getIntent();
        subCatId = getData.getStringExtra("subCatId");
        subCatName = getData.getStringExtra("subName");
        if (subCatName != null) {
            tvSubdealTitle.setText(subCatName);
        }
        getLoc();
        getSubCategoryDeals();


        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                simpleSwipeRefreshLayout.setRefreshing(false);
                initView();
            }


        });

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.get location>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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

    private void getSubCategoryDeals() {
        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        SubCatDealsRequest subCatDealsRequest = new SubCatDealsRequest();
        subCatDealsRequest.setSubcategoryId(subCatId);
        Call<SubCatDealsResponse> call = apiService.getSubCategoryDeals("application/json", subCatDealsRequest);
        call.enqueue(new Callback<SubCatDealsResponse>() {
            @Override
            public void onResponse(Call<SubCatDealsResponse> call, Response<SubCatDealsResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        if (response.body().status == 200) {

                            mSubDealsList = response.body().data;
                            if (mSubDealsList.size() > 0) {
                                for (int i = 0; i < mSubDealsList.size(); i++) {
                                    double lat = mSubDealsList.get(i).location.lat;
                                    double lng = mSubDealsList.get(i).location.lng;
                                    double distance = distance(lat, lng, current_lat, current_long);
                                    Log.e("distance", distance + "");
                                    DealsCustomModel dealsCustomModel = new DealsCustomModel();
                                    dealsCustomModel.setId(mSubDealsList.get(i)._id);
                                    dealsCustomModel.setDistance(distance);
                                    dealsCustomModel.setProvider_name(mSubDealsList.get(i)._provider.name);
                                    dealsCustomModel.setTitle(mSubDealsList.get(i).title);
                                    dealsCustomModel.setCoverImage(mSubDealsList.get(i).coverPhoto);
                                    dealsCustomModel.setProviderImage(mSubDealsList.get(i).photo);
                                    dealsCustomModel.setVip(mSubDealsList.get(i).isVIP);
                                    listDeals.add(dealsCustomModel);
                                }

                                //shorting the list according to location>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                                Collections.sort(listDeals, new Comparator<DealsCustomModel>() {
                                    @Override
                                    public int compare(DealsCustomModel d1, DealsCustomModel d2) {
                                        return Double.compare(d1.getDistance(), d2.getDistance());
                                    }
                                });

                                CallSubDealsAdapter();

                            }

                        }


                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<SubCatDealsResponse> call, Throwable t) {
                Log.e("exception", t.getMessage());
                ProgressBar.getInstanse().hideDialog();
            }


        });


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subdealAdapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void CallSubDealsAdapter() {

        RecyclerView.LayoutManager mLayoutdeals = new LinearLayoutManager(mContext, rvSavedDeal.VERTICAL, false);
        rvSavedDeal.setLayoutManager(mLayoutdeals);

        subCategoryDealAdapter = new SubCategoryDealAdapter(mContext, listDeals, new SubCategoryDealAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {
                mVip = AppPreferences.init(mContext).getString(AppConstant.VIPUSER);
                String isVip = String.valueOf(listDeals.get(position).isVip());
                if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {

                    String deal_id = listDeals.get(position).getId();
                    String distance = String.valueOf(listDeals.get(position).getDistance());
                    Intent dealdetails = new Intent(mContext, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance", distance);
                    startActivity(dealdetails);


                } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
                    Intent premiumOffer = new Intent(mContext, PremiumOfferActivity.class);
                    startActivity(premiumOffer);
                } else if (isVip.equalsIgnoreCase("false")) {
                    String deal_id = listDeals.get(position).getId();
                    String distance = String.valueOf(listDeals.get(position).getDistance());
                    Intent dealdetails = new Intent(mContext, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance", distance);
                    startActivity(dealdetails);
                }


            }
        });
        rvSavedDeal.setAdapter(subCategoryDealAdapter);


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
