package org.androidtown.studyingmineral101;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Authenticate extends AppCompatActivity {

    private WebView webView;


    public static String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activate_authenticate);

        setTitle("Login");

        final String url = this.getIntent().getStringExtra(EXTRA_URL);
        if (null == url) {
            Log.e("Twitter", "URL cannot be null");
            finish();
        }

        webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }


    class MyWebViewClient extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(getResources().getString(R.string.twitter_callback))) {
                Uri uri = Uri.parse(url);



                String verifier = uri.getQueryParameter(getString(R.string.twitter_oauth_verifier));
                Intent resultIntent = new Intent();
                resultIntent.putExtra(getString(R.string.twitter_oauth_verifier), verifier);
                setResult(RESULT_OK, resultIntent);


                finish();
                return true;
            }
            return false;
        }
    }
}


