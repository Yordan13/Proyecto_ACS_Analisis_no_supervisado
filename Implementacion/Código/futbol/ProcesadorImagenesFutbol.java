package futbol;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.5.1
 */
public class ProcesadorImagenesFutbol extends AbstractProcesadorImagenes {
	
  @Override
  public AbstractFrame procesar(AbstractFrame imagen) throws IOException {
    Mat resultado = convertirMat(imagen);
    Mat mascaraJugadores = new Mat();
    Mat campoJuego = obtenerCampoDeJuego(resultado);
    Mat umbral = obtenerJugadores(resultado, campoJuego);
    //Mat umbral = obtenerImagenUmbralizada(resultado);
    Core.bitwise_and(umbral, campoJuego, mascaraJugadores);
    resultado = dibujarContornos(resultado, mascaraJugadores);
    //Imgcodecs.imwrite("res.jpeg", resultado);
    return convertirAbstractFrame(resultado);
  }
  
  /**
   * Obtiene los jugadores dentro de cuadro, por medio de la feteccion de los huecos
   * que deja la mascara del campo de juego.
   * @param imagen, Mat de OpenCv que contiene la imagen sin procesar.
   * @param mascaraCampoJuego, Mat de OpenCv que contiene la mascara binaria de los
   * contornos analizados.
   * @return Mat de OpenCv con el cuadro de color con los jugadores marcados.
   */
  private Mat obtenerJugadores(Mat imagen, Mat mascaraCampoJuego){
    imagen = convertirHsv(imagen);
    imagen = obtenerMascara(imagen, 30);
    Core.bitwise_not(imagen, imagen);
    return imagen; 
  }

  /**
   * Obtiene una imagen conlos jugadores localizados por medio del algoritmo de Otsu.
   * @param imagen Mat de OpenCV por ser umbralizada.
   * @return Mat de OpenCv con los jugadores encontrados en el campo de juego.
   */
  private Mat obtenerImagenUmbralizada(Mat imagen) {
    imagen = convertirHsv(imagen);
    imagen = obtenerHue(imagen);
    imagen = normalizar(imagen, imagen.type());
    imagen = obtenerVarianza(imagen, imagen.type());
    imagen = umbralizarImagen(imagen);
    imagen = dilatar(imagen, 10);
    return imagen;
  }

  /**
   * Obtiene el campo de juego ubicado dentro de una imagen.
   * @param imagen Mat de OpenCv que contiene los datos correspondientes a la imagen sin procesar.
   * @return Mat de OpenCv con el campo de juego binario que se encontraba en la imagen
   * inicial.
   */
  private Mat obtenerCampoDeJuego(Mat imagen) {
    imagen = convertirHsv(imagen);
    imagen = obtenerMascara(imagen, 40);
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
   * Se obtiene los contornos de una imagen.
   * @param imagenHsv Mat de OpenCV que se desea obtener los contornos.
   * @return MAt de OpenCv con los contornos de la imagen incial.
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
   * @param imagen Mat de OpenCv a la cual se le dibujan los contornos.
   * @param mascara Mat de OpenCv que contiene los contornos por dibujar.
   * @return Mat de OpenCv con la imagen inicial que contiene los contornos enviados por
   * parámetros. 
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
   * se encuentre encerrado los espacios entre positivos.
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
   * Obtiene una imagen binaria con los contornos negativos dilatados o reducidos.
   * @param imagenHsv Mat de OpenCv que contiene una imagen binaria que se dilatará.
   * @param sensibilidad nivel de afectamiento sobre los contornos negativos de la imagen.
   * @return Mat de OpenCv con los contornos dilatados.
   */
  private Mat dilatar(Mat imagenHsv, int sensibilidad) {
    Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
        new org.opencv.core.Size(2 * sensibilidad + 1, 2 * sensibilidad + 1));
    Imgproc.dilate(imagenHsv, imagenHsv, kernel);
    return imagenHsv;
  }

