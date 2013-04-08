package ieci.tecdoc.sgm.admin.database;


/*
 * $Id: UsuarioTabla.java,v 1.1.2.2 2008/04/19 16:03:35 afernandez Exp $
 */

public class UsuarioTabla {

	private static final String SEPARATOR=", ";
	   
	protected static final String TABLE_NAME="sgm_adm_usuarios";

	protected static final String CN_USUARIO="usuario";
	protected  static final String CN_PASSWORD="password";	
	protected static final String CN_NOMBRE="nombre";
	protected static final String CN_APELLIDOS="apellidos";
	protected static final String CN_FECHA_ALTA="fecha_alta";
	
	
	private static final String ALL_COLUMN_NAMES=CN_USUARIO+SEPARATOR+
	CN_PASSWORD+SEPARATOR+CN_NOMBRE+SEPARATOR+
	CN_APELLIDOS+SEPARATOR+CN_FECHA_ALTA;
	
		
	public String getTableName() {
		return TABLE_NAME;
	}
	public String getTableNameExt(){
		return TABLE_NAME+SEPARATOR+PerfilTabla.TABLE_NAME;
	}
	
	public String getAllColumnNames() {
		return ALL_COLUMN_NAMES;
	}
	
	public String getUpdateColumnNames() {
		return  CN_NOMBRE+SEPARATOR+
				CN_APELLIDOS;
	}
	
	public String getEntityUsersColumnNames(){
		return ALL_COLUMN_NAMES + SEPARATOR + PerfilTabla.CN_ID_ENTIDAD;
	}

	public String getUsuarioColumnName() {
		return CN_USUARIO;
	}
	
	public String getPasswordColumnName() {
		return CN_PASSWORD;
	}	
	public String getNombreColumnName() {
		return CN_NOMBRE;
	}
	
	public String getApellidosColumnName() {
		return CN_APELLIDOS;
	}
	public String getFechaAltaColumnName() {
		return CN_FECHA_ALTA;
	}

	public String getByUserId(String usuario){
		if (usuario != null) {
			usuario = usuario.replaceAll("'", "''");
		}
		StringBuffer sbAux=new StringBuffer("WHERE ").append(CN_USUARIO).append("='").append(usuario).append("'");
		return sbAux.toString();
	}
	
	public String getAuth(String user, String password){
		if (user != null) {
			user = user.replaceAll("'", "''");
		}
		if (password != null) {
			password = password.replaceAll("'", "''");
		}
		StringBuffer sbAux=new StringBuffer("WHERE ").append(CN_USUARIO).append("='")
							.append(user).append("' and ").append(CN_PASSWORD).append("='")
							.append(password).append("'");
		return sbAux.toString();

	}
	public String getByEntityId(String entityId){
		StringBuffer sbAux=new StringBuffer("WHERE ").append(CN_USUARIO).append("=")
		.append(PerfilTabla.CN_ID_USUARIO).append(" and ")
		.append(PerfilTabla.CN_ID_ENTIDAD).append("='").append(entityId).append("'");
		return sbAux.toString();
	}
}
