package pruebas;

import static org.junit.Assert.assertFalse;

import futbol.AbstractFileManager;
import futbol.AbstractVideo;
import futbol.FootballVideo;
import futbol.FutbolFileManager;

import org.junit.Test;
import org.opencv.core.Core;

import java.io.File;


public class PruebaAbrirVideo {

  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    AbstractFileManager manager = new FutbolFileManager();
    File archivo = manager.open("video.mp4"); 
    AbstractVideo video = new FootballVideo(archivo);
    assertFalse(video.esVacido());
  }

}
