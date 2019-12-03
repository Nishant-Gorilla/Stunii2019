package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
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
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.TestingDealsModels;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.DealDetailActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.PremiumOfferActivity;

public class NestedItemAdapter extends RecyclerView.Adapter<NestedItemAdapter.MyViewHolder> {
    Context context;
    onItemClickListner onItemClickListner;
    List<TestingDealsModels.Dataa> mList;
    List<TestingDealsModels._provider> mProviderList;
    String provider_name;
    String mVip = "", mIsvip = "";



    public NestedItemAdapter(Context context, List<TestingDealsModels.Dataa> mNestedlist, onItemClickListner onItemClickListner) {
        this.context = context;
        this.onItemClickListner = onItemClickListner;
        this.mList = mNestedlist;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_cover_image)
        AppCompatImageView imgCoverImage;
        @BindView(R.id.img_provider_image)
        AppCompatImageView imgProviderImage;
        @BindView(R.id.tv_title)
        MyTextView tvTitle;
        @BindView(R.id.tv_desc)
        MyTextView tvDesc;
        @BindView(R.id.img_vip)
        AppCompatImageView imgVip;
        @BindView(R.id.tv_distance)
        MyTextViewBold tvDistance;


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

        View view = LayoutInflater.from(context).inflate(R.layout.item_deals, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mVip = AppPreferences.init(context).getString(AppConstant.VIPUSER);
        Log.e("mVip", mVip + "");
        String isVip = String.valueOf(mList.get(position).isVIP);
        if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {
            holder.imgVip.setVisibility(View.GONE);


        } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
            holder.imgVip.setVisibility(View.VISIBLE);

        } else if (isVip.equalsIgnoreCase("false")) {
            holder.imgVip.setVisibility(View.GONE);
        }
        String deal_id = mList.get(position)._id;
        String cover_Image = mList.get(position).coverPhoto;
        String provider_logo = mList.get(position).photo;
        String meta_title = mList.get(position).meta_title;
        mProviderList = mList.get(position)._provider;
        double distance=mList.get(position).distance;
        if(distance!=0)
        {
            holder.tvDistance.setText(String.format("%.2f", distance) + " mi");
        }
        if (!mProviderList.isEmpty()) {
            for (int i = 0; i < mProviderList.size(); i++) {
                provider_name = mList.get(position)._provider.get(i).name;
            }
            holder.tvTitle.setText(provider_name);
        }


        holder.tvDesc.setText(meta_title);


        if (cover_Image != null) {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + deal_id + "/o/" + cover_Image)
                    .into(holder.imgCoverImage);
        }
        if (provider_logo != null) {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + deal_id + "/o/" + provider_logo)
                    .into(holder.imgProviderImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVip = AppPreferences.init(context).getString(AppConstant.VIPUSER);
                String isVip = String.valueOf(mList.get(position).isVIP);
                if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {

                    String deal_id = mList.get(position)._id;
                    String distance = String.valueOf(mList.get(position).distance);
                    Intent dealdetails = new Intent(context, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance",distance);
                    context.startActivity(dealdetails);


                } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
                    Intent premiumOffer = new Intent(context, PremiumOfferActivity.class);
                    context.startActivity(premiumOffer);
                } else if (isVip.equalsIgnoreCase("false")) {
                    String deal_id = mList.get(position)._id;
                    String distance = String.valueOf(mList.get(position).distance);
                    Intent dealdetails = new Intent(context, DealDetailActivity.class);
                    dealdetails.putExtra("dealId", deal_id);
                    dealdetails.putExtra("distance",distance);
                    context.startActivity(dealdetails);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
