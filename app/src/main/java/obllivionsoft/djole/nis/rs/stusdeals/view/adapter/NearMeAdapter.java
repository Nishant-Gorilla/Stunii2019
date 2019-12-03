package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealsCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.NearMeResponse;

public class NearMeAdapter extends RecyclerView.Adapter<NearMeAdapter.MyViewHolder> {
     Context context;
     onItemClickListner onItemClickListner;
     List<NearMeResponse.Data> mList;
     String mVip="";

    public NearMeAdapter(Context mContext, List<NearMeResponse.Data> listNearDeals, onItemClickListner onItemClickListner) {

        this.context = mContext;
        this.onItemClickListner = onItemClickListner;
        this.mList = listNearDeals;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_deal)
        AppCompatImageView imgDeal;
        @BindView(R.id.rl_header)
        RelativeLayout rlHeader;
        @BindView(R.id.tv_deal_title)
        MyTextViewBold tvDealTitle;
        @BindView(R.id.img_provider)
        AppCompatImageView imgProvider;
        @BindView(R.id.tv_provider_name)
        MyTextView tvProviderName;
        @BindView(R.id.tv_distance)
        MyTextView tvDistance;
        @BindView(R.id.tv_day)
        MyTextView tvDay;
        @BindView(R.id.rating_bar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.tv_number)
        MyTextView tvNumber;
        @BindView(R.id.ll_deal)
        LinearLayout llDeal;
        @BindView(R.id.img_vip)
        AppCompatImageView imgVip;
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
    public NearMeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.provider_deal_list, parent, false);
        return new NearMeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearMeAdapter.MyViewHolder holder, int i) {

        mVip = AppPreferences.init(context).getString(AppConstant.VIPUSER);
        Log.e("mVip",mVip+"");
        String isVip = String.valueOf(mList.get(i).isVIP);
        if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {
            holder.imgVip.setVisibility(View.GONE);


        } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
            holder.imgVip.setVisibility(View.VISIBLE);

        }
        else if(isVip.equalsIgnoreCase("false"))
        {
            holder.imgVip.setVisibility(View.GONE);
        }

        String id = mList.get(i)._id;
        String providerImage = mList.get(i).photo;
        String coverImage = mList.get(i).coverPhoto;
        String title = mList.get(i).title;
        String provider_name = mList.get(i)._provider.name;
        double distance=mList.get(i).distance;
        if(distance!=0)
        {
            holder.tvDistance.setText(String.format("%.2f", distance) + " mi");
        }
        if (!title.isEmpty()) {
            holder.tvDealTitle.setText(title);
        }
        if (!provider_name.isEmpty()) {
            holder.tvProviderName.setText(provider_name);
        }

        //>>>>>>>>>>>>>>>provider image>>>>>>>>>>>>>>>>>>>>>>>>
        if (!providerImage.isEmpty()) {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + id + "/o/" + providerImage)
                    .into(holder.imgProvider);
        }

        //>>>>>>>>>>>>>>>>>>cover image>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        if (!coverImage.isEmpty()) {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + id + "/o/" + coverImage)
                    .into(holder.imgDeal);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onItemClickListner.onItemclick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
