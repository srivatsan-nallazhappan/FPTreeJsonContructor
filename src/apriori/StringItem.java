package apriori;

import java.util.ArrayList;

public class StringItem {
	private ArrayList<String> d_strArray = new ArrayList<String>();
	private int d_support;
	// for now , this constructor will suffice
	public StringItem(ArrayList<String> items, int support)
	{
		if(items.size() > 0 )
		{
			for (String str : items)
			  d_strArray.add( new String(str));
			 d_support = support; 
		}
	}
    public ArrayList<String> getStringItems()
    {
    	return d_strArray;
    }
    public int getSupport()
    {
    	return d_support;
    } 
    public int size()
    {
        return d_strArray.size();
    }
}