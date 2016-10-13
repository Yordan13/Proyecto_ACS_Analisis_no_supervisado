package pruebas;

import static org.junit.Assert.assertFalse;

import futbol.AbstractFileManager;
import futbol.FutbolFileManager;

import org.junit.Test;

import java.io.File;


public class PruebaAbrirVideo {

  @Test
  public void test() {
    AbstractFileManager manager = new FutbolFileManager();
    File archivo = manager.open("video.mp4"); 
    assertFalse( !archivo.exists() || archivo.length() == 0);
  }

}
