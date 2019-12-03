package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.view.adapter.ProviderDealAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HotDealFragment extends Fragment {
    View rootView;
    Unbinder unbinder;
    ProviderDealAdapter providerDealAdapter;
    @BindView(R.id.rv_hotdeals)
    RecyclerView rvHotdeals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hotdeal, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initView();

        return rootView;
    }

    private void initView() {


//        //>>>>>>>>>>>>>>>>>>>>>>>>>>ALL deal recycler view list>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//        RecyclerView.LayoutManager mLayoutdeals = new LinearLayoutManager(getContext(), rvHotdeals.VERTICAL, false);
//        rvHotdeals.setLayoutManager(mLayoutdeals);
//
//        providerDealAdapter =new ProviderDealAdapter(getContext(), new ProviderDealAdapter.onItemClickListner() {
//            @Override
//            public void onItemclick(int position) {
//                Intent dealdetails=new Intent(getActivity(), DealDetailActivity.class);
//                startActivity(dealdetails);
//
//            }
//        });
//        rvHotdeals.setAdapter(providerDealAdapter);


    }
}
