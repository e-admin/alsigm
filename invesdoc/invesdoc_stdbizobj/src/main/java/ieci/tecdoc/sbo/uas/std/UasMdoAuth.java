package ieci.tecdoc.sbo.uas.std;

//import org.apache.log4j.Logger;

import java.util.Date;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbError;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.exception.IeciTdException;

import ieci.tecdoc.sbo.idoc.util.UsrPoolBno;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.uas.base.UasBaseError;


public final class UasMdoAuth
{
   /**
    * Log4J Logger for this class
    */
   //private static final Logger logger = Logger.getLogger(UasMdoAuth.class);

    //~ Constructors -----------------------------------------------------------

    private UasMdoAuth(){}

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param name Login del usuario
     * @param pwd Password introducida por el usuario al logarse
     * @param cntsTriesNum Número de intentos de login que ha realizado el
     *        usuario
     *
     * @return UasAuthToken        Información completa del usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     *
     * @since V1.0
     */
    public static UasAuthToken authenticateUser(String name, String pwd,
                                                int cntsTriesNum)
                               throws Exception
    {

        UasAuthToken   token;
        UasDaoUserRecO userRecO;
        UasDaoSysRecO  sysRecO;
        
        sysRecO = UasDaoSysTbl.selectRecO();
        
        checkLoginAttemptCount(cntsTriesNum, sysRecO.getMaxBadCnts());
        
        userRecO = findUserByName(name);
        
        checkUserLocked(userRecO.getStat());
        checkPasswordValid(userRecO, pwd, sysRecO.getMaxBadCnts());
        checkPasswordMustBeChanged(userRecO.getPwdMbc());
        if (userRecO.getPwdVpCheck().compareTo("Y") == 0)
           checkPasswordExpired(userRecO.getPwdLastUpdts(), sysRecO.getPwdVp());
        
        token = buildAuthToken(userRecO);

        return token;

    }
    
   public static UasAuthToken authenticateDirectUser(int groupId)
                               throws Exception
   {

      UasAuthToken   token;
      int            userId;
      UasDaoUserRecO userRecO;
      
      userId   = UsrPoolBno.getFreeUser(groupId);
      userRecO = UasDaoUserTbl.selectRecO(userId);
      token    = buildAuthToken(userRecO);

		return token;

   }
   
   public static UasAuthToken authenticateDirectUser()
   					throws Exception
   {

      UasAuthToken   token;
      int            userId;
      UasDaoUserRecO userRecO;

      userId   = UsrPoolBno.getFreeUser();
      userRecO = UasDaoUserTbl.selectRecO(userId);
      token    = buildAuthToken(userRecO);

      return token;

   }
   
   public static void DirectUserLogout(int userId, int groupId)
                      throws Exception
   {
      UsrPoolBno.freeUser(userId,groupId);
   }

   public static void DirectUserLogout(int userId)
   						throws Exception
   {
      UsrPoolBno.freeUser(userId);
   }
   	   /**
     * Cambia la contraseña del usuario, si y solo si, se cumplen los
     * requisitos fijados en  la aplicación. Lanza una excepción si no se ha
     * podido cambiar la pasword indicando el motivo
     *
     * @param name Login del usuario
     * @param pwd Password introducida por el usuario al logarse
     * @param cntsTriesNum Número de intentos de login que ha realizado el
     *        usuario
     * @param newPwd1 Nueva password del usuario
     * @param newPwd2 Verificación de la nueva password del usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     *
     * @since V1.0
     */
    public static void changePassword(String name, String pwd,
                                      int cntsTriesNum, String newPwd1,
                                      String newPwd2)
                       throws Exception
    {

        UasDaoUserRecO userRecO;
        UasDaoSysRecO  sysRecO;        
        
        sysRecO = UasDaoSysTbl.selectRecO();

        checkLoginAttemptCount(cntsTriesNum, sysRecO.getMaxBadCnts());
        checkNewPasswordLen(sysRecO.getPwdMinLen(), newPwd1);

        userRecO = findUserByName(name);
        
        checkUserLocked(userRecO.getStat());
        checkPasswordValid(userRecO, pwd, sysRecO.getMaxBadCnts());
        checkNewPasswordFormat(newPwd1,sysRecO.getFlags());
        checkOldAndNewPasswordDoNotMatch(pwd, newPwd1);
        checkOtherAndNewPasswordDoNotMath(sysRecO.getNumPwdLock(),
                                          userRecO.getId(),newPwd1);
        checkTwoNewPasswordsMatch(newPwd1, newPwd2);
        updatePassword(pwd, newPwd1, userRecO.getId(),
                       sysRecO.getNumPwdLock());
        
    }

