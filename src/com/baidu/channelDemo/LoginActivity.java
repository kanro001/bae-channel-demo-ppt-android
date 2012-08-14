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
 * ��¼��
 */
public class LoginActivity extends Activity {

    /** log tag. */
    private static final String TAG = LoginActivity.class.getSimpleName();
    /** ������¼��WebiView */
    private WebView mWebView;

    /** ��Ȩ��ת��ַ */
    private static final String REDIRECT = "oob";
    /** ���������ĵ�ַ */
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
     * ��ȡ access token��
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
     * ��ʼ��Webview������WebviewClient
     * @param webview webview
     */
    private void initWebView(WebView webview) {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // ��ʾ����ʾ��ֱ��
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

                    // change # -> ? �����ȡ����
                    int fragmentIndex = url.indexOf("#");
                    url = "http://localhost/?" + url.substring(fragmentIndex + 1);

                    // ����URL��������ȡAccess token
                    String accessToken = Uri.parse(url).getQueryParameter("access_token");
                    Log.d(TAG, ">>> Get Original AccessToken: \r\n" + accessToken);

                    // ����
                    writeAccessToken(LoginActivity.this, accessToken);

//                    Toast.makeText(LoginActivity.this, "get access token success", Toast.LENGTH_SHORT).show();
                    
                    // ֪ͨ��¼�ɹ�
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
     * ���ˡ�
     * 
     * @return �����ǰҳ���ܻ��ˣ��򷵻�true�����򷵻�false��
     * */
    private boolean goBack() {
        WebView webView = mWebView;
        if (webView != null && webView.canGoBack()) {
            webView.goBack();

            return true;
        }

        return false;
    }

    /** Token�洢��SP�ļ��� */
    private static final String SHARED_PRE_FILE_NAME = "token";
    /** AccessToken�洢��Key */
    private static final String KEY_ACCESS_TOKEN = "access_token";

    /**
     * ���� access token�����ء�
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
     * ��ȡ�����access token��
     * 
     * @param context Context
     * @return δ���ܵ�AccessToken
     */
    public static void getAccessToken(Context context) {
//        String accessToken = null;
        SharedPreferences pref = context.getSharedPreferences(SHARED_PRE_FILE_NAME, Context.MODE_PRIVATE);
        BaseInfo.access_token = pref.getString(KEY_ACCESS_TOKEN, "");

//        return accessToken;
    }
}

