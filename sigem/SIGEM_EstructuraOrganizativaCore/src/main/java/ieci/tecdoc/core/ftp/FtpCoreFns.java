package ieci.tecdoc.core.ftp;

import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.net.InetAddress;
import ieci.tecdoc.core.exception.IeciTdException;

public final class FtpCoreFns
{

   private FtpCoreFns()
   {
   }

   public static void sendCommand(OutputStreamWriter req, StringBuffer reqText,
         String cmd)
      throws Exception
   {

      reqText.setLength(0);
      reqText.append(cmd).append(FtpCoreDefs.EOL);

      req.write(reqText.toString());
      req.flush();

   }

   public static void sendCommand(OutputStreamWriter req, StringBuffer reqText,
         String cmd, String args)
      throws Exception
   {

      reqText.setLength(0);
      reqText.append(cmd).append(' ').append(args).append(FtpCoreDefs.EOL);

      req.write(reqText.toString());
      req.flush();

   }

   // Se tienen que leer todas las líneas de la respuesta, aunque sólo se
   // guarda la primera.

   public static int getResponse(BufferedReader resp, StringBuffer respText)
      throws Exception
   {

      int respCode;
      String line;
      String code, aux;
      boolean complete = false;

      respText.setLength(0);
      respCode = 0;

      line = resp.readLine();

      if (line.charAt(3) == '-') //multilinea
      {

         code = line.substring(0, 3);
         while (!complete)
         {
            aux = resp.readLine();
            if (!aux.equals("") && aux.substring(0, 3).equals(code)
                  && aux.charAt(3) == ' ')
            {
               complete = true;
            }
         }
      }

      if (line == null) { throw new IeciTdException(FtpError.EC_INVALID_RESP,
            FtpError.EM_INVALID_RESP); }

      if (line.length() < 3) { throw new IeciTdException(
            FtpError.EC_INVALID_RESP, FtpError.EM_INVALID_RESP); }

      try
      {
         respCode = Integer.parseInt(line.substring(0, 3));
      }
      catch (NumberFormatException e)
      {
         throw new IeciTdException(FtpError.EC_INVALID_RESP,
               FtpError.EM_INVALID_RESP);
      }

      respText.append(line);

      return respCode;

   }

   public static boolean isPpResponse(int respCode)
   {
      return ((respCode >= 100) && (respCode < 200));
   }

   public static boolean isPcResponse(int respCode)
   {
      return ((respCode >= 200) && (respCode < 300));
   }

   public static boolean isPiResponse(int respCode)
   {
      return ((respCode >= 300) && (respCode < 400));
   }

   public static String getLocalHostAddress()
      throws Exception
   {
      return InetAddress.getLocalHost().getHostAddress();
   }

} // class
