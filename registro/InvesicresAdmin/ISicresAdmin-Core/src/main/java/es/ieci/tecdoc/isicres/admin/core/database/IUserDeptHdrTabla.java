package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;

public class IUserDeptHdrTabla {

	private static final Logger logger = Logger.getLogger(IUserDeptHdrTabla.class);

	private static final String SEPARATOR = ", ";
	private static final String POINT = ".";
	private static final String BLANK = " ";
	public static final String TABLE_NAME = "iuserdepthdr";
	private static final String CN_ID = "id";
	private static final String CN_NAME = "name";
	private static final String CN_PARENTID = "parentid";
	private static final String CN_MGRID = "mgrid";
	private static final String CN_TYPE = "type";
	private static final String CN_REMARKS = "remarks";
	private static final String CN_CRTRID = "crtrid";
	private static final String CN_CRTNDATE = "crtndate";
	private static final String CN_UPDRID = "updrid";
	private static final String CN_UPDDATE = "upddate";

	//Para la relacion entre grupos, departamentos y oficinas
	public static final String TABLE_NAME_GROUP = "iuserldapgrphdr";
	public static final String CN_ID_GROUP = "id";
	public static final String CN_LDAPGUID_GROUP = "ldapguid";

//	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_NAME + SEPARATOR + CN_PARENTID + SEPARATOR + CN_MGRID + SEPARATOR + CN_TYPE + SEPARATOR + CN_REMARKS + SEPARATOR + CN_CRTRID + SEPARATOR + CN_CRTNDATE + SEPARATOR + CN_UPDRID + SEPARATOR + CN_UPDDATE;
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_NAME + SEPARATOR + CN_PARENTID + SEPARATOR + CN_MGRID + SEPARATOR + CN_TYPE + SEPARATOR + CN_CRTRID + SEPARATOR + CN_CRTNDATE + SEPARATOR + CN_UPDRID +SEPARATOR + CN_UPDDATE;
	private static final String QUALIFIED_COLUMN_NAMES = TABLE_NAME + POINT + CN_ID + SEPARATOR + TABLE_NAME + POINT + CN_NAME + SEPARATOR + TABLE_NAME + POINT + CN_PARENTID + SEPARATOR + TABLE_NAME + POINT + CN_MGRID + SEPARATOR + TABLE_NAME + POINT + CN_TYPE + SEPARATOR + TABLE_NAME + POINT + CN_CRTRID + SEPARATOR + TABLE_NAME + POINT + CN_CRTNDATE + SEPARATOR + TABLE_NAME + POINT + CN_UPDRID + SEPARATOR + TABLE_NAME + POINT + CN_UPDDATE;
	private static final String UPDATE_COLUMN_NAMES = CN_TYPE;

	public IUserDeptHdrTabla() {
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

	public String getTableNameWithGroup() {
	   return getQualifiedTableName() + SEPARATOR + TABLE_NAME_GROUP + BLANK + TABLE_NAME_GROUP;
	}

	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}

	public String getQualifiedColumnNames() {
	   return QUALIFIED_COLUMN_NAMES;
	}

	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

	public String getByTypeAndCrtId(int type, int crtid) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_TYPE).append(" = ").append(type);
		sbAux.append(" AND ").append(CN_CRTRID).append(" = ").append(crtid);
		return sbAux.toString();
	}

	public String getByGuidsGroupAndDeptId(int deptId, String ldapGuidsGroup[]) {
//		select count(*) from iuserdepthdr, iuserldapgrphdr
//		where iuserdepthdr.type=2 and
//		crtrid=iuserldapgrphdr.id and
//		iuserldapgrphdr.ldapguid in ('90d2eb8603125e46b8a6152a34268828','d69e0fb538d1ae4984f1c3b7747b9554') and
//		iuserdepthdr.id=11
		StringBuffer sbAux = new StringBuffer(" WHERE ")
			.append(TABLE_NAME).append(POINT).append(CN_ID).append("=").append(deptId)
			.append(" AND ")
			.append(TABLE_NAME).append(POINT).append(CN_TYPE).append("=").append(Departamento.LDAP_DEPT_TYPE)
			.append(" AND ")
			.append(TABLE_NAME).append(POINT).append(CN_CRTRID).append("=").append(TABLE_NAME_GROUP).append(POINT).append(CN_ID_GROUP);
			if(ldapGuidsGroup.length > 0){
				sbAux.append(" AND ").append(TABLE_NAME_GROUP).append(POINT).append(CN_LDAPGUID_GROUP).append(" IN (");
				for(int i=0; i<ldapGuidsGroup.length; i++){
					sbAux.append("'").append(ldapGuidsGroup[i]).append("'");
					if(i != ldapGuidsGroup.length-1)
						sbAux.append(",");
				}
				sbAux.append(")");
			}

		return sbAux.toString();
	}
}