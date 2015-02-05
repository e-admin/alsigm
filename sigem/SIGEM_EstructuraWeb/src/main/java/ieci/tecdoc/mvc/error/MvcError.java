package ieci.tecdoc.mvc.error;


public final class MvcError
{
   
    public static final long EC_PREFIX = 3100000;   
   
   // **************************************************************************
        
   public static final long EC_NOT_ENOUGH_PERMISSIONS =  EC_PREFIX + 1; 
   
   public static final long EC_OBJ_LDAP_NOT_EXITS = EC_PREFIX + 2;
   
   public static final long EC_OBJ_LDAP_SEARCH_USER_NOT_FOUND = EC_PREFIX + 3;
   public static final long EC_OBJ_LDAP_SEARCH_EXCESS_USER = EC_PREFIX + 4;
   
   public static final long EC_OBJ_LDAP_SEARCH_GROUP_NOT_FOUND = EC_PREFIX + 5;
   public static final long EC_OBJ_LDAP_SEARCH_EXCESS_GROUP = EC_PREFIX + 6;

   
   // Meter en el fichero d properties
   public static final long EC_NOT_CAN_DELETE_DEPT = EC_PREFIX + 7;
   public static final long EC_NOT_CAN_EDIT_DEPT = EC_PREFIX + 8;
   public static final long EC_NOT_CAN_CREATE_DEPT = EC_PREFIX + 9;
   public static final long EC_NOT_CAN_DELETE_GROUP = EC_PREFIX + 10;
   public static final long EC_NOT_CAN_EDIT_GROUP = EC_PREFIX + 11;
   public static final long EC_NOT_CAN_CREATE_GROUP = EC_PREFIX + 12;
   public static final long EC_NOT_CAN_DELETE_USER = EC_PREFIX + 13;
   
   public static final long EC_NOT_CAN_EDIT_USER = EC_PREFIX + 14;
   public static final long EC_NOT_CAN_CREATE_USER = EC_PREFIX + 15;
   
   public static final long EC_NOT_CAN_ASSING_USER_TO_GROUP = EC_PREFIX + 16;
   public static final long EC_NOT_CAN_DELETE_GROUP_USER = EC_PREFIX + 17;
   
   public static final long EC_NOT_CAN_VIEW_DEPT = EC_PREFIX + 18;
   public static final long EC_NOT_CAN_VIEW_GROUP = EC_PREFIX + 19;
   
   public static final long EC_NOT_CAN_BLOCK_USER = EC_PREFIX + 20;
   public static final long EC_NOT_CAN_ADMIN = EC_PREFIX + 21;
   
   public static final long EC_DUPLICATE_CERTIFICATE = EC_PREFIX + 22;
   public static final long EC_CERTIFICATE_ERROR_1 = EC_PREFIX + 23;
   public static final long EC_CERTIFICATE_ERROR_2 = EC_PREFIX + 24;
   public static final long EC_CERTIFICATE_ERROR_5 = EC_PREFIX + 25;
   public static final long EC_CERTIFICATE_ERROR_6 = EC_PREFIX + 26;
   public static final long EC_CERTIFICATE_ERROR_7 = EC_PREFIX + 27;
   public static final long EC_CERTIFICATE_ERROR_8 = EC_PREFIX + 28;
   public static final long EC_CERTIFICATE_ERROR_9 = EC_PREFIX + 29;
   public static final long EC_CERTIFICATE_ERROR_11 = EC_PREFIX + 30;
   public static final long EC_CERTIFICATE_ERROR_12 = EC_PREFIX + 31;
   public static final long EC_CERTIFICATE_ERROR_13 = EC_PREFIX + 32;
   public static final long EC_CERTIFICATE_ERROR_14 = EC_PREFIX + 33;
   public static final long EC_CERTIFICATE_ERROR_15 = EC_PREFIX + 34;
   public static final long EC_CERTIFICATE_ERROR_16 = EC_PREFIX + 35;   
   public static final long EC_CERTIFICATE_ERROR_17 = EC_PREFIX + 36;
   public static final long EC_CERTIFICATE_ERROR_18 = EC_PREFIX + 37;
   public static final long EC_CERTIFICATE_ERROR_19 = EC_PREFIX + 38;
   public static final long EC_CERTIFICATE_ERROR_20 = EC_PREFIX + 39;
   public static final long EC_CERTIFICATE_ERROR_21 = EC_PREFIX + 40;   
   public static final long EC_CERTIFICATE_ERROR_22 = EC_PREFIX + 41;   
   public static final long EC_CERTIFICATE_ERROR_24 = EC_PREFIX + 42;
   public static final long EC_CERTIFICATE_ERROR_33 = EC_PREFIX + 43;   
   public static final long EC_CERTIFICATE_ERROR_NOT_FNMT = EC_PREFIX + 44;
   public static final long EC_CERTIFICATE_ERROR_UNKNOW = EC_PREFIX + 45;
   public static final long EC_CERTIFICATE_PARSE_ = EC_PREFIX + 46;
   
   public static final long EC_DEPT_HAS_USERS = EC_PREFIX + 47; 
   public static final long EC_DEPT_HAS_DEPTS = EC_PREFIX + 48;
   
   // **************************************************************************

   private MvcError()
   {
   }
}
