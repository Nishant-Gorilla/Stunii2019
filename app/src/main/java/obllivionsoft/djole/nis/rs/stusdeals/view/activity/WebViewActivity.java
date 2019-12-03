package obllivionsoft.djole.nis.rs.stusdeals.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import obllivionsoft.djole.nis.rs.stusdeals.R;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        intWebView();
    }

    private void intWebView() {
        Intent get=getIntent();
        String name=get.getStringExtra("Name");
        Log.e("link",name+"");
        if(name.equalsIgnoreCase("privacy"))
        {
            webview.loadUrl("https://stunii.com/privacy");
        }
        else if(name.equalsIgnoreCase("terms"))
        {
            webview.loadUrl("https://stunii.com/terms");

        }else if(name.equalsIgnoreCase("cancel"))
        {
            webview.loadUrl("https://stunii.com/signin");
        }
        else

        {
            webview.loadUrl(name);
        }


        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image
                super.onPageStarted(view, url, favicon);

                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO hide your progress image
                super.onPageFinished(view, url);

                progressbar.setVisibility(View.INVISIBLE);

            }

        });
    }
}
