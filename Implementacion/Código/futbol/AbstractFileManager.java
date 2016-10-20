package futbol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Yordan Jimï¿½nez Hernï¿½ndez
 * @version v0.6.8
 */
public abstract class AbstractFileManager {
  /**
   * Abre un archivo de una ubicacion dada.
   * 
   * @param path direccion del archivo.
   * @return File con el contenido del archivo.
   */
  public File open(String path) {
    return new File(path);
  }

  /**
   * Almacena en disco un archivo contenido en el objeto, en una 
   * ubicación indicada.
   * @param path Dirección donde se almacenará el archivo.
   * @param obj Contiene los datos que tendrá el archivo.
   * @throws IOException Si el archivo esta abierto.
   */
  public void save(String path, Object obj) throws IOException {
    FileOutputStream fout = new FileOutputStream(path);
    ObjectOutputStream oos = new ObjectOutputStream(fout);
    oos.writeObject(obj);
    oos.close();
  }
}
