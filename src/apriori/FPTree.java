package apriori;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class FPTree
{
	private FPNode root;
	private HashMap<String, LinkedList<FPNode>> headerTable = new HashMap<String, LinkedList<FPNode>>();
    private FPTree(FPNode root) {
        this.root = root;
    }

    public static FPTree buildTree(StringItems itemset)
    {
    	FPNode rootNode = new FPNode();
    	FPTree fp = new FPTree(rootNode);
    	ArrayList<ArrayList<StringItem>> levelitems = itemset.getAllStringItems();
    	for (int i=1;i<levelitems.size(); i++)
    	{
    		ArrayList<StringItem> itemsset = levelitems.get(i);
    		for ( int j=0;j<itemsset.size(); j++)
    		{
    			StringItem transaction = itemsset.get(j);
    			ArrayList<String> itemsarr = (ArrayList<String>) transaction.getStringItems().clone();
        		Collections.sort(itemsarr);
            	FPNode cur = rootNode;
        		for (int k=0;k<itemsarr.size(); k++)
        		{
        			FPNode nodeChild = cur.getChildNode(itemsarr.get(k));
        			if ( nodeChild != null )
        			{
        				cur=nodeChild;
        				nodeChild.addSupport(transaction.getSupport());
        				cur=nodeChild;
        			}
        			else
        			{
        				FPNode newChild = new FPNode(itemsarr.get(k),transaction.getSupport(),cur);
        				cur.addChild(newChild);
        				fp.updateHeaderTable(newChild);
        				cur=newChild;
        			}
        		}
    		}
    		
    	}
    	
    	return fp;
    }
    
    private void updateHeaderTable(FPNode node)
    {
    	LinkedList<FPNode> headlist = headerTable.get(node.getLabel());
    	if ( headlist == null )
    	{
    		headlist = new LinkedList<FPNode>();
        	headerTable.put(node.getLabel(), headlist);
    	}
    	headlist.addFirst(node);
    }
    
    public String getTreeJSON()
    {
    	String jsonStr = "";
		JSONObject jsonret = new JSONObject();
		JSONArray  rootJsonArr = new JSONArray();
		jsonret.put("Root", rootJsonArr);
		JSONObject rootJsonNode = new JSONObject();
		rootJsonNode.put("name", "Root");
		rootJsonNode.put("parent", "null");
		this.formJson(root, rootJsonNode);
		System.out.println("Json Tree String is " );
		System.out.println(rootJsonNode.toString());
    	return jsonStr;
    }
    
    private void formJson(FPNode node, JSONObject jsonobj)
    {
    	ArrayList<FPNode> children = node.getChildren();
    	if ( children == null || children.size() == 0 )
    		return;
    	
    	JSONArray  childJsonArr = new JSONArray();
    	for ( FPNode child: children )
    	{
    		JSONObject jsonchild = new JSONObject();
    		jsonchild.put("name", child.getLabel() + ":" + child.getSupport());
    		jsonchild.put("parent", jsonobj.get("name"));
    		formJson(child,jsonchild);
    		childJsonArr.put(jsonchild);
    	}
    	jsonobj.put("children",childJsonArr);
    	return;
    }
    
    public LinkedList<FPNode> getNodePatternList(String label)
    {
    	if ( headerTable.get(label) != null )
    		return headerTable.get(label);
    	
    	return null;
    }
}
