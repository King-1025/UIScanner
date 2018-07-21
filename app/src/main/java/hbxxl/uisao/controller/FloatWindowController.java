package hbxxl.uisao.controller;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.WindowManager.*;
import android.widget.*;
import hbxxl.uisao.*;
import android.os.*;

public class FloatWindowController implements OnClickListener,OnLongClickListener,OnTouchListener
{
	private Context context;
	private Handler worker;
	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	private LayoutInflater layoutInflater;
	private int window_width;
	private int window_height;
	private View main_view;
	private ImageView outer;
	private ImageView inner;
	private TextView notify;
	private int style;
	private final static int STYLE0=0x100;
	private final static int STYLE1=0x101;
	private final static int STYLE2=0x102;
	private final static int STYLE3=0x104;
	private boolean isWorking;
    private int flag;
	private final static String TAG="FloatWindowController";
	private float startTouchX;
	private float startTouchY;
	private Handler ui;
	private final static int UPDATE_TEXT_VIEW=0x200;
	private final static int WORKING_STATUS_IN=0x201;
	private final static int WORKING_STATUS_OUT=0x202;
	public FloatWindowController(Context context, Handler worker)
	{
		this.context = context;
		this.worker = worker;
		windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		window_width=windowManager.getDefaultDisplay().getWidth();
	    window_height=windowManager.getDefaultDisplay().getHeight();
		layoutParams=new WindowManager.LayoutParams();
		layoutParams.type=WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW+3;
		layoutParams.format=PixelFormat.RGBA_8888;
		layoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		layoutParams.width=LayoutParams.WRAP_CONTENT;
		layoutParams.height=LayoutParams.WRAP_CONTENT;
		layoutParams.x=-window_width/2;
		layoutInflater=LayoutInflater.from(context);
		main_view=layoutInflater.inflate(R.layout.controll_bar,null);
		inner=(ImageView) main_view.findViewById(R.id.controllbarImageView0);
		outer=(ImageView) main_view.findViewById(R.id.controllbarImageView1);
		notify=(TextView) main_view.findViewById(R.id.controllbarTextView1);
		inner.setOnClickListener(this);
		inner.setOnLongClickListener(this);
		inner.setOnTouchListener(this);
		style=STYLE0;
		isWorking=false;
		ui=new Handler(){
			public void handleMessage(Message msg){
				 switch(msg.what){
					 case WORKING_STATUS_IN:
						 flag=style;
						 style=STYLE3;
						 setStyle(style);
						 break;
					 case WORKING_STATUS_OUT:
						 style=flag;
						 setStyle(style);
						 break;
					 case UPDATE_TEXT_VIEW:
						 notify.setText((String)msg.obj);
						 break;
				 }
			}
		};
	}
	
	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		if(worker==null){
			Toast.makeText(context,"sorry,worker is null!",Toast.LENGTH_SHORT).show();
			Log.e(TAG,"worker is null!");
			return;
		}
       if(!isWorking){
		    start();
		}
		worker.sendEmptyMessage(ScanfService.SUBMIT_NEW_TASK_REQUEST);
		//Toast.makeText(context,"click!",1000).show();
	}

	@Override
	public boolean onLongClick(View p1)
	{
		// TODO: Implement this method
		if(isWorking){
			 pause();
		}else{
		    if(style>=0x102||style<0x100){
			    style=0x100;
		     }else{
			     style+=0x001;
		     }
		     setStyle(style);
	    }
		return true;
	}
	@Override
	public boolean onTouch(View p1, MotionEvent p2)
	{
		// TODO: Implement this method
		switch(p2.getAction()){
			case MotionEvent.ACTION_DOWN:
				startTouchX=  p2.getRawX()-layoutParams.x; 
				startTouchY=  p2.getRawY()-layoutParams.y;
				break;
			case MotionEvent.ACTION_MOVE:
				layoutParams.x= (int)(p2.getRawX()-startTouchX);
				layoutParams.y= (int)(p2.getRawY()-startTouchY);
				windowManager.updateViewLayout(main_view, layoutParams); 
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return false;
	}
	
	public void show(){
		setStyle(style);
		windowManager.addView(main_view,layoutParams);
	}
	
	public void hide(){
		windowManager.removeView(main_view);
	}
	public void updateNotify(String p1){
		Message msg=ui.obtainMessage();
		msg.what=UPDATE_TEXT_VIEW;
		msg.obj=p1;
		ui.sendMessage(msg);
	}
	public void start(){
		isWorking=true;
		ui.sendEmptyMessage(WORKING_STATUS_IN);
	}
	public void pause(){
		isWorking=false;
		ui.sendEmptyMessage(WORKING_STATUS_OUT);
		worker.sendEmptyMessage(ScanfService.CANCEL_ALL_TASK_REQUEST);
	}
	public void destory(){
		hide();
		ui.removeMessages(UPDATE_TEXT_VIEW);
		ui.removeMessages(WORKING_STATUS_IN);
		ui.removeMessages(WORKING_STATUS_OUT);
	}
	private void setStyle(int style){
		switch(style){
			case STYLE0:
				inner.setImageResource(R.drawable.selector0);
				outer.setImageResource(R.drawable.ring_bg0);
				notify.setVisibility(View.INVISIBLE);
				break;
			case STYLE1:
				inner.setImageResource(R.drawable.selector1);
				outer.setImageResource(R.drawable.ring_bg1);
				notify.setVisibility(View.INVISIBLE);
				break;
			case STYLE2:
				inner.setImageResource(R.drawable.selector2);
				outer.setImageResource(R.drawable.ring_bg2);
				notify.setVisibility(View.INVISIBLE);
				break;
			case STYLE3:
				inner.setImageResource(R.drawable.circle_anim);
				outer.setImageResource(R.drawable.ring_anim);
				notify.setVisibility(View.VISIBLE);
				break;
			default:Log.w(TAG,"style:"+style+" is no found!");
			    break;
		}
	}
	
}
