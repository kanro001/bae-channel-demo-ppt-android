package com.baidu.channelapi;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class ChannelList extends BaiduChannelActionBase{
	
	//
	// list 
	//
	//
	public ChannelActionInfo.PPTListInfoResponse get_list(String page, String limit){
		ChannelActionInfo.PPTListInfoResponse ret = new ChannelActionInfo.PPTListInfoResponse();

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(BaiduChannelActionBase.Key_Method, Value_Method));
			params.add(new BasicNameValuePair(BaiduChannelActionBase.Key_AccessToken, getAccessToken()));

			if(Integer.parseInt(page) > -1){
				params.add(new BasicNameValuePair(Key_page, page));
			}

			if(Integer.parseInt(limit)> 0){
				params.add(new BasicNameValuePair(Key_limit, limit));
			}

			// build url
			String url = BaiduChannelActionBase.ChannelRequestUrl + "?" +buildParams(params);

			HttpPost httppost = new HttpPost(url);
			
	        //取得默认的HttpClient 
	      
	        HttpClient httpclient = new DefaultHttpClient(); 

	        //取得HttpResponse 

	        HttpResponse response;
			try {
				response = httpclient.execute(httppost);
				
		        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
		        	
					String strResult = EntityUtils.toString(response.getEntity());
					
					ret = parseListResponse(strResult);
		        	
					int a;
					a= 10;
		        }
		        		        
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return ret;
	}
	
	// value of method
	private final static String Value_Method = "get_list";
	
	// key : by
	private final static String Key_page = "page";
	
	// key : order
	private final static String Key_limit = "limit";

}
