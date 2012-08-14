package com.baidu.channelDemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 登录。
 */
public class LoginActivity extends Activity {

    /** log tag. */
    private static final String TAG = LoginActivity.class.getSimpleName();
    /** 用来登录的WebiView */
    private WebView mWebView;

    /** 授权跳转地址 */
    private static final String REDIRECT = "oob";
    /** 开发者中心地址 */
    static final String DEV_CENTER = "https://openapi.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView = new WebView(LoginActivity.this);

        setContentView(mWebView);

        initWebView(mWebView);

        getAccessToken();
    }

    /**
     * 获取 access token。
     */
    private void getAccessToken() {
        String url = DEV_CENTER + "oauth/2.0/authorize?" 
                                + "response_type=token" 
                                + "&client_id=" + BaseInfo.app_key 
                                + "&redirect_uri=" + REDIRECT 
                                + "&display=mobile";

        Log.d(TAG, "GetAccessTokenUrl: " + url);

        mWebView.loadUrl(url);
    }

    /**
     * 初始化Webview，设置WebviewClient
     * @param webview webview
     */
    private void initWebView(WebView webview) {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 提示框不显示，直接
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, ">>> Should load Url:" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                if (url.startsWith(REDIRECT) || url.contains("login_success")) {

                    Log.d(TAG, "Redirect Url:" + url);

                    // change # -> ? 方便获取参数
                    int fragmentIndex = url.indexOf("#");
                    url = "http://localhost/?" + url.substring(fragmentIndex + 1);

                    // 解析URL参数，获取Access token
                    String accessToken = Uri.parse(url).getQueryParameter("access_token");
                    Log.d(TAG, ">>> Get Original AccessToken: \r\n" + accessToken);

                    // 保存
                    writeAccessToken(LoginActivity.this, accessToken);

//                    Toast.makeText(LoginActivity.this, "get access token success", Toast.LENGTH_SHORT).show();
                    
                    // 通知登录成功
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, PPTListActivity.class);
                    startActivity(intent);

                    finish();

                }

            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (goBack()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 回退。
     * 
     * @return 如果当前页面能回退，则返回true，否则返回false。
     * */
    private boolean goBack() {
        WebView webView = mWebView;
        if (webView != null && webView.canGoBack()) {
            webView.goBack();

            return true;
        }

        return false;
    }

    /** Token存储的SP文件名 */
    private static final String SHARED_PRE_FILE_NAME = "token";
    /** AccessToken存储的Key */
    private static final String KEY_ACCESS_TOKEN = "access_token";

    /**
     * 保存 access token到本地。
     * 
     * @param context Context
     * @param token access token
     */
    public static void writeAccessToken(Context context, String token) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PRE_FILE_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.commit();
    }

    /**
     * 获取保存的access token。
     * 
     * @param context Context
     * @return 未加密的AccessToken
     */
    public static void getAccessToken(Context context) {
//        String accessToken = null;
        SharedPreferences pref = context.getSharedPreferences(SHARED_PRE_FILE_NAME, Context.MODE_PRIVATE);
        BaseInfo.access_token = pref.getString(KEY_ACCESS_TOKEN, "");

//        return accessToken;
    }
}

