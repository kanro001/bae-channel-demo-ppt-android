package com.baidu.channelDemo;


import com.baidu.oauth2.BaiduOAuthViaDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ChannelDemoActivity extends Activity {
    /** Called when the activity is first created. */
    
    
    private Button login = null;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        login = (Button)findViewById(R.id.btnLogin);
        
        login.setOnClickListener(new Button.OnClickListener(){
        	
            public void onClick(View v) {
            	           	
            	if(BaseInfo.isOnline(getApplicationContext())){
            		
            		login();
            		           	
//                	Intent loginIntent = new Intent();
//                	loginIntent.setClass(getApplicationContext(), LoginActivity.class);
//                	startActivity(loginIntent);           		
            	}else{
            		Toast.makeText(getApplicationContext(), "请确定手机已联网！", Toast.LENGTH_SHORT).show();
            	}
            	
            }
        });
    }
    
    
    public void login(){    	
    	if(null != BaseInfo.access_token){ 
    		//If the access_token is not null, start ContentActivity
			Intent intent = new Intent();    				    						    				
			intent.setClass(getApplicationContext(), PPTListActivity.class); 				
			startActivity(intent); 
    	}else{    		
    		BaiduOAuthViaDialog bdOauth = new BaiduOAuthViaDialog(BaseInfo.app_key);
        	try {
        		//Start OAUTH dialog
        		bdOauth.startDialogAuth(ChannelDemoActivity.this, new String[]{"basic", "netdisk"}, new BaiduOAuthViaDialog.DialogListener(){
        			//Login successful 
        			public void onComplete(Bundle values) {
        				//Get access_token
        				BaseInfo.access_token = values.getString("access_token");
        				
        				Intent intent = new Intent();    				    						    				
        				intent.setClass(getApplicationContext(), PPTListActivity.class); 				
        				startActivity(intent);    				
        			}

        			// TODO: the error code need be redefined
        			@SuppressWarnings("unused")
    				public void onError(int error) {   				
        				Toast.makeText(getApplicationContext(), R.string.fail, Toast.LENGTH_SHORT).show();
        			}

        			public void onCancel() {   				
        				Toast.makeText(getApplicationContext(), R.string.back, Toast.LENGTH_SHORT).show();
        			}

        			public void onException(String arg0) {
        				Toast.makeText(getApplicationContext(), arg0, Toast.LENGTH_SHORT).show();
        			}
        		});
        	}catch (Exception e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
    		
    	}
    	    	
    }
           
}