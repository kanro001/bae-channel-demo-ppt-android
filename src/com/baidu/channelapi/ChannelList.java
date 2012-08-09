package com.baidu.channelapi;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;



public class ChannelList extends BaiduChannelActionBase{
	
	//
	// list folder
	//
	// @ by:  time   name   size
	// @ order : asc dsc
	//
	public ChannelActionInfo.PPTListInfoResponse list(String page, String limit){
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

			HttpGet httpget = new HttpGet(url);
			BaiduChannelActionBase.ChannelRawHTTPResponse response = sendHttpRequest(httpget);
			
			if(null != response){
				ret.message = response.message;
				
				if(null != response.response){
					ret = parseListResponse(response.response);
				}
			}

		return ret;
	}
	
	// value of method
	private final static String Value_Method = "list";
	
	// key : by
	private final static String Key_page = "page";
	
	// key : order
	private final static String Key_limit = "limit";

}
