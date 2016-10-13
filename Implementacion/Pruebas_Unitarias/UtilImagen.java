package pruebas;

import futbol.AbstractFileManager;
import futbol.AbstractFrame;
import futbol.AbstractVideo;
import futbol.FootballVideo;
import futbol.FutbolFileManager;
import futbol.ProcesadorImagenesFutbol;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class UtilImagen {  

  /**
   * Abre una imagen ubicada en la dirección indicada.
   * @param ubicacion dirección de la imagen
   * @return Mat de OpenCv con la información de la imagen.
   * @throws IOException Error si la imagen no existe.
   */
  public static Mat abrirImagen(String ubicacion) throws IOException {
    Mat res = Imgcodecs.imread(ubicacion);
    if (res.empty()) {
      throw new IOException("Error cargando la imagen");
    }
    return res;
  }

  /**
   * Ejecuta el método privado obtenerJugadores de la clase ProcesadorImagenesFutbol,
   * por medio de reflexión
   * @param imagen Imagen de donde se obtendrán los jugadores
   * @param mascara campo de juego donde se encuentran los jugadadores
   * @return Mat de OpenCv con unicamente los jugadores detectados.
   * @throws NoSuchMethodException Si el método no existe, excepción de reflexión.
   * @throws SecurityException si el método no le pertenece al objeto intentado.
   * @throws IllegalAccessException si no se posee acceso a este método.
   * @throws IllegalArgumentException Argumentos invalidos para ejecutar el proceso.
   * @throws InvocationTargetException Si el metodo invocado lanza una excepción.
   */
  public static Mat obtenerJugadores(Mat imagen, Mat mascara) throws NoSuchMethodException, 
                    SecurityException,IllegalAccessException, IllegalArgumentException, 
                    InvocationTargetException {
    ProcesadorImagenesFutbol objetoPrueba = new ProcesadorImagenesFutbol();
    Object[] argumentos = new Mat[] {imagen,mascara};

    Class<?>[] tipoArgumentos = new Class[] {Mat.class,Mat.class};
    Class<?> claseProcesadorImagenesFutbol = ProcesadorImagenesFutbol.class;
    Method metodo =
        claseProcesadorImagenesFutbol.getDeclaredMethod("obtenerJugadores", tipoArgumentos);
    metodo.setAccessible(true);
    Mat resultado = (Mat) metodo.invoke(objetoPrueba, argumentos);
    return resultado;
  }
  
  /**
   * Retorna el campo de juego dentro de la imagen, ejecutando el método privado de la 
   * clase ProcesadorImagenesFutbol
   * @param imagen Imagen con el campo de juego.
   * @return Mat de OpenCv con la información del campo de juego.
   * @throws NoSuchMethodException Si el método no existe, excepción de reflexión.
   * @throws SecurityException si el método no le pertenece al objeto intentado.
   * @throws IllegalAccessException si no se posee acceso a este método.
   * @throws IllegalArgumentException Argumentos invalidos para ejecutar el proceso.
   * @throws InvocationTargetException Si el metodo invocado lanza una excepción.
   */
  public static Mat obtenerCampoJuego(Mat imagen) throws NoSuchMethodException, SecurityException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    ProcesadorImagenesFutbol objetoPrueba = new ProcesadorImagenesFutbol();
    Object[] argumentos = new Mat[] {imagen};

    Class<?>[] tipoArgumentos = new Class[] {Mat.class};
    Class<?> claseProcesadorImagenesFutbol = ProcesadorImagenesFutbol.class;
    Method metodo =
        claseProcesadorImagenesFutbol.getDeclaredMethod("obtenerCampoDeJuego", tipoArgumentos);
    metodo.setAccessible(true);
    Mat resultado = (Mat) metodo.invoke(objetoPrueba, argumentos);
    return resultado;
  }
  
  /**
   * Calcula la métrica de eficiencia de procesamiento de pixeles del video.
   * @param tiempoInicio Tiempo en que inicia a ejecutar el procesamiento del video.
   * @param tiempoFinal Tiempo en que fializa a ejecutar el procesamiento del video.
   * @param alto Alto de los cuadros del video
   * @param ancho Ancho de los cuadros del video.
   * @param cantFrames Cantidad de cuadros del video.
   * @return Valor numérico del tiempo en procesar todo el video.
   */
  public static long pixelesPorSegundo(long tiempoInicio, long tiempoFinal, int alto, int ancho,
      int cantFrames) { 
    long pixelesTotales = alto * ancho * cantFrames;
    long tiempoTotal = tiempoFinal - tiempoInicio;
    double pixelesPorMilisegundo = pixelesTotales / tiempoTotal;
    return (long) (pixelesPorMilisegundo * 1000);
  }
  
  /**
   * Obtiene el canal de datos de una imagen de escala gris que contiene 
   * información para para aplicar el algoritmo de Dice.
   * @param imagen Mat de OpenCv de donde se obtendrán los datos o el canal.
   * @return Mat de OpenCv con el canal numero 2.
   */
  public static Mat obtenerCanal(Mat imagen) {
    ArrayList<Mat> canales = new ArrayList<>();
    Imgproc.cvtColor(imagen, imagen, Imgproc.COLOR_BGR2HSV);
    Core.split(imagen, canales);
    return canales.get(2);
  }
  
  public static double coeficienteDice(Mat img1, Mat img2) {
    Mat interseccion = new Mat(img1.rows(), img1.cols(), img1.type());
    Core.bitwise_and(img1, img2, interseccion);
    double cantPixeles1 = Core.countNonZero(img1);
    double cantPixeles2 = Core.countNonZero(img2);
    double cantPixelesInterseccion = Core.countNonZero(interseccion);
    return (2 * cantPixelesInterseccion) / (cantPixeles1 + cantPixeles2);
  }
  
  /**
   * Abre un video en una ubicación dada.
   * @param ubicacion Direccion del video
   * @return AbstractVideo con la información del video.
   */
  public static AbstractVideo abrirVideo(String ubicacion) {
    AbstractFileManager manager = new FutbolFileManager();
    File archivo = manager.open(ubicacion); 
    AbstractVideo video = new FootballVideo(archivo);
    return video;
  }
  
  /**
   * Convierte un AbstractFrame a un Mat de OpenCv.
   * @param imagen AbstractFrame a convertir.
   * @return una nueva imagen tipo Mat de OpenCv.
   */
  public static Mat convertirMat(AbstractFrame imagen) {
    byte[] datos = imagen.getDatos();
    Mat resultado = new Mat(imagen.getAlto(),imagen.getAncho(),imagen.getTipo());
    resultado.put(0, 0, datos);
    return resultado;
  }
}
