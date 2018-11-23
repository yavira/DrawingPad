package gui;

import tool.Tool;

import java.util.EventListener;

public class DrawingCanvas extends ScribbleCanvas {

  // protected
  private DrawingCanvasListener drawingCanvasListener;

  public DrawingCanvas() {
  }

  public void setTool(Tool tool) {
    drawingCanvasListener.setTool(tool);
  }

  public Tool getTool() {
    return drawingCanvasListener.getTool();
  }

  protected EventListener makeCanvasListener() {
    return (drawingCanvasListener = new DrawingCanvasListener(this)); 
  }

}
