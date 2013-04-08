package gcontrol.vos;

public interface ICAOrganoVO {

	public abstract String getCodigo();

	public abstract void setCodigo(String codigo);

	public abstract String getDescripcion();

	public abstract void setDescripcion(String descripcion);

	public abstract String getIdOrg();

	public abstract void setIdOrg(String id);

	public abstract String getIdArchivoReceptor();

	public abstract void setIdArchivoReceptor(String idArchivoReceptor);

	public abstract String getIdOrgSExtGestor();

	public abstract void setIdOrgSExtGestor(String idOrgSExtGestor);

	public abstract String getNombre();

	public abstract void setNombre(String nombre);

	public abstract String getSistExtGestor();

	public abstract void setSistExtGestor(String sistExtGestor);

	public abstract String getVigente();

	public abstract void setVigente(String vigente);

	public abstract String getNombreLargo();

	public abstract void setNombreLargo(String nombreLargo);

}