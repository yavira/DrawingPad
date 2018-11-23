
package tool;

import java.awt.Point;

public interface Tool {

   String getName();
   void startShape(Point p);
   void endShape(Point p);
   void addPointToShape(Point p);
}
