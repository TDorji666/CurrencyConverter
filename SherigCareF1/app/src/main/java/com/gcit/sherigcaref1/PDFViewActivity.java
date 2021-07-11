package com.gcit.sherigcaref1;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URLEncoder;

public class PDFViewActivity extends AppCompatActivity {

    WebView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);

        view = (WebView) findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);

        String TeacherPDFImage = getIntent().getStringExtra("PDFImage");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Opening....");

        view.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });
        String url = "";
        try{
            url = URLEncoder.encode(TeacherPDFImage,"UTF-8");
        }
        catch(Exception e){ }

        view.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
    }
}