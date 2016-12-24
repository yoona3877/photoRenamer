package photo_renamer;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;

/**
 * @author Yoon A Park, Hamin Lee
 * The manager for creating a new tree containing image files and directories. 
 * It generates string of image file name and directory name.
 */
public class ImageManager {
		/**
		 *  Build the ImageTree of nodes rooted at file in the file system; note curr is the ImageNode corresponding to file,
		 *  so this only adds nodes for children of file to the tree. If the child node is a file, then it must be a image file
		 *  to be contained in the tree. Precondition: file represents a directory.
		 * @param file
		 * 			the file or directory we are building
		 * @param curr
		 * 			the node representing file
		 */
		public static void buildImageTree(File file, ImageNode curr){
			if (file.isDirectory()){
				File[] children;
				children = file.listFiles();
				if (children.length != 0){
					for (File child: children){
						if (child.isFile()){
							if ((new MimetypesFileTypeMap().getContentType(child).split("/")[0]).equals("image")){
								ImageNode childNode = new ImageNode(child, curr, FileType.FILE);
								curr.addChild(childNode);
							}
						} else{
								ImageNode childNode = new ImageNode(child, curr, FileType.DIRECTORY);
								curr.addChild(childNode);
								buildImageTree(child, childNode);
						}
					}
				}
			}
		}
		
		/**
		 * Build a string buffer representation of the contents of the tree rooted at n, prepending each file name with the name 
		 * of the imageNode.
		 * @param imageNode
		 * 			the root of the subtree
		 * @param contents
		 * 			the string to display
		 */
		public static void buildDirectoryContents(ImageNode imageNode, StringBuffer contents) {
			contents.append(imageNode.getName());
			contents.append("\n");
			if (imageNode.getChildren().size() != 0);
				for (ImageNode child: imageNode.getChildren()){
					if (child.isDirectory()){
						buildDirectoryContents(child, contents);
					}
					else{
						contents.append(child.getName() + "\n");
					}
				}
		}
		
		public static List<ImageNode> returnAllImageNodes(ImageNode imageNode, List<ImageNode> images){
			if (imageNode.isDirectory() == false){
				images.add(imageNode);
			}
			if (imageNode.getChildren().size() != 0){
				for (ImageNode child: imageNode.getChildren()){
					if (child.isDirectory()){
						returnAllImageNodes(child, images);
					}
					else{
						images.add(child);
					}
				}
			}
			return images;
			
		}
		
		public static void main(String[] args){

			File file = new File("/Users/yoona96/Desktop/poster");
			ImageNode imageTree = new ImageNode(file, null, FileType.DIRECTORY);
			ImageManager.buildImageTree(file, imageTree);
			StringBuffer childrenList = new StringBuffer();
			String result = new String();
			ImageManager.buildDirectoryContents(imageTree, childrenList);
			result += childrenList.toString();
			System.out.println(result);
			
			System.out.println("==============");
			List<ImageNode> listimages = new ArrayList<ImageNode>();
			listimages = ImageManager.returnAllImageNodes(imageTree, listimages);
			for (int i = 0; i <listimages.size(); i++ ){
				System.out.println(listimages.get(i));
			}
			
//			System.out.println(imageTree.getChildren());
//			ImageNode child1= imageTree.getChildren().get(0);
//			System.out.println(child1);
//			ImageNode child2 = imageTree.findChild("766894_orig.jpg");
//			System.out.println(child2);
//			ImageNode image1 = imageTree.findChild("index.jpeg");
//			System.out.println(image1);
//				
//			TagManager.add("suun");
//			TagManager.add("yoona");
//			TagManager.remove("yoona");
//			image1.selectTag("suun");
//			System.out.println(image1.getName());
//			System.out.println(image1.getFile().getName());
//			image1.returnToPreviousName(2);
//			System.out.println(image1.getName());
//			System.out.println(image1.getFile().getName());
//			System.out.println(image1.getNamehistory());
			
		}
}

