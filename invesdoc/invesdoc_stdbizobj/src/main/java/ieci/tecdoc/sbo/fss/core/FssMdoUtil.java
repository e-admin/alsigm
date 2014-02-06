package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.ftp.FtpConnectionInfo;
import ieci.tecdoc.core.textutil.UtilX;
import java.util.StringTokenizer;
import ieci.tecdoc.sbo.util.idoccrypto.IdocCryptoUtil;

public final class FssMdoUtil
{
   public static final int    RT_PFS             = 3;
   public static final int    RT_FTP             = 1;
   public static final int    RT_CENTERA         = 4;
   public static final int    RT_DB              = 5;

   public static final int    OS_WINDOWS         = 1;
   public static final int    OS_NT              = 2;
   public static final int    OS_UNIX            = 3;
   public static final int    OS_LINUX           = 5;

   public static final char  WINDOWS_SEPARATOR  = '\\';
   public static final char  UNIX_SEPARATOR     = '/';
   public static final char  LINUX_SEPARATOR    = '/';

   static final int           DIR_MAX_NUM_FILES  = 1024;

   public static final int    REP_STAT_OFFLINE   = 1;
   public static final int    REP_STAT_READONLY  = 2;

   public static final int    REP_FLAG_NULL      = 0;
   public static final int    REP_FLAG_CENTERA_ENABLE_COLLISION_AVOIDANCE = 8;

   private static final int   REP_DIR_FMT_DF     = 3;
   private static final int   REP_NOT_LOCAL      = 0;

   public static final int    VOL_STAT_NOT_READY = 1;
   public static final int    VOL_STAT_READONLY  = 2;
   public static final int    VOL_STAT_FULL      = 4;

   public static final int    VOL_FLAG_NULL      = 0;
   
   public static final int    FILE_FLAG_NONE     = 0;
   public static final int    FILE_FLAG_FTS      = 1;

   public static final int    STAT_DEF           = 0;

   public static final String NEXT_ID_TBL_NAME   = "IVOLNEXTID";
   public static final int    NEXT_ID_TYPE_REP   = 1;
   public static final int    NEXT_ID_TYPE_VOL   = 2;
   public static final int    NEXT_ID_TYPE_LIST  = 3;
   public static final int    NEXT_ID_TYPE_FILE  = 4;

   private static final String REP_INFO_CURRENT_VERS = "01.01";
   private static final String VOL_INFO_CURRENT_VERS = "01.01";

   private static final String FTP_PWD_TO_CRYPT  = "IVOLFTP";	
   private static final String CENTERA_PWD_TO_CRYPT = "IVOLCENTERA";

   private FssMdoUtil()
   {
   }

   public static char getFileNameSeparator(int os)
   {
      if ( (os == OS_WINDOWS) || (os == OS_NT) )
         return WINDOWS_SEPARATOR;
      else if (os == OS_LINUX)
         return LINUX_SEPARATOR;
      else
         return UNIX_SEPARATOR;
   }

   // **************************************************************************

   public static FssRepInfo decodeRepInfo(String info) throws Exception
   {
      StringTokenizer tokens = new StringTokenizer(info, "|");
      String str = null;
      String loc = null;
      FssRepInfo repInfo = new FssRepInfo();
      int aux;

      //Versión
      str = UtilX.parseStringNextToken(tokens, true);

      //Localización
      loc = UtilX.parseStringNextToken(tokens, true);

      //Tipo de repositorio
      repInfo.m_type = UtilX.parseIntegerNextToken(tokens);

      //Sistema operativo
      repInfo.m_os = UtilX.parseIntegerNextToken(tokens);

      //Formato de directorios
      aux = UtilX.parseIntegerNextToken(tokens);

      //Local
      aux = UtilX.parseIntegerNextToken(tokens);

      // Flags
      repInfo.m_flags = UtilX.parseIntegerNextToken(tokens);

      decodeRepLocInfo(loc, repInfo.m_type, repInfo.m_os, repInfo);

      return repInfo;

   }

   public static String encodeRepInfo(FssRepInfo repInfo) throws Exception
   {      
      String       info   = null;
      StringBuffer buffer  = new StringBuffer();
      String       locInfo = null;
      String       vers;

      vers = '"' + REP_INFO_CURRENT_VERS + '"';
           
      buffer.append(vers);//versión
      buffer.append("|");
     
      locInfo = '"' + encodeRepLocInfo(repInfo) + '"';

      buffer.append(locInfo);//loc
      buffer.append("|");

      buffer.append(repInfo.m_type);//RepType
      buffer.append("|");

      buffer.append(repInfo.m_os);//OS
      buffer.append("|");

      buffer.append(REP_DIR_FMT_DF);//DirFmt
      buffer.append("|");

      buffer.append(REP_NOT_LOCAL);//Local
      buffer.append("|");

      buffer.append(repInfo.m_flags);//flags
     
      info = buffer.toString();

      return info;

   }

   public static FssVolInfo decodeVolInfo(String info) throws Exception
   {
      StringTokenizer tokens = new StringTokenizer(info, "|");
      String str = null;
      String loc = null;
      FssVolInfo volInfo = new FssVolInfo();

      //Versión
      str = UtilX.parseStringNextToken(tokens, true);

      //Tipo de repositorio
      volInfo.m_repType = UtilX.parseIntegerNextToken(tokens);

      if ((volInfo.m_repType == FssMdoUtil.RT_FTP)
            || (volInfo.m_repType == FssMdoUtil.RT_PFS) 
            || (volInfo.m_repType == FssMdoUtil.RT_DB))
      {
         //Localización
         volInfo.m_path = UtilX.parseStringNextToken(tokens, true);

         //Tamaño máximo
         volInfo.m_maxSize = UtilX.parseStringNextToken(tokens, true);
      }
      else
      {
         volInfo.m_maxSize = null;
      }

      //Flags
      volInfo.m_flags = UtilX.parseIntegerNextToken(tokens);

      return volInfo;
   }

