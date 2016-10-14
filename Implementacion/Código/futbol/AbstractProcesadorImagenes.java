package futbol;


/**
 * 
 * @author Yordan Jim�nez Hern�ndez
 * @version v0.6.22
 */
public abstract class AbstractProcesadorImagenes {

  /**
   * Procesa una imagen aplicando los algoritmos correspondientes.
   * 
   * @param imagen AbstractFrame a ser procesado.
   * @return AbstractFrame procesado.
   */
  public abstract AbstractFrame procesar(AbstractFrame imagen);
}
