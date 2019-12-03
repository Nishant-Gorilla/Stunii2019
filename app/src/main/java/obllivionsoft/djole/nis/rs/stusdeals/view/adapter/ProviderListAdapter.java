package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ProviderCustomModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.ProviderResponseModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProviderListAdapter extends RecyclerView.Adapter<ProviderListAdapter.MyViewHolder> {
    Context context;
    onItemClickListner onItemClickListner;
    List<ProviderCustomModel> mList;


    public ProviderListAdapter(AppCompatActivity context, List<ProviderCustomModel> providerList, onItemClickListner onItemClickListner) {
        this.context = context;
        this.onItemClickListner = onItemClickListner;
        this.mList = providerList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgLogo)
        AppCompatImageView imgLogo;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_provider_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String id = mList.get(position).getId();
        String image = mList.get(position).getPhoto();
        if(!image.isEmpty())
        {
            Glide.with(context)
                    .load(AppConstant.imgProvidersUrl + id + "/o/" + image)
                    .into(holder.imgLogo);
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
