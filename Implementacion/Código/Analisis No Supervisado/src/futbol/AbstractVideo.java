package futbol;

import java.io.File;

import org.opencv.core.Mat;

public abstract class AbstractVideo {
  protected int duracion;
  private int cantFrames;
  private int width;
  private int height;
  private int fps;
  
  public AbstractVideo(File data) {
    duracion = setCantFrames(0);
  }
  
  /**
   * @return the fps
   */
  public int getFps() {
    return fps;
  }
  
  /**
   * @param fps the fps to set
   */
  public void setFps(int fps) {
    this.fps = fps;
  }
  
  public abstract AbstractFrame getFrame();

  /**
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * @param height the height to set
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * @param width the width to set
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * @return the cantFrames
   */
  public int getCantFrames() {
    return cantFrames;
  }

  /**
   * @param cantFrames the cantFrames to set
   */
  public int setCantFrames(int cantFrames) {
    this.cantFrames = cantFrames;
    return cantFrames;
  }
  
}
