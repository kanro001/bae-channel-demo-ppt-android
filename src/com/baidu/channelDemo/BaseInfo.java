package com.baidu.channelDemo;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BaseInfo {
	
	
	public static boolean isOnline(Context content) {
	    ConnectivityManager connMgr = (ConnectivityManager) 
	            content.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	} 
	
	
	public static String fileTitle = null;
	
    /** 应用注册后的 api_key. */
    public static final String app_key = "GkWwrvZrCaMQfCZ190ujndZm";
    
    public static String access_token = null;
    
    public static int pageNum = 0;
    
    public static int currentPage = 2;
    
    public static String  pptId = null;
    
    public static String  pptName= null;
    
    public static int pptNum = 0; 
    
    public static HashMap<String, String> contentMap =new HashMap<String, String>();
    
    public static ArrayList<HashMap<String, String>> pptContentList =new ArrayList<HashMap<String,String>>();
    
}
