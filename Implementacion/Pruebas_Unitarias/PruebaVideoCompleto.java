package pruebas;

import static org.junit.Assert.assertTrue;

import futbol.AbstractFileManager;
import futbol.AbstractFrame;
import futbol.AbstractVideo;
import futbol.FootballVideo;
import futbol.FutbolFileManager;

import org.junit.Test;
import org.opencv.core.Core;

import java.io.File;

public class PruebaVideoCompleto {

	/*
	 * Ejecuta la prueba que analiza la estructura de un video.
	 */
  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    AbstractFileManager manager = new FutbolFileManager();
    File archivo = manager.open("video.mp4");
    AbstractVideo video = new FootballVideo(archivo);
    assertTrue(validaEstructura(video));
  }

  /*
   * Revisa la estructura cuadro por cuadro del video ingresado por 
   * parémetro.
   */
  private boolean validaEstructura(AbstractVideo video) {
    int cantidad = video.getCantFrames();
    boolean result = false;
    int alto = 0;
    int ancho = 0;
    for (int contador = 0; contador < cantidad; contador++) {
      AbstractFrame frame = video.obtenerCuadro();
      if (frame == null) {
        result = false;
        break;
      }
      if( contador > 0 && UtilImagen.esValida(frame) &&(ancho != frame.getAncho() || alto != frame.getAlto())) {
    	  result = false;
    	  break;
      }
      result = true;
      alto = frame.getAlto();
      ancho = frame.getAncho();
    }
    return result;
  }
}
