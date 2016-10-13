package pruebas;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opencv.core.Core;

import java.io.IOException;

public class PruebaTiempoDeEjecucion {

  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    try {
      long tiempo = Metricas.obtenerPixelesPorSegundo("video.mp4");
      assertTrue(tiempo < 20000000);
    } catch (IOException error) {
      System.out.println(error.getMessage());
    }
  }

}
