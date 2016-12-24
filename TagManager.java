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
 * A database of existing tags. Add and save the tags inputted by the user.
 */

public class TagManager {
	/**The list of tags which will be available*/
	private static ArrayList<String> tagList = new ArrayList<String>();
	private static File tagFile;
	
	/**
	 * Add the tag named e to tagList if it doesn't contain in the tagList already.
	 * @param e
	 * 			the tag to be added
	 */
	
	public static void add(String e){
		if (tagList.contains(e) != true){
			tagList.add(e);
		}
	}
	

	/**
	 * Construct a tagFile containing the tags added while user was running the program.
	 * This method will restore all the tags added in previous uses.
	 * @throws IOException
	 * @see save()
	 */
	public static void construct() throws IOException{
		tagFile = new File("tagFile.txt");
		if(! tagFile.exists()){
		 	tagFile.createNewFile();
		  } else{
			  try(BufferedReader br = new BufferedReader(new FileReader(tagFile))) {
				    for(String line; (line = br.readLine()) != null; ) {
				        TagManager.add(line);
				    }
				}
		  }
		}
	/**
	 * Remove the tag named e from tagList if tagList contains tag named e. Else, do nothing
	 * @param e
	 * 			the tag to be removed
	 */
	public static void remove(String e){
		if (TagManager.isEmpty() != true){
			for (int i = 0; i < tagList.size(); i++){
				if (e == tagList.get(i)){
					tagList.remove(i);
				}
			}
		}
	}
	
	/**
	 * Return a list of strings containing all available tags in tagList.
	 * @return
	 * 			the list of tags in tagList
	 */
	
	public static List<String> getTags(){
		return tagList;
	}
	
	/**
	 * Set a list of strings containing String tags to this tagList
	 * @param tags
	 * 			the tag list to be added in this tagList
	 */
	public static List<String> setTags(ArrayList<String> tags){
		return tagList = tags;
	}
	
	/**
	 * Return whether this tagList is empty.
	 * @return
	 * 			whether this tagList is empty.
	 */
	public static boolean isEmpty(){
		return tagList.size() == 0;
	}
	

	/**
	 * Save all the added tags in the tagFile.
	 */
	public static void save(){
				try {
					FileOutputStream tagout = new FileOutputStream(tagFile, false);
					BufferedWriter tg = new BufferedWriter(new OutputStreamWriter(tagout));
					for (int i = 0; i<TagManager.getTags().size(); i++){
						tg.write(TagManager.getTags().get(i));
						tg.newLine();
					}
					tg.close();	
				}
				 catch (IOException e) {
					e.printStackTrace();
				}
	        }
	}


