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
 * @author Yordan Jim�nez Hern�ndez
 * @version v0.6.22
 */

public class Metricas {

  /**
   * Retorna el tiempo de ejecuci�n del an�lisis de un video,
   * donde se obtiene el tiempo de ejecuci�n al inicio del an�lisis
   * y tambien al finalizar. Luego se calcula llamando a la funci�n
   * pixelesPorSegundo est�tico de la clase UtilImagen donde se realizar� el cambio
   * perteneciente al resultado de esta funci�n.
   * @param ubicacion direccion de almacenamiento del archivo.
   * @return Valor en milisegundos de la ejecuci�n de un video.
   * @throws IOException Si path enviado es invaido para un video o es vacido.
   */
  public static long obtenerPixelesPorSegundo( String ubicacion) throws IOException {
    AbstractFileManager manager = new FutbolFileManager();
    File archivo = manager.open(ubicacion); 
    AbstractVideo video = new FootballVideo(archivo);
    
    if (video.esVacido()) {
      throw new IOException("El archivo ingresado es vacido o invalido.");
    }
    
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
  
  /**
   *  Aplica el algoritmo de dice a todos los frames pertencientes a un
   *  video alojado en la direcci�n o ubicaci�n que se indica por par�metro.
   * @param ubicacion direcci�n de almacenamiento del video.
   * @return Valor num�rico promedio de Dice de todos los frames del video.
   * @throws IOException Si la ubicaci�n indicada no es de un video o no existe.
   * @throws NoSuchMethodException Si el m�todo no existe, excepci�n de reflexi�n.
   * @throws SecurityException si el m�todo no le pertenece al objeto intentado.
   * @throws IllegalAccessException si no se posee acceso a este m�todo.
   * @throws IllegalArgumentException Argumentos invalidos para ejecutar el proceso.
   * @throws InvocationTargetException Si el metodo invocado lanza una excepci�n.
   */
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
