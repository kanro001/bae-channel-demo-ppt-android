package com.baidu.channelapi;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

public class ChannelPervious extends BaiduChannelActionBase{
	
	//
	// pervious
	//
	public ChannelActionInfo.ChannelActionResponse perviuous(String id, String curPageNum){
		ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(BaiduChannelActionBase.Key_Method, Value_Method));
			params.add(new BasicNameValuePair(BaiduChannelActionBase.Key_AccessToken, getAccessToken()));
			params.add(new BasicNameValuePair(Key_slide_id,id ));
			params.add(new BasicNameValuePair(Key_cur_page, curPageNum));

			// build url
			String url = BaiduChannelActionBase.ChannelRequestUrl + "?" +buildParams(params);

			HttpGet httpget = new HttpGet(url);
			BaiduChannelActionBase.ChannelRawHTTPResponse response = sendHttpRequest(httpget);
			
			if(null != response){
				ret.message = response.message;
				
				if(null != response.response){
					ret = parseActionResponse(response.response);
				}
			}

		return ret;
	}
	
	// value of method
	private final static String Value_Method = "perv";
	
	// key 
	private final static String Key_slide_id = "slide_id";
	
	private final static String Key_cur_page = "cur_page";
	

}

