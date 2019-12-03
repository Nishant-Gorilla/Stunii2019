package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.DealsCustomModel;

public class SimilarDealAdapter extends RecyclerView.Adapter<SimilarDealAdapter.MyViewHolder> {
    Context context;
    onItemClickListner onItemClickListner;
    List<DealsCustomModel> mList;
    String mVip = "";



    public SimilarDealAdapter(AppCompatActivity context, List<DealsCustomModel> catDealsList, onItemClickListner onItemClickListner) {

        this.context = context;
        this.onItemClickListner = onItemClickListner;
        this.mList = catDealsList;
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
        String isVip = String.valueOf(mList.get(position).isVip());
        if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {
            holder.imgVip.setVisibility(View.GONE);


        } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
            holder.imgVip.setVisibility(View.VISIBLE);

        } else if (isVip.equalsIgnoreCase("false")) {
            holder.imgVip.setVisibility(View.GONE);
        }
        holder.tvDesc.setText(mList.get(position).getTitle());
        holder.tvTitle.setText(mList.get(position).getProvider_name());
        String cover_Image = mList.get(position).getCoverImage();
        String deal_id = mList.get(position).getId();
        String photo = mList.get(position).getProviderImage();
        double distance=mList.get(position).getDistance();
        if(distance!=0)
        {
            holder.tvDistance.setText(String.format("%.2f", distance) + " mi");
        }

        if (cover_Image != null) {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + deal_id + "/o/" + cover_Image)
                    .into(holder.imgCoverImage);
        }
        if (photo != null) {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + deal_id + "/o/" + photo)
                    .into(holder.imgProviderImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onItemclick(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}