    /**
     * Busca un usuario en la aplicación. Devuelve una instancia de
     * <code>UasDaoUserRecO</code> con toda la información del usuario si el
     * usario existe o lanza una excepción si el usuario no existe en el
     * sistema.
     *
     * @param name Login del usuario.
     *
     * @return UasDaoUserRecO    Objeto que tiene la información sobre el
     *         usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static UasDaoUserRecO findUserByName(String name)
                                  throws Exception
    {

        try
        {

            return UasDaoUserTbl.selectRecO(name);

        }
         catch(IeciTdException e)
        {

            if(e.getErrorCode().equals(DbError.EC_NOT_FOUND))
            {
             
               throw new IeciTdException(UasBaseError.EC_INVALID_USER_NAME,
                                         UasBaseError.EM_INVALID_USER_NAME);
               
            }
            else
                throw e;

        }

    }

    
   
    /**
     * IECISA
     *
     * @param cntsTriesNum Número de intentos de login que ha realizado el
     *        usuario
     * @param maxBadCnts Número máximo de intentos de login que puede realizar
     *        un usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     */
    private static void checkLoginAttemptCount(int cntsTriesNum, int maxBadCnts)
                        throws Exception
    {

        if(cntsTriesNum > maxBadCnts)
        {
         
           throw new IeciTdException(UasBaseError.EC_TOO_MANY_LOGIN_ATTEMPTS,
                                      UasBaseError.EM_TOO_MANY_LOGIN_ATTEMPTS);

        }
        
    }

    /**
     * DOCUMENT ME!
     *
     * @param isUserLocked Indica si el usuario está bloqueado o no.
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static void checkUserLocked(int isUserLocked)
                        throws Exception
    {

        if(isUserLocked == UasMisc.USER_IS_LOCKED)
        {
         
           throw new IeciTdException(UasError.EC_USER_LOCKED,
                                      UasError.EM_USER_LOCKED);

        }
        
    }

    /**
     * DOCUMENT ME!
     *
     * @param userRecO Objeto que tiene la información sobre el usuario
     * @param pwd Password introducida por el usuario al logarse
     * @param maxBadCnts Número máximo de intentos de login que puede realizar
     *        un usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static void checkPasswordValid(UasDaoUserRecO userRecO, String pwd,
                                           int maxBadCnts)
                        throws Exception
    {

        boolean valid = UasMisc.validatePassword(userRecO.getId(), pwd, userRecO.getPwd());
        
        if(!valid)
        {

            manageInvalidPassword(userRecO, maxBadCnts);
            throw new IeciTdException(UasBaseError.EC_INVALID_AUTH_SPEC,
                                      UasBaseError.EM_INVALID_AUTH_SPEC);

        }
        else
        {

            if(userRecO.getNumBadCnts() != UasMisc.RESETED_NUM_CNTS)
                resetNumBadCnts(userRecO);

        }

    }

    /**
     * DOCUMENT ME!
     *
     * @param pwd nueva Password introducida por el usuario
     * @param flags indica si hay que aplicar formato a la contraseña
     * 
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static void checkNewPasswordFormat(String newpwd, int flags)
                        throws Exception
    {

       boolean valid, isChar, isNumber;       
       int     len,i,j;
       char    val;
       byte    buf[];
      
       
       if (flags == UasMisc.PWD_FLAGS_ALFANUM)
       {
          isChar = false;
          isNumber = false;
          valid = true;
          buf = newpwd.getBytes();
          len   = buf.length;

          for (i=0; i<len; i++)
          {
           
             j = buf[i];
             
             if ((j>= 97 && j <= 122) || (j >= 65 && j <= 90) || (j == -15) || (j== -47))
                isChar = true;
             else if (j >= 48 && j <= 57)
                isNumber = true;
             else
                valid = false;

          }

          if (valid)
          {
             if (!isChar || !isNumber)
                valid = false;
          }
          
          if (!valid)
          {
             throw new IeciTdException(UasError.EC_PWD_MUST_BE_ALFANUM,
                   UasError.EM_PWD_MUST_BE_ALFANUM);
          }
          
       } 
        
    }

    
    /**
     * DOCUMENT ME!
     *
     * @param userRecO Objeto que tiene la información sobre el usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     *
     * @since V1.0
     */
    private static void resetNumBadCnts(UasDaoUserRecO userRecO)
                        throws Exception
    {

        try
        {

            DbConnection.beginTransaction();
            UasDaoUserTbl.updateNumBadCnts(UasMisc.RESETED_NUM_CNTS,
                                           userRecO.getId());
            DbConnection.endTransaction(true);

        }
         catch(Exception e)
        {

            DbConnection.ensureEndTransaction(e);

        }

    }

