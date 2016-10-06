package futbol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class AbstractFileManager {
  public File open(String path) {
    return new File(path);
  }
  
  public void save(String path, Object obj) throws IOException {
    FileOutputStream fout = new FileOutputStream(path);
    ObjectOutputStream oos = new ObjectOutputStream(fout);
    oos.writeObject(obj);
    oos.close();
  }
}
