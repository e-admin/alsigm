package es.ieci.tecdoc.isicres.admin.core.exception;

import java.util.Locale;

import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBeansException;

public class ISicresAdminDAOException extends ISicresAdminBeansException {

	private static final long serialVersionUID = 1L;
	public static final long EXC_GENERIC_EXCEPCION	= 58670000;
	public static final long SCR_USRIDENT_NOT_FOUND = EXC_GENERIC_EXCEPCION+1;
	public static final long SCR_USRIDENT_INSERT = EXC_GENERIC_EXCEPCION+2;
	public static final long SCR_USRIDENT_DELETE = EXC_GENERIC_EXCEPCION+3;
	public static final long SCR_USRIDENT_UPDATE = EXC_GENERIC_EXCEPCION+4;
	public static final long SCR_USRPERMS_NOT_FOUND = EXC_GENERIC_EXCEPCION+5;
	public static final long SCR_USRPERMS_INSERT = EXC_GENERIC_EXCEPCION+6;
	public static final long SCR_USRPERMS_DELETE = EXC_GENERIC_EXCEPCION+7;
	public static final long SCR_USRPERMS_UPDATE = EXC_GENERIC_EXCEPCION+8;
	public static final long NO_BD = EXC_GENERIC_EXCEPCION+9;
	public static final long SCR_USRLOC_NOT_FOUND = EXC_GENERIC_EXCEPCION+10;
	public static final long SCR_USRLOC_INSERT = EXC_GENERIC_EXCEPCION+11;
	public static final long SCR_USRLOC_DELETE = EXC_GENERIC_EXCEPCION+12;
	public static final long SCR_USRLOC_UPDATE = EXC_GENERIC_EXCEPCION+13;
	public static final long IUSERUSERHDR_NOT_FOUND = EXC_GENERIC_EXCEPCION+14;
	public static final long IUSERUSERTYPE_NOT_FOUND = EXC_GENERIC_EXCEPCION+15;
	public static final long IUSERUSERTYPE_INSERT = EXC_GENERIC_EXCEPCION+16;
	public static final long IUSERUSERTYPE_DELETE = EXC_GENERIC_EXCEPCION+17;
	public static final long IUSERUSERTYPE_UPDATE = EXC_GENERIC_EXCEPCION+18;
	public static final long SCR_OFIC_NOT_FOUND = EXC_GENERIC_EXCEPCION+19;
	public static final long SCR_OFIC_INSERT = EXC_GENERIC_EXCEPCION+20;
	public static final long SCR_OFIC_DELETE = EXC_GENERIC_EXCEPCION+21;
	public static final long SCR_OFIC_UPDATE = EXC_GENERIC_EXCEPCION+22;
	public static final long SCR_DIROFIC_NOT_FOUND = EXC_GENERIC_EXCEPCION+23;
	public static final long SCR_DIROFIC_INSERT = EXC_GENERIC_EXCEPCION+24;
	public static final long SCR_DIROFIC_DELETE = EXC_GENERIC_EXCEPCION+25;
	public static final long SCR_DIROFIC_UPDATE = EXC_GENERIC_EXCEPCION+26;
	public static final long SCR_ORGS_NOT_FOUND = EXC_GENERIC_EXCEPCION+27;
	public static final long SCR_ORGS_INSERT = EXC_GENERIC_EXCEPCION+28;
	public static final long SCR_ORGS_DELETE = EXC_GENERIC_EXCEPCION+29;
	public static final long SCR_ORGS_UPDATE = EXC_GENERIC_EXCEPCION+30;
	public static final long IUSERDEPTHDR_NOT_FOUND = EXC_GENERIC_EXCEPCION+31;
	public static final long IUSERDEPTHDR_UPDATE = EXC_GENERIC_EXCEPCION+32;
	public static final long IDGENERATOR_TABLE_NOT_FOUND = EXC_GENERIC_EXCEPCION+33;
	public static final long IDOCARCHHDR_NOT_FOUND = EXC_GENERIC_EXCEPCION+34;
	public static final long IUSEROBJPERMS_NOT_FOUND = EXC_GENERIC_EXCEPCION+35;
	public static final long IUSEROBJPERMS_INSERT = EXC_GENERIC_EXCEPCION+36;
	public static final long IUSEROBJPERMS_DELETE = EXC_GENERIC_EXCEPCION+37;
	public static final long IUSEROBJPERMS_UPDATE = EXC_GENERIC_EXCEPCION+38;
	public static final long SCR_BOOKOFIC_NOT_FOUND = EXC_GENERIC_EXCEPCION+39;
	public static final long SCR_BOOKOFIC_INSERT = EXC_GENERIC_EXCEPCION+40;
	public static final long SCR_BOOKOFIC_DELETE = EXC_GENERIC_EXCEPCION+41;
	public static final long SCR_BOOKOFIC_UPDATE = EXC_GENERIC_EXCEPCION+42;
	public static final long SCR_DISTLIST_NOT_FOUND = EXC_GENERIC_EXCEPCION+43;
	public static final long SCR_DISTLIST_INSERT = EXC_GENERIC_EXCEPCION+44;
	public static final long SCR_DISTLIST_DELETE = EXC_GENERIC_EXCEPCION+45;
	public static final long SCR_DISTLIST_UPDATE = EXC_GENERIC_EXCEPCION+46;
	public static final long SCR_DIRORGS_NOT_FOUND = EXC_GENERIC_EXCEPCION+47;
	public static final long SCR_DIRORGS_INSERT = EXC_GENERIC_EXCEPCION+48;
	public static final long SCR_DIRORGS_DELETE = EXC_GENERIC_EXCEPCION+49;
	public static final long SCR_DIRORGS_UPDATE = EXC_GENERIC_EXCEPCION+50;
	public static final long IVOLARCHLIST_NOT_FOUND = EXC_GENERIC_EXCEPCION+55;
	public static final long IVOLARCHLIST_INSERT = EXC_GENERIC_EXCEPCION+56;
	public static final long IVOLARCHLIST_DELETE = EXC_GENERIC_EXCEPCION+57;
	public static final long IVOLARCHLIST_UPDATE = EXC_GENERIC_EXCEPCION+58;
	public static final long SCR_REGSTATE_NOT_FOUND = EXC_GENERIC_EXCEPCION+59;
	public static final long SCR_REGSTATE_INSERT = EXC_GENERIC_EXCEPCION+60;
	public static final long SCR_REGSTATE_DELETE = EXC_GENERIC_EXCEPCION+61;
	public static final long SCR_REGSTATE_UPDATE = EXC_GENERIC_EXCEPCION+62;
	public static final long SCR_CNTCENTRAL_NOT_FOUND = EXC_GENERIC_EXCEPCION+63;
	public static final long SCR_CNTCENTRAL_INSERT = EXC_GENERIC_EXCEPCION+64;
	public static final long SCR_CNTCENTRAL_DELETE = EXC_GENERIC_EXCEPCION+65;
	public static final long SCR_CNTCENTRAL_UPDATE = EXC_GENERIC_EXCEPCION+66;
	public static final long SCR_CNTOFICINA_NOT_FOUND = EXC_GENERIC_EXCEPCION+67;
	public static final long SCR_CNTOFICINA_INSERT = EXC_GENERIC_EXCEPCION+68;
	public static final long SCR_CNTOFICINA_DELETE = EXC_GENERIC_EXCEPCION+69;
	public static final long SCR_CNTOFICINA_UPDATE = EXC_GENERIC_EXCEPCION+70;
	public static final long SCR_USERFILTER_NOT_FOUND = EXC_GENERIC_EXCEPCION+71;
	public static final long SCR_USERFILTER_INSERT = EXC_GENERIC_EXCEPCION+72;
	public static final long SCR_USERFILTER_DELETE = EXC_GENERIC_EXCEPCION+73;
	public static final long SCR_USERFILTER_UPDATE = EXC_GENERIC_EXCEPCION+74;
	public static final long SCR_TYPEADMIN_NOT_FOUND = EXC_GENERIC_EXCEPCION+75;
	public static final long SCR_TYPEADMIN_INSERT = EXC_GENERIC_EXCEPCION+76;
	public static final long SCR_TYPEADMIN_DELETE = EXC_GENERIC_EXCEPCION+77;
	public static final long SCR_TYPEADMIN_UPDATE = EXC_GENERIC_EXCEPCION+78;
	public static final long SCR_USEROFIC_NOT_FOUND = EXC_GENERIC_EXCEPCION+79;
	public static final long SCR_USEROFIC_INSERT = EXC_GENERIC_EXCEPCION+80;
	public static final long SCR_USEROFIC_DELETE = EXC_GENERIC_EXCEPCION+81;
	public static final long SCR_USEROFIC_UPDATE = EXC_GENERIC_EXCEPCION+82;

