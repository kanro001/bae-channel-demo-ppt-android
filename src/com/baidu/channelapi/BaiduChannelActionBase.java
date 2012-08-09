package com.baidu.channelapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.channelapi.ChannelActionInfo.ChannelActionResponse;
import com.baidu.channelapi.ChannelActionInfo.PPTListInfoResponse;

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
	// send HTTP Request
	//
	protected ChannelRawHTTPResponse sendHttpRequest(HttpRequestBase request){
		// response
		ChannelRawHTTPResponse ret = new ChannelRawHTTPResponse();

		if(null != request){
			
			// create client
			HttpClient client = HttpClientFactory.makeHttpClient();

			if(null != client){

				for (int retries = 0; ret.response == null && retries < Max_Retries; ++retries) {
					/*
					 * The try/catch is a workaround for a bug in the HttpClient libraries. It should be returning null
					 * instead when an error occurs. Fixed in HttpClient 4.1, but we're stuck with this for now. See:
					 * http://code.google.com/p/android/issues/detail?id=5255
					 */
					try {
						ret.response = client.execute(request);
					} catch (NullPointerException e) {
						ret.message = e.getMessage();
					} catch (ClientProtocolException e) {
						ret.message = e.getMessage();
					} catch (IOException e) {
						ret.message = e.getMessage();
					}
					
					if(null == ret.response){
						try {
							Thread.sleep(1000 * (retries + 1));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							ret.message = e.getMessage();
						}
					}
				}
			}
		}

		return ret;
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
	
	
	protected ChannelActionInfo.ChannelActionResponse parseActionInfo(HttpResponse response){
		
		ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();
		
		try {
			HttpEntity resEntity = response.getEntity();
			String json = EntityUtils.toString(resEntity);
			
			ret = parseActionInfoByJson(json);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			ret.message = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ret.message = e.getMessage();
		}
		
		return ret;
	}
	
	
	protected ChannelActionInfo.ChannelActionResponse parseActionInfoByJson(String json){
		ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();
		if(null != json && json.length() > 0){
			try {
				JSONObject jo = new JSONObject(json);
				ret = parseActionInfoByJSONObject(jo);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			}
		}

		return ret;
	}
	
	
	 protected ChannelActionInfo.ChannelActionResponse parseActionInfoByJSONObject(JSONObject jo){
		 ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();
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
						
						if(jo.has(Key_CPage)){
							ret.curPage = jo.getInt(Key_CPage);
						}

						if(jo.has(Key_TPage)){
							ret.totalPage = jo.getInt(Key_TPage);
						}

					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ret.message = e.getMessage();
			}
		 return ret;
	 }
	 
	 
	 protected ChannelActionResponse parseActionResponse(HttpResponse response){
		 ChannelActionInfo.ChannelActionResponse ret = new ChannelActionInfo.ChannelActionResponse();
			if(null != response){
				try {
					HttpEntity resEntity = response.getEntity();
					String json = EntityUtils.toString(resEntity);

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
								JSONArray list = jo.getJSONArray(Key_Action_Responese);
								ChannelActionInfo.ChannelActionResponse info = parseActionInfoByJSONObject(list.getJSONObject(0));
							}
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
	
	
	/*
	 * parse list response
	 */
	protected ChannelActionInfo.PPTListInfoResponse parseListResponse(HttpResponse response){
		ChannelActionInfo.PPTListInfoResponse ret = new ChannelActionInfo.PPTListInfoResponse();
		
		if(null != response){
			try {
				HttpEntity resEntity = response.getEntity();
				String json = EntityUtils.toString(resEntity);

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
	final static String ChannelRequestUrl = "";
	
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
	
	
	// max retries times
	final static int Max_Retries = 6;
	
	// key : list
	final static String Key_Files_List = "detail";
	
	final static String Key_Action_Responese = "response_params";
	
	// the access token
	private String mbAccessToken = null;
}
