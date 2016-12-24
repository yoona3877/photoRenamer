package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Yoon A Park, Hamin Lee
 * A GUI of the initial JFrame which contains viewLog button, addTag button, removeTag button, and chooseDir button.
 * This will allow user to add and remove tags as well as viewing the log and choosing the directory.
 * 
 * Singleton Design Pattern is used
 * This is created only once and public static getInstance() method 
 * provides a global point of access to PhotoRenamer
 *
 */
@SuppressWarnings("serial")
public class PhotoRenamer extends JFrame{
	/** PhotoRenamer itself*/
	private static PhotoRenamer instance;
	/** The JButtons PhotoRenamer contains*/
	private JButton chooseDirBt, viewLogBt, addTagBt, removeTagBt;
	/** The JPanel PhotoRenamer is divided into */
	private JPanel chooseDirpanel, viewLogpanel, addRemovepanel;
    
    public static PhotoRenamer getInstance() throws IOException {
        return instance = new PhotoRenamer("Photo Renamer");
    }
	/**
	 * Set up a PhotoRenamer GUI Framework.
	 * @param title
	 * 			the title
	 * @throws IOException 
	 */
	private PhotoRenamer(String title) throws IOException{
		super(title);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(200,150);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	// Display PhotoRenamer main window
	/**
	 * Build a JFrame PhotoRenamer main window.
	 * 
	 */
	
	public void buildWindow(){
		chooseDirBt = new JButton("Choose a Directory");
		chooseDirBt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					if (file.exists()){
						ImageNode imageTree = new ImageNode(file, null, FileType.DIRECTORY);
						ImageManager.buildImageTree(file, imageTree);
						List<ImageNode> imagelist = new ArrayList<ImageNode>();
 						imagelist = ImageManager.returnAllImageNodes(imageTree, imagelist);
						ViewAvailableImages viewAvailableImages = new ViewAvailableImages(
								"View available images", imageTree, imagelist);
						viewAvailableImages.buildWindow();
					}
				}
			}
		});
		
		//Can view Low when clicking viewLogBt
		viewLogBt = new JButton("View Log History");
		viewLogBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					NameHistory.construct();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ViewLogGUI viewLog = new ViewLogGUI("View Log");
				viewLog.buildWindow();
				
			}
		});
		
		//Can add tags when clicking add_tag button
		addTagBt = new JButton("Add tag");
		addTagBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				AddTagGUI addTagGUI;
				try {
					addTagGUI = AddTagGUI.getInstance();
					addTagGUI.buildWindow();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});
		
		//Can remove tags when clicking remove_tag button
		removeTagBt = new JButton("Remove tag");
		removeTagBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveTagGUI removeTagGUI;
				try {
					removeTagGUI = RemoveTagGUI.getInstance();
					removeTagGUI.buildWindow();
				} catch (IOException e1) {
					e1.printStackTrace();
				}		
			}
			
		});
		
		chooseDirpanel = new JPanel();
		viewLogpanel = new JPanel();
		addRemovepanel = new JPanel();
		chooseDirpanel.add(chooseDirBt);
		viewLogpanel.add(viewLogBt);
		addRemovepanel.add(addTagBt, BorderLayout.NORTH);
		addRemovepanel.add(removeTagBt, BorderLayout.SOUTH);
		
		this.add(chooseDirpanel, BorderLayout.NORTH);
		this.add(viewLogpanel, BorderLayout.CENTER);
		this.add(addRemovepanel, BorderLayout.SOUTH);
		this.pack();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		PhotoRenamer photoRenamer = PhotoRenamer.getInstance();
		photoRenamer.buildWindow();
		TagManager.construct();

		photoRenamer.addWindowListener(new WindowListener() {
            public void windowClosed(WindowEvent arg) {
                System.out.println("Window close event occur");
				try {
					TagManager.save();
					NameHistory.save();
				}
				 catch (IOException e) {
					e.printStackTrace();
				}
            }
			@Override
			public void windowOpened(WindowEvent e) {		
			}
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
			}   
		});	
	}	
}
