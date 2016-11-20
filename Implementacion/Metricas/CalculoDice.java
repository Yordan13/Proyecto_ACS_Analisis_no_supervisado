/**
 * 
 */
package metricas;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.opencv.core.Core;

import pruebas.Metricas;

/**
 * @author jjime
 *
 */
public class CalculoDice {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String video = "video.mp4";
		String archivos = "outimg//";
		try {
			double resultado = Metricas.obtenerCoeficienteDice(video, archivos);
			resultado = ((double)Math.round(resultado*100))/100;
			System.out.println("El coeficiente de Dice del video: "+video+", posee una tasa de : "+resultado+".");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