  /**
   * Obtiene una imagen binaria con los contornos negativos erosionados o aumentados.
   * @param imagenHsv Mat de OpenCv que contiene una imagen binaria que se erosionará.
   * @param sensibilidad nivel de afectamiento sobre los contornos negativos de la imagen.
   * @return Mat de OpenCv con los contornos erosionados.
   */
  private Mat erosionar(Mat imagenHsv, int sensibilidad) {
    Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
        new org.opencv.core.Size(2 * sensibilidad + 1, 2 * sensibilidad + 1));
    Imgproc.erode(imagenHsv, imagenHsv, kernel);
    return imagenHsv;
  }

  /**
   * Obtiene una imagen umbralizada por medio del algoritmo de Otsu.
   * @param imagenHsv, Mat de OpenCv que se le aplicará la umbralización.
   * @return Mat de OpenCV con su contenido umbralizado por medio del algoritmo de Otsu.
   */
  private Mat umbralizarImagen(Mat imagenHsv) {
    Imgproc.threshold(imagenHsv, imagenHsv, 0, 255, Imgproc.THRESH_OTSU);
    return imagenHsv;
  }

  /**
   * Obtiene la imagen con la varianza muestral de un cuadro definido por cada pixel.
   * @param imagenHsv Mat de OpenCv con la imagen a la cual se le obtendrá la varianza.
   * @param tipoCv tipo de imagen perteneciente a OpenCv de la imagen que se quiere como 
   * resultado.
   * @return Mat de OpenCv con la varianza obtenida de la imagen inicial.
   */
  private Mat obtenerVarianza(Mat imagenHsv, int tipoCv) {
    MatOfDouble media = new MatOfDouble();
    MatOfDouble desviacion = new MatOfDouble();
    int filas = imagenHsv.rows();
    int columnas = imagenHsv.cols();
    Mat resultado = new Mat(filas, columnas, tipoCv);
    Rect ventana;
    Mat imagenCortada;
    int ancho = 5;// ancho predeterminado se puede cambiar para optimizar
    int alto = 8;// alto predeterminado se puede cambiar para optimizar
    int anchoAux;
    int altoAux;
    for (int fila = 0; fila < filas; fila++) {
      for (int columna = 0; columna < columnas; columna++) {
        anchoAux = ancho;
        altoAux = alto;
        if (ancho + columna > columnas) {
          anchoAux = columnas - columna;
        }
        if (alto + fila > filas) {
          altoAux = filas - fila;
        }
        ventana = new Rect(columna, fila, anchoAux, altoAux);
        imagenCortada = new Mat(imagenHsv, ventana);
        Core.meanStdDev(imagenCortada, media, desviacion);

        double[] desviacionCanales = desviacion.get(0, 0);
        int valor = (int) (Math.pow(desviacionCanales[0], 2)) % 255;
        double[] canalesResultado = new double[] {valor, 0, 0};
        resultado.put(fila, columna, canalesResultado);
      }
    }
    return resultado;
  }

  /**
   * Normaliza el contenido de una imagen, cada pixel entre 0 y 255.
   * @param imagenHsv Mat de OpenCv con la imagen la cual se normalizará.
   * @param tipoCv tipo de imagen perteneciente a OpenCv de la imagen que se quiere como 
   * resultado.
   * @return Mat de OpenCv con los datos normalizados de la imagen inicial.
   */
  private Mat normalizar(Mat imagenHsv, int tipoCv) {
    int filas = imagenHsv.rows();
    int columnas = imagenHsv.cols();
    Mat resultado = new Mat(filas, columnas, tipoCv);
    for (int fila = 0; fila < filas; fila++) {
      for (int columna = 0; columna < columnas; columna++) {
        double[] canalesHsv = imagenHsv.get(fila, columna);
        int valor = (int) ((canalesHsv[0] / 360) * 255);
        double[] nuevosCanalesHsv = new double[] {valor, 0, 0};
        resultado.put(fila, columna, nuevosCanalesHsv);
      }
    }
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
