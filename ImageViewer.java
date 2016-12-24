package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * @author Hamin Lee, Yoon A Park
 * A GUI containing the image of the file, selectTag button, returnToPreviousName button, and the image's name.
 * ImageViewer contains two JComboBox containing the tagList and nameHistory. The user will be able to select a
 * tag and return to the previous names by clicking the appropriate buttons.
 *
 * Observer Design Pattern is used in this class.
 * There is a container JFrame, to display components such as panel, button, text area, and combo box.
 * The class creates the components and add the components to the display area and arrange through layout.
 * Then attaches a listener to the button, interacting with button causes the event to occur. The addActionListener
 * method is the subject's register observer mode.The listener interface is for receiving action events.
 * The ImageViewer class is interested in processing an action event implementing the interface, 
 * and the object created with that class is registered with a component, using the component's addActionListener 
 * method. When the action event occurs, that object's actionPerformed method is invoked.
 * 
 */


@SuppressWarnings("serial")
public class ImageViewer extends JFrame {
	/** The ImageNode of the selected image. */
	private ImageNode image;
	/** The JPanel ImageViewer is divided into. */
	private JPanel imagePanel, selectPanel, returnPanel;
	/** The JButtons ImageViewer contains*/
	private JButton selectTagBt, returnBt;
	/** The JComboBox ImageViewer contains*/
	private JComboBox<String> taglist, oldNames;
	/** The JLabel ImageViewer contains. */
	private JLabel imageLabel, nameLabel;

	/**
	 * Set up a ImageViewer GUI Framework.
	 * @param title
	 * 			the title of this window
	 * @param imageNode
	 * 			the ImageNode of this image
	 */
	public ImageViewer(String title, ImageNode imageNode){
		super(title);
		this.image = imageNode;
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setSize(600,600);
		this.setLocationRelativeTo(null);
	
	}
	
	/**
	 * Build a JFrame ImageViewer main window.
	 * @throws IOException
	 */
	public void buildWindow() throws IOException{
		setLayout(new FlowLayout());
		imagePanel = new JPanel();
		selectPanel = new JPanel();
		returnPanel = new JPanel();

		taglist = new JComboBox<String>();
		taglist.setPreferredSize(new Dimension(125,30));
		taglist.setPrototypeDisplayValue("Empty");
		taglist.isEditable();
		
		//list of available tags
		for (int i=0; i<TagManager.getTags().size(); i++){
			String t = TagManager.getTags().get(i);
			taglist.addItem(t);
		}
		selectPanel.add(taglist, BorderLayout.WEST);
		
		//Create select tag button
		selectTagBt = new JButton("Select Tag");
		selectTagBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String selectedTag = (String) taglist.getSelectedItem();
				try {
					image.selectTag(selectedTag);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				oldNames.addItem(image.getName());
				nameLabel.setText(image.getName());
			}
		});
		selectPanel.add(selectTagBt,BorderLayout.EAST);
		this.add(selectPanel, BorderLayout.PAGE_END);
		
		oldNames = new JComboBox<String>();
		oldNames.setPreferredSize(new Dimension(125,30));
		oldNames.setPrototypeDisplayValue("Empty");
		oldNames.isEditable();
		
		//list all modified names
		for (int i=0; i< image.getNamehistory().size(); i++){
			String n = image.getNamehistory().get(i);
			oldNames.addItem(n);
		}
		returnPanel.add(oldNames, BorderLayout.WEST);
		
		//Create return button
		returnBt = new JButton("Return to Previous Name");
		returnBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String n = (String) oldNames.getSelectedItem();
				try {
					image.returnName(n);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				oldNames.addItem(image.getName());
				nameLabel.setText(image.getName());

			}
		});
		
		returnPanel.add(returnBt, BorderLayout.SOUTH);
		this.add(returnPanel, BorderLayout.BEFORE_FIRST_LINE);

		BufferedImage img = null;		
		img = ImageIO.read(new File(image.getParentFile().getAbsolutePath()+ "/" + image.getName()));
		Image dimg = img.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		
		imageLabel = new JLabel(imageIcon);
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		this.add(imagePanel, BorderLayout.CENTER);
		
		nameLabel = new JLabel(image.getName());
		this.add(nameLabel, BorderLayout.SOUTH);
				
		}
}
