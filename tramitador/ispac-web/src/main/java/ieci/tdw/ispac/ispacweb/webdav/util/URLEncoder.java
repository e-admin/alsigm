package ieci.tdw.ispac.ispacweb.webdav.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.BitSet;

/**
 *
 * This class is very similar to the java.net.URLEncoder class.
 *
 * Unfortunately, with java.net.URLEncoder there is no way to specify to the
 * java.net.URLEncoder which characters should NOT be encoded.
 *
 * This code was moved from DefaultServlet.java
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
public class URLEncoder {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
    .getLogger(URLEncoder.class);

  protected static final char[] hexadecimal =
  {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
   'A', 'B', 'C', 'D', 'E', 'F'};

   //Array containing the safe characters set.
   protected BitSet safeCharacters = new BitSet(256);

   public URLEncoder() {
     for (char i = 'a'; i <= 'z'; i++) {
       addSafeCharacter(i);
     }
     for (char i = 'A'; i <= 'Z'; i++) {
       addSafeCharacter(i);
     }
     for (char i = '0'; i <= '9'; i++) {
       addSafeCharacter(i);
     }
   }

   public void addSafeCharacter( char c ) {
     safeCharacters.set( c );
   }

   public String encode( String path ) {
     int maxBytesPerChar = 10;

     StringBuffer rewrittenPath = new StringBuffer(path.length());
     ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);
     OutputStreamWriter writer = null;
     try {
       writer = new OutputStreamWriter(buf, "UTF8");
     } catch (Exception e) {
       logger.warn("URLEncoder::encode() "+e.getMessage());
       writer = new OutputStreamWriter(buf);
     }

     for (int i = 0; i < path.length(); i++) {
       int c = path.charAt(i);
       if (safeCharacters.get(c)) {
         rewrittenPath.append((char)c);
       } else {
         // convert to external encoding before hex conversion
         try {
           writer.write(c);
           writer.flush();
         } catch(IOException e) {
           buf.reset();
           continue;
         }
         byte[] ba = buf.toByteArray();
         for (int j = 0; j < ba.length; j++) {
           // Converting each byte in the buffer
           byte toEncode = ba[j];
           rewrittenPath.append('%');
           int low = (toEncode & 0x0f);
           int high = ((toEncode & 0xf0) >> 4);
           rewrittenPath.append(hexadecimal[high]);
           rewrittenPath.append(hexadecimal[low]);
         }
         buf.reset();
       }
     }
     return rewrittenPath.toString();
   }
}
