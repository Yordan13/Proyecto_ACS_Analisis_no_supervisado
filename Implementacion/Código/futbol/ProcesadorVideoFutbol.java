package futbol;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.6.8
 */
public class ProcesadorVideoFutbol extends AbstractProcesadorVideo {
  private VideoWriter escritor;

  /**
   * Instancia un ProcesadorVideoFutbol con el videoFile ingresado. Se espera que la entrada sea un
   * File con el contenido de un video válido.
   * 
   * @param videoFile es un File por asignar al atributo video de la instancia.
   */
  public ProcesadorVideoFutbol(File videoFile) {
    super(videoFile);
  }

  @Override
  public void analizar() throws IOException {
    if (video.esVacio()) {
      throw new IOException("El archivo ingresado es vacio o invalido");
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
   * correspondiente al video ingresado al instanciar esta clase. Se espera que la entrada sea una
   * dirección para almacenar el video resultado. Entrada atípica sería una ubicación o nombre no
   * correcto para un video.
   * 
   * @param ubicacion, String con la dirección donde se almacenará el video.
   * @see http://docs.opencv.org/java/3.1.0/org/opencv/videoio/VideoWriter.html
   */
  private void inicializarEscritor(String ubicacion) {
    escritor = new VideoWriter(ubicacion, VideoWriter.fourcc('X', 'V', 'I', 'D'), video.getFps(),
        new Size(video.getAncho(), video.getAlto()));
  }

  /**
   * Agrega un cuadro o imagen al video resultado. Se convierte de AbstractFrame a Mat. Si el
   * contenido es vacío el frame del video por agregar sera vacío tambien.
   * 
   * @param imagen, AbstractFrame por se agregado.
   */
  private void agregarCuadro(AbstractFrame imagen) {
    Mat imagenMat = convertirMat(imagen);
    escritor.write(imagenMat);
  }

  /**
   * Convierte un AbstractFrame a un Mat de OpenCv. No hay entradas atípicas.
   * 
   * @param imagen, AbstractFrame a convertir, con contenido de una imagen no vacía.
   * @return una nueva imagen tipo Mat de OpenCv.
   */
  private Mat convertirMat(AbstractFrame imagen) {
    byte[] datos = imagen.getDatos();
    Mat resultado = new Mat(imagen.getAlto(), imagen.getAncho(), imagen.getTipo());
    resultado.put(0, 0, datos);
    return resultado;
  }

  /**
   * Valida si un AbstractFrame posee un contenido de datos vacío. Se espera una instancia de
   * abstractFrame, con los datos validos de un archivo de video.
   * 
   * @param imagen, AbstractFrame a ser validado.
   * @return un valor boolean. True si la imagen no es vacía. False la imagen es vacía.
   */
  private boolean esValida(AbstractFrame imagen) {
    return imagen.getDatos().length > 0;
  }

}
