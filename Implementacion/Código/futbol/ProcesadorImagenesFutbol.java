package futbol;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;
import java.util.ArrayList;

/**
 *
 * @author Yordan Jiménez Hernández
 * @version v0.6.8
 */
public class ProcesadorImagenesFutbol extends AbstractProcesadorImagenes {
  @Override
  public AbstractFrame procesar(AbstractFrame imagen)  {
    Mat resultado = convertirMat(imagen);
    Mat mascaraJugadores = new Mat();
    Mat campoJuego = obtenerCampoDeJuego(resultado);
    Mat umbral = obtenerImagenUmbralizada(resultado);
    Core.bitwise_and(umbral, campoJuego, mascaraJugadores);
    resultado = dibujarContornos(resultado, mascaraJugadores);
    return convertirAbstractFrame(resultado);
  }

  /**
   * Obtiene los jugadores dentro de cuadro, por medio de la deteccion de los huecos
   * que deja la mascara del campo de juego.
   * @param imagen, Mat de OpenCv que contiene la imagen sin procesar.
   * @param mascaraCampoJuego, Mat de OpenCv que contiene la mascara binaria de los
   * contornos analizados.
   * @return Mat de OpenCv con el cuadro de color con los jugadores marcados.
   * @ see http://docs.opencv.org/java/2.4.2/org/opencv/core/Core.html
   */
  private Mat obtenerJugadores(Mat imagen, Mat mascaraCampoJuego){
    imagen = convertirHsv(imagen);
    imagen = obtenerMascara(imagen, 30);
    Core.bitwise_not(imagen, imagen);
    return imagen;
  }

  /**
   * Obtiene una imagen con los jugadores localizados por medio del algoritmo de Otsu.
   * 1. Convertir la imagen a formato HSV.
   * 2. Obtener el valor del canal Hue.
   * 3. Normalizar la imagen en el rango 0 - 255.
   * 4. Obtener la varianza local en un area definida por constante.
   * 5. Obtener la mascara final aplicando un threshold de OpenCV.
   * @param imagen Mat de OpenCV por ser umbralizada.
   * @return Mat de OpenCv con los jugadores encontrados en el campo de juego.
   * @see http://docs.opencv.org/2.4/doc/tutorials/imgproc/threshold/threshold.html
   */
  private Mat obtenerImagenUmbralizada(Mat imagen) {
    imagen = convertirHsv(imagen);
    imagen = obtenerHue(imagen);
    imagen = normalizar(imagen, imagen.type());
    imagen = obtenerVarianza(imagen, 10);
    imagen = umbralizarImagen(imagen);
    return imagen;
  }

  /**
   * Obtiene el campo de juego ubicado dentro de una imagen. Para ello, utiliza
   * un threshold de OpenCV para obtener una mascara de la imagen filtrada por
   * un rango de valor verde definido.
   * @param imagen Mat de OpenCv que contiene los datos correspondientes a la imagen sin procesar.
   * @return Mat de OpenCv con el campo de juego binario que se encontraba en la imagen
   * inicial.
   */
  private Mat obtenerCampoDeJuego(Mat imagen) {
    imagen = convertirHsv(imagen);
    imagen = obtenerMascara(imagen,30);
    imagen = rellenarContornos(imagen, 0.5);
    return imagen;
  }

  /**
   * Convierte una imagen tipo Mat de OpenCv de tipo BGR a HSV de tres canales.
   * @param imagenBgr Mat de OpenCv por ser convertida a HSV.
   * @return imagen tipo Mat de OpenCv convertida a Hsv de tres canales.
   */
  private Mat convertirHsv(Mat imagenBgr) {
    Mat resultado = new Mat(imagenBgr.rows(), imagenBgr.cols(), imagenBgr.type());
    Imgproc.cvtColor(imagenBgr, resultado, Imgproc.COLOR_BGR2HSV);
    return resultado;
  }

  /**
   * Obtiene una imagen binaria con el contenido de píxeles donde se encontraba de color verde
   * dentro de un rango de sensibilidad.
   * @param imagenHsv imagen tipo Mat de OpenCv sin procesar, donde se obtendrá la máscara donde
   * había un píxel verde.
   * @param sensibilidad Entero que determinará el rango de color verde el cual será aceptado.
   * @return Mat de OpenCv binario, donde cada pixel resultado sea positivo indica que había un
   * pixel verde aceptado.
   */
  private Mat obtenerMascara(Mat imagenHsv, int sensibilidad) {
    Mat res = new Mat(imagenHsv.rows(), imagenHsv.cols(), imagenHsv.type());
    Scalar limiteInferior = new Scalar(60 - sensibilidad, 0, 0); // limite superior de mascara
    Scalar limiteSuperior = new Scalar(60 + sensibilidad, 255, 255);;// limite inferior de mascara
    Core.inRange(imagenHsv, limiteInferior, limiteSuperior, res);// aplicar el filtro
    return res;
  }

