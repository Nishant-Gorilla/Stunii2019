package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import butterknife.ButterKnife;
import butterknife.Unbinder;
import obllivionsoft.djole.nis.rs.stusdeals.R;

public class DrawerFragment extends Fragment {
    View rootView;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_drawer, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
