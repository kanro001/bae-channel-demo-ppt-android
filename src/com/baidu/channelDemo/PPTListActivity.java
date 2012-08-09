package com.baidu.channelDemo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PPTListActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	public Handler uiThreadHandler = new Handler(); 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pptlist);
        
        list();
        
   }

   public void list(){
	   
//       if (null != BaseInfo.access_token){
//       	
//   		Thread workThread = new Thread(new Runnable(){
//   			
//				public void run() {
//					
//		    		BaiduPCSAPI api = new BaiduPCSAPI();
//		    		api.setAccessToken(BaseInfo.access_token );
//		    		
//		    		//The path to  file storage on the cloud
//		    		String path = BaseInfo.bdRootPath;
//		    		
//		    		//Use list api
//		    		final PCSActionInfo.PCSListInfoResponse listResponse = api.list(path, "time", "desc");
//		    				    		
//		    		uiThreadHandler.post(new Runnable(){
//		    			
//		    			public void run(){		    				
//		    			
//		    				ArrayList<HashMap<String, String>> pptList =new ArrayList<HashMap<String,String>>();   
//		    						    				
//
//		    				if( ! listResponse.list.isEmpty()){
//		    					   			    	            
//			    	            for(Iterator<PCSFileInfoResponse> i = listResponse.list.iterator(); i.hasNext();){
//			    	            	
//			    	            	HashMap<String, String> map =new HashMap<String, String>();
//			    	            				    	            	
//			    	            	PCSFileInfoResponse info = i.next();
//			    	            	
//			    	            	//Get the file name 			    	            	
//			    	         	    String path = info.path;			    	         	    
//			    	         	    String fileName = path.substring(BaseInfo.bdRootPath.length(),path.lastIndexOf("."));
//			    	         	    String fileType = path.substring(path.lastIndexOf(".")+1,info.path.length());
//		  			 			    
//			    	         	    if(fileType.equals("ppt") || fileType.equals("pptx")){
//			    	         	    	map.put("file_name", fileName);			    	            				    	   			    	            	
//			    	         	    	//Add item to content list	
//			    	         	    	pptList.add(map); 	            	
////			    	            		BaseInfo.fileNameList.add(fileName);
//			    	         	    }
//			    	         	    
//		    	         	    	map.put("file_name", fileName);			    	            				    	   			    	            	
//		    	         	    	//Add item to content list	
//		    	         	    	pptList.add(map); 
//			    	            }			    	               
//			    	        }else{			    	        	
//			    	        	//Clear content list
//			    	        	pptList.clear();
//		    					Toast.makeText(getApplicationContext(), "您的文件夹为空！", Toast.LENGTH_SHORT).show();		    					
//		    				}    
//		    				
//			    	         SimpleAdapter listAdapter =new SimpleAdapter(getApplicationContext(), pptList, R.layout.content, new String[]{"file_name"}, new int[]{R.id.file_name});   			    	        
//			    	         //Set listview to display content
//			    	         setListAdapter(listAdapter);		    	         
//			    	         Toast.makeText(getApplicationContext(), R.string.refresh, Toast.LENGTH_SHORT).show();
//
//		    			}
//		    		});	
//		    		
//				}
//			});
//			 
//   		workThread.start();
//
//       } 	   	   
   }
   
   //Set item response function
   @Override
   protected void onListItemClick(ListView l, View v, int position, long id){
   	
   	super.onListItemClick(l, v, position, id);
   	
   	//Get filename form item
   	 BaseInfo.fileTitle = l.getAdapter().getItem(position).toString();
   	 
   	 Intent intent = new Intent();
   	 
   	 intent.setClass(getApplicationContext(), ControlActivity.class);
   	 
   	 startActivity(intent);
   
   }
    
}
