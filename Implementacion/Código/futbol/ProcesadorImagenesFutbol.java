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

public class ProcesadorImagenesFutbol extends AbstractProcesadorImagenes {
  @Override
  public AbstractFrame procesar(AbstractFrame imagen) throws IOException {
    Mat temp = new Mat();
    Mat resultado = convertirMat(imagen);
    Mat umbral = obtenerImagenUmbralizada(resultado);
    Mat campoJuego = obtenerCampoDeJuego(resultado);
    Mat contornos = obtenerImagenContornos(resultado);
    Core.bitwise_and(umbral, campoJuego, temp);
    Core.bitwise_or(temp, contornos, resultado);
    
    /*PARA OBSERVAR RESULTADOS*/
    Imgcodecs.imwrite("contornos.jpeg", contornos);
    Imgcodecs.imwrite("umbral.jpeg", umbral);
    Imgcodecs.imwrite("campo.jpeg", campoJuego);
    Imgcodecs.imwrite("resultado.jpeg", resultado);
    return null;
  }

  private Mat obtenerImagenUmbralizada(Mat imagen) {
    imagen = convertirHsv(imagen);
    imagen = obtenerHue(imagen);
    imagen = normalizar(imagen, imagen.type());
    imagen = obtenerVarianza(imagen, imagen.type());
    imagen = umbralizarImagen(imagen);
    imagen = rellenarContornos(imagen);
    return imagen;
  }
  
  private Mat obtenerCampoDeJuego(Mat imagen){
    imagen = convertirHsv(imagen);
    imagen = obtenerMascara(imagen, 40);
    imagen = rellenarContornos(imagen);
    return imagen;
  }
  
  private Mat convertirHsv(Mat imgBgr) {
    Mat res = new Mat(imgBgr.rows(), imgBgr.cols(), imgBgr.type());
    Imgproc.cvtColor(imgBgr, res, Imgproc.COLOR_BGR2HSV);
    return res;
  }

  private Mat obtenerMat(AbstractFrame frame) {
    Mat mat = new Mat(frame.getAlto(), frame.getAncho(), frame.getTipo());
    mat.put(0, 0, frame.getDatos());
    return mat;
  }

  private Mat obtenerMascara(Mat imgHsv, int sensibilidad) {
    Mat res = new Mat(imgHsv.rows(), imgHsv.cols(), imgHsv.type());
    Scalar limiteInferior = new Scalar(60 - sensibilidad, 0, 0); // limite superior de mascara
    Scalar limiteSuperior = new Scalar(60 + sensibilidad, 255, 255);;// limite inferior de mascara
    Core.inRange(imgHsv, limiteInferior, limiteSuperior, res);// aplicar el filtro
    return res;
  }
  
  private Mat obtenerImagenContornos(Mat imgHsv){
    imgHsv = convertirHsv(imgHsv);
    imgHsv = obtenerMascara(imgHsv, 40);
    Mat hierarchy;
    hierarchy = new Mat();
    ArrayList<MatOfPoint> contours = new ArrayList<>();
    Imgproc.findContours(imgHsv, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
    return imgHsv;
  }
  
  private ArrayList<MatOfPoint> obtenerContornos(Mat imgHsv){
    Mat hierarchy;
    hierarchy = new Mat();
    ArrayList<MatOfPoint> contours = new ArrayList<>();
    Imgproc.findContours(imgHsv, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
    return contours;
  }

  private Mat rellenarContornos(Mat imgHsv) {
    ArrayList<MatOfPoint> contours = obtenerContornos(imgHsv);
    for (MatOfPoint cnt : contours) {
      if(Imgproc.contourArea(cnt) > 0.5 * (imgHsv.width() * imgHsv.height())){
        ArrayList<MatOfPoint> list = new ArrayList<>();
        list.add(cnt);
        Imgproc.drawContours(imgHsv, list, 0, new Scalar(255), -1);
      }
    }
    return imgHsv;
  }

  private Mat umbralizarImagen(Mat imgHsv) {
    Imgproc.threshold(imgHsv, imgHsv, 0, 255, Imgproc.THRESH_OTSU);
    return imgHsv;
  }

  private Mat obtenerVarianza(Mat imgHsv, int cvType) {
    MatOfDouble mean = new MatOfDouble();
    MatOfDouble desviacion = new MatOfDouble();
    int filas = imgHsv.rows();
    int columnas = imgHsv.cols();
    Mat resultado = new Mat(filas, columnas, cvType);
    Rect ventana;
    Mat imagenCortada;
    int ancho = 7;// ancho predeterminado se puede cambiar para optimizar
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
        imagenCortada = new Mat(imgHsv, ventana);
        Core.meanStdDev(imagenCortada, mean, desviacion);

        double[] desviacionCanales = desviacion.get(0, 0);
        int valor = (int) (Math.pow(desviacionCanales[0], 2)) % 255;
        double[] canalesResultado = new double[] {valor, 0, 0};
        resultado.put(fila, columna, canalesResultado);
      }
    }
    return resultado;
  }

  private Mat normalizar(Mat imgHsv, int cvType) {
    int filas = imgHsv.rows();
    int columnas = imgHsv.cols();
    Mat resultado = new Mat(filas, columnas, cvType);
    for (int fila = 0; fila < filas; fila++) {
      for (int columna = 0; columna < columnas; columna++) {
        double[] canalesHsv = imgHsv.get(fila, columna);
        int valor = (int) ((canalesHsv[0] / 360) * 255);
        double[] nuevosCanalesHsv = new double[] {valor, 0, 0};
        resultado.put(fila, columna, nuevosCanalesHsv);
      }
    }
    return resultado;
  }


  private Mat obtenerHue(Mat imgHsv) {
    ArrayList<Mat> canalesHsv = new ArrayList<Mat>();
    Core.split(imgHsv, canalesHsv);
    return canalesHsv.get(0);// obtener el canal 0 del hue
  }

  private Boolean equals(Mat mat1, Mat mat2) {
    if (mat1.empty() && mat2.empty()) {
      return true;
    }
    if (mat1.cols() != mat2.cols() || mat1.rows() != mat2.rows() || mat1.dims() != mat2.dims()) {
      System.out.println("here");
      return false;
    }
    for (int fila = 0; fila < mat1.rows(); fila++) {
      for (int columna = 0; columna < mat1.cols(); columna++) {
        double[] canales1 = mat1.get(fila, columna);
        double[] canales2 = mat2.get(fila, columna);
        for (int element = 0; element < mat1.dims(); element++) {
          if (canales1[element] != canales2[element]) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private Mat convertirMat(AbstractFrame imagen) {
    byte[] datos = imagen.getDatos();
    Mat resultado = new Mat(imagen.getAlto(),imagen.getAncho(),imagen.getTipo());
    resultado.put(0, 0, datos);
    return resultado;
  }
  
  private AbstractFrame convertirAbstractFrame(Mat imagen) {
    int length = (int) (imagen.rows() * imagen.cols() * imagen.elemSize());
    byte[] buffer = new byte[length];
    imagen.get(0, 0, buffer);
    return new FutbolFrame(buffer, imagen.rows(), imagen.cols(), imagen.type());
  }
}
