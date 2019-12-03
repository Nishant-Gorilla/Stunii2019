package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.AllDealFragment;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.DemandFragment;
import obllivionsoft.djole.nis.rs.stusdeals.view.fragment.SavedDealFragment;

public class Pager extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                AllDealFragment alldeal = new AllDealFragment();
                return alldeal;
            case 1:
                SavedDealFragment savedeal = new SavedDealFragment();
                return savedeal;
            case 2:
                DemandFragment demand = new DemandFragment();
                return demand;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