	//scr_userconfig
	public static final long SCR_USERCONFIG_NOT_FOUND = EXC_GENERIC_EXCEPCION+83;
	public static final long SCR_USERCONFIG_INSERT = EXC_GENERIC_EXCEPCION+84;
	public static final long SCR_USERCONFIG_DELETE = EXC_GENERIC_EXCEPCION+85;
	public static final long SCR_USERCONFIG_UPDATE = EXC_GENERIC_EXCEPCION+86;

	//scr_tt
	public static final long SCR_TT_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+87;
	public static final long SCR_TT_INSERT 	 	= EXC_GENERIC_EXCEPCION+88;
	public static final long SCR_TT_DELETE 		= EXC_GENERIC_EXCEPCION+89;
	public static final long SCR_TT_UPDATE 		= EXC_GENERIC_EXCEPCION+90;
	public static final long SCR_TT_UNIQUE_KEY_ERROR = EXC_GENERIC_EXCEPCION+116;

	//scr_ca
	public static final long SCR_CA_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+91;
	public static final long SCR_CA_INSERT 	 	= EXC_GENERIC_EXCEPCION+92;
	public static final long SCR_CA_DELETE 		= EXC_GENERIC_EXCEPCION+93;
	public static final long SCR_CA_UPDATE 		= EXC_GENERIC_EXCEPCION+94;

