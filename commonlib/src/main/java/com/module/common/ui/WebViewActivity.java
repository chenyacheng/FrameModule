package com.module.common.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.arch.base.BaseActivity;
import com.module.arch.utils.LogUtils;
import com.module.common.R;
import com.module.common.constant.RouterConstant;
import com.module.common.databinding.ActivityWebviewBinding;
import com.module.common.message.SharedViewModel;
import com.module.common.navigationbar.UltimateBar;
import com.module.common.widget.HeadToolBar;

/**
 * webView页面
 *
 * @author BD
 * @date 2021/11/29
 */
@Route(path = RouterConstant.PATH_COMMON_WEB_VIEW_ACTIVITY)
public class WebViewActivity extends BaseActivity<ActivityWebviewBinding> {

    /**
     * WebViewClient主要帮助WebView处理各种通知、请求事件
     */
    private final WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // 页面开始加载
            getBinding().progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // 页面加载完成
            getBinding().progressBar.setVisibility(View.GONE);
            if (!getBinding().webView.getSettings().getLoadsImagesAutomatically()) {
                getBinding().webView.getSettings().setLoadsImagesAutomatically(true);
            }
        }
    };
    /**
     * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
     */
    private final WebChromeClient webChromeClient = new WebChromeClient() {
        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            getBinding().progressBar.setProgress(newProgress);
        }
    };
    private SharedViewModel sharedViewModel;

    @NonNull
    @Override
    protected ActivityWebviewBinding getViewBinding() {
        return ActivityWebviewBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViewModel() {
        sharedViewModel = getApplicationScopeViewModel(SharedViewModel.class);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void init() {
        LogUtils.info(getClass().getSimpleName(), "webView页面");

        // 设置状态栏
        UltimateBar.Companion.with(this).statusDark(true).statusDrawable(R.color.common_ffffffff)
                .statusDrawable2(R.color.common_ff000000).create().drawableBar();

        toolBar();

        getBinding().webView.setWebChromeClient(webChromeClient);
        getBinding().webView.setWebViewClient(webViewClient);
        WebSettings webSettings = getBinding().webView.getSettings();
        // 允许使用js
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置WebView是否加载图片资源，默认true，为了加快HTML网页加载完成的速度，设置为false
        webSettings.setLoadsImagesAutomatically(false);

        sharedViewModel.getWebViewContent().observe(this, content -> {
            String h = "http";
            if (content.startsWith(h)) {
                getBinding().webView.loadUrl(content);
            } else {
                String notice = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body>" + content + "</body></html>";
                getBinding().webView.loadDataWithBaseURL("about:blank", notice, "text/html", "utf-8", null);
            }
        });
    }

    private void toolBar() {
        HeadToolBar headToolBar = getBinding().toolbarHead;
        headToolBar.setHeadToolBarBackground(R.color.common_ffffffff);
        headToolBar.getToolBarLayout().getBackground().setAlpha(255);
        headToolBar.setLeftDrawable(R.drawable.toolbar_back_gray);
        headToolBar.setMiddleTitle("标题");
        headToolBar.setMiddleTitleVisible(View.INVISIBLE);
        headToolBar.setLeftDrawableClickListener(v -> finish());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击返回按钮的时候判断有没有上一页
        if (getBinding().webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            // goBack()表示返回webView的上一页面
            getBinding().webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        sharedViewModel = null;
        getBinding().getRoot().removeView(getBinding().webView);
        getBinding().webView.clearHistory();
        getBinding().webView.onPause();
        getBinding().webView.removeAllViews();
        getBinding().webView.destroy();
        super.onDestroy();
    }
}
