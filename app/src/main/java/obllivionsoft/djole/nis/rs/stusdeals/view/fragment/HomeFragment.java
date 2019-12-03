package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.CancelPremiumActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.DemandActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.NearMeActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.OnBoardingActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.SearchDealsActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.WebViewActivity;

public class HomeFragment extends Fragment implements DrawerLayout.DrawerListener {
    View rootView;
    Unbinder unbinder;
    @BindView(R.id.iv_nav)
    AppCompatImageView ivNav;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fl_home_page)
    FrameLayout flHomePage;
    @BindView(R.id.search)
    RelativeLayout search;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {

        getFragmentManager().beginTransaction().replace(R.id.fl_home_page, new AllDealFragment()).commit();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_logout:
                        openLogoutDialog();
                        break;
                    case R.id.nav_cancel:
                        Intent cancel = new Intent(getContext(), CancelPremiumActivity.class);
                        getActivity().startActivity(cancel);

                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_privacy:
                        Intent privacy = new Intent(getContext(), WebViewActivity.class);
                        privacy.putExtra("Name", "privacy");
                        getActivity().startActivity(privacy);
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_tersm:
                        Intent terms = new Intent(getContext(), WebViewActivity.class);
                        terms.putExtra("Name", "terms");
                        getActivity().startActivity(terms);
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_instagram:
                        launchInstagram();
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_twitter:
                        launchTwitter();
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_facebook:
                        launchFacebook();
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_mydeals:
                        Intent nearme = new Intent(getContext(), DemandActivity.class);
                        getActivity().startActivity(nearme);
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                }
                return false;
            }
        });
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>launch facebook>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void launchFacebook() {

        final String urlFb = "https://www.facebook.com/STUNiiAPP/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser
        final PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/STUNiiAPP/";
            intent.setData(Uri.parse(urlBrowser));
        }

        startActivity(intent);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>launch twitter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void launchTwitter() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name=STUNiiAPP"));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/#!/STUNiiAPP")));
        }

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>launch instgram>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void launchInstagram() {
        Uri uri = Uri.parse("http://instagram.com/_u/stuniichester");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/stuniiapp/")));
        }

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>logout>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void openLogoutDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure?You want to logout from the Stunii")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        AppPreferences.init(getActivity()).putString(AppConstant.USER_ID, "");
                        AppPreferences.init(getActivity()).putString(AppConstant.ACCESS_TOKEN, "");
                        AppPreferences.init(getActivity()).putString(AppConstant.USER_FIRST_NAME, "");
                        AppPreferences.init(getActivity()).putString(AppConstant.USER_LAST_NAME, "");
                        AppPreferences.init(getActivity()).putString(AppConstant.PHONE_NO, "");
                        AppPreferences.init(getActivity()).putString(AppConstant.USER_EMAIL, "");
                        Intent logout = new Intent(getContext(), OnBoardingActivity.class);
                        startActivity(logout);
                        getActivity().finishAffinity();
                    }


                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }


                })
                .setIcon(R.drawable.logosignup)
                .show();
    }


    @OnClick({R.id.iv_nav})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_nav:
                drawerLayout.openDrawer(Gravity.START);
                break;

        }
    }


    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        //  drawerLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {


    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @OnClick(R.id.search)
    public void onViewClicked() {

      Intent search=new Intent(getContext(), SearchDealsActivity.class);
      getActivity().startActivity(search);

    }
}
