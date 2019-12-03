package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.SubCategoryResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    Context context;
    onItemClickListner onItemClickListner;
    List<SubCategoryResponse.SubCategory> subList;


    public SubCategoryAdapter(AppCompatActivity context, List<SubCategoryResponse.SubCategory> subCategoryList, onItemClickListner onItemClickListner) {
        this.context = context;
        this.subList = subCategoryList;
        this.onItemClickListner = onItemClickListner;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        MyTextViewBold tvSubcatName;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_subcategory_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvSubcatName.setText(subList.get(position).subCategoryName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemclick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return subList.size();
    }
}
