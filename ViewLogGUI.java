package photo_renamer;


import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * 
 * @author Hamin Lee, Yoon A Park
 * A GUI of the log text file. This JFrame will pop up when user clicks on viewLog button
 * on PhotoRenamer. It contains all the log of the modified names.
 *
 */

@SuppressWarnings("serial")
public class ViewLogGUI extends JFrame{
	
	/** The JTextArea ViewLogGUI contains.*/
	private JTextArea textarea;
	
	/**
	 * Set up a ViewLogGUI Framework.
	 * @param title
	 */
	public ViewLogGUI(String title){
		super(title);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(1000,1000);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Build a JFrame ViewLogGUI main window.
	 */
	public void buildWindow(){
		textarea = new JTextArea();
		for (int i =0; i<NameHistory.getNameHistory().size(); i++){
			textarea.append(NameHistory.getNameHistory().get(i) + "\n");
		}
		this.add(textarea);
	}
	
}