  /**
   * Se obtiene los contornos de una imagen. Para ello, se utiliza funcionalidad
   * findContours() de OpenCV.
   * @param imagenHsv Mat de OpenCV que se desea obtener los contornos.
   * @return MAt de OpenCv con los contornos de la imagen incial.
   * @see http://docs.opencv.org/java/2.4.2/org/opencv/imgproc/Imgproc.html
   */
  private ArrayList<MatOfPoint> obtenerContornos(Mat imagenHsv) {
    Mat jerarquia;
    jerarquia = new Mat();
    ArrayList<MatOfPoint> contornos = new ArrayList<>();
    Imgproc.findContours(imagenHsv, contornos, jerarquia, Imgproc.RETR_CCOMP,
        Imgproc.CHAIN_APPROX_NONE);
    return contornos;
  }

  /**
   * Obtiene una imagen a la cual se le agregan los contornos enviados por parámetros.
   * Para ello encuentra los contornos generados por la imagen umbralizada y los
   * dibuja en una imagen de tres canales por medio de las funciones findContours y
   * drawContours.
   * @param imagen Mat de OpenCv a la cual se le dibujan los contornos.
   * @param mascara Mat de OpenCv que contiene los contornos por dibujar.
   * @return Mat de OpenCv con la imagen inicial que contiene los contornos enviados por
   * parámetros.
   * @see http://docs.opencv.org/java/2.4.2/org/opencv/imgproc/Imgproc.html
   */
  private Mat dibujarContornos(Mat imagen, Mat mascara) {
    Mat jerarquía;
    jerarquía = new Mat();
    ArrayList<MatOfPoint> contornos = new ArrayList<>();
    Imgproc.findContours(mascara, contornos, jerarquía, Imgproc.RETR_EXTERNAL,
        Imgproc.CHAIN_APPROX_NONE);
    Imgproc.drawContours(imagen, contornos, -1, new Scalar(255), 2);
    return imagen;
  }

  /**
   * Rellena los contornos o espacios de un tamaño calculado dentro una imagen binaria, donde
   * se encuentre encerrado los espacios entre positivos. Para ello toma una imagen
   * binaria y encuentra sus contornos, si el area de ese contorno es mayor a
   * porcentaje, es dibujado en el resultado.
   * @param imagenHsv Mat de OpenCv que contiene la imagen por rellenar los contornos
   * @param porcentaje Double que define el tamaño del contorno por rellenar.
   * @return Mat de OpenCv con los contornos de un tamaño calculado rellenados.
   */
  private Mat rellenarContornos(Mat imagenHsv, double porcentaje) {
    Mat resultado = new Mat(imagenHsv.rows(), imagenHsv.cols(), imagenHsv.type());
    ArrayList<MatOfPoint> contornos = obtenerContornos(imagenHsv);
    for (MatOfPoint cnt : contornos) {
      ArrayList<MatOfPoint> list = new ArrayList<>();
      list.add(cnt);
      if(Imgproc.contourArea(cnt) > porcentaje * (imagenHsv.width() * imagenHsv.height())){
        Imgproc.drawContours(resultado, list, 0, new Scalar(255), -1);
      }
    }
    return resultado;
  }

  /**
   * Obtiene una imagen umbralizada por medio de la funcion threshold de OpenCV.
   * @param imagenHsv es mat de OpenCv que se le aplicará la umbralización.
   * @return un Mat de OpenCV con su contenido umbralizada.
   * @see http://docs.opencv.org/2.4/doc/tutorials/imgproc/threshold/threshold.html
   */
  private Mat umbralizarImagen(Mat imagenHsv) {
    Imgproc.threshold(imagenHsv, imagenHsv, 0, 255, Imgproc.THRESH_TRIANGLE);
    return imagenHsv;
  }

