package gui.color;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorPanel extends JPanel {

  private static final Color[][] colorGrid = {
    {Color.white, Color.lightGray, Color.darkGray, Color.black},
    {Color.gray, Color.blue, Color.cyan, Color.green},
    {Color.yellow, Color.orange, Color.pink, Color.red},
    {Color.magenta, new Color(230, 230, 250), new Color(0, 0, 128), new Color(64, 224, 208)}};
  private Color color;
  private int cellWidth;
  private int cellHeight;
  private int rowCount;
  private int columnCount;
  private int xpad;
  private int ypad;
  private Dimension dimension;

  public ColorPanel(int cellWidth, int cellHeight, int xpad, int ypad) {
    if (cellWidth < 5) {
      cellWidth = 5;
    }
    if (cellHeight < 5) {
      cellHeight = 5;
    }
    if (xpad < 2) {
      xpad = 2;
    }
    if (ypad < 2) {
      ypad = 2;
    }
    this.cellWidth = cellWidth;
    this.cellHeight = cellHeight;
    this.xpad = xpad;
    this.ypad = ypad;
    rowCount = colorGrid.length;
    columnCount = colorGrid[0].length;
    dimension = new Dimension((cellWidth + xpad) * columnCount + xpad,
      (cellHeight + ypad) * (rowCount + 1) + ypad);
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent event) {
        Point p = event.getPoint();
        int i = (p.y / (ColorPanel.this.cellHeight + ColorPanel.this.ypad));
        int j = (p.x / (ColorPanel.this.cellWidth + ColorPanel.this.xpad));
        if (i < rowCount &&
          j < columnCount) {
          color = colorGrid[i][j];
          repaint();
        }
      }
    });
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Dimension getMinimumSize() {
    return dimension;
  }

  public Dimension getPreferredSize() {
    return dimension;
  }

  public void paint(Graphics g) {
    Dimension dim = getSize();
    g.setColor(Color.lightGray);
    g.fillRect(0, 0, dim.width, dim.height);
    int x, y;
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < columnCount; j++) {
        x = (cellWidth + xpad) * j + xpad;
        y = (cellHeight + ypad) * i + ypad;
        g.setColor(colorGrid[i][j]);
        g.fillRect(x, y, cellWidth, cellHeight);
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
      }
    }
    x = xpad;
    y = (cellHeight + ypad) * rowCount + ypad;
    int width = (cellWidth + xpad) * columnCount - xpad;
    g.setColor(color);
    g.fillRect(x, y, width, cellHeight);
    g.setColor(Color.black);
    g.drawRect(x, y, width, cellHeight);
  }

}
