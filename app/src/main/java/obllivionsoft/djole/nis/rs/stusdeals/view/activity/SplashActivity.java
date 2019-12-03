package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.PermissionCheckUtil;

public class SplashActivity extends AppCompatActivity {

    String mUserid="",mAccessToken="";
    private Thread mThread;
    AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intView();
    }

    private void intView() {
        mContext=SplashActivity.this;
        mUserid= AppPreferences.init(SplashActivity.this).getString(AppConstant.USER_ID);
        mAccessToken=AppPreferences.init(mContext).getString(AppConstant.ACCESS_TOKEN);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("token",newToken+"");
                AppPreferences.init(mContext).putString(AppConstant.DEVICE_TOKEN, newToken);
            }
        });
        PermissionCheckUtil.create(SplashActivity.this, new PermissionCheckUtil.onPermissionCheckCallback() {
            @Override
            public void onPermissionSuccess() {
                splash();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionCheckUtil.PERMISSION_REQUEST_CODE) {
            splash();
        }
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>launch splash>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void splash() {

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent intent;
                    if(!mUserid.isEmpty()&&!mAccessToken.isEmpty())
                    {
                        intent = new Intent(mContext, HomeActivity.class);
                    }
                    else
                    {
                        intent = new Intent(mContext, OnBoardingActivity.class);
                    }

                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }

}
