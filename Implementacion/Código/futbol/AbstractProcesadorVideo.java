package futbol;

import java.io.File;
import java.io.IOException;
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
    video = new FootballVideo(videoFile);
  }
  
  public abstract AbstractVideo analizar()throws IOException;
}
