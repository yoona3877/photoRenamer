package photo_renamer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Hamin Lee, Yoon A Park
 * 
 * A history of modified names for every modified ImageNode.
 *
 */

public class NameHistory {
	/** The list of strings containing the history of the name modifications.*/
	private static List<String> nameHistory = new ArrayList<String>();
	/** The log file containing all the users log */ 	
	private static File logFile;
	
	/**
	 * Construct a logFile containgin user's log
	 * @throws
	 *		IOException
	 * 			
	 */
	public static void construct() throws IOException{
		logFile = new File("logFile.txt");
		if(! logFile.exists()){
    	 	logFile.createNewFile();
    	  } else{
    		  try(BufferedReader br = new BufferedReader(new FileReader(logFile))) {
    			    for(String line; (line = br.readLine()) != null; ) {
    			        NameHistory.addNameHistory(line);;
    			    }
    			}
    	  }
	}
	
	/**
	 * Return the name history of every name-modified ImageNode.
	 * @return
	 * 			the name history of every name-modified ImageNode.
	 */
	public static List<String> getNameHistory(){
		return nameHistory;
	}
	/**
	 * Add a modification of the name done on the name-modified ImageNode in the nameHistory. 
	 * 
	 * @param e
	 * 			the name modification to be added
	 * @throws IOException 
	 */
	public static void addNameHistory(String e) throws IOException{
		nameHistory.add(e);
	}
	
	
	/**
	 * Save the log information of the user. This is to restore the log information when the program closed 
	 * and ran again.
	 * 
	 * @throws IOException
	 */
	public static void save() throws IOException{
		File logFile = new File("logFile.txt");
		FileOutputStream logout = new FileOutputStream(logFile, false);
		BufferedWriter lf = new BufferedWriter(new OutputStreamWriter(logout));
		for (int i = 0; i<NameHistory.getNameHistory().size(); i++){
			lf.write(NameHistory.getNameHistory().get(i));
			lf.newLine();
		}
		lf.close();	
	}
	public static void main(String[] args){
		File file = new File("/h/u14/c5/00/leehamin/Desktop/image");
		ImageNode imageTree = new ImageNode(file, null, FileType.DIRECTORY);
		ImageManager.buildImageTree(file, imageTree);
		System.out.println(imageTree.getChildren());
		ImageNode child1= imageTree.getChildren().get(0);
		System.out.println(child1);
		ImageNode child2 = imageTree.findChild("766894_orig.jpg");
		System.out.println(child2);
		ImageNode image1 = imageTree.findChild("index.jpeg");
		System.out.println(image1);
			
		TagManager.add("suun");
		TagManager.add("yoona");
		TagManager.remove("yoona");
//		image1.selectTag("suun");
		System.out.println(image1.getName());
		System.out.println(image1.getFile().getName());
		System.out.println(image1.getName());
		System.out.println(image1.getFile().getName());
		System.out.println(image1.getNamehistory());
	}
}
