package futbol;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoWriter;

import java.io.File;
import java.io.IOException;

public class ProcesadorVideoFutbol extends AbstractProcesadorVideo {
  
  private VideoWriter escritor;
  
  public ProcesadorVideoFutbol(File videoFile) {
    super(videoFile);
  }

  @Override
  public AbstractVideo analizar() throws IOException {
    AbstractFrame imagen;
    int counter = 0;
    int cantFrames = video.getCantFrames();
    inicializarEscritor("output.avi");
    while (counter < cantFrames) {
      imagen = video.obtenerCuadro();
      if (esValida(imagen)) {
        imagen = procesadorImagenes.procesar(imagen);
        //Imgcodecs.imwrite("res"+counter+".jpeg", convertirMat(imagen));
      }
      agregarCuadro(imagen);
      counter++;
    }
    return null;
  }
  
  private void inicializarEscritor(String ubicacion) {
    escritor = new VideoWriter(ubicacion,VideoWriter.fourcc('X','V','I','D'),
        video.getFps(),new Size(video.getAncho(),video.getAlto())); 
  }
  
  private void agregarCuadro(AbstractFrame imagen) {
      Mat imagenMat=convertirMat(imagen);
      escritor.write(imagenMat);
  }
  
  private Mat convertirMat(AbstractFrame imagen) {
    byte[] datos = imagen.getDatos();
    Mat resultado = new Mat(imagen.getAlto(),imagen.getAncho(),imagen.getTipo());
    resultado.put(0, 0, datos);
    return resultado;
  }
  private boolean esValida(AbstractFrame imagen) {
    return imagen.getDatos().length>0;
  }
  
}
