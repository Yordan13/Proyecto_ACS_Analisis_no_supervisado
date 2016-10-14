package futbol;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.io.File;

/**
 * 
 * @author Yordan Jim�nez Hern�ndez
 * @version v0.6.22
 */
public class FootballVideo extends AbstractVideo {

  private VideoCapture video;

  /**
   * Inicializa una instancia de FootballVideo, asignando todas las caracteristicas de
   * pertenecientes al video ingresado.
   * 
   * @param data es un File que se asigana al atributo video.
   */
  public FootballVideo(File data) {
    super(data);
    video = new VideoCapture();
    video.open(data.getAbsolutePath());
    setFps((int) video.get(Videoio.CAP_PROP_FPS));
    setAncho((int) (video.get(Videoio.CAP_PROP_FRAME_WIDTH)));
    setAlto((int) video.get(Videoio.CAP_PROP_FRAME_HEIGHT));
    setCantFrames((int) video.get(Videoio.CAP_PROP_FRAME_COUNT));
    // duracion = fps / cantFrames;
  }

  @Override
  public AbstractFrame obtenerCuadro() {
    Mat res = new Mat();
    video.read(res);
    int length = (int) (res.rows() * res.cols() * res.elemSize());
    byte[] buffer = new byte[length];
    res.get(0, 0, buffer);
    return new FutbolFrame(buffer, res.rows(), res.cols(), res.type());
  }

  @Override
  public boolean esVacido() {
    return getCantFrames() == 0;
  }
}
