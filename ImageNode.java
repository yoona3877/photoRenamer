package photo_renamer;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yoon A Park, Hamin Lee
 * A node in a tree with a file, name, path, tags, namehistory, children, parent, and the type of the node.
 *
 * 
 * 
 */

public class ImageNode {
	/** The file this node represents. */
	private File file;
	/** The name of the file */
	private String name;
	/** The full path of this node */
	private String path;
	/** A list of strings containing selected tags. */
	private List<String> tags = new ArrayList<String>();
	/** This nodes name history containing the history of the modified file names. */
	private List<String> namehistory = new ArrayList<String>();
	/** This list of ImageNodes children. */
	private List<ImageNode> children = new ArrayList<ImageNode>();
	/** This ImageNode parent. */
	private ImageNode parent;
	/** This nodes file type. */
	private FileType type;
	
	/**
	 * A ImageNode in this tree. 
	 * 
	 * @param file
	 *			the file
	 * @param parent
	 * 			the parent node.
	 * @param type
	 * 			file or directory 
	 */
	public ImageNode(File file, ImageNode parent, FileType type){
		this.file = file;
		this.name = file.getName();
		this.parent = parent;
		this.path = file.getPath();
		this.namehistory.add(this.name);
		this.type = type;
	}
	
	/**
	 * Return True if the file type is a directory. Otherwise, return False.
	 * 
	 * @return whether this node represents a directory.
	 */
	public Boolean isDirectory(){
		return this.type == FileType.DIRECTORY;
	}
	
	/**
	 * Get the name of the current node.
	 * 
	 * @return the name of this node.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Add imageNode, representing a file or directory, as a child of this node.
	 * 
	 * @param imageNode
	 * 			the node to add as a child
	 * 				
	 */
	public void addChild(ImageNode imageNode){
		this.children.add(imageNode);
	}
	
	/**
	 * Return the List of child nodes of this node.
	 * 
	 * @return the List of child nodes directly underneath this node.
	 */
	
	public List<ImageNode> getChildren(){
		return this.children;
	}
	
	/**
	 * If this file is not a directory (i.e. if this file is a image file) and 
	 * if String e is in TagManager then add String e into this tags and change this file name.
	 * 
	 * @param e
	 * 			String representing the tag to be selected.
	 * @throws IOException 
	 * @see addChangeName
	 */
	public void selectTag(String e) throws IOException{
		if (this.isDirectory() == false){
			for (String tag: TagManager.getTags()){
				if (tag ==e){
					this.tags.add(e);
					addChangeName(e);
				}
			}
		}
	}
	
	/**
	 * Return the path of the directory represented by the parent of this node.
	 * @return the path of the parent of this node.
	 */
	public String getDirectory(){
		return this.file.getParentFile().getPath();
	}
	
	/**
	 * Change the ImageNode name with a new name containing the prefix "@" and name of the tag added to the end of the original name.
	 * This method will add the name of the tag with a prefix to the end just before the extension.
	 * 
	 * @param tag
	 * 			the name of the tag to be added
	 * @throws IOException 
	 * @see addNameHistory
	 */
	
	public void addChangeName(String tag) throws IOException{
		if (this.isDirectory() == false){
			int i = this.name.lastIndexOf(".");
			String withoutextension = this.name.substring(0, i);
			String extension = this.name.substring(i, this.name.length());
			String pat = this.file.getParentFile().getPath();
			File oldName = new File(pat + "/" + this.name);
			File newName = new File(pat + "/" + withoutextension + " @" + tag + extension);
			oldName.renameTo(newName);
			this.file = oldName;
			
			// Replace ImageNode name with new name and add to name history. 
			this.name = this.name.substring(0, i) +" @" + tag + extension;
			this.namehistory.add(this.name);
			java.util.Date date = new java.util.Date();
			NameHistory.addNameHistory("Old name: " + this.namehistory.get(this.namehistory.size()-2) 
			+ ", " + "New name: " + this.name + "  < "+ new Timestamp(date.getTime())+ " >");
		}
	}
	
	/**
	 * Return to the previous name of this file if the file's name was modified at least once.
	 * Precondition: length.back < this.namehistory.
	 * 
	 * @param back
	 * 			the index which represents the number of steps the user wants to go back
	 * @throws IOException 
	 * 
	 */

	public void returnName(String oldName) throws IOException{
		if (this.namehistory.size()>1){
			String pat = this.file.getParentFile().getPath();
			String historyName = this.name;
			File currentName = new File(pat + "/" + this.name);
			this.name = oldName;
			File previousName = new File(pat + "/" + this.name);
			currentName.renameTo(previousName);
			this.file = currentName;
			this.namehistory.add(this.name);
			java.util.Date date = new java.util.Date();
			NameHistory.addNameHistory("Old name: " + historyName 
			+ ",  " + "New name: " + this.name + "  < "+ new Timestamp(date.getTime())+ " >");
		}
	}

	
	/**
	 * Find and return a child node named name in this directory tree, or null if there is no such child node named name.
	 * 
	 * 
	 * @param name
	 * 			the file name to search for
	 * @return the node named name
	 */
	
	public ImageNode findChild(String name) {
		
			ImageNode result = null;
			if (this.isDirectory()){
				for (int i = 0; i < this.getChildren().size();i++){
					if (name.equals(this.getChildren().get(i).getName())){
						return this.getChildren().get(i);
					} else{
						result = this.getChildren().get(i).findChild(name);
					} if (result != null){
						return result;
					}
				}
			}
			return null;
	}
	
	/**
	 * Return the parent of this ImageNode. 
	 * @return the parent
	 */
		
	public ImageNode getParent() {
		return parent;
	}
	
	
	/**
	 * Return the file of this node.
	 * @return the file
	 */
	public File getFile() {
		return file;
	}


	/**
	 * Return the path of this node.
	 * @return the path
	 */
	public String getPath() {
		return path;
	}


	/**
	 * Return a list of strings representing the tags of this node.
	 * @return
	 * 			the tags of this node
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Set the tags of this node.
	 * @param tags
	 * 			the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * Return the name history of this node.
	 * @return
	 * 			the name history
	 */
	public List<String> getNamehistory() {
		return namehistory;
	}


	/**
	 * Return the file type of this node, whether a directory or a file.
	 * @return
	 * 			the file type of this node
	 */

	public FileType getType() {
		return type;
	}
	
	public File getParentFile(){
		return parent.getFile();
		
	}
	


	/**
	 * Set the name of this node.
	 * @param name
	 * 			the name 
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Return a String representation of the name of this tree or null if the tree is empty.
	 * 
	 * @return a String representation of this tree.
	 * 
	 */
	@Override
	public String toString(){
		if (this.file != null){
			return this.name;
		}else{
			return null;
		}
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
//		image1.selectTag("suun");
//		image1.selectTag("yoona");
		System.out.println(image1.getName());
		System.out.println(image1.getFile().getName());
		System.out.println(image1.getName());
		System.out.println(image1.getFile().getName());
		System.out.println(image1.getNamehistory());
		
		
		
	}
}