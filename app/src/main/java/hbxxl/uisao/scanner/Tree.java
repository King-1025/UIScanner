package hbxxl.uisao.scanner;

import android.util.*;
import java.util.*;

public class Tree
{
	private ArrayList<ArrayList<InfoUnit>>body;
	private int width;
	private int height;
    private int leaf;
	private final static String TAG="Tree";
	public Tree(){
		this(null);
	}
	public Tree(ArrayList<ArrayList<InfoUnit>> p1)
	{
		if(p1!=null){
			body=p1;
		}else{
			body=new ArrayList<ArrayList<InfoUnit>>();
		}
		grow(body);
	}

	public void grow(ArrayList<ArrayList<InfoUnit>> p1)
	{
		if(p1==null){
			Log.w(TAG,"body is"+p1+",don't grow!");
			return;
		}
		body = p1;
		width=0;
		height=body.size();
		leaf=0;
		for(int i=0;i<height;i++){
		  int len=body.get(i).size();
		  if(width<len){
			  width=len;
		  }
		  leaf+=len;
		}
	}
	public InfoUnit firstFruit(){
		return fruit(0,0);
	}
	public ArrayList<InfoUnit> firstBranch(){
		return branch(0);
	}
	public ArrayList<InfoUnit> branch(int floor){
		if(floor<height){
			return body.get(floor);
		}else{
			Log.w(TAG,"floor:"+floor+" is than height:"+height+",no branch!");
			return null;
		}
	}
	public InfoUnit fruit(int floor,int index){
		InfoUnit fruit=null;
		ArrayList<InfoUnit> branch=branch(floor);
	    if(branch!=null){
			if(index<branch.size()){
				fruit=branch.get(index);
			}else{
				Log.w(TAG,"index:"+index+" is than branch("+floor+") size:"+branch.size());
			}
		}
		return fruit;
	}
	public void pick(int floor,int index){
		InfoUnit fr=fruit(floor,index);
		if(fr==null){
			Log.w(TAG,"fruit is no found! "+"floor:"+floor+" index:"+index);
			return;
		}
		String str0="";
		String str1="";
		int size=fr.getSize();
		int space_length=floor-1;
		for(int i=0;i<space_length;i++){
			str0+="|  ";
		}
		if(floor>0){
			str1=str0+"|--";
			str0+="|  ";
		}else{
			str1="Root:";
		}
		Log.i(TAG,str1+(floor+1)+":"+(index+1)+"->"+fr.getData());
		if(size<=0){
			return;
		}
		floor++;
		Log.i(TAG,str0+"{");
		for(int i=0;i<size;i++){
	        pick(floor,(fr.getIndex()+i));
	    }
		Log.i(TAG,str0+"}");
	}
	
	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getLeaf()
	{
		return leaf;
	}
	
}
