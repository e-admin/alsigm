
package ieci.tecdoc.core.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class Base64Util
{
   
   private Base64Util()
   {
   }
   
   public static String encode(byte[] decData)
   {
      
      String        encData;
      BASE64Encoder encoder;
      
      encoder = new BASE64Encoder();      
      encData = encoder.encode(decData);
      
      return encData;
      
   }
   
   public static String encodeString(String decData)
   {
      return encode(decData.getBytes());
   }
   
   public static byte[] decode(String encData) throws Exception
   {
      
      byte[]        decData;
      BASE64Decoder decoder;
      
      decoder = new BASE64Decoder();      
      decData = decoder.decodeBuffer(encData);
      
      return decData;
      
   }

   public static String decodeToString(String encData) throws Exception
   {
      return new String(decode(encData));
   }

} // class
