//
// FileName: InvesDocKeys.java
//
package com.ieci.tecdoc.common.keys;



/**
 * @author lmvicente
 * @version @since @creationDate 30-mar-2004
 */

public interface IDocKeys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    public static final int IUSERUSERHDR_MAX_LOGIN_LENGTH = 32;
    public static final int IUSERUSERHDR_MAX_PASSWORD_LENGTH = 64;
    public static final int IUSERUSERHDR_LOCKED_STAT = 1;
    public static final int IUSERUSERHDR_INITIAL_NUMBADCNTS = 0;

    public static final int IUSERUSERTYPE_SICRES_TYPE = 5;
    public static final int IUSERUSERTYPE_INVESDOC_TYPE = 1;
    public static final int IUSERUSERTYPE_ADMIN_ID = 0;
    public static final int IUSERUSERTYPE_USER_TYPE_NOUSER = 0;
    public static final int IUSERUSERTYPE_USER_TYPE_OPERATOR = 1;
    public static final int IUSERUSERTYPE_USER_TYPE_BOOK_ADMIN = 2;
    public static final int IUSERUSERTYPE_USER_TYPE_ADMIN = 3;

    public static final int IUSEROBJPERM_DEPT_TYPE = 2;
    public static final int IUSEROBJPERM_USER_TYPE = 1;
	public static final int IUSEROBJPERM_GROUP_TYPE = 3;
    public static final int IUSEROBJPERM_QUERY_PERM = 32;
    public static final int IUSEROBJPERM_CREATE_PERM = 128;
    public static final int IUSEROBJPERM_MODIFY_PERM = 256;

    public static final int IUSEROBJPERM_CONSULT_PERM = 1;

    public static final int IDOCARCHDET_FLD_TYPE_LONG_STR = 2;

    public static final int IDOCARCHDET_FLD_DEF = 1;
    public static final int IDOCARCHDET_MISC = 2;
    public static final int IDOCARCHDET_VLD_DEF = 5;
    public static final String IDOCARCHDET_FLD_DEF_ASOBJECT = HibernateKeys.HIBERNATE_Idocarchdet
            + String.valueOf(IDOCARCHDET_FLD_DEF);
    public static final String IDOCARCHDET_MISC_ASOBJECT = HibernateKeys.HIBERNATE_Idocarchdet
            + String.valueOf(IDOCARCHDET_MISC);
    public static final String IDOCARCHDET_VLD_DEF_ASOBJECT = HibernateKeys.HIBERNATE_Idocarchdet
            + String.valueOf(IDOCARCHDET_VLD_DEF);

    public static final int IDOCPREFWFMT_TYPE_QRY = 1;
    public static final int IDOCPREFWFMT_TYPE_TBL = 2;
    public static final int IDOCPREFWFMT_TYPE_FRM = 3;
    public static final String IDOCPREFWFMT_TYPE_QRY_ASOBJECT = HibernateKeys.HIBERNATE_Idocprefwfmt
            + String.valueOf(IDOCPREFWFMT_TYPE_QRY);
    public static final String IDOCPREFWFMT_TYPE_TBL_ASOBJECT = HibernateKeys.HIBERNATE_Idocprefwfmt
            + String.valueOf(IDOCPREFWFMT_TYPE_TBL);
    public static final String IDOCPREFWFMT_TYPE_FRM_ASOBJECT = HibernateKeys.HIBERNATE_Idocprefwfmt
            + String.valueOf(IDOCPREFWFMT_TYPE_FRM);

    public static final int IDOCARCHDET_IDOC_VLD_TYPE_TVS = 3;
    public static final int IDOCARCHDET_IDOC_VLD_TYPE_TV = 1;

    public static final String IDOC_FLD_PREFIX = "fld";

    /**
     * Tamaño del campo "Número de transporte" cuando el IR está activado
     */
    public static final int FIELD_NUM_TRANSPORTE_SIZE_IR_ENABLED = 20;

    /**
     * Tamaño del campo "Número de transporte" cuando el IR está desactivado
     */
	public static final int FIELD_NUM_TRANSPORTE_SIZE_IR_DISABLED = 30;

	/**
     * Tamaño del campo "Comentarios" cuando el IR está activado
     */
    public static final int FIELD_COMENTARIOS_SIZE_IR_ENABLED = 50;

    /**
     * Tamaño del campo "Expone" cuando el IR está activado
     */
    public static final int FIELD_EXPONE_SIZE_IR_ENABLED = 4000;

    /**
     * Tamaño del campo "Solicita" cuando el IR está activado
     */
    public static final int FIELD_SOLICITA_SIZE_IR_ENABLED = 4000;

    //Información de los caracteres para los nombre de los ficheros
    public static final int MAX_LENGTH_NAME_FILE = 64;

    /**
	 * Tamaño máximo del campo "Direccion" para enviar por IR
	 */
	public static final int DIRECCION_INTERESADOS_IR_MAX_LENGTH = 160;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}