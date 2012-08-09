package com.baidu.channelapi;

import java.util.ArrayList;
import java.util.List;

//
// the HTTP request in this class is handled synchronously
// if user uses this class in UI thread, the UI thread will definitely be blocked, in order to avoid blocking UI, we suggest use this class in a work thread
//
public class BaiduChannelAPI {
		
	//
	// list info
	//
	public ChannelActionInfo.PPTListInfoResponse list(){
		
		ChannelActionInfo.PPTListInfoResponse ret = new ChannelActionInfo.PPTListInfoResponse();
		
		
		return ret;
	}
	
	//get pageNum api
	public int  getPageNum(){
		int pageNum = 0 ;
		
		return pageNum;
	}
	
	//pervious api
	public ChannelActionInfo.ChannelActionResponse pervious(){
		ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();
		
		
		
		return ret;
	}
	
	//next api
	public ChannelActionInfo.ChannelActionResponse next(){
		ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();
		
		return ret;		
	}
	
	//rest api
	public ChannelActionInfo.ChannelActionResponse reset(){
		ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();
		
		return ret;
		
	}
	
	//set access_token
	public void setAccessToken(String token){
		mbAccessToken = token;
	}
	//get access_token
	public String getAccessToken(){
		
		return mbAccessToken;
	}
	

	// record the access token
	private String mbAccessToken = null;
}
