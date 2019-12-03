package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.DealCountRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealCountResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealDetaillsModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealsCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.RedeemApiResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.StuIDResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.DealViewPager_Adapter;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.NestedItemAdapter;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.SimilarDealAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealDetailActivity extends AppCompatActivity implements OnMapReadyCallback {


    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.ll_deal)
    LinearLayout llDeal;
    @BindView(R.id.mView)
    View mView;
    @BindView(R.id.tv_deal_desc)
    MyTextView tvDealDesc;
    @BindView(R.id.v_deal_desc)
    View vDealDesc;
    @BindView(R.id.ll_directions)
    LinearLayout llDirections;
    @BindView(R.id.ll_catch_cab)
    LinearLayout llCatchCab;
    @BindView(R.id.v_catch_cab)
    View vCatchCab;
    @BindView(R.id.rv_similar_deals)
    RecyclerView rvSimilarDeals;
    @BindView(R.id.ll_recycler_view)
    LinearLayout llRecyclerView;
    @BindView(R.id.tv_review)
    MyTextViewBold tvReview;
    AppCompatActivity context;
    NestedItemAdapter nestedItemAdapter;
    @BindView(R.id.mViewpager)
    ViewPager mViewpager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.img_provider)
    AppCompatImageView imgProvider;
    @BindView(R.id.tv_deal_title)
    MyTextViewBold tvDealTitle;
    @BindView(R.id.tv_provider_name)
    MyTextView tvProviderName;
    List<String> galleryList;
    Timer swipeTimer;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    @BindView(R.id.img_cover_image)
    AppCompatImageView imgCoverImage;
    int mCount = 0;
    String mSociety_email="",mTaxiMobile = "", mTaxiLink = "", mWeb = "", mDistance = "", mDeal_id = "", mAceesToken = "", mCoverImage = "", mProviderimage = "", mDealtitle = "", mDealDesc = "", mProvider_name = "", mStartADay = "", mEndDay = "", mScanForRedeem = "", mVip = "", mDealLimit = "", mDealtype = "", mLegal = "";
    List<DealDetaillsModel.Category> catDealsList;
    SimilarDealAdapter similarDealAdapter;
    @BindView(R.id.tv_deal_expired)
    MyTextViewBold tvDealExpired;
    @BindView(R.id.tv_count)
    MyTextView tvCount;
    @BindView(R.id.ll_selling_fast)
    LinearLayout llSellingFast;
    @BindView(R.id.ll_selling)
    LinearLayout llSelling;
    @BindView(R.id.mViewone)
    View mViewone;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    @BindView(R.id.tv_deal_legal)
    MyTextView tv_deal_legal;
    @BindView(R.id.tv_distance)
    MyTextView tvDistance;
    @BindView(R.id.tv_downloadapp)
    LinearLayout tvDownloadapp;
    @BindView(R.id.tv_start_day)
    MyTextView tvStartDay;

    private GoogleMap mMap;
    double mLat, mLong;
    double distance;
    List<DealsCustomModel> listDeals;
    Gps gps;
    double current_lat = 0.0, current_long = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_deal_detail);
        ButterKnife.bind(this);

    }

    private void initView() {
        context = DealDetailActivity.this;
        listDeals = new ArrayList<>();
        Intent getData = getIntent();
        mDeal_id = getData.getStringExtra("dealId");
        mDistance = getData.getStringExtra("distance");
        distance = Double.parseDouble(mDistance);
        if (!mDistance.isEmpty()) {
            tvDistance.setText(String.format("%.2f", distance) + " mi");
        }
        mAceesToken = AppPreferences.init(context).getString(AppConstant.ACCESS_TOKEN);
        getLoc();
        getDealDetails();


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
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>deals details>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void getDealDetails() {
        try
        {
            ProgressBar.getInstanse().showDialog(context);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<DealDetaillsModel> call = apiService.dealDetails("dealDetail/" + mDeal_id);
            call.enqueue(new Callback<DealDetaillsModel>() {
                @Override
                public void onResponse(Call<DealDetaillsModel> call, Response<DealDetaillsModel> response) {
                    try {
                        ProgressBar.getInstanse().hideDialog();
                        if (response.body() != null) {
                            galleryList = response.body().data.gallery;
                            mCoverImage = response.body().data.coverPhoto;
                            mProviderimage = response.body().data.photo;
                            mDealtitle = response.body().data.title;
                            mDealDesc = response.body().data.desc;
                            mProvider_name = response.body().data._provider.name;
                            mScanForRedeem = String.valueOf(response.body().data.scanForRedeem);
                            mLat = response.body().data.location.lat;
                            mLong = response.body().data.location.lng;
                            mLegal = response.body().data.legal;
                            mWeb = response.body().data.web;
                            mTaxiMobile = response.body().data._taxi.phone_number;
                            mTaxiLink = response.body().data._taxi.app_link;
                            mStartADay = response.body().data.startDay;
                            mSociety_email=response.body().data.society_email.trim();


                            mCount = response.body().data.limitByStudent;
                            Log.e("saca", mTaxiLink + "");
                            catDealsList = response.body().category;
                            mDealtype = response.body().data.redeemType;
                            Log.e("dealtype", mTaxiMobile + "");
                            if (mDealtype.equalsIgnoreCase("unlimited")) {
                                llSellingFast.setVisibility(View.GONE);
                                mViewone.setVisibility(View.GONE);

                            } else if (mDealtype.equalsIgnoreCase("limited") && mCount != 0) {
                                llSellingFast.setVisibility(View.VISIBLE);
                                mViewone.setVisibility(View.VISIBLE);
                                mDealLimit = String.valueOf(response.body().data.limitByStudent);
                                tvCount.setText(mDealLimit);
                            } else if (mDealtype.equalsIgnoreCase("limited") && mCount == 0) {
                                llSellingFast.setVisibility(View.GONE);
                                mViewone.setVisibility(View.VISIBLE);
                                tvDealExpired.setVisibility(View.VISIBLE);
                            }
                            Log.e("sizeoflist", catDealsList.size() + "");
                            setDataofDeal();
                            setUpMapIfNeeded();


                        }
                    } catch (Exception e) {
                        ProgressBar.getInstanse().hideDialog();
                        Log.e("exception",e.getMessage()+"");
                    }

                }

                @Override
                public void onFailure(Call<DealDetaillsModel> call, Throwable t) {
                    ProgressBar.getInstanse().hideDialog();
                    Log.e("exception",t.getMessage()+"");
                }


            });

        }
        catch (Exception e) {
            Log.e("exception",e.getMessage()+"");
        }

    }

    private void setUpMapIfNeeded() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>set similar deals adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void setSimilarDealsAdapter() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(context, rvSimilarDeals.HORIZONTAL, false);
        rvSimilarDeals.setLayoutManager(layoutManager);
        similarDealAdapter = new SimilarDealAdapter(context, listDeals, new SimilarDealAdapter.onItemClickListner() {
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
        rvSimilarDeals.setAdapter(similarDealAdapter);


    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>set fields for deals details
    private void setDataofDeal() {
        tvDealTitle.setText(mDealtitle);
        tvDealDesc.setText(mDealDesc);
        tv_deal_legal.setText(mLegal);
        tvProviderName.setText(mProvider_name);
        tvStartDay.setText(mStartADay);
        if (!mWeb.isEmpty()) {
            tvReview.setText("Open website");
        } else {

            if (mScanForRedeem.equalsIgnoreCase("true")) {
                tvReview.setText("Press for QR Reader");
            } else{
                if(!mSociety_email.isEmpty())
                {
                    tvReview.setText("JOIN SOCIETY");
                }
                else
                {
                    tvReview.setText("REDEEM");
                }

            }
        }


        if (!mProviderimage.isEmpty()) {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + mDeal_id + "/o/" + mProviderimage)
                    .into(imgProvider);
        }
        if (!galleryList.isEmpty()) {
            mViewpager.setVisibility(View.VISIBLE);
            imgCoverImage.setVisibility(View.GONE);
            initViewpager();
        } else {
            mViewpager.setVisibility(View.GONE);
            imgCoverImage.setVisibility(View.VISIBLE);
            Glide
                    .with(context)
                    .load(AppConstant.imgDealsUrl + mDeal_id + "/o/" + mCoverImage)
                    .into(imgCoverImage);

        }
        if (!catDealsList.isEmpty()) {
            for (int i = 0; i < catDealsList.size(); i++) {
                double lat = catDealsList.get(i).location.lat;
                double lng = catDealsList.get(i).location.lng;
                double distance = distance(lat, lng, current_lat, current_long);
                Log.e("distance", distance + "");
                DealsCustomModel dealsCustomModel = new DealsCustomModel();
                dealsCustomModel.setId(catDealsList.get(i)._id);
                dealsCustomModel.setDistance(distance);
                dealsCustomModel.setProvider_name(catDealsList.get(i)._provider.name);
                dealsCustomModel.setTitle(catDealsList.get(i).meta_title);
                dealsCustomModel.setCoverImage(catDealsList.get(i).coverPhoto);
                dealsCustomModel.setProviderImage(catDealsList.get(i).photo);
                dealsCustomModel.setVip(catDealsList.get(i).isVIP);
                listDeals.add(dealsCustomModel);
            }

            //>>>>>>>>>>>>>>shorting list according to distnace>>>>>>>>>>>>>>>>>>>>>>>>>
            Collections.sort(listDeals, new Comparator<DealsCustomModel>() {
                @Override
                public int compare(DealsCustomModel d1, DealsCustomModel d2) {
                    return Double.compare(d1.getDistance(), d2.getDistance());
                }
            });
            setSimilarDealsAdapter();
        }

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Deals Gallery>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void initViewpager() {
        swipeTimer = new Timer();
        mViewpager.setAdapter(new DealViewPager_Adapter(context, galleryList, mDeal_id));
        indicator.setViewPager(mViewpager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = galleryList.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {

            public void run() {


                try {
                    if (currentPage == NUM_PAGES) {
                        mViewpager.setCurrentItem(currentPage, false);
                        currentPage = 0;
                    }
                    mViewpager.setCurrentItem(currentPage++, true);
                } catch (Exception e) {
                    Log.e("exception", e + "");
                }

            }
        };
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
                currentPage = pos;

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }

    @OnClick({R.id.ll_back, R.id.ll_catch_cab, R.id.tv_review, R.id.tv_downloadapp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.tv_downloadapp:
                Intent privacy = new Intent(context, WebViewActivity.class);
                privacy.putExtra("Name", mTaxiLink);
                startActivity(privacy);
                break;
            case R.id.ll_catch_cab:
                phoneCall(mTaxiMobile);
                break;
            case R.id.tv_review:
                if(!mSociety_email.isEmpty())
                {
                    openMailBox();
                }
                else {


                    if (!mWeb.isEmpty()) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(mWeb));
                        startActivity(i);
                    } else {
                        if (mDealtype.equalsIgnoreCase("limited") && mCount == 0) {
                            openExpireDealDialog();
                        } else {
                            if (mScanForRedeem.equalsIgnoreCase("true")) {
                                Intent scanQr = new Intent(this, ScanQRActivity.class);
                                scanQr.putExtra("dealtitle", mDealtitle);
                                scanQr.putExtra("dealid", mDeal_id);
                                startActivity(scanQr);
                            } else {
                                if (mDealtype.equalsIgnoreCase("unlimited")) {
                                    showStuActivity();
                                } else {
                                    CheckDealLimit();

                                }

                            }
                        }
                    }
                }


                break;
        }
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>join society>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void openMailBox() {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", mSociety_email,null));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>phone call to customer>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void phoneCall(String mPhone) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    context,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Integer.parseInt("123"));
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mPhone, null));
            startActivity(intent);

        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>expire deal dilaog>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void openExpireDealDialog() {
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>image pic from camera and gallery>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        new AlertDialog.Builder(DealDetailActivity.this)
                .setTitle(getString(R.string.app_name))
                .setMessage("OOPS YOU HAVE JUST MISSED THIS ONE! KEEP A LOOKOUT FOR THE NEXT ONE")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }


                })
                .setIcon(R.drawable.logosignup)
                .show();
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>redeem deal and subtract one deal>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void CheckDealLimit() {
        ProgressBar.getInstanse().showDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        DealCountRequest dealCountRequest = new DealCountRequest();
        dealCountRequest.setDealId(mDeal_id);
        Call<DealCountResponse> call = apiService.flashDeal(dealCountRequest);
        call.enqueue(new Callback<DealCountResponse>() {
            @Override
            public void onResponse(Call<DealCountResponse> call, Response<DealCountResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        showStuActivity();
                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<DealCountResponse> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
                Log.e("exception", t.getMessage());

            }


        });


    }

    private void showStuActivity() {

        hitRedeemLogApi();

    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>hit simple redeem api to check the logs>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void hitRedeemLogApi() {
        ProgressBar.getInstanse().showDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RedeemApiResponse> call = apiService.logRedeem("payment/redeem/" + mDeal_id,"application/json","YW5kcm9pZF9hcHA6MzA1MEI3V1QwVmoz",mAceesToken);
        call.enqueue(new Callback<RedeemApiResponse>() {
            @Override
            public void onResponse(Call<RedeemApiResponse> call, Response<RedeemApiResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    Intent showStuId = new Intent(context, ShowStuIdActivity.class);
                    startActivity(showStuId);

                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<RedeemApiResponse> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LatLng sydney = new LatLng(mLat, mLong);
        mMap.addMarker(new MarkerOptions().position(sydney).title(mProvider_name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));

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
}
