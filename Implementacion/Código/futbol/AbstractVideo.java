package futbol;

import java.io.File;

public abstract class AbstractVideo {
  protected int duracion;
  private int cantFrames;
  private int ancho;
  private int alto;
  private int fps;
  
  public AbstractVideo(File data) {
    duracion = cantFrames = 0;
  }
  
  public abstract AbstractFrame obtenerCuadro();
  
  /**
   * @return the fps.
   */
  public int getFps() {
    return fps;
  }
  
  /**
   * @param fps the fps to set.
   */
  public void setFps(int fps) {
    this.fps = fps;
  }

  /**
   * @return the alto.
   */
  public int getAlto() {
    return alto;
  }

  /**
   * @param alto the alto to set.
   */
  public void setAlto(int alto) {
    this.alto = alto;
  }

  /**
   * @return the ancho.
   */
  public int getAncho() {
    return ancho;
  }

  /**
   * @param ancho the ancho to set.
   */
  public void setAncho(int ancho) {
    this.ancho = ancho;
  }

  /**
   * @return the cantFrames.
   */
  public int getCantFrames() {
    return cantFrames;
  }

  /**
   * @param cantFrames the cantFrames to set.
   */
  public int setCantFrames(int cantFrames) {
    this.cantFrames = cantFrames;
    return cantFrames;
  }
}
