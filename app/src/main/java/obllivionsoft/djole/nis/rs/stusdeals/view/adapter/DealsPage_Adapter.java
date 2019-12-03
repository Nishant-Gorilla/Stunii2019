package obllivionsoft.djole.nis.rs.stusdeals.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.TestingDealsModels;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.DealDetailActivity;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.PremiumOfferActivity;

import java.util.List;

public class DealsPage_Adapter extends PagerAdapter {
    private LayoutInflater inflater;
    List<TestingDealsModels.Data> featuredList;
     Context context;
    String mVip="";
    public DealsPage_Adapter(Context context, List<TestingDealsModels.Data> images) {
        this.context=context;
        this.featuredList=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return featuredList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_viewpager_deal, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        final ImageView imgVip = (ImageView) imageLayout.findViewById(R.id.img_vip);
        final ImageView imgProvider = (ImageView) imageLayout.findViewById(R.id.iv_logo);

        mVip = AppPreferences.init(context).getString(AppConstant.VIPUSER);
        Log.e("mVip",mVip+"");
        String isVip = String.valueOf(featuredList.get(position).isVIP);
        if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {
            imgVip.setVisibility(View.GONE);


        } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
            imgVip.setVisibility(View.VISIBLE);

        }
        else if(isVip.equalsIgnoreCase("false"))
        {
            imgVip.setVisibility(View.GONE);
        }
        MyTextView meat_title=(MyTextView)imageLayout.findViewById(R.id.tv_deal_detail);
        MyTextView provider_title=(MyTextView)imageLayout.findViewById(R.id.tv_title);
        MyTextViewBold distacnce=(MyTextViewBold) imageLayout.findViewById(R.id.tv_distance);
        String deal_id=featuredList.get(position)._id;
        String deal_image=featuredList.get(position).coverPhoto;
        String provider_logo=featuredList.get(position).photo;
        String meta_title=featuredList.get(position).meta_title;
        String providet_name=featuredList.get(position)._provider.name;
        double distance=featuredList.get(position).distance;
        if(distance!=0)
        {
            distacnce.setText(String.format("%.2f", distance) + " mi");
        }
        meat_title.setText(meta_title);
        provider_title.setText(providet_name);
        Log.e("deal_id",deal_id+"");
        Log.e("deal_image",deal_image+"");

        //>>>>>>>>>>>>>>>provider image>>>>>>>>>>>>>>>>>>>>>>>>
        if(provider_logo!=null)
        {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + deal_id + "/o/" + provider_logo)
                    .into(imgProvider);
        }

        //>>>>>>>>>>>>>>>>>>cover image>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        if(deal_image!=null)
        {
            Glide.with(context)
                    .load(AppConstant.imgDealsUrl + deal_id + "/o/" + deal_image)
                    .into(imageView);
        }
         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mVip = AppPreferences.init(context).getString(AppConstant.VIPUSER);
                 String isVip = String.valueOf(featuredList.get(position).isVIP);
                 if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("1")) {

                     String deal_id = featuredList.get(position)._id;
                     String distance = String.valueOf(featuredList.get(position).distance);
                     Intent dealdetails = new Intent(context, DealDetailActivity.class);
                     dealdetails.putExtra("dealId", deal_id);
                     dealdetails.putExtra("distance",distance);
                     context.startActivity(dealdetails);


                 } else if (isVip.equalsIgnoreCase("true") && mVip.equalsIgnoreCase("0")) {
                     Intent premiumOffer = new Intent(context, PremiumOfferActivity.class);
                     context.startActivity(premiumOffer);
                 } else if (isVip.equalsIgnoreCase("false")) {
                     String deal_id = featuredList.get(position)._id;
                     String distance = String.valueOf(featuredList.get(position).distance);
                     Intent dealdetails = new Intent(context, DealDetailActivity.class);
                     dealdetails.putExtra("dealId", deal_id);
                     dealdetails.putExtra("distance",distance);
                     context.startActivity(dealdetails);
                 }
//                 Toast.makeText(context, "under development", Toast.LENGTH_SHORT).show();
//                 Log.e("deal_id",featuredList.get(position)._id+"");
//                 String deal_id=featuredList.get(position)._id;
//                 Intent dealdetails = new Intent(context, DealDetailActivity.class);
//                 dealdetails.putExtra("dealId",deal_id);
//                 context.startActivity(dealdetails);
             }
         });

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
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
