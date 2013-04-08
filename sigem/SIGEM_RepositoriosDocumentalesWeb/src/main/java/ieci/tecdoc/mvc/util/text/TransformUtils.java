package ieci.tecdoc.mvc.util.text;

import ieci.tecdoc.mvc.util.encoders.Base64;


public class TransformUtils
{
   
   public static String reverse(String key) {
      StringBuffer sb = new StringBuffer(key);
      return sb.reverse().toString();
   }
   
   public static String decrypt(String key) {
      return reverse(new String(Base64.decode(key)));
   }
   
}
