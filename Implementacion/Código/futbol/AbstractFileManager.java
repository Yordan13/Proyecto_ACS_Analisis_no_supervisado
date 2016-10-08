package futbol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Yordan Jiménez Hernández
 * @version v0.5.1
 */
public abstract class AbstractFileManager {
  /**
   * Abre un archivo de una ubicacion dada.
   * @param path direccion del archivo.
   * @return File con el contenido del archivo.
   */
  public File open(String path) {
    return new File(path);
  }
  
  /**
   * Guarda un archivo de una ubicacion dada.
   * @param path dirección del archivo
   * @param obj Objeto a ser guardado.
   * @throws IOException El archivo puede estar abierto o sucede algún error mientras lo guarda
   */
  public void save(String path, Object obj) throws IOException {
    FileOutputStream fout = new FileOutputStream(path);
    ObjectOutputStream oos = new ObjectOutputStream(fout);
    oos.writeObject(obj);
    oos.close();
  }
}
