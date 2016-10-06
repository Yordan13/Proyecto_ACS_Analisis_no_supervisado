import futbol.FutbolFileManager;
import futbol.ProcesadorVideoFutbol;

import org.opencv.core.Core;

import java.io.File;

public class Main {

  public static void main(String[] args) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    try {
      FutbolFileManager fileManager = new FutbolFileManager();
      File file = fileManager.open("C:\\Users\\fm010\\OneDrive\\TEC\\GitHub\\AnalisisVideosFutbol\\video.mp4");
      ProcesadorVideoFutbol videoProcesador = new ProcesadorVideoFutbol(file);
      videoProcesador.analizar();    
    }catch(Exception e) {
        
    }
  }

}
