package com.company;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Studio extends JFrame implements ActionListener {
    // Text component
    static JTextArea textArea;
    static JTextField find;
    // Frame
    JFrame frame;
    // Color
    static Color backColor;
    static Color foreColor;

    // Constructor
    Studio() {
        // Create a frame
        frame = new JFrame("CodeStudio");
        find = new JTextField(20);

        find.setName("find");

        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception ignored) {
        }

        // Text component
        textArea = new JTextArea();

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a menu for menu
        JMenu menu = new JMenu("File");

        // Create menu items
        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi9 = new JMenuItem("Print");

        // Add action listener
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);

        menu.add(mi1);
        menu.add(mi2);
        menu.add(mi3);
        menu.add(mi9);

        // Create a menu for menu
        JMenu menu1 = new JMenu("Edit");

        // Create menu items
        JMenuItem cut = new JMenuItem("cut");
        JMenuItem copy = new JMenuItem("copy");
        JMenuItem paste = new JMenuItem("paste");

        // Add action listener
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);


        // Set theme
        JMenu menu2 = new JMenu("Theme");

        JMenuItem lightTheme = new JMenuItem("Light Theme");
        JMenuItem darkTheme = new JMenuItem("Dark Theme");

        lightTheme.addActionListener(this);
        darkTheme.addActionListener(this);


        //
        find.addActionListener(actionEvent -> findText());

        menu2.add(lightTheme);
        menu2.add(darkTheme);


        menu1.add(cut);
        menu1.add(copy);
        menu1.add(paste);


        menuBar.add(menu);
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(find);


        frame.setJMenuBar(menuBar);
        frame.add(textArea);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void setTheme(boolean b){
        if (!b) {
            backColor = new Color(255, 255, 255);
            foreColor = new Color(0,0,0);

            textArea.setBackground(backColor);
            textArea.setForeground(foreColor);
            textArea.setCaretColor(foreColor);
        } else {
            backColor = new Color(50,50,50);
            foreColor = new Color(255,255,255);

            textArea.setBackground(backColor);
            textArea.setForeground(foreColor);
            textArea.setCaretColor(foreColor);
        }
    }

    // If a button is pressed
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Dark Theme":
                setTheme(true);
                break;
            case ("Light Theme"):
                setTheme(false);
                break;
            case "cut":
                textArea.cut();
                break;
            case "copy":
                textArea.copy();
                break;
            case "paste":
                textArea.paste();
                break;
            case "Save":
                save();
                break;
            case "Print":
                try {
                    // print the file
                    textArea.print();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
                break;
            case "Open":
                open();
                break;
            case "New":
                textArea.setText("");
                break;
        }
    }

    void findText(){
        try{
            Highlighter highlighter = textArea.getHighlighter();
            String text = find.getText();
            Pattern pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
            String text1 = textArea.getText();
            Matcher matcher = pattern.matcher(text);
            boolean matchfound = matcher.find();
             int pos = 0;
             while((pos = text1.indexOf(text, pos)) >= 0) {
                 highlighter.addHighlight(pos, pos + text.length(),
                         new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
                 pos += text.length();
             }
             // redundant
            if(matchfound){
                System.out.println("Match Founds");
            }else {
                System.out.println("Match not Found");
            }
        }catch ( BadLocationException e){
            e.printStackTrace();
        }

    }


    void save(){

        // Create an object of JFileChooser class
        JFileChooser j = new JFileChooser("f:");

        // Invoke the showsSaveDialog function to show the save dialog
        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {

            // Set the label to the path of the selected directory
            File file = new File(j.getSelectedFile().getAbsolutePath());

            try {
                // Create a file writer
                FileWriter fileWriter = new FileWriter(file, false);

                // Create buffered writer to write
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // Write
                bufferedWriter.write(textArea.getText());

                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(frame, evt.getMessage());
            }
        }
        // If the user cancelled the operation
        else
            JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
    }

    void open(){

        // Create an object of JFileChooser class
        JFileChooser j = new JFileChooser("f:");

        // Invoke the showsOpenDialog function to show the save dialog
        int r = j.showOpenDialog(null);

        // If the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            // Set the label to the path of the selected directory
            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                // String
                String s1;
                StringBuilder sl;

                // File reader
                FileReader fr = new FileReader(fi);

                // Buffered reader
                BufferedReader br = new BufferedReader(fr);

                // Initilize sl
                sl = new StringBuilder(br.readLine());

                // Take the input from the file
                while ((s1 = br.readLine()) != null) {
                    sl.append("\n").append(s1);
                }

                // Set the text
                textArea.setText(sl.toString());
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(frame, evt.getMessage());
            }
        }
        // If the user cancelled the operation
        else
            JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
    }

}
