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
		
		list.setAccessToken(getAccessToken());
				
		return list.get_list(page, limit);
	}
	
	//get pageNum api
	public int  getPageNum(String pptID){
		
		ChannelGetPageNum pageNum = new ChannelGetPageNum();
		
		pageNum.setAccessToken(getAccessToken());
						
		return Integer.parseInt(pageNum.getPageNum(pptID));
	}
	
	//pervious api
	public int pervious(String pptID,String curPageNum){
		
		ChannelPervious perv = new ChannelPervious();
		
		perv.setAccessToken(getAccessToken());
		
		return perv.perviuous(pptID, curPageNum);
	}
	
	//next api
	public int next(String pptID,String curPageNum){
		ChannelNext next = new ChannelNext();
		
		next.setAccessToken(getAccessToken());
		
		return next.next(pptID, curPageNum);		
	}
	
	//jump api
	public int jump(String pptID,String dest_page){
		
		ChannelJump jump = new ChannelJump();
		
		jump.setAccessToken(getAccessToken());
		
		return jump.jump(pptID, dest_page);		
	}
	
	//rest api
	public ChannelActionInfo.ChannelActionResponse reset(String pptID){
		
		ChannelReset pptReset = new ChannelReset();
		
		pptReset.setAccessToken(getAccessToken());
		
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
