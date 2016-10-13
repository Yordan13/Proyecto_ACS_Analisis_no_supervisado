package pruebas;

import futbol.AbstractFileManager;
import futbol.AbstractFrame;
import futbol.AbstractProcesadorImagenes;
import futbol.AbstractVideo;
import futbol.FootballVideo;
import futbol.FutbolFileManager;
import futbol.ProcesadorImagenesFutbol;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.6.0
 */

public class Metricas {

  /**
   * Retorna el tiempo de ejecución por cuadro del análisis de un video.
   * @param ubicacion direccion de almacenamiento del archivo.
   * @return Valor de la ejecución.
   * @throws IOException Excerp
   */
  public static long obtenerPixelesPorSegundo( String ubicacion) throws IOException {
    AbstractFileManager manager = new FutbolFileManager();
    File archivo = manager.open(ubicacion); 
    AbstractVideo video = new FootballVideo(archivo);
    AbstractProcesadorImagenes procesadorImagenes = new ProcesadorImagenesFutbol();
    AbstractFrame imagen;
    
    int cantFrames = video.getCantFrames();
    int counter = 0;
    
    long tiempoInicial = System.currentTimeMillis();
    
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      if (imagen.getDatos().length > 0) {
        imagen = procesadorImagenes.procesar(imagen);
      }
      counter++;
    }
    
    long tiempoFinal = System.currentTimeMillis();
    return UtilImagen.pixelesPorSegundo(tiempoInicial, tiempoFinal, 
        video.getAlto(), video.getAncho(), cantFrames);
  }
  
  public static double obtenerCoeficienteDice(String ubicacion) throws IOException, 
             NoSuchMethodException, SecurityException, IllegalAccessException, 
             IllegalArgumentException, InvocationTargetException {
    AbstractVideo video = UtilImagen.abrirVideo(ubicacion);
    AbstractFrame imagen;
    
    Mat archivoPrueba;
    Mat imagenActual;
    
    int cantFrames = video.getCantFrames();
    int counter = 0;
    
    double coeficiente = 0.0;
    
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      if (imagen.getDatos().length > 0) {
        
        imagenActual = UtilImagen.convertirMat(imagen);
        archivoPrueba = UtilImagen.abrirImagen("outimg\\" + counter + ".jpg");
      
        Mat mascaraJugadores = new Mat();
        Mat campoJuego = UtilImagen.obtenerCampoJuego(imagenActual);
        Mat umbral = UtilImagen.obtenerJugadores(imagenActual, campoJuego);
        Core.bitwise_and(umbral, campoJuego, mascaraJugadores);
        
        archivoPrueba = UtilImagen.obtenerCanal(archivoPrueba);
        
        coeficiente += UtilImagen.coeficienteDice(mascaraJugadores, archivoPrueba);
      }
      counter++;
    }
    return coeficiente / counter;
  }
}
