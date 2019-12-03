package obllivionsoft.djole.nis.rs.stusdeals.controller.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import obllivionsoft.djole.nis.rs.stusdeals.R;


public class MyTextViewBold extends AppCompatTextView {
    private static final String TAG = "CustomTextView";

    public MyTextViewBold(Context context) {
        super(context);
    }

    public MyTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public MyTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(ctx.getAssets(), "Proxima Nova Bold.otf");
            // typeface = Typeface.createFromAsset(ctx.getAssets(),"font/HVD Fonts - BrandonText-Light.otf");
        } catch (Exception e) {
            Log.e(TAG, "Unable to load typeface: " + e.getMessage());
            return false;
        }

        setTypeface(typeface);
        return true;
    }
}
