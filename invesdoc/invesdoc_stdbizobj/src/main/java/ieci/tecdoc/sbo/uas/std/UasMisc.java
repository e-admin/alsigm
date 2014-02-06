package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.sbo.util.idoccrypto.IdocCryptoUtil;

public final class UasMisc
{

	//~ Static fields/initializers ---------------------------------------------

	public static final int    USER_IS_NOT_LOCKED	   = 0;
	public static final int    USER_IS_LOCKED		      = 1;
	public static final int    RESETED_NUM_CNTS		   = 0;
	public static final String PWD_MUST_BE_CHANGED     = "Y";
	public static final String PWD_MUST_NOT_BE_CHANGED = "N";
	public static final double PWD_VP_INDF			      = -1;
	public static final int    PWD_MAX_LENGTH		      = 32;
	public static final int    PWD_FLAGS_DEF           = 0;
	public static final int    PWD_FLAGS_ALFANUM       = 1;
   
   private static final String PWD_TO_CRYPT  = "IUSER";  

	//~ Constructors -----------------------------------------------------------

	private UasMisc(){}

	//~ Methods ----------------------------------------------------------------

	public static String encryptPassword(String decPwd, int userId)
			     throws Exception
	{

	   String encPwd           = null;	  
	   String passwordToGenKey = PWD_TO_CRYPT + userId;
	      
	   encPwd = IdocCryptoUtil.encryptPassword(decPwd, passwordToGenKey);
	      
	   return encPwd;
      
	}  
   
   public static String decryptPassword(String pwd, int userId)
                        throws Exception
   {
   
      String decPwd           = null;    
      String passwordToGenKey = PWD_TO_CRYPT + userId;
      
      decPwd = IdocCryptoUtil.decryptPassword(pwd, passwordToGenKey);
      
      return decPwd;
   
   }  

	public static boolean validatePassword(int userId, String decPwd, String encPwd)
									throws Exception
	{

      boolean valid = false;
      String  encPwd1;
      
      encPwd1 = encryptPassword(decPwd, userId);
      if (encPwd1.equals(encPwd))
         valid = true;

		return valid;
      
	}

	public static int getVpDays(double vp)
	{

		return (int)(vp/24);

	}

	public static double getVp(int d)
	{

		return (double)d*24;

	}

}// class
