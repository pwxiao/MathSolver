package wpxiao.math.solver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class DrawActivity extends AppCompatActivity {


    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        webView = findViewById(R.id.webview);

        // 配置 WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  // 启用 JavaScript 支持
        webSettings.setDomStorageEnabled(true);  // 启用 DOM Storage 支持
        //禁止缩放
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        // 加载网页

        webView.loadUrl("file:///android_asset/plot.html");


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 销毁 WebView
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
        }
    }
}