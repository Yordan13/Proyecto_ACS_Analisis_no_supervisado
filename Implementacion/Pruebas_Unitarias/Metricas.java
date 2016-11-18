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
 * @version v0.6.8
 */

public class Metricas {

  /**
   * Obtiene la cantidad de pixeles que se analizaron al procesar un video por segundo. Por medio de
   * la funciï¿½n pixelesPorSegundo de la clase UtilImagen.
   * 
   * @param ubicacion direccion de almacenamiento del archivo del video.
   * @return Valor entero positivo de pixeles analisados por segundos.
   * @throws IOException Si path enviado es invaido para un video o es vacido.
   */
  public static long obtenerPixelesPorSegundo(String ubicacion) throws IOException {
    AbstractFileManager manager = new FutbolFileManager();
    File archivo = manager.open(ubicacion);
    AbstractVideo video = new FootballVideo(archivo);

    if (video.esVacio()) {
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
    return UtilImagen.pixelesPorSegundo(tiempoInicial, tiempoFinal, video.getAlto(),
        video.getAncho(), cantFrames);
  }

  /**
   * Aplica el algoritmo de dice a todos los frames pertencientes a un video alojado en la direcciï¿½n
   * o ubicaciï¿½n que se indica por parï¿½metro.
   * 
   * @param ubicacion direcciï¿½n de almacenamiento del video.
   * @return Valor numï¿½rico promedio de Dice de todos los frames del video.
   * @throws IOException Si la ubicaciï¿½n indicada no es de un video o no existe.
   * @throws NoSuchMethodException Si el mï¿½todo no existe, excepciï¿½n de reflexiï¿½n.
   * @throws SecurityException si el mï¿½todo no le pertenece al objeto intentado.
   * @throws IllegalAccessException si no se posee acceso a este mï¿½todo.
   * @throws IllegalArgumentException Argumentos invalidos para ejecutar el proceso.
   * @throws InvocationTargetException Si el metodo invocado lanza una excepciï¿½n.
   */
  public static double obtenerCoeficienteDice(String ubicacion)
      throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
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
