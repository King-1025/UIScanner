package hbxxl.uisao;

import android.app.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;


public class MainActivity extends Activity implements OnClickListener
{
	
	private ImageView iv;
	private Button save;
	private EditText et;
	private final static String root="/storage/sdcard1";
	//private FloatWindowController fc;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		//fc=new FloatWindowController(this,null);
		//fc.show();
		iv=(ImageView) findViewById(R.id.mainImageView1);
		save=(Button) findViewById(R.id.mainButton1);
		et=(EditText) findViewById(R.id.mainEditText1);
		save.setOnClickListener(this);
    }
	
	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		String str=et.getText().toString();
		if(str!=null){
			int dr=0x01;
			if(str.equals("logo0")){
				dr=R.drawable.logo0;
			}else if(str.equals("logo1")){
				dr=R.drawable.logo1;
			}
			if(dr!=0x01){
				saveDrawableById(dr,str+".png", Bitmap.CompressFormat.PNG);
			}else{
				Toast.makeText(this,str+"不存在！",1000).show();
			}
		}else{
			Toast.makeText(this,"输入为空！",1000).show();
		}

	}

	public void saveDrawableById(int id, String name, Bitmap.CompressFormat format) {
          Drawable drawable = idToDrawable(id);
		  if(drawable!=null){
		      Bitmap bitmap = drawableToBitmap2(drawable);
              saveBitmap(bitmap, name, format);
	      }else{
			  Toast.makeText(this,"drawable is null",1000).show();
		  }
    }
	public Drawable idToDrawable(int id) {
        return this.getResources().getDrawable(id);
    }
	
    public Bitmap drawableToBitmap0(Drawable drawable) {
        if(drawable == null)
            return null;
        return ((BitmapDrawable)drawable).getBitmap();
    }

	public Bitmap drawableToBitmap1(Drawable drawable) {  
        if (drawable instanceof BitmapDrawable) {  
            return ((BitmapDrawable) drawable).getBitmap();  
        } else if (drawable instanceof NinePatchDrawable) {  
            Bitmap bitmap = Bitmap  
				.createBitmap(  
				drawable.getIntrinsicWidth(),  
				drawable.getIntrinsicHeight(),  
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
				: Bitmap.Config.RGB_565);  
            Canvas canvas = new Canvas(bitmap);  
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
							   drawable.getIntrinsicHeight());  
            drawable.draw(canvas);  
            return bitmap;  
        } else {  
            return null;  
        }  
    }  
	public Bitmap drawableToBitmap2(Drawable drawable)
    {
	    Bitmap bitmap;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = 
			drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
			: Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w,h,config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);   
        drawable.setBounds(0, 0, w, h);   
        drawable.draw(canvas);
		return bitmap;
    }
    public void saveBitmap(Bitmap bitmap, String name, Bitmap.CompressFormat format) {
	   String path=root+"/"+name;
	   if(bitmap==null){
		   Toast.makeText(this,"bitmap is null!",1000).show();
	   }
	   else{
		// 创建一个位于SD卡上的文件
        File file = new File(path);
        FileOutputStream out = null;
        try{
            // 打开指定文件输出流
            out = new FileOutputStream(file);
            // 将位图输出到指定文件
            bitmap.compress(format,100,out);
            out.close();

			Toast.makeText(this,"保存成功！"+path,1000).show();
        } catch (IOException e) {
            e.printStackTrace();
        }}
      Log.i("","path:"+path);
    }
	
}
