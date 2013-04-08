package ieci.tecdoc.sgm.admin.interfaces;

public interface AccionMultientidad {

	/**
	 * @return el identificador
	 */
	public abstract String getIdentificador();

	/**
	 * @param identificador el identificador a fijar
	 */
	public abstract void setIdentificador(String identificador);

	/**
	 * @return el nombre
	 */
	public abstract String getNombre();

	/**
	 * @param nombre el nombre a fijar
	 */
	public abstract void setNombre(String nombre);

	/**
	 * @return el claseConfiguradora
	 */
	public abstract String getClaseConfiguradora();

	/**
	 * @param claseConfiguradora el claseConfiguradora a fijar
	 */
	public abstract void setClaseConfiguradora(String claseConfiguradora);

	/**
	 * @return el claseEjecutora
	 */
	public abstract String getClaseEjecutora();

	/**
	 * @param claseEjecutora el claseEjecutora a fijar
	 */
	public abstract void setClaseEjecutora(String claseEjecutora);

	/**
	 * @return el infoAdicional
	 */
	public abstract String getInfoAdicional();

	/**
	 * @param infoAdicional el infoAdicional a fijar
	 */
	public abstract void setInfoAdicional(String infoAdicional);

	public abstract String toXML(boolean header);
	
	public abstract String toString();
	
}