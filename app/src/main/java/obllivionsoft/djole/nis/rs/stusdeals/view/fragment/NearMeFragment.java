package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Gps;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.NearMeResponse;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.DealDetailActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.PremiumOfferActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.NearMeAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearMeFragment extends Fragment {
    View rootView;
    Unbinder unbinder;
    @BindView(R.id.tv_subdeal_title)
    MyTextViewBold tvSubdealTitle;
    @BindView(R.id.rv_near_me)
    RecyclerView rvNearMe;
    Gps gps;
    double current_lat = 0.0, current_long = 0.0;
    List<NearMeResponse.Data> listNearDeals;
    NearMeAdapter nearMeAdapter;
    String mVip = "";
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_near_me, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        getLoc();
        getNearDeals();


        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                simpleSwipeRefreshLayout.setRefreshing(false);
                initView();
            }


        });
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>.get near by deals>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getNearDeals() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NearMeResponse> call = apiService.NearMeDeals("deals/?isActive=true&lat=" + current_lat + "&lon=" + current_long);
        call.enqueue(new Callback<NearMeResponse>() {

            @Override
            public void onResponse(Call<NearMeResponse> call, Response<NearMeResponse> response) {
                try {
                    if (response.body() != null) {
                        listNearDeals = response.body().data;

                        //>>>>>>>>>>>>>>shorting a list using distance?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                        Collections.sort(listNearDeals, new Comparator<NearMeResponse.Data>() {
                            @Override
                            public int compare(NearMeResponse.Data dataa, NearMeResponse.Data t1) {
                                return Double.compare(dataa.distance, t1.distance);
                            }
                        });

                        initRecyclerview();
                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<NearMeResponse> call, Throwable t) {
                Log.e("failure", t.getMessage());

            }
        });


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>set adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void initRecyclerview() {


        RecyclerView.LayoutManager mLayoutdeals = new LinearLayoutManager(getContext(), rvNearMe.VERTICAL, false);
        rvNearMe.setLayoutManager(mLayoutdeals);


        nearMeAdapter = new NearMeAdapter(getContext(), listNearDeals, new NearMeAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {
                mVip = AppPreferences.init(getContext()).getString(AppConstant.VIPUSER);
                String isVip = String.valueOf(listNearDeals.get(position).isVIP);
                if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {

                    String deal_id = listNearDeals.get(position)._id;
                    String distance = String.valueOf(listNearDeals.get(position).distance);
                    Intent dealdetails = new Intent(getContext(), DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance", distance);
                    startActivity(dealdetails);


                } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
                    Intent premiumOffer = new Intent(getContext(), PremiumOfferActivity.class);
                    startActivity(premiumOffer);
                } else if (isVip.equalsIgnoreCase("false")) {
                    String deal_id = listNearDeals.get(position)._id;
                    String distance = String.valueOf(listNearDeals.get(position).distance);
                    Intent dealdetails = new Intent(getContext(), DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance", distance);
                    startActivity(dealdetails);
                }


            }
        });
        rvNearMe.setAdapter(nearMeAdapter);


    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>get locations near by>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getLoc() {
        gps = new Gps(getContext());

        if (gps.canGetLocation()) {
            current_lat = gps.getLatitude();
            current_long = gps.getLongitude();
            Log.e("current_lat", current_lat + "");
            Log.e("current_long", current_long + "");


        } else {
            gps.showSettingsAlert();
        }
    }
}
