package Futbol;

import java.io.File;
import java.util.Observable;

public abstract class AbstractProcesadorVideo extends Observable {
  protected int tiempoProcesamiento;
  protected int framesProcesados;
  protected AbstractVideo video;
  protected AbstractFileManager fileManager;
  protected AbstractProcesadorImagenes procesadorImagenes;
  
  
  public AbstractProcesadorVideo(File videoFile){
    tiempoProcesamiento = framesProcesados = 0;
    fileManager = new FutbolFileManager();
    procesadorImagenes = new ProcesadorImagenesFutbol();
  }
  
  public abstract AbstractVideo analizar();
}
