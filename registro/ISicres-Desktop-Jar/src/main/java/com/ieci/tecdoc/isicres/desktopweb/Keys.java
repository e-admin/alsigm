package com.ieci.tecdoc.isicres.desktopweb;

/**
 * @author LMVICENTE
 * @creationDate 30-abr-2004 9:17:23
 * @version
 * @since
 */
public interface Keys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	public static final String KEY_BUILD_TYPE_INVESICRES = "";

	public static final int X_AVERAGE = 350;
	public static final int Y_AVERAGE = 13;

	public static final int AMPL_TOP = 0;

	public static final int C_X = (X_AVERAGE / 27) / 2;
	public static final int C_Y = Y_AVERAGE;

	public static final String ORACLE = "ORACLE";
	public static final String SQLSERVER = "SQLSERVER";
	public static final String POSTGRESQL = "POSTGRESQL";
	public static final String DB2 = "DB2";

	public static final String PUNTO_COMA = ";";
	public static final String ALMOHADILLA = "#";

	public static final String GUION = "-";
	public static final String GUION_BAJO = "_";
	public static final String COMILLA = "'";
	public static final String CONTENT = " LIKE ";
	public static final String PER_CENT = "%";
	public static final String COMA = ",";
	public static final String PAR_IZQ = "(";
    public static final String PAR_DER = ")";

    public static final String TO_CHAR = "to_char(";
    public static final String TO_CONVERT_DATE = "convert(datetime";
    public static final String TO_CONVERT_DATE_FORMAT = "0";
    public static final String TO_DATE = "to_date(";
    public static final String TO_TIMESTAMP = "to_timestamp(";

    public static final String TIME_INITIAL = " 00:00:00";
    public static final String TIME_FINAL = " 23:59:59";

    public static final String DATE_FORMAT = "DD-MM-YYYY HH24:MI:SS";
    public static final String DATE_FORMAT_DB2 = "YYYY-MM-DD HH24:MI:SS";

    public static final String MONTH = "MONTH(";
    public static final String YEAR = "YEAR(";
	public static final String MONTH_FORMAT = "MM";
    public static final String YEAR_FORMAT = "YYYY";


	public static final String ACTIVATE_DTR = "<html><head>"
			+ "<script language=javascript>"
			+ "top.Main.Distr.EnabledToolbar();" + "</script>" + "</head>"
			+ "<body style=\"cursor:default\"></body></html>";
	public static final String SEARCH_DTR = "<html><head>"
			+ "<script language=javascript>" + "top.close();" + "</script>"
			+ "</head>" + "<body style=\"cursor:default\"></body></html>";

	public static final String ACTIVATE_TREE = "<script language=javascript>top.g_TreeFunc=true;</script>";
	public static final String ACTIVATE_SEVERAL = "<script language=javascript>top.g_TreeFunc=true;top.Main.Workspace.EnabledTool();</script>";
	public static final String ACTIVATE_SEVERAL_2 = "<script language=javascript>try{top.g_FolderId = -1;top.Main.Table.ToolBarTbl.habilitar();window.open(top.g_URL + \"/tablebar.htm\", \"TableBar\",\"location=no\",true);}catch(excep){}</script>";
	public static final String ACTIVATE_CLOSE_VALIDATION = "<script language=\"javascript\">try{parent.VldTbl.document.getElementById('Close').disabled = false;}catch(e){}</script>";
	public static final String ACTIVATE_SEVERAL_ADVSEARCH = "<script language=javascript>reloadTypeSearchAvanced();</script>";

	public static final String ACTIVATE_OPEN_FOLDER = "<script language=javascript>try{"
			+ "top.Main.Folder.FolderBar.bLoadForm = true;"
			+ "window.open(top.g_URL + \"/fldbarUpdate.htm\", \"FolderBar\",\"location=no\",true);top.g_Page = 0;top.g_OpcAval=true;"
			+ "if(top.g_FolderView){window.open(top.g_URL + \"/tb_folder.htm\", \"ToolBarFrm\",\"location=no\",true);top.Main.Folder.ToolBarFrm.ToolBarEnabled();top.g_ActivateTree=true;top.g_bIsNewFolder=false;}"
			+ "else{window.open(top.g_URL + \"/tb_form.htm\", \"ToolBarFrm\",\"location=no\",true);}"
			+ "top.ShowForm();top.Main.Folder.ToolBarFrm.habilitar();top.g_ActivateTree=true;top.g_bIsNewFolder=false;}catch(excep){}</script>";
	public static final String ACTIVATE_NEW_FOLDER = "<script language=javascript>try{"
			+ "top.Main.Folder.FolderBar.bLoadForm = true;"
			+ "window.open(top.g_URL + \"/fldbarUpdate.htm\", \"FolderBar\",\"location=no\",true);top.g_Page = 0;top.g_sinPulsar=true;top.g_OpcAval=true;"
			+ "if(top.g_FolderView){window.open(top.g_URL + \"/tb_folder.htm\", \"ToolBarFrm\",\"location=no\",true);top.Main.Folder.ToolBarFrm.ToolBarEnabled();top.g_ActivateTree=true;top.g_bIsBucle=false; top.g_LoadForm=false;}"
			+ "else{window.open(top.g_URL + \"/tb_form.htm\", \"ToolBarFrm\",\"location=no\",true);}"
			+ "top.ShowForm();top.Main.Folder.ToolBarFrm.habilitar();top.g_ActivateTree=true;top.g_bIsBucle =false;top.g_LoadForm = false;}catch(excep){}</script>";

	public static final String ACTIVATE_FRM_NAVIGATE_FOLDER = "<script language=javascript>"
			+ "top.g_ActivateTree=true;"
			+ "top.g_OpcAval=true;"
			+ "top.Main.Folder.ToolBarFrm.ToolBarEnabled();" + "</script>";

	public static final String ACTIVATE_FRM_DATA = "<script language=javascript>try{top.g_ActivateTree=true;top.ShowForm();if(top.g_FolderView){top.Main.Folder.ToolBarFrm.ToolBarEnabled();}else{top.Main.Folder.ToolBarFrm.habilitar();}}catch(excep){}</script>";
	public static final String ACTIVATE_TREE_1 = "<script language=javascript>top.ActivateFrmtMenu();if (!top.g_FolderView){top.g_OpcAval=true;top.Main.Folder.ToolBarFrm.habilitar();}</script>";
	public static final String ACTIVATE_TREE_2 = "<script language=javascript>top.g_ActivateTree=true;if (!top.g_FolderView){top.g_OpcAval=true;top.Main.Folder.ToolBarFrm.habilitar();}else{top.Main.Folder.ToolBarFrm.ToolBarEnabled();}</script>";
	public static final String ACTIVATE_SAVEPERSON = "<script language=\"javascript\">window.returnValue=\"\";</script>";

	public static final String IDOC_BUTTON_OK = "IDOC_BUTTON_OK";
	public static final String IDOC_BUTTON_CANCEL = "IDOC_BUTTON_CANCEL";

	public static final Integer SEARCH_WITH_FILTER = new Integer(0);
	public static final Integer SEARCH_LAST_FOR_USER = new Integer(1);

	public static final Integer J_OPENFOLDER_TYPE_XMLBOOKTREE_FORM_BUTTON = new Integer(
			0);
	public static final Integer J_OPENFOLDER_TYPE_XMLBOOK = new Integer(1);
	public static final Integer J_OPENFOLDER_TYPE_XMLBOOKTREE_UDPATE_BUTTON = new Integer(
			2);

	public static final String J_OPENED_FOLDER = "JOpenedFolder";
	public static final String J_OPENFOLDER_TYPE = "JOpenFolderType";
	public static final String J_OPENFOLDER_FORM = "JOpenFolderForm";
	public static final String J_USECASECONF = "JUseCaseConf";
	public static final String J_USERNAME = "JUserName";
	public static final String J_IDIOMA = "JIdioma";
	public static final String J_NUM_IDIOMA = "JNumIdioma";
	public static final String J_BOOK = "JBook";
	public static final String J_REPORTOPTION = "JReportOption";
	public static final String J_SIZE_REPORT = "JSizeReport";
	public static final String J_REGISTER = "JRegister";
	public static final String J_ROW = "JRow";
	public static final String J_STRCARPETA = "JStrCarpeta";
	public static final String J_FILESSCAN = "JFilesScan";
	public static final String J_PERSISTFIELDS = "JPersistFields";

	public static final String J_LOCALE = "JLocale";

	public static final String I18N_DATE_LONGFORMAT = "date.longFormat";
	public static final String I18N_DATE_SHORTFORMAT = "date.shortFormat";

	//Formato de las fechas para la bandeja de distribución
	public static final String DATE_FORMAT_VIEW_DISTRIBUTION = "date.view.distribution";

	public static final String I18N_ISICRESSRV_ERR_CREATING_LESTREE_OBJ = "exception.ISICRESSRV_ERR_CREATING_LESTREE_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_SESSION_OBJ = "exception.ISICRESSRV_ERR_CREATING_SESSION_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ = "exception.ISICRESSRV_ERR_CREATING_FDRQRY_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_REPORT_OBJ = "exception.ISICRESSRV_ERR_CREATING_REPORT_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_VLDTBL_OBJ = "exception.ISICRESSRV_ERR_CREATING_VLDTBL_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_DTR_OBJ = "exception.ISICRESSRV_ERR_CREATING_DTR_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_DTR_DIALOG = "exception.ISICRESSRV_ERR_CREATING_DTR_DIALOG";
	public static final String I18N_ISICRESSRV_ERR_CREATING_ORICDOCS_OBJ = "exception.ISICRESSRV_ERR_CREATING_ORICDOCS_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_ASOCSREG_OBJ = "exception.ISICRESSRV_ERR_CREATING_ASOCSREG_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_SAVEORICDOCS_OBJ = "exception.ISICRESSRV_ERR_CREATING_SAVEORICDOCS_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_SAVEPERSON_OBJ = "exception.ISICRESSRV_ERR_CREATING_SAVEPERSON_OBJ";
	public static final String I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT = "exception.ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT";
	public static final String I18N_ISICRESSRV_ERR_CREATING_POBLACION_OBJ = "exception.ISICRESSRV_ERR_CREATING_POBLACION_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_BUSCINTER_OBJ = "exception.ISICRESSRV_ERR_CREATING_BUSCINTER_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_CITIES_OBJ = "exception.ISICRESSRV_ERR_CREATING_CITIES_OBJ";
	public static final String ISICRESSRV_ERR_CREATING_TYPE_DOCS_OBJ = "exception.ISICRESSRV_ERR_CREATING_TYPE_DOCS_OBJ";
	public static final String ISICRESSRV_ERR_CREATING_TYPE_ADDRESSES_OBJ = "exception.ISICRESSRV_ERR_CREATING_TYPE_ADDRESSES_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ = "exception.ISICRESSRV_ERR_CREATING_DIRECTION_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_FRMPERSIST_OBJ = "exception.ISICRESSRV_ERR_CREATING_FRMPERSIST_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_UPLOAD_OBJ = "exception.ISICRESSRV_ERR_CREATING_UPLOAD_OBJ";
	public static final String I18N_ISICRESSRV_ERR_COMPUL_UPLOAD_OBJ = "exception.ISICRESSRV_ERR_COMPUL_UPLOAD_OBJ";
	public static final String I18N_ISICRESSRV_ERR_CREATING_FDRSAVE_OBJ = "exception.ISICRESSRV_ERR_CREATING_FDRSAVE_OBJ";
	public static final String I18N_ISICRESSRV_ERR_DOWNLOAD_OBJ = "exception.ISICRESSRV_ERR_DOWNLOAD_OBJ";
	public static final String I18N_ISICRESSRV_ERR_DELETE_OBJ = "exception.ISICRESSRV_ERR_DELETE_OBJ";
	public static final String I18N_ISICRESSRV_ERR_UPDATEFOLDERS_OBJ = "exception.ISICRESSRV_ERR_UPDATEFOLDERS_OBJ";
	public static final String I18N_ISICRESSRV_ERR_UPDATEFIELDS_OBJ = "exception.ISICRESSRV_ERR_UPDATEFIELDS_OBJ";
	public static final String I18N_ISICRESSRV_ERR_SETPERSISTFIELDS_OBJ = "exception.ISICRESSRV_ERR_SETPERSISTFIELDS_OBJ";
	public static final String I18N_ISICRESSRV_ERR_DOWNLOAD_FILE = "exception.I18N_ISICRESSRV_ERR_DOWNLOAD_FILE";
	public static final String I18N_ISICRESSRV_ERR_LAUNCH_EVENT_OBJ = "exception.ISICRESSRV_ERR_LAUNCH_EVENT_OBJ";

	public static final String I18N_EXCEPTION_REMOTEEXCEPTION = "exception.remoteException";
	public static final String I18N_EXCEPTION_CREATEEXCEPTION = "exception.createException";
	public static final String I18N_EXCEPTION_FEATURE_NOT_AVAILABLE = "exception.featureNotAvailable";
	public static final String I18N_EXCEPTION_NO_DATA_TO_REPORT = "exception.noDateToReport";
	public static final String I18N_EXCEPTION_VALIDATIONEXCEPTION = "exception.validationException";
	public static final String I18N_EXCEPTION_IMPOSSIBLETOLOADVALUES = "exception.impossibleToLoadValues";
	public static final String I18N_EXCEPTION_EXCEPTION_JSP = "exception.exception.jsp";
	public static final String I18N_EXCEPTION_ERROR_JSP = "exception.error.jsp";
	public static final String I18N_EXCEPTION_BOOK_HAS_NO_FOLDERS = "exception.bookHasNoFolders";
	public static final String I18N_EXCEPTION_TOOMANYROWS = "exception.tooManyRows";
	public static final String I18N_EXCEPTION_MAXUPLOADFILESIZE = "exception.maxuploadfilesize";
	public static final String I18N_EXCEPTION_MAXREPORTREGISTERS = "exception.maxreportregisters";
	public static final String I18N_EXCEPTION_CANNOT_MODIFY_PERSON_INFO = "exception.cannot.modify.person.info";

	public static final String I18N_BOOKUSECASE_ROOTNAME = "bookusecase.rootname";
	public static final String I18N_BOOKUSECASE_NODE_INBOOK_NAME = "bookusecase.node.inBook.name";
	public static final String I18N_BOOKUSECASE_NODE_OUTBOOK_NAME = "bookusecase.node.outBook.name";
	public static final String I18N_BOOKUSECASE_IDOC_BUTTON_OK = "bookusecase.button.IDOC_BUTTON_OK";
	public static final String I18N_BOOKUSECASE_IDOC_BUTTON_CANCEL = "bookusecase.button.IDOC_BUTTON_CANCEL";

	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL1 = "bookusecase.distributionhistory.headminuta.col1";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL2 = "bookusecase.distributionhistory.headminuta.col2";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL3 = "bookusecase.distributionhistory.headminuta.col3";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL4 = "bookusecase.distributionhistory.headminuta.col4";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL5 = "bookusecase.distributionhistory.headminuta.col5";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL6 = "bookusecase.distributionhistory.headminuta.col6";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL7 = "bookusecase.distributionhistory.headminuta.col7";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL8 = "bookusecase.distributionhistory.headminuta.col8";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL9 = "bookusecase.distributionhistory.headminuta.col9";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL10 = "bookusecase.distributionhistory.headminuta.col10";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL11 = "bookusecase.distributionhistory.headminuta.col11";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL12 = "bookusecase.distributionhistory.headminuta.col12";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL13 = "bookusecase.distributionhistory.headminuta.col13";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL14 = "bookusecase.distributionhistory.headminuta.col14";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL15 = "bookusecase.distributionhistory.headminuta.col15";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL16 = "bookusecase.distributionhistory.headminuta.col16";

	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL1 = "bookusecase.distributionhistory.bodyminuta.col1";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL2 = "bookusecase.distributionhistory.bodyminuta.col2";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL3 = "bookusecase.distributionhistory.bodyminuta.col3";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL4 = "bookusecase.distributionhistory.bodyminuta.col4";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL5 = "bookusecase.distributionhistory.bodyminuta.col5";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL6 = "bookusecase.distributionhistory.bodyminuta.col6";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL7 = "bookusecase.distributionhistory.bodyminuta.col7";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL8 = "bookusecase.distributionhistory.bodyminuta.col8";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL9 = "bookusecase.distributionhistory.bodyminuta.col9";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL10 = "bookusecase.distributionhistory.bodyminuta.col10";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL11 = "bookusecase.distributionhistory.bodyminuta.col11";
	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL12 = "bookusecase.distributionhistory.bodyminuta.col12";

	public static final String I18N_BOOKUSECASE_UPDATEHISTORY_HEADMINUTA_COL1 = "bookusecase.updatehistory.headminuta.col1";
	public static final String I18N_BOOKUSECASE_UPDATEHISTORY_HEADMINUTA_COL2 = "bookusecase.updatehistory.headminuta.col2";
	public static final String I18N_BOOKUSECASE_UPDATEHISTORY_HEADMINUTA_COL3 = "bookusecase.updatehistory.headminuta.col3";
	public static final String I18N_BOOKUSECASE_UPDATEHISTORY_BODYMINUTA_COL4 = "bookusecase.updatehistory.bodyminuta.col4";
	public static final String I18N_BOOKUSECASE_UPDATEHISTORY_BODYMINUTA_COL5 = "bookusecase.updatehistory.bodyminuta.col5";

	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_NOBODYMINUTA = "bookusecase.distributionhistory.nobodyminuta";
	public static final String I18N_ISICRESSRV_ERR_CLOSING_REGS = "exception.ISICRESSRV_ERR_CLOSING_REGS";
	public static final String I18N_ISICRESSRV_ERR_OPENING_REGS = "exception.ISICRESSRV_ERR_OPENING_REGS";
	public static final String I18N_ISICRESSRV_ERR_CANNOT_OPEN_CLOSE_REGS ="exception.ISICRESSRV_ERR.CANNOT_OPEN_CLOSE_REGS";

	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL1 = "bookusecase.distributionhistory.width.col1";
	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL2 = "bookusecase.distributionhistory.width.col2";
	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL3 = "bookusecase.distributionhistory.width.col3";
	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL4 = "bookusecase.distributionhistory.width.col4";
	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL5 = "bookusecase.distributionhistory.width.col5";
	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL6 = "bookusecase.distributionhistory.width.col6";
	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL7 = "bookusecase.distributionhistory.width.col7";
	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL8 = "bookusecase.distributionhistory.width.col8";
	public static final String BOOKUSECASE_DISTRIBUTIONHISTORY_WIDTH_COL9 = "bookusecase.distributionhistory.width.col9";

	public static final String BOOKUSECASE_DISTRIBUTIONSEARCH_DIST_LABEL_ROW = "bookusecase.distributionsearch.dist.label.row.";
	public static final String BOOKUSECASE_DISTRIBUTIONSEARCH_REG_LABEL_ROW = "bookusecase.distributionsearch.reg.label.row.";
	public static final String BOOKUSECASE_DISTRIBUTIONSEARCH_TITLE = "bookusecase.distributionsearch.title";
	public static final String BOOKUSECASE_DISTRIBUTIONSEARCH_BY_DISTRIBUTION = "bookusecase.distributionsearch.by.distribution";
	public static final String BOOKUSECASE_DISTRIBUTIONSEARCH_BY_FLD = "bookusecase.distributionsearch.by.fld";

	public static final String BOOKUSECASE_ASOCREGSSEARCH_TITLE = "bookusecase.asocregssearch.title";
	public static final String BOOKUSECASE_ASOCREGSSEARCH_LABEL_ROW = "bookusecase.asocregssearch.label.row.";


	public static final String XML_COLDATA_TEXT = "COLDATA";

	public static final String I18N_BOOKUSE_ORIGINDOCS_COL1 = "bookusecase.origindocs.cols.col1";
	public static final String I18N_BOOKUSE_ORIGINDOCS_COL2 = "bookusecase.origindocs.cols.col2";

	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_TRAMITATION = "bookusecase.distributionhistory.minuta.head.tramitation";

	public static final String I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_MINUTA_DIST_STATE = "bookusecase.distributionhistory.minuta.dist.state.";

	public static final String I18N_BOOK_IMCOMPLET_REGISTERS = "book.incomplet.registers";

	public static final String I18N_VALIDATIONUSECASE_VALIDATIONCODE_ERROR = "validationlistusecase.validationcode.error";
	public static final String I18N_VALIDATIONUSECASE_REGISTERBOOK = "validationlistusecase.registerbook";
	public static final String I18N_VALIDATIONUSECASE_REGISTERTYPE = "validationlistusecase.registertype";
	public static final String I18N_VALIDATIONUSECASE_TYPEOFFIC = "validationlistusecase.typeoffic";
	public static final String I18N_VALIDATIONUSECASE_TYPEADM = "validationlistusecase.typeadm";
	public static final String I18N_VALIDATIONUSECASE_TYPESUBJECT = "validationlistusecase.typesubject";
	public static final String I18N_VALIDATIONUSECASE_TYPEPROC = "validationlistusecase.typeproc";
	public static final String I18N_VALIDATIONUSECASE_TYPETRANSPORT = "validationlistusecase.typetransport";
	public static final String I18N_VALIDATIONUSECASE_TYPEENTREG = "validationlistusecase.typeentreg";
	public static final String I18N_VALIDATIONUSECASE_TYPEDISTRIBUTION = "validationlistusecase.typedistribution";
	public static final String I18N_VALIDATIONUSECASE_PERSONDATA = "validationlistusecase.persondata";
	public static final String I18N_VALIDATIONUSECASE_CITIES = "validationlistusecase.cities";
	public static final String I18N_VALIDATIONUSECASE_DOM = "validationlistusecase.dom";
	public static final String I18N_VALIDATIONUSECASE_PROV = "validationlistusecase.prov";
	public static final String I18N_VALIDATIONUSECASE_POPULATION = "validationlistusecase.population";
	public static final String I18N_VALIDATIONUSECASE_DOCUMENT = "validationlistusecase.document";
	public static final String I18N_VALIDATIONUSECASE_NAME = "validationlistusecase.name";
	public static final String I18N_VALIDATIONUSECASE_DIRECTION = "validationlistusecase.direction";
	public static final String I18N_VALIDATIONUSECASE_CODPOSTAL = "validationlistusecase.codpostal";
	public static final String I18N_VALIDATIONUSECASE_CITY = "validationlistusecase.city";
	public static final String I18N_VALIDATIONUSECASE_PROVI = "validationlistusecase.provi";
	public static final String I18N_VALIDATIONUSECASE_SEG_STATE = "validationlistusecase.seg.state";

	public static final String I18N_VALIDATION_QUERY_FIELDSINFO = "validationlist.query.fieldsinfo.";
	public static final String I18N_VALIDATION_ERROR_BUSCINTER = "validationlist.error.buscinter";

	public static final String I18N_PDF_WATER_MARK_FONT = "pdf.water.mark.font";
	public static final String I18N_PDF_WATER_MARK_ENCODING = "pdf.water.mark.encoding";
	public static final String I18N_PDF_WATER_MARK_SIZE = "pdf.water.mark.size";
	public static final String I18N_PDF_WATER_MARK_POSITION_X = "pdf.water.mark.position.x";
	public static final String I18N_PDF_WATER_MARK_POSITION_Y = "pdf.water.mark.position.y";
	public static final String I18N_PDF_WATER_BAND_VH = "pdf.water.mark.band.vh";
	public static final String I18N_PDF_WATER_BAND_SIZE = "pdf.water.mark.band.size";
	public static final String I18N_LABEL_COMPUL_DOCUMENTS = "label.compul.documents";

	public static final String I18N_QUERY_EQUAL_TEXT_VALUE = "query.equal.text.value";
	public static final String I18N_QUERY_NOT_EQUAL_TEXT_VALUE = "query.not.equal.text.value";
	public static final String I18N_QUERY_GREATER_TEXT_VALUE = "query.greater.text.value";
	public static final String I18N_QUERY_GREATER_EQUAL_TEXT_VALUE = "query.greater.equal.text.value";
	public static final String I18N_QUERY_LESSER_TEXT_VALUE = "query.lesser.text.value";
	public static final String I18N_QUERY_LESSER_EQUAL_TEXT_VALUE = "query.lesser.equal.text.value";
	public static final String I18N_QUERY_BETWEEN_TEXT_VALUE = "query.between.text.value";
	public static final String I18N_QUERY_LIKE_TEXT_VALUE = "query.like.text.value";
	public static final String I18N_QUERY_OR_TEXT_VALUE = "query.or.text.value";
	public static final String I18N_QUERY_ABC_TEXT_VALUE = "query.abc.text.value";
	public static final String I18N_QUERY_IN_AND_TEXT_VALUE = "query.in.and.text.value";
	public static final String I18N_QUERY_IN_OR_TEXT_VALUE = "query.in.or.text.value";
	public static final String I18N_QUERY_BEGIN_BY_VALUE = "query.begin.by.value";
	public static final String I18N_QUERY_DEPEND_OF_VALUE = "query.depend.of.value";
	public static final String I18N_QUERY_END_WITH_VALUE = "query.end.with.value";
	public static final String I18N_QUERY_AFTER_TO_VALUE = "query.after.to.value";
	public static final String I18N_QUERY_BEFORE_TO_VALUE = "query.before.to.value";
	public static final String I18N_QUERY_ON_MONTH_VALUE = "query.on.month.value";
	public static final String I18N_QUERY_ON_YEAR_VALUE = "query.on.year.value";

	public static final String I18N_DISTRIBUTION_NEW_FOLDER_FOR_USER = "distribution.new.folder.for.user";
	public static final String I18N_DISTRIBUTION_NEW_FOLDERS_FOR_USER = "distribution.new.folders.for.user";
	public static final String I18N_DISTRIBUTION_NEW_FOLDER_REJECTED_FOR_USER = "distribution.new.folder.rejected.for.user";
	public static final String I18N_DISTRIBUTION_NEW_FOLDERS_REJECTED_FOR_USER = "distribution.new.folders.rejected.for.user";

	public static final String I18N_BOOK_COMPUL_SATISFACTORILY_FILE = "book.COMPUL_SATISFACTORILY_FILE";

	// Configuración de usuario
	public static final String I18N_USERCONFIG_PARAMETER = "userconf.parameter.";
	// distribución externa
	public static final String I18N_DTREX_ACCEPT_SATISFY = "distributionex.accept.safisfy";
	public static final String I18N_DTREX_REJECT_SATISFY = "distributionex.reject.safisfy";
	public static final String I18N_DTREX_CREATE_SATISFY = "distributionex.create.safisfy";

	public static final String I18N_EXCEPTION_NO_REGS_TO_CLOSE = "exception.noRegsToClose";

	//INTERCAMBIO REGISTRAL
	public static final String I18N_ISICRESIR_ACCEPT_OK = "intercambioRegistral.aceptadosOk";
	public static final String I18N_ISICRESIR_ACCEPT_ERROR = "intercambioRegistral.aceptadosError";
	public static final String I18N_ISICRESIR_CANCEL_OK = "intercambioRegistral.canceladosOk";
	public static final String I18N_ISICRESIR_CANCEL_ERROR = "intercambioRegistral.canceladosError";
	public static final String I18N_ISICRESIR_UNDO_CANCEL_OK = "intercambioRegistral.desanuladosOk";
	public static final String I18N_ISICRESIR_UNDO_CANCEL_ERROR = "intercambioRegistral.desanuladosError";
	public static final String I18N_ISICRESIR_REJECT_ERROR = "intercambioRegistral.rechazadosError";
	public static final String I18N_ISICRESIR_REJECT_OK = "intercambioRegistral.rechazadosOk";
	public static final String I18N_ISICRESIR_SEND_OK = "intercambioRegistral.enviadosOk";
	public static final String I18N_ISICRESIR_SEND_ERROR = "intercambioRegistral.enviadosError";
	public static final String I18N_ISICRESIR_INPUT_DESKTOP_ERROR = "intercambioRegistral.bandejaEntradaError";
	public static final String I18N_ISICRESIR_OUTPUT_DESKTOP_ERROR = "intercambioRegistral.bandejaSalidaError";
	public static final String I18N_ISICRESIR_SEND_ESTADOS_ERRONEOS = "intercambioRegistral.enviar.estadosErroneos";
	public static final String I18N_ISICRESIR_MANUAL_OK = "intercambioRegistral.enviadosBandejaSalidaManualOk";
	public static final String I18N_ISICRESIR_MANUAL_ERROR = "intercambioRegistral.enviadosBandejaSalidaManualError";
	public static final String I18N_ISICRESIR_ERROR_UNIDAD_NO_MAPEADA = "intercambioRegistral.errorUnidadDestinoNoMapeada";
	public static final String I18N_ISICRESIR_ERROR_SHOW_HISTORIAL_INTERCAMBIO="intercambioRegistral.errorObtenerHistorialIntercambio";
	public static final String I18N_ISICRESIR_ERROR_NO_DATA_SHOW_HISTORIAL_INTERCAMBIO="intercambioRegistral.errorNoDataHistorialIntercambio";

	public static final String I18N_ISICRESIR_ERROR_NO_DATA_SHOW_DETALLE_INTERCAMBIO="intercambioRegistral.errorNoDataDetalleIntercambioRegistral";
	public static final String I18N_ISICRESIR_ERROR_SHOW_DETALLE_INTERCAMBIO="intercambioRegistral.errorObtenerDetalleIntercambioRegistral";

	public static final String I18N_ISICRESIR_ERROR_BUSCAR_IN_DCO = "intercambioRegistral.error.buscar.dco";

	public static final String REQUEST_MSG = "msg";
	public static final String REQUEST_ERROR = "error";

	public static final String XML_DIR_VALUE = "Dir";
	public static final String XML_ARCH_VALUE = "Arch";
	public static final String XML_FALSE_VALUE = "0";
	public static final String XML_TRUE_VALUE = "1";
	public static final String XML_INBOOK_ID_VALUE = "2147483646";
	public static final String XML_OUTBOOK_ID_VALUE = "2147483645";
	public static final String XML_COMBOX_VALUE = "ComboBox";
	public static final String XML_BUTTON_VALUE = "Button";
	public static final String XML_STATIC_VALUE = "Static";
	public static final String XML_LBOX_VALUE = "LBox";
	public static final String XML_SBAR_VALUE = "SBar";
	public static final String XML_CBOX_VALUE = "CBox";
	public static final String XML_EDIT_VALUE = "Edit";
	public static final String XML_TEXTAREA_VALUE = "TextArea";

	public static final String XML_DAT_TEXT = "Dat";
	public static final String XML_PAG_TEXT = "Pag";
	public static final String XML_INFDOC_TEXT = "InfDoc";
	public static final String XML_INVESICRES_TEXT = "invesicres";
	public static final String XML_SESSION_TEXT = "Session";
	public static final String XML_INVESDOC_TEXT = "invesDoc";
	public static final String XML_FRMDATA_TEXT = "FrmData";
	public static final String XML_IDCRL_TEXT = "idCrl";
	public static final String XML_TYPEID_TEXT = "typeId";
	public static final String XML_TYPEBUSC_TEXT = "typeBusc";
	public static final String XML_SICRESLIST_TEXT = "Sicreslist";
	public static final String XML_ORIGDOCS_TEXT = "OrigDocs";
	public static final String XML_DOCS_TEXT = "Docs";
	public static final String XML_ORDER_TEXT = "Order";
	public static final String XML_ASOCREGS_TEXT = "AsocRegs";
	public static final String XML_BOOK_TEXT = "Book";
	public static final String XML_FIELD_TEXT = "Field";
	public static final String XML_FIELDS_TEXT = "Fields";
	public static final String XML_LABEL_TEXT = "Label";
	public static final String XML_CHECKED_TEXT = "Checked";
	public static final String XML_IND_TEXT = "Ind";
	public static final String XML_EXT_TEXT = "Ext";
	public static final String XML_FRMTREE_TEXT = "FrmTree";
	public static final String XML_TABLEINFO_TEXT = "TableInfo";
	public static final String XML_HEADER_TEXT = "Header";
	public static final String XML_PAGEDATA_TEXT = "PageData";
	public static final String XML_TOTALNUMROWS_TEXT = "TotalNumRows";
	public static final String XML_CANUPDATEPER_TEXT = "CanUpdatePer";
	public static final String XML_LISTPID_TEXT = "LISTPID";
	public static final String XML_CANADDPER_TEXT = "CanAddPer";
	public static final String XML_CANADD_TEXT = "CanAdd";
	public static final String XML_CANDEL_TEXT = "CanDel";
	public static final String XML_TIPO_TEXT = "Tipo";
	public static final String XML_FIRSTROW_TEXT = "FirstRow";
	public static final String XML_FOLDERPID_TEXT = "FolderPId";
	public static final String XML_FOLDERNAME_TEXT = "FolderName";
	public static final String XML_FILEFORM_TEXT = "FileForm";
	public static final String XML_FOLDERID_TEXT = "FolderId";
	public static final String XML_VLDSAVE_TEXT = "VldSave";
	public static final String XML_FDRREADONLY_TEXT = "FdrReadOnly";
	public static final String XML_NUMROWS_TEXT = "NumRows";
	public static final String XML_NUMCOLS_TEXT = "NumCols";
	public static final String XML_CLASS_TEXT = "Class";
	public static final String XML_ROW_TEXT = "Row";
	public static final String XML_BOOKTYPE_TEXT = "BookType";
	public static final String XML_DISTTYPE_TEXT = "DistType";
	public static final String XML_ISBOOKADMIN_TEXT = "IsBookAdm";
	public static final String XML_NUMFOLDERS_TEXT = "NumFolders";
	public static final String XML_ALIGN_TEXT = "Align";
	public static final String XML_TABLEFORMAT_TEXT = "TableFormat";
	public static final String XML_TABLETEXT_TEXT = "TableText";
	public static final String XML_PAGES_TEXT = "Pages";
	public static final String XML_PAGE_TEXT = "Page";
	public static final String XML_NAME_TEXT = "Name";
	public static final String XML_NAMEARCH_TEXT = "NameArch";
	public static final String XML_NAME_UPPER_TEXT = "NAME";
	public static final String XML_NODELIST_TEXT = "NODELIST";
	public static final String XML_LIST_TEXT = "List";
	public static final String XML_CODE_TEXT = "CODE";
	public static final String XML_CODELOWER_TEXT = "Code";
	public static final String XML_CODELOWERADD_TEXT = "CodeAdd";
	public static final String XML_CODIGO_TEXT = "CODIGO";
	public static final String XML_FULLNAME_TEXT = "FULLNAME";
	public static final String XML_FULLNAMELOWER_TEXT = "FullName";
	public static final String XML_SELLO_TEXT = "SELLO";
	public static final String XML_HASSON_TEXT = "HASSON";
	public static final String XML_ACRON_TEXT = "ACRON";
	public static final String XML_ENABLE_TEXT = "ENABLE";
	public static final String XML_PARENTNAME_TEXT = "PARENTNAME";
	public static final String XML_PARENTREF_TEXT = "PARENTREF";
	public static final String XML_AUXDATA_TEXT = "AuxData";
	public static final String XML_RIGHTS_TEXT = "Rights";
	public static final String XML_UPDPROTECTEDFIELDS_TEXT = "UpdProtectedFields";
	public static final String XML_REGISTER_TEXT = "Register";
	public static final String XML_REGISTER_PARENT_TEXT = "RegisterParent";
	public static final String XML_FDRID_TEXT = "FdrId";
	public static final String XML_COLUMNS_TEXT = "Columns";
	public static final String XML_FOLDERSINFO_TEXT = "FoldersInfo";
	public static final String XML_FOLDERINFO_TEXT = "FolderInfo";
	public static final String XML_COLUMN_TEXT = "Column";
	public static final String XML_FIELDS_LIST = "FieldsList";
	public static final String XML_COL_TEXT = "COL";
	public static final String XML_COL_LOWER_TEXT = "Col";
	public static final String XML_DESTINO_TEXT = "Destino";
	public static final String XML_ROW_UPPER_TEXT = "ROW";
	public static final String XML_INDEX_TEXT = "INDEX";
	public static final String XML_COLUMNTEXT_TEXT = "COLUMNTEXT";
	public static final String XML_APE1_TEXT = "Ape1";
	public static final String XML_APE2_TEXT = "Ape2";
	public static final String XML_VALUES_TEXT = "Values";
	public static final String XML_BOOKTREE_TEXT = "BookTree";
	public static final String XML_ROOTNAME_TEXT = "RootName";
	public static final String XML_ROOT_TEXT = "Root";
	public static final String XML_MESSAGES_TEXT = "Messages";
	public static final String XML_MESSAGE_TEXT = "Message";
	public static final String XML_CASESENSITIVE_TEXT = "CaseSensitive";
	public static final String XML_DOC_TEXT = "Doc";
	public static final String XML_NODE_TEXT = "Node";
	public static final String XML_NODE_UPPER_TEXT = "NODE";
	public static final String XML_TYPE_TEXT = "Type";
	public static final String XML_TYPE_UPPER_TEXT = "TYPE";
	public static final String XML_PROVID_TEXT = "PROVID";
	public static final String XML_ID_TEXT = "Id";
	public static final String XML_ID_UPPER_TEXT = "ID";
	public static final String XML_PROPIO_TEXT = "Propio";
	public static final String XML_COLS_TEXT = "Cols";
	public static final String XML_PROC_TEXT = "Proc";
	public static final String XML_PREFERENCE_TEXT = "Preference";
	public static final String XML_PERSONDATA_UPPER_TEXT = "PERSONDATA";
	public static final String XML_PERSONDATA_TEXT = "PersonData";
	public static final String XML_PERSONID_TEXT = "PersonId";
	public static final String XML_PERSONNAME_TEXT = "PersonName";
	public static final String XML_PERSONTYPE_TEXT = "PersonType";
	public static final String XML_DOCUMENTTYPE_TEXT = "DocumentType";
	public static final String XML_DOCUMENT_TEXT = "Document";
	public static final String XML_IDPROC_TEXT = "IdProc";
	public static final String XML_IDARCH_TEXT = "IdArch";
	public static final String XML_IDFDR_TEXT = "IdFdr";
	public static final String XML_STATE_TEXT = "State";
	public static final String XML_OUTOFDATE_TEXT = "OutOfDate";
	public static final String XML_REMARKS_TEXT = "Remarks";
	public static final String XML_TITLE_TEXT = "Title";
	public static final String XML_ADDINFO_TEXT = "AddInfo";
	public static final String XML_INIT_TEXT = "INIT";
	public static final String XML_PASO_TEXT = "PASO";
	public static final String XML_END_TEXT = "END";
	public static final String XML_TOTAL_TEXT = "TOTAL";
	public static final String XML_INIT_LOWER_TEXT = "Init";
	public static final String XML_PASO_LOWER_TEXT = "Paso";
	public static final String XML_END_LOWER_TEXT = "End";
	public static final String XML_TOTAL_LOWER_TEXT = "Total";
	public static final String XML_TOTAL_ALL_LOWER_TEXT = "total";
	public static final String XML_RESULT_MAX_TEXT = "maxResult";
	public static final String XML_RESULT_MAX_LOWER_TEXT = "MaxResult";
	public static final String XML_HEADMINUTA_TEXT = "HEADMINUTA";
	public static final String XML_REGISTRO_TEXT = "Registro";
	public static final String XML_HEAD_TEXT = "HEAD";
	public static final String XML_BODY_TEXT = "BODY";
	public static final String XML_MINUTA_TEXT = "MINUTA";
	public static final String XML_ELEMENTOS_TEXT = "ELEMENTOS";
	public static final String XML_BODYMINUTA_TEXT = "BODYMINUTA";
	public static final String XML_CONTEXT_TEXT = "CONTEXT";
	public static final String XML_CONTEXT_LOWER_TEXT = "Context";
	public static final String XML_READONLY_TEXT = "ReadOnly";
	public static final String XML_LIBRO_TEXT = "Libro";
	public static final String XML_ISICRESQRYFMT_TEXT = "iSicresQryFmt";
	public static final String XML_PARAMS_TEXT = "PARAMS";
	public static final String XML_PROPERTIES_TEXT = "Properties";
	public static final String XML_AUTHENTICATION_TEXT = "Authentication";
	public static final String XML_REGNUMBER_TEXT = "RegNumber";
	public static final String XML_REGDATE_TEXT = "RegDate";
	public static final String XML_USER_TEXT = "User";
	public static final String XML_USERS_TEXT = "Users";
	public static final String XML_USERNAME_TEXT = "UserName";
	public static final String XML_OFFICECODE_TEXT = "OfficeCode";
	public static final String XML_OFFICENAME_TEXT = "OfficeName";
	public static final String XML_OFFICEENABLED_TEXT = "OfficeEnabled";
	public static final String XML_OTHEROFFICE_TEXT = "OtherOffice";
	public static final String XML_SESSIONID_TEXT = "SessionId";
	public static final String XML_STAMPOFFICECODE_TEXT = "StampOfficeCode";
	public static final String XML_STAMPOFFICEDESC_TEXT = "StampOfficeDesc";
	public static final String XML_UNITCODE_TEXT = "UnitCode";
	public static final String XML_ARCHIVEPID_TEXT = "ArchivePId";
	public static final String XML_ARCHIVEID_TEXT = "ArchiveId";
	public static final String XML_ARCHIVENAME_TEXT = "ArchiveName";
	public static final String XML_FDRQRYPID_TEXT = "FdrQryPId";
	public static final String XML_PERMISOS_TEXT = "Permisos";
	public static final String XML_BOOKADM_TEXT = "BookAdm";
	public static final String XML_DIMENSIONS_TEXT = "Dimensions";
	public static final String XML_LEFT_TEXT = "Left";
	public static final String XML_TOP_TEXT = "Top";
	public static final String XML_WIDTH_TEXT = "Width";
	public static final String XML_HEIGHT_TEXT = "Height";
	public static final String XML_CONTROL_TEXT = "Control";
	public static final String XML_TEXT_TEXT = "Text";
	public static final String XML_STYLE_TEXT = "Style";
	public static final String XML_OPERATOR_TEXT = "Operator";
	public static final String XML_SELECTED_TEXT = "Selected";
	public static final String XML_VALUE_TEXT = "Value";
	public static final String XML_FLDID_TEXT = "FldId";
	public static final String XML_ISEXTENDED_TEXT = "IsExtended";
	public static final String XML_ACTION_TEXT = "Action";
	public static final String XML_FLDIDADD_TEXT = "FldIdAdd";
	public static final String XML_TBLVAL_TEXT = "TblVal";
	public static final String XML_TBLFLDID_TEXT = "TblFldId";
	public static final String XML_ISVISIBLE_TEXT = "IsVisible";
	public static final String XML_ISSUST_TEXT = "IsSust";
	public static final String XML_MAXLENGTH_TEXT = "MaxLength";
	public static final String XML_CADENA_TEXT = "Cadena";
	public static final String XML_ISRESUM_TEXT = "IsResum";
	public static final String XML_OBLI_TEXT = "Obli";
	public static final String XML_DISABLED_TEXT = "Disabled";
	public static final String XML_STYLEVALIMG_TEXT = "Style_Val_Img";
	public static final String XML_FLD_UPPERF_TEXT = "Fld";
	public static final String XML_FLD_TEXT = "fld";
	public static final String XML_FLD_UPPER_TEXT = "FLD";
	public static final String XML_CONFREG_TEXT = "ConfReg";
	public static final String XML_VALIDATION_TEXT = "Validation";
	public static final String XML_DESCRIPTION_TEXT = "Description";
	public static final String XML_DESCRIPTIONADD_TEXT = "DescriptionAdd";
	public static final String XML_ERROR_TEXT = "Error";
	public static final String XML_DISTFIELDS_TEXT = "DistFields";
	public static final String XML_DISTFIELD_TEXT = "DistField";
	public static final String XML_REGFIELDS_TEXT = "RegFields";
	public static final String XML_REGFIELD_TEXT = "RegField";
	public static final String XML_DISTWHERE_TEXT = "DistWhere";
	public static final String XML_REGWHERE_TEXT = "RegWhere";
	public static final String XML_DATATYPE_TEXT = "DataType";
	public static final String XML_TVALID_TEXT = "TValid";
	public static final String XML_FIELDLABEL_TEXT = "FieldLabel";
	public static final String XML_FIELDNAME_TEXT = "FieldName";
	public static final String XML_FIELDSINFO_TEXT = "FieldsInfo";
	public static final String XML_FIELDINFO_TEXT = "FieldInfo";
	public static final String XML_FIELDSNUMBER_TEXT = "FieldsNumber";
	public static final String XML_FLDNAME_TEXT = "FldName";
	public static final String XML_OPERATORS_TEXT = "Operators";
	public static final String XML_VALUEID_TEXT = "ValueId";
	public static final String XML_PARAMETER_TEXT = "Parameter";
	public static final String XML_PARAMETERS_TEXT = "Parameters";
	public static final String XML_USERCONFIG_TEXT = "UserConfig";
	public static final String XML_CTRL_ID = "CtrlId";
	// Permisos sobre distribución
	public static final String XML_PERMS_TEXT = "Perms";
	public static final String XML_CAN_ACCEPT_TEXT = "CanAccept";
	public static final String XML_CAN_REJECT_TEXT = "CanReject";
	public static final String XML_CAN_ARCHIVE_TEXT = "CanArchive";
	public static final String XML_CAN_CHANGE_DEST_TEXT = "CanChangeDest";
	public static final String XML_CAN_CHANGE_DEST_REJECT_TEXT = "CanChangeDestReject";
	public static final String XML_CAN_DIST_TEXT = "CanDist";
	public static final String XML_AUTO_DIST_TEXT = "AutoDist";
	public static final String XML_CAN_OPENREG_TEXT = "CanOpenReg";
	public static final String XML_CAN_OPERATION_IR_TEXT = "CanOperationIR";

	//Asociacion de registros
	public static final String XML_INICIO_TEXT = "inicio";
    public static final String XML_FIN_TEXT = "fin";
    public static final String XML_RANGO_TEXT = "rango";
    public static final String XML_BOOKID_TEXT = "BookId";
    public static final String XML_FOLDERNUMBER_TEXT = "FolderNumber";
    public static final String XML_FOLDERDATE_TEXT = "FolderDate";
    public static final String XML_SUMMARY_TEXT = "Summary";
    public static final String XML_REGISTERBOOK_TEXT = "RegisterBook";
    public static final String XML_REGSWHERE_TEXT = "RegWhere";


	public static final String XML_FONT_FAMILY_TEXT = "font-family";
	public static final String XML_FONT_SIZE_TEXT = "font-size";
	public static final String XML_FONT_COLOR_TEXT = "color";
	public static final String XML_FONT_ALIGN_TEXT = "text-align";

	public static final String XML_XTYLE_COLOR_FORMAT = XML_FONT_FAMILY_TEXT
			+ ":" + "{0}" + "; " + XML_FONT_SIZE_TEXT + ":" + "{1}" + "pt; "
			+ XML_FONT_COLOR_TEXT + ":" + "{2}" + "; " + XML_FONT_ALIGN_TEXT
			+ ":" + "{3}" + ";{4}";

	public static final String XML_XTYLE_FORMAT = XML_LEFT_TEXT + ":" + "{0}"
			+ "px; " + XML_TOP_TEXT + ":" + "{1}" + "px; " + XML_WIDTH_TEXT
			+ ":" + "{2}" + "px; " + XML_HEIGHT_TEXT + ":" + "{3}" + "px;";
	public static final String XML_XTYLE_FORMAT_2 = XML_LEFT_TEXT + ":" + "{0}"
			+ "px; " + XML_TOP_TEXT + ":" + "{1}" + "px;";

	public static final String XSL_RELATIVE_PATH = "/xsl";

	public static final String XSL_LEST_RELATIVE_PATH = "/xsl/lest.xsl";
	public static final String XSL_QRYFMTMP_RELATIVE_PATH = "/xsl/qryfmt.xsl";
	public static final String XSL_VLDRES_RELATIVE_PATH = "/xsl/list.xsl";
	public static final String XSL_TBLTEXT_RELATIVE_PATH = "/xsl/tbltext.xsl";
	public static final String XSL_FRMT_RELATIVE_PATH = "/xsl/frmt.xsl";
	public static final String XSL_FMRDATA_RELATIVE_PATH = "/xsl/frmdata.xsl";
	public static final String XSL_DISTRIBUTION_RELATIVE_PATH = "/xsl/dtrlist.xsl";
	public static final String XSL_DISTRIBUTIONSEARCH_RELATIVE_PATH = "/xsl/searchdist.xsl";
	public static final String XSL_DISTRIBUTIONBOOK_RELATIVE_PATH = "/xsl/vldBooks.xsl";
	public static final String XSL_FRMPERSISTFIELDS_RELATIVE_PATH = "/xsl/persistflds.xsl";
	public static final String XSL_REPORT_RELATIVE_PATH = "/xsl/report.xsl";
	public static final String XSL_REPORTFMT_RELATIVE_PATH = "/xsl/reportsfmt.xsl";
	public static final String XSL_VLDUNIT_RELATIVE_PATH = "/xsl/vldUnit.xsl";
	public static final String XSL_VLDBUSCINTER_RELATIVE_PATH = "/xsl/vldbuscinter.xsl";
	public static final String XSL_VLDCIUDAD_RELATIVE_PATH = "/xsl/vldciudad.xsl";
	public static final String XSL_VLDDIRECCION_RELATIVE_PATH = "/xsl/vlddireccion.xsl";
	public static final String XSL_VLDDIRINTER_RELATIVE_PATH = "/xsl/vlddirinter.xsl";
	public static final String XSL_VLDPOBLACION_RELATIVE_PATH = "/xsl/vldpoblacion.xsl";
	public static final String XSL_DTRLISTFDR_RELATIVE_PATH = "/xsl/dtrlistfdr.xsl";
	public static final String XSL_HISTORYDISTRIBUTION_RELATIVE_PATH = "/xsl/dtrhistorydistribution.xsl";
	public static final String XSL_UPDHISLISTFDR_RELATIVE_PATH = "/xsl/updhislistfdr.xsl";
	public static final String XSL_ORIGDOCS_RELATIVE_PATH = "/xsl/origdocs.xsl";
	public static final String XSL_ASOCREGS_RELATIVE_PATH = "/xsl/asocregs.xsl";
	public static final String XSL_USERCONFIG_RELATIVE_PATH = "/xsl/userconfig.xsl";
	public static final String XSL_ASOCREGSSEARCH_RELATIVE_PATH = "/xsl/asocregssearch.xsl";

	public static final String IDOC_STATIC = "IDOC_STATIC";
	public static final String IDOC_CBOX = "IDOC_CBOX";
	public static final String IDOC_EDIT = "IDOC_EDIT";
	public static final String IDOC_BUTTON = "IDOC_BUTTON";

	// Validación de personas
	public static final String XML_PERSONA_NOMBRE_TEXT = "Nombre";
	public static final String XML_PERSONA_APELLIDO1_TEXT = "Apellido1";
	public static final String XML_PERSONA_APELLIDO2_TEXT = "Apellido2";

	public static final String XML_PERSONA_TEXT = "Persona";
	public static final String XML_TIPODOC_TEXT = "TipoDoc";
	public static final String XML_NIF_TEXT = "NIF";

	public static final String XML_TYPEDOCS_TEXT = "TipoDocumentos";
    public static final String XML_TYPEDOC_TEXT = "TipoDocumento";
    public static final String XML_TYPEDOC_DESCRIPCION = "Descripcion";
    public static final String XML_TYPEDOC_TYPEPERSONA = "TipoPersona";
    public static final String XML_TYPEDOC_CODIGO = "Codigo";

	public static final String XML_DIRECCION_TEXT = "Direccion";
	public static final String XML_CODPOSTAL_TEXT = "CodPostal";
	public static final String XML_POBLACION_TEXT = "Poblacion";
	public static final String XML_PROVINCIA_TEXT = "Provincia";
	public static final String XML_PREFERENCIA_TEXT = "Preferencia";

	public static final String XML_DOMICILIOS_TEXT = "Domicilios";
	public static final String XML_DOMICILIO_TEXT = "Domicilio";

	public static final String XML_DISTRIBUTION_ROOT = "Distribution";

	public static final String XPATH_PERSONAS_PERSONA = "//Personas/Persona";
	public static final String XPATH_PERSONA_ID = "//Persona/Id";
	public static final String XPATH_PERSONA_TIPO = "//Persona/Tipo";
	public static final String XPATH_PERSONA_NOMBRE = "//Persona/Nombre";
	public static final String XPATH_PERSONA_APELLIDO1 = "//Persona/Apellido1";
	public static final String XPATH_PERSONA_APELLIDO2 = "//Persona/Apellido2";
	public static final String XPATH_PERSONA_TIPODOC = "//Persona/TipoDoc";
	public static final String XPATH_PERSONA_NIF = "//Persona/NIF";
	public static final String XPATH_PERSONA_DOMICILIO = "//Persona/Domicilios/Domicilio";
	public static final String XPATH_CIUDADES_CIUDAD = "//Ciudades/Ciudad";
	public static final String XPATH_PROVINCIAS_PROVINCIA = "//Provincias/Provincia";
	public static final String XPATH_DOMICILIOS_DOMICILIO = "//Domicilios/Domicilio";

	public static final String XPATH_TYPEDOCS_TYPEDOC = "//TipoDocumentos/TipoDocumento";


	public static final String XPATH_ASOCREGS_REGISTO = "//AsocRegs/Registro";

	/*
	 * 66575267 - Gabriel Saiz
	 */
	public static final String XPATH_PERSONA_ROOT = "//Persona";
	public static final String XPATH_DOMICILIO_ROOT = "//Domicilio";

	public static final String XML_IDTEL_TEXT = "IdTel";
	public static final String XML_TIPOTEL_TEXT = "TipoTel";
	public static final String XML_PREFERENCIATEL_TEXT = "PreferenciaTel";
	public static final String XML_DIRECCIONTEL_TEXT = "DireccionTel";
	public static final String XML_TELEMATICAS_TEXT = "Telematicas";
	public static final String XML_TELEMATICA_TEXT = "Telematica";
	public static final String XPATH_PERSONA_TELEMATICA = "//Persona/Telematicas/Telematica";
	public static final String XPATH_TELEMATICAS_TELEMATICA = "//Telematicas/Telematica";
	public static final String XSL_VLDDIRINTERTEL_RELATIVE_PATH = "/xsl/vlddirintertel.xsl";
	public static final String XPATH_TELEMATICA_ROOT = "//Telematica";


	public static final String COMMA = ",";
	public static final String SPACE = " ";

	public static final String XPATH_TYPEADDRESSES_TYPEADDRESS = "//TipoDirecciones/TipoDireccion";
	public static final String XML_TYPEADDRESS_DESCRIPCION = "Descripcion";
	public static final String XML_TYPEADDRESS_CODIGO = "Codigo";

	public static final String XML_ORDERBYTABLE = "OrderByTable";


	public static final String EXTENSION_DEFECTO = "-";

	public static final String BLANK = "";


	//Politicas de autenticación
	public static final String AUTHENTICATION_POLICY_TYPE_INVESDOC = "invesdoc";
	public static final String AUTHENTICATION_POLICY_TYPE_LDAP = "ldap";

	// Arrays con la información de los campo de búsqueda para los campos validados
	public String STRING_FIELD_NAME[] = {"CODE", "ACRON", "NAME", "TRANSPORT", "MATTER"};
	public String STRING_FIELD_LABEL[] = {"0", "1", "2", "3", "2"};

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
