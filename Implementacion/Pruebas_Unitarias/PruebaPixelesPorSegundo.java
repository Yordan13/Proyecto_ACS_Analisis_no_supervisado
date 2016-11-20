package pruebas;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.opencv.core.Core;

import java.io.IOException;

/**
 * 
 * @author Yordan Jim�nez Hern�ndez
 * @version v0.6.8
 */
public class PruebaPixelesPorSegundo {

  /**
   * Se espera que la ejecuci�n normal de un analisis de un video de futbol,
   * ejecute o procese 4000000 o m�s pixeles por segundo.
   */
  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    try {
      long tiempo = Metricas.obtenerPixelesPorSegundo("video.mp4");
      assertTrue(tiempo > 4000000);
    } catch (IOException error) {
      System.out.println(error.getMessage());
      assertTrue(false);
    }
  }
  
  /**
   * Se espera que la ejecuci�n de un analisis con una direcci�n invalida falle.
   */
  @Test
  public void test2() {
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    try {
	      long tiempo = Metricas.obtenerPixelesPorSegundo("videomp4");
	      fail("Error deber�a fallar");
	    } catch (IOException error) {
	      assertTrue(true);
	    }
	  }
}
