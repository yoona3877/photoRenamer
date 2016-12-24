package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Yoon A Park, Hamin Lee
 * A JFrame GUI containing removeBt and a JCombobox of listTags. This class will allow the 
 * user to easily remove the existing tags in TagManager.
 * 
 * Singleton Design Pattern is used
 * This is created only once and public static getInstance() method 
 * provides a global point of access to RemoveTagGUI
 *
 */

@SuppressWarnings("serial")
public class RemoveTagGUI extends JFrame{


	/** The instance this GUI will be referring to. */
	private static RemoveTagGUI instance;
	/** The JButtons RemoveTagGUI contains */
	private JButton removeBt;
	/** The JComboBox RemoveTagGUI contains */
	private JComboBox<String> listTags;
	/** The JPanel RemoveTagGUI is divided into */
	private JPanel removepanel;
	
	public static RemoveTagGUI getInstance() throws IOException {
        return instance = new RemoveTagGUI("Remove tags");
    }
	/**
	 * Set up a RemoveTagGUI Framework.
	 * @param title
	 * 			the title
	 */
	private RemoveTagGUI(String title){
		super(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Build a JFrame RemoveTagGUI main window.
	 */
	public void buildWindow(){
		removepanel = new JPanel();
		
		//combo box showing list of tags
		listTags = new JComboBox<String>();
		listTags.setPreferredSize(new Dimension(125,30));
		listTags.setPrototypeDisplayValue("Empty");
		listTags.isEditable();
		for (int i =0; i < TagManager.getTags().size(); i ++){
			String tag = TagManager.getTags().get(i);
			listTags.addItem(tag);
		}
		removepanel.add(listTags, BorderLayout.WEST);
		
		//remove button
		removeBt = new JButton("remove");
		removeBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String wantToRemove = (String) listTags.getSelectedItem();
				TagManager.remove(wantToRemove);
				int index = listTags.getSelectedIndex();
				listTags.removeItemAt(index);
			}
		});
		removepanel.add(removeBt);

		this.add(removepanel, BorderLayout.CENTER);
		this.pack();
		
	}
}
