package com.baidu.channelapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


class BaiduChannelActionBase {
	//
	// set the access token
	//
	public void setAccessToken(String token){
		mbAccessToken = token;
	}
	
	//
	// get the access token
	//
	public String getAccessToken(){
		return mbAccessToken;
	}

	
	//
	// build url
	//
	protected String buildParams(List<NameValuePair> urlParams){
		
		String ret = null;
		
		if(null != urlParams && urlParams.size() > 0){
			
			try {
				HttpEntity paramsEntity = new UrlEncodedFormEntity(urlParams, "utf8");
				ret = EntityUtils.toString(paramsEntity);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	
	protected String parsePageNum(String json){
		
		ChannelActionInfo.PPTPageNumResponse ret = new ChannelActionInfo.PPTPageNumResponse();
		
		try {
			
			JSONObject pageNum;
			
			try {
				
				pageNum = new JSONObject(json);
			
			if(null != pageNum){
				
				if(pageNum.has(Key_ErrorCode)){ // get error, failed to upload piece
					ret.error_code = pageNum.getInt(Key_ErrorCode);

					if(pageNum.has(Key_ErrorMessage)){
						ret.message = pageNum.getString(Key_ErrorMessage);
					}
				}
			 else{ // success, we need to parse the parameters
					ret.error_code = 0;

					if(pageNum.has(Key_Action_Responese)){
						
						JSONObject pageCount = pageNum.getJSONObject(Key_Action_Responese); 
						
						if(pageCount.has(Key_PageNum)){
							
							ret.pageNum = pageCount.getString(Key_PageNum);
						}
					}
			 	}	
			 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			ret.message = e.getMessage();
		}
		
		return ret.pageNum;
	}
	
		
	/*
	 * parse the response for ppt infomation
	 */
	protected ChannelActionInfo.ChannelPPTInfoResponse parsePPTInfo(HttpResponse response){
		
		ChannelActionInfo.ChannelPPTInfoResponse ret = new ChannelActionInfo.ChannelPPTInfoResponse();
		
		try {
			HttpEntity resEntity = response.getEntity();
			String json = EntityUtils.toString(resEntity);
			
			ret = parsePPTInfoByJson(json);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			ret.message = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ret.message = e.getMessage();
		}
		
		return ret;
	}
	
	
	
	/*
	 * parse json
	 */
	protected ChannelActionInfo.ChannelPPTInfoResponse parsePPTInfoByJson(String json){
		ChannelActionInfo.ChannelPPTInfoResponse ret = new ChannelActionInfo.ChannelPPTInfoResponse();
		if(null != json && json.length() > 0){
			try {
				JSONObject jo = new JSONObject(json);
				ret = parsePPTInfoByJSONObject(jo);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			}
		}

		return ret;
	}
	
	/*
	 * parse file info based on JSONObject
	 */
	 protected ChannelActionInfo.ChannelPPTInfoResponse parsePPTInfoByJSONObject(JSONObject jo){
		 ChannelActionInfo.ChannelPPTInfoResponse ret = new ChannelActionInfo.ChannelPPTInfoResponse();
			try {
				 if(null != jo){
					if(jo.has(Key_ErrorCode)){ // get error, failed to upload piece
						ret.error_code = jo.getInt(Key_ErrorCode);

						if(jo.has(Key_ErrorMessage)){
							ret.message = jo.getString(Key_ErrorMessage);
						}
					}
				 else{ // success, we need to parse the parameters
						ret.error_code = 0;

						if(jo.has(Key_PPTID)){
							ret.pptId = jo.getString(Key_PPTID);
						}
						
						if(jo.has(Key_PPTNAME)){
							ret.pptName = jo.getString(Key_PPTNAME);
						}

						if(jo.has(Key_UTIME)){
							ret.uTime = jo.getLong(Key_UTIME);
						}

					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			}
		 return ret;
	 }
	 
	 
	
	/*
	 * parse simplefied response
	 */
	protected ChannelActionInfo.ChannelSimplefiedResponse parseSimplefiedResponse(HttpResponse response){

		ChannelActionInfo.ChannelSimplefiedResponse ret = new ChannelActionInfo.ChannelSimplefiedResponse();
		
		if(null != response){
			try {
				String json = EntityUtils.toString(response.getEntity());
				JSONObject jo = new JSONObject(json);

				if(null != jo){

					if(jo.has(Key_ErrorCode)){ // get error, failed to upload piece
						ret.error_code = jo.getInt(Key_ErrorCode);

						if(jo.has(Key_ErrorMessage)){
							ret.message = jo.getString(Key_ErrorMessage);
						}
					}
					else{
						ret.error_code = 0;
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			}
		}
		
		return ret;
	}
	
	protected ChannelActionInfo.ChannelActionResponse parseActionInfo(String json){
		
		ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();
		
		if(null != json){
			try {

				JSONObject jo = new JSONObject(json);

				if(null != jo){
					if(jo.has(Key_ErrorCode)){ // get error, failed to upload piece
						ret.error_code = jo.getInt(Key_ErrorCode);

						if(jo.has(Key_ErrorMessage)){
							ret.message = jo.getString(Key_ErrorMessage);
						}
					}else{
						ret.error_code = 0;
						
						if(jo.has(Key_Action_Responese)){
							
							JSONObject response = jo.getJSONObject(Key_Action_Responese);
							
							if(response.has(Key_PPTID)){
								ret.pptId = response.getString(Key_PPTID);
							}
							
							if(response.has(Key_CPage)){
								ret.curPage = response.getInt(Key_CPage);
							}

							if(response.has(Key_TPage)){
								ret.totalPage = response.getInt(Key_TPage);
							}
							
						}
					}
				}
			}catch (ParseException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			}
			
		}
		
		
		return ret;
	}
	
	
	/*
	 * parse list response
	 */
	protected ChannelActionInfo.PPTListInfoResponse parseListResponse(String json){
		ChannelActionInfo.PPTListInfoResponse ret = new ChannelActionInfo.PPTListInfoResponse();
		
		if(null != json){
			try {

				JSONObject jo = new JSONObject(json);

				if(null != jo){
					if(jo.has(Key_ErrorCode)){ // get error, failed to upload piece
						ret.error_code = jo.getInt(Key_ErrorCode);

						if(jo.has(Key_ErrorMessage)){
							ret.message = jo.getString(Key_ErrorMessage);
						}
					}else{
						ret.error_code = 0;
						if(jo.has(Key_Action_Responese)){
							JSONObject Responslist = jo.getJSONObject(Key_Action_Responese);
							if(Responslist.has("total_num")){
								ret.pptNum = Integer.parseInt(Responslist.getString("total_num"));
							}
							if(Responslist.has(Key_Files_List)){							
							
								JSONArray list = Responslist.getJSONArray(Key_Files_List);
								ret.list = new ArrayList<ChannelActionInfo.ChannelPPTInfoResponse>();
							
								for(int i = 0; i < list.length(); ++i){
									ChannelActionInfo.ChannelPPTInfoResponse info = parsePPTInfoByJSONObject(list.getJSONObject(i));
									ret.list.add(info);
								}
							}
							
						}
						
					}
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			}
		}
		
		return ret;
	}

	/*
	 * the data structure of quota, including the space info
	 */
	protected static class ChannelRawHTTPResponse {		
		// the response
		public HttpResponse response = null;
		
		// status message if failed
		public String message = null;
	}
	
	// the request url
	final static String ChannelRequestUrl = "http://innerslide.offline.bae.baidu.com/rest/2.0/slideshow/slideshow";
	
	// the key of method
	final static String Key_Method = "method";
	
	// the key of access token
	final static String Key_AccessToken = "access_token";
	
	// key of error code
	final static String Key_ErrorCode = "error_code";
	
	// key of error message
	final static String Key_ErrorMessage = "error_msg";
	
	
	// key of param
	final static String Key_Param = "param";
	
	
	// key 
	final static String Key_PPTID = "slide_id";
	
	// key 
	final static String Key_PPTNAME = "ppt_name";
	
	// key : 
	final static String Key_UTIME = "upload_time";
	
	final static String Key_CPage = "cur_page";
	
	final static String Key_TPage = "total";
	
	final static String Key_PageNum = "page_num";
	
	
	// max retries times
	final static int Max_Retries = 6;
	
	// key : list
	final static String Key_Files_List = "detail";
	
	final static String Key_Action_Responese = "response_params";
	
	// the access token
	private String mbAccessToken = null;
}
