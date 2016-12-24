package photo_renamer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Hamin Lee, Yoon A Park 
 * 
 * Test the TagManger class.
 *
 */

public class TagManagerTest {
	
	/** The TagManager to be tested*/
	private TagManager tm;
	
	
	/**
	 * Create a new TagManager.
	 * @throws Exception
	 */
	@Before
    public void setUp() throws Exception {
        tm = new TagManager();
    }
	/**
	 * Test empty test case.
	 */
	@Test
	public void testEmptyTestcase(){
		tm.setTags(new ArrayList<String>());
		boolean expected = true;
		boolean result = tm.isEmpty();
		assertEquals(expected, result);
	}
	
	/**
	 * Add tags and test whether the tags are added or not.
	 */
	@Test
    public void testTagManager() {
        tm.add("CSC207");
        tm.add("Raptors"); 
        tm.add("Hamin"); 
        Set<String> expectedTags = new HashSet<String>();
        expectedTags.add("CSC207"); 
        expectedTags.add("Raptors");
        expectedTags.add("Hamin");
        // checks if the tags are added properly
        for(String s: expectedTags) {
            if (!(tm.getTags().contains(s))) {
                fail("Expected Tags does not match with the tags added");
            }
        }
    }
	
	/**
	 * Add tags and check if it is added. Also, check if the tags are restored when reinitialized.
	 */
    @Test
    public void testAdd() {
        tm.add("Hamin");
        tm.add("Yoona");
        tm.add("Amir");
        // checks if the tags are added
        assertTrue(tm.getTags().contains("Hamin"));
        assertTrue(tm.getTags().contains("Yoona"));
        assertTrue(tm.getTags().contains("Amir"));
        
        // Create another TagManager to assure that the tags are saved in the configuration file.
        TagManager tm2 = new TagManager();
        assertTrue(tm2.getTags().contains("Hamin"));
        assertTrue(tm2.getTags().contains("Yoona"));
        assertTrue(tm2.getTags().contains("Amir"));
    }
//    @After	
//    public void 
    
   /**
    * Add and remove tags and test if the tags are removed and added. Also, check if the tags are restored when reinitialized.
    */
    @Test
    public void testRemove() {
        tm.add("Raptors");
        tm.add("Yoona");
        tm.add("pennyboard");
        tm.add("CSC207");
        tm.remove("apple");
        tm.remove("pennyboard");
        // checks if the tags are added
        assertFalse(tm.getTags().contains("apple"));
        assertFalse(tm.getTags().contains("pennyboard"));
        assertTrue(tm.getTags().contains("Raptors"));
        assertTrue(tm.getTags().contains("CSC207"));
        assertTrue(tm.getTags().contains("Yoona"));

        
        
        // Create another TagManager to assure that the tags are saved in the configuration file.
        TagManager tm2 = new TagManager();
        assertFalse(tm.getTags().contains("apple"));
        assertFalse(tm.getTags().contains("pennyboard"));
        assertTrue(tm.getTags().contains("Raptors"));
        assertTrue(tm.getTags().contains("CSC207"));
        assertTrue(tm.getTags().contains("Yoona"));
        
    }
	

    
}




