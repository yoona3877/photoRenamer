package photo_renamer;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;


import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JPanel;

/**
 * 
 * @author Hamin Lee, Yoon A Park
 * 
 * A GUI for the user to select an image file from chosen directory.
 * 
 * Observer Design Pattern is used in this class.
 * There is a container JFrame, to display components such as panel, and button.
 * The class creates the components and add the components to the display area and arrange through layout.
 * Then attaches a listener to the button, interacting with button causes the event to occur. The addActionListener
 * method is the subject's register observer mode. The listener interface is for receiving action events.
 * The ViewAvailableImages class is interested in processing an action event implementing the interface, 
 * and the object created with that class is registered with a component, using the component's addActionListener 
 * method. When the action event occurs, that object's actionPerformed method is invoked.
 * 
 * 
 *
 */


@SuppressWarnings("serial")
public class ViewAvailableImages extends JFrame{
	/** The JPanel for the ViewAvailableImages GUI. */
	private JPanel panel;
	/** The imagelist in selected directory. */
	private List<ImageNode> imagelist;
	
	/**
	 * Constructor for the ViewAvailableImages class.
	 * @param title
	 * 			the title of the images
	 * @param imageTree
	 * 			the imageTree
	 */
	public ViewAvailableImages(String title, ImageNode imageTree){}
	
	/**
	 * Set up for window ViewAvailableImages.
	 * 
	 * @param title
	 * 			the title of frame
	 * @param imageTree
	 * 			the imageTree
	 * @param imagelist
	 * 			the imagelist
	 */
	public ViewAvailableImages(String title, ImageNode imageTree, List<ImageNode> imagelist){
		super(title);
		this.imagelist = imagelist;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Build a window ViewAvailableImages. Only image files from the chosen 
	 * directory will be in the frame.
	 */
	
	public void buildWindow(){
        panel = new JPanel();
        panel.setSize(230, 370);
        panel.setLayout(new GridLayout(5, 0));

        for (int i = 0; i < imagelist.size(); i++){
        	ImageNode image = imagelist.get(i);
        	JButton button = new JButton(image.getName());
        	button.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					ImageViewer imageViewer = new ImageViewer("View this image", image);
					try {
						imageViewer.buildWindow();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
				});
        	panel.add(button);
        }
		this.add(panel, BorderLayout.CENTER);		
	}
}
