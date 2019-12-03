package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.JobResponseModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.MyViewHolder> {
    Context context;
    onItemClickListner onItemClickListner;
    List<JobResponseModel.Data> list;


    public JobsAdapter(FragmentActivity activity, List<JobResponseModel.Data> jobsList, JobsAdapter.onItemClickListner onItemClickListner) {
        this.context = activity;
        this.onItemClickListner = onItemClickListner;
        this.list = jobsList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_logo)
        CircleImageView imgLogo;
        @BindView(R.id.tv_job_title)
        MyTextViewBold tvJobTitle;
        @BindView(R.id.tv_company_name)
        MyTextViewBold tvCompanyName;
        @BindView(R.id.tv_address)
        MyTextViewBold tvAddress;
        @BindView(R.id.tv_job_type)
        MyTextView tvJobType;
        @BindView(R.id.tv_hourly_rate)
        MyTextView tvHourlyRate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onItemClickListner {
        void onItemclick(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_jobs, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvJobTitle.setText(list.get(position).jobName);
        holder.tvCompanyName.setText(list.get(position).companyName);
        holder.tvAddress.setText(list.get(position).jobAddress);
        holder.tvJobType.setText(list.get(position).jobType);
        holder.tvHourlyRate.setText(list.get(position).jobRate);
        String image= AppConstant.imageBaseURL+list.get(position).companyImage;
        Log.e("image",AppConstant.imageBaseURL+list.get(position).companyImage);



        Glide.with(context)
                .load(AppConstant.imageURL+list.get(position).companyImage)
                .into(holder.imgLogo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemclick(holder.getAdapterPosition());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
