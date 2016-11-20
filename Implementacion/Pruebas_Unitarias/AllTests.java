/**
 * 
 */
package pruebas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Yordan Jiménez Hernandez
 * 
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ PruebaAbrirVideo.class, PruebaAnalizaImagen.class, 
	PruebaCampoDeJuego.class, PruebaJugadores.class,
	PruebaPixelesPorSegundo.class, PruebaVarianza.class, PruebaVideoCompleto.class })
public class AllTests {

}
