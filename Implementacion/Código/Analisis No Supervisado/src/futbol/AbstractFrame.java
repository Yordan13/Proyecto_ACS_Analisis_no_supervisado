package futbol;

public abstract class AbstractFrame {
  private int ancho;
  private int alto;
  private int tipo;
  private byte[] datos;
  
  AbstractFrame (byte[] datos, int alto, int ancho, int tipo){
    this.setAncho(ancho);
    this.setAlto(alto);
    this.setDatos(datos);
    this.setTipo(tipo);
  }

  /**
   * @return datos value.
   */
  protected byte[] getDatos() {
    return datos;
  }

  /**
   * @param datos the datos to set.
   */
  protected void setDatos(byte[] datos) {
    this.datos = datos;
  }

  /**
   * @return the tipo
   */
  protected int getTipo() {
    return tipo;
  }

  /**
   * @param tipo the tipo to set
   */
  protected void setTipo(int tipo) {
    this.tipo = tipo;
  }

  /**
   * @return the alto
   */
  protected int getAlto() {
    return alto;
  }

  /**
   * @param alto the alto to set
   */
  protected void setAlto(int alto) {
    this.alto = alto;
  }

  /**
   * @return the ancho
   */
  protected int getAncho() {
    return ancho;
  }

  /**
   * @param ancho the ancho to set
   */
  protected void setAncho(int ancho) {
    this.ancho = ancho;
  }
}
