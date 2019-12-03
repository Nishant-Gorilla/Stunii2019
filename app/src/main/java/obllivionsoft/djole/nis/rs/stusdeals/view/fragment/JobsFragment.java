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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.JobResponseModel;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.JobDetailsActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.JobsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsFragment extends Fragment {
    View rootView;
    Unbinder unbinder;
    @BindView(R.id.rv_jobs)
    RecyclerView rvJobs;
    JobsAdapter jobsAdapter;
    List<JobResponseModel.Data> jobsList;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();

        return rootView;
    }

    private void initView() {
        mContext=getActivity();
        getJobList();



    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>get job list>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void getJobList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JobResponseModel> call = apiService.getJobs();
        call.enqueue(new Callback<JobResponseModel>() {
            @Override
            public void onResponse(Call<JobResponseModel> call, Response<JobResponseModel> response) {
                try
                {
                    if(response.body()!=null)
                    {
                        jobsList=response.body().data;
                        callAdapter();
                    }
                }
                catch (Exception e)
                {

                }

            }
            @Override
            public void onFailure(Call<JobResponseModel> call, Throwable t) {

            }


        });

    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>call jobs adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void callAdapter() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), rvJobs.VERTICAL, false);
        rvJobs.setLayoutManager(mLayoutManager);
        rvJobs.setItemAnimator(new DefaultItemAnimator());
        jobsAdapter=new JobsAdapter(getActivity(),jobsList, new JobsAdapter.onItemClickListner() {
            @Override
            public void onItemclick(int position) {
                String jobId=jobsList.get(position)._id;
                Intent jobs=new Intent(getActivity(), JobDetailsActivity.class);
                jobs.putExtra("job_id",jobId);
                getActivity().startActivity(jobs);

            }
        });
        rvJobs.setAdapter(jobsAdapter);


    }
}
