package Futbol;

import java.io.File;

import org.opencv.videoio.VideoCapture;

public class ProcesadorVideoFutbol extends AbstractProcesadorVideo {
  public ProcesadorVideoFutbol(File videoFile) {
    super(videoFile);
  }

  @Override
  public AbstractVideo analizar() {
    return null;
  }

}
