package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.CarouselEffectTransformer;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.SlidingImage_Profile;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnBoardingActivity extends AppCompatActivity {

    @BindView(R.id.mViewpager)
    ViewPager mViewpager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.tv_join)
    MyTextViewBold tvJoin;
    @BindView(R.id.tv_login)
    MyTextViewBold tvLogin;
    Timer swipeTimer;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    AppCompatActivity mContext;
    private static final Integer[] IMAGES = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight};
    @BindView(R.id.videoView)
    VideoView videoView;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    public static final int PERMISSION_REQUEST_CODE = 123;
    String mUserid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContext = OnBoardingActivity.this;
        initViewpager();

    }



    @OnClick({R.id.tv_join, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_join:
                Intent signup=new Intent(mContext,SignuoneActivity.class);
                startActivity(signup);
                break;
            case R.id.tv_login:
                Intent login=new Intent(mContext,LoginActivity.class);
                startActivity(login);
                break;
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.view pager intilization>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void initViewpager() {
        swipeTimer = new Timer();
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);
        mViewpager.setAdapter(new SlidingImage_Profile(mContext, ImagesArray));
        indicator.setViewPager(mViewpager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = ImagesArray.size();
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

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.compressed);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

    }
}
