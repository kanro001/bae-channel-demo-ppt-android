package com.baidu.channelapi;


//
// the HTTP request in this class is handled synchronously
// if user uses this class in UI thread, the UI thread will definitely be blocked, in order to avoid blocking UI, we suggest use this class in a work thread
//
public class BaiduChannelAPI {
		
	//
	// list info
	//
	public ChannelActionInfo.PPTListInfoResponse get_list(String page,String limit){
		
		ChannelList list = new ChannelList();
		
		list.getAccessToken();
				
		return list.get_list(page, limit);
	}
	
	//get pageNum api
	public int  getPageNum(String pptID){
		
		ChannelGetPageNum pageNum = new ChannelGetPageNum();
		
		pageNum.getAccessToken();
						
		return Integer.parseInt(pageNum.getPageNum(pptID));
	}
	
	//pervious api
	public int pervious(String pptID,String curPageNum){
		
		ChannelPervious perv = new ChannelPervious();
		
		perv.getAccessToken();
		
		return perv.perviuous(pptID, curPageNum);
	}
	
	//next api
	public int next(String pptID,String curPageNum){
		ChannelNext next = new ChannelNext();
		
		next.getAccessToken();
		
		return next.next(pptID, curPageNum);		
	}
	
	//jump api
	public int jump(String pptID,String dest_page){
		
		ChannelNext jump = new ChannelNext();
		
		jump.getAccessToken();
		
		return jump.next(pptID, dest_page);		
	}
	
	//rest api
	public ChannelActionInfo.ChannelActionResponse reset(String pptID){
		
		ChannelReset pptReset = new ChannelReset();
		
		pptReset.getAccessToken();
		
		return pptReset.reset(pptID);
		
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
