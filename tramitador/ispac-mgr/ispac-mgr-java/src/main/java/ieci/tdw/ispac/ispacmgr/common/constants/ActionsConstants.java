package ieci.tdw.ispac.ispacmgr.common.constants;

import java.util.ArrayList;
import java.util.Iterator;

import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt;
import ieci.tdw.ispac.ispacmgr.common.Formatters;

public class ActionsConstants {

	private final static String getKey(String key){
		return "ActionsConstants" + "_" + key;
	}

	public static abstract class Command {
		public abstract String execute(BeanPropertyFmt p);
	}

	private static String[] getFromFormatter(String formatterName, String typeBeanProperty, Command cmd){
		ArrayList ret = new ArrayList();
		BeanFormatter bf = (BeanFormatter)Formatters.getInstance().getMap().get(formatterName);
		for (Iterator itFields = bf.listIterator(); itFields.hasNext();) {
			BeanPropertyFmt p = (BeanPropertyFmt)itFields.next();
			if (p.getFieldType().equals(typeBeanProperty)){
				ret.add(cmd.execute(p));
			}
		}
		return (String[])ret.toArray(new String[ret.size()]);
	}

	public static Command COMMAND_GET_ID = new Command(){
		public String execute(BeanPropertyFmt p) {
			return p.getId();
		}
	};
	public static Command COMMAND_GET_PROPERTY = new Command(){
		public String execute(BeanPropertyFmt p) {
			return p.getProperty();
		}
	};

	public static String[] getParam(Command cmd, String formatterName, String typePropertyFmt){
		return getFromFormatter(formatterName, typePropertyFmt, cmd);
	}

	public static final String BATCH_TASK = getKey("BATCH_TASK");
	public static final String BATCH_TASK_EXPS = getKey("BATCH_TASK_EXPS");
	public static final String BATCH_TASK_LIST = getKey("BATCH_TASK_LIST");
	public static final String ID_BATCH_TASK = getParam(COMMAND_GET_ID, Formatters.BATCH_TASK_LIST_FORMATTER_NAME,"LINK")[0];
	public static final String PROPERTY_CREATED_DOC = getParam(COMMAND_GET_PROPERTY, Formatters.BATCH_TASK_FORMATTER_NAME, "BOOLEAN")[0];
	public static final String PROPERTY_MADE_TASKS = getParam(COMMAND_GET_PROPERTY, Formatters.BATCH_TASK_FORMATTER_NAME, "BOOLEAN")[1];
	public static final String TIPO_DOC_PARAMETER = getKey("tipoDocumento");
	public static final String POSIBLES_TRAMITES_DE_FASE = getKey("POSIBLES_TRAMITES_DE_FASE");
	public static final String ID_TRAMITE_PARAMETER = getKey("ID_TRAMITE_PARAMETER");
	public static final String TEMPLATES_LIST = getKey("TEMPLATES_LIST");
	public static final String TPDOCS_LIST = getKey("TPDOCS_LIST");
	public static final String PROPERTY_TASK_ID = getParam(COMMAND_GET_PROPERTY, Formatters.BATCH_TASK_FORMATTER_NAME, "BOOLEAN_HIDDEN_VALUE")[0];

	public static final String PARAM_FORM_REFRESHER = getKey("refresherflag");

	public static final String ORG_UID = getKey("uid");
	public static final String ORG_UNITS = getKey("orgunits");
	public static final String PARENT_UID = getKey("parent_uid");
	public static final String BATCH_SIGN_LIST = getKey("documentsToSingList");
	public static final String EXPS_TRASH_LIST = getKey("expsTrashList");
	public static final String SIGN_DOCUMENT_LIST = getKey("signDocumentList");
	public static final String SIGN_STATES_MAP = getKey("signStatesMap");
	public static final String TERM_LIST = getKey("termsList");
	public static final String TERM_DATE_ERROR = getKey("termsDateError");
	public static final String HASH_CODE = "HASH";

	public static final String INIT_SIGN = "initSign";
	public static final String SELECT_SIGN_CIRCUIT = "selectSignCircuit";
	public static final String SET_PROPERTIES_SIGN_CIRCUIT = "setPropertiesSignCircuit";
	public static final String GET_STATE_DOCUMENT_IN_PORTAFIRMAS= "getStateDocumentInProcessSign";
	public static final String INIT_SIGN_CIRCUIT = "initSignCircuit";
	public static final String SELECT_OPTION = "selectOption";
	public static final String SIGN = "sign";
	public static final String BATCH_SIGN = "batchSign";
	public static final String CALCULATE_DOCUMENTS_HASH = "calculateDocumentsHash";
	public static final String PARAMETER_METHOD = "method";
	public static final String FORMATTER = getKey("formatter");
	public static final String SIGN_CIRCUIT_LIST = getKey("signCircuitList");
	public static final String SIGN_CIRCUIT_ID = getKey("signCircuitId");
	public static final String SIGN_HISTORICS_DATE_ERROR = getKey("sighHistoricsDateError");

	public static final String NEW_EXPEDIENTS_LIST = getKey("newExpedientList");
	public static final String NUM_EXP_SOURCE_CLONE = getKey("numExpSourceClone");

	public static final String ACTIVE_SESSIONS = getKey("activeSessions");
	public static final String USER = getKey("user");
	public static final String READONLYSTATE = getKey("readonlyState");
	public static final String PWD=getKey("pwd");

	public static final String FILTER_ID = "filterId";
	public static final String HIERARCHICAL_ID = "hierarchicalId";
	public static final String HIERARCHICAL_NAME = "hierarchicalName";

	public static final String HIERARCHICAL_DESCENDANTID = "descendantId";
	public static final String PARAM_VALUE_LIST = "ValueList";

	public static final String SUCCES_KEY = "success";

	public static final String PARAMETER_ENTITY = "entity";
	public static final String PARAM_KEY = "keyId";
	public static final String SUBPROCESS_ACTIVITY_LIST = "subprocessActivityList";

	public static final String OK_UPLOAD_FILES_LIST = "okUploadFilesList";
	public static final String ERROR_UPLOAD_FILES_LIST = "errorUploadFilesList";
	public static final String OK_SCANNED_FILES_LIST = "okScannedFilesList";
	public static final String ERROR_SCANNED_FILES_LIST = "errorScannedFilesList";

	// Ahora el formulario de búsqueda está en sesión y se mantienen los parámetros de la última búsqueda realizada
	// public static final String FROM_SEARCH_RESULTS = "fromSearchResults";
	public static final String FORM_SEARCH_RESULTS = "searchForm";

	public static final String THIRDPARTY_POSTAL_ADDRESSES_LIST = "ThirPartyPostalAddressesList";
	public static final String POSTAL_ADDRESS = "PostalAddress";
	public static final String THIRDPARTY_ELECTRONIC_ADDRESSES_LIST = "ThirPartyElectronicAddressesList";
	public static final String ELECTRONIC_ADDRESS = "ElectronicAddress";
	public static final String THIRDPARTY = "ThirdParty";
	public static final String THIRDPARTY_LIST = "ThirdPartyList";
	public static final String CURRENT_STATE = "_currentState";

	public static final String LOAD_NOT_ASSOCIATED = "loadNotAssociated";

}
