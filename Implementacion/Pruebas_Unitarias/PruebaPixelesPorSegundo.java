package pruebas;

import static org.junit.Assert.assertTrue;

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
   * ejecute o procese 15000000 o más pixeles por segundo.
   */
  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    try {
      long tiempo = Metricas.obtenerPixelesPorSegundo("video.mp4");
      System.out.println(tiempo);
      assertTrue(tiempo > 15000000);
    } catch (IOException error) {
      System.out.println(error.getMessage());
      assertTrue(false);
    }
  }

}
