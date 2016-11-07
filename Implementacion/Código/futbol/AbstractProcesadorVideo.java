package futbol;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

/**
 * 
 * @author Yordan Jim�nez Hern�ndez
 * @version v0.6.8
 */
public abstract class AbstractProcesadorVideo extends Observable {
  protected int tiempoProcesamiento;
  protected int framesProcesados;
  protected AbstractVideo video;
  protected AbstractFileManager fileManager;
  protected AbstractProcesadorImagenes procesadorImagenes;

  /**
   * Instancia un AbstractProcesadorVideo con los datos vacidos correspondientes excepto videoFile.
   * 
   * @param videoFile es un File por asignar al atributo AbstractVideo de la instancia.
   */
  public AbstractProcesadorVideo(File videoFile) {
    tiempoProcesamiento = framesProcesados = 0;
    fileManager = new FutbolFileManager();
    procesadorImagenes = new ProcesadorImagenesFutbol();
    video = new FootballVideo(videoFile);
  }


  /**
   * Analiza todos los cuadros pertenecientes al video que se ingreso, aplicando los algoritmos
   * correspondientes.
   * 
   * @exception IOException Si el archivo es vacido o incorrecto.
   */
  public abstract void analizar() throws IOException;
}
