package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Gps;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditText;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SearchDataModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SearchResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.TestingDealsModels;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.SearchListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDealsActivity extends AppCompatActivity {

    @BindView(R.id.img_cross)
    AppCompatImageView imgCross;
    @BindView(R.id.et_search)
    MyEditText etSearch;
    @BindView(R.id.tv_search)
    MyTextViewBold tvSearch;
    @BindView(R.id.rv_deals_list)
    RecyclerView rvDealsList;
    String mSearch = "", mVip = "";
    AppCompatActivity mContext;
    Gps gps;
    double current_lat = 0, current_long = 0;
    List<SearchResponseModel.Data> searchList;
    List<SearchResponseModel.DataList> mNestedList;
    List<SearchResponseModel._provider> mProvider_list;
    List<SearchDataModel> mList;
    SearchListAdapter searchListAdapter;
    @BindView(R.id.tv_hide)
    MyTextViewBold tvHide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_deals);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext = SearchDealsActivity.this;
        mList = new ArrayList<>();
    }

    @OnClick({R.id.img_cross, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_cross:
                onBackPressed();
                break;
            case R.id.tv_search:
                mSearch = etSearch.getText().toString();
                getLoc();
                if (!mSearch.isEmpty()) {
                    hitSearchapi();
                }
                break;
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>api>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void hitSearchapi() {
        ProgressBar.getInstanse().showDialog(mContext);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SearchResponseModel> call = apiService.getSerach("home/hero?isActive=true&lat=" + current_lat + "&lon=" + current_long + "&search=" + mSearch);
        call.enqueue(new Callback<SearchResponseModel>() {

            @Override
            public void onResponse(Call<SearchResponseModel> call, Response<SearchResponseModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        mList.clear();
                        searchList = response.body().data;
                        if (!searchList.isEmpty()) {
                            tvHide.setVisibility(View.GONE);
                            rvDealsList.setVisibility(View.VISIBLE);
                            for (int i = 0; i < searchList.size(); i++) {
                                mNestedList = searchList.get(i).data;

                                for (int j = 0; j < mNestedList.size(); j++) {
                                    SearchDataModel searchDataModel = new SearchDataModel();
                                    searchDataModel.setTitle(mNestedList.get(j).title);
                                    searchDataModel.setCover_photo(mNestedList.get(j).coverPhoto);
                                    searchDataModel.setDealId(mNestedList.get(j)._id);
                                    searchDataModel.setPhoto(mNestedList.get(j).photo);
                                    searchDataModel.setProviderName(mNestedList.get(j)._provider.get(0).name);
                                    searchDataModel.setIsVip(String.valueOf(mNestedList.get(j).isVIP));
                                    searchDataModel.setDistance(mNestedList.get(j).distance);
                                    mList.add(searchDataModel);

                                }
                            }
                            Collections.sort(mList, new Comparator<SearchDataModel>() {
                                @Override
                                public int compare(SearchDataModel d1, SearchDataModel d2) {
                                    return Double.compare(d1.getDistance(), d2.getDistance());
                                }
                            });

                            setSearchAdapter();

                        }
                        else
                        {
                            tvHide.setVisibility(View.VISIBLE);
                            rvDealsList.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<SearchResponseModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
                Log.e("failure", t.getMessage());

            }
        });

    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>set adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void setSearchAdapter() {

        RecyclerView.LayoutManager mLayoutdeals = new LinearLayoutManager(mContext, rvDealsList.VERTICAL, false);
        rvDealsList.setLayoutManager(mLayoutdeals);
        rvDealsList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        searchListAdapter = new SearchListAdapter(mContext, mList, new SearchListAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {
                mVip = AppPreferences.init(mContext).getString(AppConstant.VIPUSER);
                String isVip = String.valueOf(mList.get(position).getIsVip());
                if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {

                    String deal_id = mList.get(position).getDealId();
                    String distance = String.valueOf(mList.get(position).getDistance());
                    Intent dealdetails = new Intent(mContext, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance",distance);
                    startActivity(dealdetails);


                } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
                    Intent premiumOffer = new Intent(mContext, PremiumOfferActivity.class);
                    startActivity(premiumOffer);
                } else if (isVip.equalsIgnoreCase("false")) {
                    String deal_id = mList.get(position).getDealId();
                    String distance = String.valueOf(mList.get(position).getDistance());
                    Intent dealdetails = new Intent(mContext, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance",distance);
                    startActivity(dealdetails);
                }

            }
        });
        rvDealsList.setAdapter(searchListAdapter);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>keyboard hiding>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(ev);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + w.getLeft() - scrcoords[0];
            float y = ev.getRawY() + w.getTop() - scrcoords[1];

            if (ev.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                hideSoftKeyboard(mContext);
            }
        }
        return ret;

    }
}
