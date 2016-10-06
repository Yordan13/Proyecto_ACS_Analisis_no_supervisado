package futbol;

import java.io.File;

public abstract class AbstractVideo {
  protected int duracion;
  protected int cantFrames;
  protected int fps;
  
  public AbstractVideo(File data) {
    duracion = cantFrames = 0;
  }
  public abstract AbstractFrame getFrame();
}
