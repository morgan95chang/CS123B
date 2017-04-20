/* 
 * faProcess takes in a FASTA file
 * and returns it in a 2D array 
 * @author Morgan Chang
 */

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class faProcess
{
	private String[][] data;
	BufferedReader in;

	public String[][] process(File f)
	{
		System.out.println("Processing " + f.getName());
		try 
			{
				in = new BufferedReader(new FileReader(f));
				String c;

				in.close();
			}
		catch (IOException e)
			{
				e.printStackTrace();
			}
		return data;
	}
}