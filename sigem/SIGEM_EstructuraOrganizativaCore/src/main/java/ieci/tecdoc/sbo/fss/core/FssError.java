package ieci.tecdoc.sbo.fss.core;

public final class FssError
{

   private static final String EC_PREFIX                  = "IECI_TECDOC_FSS_";

   // **************************************************************************

   public static final String  EC_REP_OFF_LINE            = EC_PREFIX
                                                                + "REP_OFF_LINE";

   public static final String  EM_REP_OFF_LINE            = "Repository is offline";

   // **************************************************************************

   public static final String  EC_REP_IS_READONLY         = EC_PREFIX
                                                                + "REP_IS_READONLY";

   public static final String  EM_REP_IS_READONLY         = "Repository is readonly";

   // **************************************************************************

   public static final String  EC_VOL_NOT_READY           = EC_PREFIX
                                                                + "VOL_NOT_READY";

   public static final String  EM_VOL_NOT_READY           = "Volume is not ready";

   // **************************************************************************

   public static final String  EC_VOL_IS_FULL             = EC_PREFIX
                                                                + "VOL_IS_FULL";

   public static final String  EM_VOL_IS_FULL             = "Volume is full";

   // **************************************************************************

   public static final String  EC_VOL_IS_READONLY         = EC_PREFIX
                                                                + "VOL_IS_READONLY";

   public static final String  EM_VOL_IS_READONLY         = "Volume is readonly";

   // **************************************************************************

   public static final String  EC_DIR_NOT_CREATED         = EC_PREFIX
                                                                + "DIR_NOT_CREATED";

   public static final String  EM_DIR_NOT_CREATED         = "Directory has not been created";

   // **************************************************************************

   public static final String  EC_INVALID_PARAM           = EC_PREFIX
                                                                + "INVALID_PARAM";

   public static final String  EM_INVALID_PARAM           = "Invalid parameter";

   // **************************************************************************

   public static final String  EC_NO_VOLS_IN_LIST         = EC_PREFIX
                                                                + "NO_VOLUMES_IN_LIST";

   public static final String  EM_NO_VOLS_IN_LIST         = "There aren´t volumes in list";

   // **************************************************************************

   public static final String  EC_UNABLE_TO_STORE_IN_LIST = EC_PREFIX
                                                                + "UNABLE_TO_STORE_IN_LIST";

   public static final String  EM_UNABLE_TO_STORE_IN_LIST = "Can´t store in list";

   private FssError()
   {
   }

} // class
