package tool;

import gui.ScribbleCanvas;
import model.LineShape;
import model.OvalShape;
import model.RectangleShape;
import model.TwoEndsShape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class TwoEndsTool extends AbstractTool {

  private static final int LINE = 0;
  private static final int OVAL = 1;
  private static final int RECT = 2;

  protected int shape;
  protected int xStart;
  protected int yStart;

  public TwoEndsTool(ScribbleCanvas canvas, String name, int shape) {
    super(canvas, name);
    this.shape = shape; 
  }
 
  public void startShape(Point p) {
    canvas.mouseButtonDown = true;
    xStart = canvas.x = p.x; 
    yStart = canvas.y = p.y; 
    Graphics g = canvas.getGraphics();
    g.setXORMode(Color.darkGray); 
    g.setColor(Color.lightGray); 
    switch (shape) {
    case LINE:
      drawLine(g, xStart, yStart, xStart, yStart); 	       
      break; 
    case OVAL:
      drawOval(g, xStart, yStart, 1, 1); 	       
      break; 
    case RECT:
      drawRect(g, xStart, yStart, 1, 1); 
      break; 
    }
  }

  public void addPointToShape(Point p) {
    if (canvas.mouseButtonDown) {
      Graphics g = canvas.getGraphics();
      g.setXORMode(Color.darkGray); 
      g.setColor(Color.lightGray); 
      switch (shape) {
      case LINE:
        drawLine(g, xStart, yStart, canvas.x, canvas.y); 
        drawLine(g, xStart, yStart, p.x, p.y); 
        break; 
      case OVAL:
        drawOval(g, xStart, yStart, canvas.x - xStart + 1, canvas.y - yStart + 1); 
        drawOval(g, xStart, yStart, p.x - xStart + 1, p.y - yStart + 1);  
        break; 
      case RECT:
        drawRect(g, xStart, yStart, canvas.x - xStart + 1, canvas.y - yStart + 1);  
        drawRect(g, xStart, yStart, p.x - xStart + 1, p.y - yStart + 1); 
        break; 
      }
      canvas.x = p.x; 
      canvas.y = p.y; 
    } 
  }

  public void endShape(Point p) {
    canvas.mouseButtonDown = false; 
    TwoEndsShape newShape = null;
    switch (shape) {
    case LINE:
      newShape = new LineShape();
      break; 
    case OVAL:
      newShape = new OvalShape();
      break;
    case RECT:
      newShape = new RectangleShape();
    }
    if (newShape != null) { 
      newShape.setColor(canvas.getCurColor());
      newShape.setEnds(xStart, yStart, p.x, p.y); 
      canvas.addShape(newShape);
    } 
    Graphics g = canvas.getGraphics();
    g.setPaintMode();
    canvas.repaint();
  }

  // helper methods 
  private static void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
    g.drawLine(x1, y1, x2, y2); 
  } 

  private static void drawRect(Graphics g, int x, int y, int w, int h) {
    if (w < 0) {
      x = x + w; 
      w = -w; 
    }
    if (h < 0) {
      y = y + h;
      h = -h;
    }
    g.drawRect(x, y, w, h); 
  }
  
  private static void drawOval(Graphics g, int x, int y, int w, int h) {
    if (w < 0) {
      x = x + w; 
      w = -w; 
    }
    if (h < 0) {
      y = y + h;
      h = -h;
    }
    g.drawOval(x, y, w, h); 
  }

  public static int getLINE() {
    return LINE;
  }

  public static int getOVAL() {
    return OVAL;
  }

  public static int getRECT() {
    return RECT;
  }
}
