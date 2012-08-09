package com.baidu.channelDemo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class ControlActivity extends Activity implements OnGestureListener{
	
    public static final String ACTION_RESPONSE = "com.baidu.push.action.RESPONSE";
    /** Action：登录成功 */
    public static final String ACTION_LOGIN = "com.baidu.push.action.LOGIN";
    
	private ViewFlipper flipper;//ViewFlipper实例  
	private GestureDetector detector;//触摸监听实例
	
	private int pageCounts = 5;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);
        
        detector = new GestureDetector(this);//初始化触摸探测  
        flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper);//获得ViewFlipper实例  
        
        for(int index = 1;index<= pageCounts;index++){
        	
        	flipper.addView(addTextView("第"+index+"页"));//将View添加到flipper队列中  
        }
 
	}
	
	private View addTextView(String text) {  
        TextView tv = new TextView(this);  
        tv.setText(text);
        tv.setTextSize(30);
        tv.setTextColor(Color.BLACK);
        tv.setGravity(0x11);  
        return tv;  
    } 
	
	@Override  
    public boolean onTouchEvent(MotionEvent event) {  
        return this.detector.onTouchEvent(event);  
    } 
	
	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
	        float velocityY) {  
	    if (e1.getX() - e2.getX() > 120) {//如果是从右向左滑动  
	     //注册flipper的进出效果  
	        this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));  
	        this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));  
	        this.flipper.showNext();  
	        return true;  
	    } else if (e1.getX() - e2.getX() < -120) {//如果是从左向右滑动  
	        this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));  
	        this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));  
	        this.flipper.showPrevious();  
	        return true;  
	    }  
	    return false;  
	}

	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
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

}
