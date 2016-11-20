/**
 * 
 */
package pruebas;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import futbol.AbstractFrame;
import futbol.AbstractProcesadorImagenes;
import futbol.ProcesadorImagenesFutbol;

/**
 * @author jjime
 *
 */
public class PruebaAnalizaImagen {

	AbstractProcesadorImagenes procesadorImagen;
	
	@Before
	public void init(){
		procesadorImagen = new ProcesadorImagenesFutbol();
	}
	
	@Test
	public void test() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	      Mat imagen;
		try {
			imagen = UtilImagen.abrirImagen("3.jpg");
			AbstractFrame cuadro = UtilImagen.convertirAbstractFrame(imagen);
		      if (!imagen.empty()) {
		    	  assertTrue(ejecutarPrueba(cuadro));
		      }else{
		    	  assertTrue(false);
		      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Aplica los algoritmos de segmentación a un cuadro.
	 */
	private boolean ejecutarPrueba(AbstractFrame cuadro){
  	  cuadro = procesadorImagen.procesar(cuadro);
  	  return UtilImagen.esValida(cuadro);
	}

}