    /**
     * DOCUMENT ME!
     *
     * @param pwdLastUpdts Fecha transcurridas desde la última modificación de
     *        la contraseña, desde el 1 de Enero de 1971.
     * @param pwdVp Periodo de validez de la contraseña.
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static void checkPasswordExpired(double pwdLastUpdts, double pwdVp)
                        throws Exception
    {

        double ctd;

        if(pwdVp != UasMisc.PWD_VP_INDF)
        {

            ctd = DateTimeUtil.getCurrentTimeHours();

            if((pwdLastUpdts+pwdVp) < ctd)
            {
             
               throw new IeciTdException(UasError.EC_MUST_CHANGE_PWD,
                                          UasError.EM_MUST_CHANGE_PWD);

            }
            
        }

    }

    /**
     * DOCUMENT ME!
     *
     * @param pwdMBC Especifica si la contraseña debe cambiarse o no
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static void checkPasswordMustBeChanged(String pwdMBC)
                        throws Exception
    {

        if(pwdMBC.equals(UasMisc.PWD_MUST_BE_CHANGED))
        {
         
           throw new IeciTdException(UasError.EC_MUST_CHANGE_PWD,
                                      UasError.EM_MUST_CHANGE_PWD);

        }
        
    }

    /**
     * DOCUMENT ME!
     *
     * @param userRecO Objeto que tiene la información sobre el usuario
     *
     * @return UasAuthToken        Información completa del usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     *
     * @since V1.0
     */
    private static UasAuthToken buildAuthToken(UasDaoUserRecO userRecO)
                                throws Exception
    {

        UasAuthToken               token;
        int                        groupId;
        IeciTdLongIntegerArrayList groupIds;
        int                        count;
        int                        i;
        
        token = new UasAuthToken();
        
        token.setUser(userRecO.getId());
        token.setDept(userRecO.getDeptId());
        
        groupIds = UasDaoGURelTbl.selectGroupIds(userRecO.getId());
        count    = groupIds.count();

        for(i = 0; i < count; i++)
        {

            groupId = groupIds.get(i);
        
            token.addGroup(groupId);

        }

        return token;

    }

    /**
     * DOCUMENT ME!
     *
     * @param userRecO Objeto que tiene la información sobre el usuario
     * @param maxBadCnts Número máximo de intentos de login que puede realizar
     *        un usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     *
     * @since V1.0
     */
    private static void manageInvalidPassword(UasDaoUserRecO userRecO,
                                              int maxBadCnts)
                                       throws Exception
    {

        short pwdIc;

        try
        {

            DbConnection.beginTransaction();

            if((userRecO.getNumBadCnts()+1) < maxBadCnts)

                UasDaoUserTbl.updateNumBadCnts(userRecO.getNumBadCnts()+1,
                                               userRecO.getId());
            else
            {

                UasDaoUserRecUa userRecUa = new UasDaoUserRecUa();
            
                userRecUa.setStat(UasMisc.USER_IS_LOCKED);
                userRecUa.setNumBadCnts(UasMisc.RESETED_NUM_CNTS);
                UasDaoUserTbl.incrementNumBadCntsAndLocked(userRecUa,
                                                           userRecO.getId());

            }
            DbConnection.endTransaction(true);

        }
         catch(Exception e)
        {

            DbConnection.ensureEndTransaction(e);

        }

    }