   public static String encodeVolInfo(FssVolInfo volInfo) throws Exception
   {      
      String       info   = null;
      StringBuffer buffer = new StringBuffer();
      String       path   = null;
      String       vers;
      String       maxSize;

      vers = '"' + VOL_INFO_CURRENT_VERS + '"';
           
      buffer.append(vers);//versión
      buffer.append("|");
      
      buffer.append(volInfo.m_repType);//versión
      buffer.append("|");
      
      if (volInfo.m_repType != FssMdoUtil.RT_CENTERA)
      {
         path = '"' + volInfo.m_path + '"';

         buffer.append(path);//loc
         buffer.append("|");

         maxSize = '"' + volInfo.m_maxSize + '"';
      
         buffer.append(maxSize);
         buffer.append("|");
      }

      buffer.append(volInfo.m_flags);
      
      info = buffer.toString();

      return info;

   }

   public static FtpConnectionInfo createFtpConnectionInfo(FssRepInfo repInfo)
         throws Exception
   {

      FtpConnectionInfo info;

      info = new FtpConnectionInfo(repInfo.m_srv, repInfo.m_port,
            repInfo.m_usr, repInfo.m_pwd);

      return info;

   }

   // **************************************************************************

   private static void decodeRepLocInfo(String locInfo, int repType, int repOs,
         FssRepInfo repInfo) throws Exception
   {
      StringTokenizer tokens = null;
      String          encPwd = null;
      String          pwd    = null;
      String          path;
      char            ftpSep = '/'; 
      char            sep;
      
      if (repType == FssMdoUtil.RT_FTP || repType == FssMdoUtil.RT_CENTERA)
      {

         tokens = new StringTokenizer(locInfo, ",");

         repInfo.m_srv = UtilX.parseStringNextToken(tokens);
         repInfo.m_port = UtilX.parseIntegerNextToken(tokens);
         repInfo.m_usr = UtilX.parseStringNextToken(tokens);

         encPwd = UtilX.parseStringNextToken(tokens);
         if (repType == FssMdoUtil.RT_FTP)
            pwd =  decryptPassword(encPwd);
         else
            pwd =  decryptCenteraPassword(encPwd);

         repInfo.m_pwd = pwd;
         
         
         /* Desde el administrador de volúmenes el directorio del repositorio
          * si es ftp siempre se pone con el separador "/" esté en el sistema 
          * operativo que esté
          */
         if (repType != FssMdoUtil.RT_CENTERA)
         {
            path = UtilX.parseStringNextToken(tokens);
         
            sep = getFileNameSeparator(repOs);
         
            repInfo.m_path = path.replace(ftpSep,sep);
         }
         else
            repInfo.m_path = "";

      }
      else
      {
         repInfo.m_path = locInfo;
      }

   }

   public static String decryptPassword(String encPwd)
			               throws Exception
	{

	   String decPwd           = null;	  
	   String passwordToGenKey = FTP_PWD_TO_CRYPT;	      
	   
	   decPwd = IdocCryptoUtil.decryptPassword(encPwd, passwordToGenKey);
	      
	   return decPwd;
      
	}

   public static String decryptCenteraPassword(String encPwd)
   							throws Exception
   {

      String decPwd           = null;	  
      String passwordToGenKey = CENTERA_PWD_TO_CRYPT;	      

      decPwd = IdocCryptoUtil.decryptPassword(encPwd, passwordToGenKey);

      return decPwd;

   }

   private static String encryptFtpPassword(String pwd)
			                throws Exception
	{

	   String encPwd            = null;	  
	   String passwordToGenKey  = FTP_PWD_TO_CRYPT;	      
	   
	   encPwd = IdocCryptoUtil.encryptPassword(pwd, passwordToGenKey);
	      
	   return encPwd;
      
	}
   
   private static String encryptCenteraPassword(String pwd)
   throws Exception
   {

      String encPwd            = null;	  
      String passwordToGenKey  = CENTERA_PWD_TO_CRYPT;	      

      encPwd = IdocCryptoUtil.encryptPassword(pwd, passwordToGenKey);

      return encPwd;

   }

   private static String encodeRepLocInfo(FssRepInfo repInfo) throws Exception
   {      
      StringBuffer buffer  = new StringBuffer();
      String       locInfo = null;
      String       encPwd  = null;

      if (repInfo.m_type == RT_PFS)
         locInfo = repInfo.m_path; 
      if (repInfo.m_type == RT_FTP || repInfo.m_type == RT_CENTERA)
      {
         if (repInfo.m_type == RT_FTP)
            encPwd =  encryptFtpPassword(repInfo.m_pwd);
         else
            encPwd =  encryptCenteraPassword(repInfo.m_pwd);

         buffer.append(repInfo.m_srv);//Servidor ftp
         buffer.append(",");

         buffer.append(repInfo.m_port);//Puerto
         buffer.append(",");

         buffer.append(repInfo.m_usr);//usuario
         buffer.append(",");

         buffer.append(encPwd);//contraseña
         buffer.append(",");
         
         buffer.append(repInfo.m_path);//RootDir

         locInfo = buffer.toString();
      }       
      if (repInfo.m_type == RT_DB)
         locInfo = "";

      return locInfo;

   }  

} // class
