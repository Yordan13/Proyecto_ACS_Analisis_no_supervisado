package futbol;

/**
 * 
 * @author Yordan Jim�nez Hern�ndez
 * @version v0.6.8
 */
public abstract class AbstractFrame {
  private int ancho;
  private int alto;
  private int tipo;
  private byte[] datos;

  /**
   * Instancia la clase AbstractFrame, asignando a cada atributo el parametro correspondiente. Se
   * espara que los datos no sean vacidos. Se espera que e alto y ancho sean mayores de 0. Se espera
   * un tipo valido de imagen de openCV indicado con un valor entero ver informaci�n adjunta. Casos
   * At�picos: Datos vacidos, ancho y alto con un valor de 0.
   * 
   * @see http://ninghang.blogspot.com/2012/11/list-of-mat-type-in-opencv.html
   * @param datos Arreglo con la informaci�n del cuadro.
   * @param alto valor del alto de la imagen.
   * @param ancho valor del ancho de la imagen
   * @param tipo tipode OpenCv de la image
   */
  public AbstractFrame(byte[] datos, int alto, int ancho, int tipo) {
    this.setAncho(ancho);
    this.setAlto(alto);
    this.setDatos(datos);
    this.setTipo(tipo);
  }

  /**
   * Retorna el valor de la colecci�n de datos.
   * 
   * @return atributo tipo byte[] de la instancia.
   */
  public byte[] getDatos() {
    return datos;
  }

  /**
   * Asigna un valor al atributo de datos. Se espera una colecci�n no vacida de datos del cuadro.
   * Caso at�pico que la coleccion sea vacida.
   * 
   * @param datos colecci�n tipo byte[] con datos.
   */
  public void setDatos(byte[] datos) {
    this.datos = datos;
  }

  /**
   * Retorna el valor num�rico de tipo de imagen dada por openCv.
   * 
   * @see http://ninghang.blogspot.com/2012/11/list-of-mat-type-in-opencv.html
   * @return Valor entero que indica el tipo de imagen.
   */
  public int getTipo() {
    return tipo;
  }

  /**
   * Asigna el valor num�rico de tipo de imagen dada por openCv.
   * 
   * @see http://ninghang.blogspot.com/2012/11/list-of-mat-type-in-opencv.html
   * @param tipo Valor entero del tipo de la imagen.
   */
  public void setTipo(int tipo) {
    this.tipo = tipo;
  }

  /**
   * Retorna el valor entero del alto del cuadro. Atipico este valor ser� 0 o menor.
   * 
   * @return Valor entero del alto de la imagen.
   */
  public int getAlto() {
    return alto;
  }

  /**
   * Asigna el valor del alto de la imagen
   * 
   * @param alto Valor entero del alto de la imagen, debe ser mayor que 0.
   */
  public void setAlto(int alto) {
    this.alto = alto;
  }

  /**
   * Retorna el valor entero del ancho del cuadro. Atipico este valor ser� 0 o menor.
   * 
   * @return Valor entero del ancho de la imagen.
   */
  public int getAncho() {
    return ancho;
  }

  /**
   * Asigna el valor del ancho de la imagen
   * 
   * @param ancho Valor entero del ancho de la imagen, debe ser mayor que 0.
   */
  public void setAncho(int ancho) {
    this.ancho = ancho;
  }
}
