package apriori;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class StringItems {
	private ArrayList<ArrayList<StringItem>> d_strItems = new ArrayList<ArrayList<StringItem>>();
	private String d_name; // give a name to the StringItems
	
	public ArrayList<ArrayList<StringItem>> getAllStringItems()
	{
		return d_strItems;
	}
	// only constructor --- 
	public StringItems(String name)
	{
		d_name = name;
		d_strItems.add( new ArrayList<StringItem>() );// add a dummy list at level 0
			
	}
	public void addStringItems(StringItem sItem)
	{
		int k = sItem.size();
		while( d_strItems.size() <= k)
		{
			d_strItems.add(new ArrayList<StringItem>());
		}
		d_strItems.get(k).add(sItem);
	}
	public ArrayList<StringItem> getStringItems(int level)
	{
		if ( d_strItems.size() < level )
			return new ArrayList<StringItem>();
		else
			return d_strItems.get(level);
	}
	public int getLevels()
	{
		return d_strItems.size();
	}
	public void printStringItems()
	{
		int levelCount = -1;
	    StringBuffer sbuff = new StringBuffer();
			
	    sbuff.append("Printing all the Frequent patterns \n");
        sbuff.append("----------------------------------------------------------\n");
		 for (ArrayList<StringItem> onelevel : d_strItems )
         {
       	     levelCount++;
       	     
       	     if ( onelevel.size() == 0 )
       	       continue;
       	      sbuff.append("Printing level " + levelCount + " ...\n");
       	    		 
       	      for ( StringItem si: onelevel)
       	      {
       		     ArrayList<String> stringVals = si.getStringItems();
       		     for(String anEntry: stringVals)
       		     {
       		    	 sbuff.append(anEntry).append(" ");
       		     }
       		     sbuff.append("#SUPP: ").append(String.valueOf(si.getSupport()));
       		     sbuff.append("\n");       		     
       		  }
         }
			 
		 System.out.println(sbuff);
		 return;
				 
	}
	public JSONObject toJson()
	{
		int levelCount = -1;
		int ind1 = -1;
		JSONObject mainWrapper = new JSONObject();
		JSONArray  outerList = new JSONArray();
		mainWrapper.put("Pattern", outerList);
		for (ArrayList<StringItem> onelevel : d_strItems )
		{
			  levelCount++;
			  if ( onelevel.size() == 0 )
	       	       continue;
			  ++ind1;
			  int ind2 = -1 ;
			  JSONArray eachLevel = new JSONArray();
			  
			  for ( StringItem si: onelevel)
			  {
		          ++ind2;	  
				  ArrayList<String> stringVals = si.getStringItems();
				  StringBuffer sbuff = new StringBuffer();
				  
				  for(String anEntry: stringVals)
				    sbuff.append(anEntry).append(",");
				 				 
				  JSONObject oneRule = new JSONObject();
				  oneRule.put("key", sbuff.toString());
				  oneRule.put("count", si.getSupport());
		          eachLevel.put(ind2,oneRule);
			   }
			   JSONObject levelWrapper = new JSONObject();
			   levelWrapper.put(String.valueOf(levelCount), eachLevel);
			   outerList .put(ind1,levelWrapper);		   
		}
		
		return mainWrapper;
	}
}