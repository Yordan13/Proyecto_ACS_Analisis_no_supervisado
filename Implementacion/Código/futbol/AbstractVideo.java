package futbol;

import java.io.File;

/**
 * 
 * @author Yordan Jimï¿½nez Hernï¿½ndez
 * @version v0.6.8
 */
public abstract class AbstractVideo {
  private int cantFrames;
  private int ancho;
  private int alto;
  private int fps;

  /**
   * Incializa los datos vacíos pertenientes a un AbstractVideo.
   */
  public AbstractVideo(File data) {
    cantFrames = 0;
  }

  /**
   * 
   * @return el cuadro actual por analizar del video.
   */
  public abstract AbstractFrame obtenerCuadro();

  /**
   * Valida si el video es vacío o invalido.
   * 
   * @return Bool que indica que el video es vacío.
   */
  public abstract boolean esVacio();

  /**
   * Obtiene un valor positivo de la cantidad de frames por segundos del video.
   * 
   * @return Un valor entero positivo que representa los fps del video ingresado al instanciar.
   */
  public int getFps() {
    return fps;
  }

  /**
   * Asigna un valor entero positivo al atributo de frames por segundo. Caso atï¿½pico que el valor
   * sea 0.
   * 
   * @param fps nï¿½mero de frames por segundo debe ser positivo mayor que 0.
   */
  public void setFps(int fps) {
    this.fps = fps;
  }

  /**
   * Retorna el valor entero del alto de los cuadros del video. Atipico este valor sea 0 o menor.
   * 
   * @return Valor entero positivo del alto de los cuadros del video.
   */
  public int getAlto() {
    return alto;
  }

  /**
   * Asigna el valor del alto de los cuadros del video
   * 
   * @param alto Valor entero del alto de los cuadros del video, debe ser mayor que 0.
   */
  public void setAlto(int alto) {
    this.alto = alto;
  }

  /**
   * Retorna el valor entero del ancho de los cuadros del video. Atipico este valor sea 0 o menor.
   * 
   * @return Valor entero positivo del ancho de los cuadros del video.
   */
  public int getAncho() {
    return ancho;
  }

  /**
   * Asigna el valor del ancho de los cuadros del video
   * 
   * @param ancho Valor entero del alto de los cuadros del video, debe ser mayor que 0.
   */
  public void setAncho(int ancho) {
    this.ancho = ancho;
  }

  /**
   * Retorna un valor entero positivo que representa la cantidad de frames pertenecientes al video
   * ingresado. Atï¿½pico este valor serï¿½ 0.
   * 
   * @return Valor entero que repreta la cantidad de frames del video.
   */
  public int getCantFrames() {
    return cantFrames;
  }

  /**
   * Asigna un valor entero positivo que representa la cantidad de frames pertenecientes al video
   * ingresado. Atï¿½pico este valor serï¿½ 0.
   */
  public void setCantFrames(int cantFrames) {
    this.cantFrames = cantFrames;
  }
}
