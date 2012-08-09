package com.baidu.channelapi;

import java.util.List;

/*
 * this class defines the response from PCS HTTP server
 */
public final class ChannelActionInfo {
	
	/*
	 * normal response
	 */
	public static class ChannelSimplefiedResponse {
		//0 means success
		public int error_code = -1;
		
		// status message if failed
		public String message = null;
	}

	public static class ChannelActionResponse {
		//0 means success
		public int error_code = -1;
		
		// status message if failed
		public String message = null;
		
		public String pptId = null;
		
		public int curPage = 0;
		
		public int totalPage = 0;
	}
	
	
	/*
	 * Server-returned data for Request line£ºGET /Create
	 */
	public static class ChannelPPTInfoResponse {

		// if upload successfully, 0 means success
		public int error_code = -1;
		
		// status message if failed
		public String message = null;
		
		public String pptId = null;
		
		public String pptName = null;
		
		public Long uTime = 0L;

	}
	
	public static class PPTListResponse{
		
		// if upload successfully, 0 means success
		public int error_code = -1;
		
		// status message if failed
		public String message = null;
		
		
		public PPTListInfoResponse listResponse;
		
	}
	
	
	/*
	 * list info
	 */
	public static class PPTListInfoResponse {

		// if upload successfully, 0 means success
		public int error_code = -1;
		
		// status message if failed
		public String message = null;
		
		public int pptNum = 0;

		// list info
		public List<ChannelPPTInfoResponse> list = null;
	}
	
	
}
