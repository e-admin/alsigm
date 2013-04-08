package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;

public class SicresOficinaTabla {

	private static final Logger logger = Logger.getLogger(SicresOficinaTabla.class);

	private static final String SEPARATOR = ", ";
	private static final String POINT = ".";
	private static final String BLANK = " ";
	public static final String TABLE_NAME = "scr_ofic";
	public static final String TABLE_NAME_ORGANIZACIONES = "scr_orgs";

	private static final String CN_ID = "id";
	private static final String CN_CODE = "code";
	private static final String CN_ACRON = "acron";
	private static final String CN_NAME = "name";
	private static final String CN_CREATION_DATE = "creation_date";
	private static final String CN_DISABLE_DATE = "disable_date";
	private static final String CN_ID_ORGS = "id_orgs";
	private static final String CN_STAMP = "stamp";
	private static final String CN_DEPTID = "deptid";
	private static final String CN_TYPE = "type";

	private static final String CN_ID_QUALIFIED = TABLE_NAME + POINT + "id";
	private static final String CN_CODE_QUALIFIED = TABLE_NAME + POINT + "code";
	private static final String CN_ACRON_QUALIFIED = TABLE_NAME + POINT + "acron";
	private static final String CN_NAME_QUALIFIED = TABLE_NAME + POINT + "name";
	private static final String CN_CREATION_DATE_QUALIFIED = TABLE_NAME + POINT + "creation_date";
	private static final String CN_DISABLE_DATE_QUALIFIED = TABLE_NAME + POINT + "disable_date";
	private static final String CN_ID_ORGS_QUALIFIED = TABLE_NAME + POINT + "id_orgs";
	private static final String CN_STAMP_QUALIFIED = TABLE_NAME + POINT + "stamp";
	private static final String CN_DEPTID_QUALIFIED = TABLE_NAME + POINT + "deptid";
	private static final String CN_TYPE_QUALIFIED = TABLE_NAME + POINT + "type";

	private static final String CN_ID_ORG = TABLE_NAME_ORGANIZACIONES + POINT + "id";

	//Parte de los grupos de LDAP (para unir tablas)
	private static final String TABLE_GROUP_LDAP = "IUSERLDAPGRPHDR";
	private static final String CN_GROUP_ID = "IUSERLDAPGRPHDR.id";

	//Parte de departamentos
	private static final String TABLE_DEPT_LDAP = "IUSERDEPTHDR";
	private static final String CN_DEPT_ID = "IUSERDEPTHDR.id";
	private static final String CN_DEPT_CRTRID = "IUSERDEPTHDR.crtrid";
	private static final String CN_DEPT_TYPE = "IUSERDEPTHDR.type";


	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_CODE
			+ SEPARATOR + CN_ACRON + SEPARATOR + CN_NAME + SEPARATOR
			+ CN_CREATION_DATE + SEPARATOR + CN_DISABLE_DATE + SEPARATOR
			+ CN_ID_ORGS + SEPARATOR + CN_STAMP + SEPARATOR + CN_DEPTID
			+ SEPARATOR + CN_TYPE;
	private static final String QUALIFIED_COLUMN_NAMES = CN_ID_QUALIFIED
			+ SEPARATOR + CN_CODE_QUALIFIED + SEPARATOR + CN_ACRON_QUALIFIED
			+ SEPARATOR + CN_NAME_QUALIFIED + SEPARATOR
			+ CN_CREATION_DATE_QUALIFIED + SEPARATOR
			+ CN_DISABLE_DATE_QUALIFIED + SEPARATOR + CN_ID_ORGS_QUALIFIED
			+ SEPARATOR + CN_STAMP_QUALIFIED + SEPARATOR + CN_DEPTID_QUALIFIED
			+ SEPARATOR + CN_TYPE_QUALIFIED;
	private static final String UPDATE_COLUMN_NAMES = CN_CODE + SEPARATOR
			+ CN_ACRON + SEPARATOR + CN_NAME + SEPARATOR + CN_CREATION_DATE
			+ SEPARATOR + CN_DISABLE_DATE + SEPARATOR + CN_ID_ORGS + SEPARATOR
			+ CN_STAMP + SEPARATOR + CN_DEPTID + SEPARATOR + CN_TYPE;

	private static final String ALL_COLUMN_NAMES_LDAP = CN_ID_QUALIFIED
			+ SEPARATOR + CN_CODE_QUALIFIED + SEPARATOR + CN_NAME_QUALIFIED
			+ SEPARATOR + CN_ACRON_QUALIFIED + SEPARATOR
			+ CN_CREATION_DATE_QUALIFIED + SEPARATOR
			+ CN_DISABLE_DATE_QUALIFIED;

	public SicresOficinaTabla() {
	}

	public String getUpdateColumnNames() {
		return UPDATE_COLUMN_NAMES;
	}

	public String getTableName() {
	   return TABLE_NAME;
	}

