package futbol;


import java.io.File;
import java.io.IOException;

public class ProcesadorVideoFutbol extends AbstractProcesadorVideo {
  public ProcesadorVideoFutbol(File videoFile) {
    super(videoFile);
  }

  @Override
  public AbstractVideo analizar() throws IOException {
    AbstractFrame imagen =video.getFrame();
    procesadorImagenes.procesar(imagen);
    return null;
  }
  
}
