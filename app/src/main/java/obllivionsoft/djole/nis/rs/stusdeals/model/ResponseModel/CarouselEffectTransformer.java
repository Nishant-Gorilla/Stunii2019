package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

public class CarouselEffectTransformer implements ViewPager.PageTransformer {

    private int maxTranslateOffsetX;
    private int leftPadding;
    private int rightPadding;
    private ViewPager viewPager;

    public CarouselEffectTransformer(Context context) {
        this.maxTranslateOffsetX = dp2px(context, 180);
        this.leftPadding = dp2px(context, 50);
        this.rightPadding = dp2px(context, 50);
    }

    public void transformPage(View view, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) view.getParent();
        }

        // get child
        LinearLayout layout = (LinearLayout) view;
        View pagePosition = layout.getChildAt(0);
        int pageIndex = (int) pagePosition.getTag();

        // check if view was measured
        int offsetX = 0;
        if (view.getLeft() == 0 && view.getMeasuredWidth() == 0) {
            int pageWidth = viewPager.getMeasuredWidth() - leftPadding - rightPadding;
            int leftInScreen = leftPadding + pageIndex * pageWidth;
            int centerXInViewPager = leftInScreen + (viewPager.getMeasuredWidth() - leftPadding - rightPadding) / 2;
            offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        }
        else {
            int leftInScreen = view.getLeft() - viewPager.getScrollX();
            int centerXInViewPager = leftInScreen + view.getMeasuredWidth()/ 2;
            offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        }


        float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);

        if (scaleFactor > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-maxTranslateOffsetX * offsetRate);
        }
        ViewCompat.setElevation(view, scaleFactor);
    }

    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

}
