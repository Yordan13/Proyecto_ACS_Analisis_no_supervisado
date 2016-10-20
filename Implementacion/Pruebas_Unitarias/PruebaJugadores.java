package pruebas;

import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.6.8
 */
public class PruebaJugadores {

  @Test
  public void test() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    try {
      Mat imagen = UtilImagen.abrirImagen("3.jpg");
      if (!imagen.empty()) {
        Mat campo = UtilImagen.obtenerCampoJuego(imagen);
        imagen = UtilImagen.obtenerJugadores(imagen,campo);
        int positivos = Core.countNonZero(imagen);
        double porcentaje = (double) positivos / (imagen.width() * imagen.height());
        assertTrue(porcentaje > 0.3);
      } else {
        System.out.println("Imagen vacida o incorrecta.");
        assertTrue(false);
      }
    } catch (IOException error) {
      System.out.println("Archivo no existe o dirección incorrecta.");
      System.out.println("Error: " + error.getMessage());
      assertTrue(false);
    } catch (IllegalAccessException error) {
      error.printStackTrace();
    } catch (IllegalArgumentException error) {
      error.printStackTrace();
    } catch (InvocationTargetException error) {
      error.printStackTrace();
    } catch (NoSuchMethodException error) {
      error.printStackTrace();
    } catch (SecurityException error) {
      error.printStackTrace();
    }
  }

}
