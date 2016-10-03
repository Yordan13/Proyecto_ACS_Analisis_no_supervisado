package Futbol;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ProcesadorImagenesFutbol extends AbstractProcesadorImagenes {
  @Override
  public AbstractFrame procesar(AbstractFrame imagen) {
    return null;
  }

  private Mat convertirHSV(Mat imgBgr) {
    Mat res = new Mat(imgBgr.rows(), imgBgr.cols(), imgBgr.type());
    Imgproc.cvtColor(imgBgr, res, Imgproc.COLOR_BGR2HSV);
    return res;
  }

  private Mat obtenerMat(AbstractFrame frame){
    Mat mat = new Mat(frame.alto, frame.ancho, frame.tipo);
    mat.put(0, 0, frame.datos);
    return mat;
  }

  private Mat obtenerMascara(Mat imgHsv) {
    Mat res = new Mat(imgHsv.rows(), imgHsv.cols(), imgHsv.type());
    Scalar limiteInferior = new Scalar(35, 50, 50); // limite superior de mascara
    Scalar limiteSuperior = new Scalar(70, 255, 255);;// limite inferior de mascara
    Core.inRange(imgHsv, limiteInferior, limiteSuperior, res);// aplicar el filtro
    return res;
  }


  private Mat rellenarHuecos(Mat imgHsv) {
    Core.bitwise_not(imgHsv, imgHsv);
    Mat res = rellenarContornos(imgHsv);
    Core.bitwise_not(res, res);
    return res;
  }

  private Mat rellenarRuido(Mat imgHsv) {
    return rellenarContornos(imgHsv);
  }
  private Mat rellenarContornos(Mat imgHsv) {
    Mat res, hierarchy, des;
    res = new Mat(imgHsv.rows(), imgHsv.cols(), imgHsv.type());
    des = new Mat(imgHsv.rows(), imgHsv.cols(), imgHsv.type());
    hierarchy = new Mat();
    ArrayList<MatOfPoint> contours = new ArrayList<>();
    Core.bitwise_not(imgHsv, res);
    Imgproc.findContours(res, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
    for (MatOfPoint cnt : contours) {
      ArrayList<MatOfPoint> list = new ArrayList<>();
      list.add(cnt);
      Imgproc.drawContours(res, list, 0, new Scalar(255), -1);
    }
    Core.bitwise_not(res, des);
    return des;
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
    Mat ImagenCortada;
    int ancho = 7;// ancho predeterminado se puede cambiar para optimizar
    int alto = 8;// alto predeterminado se puede cambiar para optimizar
    int anchoAux, altoAux;
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
        ImagenCortada = new Mat(imgHsv, ventana);
        Core.meanStdDev(ImagenCortada, mean, desviacion);

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
        System.out.println("---------------------------");
      }
    }
    return true;
  }

}
