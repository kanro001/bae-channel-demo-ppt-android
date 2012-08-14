package com.baidu.channelDemo;

import com.baidu.channelapi.BaiduChannelAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PageControlActivity extends Activity{

	private TextView pagetotal = null;	
	private TextView cur_page = null;
	private EditText jump_page_num = null;
	
	private Button btn_perv = null;
	private Button btn_next = null;
	private Button btn_jump = null;
	private Button btn_slide = null;	
	private Button btn_back = null;
	private int pageNum = 0;
	
	public Handler uiThreadHandler = new Handler(); 

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagecontrol);
        
        get_pageNum();
        
        pptReset();

        
        pagetotal = (TextView)findViewById(R.id.btnpagetotal);
        jump_page_num = (EditText)findViewById(R.id.editpagenum);
        cur_page = (TextView)findViewById(R.id.pagenumshow);
        btn_perv = (Button)findViewById(R.id.btn_page_prev);
        btn_next = (Button)findViewById(R.id.btn_page_next);
        btn_jump = (Button)findViewById(R.id.btn_page_jump);
        btn_back = (Button)findViewById(R.id.btnback);
        btn_slide = (Button)findViewById(R.id.btn_slide_control);
        
        
        btn_perv.setOnClickListener(new Button.OnClickListener(){
        	
            public void onClick(View v) {
            	
            	perv();
            }
        });
        
        btn_next.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				next();
			}
        	       	
        });
        
        btn_jump.setOnClickListener(new Button.OnClickListener(){
        	
            public void onClick(View v) {
            	
            	jump();
            }
        });
        
        
        btn_slide.setOnClickListener(new Button.OnClickListener(){
        	
            public void onClick(View v) {
            	
            	Intent intent = new Intent();
            	intent.setClass(getApplicationContext(), SlideControlActivity.class);
            	startActivity(intent);
            }
        });
        
        btn_back.setOnClickListener(new Button.OnClickListener(){
        	
            public void onClick(View v) {
            	
            	Intent intent = new Intent();
            	intent.setClass(getApplicationContext(), PPTListActivity.class);
            	startActivity(intent);
            	
            	finish();
            }
        });

	}
			
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		jump_page_num.setText(String.valueOf(BaseInfo.currentPage+1));
		cur_page.setText("第"+ (BaseInfo.currentPage+1) +"页");	
		
		if((BaseInfo.currentPage+1)>1 &&(BaseInfo.currentPage+1)<BaseInfo.pageNum){
			
			btn_next.setEnabled(true);
			btn_perv.setEnabled(true);
		}
	}
	
	public void get_pageNum(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
		
				BaiduChannelAPI  getPageNum = new BaiduChannelAPI();
		
				getPageNum.setAccessToken(BaseInfo.access_token);
		
				BaseInfo.pageNum = getPageNum.getPageNum(BaseInfo.pptId);
				
	    		uiThreadHandler.post(new Runnable(){
	    			
	    			public void run(){
	    				
	    				 pagetotal.setText("共"+BaseInfo.pageNum+"页");
	    				
	    			}
							
	    		});
			}
		});
		
		workThread.start();
				
	}
	
	
	public void pptReset(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
				
				BaiduChannelAPI pptReset = new BaiduChannelAPI();
				
				pptReset.setAccessToken(BaseInfo.access_token);
				
				BaseInfo.currentPage = pptReset.reset(BaseInfo.pptId).curPage;
				
				pageNum = BaseInfo.currentPage + 1;
								
	    		uiThreadHandler.post(new Runnable(){
	    			
	    			public void run(){
	    				
	    				 btn_perv.setEnabled(false);
	    				 
	    				 cur_page.setText("第"+ pageNum +"页");
	    				
	    			}
							
	    		});

			}
		});
		
		workThread.start();
		
	}
	
	public void perv(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
				
				BaiduChannelAPI perv = new BaiduChannelAPI();
				perv.setAccessToken(BaseInfo.access_token);
				
				BaseInfo.currentPage = perv.pervious(BaseInfo.pptId, String.valueOf(BaseInfo.currentPage));
				
				pageNum = BaseInfo.currentPage + 1;
				
				
	    		uiThreadHandler.post(new Runnable(){
	    			
	    			public void run(){
	    				
	    				if(pageNum == 1){
	    					
	    					btn_perv.setEnabled(false);
	    				}
	    				
	    				btn_next.setEnabled(true);
	    				
	    				jump_page_num.setText(String.valueOf(pageNum));
	    				 
	    				cur_page.setText("第"+pageNum+"页");
	    				
	    			}
							
	    		});
			}
		});
		
		workThread.start();
		
	}
	
	public void jump(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
		
				BaiduChannelAPI jump = new BaiduChannelAPI();
				
				jump.setAccessToken(BaseInfo.access_token);
								
				int num = Integer.parseInt(jump_page_num.getText().toString())-1;
									
				BaseInfo.currentPage  = jump.jump(BaseInfo.pptId, String.valueOf(num)) - 1;	
					
				pageNum = BaseInfo.currentPage + 1;
				
		    	uiThreadHandler.post(new Runnable(){
		    			
		    		public void run(){
		    			
		    				if(pageNum == 1){
		    					
		    					btn_perv.setEnabled(false);		    					
		    				}else{
		    					
		    					if(pageNum == BaseInfo.pageNum){
		    					
		    						btn_next.setEnabled(false);
		    					}else{
		    						
		    						btn_perv.setEnabled(true);
		    						btn_next.setEnabled(true);
		    					}
		    				}

		    				jump_page_num.setText(String.valueOf(pageNum));
		    				 cur_page.setText("第"+ pageNum +"页");		    				
		    			}
								
		    		});
			}
		});
		
		workThread.start();
		
	}
	
	public void next(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
		
				BaiduChannelAPI next = new BaiduChannelAPI();
				
				next.setAccessToken(BaseInfo.access_token);
				
				BaseInfo.currentPage  = next.next(BaseInfo.pptId, String.valueOf(BaseInfo.currentPage));	
								
				pageNum = BaseInfo.currentPage + 1;
								
	    		uiThreadHandler.post(new Runnable(){
	    			
	    			public void run(){
	    				
	    				btn_perv.setEnabled(true);
	    				
	    				if(pageNum == BaseInfo.pageNum){
	    					
	    					btn_next.setEnabled(false);
	    				}
	    				
	    				jump_page_num.setText(String.valueOf(pageNum)); 
	    				cur_page.setText("第"+ pageNum +"页");	    				
	    			}
							
	    		});
			}
		});
		
		workThread.start();
		
	}
	
}
