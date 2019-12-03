package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.CategoryNameModel;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.TestingDealsModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeDealAdapter extends RecyclerView.Adapter<HomeDealAdapter.MyViewHolder> {
    Context context;
    NestedItemAdapter nestedItemAdapter;
    onItemClickListner onItemClickListner;
    List<CategoryNameModel> mNameListing;
    List<TestingDealsModels.Dataa> mNestedlist;
    List<TestingDealsModels.DealsList> mHomeList;

    public HomeDealAdapter(FragmentActivity activity, List<TestingDealsModels.DealsList> mHomeList, HomeDealAdapter.onItemClickListner onItemClickListner) {

        this.onItemClickListner=onItemClickListner;
        this.context=activity;
        this.mHomeList=mHomeList;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        MyTextView tvTitle;
        @BindView(R.id.rv_item_list)
        RecyclerView rvItemList;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_deal_title, parent, false);
        return new HomeDealAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                holder.tvTitle.setText(mHomeList.get(position).name);
                mNestedlist= mHomeList.get(position).data;
                Collections.sort(mNestedlist, new Comparator<TestingDealsModels.Dataa>() {
                    @Override
                    public int compare(TestingDealsModels.Dataa dataa, TestingDealsModels.Dataa t1) {
                        return Double.compare(dataa.distance, t1.distance);
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, holder.rvItemList.HORIZONTAL, false);
                holder.rvItemList.setLayoutManager(layoutManager);
                nestedItemAdapter = new NestedItemAdapter(context, mNestedlist, new NestedItemAdapter.onItemClickListner() {
                    @Override
                    public void onItemclick(int position) {


                    }
                });
                holder.rvItemList.setAdapter(nestedItemAdapter);
            }








    @Override
    public int getItemCount() {
        return mHomeList.size();
    }


}

