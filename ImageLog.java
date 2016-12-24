package photo_renamer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Yoon A Park, Hamin Lee
 *
 * A log of the image files.
 */

public class ImageLog {
	/** The file with name filetxt containing the log of this node */
	private File filetxt;
	/**
	 * A ImageLog of this path.
	 * @param path
	 * 			the path of this node
	 */
	public ImageLog(String path){
		filetxt = new File(path);
	}
	
	/**
	 * Write a log of the name history of this node on filetxt. 
	 * @throws IOException
	 */
	public void writeLog() throws IOException{
		FileOutputStream fos = new FileOutputStream(filetxt);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		for (int i = 0; i<NameHistory.getNameHistory().size(); i++){
			bw.write(NameHistory.getNameHistory().get(i));
			bw.newLine();
		}
		bw.close();	
	}
	
	
	public static void main(String[] args) throws IOException{
		File file = new File("/h/u14/c5/00/leehamin/Desktop/image");
		ImageNode imageTree = new ImageNode(file, null, FileType.DIRECTORY);
		ImageManager.buildImageTree(file, imageTree);
		ImageNode image1 = imageTree.findChild("index.jpeg");
		System.out.println(image1);
		
		
		//Testing for TagManager add/remove method
		TagManager.add("babo");
		TagManager.add("lala");
		TagManager.add("yolo");
		TagManager.remove("babo");
		System.out.println(TagManager.getTags());
		System.out.println(TagManager.isEmpty());
		image1.selectTag("lala");
		image1.selectTag("yolo");
		System.out.println(image1.getTags());
		System.out.println(image1.getName());
		System.out.println(image1.getNamehistory());
		
		ImageLog imageLog = new ImageLog("/h/u14/c5/00/leehamin/Desktop/image/ImageLog.txt");
		imageLog.writeLog();
		

	}
}
