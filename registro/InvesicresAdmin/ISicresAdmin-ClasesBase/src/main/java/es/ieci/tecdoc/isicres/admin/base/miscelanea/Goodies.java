
package es.ieci.tecdoc.isicres.admin.base.miscelanea;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import es.ieci.tecdoc.isicres.admin.base.guid.GuidExt;

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
      int available, chunkSize, count;

      try {
         chunkSize = 10240;
         buffer = new byte[chunkSize];
         available = input.available();
         while (available > 0) {
            count = input.read(buffer);
            output.write(buffer, 0, count);
            available = input.available();
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

   public static String certReverseDn(String dn)
   {
      String revDn = "";
      int    index = 0;
      
      while (index != -1)
      {
         index = dn.lastIndexOf(',');
         if (index != -1)
         {
            revDn += dn.substring(index+2, dn.length());
            dn = dn.substring(0, index);
            revDn += ", ";
         }
         else
            revDn += dn;
      }
      
      return revDn;
   }

   public static String certGuion(String serialNumber)
   {
      String gSerialNumber = "";
      String tmpSerialNumber;
      
      if ((serialNumber.length() % 2) > 0)
         tmpSerialNumber = "0" + serialNumber;
      else
         tmpSerialNumber = serialNumber;
         
      
      while (tmpSerialNumber.length() > 2)
      {
         gSerialNumber += tmpSerialNumber.substring(0, 2);
         gSerialNumber += "-";
         tmpSerialNumber = tmpSerialNumber.substring(2);
      }
      
      gSerialNumber += tmpSerialNumber;
      
      return(gSerialNumber);
   }
   
   public static String getUniqueId() throws Exception
   {
      GuidExt guidGen = new GuidExt();
      
      return guidGen.toString();
   }

   private Goodies()
   {
   }

}