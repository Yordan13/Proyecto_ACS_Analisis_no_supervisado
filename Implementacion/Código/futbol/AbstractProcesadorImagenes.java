package futbol;

import java.io.IOException;

/**
 * 
 * @author Yordan Jim�nez Hern�ndez
 * @version v0.6.22
 */
public abstract class AbstractProcesadorImagenes {

  /**
   * Procesa una imagen aplicando los algoritmos correspondientes.
   * @param imagen AbstractFrame a ser procesado.
   * @return AbstractFrame procesado.
   * @throws IOException error de archivos.
   */
  public abstract AbstractFrame procesar(AbstractFrame imagen);
}
