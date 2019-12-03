package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.SubCategoryRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealsCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ProviderCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ProviderResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SubCategoryResponse;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.CategoryDealsAdapter;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.ProviderListAdapter;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.SubCategoryAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.iv_share)
    AppCompatImageView ivShare;
    @BindView(R.id.rv_logo_list)
    RecyclerView rvLogoList;
    @BindView(R.id.rv_sub_deal_text)
    RecyclerView rvSubDealText;
    @BindView(R.id.rv_sub_deal)
    RecyclerView rvSubDeal;
    AppCompatActivity context;
    ProviderListAdapter providerListAdapter;
    SubCategoryAdapter subCategoryAdapter;
    CategoryDealsAdapter categoryDealsAdapter;
    String category_id = "", mDealname = "", mVip = "";
    List<ProviderResponseModel.Data> providerList;
    List<SubCategoryResponse.SubCategory> subCategoryList;
    List<SubCategoryResponse.Deals> allDealsList;
    @BindView(R.id.tv_title)
    MyTextViewBold tvTitle;
    Gps gps;
    double current_lat = 0.0, current_long = 0.0;
    List<ProviderCustomModel> listProvider;
    List<DealsCustomModel> listDeals;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_out_out);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        listProvider = new ArrayList<>();
        listDeals = new ArrayList<>();
        context = SubCategoryActivity.this;
        Intent getId = getIntent();
        category_id = getId.getStringExtra("deal_id");
        mDealname = getId.getStringExtra("deal_name");
        tvTitle.setText(mDealname);
        Log.e("iddd", category_id + "");
        getLoc();
        getProviderList();
        getSubCategoryList();


        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                simpleSwipeRefreshLayout.setRefreshing(false);
                initView();
            }


        });

    }

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

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subCategoryListapi>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void getSubCategoryList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
        subCategoryRequest.setCategoryId(category_id);
        Call<SubCategoryResponse> call = apiService.getSubCategory("application/json", subCategoryRequest);
        call.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    Log.e("respne", response.body().toString() + "");
                    if (response.body() != null) {
                        subCategoryList = response.body().subCategory;
                        allDealsList = response.body().deals;
                        if (subCategoryList.size() > 0) {
                            CallSubCategoryAdaptor();
                        }
                        if (allDealsList.size() > 0) {

                            for (int i = 0; i < allDealsList.size(); i++) {
                                double lat = allDealsList.get(i).location.lat;
                                double lng = allDealsList.get(i).location.lng;
                                double distance = distance(lat, lng, current_lat, current_long);
                                Log.e("distance", distance + "");
                                DealsCustomModel dealsCustomModel = new DealsCustomModel();
                                dealsCustomModel.setId(allDealsList.get(i)._id);
                                dealsCustomModel.setDistance(distance);
                                dealsCustomModel.setProvider_name(allDealsList.get(i)._provider.name);
                                dealsCustomModel.setTitle(allDealsList.get(i).title);
                                dealsCustomModel.setCoverImage(allDealsList.get(i).coverPhoto);
                                dealsCustomModel.setProviderImage(allDealsList.get(i).photo);
                                dealsCustomModel.setVip(allDealsList.get(i).isVIP);
                                listDeals.add(dealsCustomModel);
                            }

                            //shorting the list according to location>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                            Collections.sort(listDeals, new Comparator<DealsCustomModel>() {
                                @Override
                                public int compare(DealsCustomModel d1, DealsCustomModel d2) {
                                    return Double.compare(d1.getDistance(), d2.getDistance());
                                }
                            });


                            callAllDealAdapter();
                        }


                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();

                }

            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                Log.e("exception", t.getMessage());
                ProgressBar.getInstanse().hideDialog();

            }


        });


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>call all category deals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void callAllDealAdapter() {


        RecyclerView.LayoutManager mLayoutdeals = new LinearLayoutManager(context, rvSubDeal.VERTICAL, false);
        rvSubDeal.setLayoutManager(mLayoutdeals);
        rvSubDeal.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        categoryDealsAdapter = new CategoryDealsAdapter(context, listDeals, new CategoryDealsAdapter.onItemClickListner() {
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
                    startActivity(dealdetails);


                } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
                    Intent premiumOffer = new Intent(context, PremiumOfferActivity.class);
                    startActivity(premiumOffer);
                } else if (isVip.equalsIgnoreCase("false")) {
                    String deal_id = listDeals.get(position).getId();
                    String distance = String.valueOf(listDeals.get(position).getDistance());
                    Intent dealdetails = new Intent(context, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance", distance);
                    startActivity(dealdetails);
                }

            }
        });
        rvSubDeal.setAdapter(categoryDealsAdapter);


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sub category adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void CallSubCategoryAdaptor() {

        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(context, rvSubDealText.HORIZONTAL, false);
        rvSubDealText.setLayoutManager(mLayout);
        rvSubDealText.setItemAnimator(new DefaultItemAnimator());
        subCategoryAdapter = new SubCategoryAdapter(context, subCategoryList, new SubCategoryAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {
                String subCatId = subCategoryList.get(position)._id;
                String subName = subCategoryList.get(position).subCategoryName;
                Intent dealDeatils = new Intent(context, SubCatDealActivity.class);
                dealDeatils.putExtra("subCatId", subCatId);
                dealDeatils.putExtra("subName", subName);
                startActivity(dealDeatils);


            }
        });
        rvSubDealText.setAdapter(subCategoryAdapter);


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>provider list apis>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getProviderList() {

        ProgressBar.getInstanse().showDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProviderResponseModel> call = apiService.getProvider();
        call.enqueue(new Callback<ProviderResponseModel>() {
            @Override
            public void onResponse(Call<ProviderResponseModel> call, Response<ProviderResponseModel> response) {
                try {
                    if (response.body() != null) {
                        providerList = response.body().data;
                        for (int i = 0; i < providerList.size(); i++) {
                            double lat = providerList.get(i).location.lat;
                            double lng = providerList.get(i).location.lng;
                            double distance = distance(lat, lng, current_lat, current_long);
                            Log.e("distance", distance + "");
                            ProviderCustomModel providerCustomModel = new ProviderCustomModel();
                            providerCustomModel.setId(providerList.get(i)._id);
                            providerCustomModel.setPhoto(providerList.get(i).photo);
                            providerCustomModel.setProvider_name(providerList.get(i).name);
                            providerCustomModel.setDistance(distance);
                            listProvider.add(providerCustomModel);

                        }
                        //shorting the list according to location>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                        Collections.sort(listProvider, new Comparator<ProviderCustomModel>() {
                            @Override
                            public int compare(ProviderCustomModel d1, ProviderCustomModel d2) {
                                return Double.compare(d1.getDistance(), d2.getDistance());
                            }
                        });
                        CallProviderAdapter();

                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<ProviderResponseModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });


    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>call provider adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void CallProviderAdapter() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, rvLogoList.HORIZONTAL, false);
        rvLogoList.setLayoutManager(mLayoutManager);
        rvLogoList.setItemAnimator(new DefaultItemAnimator());
        providerListAdapter = new ProviderListAdapter(context, listProvider, new ProviderListAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {
                String id = listProvider.get(position).getId();
                String provider_name = listProvider.get(position).getProvider_name();
                String provider_image = listProvider.get(position).getPhoto();
                Intent providerPro = new Intent(context, ProviderProfileActivity.class);
                providerPro.putExtra("provider_id", id);
                providerPro.putExtra("provider_name", provider_name);
                providerPro.putExtra("provider_image", provider_image);
                startActivity(providerPro);


            }
        });
        rvLogoList.setAdapter(providerListAdapter);


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
