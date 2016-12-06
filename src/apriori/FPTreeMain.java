package apriori;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FPTreeMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		File file = new File("D:/pout.txt");
		try {
			StringItems itemset = new StringItems("EngSkill");
	        Scanner sc = new Scanner(file);
	        while (sc.hasNextLine()) {
	        	String inp = sc.nextLine();
	        	String[] tk = inp.split("\\|");
	        	String pat = tk[0];
	        	int cnt = Integer.valueOf(tk[1].trim());
	            String[] ptk = pat.split(",");
	            ArrayList<String> items = new ArrayList<String>();
	            for ( int i=0;i<ptk.length;i++ )
	            {
	            	 items.add(ptk[i].trim());
	            }
	            StringItem sitems = new StringItem(items,cnt);
	            itemset.addStringItems(sitems);
	        }
            System.out.println(itemset.toJson().toString());
            FPTree myTree = FPTree.buildTree(itemset);
            System.out.println(myTree.getTreeJSON());
	        sc.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	}

}
