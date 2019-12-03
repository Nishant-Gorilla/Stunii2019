package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;

import java.util.List;

public class DealViewPager_Adapter extends PagerAdapter {
    private List<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    String mDealid="";




    public DealViewPager_Adapter(Context context, List<String> mImagelist, String mDeal_id) {
        this.context = context;
        this.IMAGES=mImagelist;
        this.mDealid=mDeal_id;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_viewpager, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        String value=IMAGES.get(position).toString();
        Glide
                .with(context)
                .load(AppConstant.imgGalleryUrl + mDealid + "/o/" + IMAGES.get(position))
                .into(imageView);
//        Picasso.with(context).load(value).into(imageView);


        //  imageView.setImageResource(Integer.parseInt(IMAGES.get(position)));

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
