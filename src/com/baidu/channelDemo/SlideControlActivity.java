package com.baidu.channelDemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.baidu.channelapi.BaiduChannelAPI;

public class SlideControlActivity extends Activity implements OnTouchListener, OnGestureListener{
	
	private ViewFlipper flipper;//ViewFlipper实例  
	private GestureDetector detector;//触摸监听实例
	private int index = 0;
	
	public Handler uiThreadHandler = new Handler(); 

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidecontrol);

        detector = new GestureDetector(this);//初始化触摸探测  
        flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper);//获得ViewFlipper实例  
        
        index = BaseInfo.currentPage + 1;
        
//	    for(int index = 1;index <= BaseInfo.pageNum;index++){
	        	
	    flipper.addView(addTextView("第"+index+"页"));//将View添加到flipper队列中  
//	     }
	}
	
	private View addTextView(String text) {  
        TextView tv = new TextView(this);  
        tv.setText(text);
        tv.setTextSize(30);
        tv.setTextColor(Color.BLACK);
        tv.setGravity(0x11);  
        return tv;  
    } 
	
	
	public void perv(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
				
				BaiduChannelAPI perv = new BaiduChannelAPI();
				perv.setAccessToken(BaseInfo.access_token);		
				BaseInfo.currentPage = perv.pervious(BaseInfo.pptId, String.valueOf(BaseInfo.currentPage));
			
			}
		});
		
		workThread.start();
		
	}
	
	public void jump(){
		
		Thread workThread = new Thread(new Runnable(){
   			
			public void run() {
		
				BaiduChannelAPI jump = new BaiduChannelAPI();
				
				jump.setAccessToken(BaseInfo.access_token);
				
				BaseInfo.currentPage  = jump.jump(BaseInfo.pptId, String.valueOf(BaseInfo.currentPage));	
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
			}
		});
		
		workThread.start();
		
	}
	
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
	        float velocityY) {  
	    if (e1.getX() - e2.getX() > 120) {//如果是从右向左滑动  
	     //注册flipper的进出效果  
	        this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));  
	        this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
	        if((index+1) <= BaseInfo.pageNum){
	        	flipper.addView(addTextView("第"+(index+1)+"页"));
	        	this.flipper.showNext(); 
	        	index = index + 1;
		        flipper.removeViewAt(0);
	        	next();
	        }else{
	        	Toast.makeText(getApplicationContext(), "最后一页", Toast.LENGTH_SHORT).show();
	        }
	        
	        return true;  
	    } else if (e1.getX() - e2.getX() < -120) {//如果是从左向右滑动  
	        this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));  
	        this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));  
	        if((index-1) >= 1){
	        	flipper.addView(addTextView("第"+(index-1)+"页"));
		        this.flipper.showPrevious();
		        index = index - 1;
		        flipper.removeViewAt(0);
		        perv();		        
	        }else{
	        	Toast.makeText(getApplicationContext(), "已是第一页", Toast.LENGTH_SHORT).show();
	        }
	        
	        return true;  
	    }  
	    return false;  
	}
	
	
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override  
    public boolean onTouchEvent(MotionEvent event) {  
        return this.detector.onTouchEvent(event);  
    }

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	} 
	
	
}

