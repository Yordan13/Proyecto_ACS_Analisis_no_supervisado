package futbol;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;


import java.io.File;

public class FootballVideo extends AbstractVideo {
  
  private VideoCapture video;
  
  public FootballVideo(File data) {
    super(data);
    String path = data.getAbsolutePath();
    video = new VideoCapture();
    video.open(data.getAbsolutePath());
    setFps((int) video.get(Videoio.CAP_PROP_FPS));
    setWidth((int)(video.get(Videoio.CAP_PROP_FRAME_WIDTH)));
    setHeight((int)video.get(Videoio.CAP_PROP_FRAME_HEIGHT));
    setCantFrames((int) video.get(Videoio.CAP_PROP_FRAME_COUNT));
    //duracion = fps / cantFrames;
  }
  
  @Override
  public AbstractFrame getFrame() {
    Mat res = new Mat();
    video.read(res);
    int length = (int) (res.rows() * res.cols() * res.elemSize());
    byte[] buffer = new byte[length];
    res.get(0, 0, buffer);
    return new FutbolFrame(buffer, res.rows(), res.cols(), res.type());
  }
  
}
