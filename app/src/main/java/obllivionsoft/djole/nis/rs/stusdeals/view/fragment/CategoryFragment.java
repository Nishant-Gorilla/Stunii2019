package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.CategoryResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.SubCategoryActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.CategoryAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    View rootView;
    Unbinder unbinder;
    @BindView(R.id.rv_categries)
    RecyclerView rvCategries;
    CategoryAdapter categoryAdapter;
    Context mContext;
    List<CategoryResponseModel.Data> categoryList;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mContext = getActivity();
        getCategory();

        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                simpleSwipeRefreshLayout.setRefreshing(false);
                initView();
            }


        });
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.get all category>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getCategory() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CategoryResponseModel> call = apiService.getCategory();
        call.enqueue(new Callback<CategoryResponseModel>() {
            @Override
            public void onResponse(Call<CategoryResponseModel> call, Response<CategoryResponseModel> response) {
                try {
                    if (response.body() != null) {
                        categoryList = response.body().data;
                        callAdapter();
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<CategoryResponseModel> call, Throwable t) {

            }


        });
    }

    private void callAdapter() {
        rvCategries.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvCategries.setItemAnimator(new DefaultItemAnimator());
        categoryAdapter = new CategoryAdapter(getActivity(), categoryList, new CategoryAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {
                String dealid = categoryList.get(position)._id;
                String deal_name = categoryList.get(position).name;
                Intent jobs = new Intent(getActivity(), SubCategoryActivity.class);
                jobs.putExtra("deal_id", dealid);
                jobs.putExtra("deal_name", deal_name);
                getActivity().startActivity(jobs);

            }
        });
        rvCategries.setAdapter(categoryAdapter);


    }
}
