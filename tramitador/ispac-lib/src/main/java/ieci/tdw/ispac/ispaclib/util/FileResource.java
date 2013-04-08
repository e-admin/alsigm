package ieci.tdw.ispac.ispaclib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This specialized resource implementation avoids opening the IputStream
 * to the file right away (which would put a lock on the file).
 */
public class FileResource extends Resource {

  public FileResource( File file) 
  { this.file = file;}

  /**
   * Associated file object.
   */
  protected File file = null;

  /**
   * Content accessor.
   * 
   * @return InputStream
   */
  public InputStream streamContent()
  throws IOException {
    if (binaryContent == null) {
      inputStream = new FileInputStream( file);
    }
      return super.streamContent();
  }
}
