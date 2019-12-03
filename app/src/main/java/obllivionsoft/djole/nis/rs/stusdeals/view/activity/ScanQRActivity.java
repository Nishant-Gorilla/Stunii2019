package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.QrClient;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.DealCountRequest;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealCountResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.QrDealResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.RedeemDealResponse;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.QrScannerFragment;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;

public class ScanQRActivity extends AppCompatActivity  {


    @BindView(R.id.textViewInstructions)
    TextView textViewInstructions;
    @BindView(R.id.scanningButton)
    Button scanningButton;
    @BindView(R.id.activity_scan_qr)
    RelativeLayout activityScanQr;
    private ZXingScannerView mScannerView;
    QrScannerFragment fragment;
    String title;
    private static final int REQUEST_CAMERA = 1;
    String dealid="",mStatus="",mMessage="";
    AppCompatActivity context;
    String mId = "", mEmail = "";
    String mNAme = "", mDealTitle= "",mLastname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scan_qr);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        context=ScanQRActivity.this;
        mId = AppPreferences.init(this).getString(AppConstant.USER_ID);
        mEmail = AppPreferences.init(this).getString(AppConstant.USER_EMAIL);
        mNAme = AppPreferences.init(this).getString(AppConstant.USER_FIRST_NAME);
        mLastname = AppPreferences.init(this).getString(AppConstant.USER_LAST_NAME);
        Intent getValue=getIntent();
        dealid=getValue.getStringExtra("dealid");
        mDealTitle=getValue.getStringExtra("dealtitle");

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                SetScanner();
            } else {
                requestPermission();
            }
        } else
            SetScanner();

    }

    private void SetScanner() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment = new QrScannerFragment();

        fragmentTransaction.add(R.id.scanner_fragment, fragment);
        fragmentTransaction.commit();

        if (fragment.mScannerView == null)
            fragment.mScannerView = new ZXingScannerView(this);

        fragment.mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {

                Log.v("qr", result.getText()); // Prints scan results
                Log.v("qr", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

                final ZXingScannerView.ResultHandler resultHandler = this;

                TryRedeem(result.getText(), new Runnable() {
                    @Override
                    public void run() {
                        fragment.mScannerView.resumeCameraPreview(resultHandler);
                    }
                });
                // If you would like to resume scanning, call this method below:
                //fragment.mScannerView.resumeCameraPreview(this);
            }
        });
        fragment.mScannerView.startCamera(); //pokrece kameru
    }




    private void TryRedeem(String _code, final Runnable continuationAction) {
        ProgressBar.getInstanse().showDialog(context);
        ApiInterface apiService = QrClient.getClientQr().create(ApiInterface.class);
        Call<QrDealResponse> call = apiService.getQrDeal("api/checkQr?" + "mUserid="+mId+"&qrcodeid="+_code);
        call.enqueue(new Callback<QrDealResponse>() {
            @Override
            public void onResponse(Call<QrDealResponse> call, Response<QrDealResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        mStatus=response.body().status;
                        mMessage=response.body().message;
                        if(mStatus.equalsIgnoreCase("1"))
                        {
                              showSuccessDialog();
                        }
                        else if(mStatus.equalsIgnoreCase("0"))
                        {
                              showFailurDailog();
                        }
                        else
                        {
                            Toast.makeText(ScanQRActivity.this, "Some thing Wrong", Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<QrDealResponse> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });


    }

    private void showFailurDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ScanQRActivity.this);
        builder.setMessage(mMessage)
                .setCancelable(false)
                .setPositiveButton("Scan again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));

        alert.show();
    }

    private void showSuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ScanQRActivity.this);

        builder.setMessage(mMessage)
                .setCancelable(false)

                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things90
                                hitRedeemptionApi();
                                finish();

                            }
                        });
        AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        alert.show();


    }



    private void hitRedeemptionApi() {

        ProgressBar.getInstanse().showDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RedeemDealResponse> call = apiService.hitRedeemdata("redeemed?" + "userid="+mId+"&fullname="+mNAme+mLastname+"&email="+mEmail+"&dealname="+mDealTitle  );
        call.enqueue(new Callback<RedeemDealResponse>() {
            @Override
            public void onResponse(Call<RedeemDealResponse> call, Response<RedeemDealResponse> response) {
                try {
                    if (response.body() != null) {
                        mStatus=response.body().status;
                        mMessage=response.body().message;
                        if(mStatus.equalsIgnoreCase("1"))
                        {

                            CheckDealLimit();
                        }
                        else if(mStatus.equalsIgnoreCase("0"))
                        {
                            Toast.makeText(ScanQRActivity.this, "Some thing Wrong", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ScanQRActivity.this, "Some thing Wrong", Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<RedeemDealResponse> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });



    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>redeem deal and subtract one deal>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void CheckDealLimit() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        DealCountRequest dealCountRequest = new DealCountRequest();
        dealCountRequest.setDealId(dealid);
        Call<DealCountResponse> call = apiService.flashDeal(dealCountRequest);
        call.enqueue(new Callback<DealCountResponse>() {
            @Override
            public void onResponse(Call<DealCountResponse> call, Response<DealCountResponse> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        finish();
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




    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>camera permission>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }










}
