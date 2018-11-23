package gui;

import model.Shape;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

public class ScribbleCanvas extends JPanel {

  public boolean mouseButtonDown;
  public int x, y;

  protected List shapes;  //= new ArrayList();
  protected Color curColor;  // = Color.black;
  protected EventListener listener;

  public ScribbleCanvas() {
    // calling factory method 
    listener = makeCanvasListener();
    addMouseListener((MouseListener) listener);
    addMouseMotionListener((MouseMotionListener) listener);
    this.mouseButtonDown =  false;
    this.shapes =  new ArrayList();
    this.curColor = Color.black;
  }

  public Color getCurColor() {
    return curColor;
  }

  public void setCurColor(Color curColor) {
    this.curColor = curColor;
  }

  public void addShape(Shape shape) {
    if (shape != null) {
      shapes.add(shape);
    }
  }

  public void paint(Graphics g) {
    Dimension dim = getSize();
    g.setColor(Color.white);
    g.fillRect(0, 0, dim.width, dim.height);
    g.setColor(Color.black);
    if (shapes != null) {
      Iterator iter = shapes.iterator();
      while (iter.hasNext()) {
        Shape shape = (Shape) iter.next();
        if (shape != null) {
          shape.draw(g);
        }
      }
    }
  }

  public void newFile() {
    shapes.clear();
    repaint();
  }

  public void openFile(String filename) {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
      shapes = (List) in.readObject();
      in.close();
      repaint();
    } catch (IOException e1) {
      System.out.println("Unable to open file: " + filename);
    } catch (ClassNotFoundException e2) {
      System.out.println(e2);
    }
  }

  public void saveFile(String filename) {
    try {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
      out.writeObject(shapes);
      out.close();
      System.out.println("Save drawing to " + filename);
    } catch (IOException e) {
      System.out.println("Unable to write file: " + filename);
    }
  }

  // factory method 
  protected EventListener makeCanvasListener() {
    return (new ScribbleCanvasListener(this));
  }

}

