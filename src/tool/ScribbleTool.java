package tool;

import gui.ScribbleCanvas;
import model.Stroke;

import java.awt.Point;
import java.awt.Graphics;

public class ScribbleTool extends AbstractTool {

  protected Stroke curStroke;

  public ScribbleTool(ScribbleCanvas canvas, String name) {
    super(canvas, name);
    this.curStroke = null;
  }

  public void startShape(Point p) {
    curStroke = new Stroke(canvas.getCurColor());
    curStroke.addPoint(p);
  }

  public void addPointToShape(Point p) {
    if (curStroke != null) {
      curStroke.addPoint(p);
      Graphics g = canvas.getGraphics();
      g.setColor(canvas.getCurColor());
      g.drawLine(canvas.x, canvas.y, p.x, p.y);
    }
  }

  public void endShape(Point p) {
    if (curStroke != null) {
      curStroke.addPoint(p);
      canvas.addShape(curStroke);
      curStroke = null;
    }
  }

}
