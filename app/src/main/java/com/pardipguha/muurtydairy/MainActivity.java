package com.pardipguha.muurtydairy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.http.SslError;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        if (doubleBackToExitPressedOnce) {
                            super.onBackPressed();

                        }

                        this.doubleBackToExitPressedOnce = true;
                        Snackbar.make(findViewById(R.id.coordinatelayout), "Please click BACK again to exit", 0).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.this.doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                    }

            }
                    return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  setTitle(Html.fromHtml("<font color='#ffffff'>Muurty Dairy</font>"));
        //setTitle("Muurty Dairy");
        if (!isNetworkConnected()) {
            finish();
            Toast.makeText(getApplicationContext(), "Please check your Network Connection", Toast.LENGTH_LONG).show();
        }
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        progressbar = (ProgressBar) findViewById(R.id.pb);
        webview.setHapticFeedbackEnabled(false);


        WebSettings webSettings = webview.getSettings();
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(false);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);


        webview.setWebViewClient(new MyWebViewClient());

        webview.loadUrl("http://www.muurtydairy.com");


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Security Certificate of the Website comes from an Unknown Authorization Center");

            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                    finish();
                }
            });
            builder.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressbar.setVisibility(View.GONE);

        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