  /**
   * Obtiene la imagen con la varianza muestral de un cuadro definido por cada pixel.
   * Se utiliza la formula de Varianza de una variable aleatoria.
   * En este caso, X seria la imagen de un canal original y la media
   * seria la imagen procesada mediante la funcion blur de OpenCV, la cual aplica un filtro de caja
   * normalizada, es decir, aplica un promedia los pixeles en un cuadro NxN a lo largo
   * de toda la imagen. De esta manera, aplicamos la misma
   * operacion a todos los pixeles por medio de sencillas operaciones aritmeticas
   * obteniendo de esta manera la varianza local en cada punto de la imagen.
   * @param imagenHsv Mat de OpenCv con la imagen a la cual se le obtendrá la varianza.
   * @param tamañoFiltro Tamaño de la ventana para aplicar la varianza local
   * resultado.
   * @return Mat de OpenCv con la varianza obtenida de la imagen inicial.
   * @see Vease https://es.wikipedia.org/wiki/Varianza#Variable_aleatoria
   * @see http://docs.opencv.org/2.4/modules/imgproc/doc/filtering.html?highlight=blur
   */
  private Mat obtenerVarianza(Mat imagenHsv, int tamañoFiltro) {
    int filas = imagenHsv.rows();
    int columnas = imagenHsv.cols();
    int tipo =CvType.CV_16UC1;
    imagenHsv.convertTo(imagenHsv, tipo);
    Mat resultado = new Mat(filas, columnas, tipo);
    Mat cuadradoImagen = new Mat(filas, columnas, tipo);
    Mat cuadradoMedia = new Mat(filas, columnas, tipo);
    Mat media = new Mat(filas, columnas, tipo);
    Mat mediaCuadradoImagen = new Mat(filas, columnas, tipo);

    Core.pow(imagenHsv, 2, cuadradoImagen);
    Imgproc.blur(imagenHsv, media, new Size(tamañoFiltro,tamañoFiltro));
    Core.pow(media, 2, cuadradoMedia);
    Imgproc.blur(cuadradoImagen, mediaCuadradoImagen, new Size(tamañoFiltro,tamañoFiltro));
    Core.subtract(mediaCuadradoImagen, cuadradoMedia, resultado);
    Core.normalize(resultado, resultado, 0, 255, Core.NORM_MINMAX);
    resultado.convertTo(resultado, CvType.CV_8UC1);
    return resultado;
  }

  /**
   * Normaliza el contenido de una imagen, cada pixel entre 0 y 255.
   * Utiliza la funcion provista por OpenCV
   * @param imagenHsv Mat de OpenCv con la imagen la cual se normalizará.
   * @param tipoCv tipo de imagen perteneciente a OpenCv de la imagen que se quiere como
   * resultado.
   * @return Mat de OpenCv con los datos normalizados de la imagen inicial.
   * @see http://docs.opencv.org/java/2.4.2/org/opencv/core/Core.html
   */
  private Mat normalizar(Mat imagenHsv, int tipoCv) {
    int filas = imagenHsv.rows();
    int columnas = imagenHsv.cols();
    Mat resultado = new Mat(filas, columnas, tipoCv);
    Core.normalize(imagenHsv, resultado, 0, 255, Core.NORM_MINMAX);
    return resultado;
  }

  /**
   * Obtiene el canal 0 o HUE de una imagen tipo HSV.
   * @param imagenHsv Mat de OpenCv con la imagen a obtener el hue.
   * @return Mat de OpenCv con el Hue de la imagen inicial.
   */
  private Mat obtenerHue(Mat imagenHsv) {
    int tipoCv = imagenHsv.type();
    Mat resultado = new Mat(imagenHsv.rows(), imagenHsv.cols(), imagenHsv.type());
    ArrayList<Mat> canalesHsv = new ArrayList<Mat>();
    Core.split(imagenHsv, canalesHsv);
    Mat Hue = canalesHsv.get(0);
    Hue.convertTo(resultado, tipoCv);
    return resultado;// obtener el canal 0 del hue
  }

  /**
   * Convierte un AbstractFrame a un Mat de OpenCv.
   * @param imagen AbstractFrame a convertir.
   * @return una nueva imagen tipo Mat de OpenCv.
   */
  private Mat convertirMat(AbstractFrame imagen) {
    byte[] datos = imagen.getDatos();
    Mat resultado = new Mat(imagen.getAlto(), imagen.getAncho(), imagen.getTipo());
    resultado.put(0, 0, datos);
    return resultado;
  }

  /**
   * Convierte Mat a un de OpenCv AbstractFrame .
   * @param imagen imagen Mat de OpenCv a convertir.
   * @return una nueva imagen tipo AbstractFrame.
   */
  private AbstractFrame convertirAbstractFrame(Mat imagen) {
    int tamaño = (int) (imagen.rows() * imagen.cols() * imagen.elemSize());
    byte[] buffer = new byte[tamaño];
    imagen.get(0, 0, buffer);
    return new FutbolFrame(buffer, imagen.rows(), imagen.cols(), imagen.type());
  }
}