    /**
     * DOCUMENT ME!
     *
     * @param newPwd1 Nueva password del usuario
     * @param newPwd2 Verificación de la nueva password del usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static void checkTwoNewPasswordsMatch(String newPwd1, String newPwd2)
                                           throws Exception
    {

        if(!newPwd1.equals(newPwd2))
        {
         
           throw new IeciTdException(UasError.EC_TWO_NEW_PWDS_DO_NOT_MATCH,
                                     UasError.EM_TWO_NEW_PWDS_DO_NOT_MATCH);
        
        }

    }

    /**
     * DOCUMENT ME!
     *
     * @param pwdMinLen Mínima longitud que debe tener una password
     * @param newPwd Nueva password del usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static void checkNewPasswordLen(short pwdMinLen, String newPwd)
                                     throws Exception
    {

        if((newPwd.length() < pwdMinLen) ||
               (newPwd.length() > UasMisc.PWD_MAX_LENGTH))
        {
           
            throw new IeciTdException(UasError.EC_INVALID_PWD_LEN,
                                      UasError.EM_INVALID_PWD_LEN);
        
        }

    }

    /**
     * DOCUMENT ME!
     *
     * @param oldPwd Antigua password del usuario
     * @param newPwd Nueva password del usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */
    private static void checkOldAndNewPasswordDoNotMatch(String oldPwd,
                                                         String newPwd)
                                                  throws Exception
    {

        if(oldPwd.equals(newPwd))
        {
         
           throw new IeciTdException(UasError.EC_OLD_AND_NEW_PWD_CANNOT_MATCH,
                                     UasError.EM_OLD_AND_NEW_PWD_CANNOT_MATCH);

        }
        
    }

    /**
     * DOCUMENT ME!
     *
     * @param numPwdLock  número de contraseñas a validar
     * @param userId Identificador de usuario
     * @param newPwd Nueva password del usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */

    private static void checkOtherAndNewPasswordDoNotMath(short numPwdLock,
                                                          int userId,
                                                          String newPwd)
                                                   throws Exception
    {
      
       IeciTdShortTextArrayList passwords;
       int                      num;
       
       if (numPwdLock > 1)
       {
                    
          passwords = UasDaoPwdsTbl.selectPasswords(userId);
          num = passwords.count();
          
          if (num > numPwdLock -1)
             num = numPwdLock -1;
          
          for (int i=0; i< num; i++)
          {
             
             String pwd = UasMisc.decryptPassword(passwords.get(i),userId);
             
             if (pwd.equals(newPwd))
             {
                throw new IeciTdException(UasError.EC_OLD_AND_NEW_PWD_CANNOT_MATCH,
                                          UasError.EM_OLD_AND_NEW_PWD_CANNOT_MATCH);		
             }
             
          }
          
       }
       
    }

    /**
     * DOCUMENT ME!
     *
     * @param newPwd Nueva password del usuario
     * @param userId Identificador del usuario
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     *
     * @since V1.0
     */
    private static void updatePassword(String oldPwd, String newPwd, 
                                       int userId, short numPwdLock)
                        throws Exception
    {

        String encPwd;
        double cth;
        
        encPwd    = UasMisc.encryptPassword(newPwd, userId);        
        cth       = DateTimeUtil.getCurrentTimeHours();

        try
        {

            DbConnection.beginTransaction();

            UasDaoUserRecUb userRecUb = new UasDaoUserRecUb();
        
            userRecUb.setPwd(encPwd);
            userRecUb.setPwdLastUpdts(cth);
            userRecUb.setPwdMbc(UasMisc.PWD_MUST_NOT_BE_CHANGED);
            UasDaoUserTbl.updateRow(userRecUb, userId);
            updateUserPasswords(oldPwd, userId, numPwdLock);
            
            DbConnection.endTransaction(true);

        }
         catch(Exception e)
        {

            DbConnection.ensureEndTransaction(e);

        }

    }
    /**
     * DOCUMENT ME!
     *
     * @param oldpwd Contraseña a guardar
     * @param userId Identificador de usuario
     * @param numPwdsLock Nùmero de contraseñas a validar
     *
     * @throws Exception Exception if the application business logic throws an
     *         exception
     * @throws IeciTdException DOCUMENT ME!
     *
     * @since V1.0
     */ 
   private static void updateUserPasswords(String oldPwd, int userId, 
                                           short numPwdsLock)
                                     throws Exception
   {
      
      String                   encPwd;
      Date                     updDate;
      IeciTdShortTextArrayList passwords;
      UasDaoPwdsRecAc          rec; 
      
      if (numPwdsLock > 1)
      {
         
         rec       = new UasDaoPwdsRecAc();
         passwords = UasDaoPwdsTbl.selectPasswords(userId);         
      
         encPwd  = UasMisc.encryptPassword(oldPwd, userId);
         updDate = DateTimeUtil.getCurrentDateTime(); 
      
         rec.m_id      = userId;
         rec.m_pwd     = encPwd;
         rec.m_updId   = userId;
         rec.m_updDate = updDate;
         
         if (passwords.count() >= numPwdsLock -1)
         {            
            UasDaoPwdsTbl.deleteRow(userId, passwords.get(0)); 
         }
      
         UasDaoPwdsTbl.insertRow(rec);
         
      }
      
   }    
                        
}
 // class
