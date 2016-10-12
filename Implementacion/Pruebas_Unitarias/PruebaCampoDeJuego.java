package pruebas;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;



public class PruebaCampoDeJuego {

  @Test
  public void test() throws NoSuchMethodException, SecurityException {

    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    
    try {
      Mat imagen = UtilImagen.abrirImagen("1.jpg");
      imagen = UtilImagen.obtenerCampoJuego(imagen);
      int positivos = Core.countNonZero(imagen);
      double porcentaje = (double)positivos / (imagen.width() * imagen.height());
      assertTrue(porcentaje > 0.6);
    } catch (IOException error) {
      System.out.println("Archivo no existe o dirección incorrecta.");
      System.out.println("Error: " + error.getMessage());
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
