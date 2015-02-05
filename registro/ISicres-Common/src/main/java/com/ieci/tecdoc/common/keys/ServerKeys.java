//
// FileName: ServerKeys.java
//
package com.ieci.tecdoc.common.keys;

/**
 * Claves de objetos en el servidor.
 *
 * @author lmvicente
 * @version
 * @since
 * @creationDate 02-mar-2004
 */

public interface ServerKeys {

	public static final String QUERY_FILTER = "QueryFilter";
	public static final String APERMS_USER = "APermsUser";
	public static final String GENPERMS_USER = "GenPermsUser";
	public static final String OFIC_NAME = "OficName";
	public static final String LAST_CONNECTION_USER = "LastConnectionUser";
	public static final String IS_SICRES3 = "isSicres3";

	public static final String INVESICRES_CONFIGURATION = "INVESICRESCONF";

	public static final String JNDI_HIBERNATE_FACTORY_NAME = "java:/hibernate/HibernateFactory";

	public static final String JNDI_DATASOURCE_NAME = "java:/ISicres";

	public static final String AXSF = "AXSF";
	public static final String AXSF_QUERY_RESULTS = "AXSF_QUERY_RESULTS";
	public static final String AXSF_QUERY = "AXSF_QUERY";

	public static final String JNDI_AXXNID_HOME = "java:comp/env/ejb/AxXnid";
	public static final String JNDI_AXXF_HOME = "java:comp/env/ejb/AxXf";
	public static final String JNDI_AXSF_HOME = "java:comp/env/ejb/AxSf";
	public static final String JNDI_AXPAGEH_HOME = "java:comp/env/ejb/AxPageh";
	public static final String JNDI_AXPAGEHX_HOME = "java:comp/env/ejb/AxPagehx";
	public static final String JNDI_AXFDRH_HOME = "java:comp/env/ejb/AxFdrh";
	public static final String JNDI_AXDOCH_HOME = "java:comp/env/ejb/AxDoch";
	public static final String JNDI_AXDOCHX_HOME = "java:comp/env/ejb/AxDochx";
	public static final String JNDI_AXCLFH_HOME = "java:comp/env/ejb/AxClfh";
	public static final String JNDI_AXCLFHX_HOME = "java:comp/env/ejb/AxClfhx";

	// XPath xml configuración invesicres
	public static final String XPATH_ALARM_ON_INCOMPLETE_REG = "//Configuration/General/AlarmOnIncompleteReg";
	public static final String XPATH_MODIFY_SYSTEM_DATE = "//Configuration/General/ModifySystemDate";
	public static final String XPATH_REPOSITORY_SYSTEM = "//Configuration/General/RepositorySystem";
	public static final String XPATH_POPULATION_PARTITION = "//Configuration/General/PopulationPartition";
	public static final String XPATH_AUTHENTICATION_BY_OFFICE = "//Configuration/General/AuthenticationByOffice";
	public static final String XPATH_DOCUMENT_HASHING = "//Configuration/General/DocumentHashing";
	public static final String XPATH_FORMAT_TYPE_ATRIBUTE = "//Configuration/Numeration/Format/@type";
	public static final String XPATH_TIMEOUT = "//Configuration/Distribution/TimeOut";
	public static final String XPATH_ALARM_ON_NEW_DIST = "//Configuration/Distribution/AlarmOnNewDist";
	public static final String XPATH_AUTO_ARCHIVING = "//Configuration/Distribution/AutoArchiving";
	public static final String XPATH_ALARM_ON_REJECTED = "//Configuration/Distribution/AlarmOnRejected";
	public static final String XPATH_DIST_OUT_REGISTERS = "//Configuration/Distribution/DistSRegisters";
	public static final String XPATH_VIEW_ACCEPTED_REGISTERS = "//Configuration/Distribution/ViewAcceptedRegisters";
	public static final String XPATH_CAN_CHANGE_DEST_WITHOUT_LIST = "//Configuration/Distribution/CanChangeDestWithoutList";
	public static final String XPATH_AUTO_DIST = "//Configuration/Distribution/AutoDistribution";
	public static final String XPATH_VALIDATION = "//Configuration/Extension/Validation";

	// XPath xml configuración usuario
	public static final String XPATH_CHECK_FIELDS_BOOKID = "//Configuration/Fields[@bookid=";
	public static final String XPATH_PERSONVALIDATION = "//Configuration/PersonValidation";
	public static final String XPATH_SHOW_SCAN_DIALOG = "//Configuration/ShowScanDlg";
	public static final String XPATH_REMEMBER_LAST_SELECTED_UNIT = "//Configuration/RememberLastSelectedUnit";
	public static final String XPATH_SELECTED_UNIT = "//Configuration/SelectedUnit[@bookId=";

	public static final String XPATH_SELECTED_UNIT_ORIGEN = "/codigoUnidadOrigen";
	public static final String XPATH_SELECTED_UNIT_DESTINO = "/codigoUnidadDestino";

	// XPath xml configuración booktype
	public static final String XPATH_INFOSTAMPING = "//Configuration/InfoStamping";
	public static final String XPATH_INFOSTAMPING_FLD = "//Configuration/InfoStamping/Fld";

	public static final String EQUAL = "Igual a";
	public static final String BEGIN_BY = "Empieza por";
	public static final String CONTAIN = "Contiene";

}
