import futbol.FutbolFileManager;
import futbol.ProcesadorVideoFutbol;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;

public class Main {

  /**
   * Función principal para correr el programa.
   * @param args argumentos enviados por consola.
   */
  public static void main(String[] args) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    FutbolFileManager fileManager = new FutbolFileManager();
    File file = fileManager.open("video.mp4");
    ProcesadorVideoFutbol videoProcesador = new ProcesadorVideoFutbol(file);
    videoProcesador.analizar();
  }

}
