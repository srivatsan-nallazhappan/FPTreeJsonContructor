package apriori;

import java.util.ArrayList;

public class FPNode
{
	private FPNode parent;
	private ArrayList<FPNode> children;
	private String label;
	private int support;
	
    public FPNode() 
    {
    	
    }
    
    public FPNode(String lab, int sup, FPNode par) 
    {
    	parent = par;
    	label = lab;
    	support = sup;
    }
    
    public void addChild(FPNode child)
    {
    	if ( children == null )
    		children = new ArrayList<FPNode>();
    	
    	children.add(child);
    }
    
    public FPNode getChildNode(String l)
    {
    	if (children != null)
    	{
	    	for (FPNode child: children)
	    	{
	    		if ( child.label.equals(l))
	    			return child;
	    	}
    	}
    	return null;
    }

	public ArrayList<FPNode> getChildren() {
		return children;
	}


    public void addSupport(int isup)
    {
    	support = support + isup;
    }


	public String getLabel() {
		return label;
	}

	public int getSupport() {
		return support;
	}

}
