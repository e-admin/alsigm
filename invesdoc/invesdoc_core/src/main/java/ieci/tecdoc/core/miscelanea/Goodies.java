
package ieci.tecdoc.core.miscelanea;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class Goodies 
{

   public static String getText(InputStream stream) throws Exception 
   {
      String text;
      
      text = new String(getBytes(stream));
      
      return text;
   }

   public static byte[] getBytes(InputStream stream) throws Exception 
   {

      byte[] bytes = null;
      byte[] buffer;
      BufferedInputStream input = new BufferedInputStream(stream);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      int count;

      try {
         buffer = new byte[10240];
         while ((count = input.read(buffer)) != -1) {
            output.write(buffer, 0, count);
         }
         bytes = output.toByteArray();
      }
      catch (Exception exc) {
         throw exc;
      }
      finally {
         try {
            output.close();
         }
         catch (Exception exc) {
            throw exc;
         }
      }

      return bytes;
   }

   public static byte[] fromStrToUTF8(String text) throws Exception
   {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      OutputStreamWriter output = new OutputStreamWriter(os, "UTF8");
      output.write(text);
      output.flush();
      output.close();

      return(os.toByteArray());
   }   

   public static String fromUTF8ToStr(byte[] data) throws Exception
   {
      return(new String(data, 0, data.length, "UTF-8"));
   }

   private Goodies()
   {
   }

}