package futbol;


/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.6.8
 */
public abstract class AbstractProcesadorImagenes {

  /**
   * Procesa una imagen aplicando los algoritmos correspondientes.
   * Dichas imagenes deben contener un enfoque central a un campo de juego 
   * de futbol con los juegadores en medio encuentro, fuera de ello se tomaría
   * como un caso no deseado.
   * Para un caso no deseado se detectaría elementos no exactamente dados como jugadores
   *  o campo de juego.
   * Dichos algoritmos serían:
   *    1. Obtener campo de juego, imagen binaria.
   *    2. Obtener los juegadores, imagen binaria..
   *    3. Realizar AND entre estos dos ultimos resultados para brindar el 
   *    un resultados.
   * @param imagen AbstractFrame a ser procesado BGR.
   * @return AbstractFrame procesado.
   */
  public abstract AbstractFrame procesar(AbstractFrame imagen);
}