	//scr_caofic
	public static final long SCR_CAOFIC_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+95;
	public static final long SCR_CAOFIC_INSERT 	 	= EXC_GENERIC_EXCEPCION+96;
	public static final long SCR_CAOFIC_DELETE 		= EXC_GENERIC_EXCEPCION+97;
	public static final long SCR_CAOFIC_UPDATE 		= EXC_GENERIC_EXCEPCION+98;

	//scr_caaux
	public static final long SCR_CAAUX_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+99;
	public static final long SCR_CAAUX_INSERT 	 	= EXC_GENERIC_EXCEPCION+100;
	public static final long SCR_CAAUX_DELETE 		= EXC_GENERIC_EXCEPCION+101;
	public static final long SCR_CAAUX_UPDATE 		= EXC_GENERIC_EXCEPCION+102;

	//scr_cadocs
	public static final long SCR_CADOCS_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+103;
	public static final long SCR_CADOCS_INSERT 	 	= EXC_GENERIC_EXCEPCION+104;
	public static final long SCR_CADOCS_DELETE 		= EXC_GENERIC_EXCEPCION+105;
	public static final long SCR_CADOCS_UPDATE 		= EXC_GENERIC_EXCEPCION+106;

	public static final long SCR_CA_DELETE_FOREIGN_KEY_ERROR = EXC_GENERIC_EXCEPCION+107;
	public static final long SCR_CA_UNIQUE_KEY_ERROR = EXC_GENERIC_EXCEPCION+108;




	//scr_reports
	public static final long SCR_REPORT_UNIQUE_KEY_ERROR = EXC_GENERIC_EXCEPCION+120;
	public static final long SCR_REPORT_DELETE_FOREIGN_KEY_ERROR = EXC_GENERIC_EXCEPCION+121;
	public static final long SCR_REPORT_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+122;
	public static final long SCR_REPORT_INSERT 	 	= EXC_GENERIC_EXCEPCION+123;
	public static final long SCR_REPORT_DELETE 		= EXC_GENERIC_EXCEPCION+124;
	public static final long SCR_REPORT_UPDATE 		= EXC_GENERIC_EXCEPCION+125;

		// scr_reportOfic
		public static final long SCR_REPORTOFIC_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+126;
		public static final long SCR_REPORTOFIC_INSERT 	 	= EXC_GENERIC_EXCEPCION+127;
		public static final long SCR_REPORTOFIC_DELETE 		= EXC_GENERIC_EXCEPCION+128;
		public static final long SCR_REPORTOFIC_UPDATE 		= EXC_GENERIC_EXCEPCION+129;

		// scr_reportArch
		public static final long SCR_REPORTARCH_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+130;
		public static final long SCR_REPORTARCH_INSERT 	 	= EXC_GENERIC_EXCEPCION+131;
		public static final long SCR_REPORTARCH_DELETE 		= EXC_GENERIC_EXCEPCION+132;
		public static final long SCR_REPORTARCH_UPDATE 		= EXC_GENERIC_EXCEPCION+132;

		// scr_reportPerf
		public static final long SCR_REPORTPERF_NOT_FOUND 	= EXC_GENERIC_EXCEPCION+133;
		public static final long SCR_REPORTPERF_INSERT 	 	= EXC_GENERIC_EXCEPCION+134;
		public static final long SCR_REPORTPERF_DELETE 		= EXC_GENERIC_EXCEPCION+135;
		public static final long SCR_REPORTPERF_UPDATE 		= EXC_GENERIC_EXCEPCION+136;


	//iuserdepthdr
	public static final long IUSERDEPTHDR_INSERT = EXC_GENERIC_EXCEPCION+109;

	public static final long IUSERLDAPUSERHDR_INSERT = EXC_GENERIC_EXCEPCION+110;
	public static final long IUSERLDAPUSERHDR_NOT_FOUND = EXC_GENERIC_EXCEPCION+111;

	public static final long SCR_REPORT_PERF_LOAD = EXC_GENERIC_EXCEPCION+137;

	private static String RESOURCE_FILE = "es.ieci.tecdoc.isicres.admin.core.exception.RPAdminDAOExceptionMessages";


	public ISicresAdminDAOException(long errorCode) {
		this(errorCode, null);
	}

	public ISicresAdminDAOException(String message) {
		this(message, null);
	}

	public ISicresAdminDAOException(Throwable cause) {
		this(0, cause);
	}

	public ISicresAdminDAOException(long errorCode, String message, Throwable cause){
		super(message,cause);
		this.errorCode = errorCode;
		errorMessage = loadMessage(Locale.getDefault());
	}

   public ISicresAdminDAOException(long errorCode, Throwable cause) {
	   super(cause);
	   this.errorCode = errorCode;
	   errorMessage = loadMessage(Locale.getDefault());
   }

   public ISicresAdminDAOException(String message, Throwable cause) {
	   super(message, cause);
	   errorMessage = message;
   }

   /**
    * Devuelve el nombre del archivo de recursos que contiene los mensajes para
    * esta excepción.
    *
    * @return El nombre del archivo de recursos mencionado.
    */

   public String getMessagesFile() {

      return RESOURCE_FILE;
   }

}
