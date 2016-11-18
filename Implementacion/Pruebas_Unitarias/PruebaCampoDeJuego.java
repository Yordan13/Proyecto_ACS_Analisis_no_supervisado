package pruebas;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.6.8
 */
public class PruebaCampoDeJuego {

  /**
   * Se espera que el porcentaje d ecampo de juego detectado sea mayor
   * a un 60% de los pixeles perteneciente a una imagen valida.
   */
  @Test
  public void test() {

    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    try {
      Mat imagen = UtilImagen.abrirImagen("3.jpg");
      if (!imagen.empty()) {
        imagen = UtilImagen.obtenerCampoJuego(imagen);
        Imgcodecs.imwrite("imag.jpg", imagen);
        int positivos = Core.countNonZero(imagen);
        double porcentaje = (double) positivos / (imagen.width() * imagen.height());
        assertTrue(porcentaje > 0.6);
      } else {
        System.out.println("Imagen vacida o incorrecta.");
        assertTrue(false);
      }
    } catch (IOException error) {
      System.out.println("Archivo no existe o direcciï¿½n incorrecta.");
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
