package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class HomeVPAdapter extends PagerAdapter {

    private FragmentManager manager;
    List<FrameLayout> frameLayouts;
    List<Fragment> fragList;

    public HomeVPAdapter(FragmentManager manager, List<FrameLayout> frameLayouts, List<Fragment> fragList) {
        this.manager = manager;
        this.frameLayouts = frameLayouts;
        this.fragList = fragList;
    }

    @Override
    public int getCount() {
        return frameLayouts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private void loadFragment(Fragment fragment, int containerId) {
        if (fragment != null) {
            manager.beginTransaction().replace(containerId, fragment).commit();
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewPager vp = (ViewPager) container;
        loadFragment(fragList.get(position), frameLayouts.get(position).getId());
        vp.addView(frameLayouts.get(position));
        return frameLayouts.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        RecyclerView view = (RecyclerView) object;
        vp.removeView(view);
    }
}
