package hbxxl.uisao;
import android.os.*;
import android.util.*;
import hbxxl.uisao.scanner.*;

public class Task extends Thread
{
	private ViewScanner vs;
	private int type;
    private Handler worker;
	
	private final static String TAG="Task";
	public final static int TYPE_SCANF=0x100;
	public final static int TYPE_SAVE_HTML=0x101;
	public final static int TYPE_SAVE_XML=0x102;

	public Task(Handler worker,ViewScanner vs,int type)
	{
		this.type = type;
		this.vs = vs;
		this.worker=worker;
	}
	
	@Override
	public void run()
	{
		// TODO: Implement this method
		super.run();
		if(vs==null||worker==null){
			Log.e(TAG,"task is null!");
		}else{
		  worker.sendEmptyMessage(ScanfService.TASK_START);
		   switch(type){
			   case TYPE_SCANF:
				  /* try
				   {
					   Thread.sleep(3000);
					   Log.i(TAG,"thread id:"+Thread.currentThread().getId()+" will sleep 3000ms.");
				   }
				   catch (InterruptedException e)
				   {e.printStackTrace();}*/
				   vs.scan();
				  break;
			   case TYPE_SAVE_HTML:
                  Log.w(TAG,"sava as html unimplement");
				  break;
			   case TYPE_SAVE_XML:
				  Log.w(TAG,"save as xml unimplement");
				  break;
		   }
		  worker.sendEmptyMessage(ScanfService.TASK_END);
	    }
	}

}
