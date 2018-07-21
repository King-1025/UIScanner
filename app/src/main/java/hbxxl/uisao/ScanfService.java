package hbxxl.uisao;

import android.accessibilityservice.*;
import android.os.*;
import android.util.*;
import android.view.accessibility.*;
import hbxxl.uisao.scanner.*;
import java.util.*;
import hbxxl.uisao.controller.*;
import android.widget.*;

public class ScanfService extends AccessibilityService
{
    private HandlerThread task_line;
	private Handler worker;
	
	private int live_task_size=0;
	private final static int MAX_TASK_SIZE=10;
	private final static String TASK_LINE_FLAG="TASK_LINE_THREAD";
	private final static String TAG="ScanfService";
	
	public final static int SUBMIT_NEW_TASK_REQUEST=0x100;
	public final static int CANCEL_ALL_TASK_REQUEST=0x101;
	public final static int TASK_START=0x102;
	public final static int TASK_END=0x103;
	private FloatWindowController fc;
	@Override
	protected void onServiceConnected()
	{
		// TODO: Implement this method
		super.onServiceConnected();
		task_line=new HandlerThread(TASK_LINE_FLAG);
		task_line.start();
		Log.i(TAG,"task_line start.");
		worker=new Handler(task_line.getLooper()){
			public void handleMessage(Message msg){
				switch(msg.what){
					case SUBMIT_NEW_TASK_REQUEST:
						if(live_task_size<MAX_TASK_SIZE)
						{
							Task t=new Task(worker,new ViewScanner(getRootInActiveWindow()),Task.TYPE_SCANF);
							t.start();
							Log.i(TAG,"new task id:"+t.getId()+" priority:"+t.getPriority());
						}else{
							Toast.makeText(ScanfService.this,"task line is full! "+live_task_size,Toast.LENGTH_SHORT).show();
							Log.w(TAG,"task line is full! "+live_task_size);
						}
						break;
					case CANCEL_ALL_TASK_REQUEST:
						worker.removeMessages(SUBMIT_NEW_TASK_REQUEST);
						break;
					case TASK_START:
                        live_task_size++;
						fc.updateNotify(""+live_task_size);
						break;
					case TASK_END:
                        live_task_size--;
						if(live_task_size<=0){
						    fc.pause();
						}else{
							fc.updateNotify(""+live_task_size);
						}
						break;
				}
			}
		};
		fc=new FloatWindowController(this,worker);
		fc.show();
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onInterrupt()
	{
		// TODO: Implement this method
		finish();
	}

	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		finish();
	}
	
	public void finish(){
		fc.destory();
		task_line.quitSafely();
	}
}
