/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

/**
 * This app is designed to:
 * list conserved regions
 * show mismatches
 * visualize sequences (?) 
 * Shrink conserved regions to focus on mismatches (?) 
 * based off of code from docs.oracle.com
 * @author Morgan Chang
 */
class App extends JPanel implements ActionListener
{
  static private String newline = "\n";
    private JTextArea log;
    private JFileChooser fc;
    private JButton processButton;
    private JButton sendButton;
    private String[][] data;
    private File fastaFile;

    public App()
    {
        super(new BorderLayout());
        //Create the log first, because the action listener
        //needs to refer to it.
        log = new JTextArea(5,50);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        sendButton = new JButton("Select FASTA (*.fa) file");
        sendButton.setPreferredSize(new Dimension(200, 25));
        sendButton.addActionListener(this);

        processButton = new JButton("Process FASTA file");
        sendButton.setPreferredSize(new Dimension(200, 25));
        processButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    log.append("Processing file: " + fastaFile.getName() + newline);
                    faProcess fileProcess = new faProcess();
                    data = fileProcess.process(fastaFile);
                }
            });

        add(sendButton, BorderLayout.PAGE_START);
        add(processButton, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.PAGE_END);
    }

    public void actionPerformed(ActionEvent e) 
    {
        //Set up the file chooser.
        if (fc == null) 
        {
            fc = new JFileChooser();

        //Add a custom file filter and disable the default
        //(Accept All) file filter.
            fc.addChoosableFileFilter(new faFileFilter());
            fc.setAcceptAllFileFilterUsed(false);
        }

        //Show it.
        int returnVal = fc.showDialog(App.this,"Attach");

        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            fastaFile = fc.getSelectedFile();
            log.append("Attaching file: " + fastaFile.getName() + "." + newline);
        } 
        else 
        {
            log.append("Attachment cancelled by user." + newline);
        }
        log.setCaretPosition(log.getDocument().getLength());

        //Reset the file chooser for the next time it's shown.
        fc.setSelectedFile(null);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() 
    {
        //Create and set up the window.
        JFrame frame = new JFrame("[CS123B] Final Project App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new App());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) 
    {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    } 
}
