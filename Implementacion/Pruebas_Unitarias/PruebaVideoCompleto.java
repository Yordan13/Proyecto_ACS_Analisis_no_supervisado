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

  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    AbstractFileManager manager = new FutbolFileManager();
    File archivo = manager.open("video.mp4");
    AbstractVideo video = new FootballVideo(archivo);
    assertTrue(validaEstructura(video));
  }

  private boolean validaEstructura(AbstractVideo video) {
    int amount = video.getCantFrames();
    boolean result = false;
    for (int i = 0; i < amount; i++) {
      AbstractFrame frame = video.obtenerCuadro();
      if (frame == null) {
        result = false;
        break;
      }
      result = true;

    }
    return result;
  }

}
