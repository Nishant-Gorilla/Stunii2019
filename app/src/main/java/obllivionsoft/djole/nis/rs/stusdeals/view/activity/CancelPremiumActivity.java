package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CancelPremiumActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    MyTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_premium);
        ButterKnife.bind(this);
        InitView();
    }

    private void InitView() {

        SpannableString ss = new SpannableString("We don’t want you to miss out on our amazing package deals, but we understand if you need to go. You can cancel your deal package by visiting www.stunii.com/cancelpackage and proceeding to ‘Cancel Package");

        ClickableSpan clickableSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View textView)
            {

                Intent i=new Intent(CancelPremiumActivity.this,WebViewActivity.class);
                i.putExtra("Name","cancel");
                startActivity(i);
                finish();
            }
            @Override
            public void updateDrawState(TextPaint ds)
            {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 142, 170, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setText(ss);

        tv.setMovementMethod(LinkMovementMethod.getInstance());

        tv.setHighlightColor(Color.TRANSPARENT);
    }



}
