package gui;

import tool.ScribbleTool;
import tool.Tool;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ScribbleCanvasListener implements MouseListener, MouseMotionListener {

  protected ScribbleCanvas canvas;
  protected Tool tool;

  public ScribbleCanvasListener(ScribbleCanvas canvas) {
    this.canvas = canvas;
    tool = new ScribbleTool(canvas, "Scribble");
  }

  protected ScribbleCanvasListener(ScribbleCanvas canvas, Tool tool) {
    this.canvas = canvas;
    this.tool = tool;
  }

  public void mousePressed(MouseEvent e) {
    Point p = e.getPoint();
    tool.startShape(p);
    canvas.mouseButtonDown = true;
    canvas.x = p.x;
    canvas.y = p.y;
  }

  public void mouseDragged(MouseEvent e) {
    Point p = e.getPoint();
    if (canvas.mouseButtonDown) {
      tool.addPointToShape(p);
      canvas.x = p.x;
      canvas.y = p.y;
    }
  }

  public void mouseReleased(MouseEvent e) {
    Point p = e.getPoint();
    tool.endShape(p);
    canvas.mouseButtonDown = false;
  }

  public void mouseClicked(MouseEvent e) { }

  public void mouseEntered(MouseEvent e) { }

  public void mouseExited(MouseEvent e) { }

  public void mouseMoved(MouseEvent e) { }

}

