package hbxxl.uisao.scanner;
import android.util.*;
import android.view.*;
import android.view.accessibility.*;
import java.util.*;

public class ViewScanner extends BasedScanner
{
	private AccessibilityNodeInfo root;
    private final static String TAG="ViewScanner";
	
	public ViewScanner(AccessibilityNodeInfo root)
	{
		this.root = root;
	}

	public void setRoot(AccessibilityNodeInfo root)
	{
		this.root = root;
	}

	public AccessibilityNodeInfo getRoot()
	{
		return root;
	}
    
	@Override
	public void scan()
	{
		// TODO: Implement this method
		if(root==null){
			Log.w(TAG,"root is null");
		}else{
			tree=plant(root);
		    describe(tree);
		}
	}

	@Override
	public void saveAsHTML(String filePath)
	{
		// TODO: Implement this method
	}

	@Override
	public void saveAsXML(String filePath)
	{
		// TODO: Implement this method
	}

	@Override
	public void showInLayout(ViewGroup container)
	{
		// TODO: Implement this method
	}
	
	public Tree plant(AccessibilityNodeInfo node){
		Tree tr=null;
		if(node==null){
			Log.w(TAG,"node is null!");
		}else{
			ArrayList<ArrayList<InfoUnit>> body=new ArrayList<ArrayList<InfoUnit>>();
			capture(0,node,body);
			tr=new Tree(body);
			Log.i(TAG,"tree planting is finished!");
		}
		return tr;
	}
	
	private void capture(int index,AccessibilityNodeInfo node,ArrayList<ArrayList<InfoUnit>> body){
		ArrayList<InfoUnit> current;
		ArrayList<InfoUnit> next;
	    if(index<body.size()){
			current=body.get(index);
		}else{
			current=new ArrayList<InfoUnit>();
			body.add(current);
		}
		int childCount=node.getChildCount();
	    if(childCount>0){
			if((index+1)<body.size()){
				next=body.get(index+1);
			}else{
				next=new ArrayList<InfoUnit>();
				body.add(next);
			}
			current.add(new InfoUnit(next.size(),childCount,obtainInfo(node)));
			for(int i=0;i<childCount;i++){
				capture(index+1,node.getChild(i),body);
			}
		}else{
			current.add(new InfoUnit(0,0,obtainInfo(node)));
		}
	}
	
	private String obtainInfo(AccessibilityNodeInfo rch){
		String info=null;
		if(rch!=null)
		{
	       info=rch.getClassName()+
		     " 文本:<"+rch.getText()+">"+
			 " 窗口描述:<"+rch.getContentDescription()+">"+
			 " 输入类型"+rch.getInputType()+
			 " 点击:"+rch.isClickable()+
			 " 长按:"+rch.isLongClickable()+
			 " 可见性:"+rch.isVisibleToUser() +
			 " 控件ID:"+rch.getViewIdResourceName()+
			 " 包名:"+rch.getPackageName()+
			 " 窗口ID:"+rch.getWindowId();	 
	     }
		return info;
	}
	
	public void describe(Tree p1){
		if(p1==null){
			Log.w(TAG,"tree is null,cann't describe!");
			return;
		}
		if(p1.getHeight()>0){
			if(p1.firstBranch().size()==1){
				Log.i(TAG,"start to pick fruit.");
				Log.i(TAG,"tree width:"+p1.getWidth()+" height:"+p1.getHeight()+" leaf:"+p1.getLeaf());
				Log.i(TAG,"-------------------------------");
				p1.pick(0,0);
				Log.i(TAG,"-------------------------------");
				Log.i(TAG,"tree width:"+p1.getWidth()+" height:"+p1.getHeight()+" leaf:"+p1.getLeaf());
			}else{
				Log.w(TAG,"tree doesn't have top branch.maybe,it is a forest! so cann't describe.");
			}
		}else{
			Log.i(TAG,"sorry,tree has no fruit! tree height:"+p1.getHeight());
		}
	}
	
}
