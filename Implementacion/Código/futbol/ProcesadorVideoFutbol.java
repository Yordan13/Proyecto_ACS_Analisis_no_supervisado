package futbol;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.6.22
 */
public class ProcesadorVideoFutbol extends AbstractProcesadorVideo {
  private VideoWriter escritor;
  /**
   * Instancia un ProcesadorVideoFutbol con el videoFile ingresado.
   * @param videoFile es un File por asignar al atributo video de la instancia.
   */
  public ProcesadorVideoFutbol(File videoFile) {
    super(videoFile);
  }
  
  @Override
  public void analizar() throws IOException {
    if (video.esVacido()) {
      throw new IOException("El archivo ingresado es vacido o invalido");
    } 
    AbstractFrame imagen;
    int counter = 0;
    int cantFrames = video.getCantFrames();
    inicializarEscritor("output.avi");
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      if (esValida(imagen)) {
        imagen = procesadorImagenes.procesar(imagen);
      }
      agregarCuadro(imagen);
      counter++;
    }
  }
  
  /**
   * Inicializa el objeto escritor de video tipo VideoWritter de OpenCv. Asigna las características
   * correspondiente al video ingresado al instanciar esta clase.
   * @param ubicacion, String con la dirección donde se almacenará el video.
   */
  private void inicializarEscritor(String ubicacion) {
    escritor = new VideoWriter(ubicacion,VideoWriter.fourcc('X','V','I','D'),
        video.getFps(),new Size(video.getAncho(),video.getAlto())); 
  }
  
  /**
   * Agrega un cuadro o imagen al video resultado. Se convierte de AbstractFrame a Mat.
   * @param imagen, AbstractFrame por se agregado.
   */
  private void agregarCuadro(AbstractFrame imagen) {
      Mat imagenMat=convertirMat(imagen);
      escritor.write(imagenMat);
  }
  
  /**
   * Convierte un AbstractFrame a un Mat de OpenCv.
   * @param imagen, AbstractFrame a convertir.
   * @return una nueva imagen tipo Mat de OpenCv.
   */
  private Mat convertirMat(AbstractFrame imagen) {
    byte[] datos = imagen.getDatos();
    Mat resultado = new Mat(imagen.getAlto(),imagen.getAncho(),imagen.getTipo());
    resultado.put(0, 0, datos);
    return resultado;
  }
  
  /**
   * Valida si un AbstractFrame posee un contenido de datos vacido.
   * @param imagen, AbstractFrame a ser validado.
   * @return un valor boolean. True si la imagen no es vacida. False la imagen es vacida.
   */
  private boolean esValida(AbstractFrame imagen) {
    return imagen.getDatos().length>0;
  }
  
}
