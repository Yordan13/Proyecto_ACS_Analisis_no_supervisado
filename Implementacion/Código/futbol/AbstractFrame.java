package futbol;

/**
 * 
 * @author Yordan Jim�nez Hern�ndez
 * @version v0.6.22
 */
public abstract class AbstractFrame {
  private int ancho;
  private int alto;
  private int tipo;
  private byte[] datos;
  
  /**
   * Instancia la clase AbstractFrame, asignando a cada atributo el parametro correspondiente.
   * @param datos Arreglo con la informaci�n del cuadro.
   * @param alto valor del alto de la imagen.
   * @param ancho valor del ancho de la imagen
   * @param tipo tipode  OpenCv de la image
   */
  public AbstractFrame(byte[] datos, int alto, int ancho, int tipo) {
    this.setAncho(ancho);
    this.setAlto(alto);
    this.setDatos(datos);
    this.setTipo(tipo);
  }

  public byte[] getDatos() {
    return datos;
  }
  
  public void setDatos(byte[] datos) {
    this.datos = datos;
  }

  public int getTipo() {
    return tipo;
  }

  public void setTipo(int tipo) {
    this.tipo = tipo;
  }

  public int getAlto() {
    return alto;
  }

  public void setAlto(int alto) {
    this.alto = alto;
  }

  public int getAncho() {
    return ancho;
  }

  public void setAncho(int ancho) {
    this.ancho = ancho;
  }
}
