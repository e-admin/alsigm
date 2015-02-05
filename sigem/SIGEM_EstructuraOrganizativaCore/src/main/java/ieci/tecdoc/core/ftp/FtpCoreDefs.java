package ieci.tecdoc.core.ftp;

public final class FtpCoreDefs
{

   public static final String EOL              = "\r\n";

   public static final int    BLOCK_SIZE       = 4096;

   public static final String CMD_USER         = "USER";
   public static final String CMD_PASS         = "PASS";
   public static final String CMD_QUIT         = "QUIT";
   public static final String CMD_PORT         = "PORT";
   public static final String CMD_STOR         = "STOR";
   public static final String CMD_RETR         = "RETR";
   public static final String CMD_TYPE         = "TYPE";
   public static final String CMD_MKD          = "MKD";
   public static final String CMD_DELE         = "DELE";
   public static final String CMD_SIZE         = "SIZE";
   public static final String CMD_PWD          = "PWD";   

   public static final String BINARY_FILE_TYPE = "I";

   private FtpCoreDefs()
   {
   }

} // class
