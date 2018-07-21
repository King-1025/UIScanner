package hbxxl.uisao.scanner;

public class InfoUnit
{
	private int index;
	private int size;
	private String data;

	public InfoUnit(int index, int size, String data)
	{
		this.index = index;
		this.size = size;
		this.data = data;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return index;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getSize()
	{
		return size;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getData()
	{
		return data;
	}
	

}
