package futbol;

import java.io.File;

/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.5.1
 */
public abstract class AbstractVideo {
  protected int duracion;
  private int cantFrames;
  private int ancho;
  private int alto;
  private int fps;
  
  /**
   * Incializa los datos vacidos pertenientes a un AbstractVideo.
   */
  public AbstractVideo(File data) {
    duracion = cantFrames = 0;
  }
  
  /**
   * 
   * @return el cuadro actual por analizar del video.
   */
  public abstract AbstractFrame obtenerCuadro();

  public int getFps() {
    return fps;
  }
  
  public void setFps(int fps) {
    this.fps = fps;
  }

  public int getAlto() {
    return alto;
  }

  public void setAlto(int alto) {
    this.alto = alto;
  }

  public int getAncho() {
    return ancho;
  }

  public void setAncho(int ancho) {
    this.ancho = ancho;
  }

  public int getCantFrames() {
    return cantFrames;
  }

  public int setCantFrames(int cantFrames) {
    this.cantFrames = cantFrames;
    return cantFrames;
  }
}
