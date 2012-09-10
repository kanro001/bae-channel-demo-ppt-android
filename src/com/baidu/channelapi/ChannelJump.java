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

import com.baidu.channelapi.ChannelActionInfo.ChannelActionResponse;

public class ChannelJump extends BaiduChannelActionBase{
	
	//
	// 
	//next
	//
	public int jump(String id, String dest_page){
		    
			ChannelActionResponse ret = new ChannelActionResponse();

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(BaiduChannelActionBase.Key_Method, Value_Method));
			params.add(new BasicNameValuePair(BaiduChannelActionBase.Key_AccessToken, getAccessToken()));
			params.add(new BasicNameValuePair(Key_slide_id,id ));
			params.add(new BasicNameValuePair(Key_dest_page, dest_page));

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
					
					ret = parseActionInfo(strResult);
		        	
		        }
		        		        
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return ret.curPage;
	}
	
	// value of method
	private final static String Value_Method = "jump";
	
	// key 
	private final static String Key_slide_id = "ppt_id";
	
	private final static String Key_dest_page = "dest_page";
	

}
