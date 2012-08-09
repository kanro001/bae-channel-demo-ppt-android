package com.baidu.channelDemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


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
            	
            	Intent loginIntent = new Intent();
            	loginIntent.setClass(getApplicationContext(), LoginActivity.class);
            	startActivity(loginIntent);
            	
            }
        });
    }
           
}