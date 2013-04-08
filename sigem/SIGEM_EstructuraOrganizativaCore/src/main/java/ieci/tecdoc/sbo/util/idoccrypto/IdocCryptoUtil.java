
package ieci.tecdoc.sbo.util.idoccrypto;

import ieci.tecdoc.core.base64.Base64Util;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.bouncycastle.jce.provider.*;

public final class IdocCryptoUtil
{

   private IdocCryptoUtil()
   {
   }
   
   public static String encryptPassword(String decPwd, String passwordToGenKey)
			     throws Exception
	{

	   String encPwd  = null;	  	  
	      
	   //Obtener el texto que va a dar el hash inicial
	   byte buf[] = passwordToGenKey.getBytes("UTF-8");
	
	   Security.addProvider(new BouncyCastleProvider());
	   
	   //Obtener el hash
	   MessageDigest md = MessageDigest.getInstance("MD5", "BC");
	   
	   md.update(buf);
	   byte hash[] = md.digest();
	
	   //Aunque no lo diga en ninguna parte, el cryptoapi usa una clave de 128 bits con todo ceros
	   //menos los 5 primeros bytes para cifrar....
	   byte newHash[]={(byte)hash[0], (byte)hash[1], (byte)hash[2],
	                   (byte)hash[3], (byte)hash[4],
	                   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
	                   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
	                   (byte)0x00 };
	
	   //Obtener la key basada en ese hash
	   SecretKeySpec key = new SecretKeySpec(newHash, "RC4");
	
	   //Obtener el objeto Cipher e inicializarlo con la key
	   Cipher cipher = Cipher.getInstance("RC4", "BC");
	   cipher.init(Cipher.ENCRYPT_MODE, key);
	
	   //Obtener los datos a cifrar
	   byte bufPlain[] = decPwd.getBytes();
	
	   //cifrar
	   byte bufCipher[] = cipher.doFinal(bufPlain);
	      
	   encPwd  = Base64Util.encode(bufCipher);
	      
	   return encPwd;
      
	} 

   public static String decryptPassword(String encPwd, String passwordToGenKey)
			               throws Exception
	{

	   String decPwd  = null;
	  	      
	   //Obtener el texto que va a dar el hash inicial
	   byte buf[] = passwordToGenKey.getBytes("UTF-8");
	
	   Security.addProvider(new BouncyCastleProvider());
	   
	   //Obtener el hash
	   MessageDigest md = MessageDigest.getInstance("MD5", "BC");
	   
	   md.update(buf);
	   byte hash[] = md.digest();
	
	   //Aunque no lo diga en ninguna parte, el cryptoapi usa una clave de 128 bits con todo ceros
	   //menos los 5 primeros bytes para cifrar....
	   byte newHash[]={(byte)hash[0], (byte)hash[1], (byte)hash[2],
	                   (byte)hash[3], (byte)hash[4],
	                   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
	                   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
	                   (byte)0x00 };
	
	   //Obtener la key basada en ese hash
	   SecretKeySpec key = new SecretKeySpec(newHash, "RC4");
	
	   //Obtener el objeto Cipher e inicializarlo con la key
	   Cipher cipher = Cipher.getInstance("RC4", "BC");
	   cipher.init(Cipher.DECRYPT_MODE, key);
	
	   //Obtener los datos a descifrar 
      //La contraseña viene codificada en base64
	   byte bufPlain[] = Base64Util.decode(encPwd);
	
	   //descifrar
	   byte bufCipher[] = cipher.doFinal(bufPlain);
	      
	   decPwd = new String(bufCipher, "UTF-8");
	      
	   return decPwd;
      
	} 
   
   
} // class
