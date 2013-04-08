package ieci.tecdoc.sgm.admin.database;

public class AccionMultientidadTabla {

	private static final String SEPARATOR=", ";
	   
	protected static final String TABLE_NAME="sgm_adm_acciones";

	protected static final String CN_ID="id";
	protected static final String CN_NOMBRE="nombre";
	protected static final String CN_CLASE_CONFIGURADORA="clase_config";
	protected static final String CN_CLASE_EJECUTORA="clase_exec";
	protected static final String CN_INFO_ADICIONAL="info_adicional";

	private static final String ALL_COLUMN_NAMES=CN_ID+SEPARATOR+CN_NOMBRE+SEPARATOR+CN_CLASE_CONFIGURADORA+SEPARATOR+CN_CLASE_EJECUTORA+SEPARATOR+CN_INFO_ADICIONAL;
	
	public String getTableName() {
		return TABLE_NAME;
	}

	public String getAllColumnNames() {
		return ALL_COLUMN_NAMES;
	}

	public String getIdColumnName() {
		return CN_ID;
	}
	
	public String getNombreColumnName() {
		return CN_NOMBRE;
	}
	
	public String getClaseConfiguradoraColumnName() {
		return CN_CLASE_CONFIGURADORA;
	}

	public String getClaseEjecutoraColumnName() {
		return CN_CLASE_EJECUTORA;
	}
	
	public String getInfoAdicionalColumnName() {
		return CN_INFO_ADICIONAL;
	}

	public String getById(String id){
		StringBuffer sbAux=new StringBuffer("WHERE ").append(CN_ID)
		.append("='").append(id).append("'");
		return sbAux.toString();
	}
}
