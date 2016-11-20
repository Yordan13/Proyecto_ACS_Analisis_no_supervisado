package pruebas;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.opencv.core.Core;

import java.io.IOException;

/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.6.8
 */
public class PruebaPixelesPorSegundo {

  /**
   * Se espera que la ejecución normal de un analisis de un video de futbol,
   * ejecute o procese 4000000 o más pixeles por segundo.
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
   * Se espera que la ejecución de un analisis con una dirección invalida falle.
   */
  @Test
  public void test2() {
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    try {
	      long tiempo = Metricas.obtenerPixelesPorSegundo("videomp4");
	      fail("Error debería fallar");
	    } catch (IOException error) {
	      assertTrue(true);
	    }
	  }
}
