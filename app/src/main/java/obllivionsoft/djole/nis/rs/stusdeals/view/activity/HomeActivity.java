package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.QrClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.NonSwipeableViewPager;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.VipResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.HomeVPAdapter;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.CategoryFragment;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.DemandFragment;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.HomeFragment;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.JobsFragment;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.NearMeFragment;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.StuidFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

   AppCompatActivity mContext;
    @BindView(R.id.fl_home)
    FrameLayout flHome;
    @BindView(R.id.fl_jobs)
    FrameLayout flJobs;
    @BindView(R.id.fl_categories)
    FrameLayout flCategories;
    @BindView(R.id.fl_stu_id)
    FrameLayout flStuId;
    @BindView(R.id.fl_demand)
    FrameLayout flDemand;
    @BindView(R.id.vp_main)
    NonSwipeableViewPager vpMain;
    @BindView(R.id.home_frame_container)
    FrameLayout homeFrameContainer;
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.cl_main_root)
    CoordinatorLayout clMainRoot;
    private HomeVPAdapter adapterViewPager;
    String mUserid="",mPremium="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initViews();
    }


//    public void setCurrentVp(int pos){
//        if(vpMain!=null){
//
//            vpMain.setCurrentItem(pos);
//            bottomNavigationView.setSelectedItemId(R.id.navigation_cart);
//
//        }
//    }

    private void initViews() {
        mContext=HomeActivity.this;
        mUserid  = AppPreferences.init(this).getString(AppConstant.USER_ID);;
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            View activeLabel = item.findViewById(R.id.largeLabel);
            if (activeLabel instanceof TextView) {
                activeLabel.setPadding(0, 0, 0, 0);
            }
        }

        List<FrameLayout> flList = new ArrayList<>();
        flList.add(flHome);
        flList.add(flJobs);
        flList.add(flCategories);
        flList.add(flStuId);
        flList.add(flDemand);


        List<Fragment> fragList = new ArrayList<Fragment>();
        fragList.add(new HomeFragment());
        fragList.add(new NearMeFragment());
        fragList.add(new CategoryFragment());
        fragList.add(new StuidFragment());
        fragList.add(new JobsFragment());


        adapterViewPager = new HomeVPAdapter(getSupportFragmentManager(), flList, fragList);
        vpMain.setOffscreenPageLimit(5);
        vpMain.setAdapter(adapterViewPager);
        //  BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CheckVip();
        //  bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);



//        Intent get=getIntent();
//        String coming=get.getStringExtra("coming_from");
//        Log.e("coming_from",coming+"");
//        if(coming!=null) {
//            if (coming.equalsIgnoreCase("Accepted")) {
//                vpMain.setCurrentItem(1);
//                bottomNavigationView.setSelectedItemId(R.id.navigation_orders);
//                // getSupportFragmentManager().beginTransaction().replace(R.id.fl_order_container, new UpcomingOrderFragments()).commit();
//
//            }
//            else if(coming.equalsIgnoreCase("Dispatched"))
//            {
//                vpMain.setCurrentItem(1);
//                bottomNavigationView.setSelectedItemId(R.id.navigation_orders);
//
//                //   getSupportFragmentManager().beginTransaction().replace(R.id.fl_order_container, new UpcomingOrderFragments()).commit();
//            }
//            else if(coming.equalsIgnoreCase("Rejected"))
//            {
//                vpMain.setCurrentItem(1);
//                bottomNavigationView.setSelectedItemId(R.id.navigation_orders);
//
//                //   getSupportFragmentManager().beginTransaction().replace(R.id.fl_order_container, new UpcomingOrderFragments()).commit();
//            }
//        }





//>>>>>>>>>>>>>>>>>>>>>for notification badge>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//        BottomNavigationMenuView bottomNavigationMenuView =
//                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
//        View v = bottomNavigationMenuView.getChildAt(3);
//        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
//
//        View badge = LayoutInflater.from(this)
//                .inflate(R.layout.notification_badge, itemView, true);
//        TextView textView=badge.findViewById(R.id.notifications);
//        textView.setText("3");

    }

    private void CheckVip() {
        ApiInterface apiService = QrClient.getClientQr().create(ApiInterface.class);
        Call<VipResponseModel> call = apiService.checkVip("api/isVip?" + "userid="+mUserid);
        call.enqueue(new Callback<VipResponseModel>() {
            @Override
            public void onResponse(Call<VipResponseModel> call, Response<VipResponseModel> response) {
                try {
                    if(response.body().message.equalsIgnoreCase("success"))
                    {
                        mPremium=response.body().isPremium;
                        if(mPremium.equalsIgnoreCase("No"))
                        {
                            AppPreferences.init(mContext).putString(AppConstant.VIPUSER, "0");
                        }
                        else
                        {
                            AppPreferences.init(mContext).putString(AppConstant.VIPUSER, "1");
                        }
                    }


                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<VipResponseModel> call, Throwable t) {

            }


        });
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Menu menuNav = bottomNavigationView.getMenu();
            MenuItem menuItem = menuNav.findItem(R.id.navigation_home);

            // Disable a tint color
//            menuItem.icon(false);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vpMain.setCurrentItem(0);
                    return true;
                case R.id.navigation_jobs:
                    vpMain.setCurrentItem(1);
                    return true;
                case R.id.navigation_cat:
                    vpMain.setCurrentItem(2);
                    return true;
                case R.id.navigation_stuid:
                    vpMain.setCurrentItem(3);
                    return true;
                case R.id.navigation_demand:
                    vpMain.setCurrentItem(4);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onDestroy() {
//        AppPreferences.init(this).putString(AppConstant.OWNER_ID,"");
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//
//        editor.putString("countlist","");
//        editor.putString("totalpricejson","");
//        editor.putString("item_menu_namelist","");
//        editor.putString("countlistmenuid_json","");
//        editor.commit();
//        finishAffinity(); // Close all activites
//        System.exit(0);
//        EventBus.getDefault().post(new MessageEvent("hello"));

        super.onDestroy();


    }


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
                hideSoftKeyboard(HomeActivity.this);
            }
        }
        return ret;

    }
}
