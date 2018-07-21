package hbxxl.uisao.scanner;

import android.view.accessibility.*;
import java.util.*;
import android.view.*;

public abstract class BasedScanner
{
    public Tree tree;
	
	public abstract void scan();
	public abstract void saveAsHTML(String filePath);
	public abstract void saveAsXML(String filePath);
	public abstract void showInLayout(ViewGroup container);
	
}
