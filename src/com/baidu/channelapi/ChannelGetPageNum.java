package com.baidu.channelapi;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

public class ChannelGetPageNum extends BaiduChannelActionBase{
	
	//
	// list folder
	//
	// @ by:  time   name   size
	// @ order : asc dsc
	//
	public ChannelActionInfo.ChannelActionResponse getPageNum(String id){
		ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(BaiduChannelActionBase.Key_Method, Value_Method));
			params.add(new BasicNameValuePair(BaiduChannelActionBase.Key_AccessToken, getAccessToken()));
			params.add(new BasicNameValuePair(Key_slide_id,id ));

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
	private final static String Value_Method = "get_page_num";
	
	// key 
	private final static String Key_slide_id = "slide_id";
	

}