	public String getQualifiedTableName() {
	   return TABLE_NAME + BLANK + TABLE_NAME;
	}

	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}

	public String getQualifiedColumnNames() {
	   return QUALIFIED_COLUMN_NAMES;
	}

	public String getAllColumnNamesLdap() {
		return ALL_COLUMN_NAMES_LDAP;
	}

	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

	public String getByCode(String code) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_CODE).append(" = '").append(code).append("' ");
		return sbAux.toString();
	}

	public String getByDeptId(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_DEPTID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

	public String getByLdapGuid(String ldapGuid) {
		StringBuffer sbAux = new StringBuffer(" INNER JOIN IUSERDEPTHDR IUSERDEPTHDR")
											.append(" ON DEPTID = IUSERDEPTHDR.ID")
											.append(" INNER JOIN IUSERLDAPGRPHDR IUSERLDAPGRPHDR")
											.append(" ON IUSERDEPTHDR.CRTRID = IUSERLDAPGRPHDR.ID")
											.append(" AND IUSERDEPTHDR.TYPE=")
											.append(Departamento.LDAP_DEPT_TYPE)
											.append(" WHERE IUSERLDAPGRPHDR.LDAPGUID='")
											.append(ldapGuid)
											.append("'");

		return sbAux.toString();
	}

	public String getByDeptsId(int ids[]) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_DEPTID).append(" IN( ");
		for(int i=0; i<ids.length; i++){
			sbAux.append(ids[i]);
			if(i != ids.length-1)
				sbAux.append(",");
		}
		sbAux.append(")");
		return sbAux.toString();
	}

	public String getOrderByDesc() {
		StringBuffer sbAux = new StringBuffer("ORDER BY ").append(CN_NAME_QUALIFIED);
		return sbAux.toString();
	}

	public String getByDesasociadasALibro(int bookId) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_QUALIFIED).append(" NOT IN ")
		.append("(SELECT id_ofic FROM scr_bookofic WHERE id_book = ").append(bookId).append(") ");
		return sbAux.toString();
	}

	public StringBuffer getInitialWhere() {
		StringBuffer sb = new StringBuffer("WHERE ").append(CN_ID_ORGS_QUALIFIED).append(" = ").append(CN_ID_ORG).append(" ");
		return sb;
	}

	public String getOficinasDesasociadasByAgregateUser(int id) {
		StringBuffer sb = new StringBuffer("WHERE ").append(CN_ID_QUALIFIED)
				.append(" NOT IN ").append(
						"(SELECT idofic FROM scr_usrofic WHERE iduser = ")
				.append(id).append(") AND scr_ofic.deptid != ").append(
						"(SELECT deptid FROM iuseruserhdr WHERE id = ").append(
						id).append(") ");

		return sb.toString();
	}

	public String getByAgregateUser(int id) {
		StringBuffer sb = getInitialWhere().append(" AND ").append(
				CN_ID_QUALIFIED).append(" = ").append(
				SicresUsuarioOficinaTabla.TABLE_NAME).append(POINT).append(
				SicresUsuarioOficinaTabla.CN_IDOFIC).append(" AND ").append(
				SicresUsuarioOficinaTabla.TABLE_NAME).append(POINT).append(
				SicresUsuarioOficinaTabla.CN_IDUSER).append(" = ").append(id)
				.append(" ");
		return sb.toString();
	}

	public String getTableNameWithAgregateUsers() {
		return TABLE_NAME + SEPARATOR + TABLE_NAME_ORGANIZACIONES
				+ SEPARATOR + SicresUsuarioOficinaTabla.TABLE_NAME;
	}

	public String getTableNameWithGroupLdap() {
		return TABLE_NAME + SEPARATOR + TABLE_DEPT_LDAP + SEPARATOR
				+ TABLE_GROUP_LDAP;
	}

	public String getOficinasDesasociadasUsuario(int idUser, int idsGroup[]) {
		StringBuffer sbAux = new StringBuffer();

		sbAux.append("WHERE ").append(CN_DEPTID_QUALIFIED).append(" = ")
				.append(CN_DEPT_ID);
		sbAux.append(" AND ").append(CN_GROUP_ID).append(" = ").append(
				CN_DEPT_CRTRID);
		if (idsGroup.length > 0) {
			sbAux.append(" AND ").append(CN_GROUP_ID).append(" NOT IN( ");
			for (int i = 0; i < idsGroup.length; i++) {
				sbAux.append(idsGroup[i]);
				if (i != idsGroup.length - 1)
					sbAux.append(",");
			}
			sbAux.append(")");
		}
		sbAux.append(" AND ").append(CN_DEPT_TYPE).append(" = ").append(
				Departamento.LDAP_DEPT_TYPE);
		sbAux.append(" AND ").append(CN_ID_QUALIFIED).append(" NOT IN (");
		sbAux.append(" SELECT IDOFIC FROM SCR_USROFIC WHERE IDUSER=").append(
				idUser).append(")");

		return sbAux.toString();
	}

}
