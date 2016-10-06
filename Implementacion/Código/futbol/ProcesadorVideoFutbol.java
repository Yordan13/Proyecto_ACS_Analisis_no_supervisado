package futbol;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProcesadorVideoFutbol extends AbstractProcesadorVideo {

  private ArrayList<Mat> frames = new ArrayList<>();
  private VideoWriter writer;

  public ProcesadorVideoFutbol(File videoFile) {
    super(videoFile);
  }

  @Override
  public AbstractVideo analizar() {
    int counter = 0;
    int cantFrames = video.getCantFrames();
    initWriter("output.mp4");
    while (counter < cantFrames) {
      AbstractFrame imagen = video.getFrame();
      AbstractFrame imagenProcesada = procesadorImagenes.procesar(imagen);
      makeVideo(imagenProcesada);
      counter++;
      System.out.println(counter);
    }
    return null;
  }

  private void initWriter(String path) {
    writer = new VideoWriter(path,VideoWriter.fourcc('X','2','6','4'),
        video.getFps(),new Size(video.getWidth(),video.getHeight()));
  }

  private void makeVideo(AbstractFrame imagen) {
    Mat imagenMat=convertirMat(imagen);
    writer.write(imagenMat);
  }

  private Mat convertirMat(AbstractFrame imagen) {
    byte[] datos = imagen.getDatos();
    Mat resultado = new Mat(imagen.getAlto(),imagen.getAncho(),imagen.getTipo());
    resultado.put(0, 0, datos);
    return resultado;
  }

}
