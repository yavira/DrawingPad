package gui;

import gui.color.ColorDialog;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JOptionPane;
import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Scribble extends JFrame {

  public static int width = 600;
  public static int height = 400;
  private String currentFilename;
  private ActionListener exitAction;
  private JFileChooser chooser;
  protected JMenuBar menuBar;
  protected ScribbleCanvas canvas;

  public Scribble(String title) {
    super(title);
    // calling factory method 
    canvas = makeCanvas();
    getContentPane().setLayout(new BorderLayout());
    menuBar = createMenuBar();
    getContentPane().add(menuBar, BorderLayout.NORTH);
    getContentPane().add(canvas, BorderLayout.CENTER);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if (exitAction != null) {
          exitAction.actionPerformed(new ActionEvent(Scribble.this, 0, null));
        }
      }
    });
    this.currentFilename = "";
    this.chooser = new JFileChooser(".");
  }

  protected JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu;
    JMenuItem menuItem;

    // File menu 
    menu = new JMenu("File");
    menuBar.add(menu);

    menuItem = new JMenuItem("New");
    menu.add(menuItem);
//    menuItem.addActionListener(new NewFileListener());
    menuItem.addActionListener( e -> newFile());

    menuItem = new JMenuItem("Open");
    menu.add(menuItem);
    menuItem.addActionListener(e -> openFileListener());

    menuItem = new JMenuItem("Save");
    menu.add(menuItem);
    menuItem.addActionListener(e -> saveFile());

    menuItem = new JMenuItem("Save As");
    menu.add(menuItem);
    menuItem.addActionListener(e -> saveAsFileListener());

    menu.add(new JSeparator());

    exitAction = (e -> exitListener());
    menuItem = new JMenuItem("Exit");
    menu.add(menuItem);
    menuItem.addActionListener(exitAction);

    // option menu
    menu = new JMenu("Option");
    menuBar.add(menu);

    menuItem = new JMenuItem("Color");
    menu.add(menuItem);
    menuItem.addActionListener(e -> colorListener());

    // horizontal space 
    menuBar.add(Box.createHorizontalGlue());

    // Help menu 
    menu = new JMenu("Help");
    menuBar.add(menu);

    menuItem = new JMenuItem("About");
    menu.add(menuItem);
    menuItem.addActionListener(e -> aboutListener());

    return menuBar;
  }

  // factory method 
  protected ScribbleCanvas makeCanvas() {
    return new ScribbleCanvas();
  }

  protected void newFile() { // repaint- reboot
    currentFilename = null;
    canvas.newFile();
    setTitle("Scribble Pad");
  }

  protected void openFile(String filename) {
    currentFilename = filename;
    canvas.openFile(filename);
    setTitle("Scribble Pad [" + currentFilename + "]");
  }

  protected void saveFile() {
    if (currentFilename == null) {
      currentFilename = "Untitled";
    }
    canvas.saveFile(currentFilename);
    setTitle("Scribble Pad [" + currentFilename + "]");
  }

  protected void saveFileAs(String filename) {
    currentFilename = filename;
    canvas.saveFile(filename);
    setTitle("Scribble Pad [" + currentFilename + "]");
  }

//  class NewFileListener implements ActionListener {
//
//    public void actionPerformed(ActionEvent e) {
//      newFile();
//    }
//
//  }

  private void openFileListener() {
    int retval = chooser.showDialog(null, "Open");
    if (retval == JFileChooser.APPROVE_OPTION) {
      File theFile = chooser.getSelectedFile();
      if (theFile != null) {
        if (theFile.isFile()) {
          String filename = chooser.getSelectedFile().getAbsolutePath();
          openFile(filename);
        }
      }
    }
  }

//  class OpenFileListener implements ActionListener {
//
//    public void actionPerformed(ActionEvent e) {
//      int retval = chooser.showDialog(null, "Open");
//      if (retval == JFileChooser.APPROVE_OPTION) {
//        File theFile = chooser.getSelectedFile();
//        if (theFile != null) {
//          if (theFile.isFile()) {
//            String filename = chooser.getSelectedFile().getAbsolutePath();
//            openFile(filename);
//          }
//        }
//      }
//    }
//
//  }



//  class SaveFileListener implements ActionListener {
//
//    public void actionPerformed(ActionEvent e) {
//      saveFile();
//    }
//  }

  private void saveAsFileListener() {
    int retval = chooser.showDialog(null, "Save As");
    if (retval == JFileChooser.APPROVE_OPTION) {
      File theFile = chooser.getSelectedFile();
      if (theFile != null) {
        if (!theFile.isDirectory()) {
          String filename = chooser.getSelectedFile().getAbsolutePath();
          saveFileAs(filename);
        }
      }
    }
  }

//  class SaveAsFileListener implements ActionListener {
//
//    public void actionPerformed(ActionEvent e) {
//      int retval = chooser.showDialog(null, "Save As");
//      if (retval == JFileChooser.APPROVE_OPTION) {
//        File theFile = chooser.getSelectedFile();
//        if (theFile != null) {
//          if (!theFile.isDirectory()) {
//            String filename = chooser.getSelectedFile().getAbsolutePath();
//            saveFileAs(filename);
//          }
//        }
//      }
//    }
//
//  }

  private void exitListener() {
    int result = JOptionPane.showConfirmDialog(null,
      "Do you want to exit Scribble Pad?",
      "Exit Scribble Pad?",
      JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      saveFile();
      System.exit(0);
    }
  }

//  class ExitListener implements ActionListener {
//
//    public void actionPerformed(ActionEvent e) {
//      int result = JOptionPane.showConfirmDialog(null,
//        "Do you want to exit Scribble Pad?",
//        "Exit Scribble Pad?",
//        JOptionPane.YES_NO_OPTION);
//      if (result == JOptionPane.YES_OPTION) {
//        saveFile();
//        System.exit(0);
//      }
//    }
//
//  }

  private void colorListener() {
    ColorDialog dialog = new ColorDialog(Scribble.this, "Choose color", canvas.getCurColor());
    Color result = dialog.showDialog();
    if (result != null) {
      canvas.setCurColor(result);
    }
  }

//  class ColorListener implements ActionListener {
//
//    protected ColorDialog dialog = new ColorDialog(Scribble.this, "Choose color", canvas.getCurColor());
//
//    public void actionPerformed(ActionEvent e) {
//      Color result = dialog.showDialog();
//      if (result != null) {
//        canvas.setCurColor(result);
//      }
//    }
//  }

  private void aboutListener() {
    JOptionPane.showMessageDialog(null,
      "draw.DrawingPad version 1.0\nCopyright (c) Xiaoping Jia 2002", "About",
      JOptionPane.INFORMATION_MESSAGE);
  }

//  class AboutListener implements ActionListener {
//    public void actionPerformed(ActionEvent e) {
//      JOptionPane.showMessageDialog(null,
//        "draw.DrawingPad version 1.0\nCopyright (c) Xiaoping Jia 2002", "About",
//        JOptionPane.INFORMATION_MESSAGE);
//    }
//  }

}
