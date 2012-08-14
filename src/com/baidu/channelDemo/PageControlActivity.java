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

	private TextView pageTotal = null;	
	private TextView curPage = null;
	private EditText jumpPageNum = null;
	
	private Button btnPerv = null;
	private Button btnNext = null;
	private Button btnJump = null;
	private Button btnSlide = null;	
	private Button btnBack = null;
	private int pageNum = 0;
	
	public Handler uiThreadHandler = new Handler(); 

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagecontrol);
        
        
        getPageNum();
        
        pptReset();
        
        pageTotal = (TextView)findViewById(R.id.btnpagetotal);
        jumpPageNum = (EditText)findViewById(R.id.editpagenum);
        curPage = (TextView)findViewById(R.id.pagenumshow);
        btnPerv = (Button)findViewById(R.id.btn_page_prev);
        btnNext = (Button)findViewById(R.id.btn_page_next);
        btnJump = (Button)findViewById(R.id.btn_page_jump);
        btnBack = (Button)findViewById(R.id.btnback);
        btnSlide = (Button)findViewById(R.id.btn_slide_control);
        
        
        btnPerv.setOnClickListener(new Button.OnClickListener(){
        	
            public void onClick(View v) {
            	
            	pagePerv();
            }
        });
        
        btnNext.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				pageNext();
			}
        	       	
        });
        
        btnJump.setOnClickListener(new Button.OnClickListener(){
        	
            public void onClick(View v) {
            	
            	pageJump();
            }
        });
        
        
        btnSlide.setOnClickListener(new Button.OnClickListener(){
        	
            public void onClick(View v) {
            	
            	Intent intent = new Intent();
            	intent.setClass(getApplicationContext(), SlideControlActivity.class);
            	startActivity(intent);
            }
        });
        
        btnBack.setOnClickListener(new Button.OnClickListener(){
        	
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
		
		jumpPageNum.setText(String.valueOf(BaseInfo.currentPage+1));
		curPage.setText("第"+ (BaseInfo.currentPage+1) +"页");	
		
		if((BaseInfo.currentPage+1)>1 &&(BaseInfo.currentPage+1)<BaseInfo.pageNum){
			
			btnNext.setEnabled(true);
			btnPerv.setEnabled(true);
		}
	}
	
	public void getPageNum(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
		
				BaiduChannelAPI  getPageNum = new BaiduChannelAPI();
		
				getPageNum.setAccessToken(BaseInfo.access_token);
		
				BaseInfo.pageNum = getPageNum.getPageNum(BaseInfo.pptId);
				
	    		uiThreadHandler.post(new Runnable(){
	    			
	    			public void run(){
	    				
	    				 pageTotal.setText("共"+BaseInfo.pageNum+"页");
	    				
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
	    				
	    				 btnPerv.setEnabled(false);
	    				 
	    				 curPage.setText("第"+ pageNum +"页");
	    				
	    			}
							
	    		});

			}
		});
		
		workThread.start();
		
	}
	
	public void pagePerv(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
				
				BaiduChannelAPI perv = new BaiduChannelAPI();
				perv.setAccessToken(BaseInfo.access_token);
				
				BaseInfo.currentPage = perv.pervious(BaseInfo.pptId, String.valueOf(BaseInfo.currentPage));
				
				pageNum = BaseInfo.currentPage + 1;
				
				
	    		uiThreadHandler.post(new Runnable(){
	    			
	    			public void run(){
	    				
	    				if(pageNum == 1){
	    					
	    					btnPerv.setEnabled(false);
	    				}
	    				
	    				btnNext.setEnabled(true);
	    				
	    				jumpPageNum.setText(String.valueOf(pageNum));
	    				 
	    				curPage.setText("第"+pageNum+"页");
	    				
	    			}
							
	    		});
			}
		});
		
		workThread.start();
		
	}
	
	public void pageJump(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
		
				BaiduChannelAPI jump = new BaiduChannelAPI();
				
				jump.setAccessToken(BaseInfo.access_token);
								
				int num = Integer.parseInt(jumpPageNum.getText().toString())-1;
									
				BaseInfo.currentPage  = jump.jump(BaseInfo.pptId, String.valueOf(num)) - 1;	
					
				pageNum = BaseInfo.currentPage + 1;
				
		    	uiThreadHandler.post(new Runnable(){
		    			
		    		public void run(){
		    			
		    				if(pageNum == 1){
		    					
		    					btnPerv.setEnabled(false);		    					
		    				}else{
		    					
		    					if(pageNum == BaseInfo.pageNum){
		    					
		    						btnNext.setEnabled(false);
		    					}else{
		    						
		    						btnPerv.setEnabled(true);
		    						btnNext.setEnabled(true);
		    					}
		    				}

		    				jumpPageNum.setText(String.valueOf(pageNum));
		    				curPage.setText("第"+ pageNum +"页");		    				
		    			}
								
		    		});
			}
		});
		
		workThread.start();
		
	}
	
	public void pageNext(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
		
				BaiduChannelAPI next = new BaiduChannelAPI();
				
				next.setAccessToken(BaseInfo.access_token);
				
				BaseInfo.currentPage  = next.next(BaseInfo.pptId, String.valueOf(BaseInfo.currentPage));	
								
				pageNum = BaseInfo.currentPage + 1;
								
	    		uiThreadHandler.post(new Runnable(){
	    			
	    			public void run(){
	    				
	    				btnPerv.setEnabled(true);
	    				
	    				if(pageNum == BaseInfo.pageNum){
	    					
	    					btnNext.setEnabled(false);
	    				}
	    				
	    				jumpPageNum.setText(String.valueOf(pageNum)); 
	    				curPage.setText("第"+ pageNum +"页");	    				
	    			}
							
	    		});
			}
		});
		
		workThread.start();
		
	}
	
}
