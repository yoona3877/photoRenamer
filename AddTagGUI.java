package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 
 * @author Yoon A Park, Hamin Lee
 * 
 * A user-friendly GUI containing a addTag button which allows the user to add tag 
 * by inputting the tag in the text field. 
 * 
 * Singleton Design Pattern is used
 * This is created only once and public static getInstance() method 
 * provides a global point of access to AddTagGUI
 *
 */
@SuppressWarnings("serial")
public class AddTagGUI extends JFrame{
	private static AddTagGUI instance;
	/** The JTextField AddTagGUI contains. */
	private JTextField tagNameTf;
	/** The JButton AddTagGUI contains. */
	private JButton addBt;
	/** The JPanel AddTagGUI is divided into.*/
	private JPanel addpanel;
	
	public static AddTagGUI getInstance() throws IOException {
        return instance = new AddTagGUI("Add tags");
    }
	/**
	 * Set up a AddTagGUI Framework.
	 * @param title
	 * 			the title of this window
	 */
	private AddTagGUI(String title){
		super(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Build a JFrame AddTagGUI main window.
	 */
	public void buildWindow(){
		addpanel = new JPanel();
		
		//text field
		tagNameTf = new JTextField(10);
		addpanel.add(tagNameTf);
		
		//add button
		addBt = new JButton("Add");
		addBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String tagName = tagNameTf.getText();
				if (TagManager.getTags().contains(tagName) == false){
					TagManager.add(tagName);
				}
				tagNameTf.setText("");	
			}
		});
		addpanel.add(addBt);
		
		this.add(addpanel, BorderLayout.CENTER);
		this.pack();
		
	}
}
