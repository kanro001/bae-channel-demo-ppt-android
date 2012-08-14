package com.baidu.channelDemo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.baidu.channelapi.BaiduChannelAPI;
import com.baidu.channelapi.ChannelActionInfo.ChannelPPTInfoResponse;
import com.baidu.channelapi.ChannelActionInfo.PPTListInfoResponse;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PPTListActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	public Handler uiThreadHandler = new Handler(); 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pptlist);
        
        LoginActivity.getAccessToken(getApplicationContext());
        
        list();
        
   }

   public void list(){
	   	   
	   
       if (null != BaseInfo.access_token){
       	
   			Thread workThread = new Thread(new Runnable(){
   			
				public void run() {
					
		    		BaiduChannelAPI api = new BaiduChannelAPI();
		    		api.setAccessToken(BaseInfo.access_token );		    		
		    		
		    		//Use list api
		    		final PPTListInfoResponse listResponse = api.get_list("0","20");
		    		if(listResponse.error_code == 0){
		    			
		    			uiThreadHandler.post(new Runnable(){
			    			
			    			public void run(){		    				
			    			
			    				ArrayList<HashMap<String, String>> pptList =new ArrayList<HashMap<String,String>>();   
			    						    				

			    				if( ! listResponse.list.isEmpty()){
			    					   			    	            
				    	            for(Iterator<ChannelPPTInfoResponse> i = listResponse.list.iterator(); i.hasNext();){
				    	            	
				    	            	HashMap<String, String> map =new HashMap<String, String>();
				    	            				    	            	
				    	            	ChannelPPTInfoResponse info = i.next();
				    	            	
				    	            	//Get the file name 			    	            	
				    	         	    BaseInfo.pptId = info.pptId;			    	         	   
			  			 			    BaseInfo.pptName = info.pptName;
			  			 			    
			  			 			    BaseInfo.contentMap.put(info.pptName,info.pptId);
			  			 			    
			  			 			    BaseInfo.pptContentList.add(BaseInfo.contentMap);
				    
				    	         	    map.put("file_name", BaseInfo.pptName);			    	            				    	   			    	            	
				    	         	    //Add item to content list	
				    	         	    pptList.add(map); 	            	
				    	         	    
				    	            }			    	               
				    	        }else{			    	        	
				    	        	//Clear content list
				    	        	pptList.clear();
			    					Toast.makeText(getApplicationContext(), "您的文件夹为空！", Toast.LENGTH_SHORT).show();		    					
			    				}    
			    				
				    	         SimpleAdapter listAdapter =new SimpleAdapter(getApplicationContext(), pptList, R.layout.content, new String[]{"file_name"}, new int[]{R.id.file_name});   			    	        
				    	         //Set listview to display content
				    	         setListAdapter(listAdapter);		    	         
				    	         Toast.makeText(getApplicationContext(), R.string.refresh, Toast.LENGTH_SHORT).show();

			    			}
			    		});			    			
		    		}else{
		    			
		    			Toast.makeText(getApplicationContext(),listResponse.message, Toast.LENGTH_SHORT).show();
		    		}
		    				    			    		
				}
			});
			 
   		workThread.start();

       } 	   	   
   }
   
   //Set item response function
   @Override
   protected void onListItemClick(ListView l, View v, int position, long id){
   	
   	super.onListItemClick(l, v, position, id);
   	
   	//Get filename form item
   	 BaseInfo.pptName = l.getAdapter().getItem(position).toString();
   	 
   	 BaseInfo.pptName = BaseInfo.pptName.substring(BaseInfo.pptName.lastIndexOf("=")+1,BaseInfo.pptName.lastIndexOf("}"));
   	 
   	 BaseInfo.pptId = BaseInfo.pptContentList.get(position).get(BaseInfo.pptName);
   	 
   	 Intent intent = new Intent();
   	 
   	 intent.setClass(PPTListActivity.this, PageControlActivity.class);
   	 
   	 startActivity(intent);
   	 
   	 finish();
   
   }
    
}
