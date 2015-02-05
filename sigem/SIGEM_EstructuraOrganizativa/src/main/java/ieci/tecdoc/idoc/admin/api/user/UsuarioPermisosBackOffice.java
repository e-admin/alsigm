package ieci.tecdoc.idoc.admin.api.user;

public interface UsuarioPermisosBackOffice {

	public int getIdUsuario();

	public void setIdUsuario(int idUsuario);

	public String getNombre();
	
	public void setNombre(String nombre);

	public String getNombreReal() ;

	public void setNombreReal(String nombreReal) ;

	public int getIdAplicacion();
	  
	public void setIdAplicacion(int idAplicacion);

	public String getPrimerApellido();

	public void setPrimerApellido(String primerApellido);

	public String getSegundoApellido();

	public void setSegundoApellido(String segundoApellido);

	public void load(int idUsuario, int idAplicacion, String entidad) throws Exception;	  

	public void store(String entidad) throws Exception;

	public void update(String entidad) throws Exception;
	
	public void insert(String entidad) throws Exception;

	public boolean existUsuarioPermiso(int idUsuario, int idAplicacion, String entidad) throws Exception;
	
}
