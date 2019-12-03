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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.customMonthYearPicker.YearMonthPickerDialog;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.QrClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditText;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.StripePaymentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StripeActivity extends AppCompatActivity {

    @BindView(R.id.image_cross)
    AppCompatImageView imageCross;
    @BindView(R.id.ll_cross)
    RelativeLayout llCross;
    @BindView(R.id.ll_stripe_image)
    RelativeLayout llStripeImage;
    @BindView(R.id.et_card_number)
    MyEditText etCardNumber;
    @BindView(R.id.et_expiration)
    MyTextView etExpiration;
    @BindView(R.id.et_cvv)
    MyEditText etCvv;
    @BindView(R.id.ll_card_details)
    LinearLayout llCardDetails;
    @BindView(R.id.ll_package)
    LinearLayout llPackage;
    @BindView(R.id.ll_Confirm_payment)
    LinearLayout llConfirmPayment;
    @BindView(R.id.ll_upgrade)
    LinearLayout llUpgrade;
    String month_value = "", year_value = "";
    @BindView(R.id.tv_price)
    MyTextViewBold tvPrice;
    private String stripetoken = "";
    private Card card;
    String mId = "", mEmail = "";
    String mNAme = "", mobileNumber = "", mLastname = "", mPrice = "";
    AppCompatActivity mContext;
    Stripe stripe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stripe);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        Intent getData = getIntent();
        mPrice = getData.getStringExtra("price");

        tvPrice.setText("ANNUAL DEAL PACKAGE £ "+ mPrice+ " YEARLY");


        mContext = StripeActivity.this;
        stripe = new Stripe(mContext);
        mId = AppPreferences.init(this).getString(AppConstant.USER_ID);
       // mEmail = AppPreferences.init(this).getString(AppConstant.USER_EMAIL);
        mEmail = AppPreferences.init(this).getString(AppConstant.USER_EMAIL);
        mNAme = AppPreferences.init(this).getString(AppConstant.USER_FIRST_NAME);
        mLastname = AppPreferences.init(this).getString(AppConstant.USER_LAST_NAME);
        mobileNumber = AppPreferences.init(this).getString(AppConstant.USER_MOBILE);

    }

    @OnClick({R.id.image_cross, R.id.et_expiration, R.id.ll_upgrade})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_cross:
                onBackPressed();
                break;
            case R.id.et_expiration:
                setExpirationDate();
                break;
            case R.id.ll_upgrade:
                Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
                confirmPayment();
                break;
        }
    }

    private void confirmPayment() {

        if (etCardNumber.getText().toString().trim().isEmpty() || month_value.isEmpty() || year_value.isEmpty() || etCvv.getText().toString().trim().isEmpty()) {

            Toast.makeText(StripeActivity.this, "Please Enter Valid Card Details.", Toast.LENGTH_SHORT).show();
        } else {

            ProgressBar.getInstanse().showDialog(mContext);
            getToken(etCardNumber.getText().toString(), month_value, year_value, etCvv.getText().toString());
        }
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>get stripe token for payment>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void getToken(String cardNumber, String cardExpMonth, String cardExpYear, String cardCVC) {

        card = new Card(
                cardNumber,
                Integer.parseInt(cardExpMonth),
                Integer.parseInt(cardExpYear),
                cardCVC
        );

        boolean validation = card.validateCard();
        if (validation) {

            try
            {   //CsPrefrence.getPreferences(HomeNewActivity.this).getString(Constant.UserBlockStatus,"").equals("0")

                //pk_live_6IVrA5mKQ8TrEvn10EG5Djni
                //pk_test_Qhkxp3lPza9CaAkvLKYh4WpM

                new Stripe(StripeActivity.this).createToken(
                        card,
                        "pk_live_6IVrA5mKQ8TrEvn10EG5Djni",
                        new TokenCallback() {
                            @Override
                            public void onError(Exception error) {
                                ProgressBar.getInstanse().hideDialog();
                                Log.d("Stripe", error.toString());

                            }
                            @Override
                            public void onSuccess(Token token)
                            {
                                Log.e("TokenIs", token.getId().toString() + " ");
                                stripetoken = token.getId().toString();
                                doSubcription();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!card.validateNumber()) {
            Toast.makeText(StripeActivity.this, "The card number that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else if (!card.validateExpiryDate()) {
            Toast.makeText(StripeActivity.this, "The expiration date that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else if (!card.validateCVC()) {
            Toast.makeText(StripeActivity.this, "The CVC code that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(StripeActivity.this, "The card details that you entered are invalid", Toast.LENGTH_SHORT).show();
        }
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subscription api>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void doSubcription() {


        ApiInterface apiService = QrClient.getClientQr().create(ApiInterface.class);
        Call<StripePaymentResponse> call = apiService.subscribe("api/vipsubscription?" + "emailuser=" + mEmail + "&userid=" + mId + "&stripetoken=" + stripetoken + "&name=" + mNAme + "&mobile=" + mobileNumber + "&lastname=" + mLastname + "&type=" + "A");
        call.enqueue(new Callback<StripePaymentResponse>() {
            @Override
            public void onResponse(Call<StripePaymentResponse> call, Response<StripePaymentResponse> response) {
                try {
                    if (response.body() != null) {
                        ProgressBar.getInstanse().hideDialog();
                        if (response.body().status.equalsIgnoreCase("1")) {
                            Toast.makeText(StripeActivity.this, "Subscribe successfully to the premium account", Toast.LENGTH_SHORT).show();
                            passIntent();

                        }
                        else
                        {
                            Toast.makeText(StripeActivity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<StripePaymentResponse> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });


    }

    private void passIntent() {
        Intent i = new Intent(mContext, ReferralActivity.class);
        startActivity(i);
        finishAffinity();


    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>custome expiray date picker
    private void setExpirationDate() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2018, 01, 01);

        YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(StripeActivity.this,
                calendar,
                new YearMonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onYearMonthSet(int year, int month) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");

                        String str = dateFormat.format(calendar.getTime());
                        String[] splited = str.split(" ");

                        if (splited.length > 0) {
                            if (splited[0].equals("January")) {
                                etExpiration.setText("01/" + splited[1]);

                                month_value = "01";
                                year_value = splited[1];

                            } else if (splited[0].equals("February")) {
                                etExpiration.setText("02/" + splited[1]);
                                month_value = "02";
                                year_value = splited[1];
                            } else if (splited[0].equals("March")) {
                                etExpiration.setText("03/" + splited[1]);

                                month_value = "03";
                                year_value = splited[1];

                            } else if (splited[0].equals("April")) {
                                etExpiration.setText("04/" + splited[1]);

                                month_value = "04";
                                year_value = splited[1];
                            } else if (splited[0].equals("May")) {
                                etExpiration.setText("05/" + splited[1]);

                                month_value = "05";
                                year_value = splited[1];
                            } else if (splited[0].equals("June")) {
                                etExpiration.setText("06/" + splited[1]);

                                month_value = "06";
                                year_value = splited[1];
                            } else if (splited[0].equals("July")) {
                                etExpiration.setText("07/" + splited[1]);

                                month_value = "07";
                                year_value = splited[1];
                            } else if (splited[0].equals("August")) {
                                etExpiration.setText("08/" + splited[1]);

                                month_value = "08";
                                year_value = splited[1];
                            } else if (splited[0].equals("September")) {
                                etExpiration.setText("09/" + splited[1]);

                                month_value = "09";
                                year_value = splited[1];
                            } else if (splited[0].equals("October")) {
                                etExpiration.setText("10/" + splited[1]);

                                month_value = "10";
                                year_value = splited[1];
                            } else if (splited[0].equals("November")) {
                                etExpiration.setText("11/" + splited[1]);

                                month_value = "11";
                                year_value = splited[1];

                            } else if (splited[0].equals("December")) {
                                etExpiration.setText("12/" + splited[1]);

                                month_value = "12";
                                year_value = splited[1];
                            }
                        }
                    }
                });
        yearMonthPickerDialog.setMinYear(2018);
        yearMonthPickerDialog.setMaxYear(2050);
        yearMonthPickerDialog.show();
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
