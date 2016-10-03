package Futbol;

import java.io.File;

import org.opencv.core.Mat;

public abstract class AbstractVideo {
  protected int duracion;
  protected int cantFrames;
  protected int fps;
  
  public AbstractVideo(File data) {
    duracion = cantFrames = 0;
  }
  public abstract AbstractFrame getFrame();
}
