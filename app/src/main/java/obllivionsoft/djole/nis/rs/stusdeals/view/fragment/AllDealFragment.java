package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Gps;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.HomeDealsResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.OtherDealsModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.TestingDealsModels;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.DealsPage_Adapter;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.HomeDealAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDealFragment extends Fragment {
    View rootView;
    Unbinder unbinder;
    @BindView(R.id.mViewpager)
    ViewPager mViewpager;
    @BindView(R.id.rv_deals)
    RecyclerView rvDeals;
    Timer swipeTimer;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight};
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    List<HomeDealsResponse.Data> mList;
    String dealid = "", title = "", meta_title = "", cover_photo = "", photo = "", provider_name = "";
    List<TestingDealsModels.Data> mFeautredList;
    List<OtherDealsModel> mOtherDeallist;
    List<TestingDealsModels.DataFeatured> mDealsList;
    List<TestingDealsModels.DealsList> mHomeList;
    Gps gps;
    double current_lat = 0.0, current_long = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_alldeal, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>view pager intilization>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void initView() {
        mFeautredList = new ArrayList<>();
        mOtherDeallist = new ArrayList<>();

        getLoc();
        allDeals();


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
        gps = new Gps(getActivity());

        if (gps.canGetLocation()) {
            current_lat = gps.getLatitude();
            current_long = gps.getLongitude();
            Log.e("current_lat", current_lat + "");
            Log.e("current_long", current_long + "");


        } else {
            gps.showSettingsAlert();
        }
    }

    public void allDeals() {
        ProgressBar.getInstanse().showDialog(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TestingDealsModels> call = apiService.allDeals("home/featured/?isActive=true&type=1&lat=" + current_lat + "&lon=" + current_long);
        call.enqueue(new Callback<TestingDealsModels>() {

            @Override
            public void onResponse(Call<TestingDealsModels> call, Response<TestingDealsModels> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        mHomeList = response.body().dealsList;

                        mFeautredList = response.body().dataFeatured.get(0).data;
                        initViewpager();
                        initRecyclerview();
                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<TestingDealsModels> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
                Log.e("failure", t.getMessage());

            }
        });
    }

//    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>get all deals >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//    private void allDeals() {
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<TestingDealsModels> call = apiService.allDeals("home/hero/?isActive=true&type=1&lat=5353&lon=2636&page=1");
//        call.enqueue(new Callback<TestingDealsModels>() {
//            @Override
//            public void onResponse(Call<TestingDealsModels> call, Response<TestingDealsModels> response) {
//                try {
//                    if (response.body() != null) {
//                        JSONObject jsono = new JSONObject(new Gson().toJson(response.body()));
//                        JSONArray array = jsono.getJSONArray("data");
//                        Log.e("length",array.length()+"");
//                        for (int i = 0; i < array.length(); i++) {
//                            if (i == 0) {
//                                JSONObject obj = array.getJSONObject(0);
//                                String id=obj.getString("_id");
//                                String name = obj.getString("name");
//                                JSONArray data=obj.getJSONArray("data");
//
//                                for(int j=0;j<data.length();j++)
//                                {
//                                    JSONObject dataDeal = data.getJSONObject(j);
//                                    dealid=dataDeal.getString("_id");
//                                    title=dataDeal.getString("title");
//                                    meta_title=dataDeal.getString("meta_title");
//                                    cover_photo=dataDeal.getString("coverPhoto");
//                                    photo=dataDeal.getString("photo");
//                                    JSONObject proObj=dataDeal.getJSONObject("_provider");
//                                    provider_name=proObj.getString("name");
//                                    FeautredDealsModel feautredDealsModel=new FeautredDealsModel();
//                                    feautredDealsModel.setName(name);
//                                    feautredDealsModel.setCover_photo(cover_photo);
//                                    feautredDealsModel.setPhoto(photo);
//                                    feautredDealsModel.setMeta_title(meta_title);
//                                    feautredDealsModel.setProvider_name(provider_name);
//                                    feautredDealsModel.setDealid(dealid);
//                                    feautredDealsModel.setTitle(title);
//                                    mFeautredList.add(feautredDealsModel);
//
//
//                                }
//                                initViewpager();
//
//                            } else {
//                                mDealsList=response.body().dealsList;
//                                Log.e("mDealslits",mDealsList.size()+"");
//                                mHomeList=response.body().dealsList.get(i).data;


//                                JSONObject obj = array.getJSONObject(i);
//                                String name = obj.getString("name");
//
//                                Log.e("categoryname",name+"");
//                                JSONArray data=obj.getJSONArray("data");
//                                String length= String.valueOf(data.length());
//                                CategoryNameModel categoryNameModel=new CategoryNameModel();
//                                categoryNameModel.setName(name);
//                                categoryNameModel.setSize(length);
//                                mNamelist.add(categoryNameModel);
//
//
//
//                                for(int j=0;j<data.length();j++)
//                                {
//                                    JSONObject dataDeal = data.getJSONObject(j);
//                                    dealid=dataDeal.getString("_id");
//                                    title=dataDeal.getString("title");
//                                    meta_title=dataDeal.getString("meta_title");
//                                    cover_photo=dataDeal.getString("coverPhoto");
//                                    photo=dataDeal.getString("photo");
//                                   OtherDealsModel otherDealsModel=new OtherDealsModel();
//                                    otherDealsModel.setName(name);
//                                    otherDealsModel.setCover_photo(cover_photo);
//                                    otherDealsModel.setPhoto(photo);
//                                    otherDealsModel.setMeta_title(meta_title);
//                                    otherDealsModel.setProvider_name(provider_name);
//                                    otherDealsModel.setDealid(dealid);
//                                    otherDealsModel.setTitle(title);
//                                    mOtherDeallist.add(otherDealsModel);
////                                    JSONObject proObj=dataDeal.getJSONObject("_provider");
////                                    provider_name=proObj.getString("name");
//
//                                }

//                            }
//                        }
//                        initRecyclerview();
//
//
//
//                    }
//                } catch (Exception e) {
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TestingDealsModels> call, Throwable t) {
//
//            }
//
//
//        });
//
//
//    }

    private void initRecyclerview() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), rvDeals.VERTICAL, false);
        rvDeals.setLayoutManager(mLayoutManager);
        rvDeals.setItemAnimator(new DefaultItemAnimator());
        HomeDealAdapter homeDealAdapter = new HomeDealAdapter(getActivity(), mHomeList, new HomeDealAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {


            }
        });
        rvDeals.setAdapter(homeDealAdapter);

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>view pager>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void initViewpager() {
        swipeTimer = new Timer();
        mViewpager.setAdapter(new DealsPage_Adapter(getContext(), mFeautredList));
        NUM_PAGES = mFeautredList.size();
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


    }
}

