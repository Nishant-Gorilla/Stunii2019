package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.Validations;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyEditText;
import obllivionsoft.djole.nis.rs.stusdeals.model.RequestModel.DemandRequestModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DemandResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.LoginResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.HomeActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.LoginActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.OnBoardingActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemandFragment extends Fragment {
    View rootView;
    Unbinder unbinder;
    @BindView(R.id.tv_city)
    MyEditText tvCity;
    @BindView(R.id.tv_organization)
    MyEditText tvOrganization;
    @BindView(R.id.tv_deal_type)
    MyEditText tvDealType;
    @BindView(R.id.tv_demand)
    LinearLayout tvDemand;
    String mNAme="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_demand, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {

        mNAme = AppPreferences.init(getContext()).getString(AppConstant.USER_FIRST_NAME);
    }

    @OnClick(R.id.tv_demand)
    public void onViewClicked() {

        if (Validations.isValidateDemand(getActivity(), tvCity, tvOrganization,tvDealType)) {
            demandDeals();

        }

    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.hit demand deal >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void demandDeals() {
        ProgressBar.getInstanse().showDialog(getContext());
        DemandRequestModel demandRequestModel=new DemandRequestModel();
        demandRequestModel.setCity(tvCity.getText().toString());
        demandRequestModel.setOrganisation(tvCity.getText().toString());
        demandRequestModel.setDealType(tvDealType.getText().toString());
        demandRequestModel.setStudent_name(mNAme);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DemandResponseModel> call = apiService.demandDeal("application/json", demandRequestModel);
        call.enqueue(new Callback<DemandResponseModel>() {
            @Override
            public void onResponse(Call<DemandResponseModel> call, Response<DemandResponseModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if(response.body().status==200)
                    {
                        opeDialog();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "There is some error please try again after some time", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();

                }

            }

            @Override
            public void onFailure(Call<DemandResponseModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
                Log.e("exception",t.getMessage());

            }


        });


    }

    private void opeDialog() {

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.app_name))
                .setMessage("Thank you! Your Deal Demand has successfully been submitted. Be sure to keep an eye out for this offer!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        tvCity.setText("");
                        tvOrganization.setText("");
                        tvDealType.setText("");

                    }


                })
                .setIcon(R.drawable.logosignup)
                .show();



    }
}
