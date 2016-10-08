package futbol;

import java.io.IOException;

/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.5.1
 */
public abstract class AbstractProcesadorImagenes {

  /**
   * Procesa una imagen aplicando los algoritmos correspondientes.
   * @param imagen AbstractFrame a ser procesado.
   * @return AbstractFrame procesado.
   * @throws IOException error de archivos.
   */
  public abstract AbstractFrame procesar(AbstractFrame imagen)throws IOException;
}
