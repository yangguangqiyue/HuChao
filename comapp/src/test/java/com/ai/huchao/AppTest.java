package com.ai.huchao;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {   
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	
        assertTrue( true );
    }
    /**Tests flags had done for 'ls' command*/
    public void testls() throws IOException{
    	App ap=new App();
    	App.flags=1;
	    int x=ap.Jsonls(); 
	    assertEquals(1, x); 
    }
  /**Tests entry and use 'cd' command to go to the entry */
    public void testcd() throws IOException{
    	App ap=new App();
    	App.flags=1;
    	String result="entries";
	    int x=ap.Jsoncd(result); 
	    assertEquals(1, x); 
    }
    /**Tests whether or not 'cat' command can display the item data*/
    public void testcat() throws IOException{
    	App ap=new App();
    	App.flags=1;
    	String name="hanmeimei";
	    int x=ap.Jsoncat(name); 
	    assertEquals(1, x); 
	    String name2="xiaowei";
	    int y=ap.Jsoncat(name2); 
	    assertEquals(0, y);
    }
    /**Tests whether or not exist duplicated key,if not 'add' command will add item add*/
    public void testadd() throws IOException{
    	App ap=new App();
    	App.flags=1;
    	String inkey="lilei";
    	String invalue="{\"age\":26,\"mobile\":\"13700000001\",\"address\":\"Earth somewhere else\"}";
	    int x=ap.Jsonadd(inkey, invalue); 
	    assertEquals(0, x); 
    }
    /**Tests remove command,if the key exist,delete it.*/
    public void testremove() throws IOException{
    	App ap=new App();
    	App.flags=1;
    	String inkey="lilei";
	    int x=ap.Jsonremove(inkey); 
	    assertEquals(1, x); 
	    String inkey2="xiaowei";
	    int y=ap.Jsonremove(inkey2); 
	    assertEquals(0, y); 
    }
}
