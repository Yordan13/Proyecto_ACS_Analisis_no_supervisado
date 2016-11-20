/**
 * 
 */
package metricas;

import java.io.IOException;

import org.opencv.core.Core;

import pruebas.Metricas;

/**
 * @author Yordan Jiménez Hernández
 *
 */
public class PixelesPorSegundo {

	/**
	 * Calcula cuantos pixeles puede procesar por segundo.
	 * @param args argumentos enviados por consola.
	 */
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		try {
			long tiempo = Metricas.obtenerPixelesPorSegundo("video.mp4");
			System.out.println("La cantidad de pixeles por segundo ejecutados es: " + tiempo + ".");
		} catch (IOException error) {
			System.out.println(error.getMessage());
		}

	}

}
