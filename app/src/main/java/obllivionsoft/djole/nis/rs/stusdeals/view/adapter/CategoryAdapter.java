package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.CategoryResponseModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    onItemClickListner onItemClickListner;
    List<CategoryResponseModel.Data> list;


    public CategoryAdapter(FragmentActivity activity, List<CategoryResponseModel.Data> categoryList, onItemClickListner onItemClickListner) {
        this.context = activity;
        this.list = categoryList;
        this.onItemClickListner = onItemClickListner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        AppCompatImageView image;
        @BindView(R.id.textViewCategoryName)
        MyTextViewBold textViewCategoryName;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_categories, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        String id = list.get(i)._id;
        String image = list.get(i).photo;
        holder.textViewCategoryName.setText(list.get(i).name);
        Glide.with(context)
                .load(AppConstant.imgCategoriesUrl + id + "/o/" + image)
                .into(holder.image);
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
