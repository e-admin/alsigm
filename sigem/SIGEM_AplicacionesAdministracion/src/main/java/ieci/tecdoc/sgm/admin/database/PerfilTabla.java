package ieci.tecdoc.sgm.admin.database;

/*
 * $Id: PerfilTabla.java,v 1.1.2.2 2008/04/22 18:25:54 afernandez Exp $
 */

public class PerfilTabla {

	private static final String SEPARATOR=", ";
	   
	protected static final String TABLE_NAME="sgm_adm_perfiles";

	protected static final String CN_ID_ENTIDAD="id_entidad";
	protected static final String CN_ID_APLICACION="id_aplicacion";
	protected static final String CN_ID_USUARIO="id_usuario";

	private static final String ALL_COLUMN_NAMES=CN_ID_ENTIDAD+SEPARATOR+CN_ID_APLICACION+SEPARATOR+CN_ID_USUARIO;
	
	public String getTableName() {
		return TABLE_NAME;
	}

	public String getAllColumnNames() {
		return ALL_COLUMN_NAMES;
	}

	public String getIdEntidadColumnName() {
		return CN_ID_ENTIDAD;
	}
	
	public String getIdAplicacionColumnName() {
		return CN_ID_APLICACION;
	}
	
	public String getIdUsuarioColumnName() {
		return CN_ID_USUARIO;
	}
	
	public String getByRole(String idEntidad, String idUsuario, String idAplicacion){
		StringBuffer sbAux=new StringBuffer("WHERE ").append(CN_ID_ENTIDAD)
		.append("='").append(idEntidad).append("' and ").append(CN_ID_USUARIO)
		.append("='").append(idUsuario).append("' and ").append(CN_ID_APLICACION)
		.append("='").append(idAplicacion).append("'");
		return sbAux.toString();
	}
	
	public String getByUser4Entity(String idEntidad, String idUsuario){
		StringBuffer sbAux=new StringBuffer("WHERE ").append(CN_ID_ENTIDAD)
		.append("='").append(idEntidad).append("' and ").append(CN_ID_USUARIO)
		.append("='").append(idUsuario).append("'");
		return sbAux.toString();
	}
		
	public String getByUser(String idUsuario){
		StringBuffer sbAux=new StringBuffer("WHERE ").append(CN_ID_USUARIO)
		.append("='").append(idUsuario).append("'");
		return sbAux.toString();
	}
}
